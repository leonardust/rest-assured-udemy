import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static io.restassured.RestAssured.given;

public class DownloadFileTest {

  @Test
  public void download_file() throws IOException {
    InputStream is = given().
        baseUri("https://raw.githubusercontent.com").
        log().all().
        when().
        get("/leonardust/playwright-java-new/main/README.md").
        then().
        log().all().
        extract().
        response().asInputStream();

    OutputStream os = new FileOutputStream("README.md");
    os.write(is.readAllBytes());
    os.close();
  }
}
