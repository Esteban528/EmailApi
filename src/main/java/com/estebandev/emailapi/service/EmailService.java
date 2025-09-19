package com.estebandev.emailapi.service;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.estebandev.emailapi.controller.dto.EmailDTO;
import com.estebandev.emailapi.controller.dto.EmailResponseDTO;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

/**
 * EmailService
 */
@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailComponent emailComponent;
    private Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void send(EmailDTO emailDTO) throws MailException, UnsupportedEncodingException, MessagingException {
        logger.debug("EmailDTO: " + emailDTO.toString());
        emailComponent.send(emailDTO);
    }

    public void send(EmailResponseDTO emailrDTO, MultipartFile file) throws MailException, UnsupportedEncodingException, MessagingException {
        logger.info("EmailDTO: " + emailrDTO.toString());
        emailComponent.send(emailrDTO, file);
    }
}
