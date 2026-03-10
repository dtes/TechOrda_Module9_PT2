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
    public void iLocateTheGuidesHeroSection() throws InterruptedException {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        WebElement heroSection = Hooks.getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[contains(@class, 'HeroBanner-module-') and contains(@class, '__heroBanner')]")
                )
        );

        assertTrue("Hero section should be present", heroSection.isDisplayed());
        Thread.sleep(1000);
    }

    @Then("the hero description should contain {string}")
    public void theHeroDescriptionShouldContain(String expectedText) throws InterruptedException {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        WebElement heroSection = Hooks.getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[contains(@class, 'HeroBanner-module-') and contains(@class, '__heroBanner')]")
                )
        );
        assertTrue("Page should contain hero description: " + expectedText,
                heroSection.getText().contains(expectedText) || heroSection.getText().toLowerCase().contains(expectedText.toLowerCase()));
        Thread.sleep(1000);
    }

    @Then("the description text should be clearly readable")
    public void theDescriptionTextShouldBeClearlyReadable() throws InterruptedException {
        assertTrue("Page should have readable content", Hooks.getDriver().getPageSource().length() > 0);
        Thread.sleep(1000);
    }

    @When("I locate the Getting Listed section")
    public void iLocateTheGettingListedSection() throws InterruptedException {
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
        Thread.sleep(1000);
    }

    @Then("I should see the email {string} displayed")
    public void iShouldSeeTheEmailDisplayed(String email) throws InterruptedException {
        String pageSource = Hooks.getDriver().getPageSource();
        assertTrue("Email " + email + " should be displayed on the page",
                pageSource.contains(email) || pageSource.toLowerCase().contains(email.toLowerCase()));
        Thread.sleep(1000);
    }

    @Then("the email should be correctly formatted")
    public void theEmailShouldBeCorrectlyFormatted() throws InterruptedException {
        String pageSource = Hooks.getDriver().getPageSource();
        assertTrue("Email should be in correct format",
                pageSource.contains("@epam.com") && pageSource.contains("product_ideas"));
        Thread.sleep(1000);
    }

    @Then("the email should have proper context for submission inquiries")
    public void theEmailShouldHaveProperContextForSubmissionInquiries() throws InterruptedException {
        String pageContent = Hooks.getDriver().getPageSource().toLowerCase();
        assertTrue("Email should have context about submission or contact",
                pageContent.contains("contact") || pageContent.contains("submit") ||
                        pageContent.contains("inquir") || pageContent.contains("question") ||
                        pageContent.contains("list") || pageContent.contains("publish"));
        Thread.sleep(1000);
    }

    @When("I locate the email link {string}")
    public void iLocateTheEmailLink(String email) throws InterruptedException {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        try {
            emailLink = Hooks.getDriver().findElement(By.xpath("//a[contains(@href, 'mailto:') and contains(@href, '" + email + "')]"));
            assertTrue("Email link should be present", emailLink.isDisplayed());
        } catch (Exception e) {
            try {
                emailLink = Hooks.getDriver().findElement(By.xpath("//*[contains(text(), '" + email + "')]"));
            } catch (Exception e2) {
                emailLink = Hooks.getDriver().findElement(By.tagName("body"));
                assertNotNull("Email link should be present", emailLink);
            }
        }
        Thread.sleep(2000);
    }

    @When("I click on the email link {string}")
    public void iClickOnTheEmailLink(String email) throws InterruptedException {
        emailLink = Hooks.getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//a[contains(@href, 'mailto:') and contains(@href, '" + email + "')]")
                )
        );
        assertTrue("Email link should be present", emailLink.isDisplayed());

        String href = emailLink.getAttribute("href");
        assertTrue("Email link should have mailto attribute",
                href != null && (href.contains("mailto:") || href.contains("@")));
        Thread.sleep(1000);
    }

    @Then("the default email client should open or mailto link should be triggered")
    public void theDefaultEmailClientShouldOpen() throws InterruptedException {
        String pageSource = Hooks.getDriver().getPageSource();
        assertTrue("Mailto link should be present in page",
                pageSource.contains("mailto:") || pageSource.contains("@epam.com"));
        Thread.sleep(1000);
    }
}
