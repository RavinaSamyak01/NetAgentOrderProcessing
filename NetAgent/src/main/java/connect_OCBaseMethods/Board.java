package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Board extends OrderCreation {

	@Test
	public void onBoard() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);//
		WebDriverWait wait2 = new WebDriverWait(Driver, 10);// wait time;
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		String svc = Driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);
		try {

			wait2.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"lblStages\"][contains(text(),'ON BOARD')]")));
			OrderCreation OC = new OrderCreation();
			OC.getStageName();

			movjobstatus();
			
			// --Click on Update button
			WebElement update = isElementPresent("TLAckBTn2_id");
			wait.until(ExpectedConditions.elementToBeClickable(update));
			jse.executeScript("arguments[0].click();", update);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "OnBoard_" + svc);
			System.out.println("Board Not Exist in Flow!!");
			logger.info("Board Not Exist in Flow!!");

		}
		// --Get StageName

	}
}
