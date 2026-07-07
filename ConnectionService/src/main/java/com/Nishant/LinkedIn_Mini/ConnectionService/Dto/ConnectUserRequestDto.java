package com.Nishant.LinkedIn_Mini.ConnectionService.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConnectUserRequestDto {
    @NotNull(message = "destinationUserId is required")
    private Long destinationUserId;
}
