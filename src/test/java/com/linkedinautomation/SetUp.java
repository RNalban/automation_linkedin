package com.linkedinautomation;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;
import reporting.ReportingManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class SetUp {
    public static String LINKEDIN_URL="https://www.linkedin.com/";
    public static WebDriver driver;
    protected static Proxy proxy;
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    @BeforeSuite
    public void initializeExtentReports() {
        extent = ReportingManager.initializeReport("AutomationReport.html");
    }
    @BeforeClass
    public void navigateToLinkedIn(){
        proxy=new Proxy();
        proxy.setHttpProxy("proxyAddress:port");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setCapability("proxy", proxy);
       WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.get(LINKEDIN_URL);

    }
    @BeforeMethod
    public void createExtentTest(ITestResult result){
        String testName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();
        ExtentTest extentTest = extent.createTest(testName, description);
        test.set(extentTest);

    }
    @AfterMethod(alwaysRun = true)
    public void getResults(ITestResult result) {
        // Log test result
        if (result.getStatus() == ITestResult.FAILURE) {
            getTest().fail("Test failed");

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            getTest().pass("Test passed successfully.");
        } else if (result.getStatus() == ITestResult.SKIP) {
            getTest().skip("Test skipped: " + result.getThrowable());
        }
    }


    @AfterSuite
    public void teardownAndFinalizeReport(){
        if (driver != null) {
            driver.quit();
            getTest().info("WebDriver closed successfully.");
        }
       ReportingManager.finalizeReport();
    }
    public static ExtentTest getTest() {
        return test.get();
    }
    public static String captureScreenshotBase64(String screenshotName) {
        // Create a timestamp for uniqueness
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String finalScreenshotName = screenshotName + "_" + timeStamp + ".png";

        // Take the screenshot
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            // Convert screenshot to Base64 string
            byte[] fileContent = FileUtils.readFileToByteArray(screenshotFile);
            String base64Screenshot = Base64.getEncoder().encodeToString(fileContent);

            // Log the Base64 screenshot in the extent report
           getTest().addScreenCaptureFromBase64String(base64Screenshot, finalScreenshotName);

            return base64Screenshot;  // Return the Base64 string
        } catch (IOException e) {
            e.printStackTrace();
            return null;  // If screenshot fails
        }
    }

}
