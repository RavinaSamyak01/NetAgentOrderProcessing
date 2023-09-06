package Auto_verify_30018;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import connect_OCBaseMethods.NAConfirmPUAlert;
import connect_OCBaseMethods.NADeliver;
import connect_OCBaseMethods.NAPickup;
import connect_OCBaseMethods.OrderCreation;
import connect_OCBaseMethods.ReadyForDispatch;
import connect_OCBaseMethods.TCAcknowledge;
import netAgent_BasePackage.BaseInit;

public class Auto_Verify extends BaseInit {
	method_30018 mth = new method_30018();

	@Test
	public void create_loc_verify_status() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		OrderCreation OC = new OrderCreation();

		String connect_STG_link = storage.getProperty("connect_STGURL");

		String new_tab_link = "window.open('" + connect_STG_link + "','_blank');";

		((JavascriptExecutor) Driver).executeScript(new_tab_link);

		// Get handles of the windows , move focus on connect portal

		String mainWindowHandle = Driver.getWindowHandle();
		Set<String> allWindowHandles = Driver.getWindowHandles();
		Iterator<String> iterator = allWindowHandles.iterator();
		{

			// Here we will check if child window has other child windows and will fetch the
			// heading of the child window
			try {
				while (iterator.hasNext()) {

					String ChildWindow = iterator.next();
					if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {

						// -- focucs is now on connect portal
						Driver.switchTo().window(ChildWindow);

						logger.info("Focus is on Connect portal");

						Connectlogin();
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						// -- Checking for Outbound order ============
						logger.info("Order creation in Connect : Start");
						try {

						// --Order Creation
						
						
						mth.orderCreation(14);

						// --Moved to Job Status
						WebElement idJob = isElementPresent("TLJobStatusTab_id");
						wait.until(ExpectedConditions.elementToBeClickable(idJob));
						jse.executeScript("arguments[0].click();", idJob);
						logger.info("Clicked on Job Status Tab");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// TC Acknowledge
					
						mth.tcAcknowledge();

						// Pickup Alert
						ReadyForDispatch RFD = new ReadyForDispatch();
						mth.pickupAlert();

						// --Refresh App
						OC.refreshApp();
					}
						catch (Exception e) {
							// TODO: handle exception
							Driver.close();
							Driver.switchTo().window(mainWindowHandle);
							logger.info("Issue in order processing");
						}
					

						Driver.switchTo().window(mainWindowHandle);
						Thread.sleep(1000);

						locProcessing();

						msg.append("**Verify from connect**" + "\n");

						Driver.switchTo().window(ChildWindow);

						logger.info("Focus is on Connect portal");

						verified_status(14);

						String job_status = OC.getStageName();
						logger.info("Job Status in Connect after Processing from Net Agent : " + job_status);
						
//						if() {
//							
////							setData("Test_Case", , 2, "PASS");
//							Driver.close();
//							Driver.switchTo().window(mainWindowHandle);
//						}

					}
				}
			} catch (Exception e) {
// TODO: handle exception
			}
		}

	}

	public void locProcessing() throws Exception {

		// --Order Creation
//		OrderCreation OC = new OrderCreation();

		String PUID = getData("OrderCreation", 1, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		mth.opFromNetAgent(14);

//		NAConfirmPUAlert CPU = new NAConfirmPUAlert();
		mth.naconfirmPUAlert();

		mth.NATaskSearch(PUID);

//		NAPickup Pup = new NAPickup();
		mth.naconfirmPickup();

		mth.NATaskSearch(PUID);

//		NADeliver Del = new NADeliver();
		mth.naconfirmDelivery();

		mth.NATaskSearch(PUID);

		

	}

	// -- verify JOb status should be Verified

	public void verified_status(int i) throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);//

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Search the Job
		OrderCreation OC = new OrderCreation();
		OC.searchJob(i);

		String svc = Driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);

		try {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[@id=\"lblStages\"][contains(text(),'Verify Customer Bill')]")));

			} catch (Exception eStage) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[@id=\"lblStages\"][contains(text(),'VFY CUST BILL')]")));

			}

			// --Set FAIL in TestScenarios

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 11, 5, "PASS");

			}

			// --Get StageName
			OC.getStageName();

		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "VerifyCustBill" + svc);
			System.out.println("Verify Customer Bill Not Exist in Flow!!");
			logger.info("Verify Customer Bill Not Exist in Flow!!");
			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 11, 5, "FAIL");

			}
		}

	}

}
