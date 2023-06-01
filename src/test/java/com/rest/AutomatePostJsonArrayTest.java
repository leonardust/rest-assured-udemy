package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AutomatePostJsonArrayTest {

    @BeforeClass
    void before_test() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://5134126b-ac27-42c2-998d-a73be873b737.mock.pstmn.io").
                addHeader("x-mock-match-request-body", "true").
                setConfig(config.encoderConfig(EncoderConfig.encoderConfig().
                                                            appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                setContentType(ContentType.JSON). // comment out setConfig and setContentType to "application/json;charset=utf-8"
                log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    void validate_post_request_payload_json_array_as_list_bdd_style() {
        HashMap<String, String> obj5001 = new HashMap<>();
        obj5001.put("id", "5001");
        obj5001.put("type", "None");

        HashMap<String, String> obj5002 = new HashMap<>();
        obj5002.put("id", "5002");
        obj5002.put("type", "Glazed");

        List<HashMap<String, String>> jsonList = new ArrayList<>();
        jsonList.add(obj5001);
        jsonList.add(obj5002);

        given().
                body(jsonList).
                when().
                post("/post").
                then().
                assertThat().
                body("msg", equalTo("success"));
    }

}
