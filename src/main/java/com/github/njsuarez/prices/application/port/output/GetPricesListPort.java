package com.github.njsuarez.prices.application.port.output;

import com.github.njsuarez.prices.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface GetPricesListPort {

    /**
     * Query for prices with the criteria, order by its priority
     *
     * @param brandId
     * @param productId
     * @param date
     * @return
     */
    List<Price> getPriceList(Integer brandId, Integer productId, LocalDateTime date);

}
