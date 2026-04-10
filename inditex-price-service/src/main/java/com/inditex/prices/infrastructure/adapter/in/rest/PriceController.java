package com.inditex.prices.infrastructure.adapter.in.rest;

import com.inditex.prices.application.port.in.GetApplicablePriceUseCasePort;
import com.inditex.prices.domain.model.Price;
import com.inditex.prices.infrastructure.adapter.in.rest.dto.PriceResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/prices")
@RequiredArgsConstructor
public class PriceController {

    private final GetApplicablePriceUseCasePort getApplicablePriceUseCasePort;

    @GetMapping
    public PriceResponseDTO getApplicablePrice(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime applicationDate,
            @RequestParam Long productId,
            @RequestParam Long brandId
    ) {
        Price price = getApplicablePriceUseCasePort.getApplicablePrice(applicationDate, productId, brandId);

        return PriceResponseDTO.builder()
                .productId(price.getProductId())
                .brandId(price.getBrandId())
                .priceList(price.getPriceList())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .price(price.getPrice())
                .build();
    }
}
