package com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDto(
        String id,
        String transactionType,
        String fundId,
        BigDecimal amount,
        LocalDateTime date
) {}