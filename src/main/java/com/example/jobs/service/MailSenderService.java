package com.example.jobs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailSenderService {
    final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;

    public void sendMail(String to, String subject, String body) {
        var message = new SimpleMailMessage();
        message.setFrom(from);
        message.setSubject(subject);
        message.setTo(to);
        message.setText(body);
        javaMailSender.send(message);
    }

    public void sendMailResetPassword(String email, String password) {
        var subject = "Your credentials";
        var body = "This is your credentials:\n\n" +
                "Email: " + email + "\n\n" +
                "Password: " + password + "\n\n" +
                "Please reset your password after you log in to the following website:\n\n" +
                "http://localhost:8080";

        sendMail(email, subject, body);
    }

}