package connect_OrderProcessSPL;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import connect_OCBaseMethods.ConfirmDelAlert;
import connect_OCBaseMethods.Deliver;
import connect_OCBaseMethods.NAConfirmPULLstg;
import connect_OCBaseMethods.NAConfirmPullAlert;
import connect_OCBaseMethods.NADeliver;
import connect_OCBaseMethods.NARecover;
import connect_OCBaseMethods.NATenderTo3P;
import connect_OCBaseMethods.OrderCreation;
import connect_OCBaseMethods.Recover;
import connect_OCBaseMethods.SendDelAlert;
import connect_OCBaseMethods.SendPull;
import connect_OCBaseMethods.T3rdPartyDelivery;
import connect_OCBaseMethods.TCAcknowledge;
import connect_OCBaseMethods.TenderTo3P;
import connect_OCBaseMethods.VerifyCustomerBill;
import netAgent_BasePackage.BaseInit;

public class T3PLAST extends BaseInit {
	@Test
	public void t3PLAST() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		// Actions act = new Actions(Driver);

		// --Order Creation
		OrderCreation OC = new OrderCreation();

		OC.orderCreation(12);

		// --Get ServiceID
		String ServiceID = OC.getServiceID();

		// --Scroll to get Rate
		jse.executeScript("window.scrollBy(0,400)", "");
		String cost = isElementPresent("TLActualRate_id").getText();
		setData("OrderCreation", 12, 31, cost);
		logger.info("Scroll down to Get the Rate");

		// --Error Pop Up
		try {
			wait.until(ExpectedConditions
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

		OC.naTab();

		t3PlastProcessing();

		// --COnnect Tab
		OC.connectTab();


		OC.searchJob(12);

		SendDelAlert SDA = new SendDelAlert();
		SDA.delAlert();

		OC.naTab();

		t3PlastConfDelstages();

		// --COnnect Tab
		OC.connectTab();


		OC.searchJob(12);

		// Tender to 3P
		TenderTo3P t3p = new TenderTo3P();
		t3p.tndrTo3P();

		OC.naTab();

		t3PlastRecoverstages();

		// --COnnect Tab
		OC.connectTab();


		OC.searchJob(12);

		// Verify Customer Bill
		VerifyCustomerBill VCB = new VerifyCustomerBill();
		VCB.verifyCustomerBill(12);

		// --Refresh App
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		OC.refreshApp();

		msg.append("\n\n\n");
	}

	public void t3PlastProcessing() throws Exception {
		// --Order Creation
		OrderCreation OC = new OrderCreation();

		String PUID = getData("OrderCreation", 12, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		OC.opFromNetAgent(12);

		OC.NATaskSearch(PUID);

		NAConfirmPullAlert CpullALert = new NAConfirmPullAlert();
		CpullALert.naConfirmPullAlertstage();

		OC.NATaskSearch(PUID);

		NAConfirmPULLstg ConfPull = new NAConfirmPULLstg();
		ConfPull.naConfirmPullstage(12);

		OC.NATaskSearch(PUID);

		NATenderTo3P T3p = new NATenderTo3P();
		T3p.natndrTo3P();

		OC.NATaskSearch(PUID);

		OC.narefreshApp();
	}

	public void t3PlastConfDelstages() throws Exception {
		// --Order Creation
		OrderCreation OC = new OrderCreation();

		String PUID = getData("OrderCreation", 12, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		OC.opFromNetAgent(12);

		ConfirmDelAlert CDA = new ConfirmDelAlert();
		CDA.confDelAlert();

		OC.narefreshApp();
	}

	public void t3PlastRecoverstages() throws Exception {
		// --Order Creation
		OrderCreation OC = new OrderCreation();

		String PUID = getData("OrderCreation", 12, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		OC.opFromNetAgent(12);

		NARecover nRec = new NARecover();
		nRec.narecoverAtDestination();

		OC.NATaskSearch(PUID);

		NADeliver nDel = new NADeliver();
		nDel.naconfirmDelivery();

		OC.narefreshApp();
	}

}
