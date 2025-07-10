package com.vitalGuard.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ResultPage {
	WebDriver driver;

    public ResultPage(WebDriver driver) {
        this.driver = driver;
    }

    By resultField = By.id("healthbox");
    By resultField1 = By.id("feedback");

    public String getCurrentUrlFragment() {
		String currentUrl = driver.getCurrentUrl();	
		return currentUrl;
	}

    public String getResult() {
        return driver.findElement(By.id("healthbox")).getAttribute("value");
    }
    public String getResult1() {
        return driver.findElement(By.id("feedback")).getAttribute("value");
    }
}
