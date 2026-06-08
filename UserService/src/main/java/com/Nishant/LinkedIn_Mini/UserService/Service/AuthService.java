package com.Nishant.LinkedIn_Mini.UserService.Service;

import com.Nishant.LinkedIn_Mini.UserService.Dto.LoginDto;
import com.Nishant.LinkedIn_Mini.UserService.Dto.PersonDto;
import com.Nishant.LinkedIn_Mini.UserService.Dto.SignInRequestDto;
import com.Nishant.LinkedIn_Mini.UserService.Dto.UserDto;
import com.Nishant.LinkedIn_Mini.UserService.Entity.UserEntity;
import com.Nishant.LinkedIn_Mini.UserService.Exception.DuplicateUserNameException;
import com.Nishant.LinkedIn_Mini.UserService.Exception.InvalidCredentialsException;
import com.Nishant.LinkedIn_Mini.UserService.Exception.UserNotFoundException;
import com.Nishant.LinkedIn_Mini.UserService.FeignClient.UserFeign;
import com.Nishant.LinkedIn_Mini.UserService.Repositroy.UserRepository;
import com.Nishant.LinkedIn_Mini.UserService.Util.BCrypt;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class AuthService {

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

    public String logIn(LoginDto loginDto) {
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
        String token = jwtService.generateAccessToken(userId , userName , userRole);

        return token;
    }
}
