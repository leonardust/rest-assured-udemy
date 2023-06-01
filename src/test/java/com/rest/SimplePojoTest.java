package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.pojo.simple.SimplePojo;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SimplePojoTest {

  ResponseSpecification customResponseSpecification;

  @BeforeClass
  public void beforeClass() {
    RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
        setBaseUri("https://5134126b-ac27-42c2-998d-a73be873b737.mock.pstmn.io").
        setContentType(ContentType.JSON).
        log(LogDetail.ALL);
    RestAssured.requestSpecification = requestSpecBuilder.build();

    ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
        expectStatusCode(200).
        expectContentType(ContentType.JSON).
        log(LogDetail.ALL);
    customResponseSpecification = responseSpecBuilder.build();
  }

  @Test
  void simple_pojo_example() throws JsonProcessingException {

    SimplePojo simplePojo = new SimplePojo("value1", "value2");

    SimplePojo deserializedSimplePojo = given().
        body(simplePojo).when().
        post("/postSimplePojo").
        then().spec(customResponseSpecification).
        extract().
        response().
        as(SimplePojo.class);

    ObjectMapper objectMapper = new ObjectMapper();
    String deserializedSimplePojoStr = objectMapper.writeValueAsString(deserializedSimplePojo);
    String simplePojoStr = objectMapper.writeValueAsString(simplePojo);

    assertThat(objectMapper.readTree(deserializedSimplePojoStr), equalTo(objectMapper.readTree(simplePojoStr)));
  }
}
