package connect_OrderProcessNonSPL;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import connect_OCBaseMethods.Board;
import connect_OCBaseMethods.Deliver;
import connect_OCBaseMethods.HAANotify;
import connect_OCBaseMethods.NAConfirmPUAlert;
import connect_OCBaseMethods.NADeliver;
import connect_OCBaseMethods.NADrop;
import connect_OCBaseMethods.NAPickup;
import connect_OCBaseMethods.OnHandAtDestination;
import connect_OCBaseMethods.OrderCreation;
import connect_OCBaseMethods.ReadyForDispatch;
import connect_OCBaseMethods.TCAcknowledge;
import connect_OCBaseMethods.VerifyMargin;
import connect_OCBaseMethods.WaitForArrival;
import connect_OCBaseMethods.WaitForDeptarture;
import connect_OCBaseMethods.XerWaitForArrival;
import connect_OCBaseMethods.XerWaitForDeparture;

public class AIR extends OrderCreation {

	@Test
	public void airService() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time

		// --Order Creation
		OrderCreation OC = new OrderCreation();
		OC.orderCreation(6);

		// --Moved to Job Status
		WebElement idJob = isElementPresent("TLJobStatusTab_id");
		wait.until(ExpectedConditions.elementToBeClickable(idJob));
		jse.executeScript("arguments[0].click();", idJob);
		logger.info("Clicked on Job Status Tab");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Add AirBill
		OC.addAirBill(6);

		// TC Acknowledge
		TCAcknowledge TCAck = new TCAcknowledge();
		TCAck.tcAcknowledge();

		// Pickup Alert
		ReadyForDispatch RFD = new ReadyForDispatch();
		RFD.pickupAlert();

		// switches to new tab
		naTab();

		airProcessing();

		connectTab();
		// Switch back to original browser (first window)logger.info("Moved to main
		// window");
		Thread.sleep(2000);


		OC.searchJob(6);

		// HAA Notify
		HAANotify HAAN = new HAANotify();
		HAAN.haaNtfy();

		// Wait for Departure
		WaitForDeptarture WFD = new WaitForDeptarture();
		WFD.waitForDept();

		// OnBorad
				Board Brd = new Board();
				Brd.onBoard();
		
		// XER wait for Arrival
		XerWaitForArrival XWFA = new XerWaitForArrival();
		XWFA.xerWaitForArr();

		// XER Wait for Departure
		XerWaitForDeparture XWFD = new XerWaitForDeparture();
		XWFD.xerWaitForDept();

		// Wait for Arrival
		WaitForArrival WFA = new WaitForArrival();
		WFA.waitForArr();

		// ONHAND @ DESTINATION
		OnHandAtDestination OHAD = new OnHandAtDestination();
		OHAD.onHandDst();

		// OnBorad
		Board Brd1 = new Board();
		Brd1.onBoard();

		// XER wait forArrival
		XWFA.xerWaitForArr();

		// XER Wait for Departure
		XWFD.xerWaitForDept();

		// OnBorad
		Brd.onBoard();

		// Wait for Arrival
		WFA.waitForArr();

		// ONHAND @ DESTINATION
		OHAD.onHandDst();

		// DELIVERED
		Deliver Del = new Deliver();
		Del.confirmDelivery();

		// Verify Margin
		VerifyMargin VM = new VerifyMargin();
		VM.verifyMargin(6);

		// -Refresh App
		OC.refreshApp();

	}

	public void airProcessing() throws Exception {
		// --Order Creation
		OrderCreation OC = new OrderCreation();

		String PUID = getData("OrderCreation", 6, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		OC.opFromNetAgent(6);

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
