package com.Nishant.LinkedIn_Mini.NotificationService.Schedular;

import com.Nishant.LinkedIn_Mini.NotificationService.Service.NotificationRetryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationRetryScheduler {

    private final NotificationRetryService retryService;

    @Scheduled(fixedDelay = 60000)
    public void retryFailedNotifications() throws JsonProcessingException {
        retryService.retryFailedNotifications();
    }
}