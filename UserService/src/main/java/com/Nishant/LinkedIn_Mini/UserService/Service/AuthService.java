package com.Nishant.LinkedIn_Mini.UserService.Service;

import com.Nishant.LinkedIn_Mini.UserService.Dto.SignInRequestDto;
import com.Nishant.LinkedIn_Mini.UserService.Dto.UserDto;
import com.Nishant.LinkedIn_Mini.UserService.Entity.UserEntity;
import com.Nishant.LinkedIn_Mini.UserService.Repositroy.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public UserDto signUp(SignInRequestDto signInRequestDto) {
        //here before saving the paswword we will dcerypt it
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(signInRequestDto.getUserName());
        userEntity.setEmail(signInRequestDto.getEmail());
        String encryptedPassword = signInRequestDto.getPassword();
        /*
        here at first becrypt the password
         */
        userEntity.setPassword(encryptedPassword);
        userRepository.save(userEntity);

        //now return it
        return modelMapper.map(userEntity , UserDto.class); //map the userentity value to the userDto and then return the userDto
    }
}
