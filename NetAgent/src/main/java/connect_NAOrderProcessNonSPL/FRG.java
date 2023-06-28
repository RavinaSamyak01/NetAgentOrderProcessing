package connect_NAOrderProcessNonSPL;

import java.util.ArrayList;

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
import connect_OCBaseMethods.VerifyMargin;

public class FRG extends OrderCreation {

	@Test
	public void frgFreight() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time

		// --Order Creation
		OrderCreation OC = new OrderCreation();
		OC.orderCreation(9);

		// --Move to Job Status Tab
		WebElement JoStatusTab = isElementPresent("TLJobStatusTab_id");
		wait.until(ExpectedConditions.elementToBeClickable(JoStatusTab));
		JoStatusTab.click();
		logger.info("Clicked on Job Status Tab");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// TC Acknowledge
		TCAcknowledge TCAck = new TCAcknowledge();
		TCAck.tcAcknowledge();

		// Pickup Alert
		ReadyForDispatch RFD = new ReadyForDispatch();
		RFD.pickupAlert();

		// --Refresh App
		refreshApp();

		

	}
	public void frgProcessing() throws Exception {

		// --Order Creation
		OrderCreation OC = new OrderCreation();

		String PUID = getData("OrderCreation", 9, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		OC.opFromNetAgent(9);

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
