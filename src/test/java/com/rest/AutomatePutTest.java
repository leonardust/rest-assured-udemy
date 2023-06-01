package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class AutomatePutTest {
    String workspaceId = "c6e02182-e965-4652-8d66-22ba35920cb3";

    @BeforeClass
    void before_test() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.postman.com").
                addHeader("X-Api-Key", "PMAK-6465da15c97dc91f89cf8d76-c79dd26c0355b6b4b540b8d44fd7b8b7e6").
                setContentType(ContentType.JSON).
                log(LogDetail.HEADERS);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    void validate_put_request_bdd_style() {

        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"My New Testing Workspace2\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"My empty workspace to training sending put request2\",\n" +
                "        \"visibility\": \"personal\"\n" +
                "    }\n" +
                "}";
        given().
                body(payload).
                pathParam("workspaceId", workspaceId).
                when().
                put("/workspaces/{workspaceId}").
                then().
                assertThat().
                body("workspace.name", equalTo("My New Testing Workspace2"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"),
                        "workspace.id", equalTo(workspaceId));
    }

    @Test
    void validate_put_request_no_bdd_style() {
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"My New Testing Workspace2\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"My empty workspace to training sending put request2\",\n" +
                "        \"visibility\": \"personal\"\n" +
                "    }\n" +
                "}";
        
        Response response = with().
                body(payload).
                put("/workspaces/" +  workspaceId);

        assertThat(response.path("workspace.name"), equalTo("My New Testing Workspace"));
        assertThat(response.path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));
        assertThat(response.path("workspace.id"), equalTo(workspaceId));
    }


}
