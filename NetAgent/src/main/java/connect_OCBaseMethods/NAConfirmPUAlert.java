package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import netAgent_BasePackage.BaseInit;

public class NAConfirmPUAlert extends BaseInit {

	public void naconfirmPUAlert() throws Exception {

		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 60);// wait time
		Actions act = new Actions(Driver);
		WebDriverWait wait2 = new WebDriverWait(Driver, 10);// wait time;

		/*
		 * try { wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
		 * "//*[@class=\"ajax-loadernew\"]")));
		 * 
		 * } catch (Exception ee) {
		 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
		 * "//*[@class=\"ajax-loadernew\"]")));
		 * 
		 * } Thread.sleep(5000);
		 */

		// --Get the ServiceID
		WebElement service = isElementPresent("NOEServiceID_xpath");
		wait.until(ExpectedConditions.visibilityOf(service));
		String svc = service.getText();
		System.out.println(svc);
		logger.info("ServiceID=" + svc);
		msg.append("ServiceID=" + svc + "\n");

		try {

			try {
				wait2.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Confirm Pu Alert')]")));
				// --Set Pass in TestScenarios
				if (svc.equals("LOC")) {
					setData("TC_OrderProcess", 4, 5, "PASS");

				}
			} catch (Exception eStageName) {

				if (svc.equals("LOC")) {
					setData("TC_OrderProcess", 4, 5, "FAIL");

				}
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[@id=\"lblStages\"][contains(text(),'CONFIRM PULL ALERT')]")));

			}

			OrderCreation OC = new OrderCreation();
			OC.getNAStageName();

			// --Click on Confirm PU Alert
			try {
				WebElement ConfPUAlert = isElementPresent("NPUCOnfBtn_id");
				act.moveToElement(ConfPUAlert).build().perform();
				jse.executeScript("arguments[0].click();", ConfPUAlert);
				logger.info("Clicked on Confirm PU Alert button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			} catch (Exception e) {
				try {
					WebElement ConfPUAlert = isElementPresent("TLRDSPUALert_id");
					act.moveToElement(ConfPUAlert).build().perform();
					jse.executeScript("arguments[0].click();", ConfPUAlert);
					logger.info("Clicked on Confirm PU Alert button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				} catch (Exception ee) {
					WebElement ConfPUAlert = isElementPresent("D3PConfull_id");
					act.moveToElement(ConfPUAlert).build().perform();
					jse.executeScript("arguments[0].click();", ConfPUAlert);
					logger.info("Clicked on Confirm PU Alert button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				}

			}

			// --Set Pass in TestScenarios

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 5, 5, "PASS");

			}

		} catch (Exception e) {
			logger.error(e);
			logger.info("Line number is: " + e.getStackTrace()[0].getLineNumber());
			getScreenshot(Driver, "NAConfirmPUAlert" + svc);
			System.out.println("Confirm PU Alert Not Exist in Flow!!");
			logger.info("Confirm PU Alert Not Exist in Flow!!");

			// --Set Pass in TestScenarios

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 5, 5, "FAIL");

			}

		}

	}

}
