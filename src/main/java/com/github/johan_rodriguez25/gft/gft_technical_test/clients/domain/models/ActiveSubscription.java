package com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models;

import java.math.BigDecimal;

public record ActiveSubscription(
        String fundId,
        BigDecimal amountTied
) {}