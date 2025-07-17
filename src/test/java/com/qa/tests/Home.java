package com.qa.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.net.HttpURLConnection;
import java.net.URL;
import com.qa.base.BaseTest;

public class Home extends BaseTest{
	@Test
	public void HomeBrokenLinks() throws IOException {
	    int brokenLinks = 0;
	    List<String> brokenURLs = new ArrayList<>();

	    driver.get("https://softwaretestingpro.github.io/Automation/Home.html");
	    List<WebElement> links = driver.findElements(By.xpath("//a"));

	    for (WebElement link : links) {
	        String url = link.getAttribute("href");

	        // Skip empty or JavaScript-only links
	        if (url == null || url.trim().isEmpty() || url.startsWith("javascript")) {
	            continue;
	        }

	        try {
	            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
	            connection.setRequestMethod("HEAD");
	            connection.setConnectTimeout(3000);
	            connection.connect();

	            int responseCode = connection.getResponseCode();

	            if (responseCode >= 400) {
	                brokenLinks++;
	                brokenURLs.add(url);
	            }
	        } catch (Exception e) {
	            brokenLinks++;
	            brokenURLs.add(url + " (Exception: " + e.getMessage() + ")");
	        }
	    }

	    if (!brokenURLs.isEmpty()) {
	    	System.out.println("Total Broken Links: " + brokenLinks);
	        System.out.println("Broken Link URLs:");
	        for (String broken : brokenURLs) {
	            System.out.println("❌ " + broken);
	        }
	    }

	    Assert.assertTrue(brokenLinks == 0, "There are broken links present.");
	}
}
