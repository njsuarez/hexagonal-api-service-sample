package com.github.njsuarez.prices.application.api.model;

import com.github.njsuarez.prices.domain.model.Price;

public class PriceDtoAssembler {

    public static PriceDto assemble(Price price) {
        return new PriceDto()
                .brandId(price.getBrandId())
                .productId(price.getProductId())
                .priceList(price.getPriceList())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .amount(price.getAmount().doubleValue());
    }

}
