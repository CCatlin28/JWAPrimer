package zerobank;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FlowThree {



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
	public void fundTransfer() {
		//go to fundTransfer page
		driver.findElement(By.linkText("Transfer Funds")).click();
		assertEquals(driver.getTitle(), "Zero - Transfer Funds", "Transfer funds Title did not match \n");
		System.out.println("On Transfer Page");
		
		//fill out transfer form
		Select from = new Select(driver.findElement(By.id("tf_fromAccountId")));
		from.selectByIndex(0);
		Select recieve = new Select(driver.findElement(By.id("tf_toAccountId")));
		recieve.selectByIndex(1);
		driver.findElement(By.id("tf_amount")).sendKeys("100.00");
		driver.findElement(By.id("btn_submit")).click();
		assertEquals(driver.findElement(By.tagName("h2")).getText(), "Transfer Money & Make Payments - Verify", "Not on Transfer funds Verification Page \n");
		
		//submit transfer
		driver.findElement(By.xpath("//button[@id='btn_submit']")).click();
		assertEquals(driver.findElement(By.tagName("h2")).getText(), "Transfer Money & Make Payments - Confirm", "Not on Transfer funds Confirmation Page \n");
		
		//return to view transfer page
		driver.findElement(By.linkText("View transfers or make another transfer")).click();
		assertEquals(driver.getTitle(), "Zero - Transfer Funds", "Transfer funds Title did not match \n");
		System.out.println("On Transfer Page");
		
	}
}
