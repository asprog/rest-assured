package com.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

public class AccountNegativeTests extends BaseTest {
    public AccountNegativeTests() {
        headers.put("Authorization", "Bearer " + token);
    }

    @Test
    void getAccountInfoNoAuthTokenTest() {
        given()
                .log()
                .method()
                .log()
                .uri()
                .when()
                .get("https://api.imgur.com/3/account/{username}",username)
                .prettyPeek()
                .then()
                .statusCode(401);
    }
    @Test
    void getAccountInfoNoAuthTokenWithAssertionsAfterTest() {
        Response resp = given()
                .log()
                .method()
                .log()
                .uri()
                .when()
                .get("https://api.imgur.com/3/account/{username}",username)
                .prettyPeek();
        assertNull(resp.jsonPath().get("data.url"));
    }
    @Test
    void getAccountInfoNoDataWithAssertionsAfterTest() {
        Response resp = given()
                .headers(headers)
                .log()
                .method()
                .log()
                .uri()
                .when()
                .get("https://api.imgur.com/3/account/void")
                .prettyPeek();
        assertThat(resp.jsonPath().get("data.url"),equalTo("void"));
    }
}
