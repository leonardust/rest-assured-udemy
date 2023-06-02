package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.pojo.collection.*;
import com.rest.pojo.collection.collection.CollectionRequest;
import com.rest.pojo.collection.collectionroot.CollectionRootRequest;
import com.rest.pojo.collection.collectionroot.CollectionRootResponse;
import com.rest.pojo.collection.folder.FolderRequest;
import com.rest.pojo.collection.folder.FolderResponse;
import com.rest.pojo.collection.request.RequestRequest;
import com.rest.pojo.collection.requestroot.RequestRootRequest;
import com.rest.pojo.collection.requestroot.RequestRootResponse;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

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
  void complex_pojo_create_collection() throws JsonProcessingException, JSONException {

    Header header = new Header("Content-Type", "application/json");
    List<Header> headerList = new ArrayList<>();
    headerList.add(header);

    Body body = new Body("raw", "{\"data\": \"123\"}");

    RequestRequest request = new RequestRequest("http://postman-echo.com/post", "POST", headerList, body, "This is a sample POST Request");

    RequestRootRequest requestRoot = new RequestRootRequest("Sample POST Request", request);
    List<RequestRootRequest> requestRootList = new ArrayList<>();
    requestRootList.add(requestRoot);

    FolderRequest folder = new FolderRequest("This is a folder", requestRootList);

    List<FolderRequest> folderList = new ArrayList<>();
    folderList.add(folder);

    Info info = new Info("Sample collection", "Sample description", "https://schema.getpostman.com/json/collection/v2.1.0/collection.json");

    CollectionRequest collection = new CollectionRequest(info, folderList);

    CollectionRootRequest collectionRoot = new CollectionRootRequest(collection);

    String collectionUid = given().
        body(collectionRoot).queryParam("workspace", "93569972-66ef-40a7-97bc-0e5741252c13").
        when().
        post("/collections").
        then().spec(responseSpecification).
        extract().
        response().path("collection.uid");

    CollectionRootResponse deserializedCollectionRoot = given().
        pathParam("collectionUid", collectionUid).
        when().
        get("/collections/{collectionUid}").
        then().spec(responseSpecification).
        extract().
        response().as(CollectionRootResponse.class);

    ObjectMapper objectMapper = new ObjectMapper();
    String collectionRootStr = objectMapper.writeValueAsString(collectionRoot);
    String deserializedCollectionRootStr = objectMapper.writeValueAsString(deserializedCollectionRoot);

    //JSONAssert.assertEquals(collectionRootStr, deserializedCollectionRootStr,
//    new CustomComparator(JSONCompareMode.LENIENT,
//    new Customization("collection.item[*].item[*].request.url", new ValueMatcher<Object>() {
//@Override
//public boolean equal(Object o, Object t1) {
//    return true;
//    }
//    })));

    JSONAssert.assertEquals(collectionRootStr, deserializedCollectionRootStr,
        new CustomComparator(JSONCompareMode.STRICT_ORDER,
            new Customization("collection.item[*].item[*].request.url", (o, t1) -> true))); // -> example of excluding json property from assertion

    List<String> urlRequestList = new ArrayList<>();
    List<String> urlResponseList = new ArrayList<>();

    for (RequestRootRequest requestRootRequest : requestRootList) {
      System.out.println("url from request payload " + requestRootRequest.getRequest().getUrl());
      urlRequestList.add(requestRootRequest.getRequest().getUrl());
    }

    List<FolderResponse> folderResponseList = deserializedCollectionRoot.getCollection().getItem();
    for (FolderResponse folderResponse : folderResponseList) {
      List<RequestRootResponse> requestRootResponseList = folderResponse.getItem();
      for (RequestRootResponse requestRootResponse : requestRootResponseList) {
        Url url = requestRootResponse.getRequest().getUrl();
        System.out.println("url from response: " + url.getRaw());
        urlResponseList.add(url.getRaw());
      }
    }

    assertThat(urlResponseList, containsInAnyOrder(urlRequestList.toArray()));
  }

  @Test
  void simple_pojo_create_collection() throws JsonProcessingException, JSONException {

    List<FolderRequest> folderList = new ArrayList<>();

    Info info = new Info("Sample collection", "Sample description", "https://schema.getpostman.com/json/collection/v2.1.0/collection.json");

    CollectionRequest collection = new CollectionRequest(info, folderList);

    CollectionRootRequest collectionRoot = new CollectionRootRequest(collection);

    String collectionUid = given().
        body(collectionRoot).queryParam("workspace", "93569972-66ef-40a7-97bc-0e5741252c13").
        when().
        post("/collections").
        then().spec(responseSpecification).
        extract().
        response().path("collection.uid");

    CollectionRootResponse deserializedCollectionRoot = given().
        pathParam("collectionUid", collectionUid).
        when().
        get("/collections/{collectionUid}").
        then().spec(responseSpecification).
        extract().
        response().as(CollectionRootResponse.class);

    ObjectMapper objectMapper = new ObjectMapper();
    String collectionRootStr = objectMapper.writeValueAsString(collectionRoot);
    String deserializedCollectionRootStr = objectMapper.writeValueAsString(deserializedCollectionRoot);

    assertThat(objectMapper.readTree(collectionRootStr), equalTo(objectMapper.readTree(deserializedCollectionRootStr)));
  }

  // todo: implement test scenario CollectionWithMultipleFolders
  // todo: implement test scenario CollectionWithOneFolderAndMultipleRequestsInsideTheFolder,
  // todo: implement test scenario CollectionWithOnlyOneRequest
  // todo: implement test scenario CollectionWithRequestWithMultipleHeaders
}


