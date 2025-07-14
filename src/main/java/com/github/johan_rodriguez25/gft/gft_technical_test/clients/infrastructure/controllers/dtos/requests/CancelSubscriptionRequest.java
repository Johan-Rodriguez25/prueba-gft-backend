package com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record CancelSubscriptionRequest(
        @NotBlank(message = "El ID del cliente no puede estar vacío.")
        String clientId,

        @NotBlank(message = "El ID del fondo no puede estar vacío.")
        String fundId
) {
}
