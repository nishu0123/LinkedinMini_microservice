package com.Nishant.LinkedIn_Mini.NotificationService.Service;

import com.Nishant.LinkedIn_Mini.NotificationService.Dto.EventDto.SendNotificationEventDto;
import com.nishant.linkedinmini.common.contracts.NotificationRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SendNotificationEventProducer {
    private final KafkaTemplate<String, NotificationRequestDto> kafkaTemplate;

    public SendNotificationEventProducer(KafkaTemplate<String, NotificationRequestDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotificationEvent(NotificationRequestDto event) {
        kafkaTemplate.send("send-notification-topic", event);
    }
}
