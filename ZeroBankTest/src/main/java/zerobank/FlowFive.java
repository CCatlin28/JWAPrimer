package zerobank;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FlowFive {

	public WebDriver driver;
	
	@BeforeTest
	public void setUp() {
		
		System.out.println("setUp method is running");

		//system path for browser webdriver
		System.setProperty("webdriver.chrome.driver", "C:\\SeleniumBrowserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get("http://zero.webappsecurity.com/");
		
		//asert we are on correct page... this method stops execution if fails
		String actualTitle = driver.getTitle();
		String expectedTitle = "Zero - Personal Banking - Loans - Credit Cards";
		assertEquals(actualTitle, expectedTitle, "Title did not match!! \n");
		
		//login
		driver.findElement(By.id("signin_button")).click();
		assertEquals(driver.getTitle(), "Zero - Log in", "Login Title did not match!! \n");
		driver.findElement(By.name("user_login")).sendKeys("username");
		driver.findElement(By.id("user_password")).sendKeys("password");
		driver.findElement(By.name("submit")).click();
		
		//get past security page
		driver.findElement(By.id("details-button")).click();
		driver.findElement(By.id("proceed-link")).click();
		
		assertEquals(driver.getTitle(), "Zero - Account Summary");
		
	}
	
	@AfterTest
	public void cleanUp(){
		System.out.println("cleanUp method is running");
		//close browser
		driver.close();
		driver.quit();
		
	}
	
	@Test
	public void statements() {
		//go to Statements page
		driver.findElement(By.linkText("Online Statements")).click();
		assertEquals(driver.getTitle(), "Zero - Online Statements", "Statements Title did not match \n");
		System.out.println("On Statements Page");
		
		
		//Assert old statement is available
		WebElement statement = driver.findElement(By.linkText("2009"));
		boolean isDisplayed = statement.isDisplayed();
		if(isDisplayed) {
			System.out.println("Statement is displayed");
		}else {
			System.out.println("Statement is not displayed");
		}
		Assert.assertTrue(isDisplayed);
		
	}
	

}
