package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.Wait;

public class HomePage {
    private WebDriver driver;

    private final By signIn = By.cssSelector("a[data-tracking-control-name=\"guest_homepage-basic_nav-header-signin\"]");



    public HomePage(WebDriver driver){
        this.driver=driver;
        Wait.waitForElementToBeClickable(driver,signIn);
    }
    public SignIn goToSignIn(){
        driver.findElement(signIn).click();
        return new SignIn(driver);
    }

    public boolean isSignInButtonPresent() {
        Wait.waitForVisibilityOfElement(driver,signIn);
        return driver.findElements(signIn).size() > 0;
    }
}
