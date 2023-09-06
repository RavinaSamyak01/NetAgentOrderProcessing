package connect_NAOrderProcessSPL;

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
import connect_OCBaseMethods.NAPickup;
import connect_OCBaseMethods.NATenderTo3P;
import connect_OCBaseMethods.OrderCreation;
import connect_OCBaseMethods.ReadyForDispatch;
import connect_OCBaseMethods.SendPull;
import connect_OCBaseMethods.TCAcknowledge;
import connect_OCBaseMethods.VerifyCustomerBill;
import netAgent_BasePackage.BaseInit;

public class D3P extends BaseInit {
	@Test
	public void d3P() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		// Actions act = new Actions(Driver);

		// --Order Creation
		OrderCreation OC = new OrderCreation();
		OC.orderCreation(11);

		// --Get ServiceID //
		String ServiceID = OC.getServiceID();

		// --Scroll to get Rate //
		jse.executeScript("window.scrollBy(0,400)", "");
		String cost = isElementPresent("TLActualRate_id").getText();
		setData("OrderCreation", 11, 31, cost); //
		logger.info("Scroll down to Get the Rate");

		// --Error Pop Up //
		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@class=\"ngdialog-content ui-draggable\"]"))); //
			getScreenshot(Driver, "ErrorPopUp_" + ServiceID); //
			WebElement ErrorPUp = isElementPresent("EOErrorPUp_id"); //
			wait.until(ExpectedConditions.elementToBeClickable(ErrorPUp)); //
			jse.executeScript("arguments[0].click();", ErrorPUp); //
			logger.info("Clicked on OK button of Error Message"); //
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (

		Exception eError) {
			System.out.println("error pop up is not displayed");
		}
		// --Moved to Job Status //
		WebElement idJob = isElementPresent("TLJobStatusTab_id"); //
		wait.until(ExpectedConditions.elementToBeClickable(idJob)); //
		jse.executeScript("arguments[0].click();", idJob); //
		logger.info("Clicked on Job Status Tab"); //
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// TC Acknowledge //
		TCAcknowledge TCAck = new TCAcknowledge();
		TCAck.tcAcknowledge();

		// Send Pull ALert //
		SendPull Sp = new SendPull();
		Sp.sendPull();

		// Pickup Alert
		ReadyForDispatch RFD = new ReadyForDispatch();
		RFD.pickupAlert();

		// --Refresh App
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		OC.refreshApp();

		

	}

	public void d3pProcessing() throws Exception {
		// --Order Creation
		OrderCreation OC = new OrderCreation();

		String PUID = getData("OrderCreation", 11, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		OC.opFromNetAgent(11);

		NAConfirmPUAlert CPU = new NAConfirmPUAlert();
		CPU.naconfirmPUAlert();

		OC.NATaskSearch(PUID);

		NAConfirmPullAlert CpullALert = new NAConfirmPullAlert();
		CpullALert.naConfirmPullAlertstage();

		OC.NATaskSearch(PUID);

		NAConfirmPULLstg ConfPull = new NAConfirmPULLstg();
		ConfPull.naConfirmPullstage(11);

		OC.NATaskSearch(PUID);

		NAPickup Pup = new NAPickup();
		Pup.naconfirmPickup();

		OC.NATaskSearch(PUID);

		NATenderTo3P T3p = new NATenderTo3P();
		T3p.natndrTo3P();

		OC.NATaskSearch(PUID);
		
		OC.narefreshApp();

	}
}
