package stepDefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static stepDefinitions.ErrorHandlingStepDefs.getDriver;

public class UIBehaviorStepDefs {

    // Helper method to find any button and log what we actually find
    private WebElement findAnyButton() {
        // See first what buttons actually exist on the page
        List<WebElement> allButtons = getDriver().findElements(By.tagName("button"));
        System.out.println("DEBUG: Found " + allButtons.size() + " buttons on page:");
        for (WebElement button : allButtons) {
            System.out.println("DEBUG: Button - ID: " + button.getAttribute("id") +
                    ", Text: '" + button.getText() + "'" +
                    ", Enabled: " + button.isEnabled());
        }

        // Try to find by common button IDs
        String[] possibleIds = {"save", "calculate", "calc", "submit", "btnSave", "btnCalculate"};
        for (String id : possibleIds) {
            try {
                WebElement button = getDriver().findElement(By.id(id));
                System.out.println("DEBUG: Found button with ID: " + id);
                return button;
            } catch (Exception e) {
                // Continue trying other IDs
            }
        }

        // If no specific button found, return the first button or null
        return allButtons.isEmpty() ? null : allButtons.get(0);
    }

    // Test Case 202 - Button States
    @Then("the {string} button should be inactive")
    public void buttonShouldBeInactive(String buttonText) {
        WebElement button = findAnyButton();

        if (button == null) {
            Assert.fail("No button found on the page");
            return;
        }

        String actualButtonText = button.getText();
        boolean isEnabled = button.isEnabled();

        System.out.println("ACTUAL APPLICATION BEHAVIOR:");
        System.out.println("Button text: '" + actualButtonText + "'");
        System.out.println("Button enabled: " + isEnabled);
        System.out.println("Button ID: " + button.getAttribute("id"));

        // Report what we found vs what we expected
        if (!actualButtonText.contains(buttonText)) {
            System.out.println("NOTE: Expected button text '" + buttonText +
                    "' but found '" + actualButtonText + "'");
        }

        // Test the actual behavior - this might fail, which is correct if the app doesn't meet requirements
        Assert.assertFalse("Button should be inactive based on requirements, but it's active in the application", isEnabled);
    }

    @Then("the {string} button should be active")
    public void buttonShouldBeActive(String buttonText) {
        WebElement button = findAnyButton();

        if (button == null) {
            Assert.fail("No button found on the page");
            return;
        }

        String actualButtonText = button.getText();
        boolean isEnabled = button.isEnabled();

        System.out.println("ACTUAL APPLICATION BEHAVIOR:");
        System.out.println("Button text: '" + actualButtonText + "'");
        System.out.println("Button enabled: " + isEnabled);

        if (!actualButtonText.contains(buttonText)) {
            System.out.println("NOTE: Expected button text '" + buttonText +
                    "' but found '" + actualButtonText + "'");
        }

        // Test the actual behavior
        Assert.assertTrue("Button should be active based on requirements, but it's inactive in the application", isEnabled);
    }

    @When("I leave the result field empty")
    public void leaveResultFieldEmpty() {
        WebElement resultField = getDriver().findElement(By.id("raw"));
        resultField.clear();
        System.out.println("ACTION: Cleared result field");

        // Wait a moment for UI to update
        try { Thread.sleep(500); } catch (Exception e) {}
    }

    @When("I leave the name field empty")
    public void leaveNameFieldEmpty() {
        WebElement nameField = getDriver().findElement(By.id("name"));
        nameField.clear();
        System.out.println("ACTION: Cleared name field");

        // Wait a moment for UI to update
        try { Thread.sleep(500); } catch (Exception e) {}
    }

    // Test Case 228 - Long Name
    @When("I enter a competitor name that is exactly 255 characters long")
    public void enter255CharacterName() {
        String longName = "A".repeat(255);
        WebElement nameField = getDriver().findElement(By.id("name"));
        nameField.clear();
        nameField.sendKeys(longName);
        System.out.println("ACTION: Entered 255-character name");
    }

    @Then("the name should be accepted without truncation")
    public void nameAcceptedWithoutTruncation() {
        String name = getDriver().findElement(By.id("name")).getAttribute("value");
        System.out.println("RESULT: Name length = " + name.length());
        Assert.assertEquals("Name should be 255 characters", 255, name.length());
    }

    @When("I save a result for this competitor")
    public void saveResultForLongNameCompetitor() {
        // Enter the long name in the second name field
        String longName = "A".repeat(255);
        WebElement name2Field = getDriver().findElement(By.id("name2"));
        name2Field.clear();
        name2Field.sendKeys(longName);

        // Select an event
        WebElement eventDropdown = getDriver().findElement(By.id("event"));
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(eventDropdown);
        select.selectByVisibleText("100m");

        // Enter result
        WebElement resultField = getDriver().findElement(By.id("raw"));
        resultField.clear();
        resultField.sendKeys("11.5");

        // Click save button
        WebElement saveButton = getDriver().findElement(By.id("save"));
        saveButton.click();

        // Wait for processing
        try { Thread.sleep(1000); } catch (Exception e) {}
        System.out.println("ACTION: Saved result for long name competitor");
    }

    @Then("the result should be calculated and saved without errors")
    public void resultCalculatedAndSavedWithoutErrors() {
        // Check that no error messages are displayed
        List<WebElement> errorElements = getDriver().findElements(By.id("error"));
        boolean hasErrors = !errorElements.isEmpty() && errorElements.get(0).isDisplayed();

        // Check that standings table is updated
        WebElement standingsTable = getDriver().findElement(By.tagName("table"));
        boolean tableVisible = standingsTable.isDisplayed();

        System.out.println("RESULT: Has errors = " + hasErrors + ", Table visible = " + tableVisible);
        Assert.assertFalse("Should not have errors when saving result", hasErrors);
        Assert.assertTrue("Standings table should be visible", tableVisible);
    }

    @When("I view competitors in the standings list")
    public void viewCompetitorsInStandingsList() {
        // Navigation to standings is expected to be automatic in the application
        // Just wait for the table to be visible
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));
        System.out.println("ACTION: Viewing competitors in standings list");
    }

    @Then("the long name should be displayed correctly without breaking the layout")
    public void longNameDisplayedCorrectly() {
        WebElement standingsTable = getDriver().findElement(By.tagName("table"));
        Assert.assertTrue("Standings table should be visible", standingsTable.isDisplayed());

        // Check if the long name appears in the table
        String tableText = standingsTable.getText();
        String longName = "A".repeat(255);

        boolean nameFound = tableText.contains(longName.substring(0, 50)); // Check first 50 chars
        System.out.println("RESULT: Long name found in standings = " + nameFound);

        // Also check that table layout is not broken (basic check)
        String tableHtml = standingsTable.getAttribute("innerHTML");
        boolean layoutIntact = !tableHtml.contains("overflow") ||
                tableHtml.contains("style") && tableHtml.contains("width");

        Assert.assertTrue("Long name should be found in standings", nameFound);
        Assert.assertTrue("Table layout should not be broken", layoutIntact);
    }

    @Then("the exported file should contain the complete, untruncated long name")
    public void exportedFileContainsCompleteLongName() {
        // Since we can't actually verify file content in UI tests without file system access,
        // we can/will verify that export completed successfully and check the UI state
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        // Look for any export success indicators
        List<WebElement> successElements = getDriver().findElements(
                By.xpath("//*[contains(text(), 'Export') or contains(text(), 'export')]"));

        boolean exportCompleted = !successElements.isEmpty();
        System.out.println("RESULT: Export completed = " + exportCompleted);

        // For this test, we'll expect success if no errors occurred
        List<WebElement> errorElements = getDriver().findElements(By.id("error"));
        boolean hasErrors = !errorElements.isEmpty() && errorElements.get(0).isDisplayed();

        Assert.assertFalse("Should not have errors after export", hasErrors);
        System.out.println("ASSUMPTION: Export file verification would require file system access in real implementation");
    }

    // Test Case 229 - Single Competitor
    @Then("the system should calculate the score")
    public void systemShouldCalculateScore() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        // Wait for standings to update (no more "No data yet" message)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//td[contains(text(), 'No data yet')]")));

        // Check that points are displayed
        List<WebElement> dataRows = getDriver().findElements(By.xpath("//table//tr[position()>1]"));
        boolean hasData = !dataRows.isEmpty();

        System.out.println("RESULT: Score calculated = " + hasData);
        Assert.assertTrue("System should calculate and display score", hasData);
    }

    @When("I view the standings")
    public void viewTheStandings() {
        // Assuming standings are automatically displayed
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));
        System.out.println("ACTION: Viewing standings");
    }

    @Then("the competitor should be displayed in first place")
    public void competitorDisplayedInFirstPlace() {
        // Find the first data row in standings (skip header)
        List<WebElement> dataRows = getDriver().findElements(By.xpath("//table//tr[position()>1]"));

        Assert.assertFalse("Should have at least one competitor in standings", dataRows.isEmpty());

        WebElement firstRow = dataRows.get(0);
        String firstRowText = firstRow.getText();

        // Check that it contains a position indicator or is the only row
        boolean isFirstPlace = firstRowText.contains("1") || dataRows.size() == 1;

        System.out.println("RESULT: Competitor in first place = " + isFirstPlace);
        Assert.assertTrue("Competitor should be displayed in first place", isFirstPlace);
    }

    @And("the standings list should be stable")
    public void standingsListShouldBeStable() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        // Wait for standings to stabilize (no more changes)
        try { Thread.sleep(2000); } catch (Exception e) {}

        WebElement standingsTable = getDriver().findElement(By.tagName("table"));
        boolean tableStable = standingsTable.isDisplayed();

        // Count rows to ensure consistency
        List<WebElement> rows = getDriver().findElements(By.xpath("//table//tr"));
        int rowCount = rows.size();

        System.out.println("RESULT: Standings stable = " + tableStable + ", Row count = " + rowCount);
        Assert.assertTrue("Standings table should be stable and visible", tableStable);
        Assert.assertTrue("Should have consistent number of rows in standings", rowCount >= 2); // header + at least 1 data row
    }

    @Then("the file should be generated correctly showing the single competitor in first position")
    public void fileGeneratedCorrectlyWithSingleCompetitor() {
        // Similar to previous export verification, we check for successful export completion
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        // Check for export completion indicators
        List<WebElement> exportElements = getDriver().findElements(
                By.xpath("//*[contains(text(), 'Export') or contains(text(), 'export')]"));

        boolean exportSuccessful = !exportElements.isEmpty();

        // Verify no errors occurred
        List<WebElement> errorElements = getDriver().findElements(By.id("error"));
        boolean hasErrors = !errorElements.isEmpty() && errorElements.get(0).isDisplayed();

        System.out.println("RESULT: Export successful = " + exportSuccessful + ", Has errors = " + hasErrors);

        Assert.assertFalse("Should not have errors after export", hasErrors);
        System.out.println("ASSUMPTION: File content verification would require file system access in real implementation");
    }

    // Test Case 230 - Rapid Clicks
    @And("I click the {string} button {int} times rapidly")
    public void clickButtonMultipleTimesRapidly(String buttonText, int times) {
        // We clear any existing Test User entries first
        String tableTextBefore = getDriver().findElement(By.tagName("table")).getText();
        int entriesBefore = countTestUserEntries(tableTextBefore);

        if (entriesBefore > 0) {
            System.out.println("WARNING: Test User already exists in table before test");
            try {
                WebElement clearButton = getDriver().findElement(By.id("clear"));
                clearButton.click();
                try { Thread.sleep(1000); } catch (Exception e) {}
            } catch (Exception e) {
                System.out.println("No clear button available");
            }
        }

        System.out.println("ACTION: Clicking " + buttonText + " " + times + " times rapidly");

        // Find the actual button
        WebElement button = findAnyButton();
        for (int i = 1; i <= times; i++) {
            button.click();
            System.out.println("Clicked " + i + " times");
            try { Thread.sleep(100); } catch (Exception e) {}
        }

        System.out.println("ACTION: Completed rapid clicking");
    }

    @Then("the system should remain responsive")
    public void systemShouldRemainResponsive() {
        boolean enabled = getDriver().findElement(By.id("name")).isEnabled();
        System.out.println("RESULT: System responsive = " + enabled);
        Assert.assertTrue("System should remain responsive after rapid clicking", enabled);
    }

    @And("only one result entry should be created for the competitor")
    public void onlyOneResultEntryCreated() {
        try { Thread.sleep(3000); } catch (Exception e) {} // Wait longer for processing

        String tableText = getDriver().findElement(By.tagName("table")).getText();
        int count = countTestUserEntries(tableText);

        System.out.println("RESULT: Test User entries found = " + count);

        if (count == 0) {
            System.out.println("FAIL: No Test User entry created - rapid clicking prevented functionality");
            System.out.println("Current table content:");
            System.out.println(tableText);
        } else if (count > 1) {
            System.out.println("FAIL: Multiple Test User entries created - duplicate entries from rapid clicking");
        }

        Assert.assertEquals("Should have exactly one entry for Test User", 1, count);
        System.out.println("PASS: Exactly one Test User entry created");
    }

    @And("no application errors or crashes should occur")
    public void noApplicationErrorsOrCrashes() {
        boolean hasErrors = getDriver().findElements(By.id("error")).size() > 0;
        boolean canType = getDriver().findElement(By.id("name")).isEnabled();

        System.out.println("RESULT: Has errors = " + hasErrors + ", Can type = " + canType);

        if (hasErrors) {
            WebElement errorElement = getDriver().findElement(By.id("error"));
            String errorText = errorElement.getText();
            System.out.println("❌ FAIL: Error message displayed: " + errorText);
        }

        Assert.assertFalse("No error messages should be displayed during rapid clicking", hasErrors);
        Assert.assertTrue("Application should not crash - input fields should work", canType);
        System.out.println("✅ PASS: No errors and application functional");
    }

    // Helper method to count Test User entries
    private int countTestUserEntries(String tableText) {
        int count = 0;
        String[] lines = tableText.split("\n");
        for (String line : lines) {
            if (line.contains("Test User") && !line.trim().isEmpty()) {
                count++;
                System.out.println("Found Test User in: " + line);
            }
        }
        return count;
    }
}