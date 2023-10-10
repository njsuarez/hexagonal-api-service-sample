package com.github.njsuarez.prices.domain.service;

import com.github.njsuarez.prices.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceService {

    /**
     * Return the price that match criteria
     *
     * @param brandId Must be greater then 0
     * @param productId Must be greater then 0
     * @param date
     *
     * @return Price that match the criteria. return empty if not found
     * @throws com.github.njsuarez.prices.domain.exceptions.BadParameterException If parameters are incorrect
     */
    Optional<Price> getPrice(Integer brandId, Integer productId, LocalDateTime date);

}
