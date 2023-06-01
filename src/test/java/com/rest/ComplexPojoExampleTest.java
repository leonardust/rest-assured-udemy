package com.rest;

import com.rest.pojo.collection.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ComplexPojoExampleTest {

    ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.getpostman.com").
                addHeader("x-api-key", "PMAK-6465da15c97dc91f89cf8d76-c79dd26c0355b6b4b540b8d44fd7b8b7e6").
                setContentType("application/json").
                log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    void complex_pojo_create_collection() {

        Header header = new Header("Content-Type", "application/json");
        List<Header> headerList = new ArrayList<>();
        headerList.add(header);

        Body body = new Body("raw", "{\"data\": \"123\"}");

        Request request = new Request("http://postman-echo.com/post", "POST", headerList, body, "This is a sample POST Request");

        RequestRoot requestRoot = new RequestRoot("Sample POST Request", request);
        List<RequestRoot> requestRootList = new ArrayList<>();
        requestRootList.add(requestRoot);

        Folder folder = new Folder("This is a folder", requestRootList);

        List<Object> folderList =  new ArrayList<>();
        folderList.add(folder);

        Info info = new Info("Sample collection", "Sample description", "https://schema.getpostman.com/json/collection/v2.1.0/collection.json");

        Collection collection = new Collection(info, folderList);

        CollectionRoot collectionRoot = new CollectionRoot(collection);

    given().
            body(collectionRoot).queryParam("workspace", "93569972-66ef-40a7-97bc-0e5741252c13").
            when().
            post("/collections").
            then().spec(responseSpecification).
            extract().
            response().as(CollectionRoot.class);
    }
}
