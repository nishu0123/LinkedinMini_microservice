package com.Nishant.LinkedIn_Mini.NotificationService.Service;

import com.Nishant.LinkedIn_Mini.NotificationService.Dto.EventDto.PostCreatedEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationConsumer {

    // 1. Accepts a full DTO (JSON will be automatically converted)
    @KafkaListener(topics = "post.created")
    public void handlePostCreated(PostCreatedEventDto event) {
        log.info("Processing rich object: {}", event.getImageUrl());
    }

    // 2. Accepts a simple Long/String (No complex JSON needed)
    @KafkaListener(topics = "post.liked")
    public void handlePostLiked(Long postId) {
        log.info("Processing simple ID: {}", postId);
    }
}