package example.groups;

import io.gatling.javaapi.core.*;

import static io.gatling.javaapi.core.CoreDsl.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import static example.endpoints.ApiEndpoints.*;
import static example.endpoints.WebsiteEndpoints.*;

public class ScenarioGroups {

  private record Product(
      int id,
      String name,
      String color,
      String price,
      int quantity,
      String imageSrc,
      String imageAlt) {
  }

  private static final FeederBuilder<String> usersFeeder = csv("data/users_dev.csv").circular();

  private static final ObjectMapper mapper = new ObjectMapper();

  public static final ChainBuilder homeAnonymous = group("homeAnonymous").on(
      homePage,
      session,
      exec(session -> session.set("pageNumber", "0")),
      exec(session -> session.set("searchKey", "")),
      products);

  public static final ChainBuilder authenticate = group("authenticate").on(
      loginPage,
      feed(usersFeeder),
      login);

  public static final ChainBuilder browseAndAddToCart = group("browseAndAddToCart").on(
      products,
      exec(session -> {
        try {
          List<Product> products = mapper.readValue(
              session.getString("Products"), new TypeReference<List<Product>>() {
              });

          Random rand = new Random();
          Product randomProduct = products.get(rand.nextInt(products.size()));
          List<Product> cartItems = new ArrayList<>();
          cartItems.add(randomProduct);

          // Serialize updated cart list back to session
          String cartItemsJsonString = mapper.writeValueAsString(cartItems);
          return session.set("CartItems", cartItemsJsonString);

        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }),
      addToCart);

}
