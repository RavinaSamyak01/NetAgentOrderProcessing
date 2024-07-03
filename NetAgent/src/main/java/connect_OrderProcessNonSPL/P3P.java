package connect_OrderProcessNonSPL;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import connect_OCBaseMethods.CSRAcknowledge;
import connect_OCBaseMethods.NAConfirmPUAlert;
import connect_OCBaseMethods.NADrop;
import connect_OCBaseMethods.NAPickup;
import connect_OCBaseMethods.OrderCreation;
import connect_OCBaseMethods.ReadyForDispatch;
import connect_OCBaseMethods.TCAcknowledge;
import connect_OCBaseMethods.VerifyCustomerBill;

public class P3P extends OrderCreation {

	@Test
	public void p3pservice() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time

		// --Order Creation
		OrderCreation OC = new OrderCreation();

		// OC.orderCreation(3);

		OC.searchJob(3);

		// --Scroll to get Rate
		jse.executeScript("window.scrollBy(0,400)", "");
		String cost = isElementPresent("TLActualRate_id").getText();
		setData("OrderCreation", 3, 31, cost);
		logger.info("Scroll down to Get the Rate");

		// --Moved to Job Status
		WebElement idJob = isElementPresent("TLJobStatusTab_id");
		wait.until(ExpectedConditions.elementToBeClickable(idJob));
		jse.executeScript("arguments[0].click();", idJob);
		logger.info("Clicked on Job Status Tab");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// CSR Acknowledge
		CSRAcknowledge CAck = new CSRAcknowledge();
		CAck.csrAcknowledge();

		// TC Acknowledge
		TCAcknowledge TCAck = new TCAcknowledge();
		TCAck.tcAcknowledge();

		// Pickup Alert
		ReadyForDispatch RFD = new ReadyForDispatch();
		RFD.pickupAlert();

		// --Refresh App
		refreshApp();

		// --NetAgent Tab
		naTab();

		p3pProcessing();

		// --COnnect Tab
		connectTab();

		Thread.sleep(2000);

		// OC.searchJob(3);

		// -- 3P Deliver
		OC.stage3Pdeliver(3);

		// Verify Customer Bill
		VerifyCustomerBill VCB = new VerifyCustomerBill();
		VCB.verifyCustomerBill(3);

		// --Refresh App
		refreshApp();

	}

	public void p3pProcessing() throws Exception {

		// --Order Creation
		OrderCreation OC = new OrderCreation();

		String PUID = getData("OrderCreation", 3, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		OC.opFromNetAgent(3);

		NAConfirmPUAlert CPU = new NAConfirmPUAlert();
		CPU.naconfirmPUAlert();

		OC.NATaskSearch(PUID);

		NAPickup Pup = new NAPickup();
		Pup.naconfirmPickup();

		OC.NATaskSearch(PUID);

		NADrop drp = new NADrop();
		drp.nadropAtOrigin();

		OC.NATaskSearch(PUID);

		narefreshApp();
	}

}
