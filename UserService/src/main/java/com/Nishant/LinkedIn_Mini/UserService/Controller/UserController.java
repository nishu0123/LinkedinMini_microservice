package com.Nishant.LinkedIn_Mini.UserService.Controller;

import com.Nishant.LinkedIn_Mini.UserService.Dto.*;
//import com.Nishant.LinkedIn_Mini.UserService.Entity.UserEntity;
import com.Nishant.LinkedIn_Mini.UserService.Service.AuthService;
import com.Nishant.LinkedIn_Mini.UserService.Service.UserService;
import com.nishant.linkedinmini.common.contracts.Dto.FeignDto.NotificationUserInfoDto;
import com.nishant.linkedinmini.common.contracts.Dto.FeignDto.UserInfoDto;
//import lombok.NoArgsConstructor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final AuthService authService;

    @Autowired
    private final UserService userService;

    @GetMapping("/greet")
    public String greetFromUserService()
    {
        return "hello world from the user service ";
    }

    //this is working data is being saved into the database , before adding the authentication
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@Valid @RequestBody SignInRequestDto signInRequestDto)
    {
        //here we will get the username and the password

        //at this time we have to store the user information and password will hashed or encrypted
        //we can use any library to decrypt the password
        log.info("signUp request reached to the controller");
        UserDto userDto =  authService.signUp( signInRequestDto);
        return new ResponseEntity<>(userDto , HttpStatus.CREATED);
    }

    /// login logic need to change , for every user and password it response null data in userDto
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> logIn(@Valid @RequestBody LoginDto loginDto)
    {
        log.info("login request reached to the controller");
        LoginResponseDto response = authService.logIn(loginDto);

        return ResponseEntity.ok(response);//just returning it so that we can compile the code successfully
    }
    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(@RequestHeader("X-USER-ID") Long UserId)
    {
        //implement the logic in the service layer
        userService.logout(UserId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //use id will be passed and it will return the user info in which only username and mail will be there
    @GetMapping("/{userId}/getUserInfo")
    public ResponseEntity<UserInfoDto> GetUserInfo(@PathVariable Long userId)
    {
        log.info("request received in controller to getUserInfo for id = " + userId);
        //Here we will return the userInfoDto which only contain username and the mail
        //call the service layer to get the info from the database
        UserInfoDto userInfoDto = userService.GetUserInfo(userId);
        return ResponseEntity.ok(userInfoDto);
    }

    @PostMapping("/userInfo/bulk")
    List<NotificationUserInfoDto> GetUserInfoInBulk(@RequestBody List<Long> userIdList)
    {

        List<NotificationUserInfoDto> response = userService.getUserInfoInBulk(userIdList);
        return response;
    }

}
