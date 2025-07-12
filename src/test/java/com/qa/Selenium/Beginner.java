package com.qa.Selenium;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class Beginner {
	
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
	public void B101Click() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.01-Click.html");
		driver.findElement(By.id("clickButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@style='display: block;']")));
	}
	
	@Test
	public void B101JSClick() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.01-JSClick.html");
		driver.findElement(By.id("clickButton")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@style='display: bhlock;']")));
	}
	
	@Test
	public void B102Input() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.02-Input.html");
		
		driver.findElement(By.name("user-input-name")).sendKeys("Bhagat");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='user-input-name']//following-sibling::span[@style='display: inline-block;' and text()='check_circle']")));
		
		driver.findElement(By.name("user-input-age")).sendKeys("23");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='user-input-age']//following-sibling::span[@style='display: inline-block;' and text()='check_circle']")));
		
		String text = driver.findElement(By.xpath("//label[@for='text-input']")).getText().split("\"")[1];
		driver.findElement(By.id("text-input")).sendKeys(text);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='text-input']//following-sibling::span[@style='display: inline-block;' and text()='check_circle']")));
	}
	
	@Test
	public void B103Radio() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.03-Radio.html");
		driver.findElement(By.xpath("//input[@value='male']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='successMessage' and @style='display: block;']")));
	}
	
	@Test
	public void B104Color() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.04-Color.html");
		
		element = driver.findElement(By.xpath("//button[@id='colorButton']"));
		String bgcolor = (String) js.executeScript("return window.getComputedStyle(arguments[0]).getPropertyValue('background-color');", element);
		String hexColor = Color.fromString(bgcolor).asHex();
		System.out.println(hexColor);
		driver.findElement(By.id("user-input1")).sendKeys(hexColor);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='icon1' and @class='validation-icon success']")));
		
		element = driver.findElement(By.xpath("//button[@id='colorButton']"));
		Actions actions = new Actions(driver);
		actions.moveToElement(element).pause(Duration.ofSeconds(1)).perform();
		Thread.sleep(2000);
		bgcolor = (String) js.executeScript("return window.getComputedStyle(arguments[0]).getPropertyValue('background-color');", element);
		hexColor = Color.fromString(bgcolor).asHex();
		driver.findElement(By.id("user-input2")).sendKeys(hexColor);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='icon2' and @class='validation-icon success']")));
		
		element = driver.findElement(By.xpath("//button[@id='colorButton']"));
		actions = new Actions(driver);
		actions.clickAndHold(element).pause(Duration.ofSeconds(1)).perform();
		bgcolor = (String) js.executeScript("return window.getComputedStyle(arguments[0]).getPropertyValue('background-color');", element);
		hexColor = Color.fromString(bgcolor).asHex();
		driver.findElement(By.id("user-input3")).sendKeys(hexColor);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='icon3' and @class='validation-icon success']")));
	}
	
	@Test
	public void B105Checkboxes() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.05-Checkboxes.html");
		driver.findElement(By.xpath("//input[@value='music']")).click();
		driver.findElement(By.xpath("(//input[@type='checkbox'])[3]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='successMessage' and @style='display: block;']")));
	}
	
	@Test
	public void B106ValueInput() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.06-ValueInput.html");
		String text = driver.findElement(By.xpath("//label[@for='inputField1']/following-sibling::input")).getAttribute("value");
		System.out.println(text);
		driver.findElement(By.xpath("//input[@id='inputField2']")).sendKeys(text);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'alert-success') and @style='display: block;']")));
	}
	
	@Test
	public void B107RadioList() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.07-RadioList.html");
		driver.findElement(By.xpath("//label[text()='India']//preceding-sibling::input")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='successMessage' and @style='display: block;']")));
	}
	
	@Test
	public void B108DropDown() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.08-DropDown.html");
		
//		Dropdown 1
		Select select1 = new Select(driver.findElement(By.id("dropdown1")));
		select1.selectByValue("2");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='dropdown1']/following-sibling::span[contains(@class,'success')]")));
		
//		Dropdown 2
		Select select2 = new Select(driver.findElement(By.id("dropdown2")));
		select2.selectByValue("2");
		select2.selectByValue("4");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='dropdown2']/following-sibling::span[contains(@class,'success')]")));
		
//		Dropdown 3
		Select select3 = new Select(driver.findElement(By.id("dropdown3")));
		select3.selectByIndex(3);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='dropdown3']/following-sibling::span[contains(@class,'success')]")));
	}
	
	@Test
	public void B109DoubleClick() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.09-DoubleClick.html");
		element = driver.findElement(By.id("clickButton"));
		Actions actions = new Actions(driver);
		actions.doubleClick(element).perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='successMessage' and @style='display: block;']")));
	}
	
	@Test
	public void B110RightClick() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.10-RightClick.html");
		element = driver.findElement(By.id("clickButton"));
		Actions actions = new Actions(driver);
		actions.contextClick(element).perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='successMessage' and @style='display: block;']")));
	}
	
	@Test
	public void B111PageTitle() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.11-PageTitle.html");
		String pageTitle = driver.getTitle();
		driver.findElement(By.id("titleInput")).sendKeys(pageTitle);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'success-message') and @style='display: block;']")));
	}
	
	@Test
	public void B112FetchURL() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.12-FetchURL.html");
		String pageURL = driver.getCurrentUrl();
		driver.findElement(By.id("urlInput")).sendKeys(pageURL);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'success-message') and @style='display: block;']")));
	}
	
	@Test
	public void B114Keystroke() {
		//	Not Automatable. It would be too complex to define all possible combinations in switch case.
	}
	
	@Test
	public void B115DatePicker() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.15-DatePicker.html");
		String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		driver.findElement(By.id("date-picker")).sendKeys(currentDate);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'message success')]")));
	}
	
	@Test
	public void B116CaptureScreeshot() throws IOException {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.16-CaptureScreeshot.html");
		
//		Screenshot 1 - Element Screenshot
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='flagsContainer']/img")));
		WebElement element = driver.findElement(By.xpath("//div[@id='flagsContainer']/img"));
		File screenshot1 = element.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshot1, new File("./Screenshots/B116-element.png"));
		
//		Screenshot 2 - Page Screenshot
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@id='flagsContainer']/img)[3]")));
		File screenshot2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshot2, new File("./Screenshots/B116-page.png"));
		
//		Screenshot 3 - Full Page Screenshot
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@id='flagsContainer']/img)[20]")));
		Screenshot screenshot3 = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(2000)).takeScreenshot(driver);
		ImageIO.write(screenshot3.getImage(), "PNG", new File("./Screenshots/B116-fullpage.png"));
	}
	
	@Test
	public void B117Tooltip() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.17-Tooltip.html");
		String hoverText = driver.findElement(By.className("rainbow-text")).getAttribute("data-tooltip");
		driver.findElement(By.id("inputField")).sendKeys(hoverText);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'success')]")));
	}
	
	@Test
	public void B118PageSource() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.18-PageSource.html");
		String pageSource = driver.getPageSource();
		driver.findElement(By.id("textInput")).sendKeys(pageSource);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'success')]")));
	}
	
	@Test
	public void B119AutoComplete() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.19-AutoComplete.html");
		String fruit = driver.findElement(By.id("labelText")).getText().split("'")[1];
		driver.findElement(By.id("searchInput")).sendKeys(fruit);
		driver.findElement(By.xpath("//div[text()='"+fruit+"']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'success')]")));
	}
	
	@Test
	public void B120ClickEnabledButton() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.20-ClickEnabledButton.html");
		driver.findElement(By.xpath("//button[not(@disabled)]")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'success')]")));
	}
	
	@Test
	public void B121CompareText() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.21-CompareText.html");
		String Text1 = driver.findElement(By.id("text1")).getText();
		String Text2 = driver.findElement(By.id("text2")).getText();
		if(Text1.equalsIgnoreCase(Text2))
			driver.findElement(By.xpath("//button[text()='Yes']")).click();
		else
			driver.findElement(By.xpath("//button[text()='No']")).click();
		
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@style='color: green;']")));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void B122ImagePath() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.22-ImagePath.html");
		String imageURL = driver.findElement(By.id("image")).getAttribute("data-src");
		driver.findElement(By.id("imageURL")).sendKeys(imageURL);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'success')]")));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void B123ColorOddOneOut() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.23-ColorOddOneOut.html");
		
		List<WebElement> buttons = driver.findElements(By.tagName("button"));
		Map<String, List<WebElement>> colorGroups = new HashMap<>();
		for (WebElement button : buttons) {
		    String color = button.getCssValue("background-color");
		    colorGroups.computeIfAbsent(color, k -> new ArrayList<>()).add(button);
		}

		// Find the color group with only one button
		for (Map.Entry<String, List<WebElement>> entry : colorGroups.entrySet()) {
		    if (entry.getValue().size() == 1) {
		        WebElement uniqueButton = entry.getValue().get(0);
		        uniqueButton.click(); // or return its locator
		        System.out.println("Clicked unique button with color: " + entry.getKey());
		        break;
		    }
		}

		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@style='color: green;']")));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void B124MathOddOneOut() {
		driver.get("https://softwaretestingpro.github.io/Automation/Beginner/B-1.24-MathOddOneOut.html");
		List<WebElement> buttons = driver.findElements(By.tagName("button"));

		for (WebElement button : buttons) {
			String equation = button.getText();
			String[] parts = equation.split("=");

			if (parts.length != 2)
				continue;

			String lhs = parts[0].trim();
			String rhsText = parts[1].trim();

			int expected = Integer.parseInt(rhsText);

			// ⏬ Evaluate LHS inline
			String[] tokens = lhs.split(" ");
			int result = Integer.parseInt(tokens[0]);

			for (int i = 1; i < tokens.length; i += 2) {
				String op = tokens[i];
				int val = Integer.parseInt(tokens[i + 1]);

				switch (op) {
				case "+":
					result += val;
					break;
				case "-":
					result -= val;
					break;
				case "*":
					result *= val;
					break;
				case "/":
					result /= val;
					break;
				}
			}

			if (result != expected) {
				driver.findElement(By.xpath("//button[text()='" + equation + "']")).click();
				element = wait.until(
						ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@style='color: green;']")));
				Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
				break; // Stop at first mismatch
			}
		}
	}

		
	
	@AfterTest
	public void end() throws InterruptedException {
		Thread.sleep(3000);
		driver.quit();
	}
}