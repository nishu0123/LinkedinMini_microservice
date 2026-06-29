package com.Nishant.LinkedIn_Mini.NotificationService.Service;


import com.Nishant.LinkedIn_Mini.NotificationService.Constant.NotificationChannel;
import com.Nishant.LinkedIn_Mini.NotificationService.Dto.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailNotificationStrategy implements NotificationStrategy{

    private final JavaMailSender mailSender;

    public EmailNotificationStrategy(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public NotificationChannel getSupportedChannel() {
        return NotificationChannel.EMAIL;
    }


    @Async("notificationExecutor") //find (notificationExecutor) this in the config
//    public void send(String recipientEmail, String senderName, String postContent) {
    @Override
    public void send(NotificationRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getReceiverEmailId());
        //senderName is null check this
        message.setSubject("New Post from " + request.getSenderUserName());
        message.setText("Hey! " + request.getSenderUserName() + " just posted: \n\n" + request.getMessage());
        message.setFrom("your-app@linkedin-mini.com");

        mailSender.send(message);
        log.info("Email sent successfully to {}", request.getReceiverEmailId());
    }
}
