package com.example;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    WebDriver driver;
    WebElement pageTitle;
    WebElement destinationLink;
    WebElement departureCity;
    WebElement destinationCity;
    WebElement findFlightsButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        pageTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[contains(text(), 'Welcome to the Simple Travel Agency!')]")));
        destinationLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@href='vacation.html']")));
        departureCity = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("fromPort")));
        destinationCity = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("toPort")));
        findFlightsButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='submit']")));
    }

    public boolean isTitleDisplayed() {
        return pageTitle.isDisplayed();
    }

    public void clickDestinationLink() {
        destinationLink.click();
    }

    public void selectCities(String from, String to) {
        departureCity.sendKeys(from);
        destinationCity.sendKeys(to);
    }

    // public void clickFindFlights() {
    //     hardClick(driver, findFlightsButton);
    // }
    // public static void hardClick(WebDriver driver, WebElement element) {
    //     JavascriptExecutor js = (JavascriptExecutor) driver;
    //     js.executeScript("arguments[0].click();", element);
    // }

    public void clickFindFlights() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(findFlightsButton));
        System.out.println("Element is clickable. Attempting to click using Actions class...");
        scrollIntoView(driver, findFlightsButton);
        Actions actions = new Actions(driver);
        actions.moveToElement(findFlightsButton).click().perform();
        System.out.println("Click performed using Actions class.");
    }
    private static void scrollIntoView(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }
    
}
