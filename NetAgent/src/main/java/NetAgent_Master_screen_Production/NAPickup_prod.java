package NetAgent_Master_screen_Production;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class NAPickup_prod extends OrderCreation_prod {

	@Test
	public void naconfirmPickup() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		WebDriverWait wait2 = new WebDriverWait(Driver, 7);// wait time
		Actions act = new Actions(Driver);
		JavascriptExecutor js = (JavascriptExecutor) Driver;

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Get the ServiceID
		String svc = isElementPresent("NOEServiceID_xpath").getText();
		System.out.println(svc);
		logger.info("ServiceID=" + svc);

		try {
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Pickup')]")));

			OrderCreation_prod OC = new OrderCreation_prod();
			OC.getNAStageName();

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 6, 5, "PASS");

			}

			if (svc.equals("LOC") || svc.equals("P3P") || svc.equals("DRV") || svc.equals("SDC")
					|| svc.equals("FRG") | svc.equals("D3P") || svc.equals("SD")) {

				// --Get the timeZone
				String tzone = isElementPresent("NPUTimeZone_id").getText();
				String rectime = getTimeAsTZone(tzone);

				// --Enter Actual Pickup Time
				isElementPresent("NTLPuTime_id").clear();
				isElementPresent("NTLPuTime_id").sendKeys(rectime);
				isElementPresent("NTLPuTime_id").sendKeys(Keys.TAB);
				isElementPresent("NTLPuTime_id").sendKeys(Keys.TAB);
				logger.info("Enter Actual pickup time");

				// --Click on Confirm PU button
				WebElement ConfPU = isElementPresent("NTPickupBtn_id");
				wait.until(ExpectedConditions.elementToBeClickable(ConfPU));
				Thread.sleep(2000);
				js.executeScript("arguments[0].click();", ConfPU);
				logger.info("Click on Confirm PU button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));


			}

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 7, 5, "PASS");

			}

		} catch (Exception e) {
			logger.error(e);
			logger.info("Line number is: " + e.getStackTrace()[0].getLineNumber());
			getScreenshot(Driver, "NAPICKUP" + svc);
			System.out.println("PICKUP Not Exist in Flow!!");
			logger.info("PICKUP Not Exist in Flow!!");

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 6, 5, "FAIL");

			}

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 7, 5, "FAIL");

			}

		}

	}

}
