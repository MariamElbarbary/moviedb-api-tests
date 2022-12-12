package com.movieapitests;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.movieapitests.constants.BASE_URL;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class topRatedMoviesTest extends baseTest {

    @BeforeClass
    public void setup() {
        RestAssured.basePath = "/movie/top_rated";
    }

    @Test
    public void getTopRatedMovies() {
        RestAssured
                .given()
                    .spec(getDefaultRequestSpec())
                .when()
                    .get()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                .and()
                    .body("results", notNullValue())
                    .body("page", equalTo(1));
    }

    // A BUG stated in the api specs page max:1000 but the api accepts max is 500
    @Test( enabled=false )
    public void getTopRatedMoviesWithPage1000() {
        RestAssured
                .given()
                    .spec(getDefaultRequestSpec())
                    .queryParam("page", "1000")
                .when()
                    .get()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                .body("results", notNullValue())
                .body("page", equalTo(1000));
    }

    // Can be considered a bug as its not defined in the api specs
 @Test
    public void getTopRatedMoviesWithPage1001() {
     RestAssured
             .given()
                .spec(getDefaultRequestSpec())
                .queryParam("page", "1001")
             .when()
                .get()
             .then()
                 .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)     // 422
             .and()
                .body("success", equalTo(false));
 }

 @Test
    public void getTopRatedMovies404() {
     RestAssured
             .given()
                .spec(getDefaultRequestSpec())
             .when()
                .get("/movie/top")
             .then()
             .spec(responseBuilder(HttpStatus.SC_NOT_FOUND,
                     34, "The resource you requested could not be found."));
    }

    @Test
    public void getTopRatedMoviesWithArabicLanguage() {
        RestAssured
                .given()
                    .spec(getDefaultRequestSpec())
                    .queryParam("language", "ar-EG")
                .when()
                    .get()
                .then()
                    .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void getTopRatedMoviesWithoutApiKey() {
        RestAssured
                .get(BASE_URL)
                .then()
                .spec(responseBuilder(HttpStatus.SC_UNAUTHORIZED,
                        7, "Invalid API key: You must be granted a valid key."));
    }
}
