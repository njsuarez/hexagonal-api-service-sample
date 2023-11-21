package com.github.njsuarez.prices.infrastruture.adapter.rest.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.njsuarez.prices.application.port.input.FindPricePort;
import com.github.njsuarez.prices.domain.model.Currency;
import com.github.njsuarez.prices.domain.model.Price;
import com.github.njsuarez.prices.infrastructure.adapter.rest.controller.PriceController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@WebMvcTest(PriceController.class)
public class PriceControllerTests {

    private Price samplePrice;

    @MockBean
    private FindPricePort findPricePort;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void init() {

        LocalDateTime startDate = LocalDateTime.of(2020, 10,12, 00,00,00);
        LocalDateTime endDate = LocalDateTime.of(2020, 10,12, 23,59,59);
        samplePrice = Price.builder()
                .brandId(1)
                .productId(35455)
                .priceList(1)
                .startDate(startDate)
                .endDate(endDate)
                .amount(new BigDecimal("25.56"))
                .currency(Currency.EUR)
                .build();

        when(findPricePort.getPrice(eq(samplePrice.getBrandId()), eq(samplePrice.getProductId()), any(LocalDateTime.class)))
                .thenReturn(Optional.of(samplePrice));

        when(findPricePort.getPrice(eq(2), eq(samplePrice.getProductId()), any(LocalDateTime.class)))
                .thenReturn(Optional.empty());
    }

    @Test
    void shouldReturnPrice() throws Exception {

        mockMvc.perform(get("/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("date", "2020-10-12T17:00:00Z"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brandId").value(samplePrice.getBrandId()))
                .andExpect(jsonPath("$.productId").value(samplePrice.getProductId()))
                .andExpect(jsonPath("$.priceList").value(samplePrice.getPriceList()))
                .andExpect(jsonPath("$.startDate").value("2020-10-12T00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-10-12T23:59:59"))
                .andExpect(jsonPath("$.amount").value(samplePrice.getAmount()))
                .andExpect(jsonPath("$.currency").value(samplePrice.getCurrency().toString()))
                .andDo(print());

    }

    @Test
    void shouldReturnNotFound() throws Exception {

        mockMvc.perform(get("/prices")
                        .param("brandId", "2")
                        .param("productId", "35455")
                        .param("date", "2020-10-12T17:00:00Z"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("NOT_FOUND"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("Not found price for these criteria"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andDo(print());

    }

    @Test
    void shouldReturnBadParameterBrandIdNull() throws Exception {

        mockMvc.perform(get("/prices")
                        .param("productId", "35455")
                        .param("date", "2020-10-12T17:00:00Z"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.detail").value("Required request parameter 'brandId' for method parameter type Integer is not present"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andDo(print());

    }

    @Test
    void shouldReturnBadParameterProductIdNull() throws Exception {

        mockMvc.perform(get("/prices")
                        .param("brandId", "1")
                        .param("date", "2020-10-12T17:00:00Z"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.detail").value("Required request parameter 'productId' for method parameter type Integer is not present"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andDo(print());

    }

    @Test
    void shouldReturnBadParameterDateNull() throws Exception {

        mockMvc.perform(get("/prices")
                        .param("brandId", "1")
                        .param("productId", "35455"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.detail").value("Required request parameter 'date' for method parameter type LocalDateTime is not present"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andDo(print());

    }

}
