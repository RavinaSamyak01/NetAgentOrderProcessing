package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class WaitForDeptarture extends OrderCreation {

	@Test
	public void waitForDept() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);//
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = Driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);

		try {

			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"lblStages\"][contains(text(),'Wait for Departure')]")));

			// --Get StageName
			OrderCreation OC = new OrderCreation();
			OC.getStageName();
			
			OC.movjobstatus();


			// --Departure Time
			isElementPresent("TLWFDTime_id").clear();
			isElementPresent("TLWFDTime_id").sendKeys(rdytime);
			isElementPresent("TLWFDTime_id").sendKeys(Keys.TAB);
			logger.info("Enter Departure Time");

			// --Click on Depart button
			WebElement Depart = isElementPresent("TLWFDDepart_id");
			wait.until(ExpectedConditions.elementToBeClickable(Depart));
			jse.executeScript("arguments[0].click();", Depart);
			logger.info("Click on Depart button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "WaitforDep" + svc);
			System.out.println("Wait for Departure Not Exist in Flow!!");
			logger.info("Wait for Departure Not Exist in Flow!!");

		}

	}
}
