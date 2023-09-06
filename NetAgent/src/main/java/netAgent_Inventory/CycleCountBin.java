package netAgent_Inventory;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class CycleCountBin extends BaseInit {
	@Test
	public void cycleCountBIN() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);

		logger.info("=======Cycle Count BIN Test Start=======");
		//msg.append("=======Cycle Count BIN Start=======" + "\n\n");

		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idInventory")));
			Driver.findElement(By.id("idInventory")).click();
			logger.info("Clicked on Inventory");
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@aria-labelledby=\"idInventory\"]")));

			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Cycle Count BIN")));
			Driver.findElement(By.linkText("Cycle Count BIN")).click();
			logger.info("Clicked on Cycle Count BIN");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			getScreenshot(Driver, "CycleCountBIN");

			// Select Client from dropdown
			wait.until(ExpectedConditions.elementToBeClickable(By.id("ddlClient")));
			Driver.findElement(By.id("ddlClient")).click();
			Select client = new Select(Driver.findElement(By.id("ddlClient")));
			client.selectByVisibleText("AUTOMATION INVENTORY PROFILE");
			logger.info("Selected Client");

			// Select Bin name from dropdown list.
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn_cmbLocationBinclass=")));
			Driver.findElement(By.id("btn_cmbLocationBinclass=")).click();
			Thread.sleep(2000);
			Driver.findElement(By.id("idcheckboxInput")).click();
			Driver.findElement(By.id("btn_cmbLocationBinclass=")).click();
			logger.info("Select Bin from Bin dropdown");

			// Click on Start button
			Driver.findElement(By.id("btnstart")).click();
			logger.info("Clicked on Start button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			try {
				String SuccMsg = Driver.findElement(By.id("lblSuccessMsg")).getText();
				System.out.println(SuccMsg);
				logger.info("Success Message is displayed==" + SuccMsg);
				logger.info("Success Message is not displayed==PASS");

				getScreenshot(Driver, "CycleCountBIN_Start");
			} catch (Exception Success) {
				logger.error(Success);

				logger.info("Success Message is not displayed==FAIL");

			}

			Driver.findElement(By.id("btnreset")).click();
			logger.info("Clicked on Reset button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			logger.info("Cycle Count BIN Test=PASS");
			msg.append("Cycle Count BIN Test=PASS" + "\n\n");
			setResultData("Result", 19, 5, "PASS");

		} catch (Exception CycleCountBinE) {
			logger.error(CycleCountBinE);
			getScreenshot(Driver, "CycleCountBin_error");
			logger.info("Cycle Count BIN Test=FAIL");
			msg.append("Cycle Count BIN Test=FAIL" + "\n\n");
			String Error = CycleCountBinE.getMessage();

			setResultData("Result", 19, 5, "FAIL");
			setResultData("Result", 19, 6, Error);

		}

		logger.info("=======Cycle Count BIN Test End=======");
		//msg.append("=======Cycle Count BIN End=======" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}

}
