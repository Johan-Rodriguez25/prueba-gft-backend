package com.github.johan_rodriguez25.gft.gft_technical_test.funds.infrastructure.persistance;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "funds")
public class FundEntity {
    @Id
    private String id;
    private String name;
    private BigDecimal minimumAmount;
    private String category;
}