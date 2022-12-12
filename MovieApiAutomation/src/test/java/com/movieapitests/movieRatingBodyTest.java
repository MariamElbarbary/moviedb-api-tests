package com.movieapitests;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Locale;

import static org.hamcrest.Matchers.equalTo;

public class movieRatingBodyTest extends baseTest{

    @BeforeClass
    public void setup() {
        RestAssured.basePath = "/movie/{movie_id}/rating";
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
                .expectBody("success", equalTo(false))
                .expectBody("status_code", equalTo(18))
                .build();
    }
    private void postMovieRatingInvalidBody(double bodyValue , String statusMessage) {
        RestAssured
                .given()
                    .pathParam("movie_id", "900667")
                    .spec(getDefaultRequestSpecPost())
                    .body(String.format(Locale.ENGLISH,"{\"value\": %f}", bodyValue))
                .when()
                    .post()
                .then()
                    .body("status_message", equalTo(statusMessage));
    }

    @Test
    public void invalidBodyGreaterThan10() {
        this.postMovieRatingInvalidBody(10.1,"Value too high: Value must be less than, or equal to 10.0.");
    }

   @Test
    public void invalidBodyLessThan1() {
        this.postMovieRatingInvalidBody(0.4,"Value invalid: Values must be a multiple of 0.50.");
    }
    @Test
    public void invalidBodyZero() {
        this.postMovieRatingInvalidBody(0,"Value too low: Value must be greater than 0.0.");
    }

}
