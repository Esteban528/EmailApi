package com.estebandev.emailapi.controller.dto;

/**
 * EmailDTO
 */
public record EmailResponseDTO(
        String from, 
        String originalSubject, 
        String content, 
        String originalMessageId, 
        String originalReferences) {
}
