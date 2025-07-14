package com.github.johan_rodriguez25.gft.gft_technical_test.clients.application.ports.out;

import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.Client;
import com.github.johan_rodriguez25.gft.gft_technical_test.funds.domain.models.Fund;

public interface NotificationPort {
    void sendSubscriptionNotification(Client client, Fund fund);
    boolean supports(Client.NotificationPreference preference);
}
