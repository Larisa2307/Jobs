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
        var body = "These are your credentials for Jobs:\n\n" +
                "Email: " + email + "\n" +
                "Password: " + password + "\n\n" +
                "Please reset your password after you login to the following website:\n" +
                "http://localhost:8080";

        sendMail(email, subject, body);
    }

    public void sendMailWithDecision(String email, String decision, String companyName, String jobName) {
        var subject = "Application information";
        String body;
        if ("Accepted".equals(decision)) {
            body = "Hello! From the " + companyName + " company," +
                    " we inform you that you have been accepted for the position of " + jobName + ". " +
                    "To establish more details, we will contact you by phone.\n" +
                    "\n" +
                    "A good day,\n" +
                    companyName + " company!";
        } else {
            body = "Hello! From the " + companyName + " company," +
                    " we inform you that you have been rejected for the position of " + jobName + ". " +
                    "Good luck in the future!\n" +
                    "\n" +
                    "A good day,\n" +
                    companyName + " company!";
        }

        sendMail(email, subject, body);
    }

    public void sendMailToScheduleInterview(String email, String companyName, String jobName,
                                            String date, String hour) {
        var subject = "Application information";
        var body = "Hello! From the " + companyName + " company," +
                " we inform you that we would like to schedule an interview on " + date + " at " + hour +
                " to have a discussion related to the " + jobName + " position you applied for.\n" +
                "\n" +
                "A good day,\n" +
                companyName + " company!";

        sendMail(email, subject, body);
    }
}