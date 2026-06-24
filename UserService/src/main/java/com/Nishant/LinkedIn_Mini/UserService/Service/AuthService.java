package com.Nishant.LinkedIn_Mini.UserService.Service;

import com.Nishant.LinkedIn_Mini.UserService.Dto.*;
import com.Nishant.LinkedIn_Mini.UserService.Entity.RefreshTokenEntity;
import com.Nishant.LinkedIn_Mini.UserService.Entity.UserEntity;
import com.Nishant.LinkedIn_Mini.UserService.Exception.DuplicateUserNameException;
import com.Nishant.LinkedIn_Mini.UserService.Exception.InvalidCredentialsException;
import com.Nishant.LinkedIn_Mini.UserService.Exception.UserNotFoundException;
import com.Nishant.LinkedIn_Mini.UserService.FeignClient.UserFeign;
import com.Nishant.LinkedIn_Mini.UserService.Repositroy.RefreshTokenRepository;
import com.Nishant.LinkedIn_Mini.UserService.Repositroy.UserRepository;
import com.Nishant.LinkedIn_Mini.UserService.Util.BCrypt;
import feign.FeignException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final UserFeign userFeign;


//    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BCrypt bCrypt;

    @Autowired
    private final JwtService jwtService;

    @Value("${jwt.refresh-token-expiry-days}")
    private Long refreshTokenExpiryDays;


    //it will make sure that the signup and the node creation was successful or
    //noting happened
    @Transactional
    public UserDto signUp(SignInRequestDto signInRequestDto) {
        log.info("signup request reached to the service layer");
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(signInRequestDto.getUserName());
        userEntity.setEmail(signInRequestDto.getEmail());
        //BCrypt the password before saving it into the database
        String encryptedPassword =  bCrypt.hash( signInRequestDto.getPassword() );
        userEntity.setPassword(encryptedPassword);
        if(userEntity.getUserRole() == null)
        {
            userEntity.setUserRole("Default");
        }
        userRepository.save(userEntity);

        //after user data has been saved on signup now we will create a node in the connection
        //service and for that we will call feign client api
        //here we will send only the user information there will be no connection
        //only a node will be created that will represent the graph-ql of all the user
        PersonDto personDto = new PersonDto();
        personDto.setUserId(userEntity.getId());
        personDto.setUserName(userEntity.getUserName());

        try
        {
            ResponseEntity<PersonDto> response =  userFeign.addUserNode(userEntity.getId() , personDto);
            log.info("node created for username : " + personDto.getUserName());
        }
        catch (FeignException ex){

            //check the status code
            if (ex.status() == 409) {
                throw new DuplicateUserNameException(
                        "Username already exists"
                );
            }

            log.error(
                    "Failed to create node for username: {}",
                    personDto.getUserName(),
                    ex
            );

            throw ex;
        }

        //now return it
        return modelMapper.map(userEntity , UserDto.class); //map the user entity value to the userDto and then return the userDto
    }

    public LoginResponseDto logIn(LoginDto loginDto) {
        log.info("login request reached to the service layer");

        UserEntity userEntity = userRepository
                .findByEmail(loginDto.getEmail());


        if(userEntity == null)
        {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        //now match the password
        boolean isValidPassword = bCrypt.match(loginDto.getPassword() , userEntity.getPassword());

        UserDto userDto  = new UserDto();
        if(!isValidPassword)
        {
            throw new InvalidCredentialsException("Invalid username or password");
        }else{
            userDto.setEmail(userEntity.getEmail());
            userDto.setUserName(userEntity.getUserName());
        }

        Long userId = userEntity.getId();
        String userName = userEntity.getUserName();
        String userRole = "USER";
        String accessToken = jwtService.generateAccessToken(userId , userName , userRole);

        //generate refresh token
        String refreshToken = UUID.randomUUID().toString();
        //save this refresh token in the database.
        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setUserId(userEntity.getId());
        //set the expiry time as 7 days , added in the configuration
        LocalDateTime expiryTime = LocalDateTime.now().plusDays(refreshTokenExpiryDays);
        refreshTokenEntity.setExpiryTime(expiryTime);

        try{
            refreshTokenRepository.save(refreshTokenEntity);
        }catch (Exception e){
//            throw new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR , "unable to login something went wrong");
            log.error("Failed to save refresh token for userId={}",
                    userEntity.getId(),
                    e);

            //here i can use this exception because it contains message
            throw new InvalidCredentialsException (
                    "Unable to save refresh token");
        }


        //now we have to return the access token as well as the refresh token
        LoginResponseDto response = new LoginResponseDto();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);

        return response;
    }

    public String refreshAcessToken(RefreshTokenRequestDto request) {

            String refreshToken = request.getRefreshToken();

            RefreshTokenEntity tokenEntity =
                    refreshTokenRepository.findByToken(refreshToken)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Invalid refresh token"));


            if(tokenEntity.getExpiryTime()
                    .isBefore(LocalDateTime.now())) {

                throw new RuntimeException(
                        "Refresh token expired");
            }

            UserEntity user = userRepository.findById(
                            tokenEntity.getUserId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "User not found"));

            String userRole = user.getUserRole();
            String newAccessToken =
                    jwtService.generateAccessToken(user.getId() , user.getUserName() ,userRole);

            return newAccessToken;
    }
}
