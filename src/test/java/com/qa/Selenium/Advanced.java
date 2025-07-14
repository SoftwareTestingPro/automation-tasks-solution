package com.qa.Selenium;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Advanced {
	
	WebDriver driver;
	WebDriverWait wait;
	WebElement element;
	
	@BeforeTest
	public void start() {
	driver = new EdgeDriver();
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	@Test
	public void sample() {
	}
	
	@AfterTest
	public void end() throws InterruptedException {
		Thread.sleep(3000);
		driver.quit();
	}
}