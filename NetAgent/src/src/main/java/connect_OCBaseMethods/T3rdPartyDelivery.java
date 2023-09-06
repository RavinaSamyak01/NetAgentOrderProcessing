package connect_OCBaseMethods;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;


public class T3rdPartyDelivery extends BaseInit {
	
	@Test
	public void t3rdPartyDel() throws IOException {
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		// Actions act = new Actions(Driver);
		// JavascriptExecutor js = (JavascriptExecutor) Driver;// scroll,click

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = Driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"lblStages\"][contains(text(),'3rd Party Delivery')]")));

			// --Get StageName
			OrderCreation OC = new OrderCreation();
			OC.getStageName();
			
			OC.movjobstatus();


			// --Get the timeZone
			String tzone = isElementPresent("TT3TimeZone_id").getText();
			String rectime = getTimeAsTZone(tzone);

			// --Enter Drop Time
			WebElement DropTime = isElementPresent("TT3droptime_id");
			DropTime.clear();
			DropTime.sendKeys(rectime);
			DropTime.sendKeys(Keys.TAB);
			logger.info("Entered Drop time");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Click on Tender To 3P
			WebElement Ten3P = isElementPresent("TT3Button_id");
			wait.until(ExpectedConditions.elementToBeClickable(Ten3P));
			Ten3P.click();
			logger.info("Clicked on Tender To 3P button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			
			

		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "Tenderto3P" + svc);
			System.out.println("Tender to 3P Not Exist in Flow!!");
			logger.info("Tender to 3P Not Exist in Flow!!");

		}

		
	}

}
