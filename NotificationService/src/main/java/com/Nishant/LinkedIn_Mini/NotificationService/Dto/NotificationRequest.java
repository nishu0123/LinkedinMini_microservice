package com.Nishant.LinkedIn_Mini.NotificationService.Dto;

import com.nishant.linkedinmini.common.contracts.Constants.DeliveryChannel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
//For this dto no validation is required because data in this dto will depend of the method
//of notification .
public class NotificationRequest {
    private Long senderUserId;
    private String senderUserName;
    private Long receiverUserId;
    private String receiverUserName;
    private String receiverEmailId;
    private String receiverMobileNumber;
    private DeliveryChannel channel;
    private String message;
}
