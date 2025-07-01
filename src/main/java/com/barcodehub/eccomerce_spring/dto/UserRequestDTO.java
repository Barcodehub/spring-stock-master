package com.barcodehub.eccomerce_spring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email formato no valido")
        String email,

        @NotBlank(message = "Password is required")
        String password

) {}