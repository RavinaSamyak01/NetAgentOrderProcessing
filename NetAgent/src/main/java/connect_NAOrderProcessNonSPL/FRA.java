package connect_NAOrderProcessNonSPL;

import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import connect_OCBaseMethods.Board;
import connect_OCBaseMethods.Board1;
import connect_OCBaseMethods.ConfirmDelAlert;
import connect_OCBaseMethods.Deliver;
import connect_OCBaseMethods.NAConfirmPUAlert;
import connect_OCBaseMethods.NADrop;
import connect_OCBaseMethods.NAPickup;
import connect_OCBaseMethods.OrderCreation;

import connect_OCBaseMethods.ReadyForDispatch;
import connect_OCBaseMethods.Recover;
import connect_OCBaseMethods.SendDelAlert;
import connect_OCBaseMethods.TCAcknowledge;
import connect_OCBaseMethods.VerifyMargin;
import connect_OCBaseMethods.WaitForArrival;
import connect_OCBaseMethods.WaitForDeptarture;
import connect_OCBaseMethods.XerWaitForArrival;
import connect_OCBaseMethods.XerWaitForDeparture;

public class FRA extends OrderCreation {

	@Test
	public void fraFreight() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;
		// WebDriverWait wait = new WebDriverWait(Driver, 50);// wait time

		// --Order Creation
		OrderCreation OC = new OrderCreation();
		OC.orderCreation(8);

		// --Select Flight
		OC.selectFlight(8);

		// --Add AirBill
		OC.addAirBill(8);

		// TC Acknowledge
		TCAcknowledge TCAck = new TCAcknowledge();
		TCAck.tcAcknowledge();

		// Pickup Alert
		ReadyForDispatch RFD = new ReadyForDispatch();
		RFD.pickupAlert();

		// --Refresh App
		refreshApp();

		

	}
	public void fRAProcessing() throws Exception {

		// --Order Creation
		OrderCreation OC = new OrderCreation();

		String PUID = getData("OrderCreation", 8, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		OC.opFromNetAgent(8);

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

	public void fraConfDelstages() throws Exception {
		// --Order Creation
		OrderCreation OC = new OrderCreation();

		String PUID = getData("OrderCreation", 8, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		OC.opFromNetAgent(8);

		ConfirmDelAlert CDA = new ConfirmDelAlert();
		CDA.confDelAlert();

		narefreshApp();
	}
	

}
