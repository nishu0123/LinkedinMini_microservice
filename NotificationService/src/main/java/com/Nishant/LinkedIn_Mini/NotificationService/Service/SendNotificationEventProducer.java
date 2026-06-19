package com.Nishant.LinkedIn_Mini.NotificationService.Service;

import com.Nishant.LinkedIn_Mini.NotificationService.Dto.EventDto.SendNotificationEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SendNotificationEventProducer {
    private final KafkaTemplate<String, SendNotificationEventDto> kafkaTemplate;

    public SendNotificationEventProducer(KafkaTemplate<String, SendNotificationEventDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotificationEvent(SendNotificationEventDto event) {
        kafkaTemplate.send("send-notification-topic", event);
    }
}
