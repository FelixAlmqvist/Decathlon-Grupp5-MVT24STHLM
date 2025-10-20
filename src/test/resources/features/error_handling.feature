Feature: Error Handling and Input Validation
  As a user
  I want to see clear error messages for invalid inputs
  So that I can correct my data and use the application properly

  @ID-197 @GUI-Validation @High-Priority
  Scenario Outline: Verify that error message is displayed for negative results in all events
    Given the application is started and main window is visible
    When I enter "Test User" in the competitor name field
    And I select "<event>" from event dropdown
    And I enter "-5" in the result field
    And I click the "Save score" button
    Then an error message should be displayed informing about invalid value

    Examples:
      | event          |
      | 100m           |
      | 400m           |
      | 1500m          |
      | 110m Hurdles   |
      | High Jump      |
      | Long Jump      |
      | Shot Put       |
      | Discus         |
      | Javelin        |
      | Pole Vault     |

  Scenario: Verify error message for non-numeric input
    Given the application is started and main window is visible
    When I enter "Test User" in the competitor name field
    And I select "100m" from event dropdown
    And I enter "abc" in the result field
    And I click the "Save score" button
    Then an error message should be displayed informing about invalid value

  Scenario: Verify error message for empty result field
    Given the application is started and main window is visible
    When I enter "Test User" in the competitor name field
    And I select "100m" from event dropdown
    And I enter "" in the result field
    And I click the "Save score" button
    Then an error message should be displayed informing about invalid value