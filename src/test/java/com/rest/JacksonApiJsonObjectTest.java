import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.matchesPattern;

public class JacksonApiJsonObjectTest {

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

  @Test
  void serialize_map_to_json_using_jackson() throws JsonProcessingException {
    HashMap<String,Object> mainObject = new HashMap<>();

    HashMap<String,String> nestedObject = new HashMap<>();
    nestedObject.put("name", "My new Testing Workspace");
    nestedObject.put("type", "personal");
    nestedObject.put("description", "Rest Assured created this");

    mainObject.put("workspace", nestedObject);

    // Using jackson explicitly is not required due rest assured doing it under the hood.
    ObjectMapper objectMapper = new ObjectMapper();
    String mainObjectString = objectMapper.writeValueAsString(mainObject);

    given().
        body(mainObjectString).
        when().
        post("/workspaces").
        then().spec(responseSpecification).
        assertThat().
        body("workspace.name", equalTo("My new Testing Workspace"),
            "workspace.id", matchesPattern("^[a-z0-9-]{36}$"));
  }

  @Test
  void serialize_json_using_jackson() throws JsonProcessingException {

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode nestedObjectNode = objectMapper.createObjectNode();
    nestedObjectNode.put("name", "My new Testing Workspace");
    nestedObjectNode.put("type", "personal");
    nestedObjectNode.put("description", "Rest Assured created this");

    ObjectNode mainObjectNode = objectMapper.createObjectNode();
    mainObjectNode.set("workspace", nestedObjectNode);

    String mainObjectString = objectMapper.writeValueAsString(mainObjectNode);

    given().
        body(mainObjectNode).
        when().
        post("/workspaces").
        then().spec(responseSpecification).
        assertThat().
        body("workspace.name", equalTo("My new Testing Workspace"),
            "workspace.id", matchesPattern("^[a-z0-9-]{36}$"));
  }
}
