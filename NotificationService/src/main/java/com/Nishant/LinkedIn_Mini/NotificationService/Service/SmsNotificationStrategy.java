package com.Nishant.LinkedIn_Mini.NotificationService.Service;

import com.Nishant.LinkedIn_Mini.NotificationService.Dto.NotificationRequest;
import com.nishant.linkedinmini.common.contracts.Constants.DeliveryChannel;
import com.nishant.linkedinmini.common.contracts.NotificationRequestDto;
import org.springframework.stereotype.Service;

@Service
public class SmsNotificationStrategy implements NotificationStrategy{

    @Override
    public DeliveryChannel getSupportedChannel() {
        return DeliveryChannel.SMS;
    }

    @Override
    public void send(NotificationRequestDto request) {
        System.out.println("send(NotificationRequestDto request) function called inside SmsNotificationStrategy");
    }
}
