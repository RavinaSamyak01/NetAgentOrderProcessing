package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;



public class SendDelAlert extends BaseInit {
	@Test
	public void delAlert() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		Actions act = new Actions(Driver);
		WebDriverWait wait2 = new WebDriverWait(Driver, 10);// wait time;
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = Driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);

		try {
			wait2.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"lblStages\"][contains(text(),'Send Del Alert')]")));

			// --Get StageName
			OrderCreation OC = new OrderCreation();
			OC.getStageName();
			
			OC.movjobstatus();
	
			//Edit Driver
			OC.EditDriver();

			// --Contacted
			Select Contacttype = new Select(isElementPresent("TLRDContacted_id"));
			Contacttype.selectByVisibleText("Email");
			logger.info("Selected Email option as Contacted");
			Thread.sleep(1000);

			// --Contatcted Value
			isElementPresent("TLRDContValue_id").clear();
			isElementPresent("TLRDContValue_id").sendKeys("Ravina.prajapati@samyak.com");
			logger.info("entered Contacted By");

			// --Spoke with
			isElementPresent("TLRDSpokeW_id").clear();
			isElementPresent("TLRDSpokeW_id").sendKeys("Ravina");
			logger.info("entered Spoke With");
			Thread.sleep(1000);

			// --Driver
			Select Driver = new Select(isElementPresent("PDNameDrp_id"));
			Driver.selectByIndex(1);
			logger.info("Selected Driver");
			Thread.sleep(1000);

			// --Alert&Confirm button
			try {

				WebElement AlConfirm = isElementPresent("TLRDSPUALert_id");
				wait.until(ExpectedConditions.elementToBeClickable(AlConfirm));
				jse.executeScript("arguments[0].click();", AlConfirm);
				logger.info("CLicked on Alert and Confirm Button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(1500);
				
			

			} catch (Exception ee) {
				WebElement AlConfirm = isElementPresent("TLRDAlConfrm_id");
				act.moveToElement(AlConfirm).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(AlConfirm));
				jse.executeScript("arguments[0].click();", AlConfirm);
				logger.info("CLicked on Alert and Confirm Button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			}
		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "SENDDELALERT" + svc);
			System.out.println("SEND DEL ALERT Not Exist in Flow!!");
			logger.info("SEND DEL ALERT Not Exist in Flow!!");

		}
		
		Driver.navigate().refresh();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		logger.info("Page is refreshed");
		Thread.sleep(1000);
		
	}
}
