package connect_OCBaseMethods;

import java.time.LocalTime;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import netAgent_BasePackage.BaseInit;

public class NAConfirmPULLstg extends BaseInit {

	public void naConfirmPullstage(int i) throws Exception {

		JavascriptExecutor js = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 60);// wait time
		WebDriverWait wait2 = new WebDriverWait(Driver, 10);// wait time
		Actions act = new Actions(Driver);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		OrderCreation OC = new OrderCreation();

		// --Get the ServiceID
		WebElement service = isElementPresent("NASPLService_xpath");
		wait.until(ExpectedConditions.visibilityOf(service));
		String svc = service.getAttribute("value");
		System.out.println(svc);
		logger.info("ServiceID=" + svc);
		msg.append("ServiceID=" + svc + "\n");

		try {
			wait2.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'Confirm Pull')]")));

			// --Get StageName
			OC.getNAStageName();

			// Click on Confirm Pull
			WebElement ConfmPull = isElementPresent("NAConfPull_id");
			wait.until(ExpectedConditions.elementToBeClickable(ConfmPull));
			act.moveToElement(ConfmPull).build().perform();
			js.executeScript("arguments[0].click();", ConfmPull);
			logger.info("Click on Confirm Pull  button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			// --Get the validation
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idPartPullDttmValidation")));
				String Valmsg = isElementPresent("NApartPullval_id").getText();
				logger.info("Validation message is displayed=" + Valmsg);
				if (Valmsg.contains("Part Pull DateTime is required.")) {
					// --Get the timeZone
					String tzone = isElementPresent("NAPPzoneID_xpath").getText();
					String rectime = getTimeAsTZone(tzone);
					String RecDate = getDateAsTZone(tzone);

					// --Enter Actual Pickup Date
					isElementPresent("NAPartPDate_id").clear();
					isElementPresent("NAPartPDate_id").sendKeys(RecDate);
					isElementPresent("NAPartPDate_id").sendKeys(Keys.TAB);
					logger.info("Enter Actual Part Pull Date");

					/*
					 * String QPT =
					 * Driver.findElement(By.id("txtQuotedforPickupTime")).getAttribute("value");
					 * logger.info("QPT==" + QPT);
					 * 
					 * LocalTime t = LocalTime.parse(QPT); LocalTime tn = t.minusMinutes(1); String
					 * Time = tn.toString(); logger.info("new time==" + Time);
					 */
					// --Enter Actual Pickup Time
					isElementPresent("NAPartPTime_id").clear();
					isElementPresent("NAPartPTime_id").sendKeys(rectime);
					isElementPresent("NAPartPTime_id").sendKeys(Keys.TAB);
					logger.info("Enter Actual Part Pull time");

					// Click on Confirm Pull

					Thread.sleep(5000);
					// Click on Confirm Pull
					ConfmPull = isElementPresent("NAConfPull_id");
					act.moveToElement(ConfmPull).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(ConfmPull));
					ConfmPull = isElementPresent("NAConfPull_id");
					wait.until(ExpectedConditions.elementToBeClickable(ConfmPull));
					act.moveToElement(ConfmPull).build().perform();
					ConfmPull.click();
					logger.info("Click on Confirm Pull  button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					try {
						wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//label[contains(@class,'error-messages')]")));
						String Validmsg = isElementPresent("NAPartsVal_xpath").getText();
						logger.info("Validation message is displayed = " + Validmsg);
						// --Get the serial NO

						String Env = storage.getProperty("Env");
						System.out.println("Env " + Env);
						String SerialNo = null;
						if (Env.equalsIgnoreCase("PROD")) {
							SerialNo = isElementPresent("part_master_no_xpath").getText();

						} else {
							SerialNo = isElementPresent("NAPartSerNo_xpath").getText();
						}

						logger.info("Serial No of Part is==" + SerialNo + "\n");
						// enter serial number in scan
						WebElement SerialNoBar = isElementPresent("NASerTextBox_id");
						act.moveToElement(SerialNoBar).build().perform();
						SerialNoBar.clear();
						SerialNoBar.sendKeys(SerialNo);
						SerialNoBar.sendKeys(Keys.TAB);
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						logger.info("Entered serial No in scan barcode");

						tzone = isElementPresent("NAPPzoneID_xpath").getText();
						rectime = getTimeAsTZone(tzone);
						RecDate = getDateAsTZone(tzone);

						// --Enter Actual Pickup Date
						isElementPresent("NAPartPDate_id").clear();
						isElementPresent("NAPartPDate_id").sendKeys(RecDate);
						isElementPresent("NAPartPDate_id").sendKeys(Keys.TAB);
						logger.info("Enter Actual Part Pull Date");

						/*
						 * String QPT =
						 * Driver.findElement(By.id("txtQuotedforPickupTime")).getAttribute("value");
						 * logger.info("QPT==" + QPT);
						 * 
						 * LocalTime t = LocalTime.parse(QPT); LocalTime tn = t.minusMinutes(1); String
						 * Time = tn.toString(); logger.info("new time==" + Time);
						 */
						// --Enter Actual Pickup Time
						isElementPresent("NAPartPTime_id").clear();
						isElementPresent("NAPartPTime_id").sendKeys(rectime);
						isElementPresent("NAPartPTime_id").sendKeys(Keys.TAB);
						logger.info("Enter Actual Part Pull time");

						// Click on Confirm Pull

						Thread.sleep(5000);
						// Click on Confirm Pull
						ConfmPull = isElementPresent("NAConfPull_id");
						act.moveToElement(ConfmPull).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(ConfmPull));
						ConfmPull = isElementPresent("NAConfPull_id");
						wait.until(ExpectedConditions.elementToBeClickable(ConfmPull));
						act.moveToElement(ConfmPull).build().perform();
						ConfmPull.click();
						logger.info("Click on Confirm Pull  button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						try {
							ConfmPull = isElementPresent("NAConfPull_id");
							act.moveToElement(ConfmPull).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(ConfmPull));
							ConfmPull = isElementPresent("NAConfPull_id");
							wait.until(ExpectedConditions.elementToBeClickable(ConfmPull));
							act.moveToElement(ConfmPull).build().perform();
							ConfmPull.click();
							logger.info("Click on Confirm Pull  button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						} catch (Exception e) {

						}
					} catch (Exception ee) {
						logger.info("Validation message is not displayed for Recalculate");

					}
				}

			} catch (Exception error) {
				System.out.println(error);
				logger.info("Validation message is not displayed for PartTime");

			}

		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "ConfirmPull" + svc);
			System.out.println("Confirm Pull Not Exist in Flow!!");
			logger.info("Confirm Pull Not Exist in Flow!!");
		}

	}
}
