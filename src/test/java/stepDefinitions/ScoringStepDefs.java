package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.*;

public class ScoringStepDefs {

    WebDriver driver;

    @After
    public void tearDown(){
        assert driver != null;
        driver.quit();
    }

    @Given("That i am on the correct page on {string}")
    public void startWebsite(String browser){
        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                System.out.println("faulty name for browser picked");
                fail();
        }
        driver.get("http://localhost:8080/");
        //hitta en assert för att dubbelkolla att hemsidan är uppe
    }

}
