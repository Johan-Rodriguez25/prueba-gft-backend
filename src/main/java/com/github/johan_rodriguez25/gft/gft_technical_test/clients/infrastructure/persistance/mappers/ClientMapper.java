package com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.persistance.mappers;

import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.Client;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.ActiveSubscription;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.Transaction;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.persistance.ClientEntity;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.persistance.ActiveSubscriptionEntity;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.persistance.TransactionEntity;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientEntity toEntity(Client client) {
        if (client == null) {
            return null;
        }

        List<ActiveSubscriptionEntity> subscriptions = client.activeSubscriptions().stream()
                .map(this::toSubscripcionActivaEntity)
                .toList();

        List<TransactionEntity> transactions = client.transactionHistory().stream()
                .map(this::toTransactionEntity)
                .toList();

        return new ClientEntity(
                client.id(),
                client.fullName(),
                client.email(),
                client.password(),
                client.role(),
                client.phoneNumber(),
                client.balance(),
                client.notificationPreference().name(),
                subscriptions,
                transactions
        );
    }

    public Client toDomain(ClientEntity entity) {
        if (entity == null) {
            return null;
        }

        List<ActiveSubscription> subscriptions = entity.getActiveSubscriptions().stream()
                .map(this::toSubscripcionActivaDomain)
                .toList();

        List<Transaction> transactions = entity.getTransactionHistory().stream()
                .map(this::toTransactionDomain)
                .toList();

        return new Client(
                entity.getId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRole(),
                entity.getPhoneNumber(),
                entity.getBalance(),
                Client.NotificationPreference.valueOf(entity.getNotificationPreference()),
                subscriptions,
                transactions
        );
    }

    private ActiveSubscriptionEntity toSubscripcionActivaEntity(ActiveSubscription domain) {
        return new ActiveSubscriptionEntity(
                domain.fundId(),
                domain.amountTied()
        );
    }

    private TransactionEntity toTransactionEntity(Transaction domain) {
        return new TransactionEntity(
                domain.id(),
                domain.transactionType().name(),
                domain.fundId(),
                domain.amount(),
                domain.date());
    }

    private ActiveSubscription toSubscripcionActivaDomain(ActiveSubscriptionEntity entity) {
        return new ActiveSubscription(
                entity.getFundId(),
                entity.getAmountTied()
        );
    }

    private Transaction toTransactionDomain(TransactionEntity entity) {
        return new Transaction(
                entity.getId(),
                Transaction.TransactionType.valueOf(entity.getTransactionType()),
                entity.getFundId(),
                entity.getAmount(),
                entity.getDate());
    }
}