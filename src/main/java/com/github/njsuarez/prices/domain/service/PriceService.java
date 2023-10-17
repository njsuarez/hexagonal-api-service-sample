package com.github.njsuarez.prices.domain.service;

import com.github.njsuarez.prices.application.port.input.FindPricePort;
import com.github.njsuarez.prices.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceService extends FindPricePort {

    Optional<Price> getPrice(Integer brandId, Integer productId, LocalDateTime date);

}
