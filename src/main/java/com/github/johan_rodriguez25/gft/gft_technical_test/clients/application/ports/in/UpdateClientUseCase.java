package com.github.johan_rodriguez25.gft.gft_technical_test.clients.application.ports.in;

import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.Client;
import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.UpdateClient;

import java.util.Optional;

public interface UpdateClientUseCase {
    Optional<Client> updateClient(UpdateClient updateClient, String id);
}
