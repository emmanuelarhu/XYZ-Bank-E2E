# 🏦 XYZ Bank Selenium Test Automation

A comprehensive Selenium test automation project for testing the XYZ Bank web application, including defect validation and quality assurance reporting with Allure.

## 📸 Application Under Test

### XYZ Bank Home Page
The main banking application with Customer Login and Bank Manager Login options.

### Bank Manager Dashboard
- **Add Customer**: Create new bank customers with validation
- **Open Account**: Create accounts for existing customers
- **Customers**: View and manage customer list

### Customer Dashboard
- **Account Overview**: View balance, account number, currency
- **Transactions**: View transaction history with filtering
- **Deposit**: Add money to account
- **Withdrawal**: Withdraw money from account

## 🎯 What This Project Tests

✅ **Customer Creation Validation Defects**  
✅ **Session Security Vulnerabilities**  
✅ **Transaction Filter Functionality**  
✅ **Input Validation Bypass Issues**  
✅ **Automated Defect Reporting with Allure**  
✅ **Comprehensive CI/CD with GitHub Actions**

## 🐛 Defects Under Test

| Defect ID | Priority | Description | Status |
|-----------|----------|-------------|---------|
| **G2TML-398** | High | Name validation fails to reject numeric characters | Open |
| **G2TML-400** | High | Special characters validation bypass - names accept @#$! symbols | Open |
| **G2TML-401** | High | Postal code field accepts alphabetic characters instead of numeric only | Open |
| **G2TML-402** | Medium | Transaction filter Reset button not clearing date filters properly | Open |
| **G2TML-403** | **Critical** | Session not terminated after logout - security vulnerability | Open |

## 🚀 Quick Start

### Prerequisites
- **Java 21** or higher
- **Maven 3.6+**
- **Chrome Browser** (latest version)
- **ChromeDriver** (auto-downloaded in CI/CD)

### Run Tests Locally
```bash
# 1. Clone this repository
git clone https://github.com/your-username/xyz-bank-selenium-tests.git
cd xyz-bank-selenium-tests

# 2. Run all tests
mvn test

# 3. Generate Allure report
mvn allure:report

# 4. Open Allure report
mvn allure:serve
```

### Run Tests in Headless Mode (CI/CD simulation)
```bash
mvn test -Dheadless=true
```

## 📊 Allure Reporting

This project includes comprehensive **Allure reporting** with:

- 📈 **Test execution trends**
- 📋 **Detailed test steps** with screenshots
- 🐛 **Defect tracking** and categorization
- 📸 **Automatic screenshots** on failures
- 📊 **Test categorization** by Epic/Feature/Story
- ⏱️ **Execution time analysis**

### View Allure Reports
- **Locally**: `mvn allure:serve`
- **CI/CD**: Automatically deployed to GitHub Pages
- **Live Demo**: Available after each test run

## 📁 Project Structure

```
📦 xyz-bank-selenium-tests/
├── 📁 .github/workflows/
│   └── ci.yml                           # GitHub Actions with Allure
├── 📁 src/
│   ├── 📁 main/java/pages/
│   │   ├── HomePage.java                # XYZ Bank home page
│   │   ├── AddCustomerPage.java         # Bank manager customer creation
│   │   └── CustomerPage.java            # Customer dashboard & operations
│   └── 📁 test/java/
│       ├── 📁 base/
│       │   └── BaseTest.java            # Test setup with Allure integration
│       └── 📁 tests/
│           ├── BankManagerValidationTests.java  # Customer creation defects
│           └── CustomerSessionTests.java        # Session & filter defects
├── 📁 target/
│   ├── 📁 allure-results/               # Raw Allure test data
│   └── 📁 site/allure-maven-plugin/     # Generated Allure reports
├── pom.xml                              # Maven dependencies with Allure
└── README.md                            # This file
```

## 🧪 Test Categories

### 🏛️ Epic: XYZ Bank Management
- **Feature**: Customer Management
    - Story: Add Customer Validation
    - Story: Customer Data Integrity

### 👤 Epic: XYZ Bank Customer Operations
- **Feature**: Customer Session Management
    - Story: Customer Session Security
    - Story: Transaction Filtering

## 📋 Test Execution Flow

### Bank Manager Tests
1. **🌐 Navigate to XYZ Bank**
2. **👨‍💼 Login as Bank Manager**
3. **📝 Test Customer Creation with Invalid Data**
    - Names with numbers (John123)
    - Names with special characters (John@#$)
    - Alphabetic postal codes (ABCDE)
4. **📸 Capture Screenshots of Defects**
5. **📊 Document Results in Allure**

### Customer Session Tests
1. **👤 Login as Existing Customer**
2. **🔒 Test Session Security After Logout**
3. **📊 Test Transaction Filter Reset**
4. **💰 Test Deposit/Withdrawal Operations**
5. **📸 Document Security Vulnerabilities**

## 🎮 GitHub Actions Features

The project automatically:
- 🔄 **Runs tests** on every push/PR
- 📊 **Generates Allure reports**
- 🚀 **Deploys reports** to GitHub Pages
- 📧 **Sends notifications** (email/Slack)
- 📸 **Captures screenshots** on failures
- 🐛 **Tracks defect status** in reports

## 📧 Setup Notifications

Add these secrets to your GitHub repository:

| Secret Name | Description | Example |
|-------------|-------------|---------|
| `EMAIL_USERNAME` | Gmail address for notifications | `your.email@gmail.com` |
| `EMAIL_PASSWORD` | Gmail app password | `abcd efgh ijkl mnop` |
| `NOTIFICATION_EMAIL` | Team notification email | `qa-team@company.com` |
| `SLACK_WEBHOOK_URL` | Slack webhook for channel notifications | `https://hooks.slack.com/...` |

### How to Add GitHub Secrets:
1. Go to **Repository Settings** → **Secrets and variables** → **Actions**
2. Click **New repository secret**
3. Add each secret above

## 🛠️ Technologies Used

- **Java 21** - Programming language
- **Selenium WebDriver 4.16** - Browser automation
- **JUnit 5** - Testing framework
- **Allure Framework** - Advanced test reporting
- **Maven** - Project management
- **GitHub Actions** - CI/CD automation
- **Chrome** - Target browser

## 📊 Allure Report Features

### 📈 Dashboard Overview
- Test execution summary
- Pass/fail trends over time
- Test duration analytics
- Environment information

### 🐛 Defect Tracking
- Defect severity classification
- Test-to-defect mapping
- Defect status tracking
- Root cause analysis

### 📸 Visual Documentation
- Step-by-step screenshots
- Failure evidence capture
- Test data attachments
- Browser console logs

## 🔧 Advanced Configuration

### Run Specific Test Categories
```bash
# Run only Bank Manager tests
mvn test -Dtest="BankManagerValidationTests"

# Run only Customer Session tests  
mvn test -Dtest="CustomerSessionTests"

# Run only Critical severity tests
mvn test -Dgroups="critical"
```

### Custom Allure Categories
The project includes custom Allure categories:
- 🐛 **Product Defects**
- 🔒 **Security Issues**
- ✅ **Feature Validation**
- 📊 **Regression Tests**

## 🚨 Critical Security Note

**Defect G2TML-403** represents a **CRITICAL security vulnerability**:
- Customers can access banking data after logout
- Session tokens are not properly invalidated
- Privacy violations and unauthorized access possible

**Immediate Action Required** for production systems.

## 🐛 Common Issues & Solutions

### "ChromeDriver not found"
**Solution**: ChromeDriver is auto-downloaded in CI/CD. For local testing, ensure Chrome browser is installed.

### "Tests pass locally but fail in CI"
**Solution**: CI runs in headless mode. Check GitHub Actions logs for detailed error information.

### "Allure report not generating"
**Solution**:
```bash
mvn clean test allure:report
mvn allure:serve
```

## 📈 Continuous Improvement

### Planned Enhancements
- 🔄 **API testing integration**
- 📱 **Mobile responsive testing**
- 🌍 **Cross-browser testing** (Firefox, Safari)
- 📊 **Performance testing** integration
- 🤖 **AI-powered test generation**

## 🤝 Contributing

1. **Fork** this repository
2. **Create** feature branch (`git checkout -b feature/new-test`)
3. **Add** tests with proper Allure annotations
4. **Test** locally (`mvn test`)
5. **Create** pull request

### Code Standards
- Use **@Step** annotations for Allure
- Include **@Severity** for defect prioritization
- Add **@Description** for test documentation
- Capture **screenshots** on critical steps

## 📞 Support & Contact

- 📧 **Email**: emmanuelarhu706@gmail.com
- 🐙 **GitHub**: [@emmanuelarhu](https://github.com/emmanuelarhu)
- 💼 **LinkedIn**: [Emmanuel Arhu](https://www.linkedin.com/in/emmanuelarhu)
- 🌐 **Website**: [emmanuelarhu.link](https://emmanuelarhu.link)


## 📄 License

This project is for **educational and testing purposes**. Feel free to use and modify!

---

⭐ **Star this repo if it helps you with test automation!** ⭐

🏦 **Happy Banking & Testing!** 🏦

## 📊 Live Demo

🔗 **Test Application**: [XYZ Bank Demo](https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login)  
📈 **Sample Allure Report**: Available after first test run  
🎬 **Test Execution Video**: Check GitHub Actions artifacts