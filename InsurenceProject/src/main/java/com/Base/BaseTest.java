package com.Base;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.listeners.WebEventListners;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.utils.Utilities;

public class BaseTest {
	public static WebDriver driver;
	public static Properties prop;
	public static String project_Directery=System.getProperty("user.dir");
	public ExtentReports report;
	public ExtentTest test;
	
	
	@BeforeTest
	public void listners(){
		report=new ExtentReports(project_Directery+"/target/Extent.html",true);
	}
	@AfterTest
	public void endListners(){
		report.flush();
		report.close();
	}
	
	

	@BeforeMethod
	public void initlization_SetUp(ITestResult result){
		test=report.startTest(result.getMethod().getMethodName());
		String OS=prop.getProperty("OS");
		String browser=prop.getProperty("browser");
		
		if (OS.equalsIgnoreCase("windows")) {
			if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", project_Directery+"\\Drivers\\chromedriver.exe");
				ChromeOptions options=new ChromeOptions();
				options.addArguments("--disable-notifications");
				options.addArguments("disable-infobars");
				driver=new ChromeDriver(options);
				
			} else if(browser.equalsIgnoreCase("firefox")){
				System.setProperty("webdriver.gecko.driver", project_Directery+"\\Drivers\\geckodriver.exe");
				driver=new FirefoxDriver();
				
			} else if (browser.equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver", project_Directery+"\\Drivers\\IEDriverServer.exe");
				driver=new InternetExplorerDriver();
			}
		} else if(OS.equalsIgnoreCase("mac")) {
			if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", project_Directery+"\\Drivers\\chromedriver.exe");
				ChromeOptions options=new ChromeOptions();
				options.addArguments("--disable-notifications");
				options.addArguments("disable-infobars");
				driver=new ChromeDriver(options);
				
			} else if(browser.equalsIgnoreCase("firefox")){
				System.setProperty("webdriver.gecko.driver", project_Directery+"\\Drivers\\geckodriver.exe");
				driver=new FirefoxDriver();
				
			} else if (browser.equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver", project_Directery+"\\Drivers\\IEDriverServer.exe");
				driver=new InternetExplorerDriver();
			}
		}else if (OS.equalsIgnoreCase("linux")) {
			if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", project_Directery+"\\Drivers\\chromedriver.exe");
				ChromeOptions options=new ChromeOptions();
				options.addArguments("--disable-notifications");
				options.addArguments("disable-infobars");
				driver=new ChromeDriver(options);
				
			} else if(browser.equalsIgnoreCase("firefox")){
				System.setProperty("webdriver.gecko.driver", project_Directery+"\\Drivers\\geckodriver.exe");
				driver=new FirefoxDriver();
				
			} else if (browser.equalsIgnoreCase("IE")) {
				System.setProperty("webdriver.ie.driver", project_Directery+"\\Drivers\\IEDriverServer.exe");
				driver=new InternetExplorerDriver();
			}
		}
		EventFiringWebDriver e_driver=new EventFiringWebDriver(driver);
		WebEventListners event=new WebEventListners();
		e_driver.register(event);
		driver=e_driver;		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Utilities.PAGE_LOAD_TIEMOUTS, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Utilities.IMPLECIT_WAIT, TimeUnit.SECONDS);
		
		driver.navigate().to(Utilities.geturl());
		}
	
	public WebDriverWait webDriverWait(/*WebDriver driver,*/int sec){
		return new WebDriverWait(driver, sec);
	}
	
	public void selectOption(WebElement element,String SelecteableText){
		Select select=new Select(element);
		select.selectByVisibleText(SelecteableText);
	}
	
	public void mouseMover(WebDriver driver,WebElement element){
		Actions action=new Actions(driver);
		action.moveToElement(element);
	}
	
	public void alertHandle(String HandleBtn){
		Alert alert=driver.switchTo().alert();
		
		if (HandleBtn.equalsIgnoreCase("ok") || HandleBtn.equalsIgnoreCase("accept")) {
			alert.accept();
		}
		else if (HandleBtn.equalsIgnoreCase("dismiss")||HandleBtn.equalsIgnoreCase("cancel")) {
			alert.dismiss();
		}
	}
	
	public void jsClick(WebDriver driver,WebElement element){
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", element);
		
	}
	
	public void switchframe(String frame){
		driver.switchTo().frame(frame);
	}
	
	public void switchdefaultframe(){
		driver.switchTo().defaultContent();
	}
	
	public void switchtoWindow(int windownum){
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> itr = windows.iterator();
		String windowName=null;		
		for (int i = 0; i <windownum; i++) {
			windowName=itr.next();			
		}
		driver.switchTo().window(windowName);
	}
	
	public void scrolltotheElement(WebElement element){
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView(true)", element);
	}
	
	public void rightclick_onElement(WebElement element){
		Actions action=new Actions(driver);
		action.contextClick(element).build().perform();
	}
	
	//Example ref from CRMPRO
	public void dynimicWebTable(String name){
		driver.findElement(By.xpath("//a[contains(text(),'"+name+"')]/parent::td//preceding-sibling::td/input")).click();
		
		//hear we can modify the data
		
		//2nd way
		
//		List<WebElement> tableRows = driver.findElements(By.xpath("//form[@id='vContactsForm']/table/tbody//tr"));
//		
//		//form[@id='vContactsForm']/table/tbody//tr[4]/td[2]/a
//		
//		String before_xpath="//form[@id='vContactsForm']/table/tbody//tr[";
//		String after_xpath="]/td[2]/a";
//		System.out.println(tableRows.size());
//		
//		for (int i = 4; i < tableRows.size()-1; i++) {
//			String Name=driver.findElement(By.xpath(before_xpath+i+after_xpath)).getText();
//			if(Name.contains(name)){
//				//form[@id='vContactsForm']/table/tbody//tr[4]/td[1]/input
//				driver.findElement(By.xpath("//form[@id='vContactsForm']/table/tbody//tr["+i+"]/td[1]/input")).click();
//			}
//		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public BaseTest()  {
		try {
			FileInputStream file=new FileInputStream(project_Directery+"\\src\\main\\java\\com\\configs\\config.properties");
			prop=new Properties();
			prop.load(file);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	

	
	@AfterMethod
	public void tearDown(ITestResult result){
		if (result.getStatus()==ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, "Test Case is Failed -> "+result.getName()+"\n"+
		result.getThrowable()+test.addScreenCapture(Utilities.takescreenshots(result.getName())));
		}
		else if (result.getStatus()==ITestResult.SKIP) {
			test.log(LogStatus.SKIP, "Test Case is Skipped -> "+result.getName());
		}
		else if (result.getStatus()==ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, "Test Case is Passed -> "+result.getName());
		}
		driver.quit();
		report.endTest(test);
	}
	
	@Test
	public void a(){
		
	}
	@Test
	public void b(){
		throw new ArithmeticException();
	}
	
	@Test
	public void c(){
		throw new ArithmeticException();
	}
	
	@Test
	public void d(){
		
	}
	
	
	
	
	
}

/*EventFiringWebDriver e_driver=new EventFiringWebDriver(driver);
WebEventListner event=new WebEventListner();
e_driver.register(event);
driver=e_driver;*/
