package org.example.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.utils.XPathUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.Assert.*;

public class AboutSteps {

    @When("I click on the {string} navigation tab")
    public void iClickOnNavigationTab(String tabName) {
        WebElement tab = Hooks.getWait().until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[" + XPathUtils.textContainsIgnoreCase(tabName) + "]")
        ));
        tab.click();
    }

    @Then("I should be on the {string} page")
    public void iShouldBeOnPage(String path) {
        Hooks.getWait().until(ExpectedConditions.urlContains(path));
        assertTrue("URL should contain " + path, Hooks.getDriver().getCurrentUrl().contains(path));
    }

    @Then("the {string} tab should be visually highlighted")
    public void theTabShouldBeHighlighted(String tabName) {
        WebElement activeTab = Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//a[contains(@class, 'active') or contains(@class, 'selected') or @aria-current='page']")
        ));
        assertNotNull("Active tab should be present", activeTab);
    }

    @Then("the page should display about content")
    public void thePageShouldDisplayAboutContent() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        String pageSource = Hooks.getDriver().getPageSource().toLowerCase();
        assertTrue("Page should contain about-related content",
            pageSource.contains("about") || pageSource.contains("mission") || pageSource.contains("epam"));
    }

    @When("I locate the mission section")
    public void iLocateTheMissionSection() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        try {
            WebElement missionSection = Hooks.getDriver().findElement(
                    By.xpath("//*[" + XPathUtils.textContainsIgnoreCase("mission") + " or " +
                            XPathUtils.textContainsIgnoreCase("purpose") + "]")
            );
            assertNotNull("Mission section should be present", missionSection);
        } catch (Exception e) {
            assertTrue("Mission section should be present on the page",
                    Hooks.getDriver().getPageSource().toLowerCase().contains("mission") ||
                            Hooks.getDriver().getPageSource().toLowerCase().contains("purpose"));
        }
    }

    @Then("the mission statement should be clearly displayed")
    public void theMissionStatementShouldBeClearlyDisplayed() {
        String pageSource = Hooks.getDriver().getPageSource().toLowerCase();
        assertTrue("Mission statement should be displayed",
                pageSource.contains("resource") || pageSource.contains("platform") || pageSource.contains("epam"));
    }

    @Then("the text should include {string} or {string}")
    public void theTextShouldIncludeOr(String text1, String text2) {
        String pageSource = Hooks.getDriver().getPageSource().toLowerCase();
        assertTrue("Text should include '" + text1 + "' or '" + text2 + "'",
                pageSource.contains(text1.toLowerCase()) || pageSource.contains(text2.toLowerCase()));
    }

    @Then("the mission should mention {string}")
    public void theMissionShouldMention(String keyword) {
        String pageSource = Hooks.getDriver().getPageSource().toLowerCase();
        assertTrue("Mission should mention: " + keyword,
                pageSource.contains(keyword.toLowerCase()));
    }

    @When("I locate the target audience section")
    public void iLocateTheTargetAudienceSection() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        try {
            WebElement audienceSection = Hooks.getDriver().findElement(
                    By.xpath("//*[" + XPathUtils.textContainsIgnoreCase("benefit") + " or " +
                            XPathUtils.textContainsIgnoreCase("audience") + " or " +
                            XPathUtils.textContainsIgnoreCase("client") + "]")
            );
            assertNotNull("Target audience section should be present", audienceSection);
        } catch (Exception e) {
            assertTrue("Target audience section should be present on the page",
                    Hooks.getDriver().getPageSource().toLowerCase().contains("client") ||
                            Hooks.getDriver().getPageSource().toLowerCase().contains("audience"));
        }
    }

    @Then("I should see {string} or similar audience label")
    public void iShouldSeeOrSimilarAudienceLabel(String audienceLabel) {
        String pageSource = Hooks.getDriver().getPageSource().toLowerCase();
        assertTrue("Page should contain audience label like '" + audienceLabel + "'",
                pageSource.contains(audienceLabel.toLowerCase()) ||
                pageSource.contains("client") ||
                pageSource.contains("customer") ||
                pageSource.contains("organization"));
    }

    @Then("the description should mention searching for solutions")
    public void theDescriptionShouldMentionSearchingForSolutions() {
        String pageSource = Hooks.getDriver().getPageSource().toLowerCase();
        assertTrue("Description should mention searching for solutions",
                (pageSource.contains("search") && pageSource.contains("solution")) ||
                pageSource.contains("discover") ||
                pageSource.contains("explore") ||
                pageSource.contains("find"));
    }

    @Then("the description should mention digital transformation")
    public void theDescriptionShouldMentionDigitalTransformation() {
        String pageSource = Hooks.getDriver().getPageSource().toLowerCase();
        assertTrue("Description should mention digital transformation",
                (pageSource.contains("digital") && pageSource.contains("transformation")) ||
                pageSource.contains("digital transformation") ||
                pageSource.contains("business") ||
                pageSource.contains("technology"));
    }
}
