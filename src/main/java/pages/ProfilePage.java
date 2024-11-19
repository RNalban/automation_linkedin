package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.Wait;

public class ProfilePage {
    WebDriver driver;
    private final By moreButton = By.cssSelector("section[class*=\"artdeco\"] button[aria-label*=\"More actions\"]");
    private final By connectButton= By.xpath("(//button[contains(@aria-label, 'Invite')])[2]");


    private final By listConnectionBtn = By.cssSelector("section[class*=\"artdeco\"] button[aria-label*=\"More actions\"]+div div[aria-label*=\"Invite\"] span[class*='display']");
    private final By noteButton = By.xpath("(//button[contains(@aria-label, 'Add a note')])");
    private final By sendButton= By.xpath("(//button[contains(@aria-label, 'Send')])");
    private final By messageBox= By.id("custom-message");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickConnect() {
      Wait.addRandomDelay(2000,5000);

//        if(driver.findElement(connectButton).isDisplayed()){
////            Wait.waitForElementToBeClickable(driver,connectButton);
////            driver.findElement(connectButton).click();
      //  }else{
      Wait.waitForElementIgnoreException(driver,moreButton);
        driver.findElement(moreButton).click();
        Wait.waitForVisibilityOfElement(driver,listConnectionBtn);
        driver.findElement(listConnectionBtn).click();

    }

    public void sendPersonalizedMessage(String message) {
        Wait.waitForVisibilityOfElement(driver, noteButton);
        driver.findElement(noteButton).click();
        Wait.waitForVisibilityOfElement(driver, messageBox);
        driver.findElement(messageBox).click();
        driver.findElement(messageBox).sendKeys(message);
        driver.findElement(sendButton).click();
    }
}

