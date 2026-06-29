package com.Nishant.LinkedIn_Mini.NotificationService.Service;

import com.Nishant.LinkedIn_Mini.NotificationService.Constant.NotificationChannel;
import com.Nishant.LinkedIn_Mini.NotificationService.Dto.NotificationRequest;
import org.springframework.stereotype.Service;

@Service
public class SmsNotificationStrategy implements NotificationStrategy{

    @Override
    public NotificationChannel getSupportedChannel() {
        return NotificationChannel.SMS;
    }

    @Override
    public void send(NotificationRequest request) {
        System.out.println("send(NotificationRequestDto request) function called inside SmsNotificationStrategy");
    }
}
