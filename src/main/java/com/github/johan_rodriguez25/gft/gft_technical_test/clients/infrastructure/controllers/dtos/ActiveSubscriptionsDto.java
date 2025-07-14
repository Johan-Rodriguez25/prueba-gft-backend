package com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers.dtos;

import java.math.BigDecimal;

public record ActiveSubscriptionsDto(
        String fundId,
        BigDecimal amountTied
) {}