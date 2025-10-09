import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ErrorHandlingStepDefs {
    @Given("the application is started and main window is visible")
    public void theApplicationIsStartedAndMainWindowIsVisible() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("I enter {string} in the competitor name field")
    public void iEnterInTheCompetitorNameField(String arg0) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("I select {string} from discipline dropdown")
    public void iSelectFromDisciplineDropdown(String arg0) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("I enter {string} in the result field")
    public void iEnterInTheResultField(String arg0) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("I click the {string} button")
    public void iClickTheButton(String arg0) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("an error message should be displayed informing about invalid value")
    public void anErrorMessageShouldBeDisplayedInformingAboutInvalidValue() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
