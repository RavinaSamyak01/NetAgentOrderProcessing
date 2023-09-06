package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Deliver extends OrderCreation {

	@Test
	public void confirmDelivery() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		// Actions act = new Actions(driver);
		WebDriverWait wait2 = new WebDriverWait(Driver, 10);// wait
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = null;
		try {
			wait2.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"lblStages\"][contains(text(),'Deliver')]")));

			// --Get the ServiceID
			svc = isElementPresent("TLServID_id").getText();
			System.out.println(svc);
			logger.info("ServiceID=" + svc);

			// --Get StageName
			OrderCreation OC = new OrderCreation();
			OC.getStageName();
			
			OC.movjobstatus();


			if (svc.equals("LOC") || svc.equals("DRV") || svc.equals("SDC") || svc.equals("FRG")
					|| svc.equals("3PLAST")) {

				// --Get the timeZone
				String tzone = isElementPresent("TLLOCDActTimZone_id").getText();
				String rectime = getTimeAsTZone(tzone);

				// --Enter Actual DL time
				isElementPresent("TLDActDLTime_id").clear();
				isElementPresent("TLDActDLTime_id").sendKeys(rectime);
				logger.info("Enter Actual DL Time");

				// --Enter SIgnature
				isElementPresent("TLDSignature_id").clear();
				isElementPresent("TLDSignature_id").sendKeys("RVOza");
				logger.info("Enter Signature");

				// --Click on Confirm DL
				WebElement ConfirmDel = isElementPresent("TLDConfDL_id");
				wait.until(ExpectedConditions.elementToBeClickable(ConfirmDel));
				jse.executeScript("arguments[0].click();", ConfirmDel);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				logger.info("Clicked on Confirm DEL button");

				// --CHeck parameter modify validation
				reCalc(svc);

				// --Pop Up
				boolean dlpop = Driver.getPageSource().contains("NetLink Global Logistics");

				if (dlpop == true) {
					isElementPresent("TLDPUOK_id").click();
					logger.info("Clicked on OK button of pop up");

				}

				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			}

			if (svc.equals("SD") || svc.equals("PA") || svc.equals("FRA")) {
				// --Get the timeZone
				String tzone = isElementPresent("TLSDDActTimZone_id").getText();
				String rectime = getTimeAsTZone(tzone);

				// --Enter Actual Del Time
				isElementPresent("TLDActDLTime_id").clear();
				isElementPresent("TLDActDLTime_id").sendKeys(rectime);
				isElementPresent("TLDActDLTime_id").sendKeys(Keys.TAB);

				// --Enter Signature
				isElementPresent("TLDSignature_id").clear();
				isElementPresent("TLDSignature_id").sendKeys("RVOza");
				logger.info("Enter Signature");

				// --Click on Confirm DL
				WebElement ConDL = isElementPresent("TLDConfDL2_id");
				wait.until(ExpectedConditions.elementToBeClickable(ConDL));
				jse.executeScript("arguments[0].click();", ConDL);
				logger.info("Clicked on Confirm DEL button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --CHeck parameter modify validation
				reCalc(svc);

				// --Pop Up
				boolean dlpop = Driver.getPageSource().contains("NetLink Global Logistics");

				if (dlpop == true) {
					isElementPresent("TLDPUOK_id").click();
					logger.info("Clicked on OK button of pop up");

				}

				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			}

			if (svc.equals("AIR")) {
				// --Get the timeZone
				String tzone = isElementPresent("TLDAIRTZone_id").getText();
				String rectime = getTimeAsTZone(tzone);

				// --Enter Actual Del Time
				isElementPresent("TLDAIRActualDTime_id").clear();
				isElementPresent("TLDAIRActualDTime_id").sendKeys(rectime);
				isElementPresent("TLDAIRActualDTime_id").sendKeys(Keys.TAB);

				// --Enter Signature
				isElementPresent("TLDAIRSign_id").clear();
				isElementPresent("TLDAIRSign_id").sendKeys("RVOza");
				logger.info("Enter Signature");

				// --Click on Confirm DL
				WebElement ConDL = isElementPresent("TLDConfDL2_id");
				wait.until(ExpectedConditions.elementToBeClickable(ConDL));
				jse.executeScript("arguments[0].click();", ConDL);
				logger.info("Clicked on Confirm DEL button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --CHeck parameter modify validation
				reCalc(svc);

				// --Pop Up
				boolean dlpop = Driver.getPageSource().contains("NetLink Global Logistics");

				if (dlpop == true) {
					isElementPresent("TLDPUOK_id").click();
					logger.info("Clicked on OK button of pop up");

				}

				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			}
		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "DELIVER" + svc);
			System.out.println("DELIVER Not Exist in Flow!!");
			logger.info("DELIVER Not Exist in Flow!!");

		}

	}

}
