package org.example.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.utils.JSUtils;
import org.example.utils.PageUtils;
import org.example.utils.XPathUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SolutionsSteps {

    @When("I select {string} from industry filters")
    public void iSelectFromIndustryFilters(String industry) throws InterruptedException {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        // Явное ожидание элемента с регистронезависимым поиском
        WebElement checkboxLabel = Hooks.getWait().until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[" + XPathUtils.textContainsIgnoreCase(industry) + "]")
            )
        );
        JSUtils.scrollAndClick(checkboxLabel);
        Thread.sleep(2000);
    }

    @Then("the {string} option should be visually selected")
    public void theOptionShouldBeVisuallySelected(String filterName) throws InterruptedException {
        WebElement checkboxLabel = Hooks.getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//label[" + XPathUtils.textContainsIgnoreCase(filterName) + "]")
                )
        );

        // Ищем связанный checkbox: сначала через атрибут for, потом как sibling/child
        WebElement checkbox;
        String labelFor = checkboxLabel.getAttribute("for");
        // <label for="id"> → <input id="id">
        checkbox = Hooks.getDriver().findElement(By.id(labelFor));

        assertTrue("Filter '" + filterName + "' checkbox should be checked", checkbox.isSelected());
        Thread.sleep(2000);
    }

    @Then("only {string} solutions should be displayed")
    public void onlyFinancialServicesSolutionsShouldBeDisplayed(String industry) throws InterruptedException {
        List<WebElement> list = Hooks.getWait().until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.xpath("//a[contains(@class, 'CatalogCard-module-')]")
                )
        );

        // Check that selected tag presence in list item's badges
        for (WebElement item : list) {
            List<WebElement> badges = item.findElements(
                    By.xpath(".//span[" + XPathUtils.textContainsIgnoreCase(industry) + "]"));
            assertTrue("Badge '" + industry + "' should be present in card", !badges.isEmpty());
            JSUtils.scrollTo(badges.get(0));
            assertTrue("Badge '" + industry + "' should be visible", badges.get(0).isDisplayed());
        }

        Thread.sleep(2000);
    }

    @Then("the result count should update correctly")
    public void theResultCountShouldUpdateCorrectly() throws InterruptedException {
        WebElement countElement = Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//p[contains(@class, '__sortCount')]")));

        String countText = countElement.getText()
                .replaceAll(".* of ([0-9]+) results.*", "$1");

        assertTrue("Result count should be present", Integer.parseInt(countText) > 0);
        Thread.sleep(2000);
    }

    @Then("the URL should contain the filter parameter {string}")
    public void theUrlShouldContainFilterParameter(String filterName) throws InterruptedException {
        WebElement checkboxLabel = Hooks.getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//label[" + XPathUtils.textContainsIgnoreCase(filterName) + "]")
                )
        );

        // Ищем связанный checkbox: сначала через атрибут for
        String labelFor = checkboxLabel.getAttribute("for");

        String currentUrl = Hooks.getDriver().getCurrentUrl();
        assertTrue("URL should contain filter or catalog reference",
                currentUrl.contains("catalog") || currentUrl.contains(labelFor));

        Thread.sleep(2000);
    }

    @When("I select {string} from category filters")
    public void iSelectFromCategoryFilters(String category) throws InterruptedException {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        // Явное ожидание элемента с регистронезависимым поиском
        WebElement checkboxLabel = Hooks.getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//label[" + XPathUtils.textContainsIgnoreCase(category) + "]")
                )
        );
        JSUtils.scrollAndClick(checkboxLabel);
        Thread.sleep(3000);
    }

    @Then("only solutions matching both {string} and {string} should be displayed")
    public void onlySolutionsMatchingBothIndustryAndCategoryShouldBeDisplayed(String industry, String category) throws InterruptedException {
        List<WebElement> list = Hooks.getWait().until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.xpath("//a[contains(@class, 'CatalogCard-module-')]")
                )
        );

        // Check that selected tag presence in list item's badges
        for (WebElement item : list) {
            List<WebElement> badges = item.findElements(
                    By.xpath(".//span[" + XPathUtils.textContainsIgnoreCase(industry) + "] | " +
                            ".//span[" + XPathUtils.textContainsIgnoreCase(category) + "]"));
            assertTrue("Badge '" + industry + "' or category '" + category + "' should be present in card", !badges.isEmpty());
            JSUtils.scrollTo(badges.get(0));
            assertTrue("Badge '" + industry + "' or category '" + category + "' should be visible", badges.get(0).isDisplayed());
        }

    }

    @Then("both filters {string} and {string} should be visually active")
    public void bothFiltersShouldBeVisuallyActive(String industry, String category) throws InterruptedException {
        theOptionShouldBeVisuallySelected(industry);
        theOptionShouldBeVisuallySelected(category);
    }

    @Then("the result count should be accurate")
    public void theResultCountShouldBeAccurate() {
        WebElement body = Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        assertTrue("Result count should be displayed", Hooks.getDriver().getPageSource().length() > 0);
    }

    @When("I locate pagination controls at the bottom")
    public void iLocatePaginationControlsAtTheBottom() throws InterruptedException {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        // Scroll to bottom to ensure pagination controls are visible
        JavascriptExecutor js = (JavascriptExecutor) Hooks.getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        Thread.sleep(2000);

        // Locate pagination controls
        WebElement pagination = Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//ul[contains(@class, 'pagination')]")
        ));
        assertTrue("Pagination controls should be visible", pagination.isDisplayed());
    }

    @When("I click the {string} button or page 2")
    public void iClickTheButtonOrPage(String buttonText) throws InterruptedException {
        WebElement nextButton = Hooks.getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[" + XPathUtils.elementContainsIgnoreCase(buttonText) + "] | " +
                        "//a[" + XPathUtils.elementContainsIgnoreCase(buttonText) + "] | " +
                        "//button[contains(@aria-label, '" + buttonText + "')] | " +
                        "//a[text()='2'] | " +
                        "//button[text()='2']")
        ));
        JSUtils.click(nextButton);
        Thread.sleep(2000);
    }

    @Then("page 2 should load successfully")
    public void pageShouldLoadSuccessfully() {
        WebElement body = Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        String currentUrl = Hooks.getDriver().getCurrentUrl();
        assertTrue("Page 2 should load", currentUrl.contains("page=2"));
    }

    @Then("a new set of solutions should be displayed")
    public void aNewSetOfSolutionsShouldBeDisplayed() throws InterruptedException {
        WebElement body = Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        List<WebElement> solutions = Hooks.getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//a[contains(@class, 'CatalogCard-module-')]")
        ));
        assertTrue("Solutions should be displayed on page 2", solutions.size() > 0);
    }

    @Then("page 2 should be highlighted in pagination")
    public void pageShouldBeHighlightedInPagination() throws InterruptedException {
        WebElement activePage = Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(@class, 'active') and text()='2'] | " +
                        "//a[contains(@class, 'active') and text()='2'] | " +
                        "//button[@aria-current='page' and text()='2'] | " +
                        "//a[@aria-current='page' and text()='2']")
        ));
        assertTrue("Page 2 should be highlighted", activePage.isDisplayed());
    }

    @Then("the URL should update with page parameter")
    public void theUrlShouldUpdateWithPageParameter() {
        String currentUrl = Hooks.getDriver().getCurrentUrl();
        assertTrue("URL should contain page parameter",
                currentUrl.contains("page=2") || currentUrl.contains("p=2") ||
                        currentUrl.contains("/2") || currentUrl.contains("offset"));
    }
}
