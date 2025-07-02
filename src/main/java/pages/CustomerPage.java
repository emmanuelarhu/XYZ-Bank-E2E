package pages;

//import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;

public class CustomerPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Customer login elements
    private final By customerNameDropdown = By.id("userSelect");
    private final By loginButton = By.xpath("//button[contains(text(),'Login')]");

    // Dashboard elements
    private final By welcomeMessage = By.xpath("//span[@class='fontBig ng-binding']");
    private final By accountNumber = By.xpath("//strong[contains(text(),'Account Number')]/following-sibling::text()");
    private final By balance = By.xpath("//strong[contains(text(),'Balance')]/following-sibling::text()");
    private final By currency = By.xpath("//strong[contains(text(),'Currency')]/following-sibling::text()");
    private final By logoutButton = By.xpath("//button[contains(text(),'Logout')]");

    // Navigation tabs
    private final By transactionsTab = By.xpath("//button[contains(text(),'Transactions')]");
    private final By depositTab = By.xpath("//button[contains(text(),'Deposit')]");
    private final By withdrawalTab = By.xpath("//button[contains(text(),'Withdrawl')]");

    // Deposit elements
    private final By depositAmountField = By.xpath("//input[@placeholder='amount']");
    private final By depositButton = By.xpath("//button[@type='submit' and contains(text(),'Deposit')]");

    // Withdrawal elements
    private final By withdrawAmountField = By.xpath("//input[@placeholder='amount']");
    private final By withdrawButton = By.xpath("//button[@type='submit' and contains(text(),'Withdraw')]");

    // Transaction elements
    private final By transactionTable = By.xpath("//table");
    private final By resetButton = By.xpath("//button[contains(text(),'Reset')]");
    private final By startDateFilter = By.id("start");
    private final By endDateFilter = By.id("end");

    // Success/Error messages
    private final By successMessage = By.xpath("//span[contains(@class,'error') and contains(text(),'successful')]");
    private final By errorMessage = By.xpath("//span[contains(@class,'error')]");

    public CustomerPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        System.out.println("👤 CustomerPage initialized");
    }

    //@Step("Select customer from dropdown: {customerName}")
    public void selectCustomer(String customerName) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(customerNameDropdown));
            Select select = new Select(dropdown);
            select.selectByVisibleText(customerName);
            System.out.println("👤 Customer selected: " + customerName);
        } catch (Exception e) {
            System.out.println("❌ Could not select customer: " + e.getMessage());
            throw e;
        }
    }

    //@Step("Click login button")
    public void clickLogin() {
        try {
            WebElement button = driver.findElement(loginButton);
            button.click();
            System.out.println("🔑 Login button clicked");
        } catch (Exception e) {
            System.out.println("❌ Could not click login: " + e.getMessage());
            throw e;
        }
    }

    //@Step("Login as customer: {customerName}")
    public void loginAsCustomer(String customerName) {
        selectCustomer(customerName);
        clickLogin();
    }

    //@Step("Click logout button")
    public void clickLogout() {
        try {
            WebElement button = driver.findElement(logoutButton);
            button.click();
            System.out.println("🚪 Logout button clicked");
        } catch (Exception e) {
            System.out.println("❌ Could not click logout: " + e.getMessage());
            throw e;
        }
    }

    //@Step("Navigate to transactions tab")
    public void clickTransactionsTab() {
        try {
            WebElement tab = driver.findElement(transactionsTab);
            tab.click();
            System.out.println("📊 Transactions tab clicked");
        } catch (Exception e) {
            System.out.println("❌ Could not click transactions tab: " + e.getMessage());
            throw e;
        }
    }

    //@Step("Navigate to deposit tab")
    public void clickDepositTab() {
        try {
            WebElement tab = driver.findElement(depositTab);
            tab.click();
            System.out.println("💰 Deposit tab clicked");
        } catch (Exception e) {
            System.out.println("❌ Could not click deposit tab: " + e.getMessage());
            throw e;
        }
    }

    //@Step("Navigate to withdrawal tab")
    public void clickWithdrawalTab() {
        try {
            WebElement tab = driver.findElement(withdrawalTab);
            tab.click();
            System.out.println("💸 Withdrawal tab clicked");
        } catch (Exception e) {
            System.out.println("❌ Could not click withdrawal tab: " + e.getMessage());
            throw e;
        }
    }

    //@Step("Deposit amount: {amount}")
    public void depositAmount(String amount) {
        try {
            WebElement amountField = driver.findElement(depositAmountField);
            amountField.clear();
            amountField.sendKeys(amount);

            WebElement button = driver.findElement(depositButton);
            button.click();
            System.out.println("💰 Deposited amount: " + amount);
        } catch (Exception e) {
            System.out.println("❌ Could not deposit amount: " + e.getMessage());
            throw e;
        }
    }

    //@Step("Withdraw amount: {amount}")
    public void withdrawAmount(String amount) {
        try {
            WebElement amountField = driver.findElement(withdrawAmountField);
            amountField.clear();
            amountField.sendKeys(amount);

            WebElement button = driver.findElement(withdrawButton);
            button.click();
            System.out.println("💸 Withdrew amount: " + amount);
        } catch (Exception e) {
            System.out.println("❌ Could not withdraw amount: " + e.getMessage());
            throw e;
        }
    }

    //@Step("Click reset button in transactions")
    public void clickResetButton() {
        try {
            WebElement button = driver.findElement(resetButton);
            button.click();
            System.out.println("🔄 Reset button clicked");
        } catch (Exception e) {
            System.out.println("❌ Could not click reset button: " + e.getMessage());
            throw e;
        }
    }

    //@Step("Set date filter")
    public void setDateFilter(String startDate, String endDate) {
        try {
            if (!startDate.isEmpty()) {
                WebElement startField = driver.findElement(startDateFilter);
                startField.clear();
                startField.sendKeys(startDate);
            }

            if (!endDate.isEmpty()) {
                WebElement endField = driver.findElement(endDateFilter);
                endField.clear();
                endField.sendKeys(endDate);
            }
            System.out.println("📅 Date filter set: " + startDate + " to " + endDate);
        } catch (Exception e) {
            System.out.println("❌ Could not set date filter: " + e.getMessage());
            throw e;
        }
    }

    //@Step("Check if customer is logged in")
    public boolean isCustomerLoggedIn() {
        try {
            return driver.findElement(welcomeMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    //@Step("Check if logout button is visible")
    public boolean isLogoutButtonVisible() {
        try {
            return driver.findElement(logoutButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    //@Step("Check if success message is displayed")
    public boolean isSuccessMessageDisplayed() {
        try {
            return driver.findElement(successMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    //@Step("Check if error message is displayed")
    public boolean isErrorMessageDisplayed() {
        try {
            return driver.findElement(errorMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    //@Step("Get current balance")
    public String getCurrentBalance() {
        try {
            // Balance is in a complex structure, let's get the text near Balance
            String pageText = driver.getPageSource();
            // Extract balance from the page text - this is a simplified approach
            String balanceText = driver.findElement(By.xpath("//strong[contains(text(),'Balance')]/..")).getText();
            System.out.println("💰 Balance info: " + balanceText);
            return balanceText;
        } catch (Exception e) {
            System.out.println("❌ Could not get balance: " + e.getMessage());
            return "";
        }
    }

    //@Step("Check if date filters are cleared")
    public boolean areDateFiltersCleared() {
        try {
            WebElement startField = driver.findElement(startDateFilter);
            WebElement endField = driver.findElement(endDateFilter);

            boolean startCleared = startField.getAttribute("value").isEmpty();
            boolean endCleared = endField.getAttribute("value").isEmpty();

            System.out.println("📅 Start field cleared: " + startCleared + ", End field cleared: " + endCleared);
            return startCleared && endCleared;
        } catch (Exception e) {
            System.out.println("❌ Could not check date filters: " + e.getMessage());
            return false;
        }
    }

    //@Step("Navigate back using browser")
    public void navigateBack() {
        try {
            driver.navigate().back();
            System.out.println("⬅️ Navigated back using browser");
        } catch (Exception e) {
            System.out.println("❌ Could not navigate back: " + e.getMessage());
        }
    }

    //@Step("Check if can access dashboard after logout")
    public boolean canAccessDashboardAfterLogout() {
        try {
            // Try to find dashboard elements
            return driver.findElement(welcomeMessage).isDisplayed() ||
                    driver.findElement(depositTab).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}