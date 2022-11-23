package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {

	// Element List
	By USER_NAME_FEILD = By.xpath("//*[@id=\"username\"]");
	By PASSWORD_FEILD = By.xpath("//*[@id=\"password\"]");
	By SIGNIN_BUTTON_FEILD = By.xpath("//*[@name=\"login\"]");
	By DASHBOARD_HEADER_FEILD = By.xpath("//*[@id=\"page-wrapper\"]/div[2]/div/h2");
	By CUSTOMER_MENU_LOCATOR = By.xpath("//*[@id=\"side-menu\"]/li[3]/a/span[1]");
	By ADD_CUSTOMER_MENU_LOCATOR = By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a");
	By ADD_CONTACT_HEADER_LOCATOR = By.xpath("//*[@id=\"page-wrapper\"]/div[3]/div[1]/div/div/div/div[1]/h5");
	By FULL_NAME_LOCATOR = By.xpath("//*[@id=\"account\"]");
	By COMPANY_DROPDOWN_LOCATOR = By.xpath("//select[@id=\"cid\"]");
	By EMAIL_LOCATOR = By.xpath("//*[@id=\"email\"]");
	By PHONE_LOCATOR = By.xpath("//*[@id=\"phone\"]");
	By COUNTRY_LOCATOR = By.xpath("//select[@id=\"country\"]");

	// Test Data
	String userName = "demo@techfios.com";
	String password = "abc123";

	WebDriver driver;
	String browser; //Declare variable 
	String url;
	
	@BeforeClass
	public void readConfig() {
		
		// How may type of class is their to reed a file - FileReader //Scanner //INputStream //BufferedReader
		
		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser user: " + browser);
			url = prop.getProperty("url");
			
			
		}catch(IOException e) {
			e.getStackTrace();
		}
	}

	@BeforeMethod
	public void init() {

		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		}

		else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.manage().deleteAllCookies();
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	//@Test(priority = 2)
	public void loginTest() {

		driver.findElement(USER_NAME_FEILD).sendKeys(userName);
		driver.findElement(PASSWORD_FEILD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON_FEILD).click();

		// Verification or validation for Dashboard
		Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FEILD).getText(), "Dashboard", "Wrong Page!!!");
	}

	//@Test(priority=1)
	public void negLoginTest() {

		driver.findElement(USER_NAME_FEILD).sendKeys(userName);
		driver.findElement(PASSWORD_FEILD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON_FEILD).click();

		// Verification or validation for Dashboard
		// Assert.assertEquals(driver.findElement(DASHBOARD_HEADER_FEILD).getText(),"Dashboard", "Wrong Page!!!");
	}
	
	@Test
	public void addCustomerTest() {
		
		//int num1, num2, num3;
		
		loginTest();
		
		driver.findElement(CUSTOMER_MENU_LOCATOR).click();
		
		waitForElement(driver, 5, ADD_CUSTOMER_MENU_LOCATOR); // Make method for wait
		
		driver.findElement(ADD_CUSTOMER_MENU_LOCATOR).click();
		
		Assert.assertEquals(driver.findElement(ADD_CONTACT_HEADER_LOCATOR).getText(), "Add Contact", "Wrong page!!!");
			
		
		driver.findElement(FULL_NAME_LOCATOR).sendKeys("December Selenium" + generateRandomNo(999)); // concanated random numbers to name
		
		selectFromDropdown(COMPANY_DROPDOWN_LOCATOR, "Techfios");  // Make method of dropdown
		
		driver.findElement(EMAIL_LOCATOR).sendKeys(generateRandomNo(9999) +"sam@gmail.com");
			    
		driver.findElement(PHONE_LOCATOR).sendKeys(generateRandomPhoneNo(999,999,9999));
		
		
		
	}
	

	private String generateRandomPhoneNo(int i, int j, int k) {
		Random prnd = new Random();
	    int num1 = prnd.nextInt(i);
	    int num2 = prnd.nextInt(j);
	    int num3 = prnd.nextInt(k);
	    //DecimalFormat df3 = new DecimalFormat("000"); // 3 zeros
	   // DecimalFormat df4 = new DecimalFormat("0000"); // 4 zeros
	    //String phoneNumber = df3.format(num1) + "-" + df3.format(num2) + "-" + df4.format(num3);
	    String phoneNumber = num1 + "-" + num2 + "-" + num3;
		return phoneNumber;
	  
	}

	//generate random number and control how many digits
	private int generateRandomNo(int boundaryNo) {
	
		Random rnd = new Random();
		int generatedNo = rnd.nextInt(boundaryNo);
		return generatedNo;
		
	}

	private void selectFromDropdown(By locator , String visibleText) {
		
		Select sel = new Select(driver.findElement(locator));
		sel.selectByVisibleText(visibleText);

	}

	private void waitForElement(WebDriver driver, int timeInSeconds, By locator) {
		
		WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		
	}


	//@AfterMethod
	public void tearDown() {
		driver.close();
	}

}
