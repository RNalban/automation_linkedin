package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import utils.Wait;

public class ProfilePage {
    WebDriver driver;
    private final By moreButton = By.cssSelector("section[class*=\"artdeco\"] button[aria-label*=\"More actions\"]");
    private final By connectButton= By.cssSelector("section[class*=\"artdeco\"] button[aria-label*=\"Invite\"]");


    private final By listConnectionBtn = By.cssSelector("section[class*=\"artdeco\"] button[aria-label*=\"More actions\"]+div div[aria-label*=\"Invite\"] span[class*='display']");
    private final By pendingBtn = By.cssSelector("section[class*=\"artdeco\"] button[aria-label*=\"Pending\"] svg +span");
    private final By listPendingBtn = By.cssSelector("section[class*=\"artdeco\"] button[aria-label*=\"More actions\"]+div div[aria-label*=\"Pending\"]");
    private final By noteButton = By.xpath("(//button[contains(@aria-label, 'Add a note')])");
    private final By withoutNoteBtn = By.xpath("(//button[contains(@aria-label, 'Send without a note')])");
    private final By sendButton= By.xpath("(//button[contains(@aria-label, 'Send')])");
    private final By messageBox= By.id("custom-message");

    public ProfilePage(WebDriver driver) {
        Wait.addRandomDelay(3000,5000);
        this.driver = driver;
    }

    public void clickConnect() {
      Wait.addRandomDelay(2000,5000);
        Wait.waitForElementIgnoreException(driver,moreButton);
      if(driver.findElement(connectButton).isDisplayed()){
          driver.findElement(connectButton).click();
      }
    else{

        driver.findElement(moreButton).click();
        Wait.waitForVisibilityOfElement(driver,listConnectionBtn);
        driver.findElement(listConnectionBtn).click();
}

    }

    public void sendPersonalizedMessage(String message) {
        Wait.waitForVisibilityOfElement(driver, noteButton);
        driver.findElement(noteButton).click();
        Wait.waitForVisibilityOfElement(driver, messageBox);
        driver.findElement(messageBox).click();
        driver.findElement(messageBox).sendKeys(message);
        driver.findElement(sendButton).click();
        driver.findElement(withoutNoteBtn).click();
    }
    public void sendWithoutMessage() {
        Wait.addRandomDelay(2000,5000);
        Wait.waitForVisibilityOfElement(driver, withoutNoteBtn);
        driver.findElement(withoutNoteBtn).click();
    }


    public boolean checkPendingStatus(){
        Wait.addRandomDelay(2000,5000);

        try {
            Wait.addRandomDelay(2000,5000);
            // Check if pendingBtn is not displayed
            Wait.waitForVisibilityOfElement(driver, pendingBtn);
            if (!driver.findElements(pendingBtn).isEmpty() && driver.findElement(pendingBtn).isDisplayed()) {
                System.out.println("pendingBtn is present but not visible. Waiting for visibility...");
                return driver.findElements(pendingBtn).size() > 0; // Return true if pendingBtn becomes visible
            } else {
                // If pendingBtn is displayed or not present, handle the moreButton logic
                System.out.println("pendingBtn is either visible or not present. Clicking moreButton...");
                driver.findElement(moreButton).click();
                Wait.waitForVisibilityOfElement(driver, listPendingBtn);
                return driver.findElements(listPendingBtn).size() > 0; // Return true if listPendingBtn is visible
            }
        } catch (NoSuchElementException e) {
            System.err.println("Element not found: " + e.getMessage());
            return false;
        }
    }

}

