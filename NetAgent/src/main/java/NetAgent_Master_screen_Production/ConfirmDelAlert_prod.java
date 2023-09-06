package NetAgent_Master_screen_Production;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import netAgent_BasePackage.BaseInit;

public class ConfirmDelAlert_prod extends BaseInit {

	public void confDelAlert() throws IOException {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		Actions act = new Actions(Driver);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

		// --Get the ServiceID
		String svc = isElementPresent("NOEServiceID_xpath").getText();
		System.out.println(svc);
		logger.info("ServiceID=" + svc);
		msg.append("ServiceID=" + svc + "\n");

		try {

//			wait.until(ExpectedConditions
//					.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Confirm Del Alert')]")));

			OrderCreation_prod OC = new OrderCreation_prod();
			OC.getNAStageName();

			// --Click on Confirm PU Alert
			try {
				WebElement ConfPUAlert = isElementPresent("NPUCOnfBtn_id");
				act.moveToElement(ConfPUAlert).build().perform();
				jse.executeScript("arguments[0].click();", ConfPUAlert);
				logger.info("Clicked on Confirm Del Alert button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			} catch (Exception e) {
				try {
					WebElement ConfPUAlert = isElementPresent("TLRDSPUALert_id");
					act.moveToElement(ConfPUAlert).build().perform();
					jse.executeScript("arguments[0].click();", ConfPUAlert);
					logger.info("Clicked on Confirm Del Alert button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				} catch (Exception ee) {
					WebElement ConfPUAlert = isElementPresent("D3PConfull_id");
					act.moveToElement(ConfPUAlert).build().perform();
					jse.executeScript("arguments[0].click();", ConfPUAlert);
					logger.info("Clicked on Confirm Del Alert button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				}

			}

		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "NAConfirmDelAlert" + svc);
			System.out.println("Confirm Del Alert Not Exist in Flow!!");
			logger.info("Confirm Del Alert Not Exist in Flow!!");
		}

	}

}
