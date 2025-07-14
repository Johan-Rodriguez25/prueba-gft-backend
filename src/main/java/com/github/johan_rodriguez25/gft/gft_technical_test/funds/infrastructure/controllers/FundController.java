package com.github.johan_rodriguez25.gft.gft_technical_test.funds.infrastructure.controllers;

import com.github.johan_rodriguez25.gft.gft_technical_test.funds.application.ports.in.CreateFundUseCase;
import com.github.johan_rodriguez25.gft.gft_technical_test.funds.application.ports.in.GetFundByIdUseCase;
import com.github.johan_rodriguez25.gft.gft_technical_test.funds.domain.models.Fund;
import com.github.johan_rodriguez25.gft.gft_technical_test.funds.infrastructure.controllers.dtos.FundApiMapper;
import com.github.johan_rodriguez25.gft.gft_technical_test.funds.infrastructure.controllers.dtos.requests.FundRequest;
import com.github.johan_rodriguez25.gft.gft_technical_test.funds.infrastructure.controllers.dtos.responses.FundResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/funds")
public class FundController {
    private final CreateFundUseCase createFundUseCase;
    private final GetFundByIdUseCase getFundByIdUseCase;
    private final FundApiMapper fundApiMapper;

    public FundController(
            CreateFundUseCase createFundUseCase,
            GetFundByIdUseCase getFundByIdUseCase,
            FundApiMapper fundApiMapper
    ) {
        this.createFundUseCase = createFundUseCase;
        this.getFundByIdUseCase = getFundByIdUseCase;
        this.fundApiMapper = fundApiMapper;
    }

    @PostMapping
    public ResponseEntity<FundResponse> createFund(
            @Valid @RequestBody FundRequest fundRequest
    ) {
        Fund fundToCreate = fundApiMapper.toDomain(fundRequest);

        Fund fundCreated = createFundUseCase.createFund(fundToCreate);

        FundResponse responseDto = fundApiMapper.toResponseDto(fundCreated);

        URI location = URI.create("/funds/" + responseDto.id());
        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FundResponse> getFundById(@PathVariable String id) {
        Fund fund = getFundByIdUseCase.findFundById(id).orElseThrow(
                () -> new IllegalArgumentException("Fund with id " + id + " not found")
        );
        FundResponse responseDto = fundApiMapper.toResponseDto(fund);
        return ResponseEntity.ok(responseDto);
    }
}
