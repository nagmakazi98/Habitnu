package com.example;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AppTest {
    WebDriver driver;
    HomePage homePage;
    FlightSelectionPage flightSelectionPage;
    PurchasePage purchasePage;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://blazedemo.com/index.php");
        homePage = new HomePage(driver);
        flightSelectionPage = new FlightSelectionPage(driver);
        purchasePage = new PurchasePage(driver);
    }

    @DataProvider(name = "flightData")
    public Object[][] provideExcelData() throws IOException {
        ExcelReader reader = new ExcelReader();
        return reader.readExcel("C:\\Nagma.Crio\\Crio Projects\\Nagma_Habitnu_Assigment\\demo\\src\\test\\resources\\testdata.xlsx");
    }

    @Test(dataProvider = "flightData")
    public void testFlightPurchase(String departureCity, String destinationCity) throws IOException {
        try {
            // Step 1: Verify Home Page Title
            Assert.assertTrue(homePage.isTitleDisplayed(), "Home page title is not displayed.");

            // Step 2: Click on Destination Link and Navigate Back
            homePage.clickDestinationLink();
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("vacation"), "New tab URL does not contain 'vacation'.");
            driver.navigate().back();

            // Step 3: Purchase Ticket
            homePage.selectCities(departureCity, destinationCity);
            homePage.clickFindFlights();
            flightSelectionPage.selectLowestPriceFlight();
            flightSelectionPage.clickChooseFlight();

            // Step 4: Verify Total Cost and Purchase Flight
            String totalCost = purchasePage.getTotalCost();
            Assert.assertTrue(totalCost.matches("\\d{3}\\.\\d{2}"), "Total cost is not in the correct format.");
            purchasePage.clickPurchaseFlight();

            // Step 5: Store Confirmation ID
            String confirmationId = purchasePage.getConfirmationId();
            System.out.println("Confirmation ID: " + confirmationId);
        } catch (AssertionError e) {
            Screenshots.captureScreenshot(driver, "testFailure");
            throw e;
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}