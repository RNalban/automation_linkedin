package pages;

import org.openqa.selenium.*;
import utils.Wait;

public class FeedPage {
    protected WebDriver driver;
    public static String LINKEDIN_URL="https://www.linkedin.com/";
    private final By logoIcon = By.cssSelector("li-icon.ivm-view-attr__icon");
    private final By searchInput = By.cssSelector("input.search-global-typeahead__input");;
    private final By peopleBtn= By.cssSelector("#search-reusables__filters-bar > ul > li:nth-child(1) > button");
    private final By peoplefilterBtn= By.cssSelector("button[aria-label=\"Filter by: People\"]");
    private final By signOutBtn= By.cssSelector("nav[aria-label*=\"Primary\"] button + div a[href=\"/m/logout\"]");
    private final By meBtn= By.cssSelector("nav[aria-label*=\"Primary\"] button img");
    public FeedPage(WebDriver driver) {
        this.driver= driver;
        Wait.waitForVisibilityOfElement(driver,logoIcon);
    }

    public boolean isLogoPresent() {
        return driver.findElements(logoIcon).size() > 0;
    }

    public void search(String input){
        driver.findElement(searchInput).sendKeys(input+ Keys.ENTER);

    }

    public void clickOnPeople() {
        Wait.waitForElementToBeClickable(driver,peopleBtn);
        driver.findElement(peopleBtn).click();
        Wait.waitForVisibilityOfElement(driver,peoplefilterBtn);

    }
    public HomePage signOut(){

        Wait.addRandomDelay(3000,5000);

        driver.get(LINKEDIN_URL+"m/logout");
        driver.manage().deleteAllCookies();
        Wait.addRandomDelay(3000,5000);
        driver.get(LINKEDIN_URL+"home");

        return new HomePage(driver);

    }
    public ProfilePage goToProfile(String profileLink) {
        driver.get(profileLink);
        return new ProfilePage(driver); // Return a ProfilePage object
    }



}
