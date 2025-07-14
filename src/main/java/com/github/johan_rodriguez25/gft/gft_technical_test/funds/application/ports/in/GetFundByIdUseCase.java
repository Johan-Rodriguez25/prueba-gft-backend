package com.github.johan_rodriguez25.gft.gft_technical_test.funds.application.ports.in;

import com.github.johan_rodriguez25.gft.gft_technical_test.funds.domain.models.Fund;

import java.util.Optional;

public interface GetFundByIdUseCase {
    Optional<Fund> findFundById(String id);
}
