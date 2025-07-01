package com.barcodehub.eccomerce_spring.exception;

import com.barcodehub.eccomerce_spring.dto.errors.ErrorDetailDTO;
import com.barcodehub.eccomerce_spring.dto.errors.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.NOT_FOUND.toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAuthenticationFailed(AuthenticationFailedException ex) {
        ErrorDetailDTO errorDetail = new ErrorDetailDTO(
                "401",
                ex.getMessage(),
                ex.getField()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponseDTO(null, List.of(errorDetail)));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailExists(EmailAlreadyExistsException ex) {
        ErrorDetailDTO error = new ErrorDetailDTO(
                "400", // "USER_001"
                ex.getMessage(),
                ex.getFieldName() // "email"
        );

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(null, List.of(error)));
    }
}