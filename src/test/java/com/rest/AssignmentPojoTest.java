package com.rest;

import com.rest.pojo.assignment.Address;
import com.rest.pojo.assignment.Geo;
import com.rest.pojo.assignment.User;
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
import static org.hamcrest.Matchers.notNullValue;

public class AssignmentPojoTest {

  ResponseSpecification responseSpecification;

  @BeforeClass
  public void beforeClass() {
    RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
        setBaseUri("https://jsonplaceholder.typicode.com").
        setContentType("application/json").
        log(LogDetail.ALL);
    RestAssured.requestSpecification = requestSpecBuilder.build();

    ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
        expectStatusCode(201).
        expectContentType(ContentType.JSON).
        log(LogDetail.ALL);
    responseSpecification = responseSpecBuilder.build();
  }

  @Test
  void assignment_serialize_and_validate_content_type_and_id_is_not_null() {

    Geo geo = new Geo("-37.3159", "81.1496");
    Address address = new Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", geo);
    User user = new User("Leanne Graham", "Bret", "Sincere@april.biz", address);

    User deserializedUser = given().
            body(user).
            when().
            post("/users").
            then().spec(responseSpecification).
            extract().response().as(User.class);
    assertThat(deserializedUser.getId(), notNullValue());
  }

}
