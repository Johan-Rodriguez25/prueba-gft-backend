package com.github.johan_rodriguez25.gft.gft_technical_test.clients.infrastructure.persistance;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SpringDataClientRepository extends MongoRepository<ClientEntity, String> {
    Optional<ClientEntity> findByEmail(String email);
}
