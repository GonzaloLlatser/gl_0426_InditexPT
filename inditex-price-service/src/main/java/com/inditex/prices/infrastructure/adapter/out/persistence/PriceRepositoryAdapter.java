package com.inditex.prices.infrastructure.adapter.out.persistence;

import com.inditex.prices.application.port.out.PriceRepositoryPort;
import com.inditex.prices.domain.model.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PriceRepositoryAdapter implements PriceRepositoryPort {

    private final PriceJpaRepository priceJpaRepository;

    @Override
    public Optional<Price> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return priceJpaRepository
                .findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        brandId,
                        productId,
                        applicationDate,
                        applicationDate
                )
                .map(this::toDomain);
    }

    private Price toDomain(PriceEntity priceEntity) {
        return Price.builder()
                .brandId(priceEntity.getBrandId())
                .productId(priceEntity.getProductId())
                .priceList(priceEntity.getPriceList())
                .startDate(priceEntity.getStartDate())
                .endDate(priceEntity.getEndDate())
                .priority(priceEntity.getPriority())
                .price(priceEntity.getPrice())
                .currency(priceEntity.getCurrency())
                .build();
    }
}
