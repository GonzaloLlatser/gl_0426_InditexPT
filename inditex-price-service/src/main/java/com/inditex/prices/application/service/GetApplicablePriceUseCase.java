package com.inditex.prices.application.service;

import com.inditex.prices.application.port.in.GetApplicablePriceUseCasePort;
import com.inditex.prices.application.port.out.PriceRepositoryPort;
import com.inditex.prices.domain.model.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GetApplicablePriceUseCase implements GetApplicablePriceUseCasePort {

    private final PriceRepositoryPort priceRepositoryPort;

    @Override
    public Price getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return priceRepositoryPort.findApplicablePrice(applicationDate, productId, brandId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Price not found"));
    }
}
