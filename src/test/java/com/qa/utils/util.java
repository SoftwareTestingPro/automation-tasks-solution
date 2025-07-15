package com.qa.utils;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class util {
	 public static void deleteReportsFolder() throws IOException {
	    	File folder = new File("./reports");
	        if (folder.exists())
	            FileUtils.deleteDirectory(folder);
	    }
}