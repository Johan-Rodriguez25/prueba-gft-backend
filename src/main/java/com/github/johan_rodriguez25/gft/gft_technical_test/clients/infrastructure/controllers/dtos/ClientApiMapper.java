package com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers.dtos;

import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.ActiveSubscription;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.Client;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.Transaction;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.UpdateClient;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers.dtos.requests.ClientRequest;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers.dtos.requests.UpdateClientRequest;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers.dtos.responses.ClientResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;

@Component
public class ClientApiMapper {
    public Client toDomain(ClientRequest dto) {
        return new Client(
                null,
                dto.fullName(),
                dto.email(),
                dto.password(),
                null,
                dto.phoneNumber(),
                new BigDecimal("500000"),
                Client.NotificationPreference.valueOf(dto.notificationPreference()),
                Collections.emptyList(),
                Collections.emptyList()
        );
    }

    public ClientResponse toResponseDto(Client client) {
        var subscriptionsDto = client.activeSubscriptions().stream()
                .map(s -> new ActiveSubscriptionsDto(
                        s.fundId(),
                        s.amountTied()
                ))
                .toList();

        var transactionsDto = client.transactionHistory().stream()
                .map(t -> new TransactionDto(
                        t.id(),
                        t.transactionType().name(),
                        t.fundId(),
                        t.amount(),
                        t.date()))
                .toList();

        return new ClientResponse(
                client.id(),
                client.fullName(),
                client.email(),
                client.phoneNumber(),
                client.balance(),
                client.notificationPreference().name(),
                subscriptionsDto,
                transactionsDto
        );
    }

    public UpdateClient toDomainForUpdate(UpdateClientRequest dto) {
        return new UpdateClient(
                dto.fullName(),
                dto.email(),
                dto.phoneNumber(),
                dto.balance(),
                dto.notificationPreference()
                        .map(UpdateClient.NotificationPreference::valueOf),
                dto.activeSubscriptions().map(subs -> subs.stream()
                        .map(s -> new ActiveSubscription(s.fundId(), s.amountTied()))
                        .toList()),
                dto.transactionHistory().map(txs -> txs.stream()
                        .map(t -> new Transaction(
                                t.id(),
                                Transaction.TransactionType.valueOf(t.transactionType()),
                                t.fundId(),
                                t.amount(),
                                t.date()))
                        .toList())
        );
    }
}