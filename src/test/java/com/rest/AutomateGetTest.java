package com.rest;

import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AutomateGetTest {

    RequestSpecification requestSpecification;

    @BeforeClass
    void before_class() {
        requestSpecification = given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-6465da15c97dc91f89cf8d76-c79dd26c0355b6b4b540b8d44fd7b8b7e6");
    }

    @Test
    void validate_get_status_code() {
        given(requestSpecification).
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().
                statusCode(202);
    }

    @Test
    void validate_response_body() {
        given(requestSpecification).
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().
                statusCode(200).
                body("workspaces.name", hasItems("My Workspace", "Edu", "My Updated Empty workspace"),
                        "workspaces.type", hasItems("personal", "personal", "personal"),
                        "workspaces[1].name", equalTo("Edu"),
                        "workspaces[1].name", is(equalTo("Edu")),
                        "workspaces.size()", is(equalTo(3)),
                        "workspaces.name", hasItem("Edu"));
    }

    @Test
    void extract_response() {
        String name = given(requestSpecification).
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                extract().response().path("workspaces[0].name");
        System.out.println("workspace name = " + name);
//        JsonPath jsonPath = new JsonPath(response.asString());
//        System.out.println("response = " + jsonPath.getString("workspaces[0].name"));
    }

    @Test
    void hamcrest_assert_on_extracted_response() {
        String name = given(requestSpecification).
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                extract().response().path("workspaces[1].name");
        System.out.println("workspace name = " + name);
        assertThat(name, equalTo("Edu"));  // hamcrest
        Assert.assertEquals(name, "Edu"); // testng
    }

    @Test
    void validate_response_body_hamcrest_collection_matchers() {
        given(requestSpecification).
                when().
                get("/workspaces").
                then().
//                log().all().
                        assertThat().
                statusCode(200).
                body("workspaces.name", hasItem("Edu"),
                        "workspaces.name", not(hasItem("Dupa")),
                        "workspaces.name", hasItems("Edu", "My Updated Empty workspace"),
                        "workspaces.name", contains("My Workspace", "Edu", "My Updated Empty workspace"),
                        "workspaces.name", containsInAnyOrder("Edu", "My Workspace", "My Updated Empty workspace"),
                        "workspaces.name", is(not(empty())),
                        "workspaces.name", hasSize(3),
                        "workspaces.name.size()", equalTo(3));
    }

    @Test
    void validate_response_body_hamcrest_map_matchers() {
        given(requestSpecification).
                when().
                get("/workspaces").
                then().
//                log().all().
                        assertThat().
                statusCode(200).
                body("workspaces[0]", hasKey("id"),
                        "workspaces[0]", hasValue("My Workspace"),
                        "workspaces[0]", hasEntry("id", "31ea5d16-6e36-47cc-832c-aed2ca70a0af"),
                        "workspaces[0]", not(equalTo(Collections.EMPTY_MAP)));
    }

    @Test
    void validate_response_body_hamcrest_all_any_off() {
        given(requestSpecification).
                when().
                get("/workspaces").
                then().
//                log().all().
                        assertThat().
                statusCode(200).
                body("workspaces[0].name", allOf(startsWith("My"), containsString("Workspace")),
                        "workspaces[0].name", anyOf(startsWith("My"), containsString("Edu")));
    }
}
