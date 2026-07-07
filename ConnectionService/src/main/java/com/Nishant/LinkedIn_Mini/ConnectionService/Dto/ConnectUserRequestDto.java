package com.Nishant.LinkedIn_Mini.ConnectionService.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ConnectUserRequestDto {
    @NotNull(message = "destinationUserId is required")
    @Positive(message = "Destination user id must be a positive number")
    private Long destinationUserId;
}
