package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ResponseSpecificationTest {

    @BeforeClass
    void before_test() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.postman.com").
                addHeader("X-Api-Key", "PMAK-6465da15c97dc91f89cf8d76-c79dd26c0355b6b4b540b8d44fd7b8b7e6").
                log(LogDetail.HEADERS);
        RestAssured.requestSpecification = requestSpecBuilder.build();

//        responseSpecification = RestAssured.expect().
//                                           statusCode(200).
//                                           contentType(ContentType.JSON).
//                                           log().all();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.XML).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    void validate_get_status_code() {
        get("/workspaces");
    }

    @Test
    void validate_response_body() {
        Response response = get("/workspaces").
                then().
                extract().response();
        assertThat(response.path("workspaces[1].name").toString(), equalTo("Edu"));
    }
}