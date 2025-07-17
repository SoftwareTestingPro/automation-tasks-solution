package com.qa.tests;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.qa.base.BaseTest;

public class Intermediate extends BaseTest{
	
	@Test
	public void I201DragAndDropText() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.01-DragAndDropText.html");
		WebElement source = driver.findElement(By.id("draggableText"));
        WebElement target = driver.findElement(By.id("container2"));

        Actions actions = new Actions(driver);
        actions.dragAndDrop(source, target).build().perform();
		
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='success-message' and contains(text(),'Success')]")));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void I202HoverLink() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.02-HoverLink.html");
		
		// Find the element to hover over
        WebElement hoverTarget = driver.findElement(By.id("flagImage")); // or By.cssSelector / By.xpath

        // Create Actions object
        Actions actions = new Actions(driver);

        // Perform hover
        actions.moveToElement(hoverTarget).perform();
		
		driver.findElement(By.xpath("//a[text()='Who is He ?']")).click();
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='successMessage' and @style='display: block;']")));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
//	public void I203Train() {
//		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.03-Train.html");
//		driver.findElement(By.id("showButton")).click();
//		element = wait.until(ExpectedConditions
//				.visibilityOfElementLocated(By.xpath("//div[@id='successMessage' and @style='display: block;']")));
//		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
//	}
	
	@Test
	public void I204Calendar() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.04-Calendar.html");
		driver.findElement(By.xpath("(//tr[count(td[@class='calendar-date'])>4]/td[4])[1]")).click();
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='message1' and @style='color: green;']")));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void I205CricketScorecard() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.05-CricketScorecard.html");
		
//		Find Top Scorer
		List<WebElement> elements = driver.findElements(By.xpath("//li/span"));
		List<Integer> scores = new ArrayList<>();

		// Extract numeric score from each element
		for (WebElement el : elements) {
		    String text = el.getText().trim();
		    String digits = text.replaceAll("\\D+", "");
		    if (!digits.isEmpty()) {
		        scores.add(Integer.parseInt(digits));
		    }
		}

		// Find index of largest score
		int maxIndex = 0;
		for (int i = 1; i < scores.size(); i++) {
		    if (scores.get(i) > scores.get(maxIndex)) {
		        maxIndex = i;
		    }
		}
		
		maxIndex = maxIndex+1;
		String TopScorer = driver.findElement(By.xpath("(//span[contains(@id,'player-name')])["+maxIndex+"]")).getText();
		driver.findElement(By.id("inputField1")).sendKeys(TopScorer);

		element = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@id='result1' and contains(@class, 'tick')]")));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
		
//		Find runs scored by Sachin
		String SachinRuns = driver.findElement(By.id("player-score-1")).getText();
		driver.findElement(By.id("inputField2")).sendKeys(SachinRuns);

		element = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@id='result2' and contains(@class, 'tick')]")));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void I214JSInput() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.14-JSInput.html");
		
		// Locate the input element (must be present in DOM)
        WebElement inputField = driver.findElement(By.id("secureInput"));

        // Use JavascriptExecutor to set its value
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='India';", inputField);

        // Optional: trigger input event if needed
        js.executeScript("arguments[0].dispatchEvent(new Event('input'));", inputField);
		
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='successMessage' and @style='display: block;']")));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
//	@Test
//	public void I203Train() {
//	driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.03-Train.html");
//	driver.findElement(By.id("showButton")).click();
//	element = wait.until(ExpectedConditions
//			.visibilityOfElementLocated(By.xpath("//div[@id='successMessage' and @style='display: block;']")));
//	Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
//}
	
}