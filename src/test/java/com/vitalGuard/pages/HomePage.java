package com.vitalGuard.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "/html/body/header/nav/div[2]/a[1]")
	WebElement navLink1;
	@FindBy(xpath = "/html/body/header/nav/div[2]/a[2]")
	WebElement navLink2;
	@FindBy(xpath = "/html/body/header/nav/div[2]/a[3]")
	WebElement navLink3;
	@FindBy(xpath = "/html/body/header/nav/div[2]/a[4]")
	WebElement navLink4;
	@FindBy(xpath = "/html/body/header/nav/div[2]/a[5]")
	WebElement navLink5;
	
	
	@FindBy(xpath = "//*[@id='age']")
	WebElement ageInput;

	@FindBy(xpath = "//*[@id='age-error']")
	WebElement ageError;

	@FindBy(xpath = "//*[@id=\"container\"]/div[2]/button")
	WebElement calculateBtn;
	
	@FindBy(xpath = "//*[@id='pulse']")
	WebElement pulseInput;

	@FindBy(xpath = "//*[@id='pulse-error']")
	WebElement pulseError;
	
	@FindBy(xpath = "//*[@id='bp']")
	WebElement bpInput;

	@FindBy(xpath = "//*[@id='bp-error']")
	WebElement bpError;

	public void clickNavLink(String numXPath) {
		switch (numXPath) {
		case "1":
			navLink1.click();
			break;
		case "2":
			navLink2.click();
			break;
		case "3":
			navLink3.click();
			break;
		case "4":
			navLink4.click();
			break;
		case "5":
			navLink5.click();
			break;
		default:
			throw new IllegalArgumentException("Invalid link number: " + numXPath);
		}
	}

	public String getCurrentUrlFragment() {
		String currentUrl = driver.getCurrentUrl();
		int hashIndex = currentUrl.indexOf("#");
		return (hashIndex != -1) ? currentUrl.substring(hashIndex + 1) : "";
	}
	
	

	public void enterAge(String age) {
	    ageInput.clear();
	    ageInput.sendKeys(age);
	    
	}

	public String getAgeErrorMessage() {
	    return ageError.getText().trim();
	}
	
	

	public void enterPulseRate(String pulse) {
	    pulseInput.clear();
	    pulseInput.sendKeys(pulse);
	    
	}

	public String getPulseErrorMessage() {
	    return pulseError.getText().trim();
	}
	

	public void enterBloodPressure(String bp) {
	    bpInput.clear();
	    bpInput.sendKeys(bp);
	   
	}

	public String getBPErrorMessage() {
	    return bpError.getText().trim();
	}
	
	public void clickCalculateBtn() {
		calculateBtn.click();
	}




}
