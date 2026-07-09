package com.Nishant.LinkedIn_Mini.PostService.Service;

import com.nishant.linkedinmini.common.contracts.Dto.KafkaEventDto.PostCreatedEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PostCreatedEventProducer {
    private final KafkaTemplate<String, PostCreatedEventDto> kafkaTemplate;

    public PostCreatedEventProducer(KafkaTemplate<String, PostCreatedEventDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPostEvent(PostCreatedEventDto event) {
        //here set the username in the event payload
        if(event.getUserName() == null){
            //fetch the userdetails with the help of the userId using the feign call
            log.info("Username missing in postCreated event. Fetching user details for userId={}",
                    event.getUserId());

            log.error("TO DO: fetch the details user then proceed");

        }
        log.info("Publishing postCreated event for post created by userId={}",event.getUserId());

        kafkaTemplate.send("post-created-topic", event);

        log.info("Successfully published postCreated event to topic={}","post-created-topic");
    }
}
