package com.Nishant.LinkedIn_Mini.UserService.Controller;

import com.Nishant.LinkedIn_Mini.UserService.Dto.LoginDto;
import com.Nishant.LinkedIn_Mini.UserService.Dto.SignInRequestDto;
import com.Nishant.LinkedIn_Mini.UserService.Dto.UserDto;
import com.Nishant.LinkedIn_Mini.UserService.Service.AuthService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final AuthService authService;



    @GetMapping("/greet")
    public String greetFromUserService()
    {
        return "hello world from the user service ";
    }

    //this is working data is being saved into the database
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignInRequestDto signInRequestDto)
    {
        //here we will get the username and the password

        //at this tiem we have to store the user information and password will hashed or encrypted
        //we can use any library to decrypt the password
        UserDto userDto =  authService.signUp( signInRequestDto);
        return new ResponseEntity<>(userDto , HttpStatus.CREATED);
    }

    /// login logic need to change , for every user and password it response null data in userDto
    @PostMapping("/lognin")
    public ResponseEntity<UserDto> logIn(@RequestBody LoginDto loginDto)
    {
        //here we have to verify the userName and password , using the same bcrypt or we can
        //give this responsibility to
        UserDto userDto = new UserDto();
        return ResponseEntity.ok(userDto);//just returning it so that we can compile the code successfully
    }

}
