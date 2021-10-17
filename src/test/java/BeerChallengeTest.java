import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class BeerChallengeTest {

    @Test
    public void abvTest() {
        Response response =
                given()
                        .baseUri("https://api.punkapi.com/v2/")
                        .param("brewed_after", "12-2015")
                        .when()
                        .get("beers")
                        .then()
                        .log().all()
                        .assertThat()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract()
                        .response();
        List<Object> abvValues = response.path("abv");

        for (Object abv : abvValues) {
            System.out.println("ABV: " + abv);
            System.out.println(abv.getClass());
            Assert.assertNotNull(abv);

            Assert.assertTrue(Double.parseDouble(abv.toString()) > 3.0);
            Assert.assertNotEquals("", abv);
            Assert.assertEquals(abv.getClass(), Float.class);

        }

    }
}
