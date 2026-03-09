Feature: Assets Page Testing

  Background:
    Given I open the browser

  Scenario: TC-AST-003 - Verify Mode Toggle from Solutions to Assets
    Given I navigate to "https://solutionshub.epam.com/catalog"
    When I click on the "Assets" mode toggle
    Then the URL should contain "mode=assets"
    And the page should display asset cards
    And the "Assets" mode button should be highlighted

  Scenario: TC-AST-006 - Verify Mode State Persists on Page Refresh
    Given I navigate to "https://solutionshub.epam.com/catalog?mode=assets"
    When I refresh the page
    Then the URL should still contain "mode=assets"
    And the page should still display asset cards
    And the "Assets" mode should remain active

  Scenario: TC-AST-008 - Verify Asset Type Filter Beta Solution
    Given I navigate to "https://solutionshub.epam.com/catalog?mode=assets"
    When I select "Beta solution" from asset type filters
    Then only beta solution assets should be displayed
    And each asset card should show a "Beta" badge
    And the asset result count should update correctly
