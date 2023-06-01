package com.rest;

import com.rest.pojo.workspace.Workspace;
import com.rest.pojo.workspace.WorkspaceRoot;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class WorkspacePojoTest {

  ResponseSpecification responseSpecification;

  @BeforeClass
  public void beforeClass() {
    RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
        setBaseUri("https://api.postman.com").
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

  @Test(dataProvider = "workspace")
  void workspace_serialize_deserialize(String name, String type, String description) {

    Workspace workspace = new Workspace(name, type, description);
    HashMap<String,String> myHashMap = new HashMap<>();
    workspace.setMyHashMap(myHashMap);
    WorkspaceRoot workspaceRoot = new WorkspaceRoot(workspace);

    WorkspaceRoot deserializedWorkspaceRoot = given().
            body(workspaceRoot).
            when().
            post("/workspaces").
            then().spec(responseSpecification).
            extract().response().as(WorkspaceRoot.class);
    assertThat(deserializedWorkspaceRoot.getWorkspace().getName(), equalTo(workspaceRoot.getWorkspace().getName()));
    assertThat(deserializedWorkspaceRoot.getWorkspace().getId(), matchesPattern("^[a-z0-9-]{36}$"));
  }

  @DataProvider(name= "workspace")
  public Object[][] getWorkspace() {
    return new Object[][]{
        {"myWorkspace3", "personal", "description"},
        {"myWorkspace4", "personal", "description"}
    };
  }
}
