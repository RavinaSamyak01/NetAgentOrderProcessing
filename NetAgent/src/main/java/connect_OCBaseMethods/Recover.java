package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Recover extends OrderCreation {

	@Test
	public void recoverAtDestination() throws Exception {
		// JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		WebDriverWait wait2 = new WebDriverWait(Driver, 10);// wait time;
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = Driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);
	
		try {
			wait2.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"lblStages\"][contains(text(),'Recover @ Destination')]")));

			// --Get StageName
			OrderCreation OC = new OrderCreation();
			OC.getStageName();
			
			OC.movjobstatus();


			// --Actual Recover Time

			WebElement ZoneID = isElementPresent("TLRADTimeZone_id");
			String ZOneID = ZoneID.getText();
			System.out.println("ZoneID of is==" + ZOneID);

			// --get Time
			String ZnTime = getTimeAsTZone(ZOneID);

			WebElement Time = isElementPresent("TLRADRecTime_id");
			Time.clear();
			Time.sendKeys(ZnTime);
			Time.sendKeys(Keys.TAB);
			logger.info("Enter Arrival Time");

			// --Recover Date
			isElementPresent("TLRADRecDate_id").sendKeys(Keys.TAB);

			// --Click on COnfirm Recover
			WebElement ConfRecover = isElementPresent("TLRADConfRecover_id");
			wait.until(ExpectedConditions.elementToBeClickable(ConfRecover));
			ConfRecover.click();
			logger.info("Click on Confirm Recover button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "RECOVER@DEST" + svc);
			System.out.println("RECOVER @ DESTINATION Not Exist in Flow!!");
			logger.info("RECOVER @ DESTINATION Not Exist in Flow!!");

		}

	}

}
