package com.github.njsuarez.prices.domain.service;

import com.github.njsuarez.prices.domain.exceptions.BadParameterException;
import com.github.njsuarez.prices.domain.exceptions.NotFoundException;
import com.github.njsuarez.prices.domain.model.Price;
import com.github.njsuarez.prices.domain.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PriceServiceTests {

    private PriceService priceService;

    @Mock
    private PriceRepository priceRepository;

    @BeforeEach
    void init() {
        priceService = new PriceServiceImpl(priceRepository);
    }

    @Test
    public void testGetPrice() throws Exception {

        LocalDateTime startDate = LocalDateTime.of(2020, 10,12, 00,00,00);
        LocalDateTime endDate = LocalDateTime.of(2020, 10,12, 23,59,59);
        Price samplePrice = Price.builder()
                .brandId(1)
                .productId(35455)
                .priceList(1)
                .startDate(startDate)
                .endDate(endDate)
                .amount(new BigDecimal("25.56"))
                .priority(0)
                .build();

        when(priceRepository.getPrice(eq(samplePrice.getBrandId()), eq(samplePrice.getProductId()), any(LocalDateTime.class)))
                .thenReturn(List.of(samplePrice));

        LocalDateTime date = LocalDateTime.of(2020, 10,12, 12,00,00);
        Price price = priceService.getPrice(1, 35455, date).orElse(Price.builder().build());

        assertEquals(1 , price.getBrandId());
        assertEquals(35455 , price.getProductId());
        assertEquals(1 , price.getPriceList());
        assertEquals(0 , price.getPriority());
        assertEquals(new BigDecimal("25.56") , price.getAmount());

    }

    @Test
    public void testGetPriceWithPriority() throws Exception {

        LocalDateTime startDate = LocalDateTime.of(2020, 10,12, 00,00,00);
        LocalDateTime endDate = LocalDateTime.of(2020, 10,12, 23,59,59);

        Price primaryPrice = Price.builder()
                .brandId(2)
                .productId(35455)
                .priceList(1)
                .startDate(startDate)
                .endDate(endDate)
                .amount(new BigDecimal("25.56"))
                .priority(1)
                .build();

        Price secondaryPrice = Price.builder()
                .brandId(2)
                .productId(35455)
                .priceList(1)
                .startDate(startDate)
                .endDate(endDate)
                .amount(new BigDecimal("12.56"))
                .priority(0)
                .build();

        when(priceRepository.getPrice(eq(2), eq(35455), any(LocalDateTime.class)))
                .thenReturn(List.of(primaryPrice, secondaryPrice));

        LocalDateTime date = LocalDateTime.of(2020, 10,12, 12,00,00);
        Price price = priceService.getPrice(2, 35455, date).orElse(Price.builder().build());

        assertEquals(2 , price.getBrandId());
        assertEquals(35455 , price.getProductId());
        assertEquals(1 , price.getPriceList());
        assertEquals(1 , price.getPriority());
        assertEquals(new BigDecimal("25.56") , price.getAmount());

    }
    @Test
    public void testGetPriceNotFound() throws Exception {
        when(priceRepository.getPrice(any(Integer.class), any(Integer.class), any(LocalDateTime.class))).thenReturn(Collections.emptyList());
        LocalDateTime date = LocalDateTime.of(2020, 10,12, 12,00,00);
        Exception exception = assertThrows(NotFoundException.class, () -> {
            priceService.getPrice(1, 35455, date);
        });
    }

    @Test
    public void testGetPriceInvalidBrand() throws Exception {
        LocalDateTime date = LocalDateTime.of(2020, 10,12, 12,00,00);
        Exception exception = assertThrows(BadParameterException.class, () -> {
            priceService.getPrice(-1, 35455, date);
        });
    }

    @Test
    public void testGetPriceNullBrand() throws Exception {
        LocalDateTime date = LocalDateTime.of(2020, 10,12, 12,00,00);
        Exception exception = assertThrows(BadParameterException.class, () -> {
            priceService.getPrice(null, 35455, date);
        });
    }

    @Test
    public void testGetPriceInvalidProduct() throws Exception {
        LocalDateTime date = LocalDateTime.of(2020, 10,12, 12,00,00);
        Exception exception = assertThrows(BadParameterException.class, () -> {
            priceService.getPrice(1, -35455, date);
        });
    }

    @Test
    public void testGetPriceNullProduct() throws Exception {
        LocalDateTime date = LocalDateTime.of(2020, 10,12, 12,00,00);
        Exception exception = assertThrows(BadParameterException.class, () -> {
            priceService.getPrice(1, null, date);
        });
    }

    @Test
    public void testGetPriceInvalidDate() throws Exception {
        Exception exception = assertThrows(BadParameterException.class, () -> {
            priceService.getPrice(1, 35455, null);
        });
    }
}
