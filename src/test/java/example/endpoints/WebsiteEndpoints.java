package example.endpoints;

import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.http.HttpRequestActionBuilder;

public class WebsiteEndpoints {

  public static HttpRequestActionBuilder homePage = http("HomePage")
      .get("https://ecomm.gatling.io/")
      .check(status().in(200, 304));

}
