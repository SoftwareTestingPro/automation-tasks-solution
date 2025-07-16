package com.qa.reports;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;
    private static ExtentSparkReporter spark;

    public static void deleteReportsFolder() throws IOException {
    	File folder = new File("./reports");
        if (folder.exists())
            FileUtils.deleteDirectory(folder);
    }
    
    public static ExtentReports getInstance() {
        if (extent == null) {
            spark = new ExtentSparkReporter("reports/ExtentReport.html");
            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Framework", "Selenium + TestNG");
            extent.setSystemInfo("Author", "Sushil");
        }
        return extent;
    }
}