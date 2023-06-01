package com.rest;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;

public class AutomateHeadersTest {


    @Test
    public void multiple_headers_using_headers() {

        Header header = new Header("header", "value2");
        Header matchHeader = new Header("x-mock-match-request-headers", "header");
        Headers headers = new Headers(header, matchHeader);

        given().
                baseUri("https://5134126b-ac27-42c2-998d-a73be873b737.mock.pstmn.io").
                headers(headers).
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void multiple_headers_using_map() {

        HashMap<String, String> headers = new HashMap<>();
        headers.put("header", "value2");
        headers.put("x-mock-match-request-headers", "header");

        given().
                baseUri("https://5134126b-ac27-42c2-998d-a73be873b737.mock.pstmn.io").
                headers(headers).
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);

    }

    @Test
    public void multi_value_header_in_the_request() {

        Header header1 = new Header("header", "value1");
        Header header2 = new Header("header", "value2");
        Headers headers = new Headers(header1, header2);

        given().
                baseUri("https://5134126b-ac27-42c2-998d-a73be873b737.mock.pstmn.io").
                headers(headers).
                log().headers().
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void assert_response_headers() {

        HashMap<String, String> headers = new HashMap<>();
        headers.put("header", "value1");
        headers.put("x-mock-match-request-headers", "header");

        given().
                baseUri("https://5134126b-ac27-42c2-998d-a73be873b737.mock.pstmn.io").
                headers(headers).
                when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200).
                headers("responseHeader", "resValue1", "X-RateLimit-Limit", "120");
//                header("responseHeader", "resValue1").
//                header("X-RateLimit-Limit", "120");
    }

    @Test
    public void extract_response_headers() {

        HashMap<String, String> headers = new HashMap<>();
        headers.put("header", "value1");
        headers.put("x-mock-match-request-headers", "header");

        Headers extractedHeaders = given().
                baseUri("https://5134126b-ac27-42c2-998d-a73be873b737.mock.pstmn.io").
                headers(headers).
                when().
                get("/get").
                then().
                assertThat().
                statusCode(200).
                extract().headers();

        for (Header header : extractedHeaders) {
            System.out.print("header name = " + header.getName() + ", ");
            System.out.println("header value = " + header.getValue());
        }

    }

    @Test
    public void extract_multi_value_response_headers() {

        HashMap<String, String> headers = new HashMap<>();
        headers.put("header", "value1");
        headers.put("x-mock-match-request-headers", "header");

        Headers extractedHeaders = given().
                baseUri("https://5134126b-ac27-42c2-998d-a73be873b737.mock.pstmn.io").
                headers(headers).
                when().
                get("/get").
                then().
                assertThat().
                statusCode(200).
                extract().headers();

        List<String> values = extractedHeaders.getValues("multiValueHeader");

        for (String value : values) {
            System.out.println(value);
        }

    }

}
