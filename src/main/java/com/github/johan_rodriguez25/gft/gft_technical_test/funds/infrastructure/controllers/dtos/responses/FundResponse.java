package com.github.johan_rodriguez25.gft.gft_technical_test.funds.infrastructure.controllers.dtos.responses;

import java.math.BigDecimal;

public record FundResponse(
        String id,
        String name,
        BigDecimal minimunAmount,
        String category
) {
}
