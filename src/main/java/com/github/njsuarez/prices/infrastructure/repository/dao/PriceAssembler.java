package com.github.njsuarez.prices.infrastructure.repository.dao;

import com.github.njsuarez.prices.domain.model.Price;

public class PriceAssembler {

    public static Price assemble(PriceDao priceDao) {
        return Price.builder()
                .brandId(priceDao.getBrandId())
                .productId(priceDao.getProductId())
                .priceList(priceDao.getPriceList())
                .amount(priceDao.getPrice())
                .priority(priceDao.getPriority())
                .startDate(priceDao.getStartDate())
                .endDate(priceDao.getEndDate())
                .build();
    }
}
