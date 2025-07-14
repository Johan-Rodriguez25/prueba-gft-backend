package com.github.johan_rodriguez25.gft.gft_technical_test.funds.application.services;

import com.github.johan_rodriguez25.gft.gft_technical_test.funds.application.ports.in.CreateFundUseCase;
import com.github.johan_rodriguez25.gft.gft_technical_test.funds.application.ports.in.GetAllFunds;
import com.github.johan_rodriguez25.gft.gft_technical_test.funds.application.ports.in.GetFundByIdUseCase;
import com.github.johan_rodriguez25.gft.gft_technical_test.funds.application.ports.out.FundRepositoryPort;
import com.github.johan_rodriguez25.gft.gft_technical_test.funds.domain.models.Fund;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FundService implements
        CreateFundUseCase,
        GetFundByIdUseCase,
        GetAllFunds
{
    private final FundRepositoryPort fundRepositoryPort;

    public FundService(FundRepositoryPort fundRepositoryPort) {
        this.fundRepositoryPort = fundRepositoryPort;
    }

    @Override
    public Fund createFund(Fund fund) {
        return fundRepositoryPort.save(fund);
    }

    @Override
    public Optional<Fund> findFundById(String id) {
        return fundRepositoryPort.findById(id);
    }

    @Override
    public List<Fund> findAllFunds() {
        return fundRepositoryPort.findAll();
    }
}
