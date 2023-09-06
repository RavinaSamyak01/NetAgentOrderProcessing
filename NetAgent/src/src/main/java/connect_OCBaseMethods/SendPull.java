package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import netAgent_BasePackage.BaseInit;

public class SendPull extends BaseInit {

	public void sendPull() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		Actions act = new Actions(Driver);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		OrderCreation OC = new OrderCreation();

		// --Get the ServiceID
		String svc = isElementPresent("TLServID_id").getText();
		System.out.println(svc);
		logger.info("ServiceID=" + svc);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"lblStages\"][contains(text(),'Send Pull Alert')]")));

			// --Get StageName
			OC.getStageName();
			
			OC.movjobstatus();

			
			//--Verify Courier
			String ExpCourier="34769";
			String ActCourier=isElementPresent("PDCouriervalue_xpath").getText();
			logger.info("Actual Courier="+ActCourier);
			
			if(ExpCourier.equalsIgnoreCase(ActCourier)) {
				logger.info("By default Automation courier is selected ");
			}else {
				OC.EditDriver();
			}

			// --Check Contacted
			if (isElementPresent("TLRDContacted_id").isDisplayed()) {
				WebElement email = isElementPresent("TLRDContacted_id");
				wait.until(ExpectedConditions.elementToBeClickable(email));
				jse.executeScript("arguments[0].click();", email);
				Select CBy = new Select(email);
				CBy.selectByValue("number:377");
				System.out.println("email selected");
				logger.info("Email is selected as a Contact By");
			} else {
				Select Contacttype = new Select(isElementPresent("TLRDContacted_id"));
				Contacttype.selectByVisibleText("Email");
				logger.info("Email is selected as a Contact By");

			}

			// --Enter ContactBy Value
			WebElement emailValue = isElementPresent("TLRDContValue_id");
			wait.until(ExpectedConditions.elementToBeClickable(emailValue));
			emailValue.clear();
			emailValue.sendKeys("Ravina.prajapati@samyak.com");
			logger.info("Entered EmailID");

			// --Spoke With
			WebElement spoke = isElementPresent("TLRDSpokeW_id");
			wait.until(ExpectedConditions.elementToBeClickable(spoke));
			spoke.clear();
			spoke.sendKeys("Ravina");
			logger.info("Entered Spoke With");

			// --Click on Send Pull button

			// try {
			WebElement Sendpullalert = isElementPresent("TLRDSPUALert_id");
			act.moveToElement(Sendpullalert).build().perform();
			jse.executeScript("arguments[0].click();", Sendpullalert);
			logger.info("Clicked on Send Pull button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			/*
			 * } catch (Exception e) { WebElement Sendpualert =
			 * isElementPresent("TLRDSPUALert_id");
			 * act.moveToElement(Sendpualert).build().perform();
			 * jse.executeScript("arguments[0].click();", Sendpualert);
			 * logger.info("Clicked on Alert&Confirm button");
			 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")
			 * ));
			 * 
			 * }
			 */

		} catch (Exception e) {
			logger.error(e);

			getScreenshot(Driver, "SendPull" + svc);
			System.out.println("Send Pull Not Exist in Flow!!");
			logger.info("Send Pull Not Exist in Flow!!");

		}

	}

}
