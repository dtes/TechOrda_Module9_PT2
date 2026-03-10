Feature: Solutions Page Testing

  Background:
    Given I open the browser

  Scenario: TC-SOL-004 - Verify Single Industry Filter Selection
    Given I navigate to "https://solutionshub.epam.com/catalog"
    When I select "Financial Services" from industry filters
    Then the "Financial Services" option should be visually selected
    And only "Financial Services" solutions should be displayed
    And the result count should update correctly
    And the URL should contain the filter parameter "Financial Services"

  Scenario: TC-SOL-007 - Verify Combined Filters Industry and Category
    Given I navigate to "https://solutionshub.epam.com/catalog"
    When I select "EdTech" from industry filters
    And I select "Analytics" from category filters
    Then only solutions matching both "EdTech" and "Analytics" should be displayed
    And both filters "EdTech" and "Analytics" should be visually active
    And the result count should update correctly

  Scenario: TC-SOL-027 - Verify Next Page Navigation
    Given I navigate to "https://solutionshub.epam.com/catalog"
    When I locate pagination controls at the bottom
    And I click the "Next" button or page 2
    Then page 2 should load successfully
    And a new set of solutions should be displayed
    And page 2 should be highlighted in pagination
    And the URL should update with page parameter
