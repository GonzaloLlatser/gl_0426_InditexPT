package com.inditex.prices.e2e;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PriceApiE2ETest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @ParameterizedTest
    @CsvSource({
            "2020-06-14T10:00:00, 1, 2020-06-14T00:00:00, 2020-12-31T23:59:59, 35.50",
            "2020-06-14T16:00:00, 2, 2020-06-14T15:00:00, 2020-06-14T18:30:00, 25.45",
            "2020-06-14T21:00:00, 1, 2020-06-14T00:00:00, 2020-12-31T23:59:59, 35.50",
            "2020-06-15T10:00:00, 3, 2020-06-15T00:00:00, 2020-06-15T11:00:00, 30.50",
            "2020-06-16T21:00:00, 4, 2020-06-15T16:00:00, 2020-12-31T23:59:59, 38.95"
    })
    void shouldReturnExpectedPriceForChallengeScenario(
            String applicationDate,
            Long expectedPriceList,
            String expectedStartDate,
            String expectedEndDate,
            String expectedPrice
    ) {
        given()
                .queryParam("applicationDate", applicationDate)
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
        .when()
                .get("/api/v1/prices")
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("productId", equalTo(35455))
                .body("brandId", equalTo(1))
                .body("priceList", equalTo(expectedPriceList.intValue()))
                .body("startDate", equalTo(expectedStartDate))
                .body("endDate", equalTo(expectedEndDate))
                .body("price", equalTo(Float.parseFloat(expectedPrice)));
    }

    @Test
    void shouldReturnNotFoundWhenNoApplicablePriceExists() {
        given()
                .queryParam("applicationDate", "2021-06-14T16:00:00")
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
        .when()
                .get("/api/v1/prices")
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void shouldReturnBadRequestWhenApplicationDateIsInvalid() {
        given()
                .queryParam("applicationDate", "invalid-date")
                .queryParam("productId", 35455)
                .queryParam("brandId", 1)
        .when()
                .get("/api/v1/prices")
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
