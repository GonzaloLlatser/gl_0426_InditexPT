package com.inditex.prices.infrastructure.adapter.in.rest;

import com.inditex.prices.application.port.in.GetApplicablePriceUseCasePort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/prices")
@RequiredArgsConstructor
public class PriceController {

    private final GetApplicablePriceUseCasePort getApplicablePriceUseCasePort;
}
