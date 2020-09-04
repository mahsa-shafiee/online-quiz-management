package com.maktab.onlineQuizManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class EmailService {

    private JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        mailSender.send(email);
    }


    public void sendRegistrationEmail(User user, HttpServletRequest request) {
        String appUrl = request.getScheme() + "://" +
                request.getServerName() + ":" + request.getServerPort();
        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        registrationEmail.setTo(user.getEmailAddress());
        registrationEmail.setSubject("Registration Confirmation");
        registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
                + appUrl + "/confirm?token=" + user.getConfirmationToken());
        sendEmail(registrationEmail);
    }
}
