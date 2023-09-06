package NetAgent_Master_screen_Production;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import netAgent_BasePackage.BaseInit;

public class NAConfirmPUAlert_prod extends BaseInit {

	public void naconfirmPUAlert() throws Exception {

		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 50);// wait time
		WebDriverWait wait2 = new WebDriverWait(Driver, 20);// wait time
		Actions act = new Actions(Driver);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
		/*
		 * try {
		 *  wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
		 * "//*[@class=\"ajax-loadernew\"]")));
		 * 
		 * } catch (Exception ee) {
		 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
		 * "//*[@class=\"ajax-loadernew\"]")));
		 * 
		 * } Thread.sleep(5000);
		 */
		
		
		// --Fetch flight details from Net Agent
		
		fetch_flight_details_Net_Agent(2);
		
		// --Get the ServiceID
		WebElement service = isElementPresent("NOEServiceID_xpath");
		wait.until(ExpectedConditions.visibilityOf(service));
		String svc = service.getText();
		System.out.println(svc);
		logger.info("ServiceID=" + svc);
		msg.append("ServiceID=" + svc + "\n");
		
		try {

			try {
				
				// --Set Pass in TestScenarios
				if (svc.equals("LOC")) {
					setData("TC_OrderProcess", 4, 5, "PASS");

				}
			} catch (Exception eStageName) {

				if (svc.equals("LOC")) {
					setData("TC_OrderProcess", 4, 5, "FAIL");

				}
//				wait.until(ExpectedConditions.visibilityOfElementLocated(
//						By.xpath("//*[@id=\"lblStages\"][contains(text(),'CONFIRM PULL ALERT')]")));
				
				wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			}

			OrderCreation_prod OC = new OrderCreation_prod();
			OC.getNAStageName();

			// --Click on Confirm PU Alert
			try {
				WebElement ConfPUAlert = isElementPresent("NPUCOnfBtn_id");
				act.moveToElement(ConfPUAlert).build().perform();
				jse.executeScript("arguments[0].click();", ConfPUAlert);
				logger.info("Clicked on Confirm PU Alert button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

			} catch (Exception e) {
				try {
					WebElement ConfPUAlert = isElementPresent("TLRDSPUALert_id");
					act.moveToElement(ConfPUAlert).build().perform();
					jse.executeScript("arguments[0].click();", ConfPUAlert);
					logger.info("Clicked on Confirm PU Alert button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

				} catch (Exception ee) {
					WebElement ConfPUAlert = isElementPresent("D3PConfull_id");
					act.moveToElement(ConfPUAlert).build().perform();
					jse.executeScript("arguments[0].click();", ConfPUAlert);
					logger.info("Clicked on Confirm PU Alert button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

				}

			}

			// --Set Pass in TestScenarios

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 5, 5, "PASS");

			}

		} catch (Exception e) {
			logger.error(e);
			logger.info("Line number is: " + e.getStackTrace()[0].getLineNumber());
			getScreenshot(Driver, "NAConfirmPUAlert" + svc);
			System.out.println("Confirm PU Alert Not Exist in Flow!!");
			logger.info("Confirm PU Alert Not Exist in Flow!!");

			// --Set Pass in TestScenarios

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 5, 5, "FAIL");

			}

		}

	}
	
	public void fetch_flight_details_Net_Agent(int i)
			throws IOException, InterruptedException, EncryptedDocumentException, InvalidFormatException {

		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		Actions act = new Actions(Driver);

		
		// --Airline Name
		WebElement Airlines= isElementPresent("na_airline_name_xpath");
		jse.executeScript("arguments[0].scrollIntoView(true);", Airlines); 
		Thread.sleep(1500);
		String Airline_name = Airlines.getText();
		logger.info("Airline_name is : " +Airline_name);
		
		setData("AgentConsole", 1, 3, Airline_name);
		
		// -- FLight No 
		
		WebElement flight_no = isElementPresent("na_flight_no_xpath");
		jse.executeScript("arguments[0].scrollIntoView(true);", flight_no); 
		Thread.sleep(1500);
		String Flight_no = Airlines.getText();
		logger.info("Flight No is : " +Flight_no);		
		setData("AgentConsole", 1, 1, Flight_no);
		setData("OrderCreation", 2, 40, Flight_no);
		 
		// --Airline ID
		
		WebElement AirlineID = isElementPresent("na_flight_no_xpath");
		jse.executeScript("arguments[0].scrollIntoView(true);", AirlineID); 
		Thread.sleep(1500);
		String Airline_ID = AirlineID.getText();
		logger.info("Airline ID is : " +Airline_ID);		
		setData("AgentConsole", 1, 0, Airline_ID);
		setData("OrderCreation", 2, 39, Airline_ID);
		
		// -- Arrival Airport
		
		WebElement Arrival_Airport = isElementPresent("na_arr_airport_xpath");
		jse.executeScript("arguments[0].scrollIntoView(true);", Arrival_Airport); 
		Thread.sleep(1500);
		String Arrival_Airport_id = Arrival_Airport.getText();
		logger.info("Airline ID is : " +Arrival_Airport_id);		
		setData("AgentConsole", 1, 2, Arrival_Airport_id);
		setData("OrderCreation", 2, 41, Arrival_Airport_id);
		
		WebElement confirm =  isElementPresent("NPUCOnfBtn_id");
		jse.executeScript("arguments[0].scrollIntoView(true);", confirm); 
		Thread.sleep(1500);

	}

}
