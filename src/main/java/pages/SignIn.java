package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.FeedPage;
import utils.Wait;

public class SignIn {
    private WebDriver driver;
    private final By username = By.name("session_key");
    private final By password = By.id("password");
    private final By signInBtn = By.cssSelector("#organic-div > form > div.login__form_action_container > button");
    private final By rememberMe = By.cssSelector("label[for=\"rememberMeOptIn-checkbox\"]");

    public SignIn(WebDriver driver){
        this.driver=driver;
        Wait.waitForVisibilityOfElement(driver, username);
    }

    public void enterUsername(String user){
        driver.findElement(username).click();
        driver.findElement(username).sendKeys(user);
        Wait.addRandomDelay(3000,5000);
    }

    public void enterPassword(String pass){
        driver.findElement(password).click();
        driver.findElement(password).sendKeys(pass);
        Wait.addRandomDelay(3000,5000);
    }
    public void disableRememberMe(){
        driver.findElement(rememberMe).click();
    }
    public FeedPage clickOnSignIn(){
        driver.findElement(signInBtn).click();
        return new FeedPage(driver);
    }


}
