package com.Nishant.LinkedIn_Mini.ConnectionService.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ConnectUserRequestDto {
    @NotBlank(message = "destinationUserId is required")
    private Long destinationUserId;
}
