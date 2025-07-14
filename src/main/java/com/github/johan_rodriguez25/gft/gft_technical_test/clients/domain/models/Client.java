package com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models;

import com.github.johan_rodriguez25.gft.gft_technical_test.funds.domain.models.Fund;
import com.github.johan_rodriguez25.gft.gft_technical_test.shared.domain.exceptions.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record Client(
        String id,
        String fullName,
        String email,
        String password,
        Role role,
        String phoneNumber,
        BigDecimal balance,
        NotificationPreference notificationPreference,
        List<ActiveSubscription> activeSubscriptions,
        List<Transaction> transactionHistory
) {
    public enum NotificationPreference {
        EMAIL,
        SMS
    }

    public Client subscribeToFund(Fund fund, BigDecimal amount) {
        if (this.balance.compareTo(fund.minimumAmount()) < 0) {
            throw new ResourceNotFoundException("No tiene saldo disponible para vincularse al fondo " + fund.name());
        }

        BigDecimal newBalance = this.balance.subtract(amount);

        List<ActiveSubscription> newActiveSubscriptions = new ArrayList<>(this.activeSubscriptions);
        List<Transaction> newTransactionHistory = new ArrayList<>(this.transactionHistory);

        newActiveSubscriptions.add(new ActiveSubscription(fund.id(), amount));
        newTransactionHistory.add(new Transaction(
                UUID.randomUUID().toString(),
                Transaction.TransactionType.SUBSCRIBE,
                fund.id(),
                amount,
                LocalDateTime.now()
        ));

        return new Client(
                this.id,
                this.fullName,
                this.email,
                this.password,
                this.role,
                this.phoneNumber,
                newBalance,
                this.notificationPreference,
                newActiveSubscriptions,
                newTransactionHistory
        );
    }

    public Client cancelSubscription(String fundId) {
        ActiveSubscription subscriptionToCancel = this.activeSubscriptions().stream()
                .filter(sub -> sub.fundId().equals(fundId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró una suscripción activa para el fondo con ID: " + fundId
                ));

        BigDecimal amountToReturn = subscriptionToCancel.amountTied();
        BigDecimal newBalance = this.balance.add(amountToReturn);

        List<ActiveSubscription> newActiveSubscriptions = this.activeSubscriptions().stream()
                .filter(sub -> !sub.fundId().equals(fundId))
                .toList();

        List<Transaction> newTransactionHistory = new ArrayList<>(this.transactionHistory);
        newTransactionHistory.add(new Transaction(
                UUID.randomUUID().toString(),
                Transaction.TransactionType.CANCELLATION,
                subscriptionToCancel.fundId(),
                amountToReturn,
                LocalDateTime.now()
        ));

        return new Client(
                this.id,
                this.fullName,
                this.email,
                this.password,
                this.role,
                this.phoneNumber,
                newBalance,
                this.notificationPreference,
                newActiveSubscriptions,
                newTransactionHistory
        );
    }
}