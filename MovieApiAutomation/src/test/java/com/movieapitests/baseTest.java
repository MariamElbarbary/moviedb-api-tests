package com.movieapitests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.listener.ResponseValidationFailureListener;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;

import static com.movieapitests.constants.*;
import static io.restassured.config.FailureConfig.failureConfig;
import static io.restassured.config.LogConfig.logConfig;
import static org.hamcrest.Matchers.equalTo;

public abstract class baseTest {
    static String getGuestSessionID;

    @BeforeClass
    public static void init() {
//        RestAssured.responseSpecification = new ResponseSpecBuilder()
//                .expectContentType(ContentType.JSON)
//                .build();

        getGuestSessionID = authSession.getGuestSessionId();
    }

    protected static RequestSpecification getDefaultRequestSpec() {
        return new RequestSpecBuilder()
                .addQueryParam("api_key", API_KEY)
                .setBaseUri(BASE_URL)
                .build();
    }

    protected static RequestSpecification getDefaultRequestSpecPost() {

        return new RequestSpecBuilder()
                .setContentType("application/json;charset=utf-8")
                .addQueryParam("api_key", API_KEY)
                .addQueryParam("guest_session_id", getGuestSessionID)
                .setBaseUri(BASE_URL)
                .build();
    }

    protected  static ResponseSpecification responseBuilder(int statusCode, int failureCode, String errorMessage) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectBody("success", equalTo(false))
                .expectBody("status_code", equalTo(failureCode))
                .expectBody("status_message", equalTo(errorMessage))
                .build();
    }

    public static RestAssuredConfig getDefaultConfig() {

        ResponseValidationFailureListener failureListener = (reqSpec, resSpec, response) ->
                System.out.printf("We have a failure, " +
                                "response status was %s and the body contained: %s",
                        response.getStatusCode(), response.body().asPrettyString());

        return RestAssured.config()
                .logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL))
                .failureConfig(failureConfig().failureListeners(failureListener));
    }
}
