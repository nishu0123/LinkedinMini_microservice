package com.Nishant.LinkedIn_Mini.UserService.Service;

import com.Nishant.LinkedIn_Mini.UserService.Entity.UserEntity;
import com.Nishant.LinkedIn_Mini.UserService.Exception.DuplicateUserNameException;
import com.Nishant.LinkedIn_Mini.UserService.Repositroy.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class ValidationService {

    @Autowired
    private final UserRepository userRepository;


    public void validateDuplicateUserByEmail(String email)
    {
        UserEntity userInfo = userRepository.findByEmail(email);
        if(null != userInfo){
            log.error("user trying to sign up already exist");
            throw new DuplicateUserNameException("user already exist");
        }
    }
}
