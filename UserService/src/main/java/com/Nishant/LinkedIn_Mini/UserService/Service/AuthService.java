package com.Nishant.LinkedIn_Mini.UserService.Service;

import com.Nishant.LinkedIn_Mini.UserService.Dto.LoginDto;
import com.Nishant.LinkedIn_Mini.UserService.Dto.SignInRequestDto;
import com.Nishant.LinkedIn_Mini.UserService.Dto.UserDto;
import com.Nishant.LinkedIn_Mini.UserService.Entity.UserEntity;
import com.Nishant.LinkedIn_Mini.UserService.Exception.InvalidCredentialsException;
import com.Nishant.LinkedIn_Mini.UserService.Exception.UserNotFoundException;
import com.Nishant.LinkedIn_Mini.UserService.Repositroy.UserRepository;
import com.Nishant.LinkedIn_Mini.UserService.Util.BCrypt;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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


//    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BCrypt bCrypt;

    @Autowired
    private final JwtService jwtService;


    public UserDto signUp(SignInRequestDto signInRequestDto) {
        log.info("signup request reached to the service layer");
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(signInRequestDto.getUserName());
        userEntity.setEmail(signInRequestDto.getEmail());
        //BCrypt the password before saving it into the database
        String encryptedPassword =  bCrypt.hash( signInRequestDto.getPassword() );
        userEntity.setPassword(encryptedPassword);
        userRepository.save(userEntity);

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
