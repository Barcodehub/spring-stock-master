package com.barcodehub.eccomerce_spring.dto.errors;

import java.util.List;

public record ErrorResponseDTO(
        Object data,
        List<ErrorDetailDTO> errors
) {}

