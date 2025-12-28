package com.Nishant.LinkedIn_Mini.UserService.Controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@NoArgsConstructor
@RestController
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/greet")
    public String greetFromUserService()
    {
        return "hello world from the user service ";
    }
}
