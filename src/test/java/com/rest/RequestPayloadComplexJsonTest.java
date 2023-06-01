import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RequestPayloadComplexJsonTest {

  ResponseSpecification customResponseSpecification;

  @BeforeClass
  public void beforeClass() {
    RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
        setBaseUri("https://5134126b-ac27-42c2-998d-a73be873b737.mock.pstmn.io").
        addHeader("x-mock-match-request-body", "true").
        setContentType("application/json;charset=utf-8").
        log(LogDetail.ALL);
    RestAssured.requestSpecification = requestSpecBuilder.build();

    ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
        expectStatusCode(200).
        expectContentType(ContentType.JSON).
        log(LogDetail.ALL);
    customResponseSpecification = responseSpecBuilder.build();
  }

  @Test
  public void validate_post_request_payload_complex_json() {

    List<Integer> idArrayList =  new ArrayList<>();
    idArrayList.add(5);
    idArrayList.add(9);

    HashMap<String,Object> batterHashMap1 = new HashMap<>();
    batterHashMap1.put("id", "1001");
    batterHashMap1.put("type", "Regular");

    HashMap<String,Object> batterHashMap2 = new HashMap<>();
    batterHashMap2.put("id", idArrayList);
    batterHashMap2.put("type", "Chocolate");

    List<HashMap<String, Object>> batterArrayList = new ArrayList<>();
    batterArrayList.add(batterHashMap1);
    batterArrayList.add(batterHashMap2);

    HashMap<String, List<HashMap<String, Object>>> battersHashMap = new HashMap<>();
    battersHashMap.put("batter", batterArrayList);

    List<String> typeArrayList = new ArrayList<>();
    typeArrayList.add("test1");
    typeArrayList.add("test2");

    HashMap<String, Object> toppingHashMap2 = new HashMap<>();
    toppingHashMap2.put("id", "5002");
    toppingHashMap2.put("type", typeArrayList);

    HashMap<String, Object> toppingHashMap1 = new HashMap<>();
    toppingHashMap1.put("id", "5001");
    toppingHashMap1.put("type", "None");

    List<HashMap<String,Object>> toppingArraylist = new ArrayList<>();
    toppingArraylist.add(toppingHashMap1);
    toppingArraylist.add(toppingHashMap2);

    HashMap<String, Object> mainHashMap = new HashMap<>();
    mainHashMap.put("id", "0001");
    mainHashMap.put("type", "donut");
    mainHashMap.put("name", "Cake");
    mainHashMap.put("ppu", 0.55);
    mainHashMap.put("batters", battersHashMap);
    mainHashMap.put("topping", toppingArraylist);

    given().
        body(mainHashMap).
        when().
        post("/postComplexJson").
        then().
        spec(customResponseSpecification).
        assertThat().
        body("msg", equalTo("success"));

  }
}
