package com.inditex.prices.application.service;

import com.inditex.prices.application.port.in.GetApplicablePriceUseCasePort;
import com.inditex.prices.application.port.out.PriceRepositoryPort;
import com.inditex.prices.domain.model.Price;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class GetApplicablePriceUseCase implements GetApplicablePriceUseCasePort {

    private final PriceRepositoryPort priceRepositoryPort;

    @Override
    public Price getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return null;
    }
}
