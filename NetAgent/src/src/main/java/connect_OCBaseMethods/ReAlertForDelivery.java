package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;


public class ReAlertForDelivery extends BaseInit {
	@Test
	public void reAlertfordel() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		Actions act = new Actions(Driver);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		String svc = Driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);

		try {

			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"lblStages\"][contains(text(),'Re-alert for Delivery')]")));

			// --Get StageName
			OrderCreation OC = new OrderCreation();
			OC.getStageName();
			
			OC.movjobstatus();


			// --Alert&Confirm
			WebElement Sendpualert = isElementPresent("TLRDAlConfrm_id");
			act.moveToElement(Sendpualert).build().perform();
			jse.executeScript("arguments[0].click();", Sendpualert);
			logger.info("Clicked on Alert&Confirm button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "Re-alertforDel" + svc);
			System.out.println("Re-alert for Delivery Not Exist in Flow!!");
			logger.info("Re-alert for Delivery Not Exist in Flow!!");

		}

	}

}
