package com.Nishant.LinkedIn_Mini.NotificationService.Service;

import com.Nishant.LinkedIn_Mini.NotificationService.Constant.NotificationChannel;
import com.Nishant.LinkedIn_Mini.NotificationService.Dto.EventDto.SendNotificationEventDto;
import com.Nishant.LinkedIn_Mini.NotificationService.Dto.NotificationRequest;
import com.Nishant.LinkedIn_Mini.NotificationService.FeignClient.GetUserInfoFeign;
import com.nishant.linkedinmini.common.contracts.Dto.FeignDto.UserInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class SendNotificationEventConsumer
{
    private final GetUserInfoFeign getUserInfoFeign;

    private final EmailService emailService;

    private final NotificationStrategyOrchestrator notificationStrategyOrchestrator;

    public SendNotificationEventConsumer(
            GetUserInfoFeign getUserInfoFeign,
            EmailService emailService, NotificationStrategyOrchestrator notificationStrategyOrchestrator)
    {
        this.getUserInfoFeign = getUserInfoFeign;
        this.emailService = emailService;
        this.notificationStrategyOrchestrator = notificationStrategyOrchestrator;
    }

    @KafkaListener(
            topics = "send-notification-topic",
            groupId = "notification-group"
    )
    public void consume(
            SendNotificationEventDto event)
    {
        log.info(
                "Received notification event for follower {}",
                event.getUsersFollowerId()
        );

        UserInfoDto follower =
                getUserInfoFeign.GetUserInfo(
                        event.getUsersFollowerId()
                ).getBody().getData();


        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setMessage(event.getContent());
        notificationRequest.setSenderUserName(follower.getUserName());
        notificationRequest.setReceiverEmailId(event.getReceipientEmail());
        //decision making logic
        notificationRequest.setChannel(NotificationChannel.EMAIL);//this will decide which method of notify will be used

        notificationStrategyOrchestrator.notify(notificationRequest);

        //now new implementation using strategy pattern should work
        /*
        emailService.sendPostNotificationEmail(
                event.getReceipientEmail(),
                follower.getUserName(),
                event.getContent()
        );
         */
    }
}