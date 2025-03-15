package com.example;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlightSelectionPage {

    WebDriver driver;
    List<WebElement> prices;
    List<WebElement> chooseFlightButton;

    public FlightSelectionPage(WebDriver driver) {
        this.driver = driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Wait for the flights table to load using XPath
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[contains(@class, 'table')]")));

        // Wait for flight prices to load
        // prices = (List<WebElement>) wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("td:nth-child(7)")));

        prices = driver.findElements(By.cssSelector("td:nth-child(7)"));

        // Debugging: Print the number of flight prices found
        System.out.println("Number of flight prices found: " + prices.size());
        for (WebElement price : prices) {
            System.out.println("Flight price: " + price.getText());
        }

        // Wait for the "Choose Flight" button to load
        // chooseFlightButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='submit']")));
        List<WebElement> chooseFlightButton = driver.findElements(By.xpath("//input[@value='Choose This Flight']"));

        System.out.println("Button is located");
    }

    /**
     * Selects the flight with the lowest price.
     */
    public void selectLowestPriceFlight() {
        double minPrice = Double.MAX_VALUE;
        WebElement selectedFlight = null;

        // Iterate through all flight prices to find the lowest one
        for (WebElement price : prices) {
            double currentPrice = Double.parseDouble(price.getText().replace("$", ""));
            if (currentPrice < minPrice) {
                minPrice = currentPrice;
                selectedFlight = price;
            }
        }

        // Click the selected flight
        if (selectedFlight != null) {
            selectedFlight.click();
        } else {
            throw new RuntimeException("No flights found.");
        }
    }

    /**
     * Clicks the "Choose Flight" button.
     */
    // public void clickChooseFlight() {
    //     chooseFlightButton.click();
    //     System.out.println("Button is clicked");
    // }
}