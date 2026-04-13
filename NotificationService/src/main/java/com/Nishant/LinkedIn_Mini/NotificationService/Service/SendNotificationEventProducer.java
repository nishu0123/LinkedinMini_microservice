package com.Nishant.LinkedIn_Mini.NotificationService.Service;

import com.Nishant.LinkedIn_Mini.NotificationService.Dto.EventDto.SendNotificationEventDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class SendNotificationEventProducer {
    private final KafkaTemplate<String, SendNotificationEventDto> kafkaTemplate;

    public SendNotificationEventProducer(KafkaTemplate<String, SendNotificationEventDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotificationEvent(SendNotificationEventDto event) {
        kafkaTemplate.send("send-notification-topic", event);
    }
}
