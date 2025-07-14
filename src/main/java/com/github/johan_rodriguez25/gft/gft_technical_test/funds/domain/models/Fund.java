package com.github.johan_rodriguez25.gft.gft_technical_test.funds.domain.models;

import java.math.BigDecimal;

public record Fund(
        String id,
        String name,
        BigDecimal minimumAmount,
        String category
) {}