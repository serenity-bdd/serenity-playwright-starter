package starter.steps;

import com.microsoft.playwright.Page;
import net.serenitybdd.annotations.Step;
import starter.pages.WikipediaHomePage;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step library for navigation actions.
 * <p>
 * Step libraries contain @Step annotated methods that appear in Serenity reports.
 * They use Page Objects to perform actual browser interactions.
 * </p>
 */
public class NavigationSteps {

    private WikipediaHomePage homePage;

    private void ensurePageObject(Page page) {
        if (homePage == null) {
            homePage = new WikipediaHomePage(page);
        }
    }

    @Step("Open the Wikipedia home page")
    public void openWikipediaHomePage(Page page) {
        ensurePageObject(page);
        homePage.open();
    }

    @Step("Verify the Wikipedia home page is displayed")
    public void verifyWikipediaHomePageIsDisplayed(Page page) {
        ensurePageObject(page);
        assertThat(homePage.isDisplayed())
                .as("Wikipedia home page should be displayed")
                .isTrue();
    }
}
