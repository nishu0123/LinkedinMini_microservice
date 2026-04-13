package com.Nishant.LinkedIn_Mini.NotificationService.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPostNotificationEmail(String recipientEmail, String senderName, String postContent) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("New Post from " + senderName);
        message.setText("Hey! " + senderName + " just posted: \n\n" + postContent);
        message.setFrom("your-app@linkedin-mini.com");

        mailSender.send(message);
        log.info("Email sent successfully to {}", recipientEmail);
    }
}