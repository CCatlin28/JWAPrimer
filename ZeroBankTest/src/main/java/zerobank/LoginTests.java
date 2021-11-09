package zerobank;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginTests {

	public WebDriver driver;
	public WebElement alert;

	
	@BeforeTest
	public void setUp() {
		
		System.out.println("setUp method is running");

		//system path for browser webdriver
		System.setProperty("webdriver.chrome.driver", "C:\\SeleniumBrowserDrivers\\chromedriver.exe");
				
		//initiate webdriver
		driver = new ChromeDriver();
		
		//implicit wait
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		//open browser
		driver.get("http://zero.webappsecurity.com/");
		
		//asert we are on correct page... this method stops execution if fails
		String actualTitle = driver.getTitle();
		String expectedTitle = "Zero - Personal Banking - Loans - Credit Cards";
		
		//assert with message
		assertEquals(actualTitle, expectedTitle, "Title did not match!! \n");
		
	}
	
	@AfterTest
	public void cleanUp(){
		System.out.println("cleanUp method is running");
		//close browser
		driver.close();
		driver.quit();
		
	}
	
	@BeforeMethod
	public void resfreshPage() {
		System.out.println("resfreshPage method is running");
		//make sure on correct page
		driver.findElement(By.linkText("Zero Bank")).click();
		driver.findElement(By.id("signin_button")).click();
		assertEquals(driver.getTitle(), "Zero - Log in", "Login Title did not match!! \n");

		
	}
	
	@Test
	public void invalidPassword() {
		System.out.println("invalidPassword method is running");
		//enter username
		driver.findElement(By.name("user_login")).sendKeys("username");
				
		//enter invalid password
		driver.findElement(By.id("user_password")).sendKeys("P@ssword");
				
		//click on login button
		driver.findElement(By.name("submit")).click();
				
		//implicit wait time reset
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		//create variable for alert message
		alert = driver.findElement(By.xpath("//div[contains(text(),'Login and/or password are wrong.')]"));
				
		boolean isDisplayed = alert.isDisplayed();
		if(isDisplayed) {
			System.out.println("Login Failed Alert is Displayed");
			System.out.println(driver.getTitle());
		}else {
			System.out.println("Login Failed Alert is Not Displayed");
			System.out.println(driver.getTitle());
		}
		
	}
	
	@Test
	public void noUser() {
		System.out.println("noUser method is running");
			
		//enter invalid password
		driver.findElement(By.id("user_password")).sendKeys("password");
				
		//click on login button
		driver.findElement(By.name("submit")).click();
				
		//implicit wait time reset
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		//create variable for alert message
		alert = driver.findElement(By.xpath("//div[contains(text(),'Login and/or password are wrong.')]"));
				
		boolean isDisplayed = alert.isDisplayed();
		if(isDisplayed) {
			System.out.println("Login Failed Alert is Displayed");
			System.out.println(driver.getTitle());
		}else {
			System.out.println("Login Failed Alert is Not Displayed");
			System.out.println(driver.getTitle());
		}
		
	}
	
	@Test
	public void noPassword() {
		System.out.println("noPassword method is running");
		//enter username
		driver.findElement(By.name("user_login")).sendKeys("username");
				
		//click on login button
		driver.findElement(By.name("submit")).click();
				
		//implicit wait time reset
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		//create variable for alert message
		alert = driver.findElement(By.xpath("//div[contains(text(),'Login and/or password are wrong.')]"));
				
		boolean isDisplayed = alert.isDisplayed();
		if(isDisplayed) {
			System.out.println("Login Failed Alert is Displayed");
			System.out.println(driver.getTitle());
		}else {
			System.out.println("Login Failed Alert is Not Displayed");
			System.out.println(driver.getTitle());
		}
		
	}
	
	@Test
	public void noData() {
		System.out.println("noData method is running");
		//click on login button
		driver.findElement(By.name("submit")).click();
				
		//implicit wait time reset
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		//create variable for alert message
		alert = driver.findElement(By.xpath("//div[contains(text(),'Login and/or password are wrong.')]"));
				
		boolean isDisplayed = alert.isDisplayed();
		if(isDisplayed) {
			System.out.println("Login Failed Alert is Displayed");
			System.out.println(driver.getTitle());
		}else {
			System.out.println("Login Failed Alert is Not Displayed");
			System.out.println(driver.getTitle());
		}
		
	}
	
	@Test
	public void HappyLogInTest() {
		
		System.out.println("HappyLogInTest method is running");
				
		//enter username
		driver.findElement(By.name("user_login")).sendKeys("username");
				
		//enter password
		driver.findElement(By.id("user_password")).sendKeys("password");
				
		//click on login button
		driver.findElement(By.name("submit")).click();
				
		//implicit wait time reset
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				
		driver.findElement(By.id("details-button")).click();
		driver.findElement(By.id("proceed-link")).click();
		
		assertEquals(driver.getTitle(), "Zero - Account Summary");
		
		//reset unique to this test
		driver.findElement(By.xpath("//body/div[1]/div[1]/div[1]/div[1]/div[1]/ul[1]/li[3]/a[1]/i[1]")).click();
		driver.findElement(By.linkText("Logout")).click();
		
				
	}
}
