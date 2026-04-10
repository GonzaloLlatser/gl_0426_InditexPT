package com.inditex.prices.application.service;

import com.inditex.prices.application.port.in.GetApplicablePriceUseCasePort;
import com.inditex.prices.application.port.out.PriceRepositoryPort;
import com.inditex.prices.domain.model.Price;
import com.inditex.prices.domain.model.PriceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GetApplicablePriceUseCase implements GetApplicablePriceUseCasePort {

    private final PriceRepositoryPort priceRepositoryPort;

    @Override
    public Price getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return priceRepositoryPort.findApplicablePrice(applicationDate, productId, brandId)
                .orElseThrow(() -> new PriceNotFoundException("No price was found for the requested criteria"));
    }
}
