package com.vitalGuard.tests;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.vitalGuard.pages.HomePage;
import com.vitalGuard.pages.ResultPage;
import com.vitalGuard.utils.DriverManager;
import com.vitalGuard.utils.ExcelReader;
import com.vitalGuard.utils.ExcelWriter;
//import com.vitalGuard.utils.TestListener;
import org.testng.annotations.Listeners;
import com.vitalGuard.utils.ExtentTestListener;

@SuppressWarnings("unused")


@Listeners(ExtentTestListener.class)


public class TestRunner {

	WebDriver driver;
	DriverManager driverManager = new DriverManager();

	List<String> resultData = new ArrayList<>();

	public WebDriver getDriver() {
		return driver;
	}

	@BeforeTest
	@Parameters("browser")
	public void setup(@Optional("chrome") String browser) {
		driver = driverManager.createDriver(browser);
	}

	@DataProvider(name = "testData")
	public Object[][] getTestData() {
		String filePath = "./src/resources/VitalGuard (3).xlsx";
		return ExcelReader.readExcel(filePath, "Sheet1");
	}

	@DataProvider(name = "linkData")
	public Object[][] linkData() {
		Object[][] data = new Object[5][2];
		data[0][0] = "1";
		data[0][1] = "container";
		data[1][0] = "2";
		data[1][1] = "pulseRate";
		data[2][0] = "3";
		data[2][1] = "bloodPressure";
		data[3][0] = "4";
		data[3][1] = "ageFactor";
		data[4][0] = "5";
		data[4][1] = "foot";
		return data;
	}

	@DataProvider(name = "invalidAgeData")
	public Object[][] invalidAgeData() {
		return new Object[][] {
				// {"", "Age is required"}, // Empty input
				{ "0", "Age must be between 1 and 120." }, // Below minimum
				{ "121", "Age must be between 1 and 120." }, // Above maximum
				{ "-5", "Age must be between 1 and 120." }, // Negative number
				// {"abc", "Age must be a number"}, // Non-numeric
				{ "12.5", "Age must be an integer." }, // Decimal
				// {" ", "Age is required"}, // Space
				// {"@#", "Age must be a number"}, // Special characters
				{ "999", "Age must be between 1 and 120." }// Far above max
				// {"1 2", "Age must be a number"} // Mixed input
		};
	}
	
	@DataProvider(name = "invalidAgeAlertData")
	public Object[][] invalidAgeAlertData() {
		return new Object[][] {
				// {"", "Age is required"}, // Empty input
				{ "0", "Please enter a valid age between 1 and 120." }, // Below minimum
				{ "121", "Please enter a valid age between 1 and 120." }, // Above maximum
				{ "-5", "Please enter a valid age between 1 and 120." }, // Negative number
				// {"abc", "Age must be a number"}, // Non-numeric
				{ "12.5", "Please enter a valid age between 1 and 120." }, // Decimal
				// {" ", "Age is required"}, // Space
				// {"@#", "Age must be a number"}, // Special characters
				{ "999", "Please enter a valid age between 1 and 120." }// Far above max
				// {"1 2", "Age must be a number"} // Mixed input
		};
	}

	@DataProvider(name = "invalidPulseData")
	public Object[][] invalidPulseData() {
		return new Object[][] {
				// {"", "Pulse rate must be a number."}, // Empty
				{ "39", "Pulse rate should be between 40 and 180 bpm." }, // Below min
				{ "181", "Pulse rate should be between 40 and 180 bpm." }, // Above max
				{ "-10", "Pulse rate should be between 40 and 180 bpm." }, // Negative
				{ "abc", "Pulse rate must be a number." }, // Non-numeric
				{ "100.5", "Pulse rate must be an integer." }, // Decimal
				{ " ", "Pulse rate must be a number." }, // Space
				{ "@#", "Pulse rate must be a number." }, // Special characters
				{ "999", "Pulse rate should be between 40 and 180 bpm." }, // Far above max
				// {"40 50", "Pulse rate must be a number"} // Mixed input
		};
	}
	
	@DataProvider(name = "invalidPulseAlertData")
	public Object[][] invalidPulseAlertData() {
		return new Object[][] {
				// {"", "Pulse rate must be a number."}, // Empty
				{ "39", "Please enter a valid pulse rate between 40 and 180 bpm." }, // Below min
				{ "181", "Please enter a valid pulse rate between 40 and 180 bpm." }, // Above max
				{ "-10", "Please enter a valid pulse rate between 40 and 180 bpm." }, // Negative
				{ "abc", "Please enter a valid pulse rate between 40 and 180 bpm." }, // Non-numeric
				{ "100.5", "Please enter a valid pulse rate between 40 and 180 bpm." }, // Decimal
				{ " ", "Please enter a valid pulse rate between 40 and 180 bpm." }, // Space
				{ "@#", "Please enter a valid pulse rate between 40 and 180 bpm." }, // Special characters
				{ "999", "Please enter a valid pulse rate between 40 and 180 bpm." }, // Far above max
				// {"40 50", "Pulse rate must be a number"} // Mixed input
		};
	}

	@DataProvider(name = "invalidBPData")
	public Object[][] invalidBPData() {
		return new Object[][] { { "", "Use format: systolic/diastolic (e.g., 120/80)" }, // Empty
				{ "120", "Use format: systolic/diastolic (e.g., 120/80)" }, // Missing diastolic
				{ "120/", "Use format: systolic/diastolic (e.g., 120/80)" }, // Missing diastolic value
				{ "/80", "Use format: systolic/diastolic (e.g., 120/80)" }, // Missing systolic value
				{ "abc/xyz", "Use format: systolic/diastolic (e.g., 120/80)" }, // Non-numeric
				{ "150/80", "Systolic must be between 90 and 140" }, // Systolic too high
				{ "85/80", "Systolic must be between 90 and 140" }, // Systolic too low
				{ "120/95", "Diastolic must be between 60 and 90" }, // Diastolic too high
				{ "120/55", "Diastolic must be between 60 and 90" }, // Diastolic too low
				{ "120.5/80", "Use format: systolic/diastolic (e.g., 120/80)" }, // Decimal systolic
				{ "120/80.5", "Use format: systolic/diastolic (e.g., 120/80)" }, // Decimal diastolic
				{ "120 80", "Use format: systolic/diastolic (e.g., 120/80)" }, // Space instead of slash
				{ "120-80", "Use format: systolic/diastolic (e.g., 120/80)" }, // Hyphen instead of slash
				{ "@120/@80", "Use format: systolic/diastolic (e.g., 120/80)" }, // Special characters
				{ "999/999", "Systolic must be between 90 and 140" } // Both values far above range
		};
	}
	
	@DataProvider(name = "invalidBPAlertData")
	public Object[][] invalidBPAlertData() {
		return new Object[][] { { "", "Please enter blood pressure in the format systolic/diastolic (e.g., 120/80)." }, // Empty
				{ "120", "Please enter blood pressure in the format systolic/diastolic (e.g., 120/80)." }, // Missing diastolic
				{ "120/", "Please enter blood pressure in the format systolic/diastolic (e.g., 120/80)." }, // Missing diastolic value
				{ "/80", "Please enter blood pressure in the format systolic/diastolic (e.g., 120/80)." }, // Missing systolic value
				{ "abc/xyz", "Please enter blood pressure in the format systolic/diastolic (e.g., 120/80)." }, // Non-numeric
				{ "150/80", "Please enter blood pressure in the format systolic/diastolic (e.g., 120/80)." }, // Systolic too high
				{ "85/80", "Please enter blood pressure in the format systolic/diastolic (e.g., 120/80)." }, // Systolic too low
				{ "120/95", "Please enter blood pressure in the format systolic/diastolic (e.g., 120/80)." }, // Diastolic too high
				{ "120/55", "Please enter blood pressure in the format systolic/diastolic (e.g., 120/80)." }, // Diastolic too low
				{ "120.5/80", "Please enter blood pressure in the format systolic/diastolic (e.g., 120/80)." }, // Decimal systolic
				{ "120/80.5", "Please enter blood pressure in the format systolic/diastolic (e.g., 120/80)." }, // Decimal diastolic
				{ "120 80", "Please enter blood pressure in the format systolic/diastolic (e.g., 120/80)." }, // Space instead of slash
				{ "120-80", "Please enter blood pressure in the format systolic/diastolic (e.g., 120/80)." }, // Hyphen instead of slash
				{ "@120/@80", "Please enter blood pressure in the format systolic/diastolic (e.g., 120/80)." }, // Special characters
				{ "999/999", "Please enter blood pressure in the format systolic/diastolic (e.g., 120/80)." } // Both values far above range
		};
	}
	
	
	
	
	@Test(dataProvider = "linkData", priority = 0)
	public void valNavbarlink(String numxPath, String resultTxt) {

		HomePage homePage = new HomePage(driver);
		homePage.clickNavLink(numxPath);

		String actualFragment = homePage.getCurrentUrlFragment();
		Assert.assertEquals(actualFragment, resultTxt, "URL fragment mismatch for link " + numxPath);

	}

	@Test(dataProvider = "testData", priority = 1)
	public void validateVitalGuardCalculate(String age, String pulse, String bp, String expectedResult, String feedback)
			throws Exception {

		HomePage homePage = new HomePage(driver);
		ResultPage resultPage = new ResultPage(driver);

		homePage.enterAge(age);
		homePage.enterPulseRate(pulse);
		homePage.enterBloodPressure(bp);
		homePage.clickCalculateBtn();

		// String actualResult = resultPage.getResult().trim();
		try {
			String actualFragment = resultPage.getCurrentUrlFragment();
			Assert.assertEquals(actualFragment, "https://hackvyatharth.github.io/Vitalguard-main/result.html",
					"URL fragment mismatch for link ");
		}
		
		finally {
			// Navigate back to input page
			driver.get("https://hackvyatharth.github.io/Vitalguard-main/index.html");
		}

	}

	@Test(dataProvider = "testData", priority = 2)
	public void validateVitalGuard(String age, String pulse, String bp, String expectedResult, String feedback)
			throws Exception {

		HomePage homePage = new HomePage(driver);
		ResultPage resultPage = new ResultPage(driver);

		homePage.enterAge(age);
		homePage.enterPulseRate(pulse);
		homePage.enterBloodPressure(bp);
		homePage.clickCalculateBtn();
		try {
			String actualResult = resultPage.getResult().trim();
	
			String status;
	
			double actual = Double.parseDouble(actualResult);
			double expected = Double.parseDouble(expectedResult.trim());
	
			Assert.assertEquals(actual, expected, "Mismatch in health index result");
		}
		finally {
			// Navigate back to input page
			driver.get("https://hackvyatharth.github.io/Vitalguard-main/index.html");
		}
	}

	

	@Test(dataProvider = "invalidAgeData", priority = 3)
	public void validateInvalidAgeInput(String ageInput, String expectedError) {
		HomePage homePage = new HomePage(driver);
		homePage.enterAge(ageInput);


		String actualError = homePage.getAgeErrorMessage();
		Assert.assertEquals(actualError, expectedError, "Error message mismatch for age input: " + ageInput);
	}

	@Test(dataProvider = "invalidPulseData", priority = 4)
	public void validateInvalidPulseInput(String pulseInput, String expectedError) {
		HomePage homePage = new HomePage(driver);
		homePage.enterPulseRate(pulseInput);

		String actualError = homePage.getPulseErrorMessage();
		Assert.assertEquals(actualError, expectedError, "Error message mismatch for pulse input: " + pulseInput);
	}

	@Test(dataProvider = "invalidBPData", priority = 5)
	public void validateInvalidBPInput(String bpInput, String expectedError) {
		HomePage homePage = new HomePage(driver);
		homePage.enterBloodPressure(bpInput);


		String actualError = homePage.getBPErrorMessage();
		Assert.assertEquals(actualError, expectedError, "Error message mismatch for BP input: " + bpInput);
	}
	

	@Test(dataProvider = "testData", priority=6)
    public void validateVitalGuard2(String age, String pulse, String bp, String expectedIndex, String expectedFeedback) {
        String actualIndex;
        String actualFeedback;
        String status="Pass";
        HomePage homePage = new HomePage(driver);
        ResultPage resultPage = new ResultPage(driver);
        try {
            homePage.enterAge(age);
            homePage.enterPulseRate(pulse);
            homePage.enterBloodPressure(bp);
            homePage.clickCalculateBtn();

            boolean isResultPage = driver.getCurrentUrl().contains("result");

            if (isResultPage) {
            	try {
	                actualIndex = resultPage.getResult().trim();
	                actualFeedback = resultPage.getResult1().trim();
	
	                boolean indexMatch = actualIndex.toLowerCase().contains(expectedIndex.trim().toLowerCase());
	                boolean feedbackMatch = actualFeedback.toLowerCase().contains(expectedFeedback.trim().toLowerCase());
	
	                status = (indexMatch && feedbackMatch) ? "Pass" : "Fail";
            	}
            	catch(NullPointerException e)
            	{
            		int i=0;
            	}

            } else {
                try {
                	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
                	wait.until(ExpectedConditions.alertIsPresent());

                    Alert alert = driver.switchTo().alert();
                    actualFeedback = alert.getText().trim();
                    alert.accept();
                    actualIndex = "0";

                    boolean indexMatch = actualIndex.equals(expectedIndex.trim());
                    boolean feedbackMatch = actualFeedback.toLowerCase().contains(expectedFeedback.trim().toLowerCase());

                    status = (indexMatch && feedbackMatch) ? "Pass" : "Fail";

                } catch (NoAlertPresentException e) {
                    actualIndex = "No result or alert";
                    actualFeedback = "No feedback";
                    status = "Fail";
                }
            }

        } catch (Exception e) {
            actualIndex = "Exception";
            actualFeedback = e.getMessage();
            status = "Fail";
        }
        try {
			resultData.add(status);
	        Assert.assertEquals(status, "Pass");
        }
        //System.out.println(actualFeedback);
        finally {
	        // Navigate back to input page
	        driver.get("https://hackvyatharth.github.io/Vitalguard-main/");
        }
    }

	@Test(dataProvider = "invalidAgeAlertData",priority=7)
	public void validateInvalidAgeAlert(String ageInput, String expectedError) {
		HomePage homePage = new HomePage(driver);
		homePage.enterAge(ageInput);
		homePage.clickCalculateBtn();

		
		String actualError = driver.switchTo().alert().getText();
		driver.switchTo().alert().accept();
		Assert.assertEquals(actualError, expectedError, "Error message mismatch for age input: " + ageInput);
	}
	
	@Test(dataProvider = "invalidPulseAlertData",priority=8)
	public void validateInvalidPulseAlert(String pulseInput, String expectedError) {
		HomePage homePage = new HomePage(driver);
		homePage.enterAge("20");
		homePage.enterPulseRate(pulseInput);
		homePage.clickCalculateBtn();

	
		String actualError = driver.switchTo().alert().getText();
		driver.switchTo().alert().accept();
		Assert.assertEquals(actualError, expectedError, "Error message mismatch for age input: " + pulseInput);
	}
	
	@Test(dataProvider = "invalidBPAlertData",priority=9)
	public void validateInvalidBPAlert(String bpInput, String expectedError){ 
		// Navigate back to input page
        driver.get("https://hackvyatharth.github.io/Vitalguard-main/");
		HomePage homePage = new HomePage(driver);
		homePage.enterAge("20");
		homePage.enterPulseRate("80");
		homePage.enterBloodPressure(bpInput);
		homePage.clickCalculateBtn();

		try {
			String actualError = driver.switchTo().alert().getText();
			driver.switchTo().alert().accept();
			Assert.assertEquals(actualError, expectedError, "Error message mismatch for age input: " + bpInput);
		}
		catch(NoAlertPresentException e) {
			Assert.fail();
		}
		
	}

	@AfterClass
	public void tearDown() {
		String filePath = "./src/resources/VitalGuard (3).xlsx";
		ExcelWriter.writeResults(filePath, "Sheet1", resultData);
		driver.quit();
	}

}
