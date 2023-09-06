package Auto_verify_30018;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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

public class ASN_verify_connect extends BaseInit {
	method_30018 mth = new method_30018();

	@Test
	public void create_loc_verify_status() throws Exception {
		
		msg.append("===== ASN Auto Verify Testcase  : Start =====" + "\n");
		logger.info("===== ASN Auto Verify Testcase  : Start =====");
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		WebDriverWait wait2 = new WebDriverWait(Driver, 90);
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
						
						
						mth.ASN_create(1);



						// --Refresh App
						OC.refreshApp();
						wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					}
						catch (Exception e) {
							// TODO: handle exception
							logger.info("Issue in ASN Creation processing");
						}
					
					}
				}
			} catch (Exception e) {
// TODO: handle exception

				logger.info("Error in Order creation from connect : " + e);
				getScreenshot(Driver, "Order_issue_AV");
				Driver.switchTo().window(mainWindowHandle);
			}
		}
		msg.append("===== Net Agent Auto Verify Testcase  : End =====" + "\n");
		logger.info("===== Net Agent Auto Verify Testcase  : End =====");
	}

	public void locProcessing() throws Exception {

		// --Order Creation
//		OrderCreation OC = new OrderCreation();

		String PUID = getData("Auto_verify_30018", 1, 4);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		mth.opFromNetAgent(1);
		
		mth.naconfirmPUAlert();

		mth.NATaskSearch(PUID);

		mth.naconfirmPickup();

		mth.NATaskSearch(PUID);

		mth.naconfirmDelivery();

		mth.NATaskSearch_after_delivery(PUID);

		

	}

	// -- verify JOb status should be Verified

	public void verified_status(int i) throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);//	

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Search the Job
		OrderCreation OC = new OrderCreation();
		mth.searchJob(i);

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
				setData("Auto_verify_30018", 9, 3, "PASS");

			}

			// --Get StageName
			OC.getStageName();

		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "VerifyCustBill" + svc);
			System.out.println("Verify Customer Bill Not Exist in Flow!!");
			logger.info("Verify Customer Bill Not Exist in Flow!!");
			if (svc.equals("LOC")) {
				setData("Auto_verify_30018", 9, 3, "FAIL");

			}
		}

	}

}
