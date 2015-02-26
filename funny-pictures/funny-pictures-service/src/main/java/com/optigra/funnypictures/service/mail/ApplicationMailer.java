package com.optigra.funnypictures.service.mail;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by oleh on 25.02.15.
 */
@Service("applicationMailer")
public class ApplicationMailer {
    @Resource(name = "mailSender")
    private MailSender mailSender;

    /**
     * Send email with feedback.
     * @param from
     * @param to
     * @param subject
     * @param msg
     */
    public void sendMail(final String from, final String to, final String subject, final String msg) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);
        mailSender.send(message);
    }
}
