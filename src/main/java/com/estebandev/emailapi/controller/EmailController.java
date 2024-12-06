package com.estebandev.emailapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * EmailController
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    @PostMapping("/send") 
    public ResponseEntity<String> responseEntity() {
        return ResponseEntity.ok("Email Sended");
    }
    
}
