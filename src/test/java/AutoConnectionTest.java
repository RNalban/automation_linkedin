import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.FeedPage;
import pages.ProfilePage;
import pages.HomePage;
import pages.SignIn;
import utils.ConfigLoader;
import utils.Contact;
import utils.ExcelHandler;
import utils.Wait;

import java.util.List;

public class AutoConnectionTest extends SetUp {
    FeedPage feedPage;
    ProfilePage profilePage;

    String messageTemplate = "Hi {{first_name}} {{last_name}},\n\nI noticed you work at {{company}}. I'd love to connect and learn more about your work.";

    @DataProvider(name = "accounts")
    public Object[][] getAccountData() {
        return new Object[][] {
                { "account1", ConfigLoader.getEmail("account1"), ConfigLoader.getPassword("account1") }
                //{ "account2", ConfigLoader.getEmail("account2"), ConfigLoader.getPassword("account2") }
        };
    }

    @Test(dataProvider = "accounts")
    public void loginToLinkedInAccount(String accountKey,String username, String password)  {
        HomePage homePage = new HomePage(driver);
        SignIn signIn = homePage.goToSignIn();
        signIn.enterUsername(username);
        signIn.enterPassword(password);
        signIn.disableRememberMe();
        feedPage=signIn.clickOnSignIn();
        boolean isPresent = feedPage.isLogoPresent();
        Assert.assertTrue(isPresent, "LogoIcon is present");

    }
    @Test(dependsOnMethods = {"loginToLinkedInAccount"})
    public void sendConnectionRequest() throws Exception {
        List<Contact> contacts = ExcelHandler.getContactsFromExcel(2);
            for (Contact contact : contacts) {
                String profileUrl = contact.getLink();
                 profilePage = feedPage.goToProfile(profileUrl);
                Wait.addRandomDelay(2000,2000);
                System.out.println("Visited profile: " + profileUrl);
                profilePage.clickConnect();
                profilePage.sendPersonalizedMessage(messageTemplate.replace("{{first_name}}", contact.getFirstName()).replace("{{last_name}}",contact.getLastName()).replace("{{company}}",""));

            }
        }
    }

