package com.github.johan_rodriguez25.gft.gft_technical_test.clients.application.ports.out;

import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.Client;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.UpdateClient;

import java.util.Optional;

public interface ClientRepositoryPort {
    Client save(Client client);
    Optional<Client> findById(String id);
    Optional<Client> update(UpdateClient updateClient, String id);
    Optional<Client> findByEmail(String email);
}
