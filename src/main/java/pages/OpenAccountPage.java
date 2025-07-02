package pages;

//import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import java.util.List;

public class OpenAccountPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Page elements
    private final By openAccountTab = By.xpath("//button[contains(text(),'Open Account')]");
    private final By customersTab = By.xpath("//button[contains(text(),'Customers')]");
    private final By addCustomerTab = By.xpath("//button[contains(text(),'Add Customer')]");

    // Form elements
    private final By customerDropdown = By.id("userSelect");
    private final By currencyDropdown = By.id("currency");
    private final By processButton = By.xpath("//button[@type='submit' and contains(text(),'Process')]");

    // Customer table elements (for verification)
    private final By customerTable = By.xpath("//table");
    private final By customerRows = By.xpath("//table//tr[td]");

    public OpenAccountPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        System.out.println("🏦 OpenAccountPage initialized");
    }

//    @Step("Click Open Account tab")
    public void clickOpenAccountTab() {
        try {
            WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(openAccountTab));
            tab.click();
            System.out.println("🏦 Open Account tab clicked");
        } catch (Exception e) {
            System.out.println("❌ Could not click Open Account tab: " + e.getMessage());
            throw e;
        }
    }

//    @Step("Click Customers tab")
    public void clickCustomersTab() {
        try {
            WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(customersTab));
            tab.click();
            System.out.println("👥 Customers tab clicked");
            // Wait for table to load
            wait.until(ExpectedConditions.visibilityOfElementLocated(customerTable));
        } catch (Exception e) {
            System.out.println("❌ Could not click Customers tab: " + e.getMessage());
            throw e;
        }
    }

//    @Step("Select customer: {customerName}")
    public void selectCustomer(String customerName) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(customerDropdown));
            Select select = new Select(dropdown);
            select.selectByVisibleText(customerName);
            System.out.println("👤 Selected customer: " + customerName);
        } catch (Exception e) {
            System.out.println("❌ Could not select customer: " + e.getMessage());
            throw e;
        }
    }

//    @Step("Select currency: {currency}")
    public void selectCurrency(String currency) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(currencyDropdown));
            Select select = new Select(dropdown);
            select.selectByVisibleText(currency);
            System.out.println("💰 Selected currency: " + currency);
        } catch (Exception e) {
            System.out.println("❌ Could not select currency: " + e.getMessage());
            throw e;
        }
    }

//    @Step("Click Process button")
    public void clickProcessButton() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(processButton));
            button.click();
            System.out.println("⚙️ Process button clicked");
        } catch (Exception e) {
            System.out.println("❌ Could not click Process button: " + e.getMessage());
            throw e;
        }
    }

//    @Step("Open account for customer: {customerName} with currency: {currency}")
    public void openAccount(String customerName, String currency) {
        selectCustomer(customerName);
        selectCurrency(currency);
        clickProcessButton();
    }

//    @Step("Check if JavaScript alert is present")
    public boolean isAlertPresent() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            System.out.println("🔔 JavaScript alert is present");
            return true;
        } catch (Exception e) {
            System.out.println("ℹ️ No JavaScript alert present");
            return false;
        }
    }

//    @Step("Get JavaScript alert text")
    public String getAlertText() {
        try {
            if (isAlertPresent()) {
                String alertText = driver.switchTo().alert().getText();
                System.out.println("🔔 Alert text: " + alertText);
                return alertText;
            }
            return "";
        } catch (Exception e) {
            System.out.println("❌ Could not get alert text: " + e.getMessage());
            return "";
        }
    }

//    @Step("Accept JavaScript alert if present")
    public void acceptAlertIfPresent() {
        try {
            if (isAlertPresent()) {
                String alertText = driver.switchTo().alert().getText();
                System.out.println("🔔 Alert text: " + alertText);
                driver.switchTo().alert().accept();
                System.out.println("✅ Alert accepted");
            }
        } catch (Exception e) {
            System.out.println("ℹ️ No alert present to accept");
        }
    }

//    @Step("Wait for account creation alert")
    public String waitForAccountCreationAlert(int timeoutSeconds) {
        try {
            WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            alertWait.until(ExpectedConditions.alertIsPresent());
            String alertText = driver.switchTo().alert().getText();
            System.out.println("🔔 Account creation alert: " + alertText);
            return alertText;
        } catch (Exception e) {
            System.out.println("ℹ️ No account creation alert within " + timeoutSeconds + " seconds");
            return "";
        }
    }

//    @Step("Extract account number from alert text")
    public String extractAccountNumber(String alertText) {
        try {
            // Alert text format: "Account created successfully with account Number :1016"
            if (alertText.contains("account Number :")) {
                String[] parts = alertText.split("account Number :");
                if (parts.length > 1) {
                    String accountNumber = parts[1].trim();
                    System.out.println("🏦 Extracted account number: " + accountNumber);
                    return accountNumber;
                }
            }
            System.out.println("❌ Could not extract account number from: " + alertText);
            return "";
        } catch (Exception e) {
            System.out.println("❌ Error extracting account number: " + e.getMessage());
            return "";
        }
    }

//    @Step("Get list of available customers in dropdown")
    public List<String> getAvailableCustomers() {
        try {
            WebElement dropdown = driver.findElement(customerDropdown);
            Select select = new Select(dropdown);
            List<WebElement> options = select.getOptions();

            List<String> customerNames = options.stream()
                    .map(WebElement::getText)
                    .filter(text -> !text.trim().isEmpty() && !text.contains("---"))
                    .toList();

            System.out.println("👥 Available customers: " + customerNames);
            return customerNames;
        } catch (Exception e) {
            System.out.println("❌ Could not get available customers: " + e.getMessage());
            return List.of();
        }
    }

//    @Step("Get list of available currencies in dropdown")
    public List<String> getAvailableCurrencies() {
        try {
            WebElement dropdown = driver.findElement(currencyDropdown);
            Select select = new Select(dropdown);
            List<WebElement> options = select.getOptions();

            List<String> currencies = options.stream()
                    .map(WebElement::getText)
                    .filter(text -> !text.trim().isEmpty() && !text.contains("---"))
                    .toList();

            System.out.println("💰 Available currencies: " + currencies);
            return currencies;
        } catch (Exception e) {
            System.out.println("❌ Could not get available currencies: " + e.getMessage());
            return List.of();
        }
    }

//    @Step("Verify customer has account with currency: {firstName} {lastName} - {currency}")
    public boolean verifyCustomerHasAccount(String firstName, String lastName, String currency) {
        try {
            // Navigate to Customers tab to check account details
            clickCustomersTab();
            Thread.sleep(2000);

            // Get all customer rows
            List<WebElement> rows = driver.findElements(customerRows);
            System.out.println("👥 Checking " + rows.size() + " customers for account information");

            // Search for the customer and check if they have an account
            for (WebElement row : rows) {
                String rowText = row.getText();
                System.out.println("🔍 Checking row: " + rowText);

                if (rowText.contains(firstName) && rowText.contains(lastName)) {
                    System.out.println("👤 Found customer: " + firstName + " " + lastName);

                    // Check if the row contains account numbers (indicating accounts exist)
                    boolean hasAccountNumbers = rowText.matches(".*\\d{4}.*"); // Look for 4-digit account numbers
                    if (hasAccountNumbers) {
                        System.out.println("✅ Customer has account(s): " + rowText);
                        return true;
                    } else {
                        System.out.println("❌ Customer found but no accounts visible");
                        return false;
                    }
                }
            }

            System.out.println("❌ Customer not found: " + firstName + " " + lastName);
            return false;

        } catch (Exception e) {
            System.out.println("❌ Could not verify customer account: " + e.getMessage());
            return false;
        }
    }

//    @Step("Complete account creation workflow with verification")
    public String createAccountAndVerify(String customerName, String currency) {
        try {
            // Step 1: Open account
            openAccount(customerName, currency);

            // Step 2: Wait for success alert
            String alertText = waitForAccountCreationAlert(5);

            if (!alertText.isEmpty()) {
                boolean isSuccess = alertText.toLowerCase().contains("successfully") &&
                        alertText.toLowerCase().contains("account");

                if (isSuccess) {
                    // Step 3: Extract account number
                    String accountNumber = extractAccountNumber(alertText);
                    acceptAlertIfPresent();

                    // Step 4: Verify account exists in customer list
                    String[] nameParts = customerName.split(" ");
                    if (nameParts.length >= 2) {
                        boolean accountExists = verifyCustomerHasAccount(nameParts[0], nameParts[1], currency);
                        if (accountExists) {
                            System.out.println("✅ Account creation verified: " + accountNumber);
                            return accountNumber;
                        }
                    }
                }

                acceptAlertIfPresent();
                System.out.println("❌ Account creation failed or not verified: " + alertText);
                return "";
            } else {
                System.out.println("❌ No success alert received for account creation");
                return "";
            }

        } catch (Exception e) {
            System.out.println("❌ Account creation workflow failed: " + e.getMessage());
            return "";
        }
    }

//    @Step("Verify form elements are displayed")
    public boolean areFormElementsDisplayed() {
        try {
            boolean customerDropdownVisible = driver.findElement(customerDropdown).isDisplayed();
            boolean currencyDropdownVisible = driver.findElement(currencyDropdown).isDisplayed();
            boolean processButtonVisible = driver.findElement(processButton).isDisplayed();

            boolean allVisible = customerDropdownVisible && currencyDropdownVisible && processButtonVisible;
            System.out.println("📋 All Open Account form elements visible: " + allVisible);
            return allVisible;
        } catch (Exception e) {
            System.out.println("❌ Form elements check failed: " + e.getMessage());
            return false;
        }
    }
}