    import org.testng.Assert;
    import org.testng.annotations.*;
    import pages.FeedPage;
    import pages.HomePage;
    import pages.SignIn;
    import utils.ConfigLoader;

    public class LoginTest extends SetUp{
        @DataProvider(name = "accounts")
        public Object[][] getAccountData() {
            return new Object[][] {
                    { "account1", ConfigLoader.getEmail("account1"), ConfigLoader.getPassword("account1") }
            };
        }

        @Test(dataProvider = "accounts",description = "The user login into their linkedin account")
        public void Login(String accountKey, String username, String password)  {
                HomePage homePage = new HomePage(driver);
                SignIn signIn = homePage.goToSignIn();
                signIn.enterUsername(username);
                signIn.enterPassword(password);
                signIn.disableRememberMe();
                FeedPage feedPage = signIn.clickOnSignIn();
                boolean isPresent = feedPage.isLogoPresent();
                Assert.assertTrue(isPresent, "LogoIcon is present");

                HomePage homePage1=feedPage.signOut();
                boolean isLoggedOut = homePage1.isSignInButtonPresent();  // Assume a method to verify the sign-in button is visible after logout
                Assert.assertTrue(isLoggedOut, "Logout failed for account: " + accountKey);
            }


        }

