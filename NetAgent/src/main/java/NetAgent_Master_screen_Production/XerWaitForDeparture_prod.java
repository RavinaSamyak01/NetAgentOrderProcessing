package NetAgent_Master_screen_Production;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class XerWaitForDeparture_prod extends OrderCreation_prod {
	@Test
	public void xerWaitForDept() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = Driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);
//		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		try {

			// --Get StageName
			
			OrderCreation_prod OC = new OrderCreation_prod();
			String job_status=	OC.getStageName();

				String stg = Driver.findElement(By.id("lblStages")).getText();
				
		

		//	if (stg.contains("XER WAIT FOR DEPT")) {// String tzone =
				// Driver.findElement(By.id("lblEditArrivalTimeSZone")).getText();
				// String xerarrtime = getTime(tzone);
				
				if (stg.contains("XER WAIT FOR DEPT")) {

				WebElement ZoneID = isElementPresent("TLXWFDTimeZone_id");
				String ZOneID = ZoneID.getText();
				System.out.println("ZoneID of is==" + ZOneID);

				// --get Time
				String ZnTime = getTimeAsTZone(ZOneID);

				WebElement Time = isElementPresent("TLXWFDTime_id");
				Time.clear();
				Time.sendKeys(ZnTime);
				Time.sendKeys(Keys.TAB);
				logger.info("Enter Depature Time");

				// --Click on Button
				WebElement Depart = isElementPresent("TLXWFDButton_id");
				wait.until(ExpectedConditions.elementToBeClickable(Depart));
				jse.executeScript("arguments[0].click();", Depart);
				logger.info("Click on Depart button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			}
		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "XERWaitforDep" + svc);
			System.out.println("XER Wait for Departure Not Exist in Flow!!");
			logger.info("XER Wait for Departure Not Exist in Flow!!");

		}
	}

}
