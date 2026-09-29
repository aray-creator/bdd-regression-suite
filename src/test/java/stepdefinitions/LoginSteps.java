package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.LoginPage;
import utils.CsvReader;
import utils.DriverFactory;

import java.util.Map;

public class LoginSteps {

    private LoginPage loginPage;
    private Map<String, String> data;

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.open();
    }

    @When("I login using data from {string} for test {string}")
    public void iLoginUsingData(String file, String testId) {
        data = CsvReader.getRow(file, testId);
        loginPage.login(data.get("username"), data.get("password"));
    }

    @Then("I should see the expected login result")
    public void iShouldSeeExpectedResult() {
        if (data.get("expected_result").equals("success")) {
            Assert.assertTrue(loginPage.isOnInventoryPage(), "Login should succeed");
        } else {
            Assert.assertTrue(loginPage.getErrorText().contains(data.get("expected_message")),
                    "Error message mismatch for " + data.get("test_id"));
        }
    }
}