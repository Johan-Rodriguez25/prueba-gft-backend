package com.github.johan_rodriguez25.gft.gft_technical_test.funds.application.ports.out;

import com.github.johan_rodriguez25.gft.gft_technical_test.funds.domain.models.Fund;

import java.util.List;
import java.util.Optional;

public interface FundRepositoryPort {
    Fund save(Fund fund);
    Optional<Fund> findById(String id);
    List<Fund> findAll();
}
