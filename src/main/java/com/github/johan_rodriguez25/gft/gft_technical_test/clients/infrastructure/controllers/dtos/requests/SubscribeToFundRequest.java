package com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record SubscribeToFundRequest(
        @NotBlank(message = "El ID del cliente no puede estar vacío.")
        String clientId,

        @NotBlank(message = "El ID del fondo no puede estar vacío.")
        String fundId,

        @NotNull(message = "El monto no puede estar vacío.")
        @Positive
        BigDecimal amount
) {
}
