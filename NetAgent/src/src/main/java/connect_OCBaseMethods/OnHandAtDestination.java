package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;


public class OnHandAtDestination extends BaseInit {

	@Test
	public void onHandDst() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = Driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);
		try {

			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"lblStages\"][contains(text(),'Onhand @ Destination')]")));

			String stg = Driver.findElement(By.id("lblStages")).getText();

			// --Get StageName
			OrderCreation OC = new OrderCreation();
			OC.getStageName();
			
			OC.movjobstatus();


			if (stg.contains("ONHAND")) {
				Thread.sleep(6000);
				String tzone = Driver.findElement(By.id("spnOnHand")).getText();
				String onhandtime = getTimeAsTZone(tzone);

				WebElement Time = isElementPresent("ONHADTime_id");
				Time.clear();
				Time.sendKeys(onhandtime);
				Time.sendKeys(Keys.TAB);
				logger.info("Enter On Hand Time");

				WebElement SpokeWith = isElementPresent("ONHADSpokeWith_id");
				SpokeWith.clear();
				SpokeWith.sendKeys("RV Oza");
				SpokeWith.sendKeys(Keys.TAB);
				logger.info("Enter On Hand Spoke With");

				WebElement ConfirmOnHandBtn = isElementPresent("ONHADConfOnHand_id");
				wait.until(ExpectedConditions.elementToBeClickable(ConfirmOnHandBtn));
				ConfirmOnHandBtn.click();
				logger.info("Click on Confirm OnHand button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			}
		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "Onhand@Dest" + svc);
			System.out.println("Onhand @ Destination Not Exist in Flow!!");
			logger.info("Onhand @ Destination Not Exist in Flow!!");
		}

	}

}
