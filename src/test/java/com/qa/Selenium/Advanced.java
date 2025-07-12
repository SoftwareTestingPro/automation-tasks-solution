package com.qa.Selenium;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Advanced {
	
	WebDriver driver;
	WebDriverWait wait;
	
	@BeforeTest
	public void start() {
	driver = new EdgeDriver();
	wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}
	
	@Test
	public void test() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.01-Click.html");
		driver.findElement(By.id("clickButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@style='display: block;']")));
	}
	
	@Test
	public void test1() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.01-Click.html");
		driver.findElement(By.id("clickButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@style='display: block;']")));
	}
	
	@AfterTest
	public void end() {
		driver.quit();
	}
}