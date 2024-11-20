package reporting;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportingManager {
        private static ExtentReports extent;
        private static ExtentSparkReporter  htmlReporter;
    // Initialize the report
        public static ExtentReports initializeReport(String reportName) {
            String pattern = "dd-M-yyyy'T'hh.mm";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(new Date());

            htmlReporter = new ExtentSparkReporter (System.getProperty("user.dir") + "/src/test/reports/"+date+reportName);
            htmlReporter.config().setDocumentTitle("LinkedIn Automation Report");
            htmlReporter.config().setReportName("Automation Test Report");
            htmlReporter.config().setTheme(Theme.STANDARD);

            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);

            // Add system information
            extent.setSystemInfo("Environment", "Chrome");
            extent.setSystemInfo("Tester", "Rehmat Nalban");
            return extent;
        }

        public static void finalizeReport() {
            if (extent != null) {
                extent.flush();
            }
        }
    }

