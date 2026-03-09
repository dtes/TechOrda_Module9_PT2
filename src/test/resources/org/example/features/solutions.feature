Feature: Solutions Page Testing

  Background:
    Given I open the browser

  Scenario: TC-SOL-004 - Verify Single Industry Filter Selection
    Given I navigate to "https://solutionshub.epam.com/catalog"
    When I select "Financial Services" from industry filters
    Then the "Financial Services" option should be visually selected
    And only Financial Services solutions should be displayed
    And the result count should update correctly
    And the URL should contain the filter parameter

  Scenario: TC-SOL-007 - Verify Combined Filters Industry and Category
    Given I navigate to "https://solutionshub.epam.com/catalog"
    When I select "Healthcare" from industry filters
    And I select "AI" from category filters
    Then only solutions matching both Healthcare and AI should be displayed
    And both filters should be visually active
    And the result count should be accurate

  Scenario: TC-SOL-012 - Verify Search with Keyword Gen AI
    Given I navigate to "https://solutionshub.epam.com/catalog"
    When I enter "Gen AI" in the search field
    And I submit the search
    Then the search should execute successfully
    And results should show solutions related to Gen AI
    And the result count should show number of matches
