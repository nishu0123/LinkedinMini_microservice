package com.Nishant.LinkedIn_Mini.NotificationService.Service;

import com.Nishant.LinkedIn_Mini.NotificationService.Constant.NotificationChannel;
import com.Nishant.LinkedIn_Mini.NotificationService.Dto.NotificationRequest;

public interface NotificationStrategy{

    NotificationChannel getSupportedChannel();

    //NotificationRequestDto , this payload should contain all the field that
    //is required or will be required in future to add another method
    //like email , number , these two are important
    //NotificationRequestDto - sender mail , receiver mail , receiver mobile number
    //and how payload will be transeffered ?  , this is also a question
    void send(NotificationRequest request);
}
