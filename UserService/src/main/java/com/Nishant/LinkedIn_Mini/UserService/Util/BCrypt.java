package com.Nishant.LinkedIn_Mini.UserService.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCrypt {


    private final PasswordEncoder passwordEncoder;
    BCrypt(PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
    }

    public String hash(String password)
    {
        return passwordEncoder.encode(password);
    }

    public boolean match(String passwordPlainText , String encodedPassword)
    {
        CharSequence rawPassword = passwordPlainText;
        return passwordEncoder.matches (rawPassword ,  encodedPassword);
    }
}
