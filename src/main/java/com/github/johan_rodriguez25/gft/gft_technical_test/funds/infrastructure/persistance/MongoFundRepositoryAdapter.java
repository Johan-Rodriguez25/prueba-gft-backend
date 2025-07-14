package com.github.johan_rodriguez25.gft.gft_technical_test.funds.infrastructure.persistance;

import com.github.johan_rodriguez25.gft.gft_technical_test.funds.application.ports.out.FundRepositoryPort;
import com.github.johan_rodriguez25.gft.gft_technical_test.funds.domain.models.Fund;
import com.github.johan_rodriguez25.gft.gft_technical_test.funds.infrastructure.persistance.mappers.FundMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MongoFundRepositoryAdapter implements FundRepositoryPort {
    private final SpringDataFundRepository springDataFundRepository;
    private final FundMapper fundMapper;

    public MongoFundRepositoryAdapter(
            SpringDataFundRepository springDataFundRepository,
            FundMapper fundMapper
    ) {
        this.springDataFundRepository = springDataFundRepository;
        this.fundMapper = fundMapper;
    }

    @Override
    public Fund save(Fund fund) {
        FundEntity fundEntity = fundMapper.toEntity(fund);

        final FundEntity savedFundEntity = springDataFundRepository.save(fundEntity);

        return fundMapper.toDomain(savedFundEntity);
    }

    @Override
    public Optional<Fund> findById(String id) {
        final FundEntity savedFundEntity = springDataFundRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Fund with id " + id + " not found")
        );

        return Optional.of(fundMapper.toDomain(savedFundEntity));
    }

    @Override
    public List<Fund> findAll() {
        List<FundEntity> fundEntities = springDataFundRepository.findAll();

        return fundEntities.stream()
                .map(fundMapper::toDomain)
                .toList();
    }
}
