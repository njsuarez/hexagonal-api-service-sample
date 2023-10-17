package com.github.njsuarez.prices.infrastructure.adapter.rest.assembler;

import com.github.njsuarez.prices.domain.model.Price;
import com.github.njsuarez.prices.infrastructure.adapter.rest.api.model.PriceDto;

public class PriceDtoAssembler {

    public static PriceDto assemble(Price price) {
        return new PriceDto()
                .brandId(price.getBrandId())
                .productId(price.getProductId())
                .priceList(price.getPriceList())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .amount(price.getAmount().doubleValue())
                .currency(String.valueOf(price.getCurrency()));
    }
}
