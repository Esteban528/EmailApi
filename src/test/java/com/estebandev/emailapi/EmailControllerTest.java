package com.estebandev.emailapi;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.estebandev.emailapi.config.SecurityConfig;
import com.estebandev.emailapi.controller.EmailController;
import com.estebandev.emailapi.controller.dto.EmailDTO;
import com.estebandev.emailapi.service.EmailService;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.net.http.HttpHeaders;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * EmailControllerTest
 */
@WebMvcTest(EmailController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
public class EmailControllerTest {
    Logger logger = LoggerFactory.getLogger(EmailControllerTest.class);

    @Value("${var.username}")
    private String username;

    @Value("${var.password}")
    private String password;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmailService emailService;

    @Test
    void testSendEmail() throws Exception {
        EmailDTO emailDTO = new EmailDTO("testing@spring.com", "Testing", "Hello! This is a test");
        String credentials = Base64.getEncoder().encodeToString(String.format("%s:%s", username, password).getBytes());

        Mockito.doNothing().when(emailService).send(emailDTO);

        mockMvc.perform(post("/email/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "email": "testing@spring.com",
                          "subject": "Testing",
                          "message": "Hello! This is a test"
                        }
                        """)
                .header("Authorization", "Basic " + credentials))
                .andExpect(status().isOk());
        Mockito.verify(emailService).send(emailDTO);
    }

}
