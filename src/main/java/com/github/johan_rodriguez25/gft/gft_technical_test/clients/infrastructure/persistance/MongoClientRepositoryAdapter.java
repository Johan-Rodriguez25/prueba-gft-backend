package com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.persistance;

import com.github.johan_rodriguez25.gft.gft_technical_test.clients.application.ports.out.ClientRepositoryPort;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.Client;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.UpdateClient;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.persistance.mappers.ClientMapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MongoClientRepositoryAdapter implements ClientRepositoryPort {
    private final SpringDataClientRepository springDataClientRepository;
    private final ClientMapper clientMapper;

    public MongoClientRepositoryAdapter(
            SpringDataClientRepository springDataClientRepository,
            ClientMapper clientMapper
    ) {
        this.springDataClientRepository = springDataClientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public Client save(Client client) {
        ClientEntity clientEntity = clientMapper.toEntity(client);

        final ClientEntity savedCliente = springDataClientRepository.save(clientEntity);

        return clientMapper.toDomain(savedCliente);
    }

    @Override
    public Optional<Client> findById(String id) {
        final ClientEntity foundClient = springDataClientRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Client with id " + id + " not found")
        );

        return Optional.of(clientMapper.toDomain(foundClient));
    }

    @Override
    public Optional<Client> update(UpdateClient updateClient, String id) {
        final ClientEntity foundClient = springDataClientRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Client with id " + id + " not found")
        );

        updateClient.fullName().ifPresent(foundClient::setFullName);
        updateClient.email().ifPresent(foundClient::setEmail);
        updateClient.phoneNumber().ifPresent(foundClient::setPhoneNumber);
        updateClient.balance().ifPresent(foundClient::setBalance);
        updateClient.notificationPreference().ifPresent(
                preference -> foundClient.setNotificationPreference(preference.name())
        );
        updateClient.activeSubscriptions().ifPresent(subscriptions -> {
            List<ActiveSubscriptionEntity> subscriptionEntities = subscriptions.stream()
                    .map(sub -> new ActiveSubscriptionEntity(sub.fundId(), sub.amountTied()))
                    .collect(Collectors.toList());
            foundClient.setActiveSubscriptions(subscriptionEntities);
        });
        updateClient.transactionHistory().ifPresent(transactions -> {
            List<TransactionEntity> transactionEntities = transactions.stream()
                    .map(tx -> new TransactionEntity(
                            tx.id(),
                            tx.transactionType().name(),
                            tx.fundId(),
                            tx.amount(),
                            tx.date()
                    ))
                    .collect(Collectors.toList());
            foundClient.setTransactionHistory(transactionEntities);
        });

        final ClientEntity updatedClient = springDataClientRepository.save(foundClient);

        return Optional.of(clientMapper.toDomain(updatedClient));
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        final ClientEntity foundClient = springDataClientRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("Client with email " + email + " not found")
        );

        return Optional.of(clientMapper.toDomain(foundClient));
    }
}
