package com.Nishant.LinkedIn_Mini.NotificationService.Service;

import com.Nishant.LinkedIn_Mini.NotificationService.Constant.NotificationStatus;
import com.Nishant.LinkedIn_Mini.NotificationService.Dto.EventDto.SendNotificationEventDto;
import com.Nishant.LinkedIn_Mini.NotificationService.Dto.NotificationRequest;
import com.Nishant.LinkedIn_Mini.NotificationService.Entity.NotificationEntity;
import com.Nishant.LinkedIn_Mini.NotificationService.FeignClient.GetUserInfoFeign;
import com.Nishant.LinkedIn_Mini.NotificationService.Repository.NotificationRepository;
import com.nishant.linkedinmini.common.contracts.Constants.DeliveryChannel;
import com.nishant.linkedinmini.common.contracts.Dto.FeignDto.UserInfoDto;
import com.nishant.linkedinmini.common.contracts.NotificationRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class SendNotificationEventConsumer
{
    private final GetUserInfoFeign getUserInfoFeign;

    private final EmailService emailService;

    private final NotificationRepository notificationRepository;

    private final NotificationStrategyOrchestrator notificationStrategyOrchestrator;

    public SendNotificationEventConsumer(
            GetUserInfoFeign getUserInfoFeign,
            EmailService emailService, NotificationRepository notificationRepository, NotificationStrategyOrchestrator notificationStrategyOrchestrator)
    {
        this.getUserInfoFeign = getUserInfoFeign;
        this.emailService = emailService;
        this.notificationRepository = notificationRepository;
        this.notificationStrategyOrchestrator = notificationStrategyOrchestrator;
    }

    @KafkaListener(
            topics = "send-notification-topic",
            groupId = "notification-group"
    )
    public void consume(
            NotificationRequestDto event)
    {
        log.info(
                "Received notification event for follower {}",
                event.getRecipientUserId()
        );

        UserInfoDto follower =
                getUserInfoFeign.GetUserInfo(
                        event.getRecipientUserId()
                ).getBody().getData();


//
//        NotificationRequestDto notificationRequest = new NotificationRequestDto();
//        notificationRequest.setMessage(event.getContent());
//        notificationRequest.setSenderUserName(follower.getUserName());
//        notificationRequest.setReceiverEmailId(event.getReceipientEmail());
//        //decision making logic
//        notificationRequest.setChannel(DeliveryChannel.EMAIL);//this will decide which method of notify will be used


        //insert a row against this notification and set the status pending
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setNotificationId(event.getNotificationId());
        notificationEntity.setEventType(event.getEventType());
        notificationEntity.setCreatedAt(event.getCreatedAt());
        //TO DO : check this payload part , how we can manage
        notificationEntity.setPayload(event.getPayload().toString());
        notificationEntity.setDeliveryChannel(event.getChannel());
        notificationEntity.setRetryCount(0);
        notificationEntity.setStatus(NotificationStatus.PENDING);

        notificationRepository.save(notificationEntity);


        //when producer produce event it pass NotificationRequestDto and all the data are already set
        //and we will not change it , that will be the single source of truth

        notificationStrategyOrchestrator.notify(event);

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