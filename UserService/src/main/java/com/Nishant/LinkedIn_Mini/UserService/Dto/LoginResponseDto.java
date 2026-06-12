package com.Nishant.LinkedIn_Mini.UserService.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@Data
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
}
