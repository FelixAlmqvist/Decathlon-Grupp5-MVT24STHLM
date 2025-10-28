package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class DataManagementStepDefs {

    WebDriver driver;

    @Given("the application is started with test data")
    public void theApplicationIsStartedWithTestData() {
        // We need to implement these methods or call existing ones
        // If these methods exist in other step definition classes,
        // we'll need to recreate them here or use composition
        theApplicationIsStartedAndMainWindowIsVisible();

        // Add some test data
        iEnterInTheCompetitorNameField("Test Competition 1");
        iSelectFromEventDropdown("100m");
        iEnterInTheResultField("11.5");
        iClickTheButton("Save score");
    }

    @When("I create a complex session with multiple competitors and events")
    public void iCreateAComplexSessionWithMultipleCompetitorsAndEvents() {
        // Add multiple competitors
        String[] competitors = {"Alice", "Bob", "Charlie"};
        // TODO: Implement the rest of this method
    }

    // We need to add these methods since they're being called above
    public void theApplicationIsStartedAndMainWindowIsVisible() {
        // We can copy implementation from ErrorHandlingStepDefs or create our own
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8080/");
    }

    public void iEnterInTheCompetitorNameField(String name) {
        WebElement nameField = driver.findElement(By.id("name"));
        nameField.clear();
        nameField.sendKeys(name);
    }

    public void iSelectFromEventDropdown(String event) {
        WebElement dropdown = driver.findElement(By.id("event"));
    }

    public void iEnterInTheResultField(String result) {
        WebElement resultField = driver.findElement(By.id("raw"));
        resultField.clear();
        resultField.sendKeys(result);
    }

    public void iClickTheButton(String buttonText) {
        WebElement button;
        if ("Save score".equals(buttonText)) {
            button = driver.findElement(By.id("save"));
        } else if ("Add competitor".equals(buttonText)) {
            button = driver.findElement(By.id("add"));
        } else {
            throw new IllegalArgumentException("Unknown button: " + buttonText);
        }
        button.click();
    }

}
