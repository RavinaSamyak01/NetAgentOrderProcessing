package netAgent_BasePackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import connect_OCBaseMethods.OrderCreation;
import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class BaseInit {

	public static StringBuilder msg = new StringBuilder();
	public static WebDriver Driver;
	public static Properties storage = new Properties();

	// public static GenerateData genData;
	public static String SuccMsgReplnsh;
	public static String WOID;
	public static String WOTP;
	public ResourceBundle rb = ResourceBundle.getBundle("config");
	public String EmailID = rb.getString("MainEmailAddress");

	public static String PUId, JobId, Client, FSLName, Agent;
	public static String Part1, Part1Name, Part2, Part2Name, P2Field2, P2Field3, P2Field4, P2Field5;
	public static String LOCCode1, LOC1LEN, LOC1WID, LOC1HGT, LOCCode2, LOC2Part;

	public static Logger logger;
	public static ExtentReports report;
	public static ExtentTest test;
	String BaseURL;

	@BeforeSuite
	public void beforeMethod() throws Exception {
		System.out.println("MainEmailAddress " + EmailID);
		if (Driver == null) {
			String logFilename = this.getClass().getSimpleName();
			logger = Logger.getLogger(logFilename);
			startTest();
			storage = new Properties();
			FileInputStream fi = new FileInputStream(".\\src\\main\\resources\\config.properties");
			storage.load(fi);
			logger.info("initialization of the properties file is done");

			// --Opening Chrome Browser
			DesiredCapabilities capabilities = new DesiredCapabilities();
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();

			options.addArguments("--headless", "--window-size=1920, 1080");
			// options.addArguments("--incognito");
			// options.addArguments("--test-type");
			options.addArguments("--disable-extensions");
			options.addArguments("--no-sandbox");
			options.addArguments("enable-automation");
			options.addArguments("--dns-prefetch-disable");
			options.addArguments("--disable-gpu");
			options.addArguments("enable-features=NetworkServiceInProcess");
			options.addArguments("--disable-infobars");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--force-device-scale-factor=1");
			// options.addArguments("--aggressive-cache-discard");
			// options.addArguments("--disable-cache");
			// options.addArguments("--disable-application-cache");
			// options.addArguments("--disable-offline-load-stale-cache");
			// options.addArguments("--disk-cache-size=0");
			options.addArguments("--no-proxy-server");
			options.addArguments("--log-level=3");
			options.addArguments("--silent");
			// options.addArguments("--disable-browser-side-navigation");
			options.addArguments("--no-proxy-server");
			options.addArguments("--proxy-bypass-list=*");
			options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

			System.setProperty("webDriver.chrome.silentOutput", "true");
			// options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
			// options.setProxy(null);
			// options.setPageLoadStrategy(PageLoadStrategy.NONE);
			// options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
			String downloadFilepath = System.getProperty("user.dir") + "\\src\\main\\resources\\Downloads";
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			// chromePrefs.put("download.prompt_for_download", "false");
			chromePrefs.put("safebrowsing.enabled", "false");
			chromePrefs.put("download.default_directory", downloadFilepath);
			options.setExperimentalOption("prefs", chromePrefs);
			capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			capabilities.setPlatform(Platform.ANY);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			Driver = new ChromeDriver(options);
			TimeUnit.SECONDS.sleep(1);

			// Default size
			Driver.manage().window().maximize();
			Dimension currentDimension = Driver.manage().window().getSize();
			int height = currentDimension.getHeight();
			int width = currentDimension.getWidth();
			System.out.println("Current height: " + height);
			System.out.println("Current width: " + width);
			System.out.println("window size==" + Driver.manage().window().getSize());

			// Driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

			// options.addArguments("--start-maximized");
			// options.addArguments("--headless");
			// options.addArguments("window-size=1936,1066");
			// options.addArguments("window-size=1936,1056");
			// options.addArguments("window-size=1036x776");

			// -Clear Result excel
			resetResultofExcel();

			// --NetAgent Login
			Login();

		}
	}

	public void Connectlogin() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		// Actions act = new Actions(driver);
		String Env = storage.getProperty("Env");
		System.out.println("Env " + Env);

		String baseUrl = null;
		if (Env.equalsIgnoreCase("Pre-Prod")) {
			baseUrl = storage.getProperty("ConnectPREPRODURL");
			Driver.get(baseUrl);
			Thread.sleep(2000);
			try {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("login")));
				String UserName = storage.getProperty("ConnectPREPRODUserName");
				highLight(isElementPresent("ConnectUserName_id"), Driver);
				isElementPresent("ConnectUserName_id").sendKeys(UserName);
				logger.info("Entered UserName");
				String Password = storage.getProperty("ConnectPREPRODPassword");
				highLight(isElementPresent("ConnectPassword_id"), Driver);
				isElementPresent("ConnectPassword_id").sendKeys(Password);
				logger.info("Entered Password");

			} catch (Exception e) {
				msg.append("URL is not working==FAIL");
				getScreenshot(Driver, "LoginIssue");
				Driver.quit();
				Env = storage.getProperty("Env");
				String File = ".\\Report\\NetAgent_Screenshot\\LoginIssue.png";
				Env = storage.getProperty("Env");
				String subject = "Selenium Automation Script:" + Env + " NetAgent";

				try {
//					/kunjan.modi@samyak.com, pgandhi@samyak.com,parth.doshi@samyak.com
					/*
					 * SendEmailOld.
					 * sendMail("ravina.prajapati@samyak.com, asharma@samyak.com, parth.doshi@samyak.com"
					 * , subject, msg.toString(), File);
					 */

					SendEmailOld.sendMail("ravina.prajapati@samyak.com", subject, msg.toString(), File);

				} catch (Exception ex) {
					logger.error(ex);
					logger.info("Line number is: " + ex.getStackTrace()[0].getLineNumber());

				}

			}

		} else if (Env.equalsIgnoreCase("STG")) {
			baseUrl = storage.getProperty("ConnectSTGURL");
			Driver.get(baseUrl);
			Thread.sleep(2000);
			try {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("login")));
				String UserName = storage.getProperty("ConnectSTGUserName");
				highLight(isElementPresent("ConnectUserName_id"), Driver);
				isElementPresent("ConnectUserName_id").sendKeys(UserName);
				logger.info("Entered UserName");
				String Password = storage.getProperty("ConnectSTGPassword");
				highLight(isElementPresent("ConnectPassword_id"), Driver);
				isElementPresent("ConnectPassword_id").sendKeys(Password);
				logger.info("Entered Password");
			} catch (Exception e) {
				msg.append("URL is not working==FAIL");
				getScreenshot(Driver, "LoginIssue");
				Driver.quit();
				Env = storage.getProperty("Env");
				String File = ".\\Report\\NetAgent_Screenshot\\LoginIssue.png";
				Env = storage.getProperty("Env");
				String subject = "Selenium Automation Script:" + Env + "NetAgent";

				try {
//					/kunjan.modi@samyak.com, pgandhi@samyak.com,parth.doshi@samyak.com
					/*
					 * SendEmailOld.
					 * sendMail("ravina.prajapati@samyak.com, asharma@samyak.com, parth.doshi@samyak.com"
					 * , subject, msg.toString(), File);
					 */

					SendEmailOld.sendMail("ravina.prajapati@samyak.com", subject, msg.toString(), File);

				} catch (Exception ex) {
					logger.error(ex);
					logger.info("Line number is: " + ex.getStackTrace()[0].getLineNumber());

				}

			}

		} else if (Env.equalsIgnoreCase("Test")) {
			baseUrl = storage.getProperty("ConnectTestURL");
			Driver.get(baseUrl);
			Thread.sleep(2000);
			try {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("login")));
				String UserName = storage.getProperty("ConnectTestUserName");
				highLight(isElementPresent("ConnectUserName_id"), Driver);
				isElementPresent("ConnectUserName_id").sendKeys(UserName);
				logger.info("Entered UserName");
				String Password = storage.getProperty("ConnectTestPass");
				highLight(isElementPresent("ConnectPassword_id"), Driver);
				isElementPresent("ConnectPassword_id").sendKeys(Password);
				logger.info("Entered Password");
			} catch (Exception e) {
				msg.append("URL is not working==FAIL");
				getScreenshot(Driver, "LoginIssue");
				Driver.quit();
				Env = storage.getProperty("Env");
				String File = ".\\Report\\NetAgent_Screenshot\\LoginIssue.png";
				Env = storage.getProperty("Env");
				String subject = "Selenium Automation Script:" + Env + "NetAgent";

				try {
//					/kunjan.modi@samyak.com, pgandhi@samyak.com,parth.doshi@samyak.com
					/*
					 * SendEmailOld.
					 * sendMail("ravina.prajapati@samyak.com, asharma@samyak.com, parth.doshi@samyak.com"
					 * , subject, msg.toString(), File);
					 */

					SendEmailOld.sendMail("ravina.prajapati@samyak.com", subject, msg.toString(), File);

				} catch (Exception ex) {
					logger.error(ex);
					logger.info("Line number is: " + ex.getStackTrace()[0].getLineNumber());

				}

			}

		}

		BaseURL = baseUrl;
		highLight(isElementPresent("ConnectLogin_id"), Driver);
		isElementPresent("ConnectLogin_id").click();
		logger.info("Login done");
		getScreenshot(Driver, "ConnectLogin");
		wait.until(
				ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[contains(text(),'Logging In...')]")));
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception ee) {
			WebDriverWait wait1 = new WebDriverWait(Driver, 50);
			wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		}
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));

	}

	public void ConnectlogOut() throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		Actions act = new Actions(Driver);
		JavascriptExecutor js = (JavascriptExecutor) Driver;

		WebElement LogOut = isElementPresent("ConnectLogOut_linkText");
		act.moveToElement(LogOut).build().perform();
		wait.until(ExpectedConditions.elementToBeClickable(LogOut));
		highLight(LogOut, Driver);
		js.executeScript("arguments[0].click();", LogOut);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@ng-bind=\"LogoutMessage\"]")));
		String LogOutMsg = isElementPresent("ConnectLogOutMsg_xpath").getText();
		logger.info("Logout Message is displayed==" + LogOutMsg);
		logger.info("Logout done");
		getScreenshot(Driver, "ConnectLogOut");

	}

	@BeforeMethod
	public void testMethodName(Method method) {

		String testName = method.getName();
		test = report.startTest(testName);

	}

	public static void startTest() {
		// You could find the xml file below. Create xml file in your project and copy
		// past the code mentioned below

		System.setProperty("extent.reporter.pdf.start", "true");
		System.setProperty("extent.reporter.pdf.out", "./Report/PDFExtentReport/ExtentPDF.pdf");

		// report.loadConfig(new File(System.getProperty("user.dir")
		// +"\\extent-config.xml"));
		report = new ExtentReports("./Report/ExtentReport/ExtentReportResults.html", true);
		// test = report.startTest();
	}

	public static void endTest() {
		report.endTest(test);
		report.flush();
	}

	public static String getScreenshot(WebDriver Driver, String screenshotName) throws IOException {

		TakesScreenshot ts = (TakesScreenshot) Driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots" under src
		// folder
		String destination = System.getProperty("user.dir") + "/Report/NA_Screenshot/" + screenshotName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	public static String getFailScreenshot(WebDriver Driver, String screenshotName) throws Exception {
		// String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) Driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots" under src
		// folder
		String destination = System.getProperty("user.dir") + "/Report/FailedTestsScreenshots/" + screenshotName
				+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	public static WebElement isElementPresent(String propkey) {

		try {

			if (propkey.contains("xpath")) {

				return Driver.findElement(By.xpath(storage.getProperty(propkey)));

			} else if (propkey.contains("id")) {

				return Driver.findElement(By.id(storage.getProperty(propkey)));

			} else if (propkey.contains("name")) {

				return Driver.findElement(By.name(storage.getProperty(propkey)));

			} else if (propkey.contains("linkText")) {

				return Driver.findElement(By.linkText(storage.getProperty(propkey)));

			} else if (propkey.contains("className")) {

				return Driver.findElement(By.className(storage.getProperty(propkey)));

			} else if (propkey.contains("cssSelector")) {

				return Driver.findElement(By.cssSelector(storage.getProperty(propkey)));

			} else {

				System.out.println("propkey is not defined");

				logger.info("prop key is not defined");
			}

		} catch (Exception e) {

		}
		return null;

	}

	public static void highLight(WebElement element, WebDriver driver) {
		// for (int i = 0; i < 2; i++) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
					"color: black; border: 4px solid red;");
			Thread.sleep(500);
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// }

	}

	@AfterMethod
	public void getResult(ITestResult result) throws Exception {

		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
			// test.log(LogStatus.FAIL, "Test Case Failed is " +
			// result.getThrowable().getMessage());
			test.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
			// To capture screenshot path and store the path of the screenshot in the string
			// "screenshotPath"
			// We do pass the path captured by this mehtod in to the extent reports using
			// "logger.addScreenCapture" method.
			String screenshotPath = getFailScreenshot(Driver, result.getName());
			// To add it in the extent report
			test.log(LogStatus.FAIL, test.addScreenCapture(screenshotPath));
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(LogStatus.PASS, "Test Case Pass is " + result.getName());
			String screenshotPath = getScreenshot(Driver, result.getName());
			// To add it in the extent report
			test.log(LogStatus.PASS, test.addScreenCapture(screenshotPath));
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
		}
	}

	// --Updated by Ravina
	public void Login() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);

		String Env = storage.getProperty("Env");
		String baseUrl = null;
		if (Env.equalsIgnoreCase("Pre-Prod")) {
			baseUrl = storage.getProperty("PREProdURL");
			Driver.get(baseUrl);
			logger.info("Url opened");
			try {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("login")));
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name("loginForm")));
				getScreenshot(Driver, "LoginPage");
				String UserName = storage.getProperty("PreProdUserName");
				String password = storage.getProperty("PreProdPass");
				// Enter User_name and Password and click on Login
				isElementPresent("UserName_id").clear();
				isElementPresent("UserName_id").sendKeys(UserName);
				isElementPresent("Password_id").clear();
				isElementPresent("Password_id").sendKeys(password);
			} catch (Exception e) {
				msg.append("URL is not working==FAIL");
				getScreenshot(Driver, "LoginIssue");
				Driver.quit();
				Env = storage.getProperty("Env");
				String File = ".\\Report\\NA_Screenshot\\LoginIssue.png";
				String subject = "Selenium Automation Script: " + Env + " NetAgent Portal";

				try {
//					/kunjan.modi@samyak.com, pgandhi@samyak.com,parth.doshi@samyak.com

					SendEmailOld.sendMail(EmailID, subject, msg.toString(), File);

					/*
					 * SendEmailOld.
					 * sendMail("ravina.prajapati@samyak.com, asharma@samyak.com, parth.doshi@samyak.com"
					 * , subject, msg.toString(), File);
					 */
					// SendEmailOld.sendMail("ravina.prajapati@samyak.com, asharma@samyak.com
					// ,parth.doshi@samyak.com", subject, msg.toString(), File);

				} catch (Exception ex) {
					logger.error(ex);
					logger.info("Line number is: " + ex.getStackTrace()[0].getLineNumber());

				}
			}

		} else if (Env.equalsIgnoreCase("STG")) {
			baseUrl = storage.getProperty("STGURL");
			Driver.get(baseUrl);
			logger.info("Url opened");
			try {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("login")));
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name("loginForm")));
				getScreenshot(Driver, "LoginPage");
				String UserName = storage.getProperty("STGUserName");
				String password = storage.getProperty("STGPassword");
				// Enter User_name and Password and click on Login
				isElementPresent("UserName_id").clear();
				isElementPresent("UserName_id").sendKeys(UserName);
				isElementPresent("Password_id").clear();
				isElementPresent("Password_id").sendKeys(password);
			} catch (Exception e) {
				msg.append("URL is not working==FAIL");
				getScreenshot(Driver, "LoginIssue");
				Driver.quit();
				Env = storage.getProperty("Env");
				String File = ".\\Report\\NA_Screenshot\\LoginIssue.png";
				String subject = "Selenium Automation Script: " + Env + " NetAgent Portal";

				try {
//					/kunjan.modi@samyak.com, pgandhi@samyak.com,parth.doshi@samyak.com

					SendEmailOld.sendMail(EmailID, subject, msg.toString(), File);

					/*
					 * SendEmailOld.
					 * sendMail("ravina.prajapati@samyak.com, asharma@samyak.com, parth.doshi@samyak.com"
					 * , subject, msg.toString(), File);
					 */
					// SendEmailOld.sendMail("ravina.prajapati@samyak.com, asharma@samyak.com
					// ,parth.doshi@samyak.com", subject, msg.toString(), File);

				} catch (Exception ex) {
					logger.error(ex);
					logger.info("Line number is: " + ex.getStackTrace()[0].getLineNumber());

				}
			}

		} else if (Env.equalsIgnoreCase("Test")) {
			baseUrl = storage.getProperty("TestURL");
			Driver.get(baseUrl);
			logger.info("Url opened");
			try {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("login")));
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name("loginForm")));
				getScreenshot(Driver, "LoginPage");
				String UserName = storage.getProperty("TestUserName");
				String password = storage.getProperty("TestPassword");
				// Enter User_name and Password and click on Login
				isElementPresent("UserName_id").clear();
				isElementPresent("UserName_id").sendKeys(UserName);
				isElementPresent("Password_id").clear();
				isElementPresent("Password_id").sendKeys(password);
			} catch (Exception e) {
				msg.append("URL is not working==FAIL");
				getScreenshot(Driver, "LoginIssue");
				Driver.quit();
				Env = storage.getProperty("Env");
				String File = ".\\Report\\NA_Screenshot\\LoginIssue.png";
				String subject = "Selenium Automation Script: " + Env + "  NetAgent Portal";

				try {
//					/kunjan.modi@samyak.com, pgandhi@samyak.com,parth.doshi@samyak.com

					SendEmailOld.sendMail(EmailID, subject, msg.toString(), File);

					/*
					 * SendEmailOld.
					 * sendMail("ravina.prajapati@samyak.com, asharma@samyak.com, parth.doshi@samyak.com"
					 * , subject, msg.toString(), File);
					 */
					// SendEmailOld.sendMail("ravina.prajapati@samyak.com, asharma@samyak.com
					// ,parth.doshi@samyak.com", subject, msg.toString(), File);

				} catch (Exception ex) {
					logger.error(ex);
					logger.info("Line number is: " + ex.getStackTrace()[0].getLineNumber());

				}
			}

		}
		String BaseUrl = baseUrl;
		msg.append(baseUrl + "\n\n");
		isElementPresent("Login_id").click();
		Thread.sleep(10000);
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));

		} catch (Exception e) {
			WebDriverWait wait1 = new WebDriverWait(Driver, 120);
			wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait1.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));

		}
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));

	
		getScreenshot(Driver, "HomeScreen");

	}

	public void logOut() throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		Actions act = new Actions(Driver);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'userthumb')]")));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'userthumb')]")));
		WebElement LogoutDiv = isElementPresent("LogOutDiv_xpath");
		act.moveToElement(LogoutDiv).build().perform();
		act.moveToElement(LogoutDiv).click().build().perform();
		WebElement LogOut = isElementPresent("LogOut_linkText");
		wait.until(ExpectedConditions.visibilityOf(LogOut));
		wait.until(ExpectedConditions.elementToBeClickable(LogOut));
		act.moveToElement(LogOut).click().build().perform();
		logger.info("Clicked on LogOut");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		getScreenshot(Driver, "LogOutDiv");
	}

	public void Complete() throws Exception {
		Driver.close();
		Driver.quit();

	}

	public String CuDate() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy ");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		System.out.println("Current Date :- " + date1);
		return date1;
	}

	public static String getDate(Calendar cal) {
		return "" + cal.get(Calendar.MONTH) + "/" + (cal.get(Calendar.DATE) + 1) + "/" + cal.get(Calendar.YEAR);
	}

	public static Date addDays(Date d, int days) {
		d.setTime(d.getTime() + days * 1000 * 60 * 60 * 24);
		return d;
	}

	public void scrollToElement(WebElement element, WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public static String getData(String sheetName, int row, int col)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String Cell = null;
		String Env = storage.getProperty("Env");
		String FilePath = null;
		if (Env.equalsIgnoreCase("Pre-Prod")) {
			FilePath = storage.getProperty("PrePRODFile");
		} else if (Env.equalsIgnoreCase("STG")) {
			FilePath = storage.getProperty("STGFile");
		} else if (Env.equalsIgnoreCase("Test")) {
			FilePath = storage.getProperty("TestFile");
		}

		File src = new File(FilePath);

		FileInputStream FIS = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(FIS);
		try {
			Sheet sh1 = workbook.getSheet(sheetName);

			DataFormatter formatter = new DataFormatter();
			Cell = formatter.formatCellValue(sh1.getRow(row).getCell(col));
			FIS.close();

		} catch (Exception e) {
			FIS.close();
			logger.info("Issue in GetData" + e);

		}
		return Cell;
	}

	public static void setData(String sheetName, int row, int col, String value)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String Env = storage.getProperty("Env");
		String FilePath = null;
		if (Env.equalsIgnoreCase("Pre-Prod")) {
			FilePath = storage.getProperty("PrePRODFile");
		} else if (Env.equalsIgnoreCase("STG")) {
			FilePath = storage.getProperty("STGFile");
		} else if (Env.equalsIgnoreCase("Test")) {
			FilePath = storage.getProperty("TestFile");
		}
		File src = new File(FilePath);
		FileInputStream fis = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(fis);
		FileOutputStream fos1 = new FileOutputStream(src);
		Sheet sh = workbook.getSheet(sheetName);

		try {
			sh.getRow(row).createCell(col).setCellValue(value);
			workbook.write(fos1);
			fos1.close();
			fis.close();

		} catch (Exception e) {
			fos1.close();
			fis.close();
			logger.info("Issue in SetData" + e);

		}
	}

	public static int getTotalRow(String sheetName)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String Env = storage.getProperty("Env");
		String FilePath = null;
		if (Env.equalsIgnoreCase("Pre-Prod")) {
			FilePath = storage.getProperty("PrePRODFile");
		} else if (Env.equalsIgnoreCase("STG")) {
			FilePath = storage.getProperty("STGFile");
		} else if (Env.equalsIgnoreCase("Test")) {
			FilePath = storage.getProperty("TestFile");
		}
		File src = new File(FilePath);

		FileInputStream FIS = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(FIS);
		Sheet sh1 = workbook.getSheet(sheetName);

		int rowNum = sh1.getLastRowNum() + 1;
		return rowNum;

	}

	public static int getTotalCol(String sheetName)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String Env = storage.getProperty("Env");
		String FilePath = null;
		if (Env.equalsIgnoreCase("Pre-Prod")) {
			FilePath = storage.getProperty("PrePRODFile");
		} else if (Env.equalsIgnoreCase("STG")) {
			FilePath = storage.getProperty("STGFile");
		} else if (Env.equalsIgnoreCase("Test")) {
			FilePath = storage.getProperty("TestFile");
		}
		File src = new File(FilePath);

		FileInputStream FIS = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(FIS);
		Sheet sh1 = workbook.getSheet(sheetName);

		Row row = sh1.getRow(0);
		int colNum = row.getLastCellNum();
		return colNum;

	}

	public void pagination() {
		Actions act = new Actions(Driver);
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;

		// Check paging
		List<WebElement> pagination = Driver
				.findElements(By.xpath("//*[@class=\"dx-pages\"]//div[contains(@aria-label,'Page')]"));
		System.out.println("size of pagination is==" + pagination.size());

		if (pagination.size() > 0) {
			WebElement pageinfo = Driver.findElement(By.xpath("//*[@class=\"dx-info\"]"));
			System.out.println("page info is==" + pageinfo.getText());
			WebElement pagerdiv = Driver.findElement(By.className("dx-pages"));
			WebElement secndpage = Driver.findElement(By.xpath("//*[@aria-label=\"Page 2\"]"));
			WebElement prevpage = Driver.findElement(By.xpath("//*[@aria-label=\"Previous page\"]"));
			WebElement nextpage = Driver.findElement(By.xpath("//*[@aria-label=\" Next page\"]"));
			WebElement firstpage = Driver.findElement(By.xpath("//*[@aria-label=\"Page 1\"]"));
			// Scroll
			js.executeScript("arguments[0].scrollIntoView();", pagerdiv);

			if (pagination.size() > 1) {
				// click on page 2
				secndpage = Driver.findElement(By.xpath("//*[@aria-label=\"Page 2\"]"));
				act.moveToElement(secndpage).click().perform();
				System.out.println("Clicked on page 2");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				// click on previous button
				prevpage = Driver.findElement(By.xpath("//*[@aria-label=\"Previous page\"]"));
				prevpage = Driver.findElement(By.xpath("//*[@aria-label=\"Previous page\"]"));
				act.moveToElement(prevpage).click().perform();
				System.out.println("clicked on previous page");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				// click on next button
				nextpage = Driver.findElement(By.xpath("//*[@aria-label=\" Next page\"]"));
				nextpage = Driver.findElement(By.xpath("//*[@aria-label=\" Next page\"]"));
				act.moveToElement(nextpage).click().perform();
				System.out.println("clicked on next page");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				firstpage = Driver.findElement(By.xpath("//*[@aria-label=\"Page 1\"]"));
				act.moveToElement(firstpage).click().perform();
				System.out.println("Clicked on page 1");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} else {
				System.out.println("Only 1 page is exist");
			}

		} else {
			System.out.println("pagination is not exist");
		}
	}

	@AfterSuite
	public void SendEmail() throws Exception {
		try {
			logOut();
		} catch (Exception logout) {

		}

		report.flush();
		// --Close browser
		Complete();
		System.out.println("====Sending Email=====");
		logger.info("====Sending Email=====");
		// Send Details email

		msg.append("*** This is automated generated email and send through automation script ***" + "\n");
		msg.append("Please find attached file of Report and Log");

		String Env = storage.getProperty("Env");
		msg.append("Environment==" + Env + ":" + BaseURL + "\n\n");
		String subject = "Selenium Automation Script: " + Env + " NetAgent Order Processing";
		String File = null;
		if (Env.equalsIgnoreCase("Test")) {
			File = ".\\src\\main\\resources\\NA OCP Result_Test.xlsx,.\\Report\\ExtentReport\\ExtentReportResults.html,.\\Report\\log\\NetAgent-Order Processing.html";
		} else if (Env.equalsIgnoreCase("STG")) {
			File = ".\\src\\main\\resources\\NA OCP Result_STG.xlsx,.\\Report\\ExtentReport\\ExtentReportResults.html,.\\Report\\log\\NetAgent-Order Processing.html";

		} else if (Env.equalsIgnoreCase("Pre-Prod")) {
			File = ".\\src\\main\\resources\\NA OCP Result_PreProd.xlsx,.\\Report\\ExtentReport\\ExtentReportResults.html,.\\Report\\log\\NetAgent-Order Processing.html";

		} else if (Env.equalsIgnoreCase("PROD")) {
			File = ".\\src\\main\\resources\\NA OCP Result_Prod.xlsx,.\\Report\\ExtentReport\\ExtentReportResults.html,.\\Report\\log\\NetAgent-Order Processing.html";

		}
		try {
//			/kunjan.modi@samyak.com, pgandhi@samyak.com,parth.doshi@samyak.com

			SendEmailOld.sendMail(EmailID, subject, msg.toString(), File);

			/*
			 * SendEmailOld.sendMail(
			 * "ravina.prajapati@samyak.com, asharma@samyak.com, parth.doshi@samyak.com,saurabh.jain@samyak.com"
			 * , subject, msg.toString(), File);
			 */
			// SendEmailOld.sendMail("ravina.prajapati@samyak.com, asharma@samyak.com
			// ,parth.doshi@samyak.com", subject, msg.toString(), File);

		} catch (Exception ex) {
			logger.error(ex);
			logger.info("Line number is: " + ex.getStackTrace()[0].getLineNumber());

		}
	}

	public void isFileDownloaded(String fileName) {
		String downloadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\Downloads";
		File dir = new File(downloadPath);
		File[] dirContents = dir.listFiles();

		for (int i = 0; i < dirContents.length; i++) {
			if (dirContents[i].getName().contains(fileName)) {
				logger.info("File is exist with FileName==" + fileName);
				// File has been found, it can now be deleted:
				dirContents[i].delete();
				logger.info(fileName + " File is Deleted");
				break;

			} else {
				logger.info("File is not exist with Filename==" + fileName);
			}
		}

	}

	public static boolean waitUntilFileToDownload(String fileName) throws InterruptedException {
		/*
		 * String folderLocation = System.getProperty("user.dir") +
		 * "\\src\\main\\resources\\Downloads"; File directory = new
		 * File(folderLocation); boolean downloadinFilePresence = false; File[]
		 * filesList = null; LOOP: while (true) { filesList = directory.listFiles(); for
		 * (File file : filesList) { //System.out.println(file.getName()); String FName
		 * = file.getName(); downloadinFilePresence = file.getName().contains(Name);
		 * 
		 * } if (downloadinFilePresence) { for (; downloadinFilePresence;) { //
		 * Thread.sleep(5000); continue LOOP; } } else {
		 * logger.info("File is Downloaded successfully:Verified"); break; } }
		 */
		String downloadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\Downloads";
		File dir = new File(downloadPath);
		File[] dir_contents = dir.listFiles();

		if (dir_contents != null) {
			for (File dir_content : dir_contents) {
				if (dir_content.getName().contains(fileName))
					return true;
				logger.info("File is downloaded");
				break;
			}
		}

		return false;
	}

	public void narefreshApp() {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		try {
			WebElement NGLLOgo = Driver.findElement(By.id("imgNGLLogo"));
			act.moveToElement(NGLLOgo).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(NGLLOgo));
			js.executeScript("arguments[0].click();", NGLLOgo);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
		} catch (Exception refresh) {
			WebElement NGLLOgo = Driver.findElement(By.id("aNGLLogo"));
			act.moveToElement(NGLLOgo).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(NGLLOgo));
			act.moveToElement(NGLLOgo).click().perform();
			js.executeScript("arguments[0].click();", NGLLOgo);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
		}

	}

	public String getTimeAsTZone(String timeZone) {

		System.out.println("ZoneID of is==" + timeZone);
		logger.info("ZoneID of is==" + timeZone);
		if (timeZone.equalsIgnoreCase("EDT")) {
			timeZone = "America/New_York";
		} else if (timeZone.equalsIgnoreCase("CDT")) {
			timeZone = "CST";
		} else if (timeZone.equalsIgnoreCase("PDT")) {
			timeZone = "PST";
		} else if (timeZone.equalsIgnoreCase("MDT")) {
			timeZone = "America/Denver";
		}

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		logger.info(dateFormat.format(date));
		String Time = dateFormat.format(date);
		System.out.println("Time==" + Time);
		return Time;

	}

	public String getDateAsTZone(String timeZone) {

		System.out.println("ZoneID is==" + timeZone);
		logger.info("ZoneID is==" + timeZone);
		if (timeZone.equalsIgnoreCase("EDT")) {
			timeZone = "America/New_York";
		} else if (timeZone.equalsIgnoreCase("CDT")) {
			timeZone = "CST";
		} else if (timeZone.equalsIgnoreCase("PDT")) {
			timeZone = "PST";
		} else if (timeZone.equalsIgnoreCase("MDT")) {
			timeZone = "America/Denver";
		}

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		logger.info(dateFormat.format(date));
		String Date = dateFormat.format(date);
		System.out.println("Date==" + Date);
		return Date;

	}

	public void ActivateAccount()
			throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		Actions act = new Actions(Driver);
		// JavascriptExecutor js = (JavascriptExecutor) driver;
		Thread.sleep(5000);
		// --Go to Customer
		WebElement Customers = isElementPresent("Customers_id");
		wait.until(ExpectedConditions.visibilityOf(Customers));
		wait.until(ExpectedConditions.elementToBeClickable(Customers));
		act.moveToElement(Customers).click().build().perform();
		logger.info("Click on Customers menu");

		// --Go to Customer
		WebElement Customer = isElementPresent("Customer_id");
		wait.until(ExpectedConditions.visibilityOf(Customer));
		wait.until(ExpectedConditions.elementToBeClickable(Customer));
		act.moveToElement(Customer).click().build().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		logger.info("Click on Customer");

		// --Get Account No
		String Env = storage.getProperty("Env");
		String FedExAC = null;
		if (Env.equalsIgnoreCase("Pre-Prod")) {
			FedExAC = getData("OrderCreation", 5, 2);
		} else if (Env.equalsIgnoreCase("STG")) {
			FedExAC = getData("OrderCreation", 5, 2);
		} else if (Env.equalsIgnoreCase("Test")) {
			FedExAC = getData("OrderCreation", 5, 2);
		}

		// --Enter Account No
		WebElement CustomerInput = isElementPresent("CustACID_id");
		wait.until(ExpectedConditions.visibilityOf(CustomerInput));
		wait.until(ExpectedConditions.elementToBeClickable(CustomerInput));
		CustomerInput.clear();
		CustomerInput.sendKeys(FedExAC);
		logger.info("Enter Account Number");

		// --Click on Search button
		WebElement SearchButton = isElementPresent("CustSearchBTN_id");
		wait.until(ExpectedConditions.visibilityOf(SearchButton));
		wait.until(ExpectedConditions.elementToBeClickable(SearchButton));
		act.moveToElement(SearchButton).click().build().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		logger.info("Click on Search button");

		// --Waiting for Customer Editor
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("customerscreens")));

		// --Activate the status of Customer
		Select CustSTatus = new Select(isElementPresent("CustEdStatusDrop_id"));
		CustSTatus.selectByVisibleText("Active Account");
		Thread.sleep(2000);
		logger.info("Activate the Account");

		// --Click on Save button
		WebElement SaveBTN = isElementPresent("CustEdSaveBtn_xpath");
		wait.until(ExpectedConditions.visibilityOf(SaveBTN));
		wait.until(ExpectedConditions.elementToBeClickable(SaveBTN));
		act.moveToElement(SaveBTN).click().build().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		logger.info("Click on Save button");

		// --Wait for Success Msg
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RecordSave")));
		String SucMsg = isElementPresent("CustEdSuccMsg_id").getText();
		logger.info("SuccessMessage=" + SucMsg);
		logger.info("Account Activated successfully");

		// --Refresh App
		OrderCreation OC = new OrderCreation();
		OC.refreshApp();

	}

	public void Ashotscreenshot(String screenshotName) throws IOException {
		Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
				.takeScreenshot(Driver);
		String destination = System.getProperty("user.dir") + "/Report/NA_Screenshot/" + screenshotName + ".png";
		ImageIO.write(fpScreenshot.getImage(), "PNG", new File(destination));
	}

	public static void setResultData(String sheetName, int row, int col, String value)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String Env = storage.getProperty("Env");
		String FilePath = null;
		if (Env.equalsIgnoreCase("Pre-Prod")) {
			FilePath = storage.getProperty("PrePRODResultFile");
		} else if (Env.equalsIgnoreCase("STG")) {
			FilePath = storage.getProperty("STGResultFile");
		} else if (Env.equalsIgnoreCase("Prod")) {
			FilePath = storage.getProperty("PRODResultFile");
		} else if (Env.equalsIgnoreCase("TEST")) {
			FilePath = storage.getProperty("TESTResultFile");
		}

		File src = new File(FilePath);
		FileInputStream fis = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(fis);
		FileOutputStream fos1 = new FileOutputStream(src);
		Sheet sh = workbook.getSheet(sheetName);

		try {
			sh.getRow(row).createCell(col).setCellValue(value);
			workbook.write(fos1);
			fos1.close();
			fis.close();

		} catch (Exception e) {
			fos1.close();
			fis.close();
			logger.info("Issue in SetData" + e);

		}

	}

	public void resetResultofExcel() throws EncryptedDocumentException, InvalidFormatException, IOException {
		String Env = storage.getProperty("Env");
		String FilePath = null;

		if (Env.equalsIgnoreCase("Pre-Prod")) {
			FilePath = storage.getProperty("PrePRODResultFile");
		} else if (Env.equalsIgnoreCase("STG")) {
			FilePath = storage.getProperty("STGResultFile");
		} else if (Env.equalsIgnoreCase("Prod")) {
			FilePath = storage.getProperty("PRODResultFile");
		} else if (Env.equalsIgnoreCase("TEST")) {
			FilePath = storage.getProperty("TESTResultFile");
		}

		File src = new File(FilePath);
		FileInputStream fis = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(fis);
		FileOutputStream fos1 = new FileOutputStream(src);
		Sheet sh = workbook.getSheet("Result");

		// --Get total Row
		int totalrow = sh.getLastRowNum() + 1;
		System.out.println("Total row==" + totalrow);
		// -Get total Col
		Row RowNo = sh.getRow(0);
		int totalcol = RowNo.getLastCellNum();
		System.out.println("Total Columns==" + totalcol);

		int ResultColIndex = 0;
		int FailLogColIndex = 0;
		int NAResultColIndex = 0;
		int NAFailLogColIndex = 0;

		// --Get column index by its name

		for (int tcol = 0; tcol < totalcol; tcol++) {
			String Colname = sh.getRow(0).getCell(tcol).getStringCellValue();

			System.out.println("Colname==" + Colname);

			if (Colname.equalsIgnoreCase("Connect OP Result")) {
				ResultColIndex = sh.getRow(0).getCell(tcol).getColumnIndex();
				System.out.println("Index of the column==" + ResultColIndex);

			} else if (Colname.equalsIgnoreCase("Fail Log")) {
				FailLogColIndex = sh.getRow(0).getCell(tcol).getColumnIndex();
				System.out.println("Index of the column==" + FailLogColIndex);

			} else if (Colname.equalsIgnoreCase("NA Process Result")) {
				NAResultColIndex = sh.getRow(0).getCell(tcol).getColumnIndex();
				System.out.println("Index of the column==" + NAResultColIndex);

			} else if (Colname.equalsIgnoreCase("NA Fail Log")) {
				NAFailLogColIndex = sh.getRow(0).getCell(tcol).getColumnIndex();
				System.out.println("Index of the column==" + NAFailLogColIndex);
				break;
			}

		}

		// --Set blank value in Result and Fail Log column
		for (int row = 1; row < totalrow; row++) {
			fis = new FileInputStream(src);
			fos1 = new FileOutputStream(src);

			try {
				sh.getRow(row).createCell(ResultColIndex).setCellValue("");
				sh.getRow(row).createCell(FailLogColIndex).setCellValue("");
				sh.getRow(row).createCell(NAResultColIndex).setCellValue("");
				sh.getRow(row).createCell(NAFailLogColIndex).setCellValue("");
				workbook.write(fos1);
				fos1.close();
				fis.close();
			} catch (Exception e) {
				fos1.close();
				fis.close();
				logger.info("Issue in SetData" + e);

			}
		}

	}
	
	public String getExtraTimeAsTZone(String timeZone) {

		System.out.println("ZoneID of is==" + timeZone);
		logger.info("ZoneID of is==" + timeZone);
		if (timeZone.equalsIgnoreCase("EDT")) {
			timeZone = "America/New_York";
		} else if (timeZone.equalsIgnoreCase("CDT")) {
			timeZone = "CST";
		} else if (timeZone.equalsIgnoreCase("PDT")) {
			timeZone = "PST";
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
		cal.add(Calendar.MINUTE, 1);
		logger.info(dateFormat.format(cal.getTime()));
		String Time = dateFormat.format(cal.getTime());
		System.out.println("New Time==" + Time);
		return Time;

	}
}
