package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class XerWaitForArrival extends OrderCreation {

	@Test
	public void xerWaitForArr() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = Driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);

		try {

			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"lblStages\"][contains(text(),'XER Wait For Arr@')]")));

			// --Get StageName
			OrderCreation OC = new OrderCreation();
			OC.getStageName();

			String stg = Driver.findElement(By.id("lblStages")).getText();
			
			OC.movjobstatus();

			if (stg.contains("XER WAIT FOR ARR")) {
				// String tzone =
				// Driver.findElement(By.id("lblEditArrivalTimeSZone")).getText();
				// String xerarrtime = getTime(tzone);

				WebElement ZoneID = isElementPresent("TLXWFATZoneID_id");
				String ZOneID = ZoneID.getText();
				System.out.println("ZoneID of is==" + ZOneID);

				// --get Time
				String ZnTime = getTimeAsTZone(ZOneID);

				WebElement Time = isElementPresent("TLXWFATime_id");
				Time.clear();
				Time.sendKeys(ZnTime);
				Time.sendKeys(Keys.TAB);
				logger.info("Enter Arrival Time");

				// --Click on Button
				WebElement Depart = isElementPresent("TLXWFAButton_id");
				wait.until(ExpectedConditions.elementToBeClickable(Depart));
				jse.executeScript("arguments[0].click();", Depart);
				logger.info("Click on Arrival button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			}
		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "XERWaitforArr" + svc);
			System.out.println("XER Wait for Arrival Not Exist in Flow!!");
			logger.info("XER Wait for Arrival Not Exist in Flow!!");

		}

	}

}
