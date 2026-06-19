package com.Nishant.LinkedIn_Mini.NotificationService.Dto.FeignDto;

import lombok.Data;

@Data
public class NotificationUserInfoDto {
    private Long userId;
    private String userName;
    private String email;
}
