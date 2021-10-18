package cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import model.Beer;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;

public class StepDefinitions {

    private static final String BASE_URL = "https://api.punkapi.com/v2/";

    List<Beer> allBeers = new ArrayList<>();
    List<Beer> onePageBeers = new ArrayList<>();

    @Given("The request specification to PUNK API")
    public void theRequestSpecificationToPUNKAPI() {
        RestAssured.requestSpecification = given().baseUri(BASE_URL);
    }

    @When("I get all beers produced after {string}")
    public void iGetAllBeersProducedAfter(String date) {
        int pageNumber = 1;
        do {
            onePageBeers = with()
                    .param("brewed_after", date)
                    .param("page", pageNumber)
                    .get("beers")
                    .then()
                    .statusCode(200)
                    .extract()
                    .response().as(new TypeRef<List<Beer>>() {
                    });
            allBeers.addAll(onePageBeers);
            pageNumber += 1;
        }
        while (!onePageBeers.isEmpty());
        Assert.assertFalse("Beers list is empty", allBeers.isEmpty());
    }

    @Then("ABV values for all beers should not be a null")
    public void abvValuesForAllBeersShouldNotBeANull() {
        for (Beer beer : allBeers) {
            Assert.assertNotNull("ABV value is null.", beer.getAbv());
        }
    }

    @And("ABV values should not be an empty string")
    public void abvValuesShouldNotBeAnEmptyString() {
        for (Beer beer : allBeers) {
            Assert.assertNotEquals("ABV value is an empty string.", "", beer.getAbv());
        }
    }

    @And("ABV values should be a double")
    public void abvValuesShouldBeADouble() {
        for (Beer beer : allBeers) {
            Assert.assertEquals("ABV value: " + beer.getAbv() + ", is not a double.", beer.getAbv().getClass(), Double.class);
        }
    }

    @And("ABV values should be higher than {double}")
    public void abvValuesShouldBeHigherThan(double lowLimit) {
        for (Beer beer : allBeers) {
            Assert.assertTrue("ABV value: " + beer.getAbv() + ", is lower than 4.0", (Double) beer.getAbv() > lowLimit);
        }
    }

    @Then("Beer names should not be a null")
    public void allBeerNamesShouldNotNotBeANull() {
        for (Beer beer : allBeers) {
            Assert.assertNotNull("Beer name is null", beer.getName());
        }
    }

    @And("Beer names should not be an empty string")
    public void allBeerNamesShouldNotBeAnEmptyString() {
        for (Beer beer : allBeers) {
            Assert.assertNotEquals("Beer name is an empty string.", "", beer.getName());
        }
    }

    @Then("Image links should point to image file on PUNK API server")
    public void imageLinksShouldPointToImageFileOnPUNKAPIServer() {
        for (Beer beer : allBeers) {
            Assert.assertTrue("Image url is not valid", beer.getImageUrl().matches("https://images.punkapi.com/v2/[a-zA-Z0-9]+.(png|jpg)"));
        }
    }

    @And("There should be at least one hops in the ingredients list")
    public void thereShouldBeAtLeastOneHopsInTheIngredientsList() {
        for (Beer beer : allBeers) {
            Assert.assertNotNull("There are no hops in the ingredients list", beer.getIngredients().getHops());
            Assert.assertFalse("There are no hops in the ingredients list", beer.getIngredients().getHops().isEmpty());
        }
    }

    @Then("There should be at least one malt in the ingredients list")
    public void thereShouldBeAtLeastOneMaltInTheIngredientsList() {
        for (Beer beer : allBeers) {
            Assert.assertNotNull("There are no malt in the ingredients list", beer.getIngredients().getMalt());
            Assert.assertFalse("There are no malt in the ingredients list", beer.getIngredients().getMalt().isEmpty());
        }
    }

    @And("Yeast should not be null or empty")
    public void yeastShouldNotBeNullOrEmpty() {
        for (Beer beer : allBeers) {
            Assert.assertNotNull("Yeast is null", beer.getIngredients().getYeast());
            Assert.assertNotEquals("Yeast in an empty string", "", beer.getIngredients().getYeast());
        }
    }
}