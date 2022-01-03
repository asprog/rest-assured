import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public abstract class BaseTest {
    static Properties properties = new Properties();
    static String token;
    static String username;

    @BeforeAll
    static void beforeAll() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.filters(new AllureRestAssured());
        getProperties();
        token    = properties.getProperty("token");
        username = properties.getProperty("username");
        //headers.put("Authorization", "Bearer " + token);
    }
    private static void getProperties(){
      try(InputStream output = new FileInputStream("src/test/resources/application.properties")){
        properties.load(output);
      }catch (IOException e){
        e.printStackTrace();
      }
    }
    /*@Test
    void getAccountInfoTest() {
        given()
                .headers(headers)
                .log()
                .all()
                .when()
                .get("https://api.imgur.com/3/account/" + userName)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
    @Test
    void getAccountInfoVerifyUrlTest() {
        String url = given()
                .headers(headers)
                .when()
                .get("https://api.imgur.com/3/account/" + userName)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .response()
                .jsonPath()
                .getString("data.url");
        assertThat(url, equalTo(userName));
    }
*/

    /*@AfterEach
    void tearDown() {
        given()
                .headers("Authorization", token)
                .when()
                .delete("account/{username}/image/{deleteHash}", userName, uploadedImageHashCode)
                .prettyPeek()
                .then()
                .statusCode(200);
    }*/

}
