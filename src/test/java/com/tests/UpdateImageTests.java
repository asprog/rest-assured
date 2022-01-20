package com.tests;

import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static com.tests.Endpoints.UPLOAD_IMAGE;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

public class UpdateImageTests extends BaseTest {
    private final String PATH_TO_IMAGE = "src/test/resources/tv.gif";
    private String imageId;
    private MultiPartSpecification base64MultiPartSpec;
    private String encodedFile;
    private Response response;
    private String deleteHash;

    public UpdateImageTests() {
        headers.put("Authorization", "Bearer " + token);
    }

    @BeforeEach
    void beforeTest() {
        byte[] byteArray = getFileContent(PATH_TO_IMAGE);
        encodedFile = Base64.getEncoder().encodeToString(byteArray);
        base64MultiPartSpec = new MultiPartSpecBuilder(encodedFile)
                .controlName("image")
                .build();

        RequestSpecification requestSpecificationWithAuthBase64 = new RequestSpecBuilder()
                .addHeaders(headers)
                .addMultiPart(base64MultiPartSpec)
                .build();
        response = given(requestSpecificationWithAuthBase64, positiveResponseSpecification)
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .response();
        imageId = response.jsonPath().getString("data.id");
        deleteHash = response.jsonPath().getString("data.deletehash");
    }

    @DisplayName("изменение title")
    @Test
    void updateFileTest() {
       /* response = given(requestSpecificationWithAuthBase64, positiveResponseSpecification)
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .response();*/
    }

}
