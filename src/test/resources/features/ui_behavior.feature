Feature: UI Behavior and Interactions
  As a user
  I want the UI to behave consistently and predictably
  So that I can interact with the application efficiently

  @ID-202 @GUI-Behavior @Medium-Priority
  Scenario: Verify Calculate Score button is inactive when fields are empty
    Given the application is started and main window is visible
    Then the "Calculate Score" button should be inactive
    When I enter "Lisa" in the competitor name field
    And I leave the result field empty
    Then the "Calculate Score" button should be inactive
    When I enter "12.0" in the result field
    And I leave the name field empty
    Then the "Calculate Score" button should be inactive
    When I enter "Lisa" in the competitor name field
    And I enter "12.0" in the result field
    Then the "Calculate Score" button should be active

  @ID-228 @Input-Validation @Medium-Priority
  Scenario: Verify handling of competitor names with maximum length
    Given the application is started and main window is visible
    When I enter a competitor name that is exactly 255 characters long
    Then the name should be accepted without truncation
    And the input field and interface layout should not break
    When I save a result for this competitor
    Then the result should be calculated and saved without errors
    When I view competitors in the standings list
    Then the long name should be displayed correctly without breaking the layout
    When I export the results
    Then the exported file should contain the complete, untruncated long name

  @ID-229 @Edge-Case @Medium-Priority
  Scenario: Verify system behavior with only one competitor
    Given the application is started and main window is visible
    When I add only one competitor with a result
    Then the system should calculate the score
    When I view the standings
    Then the competitor should be displayed in first place
    And the standings list should be stable
    When I export the results
    Then the file should be generated correctly showing the single competitor in first position

  @ID-230 @GUI-Stability @Medium-Priority
  Scenario: Verify system stability with rapid sequential calculation requests
    Given the application is started and main window is visible
    When I enter "Test User" in the competitor name field
    And I select "100m" from event dropdown
    And I enter "11.5" in the result field
    And I click the "Calculate Score" button 10 times rapidly
    Then the system should remain responsive
    And only one result entry should be created for the competitor
    And no application errors or crashes should occur