package connect_OCBaseMethods;

import org.eclipse.sisu.reflect.Logs;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class ReadyForDispatch extends BaseInit {

	@Test
	public void pickupAlert() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait;
		try {
			wait = new WebDriverWait(Driver, 30);// wait time

		} catch (Exception ewait) {
			wait = new WebDriverWait(Driver, 60);// wait time

		}
		//WebDriverWait wait = new WebDriverWait(Driver, 60);// wait time
		WebDriverWait wait2 = new WebDriverWait(Driver, 7);// wait time
		Actions act = new Actions(Driver);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		OrderCreation OC = new OrderCreation();
		String svc = OC.getServiceID();
		try {
			wait2.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"lblStages\"][contains(text(),'Ready For Dispatch')]")));

			// --Get StageName
			OC.getStageName();

			OC.movjobstatus();

			OC.EditDriver();

			// --Check Contacted
			if (isElementPresent("TLRDContacted_id").isDisplayed()) {
				WebElement email = isElementPresent("TLRDContacted_id");
				wait.until(ExpectedConditions.elementToBeClickable(email));
				jse.executeScript("arguments[0].click();", email);
				Select CBy = new Select(email);
				CBy.selectByValue("number:377");
				System.out.println("email selected");
				logger.info("Email is selected as a Contact By");
			} else {
				Select Contacttype = new Select(isElementPresent("TLRDContacted_id"));
				Contacttype.selectByVisibleText("Email");
				logger.info("Email is selected as a Contact By");

			}

			// --Enter ContactBy Value
			WebElement emailValue = isElementPresent("TLRDContValue_id");
			wait.until(ExpectedConditions.elementToBeClickable(emailValue));
			emailValue.clear();
			emailValue.sendKeys("Ravina.prajapati@samyak.com");
			logger.info("Entered EmailID");

			// --Spoke With
			WebElement spoke = isElementPresent("TLRDSpokeW_id");
			wait.until(ExpectedConditions.elementToBeClickable(spoke));
			spoke.clear();
			spoke.sendKeys("Ravina");
			logger.info("Entered Spoke With");

			Thread.sleep(5000);

			// --Click on Alert and Confirm
			try {
				WebElement Sendpualert = isElementPresent("TLRDSPuAlert_xpath");
				act.moveToElement(Sendpualert).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(Sendpualert));
				jse.executeScript("arguments[0].click();", Sendpualert);
				logger.info("Clicked on Alert&Confirm button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			} catch (Exception e) {
				WebElement Sendpualert = isElementPresent("TLRDAlConfrmBtn_xpath");
				wait.until(ExpectedConditions.elementToBeClickable(Sendpualert));
				act.moveToElement(Sendpualert).build().perform();
				jse.executeScript("arguments[0].click();", Sendpualert);
				logger.info("Clicked on Alert&Confirm button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			}

			try {
				try {
					


					 WebElement Sendpualert = new WebDriverWait(Driver, 7)
							    .until(ExpectedConditions.visibilityOfElementLocated(By.id("TLRDSPUALert_id")));

					act.moveToElement(Sendpualert).build().perform();
					wait2.until(ExpectedConditions.elementToBeClickable(Sendpualert));
					Sendpualert = isElementPresent("TLRDSPUALert_id");
					Sendpualert.sendKeys(Keys.ENTER);
					logger.info("Clicked on Alert&Confirm button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				} catch (Exception e) {
					WebElement Sendpualert = new WebDriverWait(Driver, 7)
						    .until(ExpectedConditions.visibilityOfElementLocated(By.id("TLRDSPUALert_id")));
					act.moveToElement(Sendpualert).build().perform();
					wait2.until(ExpectedConditions.elementToBeClickable(Sendpualert));
					Sendpualert = isElementPresent("TLRDSPUALert_id");
					Sendpualert.sendKeys(Keys.ENTER);
					logger.info("Clicked on Alert&Confirm button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				}
				logger.info("Clicked on confirm by Action class");
			} catch (Exception ee) {

			}

			// --Set Pass in TestScenarios

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 3, 5, "PASS");

			}

		} catch (Exception e) {
			logger.error(e);

			getScreenshot(Driver, "READYFORDISPATCH" + svc);
			System.out.println("READY FOR DISPATCH Not Exist in Flow!!");
			logger.info("READY FOR DISPATCH Not Exist in Flow!!");

			// --Set FAIL in TestScenarios

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 3, 5, "FAIL");

			}
		}

	}

}
