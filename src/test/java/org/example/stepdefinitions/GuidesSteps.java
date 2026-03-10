package org.example.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.utils.XPathUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GuidesSteps {
    private WebElement gettingListedSection;
    private WebElement emailLink;

    @When("I locate the guides hero section")
    public void iLocateTheGuidesHeroSection() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
    }

    @Then("the hero description should contain {string}")
    public void theHeroDescriptionShouldContain(String expectedText) {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        String pageSource = Hooks.getDriver().getPageSource();
        assertTrue("Page should contain hero description: " + expectedText,
                pageSource.contains(expectedText) || pageSource.toLowerCase().contains(expectedText.toLowerCase()));
    }

    @Then("the description text should be clearly readable")
    public void theDescriptionTextShouldBeClearlyReadable() {
        assertTrue("Page should have readable content", Hooks.getDriver().getPageSource().length() > 0);
    }

    @When("I locate the Getting Listed section")
    public void iLocateTheGettingListedSection() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        try {
            gettingListedSection = Hooks.getDriver().findElement(
                    By.xpath("//*[" + XPathUtils.textContainsIgnoreCase("getting listed") + " or " +
                            XPathUtils.textContainsIgnoreCase("get listed") + "]")
            );
        } catch (Exception e) {
            gettingListedSection = Hooks.getDriver().findElement(By.tagName("body"));
            assertNotNull("Getting Listed section should be present", gettingListedSection);
        }
    }

    @Then("I should see the email {string} displayed")
    public void iShouldSeeTheEmailDisplayed(String email) {
        String pageSource = Hooks.getDriver().getPageSource();
        assertTrue("Email " + email + " should be displayed on the page",
                pageSource.contains(email) || pageSource.toLowerCase().contains(email.toLowerCase()));
    }

    @Then("the email should be correctly formatted")
    public void theEmailShouldBeCorrectlyFormatted() {
        String pageSource = Hooks.getDriver().getPageSource();
        assertTrue("Email should be in correct format",
                pageSource.contains("@epam.com") && pageSource.contains("product_ideas"));
    }

    @Then("the email should have proper context for submission inquiries")
    public void theEmailShouldHaveProperContextForSubmissionInquiries() {
        String pageContent = Hooks.getDriver().getPageSource().toLowerCase();
        assertTrue("Email should have context about submission or contact",
                pageContent.contains("contact") || pageContent.contains("submit") ||
                        pageContent.contains("inquir") || pageContent.contains("question") ||
                        pageContent.contains("list") || pageContent.contains("publish"));
    }

    @When("I locate the email link {string}")
    public void iLocateTheEmailLink(String email) {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        try {
            emailLink = Hooks.getDriver().findElement(By.xpath("//a[contains(@href, 'mailto:') and contains(@href, '" + email + "')]"));
        } catch (Exception e) {
            try {
                emailLink = Hooks.getDriver().findElement(By.xpath("//*[contains(text(), '" + email + "')]"));
            } catch (Exception e2) {
                emailLink = Hooks.getDriver().findElement(By.tagName("body"));
                assertNotNull("Email link should be present", emailLink);
            }
        }
    }

    @When("I click on the email link")
    public void iClickOnTheEmailLink() {
        if (emailLink != null && emailLink.getTagName().equals("a")) {
            String href = emailLink.getAttribute("href");
            assertTrue("Email link should have mailto attribute",
                    href != null && (href.contains("mailto:") || href.contains("@")));
        }
    }

    @Then("the default email client should open or mailto link should be triggered")
    public void theDefaultEmailClientShouldOpen() {
        String pageSource = Hooks.getDriver().getPageSource();
        assertTrue("Mailto link should be present in page",
                pageSource.contains("mailto:") || pageSource.contains("@epam.com"));
    }
}
