package connect_OrderProcessNonSPL;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import connect_OCBaseMethods.NAConfirmPUAlert;
import connect_OCBaseMethods.NADeliver;
import connect_OCBaseMethods.NAPickup;
import connect_OCBaseMethods.OrderCreation;
import connect_OCBaseMethods.ReadyForDispatch;
import connect_OCBaseMethods.TCAcknowledge;
import connect_OCBaseMethods.VerifyCustomerBill;
import connect_OCBaseMethods.cancel_job;
import netAgent_BasePackage.BaseInit;

public class LOC extends BaseInit {
	@Test
	public void locLocal() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 50);// wait time
		String Env = storage.getProperty("Env");

		// --Order Creation
		OrderCreation OC = new OrderCreation();
		OC.orderCreation(1);

		// --OrderCreation Pass

		// --Scroll to get Rate
		jse.executeScript("window.scrollBy(0,400)", "");
		String cost = isElementPresent("TLActualRate_id").getText();
		setData("OrderCreation", 1, 31, cost);
		logger.info("Scroll down to Get the Rate");

		// --Moved to Job Status
		WebElement idJob = isElementPresent("TLJobStatusTab_id");
		wait.until(ExpectedConditions.elementToBeClickable(idJob));
		jse.executeScript("arguments[0].click();", idJob);
		logger.info("Clicked on Job Status Tab");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// TC Acknowledge
		TCAcknowledge TCAck = new TCAcknowledge();
		TCAck.tcAcknowledge();

		// Pickup Alert
		ReadyForDispatch RFD = new ReadyForDispatch();
		RFD.pickupAlert();

		// --Refresh App
		OC.refreshApp();

		// --NetAgent Tab
		OC.naTab();

		locProcessing();

		// --COnnect Tab
		OC.connectTab();

		Thread.sleep(2000);


		// Connectlogin();

		VerifyCustomerBill VFCB = new VerifyCustomerBill();
		VFCB.verifyCustomerBill(1);
		
		
		if (Env.equalsIgnoreCase("PROD")) {

		
		
		// -- cancel job
		cancel_job cb = new cancel_job();
		cb.job_cancel(1);
				
		}
		
		else {
			
			logger.info("Current Enviornment is not Production , so job cancellation is not handled");
		}
		
		
		//-- navigae to NA tab
		
		//OC.naTab();

	}

	public void locProcessing() throws Exception {

		// --Order Creation
		OrderCreation OC = new OrderCreation();

		String PUID = getData("OrderCreation", 1, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		OC.opFromNetAgent(1);

		NAConfirmPUAlert CPU = new NAConfirmPUAlert();
		CPU.naconfirmPUAlert();

		OC.NATaskSearch(PUID);

		NAPickup Pup = new NAPickup();
		Pup.naconfirmPickup();

		OC.NATaskSearch(PUID);

		NADeliver Del = new NADeliver();
		Del.naconfirmDelivery();

		OC.NATaskSearch(PUID);

		narefreshApp();

	}
}
