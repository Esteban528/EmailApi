package com.estebandev.emailapi.service;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.estebandev.emailapi.config.MailConfig;
import com.estebandev.emailapi.controller.dto.EmailDTO;
import com.estebandev.emailapi.controller.dto.EmailResponseDTO;

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
    private static final Logger logger = LoggerFactory.getLogger(EmailComponent.class);

    private MimeMessage makeMessage(EmailDTO emailDTO) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(mailConfig.getSenderEmail(), mailConfig.getSenderName());
        helper.setTo(emailDTO.email());
        helper.setSubject(emailDTO.subject());
        helper.setText(emailDTO.message(), true);

        return message;
    }

    private MimeMessage makeReply(EmailResponseDTO emailResponseDTO, MultipartFile file) throws MessagingException, UnsupportedEncodingException {
        MimeMessage reply = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(reply, true, "UTF-8");
        helper.setFrom(mailConfig.getSenderEmail(), mailConfig.getSenderName());
        helper.setTo(emailResponseDTO.from());
        helper.setSubject(emailResponseDTO.originalSubject());
        helper.setText(emailResponseDTO.content(), true);
        reply.setHeader("In-Reply-To", emailResponseDTO.originalMessageId());
        if (file != null && !file.isEmpty()) {
            helper.addAttachment(file.getOriginalFilename(), file);
        }

        String newReferences = (emailResponseDTO.originalReferences() != null
                && !emailResponseDTO.originalReferences().isBlank())
                        ? emailResponseDTO.originalReferences() + " " + emailResponseDTO.originalMessageId()
                        : emailResponseDTO.originalMessageId();

        reply.setHeader("References", newReferences);

        logger.info(mailConfig.toString());
        logger.info(reply.toString());

        return reply;
    }

    public void send(EmailDTO emailDTO) throws MailException, UnsupportedEncodingException, MessagingException {
        sender.send(makeMessage(emailDTO));
    }

    public void send(EmailResponseDTO emailResponseDTO, MultipartFile file) throws MailException, UnsupportedEncodingException, MessagingException {
        sender.send(makeReply(emailResponseDTO, file));
    }
}
