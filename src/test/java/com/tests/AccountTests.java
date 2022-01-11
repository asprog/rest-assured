package com.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AccountTests extends BaseTest {

    public AccountTests() {
        headers.put("Authorization", "Bearer " + token);
    }

    @Test
    void getAccountInfoTest() {
        given(requestWithAuth,positiveResponseSpecification).get("https://api.imgur.com/3/account/{username}",username);
    }
    @Test
    void getAccountInfoWithAssertionsAfterTest() {
        Response resp = given()
                .headers(headers)
                .log()
                .method()
                .log()
                .uri()
                .when()
                .get("https://api.imgur.com/3/account/{username}",username)
                .prettyPeek();
        assertThat(resp.jsonPath().get("data.url"),equalTo(username));

    }
}
