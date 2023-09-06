package netAgent_Tools;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class MileageCalc extends BaseInit {
	@Test
	public void mileageCalc() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);

		logger.info("=======Mileage Calculation Test Start=======");
		//msg.append("======Mileage Calculation Test Start=======" + "\n\n");

		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Tools")));
			Driver.findElement(By.partialLinkText("Tools")).click();
			logger.info("Click on Tools");

			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("MileageCalc")));
			Driver.findElement(By.linkText("MileageCalc")).click();
			logger.info("Click on MileageCalc");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			System.out.println("Title of the screen is==" + Driver.getTitle());

			JavascriptExecutor js = (JavascriptExecutor) Driver;
			js.executeScript("window.scrollBy(0,-250)");
			Thread.sleep(2000);
			getScreenshot(Driver, "MileageCal_screen");

			// For Record not Found Testing.
			// PUId = "1234567";
			// JobId = "123456789";

			// Search with Pickup

			Driver.findElement(By.id("txtpickupid")).clear();
			logger.info("Cleared PickupID");
			Driver.findElement(By.id("txtpickupid")).sendKeys("1234567");
			logger.info("Entered PickupID");

			Driver.findElement(By.id("btngetdetails")).click();
			logger.info("Click on GetDetails button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			String NoData = Driver.findElement(By.id("errorid")).getText();
			System.out.println("Records::" + NoData);
			logger.info("Records::" + NoData);

			String CalMsg = Driver.findElement(By.xpath("//label/strong")).getText();
			logger.info("Message==" + CalMsg);

			// Reset
			Driver.findElement(By.id("btnReset")).click();
			logger.info("Click on Reset button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			// Search with JobId
			Driver.findElement(By.id("txtJobid")).clear();
			logger.info("Clear JobID");
			Driver.findElement(By.id("txtJobid")).sendKeys(JobId);
			logger.info("Entered JobID");

			Driver.findElement(By.id("btngetdetails")).click();
			logger.info("Click on GetDetails button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			CalMsg = Driver.findElement(By.xpath("//label/strong")).getText();
			logger.info("Message==" + CalMsg);

			Driver.findElement(By.id("btnReset")).click();
			logger.info("Click on Reset button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			NoData = Driver.findElement(By.id("errorid")).getText();
			System.out.println("Records::" + NoData);
			logger.info("Records::" + NoData);

			Driver.findElement(By.id("txtpickupid")).clear();
			logger.info("Clear PickUpID");
			Driver.findElement(By.id("txtpickupid")).sendKeys(PUId);
			logger.info("Enter PickUpID");

			Driver.findElement(By.id("btngetdetails")).click();
			logger.info("Click on GetDetails button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			String act = Driver.findElement(By.xpath("//label/strong/span")).getText();
			System.out.println(act);
			logger.info("Message==" + act);

			String Exp = getData("Sheet1", 2, 21);

			if (act.contains(Exp)) {
				System.out.println("Miles Comparison is PASS");
				logger.info("Miles Comparison is PASS");
				getScreenshot(Driver, "MileageCal_Cal");

			}

			else {
				System.out.println("Miles Comparison is FAIL");
				logger.info("Miles Comparison is FAIL");
				getScreenshot(Driver, "MileageCal_Cal");

			}

			// Click on Calculate
			// Driver.findElement(By.id("btCalculate")).click();
			// Thread.sleep(10000);
			System.out.println("Mileage Calculation : " + CalMsg);
			logger.info("Mileage Calculation : " + CalMsg);
			// Click on Get Direction
			Driver.findElement(By.id("btnDirection")).click();
			logger.info("Click on Direction");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			boolean mapcheck = Driver.findElement(By.xpath(".//*[@id='mapLoad']")).isDisplayed();

			if (mapcheck == false) {
				throw new Error("Error: Map Not Display on Get Direction");
			}else {
				getScreenshot(Driver, "MileageCal_Map");

			}

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			getScreenshot(Driver, "MileageCalc");

			// Expand and Collapse Note
			Driver.findElement(By.id("imgData")).click();
			logger.info("Expand Note");
			Thread.sleep(2000);

			Driver.findElement(By.id("imgData")).click();
			logger.info("Collapse Note");

			// Reset
			Driver.findElement(By.id("btnReset")).click();
			logger.info("Click on Reset button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			logger.info("Mileage Calculation Test=PASS");
			msg.append("Mileage Calculation Test=PASS" + "\n\n");
			setResultData("Result", 34, 5, "PASS");

		} catch (Exception MileageCalculationE) {
			logger.error(MileageCalculationE);
			getScreenshot(Driver, "MileageCalculation_error");
			logger.info("Mileage Calculation Test=FAIL");
			msg.append("Mileage Calculation Test=FAIL" + "\n\n");
			String Error = MileageCalculationE.getMessage();

			setResultData("Result", 34, 5, "FAIL");
			setResultData("Result", 34, 6, Error);

		}

		logger.info("=======Mileage Calculation Test End=======");
		//msg.append("======Mileage Calculation Test End=======" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}

}
