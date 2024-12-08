package com.estebandev.emailapi.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estebandev.emailapi.controller.dto.EmailDTO;
import com.estebandev.emailapi.service.EmailService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

/**
 * EmailController
 */
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestBody EmailDTO emailDTO)
            throws MailException, UnsupportedEncodingException, MessagingException {
        emailService.send(emailDTO);
        return ResponseEntity.ok(true);
    }

    @ExceptionHandler({ MailException.class, MessagingException.class })
    public ResponseEntity<String> handle(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Sending mail error " + e.getMessage());
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    public ResponseEntity<String> handleUnsupportedEncoding(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Encoding not supported " + e.getMessage());
    }
}
