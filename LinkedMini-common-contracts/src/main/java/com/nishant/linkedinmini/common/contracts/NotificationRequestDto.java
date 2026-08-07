package com.nishant.linkedinmini.common.contracts;

import com.nishant.linkedinmini.common.contracts.Constants.DeliveryChannel;
import com.nishant.linkedinmini.common.contracts.Constants.NotificationEventType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class NotificationRequestDto {

    private UUID notificationId;

    private NotificationEventType eventType;

    private Long recipientUserId;

    private String recipientEmail;

    private DeliveryChannel channel;

    private String templateName;

    private Map<String , Object> payload; //we want store information in form of json

    private LocalDateTime createdAt;
}