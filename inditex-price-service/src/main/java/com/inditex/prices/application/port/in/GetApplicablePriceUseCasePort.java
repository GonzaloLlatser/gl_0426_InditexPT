package com.inditex.prices.application.port.in;

import com.inditex.prices.domain.model.Price;

import java.time.LocalDateTime;

public interface GetApplicablePriceUseCasePort {

    Price getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}
