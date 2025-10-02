package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
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
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoringStepDefs {

    WebDriver driver;
    boolean decathlon = true;

    @After
    public void tearDown() {
        assert driver != null;
        //driver.quit();
    }

    @Given("That i am on the correct page on {string}")
    public void startWebsite(String browser) {
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
        assertEquals("Name", driver.findElement(By.cssSelector("label[for='name']")).getText());
        //Tycker inte om att kolla på name fältet men ingen ID eller speciel class för title
    }

    @When("I fill in {string}")
    public void fillInName(String name) {
        //eventually add a check for if already in standings
        driver.findElement(By.cssSelector("#name")).sendKeys(name);
        driver.findElement(By.cssSelector("#add")).click();
        driver.findElement(By.cssSelector("#name2")).sendKeys(name);
    }

    @And("I pick {string}")
    public void fillInSport(String sport) {
        WebElement tmp = driver.findElement(By.cssSelector("#event"));
        Select dropdown = new Select(tmp);;
        if (decathlon) {
            switch (sport.toLowerCase()) {
                case "100m":
                    dropdown.selectByValue("100m");
                    break;
                case "110mhurdles":
                    break;
                case "400m":
                    dropdown.selectByValue("400m");
                    break;
                case "1500m":
                    break;
                case "discusthrow":
                    break;
                case "highjump":
                    break;
                case "javelinthrow":
                    break;
                case "longjump":
                    dropdown.selectByValue("longJump");
                    break;
                case "polevault":
                    break;
                case "shotput":
                    dropdown.selectByValue("shotPut");
                    break;
                default:
                    System.out.println("No sport under: " + sport + " in Decathlon");
            }

        } else { //no way to choose here yet revist once availible
            switch (sport) {
                case "100m":
                    break;

            }

        }

    }

    @And("I put in my {string}")
    public void PutInResult(String result) {
        driver.findElement(By.cssSelector("#raw")).sendKeys(result);

    }

    @Then("I submit and verify {string}")
    public void SubmitAndVerify(String totalScore) {
        driver.findElement(By.cssSelector("#save")).click();

        assertEquals(totalScore, findTotalscoreOfCompetitor(driver.findElement(By.cssSelector("#name2")).getText()));
    }

    public String findTotalscoreOfCompetitor(String name){
        List<WebElement> standings = driver.findElements(By.cssSelector("#standings tr"));
        for(WebElement cell : standings)
        {
            if(cell.getText().contains(name))
            {

                List<WebElement> cells = driver.findElements(By.tagName("td"));
                return cells.getLast().getText();
            }
        }
        return null;
    }
}
