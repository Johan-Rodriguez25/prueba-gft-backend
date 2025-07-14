package com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.persistance;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {
    private String id;
    private String transactionType;
    private String fundId;
    private BigDecimal amount;
    private LocalDateTime date;
}