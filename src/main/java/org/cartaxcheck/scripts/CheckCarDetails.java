package org.cartaxcheck.scripts;

import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.asserts.SoftAssert;

import java.util.*;

public class CheckCarDetails extends Base
{
    public CheckCarDetails(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//h1")
    private WebElement carHistoryCheck;

    @FindBy(id = "vrm-input")
    private WebElement inputVehicleNumber;

    @FindBy(xpath = "//button")
    private WebElement checkVehicleBtn;

    private By orderSummaryList = By.xpath("//dl");
    private By header = By.xpath("//dt");
    private By value = By.xpath("//dd");

    @FindBy(xpath = "//h1")
    private WebElement orderSummaryPage;

    public CheckCarDetails navigateToSite(String url)
    {
        driver.navigate().to(url);
        waitForPageToLoad();
        Assert.assertEquals("Car History Check", carHistoryCheck.getText());
        waitForPageToLoad();
        return getPage(CheckCarDetails.class);
    }

    public List<VehicleInfo> getVehicleDetailsList(List<String> vehicleNumbers)
    {
        List<VehicleInfo> vehicleInfoList = new ArrayList<>();

        for (String vehicleNumber : vehicleNumbers) {
            if (!carHistoryCheck.getText().equalsIgnoreCase("Car History Check"))
            {
                driver.navigate().back();
            }

            waitForVisibilityOfElement(inputVehicleNumber);
            enterText(inputVehicleNumber, vehicleNumber);
            waitForElementTobeClickable(checkVehicleBtn);
            checkVehicleBtn.click();
            List<WebElement> webElements = getElements(orderSummaryList);

            Map<String, String> registrationElements = new HashMap<>();
            for (WebElement webElement : webElements) {
                if ((null != webElement.getText()) && (webElement.getText().contains("Registration") || webElement.getText().contains("Vehicle") || webElement.getText().contains("Year") || webElement.getText().contains("Colour"))) {
                    String[] elements = webElement.getText().split("\n");
                    if (elements.length == 2) {

                        if (elements[0].equalsIgnoreCase("Vehicle")) {
                            String[] makeModel = elements[1].split(" ", 2);
                            registrationElements.put("Make", makeModel[0]);
                            registrationElements.put("Model", makeModel[1]);
                        } else {
                            registrationElements.put(elements[0], elements[1]);
                        }
                    }
                }
            }

            vehicleInfoList.add(new VehicleInfo(registrationElements.get("Registration"),
                    registrationElements.get("Make"),
                    registrationElements.get("Model"),
                    registrationElements.get("Color"),
                    registrationElements.get("Year")));
        }

        return vehicleInfoList;
    }

    public Boolean validateVehicleDetails(List<VehicleInfo> expectedVehiclesList, List<VehicleInfo> actualVehiclesList) {
        Collections.sort(expectedVehiclesList);
        Collections.sort(actualVehiclesList);

        expectedVehiclesList.removeIf(Objects::isNull);
        actualVehiclesList.removeIf(Objects::isNull);
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < expectedVehiclesList.size(); i++) {
            VehicleInfo expectedvehicle = expectedVehiclesList.get(i);
            VehicleInfo actualvehicle = expectedVehiclesList.get(i);
            if ((expectedvehicle != null && expectedvehicle.getRegistrationNumber() != null)
                    && (actualvehicle != null && actualvehicle.getRegistrationNumber() != null))
            softAssert.assertEquals(expectedvehicle.getRegistrationNumber(), actualvehicle.getRegistrationNumber());
        }
        softAssert.assertAll();
        return true;
    }
}
