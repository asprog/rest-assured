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

public class ImageNegativeTests extends BaseTest{
  private final String PATH_TO_IMAGE = "src/test/resources/tv.gif";
  static String encodedFile;
  //String uploadedImageId;

  public ImageNegativeTests() {
    headers.put("Authorization", "Bearer " + token);
  }

  @BeforeEach
  void beforeTest(){
    byte[] byteArray = getFileContent();
    encodedFile = Base64.getEncoder().encodeToString(byteArray);
  }
  @Test
  void uploadFileNoAuthTokenTest(){
             given()
            .multiPart("image",encodedFile)
            .expect()
            .when()
            .post("https://api.imgur.com/3/image")
            .prettyPeek()
            .then()
            .statusCode(401);
  }
  @Test
  void uploadFileNoDataTest(){
    given()
            .headers(headers)
            .expect()
            .when()
            .post("https://api.imgur.com/3/image")
            .prettyPeek()
            .then()
            .statusCode(400);
  }
  @Test
  void uploadFileZeroLengthDataTest(){
    given()
            .headers(headers)
            .multiPart("image",0)
            .expect()
            .when()
            .post("https://api.imgur.com/3/image")
            .prettyPeek()
            .then()
            .statusCode(400);
  }
  @AfterEach
  void tearDown(){
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
