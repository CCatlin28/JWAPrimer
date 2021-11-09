package zerobank;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FlowTwo {


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
	public void accountActivity() {
		
		//Go to Checking account page
		driver.findElement(By.linkText("Checking")).click();
		assertEquals(driver.getTitle(), "Zero - Account Activity");
		System.out.println("On Checking Account Activity Page");
	
		//Go to find activity page
		driver.findElement(By.linkText("Find Transactions")).click();
		assertEquals(driver.getTitle(), "Zero - Account Activity");
		System.out.println("On Find Account Activity Page");
		
		//fill in part of form and complete search
		driver.findElement(By.xpath("//input[@id='aa_description']")).sendKeys("CAR PAYMENT");
		driver.findElement(By.tagName("button")).click();
		System.out.println("Searching...");
		
		//verify search
		assertEquals(driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div[2]/table[1]/tbody[1]/tr[1]/td[4]")).getText(), "1548");
		System.out.println("Car Payment was found");
	}
	
	

}
