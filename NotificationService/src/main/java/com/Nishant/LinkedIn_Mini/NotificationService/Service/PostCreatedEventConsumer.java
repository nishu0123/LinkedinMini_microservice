package com.Nishant.LinkedIn_Mini.NotificationService.Service;

import com.Nishant.LinkedIn_Mini.NotificationService.Dto.EventDto.PostCreatedEventDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class PostCreatedEventConsumer {

    @KafkaListener(topics = "post-created-topic", groupId = "notification-group")
    public void consumePostEvent(PostCreatedEventDto event) {
        System.out.println("New post by: " + event.getUserId());
        System.out.println("Image URL: " + event.getImageUrl());

        // Logic to find followers and send emails/push notifications
    }
}
