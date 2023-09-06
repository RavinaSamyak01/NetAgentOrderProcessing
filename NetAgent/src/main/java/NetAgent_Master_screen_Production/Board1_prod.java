package NetAgent_Master_screen_Production;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Board1_prod extends OrderCreation_prod {

	@Test
	public void onBoard1() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);//
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		String svc = Driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		
		try {

		// --Get StageName
			OrderCreation_prod OC = new OrderCreation_prod();
			
			String stg = Driver.findElement(By.id("lblStages")).getText();

			if (stg.contains("ON BOARD")) {
			// --Click on Update button
			WebElement update = isElementPresent("TLAckBTn2_id");
			wait.until(ExpectedConditions.elementToBeClickable(update));
			jse.executeScript("arguments[0].click();", update);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			}

		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "OnBoard1_" + svc);
			System.out.println("Board Not Exist in Flow!!");
			logger.info("Board Not Exist in Flow!!");

		}

	}
}
