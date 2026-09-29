# BDD Regression Suite — SauceDemo

A data-driven test automation framework built with **Java, Selenium WebDriver, Cucumber (BDD), and TestNG**, targeting [saucedemo.com](https://www.saucedemo.com).

## Why this project
Built to strengthen automation framework design skills — specifically test data management, the Page Object Model, and synchronization handling — as a follow-up to interview feedback on automation depth.

## Tech stack
- Java 17
- Selenium WebDriver 4.25
- Cucumber-JVM 7.20 (BDD)
- TestNG 7.10 (test execution)
- Apache Commons CSV (data-driven testing)
- Maven

## Framework design
- **Page Object Model** — locators and page actions are isolated in `pages/`, separate from test logic
- **Data-driven testing** — test data (login credentials, checkout details) lives in CSV files under `resources/testdata/`, looked up by test ID, so new cases are added as rows, not code
- **Explicit waits** — no `Thread.sleep`; uses `WebDriverWait` and `ExpectedConditions` throughout
- **Resilient clicks** — a custom `ElementUtils.clickAndWaitForUrl()` handles a real flaky-click issue found during development, where a freshly-loaded page's JS listener wasn't attached in time for the first click; it retries with a JavaScript click and confirms navigation via URL
- **ThreadLocal WebDriver** — driver instances are thread-safe, ready for parallel execution
- **Screenshots on failure** — attached automatically to the Cucumber report via hooks
- **Tag-based execution** — `@smoke`, `@regression` tags control which scenarios run

## Project structure
src/test/
├── java/
│ ├── hooks/ # Setup & teardown, screenshot-on-failure
│ ├── pages/ # Page Object classes
│ ├── runners/ # TestNG-Cucumber runner
│ ├── stepdefinitions/ # Step implementations
│ └── utils/ # CsvReader, DriverFactory, ElementUtils
└── resources/
├── features/ # Gherkin feature files
└── testdata/ # CSV test data


## How to run
```bash
# Full regression suite
mvn clean test

# Only smoke tests
mvn clean test -Dcucumber.filter.tags="@smoke"

# Headless mode
mvn clean test -Dheadless=true
```

## Reports
An HTML report is generated at `target/cucumber-reports/report.html` after each run, with screenshots attached to any failed scenario.

## Features covered
- **Login** — valid login, locked-out user, invalid credentials, missing fields (5 scenarios)
- **Cart & Checkout** — add to cart, cart badge count, checkout with valid/invalid details (5 scenarios)

## Possible extensions
- Parallel execution via TestNG data providers
- `config.properties` for environment/browser configuration
- CI pipeline via GitHub Actions