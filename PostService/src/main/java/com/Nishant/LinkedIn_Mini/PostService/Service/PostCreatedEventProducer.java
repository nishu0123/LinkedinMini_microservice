package com.Nishant.LinkedIn_Mini.PostService.Service;

import com.nishant.linkedinmini.common.contracts.KafkaEventDto.PostCreatedEventDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PostCreatedEventProducer {
    private final KafkaTemplate<String, PostCreatedEventDto> kafkaTemplate;

    public PostCreatedEventProducer(KafkaTemplate<String, PostCreatedEventDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPostEvent(PostCreatedEventDto event) {
        //here set the username in the event payload
        if(event.getUserName() == null){
            //fetch the userdetails with the help of the userId using the feign call

        }
        kafkaTemplate.send("post-created-topic", event);
    }
}
