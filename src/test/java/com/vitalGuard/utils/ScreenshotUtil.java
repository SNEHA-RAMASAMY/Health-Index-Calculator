package com.vitalGuard.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {
    public static void captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            
	        File dest = new File(screenshotName);
			FileUtils.copyFile(src, dest);
			
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

	public static String timestamp() {
		return  new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
	}
}