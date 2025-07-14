package com.github.johan_rodriguez25.gft.gft_technical_test.funds.infrastructure.persistance.mappers;

import com.github.johan_rodriguez25.gft.gft_technical_test.funds.domain.models.Fund;
import com.github.johan_rodriguez25.gft.gft_technical_test.funds.infrastructure.persistance.FundEntity;
import org.springframework.stereotype.Component;

@Component
public class FundMapper {
    public FundEntity toEntity(Fund fund) {
        if (fund == null) {
            return null;
        }

        return new FundEntity(
                fund.id(),
                fund.name(),
                fund.minimumAmount(),
                fund.category()
        );
    }

    public Fund toDomain(FundEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Fund(
                entity.getId(),
                entity.getName(),
                entity.getMinimumAmount(),
                entity.getCategory()
        );
    }
}
