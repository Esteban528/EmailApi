package com.estebandev.emailapi.service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.estebandev.emailapi.config.MailConfig;
import com.estebandev.emailapi.controller.dto.EmailDTO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

/**
 * EmailComponent
 */
@Component
@RequiredArgsConstructor
public class EmailComponent {
    private final JavaMailSender sender;
    private final MailConfig mailConfig;

    private MimeMessage makeMessage(EmailDTO emailDTO) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(mailConfig.getSenderEmail(), mailConfig.getSenderName());
        helper.setTo(emailDTO.email());
        helper.setSubject(emailDTO.subject());
        helper.setText(emailDTO.message(), true);

        return message;
    }

    public void send(EmailDTO emailDTO) throws MailException, UnsupportedEncodingException, MessagingException {
        sender.send(makeMessage(emailDTO));
    }
}
