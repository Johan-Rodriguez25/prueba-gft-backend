package com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.persistance;

import java.math.BigDecimal;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveSubscriptionEntity {
    private String fundId;
    private BigDecimal amountTied;
}