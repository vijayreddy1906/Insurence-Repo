package com.listeners;
/*
use this when u want to add screenshots in TestNG Reports add this class in Listners in testng.xml
	<listeners>
		<listener class-name="com.listners.Listener"></listener>
	</listeners>
	before Test u have to past listners

*/

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.Base.BaseTest;
import com.utils.Utilities;



public class Listener extends BaseTest implements ITestListener
{
	
	public void onTestFailure(ITestResult result) 
	{
		if(!result.isSuccess())
		{
			
			Reporter.log("<a href='" + Utilities.takescreenshots(result.getName()) + "'> <img src='" + Utilities.takescreenshots(result.getName()) + "' height='100' width='100'/> </a>");
			Reporter.log("Test has Failed:" + result.getMethod().getMethodName());
		}
	}
	
	
	
	
	public void onTestSuccess(ITestResult obj1) 
	{
		if(obj1.isSuccess())
		{
			Reporter.log("Test has Success:" + obj1.getMethod().getMethodName());
		}
	}
	
	
	
	
	
	public void onTestStart(ITestResult arg0) 
	{
		Reporter.log("Test started Running:" + arg0.getMethod().getMethodName());
	}
	
		
	public void onTestSkipped(ITestResult result) 
	{
		Reporter.log("Test is skipped:" + result.getMethod().getMethodName());
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) 
	{
	
		
	}

	public void onFinish(ITestContext arg0) 
	{
		//Reporter.log("Test is finished:" + ((ITestResult) arg0).getMethod().getMethodName());
		
	}

		
}
