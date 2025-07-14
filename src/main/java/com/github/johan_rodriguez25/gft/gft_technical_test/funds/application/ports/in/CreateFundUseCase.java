package com.github.johan_rodriguez25.gft.gft_technical_test.funds.application.ports.in;

import com.github.johan_rodriguez25.gft.gft_technical_test.funds.domain.models.Fund;

public interface CreateFundUseCase {
    Fund createFund(Fund fund);
}
