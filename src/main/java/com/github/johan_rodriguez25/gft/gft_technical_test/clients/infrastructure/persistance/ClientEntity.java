package com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.persistance;

import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "clients")
public class ClientEntity {
    @Id
    private String id;
    private String fullName;
    private String email;
    private String password;
    private Role role;
    private String phoneNumber;
    private BigDecimal balance;
    private String notificationPreference;
    private List<ActiveSubscriptionEntity> activeSubscriptions;
    private List<TransactionEntity> transactionHistory;
}