package starter.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

/**
 * Page Object for the Wikipedia home page.
 * <p>
 * This class encapsulates all locator strategies and page-specific actions.
 * Tests and step libraries should never need to know about CSS selectors,
 * XPath expressions, or other locator details.
 * </p>
 */
public class WikipediaHomePage {

    private final Page page;

    private static final String URL = "https://en.wikipedia.org";

    public WikipediaHomePage(Page page) {
        this.page = page;
    }

    // ========== Locators (private) ==========

    private Locator searchBox() {
        return page.getByRole(AriaRole.SEARCHBOX,
                new Page.GetByRoleOptions().setName("Search Wikipedia"));
    }

    private Locator mainLogo() {
        return page.locator(".central-textlogo, .wikipedia-wordmark");
    }

    // ========== Actions (public) ==========

    public void open() {
        page.navigate(URL);
    }

    public void searchFor(String term) {
        searchBox().fill(term);
        searchBox().press("Enter");
    }

    // ========== Queries (public) ==========

    public String getTitle() {
        return page.title();
    }

    public boolean isLogoVisible() {
        return mainLogo().isVisible();
    }

    public boolean isDisplayed() {
        return page.url().contains("wikipedia.org") &&
               page.title().toLowerCase().contains("wikipedia");
    }
}
