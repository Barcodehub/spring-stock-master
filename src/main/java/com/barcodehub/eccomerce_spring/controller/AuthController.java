package com.barcodehub.eccomerce_spring.controller;

import com.barcodehub.eccomerce_spring.dto.AuthRequestDTO;
import com.barcodehub.eccomerce_spring.dto.AuthResponseDTO;
import com.barcodehub.eccomerce_spring.dto.errors.SuccessResponseDTO;
import com.barcodehub.eccomerce_spring.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<SuccessResponseDTO<AuthResponseDTO>> login(
            @Valid @RequestBody AuthRequestDTO request) {
        return authService.authenticate(request);
    }
}