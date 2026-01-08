package starter.steps;

import com.microsoft.playwright.Page;
import net.serenitybdd.annotations.Step;
import starter.pages.WikipediaHomePage;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step library for search functionality.
 * <p>
 * Step libraries contain @Step annotated methods that appear in Serenity reports.
 * They use Page Objects to perform actual browser interactions.
 * </p>
 */
public class SearchSteps {

    private WikipediaHomePage homePage;

    private void ensurePageObject(Page page) {
        if (homePage == null) {
            homePage = new WikipediaHomePage(page);
        }
    }

    @Step("Search for '{1}'")
    public void searchFor(Page page, String term) {
        ensurePageObject(page);
        homePage.searchFor(term);
    }

    @Step("Verify the page title contains '{1}'")
    public void verifyTitleContains(Page page, String expectedText) {
        ensurePageObject(page);
        assertThat(homePage.getTitle())
                .as("Page title should contain: " + expectedText)
                .containsIgnoringCase(expectedText);
    }
}
