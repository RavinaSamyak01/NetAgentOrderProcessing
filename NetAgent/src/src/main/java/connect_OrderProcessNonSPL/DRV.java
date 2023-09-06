package connect_OrderProcessNonSPL;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import connect_OCBaseMethods.HoldAtOrigin;
import connect_OCBaseMethods.NAConfReAlertDelivery;
import connect_OCBaseMethods.NAConfirmPUAlert;
import connect_OCBaseMethods.NADeliver;
import connect_OCBaseMethods.NAPickup;
import connect_OCBaseMethods.OrderCreation;
import connect_OCBaseMethods.OutForDelivery;
import connect_OCBaseMethods.ReAlertForDelivery;
import connect_OCBaseMethods.ReadyForDispatch;
import connect_OCBaseMethods.TCAcknowledge;
import connect_OCBaseMethods.VerifyMargin;

public class DRV extends OrderCreation {

	@Test
	public void drvDriver() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		Actions act = new Actions(Driver);

		// --Order Creation
		OrderCreation OC = new OrderCreation();
		OC.orderCreation(5);

		// --Scroll to HAS
		WebElement HAS = isElementPresent("DRVHAS_xpath");
		jse.executeScript("arguments[0].scrollIntoView();", HAS);
		Thread.sleep(2000);

		// --Select HAS
		wait.until(ExpectedConditions.elementToBeClickable(HAS));
		act.moveToElement(HAS).click().build().perform();
		logger.info("Select HAS from Package Information");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Click on Save Changes
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
		isElementPresent("TLSaveChanges_id").click();
		logger.info("Clicked on Save Changes button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

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

		// --NetAgent Tab
		naTab();

		drvProcessing();

		// --COnnect Tab
		connectTab();

		Thread.sleep(2000);


		OC.searchJob(5);

		// HOLD @ ORIGIN
		HoldAtOrigin HAO = new HoldAtOrigin();
		HAO.hldAtOrigin();

		// RE-ALERT FOR DELIVERY
		ReAlertForDelivery RAFD = new ReAlertForDelivery();
		RAFD.reAlertfordel();

		// --Refresh App
		refreshApp();

		// --NetAgent Tab
		naTab();

		drvConfDelProcessing();

		// --Connect Tab
		connectTab();

		Thread.sleep(2000);


		OC.searchJob(5);

		// OUT FOR DELIVERY
		OutForDelivery OFD = new OutForDelivery();
		OFD.outForDel();

		// --Refresh App
		refreshApp();

		// --NetAgent Tab
		naTab();

		drvDelProcessing();

		// --Connect Tab
		connectTab();

		Thread.sleep(2000);


		OC.searchJob(5);

		// Verify Margin
		VerifyMargin VM = new VerifyMargin();
		VM.verifyMargin(5);

		// --Refresh App 
		refreshApp();

	}

	public void drvProcessing() throws Exception {

		// --Order Creation
		OrderCreation OC = new OrderCreation();

		String PUID = getData("OrderCreation", 5, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		OC.opFromNetAgent(5);

		NAConfirmPUAlert CPU = new NAConfirmPUAlert();
		CPU.naconfirmPUAlert();

		OC.NATaskSearch(PUID);

		NAPickup Pup = new NAPickup();
		Pup.naconfirmPickup();

		OC.NATaskSearch(PUID);

		narefreshApp();
	}

	public void drvConfDelProcessing() throws Exception {

		// --Order Creation
		OrderCreation OC = new OrderCreation();

		String PUID = getData("OrderCreation", 5, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		OC.opFromNetAgent(5);

		NAConfReAlertDelivery CRD = new NAConfReAlertDelivery();
		CRD.naConfReAlertDelivery();

		OC.NATaskSearch(PUID);

		narefreshApp();

	}

	public void drvDelProcessing() throws Exception {

		// --Order Creation
		OrderCreation OC = new OrderCreation();

		String PUID = getData("OrderCreation", 5, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		OC.opFromNetAgent(5);

		NADeliver CRD = new NADeliver();
		CRD.naconfirmDelivery();

		OC.NATaskSearch(PUID);

		narefreshApp();

	}

}
