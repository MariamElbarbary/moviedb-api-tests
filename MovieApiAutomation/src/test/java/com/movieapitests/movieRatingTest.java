package com.movieapitests;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class movieRatingTest extends baseTest {

    @BeforeClass
    public void setup() {
        RestAssured.basePath = "/movie/{movie_id}/rating";
    }

    @Test
    public void postMovieRating() {
        RestAssured
                .given()
                    .pathParam("movie_id", "900667")
                    .spec(getDefaultRequestSpecPost())
                    .body("{\"value\": 10}")
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                .and()
                    .body("success", equalTo(true))
                    .body("status_code", equalTo(1))
                    .body("status_message", equalTo("Success."));
    }

    @Test
    public void postMovieRatingInvalidMovieId() {
        RestAssured
                .given()
                .pathParam("movie_id", "hkg")
                .spec(getDefaultRequestSpecPost())
                .body("{\"value\": 8.5}")
                .when()
                    .post()
                .then()
                    .spec(responseBuilder(HttpStatus.SC_NOT_FOUND,
                        34, "The resource you requested could not be found."));
    }

    @Test
    public void postMovieRatingInvalidBody() {
        RestAssured
                .given()
                    .pathParam("movie_id", "900667")
                    .spec(getDefaultRequestSpecPost())
                    .body("{\"value\": jkj}")
                .when()
                    .post()
                .then()
                    .spec(responseBuilder(HttpStatus.SC_BAD_REQUEST,
                        47, "The input is not valid."));
    }

    @Test
    public void postMovieRatingInvalidApiKey() {
        RestAssured
                .given()
                .pathParam("movie_id", "100")
                .spec(getDefaultRequestSpecPost())
                .queryParam("api_key", "1001")
                .body("{\"value\": 8.5}")
                .when()
                    .post()
                .then()
                .spec(responseBuilder(HttpStatus.SC_UNAUTHORIZED,
                        7,"Invalid API key: You must be granted a valid key."));
    }

    @Test
    public void postMovieRatingInvalidGuestSessionId() {
        RestAssured
                .given()
                    .pathParam("movie_id", "100")
                    .spec(getDefaultRequestSpecPost())
                    .queryParam("guest_session_id", "1001")
                    .body("{\"value\": 8.5}")
                .when()
                    .post()
                .then()
                .spec(responseBuilder(HttpStatus.SC_UNAUTHORIZED,
                        3,"Authentication failed: You do not have permissions to access the service." ));
    }
    @Test
    public void postMovieRatingInvalidSessionId() {
        RestAssured
                .given()
                    .pathParam("movie_id", "100")
                    .spec(getDefaultRequestSpecPost())
                    .queryParam("guest_session_id", "")
                    .queryParam("session_id", "1001")
                    .body("{\"value\": 8.5}")
                .when()
                    .post()
                .then()
                    .spec(responseBuilder(HttpStatus.SC_UNAUTHORIZED,
                        3,"Authentication failed: You do not have permissions to access the service." ));
    }

    @Test
    public void postMovieRatingEmptySessionIdAndGuestId() {
        RestAssured
                .given()
                    .pathParam("movie_id", "100")
                    .spec(getDefaultRequestSpecPost())
                    .queryParam("guest_session_id", "")
                    .body("{\"value\": 8.5}")
                .when()
                    .post()
                .then()
                    .spec(responseBuilder(HttpStatus.SC_UNAUTHORIZED,
                        3,"Authentication failed: You do not have permissions to access the service." ));
    }
}
