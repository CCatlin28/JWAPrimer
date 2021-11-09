package zerobank;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FlowOne {

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
		
	}
	
	@AfterTest
	public void cleanUp(){
		System.out.println("cleanUp method is running");
		//close browser
		driver.close();
		driver.quit();
		
	}
	
	@Test (priority =1)
	public void feedback() {
		
		//Go to Feedback page
		driver.findElement(By.xpath("//strong[contains(text(),'Feedback')]")).click();
		assertEquals(driver.getTitle(), "Zero - Contact Us");
		System.out.println("On Feedback Page");
	
		
		//fill out form and submit
		driver.findElement(By.id("name")).sendKeys("Customer");
		driver.findElement(By.id("email")).sendKeys("Customer@Selenium.com");
		driver.findElement(By.id("subject")).sendKeys("Test");
		driver.findElement(By.cssSelector("#comment")).sendKeys("This is a test of the Feedback System");
		driver.findElement(By.name("submit")).click();
		System.out.println("Submitting Feedback");
		
		//verify submission
		assertEquals(driver.findElement(By.tagName("h3")).getText(), "Feedback");
		
		System.out.println("Feedback was submitted");
	}
	
	@Test (priority=2)
	public void faqPage() {
		driver.findElement(By.partialLinkText("Zero Ba")).click();
		driver.findElement(By.xpath("//strong[contains(text(),'Feedback')]")).click();
		driver.findElement(By.cssSelector("#faq-link")).click();
		assertEquals(driver.findElement(By.tagName("h3")).getText(), "Frequently Asked Questions");
		
	}
	
}
