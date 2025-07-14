package com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers.dtos.requests;

import jakarta.validation.constraints.*;

public record ClientRequest(
        @NotBlank(message = "El nombre completo no puede estar vacío.")
        String fullName,

        @NotBlank(message = "El email no puede estar vacío.")
        @Email(message = "El formato del email no es válido.")
        String email,

        @NotBlank(message = "La contraseña no puede estar vacía.")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
        String password,

        @NotBlank(message = "El número de celular no puede estar vacío.")
        String phoneNumber,

        @NotNull(message = "La preferencia de notificación no puede ser nula.")
        @Pattern(regexp = "EMAIL|SMS", message = "La preferencia debe ser EMAIL o SMS.")
        String notificationPreference
) {}