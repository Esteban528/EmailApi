package com.estebandev.emailapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * MailConfig
 */
@Component
@Data
public class MailConfig {
    @Value("${spring.mail.sendername}")
    private String senderName;

    @Value("${spring.mail.sendermail}")
    private String senderEmail;
}
