package example.endpoints;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.http.*;

public class ApiEndpoints {

  public static HttpRequestActionBuilder session = http("session")
      .get("/session")
      .check(status().is(200))
      .check(jmesPath("sessionId").saveAs("SessionId"));

}
