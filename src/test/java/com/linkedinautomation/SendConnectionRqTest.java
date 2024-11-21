package com.linkedinautomation;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.FeedPage;
import pages.ProfilePage;
import pages.HomePage;
import pages.SignIn;
import reporting.ReportingManager;
import utils.ConfigLoader;
import utils.ConnectionLimiter;
import utils.Contact;
import utils.ExcelHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SendConnectionRqTest extends SetUp {
    FeedPage feedPage;
    ProfilePage profilePage;
    private String sharedAccountKey;
    private String sharedUsername;
    static ConnectionLimiter connectionLimiter = new ConnectionLimiter();


    String messageTemplate = "Hi {{first_name}} {{last_name}},\n\nI noticed you work at {{company}}. I'd love to connect and learn more about your work.";

    @DataProvider(name = "accounts")
    public Object[][] getAccountData() {
        return new Object[][]{
                //{"account1", ConfigLoader.getEmail("account1"), ConfigLoader.getPassword("account1")}
                { "account2", ConfigLoader.getEmail("account2"), ConfigLoader.getPassword("account2") }
        };
    }

    @Test(dataProvider = "accounts",priority = 1,description = "The user login into their linkedin account")
    public void loginToLinkedInAccount(String accountKey, String username, String password) {
        sharedAccountKey=accountKey;
        sharedUsername =username;
        HomePage homePage = new HomePage(SetUp.driver);
        SignIn signIn = homePage.goToSignIn();
        signIn.enterUsername(username);
        signIn.enterPassword(password);
        signIn.disableRememberMe();
        feedPage = signIn.clickOnSignIn();
        boolean isPresent = feedPage.isLogoPresent();
        Assert.assertTrue(isPresent, "LogoIcon is present");

    }

    @Test(dependsOnMethods = {"loginToLinkedInAccount"},description = "The user sends a request to connect with or without note")
    public void sendConnectionRequest()  {
        try {
            int profilesVisited = 0;

            int limit = setDailyConnectionLimits();

            List<Contact> contacts = ExcelHandler.getContactsFromExcel(2);
            List<String[]> profileData = new ArrayList<>();
            for (Contact contact : contacts) {
                String profileUrl = contact.getLink();
                profilePage = feedPage.goToProfile(profileUrl);

                String[] columnNames = {"Connection Sent Time", "Profile Names", "Status"};

                profilesVisited++;
                profilePage.clickConnect();
                /*send without invite note** is for the basic user has reached a limit to send note*/
                //profilePage.sendWithoutMessage();

                /*sending an invite with a note is for premium user to send unlimited note**/
                 profilePage.sendPersonalizedMessage(messageTemplate.replace("{{first_name}}", contact.getFirstName()).replace("{{last_name}}", contact.getLastName()).replace("{{company}}", contact.getCompany()));

                boolean isPresent = profilePage.checkPendingStatus();
                Assert.assertTrue(isPresent, "Pending Status is present");
                String status = profilePage.getConnectionStatus();
                String name = contact.getFirstName() + " " + contact.getLastName();
                String pattern = "dd-M-yyyy'T'HH.mm.ss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String date = simpleDateFormat.format(new Date());
                profileData.add(new String[]{date, name, status});
                String[][] data = new String[profileData.size()][];
                for (int i = 0; i < profileData.size(); i++) {
                    data[i] = profileData.get(i);
                }
                SetUp.getTest().info("Visited profile of " + contact.getFirstName() + "\t" + contact.getLastName());
                SetUp.getTest().pass("Connection request from "+sharedAccountKey+" sent to: " + contact.getFirstName());
                SetUp.getTest().info(ReportingManager.createTable(columnNames.length, profilesVisited, columnNames, data));

            }

            SetUp.getTest().info("Total profiles visited for " +sharedAccountKey+" are "+ profilesVisited);
            SetUp.getTest().pass("Completed sending connection requests for account "+sharedAccountKey);


        } catch (Exception e) {
            String screenshotPath = SetUp.captureScreenshotBase64("sendConnectionRequest_Failure");

            // Log the failure message and attach the screenshot
            SetUp.getTest().fail("Failed to send request to profile. Error: " + e.getMessage())
                    .addScreenCaptureFromBase64String(screenshotPath);
            throw new RuntimeException("Connection request process failed for account: " + sharedAccountKey, e);
        }



        feedPage.signOut();
    }
    public static int setDailyConnectionLimits(){
        int limit = connectionLimiter.sendConnectionRequest("account1");
        for (int i = 1; i <= connectionLimiter.MAX_CONNECTIONS_PER_DAY; ++i) {
            if (limit != -1) {
                limit = connectionLimiter.sendConnectionRequest("userEmail");
            } else {
                System.out.println("Daily limit reached.");
                break; // Exit loop if the limit is reached
            }
        }
        return limit;

    }

}

