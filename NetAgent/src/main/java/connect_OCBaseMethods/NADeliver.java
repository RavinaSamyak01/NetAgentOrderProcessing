package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class NADeliver extends OrderCreation {

	@Test
	public void naconfirmDelivery() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		// Actions act = new Actions(Driver);
		WebDriverWait wait2 = new WebDriverWait(Driver, 10);// wait time
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = null;
		try {
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Deliver')]")));

			// --Get the ServiceID

			svc = isElementPresent("NOEServiceID_xpath").getText();
			System.out.println(svc);
			logger.info("ServiceID=" + svc);

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 8, 5, "PASS");

			}

			OrderCreation OC = new OrderCreation();
			OC.getNAStageName();

			if (svc.equals("LOC") || svc.equals("DRV") || svc.equals("SDC") || svc.equals("FRG")
					|| svc.equals("3PLAST")) {

				// --Get the timeZone
				String tzone = isElementPresent("NDelTimeZone_id").getText();
				String rectime = getTimeAsTZone(tzone);

				// --Enter Actual DL time
				isElementPresent("NDelTime_id").clear();
				isElementPresent("NDelTime_id").sendKeys(rectime);
				logger.info("Enter Actual DL Time");

				// --Enter SIgnature
				isElementPresent("NDelsign_id").clear();
				isElementPresent("NDelsign_id").sendKeys("RVOza");
				logger.info("Enter Signature");

				// --Click on Confirm DL
				WebElement ConfirmDel = isElementPresent("NDelbutton_id");
				wait.until(ExpectedConditions.elementToBeClickable(ConfirmDel));
				jse.executeScript("arguments[0].click();", ConfirmDel);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				logger.info("Clicked on Confirm DEL button");

				try {
					wait2.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
					WebElement DOK = Driver.findElement(By.id("iddataok"));
					jse.executeScript("arguments[0].click();", DOK);
					logger.info("Click on OK of Dialogue box");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				} catch (Exception e) {
					logger.info("Dialogue is not exist");

				}
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

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 9, 5, "PASS");

			}
		} catch (Exception e) {
			logger.error(e);
			logger.info("Line number is: " + e.getStackTrace()[0].getLineNumber());
			getScreenshot(Driver, "DELIVER" + svc);
			System.out.println("DELIVER Not Exist in Flow!!");
			logger.info("DELIVER Not Exist in Flow!!");
			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 8, 5, "FAIL");

			}
			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 9, 5, "FAIL");

			}
		}

	}

}
