package com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(
        String id,
        TransactionType transactionType,
        String fundId,
        BigDecimal amount,
        LocalDateTime date
) {
    public enum TransactionType {
        SUBSCRIBE,
        CANCELLATION
    }
}