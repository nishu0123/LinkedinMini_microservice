package com.Nishant.LinkedIn_Mini.ConnectionService.Dto;

import lombok.Data;

@Data
public class ConnectUserResponseDto {
    private Long sourceUserId;
    private Long destinationUserId;
}
