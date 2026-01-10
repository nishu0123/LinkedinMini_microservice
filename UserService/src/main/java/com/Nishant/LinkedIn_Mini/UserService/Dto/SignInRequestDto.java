package com.Nishant.LinkedIn_Mini.UserService.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Data
@Getter
@Setter
@Slf4j
public class SignInRequestDto {
    String userName;
    String email;
    String password;
}
