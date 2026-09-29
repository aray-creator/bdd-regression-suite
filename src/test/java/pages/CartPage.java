package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ElementUtils;

public class CartPage {
    private final WebDriver driver;
    private final By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public void proceedToCheckout() {
        ElementUtils.clickAndWaitForUrl(driver, checkoutButton, "checkout-step-one");
    }
}