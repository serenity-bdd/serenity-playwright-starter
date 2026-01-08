package starter;

import com.microsoft.playwright.*;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.playwright.PlaywrightSerenity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import starter.steps.NavigationSteps;
import starter.steps.SearchSteps;

/**
 * Sample Serenity Playwright test demonstrating Wikipedia search.
 * <p>
 * This test uses the three-layer architecture:
 * <ul>
 *   <li>Test class - describes business scenarios</li>
 *   <li>Step libraries - provide reporting with @Step annotations</li>
 *   <li>Page objects - encapsulate locators and page interactions</li>
 * </ul>
 * </p>
 */
@ExtendWith(SerenityJUnit5Extension.class)
class WikipediaSearchTest {

    @Steps
    NavigationSteps navigation;

    @Steps
    SearchSteps search;

    private static Playwright playwright;
    private static Browser browser;
    private Page page;

    @BeforeAll
    static void setupBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
        );
    }

    @AfterAll
    static void closeBrowser() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @BeforeEach
    void setupPage() {
        page = browser.newPage();
        PlaywrightSerenity.registerPage(page);
    }

    @AfterEach
    void closePage() {
        PlaywrightSerenity.unregisterPage(page);
        if (page != null) page.close();
    }

    @Test
    @DisplayName("Should be able to open the Wikipedia home page")
    void shouldOpenWikipediaHomePage() {
        navigation.openWikipediaHomePage(page);
        navigation.verifyWikipediaHomePageIsDisplayed(page);
    }

    @Test
    @DisplayName("Should be able to search for a term on Wikipedia")
    void shouldSearchForTerm() {
        navigation.openWikipediaHomePage(page);
        search.searchFor(page, "Playwright");
        search.verifyTitleContains(page, "Playwright");
    }

    @Test
    @DisplayName("Should find article about Serenity BDD")
    void shouldFindSerenityBDDArticle() {
        navigation.openWikipediaHomePage(page);
        search.searchFor(page, "Behavior-driven development");
        search.verifyTitleContains(page, "Behavior-driven development");
    }
}
