package com.Nishant.LinkedIn_Mini.NotificationService.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send")
public class NotificationController {

    @GetMapping("/greet")
    public String greetFromUserService()
    {
        return "welcome to notification service ";
    }
}
