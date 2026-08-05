package com.Nishant.LinkedIn_Mini.NotificationService.Service;

import com.Nishant.LinkedIn_Mini.NotificationService.Dto.EventDto.SendNotificationEventDto;
import com.Nishant.LinkedIn_Mini.NotificationService.Dto.NotificationRequest;
import com.Nishant.LinkedIn_Mini.NotificationService.FeignClient.GetFollowerFeign;
import com.Nishant.LinkedIn_Mini.NotificationService.FeignClient.GetUserInfoFeign;
import com.nishant.linkedinmini.common.contracts.Constants.DeliveryChannel;
import com.nishant.linkedinmini.common.contracts.Dto.FeignDto.NotificationUserInfoDto;
import com.nishant.linkedinmini.common.contracts.Dto.FeignDto.PersonDto;
import com.nishant.linkedinmini.common.contracts.Dto.FeignDto.UserInfoDto;
import com.nishant.linkedinmini.common.contracts.NotificationRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.Nishant.LinkedIn_Mini.NotificationService.Constant.AppConstants.SUPER_USER_FOLLOWER_MIN_LIMIT;


@Slf4j
@Service
public class PostCreatedEventConsumer {

    private final GetFollowerFeign getFollowerFeign;

    private final  SendNotificationEventProducer sendNotificationEventProducer;

    private final GetUserInfoFeign getUserInfoFeign;

//    private final GetUserInfoInBulkFeign getUserInfoInBulkFeign;

    private final EmailService emailService;

    private final NotificationStrategyOrchestrator notificationStrategyOrchestrator;

    public PostCreatedEventConsumer(GetFollowerFeign getFollowerFeign, SendNotificationEventProducer sendNotificationEventProducer, GetUserInfoFeign getUserInfoFeign, EmailService emailService, NotificationStrategyOrchestrator notificationStrategyOrchestrator) {
        this.getFollowerFeign = getFollowerFeign;
        this.sendNotificationEventProducer = sendNotificationEventProducer;
        this.getUserInfoFeign = getUserInfoFeign;

        this.emailService = emailService;
        this.notificationStrategyOrchestrator = notificationStrategyOrchestrator;
    }


    @KafkaListener(topics = "post-created-topic", groupId = "notification-group-v2")
    public void consumePostEvent(NotificationRequestDto postCreatedEventDto) {
//        System.out.println("New post by: " + postCreatedEventDto.);
        System.out.println("Image URL: " + postCreatedEventDto.getPayload().get("postContent"));

        // Logic to find followers and send emails/push notifications


        //now call the connection service to get the first-degree connection and send the email to all the user
        List<PersonDto> followersList =  getFollowerFeign.getFirstDegreeConnection(Long.valueOf(postCreatedEventDto.getPayload().get("userId").toString()) , postCreatedEventDto.getPayload().get("userId").toString()).getBody().getData();


        //chek if there is no followers then avoid feign call
        if(followersList == null || followersList.isEmpty())
        {
            log.info("NO followers found for user {}",
                    Long.valueOf(postCreatedEventDto.getPayload().get("userId").toString()));
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
        List<NotificationUserInfoDto> followersInfoList =  getUserInfoFeign.GetUserInfoInBulk(userIdList).getBody().getData();

        //now i have list of the user to whom i have to send the mail or notification

        //lets apply logic to handle the super user problem
        if(followersList.size() >  SUPER_USER_FOLLOWER_MIN_LIMIT)
        {
            int noOfFollowers = followersInfoList.size();
            for(int i = 0; i<noOfFollowers; i++)
            {
                //here now produce send notification event for each user
                NotificationRequestDto sendNotificationEventDto  = new NotificationRequestDto();
                sendNotificationEventDto.setCreatedAt(LocalDateTime.now());
                sendNotificationEventDto.setNotificationId(UUID.randomUUID());
                sendNotificationEventDto.setTemplateName("default");
                sendNotificationEventDto.setRecipientUserId(followersInfoList.get(i).getUserId());
                sendNotificationEventDto.setRecipientEmail(followersInfoList.get(i).getEmail());
                sendNotificationEventDto.setEventType(postCreatedEventDto.getEventType());
                sendNotificationEventDto.setChannel(postCreatedEventDto.getChannel());
                sendNotificationEventDto.setPayload(postCreatedEventDto.getPayload());

//                sendNotificationEventDto.setUserId(Long.valueOf(postCreatedEventDto.getPayload().get("userId").toString()));
//                sendNotificationEventDto.setContent(postCreatedEventDto.getPayload().get("imageUrl").toString());
//                sendNotificationEventDto.setUsersFollowerId(followersInfoList.get(i).getUserId());
//                sendNotificationEventDto.setReceipientEmail(followersInfoList.get(i).getEmail());
//                sendNotificationEventDto.setUserName(postCreatedEventDto.getPayload().get("userName").toString());

                sendNotificationEventProducer.sendNotificationEvent(sendNotificationEventDto);
                log.info(" sendNotificationEvent produced");

            }
        }else{
            //where user is less than the celebirity limit then user async and threading to
            //process the sending email
            int noOfFollowers = followersInfoList.size();
            for(int i = 0; i<noOfFollowers; i++)
            {

                NotificationRequestDto notificationRequest  = new NotificationRequestDto();
                notificationRequest.setCreatedAt(LocalDateTime.now());
                notificationRequest.setNotificationId(UUID.randomUUID());
                notificationRequest.setTemplateName("default");
                notificationRequest.setRecipientUserId(followersInfoList.get(i).getUserId());
                notificationRequest.setRecipientEmail(followersInfoList.get(i).getEmail());
                notificationRequest.setEventType(postCreatedEventDto.getEventType());
                notificationRequest.setChannel(postCreatedEventDto.getChannel());
                notificationRequest.setPayload(postCreatedEventDto.getPayload());



//                String sendername = postCreatedEventDto.getPayload().get("userName").toString();
//                String receipientMail = followersInfoList.get(i).getEmail();
//                String postUrl = postCreatedEventDto.getPayload().get("imageUrl").toString();
//
//                NotificationRequestDto notificationRequest = new NotificationRequestDto();
//                notificationRequest.setMessage(postUrl);
//                notificationRequest.setSenderUserName(sendername);
//                notificationRequest.setReceiverEmailId(receipientMail);
//                //correct the below notification-channel and delivery-channel two enums class have been created for the same purpoes remove this imbiguity
//                notificationRequest.setChannel(DeliveryChannel.EMAIL);//this will decide , user will be notified with which means of communication
//                //set the sender user name if null
//                if(notificationRequest.getSenderUserName() == null)
//                {
//                    UserInfoDto senderUserInfoDto =
//                            getUserInfoFeign.GetUserInfo(
//                                    Long.valueOf(postCreatedEventDto.getPayload().get("userId").toString())
//                            ).getBody().getData();
//                    notificationRequest.setSenderUserName(senderUserInfoDto.getUserName());
//                }

                //pass the dto that we got from the post-service itself

                notificationStrategyOrchestrator.notify(notificationRequest);

            }

        }
    }
}
