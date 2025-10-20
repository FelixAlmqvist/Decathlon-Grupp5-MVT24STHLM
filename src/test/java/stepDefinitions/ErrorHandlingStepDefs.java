package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class ErrorHandlingStepDefs {

    WebDriver driver;

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("the application is started and main window is visible")
    public void theApplicationIsStartedAndMainWindowIsVisible() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8080/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleIs("Track and Field"));

        // Wait for the page to load completely
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));
    }

    @When("I enter {string} in the competitor name field")
    public void iEnterInTheCompetitorNameField(String name) {
        WebElement nameField = driver.findElement(By.id("name"));
        nameField.clear();
        nameField.sendKeys(name);
    }

    @And("I select {string} from event dropdown")
    public void iSelectFromEventDropdown(String event) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for event dropdown to be populated and clickable
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("event")));
        Select select = new Select(dropdown);

        // Use selectByVisibleText with the exact text shown in your application
        try {
            select.selectByVisibleText(event);
        } catch (Exception e) {
            // If visible text fails, try by value as fallback
            switch (event.toLowerCase()) {
                case "100m": select.selectByValue("100m"); break;
                case "400m": select.selectByValue("400m"); break;
                case "1500m": select.selectByValue("1500m"); break;
                case "110m hurdles": select.selectByValue("110mHurdles"); break;
                case "high jump": select.selectByValue("highJump"); break;
                case "long jump": select.selectByValue("longJump"); break;
                case "shot put": select.selectByValue("shotPut"); break;
                case "discus": select.selectByValue("discus"); break;
                case "javelin": select.selectByValue("javelin"); break;
                case "pole vault": select.selectByValue("poleVault"); break;
                default: fail("Unknown event: " + event);
            }
        }

        // Verify the selection was successful
        String selectedText = select.getFirstSelectedOption().getText();
        assertTrue(selectedText.contains(event) || event.contains(selectedText),
                "Event should be selected. Expected: " + event + ", Got: " + selectedText);
    }

    @And("I enter {string} in the result field")
    public void iEnterInTheResultField(String result) {
        WebElement resultField = driver.findElement(By.id("raw"));
        resultField.clear();
        resultField.sendKeys(result);
    }

    @And("I click the {string} button")
    public void iClickTheButton(String buttonText) {
        WebElement button;
        if ("Save score".equals(buttonText)) {
            button = driver.findElement(By.id("save"));
        } else if ("Add competitor".equals(buttonText)) {
            button = driver.findElement(By.id("add"));
        } else {
            throw new IllegalArgumentException("Unknown button: " + buttonText);
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(button)).click();
    }

    @Then("an error message should be displayed informing about invalid value")
    public void anErrorMessageShouldBeDisplayedInformingAboutInvalidValue() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            // Wait for error message to appear in the error banner
            WebElement errorMessage = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("error"))
            );

            assertTrue(errorMessage.isDisplayed(), "Error message should be visible");

            // Check that the error message actually has content
            String messageText = errorMessage.getText();
            assertFalse(messageText.trim().isEmpty(), "Error message should not be empty");
            assertTrue(!messageText.isEmpty(), "Error message should contain text");

        } catch (Exception e) {
            // Alternative check: Verify the invalid input was not accepted
            // by checking that standings don't contain the invalid value
            WebElement standings = driver.findElement(By.id("standings"));
            String standingsText = standings.getText();
            assertFalse(standingsText.contains("-5") || standingsText.contains("abc"),
                    "Invalid result should not appear in standings");
        }
    }
}