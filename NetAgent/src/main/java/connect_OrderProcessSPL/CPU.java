package connect_OrderProcessSPL;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import connect_OCBaseMethods.NAConfirmPUAlert;
import connect_OCBaseMethods.NAConfirmPULLstg;
import connect_OCBaseMethods.NAConfirmPullAlert;
import connect_OCBaseMethods.NACustomerDelInProgress;
import connect_OCBaseMethods.NADrop;
import connect_OCBaseMethods.NAPickup;
import connect_OCBaseMethods.OrderCreation;
import connect_OCBaseMethods.SendPull;
import connect_OCBaseMethods.TCAcknowledge;
import connect_OCBaseMethods.VerifyCustomerBill;
import connect_OCBaseMethods.cancel_job;
import netAgent_BasePackage.BaseInit;

public class CPU extends BaseInit {

	@Test
	public void FedExCpu() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 60);// wait time
		WebDriverWait wait2 = new WebDriverWait(Driver, 7);// wait time
		// Actions act = new Actions(Driver);
		String Env = storage.getProperty("Env");
		// --Order Creation
		OrderCreation OC = new OrderCreation();

		OC.orderCreation(13);

		// --Get ServiceID
		String ServiceID = OC.getServiceID();

		// --Scroll to get Rate
		jse.executeScript("window.scrollBy(0,400)", "");
		String cost = isElementPresent("TLActualRate_id").getText();
		setData("OrderCreation", 13, 31, cost);
		logger.info("Scroll down to Get the Rate");

		// --Error Pop Up
		try {
			wait2.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@class=\"ngdialog-content ui-draggable\"]")));
			getScreenshot(Driver, "ErrorPopUp_" + ServiceID);
			WebElement ErrorPUp = isElementPresent("EOErrorPUp_id");
			wait.until(ExpectedConditions.elementToBeClickable(ErrorPUp));
			jse.executeScript("arguments[0].click();", ErrorPUp);
			logger.info("Clicked on OK button of Error Message");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		} catch (

		Exception eError) {
			System.out.println("error pop up is not displayed");
		}

		// --Moved to Job Status
		WebElement idJob = isElementPresent("TLJobStatusTab_id");
		wait.until(ExpectedConditions.elementToBeClickable(idJob));
		jse.executeScript("arguments[0].click();", idJob);
		logger.info("Clicked on Job Status Tab");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// TC Acknowledge
		TCAcknowledge TCAck = new TCAcknowledge();
		TCAck.tcAcknowledge();

		// Send Pull ALert
		SendPull Sp = new SendPull();
		Sp.sendPull();

		// --Refresh App
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		OC.refreshApp();

		String Portal = storage.getProperty("Portal");
		if (Portal.equalsIgnoreCase("Mob") && Env.equalsIgnoreCase("Prod")) {
			logger.info("No need to perform Processing");
		} else {
			// --NetAgent Tab
			OC.naTab();

			cpuProcessing();

			// --COnnect Tab
			OC.connectTab();

			OC.searchJob(13);

			// Verify Customer Bill
			VerifyCustomerBill VCB = new VerifyCustomerBill();
			VCB.verifyCustomerBill(13);

			if (Env.equalsIgnoreCase("PROD")) {

				// -- cancel job
				cancel_job cb = new cancel_job();
				cb.job_cancel(13);

			}

			else {

				logger.info("Current Enviornment is not Production , so job cancellation is not handled");
			}

			// -- navigae to NA tab

			// OC.naTab();

			OC.refreshApp();
		}

	}

	public void cpuProcessing() throws Exception {
		// --Order Creation
		OrderCreation OC = new OrderCreation();

		String PUID = getData("OrderCreation", 13, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		OC.opFromNetAgent(13);

		NAConfirmPullAlert CpullALert = new NAConfirmPullAlert();
		CpullALert.naConfirmPullAlertstage();

		OC.NATaskSearch(PUID);

		NAConfirmPULLstg ConfPull = new NAConfirmPULLstg();
		ConfPull.naConfirmPullstage(13);

		OC.NATaskSearch(PUID);

		NACustomerDelInProgress CDelInProg = new NACustomerDelInProgress();
		CDelInProg.nacustomerDelInProgress();

		OC.NATaskSearch(PUID);

		OC.narefreshApp();

	}

}
