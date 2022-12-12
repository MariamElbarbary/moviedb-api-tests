package com.movieapitests;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static com.movieapitests.constants.API_KEY;
import static com.movieapitests.constants.BASE_URL;
import static org.hamcrest.Matchers.notNullValue;

public class authSession {
    static String basePath = "/authentication/guest_session/new";

    public static String getGuestSessionId() {
        return RestAssured
                .given()
                    .queryParam("api_key", API_KEY)
                .when()
                    .get(BASE_URL + basePath)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body("guest_session_id", notNullValue())
                .extract()
                    .response().jsonPath().getString("guest_session_id");
    }

}
