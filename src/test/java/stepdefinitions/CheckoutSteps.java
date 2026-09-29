package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.CartPage;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.CsvReader;
import utils.DriverFactory;

import java.util.Map;

public class CheckoutSteps {

    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private Map<String, String> data;

    @Given("I am logged in as a standard user")
    public void iAmLoggedInAsAStandardUser() {
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage = new InventoryPage(DriverFactory.getDriver());
    }

    @When("I add {string} to the cart")
    public void iAddToTheCart(String productName) {
        inventoryPage.addProductToCart(productName);
    }

    @Then("the cart badge should show {string} item")
    public void theCartBadgeShouldShow(String count) {
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), count);
    }

    @And("I go to the cart")
    public void iGoToTheCart() {
        inventoryPage.goToCart();
        cartPage = new CartPage(DriverFactory.getDriver());
    }

    @And("I proceed to checkout")
    public void iProceedToCheckout() {
        cartPage.proceedToCheckout();
        checkoutPage = new CheckoutPage(DriverFactory.getDriver());
    }

    @And("I enter checkout details from {string} for test {string}")
    public void iEnterCheckoutDetails(String file, String testId) {
        data = CsvReader.getRow(file, testId);
        checkoutPage.enterDetails(data.get("first_name"), data.get("last_name"), data.get("postal_code"));
    }

    @Then("I should see the expected checkout result")
    public void iShouldSeeTheExpectedCheckoutResult() {
        if (data.get("expected_result").equals("success")) {
            checkoutPage.finishCheckout();
            String confirmation = checkoutPage.getConfirmationText().toLowerCase();
            Assert.assertTrue(confirmation.contains(data.get("expected_message").toLowerCase()),
                    "Confirmation mismatch for " + data.get("test_id"));
        } else {
            Assert.assertTrue(checkoutPage.getErrorText().contains(data.get("expected_message")),
                    "Error message mismatch for " + data.get("test_id"));
        }
    }
}