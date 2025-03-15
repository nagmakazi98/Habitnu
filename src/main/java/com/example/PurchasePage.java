package com.example;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
public class PurchasePage {

    WebDriver driver;
    WebElement totalCost;
    WebElement purchaseButton;
    WebElement confirmationId;

    public PurchasePage(WebDriver driver) {
        this.driver = driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        totalCost = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("p:nth-child(7)>em")));
        // totalCost = driver.findElement(By.cssSelector("p:nth-child(7)>em"));
        System.out.println("Total cost: " + totalCost.getText());

        purchaseButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='submit']")));
        confirmationId = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("tr:nth-child(1) > td:nth-child(2)")));
    }

    public String getTotalCost() {
        return totalCost.getText();
    }

    public void clickPurchaseFlight() {
        purchaseButton.click();
    }

    public String getConfirmationId() {
        return confirmationId.getText();
    }
    
}
