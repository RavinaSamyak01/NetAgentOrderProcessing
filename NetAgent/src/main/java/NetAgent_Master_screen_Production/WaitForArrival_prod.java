package NetAgent_Master_screen_Production;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class WaitForArrival_prod extends OrderCreation_prod {

	@Test
	public void waitForArr() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		WebDriverWait wait2 = new WebDriverWait(Driver, 10);// wait time
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = Driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);

		try {

//			wait2.until(ExpectedConditions.visibilityOfElementLocated(
//					By.xpath("//*[@id=\"lblStages\"][contains(text(),'Wait for Arrival')]")));

			// --Get StageName
			OrderCreation_prod OC = new OrderCreation_prod();
			OC.getStageName();

			String stg = Driver.findElement(By.id("lblStages")).getText();

			if (stg.contains("WAIT FOR ARRIVAL")) {

				// --Actual Recover Time

				WebElement ZoneID = isElementPresent("TLWFATimeZone_id");
				String ZOneID = ZoneID.getText();
				System.out.println("ZoneID of is==" + ZOneID);

				// --get Time
				String ZnTime = getTimeAsTZone(ZOneID);

				WebElement Time = isElementPresent("TLWFATime_id");
				Time.clear();
				Time.sendKeys(ZnTime);
				Time.sendKeys(Keys.TAB);
				logger.info("Enter Arrival Time");
				wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				// --Click on Arrival button
				WebElement Arrival = isElementPresent("TLWFArrival_id");
				wait2.until(ExpectedConditions.elementToBeClickable(Arrival));
				jse.executeScript("arguments[0].click();", Arrival);
				logger.info("Click on Arrival button");
				wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			}
		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "WaitforArrival" + svc);
			System.out.println("Wait for Arrival Not Exist in Flow!!");
			logger.info("Wait for Arrival Not Exist in Flow!!");
		}

	}

}
