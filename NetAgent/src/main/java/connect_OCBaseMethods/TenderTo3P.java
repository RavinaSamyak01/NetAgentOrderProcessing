package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class TenderTo3P extends BaseInit {

	@Test
	public void tndrTo3P() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);// wait time
		WebDriverWait wait2 = new WebDriverWait(Driver, 7);// wait time
		// Actions act = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) Driver;// scroll,click

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = Driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);

		try {
			try {
				wait2.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[@id=\"lblStages\"][contains(text(),'Tender to 3P')]")));

			} catch (Exception e) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[@id=\"lblStages\"][contains(text(),'3rd Party Delivery')]")));

			}

			// --Get StageName
			OrderCreation OC = new OrderCreation();
			String stage = OC.getStageName();
			
			OC.movjobstatus();


			// --Get the timeZone
			String tzone = isElementPresent("TT3TimeZone_id").getText();
			String rectime = getTimeAsTZone(tzone);

			// --Enter Drop Time
			WebElement DropTime = isElementPresent("TT3droptime_id");
			wait.until(ExpectedConditions.elementToBeClickable(DropTime));
			DropTime.clear();
			DropTime.sendKeys(rectime);
			DropTime.sendKeys(Keys.TAB);
			logger.info("Entered Drop time");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			if (svc.equalsIgnoreCase("3PLAST") && stage.equalsIgnoreCase("3rd Party Delivery")) {

				try {
					WebElement POD = isElementPresent("TT3POD_id");

					if (POD.getAttribute("value").isBlank()) {
						String TrackID = OC.shipLabel();
						// --Enter POD
						POD.clear();
						POD.sendKeys(TrackID);
						POD.sendKeys(Keys.TAB);
						logger.info("Entered POD");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					} else {
						logger.info("POD is already set");

					}

				} catch (Exception e) {

					WebElement POD = isElementPresent("TT3POD2_id");
					if (POD.getAttribute("value").isBlank()) {
						String TrackID = OC.shipLabel();
						// --Enter POD
						POD.clear();
						POD.sendKeys(TrackID);
						POD.sendKeys(Keys.TAB);
						logger.info("Entered POD");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					} else {
						logger.info("POD is already set");

					}

				}

			} else if (svc.equalsIgnoreCase("P3P") && stage.equalsIgnoreCase("Tender to 3P")
					|| (svc.equalsIgnoreCase("3PLAST") && stage.equalsIgnoreCase("Tender to 3P"))) {

				try {
					WebElement POD = isElementPresent("TT3POD_id");

					if (POD.getAttribute("value").isBlank()) {
						String TrackID = OC.shipLabel();
						// --Enter POD
						POD.clear();
						POD.sendKeys(TrackID);
						POD.sendKeys(Keys.TAB);
						logger.info("Entered POD");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					} else {
						logger.info("POD is already set");

					}

				} catch (Exception e) {

					WebElement POD = isElementPresent("TT3POD2_id");
					if (POD.getAttribute("value").isBlank()) {
						String TrackID = OC.shipLabel();
						// --Enter POD
						POD.clear();
						POD.sendKeys(TrackID);
						POD.sendKeys(Keys.TAB);
						logger.info("Entered POD");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					} else {
						logger.info("POD is already set");

					}

				}

			}

			// --Get the timeZone
			tzone = isElementPresent("TT3TimeZone_id").getText();
			rectime = getTimeAsTZone(tzone);

			// --Enter Drop Time
			DropTime = isElementPresent("TT3droptime_id");
			wait.until(ExpectedConditions.elementToBeClickable(DropTime));
			DropTime.clear();
			DropTime.sendKeys(rectime);
			DropTime.sendKeys(Keys.TAB);
			logger.info("Entered Drop time");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Click on Tender To 3P
			WebElement Ten3P = isElementPresent("TT3Button_id");
			wait.until(ExpectedConditions.elementToBeClickable(Ten3P));
			js.executeScript("arguments[0].click();", Ten3P);
			logger.info("Clicked on Tender To 3P button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			try {
				wait2.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//*[@id=\"idValidationforMain\"]//ul[@id=\"errorid\"]")));
				String Valmsg = isElementPresent("OCValOnePack_xpath").getText();
				logger.info("Validation message is displayed=" + Valmsg);
				if (Valmsg.contains("3P Deliver Date Time must be greater than Tender To 3P Date Time.")) {

					// --Get the timeZone
					tzone = isElementPresent("TT3TimeZone_id").getText();
					rectime = getTimeAsTZone(tzone);

					// --Enter Drop Time
					DropTime = isElementPresent("TT3droptime_id");
					wait.until(ExpectedConditions.elementToBeClickable(DropTime));
					DropTime.clear();
					DropTime.sendKeys(rectime);
					DropTime.sendKeys(Keys.TAB);
					logger.info("Entered Drop time");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					// --Click on Tender To 3P
					Ten3P = isElementPresent("TT3Button_id");
					wait.until(ExpectedConditions.elementToBeClickable(Ten3P));
					js.executeScript("arguments[0].click();", Ten3P);
					logger.info("Clicked on Tender To 3P/ 3rd Party Delivery button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				}

			} catch (Exception evalidation) {

			}
		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "Tenderto3P" + svc);
			System.out.println("Tender to 3P Not Exist in Flow!!");
			logger.info("Tender to 3P Not Exist in Flow!!");

		}
Driver.navigate().refresh();
wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
Thread.sleep(1500);
logger.info("page is refreshed");

	}

}
//--510087763
