package org.cartaxcheck.steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.cartaxcheck.scripts.VehicleInfo;
import org.cartaxcheck.scripts.CheckCarDetails;
import org.cartaxcheck.scripts.ReadFiles;
import org.cartaxcheck.scripts.Driver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.util.List;

import static org.cartaxcheck.scripts.ReadFiles.readInputFile;
import static org.cartaxcheck.scripts.Constants.APPLICATION_URL;

public class VehicleDetailsSteps extends BaseStepDefinition {
    private static List<String> vehicleRegistrationNumbers;
    private static List<VehicleInfo> expectedVehicleDetails;
    private static List<VehicleInfo> actualVehicleDetails;
    @Given("^read and extract vehicle registration numbers from car input file$")
    public void readAndExtractVehicleRegistrationNumbersFromCarInputFile() throws IOException {
        vehicleRegistrationNumbers = readInputFile();
    }

    @And("^I am on car tax check page$")
    public void iAmOnCarTaxCheckPage() {
        checkCarDetails = checkCarDetails.navigateToSite(APPLICATION_URL);
    }

    @When("^I enter each registration and extract the vehicle details$")
    public void iEnterEachRegistrationAndExtractTheVehicleDetails() {
        actualVehicleDetails = checkCarDetails.getVehicleDetailsList(vehicleRegistrationNumbers);
    }

    @Then("^compare vehicle details with output file$")
    public void compareVehicleDetailsWithOutputFile() throws IOException {
        expectedVehicleDetails = ReadFiles.readOutputFile();
        System.out.println("EXPECTED RESULT: " + expectedVehicleDetails.toString());
        System.out.println("ACTUAL RESULT: " + actualVehicleDetails.toString());
        checkCarDetails.validateVehicleDetails(expectedVehicleDetails, actualVehicleDetails);
    }

    @Before
    public void setUp() throws Exception
    {
        driver = (RemoteWebDriver) Driver.getDriver(Driver.BrowserDriver.CHROME);
        driver.manage().deleteAllCookies();
        checkCarDetails = new CheckCarDetails(driver);
    }

    @After
    public void tearDown(Scenario scenario) throws Exception
    {
        System.out.println("Execution Finished!");
    }
}
