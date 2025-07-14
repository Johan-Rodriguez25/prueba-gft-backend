package com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers.dtos.responses;

import com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers.dtos.ActiveSubscriptionsDto;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.controllers.dtos.TransactionDto;

import java.math.BigDecimal;
import java.util.List;

public record ClientResponse(
        String id,
        String fullName,
        String email,
        String phoneNumber,
        BigDecimal balance,
        String notificationPreference,
        List<ActiveSubscriptionsDto> activeSubscriptions,
        List<TransactionDto> transactionHistory
) {}