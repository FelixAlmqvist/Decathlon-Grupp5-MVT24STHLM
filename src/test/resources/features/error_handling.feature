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

  @ID-198 @GUI-Validation @High-Priority
  Scenario: Verify error message for non-numeric input
    Given the application is started and main window is visible
    When I enter "Test User" in the competitor name field
    And I select "100m" from event dropdown
    And I enter "abc" in the result field
    And I click the "Save score" button
    Then an error message should be displayed informing about invalid value

  @ID-199 @GUI-Validation @High-Priority
  Scenario: Verify error message for empty result field
    Given the application is started and main window is visible
    When I enter "Test User" in the competitor name field
    And I select "100m" from event dropdown
    And I enter "" in the result field
    And I click the "Save score" button
    Then an error message should be displayed informing about invalid value

  @ID-200 @GUI-Validation @High-Priority
  Scenario: Verify error message for empty name field
    Given the application is started and main window is visible
    When I enter "" in the competitor name field
    And I select "100m" from event dropdown
    And I enter "12.5" in the result field
    And I click the "Save score" button
    Then an error message should be displayed informing about invalid name

  @ID-201 @GUI-Validation @High-Priority
  Scenario: Verify error message for zero result value
    Given the application is started and main window is visible
    When I enter "Test User" in the competitor name field
    And I select "100m" from event dropdown
    And I enter "0" in the result field
    And I click the "Save score" button
    Then an error message should be displayed informing about invalid value

  @ID-203 @GUI-Validation @Medium-Priority
  Scenario: Verify error message for multiple invalid fields simultaneously
    Given the application is started and main window is visible
    When I enter "" in the competitor name field
    And I select "100m" from event dropdown
    And I enter "abc" in the result field
    And I click the "Save score" button
    Then an error message should be displayed informing about multiple invalid values

  @ID-234 @GUI-Validation @High-Priority
  Scenario: Verify error message for invalid special characters in name field
    Given the application is started and main window is visible
    When I enter "Test!" in the competitor name field
    And I select "100m" from event dropdown
    And I enter "12.5" in the result field
    And I click the "Save score" button
    Then an error message should be displayed informing about invalid characters in name