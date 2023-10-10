package com.github.njsuarez.prices.domain.repository;

import com.github.njsuarez.prices.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository {

    /**
     * Query for prices with the criteria, order by its priority
     *
     * @param brandId
     * @param productId
     * @param date
     * @return
     */
    List<Price> getPrice(Integer brandId, Integer productId, LocalDateTime date);

}
