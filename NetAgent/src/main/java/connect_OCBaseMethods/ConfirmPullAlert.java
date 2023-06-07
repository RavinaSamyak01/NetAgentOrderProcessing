package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import netAgent_BasePackage.BaseInit;


public class ConfirmPullAlert extends BaseInit {

	public void ConfirmPullAlertstage() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		Actions act = new Actions(Driver);
		JavascriptExecutor js = (JavascriptExecutor) Driver;

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Get the ServiceID
		String svc = isElementPresent("TLServID_id").getText();
		System.out.println(svc);
		logger.info("ServiceID=" + svc);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"lblStages\"][contains(text(),'CONFIRM PULL ALERT')]")));

			OrderCreation OC = new OrderCreation();
			OC.getStageName();
			
			OC.movjobstatus();

			// Click on Confirm Pull ALert
			WebElement ConfPullAlert = isElementPresent("TLRDSPUALert_id");
			wait.until(ExpectedConditions.elementToBeClickable(ConfPullAlert));
			act.moveToElement(ConfPullAlert).build().perform();
			js.executeScript("arguments[0].click();", ConfPullAlert);
			logger.info("Click on Confirm Pull Alert  button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "ConfirmPullAlert" + svc);
			System.out.println("Confirm Pull ALert Not Exist in Flow!!");
			logger.info("Confirm Pull ALert Not Exist in Flow!!");
		}

	}
}
