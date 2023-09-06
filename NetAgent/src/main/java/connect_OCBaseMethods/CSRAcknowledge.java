package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class CSRAcknowledge extends BaseInit {

	@Test
	public void csrAcknowledge() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);//
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		Actions act = new Actions(Driver);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		String svc = Driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);

		try {

			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"lblStages\"][contains(text(),'CSR Acknowledge')]")));

			// --Get StageName
			OrderCreation OC = new OrderCreation();
			OC.getStageName();

			OC.movjobstatus();

			if (svc.equals("LOC") || svc.equals("P3P")) {
				// --Click on Update button
				WebElement update = isElementPresent("TLAcknBTN_id");

				jse.executeScript("arguments[0].scrollIntoView();", update);
				Thread.sleep(2000);

				wait.until(ExpectedConditions.elementToBeClickable(update));
				jse.executeScript("arguments[0].click();", update);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//*[@id=\"idValidationforMain\"]//ul[@id=\"errorid\"]")));
					String Validmsg = isElementPresent("OCValOnePack_xpath").getText();
					logger.info("Validation message is displayed=" + Validmsg);
					if (Validmsg.contains("The Commodity field is required.")) {
						// --Go to Edit Job tab
						WebElement EditOrTab = isElementPresent("EOEditOrderTab_id");
						act.moveToElement(EditOrTab).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(EditOrTab));
						act.moveToElement(EditOrTab).click().perform();
						logger.info("Click on Edit Order Tab");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						Thread.sleep(5000);

						// -- COMMODITY_DROPDOWN
						WebElement CommDrop = isElementPresent("EOCommDrop_id");
						act.moveToElement(CommDrop).build().perform();
						Thread.sleep(2000);
						Select comodity_drpdown = new Select(CommDrop);
						comodity_drpdown.selectByIndex(1);
						logger.info("comodity dropdown is selected");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						Thread.sleep(2000);

						// --Enter Commodity
						WebElement Commo = isElementPresent("EOCommodity_id");
						act.moveToElement(Commo).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(Commo));
						Commo = isElementPresent("EOCommodity_id");
						Commo.clear();
						Commo.sendKeys("BOX");
						logger.info("Enter Commodity");

						// --Click on Save Changes button
						WebElement SaveChanges = isElementPresent("TLSaveChanges_id");
						act.moveToElement(SaveChanges).build().perform();
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
						wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
						act.moveToElement(SaveChanges).click().perform();
						logger.info("Click on Save Changes button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						Thread.sleep(5000);

						try {
							// --Click on Save Changes button
							SaveChanges = isElementPresent("TLSaveChanges_id");
							act.moveToElement(SaveChanges).build().perform();
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
							wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
							SaveChanges = isElementPresent("TLSaveChanges_id");
							act.moveToElement(SaveChanges).click().perform();
							logger.info("Click on Save Changes button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							Thread.sleep(5000);
						} catch (Exception e) {

						}

						// --Go to job Status Tab
						WebElement JobOverTab = isElementPresent("TLJobStatusTab_id");
						act.moveToElement(JobOverTab).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(JobOverTab));
						// act.moveToElement(JobOverTab).click().perform();
						jse.executeScript("arguments[0].click();", JobOverTab);
						logger.info("Click on Job Overview Tab");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						update = isElementPresent("TLAcknBTN_id");
						act.moveToElement(update).build().perform();
						Thread.sleep(2000);
						wait.until(ExpectedConditions.elementToBeClickable(update));
						jse.executeScript("arguments[0].click();", update);
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					}
				} catch (Exception e) {
					logger.info("Validation for commodity is not displyed");

				}

			}

			if (svc.equals("SD") || svc.equals("PA")) {
				// --Click on Update button
				WebElement update = isElementPresent("TLAckBTn2_id");
				jse.executeScript("arguments[0].scrollIntoView();", update);
				Thread.sleep(2000);
				wait.until(ExpectedConditions.elementToBeClickable(update));
				jse.executeScript("arguments[0].click();", update);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			}
		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "CSRACKNOWLEDGE" + svc);
			System.out.println("CSR ACKNOWLEDGE Not Exist in Flow!!");
			logger.info("CSR ACKNOWLEDGE Not Exist in Flow!!");

		}

	}
}
