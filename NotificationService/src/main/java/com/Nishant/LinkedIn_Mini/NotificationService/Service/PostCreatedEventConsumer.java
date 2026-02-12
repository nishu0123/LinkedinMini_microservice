package com.Nishant.LinkedIn_Mini.NotificationService.Service;

import com.Nishant.LinkedIn_Mini.NotificationService.Dto.EventDto.PostCreatedEventDto;
import com.Nishant.LinkedIn_Mini.NotificationService.Dto.FeignDto.PersonDto;
import com.Nishant.LinkedIn_Mini.NotificationService.FeignClient.GetFollowerFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.System.in;


@Slf4j
@Service
public class PostCreatedEventConsumer {

    private final GetFollowerFeign getFollowerFeign;

    public PostCreatedEventConsumer(GetFollowerFeign getFollowerFeign) {
        this.getFollowerFeign = getFollowerFeign;
    }

    @KafkaListener(topics = "post-created-topic", groupId = "notification-group")
    public void consumePostEvent(PostCreatedEventDto event) {
        System.out.println("New post by: " + event.getUserId());
        System.out.println("Image URL: " + event.getImageUrl());

        // Logic to find followers and send emails/push notifications


        //now call the connection service to get the first-degree connection and send the email to all the user
        List<PersonDto> followersList =  getFollowerFeign.getFirstDegreeConnection(event.getUserId() , event.getUserId().toString());

        //now produce an event followerInfoReceivedToSendMailOrPushNotification from notification service
        //and notification service will again consume the same event to send the mail

        for(int i = 0; i<followersList.size(); i++)
        {
            PersonDto tempval = followersList.get(i); //tempval will store the follwers details
            log.info("follower " + i+1 + " = " + tempval);
        }
    }
}
