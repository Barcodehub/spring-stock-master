package com.barcodehub.eccomerce_spring.dto;

import com.barcodehub.eccomerce_spring.models.Role;

public record AuthResponseDTO(
        String token,
        String email,
        Role role
) {}