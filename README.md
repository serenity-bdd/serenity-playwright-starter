# Serenity Playwright Starter

This is a starter project for [Serenity BDD](https://serenity-bdd.github.io/) with [Playwright](https://playwright.dev/), demonstrating how to write automated browser tests with rich reporting.

## Features

- **Playwright** - Modern, fast, and reliable browser automation
- **Serenity BDD** - Rich HTML reports with screenshots at each step
- **JUnit 5** - Modern test framework with excellent IDE support
- **Page Objects** - Clean separation of locators and test logic
- **Step Libraries** - Reusable test steps with automatic reporting

## Prerequisites

- **Java 17** or higher
- **Maven 3.8+**
- An IDE (IntelliJ IDEA recommended)

## Project Structure

```
src/test/java/
├── starter/
│   ├── WikipediaSearchTest.java    # Test class
│   ├── pages/
│   │   └── WikipediaHomePage.java  # Page Object
│   └── steps/
│       ├── NavigationSteps.java    # Step library
│       └── SearchSteps.java        # Step library
└── resources/
    ├── serenity.conf               # Serenity configuration
    └── logback-test.xml            # Logging configuration
```

## Running the Tests

### Run all tests

```bash
mvn clean verify
```

### Run tests and view the report

```bash
mvn clean verify
open target/site/serenity/index.html
```

### Run with visible browser (not headless)

Edit `WikipediaSearchTest.java` and change:
```java
new BrowserType.LaunchOptions().setHeadless(false)
```

## Architecture

This project follows the **three-layer architecture**:

```
Test Class (describes business scenarios)
    └── Step Library (@Step methods for reporting)
          └── Page Object (encapsulates locators)
```

### Page Objects

Page Objects encapsulate all locator strategies and page interactions:

```java
public class WikipediaHomePage {
    private final Page page;

    // Locators are private
    private Locator searchBox() {
        return page.getByRole(AriaRole.SEARCHBOX,
            new Page.GetByRoleOptions().setName("Search Wikipedia"));
    }

    // Actions are public
    public void searchFor(String term) {
        searchBox().fill(term);
        searchBox().press("Enter");
    }
}
```

### Step Libraries

Step libraries provide `@Step` annotations for Serenity reporting:

```java
public class SearchSteps {
    private WikipediaHomePage homePage;

    @Step("Search for '{1}'")
    public void searchFor(Page page, String term) {
        homePage.searchFor(term);
    }
}
```

### Tests

Tests use step libraries to describe business scenarios:

```java
@ExtendWith(SerenityJUnit5Extension.class)
class WikipediaSearchTest {

    @Steps SearchSteps search;
    @Steps NavigationSteps navigation;

    @Test
    void shouldSearchForTerm() {
        navigation.openWikipediaHomePage(page);
        search.searchFor(page, "Playwright");
        search.verifyTitleContains(page, "Playwright");
    }
}
```

## Serenity Reports

After running the tests, Serenity generates rich HTML reports in `target/site/serenity/`:

- **Test Results** - Pass/fail status with screenshots
- **Step Details** - Each `@Step` method is documented
- **Screenshots** - Captured at each step completion
- **Test Timeline** - Visual representation of test execution

## Configuration

### serenity.conf

Configure Serenity behavior in `src/test/resources/serenity.conf`:

```hocon
serenity {
  project.name = "My Project"
  take.screenshots = FOR_EACH_ACTION
}
```

### Screenshot Strategies

| Strategy | Description |
|----------|-------------|
| `FOR_EACH_ACTION` | Screenshot after every step (default) |
| `BEFORE_AND_AFTER_EACH_STEP` | Screenshot before and after each step |
| `FOR_FAILURES` | Screenshot only when a step fails |
| `DISABLED` | No automatic screenshots |

## Browser Support

Playwright supports multiple browsers:

```java
// Chromium (default)
browser = playwright.chromium().launch();

// Firefox
browser = playwright.firefox().launch();

// WebKit (Safari)
browser = playwright.webkit().launch();
```

## Learn More

- [Serenity BDD Documentation](https://serenity-bdd.github.io/)
- [Serenity Playwright Guide](https://serenity-bdd.github.io/docs/playwright/playwright_intro)
- [Playwright Documentation](https://playwright.dev/java/)

## License

This project is licensed under the Apache License 2.0.