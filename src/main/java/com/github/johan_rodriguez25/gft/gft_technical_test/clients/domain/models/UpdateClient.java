package com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public record UpdateClient(
        Optional<String> fullName,
        Optional<String> email,
        Optional<String> phoneNumber,
        Optional<BigDecimal> balance,
        Optional<NotificationPreference> notificationPreference,
        Optional<List<ActiveSubscription>> activeSubscriptions,
        Optional<List<Transaction>> transactionHistory
) {
    public enum NotificationPreference {
        EMAIL,
        SMS
    }
}