package com.Nishant.LinkedIn_Mini.PostService.Service;

import com.nishant.linkedinmini.common.contracts.Constants.DeliveryChannel;
import com.nishant.linkedinmini.common.contracts.Constants.NotificationEventType;
import com.nishant.linkedinmini.common.contracts.Dto.KafkaEventDto.PostCreatedEventDto;
import com.nishant.linkedinmini.common.contracts.NotificationRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class PostCreatedEventProducer {
    private final KafkaTemplate<String, NotificationRequestDto> kafkaTemplate;

    public PostCreatedEventProducer(KafkaTemplate<String, NotificationRequestDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public Map<String, Object> createPostCreatedEventPayload(Long userId , String userName , String imageUrl) {

        Map<String, Object> payload = new HashMap<>();

        String userIdString = userId.toString();

        payload.put("userId", userIdString);
        payload.put("userName", userName);
        payload.put("imageUrl", imageUrl);

        return payload;
    }

    public void sendPostEvent(PostCreatedEventDto postCreatedEventDto) {
        //create the event payload
        NotificationRequestDto event = new NotificationRequestDto();
        //here set the username in the event payload
        if(postCreatedEventDto.getUserName() == null){
            //fetch the userdetails with the help of the userId using the feign call
            log.info("Username missing in postCreated event. Fetching user details for userId={}",
                    postCreatedEventDto.getUserId());

            log.error("TO DO: fetch the details user then proceed");

        }
        log.info("Publishing postCreated event for post created by userId={}",postCreatedEventDto.getUserId());

        //now set the data in the NotificationRequestDto
        event.setEventType(NotificationEventType.POST_CREATED);
        event.setCreatedAt(LocalDateTime.now());
        event.setChannel(DeliveryChannel.EMAIL);//i have send it through the mail
        //since payload is the map or we can say it is in the json format
        event.setPayload(createPostCreatedEventPayload(postCreatedEventDto.getUserId() , postCreatedEventDto.getUserName() ,postCreatedEventDto.getImageUrl()));
        event.setRecipientUserId(postCreatedEventDto.getUserId());
        event.setRecipientEmail(postCreatedEventDto.getEmail());
        event.setNotificationId(UUID.randomUUID());
        event.setTemplateName("default");

        kafkaTemplate.send("post-created-topic", event);

        log.info("Successfully published postCreated event to topic={}","post-created-topic");
    }
}
