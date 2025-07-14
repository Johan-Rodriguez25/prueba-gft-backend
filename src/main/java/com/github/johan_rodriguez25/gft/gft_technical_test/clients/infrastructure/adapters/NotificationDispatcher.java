package com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.adapters;

import com.github.johan_rodriguez25.gft.gft_technical_test.clients.application.ports.out.NotificationPort;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.Client;
import com.github.johan_rodriguez25.gft.gft_technical_test.funds.domain.models.Fund;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Primary
public class NotificationDispatcher implements NotificationPort {
    private final List<NotificationPort> notificationProviders;

    public NotificationDispatcher(List<NotificationPort> notificationProviders) {
        this.notificationProviders = notificationProviders;
    }

    public void sendSubscriptionNotification(Client client, Fund fund) {
        notificationProviders.stream()
                .filter(provider -> !(provider instanceof NotificationDispatcher))
                .filter(provider -> provider.supports(client.notificationPreference()))
                .findFirst()
                .ifPresent(provider -> provider.sendSubscriptionNotification(client, fund));
    }

    public boolean supports(Client.NotificationPreference preference) {
        return false;
    }
}
