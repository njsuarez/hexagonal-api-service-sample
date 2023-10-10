package com.github.njsuarez.prices.application.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class
PriceControllerIT {

    @LocalServerPort
    private Integer port;

    @Test()
    @DisplayName("Test 1: peticion a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    void shouldReturnPriceCase1() throws Exception {

        given().queryParam("brandId","1")
                .queryParam("productId","35455")
                .queryParam("date","2020-06-14T10:00:00Z")
                .when().accept(ContentType.JSON)
                .get("http://localhost:" + port + "/prices")
                .then()
                .assertThat().statusCode(200)
                .body("brandId", equalTo(1))
                .body("productId", equalTo(35455))
                .body("priceList", equalTo(1))
                .body("startDate", equalTo("2020-06-14T00:00:00"))
                .body("endDate", equalTo("2020-12-31T23:59:59"))
                .body("amount", equalTo(35.50f));
    }

    @Test()
    @DisplayName("Test 2: peticion a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    void shouldReturnPriceCase2() throws Exception {

        given().queryParam("brandId","1")
                .queryParam("productId","35455")
                .queryParam("date","2020-06-14T16:00:00Z")
                .when().get("http://localhost:" + port + "/prices")
                .then()
                .assertThat().statusCode(200)
                .body("brandId", equalTo(1))
                .body("productId", equalTo(35455))
                .body("priceList", equalTo(2))
                .body("startDate", equalTo("2020-06-14T15:00:00"))
                .body("endDate", equalTo("2020-06-14T18:30:00"))
                .body("amount", equalTo(25.45f));

    }

    @Test()
    @DisplayName("Test 3: peticion a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    void shouldReturnPriceCase3() throws Exception {

        given().queryParam("brandId","1")
                .queryParam("productId","35455")
                .queryParam("date","2020-06-14T21:00:00Z")
                .when().get("http://localhost:" + port + "/prices")
                .then()
                .assertThat().statusCode(200)
                .body("brandId", equalTo(1))
                .body("productId", equalTo(35455))
                .body("priceList", equalTo(1))
                .body("startDate", equalTo("2020-06-14T00:00:00"))
                .body("endDate", equalTo("2020-12-31T23:59:59"))
                .body("amount", equalTo(35.5f));

    }

    @Test()
    @DisplayName("Test 4: peticion a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA)")
    void shouldReturnPriceCase4() throws Exception {

        given().queryParam("brandId","1")
                .queryParam("productId","35455")
                .queryParam("date","2020-06-15T10:00:00Z")
                .when().get("http://localhost:" + port + "/prices")
                .then()
                .assertThat().statusCode(200)
                .body("brandId", equalTo(1))
                .body("productId", equalTo(35455))
                .body("priceList", equalTo(3))
                .body("startDate", equalTo("2020-06-15T00:00:00"))
                .body("endDate", equalTo("2020-06-15T11:00:00"))
                .body("amount", equalTo(30.5f));

    }

    @Test()
    @DisplayName("Test 5: peticion a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)")
    void shouldReturnPriceCase5() throws Exception {

        given().queryParam("brandId","1")
                .queryParam("productId","35455")
                .queryParam("date","2020-06-16T21:00:00Z")
                .when().get("http://localhost:" + port + "/prices")
                .then()
                .assertThat().statusCode(200)
                .body("brandId", equalTo(1))
                .body("productId", equalTo(35455))
                .body("priceList", equalTo(4))
                .body("startDate", equalTo("2020-06-15T16:00:00"))
                .body("endDate", equalTo("2020-12-31T23:59:59"))
                .body("amount", equalTo(38.95f));

    }

}
