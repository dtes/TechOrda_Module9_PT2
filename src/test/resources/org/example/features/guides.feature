Feature: Guides Page Testing

  Background:
    Given I open the browser

  Scenario: TC-GUI-006 - Verify Hero Description Text
    Given I navigate to "https://solutionshub.epam.com/guides"
    When I locate the guides hero section
    Then the hero description should contain "Explore our platform capabilities for evaluation, listing and promotion of your software offering"
    And the description text should be clearly readable

  Scenario: TC-GUI-010 - Verify Contact Email Display
    Given I navigate to "https://solutionshub.epam.com/guides"
    When I locate the Getting Listed section
    Then I should see the email "product_ideas@epam.com" displayed
    And the email should be correctly formatted
    And the email should have proper context for submission inquiries

  Scenario: TC-GUI-011 - Verify Email Link Functionality
    Given I navigate to "https://solutionshub.epam.com/guides"
    When I locate the email link "product_ideas@epam.com"
    And I click on the email link
    Then the default email client should open or mailto link should be triggered
