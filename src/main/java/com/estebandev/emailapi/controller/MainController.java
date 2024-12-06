package com.estebandev.emailapi.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

/**
 * MainController
 */
@RestController
@RequestMapping("/")
public class MainController {
    private final String redirectURL = "https://github.com/Esteban528/EmailApi";

    @GetMapping
    public void home(HttpServletResponse response) throws IOException {
        response.sendRedirect(redirectURL);
    }
}
