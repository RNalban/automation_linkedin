import org.testng.annotations.Test;
import pages.FeedPage;
import pages.HomePage;
import pages.SignIn;

public class SearchTest extends SetUp {

    @Test
    public void searchPeople(){
        HomePage homePage = new HomePage(driver);
        SignIn signIn = homePage.goToSignIn();
        signIn.enterUsername("rn210898@outlook.com");
        signIn.enterPassword("RNaug1998");
        signIn.disableRememberMe();
        FeedPage feedPage=signIn.clickOnSignIn();
        feedPage.search("Tech");
        feedPage.clickOnPeople();

    }
}
