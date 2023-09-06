package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;


public class OutForDelivery extends BaseInit {

	@Test
	public void outForDel() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		String svc = Driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"lblStages\"][contains(text(),'Out For Delivery')]")));

			// --Get StageName
			OrderCreation OC = new OrderCreation();
			OC.getStageName();
			
			OC.movjobstatus();


			WebElement HAOrigin = isElementPresent("TLHAOBtn_id");
			jse.executeScript("arguments[0].click();", HAOrigin);
			logger.info("Click on Confirm Out For Del button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "OutForDelivery" + svc);
			System.out.println("Out For Delivery Not Exist in Flow!!");
			logger.info("Out For Delivery Not Exist in Flow!!");

		}

	}
}
