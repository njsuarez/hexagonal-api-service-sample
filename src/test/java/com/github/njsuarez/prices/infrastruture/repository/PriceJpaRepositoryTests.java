package com.github.njsuarez.prices.infrastruture.repository;

import com.github.njsuarez.prices.domain.model.Price;
import com.github.njsuarez.prices.domain.repository.PriceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PriceJpaRepositoryTests {

    @Autowired
    private PriceRepository priceRepository;

    @Test
    void getPriceByBrandProductAndDate() throws Exception {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10,0, 0);
        List<Price> prices = priceRepository.getPrice(1,35455, date);

        assertEquals(1, prices.size());

        Price price = prices.stream().findFirst().orElse(Price.builder().build());

        assertEquals(1, price.getBrandId());
        assertEquals(35455, price.getProductId());
        assertEquals(0, price.getPriority());
        assertEquals(1, price.getPriceList());
        assertEquals(35.50, price.getAmount().doubleValue());
    }

    @Test
    void getPriceByBrandProductAndDateNotFound() throws Exception {
        LocalDateTime date = LocalDateTime.of(2021, 1, 1, 12,0, 0);
        List<Price> prices = priceRepository.getPrice(1,35455, date);

        assertEquals(0, prices.size());
    }

    @Test
    void getPriceByBrandProductAndDateNullDateValue() throws Exception {
        List<Price> prices = priceRepository.getPrice(1,35455, null);
        assertEquals(0, prices.size());
    }

    @Test
    void getPriceByBrandProductAndDateNullBrandValue() throws Exception {
        LocalDateTime date = LocalDateTime.of(2021, 1, 1, 12,0, 0);
        List<Price> prices = priceRepository.getPrice(null,35455, date);

        assertEquals(0, prices.size());
    }

    @Test
    void getPriceByBrandProductAndDateNullPRoductValue() throws Exception {
        LocalDateTime date = LocalDateTime.of(2021, 1, 1, 12,0, 0);
        List<Price> prices = priceRepository.getPrice(1,null, date);

        assertEquals(0, prices.size());
    }

    @Test
    void getPriceByBrandProductAndDateSelectPriority() throws Exception {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 18,0, 0);
        List<Price> prices = priceRepository.getPrice(1,35455, date);

        assertEquals(2, prices.size());

        Price price = prices.stream().findFirst().orElse(Price.builder().build());

        assertEquals(1, price.getBrandId());
        assertEquals(35455, price.getProductId());
        assertEquals(1, price.getPriority());
        assertEquals(2, price.getPriceList());
        assertEquals(25.45, price.getAmount().doubleValue());
    }


}
