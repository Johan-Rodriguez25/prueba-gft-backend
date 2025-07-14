package com.github.johan_rodriguez25.gft.gft_technical_test.auth.infrastructure.controllers.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "El email debe ser válido")
        String email,

        @NotBlank(message = "La contraseña no puede estar vacía")
        String password
) {
}
