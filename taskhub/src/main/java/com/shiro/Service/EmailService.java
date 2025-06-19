package com.shiro.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendReminder(String to){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mf.rahman98@gmail.com");
        String subject = "Task Reminder";
        String text = "Reminder that you have task due at TaskHub";
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

}
