package com.ccpm.evaluacion.dto;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponseDto(
        Long id,
        LocalDateTime created,
        LocalDateTime modified,
        LocalDateTime lastLogin,
        String token,
        Boolean isActive,
        String name,
        String email,
        List<PhoneDto> phones
) {
}

