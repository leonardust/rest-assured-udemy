package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class AutomatePostTest {

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
    void validate_post_request_bdd_style() {
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"My New Testing Workspace\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"My empty workspace to training sending post request\",\n" +
                "        \"visibility\": \"personal\"\n" +
                "    }\n" +
                "}";
        given().
                body(payload).
                when().
                post("/workspaces").
                then().
                assertThat().
                body("workspace.name", equalTo("My New Testing Workspace"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    void validate_post_request_no_bdd_style() {
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"My New Testing Workspace\",\n" +
                "        \"type\": \"personal\",\n" +
                "        \"description\": \"My empty workspace to training sending post request\",\n" +
                "        \"visibility\": \"personal\"\n" +
                "    }\n" +
                "}";

        Response response = with().
                body(payload).
                post("/workspaces");

        assertThat(response.path("workspace.name"), equalTo("My New Testing Workspace"));
        assertThat(response.path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    void validate_post_request_payload_from_file_bdd_style() {
        File file = new File("src/main/resources/create_workspace_payload.json");
        given().
                body(file).
                when().
                post("/workspaces").
                then().
                assertThat().
                body("workspace.name", equalTo("My New Testing Workspace"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    void validate_post_request_payload_from_file_no_bdd_style() {
        File file = new File("src/main/resources/create_workspace_payload.json");
        Response response = with().
                body(file).
                post("/workspaces");

        assertThat(response.path("workspace.name"), equalTo("My New Testing Workspace"));
        assertThat(response.path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    void validate_post_request_payload_as_map_bdd_style() {
        HashMap<String,Object> mainObject = new HashMap<>();

        HashMap<String,String> nestedObject = new HashMap<>();
        nestedObject.put("name", "My New Testing Workspace");
        nestedObject.put("type", "personal");
        nestedObject.put("description", "My empty workspace to training sending post request");

        mainObject.put("workspace", nestedObject);

        given().
                body(mainObject).
                when().
                post("/workspaces").
                then().
                assertThat().
                body("workspace.name", equalTo("My New Testing Workspace"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    void validate_post_request_payload_as_map_no_bdd_style() {
        HashMap<String,Object> mainObject = new HashMap<>();

        HashMap<String,String> nestedObject = new HashMap<>();
        nestedObject.put("name", "My New Testing Workspace");
        nestedObject.put("type", "personal");
        nestedObject.put("description", "My empty workspace to training sending post request");

        mainObject.put("workspace", nestedObject);
        Response response = with().
                body(mainObject).
                post("/workspaces");

        assertThat(response.path("workspace.name"), equalTo("My New Testing Workspace"));
        assertThat(response.path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    void validate_post_request_payload_json_array_as_list_bdd_style() {
        HashMap<String,Object> mainObject = new HashMap<>();

        HashMap<String,String> nestedObject = new HashMap<>();
        nestedObject.put("name", "My New Testing Workspace");
        nestedObject.put("type", "personal");
        nestedObject.put("description", "My empty workspace to training sending post request");

        mainObject.put("workspace", nestedObject);

        given().
                body(mainObject).
                when().
                post("/workspaces").
                then().
                assertThat().
                body("workspace.name", equalTo("My New Testing Workspace"),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"));
    }

}
