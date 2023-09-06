package NetAgent_Master_screen_Production;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Deliver_prod extends OrderCreation_prod {

	@Test
	public void confirmDelivery() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		 Actions act = new Actions(Driver);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = null;
		try {
//			wait.until(ExpectedConditions
//					.visibilityOfElementLocated(By.xpath("//*[@id=\"lblStages\"][contains(text(),'Deliver')]")));

			// --Get the ServiceID
			svc = isElementPresent("TLServID_id").getText();
			System.out.println(svc);
			logger.info("ServiceID=" + svc);

			// --Get StageName
			OrderCreation_prod OC = new OrderCreation_prod();
			OC.getStageName();

			if (svc.equals("LOC") || svc.equals("DRV") || svc.equals("SDC") || svc.equals("FRG")
					|| svc.equals("3PLAST")) {

				// --Get the timeZone
				String tzone = isElementPresent("TLLOCDActTimZone_id").getText();
				String rectime = getTimeAsTZone(tzone);

				// --Enter Actual DL time
				isElementPresent("TLDActDLTime_id").clear();
				isElementPresent("TLDActDLTime_id").sendKeys(rectime);
				logger.info("Enter Actual DL Time");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				
				// --Enter SIgnature
				isElementPresent("TLDSignature_id").clear();
				isElementPresent("TLDSignature_id").sendKeys("RVOza");
				logger.info("Enter Signature");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				
				// --Click on Confirm DL
				WebElement ConfirmDel = isElementPresent("TLDConfDL2_xpath");
				wait.until(ExpectedConditions.elementToBeClickable(ConfirmDel));
				jse.executeScript("arguments[0].click();", ConfirmDel);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				logger.info("Clicked on Confirm DEL button");

				// --CHeck parameter modify validation
				reCalc(svc);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

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
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				
				// --Enter Signature
				isElementPresent("TLDSignature_id").clear();
				isElementPresent("TLDSignature_id").sendKeys("RVOza",Keys.TAB);
				logger.info("Enter Signature");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(1500);
				// --Click on Confirm DL
				WebElement ConDL = isElementPresent("TLDConfDL2_xpath");
				//wait.until(ExpectedConditions.elementToBeClickable(ConDL));
				act.moveToElement(ConDL).build().perform();
				Thread.sleep(500);
				act.click(ConDL).build().perform();
				
				logger.info("Clicked on Confirm DEL button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				
				
				// -- Revalidate that SD job is delivered or not
				
//				try {
//					String del_status = OC.getStageName();
//					if(del_status.equalsIgnoreCase("Deliver")) {
//						// --Click on Confirm DL
//						WebElement ConfmDL = isElementPresent("TLDConfDL2_xpath");
//						ConfmDL.click();
//						logger.info("Click on Confirm Del button");
//						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
//					}
//				}
//				catch (Exception e) {
//					// TODO: handle exception
//					logger.info("Deliver stage is already proceeed");
//				}
				
				
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
