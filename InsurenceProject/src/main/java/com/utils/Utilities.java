package com.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.Base.BaseTest;

public class Utilities extends BaseTest {
	
	public static long PAGE_LOAD_TIEMOUTS=30;
	public static long IMPLECIT_WAIT=30;
	public static int FAILED_TESTS_RETRY_COUNT=2;
	


	public static String geturl(){
		String urltype=prop.getProperty("url");
		return prop.getProperty(urltype);
	}
	
	public static String takescreenshots(String filename){
		
		File srcfile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String destination=project_Directery+"//screenshots//failed//"+filename+".png";
		
		try {FileUtils.copyFile(srcfile, new File(destination));
		} catch (IOException e) {e.printStackTrace();}
		
		return destination;
	}

}
