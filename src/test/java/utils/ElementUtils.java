package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Supplier;

public class ElementUtils {

    public static void clickAndWaitForUrl(WebDriver driver, By locator, String expectedUrlFragment) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();

        boolean navigated = waitQuietlyForUrl(driver, expectedUrlFragment, 3);

        if (!navigated) {
            // First click didn't register (JS listener not ready yet) — retry with a JS click
            WebElement retryElement = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", retryElement);
            wait.until(ExpectedConditions.urlContains(expectedUrlFragment));
        }
    }

    private static boolean waitQuietlyForUrl(WebDriver driver, String fragment, int seconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .until(ExpectedConditions.urlContains(fragment));
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
}