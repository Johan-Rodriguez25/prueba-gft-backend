package com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers.dtos.requests;

import com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers.dtos.ActiveSubscriptionsDto;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers.dtos.TransactionDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public record UpdateClientRequest(
        Optional<String> fullName,
        Optional<String> email,
        Optional<String> phoneNumber,
        Optional<BigDecimal> balance,
        Optional<String> notificationPreference,
        Optional<List<ActiveSubscriptionsDto>> activeSubscriptions,
        Optional<List<TransactionDto>> transactionHistory
) {
}