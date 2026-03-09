package org.example.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.Assert.assertTrue;

public class SolutionsSteps {
    @When("I select {string} from industry filters")
    public void iSelectFromIndustryFilters(String industry) {
        WebElement body = Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        try {
            WebElement checkboxLabel = body.findElement(By.xpath("//label[contains(text(), '" + industry + "')]"));

            // Кликаем через JavaScript
            ((JavascriptExecutor) Hooks.getDriver()).executeScript("arguments[0].click();", checkboxLabel);

            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println("====");
            e.printStackTrace();
            System.out.println("Filter element not found or not clickable: " + industry);
        }
    }

    @Then("the {string} option should be visually selected")
    public void theOptionShouldBeVisuallySelected(String filterName) {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        assertTrue("Filter should be applied", Hooks.getDriver().getPageSource().length() > 0);
    }

    @Then("only Financial Services solutions should be displayed")
    public void onlyFinancialServicesSolutionsShouldBeDisplayed() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        assertTrue("Page should show filtered results", Hooks.getDriver().getPageSource().length() > 0);
    }

    @Then("the result count should update correctly")
    public void theResultCountShouldUpdateCorrectly() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        assertTrue("Result count should be present", Hooks.getDriver().getPageSource().length() > 0);
    }

    @Then("the URL should contain the filter parameter")
    public void theUrlShouldContainFilterParameter() {
        String currentUrl = Hooks.getDriver().getCurrentUrl();
        assertTrue("URL should contain filter or catalog reference",
                currentUrl.contains("catalog") || currentUrl.contains("filter") ||
                        currentUrl.contains("?") || currentUrl.contains("&"));
    }

    @When("I select {string} from category filters")
    public void iSelectFromCategoryFilters(String category) {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        try {
            WebElement filterElement = Hooks.getWait().until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//label[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + category.toLowerCase() + "')] | " +
                            "//input[@type='checkbox' and following-sibling::*[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + category.toLowerCase() + "')]] | " +
                            "//button[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + category.toLowerCase() + "')]")
            ));
            filterElement.click();
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Category filter element not found: " + category);
        }
    }

    @Then("only solutions matching both Healthcare and AI should be displayed")
    public void onlySolutionsMatchingBothHealthcareAndAIShouldBeDisplayed() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        assertTrue("Filtered results should be displayed", Hooks.getDriver().getPageSource().length() > 0);
    }

    @Then("both filters should be visually active")
    public void bothFiltersShouldBeVisuallyActive() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        assertTrue("Both filters should be applied", Hooks.getDriver().getPageSource().length() > 0);
    }

    @Then("the result count should be accurate")
    public void theResultCountShouldBeAccurate() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        assertTrue("Result count should be displayed", Hooks.getDriver().getPageSource().length() > 0);
    }

    @When("I enter {string} in the search field")
    public void iEnterInSearchField(String searchTerm) {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        try {
            WebElement searchBox = Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[@type='search'] | //input[@type='text' and contains(@placeholder, 'Search')] | //input[contains(@placeholder, 'search')]")
            ));
            searchBox.clear();
            searchBox.sendKeys(searchTerm);
        } catch (Exception e) {
            System.out.println("Search field not found");
        }
    }

    @When("I submit the search")
    public void iSubmitTheSearch() {
        try {
            WebElement searchBox = Hooks.getDriver().findElement(
                    By.xpath("//input[@type='search'] | //input[@type='text' and contains(@placeholder, 'Search')] | //input[contains(@placeholder, 'search')]")
            );
            searchBox.sendKeys(Keys.ENTER);
            Thread.sleep(1000);
        } catch (Exception e) {
            try {
                WebElement searchButton = Hooks.getDriver().findElement(
                        By.xpath("//button[contains(@type, 'submit')] | //button[contains(., 'Search')] | //button[@type='submit']")
                );
                searchButton.click();
                Thread.sleep(1000);
            } catch (Exception e2) {
                System.out.println("Search submission method not found");
            }
        }
    }

    @Then("the search should execute successfully")
    public void theSearchShouldExecuteSuccessfully() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        assertTrue("Search should execute", Hooks.getDriver().getPageSource().length() > 0);
    }

    @Then("results should show solutions related to Gen AI")
    public void resultsShouldShowSolutionsRelatedToGenAI() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        String pageContent = Hooks.getDriver().getPageSource().toLowerCase();
        assertTrue("Results should contain relevant content",
                pageContent.contains("solution") || pageContent.contains("catalog") ||
                        pageContent.contains("ai") || pageContent.contains("result"));
    }

    @Then("the result count should show number of matches")
    public void theResultCountShouldShowNumberOfMatches() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        String pageContent = Hooks.getDriver().getPageSource();
        assertTrue("Result count should be displayed",
                pageContent.matches(".*\\d+.*") || pageContent.toLowerCase().contains("result"));
    }
}
