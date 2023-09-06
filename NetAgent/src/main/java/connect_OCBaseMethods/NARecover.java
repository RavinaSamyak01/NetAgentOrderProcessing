package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class NARecover extends OrderCreation {

	@Test
	public void narecoverAtDestination() throws Exception {
		// JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 50);// wait time
		WebDriverWait wait2 = new WebDriverWait(Driver, 7);// wait time

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		// --Get the ServiceID
		WebElement service = isElementPresent("NOEServiceID_xpath");
		wait.until(ExpectedConditions.visibilityOf(service));
		String svc = service.getText();
		System.out.println(svc);
		logger.info("ServiceID=" + svc);
		msg.append("ServiceID=" + svc + "\n");
		try {
			wait2.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Recover @ Destination')]")));

			// --Get StageName
			OrderCreation OC = new OrderCreation();
			OC.getNAStageName();

			// --Actual Recover Time

			WebElement ZoneID = isElementPresent("NPUTimeZone_id");
			String ZOneID = ZoneID.getText();
			System.out.println("ZoneID of is==" + ZOneID);

			// --get Time
			String ZnTime = getTimeAsTZone(ZOneID);

			WebElement Time = isElementPresent("NTLPuTime_id");
			Time.clear();
			Time.sendKeys(ZnTime);
			Time.sendKeys(Keys.TAB);
			logger.info("Enter Recovery Time");

			// --Recover Date

			// --Click on COnfirm Recover
			try {
				WebElement ConfRecover = isElementPresent("NSPLRecover_xpath");
				wait.until(ExpectedConditions.elementToBeClickable(ConfRecover));
				ConfRecover.click();
				logger.info("Click on Confirm Recover button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			} catch (Exception e) {
				WebElement ConfRecover = isElementPresent("NTPickupBtn_id");
				wait.until(ExpectedConditions.elementToBeClickable(ConfRecover));
				ConfRecover.click();
				logger.info("Click on Confirm Recover button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			}

		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "RECOVER@DEST" + svc);
			System.out.println("RECOVER @ DESTINATION Not Exist in Flow!!");
			logger.info("RECOVER @ DESTINATION Not Exist in Flow!!");

		}

	}

}
