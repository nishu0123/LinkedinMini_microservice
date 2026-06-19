package com.Nishant.LinkedIn_Mini.NotificationService.Dto.EventDto;

import lombok.Data;

@Data
public class SendNotificationEventDto {
    private Long userId;
    private String userName;
    private Long usersFollowerId;
//    private String usersFollowerEmailId; //for email again i have do the feign-call to the user service. , or consumer can
    //do the feign call to the user service then this field is ot required and this will be the memory efficient
    private String content;
    private String receipientEmail;
}
