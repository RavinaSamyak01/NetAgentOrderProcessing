package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class CSRAcknowledge extends BaseInit {

	@Test
	public void csrAcknowledge() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);//
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click

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
