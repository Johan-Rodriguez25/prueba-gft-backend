package com.github.johan_rodriguez25.gft.gft_technical_test.funds.application.ports.in;

import com.github.johan_rodriguez25.gft.gft_technical_test.funds.domain.models.Fund;

import java.util.List;

public interface GetAllFunds {
    List<Fund> findAllFunds();
}
