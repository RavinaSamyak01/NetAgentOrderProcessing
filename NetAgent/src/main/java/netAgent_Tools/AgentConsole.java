package netAgent_Tools;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class AgentConsole extends BaseInit {
	@Test
	public void agentConsole() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);

		logger.info("=======Agent Console Test Start=======");
		msg.append("=======Tools Tab Test Start=======" + "\n\n");

		try {
			// Go to Tools - Agent Console screen
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idTools")));
			Driver.findElement(By.id("idTools")).click();
			logger.info("Clicked on Tools");

			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Agent Console")));
			Driver.findElement(By.linkText("Agent Console")).click();
			logger.info("Clicked on Agent Console");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			getScreenshot(Driver, "agentconsole_Screen");

			String AirlineID = getData("AgentConsole", 1, 0);
			String FlightNo = getData("AgentConsole", 1, 1);
			String ArrAP = getData("AgentConsole", 1, 2);

			JavascriptExecutor js = (JavascriptExecutor) Driver;
			js.executeScript("window.scrollBy(0,-250)");
			Thread.sleep(2000);

			// --Click on Get FlightPath without data
			// Click on Check Status
			Driver.findElement(By.id("btCheckStatus")).click();
			logger.info("Click on Check Flight Path");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			WebElement AIRVal = Driver.findElement(By.id("idValidation"));
			if (AIRVal.isDisplayed()) {
				logger.info("Validation Displayed==" + AIRVal.getText());

			} else {
				logger.info("Validation is not displayed for Airline");

			}

			// --Enter invalid AirlineID
			Driver.findElement(By.id("txtAirlineId")).clear();
			logger.info("Clear AirlineID");
			Driver.findElement(By.id("txtAirlineId")).sendKeys("sdfgh");
			logger.info("Enter AirlineID");
			Thread.sleep(2000);
			WebElement alid = Driver.findElement(By.id("txtAirlineId"));
			alid.sendKeys(Keys.ENTER);
			WebElement AirlineVal = Driver.findElement(By.id("iconRemoveAirline"));
			if (AirlineVal.isDisplayed()) {
				logger.info("Validation Displayed==" + AirlineVal.getText());
				// --Click on Remove
				WebElement RemoveAL = Driver.findElement(By.id("idremoveicon"));
				RemoveAL.click();
				logger.info("Clicked on Remove button");
				if (RemoveAL.isDisplayed()) {
					logger.info("No Results Found is not removed=FAIL");
				} else {
					logger.info("No Results Found is removed=PASS");
					Driver.findElement(By.id("txtAirlineId")).clear();
					logger.info("Clear AirlineID");
				}

			} else {
				logger.info("Validation is not displayed for invalid Airline");

			}

			// --Enter invalid AirportID
			// Select Arriving Airport
			Driver.findElement(By.id("txtAirportID")).clear();
			logger.info("Clear AirportID");
			Driver.findElement(By.id("txtAirportID")).sendKeys("edfrgt	");
			logger.info("Enter AirportID");
			Thread.sleep(2000);
			WebElement apid = Driver.findElement(By.id("txtAirportID"));
			apid.sendKeys(Keys.ENTER);
			WebElement AirportVal = Driver.findElement(By.id("iconRemoveAirport"));
			if (AirportVal.isDisplayed()) {
				logger.info("Validation Displayed==" + AirportVal.getText());
				Driver.findElement(By.id("txtAirportID")).clear();
				logger.info("Clear AirportID");

			} else {
				logger.info("Validation is not displayed for invalid AirportID");

			}

			// Select AirlineId
			Driver.findElement(By.id("txtAirlineId")).clear();
			logger.info("Clear AirlineID");
			Driver.findElement(By.id("txtAirlineId")).sendKeys(AirlineID);
			logger.info("Enter AirlineID");
			Thread.sleep(2000);
			alid = Driver.findElement(By.id("txtAirlineId"));
			alid.sendKeys(Keys.DOWN);
			alid.sendKeys(Keys.ENTER);
			Thread.sleep(2000);

			// enter FLight No
			wait.until(ExpectedConditions.elementToBeClickable(By.id("txtFlightNo")));
			Driver.findElement(By.id("txtFlightNo")).clear();
			logger.info("Clear FlightNo");
			Driver.findElement(By.id("txtFlightNo")).sendKeys(FlightNo);
			logger.info("Enter FlightNo");

			// Select Arriving Airport
			Driver.findElement(By.id("txtAirportID")).clear();
			logger.info("Clear AirportID");
			Driver.findElement(By.id("txtAirportID")).sendKeys(ArrAP);
			logger.info("Enter AirportID");
			Thread.sleep(2000);
			apid = Driver.findElement(By.id("txtAirportID"));
			apid.sendKeys(Keys.DOWN);
			apid.sendKeys(Keys.DOWN);
			apid.sendKeys(Keys.ENTER);
			Thread.sleep(2000);

			// Click on Check Status
			wait.until(ExpectedConditions.elementToBeClickable(By.id("btCheckStatus")));
			Driver.findElement(By.id("btCheckStatus")).click();
			logger.info("Click on Check Flight Path");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblValidateFlight")));
				String FlighVal = Driver.findElement(By.id("lblValidateFlight")).getText();
				logger.info("Fight validation==" + FlighVal);
			} catch (Exception NoFlight) {
				logger.error(NoFlight);
				String winHandleBefore = Driver.getWindowHandle();
				System.out.println("Main window title==" + Driver.switchTo().window(winHandleBefore).getTitle());

				for (String winHandle : Driver.getWindowHandles()) {
					Driver.switchTo().window(winHandle);
					System.out.println("Child window title==" + Driver.switchTo().window(winHandle).getTitle());
				//	getScreenshot(Driver, "AgentConsole_FlightPath");
					logger.info("Switched to Flight Path Map");
				}
				// Close new window
				Driver.close();
				logger.info("Close Flight Path Map window");

				Thread.sleep(5000);

				// Switch back to original browser (first window)
				Driver.switchTo().window(winHandleBefore);
				logger.info("Switched to Main window");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			}

			// Weather info
			// --Reset button
			Driver.findElement(By.id("txtZipCode")).clear();
			logger.info("Clear ZipCode");
			Driver.findElement(By.id("txtZipCode")).sendKeys("60406");
			logger.info("Enter ZipCode");
			Driver.findElement(By.id("btnReset")).click();
			logger.info("Click on Reset button");

			// --Click on Submit without ZipCode
			Driver.findElement(By.id("txtZipCode")).clear();
			logger.info("Clear ZipCode");
			Driver.findElement(By.id("btnSubmit")).click();
			logger.info("Click on Submit button");

			WebElement Vallidation = Driver.findElement(By.id("lblValidate"));
			if (Vallidation.isDisplayed()) {
				logger.info("Validation Displayed==" + Vallidation.getText());

			} else {
				logger.info("Validation is not displayed for ZipCode");

			}

			// --Click on Submit with valid ZipCode
			Driver.findElement(By.id("txtZipCode")).clear();
			logger.info("Clear ZipCode");
			Driver.findElement(By.id("txtZipCode")).sendKeys("60406");
			logger.info("Enter ZipCode");

			Driver.findElement(By.id("btnSubmit")).click();
			logger.info("Click on Submit button");
			Thread.sleep(2000);
			WebElement ZipVallidation = Driver.findElement(By.id("lblValidateZip"));
			if (ZipVallidation.isDisplayed()) {
				logger.info("Validation Displayed==" + ZipVallidation.getText());
				logger.info("Unable to get Weather==FAIL");
				getScreenshot(Driver, "agentconsole_WeatherInfo");
				Driver.findElement(By.id("closeiconid")).click();
				logger.info("Clicked on close button of message");

			} else {
				logger.info("Get the Weather");
				getScreenshot(Driver, "agentconsole_WeatherInfo");

			}

			// --Weather Up/Down icon
			wait.until(ExpectedConditions.elementToBeClickable(By.id("imgWeather")));
			Driver.findElement(By.id("imgWeather")).click();
			logger.info("Click on Weather Up/Down icon");

			// --Renderer issue

			/*
			 * // CLick on link "Click Here"
			 * Driver.findElement(By.linkText("Click here")).click();
			 * 
			 * String winHandleBefore1 = Driver.getWindowHandle(); for (String winHandle :
			 * Driver.getWindowHandles()) { Driver.switchTo().window(winHandle);
			 * getScreenshot(Driver, "AgentConsole_ClickHere");
			 * logger.info("Switched to Click Here page"); } // Close new window
			 * Driver.close(); logger.info("Close Click Here page");
			 * 
			 * Thread.sleep(5000);
			 * 
			 * // Switch back to original browser (first window)
			 * Driver.switchTo().window(winHandleBefore1);
			 * logger.info("Switched to Main window");
			 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
			 * "//*[@class=\"ajax-loadernew\"]")));
			 */
			getScreenshot(Driver, "agentconsole_Screen1");
			logger.info("Agent Console Test=PASS");
			msg.append("Agent Console Test=PASS" + "\n\n");
			setResultData("Result", 33, 5, "PASS");

		} catch (Exception AgentConsoleE) {
			logger.error(AgentConsoleE);
			getScreenshot(Driver, "AgentConsoleE_error");
			logger.info("Agent Console Test=FAIL");
			msg.append("Agent Console Test=FAIL" + "\n\n");
			String Error = AgentConsoleE.getMessage();

			setResultData("Result", 33, 5, "FAIL");
			setResultData("Result", 33, 6, Error);

		}

		logger.info("=======Agent Console Test End=======");
		// msg.append("=======Agent Console Test End=======" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}

}
