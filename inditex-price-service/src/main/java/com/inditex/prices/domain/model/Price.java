package com.inditex.prices.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Price {

    private Long brandId;
    private Long productId;
    private Long priceList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer priority;
    private BigDecimal price;
    private String currency;
}
