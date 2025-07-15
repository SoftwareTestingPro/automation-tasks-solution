package com.qa.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.qa.base.BaseTest;

public class Intermediate extends BaseTest{
	
	@Test
	public void I201DragAndDropText() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.01-DragAndDropText.html");
		driver.findElement(By.id("showButton")).click();
		element = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@id='successMessage' and @style='display: block;']")));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
}