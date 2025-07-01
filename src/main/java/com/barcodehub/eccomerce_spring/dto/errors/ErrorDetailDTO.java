package com.barcodehub.eccomerce_spring.dto.errors;

public record ErrorDetailDTO(
        String code,
        String description,
        String field
) {}