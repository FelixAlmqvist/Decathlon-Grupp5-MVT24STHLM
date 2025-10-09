Feature: Error Handling and Input Validation
  As a user
  I want to see clear error messages for invalid inputs
  So that I can correct my data and use the application properly

  @ID-197 @GUI-Validation @High-Priority
  Scenario: Verify that error message is displayed for negative result in GUI
    Given the application is started and main window is visible
    When I enter "Anna" in the competitor name field
    And I select "100 m run" from discipline dropdown
    And I enter "-5" in the result field
    And I click the "Calculate Score" button
    Then an error message should be displayed informing about invalid value

