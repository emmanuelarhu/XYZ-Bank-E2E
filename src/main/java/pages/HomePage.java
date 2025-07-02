package pages;

//import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
    private final WebDriver driver;

    // Page elements
    private final By customerLoginButton = By.xpath("//button[contains(text(),'Customer Login')]");
    private final By bankManagerLoginButton = By.xpath("//button[contains(text(),'Bank Manager Login')]");
    private final By homeButton = By.xpath("//button[contains(text(),'Home')]");
    private final By pageTitle = By.xpath("//strong[text()='XYZ Bank']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        System.out.println("🏠 HomePage initialized");
    }

    //@Step("Click Customer Login button")
    public void clickCustomerLogin() {
        try {
            WebElement customerButton = driver.findElement(customerLoginButton);
            customerButton.click();
            System.out.println("👤 Customer Login button clicked");
        } catch (Exception e) {
            System.out.println("❌ Could not click Customer Login: " + e.getMessage());
            throw e;
        }
    }

    //@Step("Click Bank Manager Login button")
    public void clickBankManagerLogin() {
        try {
            WebElement managerButton = driver.findElement(bankManagerLoginButton);
            managerButton.click();
            System.out.println("👨‍💼 Bank Manager Login button clicked");
        } catch (Exception e) {
            System.out.println("❌ Could not click Bank Manager Login: " + e.getMessage());
            throw e;
        }
    }

    //@Step("Click Home button")
    public void clickHome() {
        try {
            WebElement homeBtn = driver.findElement(homeButton);
            homeBtn.click();
            System.out.println("🏠 Home button clicked");
        } catch (Exception e) {
            System.out.println("❌ Could not click Home: " + e.getMessage());
        }
    }

    //@Step("Verify page elements are displayed")
    public boolean isCustomerLoginDisplayed() {
        try {
            return driver.findElement(customerLoginButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isBankManagerLoginDisplayed() {
        try {
            return driver.findElement(bankManagerLoginButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPageTitleDisplayed() {
        try {
            return driver.findElement(pageTitle).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    //@Step("Get page title")
    public String getPageTitle() {
        return driver.getTitle();
    }
}