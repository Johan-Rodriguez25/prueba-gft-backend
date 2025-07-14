package com.github.johan_rodriguez25.gft.gft_technical_test.clients.application.ports.in;

import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.Client;

public interface CancelSubscriptionUseCase {
    Client cancelSubscription(String clientId, String fundId);
}
