package com.Nishant.LinkedIn_Mini.PostService.Service;

import com.Nishant.LinkedIn_Mini.PostService.Dto.EventDto.PostCreatedEventDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PostCreatedEventProducer {
    private final KafkaTemplate<String, PostCreatedEventDto> kafkaTemplate;

    public PostCreatedEventProducer(KafkaTemplate<String, PostCreatedEventDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPostEvent(PostCreatedEventDto event) {
        kafkaTemplate.send("post-created-topic", event);
    }
}
