package com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.services;

import com.github.johan_rodriguez25.gft.gft_technical_test.clients.application.ports.in.CancelSubscriptionUseCase;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.application.ports.in.SubscribeToFundUseCase;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.application.ports.out.ClientRepositoryPort;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.application.ports.out.NotificationPort;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.Client;
import com.github.johan_rodriguez25.gft.gft_technical_test.funds.application.ports.out.FundRepositoryPort;
import com.github.johan_rodriguez25.gft.gft_technical_test.funds.domain.models.Fund;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class SubscriptionService implements SubscribeToFundUseCase, CancelSubscriptionUseCase {
    private final ClientRepositoryPort clientRepositoryPort;
    private final FundRepositoryPort fundRepositoryPort;
    private final NotificationPort notificationPort;

    public SubscriptionService(ClientRepositoryPort clientRepositoryPort, FundRepositoryPort fundRepositoryPort, NotificationPort notificationPort) {
        this.clientRepositoryPort = clientRepositoryPort;
        this.fundRepositoryPort = fundRepositoryPort;
        this.notificationPort = notificationPort;
    }

    @Override
    @Transactional
    public Client subscribeToFund(String clientId, String fundId, BigDecimal amount) {
        Client client = clientRepositoryPort.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + clientId));
        Fund fund = fundRepositoryPort.findById(fundId)
                .orElseThrow(() -> new IllegalArgumentException("Fondo no encontrado con ID: " + fundId));

        Client updatedClient = client.subscribeToFund(fund, amount);

        Client savedClient = clientRepositoryPort.save(updatedClient);

        notificationPort.sendSubscriptionNotification(savedClient, fund);

        return savedClient;
    }

    @Override
    @Transactional
    public Client cancelSubscription(String clientId, String fundId) {
        Client client = clientRepositoryPort.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con ID: " + clientId));

        Client updatedClient = client.cancelSubscription(fundId);

        return clientRepositoryPort.save(updatedClient);
    }
}
