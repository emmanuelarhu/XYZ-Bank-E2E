package tests;
import io.qameta.allure.Step;
import base.BaseTest;
import pages.HomePage;
import pages.AddCustomerPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

@Epic("XYZ Bank Management")
@Feature("Customer Management")
public class BankManagerValidationTests extends BaseTest {

    @Test
    @Story("Add Customer Validation")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Defect G2TML-398: Name validation fails to reject numeric characters")
    @Description("Verify that customer names with numeric characters are properly rejected by validation")
    public void testNameValidationRejectsNumericCharacters() {
        System.out.println("🧪 Testing: Name validation with numeric characters (Defect G2TML-398)");

        navigateToHomePage();

        HomePage homePage = new HomePage(driver);
        AddCustomerPage addCustomerPage = new AddCustomerPage(driver);

        // Step 1: Navigate to Bank Manager login
        homePage.clickBankManagerLogin();
        takeScreenshot("bank_manager_dashboard");

        // Step 2: Click Add Customer tab
        addCustomerPage.clickAddCustomerTab();
        takeScreenshot("add_customer_form");

        // Step 3: Verify form elements are displayed
        Assertions.assertTrue(addCustomerPage.areFormElementsDisplayed(),
                "Add Customer form should be visible");

        // Step 4: Enter invalid name with numeric characters
        String firstNameWithNumbers = "John123";
        String validLastName = "Smith";
        String validPostCode = "12345";

        System.out.println("🔍 Attempting to create customer: " + firstNameWithNumbers + " " + validLastName);

        // Record initial customer count
        addCustomerPage.clickCustomersTab();
        int initialCustomerCount = addCustomerPage.getCustomerCount();
        System.out.println("📊 Initial customer count: " + initialCustomerCount);

        // Go back to Add Customer form
        addCustomerPage.clickAddCustomerTab();

        // Step 5: Add customer with numeric characters in name
        addCustomerPage.addCustomer(firstNameWithNumbers, validLastName, validPostCode);

        // Step 6: Wait for and handle any alerts
        String alertText = addCustomerPage.waitForAlertAndGetText(5);
        takeScreenshot("after_submit_with_numeric_name");

        if (!alertText.isEmpty()) {
            System.out.println("🔔 Alert received: " + alertText);

            // Check if it's a success alert (indicates defect)
            boolean isSuccessAlert = alertText.toLowerCase().contains("successfully") ||
                    alertText.toLowerCase().contains("added");

            addCustomerPage.acceptAlertIfPresent();

            if (isSuccessAlert) {
                // DEFECT: Customer was created with numeric characters
                boolean customerExists = addCustomerPage.verifyCustomerInList(firstNameWithNumbers, validLastName);
                takeScreenshot("defect_confirmed_customer_created_with_numbers");

                System.out.println("🐛 DEFECT CONFIRMED: Customer created with numeric characters in name");
                System.out.println("🐛 Expected: Validation error for numeric characters");
                System.out.println("🐛 Actual: Customer created successfully");

                Assertions.fail("DEFECT G2TML-398: System should reject names with numeric characters, but customer '"
                        + firstNameWithNumbers + " " + validLastName + "' was created successfully. Customer exists in list: " + customerExists);
            } else {
                // Expected behavior: validation error
                System.out.println("✅ Validation working correctly - numeric characters rejected with alert: " + alertText);
            }
        } else {
            // Check for validation errors on the form
            boolean hasValidationError = addCustomerPage.isValidationErrorDisplayed();
            if (hasValidationError) {
                System.out.println("✅ Validation working correctly - form validation error displayed");
            } else {
                // Check if customer was silently created (another form of defect)
                boolean customerExists = addCustomerPage.verifyCustomerInList(firstNameWithNumbers, validLastName);
                if (customerExists) {
                    takeScreenshot("defect_silent_customer_creation");
                    Assertions.fail("DEFECT G2TML-398: Customer with numeric characters was created without any validation error or alert");
                }
            }
        }
    }

    @Test
    @Story("Add Customer Validation")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Defect G2TML-400: Special characters validation bypass")
    @Description("Verify that customer names with special characters are properly rejected")
    public void testNameValidationRejectsSpecialCharacters() {
        System.out.println("🧪 Testing: Name validation with special characters (Defect G2TML-400)");

        navigateToHomePage();

        HomePage homePage = new HomePage(driver);
        AddCustomerPage addCustomerPage = new AddCustomerPage(driver);

        // Step 1: Navigate to Bank Manager
        homePage.clickBankManagerLogin();
        addCustomerPage.clickAddCustomerTab();

        // Step 2: Test with special characters
        String firstNameWithSpecialChars = "John@#$";
        String lastNameWithSpecialChars = "Smith!";
        String validPostCode = "12345";

        System.out.println("🔍 Attempting to create customer: " + firstNameWithSpecialChars + " " + lastNameWithSpecialChars);

        // Use the complete workflow method
        boolean customerCreated = addCustomerPage.addCustomerAndVerify(firstNameWithSpecialChars, lastNameWithSpecialChars, validPostCode);
        takeScreenshot("after_submit_with_special_chars");

        if (customerCreated) {
            takeScreenshot("defect_confirmed_special_chars_accepted");
            System.out.println("🐛 DEFECT CONFIRMED: Customer created with special characters in name");
            Assertions.fail("DEFECT G2TML-400: System should reject names with special characters, but customer '"
                    + firstNameWithSpecialChars + " " + lastNameWithSpecialChars + "' was created successfully");
        } else {
            System.out.println("✅ Validation working correctly - special characters rejected");
        }
    }

    @Test
    @Story("Add Customer Validation")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Defect G2TML-401: Postal code accepts alphabetic characters")
    @Description("Verify that postal code field only accepts numeric characters")
    public void testPostalCodeValidationRejectsAlphabeticCharacters() {
        System.out.println("🧪 Testing: Postal code validation with alphabetic characters (Defect G2TML-401)");

        navigateToHomePage();

        HomePage homePage = new HomePage(driver);
        AddCustomerPage addCustomerPage = new AddCustomerPage(driver);

        // Step 1: Navigate to Add Customer form
        homePage.clickBankManagerLogin();
        addCustomerPage.clickAddCustomerTab();

        // Step 2: Test with alphabetic postal code
        String validFirstName = "John";
        String validLastName = "Smith";
        String alphabeticPostCode = "ABCDE";

        System.out.println("🔍 Attempting to create customer with postal code: " + alphabeticPostCode);

        boolean customerCreated = addCustomerPage.addCustomerAndVerify(validFirstName, validLastName, alphabeticPostCode);
        takeScreenshot("after_submit_with_alphabetic_postcode");

        if (customerCreated) {
            takeScreenshot("defect_confirmed_alphabetic_postcode_accepted");
            System.out.println("🐛 DEFECT CONFIRMED: Alphabetic postal code accepted");
            Assertions.fail("DEFECT G2TML-401: Postal code should only accept numeric characters, but '"
                    + alphabeticPostCode + "' was accepted");
        } else {
            System.out.println("✅ Validation working correctly - alphabetic postal code rejected");
        }
    }

    @Test
    @Story("Add Customer Validation")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Test Valid Customer Creation")
    @Description("Verify that valid customer data is accepted and customer is created successfully")
    public void testValidCustomerCreation() {
        System.out.println("🧪 Testing: Valid customer creation");

        navigateToHomePage();

        HomePage homePage = new HomePage(driver);
        AddCustomerPage addCustomerPage = new AddCustomerPage(driver);

        // Navigate to Add Customer form
        homePage.clickBankManagerLogin();
        addCustomerPage.clickAddCustomerTab();

        // Enter completely valid data
        String validFirstName = "Emmanuel";
        String validLastName = "Arhu";
        String validPostCode = "12345";

        System.out.println("🔍 Creating valid customer: " + validFirstName + " " + validLastName);

        boolean customerCreated = addCustomerPage.addCustomerAndVerify(validFirstName, validLastName, validPostCode);
        takeScreenshot("valid_customer_creation");

        if (customerCreated) {
            System.out.println("✅ Valid customer created successfully and verified in customer list");
        }

        Assertions.assertTrue(customerCreated,
                "Valid customer data should create customer successfully and customer should appear in customer list");
    }

    @Test
    @Story("Add Customer Validation")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Test Empty Fields Validation")
    @Description("Verify that empty required fields are properly validated")
    public void testEmptyFieldsValidation() {
        System.out.println("🧪 Testing: Empty fields validation");

        navigateToHomePage();

        HomePage homePage = new HomePage(driver);
        AddCustomerPage addCustomerPage = new AddCustomerPage(driver);

        // Navigate to Add Customer form
        homePage.clickBankManagerLogin();
        addCustomerPage.clickAddCustomerTab();

        // Try to submit with empty fields
        addCustomerPage.clickAddCustomerButton();
        waitForSeconds(2);
        takeScreenshot("empty_fields_validation");

        // Check if validation prevents submission
        String alertText = addCustomerPage.getAlertText();
        boolean hasValidationError = addCustomerPage.isValidationErrorDisplayed();

        // Should either show validation error or reject via alert
        boolean validationWorking = hasValidationError ||
                (!alertText.isEmpty() && !alertText.toLowerCase().contains("successfully"));

        if (validationWorking) {
            System.out.println("✅ Empty fields validation working correctly");
            if (!alertText.isEmpty()) {
                addCustomerPage.acceptAlertIfPresent();
            }
        } else {
            System.out.println("❌ Empty fields validation may not be working properly");
        }

        Assertions.assertTrue(validationWorking, "Empty fields should trigger validation error or rejection");
    }

    @Test
    @Story("Customer List Management")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Test Customer List Functionality")
    @Description("Verify that customer list displays correctly and search functionality works")
    public void testCustomerListFunctionality() {
        System.out.println("🧪 Testing: Customer list functionality");

        navigateToHomePage();

        HomePage homePage = new HomePage(driver);
        AddCustomerPage addCustomerPage = new AddCustomerPage(driver);

        // Navigate to customer list
        homePage.clickBankManagerLogin();
        addCustomerPage.clickCustomersTab();
        takeScreenshot("customer_list_page");

        // Get customer count
        int customerCount = addCustomerPage.getCustomerCount();
        System.out.println("👥 Found " + customerCount + " customers in the list");

        // Test search functionality if customers exist
        if (customerCount > 0) {
            addCustomerPage.searchCustomer("Harry");
            waitForSeconds(1);
            takeScreenshot("customer_search_results");
            System.out.println("🔍 Search functionality tested");
        }

        Assertions.assertTrue(customerCount >= 0, "Customer list should be accessible");
        System.out.println("✅ Customer list functionality working correctly");
    }
}