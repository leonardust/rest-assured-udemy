import io.restassured.config.EncoderConfig;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class RequestParametersTest {

  @Test
  public void single_query_parameter() {

    given().
        baseUri("https://postman-echo.com").
//        param("foo1", "bar1"). // more generic
        queryParam("foo1", "bar1").
        log().all().
        when().
        get("/get").
        then().
        log().all().
        assertThat().
        statusCode(200);
  }

  @Test
  public void multiple_query_parameter() {

    HashMap<String, String> queryParams =  new HashMap<>();
    queryParams.put("foo1", "bar1");
    queryParams.put("foo2", "bar2");

    given().
        baseUri("https://postman-echo.com").
//        queryParam("foo1", "bar1").
//        queryParam("foo2", "bar2").
        queryParams(queryParams).
        log().all().
        when().
        get("/get").
        then().
        log().all().
        assertThat().
        statusCode(200);
  }

  @Test
  public void multiple_value_query_parameter() {

    given().
        baseUri("https://postman-echo.com").
        queryParam("foo1", "bar1,bar2,bar3").
//        queryParam("foo1", "bar1;bar2;bar3").
        log().all().
        when().
        get("/get").
        then().
        log().all().
        assertThat().
        statusCode(200);
  }

  @Test
  public void path_parameter() {

    given().
        baseUri("https://reqres.in").
        pathParam("userId", "2").
        log().all().
        when().
        get("/api/users/{userId}").
        then().
        log().all().
        assertThat().
        statusCode(200);
  }

  @Test
  public void multipart_form_data() {

    given().
        baseUri("https://postman-echo.com").
        multiPart("foo1", "bar1").
        log().all().
        when().
        post("/post").
        then().
        log().all().
        assertThat().
        statusCode(200);
  }

  @Test
  public void form_url_encoded() {

    given().
        baseUri("https://postman-echo.com").config(config.encoderConfig(EncoderConfig.encoderConfig().
            appendDefaultContentCharsetToContentTypeIfUndefined(false))).
        formParam("key1", "value1").
        formParam("key 2", "value 2").
        log().all().
        when().
        post("/post").
        then().
        log().all().
        assertThat().
        statusCode(200);
  }
}
