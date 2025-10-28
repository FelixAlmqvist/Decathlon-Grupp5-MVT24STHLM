Feature: Data Management and Persistence
  As a user
  I want my competition data to be managed reliably across sessions and UIs
  So that I don't lose important information and can work flexibly

  @ID-215 @Data-Integrity @High-Priority
  Scenario: Verify data integrity during save/restore operations
    Given the application is started and main window is visible
    And the application is started with test data
    When I create a complex session with multiple competitors and events
    Then all data should be saved correctly
    When I save and close the application
    And I restart the application and restore the session
    Then all original data should be restored exactly
    And all scores and standings should match the original

  @ID-218 @Cross-UI-Compatibility @High-Priority
  Scenario: Verify save/restore works between sessions
    Given the application is started and main window is visible
    And the application is started with test data
    When I save and close the application
    And I restart the application and restore the session
    Then all original data should be restored exactly
    When I enter "14.0" as result value
    Then the calculation should complete successfully
    When I save and close the application
    And I restart the application and restore the session
    Then all original data should be restored exactly

  @ID-219 @Export-Functionality @High-Priority
  Scenario: Verify exported files have unique timestamped filenames
    Given the application is started and main window is visible
    Given the application is running with at least one competitor and results
    When I export the results for the first time
    Then the file should be saved with a name containing timestamp
    When I export the results for the second time
    Then a new file should be created with a different timestamp in filename
    When I check the export folder
    Then two separate files with different names should exist

  @ID-231 @Data-Recovery @Low-Priority
  Scenario: Verify data persistence during unexpected shutdown
    Given the application is started and main window is visible
    When I add several competitors and results
    And I force close the application without saving
    And I reopen the application
    Then the application should start normally
    And unsaved data may be lost but application should not be corrupt

  @ID-232 @File-Handling @Low-Priority
  Scenario: Verify file locking handling on save files
    Given the application is started and main window is visible
    Given the application is started and a save file exists
    And the save file is open in another program
    When I try to save new data to the same file
    Then a clear error message should inform about the locked file
    And the application should not crash
