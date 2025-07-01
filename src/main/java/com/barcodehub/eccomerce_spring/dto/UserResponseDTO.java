package com.barcodehub.eccomerce_spring.dto;

import com.barcodehub.eccomerce_spring.models.Role;
import java.time.LocalDateTime;

public record UserResponseDTO(
        Integer id,
        String name,
        String email,
        Role role,
        LocalDateTime createdAt
) {}