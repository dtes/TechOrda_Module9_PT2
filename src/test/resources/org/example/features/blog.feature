Feature: Blog Page Testing

  Background:
    Given I open the browser

  Scenario: TC-BLOG-004 - Verify Hero Title Text
    Given I navigate to "https://solutionshub.epam.com/blog"
    When I locate the blog hero section
    Then the hero title should contain "SolutionsHub Blog Posts"
    And the title text "SolutionsHub Blog Posts" should be clearly visible

  Scenario: TC-BLOG-007 - Verify Blog Post Card Information
    Given I navigate to "https://solutionshub.epam.com/blog"
    When I examine the first blog post card
    Then the post card should display a title
    And the post card should display a publication date
    And the post card should display a description or excerpt
    And the post card should display category or tags

  Scenario: TC-BLOG-009 - Verify Blog Post Chronological Order
    Given I navigate to "https://solutionshub.epam.com/blog"
    When I examine the publication dates of blog posts
    Then the posts should be ordered chronologically
