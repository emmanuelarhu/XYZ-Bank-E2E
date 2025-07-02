package base;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected final String BASE_URL = "https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login";

    @BeforeEach
    public void setUp() {
        System.out.println("🚀 Starting XYZ Bank test setup...");

        // Get current working directory safely
        String currentDir = System.getProperty("user.dir");
        if (currentDir == null) {
            currentDir = ".";
        }

        // Set ChromeDriver path
        String chromeDriverPath = currentDir + File.separator +
                "resources" + File.separator + "chromedriver.exe";

        System.out.println("🔍 Looking for ChromeDriver at: " + chromeDriverPath);

        // Check if ChromeDriver exists locally, otherwise use system PATH (for CI)
        File chromeDriverFile = new File(chromeDriverPath);
        if (chromeDriverFile.exists()) {
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            System.out.println("✅ Using local ChromeDriver");
        } else if (System.getenv("CI") != null) {
            System.out.println("🤖 CI environment detected - using system ChromeDriver");
        } else {
            System.err.println("❌ ChromeDriver not found at: " + chromeDriverPath);
            throw new RuntimeException("ChromeDriver executable not found at: " + chromeDriverPath);
        }

        // Configure Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1366,768");
        options.addArguments("--remote-allow-origins=*");
//        options.addArguments("--disable-blink-features=AutomationControlled");
//        options.addExperimentalOption("useAutomationExtension", false);
//        options.addExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        // Add headless mode for CI environment
        String headless = System.getProperty("headless");
        if ("true".equals(headless) || System.getenv("CI") != null) {
            options.addArguments("--headless");
            System.out.println("🤖 Running in headless mode (CI environment)");
        }

        try {
            // Initialize driver
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();

            System.out.println("✅ Browser setup completed successfully");
            String chromeVersion = ((ChromeDriver) driver).getCapabilities().getCapability("browserVersion").toString();
            System.out.println("🌐 Chrome version: " + chromeVersion);

            // Add environment info to Allure
            Allure.addAttachment("Environment", "text/plain",
                    "Browser: Chrome " + chromeVersion + "\n" +
                            "Base URL: " + BASE_URL + "\n" +
                            "Headless: " + ("true".equals(headless) || System.getenv("CI") != null));

        } catch (Exception e) {
            System.err.println("❌ Failed to initialize Chrome driver: " + e.getMessage());
            throw new RuntimeException("Browser setup failed: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            try {
                System.out.println("🔄 Closing browser...");
                driver.quit();
                System.out.println("✅ Browser closed successfully");
            } catch (Exception e) {
                System.err.println("⚠️ Error while closing browser: " + e.getMessage());
            }
        }
    }

    protected void navigateToHomePage() {
        System.out.println("🌐 Navigating to XYZ Bank...");
        try {
            driver.get(BASE_URL);
            System.out.println("📍 Navigated to: " + BASE_URL);
            System.out.println("📄 Page title: " + driver.getTitle());

            // Add URL to Allure report
            Allure.addAttachment("Current URL", driver.getCurrentUrl());
        } catch (Exception e) {
            System.err.println("❌ Failed to navigate to XYZ Bank: " + e.getMessage());
            takeScreenshot("navigation_failed");
            throw e;
        }
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] takeScreenshot(String screenshotName) {
        try {
            System.out.println("📸 Taking screenshot: " + screenshotName);
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            System.err.println("❌ Failed to take screenshot: " + e.getMessage());
            return new byte[0];
        }
    }

    protected void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}