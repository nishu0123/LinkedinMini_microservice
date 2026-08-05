package com.Nishant.LinkedIn_Mini.NotificationService.Service;


import com.Nishant.LinkedIn_Mini.NotificationService.Dto.NotificationRequest;
import com.nishant.linkedinmini.common.contracts.Constants.DeliveryChannel;
import com.nishant.linkedinmini.common.contracts.NotificationRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
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
    public DeliveryChannel getSupportedChannel() {
        return DeliveryChannel.EMAIL;
    }

    @Async("notificationExecutor") //find (notificationExecutor) this in the config
//    public void send(String recipientEmail, String senderName, String postContent) {
    @Override
    public void send(NotificationRequestDto request) {

        String userName = request.getPayload().get("userName").toString();
        String postContent = request.getPayload().get("postContent").toString();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getRecipientEmail());
        //senderName is null check this
        message.setSubject("New Post from " + userName);
        message.setText("Hey! " + userName + " just posted: \n\n" + postContent);
        message.setFrom("nishant@linkedin-mini.com");

        try {
            mailSender.send(message);
            log.info("Email sent successfully to {}", request.getRecipientEmail());
        } catch (MailException ex) {
            log.error("Failed to send email to {} : {}", request.getRecipientEmail(), ex.getMessage());
        }
    }
}
