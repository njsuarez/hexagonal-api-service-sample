package com.github.njsuarez.prices.application.controller;

import com.github.njsuarez.prices.application.api.PricesApi;
import com.github.njsuarez.prices.application.api.model.PriceDto;
import com.github.njsuarez.prices.domain.exceptions.NotFoundException;
import com.github.njsuarez.prices.domain.model.Price;
import com.github.njsuarez.prices.domain.service.PriceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.github.njsuarez.prices.application.api.model.PriceDtoAssembler.*;

@Slf4j
@AllArgsConstructor
@RestController
public class PriceController implements PricesApi {

    private PriceService priceService;

    @Override
    public ResponseEntity<PriceDto> _getPrices(Integer productId, Integer brandId, LocalDateTime date) {
        log.info("GET Prices brandId: {} productId: {} date: {}", brandId, productId, date);
        Price price = priceService.getPrice(brandId, productId, date).orElseThrow(() -> new NotFoundException("Not found price for these criteria"));
        return ResponseEntity.ok(assemble(price));
    }

}
