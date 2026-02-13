package com.Nishant.LinkedIn_Mini.NotificationService.Service;

import com.Nishant.LinkedIn_Mini.NotificationService.Dto.EventDto.PostCreatedEventDto;
import com.Nishant.LinkedIn_Mini.NotificationService.Dto.EventDto.SendNotificationEventDto;
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

    private final  SendNotificationEventProducer sendNotificationEventProducer;

    public PostCreatedEventConsumer(GetFollowerFeign getFollowerFeign, SendNotificationEventProducer sendNotificationEventProducer) {
        this.getFollowerFeign = getFollowerFeign;
        this.sendNotificationEventProducer = sendNotificationEventProducer;
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
            int followerCount = i + 1;
            PersonDto tempval = followersList.get(i); //tempval will store the followers details
            log.info("follower " + followerCount + " = " + tempval);
            log.info("notification sent to " + tempval.getUserName());

            //write logic to send the mail  , to get the mail we need to call the feign to user srvice to get the
            //user detail

            /*
            //produce event sendNotificationEvent
            SendNotificationEventDto sendNotificationEventDto = new SendNotificationEventDto();
            sendNotificationEventDto.setContent(event.getImageUrl());
            sendNotificationEventDto.setUserId(event.getUserId());
            sendNotificationEventDto.setUsersFollowerId(tempval.getUserId());

            sendNotificationEventProducer.sendNotificationEvent(sendNotificationEventDto);
            */

        }
    }
}
