package connect_NAOrderProcessNonSPL;

import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
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
import connect_OCBaseMethods.VerifyCustomerBill;
import connect_OCBaseMethods.WaitForArrival;
import connect_OCBaseMethods.WaitForDeptarture;
import connect_OCBaseMethods.XerWaitForArrival;
import connect_OCBaseMethods.XerWaitForDeparture;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SD extends OrderCreation {
	@Test
	public void sdSameDay() throws Exception {

		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 50);// wait time
		Actions act = new Actions(Driver);

		// --Order Creation
		OrderCreation OC = new OrderCreation();

		OC.orderCreation(2);

		// --Unknown Shipper
		OC.unknowShipper(2);

		// --Select Flight
		OC.selectFlight(2);

		// --Add AirBill
		OC.addAirBill(2);

		// TC Acknowledge
		TCAcknowledge TCAck = new TCAcknowledge();
		TCAck.tcAcknowledge();

		// Pickup Alert
		ReadyForDispatch RFD = new ReadyForDispatch();
		RFD.pickupAlert();

		// --Refresh App
		refreshApp();

		

	}
	public void sdProcessing() throws Exception {

		// --Order Creation
		OrderCreation OC = new OrderCreation();

		String PUID = getData("OrderCreation", 2, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		OC.opFromNetAgent(2);

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

	public void sdConfDelstages() throws Exception {
		// --Order Creation
		OrderCreation OC = new OrderCreation();

		String PUID = getData("OrderCreation", 2, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		OC.opFromNetAgent(2);

		ConfirmDelAlert CDA = new ConfirmDelAlert();
		CDA.confDelAlert();

		narefreshApp();
	}
	

}
