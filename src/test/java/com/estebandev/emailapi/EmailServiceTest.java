package com.estebandev.emailapi;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import java.io.UnsupportedEncodingException;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;
import org.springframework.test.context.ActiveProfiles;

import com.estebandev.emailapi.controller.dto.EmailDTO;
import com.estebandev.emailapi.service.EmailComponent;
import com.estebandev.emailapi.service.EmailService;

import jakarta.mail.MessagingException;

/**
 * EmailServiceTest
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class EmailServiceTest {

    @Mock
    EmailComponent emailComponent;

    @InjectMocks
    EmailService emailService;
    
    void testEmailService() throws MailException, UnsupportedEncodingException, MessagingException {
        doNothing().when(emailComponent).send(any(EmailDTO.class));
        emailService.send(any(EmailDTO.class));
        verify(emailComponent).send(any(EmailDTO.class));
    }
}
