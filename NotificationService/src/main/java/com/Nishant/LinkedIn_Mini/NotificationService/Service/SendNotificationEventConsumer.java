package com.Nishant.LinkedIn_Mini.NotificationService.Service;

import com.Nishant.LinkedIn_Mini.NotificationService.Dto.EventDto.SendNotificationEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
public class SendNotificationEventConsumer {

    @KafkaListener(topics = "send-notification-topic", groupId = "notification-group")
    private void SendNotificationEventConsume(SendNotificationEventDto event)
    {
        //here we have the details about the user and the follower and the conent

        //do a feign call to user service to get the email of the follower
        log.info("consuming send-notificaton-topic event");



        //and then send the email to the follower

    }
}
