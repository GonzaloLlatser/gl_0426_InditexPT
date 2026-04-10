package com.inditex.prices.application.service;

import com.inditex.prices.application.port.out.PriceRepositoryPort;
import com.inditex.prices.domain.model.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetApplicablePriceUseCaseTest {

    @Mock
    private PriceRepositoryPort priceRepositoryPort;

    @InjectMocks
    private GetApplicablePriceUseCase getApplicablePriceUseCase;

    @Test
    void shouldReturnApplicablePriceWhenRepositoryFindsOne() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price expectedPrice = Price.builder()
                .brandId(brandId)
                .productId(productId)
                .priceList(2L)
                .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency("EUR")
                .build();

        when(priceRepositoryPort.findApplicablePrice(applicationDate, productId, brandId))
                .thenReturn(Optional.of(expectedPrice));

        Price actualPrice = getApplicablePriceUseCase.getApplicablePrice(applicationDate, productId, brandId);

        assertThat(actualPrice).isEqualTo(expectedPrice);
        verify(priceRepositoryPort).findApplicablePrice(applicationDate, productId, brandId);
    }

    @Test
    void shouldThrowNotFoundWhenRepositoryDoesNotFindAnyPrice() {
        LocalDateTime applicationDate = LocalDateTime.of(2021, 6, 14, 16, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        when(priceRepositoryPort.findApplicablePrice(applicationDate, productId, brandId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> getApplicablePriceUseCase.getApplicablePrice(applicationDate, productId, brandId))
                .isInstanceOf(ResponseStatusException.class)
                .extracting(
                        throwable -> ((ResponseStatusException) throwable).getStatusCode(),
                        Throwable::getMessage
                )
                .containsExactly(HttpStatus.NOT_FOUND, "404 NOT_FOUND \"Price not found\"");

        verify(priceRepositoryPort).findApplicablePrice(applicationDate, productId, brandId);
    }
}
