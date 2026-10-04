package com.ccpm.evaluacion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record PhoneDto(
        @NotBlank(message = "El número de teléfono es obligatorio")
        String number,

        @NotBlank(message = "El código de ciudad es obligatorio")
        String citycode,

        @NotBlank(message = "El código de país es obligatorio")
        String contrycode
) {}