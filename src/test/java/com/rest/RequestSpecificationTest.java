package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RequestSpecificationTest {

    @BeforeClass
    void before_test() {
//        requestSpecification = with().
//                baseUri("https://api.postman.com").
//                header("X-Api-Key", "PMAK-6465da15c97dc91f89cf8d76-c79dd26c0355b6b4b540b8d44fd7b8b7e6").
//                log().all(); // to log request
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.postman.com");
        requestSpecBuilder.addHeader("X-Api-Key", "PMAK-6465da15c97dc91f89cf8d76-c79dd26c0355b6b4b540b8d44fd7b8b7e6");
        requestSpecBuilder.log(LogDetail.HEADERS);

        RestAssured.requestSpecification = requestSpecBuilder.build();
    }

    @Test
    public void query_test() {
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(RestAssured.requestSpecification);
        System.out.println(queryableRequestSpecification.getBaseUri());
        System.out.println(queryableRequestSpecification.getHeaders());
    }

    @Test
    public void validate_status_code_test() {
        // .then().log().all().extract().response() to log response
//        Response response = requestSpecification.get("/workspaces").then().log().all().extract().response();
        Response response = get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(), is(equalTo(200)));
    }

    @Test
    void validate_response_body() {
//        Response response = requestSpecification.get("/workspaces").then().log().all().extract().response();
        Response response = get("/workspaces").then().log().all().extract().response();
        assertThat(response.statusCode(), is(equalTo(200)));
        assertThat(response.path("workspaces.name"), hasItems("My Workspace", "Edu", "My Updated Empty workspace"));
        assertThat(response.path("workspaces.type"), hasItems("personal", "personal", "personal"));
        assertThat(response.path("workspaces[1].name").toString(), equalTo("Edu"));
        assertThat(response.path("workspaces.size()"), equalTo(3));
        assertThat(response.path("workspaces.name"), hasItem("Edu"));
    }
}