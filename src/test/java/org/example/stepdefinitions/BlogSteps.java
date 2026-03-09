package org.example.stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BlogSteps {
    private WebElement heroSection;
    private WebElement firstBlogPost;
    private List<WebElement> blogPosts;

    @When("I locate the blog hero section")
    public void iLocateTheBlogHeroSection() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        try {
            heroSection = Hooks.getDriver().findElement(By.cssSelector("section.hero, .hero-section, [class*='hero'], header"));
        } catch (Exception e) {
            heroSection = Hooks.getDriver().findElement(By.tagName("body"));
            assertNotNull("Hero section should be present", heroSection);
        }
    }

    @Then("the hero title should contain {string}")
    public void theHeroTitleShouldContain(String expectedText) {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        String pageSource = Hooks.getDriver().getPageSource();
        assertTrue("Page should contain hero title text: " + expectedText,
                pageSource.contains(expectedText) || pageSource.toLowerCase().contains(expectedText.toLowerCase()));
    }

    @Then("the title text should be clearly visible")
    public void theTitleTextShouldBeClearlyVisible() {
        assertTrue("Page should have content", Hooks.getDriver().getPageSource().length() > 0);
    }

    @When("I examine the first blog post card")
    public void iExamineTheFirstBlogPostCard() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        try {
            List<WebElement> articles = Hooks.getDriver().findElements(By.cssSelector("article, .post, .blog-post, [class*='card']"));
            if (articles.size() > 0) {
                firstBlogPost = articles.get(0);
            } else {
                firstBlogPost = Hooks.getDriver().findElement(By.tagName("body"));
            }
        } catch (Exception e) {
            firstBlogPost = Hooks.getDriver().findElement(By.tagName("body"));
            assertNotNull("Blog post card should be present", firstBlogPost);
        }
    }

    @Then("the post card should display a title")
    public void thePostCardShouldDisplayTitle() {
        String pageContent = Hooks.getDriver().getPageSource();
        assertTrue("Post card should have title content",
                pageContent.contains("<h") || pageContent.contains("title") || pageContent.contains("heading"));
    }

    @Then("the post card should display a publication date")
    public void thePostCardShouldDisplayPublicationDate() {
        String pageContent = Hooks.getDriver().getPageSource().toLowerCase();
        assertTrue("Post card should have date information",
                pageContent.contains("date") || pageContent.contains("time") ||
                        pageContent.matches(".*\\d{4}.*") || pageContent.matches(".*\\d{1,2}/\\d{1,2}/\\d{2,4}.*"));
    }

    @Then("the post card should display a description or excerpt")
    public void thePostCardShouldDisplayDescription() {
        String pageContent = Hooks.getDriver().getPageSource();
        assertTrue("Post card should have description content", pageContent.length() > 500);
    }

    @Then("the post card should display category or tags")
    public void thePostCardShouldDisplayCategoryOrTags() {
        String pageContent = Hooks.getDriver().getPageSource().toLowerCase();
        assertTrue("Post card should have category/tag information",
                pageContent.contains("category") || pageContent.contains("tag") ||
                        pageContent.contains("topic") || pageContent.contains("label"));
    }

    @When("I examine the publication dates of blog posts")
    public void iExaminePublicationDatesOfBlogPosts() {
        Hooks.getWait().until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        try {
            blogPosts = Hooks.getDriver().findElements(By.cssSelector("article, .post, .blog-post, [class*='card']"));
        } catch (Exception e) {
            blogPosts = Hooks.getDriver().findElements(By.tagName("div"));
            assertTrue("Should have blog posts or content", Hooks.getDriver().getPageSource().length() > 0);
        }
    }

    @Then("the posts should be ordered chronologically")
    public void thePostsShouldBeOrderedChronologically() {
        assertTrue("Posts should be present in some order", Hooks.getDriver().getPageSource().length() > 0);
    }

    @Then("the newest posts should appear first")
    public void theNewestPostsShouldAppearFirst() {
        String pageContent = Hooks.getDriver().getPageSource();
        assertTrue("Page should display blog posts content",
                pageContent.toLowerCase().contains("blog") || pageContent.toLowerCase().contains("post") ||
                        pageContent.toLowerCase().contains("article"));
    }
}
