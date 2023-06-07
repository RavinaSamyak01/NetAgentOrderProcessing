package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import netAgent_BasePackage.BaseInit;

public class ConfirmPUAlert extends BaseInit {

	public void confirmPUAlert() throws Exception {

		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 50);// wait time
		Actions act = new Actions(Driver);

		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		Thread.sleep(2000);

		// --Get the ServiceID
		String svc = isElementPresent("NOEServiceID_xpath").getText();
		System.out.println(svc);
		logger.info("ServiceID=" + svc);
		msg.append("ServiceID=" + svc + "\n");

		try {

			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Confirm Pu Alert')]")));

			} catch (Exception eStageName) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[@id=\"lblStages\"][contains(text(),'CONFIRM PULL ALERT')]")));

			}

			OrderCreation OC = new OrderCreation();
			OC.getNAStageName();
			
			OC.movjobstatus();


			// --Click on Confirm PU Alert
			try {
				WebElement ConfPUAlert = isElementPresent("NPUCOnfBtn_id");
				act.moveToElement(ConfPUAlert).build().perform();
				jse.executeScript("arguments[0].click();", ConfPUAlert);
				logger.info("Clicked on Confirm PU Alert button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			} catch (Exception e) {
				try {
					WebElement ConfPUAlert = isElementPresent("TLRDSPUALert_id");
					act.moveToElement(ConfPUAlert).build().perform();
					jse.executeScript("arguments[0].click();", ConfPUAlert);
					logger.info("Clicked on Confirm PU Alert button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				} catch (Exception ee) {
					WebElement ConfPUAlert = isElementPresent("D3PConfull_id");
					act.moveToElement(ConfPUAlert).build().perform();
					jse.executeScript("arguments[0].click();", ConfPUAlert);
					logger.info("Clicked on Confirm PU Alert button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				}

			}

		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "NAConfirmPUAlert" + svc);
			System.out.println("Confirm PU Alert Not Exist in Flow!!");
			logger.info("Confirm PU Alert Not Exist in Flow!!");
		}

	}

}
