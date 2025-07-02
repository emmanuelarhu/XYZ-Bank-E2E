package pages;

//import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class AddCustomerPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Page elements
    private final By addCustomerTab = By.xpath("//button[contains(text(),'Add Customer')]");
    private final By openAccountTab = By.xpath("//button[contains(text(),'Open Account')]");
    private final By customersTab = By.xpath("//button[contains(text(),'Customers')]");

    // Form elements
    private final By firstNameField = By.xpath("//input[@placeholder='First Name']");
    private final By lastNameField = By.xpath("//input[@placeholder='Last Name']");
    private final By postCodeField = By.xpath("//input[@placeholder='Post Code']");
    private final By addCustomerButton = By.xpath("//button[@type='submit' and contains(text(),'Add Customer')]");

    // Validation elements
    private final By validationIcon = By.xpath("//span[@style='color:red']");

    // Customer table elements (for verification)
    private final By customerTable = By.xpath("//table");
    private final By customerRows = By.xpath("//table//tr[td]"); // All rows with data
    private final By searchField = By.xpath("//input[@placeholder='Search Customer']");

    public AddCustomerPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        System.out.println("📝 AddCustomerPage initialized");
    }

//    @Step("Click Add Customer tab")
    public void clickAddCustomerTab() {
        try {
            WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(addCustomerTab));
            tab.click();
            System.out.println("📝 Add Customer tab clicked");
        } catch (Exception e) {
            System.out.println("❌ Could not click Add Customer tab: " + e.getMessage());
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

//    @Step("Enter first name: {firstName}")
    public void enterFirstName(String firstName) {
        try {
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
            field.clear();
            field.sendKeys(firstName);
            System.out.println("📝 First name entered: " + firstName);
        } catch (Exception e) {
            System.out.println("❌ Could not enter first name: " + e.getMessage());
            throw e;
        }
    }

//    @Step("Enter last name: {lastName}")
    public void enterLastName(String lastName) {
        try {
            WebElement field = driver.findElement(lastNameField);
            field.clear();
            field.sendKeys(lastName);
            System.out.println("📝 Last name entered: " + lastName);
        } catch (Exception e) {
            System.out.println("❌ Could not enter last name: " + e.getMessage());
            throw e;
        }
    }

//    @Step("Enter postal code: {postCode}")
    public void enterPostCode(String postCode) {
        try {
            WebElement field = driver.findElement(postCodeField);
            field.clear();
            field.sendKeys(postCode);
            System.out.println("📮 Post code entered: " + postCode);
        } catch (Exception e) {
            System.out.println("❌ Could not enter post code: " + e.getMessage());
            throw e;
        }
    }

//    @Step("Click Add Customer button")
    public void clickAddCustomerButton() {
        try {
            WebElement button = driver.findElement(addCustomerButton);
            button.click();
            System.out.println("✅ Add Customer button clicked");
        } catch (Exception e) {
            System.out.println("❌ Could not click Add Customer button: " + e.getMessage());
            throw e;
        }
    }

//    @Step("Add customer with details: {firstName}, {lastName}, {postCode}")
    public void addCustomer(String firstName, String lastName, String postCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostCode(postCode);
        clickAddCustomerButton();
    }

//    @Step("Check if validation error icon is displayed")
    public boolean isValidationErrorDisplayed() {
        try {
            // Wait briefly for validation to appear
            Thread.sleep(1000);
            WebElement errorIcon = driver.findElement(validationIcon);
            boolean isDisplayed = errorIcon.isDisplayed();
            System.out.println("⚠️ Validation error icon displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            System.out.println("ℹ️ No validation error found");
            return false;
        }
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

//    @Step("Wait for and handle any alert")
    public String waitForAlertAndGetText(int timeoutSeconds) {
        try {
            WebDriverWait alertWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            alertWait.until(ExpectedConditions.alertIsPresent());
            String alertText = driver.switchTo().alert().getText();
            System.out.println("🔔 Alert appeared with text: " + alertText);
            return alertText;
        } catch (Exception e) {
            System.out.println("ℹ️ No alert appeared within " + timeoutSeconds + " seconds");
            return "";
        }
    }

//    @Step("Verify customer exists in customer list: {firstName} {lastName}")
    public boolean verifyCustomerInList(String firstName, String lastName) {
        try {
            // Navigate to Customers tab first
            clickCustomersTab();

            // Wait for table to load
            Thread.sleep(2000);

            // Get all customer rows
            List<WebElement> rows = driver.findElements(customerRows);
            System.out.println("👥 Found " + rows.size() + " customers in the table");

            // Search for the customer
            for (WebElement row : rows) {
                String rowText = row.getText();
                System.out.println("🔍 Checking row: " + rowText);

                if (rowText.contains(firstName) && rowText.contains(lastName)) {
                    System.out.println("✅ Customer found: " + firstName + " " + lastName);
                    return true;
                }
            }

            System.out.println("❌ Customer NOT found: " + firstName + " " + lastName);
            return false;

        } catch (Exception e) {
            System.out.println("❌ Could not verify customer in list: " + e.getMessage());
            return false;
        }
    }

//    @Step("Search for customer: {searchTerm}")
    public void searchCustomer(String searchTerm) {
        try {
            WebElement search = driver.findElement(searchField);
            search.clear();
            search.sendKeys(searchTerm);
            System.out.println("🔍 Searching for: " + searchTerm);
            Thread.sleep(1000); // Wait for search to filter
        } catch (Exception e) {
            System.out.println("❌ Could not search for customer: " + e.getMessage());
        }
    }

//    @Step("Get total number of customers in table")
    public int getCustomerCount() {
        try {
            List<WebElement> rows = driver.findElements(customerRows);
            int count = rows.size();
            System.out.println("👥 Total customers in table: " + count);
            return count;
        } catch (Exception e) {
            System.out.println("❌ Could not count customers: " + e.getMessage());
            return 0;
        }
    }

//    @Step("Verify form elements are displayed")
    public boolean areFormElementsDisplayed() {
        try {
            boolean firstNameVisible = driver.findElement(firstNameField).isDisplayed();
            boolean lastNameVisible = driver.findElement(lastNameField).isDisplayed();
            boolean postCodeVisible = driver.findElement(postCodeField).isDisplayed();
            boolean buttonVisible = driver.findElement(addCustomerButton).isDisplayed();

            boolean allVisible = firstNameVisible && lastNameVisible && postCodeVisible && buttonVisible;
            System.out.println("📋 All form elements visible: " + allVisible);
            return allVisible;
        } catch (Exception e) {
            System.out.println("❌ Form elements check failed: " + e.getMessage());
            return false;
        }
    }

//    @Step("Complete customer creation workflow with verification")
    public boolean addCustomerAndVerify(String firstName, String lastName, String postCode) {
        try {
            // Step 1: Add customer
            addCustomer(firstName, lastName, postCode);

            // Step 2: Wait for alert and handle it
            String alertText = waitForAlertAndGetText(5);
            if (!alertText.isEmpty()) {
                boolean isSuccess = alertText.toLowerCase().contains("successfully") ||
                        alertText.toLowerCase().contains("added");
                acceptAlertIfPresent();

                if (isSuccess) {
                    // Step 3: Verify customer appears in customer list
                    boolean customerExists = verifyCustomerInList(firstName, lastName);
                    return customerExists;
                } else {
                    System.out.println("❌ Alert indicates failure: " + alertText);
                    return false;
                }
            } else {
                System.out.println("❌ No alert appeared - checking for validation errors");
                return !isValidationErrorDisplayed();
            }

        } catch (Exception e) {
            System.out.println("❌ Customer creation workflow failed: " + e.getMessage());
            return false;
        }
    }
}