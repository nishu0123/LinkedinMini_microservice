package com.Nishant.LinkedIn_Mini.UserService.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RefreshTokenRequestDto {

    @NotBlank(message = "RefreshToken is required")
    private String refreshToken;
}
