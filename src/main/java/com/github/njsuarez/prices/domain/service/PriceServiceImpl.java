package com.github.njsuarez.prices.domain.service;


import com.github.njsuarez.prices.application.port.output.GetPricesListPort;
import com.github.njsuarez.prices.domain.exceptions.BadParameterException;
import com.github.njsuarez.prices.domain.exceptions.NotFoundException;
import com.github.njsuarez.prices.domain.model.Price;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class PriceServiceImpl implements PriceService {

    private GetPricesListPort getPricesListPort;

    @Override
    public Optional<Price> getPrice(Integer brandId, Integer productId, LocalDateTime date) {

        if (brandId == null || brandId.compareTo(0) < 0) {
            throw new BadParameterException("BrandId is not valid, must be greater than 0");
        }

        if (productId == null || productId.compareTo(0) < 0) {
            throw new BadParameterException("ProductId is not valid, must be greater than 0");
        }

        if (date == null) {
            throw new BadParameterException("Date must be not null");
        }

        log.info("Get prices for brandId: {}, productId: {}, date: {}", brandId, productId, date);
        return Optional.of(getPricesListPort.getPriceList(brandId, productId, date).stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Not found price for these cirteria")));
    }

}
