package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Drop extends OrderCreation {

	@Test
	public void dropAtOrigin() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		Actions act = new Actions(Driver);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = null;
		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Drop @ Origin')]")));

			// --Get the ServiceID
			svc = isElementPresent("NOEServiceID_xpath").getText();
			System.out.println(svc);
			logger.info("ServiceID=" + svc);

			// --Get StageName
			OrderCreation OC = new OrderCreation();
			OC.getNAStageName();

			OC.movjobstatus();

			// --Get the timeZone
			String tzone = isElementPresent("NDelTimeZone_id").getText();
			String rectime = getTimeAsTZone(tzone);

			// --Enter DropOff time
			WebElement dropoff = isElementPresent("NDelTime_id");
			dropoff.sendKeys(rectime);
			dropoff.sendKeys(Keys.TAB);
			logger.info("Enter Drop off time");

			// --Click on COnfirm Drop
			WebElement btng = isElementPresent("NDelbutton_id");
			jse.executeScript("arguments[0].click();", btng);
			logger.info("Click on Confirm Drop button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			try {
				String awb = isElementPresent("Error_id").getText();
				System.out.println(awb);
				logger.info("awb");
				if (awb.contains("Airbill is Required.")) {

					isElementPresent("TLDAOAirBill_id").click();
					logger.info("Click on Add AirBill");

					// --Add Airbill
					WebElement AirBill = isElementPresent("NTAirBill_id");
					jse.executeScript("arguments[0].scrollIntoView();", AirBill);

					WebElement AddAirBill = isElementPresent("NTAddAirBill_id");
					jse.executeScript("arguments[0].click();", AddAirBill);
					wait.until(ExpectedConditions
							.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"tableairbill\"]/tbody/tr")));
					logger.info("AirBill editor is opened");

					/// --Enter AirBill

					WebElement Airbill = isElementPresent("NTAirBill01_id");
					act.moveToElement(Airbill).build().perform();
					Airbill.sendKeys("11111111");
					logger.info("Entered AirBill");

					/// --Enter Description
					WebElement Desc = isElementPresent("NTAirBDesc_id");
					act.moveToElement(Desc).build().perform();
					Desc.sendKeys("SD Service Automation");
					logger.info("Entered Description");

					/// --Enter NoOFPieces
					WebElement NoOFP = isElementPresent("NTAirBQunt_id");
					act.moveToElement(NoOFP).build().perform();
					NoOFP.sendKeys("2");
					logger.info("Entered NoOFPieces");

					/// --Enter Total Weight
					WebElement TWght = isElementPresent("NTAirBWeight_id");
					act.moveToElement(TWght).build().perform();
					TWght.sendKeys("10");
					logger.info("Entered Total Weight");

					// --Track
					WebElement Track = isElementPresent("NTAirBTrack_id");
					wait.until(ExpectedConditions.elementToBeClickable(Track));
					jse.executeScript("arguments[0].click();", Track);
					logger.info("Clicked on Track button");

					// --AIrbill new window
					String WindowHandlebefore = Driver.getWindowHandle();
					for (String windHandle : Driver.getWindowHandles()) {
						Driver.switchTo().window(windHandle);
						logger.info("Switched to Track window");

						Thread.sleep(5000);
						getScreenshot(Driver, "Track" + PUId);

					}
					Driver.close();
					logger.info("Closed Track window");

					Driver.switchTo().window(WindowHandlebefore);
					logger.info("Switched to main window");

					// Scroll up
					jse.executeScript("window.scrollBy(0,-250)");
				}
			} catch (Exception e) {
				System.out.println("Airbill already exist !!");
				logger.info("Airbill already exist !!");

			}

			try {
				try {
					// --Click on Yes button
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnYes")));
					WebElement BtnYes = isElementPresent("EOAIRUnSHYes_id");
					wait.until(ExpectedConditions.elementToBeClickable(BtnYes));
					act.moveToElement(BtnYes).build().perform();
					jse.executeScript("arguments[0].click();", BtnYes);
					logger.info("Clicked on Yes button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				} catch (Exception ee) {
					// --Click on Yes button
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@ng-click=\"ok()\"]")));
					String StHMsg = isElementPresent("AirSTationH_xpath").getText();
					logger.info("Message==" + StHMsg);
					WebElement BtnYes = isElementPresent("CPUDYesPrc_xpath");
					wait.until(ExpectedConditions.elementToBeClickable(BtnYes));
					act.moveToElement(BtnYes).build().perform();
					jse.executeScript("arguments[0].click();", BtnYes);
					logger.info("Clicked on Yes button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				}

			} catch (Exception e) {

			}

			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//*[@id=\"idValidationforMain\"]//ul[@id=\"errorid\"]")));
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

					// --Enter SIgnature
					isElementPresent("TLDSignature_id").clear();
					isElementPresent("TLDSignature_id").sendKeys("RVOza");
					logger.info("Enter Signature");

					// --Get the timeZone
					tzone = isElementPresent("TLDropTimeZone_id").getText();
					rectime = getTimeAsTZone(tzone);

					// --Enter Actual DL time
					isElementPresent("TLDActDLTime_id").clear();
					isElementPresent("TLDActDLTime_id").sendKeys(rectime);
					logger.info("Enter Actual DL Time");

					String Date = getDateAsTZone(tzone);

					// --Enter Actual DL Date
					isElementPresent("TLDropDate_id").clear();
					isElementPresent("TLDAcTLDropDate_idtDLTime_id").sendKeys(Date);
					logger.info("Enter Actual DL Date");

					// --Click on COnfirm Drop
					btng = isElementPresent("TLDAOConfDrop_id");
					jse.executeScript("arguments[0].click();", btng);
					logger.info("Click on Confirm Drop button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				}

			} catch (Exception PModify) {
				logger.info("Validation message is not displayed for Recalculate the charges");

			}

		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "DROP@ORIGIN" + svc);
			System.out.println("DROP @ ORIGIN Not Exist in Flow!!");
			logger.info("DROP @ ORIGIN Not Exist in Flow!!");

		}

	}
}
