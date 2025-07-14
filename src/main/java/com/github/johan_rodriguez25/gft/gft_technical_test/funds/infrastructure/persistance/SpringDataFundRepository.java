package com.github.johan_rodriguez25.gft.gft_technical_test.funds.infrastructure.persistance;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataFundRepository extends MongoRepository<FundEntity, String> {
}
