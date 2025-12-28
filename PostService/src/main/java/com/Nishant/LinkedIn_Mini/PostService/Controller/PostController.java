package com.Nishant.LinkedIn_Mini.PostService.Controller;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@NoArgsConstructor
@ResponseBody
@RequiredArgsConstructor
//@RestController = @Controler + @Responsebody
@RequestMapping("/core")
public class PostController {

    @GetMapping("/greet")
    public String greetFromPostService()
    {
        return "hello world from post service ";
    }

}
