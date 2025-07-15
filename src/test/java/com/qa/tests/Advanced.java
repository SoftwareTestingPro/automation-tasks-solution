package com.qa.tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.qa.base.BaseTest;

public class Advanced extends BaseTest{

	@Test
	public void A301HiddenElement() {
		driver.get("https://softwaretestingpro.github.io/Automation/Advanced/A-3.01-HiddenElement.html");
		driver.findElement(By.id("showButton")).click();
		element = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@id='successMessage' and @style='display: block;']")));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}

	@Test
	public void A302Download() throws IOException, InterruptedException {
		driver.get("https://softwaretestingpro.github.io/Automation/Advanced/A-3.02-Download.html");
		// Delete file if already exists due to multiple executions
		Path filePath = Paths.get(System.getProperty("user.home") + "/Downloads/100mb.zip");
		Files.deleteIfExists(filePath);
		// Download file
		driver.findElement(By.className("download-link")).click();
		long startTime = System.currentTimeMillis();

		String downloadDir = System.getProperty("user.home") + "/Downloads";
		String expectedFileName = "100mb.zip";
		File finalFile = new File(downloadDir + "/" + expectedFileName);
		System.out.println();

		int timeoutSeconds = 120;
		int waited = 0;

		while ((!finalFile.exists() || new File(downloadDir).list((d, name) -> name.endsWith(".crdownload")).length > 0)
				&& waited < timeoutSeconds) {
			Thread.sleep(1000);
			waited++;
		}

		// Final check after loop to ensure file is ready
		boolean isDownloaded = finalFile.exists()
				&& new File(downloadDir).list((d, name) -> name.endsWith(".crdownload")).length == 0;

		long endTime = System.currentTimeMillis();
		Assert.assertTrue(isDownloaded, "File not found in downloads folder or still downloading");
		System.out.println("✅ Total time taken to download file: " + (endTime - startTime) / 1000 + " seconds");
//    }

//		driver.findElement(By.className("download-link")).click();
//		long startTime = System.currentTimeMillis();
//
//		File file = new File("user.home" + "/Downloads/100mb.zip");
//		while ((file.exists() || !(new File("user.home" + "/Downloads").list((d, name) -> name.endsWith(".crdownload")).length > 0))
////        while (file.exists() && ) {
//		{
//			long endTime = System.currentTimeMillis();
//			Assert.assertTrue(file.exists(), "File not found in downloads folder");
//			System.out.println("Total time taken to download file:" + (endTime - startTime) / 1000);
//		}
	}

	@Test
	public void A303Whiteboard() {
		driver.get("https://softwaretestingpro.github.io/Automation/Advanced/A-3.03-Whiteboard.html");

		WebElement canvas = driver.findElement(By.id("whiteboard"));
		Actions drawAction = new Actions(driver);

		drawAction.moveToElement(canvas).clickAndHold().moveByOffset(50, 0).moveByOffset(0, 50).moveByOffset(-50, 0)
				.moveByOffset(0, -50).release().perform();
	}

	@Test
	public void A304ReadQR() throws MalformedURLException, IOException, NotFoundException {
		driver.get("https://softwaretestingpro.github.io/Automation/Advanced/A-3.04-ReadQR.html");

		// Locate QR code image element
		WebElement qrImage = driver.findElement(By.id("qr"));
		String qrImageUrl = qrImage.getAttribute("src");

		// Read image from URL
		BufferedImage bufferedImage = ImageIO.read(new URL(qrImageUrl));

		// Decode QR code using ZXing
		LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Result result = new MultiFormatReader().decode(bitmap);

		driver.findElement(By.id("user-input")).sendKeys(result.getText());
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@class='success-message' and contains(text(),'Success')]")));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}

	@Test
	public void A305XLSWriteData() {
		driver.get("https://softwaretestingpro.github.io/Automation/Advanced/A-3.05-XLSWriteData.html");

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Employee Data");

		// Define the data
		String[][] data = { { "Name", "Department", "Position" }, { "John Doe", "Engineering", "Software Engineer" },
				{ "Jane Smith", "Marketing", "Marketing Manager" },
				{ "Emily Johnson", "Human Resources", "HR Specialist" },
				{ "Michael Brown", "Sales", "Sales Representative" },
				{ "Linda Wilson", "Finance", "Financial Analyst" } };

		// Write data to sheet
		for (int i = 0; i < data.length; i++) {
			Row row = sheet.createRow(i);
			for (int j = 0; j < data[i].length; j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue(data[i][j]);
			}
		}

		// Save the workbook to file
		try (FileOutputStream out = new FileOutputStream("data/EmployeeData.xlsx")) {
			workbook.write(out);
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void A323XLSReadData() throws IOException, InterruptedException {
		driver.get("https://softwaretestingpro.github.io/Automation/Advanced/A-3.23-XLSReadData.html");

		FileInputStream fis = new FileInputStream("data/EmployeeData.xlsx");
		Workbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheet("Employee Data");
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			String name = row.getCell(0).getStringCellValue();
			String department = row.getCell(1).getStringCellValue();
			String position = row.getCell(2).getStringCellValue();

			WebElement nameField = driver.findElement(By.xpath("(//input[@placeholder='Enter Name'])[" + i + "]"));
			WebElement deptField = driver
					.findElement(By.xpath("(//input[@placeholder='Enter Department'])[" + i + "]"));
			WebElement positionField = driver
					.findElement(By.xpath("(//input[@placeholder='Enter Position'])[" + i + "]"));

			nameField.sendKeys(name);
			deptField.sendKeys(department);
			positionField.sendKeys(position);
		}
	}
	
	@Test
	public void A306BrokenImages() throws IOException {
		int brokenImages =0;
		driver.get("https://softwaretestingpro.github.io/Automation/Advanced/A-3.06-BrokenImages.html");
		List<WebElement> links = driver.findElements(By.xpath("//img[@alt='Country Flag']"));
		for (WebElement link : links) {
		    String url = link.getAttribute("src");
	                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
	                connection.setRequestMethod("HEAD");
	                connection.setConnectTimeout(3000);
	                connection.connect();
	                int responseCode = connection.getResponseCode();
	                if (responseCode >= 400)
	                	brokenImages++;
	        }

		driver.findElement(By.id("user-input")).sendKeys(""+brokenImages);
		element = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@class='success-message' and contains(text(),'Success')]")));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void A307Clock() {
		driver.get("https://softwaretestingpro.github.io/Automation/Advanced/A-3.07-Clock.html");
		
//		fetch degree values
		double hour = Double.parseDouble(driver.findElement(By.id("hour")).getAttribute("style").replaceAll("[^\\d.]", ""));
		double min = Double.parseDouble(driver.findElement(By.id("minute")).getAttribute("style").replaceAll("[^\\d.]", ""));
		double sec = Double.parseDouble(driver.findElement(By.id("second")).getAttribute("style").replaceAll("[^\\d.]", ""));
		
//		convert degree to digital time
	    int hours = (int)(hour/30);
	    int mins = (int)(min/6);
	    int secs = (int)(sec/6);
		
		String time = String.format("%02d:%02d:%02d", hours, mins, secs);
		WebElement input = driver.findElement(By.id("user-input"));
		input.sendKeys(time);
	}
	
	@Test
	public void A308ShapesOddOneOut() {
		driver.get("https://softwaretestingpro.github.io/Automation/Advanced/A-3.08-ShapesOddOneOut.html");
		
		  // Find all shape buttons
        List<WebElement> shapeButtons = driver.findElements(By.className("shape-button"));

        // Store shape class names
        List<String> shapeTypes = new ArrayList<>();
        for (WebElement button : shapeButtons) {
            WebElement shapeDiv = button.findElement(By.className("shape"));
            String shapeClass = shapeDiv.getAttribute("class"); // e.g., "shape triangle"
            shapeTypes.add(shapeClass);
        }

        // Count frequency of each shape type
        Map<String, Integer> shapeCount = new HashMap<>();
        for (String shape : shapeTypes) {
            shapeCount.put(shape, shapeCount.getOrDefault(shape, 0) + 1);
        }

        // Find and print the unique shape
        for (int i = 0; i < shapeTypes.size(); i++) {
            String shape = shapeTypes.get(i);
            if (shapeCount.get(shape) == 1) {
                WebElement uniqueButton = shapeButtons.get(i);
                // Optional: click or highlight
                uniqueButton.click();
                break;
            }
        }
		
		element = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//span[text()='Success! You found the odd shape!']")));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void A309Location() {
		
//		Map<String, Object> prefs = new HashMap<>();
//      prefs.put("profile.default_content_setting_values.geolocation", 1); // 1 = allow
//      EdgeOptions options = new EdgeOptions();
//      options.setExperimentalOption("prefs", prefs);
//      driver = new EdgeDriver(options);
		
		driver.get("https://softwaretestingpro.github.io/Automation/Advanced/A-3.09-Location.html");
		driver.findElement(By.tagName("button")).click();
		
//		If success message and geo location details are not displayed and message "Location information is unavailable." is displayed, then enable location services from windows settigs
		
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='success-message' and contains(text(),'Success')]")));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
	@Test
	public void A310Game() {
		driver.get("https://softwaretestingpro.github.io/Automation/Advanced/A-3.10-Game.html");
		driver.findElement(By.id("showButton")).click();
		element = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@id='successMessage' and @style='display: block;']")));
		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
	}
	
//	@Test
//	public void A301HiddenElement() {
//		driver.get("https://softwaretestingpro.github.io/Automation/Advanced/A-3.01-HiddenElement.html");
//		driver.findElement(By.id("showButton")).click();
//		element = wait.until(ExpectedConditions
//				.visibilityOfElementLocated(By.xpath("//div[@id='successMessage' and @style='display: block;']")));
//		Assert.assertTrue(element.isDisplayed(), "Success message is not displayed");
//	}

}