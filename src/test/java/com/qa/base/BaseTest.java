package com.qa.base;

import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.qa.reports.ExtentManager;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static WebElement element;
    
    public WebDriver getDriver() {
        return driver;
    }

    @BeforeSuite
    public void start() throws IOException {
    	ExtentManager.deleteReportsFolder();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.geolocation", 1);

        EdgeOptions options = new EdgeOptions();
        options.setExperimentalOption("prefs", prefs);
        
        driver = new EdgeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
	@AfterSuite
	public void end() throws InterruptedException {
		Thread.sleep(3000);
		driver.quit();
	}
}