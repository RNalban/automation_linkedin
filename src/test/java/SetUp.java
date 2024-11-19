import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.ConfigLoader;
import utils.Paths;
import utils.Wait;

public class SetUp {
    public static String LINKEDIN_URL="https://www.linkedin.com/";
    public static WebDriver driver;
    protected static Proxy proxy;
    @BeforeMethod
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



    @AfterSuite
    public void teardown(){
        if(driver!=null)
            driver.quit();
    }
}
