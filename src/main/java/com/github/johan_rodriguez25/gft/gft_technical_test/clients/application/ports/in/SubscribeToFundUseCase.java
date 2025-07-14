package com.github.johan_rodriguez25.gft.gft_technical_test.clients.application.ports.in;

import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.Client;

import java.math.BigDecimal;

public interface SubscribeToFundUseCase {
    Client subscribeToFund(String clientId, String fundId, BigDecimal amount);
}
