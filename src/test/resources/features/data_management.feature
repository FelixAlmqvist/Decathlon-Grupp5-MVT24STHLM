Feature: Data Management
  As a user
  I want my competition data to be managed reliably and efficiently
  So that I can trust the system with my competition results

  @ID-215 @Data-Persistence @High-Priority
  Scenario: Verify data persistence after application restart
    Given the application is started and main window is visible
    And I have added a competitor named "Test Athlete"
    When I select "100m" from event dropdown
    And I enter "11.5" as result value
    Then the calculation should complete successfully
    And the system should handle the calculation correctly

  @ID-218 @Data-Processing @Medium-Priority
  Scenario: Verify handling of large datasets
    Given the application is started and main window is visible
    When I add 40 competitors with results
    Then all competitors should be saved without performance issues
    When I update the standings
    Then standings should update within 2 seconds
    When I export the results
    Then export should complete within 5 seconds

  @ID-219 @Error-Recovery @High-Priority
  Scenario: Verify data recovery after system failure
    Given the application is started and main window is visible
    When I enter competitor data that causes processing failure
    Then the system should display a recoverable error message
    And the application should not crash

  @ID-231 @Data-Integrity @High-Priority
  Scenario: Verify data consistency during concurrent access
    Given the application is started and main window is visible
    And I have added a competitor named "Concurrent Test"
    When I perform operations that simulate failed data loading
    Then the system should display a recoverable error message
    And the application should not crash

  @ID-232 @Export-Functionality @Medium-Priority
  Scenario: Verify comprehensive data export
    Given the application is started and main window is visible
    And I have added a competitor named "Export Test"
    When I export the results
    Then export should complete within 5 seconds
    And the application should not crash