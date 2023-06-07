package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;


public class HAANotify extends BaseInit {

	@Test

	public void haaNtfy() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		Actions act = new Actions(Driver);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		String svc = null;

		try {
			try {
				// --Click on Search All Job
				WebElement SearchAllJob = isElementPresent("TLSearchAlljob_id");
				wait.until(ExpectedConditions.visibilityOf(SearchAllJob));
				wait.until(ExpectedConditions.elementToBeClickable(SearchAllJob));
				act.moveToElement(SearchAllJob).build().perform();
				jse.executeScript("arguments[0].click();", SearchAllJob);
				logger.info("Clicked on SearchAllJob");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				for (int j = 1; j < 2; j++) {
					if (j == 1) // HAA for AIR
					{

						// --Get the PUID
						String pickupid = getData("Sheet1", 6, 32);
						logger.info("PUID==" + pickupid);

						// --Enter PU ID
						WebElement PUInput = isElementPresent("TLSAllJPickup_id");
						PUInput.clear();
						PUInput.sendKeys(pickupid);
						logger.info("Entered value in PickUp Input");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					}
				}

				// --Click on Search buttton
				WebElement SearchBtn = isElementPresent("TLSAllJSearchBtn_id");
				wait.until(ExpectedConditions.elementToBeClickable(SearchBtn));
				act.moveToElement(SearchBtn).build().perform();
				jse.executeScript("arguments[0].click();", SearchBtn);
				logger.info("Clicked on Search button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --Select 1st Job
				WebElement Select1stJob = isElementPresent("TLSAllJob1stJob_id");
				wait.until(ExpectedConditions.elementToBeClickable(Select1stJob));
				act.moveToElement(Select1stJob).build().perform();
				jse.executeScript("arguments[0].click();", Select1stJob);
				logger.info("Clicked on 1st JobID");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			} catch (Exception e) {
				logger.info(e);
			}
			svc = isElementPresent("TLServID_id").getText();
			System.out.println(svc);

			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"lblStages\"][contains(text(),'HAA Notify')]")));

			// --Get StageName
			OrderCreation OC = new OrderCreation();
			OC.getStageName();
			
			OC.movjobstatus();


			if (svc.equals("AIR")) {
				// --Move to Job Status Tab
				WebElement JoStatusTab = isElementPresent("TLJobStatusTab_id");
				wait.until(ExpectedConditions.elementToBeClickable(JoStatusTab));
				JoStatusTab.click();
				logger.info("Clicked on Job Status Tab");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			}

			// --Enter HAA Spoke with
			WebElement HAASpokeWith = isElementPresent("EOHAASpkWith_id");
			HAASpokeWith.clear();
			HAASpokeWith.sendKeys("Ravina Oza");
			HAASpokeWith.sendKeys(Keys.TAB);
			logger.info("Entered value in HAA Spoke With");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Click on Notify HAA button
			WebElement HAANotifyBtn = isElementPresent("EONotifyHAABtn_id");
			wait.until(ExpectedConditions.elementToBeClickable(HAANotifyBtn));
			HAANotifyBtn.click();
			logger.info("Clicked on HAA Notify button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "HAANotify" + svc);
			System.out.println("HAA Notify Not Exist in Flow!!");
			logger.info("HAA Notify Not Exist in Flow!!");

		}

	}
}
