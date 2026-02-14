package com.Nishant.LinkedIn_Mini.NotificationService.Service;

import com.Nishant.LinkedIn_Mini.NotificationService.Dto.EventDto.PostCreatedEventDto;
import com.Nishant.LinkedIn_Mini.NotificationService.Dto.EventDto.SendNotificationEventDto;
import com.Nishant.LinkedIn_Mini.NotificationService.Dto.FeignDto.PersonDto;
import com.Nishant.LinkedIn_Mini.NotificationService.Dto.FeignDto.UserInfoDto;
import com.Nishant.LinkedIn_Mini.NotificationService.FeignClient.GetFollowerFeign;
import com.Nishant.LinkedIn_Mini.NotificationService.FeignClient.GetUserInfoFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.Nishant.LinkedIn_Mini.NotificationService.Constant.AppConstants.SUPER_USER_FOLLOWER_MIN_LIMIT;
import static java.lang.System.in;


@Slf4j
@Service
public class PostCreatedEventConsumer {

    private final GetFollowerFeign getFollowerFeign;

    private final  SendNotificationEventProducer sendNotificationEventProducer;

    private final GetUserInfoFeign getUserInfoFeign;

    public PostCreatedEventConsumer(GetFollowerFeign getFollowerFeign, SendNotificationEventProducer sendNotificationEventProducer, GetUserInfoFeign getUserInfoFeign) {
        this.getFollowerFeign = getFollowerFeign;
        this.sendNotificationEventProducer = sendNotificationEventProducer;
        this.getUserInfoFeign = getUserInfoFeign;
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

        //just for logging purpose
        for(int i = 0; i<followersList.size(); i++)
        {
            int followerCount = i + 1;
            PersonDto tempval = followersList.get(i); //tempval will store the followers details
            log.info("follower " + followerCount + " = " + tempval);
            log.info("notification sent to " + tempval.getUserName());

        }

        //lets apply logic to handle the super user problem
        if(followersList.size() < SUPER_USER_FOLLOWER_MIN_LIMIT)
        {
            for(int i = 0; i<followersList.size(); i++)
            {

                PersonDto person = followersList.get(i); //tempval will store the followers details

                //write logic to send the mail  , to get the mail we need to call the feign to user srvice to get the
                //user detail

                //get the mail from user service using feign client
//                UserInfoDto followerInfo =  getUserInfoFeign.GetUserInfo(person.getUserId());
                //its working fine
                UserInfoDto followerInfo =  getUserInfoFeign.GetUserInfo(1L , 1L);//to check if it is working or not
                log.info("got the user info from the user service = "+ followerInfo);

                //now we have email id of the user , integrate the mail service to send the mail






            }


        }else{
                 /*
            //produce event sendNotificationEvent  , making the process asynchronous for large number of followers
            SendNotificationEventDto sendNotificationEventDto = new SendNotificationEventDto();
            sendNotificationEventDto.setContent(event.getImageUrl());
            sendNotificationEventDto.setUserId(event.getUserId());
            sendNotificationEventDto.setUsersFollowerId(tempval.getUserId());

            sendNotificationEventProducer.sendNotificationEvent(sendNotificationEventDto);
            */

        }
    }
}
