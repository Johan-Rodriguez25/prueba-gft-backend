package com.github.johan_rodriguez25.gft.gft_technical_test.funds.infrastructure.controllers.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record FundRequest(
        @NotBlank(message = "El nombre del fondo no puede estar vacío.")
        String name,

        @NotBlank(message = "El valor minimo del fondo no puede estar vacío")
        @Positive
        BigDecimal minimunAmount,

        @NotBlank(message = "La categorya del fondo no puede estar vacía.")
        String category
) {}
