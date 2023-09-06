package connect_OCBaseMethods;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import netAgent_BasePackage.BaseInit;

public class NAConfReAlertDelivery extends BaseInit {

	public void naConfReAlertDelivery() throws IOException {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		// Actions act = new Actions(Driver);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = null;
		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Confirm Delivery Re-alert')]")));

			// --Get the ServiceID
			svc = isElementPresent("NOEServiceID_xpath").getText();
			System.out.println(svc);
			logger.info("ServiceID=" + svc);

			OrderCreation OC = new OrderCreation();
			OC.getNAStageName();

			// --Click on Confirm button
			WebElement ConDL = isElementPresent("NPUCOnfBtn_id");
			wait.until(ExpectedConditions.elementToBeClickable(ConDL));
			jse.executeScript("arguments[0].click();", ConDL);
			logger.info("Clicked on Confirm button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception e) {
			logger.error(e);
			logger.info("Line number is: " + e.getStackTrace()[0].getLineNumber());
			getScreenshot(Driver, "ConfReAlertDelivery" + svc);
			System.out.println("Confirm Re-Alert Delivery Not Exist in Flow!!");
			logger.info("Confirm Re-Alert Delivery Not Exist in Flow!!");

		}
	}

}
