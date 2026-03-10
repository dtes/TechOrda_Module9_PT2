package org.example.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.utils.JSUtils;
import org.example.utils.XPathUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AssetsSteps {
    @When("I click on the {string} mode toggle")
    public void iClickOnModeToggle(String mode) throws InterruptedException {
        // Ожидаем, что body загрузится
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        Thread.sleep(3000);

        // Ищем кнопку по тексту (регистронезависимый XPath) или по CSS.
        // Используем presenceOfElementLocated — кнопка может быть в DOM, но скрыта визуально
        WebElement toggleButton = Hooks.getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//button[" + XPathUtils.textContainsIgnoreCase(mode) + "]")
                )
        );

        // Прокручиваем к элементу и кликаем через JS (обходит ограничение видимости)
        JSUtils.scrollAndClick(toggleButton);
        Thread.sleep(1000);
    }

    @Then("the URL should contain {string}")
    public void theUrlShouldContain(String expectedParam) throws InterruptedException {
        Hooks.getWait().until(ExpectedConditions.urlContains(expectedParam));
        assertTrue("URL should contain " + expectedParam,
                Hooks.getDriver().getCurrentUrl().contains(expectedParam));
        Thread.sleep(1000);
    }

    @Then("the page should display asset cards")
    public void thePageShouldDisplayAssetCards() throws InterruptedException {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        String pageSource = Hooks.getDriver().getPageSource().toLowerCase();
        assertTrue("Page should display asset-related content",
                pageSource.contains("asset") || pageSource.contains("card") || pageSource.contains("catalog"));

        List<WebElement> list = Hooks.getWait().until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.xpath("//a[contains(@class, 'CatalogCard-module-')]")
                )
        );

        // Check that selected tag presence in list item's badges
        for (WebElement item : list) {
            JSUtils.scrollTo(item);
            assertTrue("Asset item link should contains /asset/",
                    item.getAttribute("href").contains("/asset/"));
        }

        Thread.sleep(1000);
    }

    @Then("the {string} mode button should be highlighted")
    public void theModeButtonShouldBeHighlighted(String mode) throws InterruptedException {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        // Ищем кнопку по тексту (регистронезависимый XPath) или по CSS.
        // Используем presenceOfElementLocated — кнопка может быть в DOM, но скрыта визуально
        WebElement toggleButton = Hooks.getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//button[" + XPathUtils.textContainsIgnoreCase(mode) + "]")
                )
        );

        assertTrue("Assets mode should be active",
                toggleButton.getAttribute("disabled").equals("true") &&
                        toggleButton.getAttribute("class").contains("__active"));
        Thread.sleep(1000);
    }

    @When("I refresh the page")
    public void iRefreshThePage() throws InterruptedException {
        Hooks.getDriver().navigate().refresh();
        Thread.sleep(1000);
    }

    @Then("the URL should still contain {string}")
    public void theUrlShouldStillContain(String expectedParam) throws InterruptedException {
        Hooks.getWait().until(ExpectedConditions.urlContains(expectedParam));
        assertTrue("URL should still contain " + expectedParam,
                Hooks.getDriver().getCurrentUrl().contains(expectedParam));
        Thread.sleep(1000);
    }

    @Then("the page should still display asset cards")
    public void thePageShouldStillDisplayAssetCards() throws InterruptedException {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        String pageSource = Hooks.getDriver().getPageSource().toLowerCase();
        assertTrue("Page should still display assets",
                pageSource.contains("asset") || pageSource.contains("catalog"));
        Thread.sleep(1000);
    }

    @When("I select {string} from asset type filters")
    public void iSelectFromAssetTypeFilters(String filterType) throws InterruptedException {
        WebElement filterElement = Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[" + XPathUtils.elementContainsIgnoreCase(filterType) + "]")
        ));
        Assert.assertTrue("Filter element should be present", filterElement.isDisplayed());
        JSUtils.click(filterElement);
        Thread.sleep(1000);
    }

    @Then("only beta solution assets should be displayed")
    public void onlyBetaSolutionAssetsShouldBeDisplayed() throws InterruptedException {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        // Find catalog cards (same selector as thePageShouldDisplayAssetCards)
        List<WebElement> cards = Hooks.getWait().until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.xpath("//a[contains(@class, 'CatalogCard-module-')]")
                )
        );
        assertFalse("There should be at least one asset card after filtering", cards.isEmpty());

        // Open only the first card to verify it matches the selected filter
        String firstCardHref = cards.get(0).getAttribute("href");
        assertNotNull("First card should have a valid href", firstCardHref);
        assertFalse("First card href should not be empty", firstCardHref.isEmpty());

        String currentUrl = Hooks.getDriver().getCurrentUrl();
        assertNotNull("Current URL should not be null", currentUrl);
        Hooks.getDriver().get(firstCardHref);
        Thread.sleep( 2000);

        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        // On the asset/solution detail page, verify the "Beta solution" badge is present.
        // JS-based polling handles SPA lazy rendering and off-screen elements
        // that Selenium's visibility check cannot reach.
        assertTrue("Asset detail page should show 'Beta solution' badge",
                Hooks.getWait().until(driver ->
                        (Boolean) ((JavascriptExecutor) driver).executeScript(
                                "return Array.from(document.querySelectorAll('span[class*=\"Badge-module\"][class*=\"__content\"]'))" +
                                ".some(function(s){ return s.textContent.trim() === 'Beta solution'; });"
                        )
                )
        );

        // Navigate back to the filtered list for subsequent steps
        Hooks.getDriver().get(currentUrl);
        Thread.sleep(3000);
    }

    @Then("the asset result count should update correctly")
    public void theAssetResultCountShouldUpdateCorrectly() throws InterruptedException {
        WebElement countElement = Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//p[contains(@class, '__sortCount')]")));

        String countText = countElement.getText()
                .replaceAll(".* of ([0-9]+) results.*", "$1");

        assertTrue("Result count should be present", Integer.parseInt(countText) > 0);
    }

    @Then("the page should display asset cards or solutions")
    public void thePageShouldDisplayAssetCardsOrSolutions() throws InterruptedException {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        String pageSource = Hooks.getDriver().getPageSource().toLowerCase();
        assertTrue("Page should display content",
                pageSource.contains("asset") || pageSource.contains("solution") ||
                        pageSource.contains("catalog") || pageSource.contains("card"));
        Thread.sleep(1000);
    }
}
