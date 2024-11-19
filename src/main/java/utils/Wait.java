package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Wait {

    public static WebElement waitForVisibilityOfElement(WebDriver driver, By element){
        return  new WebDriverWait(driver, Duration.ofSeconds(40)).until(ExpectedConditions.visibilityOfElementLocated(element));

    }
    public static WebElement waitForElementToBeClickable(WebDriver driver,By element){
        return  new WebDriverWait(driver, Duration.ofSeconds(40)).until(ExpectedConditions.elementToBeClickable(element));

    }

    public static FluentWait<WebDriver> waitForElementIgnoreException(WebDriver driver, By element){
        return  new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(6))
                .ignoring(NoSuchElementException.class);
    }

    public static void addRandomDelay(long baseDelay, long variability) {
        if (baseDelay < 0 || variability < 0) {
            throw new IllegalArgumentException("baseDelay and variability must be non-negative");
        }

        try {
            long delay = baseDelay + (long) (Math.random() * variability);
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted during delay: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }



}


