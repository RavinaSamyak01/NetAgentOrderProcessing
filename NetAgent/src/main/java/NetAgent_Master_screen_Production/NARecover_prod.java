package NetAgent_Master_screen_Production;

import java.awt.Desktop.Action;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class NARecover_prod extends OrderCreation_prod {

	@Test
	public void recoverAtDestination() throws Exception {
		// JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		WebDriverWait wait2 = new WebDriverWait(Driver, 10);// wait time

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = Driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);
	
		try {
//			wait2.until(ExpectedConditions.visibilityOfElementLocated(
//					By.xpath("//*[@id=\"lblStages\"][contains(text(),'Recover @ Destination')]")));

			// --Get StageName
			OrderCreation_prod OC = new OrderCreation_prod();
			OC.getStageName();

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
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Recover Date
			isElementPresent("TLRADRecDate_id").sendKeys(Keys.TAB);
			logger.info("Enter Arrival Date");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Click on COnfirm Recover
			Thread.sleep(1500);
			WebElement ConfRecover = isElementPresent("TLRADConfRecover_xpath");
			Actions act = new Actions(Driver);
			act.moveToElement(ConfRecover).build().perform();
			Thread.sleep(500);
			wait2.until(ExpectedConditions.elementToBeClickable(ConfRecover));
			ConfRecover.click();
			logger.info("Click on Confirm Recover button");
			
			wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			Thread.sleep(2000);
		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "RECOVER@DEST" + svc);
			System.out.println("RECOVER @ DESTINATION Not Exist in Flow!!");
			logger.info("RECOVER @ DESTINATION Not Exist in Flow!!");

		}

	}

}
