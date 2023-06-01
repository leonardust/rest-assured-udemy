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

public class AutomateDeleteTest {
    String workspaceId = "6849ec02-bd2a-4cdd-a728-e68e7d337e54";

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
    void validate_delete_request_bdd_style() {

        given().
                pathParam("workspaceId", workspaceId).
                when().
                delete("/workspaces/{workspaceId}").
                then().
                assertThat().
                body("workspace.id", matchesPattern("^[a-z0-9-]{36}$"),
                        "workspace.id", equalTo(workspaceId));
    }

    @Test
    void validate_delete_request_no_bdd_style() {

        Response response = with().
                pathParam("workspaceId", workspaceId).
                delete("/workspaces/{workspaceId}");

        assertThat(response.path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));
        assertThat(response.path("workspace.id"), equalTo(workspaceId));
    }


}
