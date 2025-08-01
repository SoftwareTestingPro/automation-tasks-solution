package com.qa.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.qa.base.BaseTest;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.*;

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
	public void I206Table() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.06-Table.html");
		
        List<WebElement> salaryElements = driver.findElements(By.xpath("//tbody/tr/td[3]"));
        List<Integer> salaryList = new ArrayList<>();

        for (WebElement element : salaryElements) {
            String salaryText = element.getText();
            String cleaned = salaryText.replaceAll("[^\\d]", ""); // Remove non-digit characters
            int salary = Integer.parseInt(cleaned);
            salaryList.add(salary);
        }
        int maxSalary = Collections.max(salaryList);
        int index = salaryList.indexOf(maxSalary)+1;
        
        driver.findElement(By.xpath("(//tbody/tr/td[3])["+index+"]")).click();
        element = driver.findElement(By.className("success"));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
    }
	
	@Test
	public void I207Rubix() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.07-Rubix.html");
		
		// Locate all 9 blocks
        List<WebElement> blocks = driver.findElements(By.className("block"));

        // Map to count occurrences of each background color
        Map<String, Integer> colorCount = new HashMap<>();
        Map<String, WebElement> colorToElement = new HashMap<>();

        // Collect background colors
        for (WebElement block : blocks) {
            String bgColor = block.getCssValue("background-color");
            colorCount.put(bgColor, colorCount.getOrDefault(bgColor, 0) + 1);
            colorToElement.put(bgColor, block); // Store reference to element
        }

        // Find the color that appears only once
        for (Map.Entry<String, Integer> entry : colorCount.entrySet()) {
            if (entry.getValue() == 1) {
                WebElement uniqueBlock = colorToElement.get(entry.getKey());
                uniqueBlock.click();
                System.out.println("✅ Clicked block with unique color: " + entry.getKey());
                break;
            }
        }
		
		element = driver.findElement(By.id("successMessage"));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void I208Slider() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.08-Slider.html");
		
		 // Locate the slider handle
        WebElement slider = driver.findElement(By.className("slider"));

        // Move slider by 50 pixels to the right
        Actions action = new Actions(driver);
        action.clickAndHold(slider).moveByOffset(60, 0).release().perform();

		element = driver.findElement(By.id("successMessage"));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void I209RandomButtons() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.09-RandomButtons.html");
		int count = driver.findElements(By.xpath("//div/button")).size();
		driver.findElement(By.xpath("//input")).sendKeys(""+count);
		element = driver.findElement(By.id("successMessage"));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void I210Alerts() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.10-Alerts.html");
		
		
//		Show Alert
		driver.findElement(By.className("alert-button")).click();
		driver.switchTo().alert().accept();
		
		
//		Show Confirm
		driver.findElement(By.className("confirm-button")).click();
		driver.switchTo().alert().accept();
		driver.switchTo().alert().accept();
		
		
//		Show Prompt
		driver.findElement(By.className("prompt-button")).click();
		driver.switchTo().alert().sendKeys("Sushil");
		driver.switchTo().alert().accept();
		driver.switchTo().alert().accept();
		
//		Show Custom Alert
		driver.findElement(By.className("custom-button")).click();
		driver.findElement(By.xpath("//button[text()='Close']")).click();
	}
	
	@Test
	public void I211Progressbar() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.11-Progressbar.html");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successMessage")));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void I212Frame() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.12-Frame.html");
		driver.switchTo().frame("button-frame");
		driver.findElement(By.className("button")).click();
		driver.switchTo().defaultContent();
		element = driver.findElement(By.className("success"));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void I213BrowserPopUp() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.13-BrowserPopUp.html");
		driver.findElement(By.tagName("button")).click();
		
		String originalWindow = driver.getWindowHandle();
		String Quote = "";
	    for (String handle : driver.getWindowHandles()) {
	        driver.switchTo().window(handle);
	        System.out.println(driver.getTitle());
	        if (driver.getTitle().equals("Life Quote")) {
	        	Quote = driver.findElement(By.tagName("p")).getText();
	        	System.out.println(Quote);
	        }
	    }
	    driver.close();
	    driver.switchTo().window(originalWindow);
	    driver.findElement(By.id("userInput")).sendKeys(Quote);
		element = driver.findElement(By.className("success"));
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
	
	@Test
	public void I215RandomPopUp() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.15-RandomPopUp.html");
		long startTime = System.currentTimeMillis();
		driver.findElement(By.id("alertButton")).click();
		wait.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().accept();
		long endTime = System.currentTimeMillis();
		
		long seconds = (endTime - startTime) / 1000;
		driver.findElement(By.id("inputField")).sendKeys(""+seconds);
		element = driver.findElement(By.className("success"));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void I216RandomList() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.16-RandomList.html");
		int count = driver.findElements(By.className("item")).size();
		driver.findElement(By.id("userInput")).sendKeys(""+count);
		element = driver.findElement(By.id("successMessage"));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void I217LoadingResults() {
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.17-LoadingResults.html");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='isbn-0']")));
		String ISBN1 = driver.findElement(By.xpath("//div[@id='isbn-0']")).getText().replaceAll("ISBN: ", "");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='isbn-1']")));
		String ISBN2 = driver.findElement(By.xpath("//div[@id='isbn-1']")).getText().replaceAll("ISBN: ", "");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='isbn-2']")));
		String ISBN3 = driver.findElement(By.xpath("//div[@id='isbn-2']")).getText().replaceAll("ISBN: ", "");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='isbn-3']")));
		String ISBN4 = driver.findElement(By.xpath("//div[@id='isbn-3']")).getText().replaceAll("ISBN: ", "");
		
		driver.findElement(By.id("input-0")).sendKeys(ISBN1);
		driver.findElement(By.id("input-1")).sendKeys(ISBN2);
		driver.findElement(By.id("input-2")).sendKeys(ISBN3);
		driver.findElement(By.id("input-3")).sendKeys(ISBN4);
		
		element = driver.findElement(By.className("success"));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void I218Chart() {
//		NA for Automation
	}
	
	@Test
	public void I219FetchDate() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.19-FetchDate.html");
		LocalDate today = LocalDate.now();
        String formattedDate = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println("Today's date: " + formattedDate);
        driver.findElement(By.id("dateInput")).sendKeys(formattedDate);
		element = driver.findElement(By.id("successMessage"));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void I220ValueInput() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.20-ValueInput.html");
		String value = driver.findElement(By.id("inputField1")).getAttribute("value");
		System.out.println("Extracted value: " + value);
		driver.findElement(By.id("inputField2")).sendKeys(value);
		element = driver.findElement(By.className("alert-success"));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void I221ScrollableList() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.21-ScrollableList.html");
		
		WebElement container = driver.findElement(By.className("terms"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight;", container);
		
		driver.findElement(By.id("acceptButton")).click();
		element = driver.findElement(By.className("enabled"));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void I222SortNumbers() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.22-SortNumbers.html");
		
		List<WebElement> Elements = driver.findElements(By.className("number-box"));
        List<Integer> numbersList = new ArrayList<>();

        for (WebElement element : Elements) {
            String getNum = element.getText();
            int num = Integer.parseInt(getNum);
            numbersList.add(num);
        }
        
        Collections.sort(numbersList);
        
        for (int i = 0; i < numbersList.size(); i++) {
            WebElement inputField = driver.findElement(By.xpath("(//div[@class='input-box']/input)["+(i+1)+"]"));
            inputField.sendKeys(String.valueOf(numbersList.get(i)));
        }
        
        element = driver.findElement(By.xpath("//div[contains(text(),'Success!')]"));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void I223SortCharacters() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.23-SortCharacters.html");
		
		List<WebElement> Elements = driver.findElements(By.className("letter-box"));
		List<String> charList = new ArrayList<>();

        for (WebElement element : Elements) {
            String getChar = element.getText();
            charList.add(getChar);
        }
        
        Collections.sort(charList);
        
        for (int i = 0; i < charList.size(); i++) {
            WebElement inputField = driver.findElement(By.xpath("(//div[@class='input-box']/input)["+(i+1)+"]"));
            inputField.sendKeys(String.valueOf(charList.get(i)));
        }
        
        element = driver.findElement(By.xpath("//div[contains(text(),'Success!')]"));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void I223SortWords() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.24-SortWords.html");
		
		List<WebElement> Elements = driver.findElements(By.className("word-box"));
		List<String> wordList = new ArrayList<>();

        for (WebElement element : Elements) {
            String getChar = element.getText();
            wordList.add(getChar);
        }
        
        Collections.sort(wordList);
        
        for (int i = 0; i < wordList.size(); i++) {
            WebElement inputField = driver.findElement(By.xpath("(//div[@class='input-box']/input)["+(i+1)+"]"));
            inputField.sendKeys(String.valueOf(wordList.get(i)));
        }
        
        element = driver.findElement(By.xpath("//div[contains(text(),'Success!')]"));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void I225SplitWords() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.25-SplitWords.html");
		
        String input = driver.findElement(By.id("sentence")).getText();
        List<String> wordList = Arrays.asList(input.split(" "));
        
        for (int i = 0; i < wordList.size(); i++) {
            WebElement inputField = driver.findElement(By.xpath("(//div[@class='input-container']/input)["+(i+1)+"]"));
            inputField.sendKeys(String.valueOf(wordList.get(i)));
        }
        
        element = driver.findElement(By.xpath("//div[contains(text(),'Success!')]"));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void I226ImagePosition() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.26-ImagePosition.html");
		driver.findElement(By.id("showButtgggggon")).click();
		element = driver.findElement(By.id("successMessage"));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void I227YoungestPerson() {
		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.27-YoungestPerson.html");
		
		List<WebElement> Elements = driver.findElements(By.xpath("//label"));
		List<String> dobList = new ArrayList<>();

        for (WebElement element : Elements) {
            String getDob = element.getText().split("DOB:")[1].split("\\)")[0].trim();
            dobList.add(getDob);
        }
        
//      System.out.println(dobList);
        
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
	            .parseCaseInsensitive()
	            .appendPattern("d/M/yyyy, h:mm:ss a")
	            .toFormatter(Locale.ENGLISH);

	        List<String> sortedDates = dobList.stream()
	            .map(String::trim)
	            .sorted(Comparator.comparing(s -> LocalDateTime.parse(s, formatter)))
	            .collect(Collectors.toList());

//        System.out.println(sortedDates);
        String youngestDate = sortedDates.getLast();
        driver.findElement(By.xpath("//label[contains(text(),'"+youngestDate+"')]")).click();
        
        element = driver.findElement(By.className("success"));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
//	@Test
//	public void I203Train() {
//		driver.get("https://softwaretestingpro.github.io/Automation/Intermediate/I-2.03-Train.html");
//		driver.findElement(By.id("showButton")).click();
//		element = driver.findElement(By.id("successMessage"));
//		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
//	}
}