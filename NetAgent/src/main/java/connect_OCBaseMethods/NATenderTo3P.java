package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class NATenderTo3P extends OrderCreation {

	@Test
	public void natndrTo3P() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		// Actions act = new Actions(Driver);
		JavascriptExecutor js = (JavascriptExecutor) Driver;// scroll,click
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Get the ServiceID
		String svc = isElementPresent("NOEServiceID_xpath").getText();
		System.out.println(svc);
		logger.info("ServiceID=" + svc);

		try {
			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tender to 3P')]")));

			} catch (Exception e) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[@id=\"lblStages\"][contains(text(),'3rd Party Delivery')]")));

			}

			// --Get StageName
			OrderCreation OC = new OrderCreation();
			String stage = OC.getNAStageName();

			// --Get the timeZone
			String tzone = isElementPresent("NDelTimeZone_id").getText();
			String rectime = getTimeAsTZone(tzone);

			// --Enter Drop Time
			WebElement DropTime = isElementPresent("NDelTime_id");
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
					|| ((svc.equalsIgnoreCase("H3P") && stage.equalsIgnoreCase("Tender to 3P"))
							|| ((svc.equalsIgnoreCase("3PLAST") && stage.equalsIgnoreCase("Tender to 3P"))))) {

				try {

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

				catch (Exception e) {

					WebElement pkg_tracking_no = isElementPresent("pkg_tracking_id");
					js.executeScript("arguments[0].scrollIntoView(true);", pkg_tracking_no);

					// --Get packageTrackingID
					String TrackingNo = pkg_tracking_no.getText();
					logger.info("Tracking No==" + TrackingNo);

					if (TrackingNo.isBlank()) {
						pkg_tracking_no.clear();
						pkg_tracking_no.sendKeys("12345");
					} else {

					}

				}

			}

			// --Get the timeZone
			tzone = isElementPresent("NDelTimeZone_id").getText();
			rectime = getTimeAsTZone(tzone);

			// --Enter Drop Time
			DropTime = isElementPresent("NDelTime_id");
			wait.until(ExpectedConditions.elementToBeClickable(DropTime));
			DropTime.clear();
			DropTime.sendKeys(rectime);
			DropTime.sendKeys(Keys.TAB);
			logger.info("Entered Drop time");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Click on Tender To 3P
			WebElement Ten3P = isElementPresent("NDelbutton_id");
			wait.until(ExpectedConditions.elementToBeClickable(Ten3P));
			js.executeScript("arguments[0].scrollIntoView(true);", Ten3P);
			Thread.sleep(1000);
			js.executeScript("arguments[0].click();", Ten3P);
			logger.info("Clicked on Tender To 3P button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			try {
				// popup
				wait.until(
						ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"modal-dialog\"]")));
				/*
				 * // --Click on Confirm DL WebElement ConfirmDel =
				 * isElementPresent("NADInPConf_id");
				 * wait.until(ExpectedConditions.elementToBeClickable(ConfirmDel));
				 * jse.executeScript("arguments[0].click();", ConfirmDel);
				 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")
				 * )); logger.info("Clicked on Confirm button");
				 * 
				 */
				String DEialogueContent = isElementPresent("NAtT3pDc_xpath").getText();
				logger.info("Dialogue content==" + DEialogueContent);

				WebElement Yes = isElementPresent("CCADOK_id");
				// wait.until(ExpectedConditions.elementToBeClickable(Close));
				js.executeScript("arguments[0].click();", Yes);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				logger.info("Clicked on Yes");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			} catch (Exception e) {

			}
		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "Tenderto3P" + svc);
			System.out.println("Tender to 3P Not Exist in Flow!!");
			logger.info("Tender to 3P Not Exist in Flow!!");

		}

	}

}
//--510087763
