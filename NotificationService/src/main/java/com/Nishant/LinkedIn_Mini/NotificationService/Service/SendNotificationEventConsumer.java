package com.Nishant.LinkedIn_Mini.NotificationService.Service;

import com.Nishant.LinkedIn_Mini.NotificationService.Dto.EventDto.SendNotificationEventDto;
import com.Nishant.LinkedIn_Mini.NotificationService.Dto.FeignDto.UserInfoDto;
import com.Nishant.LinkedIn_Mini.NotificationService.FeignClient.GetUserInfoFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class SendNotificationEventConsumer
{
    private final GetUserInfoFeign getUserInfoFeign;

    private final EmailService emailService;

    public SendNotificationEventConsumer(
            GetUserInfoFeign getUserInfoFeign,
            EmailService emailService)
    {
        this.getUserInfoFeign = getUserInfoFeign;
        this.emailService = emailService;
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
                );

        emailService.sendPostNotificationEmail(
                event.getReceipientEmail(),
                follower.getUserName(),
                event.getContent()
        );
    }
}