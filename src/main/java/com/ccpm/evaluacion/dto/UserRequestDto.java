package com.ccpm.evaluacion.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record UserRequestDto(
        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotBlank(message = "El correo es obligatorio")
        @Pattern(
                regexp = "^[a-zA-Z0-0._%+-]+@[a-zA-Z0-0.-]+\\.[a-zA-Z]{2,6}$",
                message = "El formato del correo no es válido"
        )
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=(?:.*\\d){2})[a-zA-Z0-9]{8,12}$",
                message = "La contraseña debe tener una mayúscula, dos números y entre 8 y 12 caracteres"
        )
        String password,

        @Valid
        List<PhoneDto> phones
) {
    public UserRequestDto {
        if (phones == null) {
            phones = List.of();
        }
    }
}