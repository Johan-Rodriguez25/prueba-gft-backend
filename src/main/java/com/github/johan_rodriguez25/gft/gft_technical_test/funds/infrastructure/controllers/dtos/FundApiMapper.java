package com.github.johan_rodriguez25.gft.gft_technical_test.funds.infrastructure.controllers.dtos;

import com.github.johan_rodriguez25.gft.gft_technical_test.funds.domain.models.Fund;
import com.github.johan_rodriguez25.gft.gft_technical_test.funds.infrastructure.controllers.dtos.requests.FundRequest;
import com.github.johan_rodriguez25.gft.gft_technical_test.funds.infrastructure.controllers.dtos.responses.FundResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class FundApiMapper {
    public Fund toDomain(FundRequest dto) {
        return new Fund(
                null,
                dto.name(),
                dto.minimunAmount(),
                dto.category()
        );
    }

    public FundResponse toResponseDto(Fund fund) {
        return new FundResponse(
                fund.id(),
                fund.name(),
                fund.minimumAmount(),
                fund.category()
        );
    }

    public Iterable<FundResponse> toResponseDtos(Iterable<Fund> funds) {
        return StreamSupport.stream(funds.spliterator(), false)
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
