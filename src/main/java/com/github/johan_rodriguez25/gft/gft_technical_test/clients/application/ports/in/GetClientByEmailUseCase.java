package com.github.johan_rodriguez25.gft.gft_technical_test.clients.application.ports.in;

import com.github.johan_rodriguez25.gft.gft_technical_test.clients.domain.models.Client;

import java.util.Optional;

public interface GetClientByEmailUseCase {
    Optional<Client> findClientByEmail(String email);
}
