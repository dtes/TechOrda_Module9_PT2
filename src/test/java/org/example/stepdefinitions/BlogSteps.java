package org.example.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class BlogSteps {
    private WebElement heroSection;
    private WebElement firstBlogPost;
    private List<WebElement> blogPosts;

    private final List<String> months = List.of("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

    @When("I locate the blog hero section")
    public void iLocateTheBlogHeroSection() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        WebElement heroSection = Hooks.getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[contains(@class, 'HeroBanner-module-') and contains(@class, '__heroBanner')]")
                )
        );

        assertTrue("Hero section should be present", heroSection.isDisplayed());
    }

    @Then("the hero title should contain {string}")
    public void theHeroTitleShouldContain(String expectedText) {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        WebElement heroSection = Hooks.getWait().until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[contains(@class, 'HeroBanner-module-') and contains(@class, '__heroBanner')]")
                )
        );

        assertTrue("Page should contain hero title text: " + expectedText,
                heroSection.getText().contains(expectedText) ||
                        heroSection.getText().toLowerCase().contains(expectedText.toLowerCase()));
    }

    @Then("the title text {string} should be clearly visible")
    public void theTitleTextShouldBeClearlyVisible(String titleText) {
        WebElement heroSection = Hooks.getWait().until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(@class, 'HeroBanner-module-') and contains(@class, '__heroBanner')]")
                )
        );

        WebElement h1 = heroSection.findElement(By.tagName("h1"));
        assertTrue("Hero section title text should be visible",
                h1.isDisplayed() && h1.getText().contains(titleText));
    }

    @When("I examine the first blog post card")
    public void iExamineTheFirstBlogPostCard() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        List<WebElement> articles = Hooks.getWait().until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//a[contains(@class, 'BlogPostCard-module-')]")
                )
        );

        assertTrue("The first article should be visible", articles.get(0).isDisplayed());
    }

    @Then("the post card should display a title")
    public void thePostCardShouldDisplayTitle() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        List<WebElement> articles = Hooks.getWait().until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//a[contains(@class, 'BlogPostCard-module-')]")
                )
        );

        for (WebElement article : articles) {
            WebElement titleElement = article.findElement(By.tagName("h2"));
            assertTrue("Post card should have title", titleElement.getText().trim().length() > 0);
        }
    }

    @Then("the post card should display a publication date")
    public void thePostCardShouldDisplayPublicationDate() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        List<WebElement> articles = Hooks.getWait().until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//a[contains(@class, 'BlogPostCard-module-')]")
                )
        );

        for (WebElement article : articles) {
            WebElement date = article.findElement(By.xpath(".//p[contains(@class, '__date')]"));
            assertTrue("Post card should have date information",
                    date.getText().matches("([A-Z]{1}[a-z]+).*(\\d{1,2}),.*(\\d{4})"));
        }
    }

    @Then("the post card should display a description or excerpt")
    public void thePostCardShouldDisplayDescription() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        List<WebElement> articles = Hooks.getWait().until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//a[contains(@class, 'BlogPostCard-module-')]")
                )
        );

        for (WebElement article : articles) {
            WebElement desc = article.findElement(By.xpath(".//p[contains(@class, '__description')]"));
            assertTrue("Post card should have title", desc.getText().trim().length() > 10);
        }
    }

    @Then("the post card should display category or tags")
    public void thePostCardShouldDisplayCategoryOrTags() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        List<WebElement> articles = Hooks.getWait().until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//a[contains(@class, 'BlogPostCard-module-')]")
                )
        );

        for (WebElement article : articles) {
            WebElement tag = article.findElement(
                    By.xpath(".//span[contains(@class, 'Badge-module-') and contains(@class, '__content')]"));
            assertTrue("Post card should have title", tag.getText().trim().length() > 3);
        }
    }

    @When("I examine the publication dates of blog posts")
    public void iExaminePublicationDatesOfBlogPosts() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        List<WebElement> articles = Hooks.getWait().until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//a[contains(@class, 'BlogPostCard-module-')]")
                )
        );

        for (WebElement article : articles) {
            WebElement date = article.findElement(By.xpath(".//p[contains(@class, '__date')]"));
            assertTrue("Post card should have date information",
                    date.getText().matches("([A-Z]{1}[a-z]+).*(\\d{1,2}),.*(\\d{4})"));
        }
    }

    @Then("the posts should be ordered chronologically")
    public void thePostsShouldBeOrderedChronologically() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        List<WebElement> articles = Hooks.getWait().until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.xpath("//a[contains(@class, 'BlogPostCard-module-')]")
                )
        );

        String prevDate = null;
        for (WebElement article : articles) {
            WebElement date = article.findElement(By.xpath(".//p[contains(@class, '__date')]"));
            assertTrue("Post card should have date information",
                    date.getText().matches("([A-Z]{1}[a-z]+).*(\\d{1,2}),.*(\\d{4})"));
            String month = date.getText().split(",")[0].split(" ")[0];
            assertTrue("Post card should have a valid month: " + months, months.contains(month));

            String day = date.getText().split(",")[0].split(" ")[1].trim();
            assertTrue("Post card should have a valid day: " + day, Integer.parseInt(day) > 0);

            String year = date.getText().split(",")[1].trim();
            assertTrue("Post card should be from a valid year: " + year, Integer.parseInt(year) > 2000);

            int monthIndex = months.indexOf(month) + 1;
            String pubDate = year +
                    (monthIndex < 10 ? "0" + monthIndex : monthIndex) +
                    (day.length() < 2 ? "0" + day : day);
            if (prevDate == null) {
                prevDate = pubDate;
            }
            assertTrue("Post card should be chronologically ordered prev: " + prevDate + ". pub: " + pubDate, prevDate.compareTo(pubDate) >= 0);
            prevDate = pubDate;
        }
    }
}
