package com.estebandev.emailapi;

import static org.mockito.Mockito.*;

import java.io.UnsupportedEncodingException;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ActiveProfiles;

import com.estebandev.emailapi.config.MailConfig;
import com.estebandev.emailapi.controller.dto.EmailDTO;
import com.estebandev.emailapi.service.EmailComponent;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class EmailComponentTest {
    private String from = "test@spring.com";

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MailConfig mailConfig;

    @InjectMocks
    private EmailComponent emailComponent;

    @BeforeEach
    void before() {
        when(mailConfig.getSenderEmail()).thenReturn("testing@example.com");
        when(mailConfig.getSenderName()).thenReturn("Estebandev");
    }

    @Test
    void send_ShouldSendEmail_WhenEmailDTOIsValid() throws MessagingException, UnsupportedEncodingException {

        // Arrange
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        String senderName = "Test Sender";
        String senderEmail = "test@sender.com";
        String recipientEmail = "recipient@test.com";
        String subject = "Test Subject";
        String body = "<h1>Test Message</h1>";

        EmailDTO emailDTO = new EmailDTO(recipientEmail, subject, body);

        // Act
        emailComponent.send(emailDTO);

        // Assert
        verify(javaMailSender).send(mimeMessage); // Verify that the sender.send was called
    }

    @Test
    void makeMessage_ShouldBuildMimeMessageProperly() throws MessagingException, UnsupportedEncodingException {
        // Arrange
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        String recipientEmail = "recipient@test.com";
        String subject = "Test Subject";
        String body = "<h1>Test Message</h1>";

        EmailDTO emailDTO = new EmailDTO(recipientEmail, subject, body);

        // Act
        emailComponent.send(emailDTO);

        // Assert
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        verify(mimeMessage)
                .setFrom(
                        new InternetAddress(mailConfig.getSenderEmail(), mailConfig.getSenderName(),
                                helper.getEncoding()));
        verify(mimeMessage).setRecipient(eq(Message.RecipientType.TO), any(InternetAddress.class));
        verify(mimeMessage).setSubject(subject);
        // verify(mimeMessage).setText(body, true);
    }
}
