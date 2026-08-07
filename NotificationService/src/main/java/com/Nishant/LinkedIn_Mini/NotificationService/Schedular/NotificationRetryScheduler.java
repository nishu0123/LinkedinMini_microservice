package com.Nishant.LinkedIn_Mini.NotificationService.Schedular;

import com.Nishant.LinkedIn_Mini.NotificationService.Service.NotificationRetryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationRetryScheduler {

    private final NotificationRetryService retryService;

    @Scheduled(fixedDelay = 60000)
    public void retryFailedNotifications() throws JsonProcessingException {

        log.info("========== RETRY SCHEDULER TRIGGERED ==========");

        retryService.retryFailedNotifications();
    }
}