package com.nishant.linkedinmini.common.contracts.FeignDto;

import lombok.Data;

@Data
public class NotificationUserInfoDto {
    private Long userId;
    private String userName;
    private String email;
}
