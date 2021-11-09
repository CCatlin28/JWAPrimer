package zerobank;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FlowFour {

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
	
	@Test (priority=1)
	public void payBills() {
		//go to payBills page
		driver.findElement(By.linkText("Pay Bills")).click();
		assertEquals(driver.getTitle(), "Zero - Pay Bills", "Pay Bills Title did not match \n");
		System.out.println("On Pay Bills Page");
		
		//fill out pay bill form
		Select bill = new Select(driver.findElement(By.cssSelector("#sp_payee")));
		bill.selectByIndex(3);
		Select account = new Select(driver.findElement(By.cssSelector("#sp_account")));
		account.selectByIndex(1);
		driver.findElement(By.cssSelector("#sp_amount")).sendKeys("50.00");
//		driver.findElement(By.cssSelector("#pay_saved_payees")).click();
		
		//verify complete form alert appears... I have the wrong name so this won't work... 
		//message disapears when attempt to inspect so not sure how to test this appears
//		Alert jsAlert = driver.switchTo().alert();
//		String alertText = jsAlert.getText();
//		System.out.println("Alert text is ---- "+alertText);
		
		//finish filling out form
		driver.findElement(By.cssSelector("#sp_date")).sendKeys("2021-10-09");
		driver.findElement(By.cssSelector("#pay_saved_payees")).click();
		assertEquals(driver.findElement(By.cssSelector("#alert_content")).getText(),"The payment was successfully submitted.");
				
	}
	
	@Test (priority=2)
	public void buyForeignCurrency() {
		//go to ForeignCurrencyPage page
		driver.findElement(By.partialLinkText("Purchase Foreign Cur")).click();
		assertEquals(driver.findElement(By.xpath("//h2[contains(text(),'Purchase foreign currency cash')]")).getText(),"Purchase foreign currency cash", "Purchase Currency Title did not match \n");
		System.out.println("On Foreign Currency Page");
		
		//verify empty form alert
		driver.findElement(By.cssSelector("#purchase_cash")).click();
		Alert jsAlert = driver.switchTo().alert();
		String alertText = jsAlert.getText();
		System.out.println("Alert text is ---- "+alertText);
		assertEquals(alertText, "Please, ensure that you have filled all the required fields with valid values.");
		jsAlert.accept();
		
		//fill out buy currency form
		Select currency = new Select(driver.findElement(By.id("pc_currency")));	
		currency.selectByVisibleText("Great Britain (pound)");
		driver.findElement(By.cssSelector("#pc_amount")).sendKeys("100.00");
		driver.findElement(By.name("inDollars")).click();
		driver.findElement(By.id("pc_calculate_costs")).click();
		assertEquals(driver.findElement(By.cssSelector("#pc_conversion_amount")).getText(),"59.02 pound (GBP) = 100.00 U.S. dollar (USD)");
		System.out.println("The conversion rate has been calculated");
		driver.findElement(By.cssSelector("#purchase_cash")).click();
		assertEquals(driver.findElement(By.cssSelector("#alert_content")).getText(), "Foreign currency cash was successfully purchased.");
		
		
				
	}
}
