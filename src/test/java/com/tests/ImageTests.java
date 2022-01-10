package com.tests;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ImageTests extends BaseTest{
  private final String PATH_TO_IMAGE = "src/test/resources/tv.gif";
  static String encodedFile;
  String uploadedImageId;

  public ImageTests() {
    headers.put("Authorization", "Bearer " + token);
  }

  @BeforeEach
  void beforeTest(){
    byte[] byteArray = getFileContent();
    encodedFile = Base64.getEncoder().encodeToString(byteArray);
  }
  @Test
  void uploadFileTest(){
    uploadedImageId = given()
            .headers(headers)
            .multiPart("image",encodedFile)
            .expect()
            .body("success",is(true))
            .body("data.id",is(notNullValue()))
            .when()
            .post("https://api.imgur.com/3/image")
            .prettyPeek()
            .then()
            .extract()
            .response()
            .jsonPath()
            .getString("data.deletehash");

  }
  @AfterEach
  void tearDown(){
    given()
            .headers(headers)
            .when()
            .delete("https://api.imgur.com/3/account/{username}/image/{deleteHash}",username,uploadedImageId)
            .prettyPeek()
            .then()
            .statusCode(200);
  }

  private byte[] getFileContent(){
    byte[] byteArray = new byte[8];
    try {
      byteArray = FileUtils.readFileToByteArray(new File(PATH_TO_IMAGE));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return byteArray;
  }

}
