package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class NAPickup extends OrderCreation {

	@Test
	public void naconfirmPickup() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		Actions act = new Actions(Driver);
		JavascriptExecutor js = (JavascriptExecutor) Driver;

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Get the ServiceID
		String svc = isElementPresent("NOEServiceID_xpath").getText();
		System.out.println(svc);
		logger.info("ServiceID=" + svc);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Pickup')]")));

			OrderCreation OC = new OrderCreation();
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

				try {
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
					WebElement Dyes = isElementPresent("NTPuPopYes_id");
					js.executeScript("arguments[0].click();", Dyes);
					logger.info("Clicked on Yes button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				} catch (Exception e) {
					logger.info("Dialogue is not exist");

				}

			}

			if (svc.equals("PA") || svc.equals("AIR") || svc.equals("FRA")) {

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
				ConfPU.click();
				logger.info("Click on Confirm PU button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				try {

					wait.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//*[@id=\"idValidationforMain\"]//ul[@id=\"errorid\"]")));
					String Valmsg = isElementPresent("OCValOnePack_xpath").getText();
					logger.info("Validation message is displayed=" + Valmsg);
					if (Valmsg.contains("Parameter(s) are modified. Please recalculate customer charges.")) {
						// Recalculate the charges
						// --Go to Edit Job tab
						WebElement EditOrTab = isElementPresent("EOEditOrderTab_id");
						act.moveToElement(EditOrTab).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(EditOrTab));
						act.moveToElement(EditOrTab).click().perform();
						logger.info("Click on Edit Order Tab");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// -Recalculate button
						WebElement ReCalc = isElementPresent("EORecal_id");
						act.moveToElement(ReCalc).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(ReCalc));
						act.moveToElement(ReCalc).click().perform();
						logger.info("Click on Recalculate button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// --Click on Save Changes button
						WebElement SaveChanges = isElementPresent("TLSaveChanges_id");
						act.moveToElement(SaveChanges).build().perform();
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
						wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
						act.moveToElement(SaveChanges).click().perform();
						logger.info("Click on Save Changes button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// --Go to job Status Tab
						WebElement JobOverTab = isElementPresent("TLJobStatusTab_id");
						act.moveToElement(JobOverTab).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(JobOverTab));
						act.moveToElement(JobOverTab).click().perform();
						logger.info("Click on Job Overview Tab");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						tzone = isElementPresent("TLPUTimeZone_id").getText();
						rectime = getTimeAsTZone(tzone);

						// --Enter Actual Pickup Time
						isElementPresent("TLPActPUpTime_id").clear();
						isElementPresent("TLPActPUpTime_id").sendKeys(rectime);
						isElementPresent("TLPActPUpTime_id").sendKeys(Keys.TAB);
						logger.info("Enter Actual pickup time");

						// --Click on Confirm PU button
						ConfPU = isElementPresent("TLPUConfPU2_id");
						wait.until(ExpectedConditions.elementToBeClickable(ConfPU));
						Thread.sleep(2000);
						ConfPU.click();
						logger.info("Click on Confirm PU button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					}

				} catch (Exception PModify) {
					logger.info("Validation message is not displayed for Recalculate the charges");

				}

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
