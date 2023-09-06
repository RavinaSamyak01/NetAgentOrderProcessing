package connect_OCBaseMethods;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import netAgent_BasePackage.BaseInit;

public class NACustomerDelInProgress extends BaseInit {

	public void nacustomerDelInProgress() throws IOException {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		Actions act = new Actions(Driver);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		try {
			// popup
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("idCustDelInProgress")));
			/*
			 * // --Click on Confirm DL WebElement ConfirmDel =
			 * isElementPresent("NADInPConf_id");
			 * wait.until(ExpectedConditions.elementToBeClickable(ConfirmDel));
			 * jse.executeScript("arguments[0].click();", ConfirmDel);
			 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")
			 * )); logger.info("Clicked on Confirm button");
			 */
			WebElement Close = isElementPresent("NADinPCloseP_xpath");
			// wait.until(ExpectedConditions.elementToBeClickable(Close));
			jse.executeScript("arguments[0].click();", Close);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			logger.info("Clicked on Close button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception e) {

		}

		// --Get the ServiceID
		WebElement service = isElementPresent("NOEServiceID_xpath");
		wait.until(ExpectedConditions.visibilityOf(service));
		String svc = service.getText();
		System.out.println(svc);
		logger.info("ServiceID=" + svc);
		msg.append("ServiceID=" + svc + "\n");

		try {// --Get the timeZone
			String tzone = isElementPresent("NDelTimeZone_id").getText();
			String rectime = getTimeAsTZone(tzone);

			// --Enter Actual DL time
			isElementPresent("NDelTime_id").clear();
			isElementPresent("NDelTime_id").sendKeys(rectime);
			logger.info("Enter Actual DL Time");

			// --Enter SIgnature
			isElementPresent("NDelsign_id").clear();
			isElementPresent("NDelsign_id").sendKeys("RVOza");
			logger.info("Enter Signature");

			// --Click on Confirm DL
			WebElement ConfirmDel = isElementPresent("NDelbutton_id");
			wait.until(ExpectedConditions.elementToBeClickable(ConfirmDel));
			jse.executeScript("arguments[0].click();", ConfirmDel);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			logger.info("Clicked on Confirm DEL button");

			try {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
				WebElement DOK = Driver.findElement(By.id("iddataok"));
				jse.executeScript("arguments[0].click();", DOK);
				logger.info("Click on OK of Dialogue box");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception e) {
				logger.info("Dialogue is not exist");

			}
		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "CustomerDelInProgress" + svc);
			System.out.println("Customer Delivery In Progress Not Exist in Flow!!");
			logger.info("Customer Delivery In Progress Not Exist in Flow!!");
		}
	}

}
