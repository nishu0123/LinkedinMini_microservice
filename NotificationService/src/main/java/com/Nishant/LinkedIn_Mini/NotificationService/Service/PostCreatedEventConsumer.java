package com.Nishant.LinkedIn_Mini.NotificationService.Service;

import com.Nishant.LinkedIn_Mini.NotificationService.Dto.EventDto.SendNotificationEventDto;
import com.Nishant.LinkedIn_Mini.NotificationService.FeignClient.GetFollowerFeign;
import com.Nishant.LinkedIn_Mini.NotificationService.FeignClient.GetUserInfoFeign;
import com.nishant.linkedinmini.common.contracts.FeignDto.NotificationUserInfoDto;
import com.nishant.linkedinmini.common.contracts.FeignDto.PersonDto;
import com.nishant.linkedinmini.common.contracts.KafkaEventDto.PostCreatedEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.Nishant.LinkedIn_Mini.NotificationService.Constant.AppConstants.SUPER_USER_FOLLOWER_MIN_LIMIT;


@Slf4j
@Service
public class PostCreatedEventConsumer {

    private final GetFollowerFeign getFollowerFeign;

    private final  SendNotificationEventProducer sendNotificationEventProducer;

    private final GetUserInfoFeign getUserInfoFeign;

//    private final GetUserInfoInBulkFeign getUserInfoInBulkFeign;

    private final EmailService emailService;

    public PostCreatedEventConsumer(GetFollowerFeign getFollowerFeign, SendNotificationEventProducer sendNotificationEventProducer, GetUserInfoFeign getUserInfoFeign, EmailService emailService) {
        this.getFollowerFeign = getFollowerFeign;
        this.sendNotificationEventProducer = sendNotificationEventProducer;
        this.getUserInfoFeign = getUserInfoFeign;

        this.emailService = emailService;
    }


    @KafkaListener(topics = "post-created-topic", groupId = "notification-group-v2")
    public void consumePostEvent(PostCreatedEventDto postCreatedEventDto) {
        System.out.println("New post by: " + postCreatedEventDto.getUserId());
        System.out.println("Image URL: " + postCreatedEventDto.getImageUrl());

        // Logic to find followers and send emails/push notifications


        //now call the connection service to get the first-degree connection and send the email to all the user
        List<PersonDto> followersList =  getFollowerFeign.getFirstDegreeConnection(postCreatedEventDto.getUserId() , postCreatedEventDto.getUserId().toString());


        //chek if there is no followers then avoid feign call
        if(followersList == null || followersList.isEmpty())
        {
            log.info("NO followers found for user {}",
                    postCreatedEventDto.getUserId());
            return;
        }

        //now produce an event followerInfoReceivedToSendMailOrPushNotification from notification service
        //and notification service will again consume the same event to send the mail


        List<Long> userIdList = new ArrayList<>();

        //just for logging purpose
        log.info("inside consumer function of  postEventCreated topic ");
        for(int i = 0; i<followersList.size(); i++)
        {
            int followerCount = i + 1;
            PersonDto tempval = followersList.get(i); //tempval will store the followers details
            log.info("follower " + followerCount + " = " + tempval);
//            log.info("notification sent to " + tempval.getUserName());

            //creating the list of the userId
            userIdList.add(followersList.get(i).getUserId());

        }

        //with the help of only one feign call fetch all the users data from userService
        //creating an another api which will implement the bulk
        List<NotificationUserInfoDto> followersInfoList =  getUserInfoFeign.GetUserInfoInBulk(userIdList);

        //now i have list of the user to whom i have to send the mail or notification

        //lets apply logic to handle the super user problem
        if(followersList.size() >  SUPER_USER_FOLLOWER_MIN_LIMIT)
        {
            int noOfFollowers = followersInfoList.size();
            for(int i = 0; i<noOfFollowers; i++)
            {
                //here now produce send notification event for each user
                SendNotificationEventDto sendNotificationEventDto  = new SendNotificationEventDto();
                sendNotificationEventDto.setUserId(postCreatedEventDto.getUserId());
                sendNotificationEventDto.setContent(postCreatedEventDto.getImageUrl());
                sendNotificationEventDto.setUsersFollowerId(followersInfoList.get(i).getUserId());
                sendNotificationEventDto.setReceipientEmail(followersInfoList.get(i).getEmail());
                sendNotificationEventDto.setUserName(postCreatedEventDto.getUserName());

                sendNotificationEventProducer.sendNotificationEvent(sendNotificationEventDto);
                log.info(" sendNotificationEvent produced");

            }
        }else{
            //where user is less than the celebirity limit then user async and threading to
            //process the sending email
            int noOfFollowers = followersInfoList.size();
            for(int i = 0; i<noOfFollowers; i++)
            {
                //To Do rather than sender mail , i have to set the sender name implement
                String sendername = postCreatedEventDto.getUserName();
                String receipientMail = followersInfoList.get(i).getEmail();
                String postUrl = postCreatedEventDto.getImageUrl();
                emailService.sendPostNotificationEmail(receipientMail , sendername , postUrl);
                log.info("email sent to " + receipientMail + " successfully");
            }

        }
    }
}
