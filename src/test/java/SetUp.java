import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;
import reporting.ReportingManager;

public class SetUp {
    public static String LINKEDIN_URL="https://www.linkedin.com/";
    public static WebDriver driver;
    protected static Proxy proxy;
     ITestResult result;
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
    @AfterMethod
    public void getResults(ITestResult result) {
        // Log test result
        if (result.getStatus() == ITestResult.FAILURE) {
            getTest().fail("Test failed: " + result.getThrowable());
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
}
