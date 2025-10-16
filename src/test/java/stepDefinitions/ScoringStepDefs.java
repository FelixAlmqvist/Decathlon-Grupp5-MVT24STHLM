package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoringStepDefs {

    WebDriver driver;
    boolean decathlon = true;
    private String name_ = "";

    @After
    public void tearDown() {
        if (driver != null) {   
            driver.quit();     
        }
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
        name_ = name;
        driver.findElement(By.cssSelector("#name")).sendKeys(name);
        driver.findElement(By.cssSelector("#add")).click();
    }


    @And("I pick competition {string}")
    public void chooseCompetition(String competition) {
        WebElement tmp = driver.findElement(By.cssSelector("#mode"));
        Select dropdown = new Select(tmp);
        if(competition.equalsIgnoreCase("hepta")){
            dropdown.selectByValue("HEP");
            decathlon = false;
        }else if(competition.equalsIgnoreCase("deca")){
            dropdown.selectByValue("DEC");
            decathlon = true;
        }
    }

    @And("I pick sport {string}")
    public void fillInSport(String sport) {
        WebElement tmp = driver.findElement(By.cssSelector("#event"));
        driver.findElement(By.cssSelector("#name2")).sendKeys(name_);
        Select dropdown = new Select(tmp);
        if (decathlon) {
            switch (sport.toLowerCase()) {
                case "100m":
                    dropdown.selectByValue("100m");
                    break;
                case "110mhurdles":
                    dropdown.selectByValue("110mHurdles");
                    break;
                case "400m":
                    dropdown.selectByValue("400m");
                    break;
                case "1500m":
                    dropdown.selectByValue("1500m");
                    break;
                case "discus":
                    dropdown.selectByValue("discus");
                    break;
                case "highjump":
                    dropdown.selectByValue("highJump");
                    break;
                case "javelin":
                    dropdown.selectByValue("javelin");
                    break;
                case "longjump":
                    dropdown.selectByValue("longJump");
                    break;
                case "polevault":
                    dropdown.selectByValue("poleVault");
                    break;
                case "shotput":
                    dropdown.selectByValue("shotPut");
                    break;
                default:
                    System.out.println("No sport under: " + sport + " in Decathlon");
            }

        } else {
            switch (sport.toLowerCase()) {
                case "100mhurdles":
                    dropdown.selectByValue("100mHurdles");
                    break;
                case "200m":
                    dropdown.selectByValue("200m");
                    break;
                case "800m":
                    dropdown.selectByValue("800m");
                    break;
                case "highjump":
                    dropdown.selectByValue("highJump");
                    break;
                case "javelin":
                    dropdown.selectByValue("javelin");
                    break;
                case "longjump":
                    dropdown.selectByValue("longJump");
                    break;
                case "shotput":
                    dropdown.selectByValue("shotPut");
                    break;

            }

        }

    }

    @And("I put in my {string}")
    public void PutInResult(String result) {
        driver.findElement(By.cssSelector("#raw")).sendKeys(result);

    }

    @Then("I submit and verify {string}")
    public void SubmitAndVerify(String addedScore) {
        String oldScore = findTotalscoreOfCompetitor(name_, "0");
        driver.findElement(By.cssSelector("#save")).click();
        waitForRefresh(driver,By.cssSelector("#standings tr"));
        String newTotal = findTotalscoreOfCompetitor(name_, oldScore);
        assertEquals(addedScore,String.valueOf(Integer.parseInt(newTotal)-Integer.parseInt(oldScore)));
    }

    public String findTotalscoreOfCompetitor(String name, String oldScore){
        while(true) {
            List<WebElement> standings = driver.findElements(By.cssSelector("#standings tr"));
            try{
                for (WebElement cell : standings) {


                    if (cell.getText().contains(name)) {
                        String s = cell.getText();
                        String[] sArray = s.split(" ");
                        if (sArray[sArray.length - 1].equals(oldScore)) {
                            waitForRefresh(driver, By.cssSelector("#standings tr"));
                        }

                        return sArray[sArray.length - 1];
                    }
                }

            fail("Competitor not found: " + name_);
            return null;
            }catch (StaleElementReferenceException e) {
                System.out.println("standings uppdaterades under sökning av element");
            }
        }
    }

    private static void waitForRefresh(WebDriver driver, By by){
        WebElement element = (new WebDriverWait(driver, Duration.ofSeconds(5))).until(
                            ExpectedConditions.refreshed(
                                    ExpectedConditions.visibilityOfElementLocated(by)));
    }

}
