Feature: Calculation Accuracy and Performance
  As a user
  I want accurate calculations that follow official standards and perform well
  So that competition results are reliable and the system remains responsive

  @ID-204 @Calculation-Validation @High-Priority
  Scenario: Verify high jump calculation with unit inconsistency
    Given the application is started and main window is visible
    And I have added a competitor named "Test Athlete"
    When I select "High Jump" from event dropdown
    And I enter a result value that triggers unit validation
    Then the system should handle the calculation correctly
    And the unit validation should be handled appropriately

  @ID-216 @Unit-Consistency @Medium-Priority
  Scenario: Verify unit consistency between UIs
    Given both Desktop and Web UIs are available
    When I enter "11.5" as "100m" result in Desktop UI
    Then the system should accept seconds as unit
    When I enter the same "11.5" in Web UI
    Then the same unit should be accepted
    When I compare score calculations
    Then identical scores should be displayed in both UIs

  @ID-217 @Performance @Medium-Priority
  Scenario: Verify performance with 40 competitors
    Given the application is started and main window is visible
    When I add 40 competitors with results
    Then all competitors should be saved without performance issues
    When I update the standings
    Then standings should update within 2 seconds
    When I export the results
    Then export should complete within 5 seconds

  @ID-233 @IAAF-Standards @High-Priority
  Scenario: Verify IAAF rounding rules compliance
    Given the application is started and main window is visible
    When I calculate points for an event that gives a decimal value
    Then the system should display an integer score without decimals
    When I verify the total score for a competitor with multiple events
    Then the total score should also be an integer
    And it should be correctly summed from individual integer scores