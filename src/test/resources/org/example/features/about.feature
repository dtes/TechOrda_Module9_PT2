Feature: About Page Testing

  Background:
    Given I open the browser

  Scenario: TC-ABT-001 - Verify About Tab Navigation from Homepage
    Given I navigate to "https://solutionshub.epam.com/"
    When I click on the "About" navigation tab
    Then I should be on the "/about" page
    And the "About" tab should be visually highlighted
    And the page should display about content

  Scenario: TC-ABT-004 - Verify Company Mission Display
    Given I navigate to "https://solutionshub.epam.com/about"
    When I locate the mission section
    Then the mission statement should be clearly displayed
    And the text should include "central resource" or "comprehensive information"
    And the mission should mention "EPAM software solutions"

  Scenario: TC-ABT-013 - Verify External Clients Audience Description
    Given I navigate to "https://solutionshub.epam.com/about"
    When I locate the target audience section
    Then I should see "External Clients" or similar audience label
    And the description should mention searching for solutions
    And the description should mention digital transformation
