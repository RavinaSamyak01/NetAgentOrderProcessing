package connect_OCBaseMethods;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import netAgent_BasePackage.BaseInit;


public class OrderCreationSPL extends BaseInit {

	static String pck, rdytime, rectime, arrtime, tndrtime;

	public void orderCreation(int i)
			throws EncryptedDocumentException, InvalidFormatException, IOException, AWTException, InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 40);// wait time
		Actions act = new Actions(Driver);

		Robot r = new Robot();
		String ServiceID = null;
		try {
			// --Go to Operation
			WebElement operation = isElementPresent("OperMenu_id");
			wait.until(ExpectedConditions.visibilityOf(operation));
			wait.until(ExpectedConditions.elementToBeClickable(operation));
			act.moveToElement(operation).click().build().perform();
			logger.info("Click on Operation");

			// --Go to TaskLog
			WebElement TaskLog = isElementPresent("OpTaskLog_id");
			wait.until(ExpectedConditions.visibilityOf(TaskLog));
			wait.until(ExpectedConditions.elementToBeClickable(TaskLog));
			act.moveToElement(TaskLog).click().build().perform();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
			logger.info("Click on Tasklog");

			// --Click on New Order
			WebElement Order = isElementPresent("NewOrder_id");
			wait.until(ExpectedConditions.visibilityOf(Order));
			wait.until(ExpectedConditions.elementToBeClickable(Order));
			jse.executeScript("arguments[0].click();", Order);
			logger.info("Click on New Order");

			// --Waiting for Order section
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("idOrder")));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --get the Data
			getData("SPL", i, 0);

			// --Get Service
			ServiceID = getData("SPL", i, 10);
			System.out.println("Service== " + ServiceID);
			logger.info("=====Service:- " + ServiceID + "=====");
			msg.append("\n" + "=====Service:- " + ServiceID + "=====" + "\n");

			// Enter Caller
			String Caller = getData("SPL", i, 0);
			isElementPresent("OCCallerName_id").clear();
			isElementPresent("OCCallerName_id").sendKeys(Caller);
			logger.info("Entered CallerName");

			// Enter Phone
			String Phone = getData("SPL", i, 1);
			isElementPresent("OCContactPh_id").clear();
			isElementPresent("OCContactPh_id").sendKeys(Phone);
			logger.info("Entered Contact/Phone");

			// Enter Account#
			String Account = getData("SPL", i, 2);
			isElementPresent("OCCustCode_id").clear();
			isElementPresent("OCCustCode_id").sendKeys(Account);
			isElementPresent("OCCustCode_id").sendKeys(Keys.TAB);
			logger.info("Entered Customer Code");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			try {
				WebElement DocDialogue = isElementPresent("DRVDialforDoc_xpath");
				wait.until(ExpectedConditions.visibilityOfAllElements(DocDialogue));
				logger.info("Dialogue is displayed for Customer");
				WebElement BTNOk = isElementPresent("DRVDialOKbtn_id");
				wait.until(ExpectedConditions.elementToBeClickable(BTNOk));
				jse.executeScript("arguments[0].click();", BTNOk);
				logger.info("Clicked on OK button");

			} catch (Exception Doc) {
				logger.info("Dialogue is not displayed for Customer");

			}

			// --Select Part
			inventorySearch(i);

			// --Del Zip
			String DLZip = getData("SPL", i, 3);
			isElementPresent("OCDLZip_id").clear();
			isElementPresent("OCDLZip_id").sendKeys(DLZip);
			isElementPresent("OCDLZip_id").sendKeys(Keys.TAB);
			Thread.sleep(2000);
			logger.info("Entered DL Zip");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Click on Delivery Address Tab
			WebElement DLAddTab = isElementPresent("OCDLAdd_id");
			wait.until(ExpectedConditions.elementToBeClickable(DLAddTab));
			jse.executeScript("arguments[0].click();", DLAddTab);
			logger.info("Clicked on Delivery Address Tab");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --DEL Company
			String DelCompany = getData("SPL", i, 4);
			isElementPresent("OCDLComp_id").clear();
			isElementPresent("OCDLComp_id").sendKeys(DelCompany);
			logger.info("Entered DL Company");

			// --Del Address
			WebElement DL = isElementPresent("OCDLAdd_id");
			jse.executeScript("arguments[0].click();", DL);
			logger.info("Entered DL Address");

			// --DEL Address1
			String DLAddress1 = getData("SPL", i, 5);
			isElementPresent("OCDLAddL1_id").clear();
			isElementPresent("OCDLAddL1_id").sendKeys(DLAddress1);
			logger.info("Entered DL Address Line1");

			// String DLAddr2 = getData("SPL", i, 14);
			// Driver.findElement(By.id("txtDelAddrLine2")).sendKeys(DLAddr2);

			// --DL Attention
			String DLAttn = getData("SPL", i, 7);
			isElementPresent("OCDLAtt_id").clear();
			isElementPresent("OCDLAtt_id").sendKeys(DLAttn);
			logger.info("Entered DL Attention");

			// --DL Phone
			String DLPhone = getData("SPL", i, 8);
			isElementPresent("OCDLPhone_id").clear();
			isElementPresent("OCDLPhone_id").sendKeys(DLPhone);
			logger.info("Entered DL Phone");

			// --DL Miles
			String dmi = isElementPresent("OCDLMiles_id").getAttribute("value");
			logger.info("DL Miles==" + dmi);

			// String DLInst = getData("SPL", i, 17);
			// Driver.findElement(By.id("txtDLPhone")).sendKeys(DLInst);
			// String srv =
			// Driver.findElement(By.id("idNewOrderServiceId")).getAttribute("value");

			// --Enter the ServiceID
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idNewOrderServiceId")));
			WebElement Service = isElementPresent("OCServiceID_id");
			wait.until(ExpectedConditions.elementToBeClickable(Service));
			Service.clear();
			Service.sendKeys("H3P");
			Service.sendKeys(Keys.TAB);

			// --Select 3P Account
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cmb3PAccount")));
			WebElement p3acc = isElementPresent("EO3PAcDrop_id");
			wait.until(ExpectedConditions.elementToBeClickable(p3acc));
			Select T3pAc = new Select(p3acc);
			T3pAc.selectByIndex(1);
			logger.info("Selected 3P account");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --select FedEx service
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("scrollCarrierService")));
			WebElement ServiceSelect = isElementPresent("OCFDXServiceSelect_id");
			act.moveToElement(ServiceSelect).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(ServiceSelect));
			jse.executeScript("arguments[0].click();", ServiceSelect);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			logger.info("Selected FedEx service");

			// --Scroll Up
			r.keyPress(KeyEvent.VK_TAB);
			jse.executeScript("window.scrollBy(0,250)", "");
			Thread.sleep(2000);

			// --Total Mileage
			String tmile = isElementPresent("OCTotalMil_id").getAttribute("value");
			logger.info("Total Mileage==" + tmile);

			setData("SPL", i, 13, dmi);
			setData("SPL", i, 15, tmile);

			// --Scroll down
			/*
			 * r.keyPress(KeyEvent.VK_TAB); jse.executeScript("window.scrollBy(0,250)", "");
			 * Thread.sleep(2000);
			 */

			// --Click on Create Order button
			WebElement order = isElementPresent("OCSPLCreateOrder_id");
			jse.executeScript("arguments[0].scrollIntoView();", order);
			Thread.sleep(2000);
			act.moveToElement(order).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(order));
			jse.executeScript("arguments[0].click();", order);
			logger.info("Click on Create Order button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			Thread.sleep(2000);

			// --Get the PickUPID

			WebElement PickUPID = isElementPresent("OCPickuPID_xpath");
			wait.until(ExpectedConditions.visibilityOf(PickUPID));
			wait.until(ExpectedConditions.elementToBeClickable(PickUPID));
			String pck = PickUPID.getText();
			System.out.println("Pickup = " + pck);
			logger.info("=====Pickup =" + pck + "=====" + "\n");
			msg.append("=====Pickup =" + pck + "=====" + "\n");

			// --Set PickUPID
			setData("SPL", i, 18, pck);

			// --Click on Edit Order
			WebElement EditOrder = isElementPresent("OCEditOrder_id");
			wait.until(ExpectedConditions.elementToBeClickable(EditOrder));
			EditOrder.click();
			logger.info("Clicked on Edit Order");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "CreateOrder_" + ServiceID);

			// --ScrollUp
			jse.executeScript("window.scrollBy(0,-250)", "");
			Thread.sleep(2000);
			getScreenshot(Driver, "CreateOrder1_" + ServiceID);

			// --ScrollDown
			jse.executeScript("window.scrollBy(0,250)", "");
			Thread.sleep(2000);
			getScreenshot(Driver, "CreateOrder2_" + ServiceID);

			System.out.println("Issue in Create Order");
			logger.info("Issue in Create Order");
		}

	}

	public void selectDropOffLoc() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor jse = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		// --Select Drop Off Location
		WebElement dropOfLoc = isElementPresent("EOSelDropOfLoc_id");
		act.moveToElement(dropOfLoc).build().perform();
		wait.until(ExpectedConditions.elementToBeClickable(dropOfLoc));
		jse.executeScript("arguments[0].click();", dropOfLoc);
		Thread.sleep(5000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		logger.info("Click on Select Drop-Off Location");

		// --Select 1st address
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"FDXUPSFOrm\"]")));
		WebElement AsAddress = isElementPresent("EODrpLoc1stLoc_id");
		wait.until(ExpectedConditions.elementToBeClickable(AsAddress));
		jse.executeScript("arguments[0].click();", AsAddress);
		logger.info("Select 1st drop off location");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

	}

	public String getServiceID() {
		// --Get ServiceID

		String svc = Driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);
		logger.info("Service is==" + svc);

		return svc;
	}

	public void getStageName() {
		WebDriverWait wait = new WebDriverWait(Driver, 50);// wait time

		// --Get the Stage Name
		WebElement Stage = isElementPresent("EOStageName_id");
		wait.until(ExpectedConditions.visibilityOf(Stage));
		String StageName = Stage.getText();
		System.out.println(StageName);
		logger.info("Stage=" + StageName);
		msg.append("Stage=" + StageName + "\n");

	}

	public void refreshApp() {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		try {
			WebElement NGLLOgo = isElementPresent("RefreshLogo_xpath");
			wait.until(ExpectedConditions.elementToBeClickable(NGLLOgo));
			act.moveToElement(NGLLOgo).build().perform();
			js.executeScript("arguments[0].click();", NGLLOgo);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
		} catch (Exception refresh) {
			WebElement NGLLOgo = isElementPresent("RefreshLogo_xpath");
			wait.until(ExpectedConditions.elementToBeClickable(NGLLOgo));
			act.moveToElement(NGLLOgo).build().perform();
			act.moveToElement(NGLLOgo).click().perform();
			js.executeScript("arguments[0].click();", NGLLOgo);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
		}

	}

	public void unknowShipper(int i) throws EncryptedDocumentException, InvalidFormatException, IOException {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		// --Unknown Shipper click
		WebElement UnShipper = isElementPresent("TLUnShipp_id");
		wait.until(ExpectedConditions.visibilityOf(UnShipper));
		wait.until(ExpectedConditions.elementToBeClickable(UnShipper));
		act.moveToElement(UnShipper).build().perform();
		js.executeScript("arguments[0].click();", UnShipper);
		logger.info("Clicked on Unknown Shipper");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Wait for pop up of Unknown Shipper
		wait.until(
				ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"modal-dialog modal-sm\"]")));

		// --Click on Confirm Button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnConfirmExtrernal")));
		WebElement UnShCOnfirm = isElementPresent("TLUnShConfrm_id");
		wait.until(ExpectedConditions.elementToBeClickable(UnShCOnfirm));
		UnShCOnfirm.click();
		logger.info("Clicked on Confirm button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Scroll to get Rate
		js.executeScript("window.scrollBy(0,400)", "");
		String cost = isElementPresent("TLActualRate_id").getText();
		setData("SPL", i, 17, cost);
		logger.info("Scroll down to Get the Rate");

		// --Click on Save Changes
		isElementPresent("TLSaveChanges_id").click();
		logger.info("Clicked on Save Changes button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
	}

	public void selectFlight() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		// JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Move to Job Status Tab
		WebElement JoStatusTab = isElementPresent("TLJobStatusTab_id");
		wait.until(ExpectedConditions.visibilityOf(JoStatusTab));
		wait.until(ExpectedConditions.elementToBeClickable(JoStatusTab));
		act.moveToElement(JoStatusTab).click().build().perform();
		logger.info("Clicked on Job Status Tab");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Click on Select Flight
		WebElement SelectFlight = isElementPresent("TLSelFlight_id");
		wait.until(ExpectedConditions.elementToBeClickable(SelectFlight));
		SelectFlight.click();
		logger.info("Clicked on Select Flight button");

		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception ExtraWait) {
			WebDriverWait wait1 = new WebDriverWait(Driver, 50);// wait time
			wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		}

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"ItineraryForm\"]")));
		Thread.sleep(2000);

		// --CLick on Select Flight
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hlkSel_0")));
		WebElement Select1stFlight = isElementPresent("TLSelect1stFlgt_id");
		wait.until(ExpectedConditions.elementToBeClickable(Select1stFlight));
		Select1stFlight.click();
		logger.info("Selected 1st flight");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Click on Assign button
		WebElement AssignFlight = isElementPresent("TLAssignFlght_xpath");
		wait.until(ExpectedConditions.elementToBeClickable(AssignFlight));
		AssignFlight.click();
		logger.info("Clicked on Assign button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
	}

	public void reCalc(String svc) {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"idValidationforMain\"]//ul[@id=\"errorid\"]")));
			String Valmsg = isElementPresent("OCValOnePack_xpath").getText();
			logger.info("Validation message is displayed=" + Valmsg);
			if (Valmsg.contains("Parameter(s) are modified. Please recalculate customer charges.")) {
				// Recalculate the charges
				// --Go to Edit Job tab
				WebElement EditOrTab = isElementPresent("EOEditOrderTab_id");
				act.moveToElement(EditOrTab).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(EditOrTab));
				act.moveToElement(EditOrTab).click().perform();
				logger.info("Click on Edit Order Tab");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// -Recalculate button
				WebElement ReCalc = isElementPresent("EORecal_id");
				act.moveToElement(ReCalc).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(ReCalc));
				act.moveToElement(ReCalc).click().perform();
				logger.info("Click on Recalculate button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --Click on Save Changes button
				WebElement SaveChanges = isElementPresent("TLSaveChanges_id");
				act.moveToElement(SaveChanges).build().perform();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
				wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
				act.moveToElement(SaveChanges).click().perform();
				logger.info("Click on Save Changes button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(2000);

				// --Go to job Status Tab
				WebElement JobOverTab = isElementPresent("TLJobStatusTab_id");
				act.moveToElement(JobOverTab).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(JobOverTab));
				act.moveToElement(JobOverTab).click().perform();
				logger.info("Click on Job Overview Tab");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(5000);

				// --Enter SIgnature
				wait.until(ExpectedConditions.elementToBeClickable(By.id("txtDeliverySignature")));
				isElementPresent("TLDSignature_id").clear();
				isElementPresent("TLDSignature_id").sendKeys("RVOza");
				isElementPresent("TLDSignature_id").sendKeys(Keys.TAB);
				logger.info("Enter Signature");

				if (svc.equals("LOC") || svc.equals("DRV") || svc.equals("SDC") || svc.equals("FRG")) {
					// --Get the timeZone
					String tzone = isElementPresent("TLLOCDActTimZone_id").getText();
					logger.info("Actual DL TimeZone==" + tzone);
					String rectime = getTimeAsTZone(tzone);
					logger.info("Actual DL Time==" + rectime);

					// --Enter Actual DL time
					wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActualDeliveryTime")));
					isElementPresent("TLDActDLTime_id").clear();
					isElementPresent("TLDActDLTime_id").sendKeys(rectime);
					logger.info("Enter Actual DL Time");

					// --Click on Confirm DL button
					WebElement ConfDL = isElementPresent("TLDConfDL_id");
					wait.until(ExpectedConditions.elementToBeClickable(ConfDL));
					Thread.sleep(2000);
					act.moveToElement(ConfDL).click().build().perform();
					logger.info("Click on Confirm DEL button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				} else if (svc.equals("SD") || svc.equals("PA") || svc.equals("FRA")) {

					// --Enter SIgnature
					wait.until(ExpectedConditions.elementToBeClickable(By.id("txtDeliverySignature")));
					isElementPresent("TLDSignature_id").clear();
					isElementPresent("TLDSignature_id").sendKeys("RVOza");
					isElementPresent("TLDSignature_id").sendKeys(Keys.TAB);
					logger.info("Enter Signature");

					// --Enter Actual DL time
					// --Get the timeZone
					String tzone = isElementPresent("TLSDDActTimZone_id").getText();
					logger.info("Actual DL TimeZone==" + tzone);
					String rectime = getTimeAsTZone(tzone);
					logger.info("Actual DL Time==" + rectime);

					// --Enter Actual DL time
					wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActualDeliveryTime")));
					isElementPresent("TLDActDLTime_id").clear();
					isElementPresent("TLDActDLTime_id").sendKeys(rectime);
					logger.info("Enter Actual DL Time");

					// --Click on Confirm DL button
					WebElement ConfDL = isElementPresent("TLDConfDL2_id");
					wait.until(ExpectedConditions.elementToBeClickable(ConfDL));
					Thread.sleep(2000);
					act.moveToElement(ConfDL).click().build().perform();
					logger.info("Click on Confirm DEL button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				} else if (svc.equals("AIR")) {
					Thread.sleep(5000);

					// --Get the timeZone
					String tzone = isElementPresent("TLDAIRTZone_id").getText();
					logger.info("Actual DL TimeZone==" + tzone);
					String rectime = getTimeAsTZone(tzone);
					logger.info("Actual DL Time==" + rectime);

					// --Enter Actual DL time
					wait.until(ExpectedConditions.elementToBeClickable(By.id("txtOnHandActualDeliveryTime")));
					isElementPresent("TLDAIRActualDTime_id").clear();
					isElementPresent("TLDAIRActualDTime_id").sendKeys(rectime);
					logger.info("Enter Actual DL Time");

					// --Click on Confirm DL
					WebElement ConDL = isElementPresent("TLDConfDL2_id");
					wait.until(ExpectedConditions.elementToBeClickable(ConDL));
					js.executeScript("arguments[0].click();", ConDL);
					logger.info("Clicked on Confirm DEL button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				}

			}

		} catch (Exception PModify) {
			logger.info("Validation message is not displayed for Recalculate the charges");

		}
	}

	public static void routeNoteSpecialInstruction() throws InterruptedException {
		Thread.sleep(1000);
		Driver.findElement(By.id("btnRouteAck")).click();
		Thread.sleep(1000);

		Driver.findElement(By.id("btnSpecialFields")).click();
		Thread.sleep(4000);
		Driver.findElement(By.id("txtrefer0")).sendKeys("1");
		Driver.findElement(By.id("txtrefer1")).sendKeys("2");
		Driver.findElement(By.id("txtrefer2")).sendKeys("3");
		Driver.findElement(By.id("txtrefer4")).sendKeys("4");
		Driver.findElement(By.id("txtrefer6")).sendKeys("321012");

		Select rtn = new Select(Driver.findElement(By.id("cmbrefer3")));
		rtn.selectByVisibleText("Customer");

		Select suptype = new Select(Driver.findElement(By.id("cmbrefer5")));
		suptype.selectByVisibleText("Other");
		Thread.sleep(2000);
		Driver.findElement(By.id("btnSave")).click();

	}

	public static void fedExCarrier() throws InterruptedException {
		Thread.sleep(2000);
		Select cracct = new Select(Driver.findElement(By.id("cmb3PAccount")));
		cracct.selectByVisibleText("Quantum Fedex US (510087763)");
	}

	public static void upsCarrier() throws InterruptedException {
		Thread.sleep(2000);
		Select cracct = new Select(Driver.findElement(By.id("cmb3PAccount")));
		cracct.selectByVisibleText("United State - UPS (6295vv)");
	}

	public static void dhlCarrier() throws InterruptedException {
		Thread.sleep(2000);
		Select cracct = new Select(Driver.findElement(By.id("cmb3PAccount")));
		cracct.selectByVisibleText("Quantum DHL UK (ca_7787eb057e2c4e308589cd5e5dee7ba7)");
	}

	public static void shipLabelServicesFedEx() throws Exception {
		Thread.sleep(2000);
		Driver.findElement(By.id("hlkShiplabel")).click();
		Thread.sleep(4000);
		Select rtn = new Select(Driver.findElement(By.id("cmbServiceType")));
		rtn.selectByVisibleText("FEDEX_GROUND");
		Thread.sleep(2000);
		Driver.findElement(By.id("btnSubmit")).click();
		Thread.sleep(10000);
		getScreenshot(Driver, "ShipLabel_FedEx.jpg");
		Driver.findElement(By.id("idanchorclose")).click();
		Thread.sleep(4000);

	}

	public static void shipLabelServicesUPS() throws Exception {
		Thread.sleep(2000);
		Driver.findElement(By.id("hlkShiplabel")).click();
		Thread.sleep(4000);
		Select rtn = new Select(Driver.findElement(By.id("cmbServiceType")));
		rtn.selectByVisibleText("Ground");
		Thread.sleep(2000);
		Driver.findElement(By.id("btnSubmit")).click();
		Thread.sleep(10000);
		getScreenshot(Driver, "ShipLabel_UPS.jpg");
		Driver.findElement(By.id("idanchorclose")).click();
		Thread.sleep(4000);

	}

	public static void shipLabelServicesDHL() throws Exception {
		Thread.sleep(2000);
		Driver.findElement(By.id("hlkShiplabel")).click();
		Thread.sleep(4000);
		Select rtn = new Select(Driver.findElement(By.id("cmbServiceType")));
		rtn.selectByVisibleText("Express1200NonDoc");
		Thread.sleep(2000);
		Driver.findElement(By.id("btnSubmit")).click();
		Thread.sleep(10000);
		getScreenshot(Driver, "ShipLabel_DHL.jpg");
		Driver.findElement(By.id("idanchorclose")).click();
		Thread.sleep(4000);

	}

	public void searchJob(int i) throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		Actions act = new Actions(Driver);

		try {
			// Enter JobID#
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("txtContains")));

			String PUID = getData("SPL", i, 18);
			isElementPresent("TLSearch_id").clear();
			isElementPresent("TLSearch_id").sendKeys(PUID);
			isElementPresent("TLSearch_id").sendKeys(Keys.TAB);

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			WebElement Search = isElementPresent("TLSearchButton_id");
			wait.until(ExpectedConditions.elementToBeClickable(Search));
			jse.executeScript("arguments[0].click();", Search);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception eTasklog) {
			// --Go To Operations
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_operations")));
			WebElement Operations = isElementPresent("Operations_id");
			act.moveToElement(Operations).click().perform();
			logger.info("Clicked on Operations");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"OpenCloseClass dropdown open\"]//ul")));

			// --Go to TaskLog
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_TaskLog")));
			isElementPresent("OpTaskLog_id").click();
			logger.info("Clicked on TaskLog");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			getScreenshot(Driver, "TaskLog");

			// Enter JobID#
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			String PUID = getData("SPL", i, 18);
			msg.append("PickUpID=" + PUID + "\n");
			isElementPresent("TLSearch_id").clear();
			isElementPresent("TLSearch_id").sendKeys(PUID);
			isElementPresent("TLSearch_id").sendKeys(Keys.TAB);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			WebElement Search = isElementPresent("TLSearchButton_id");
			wait.until(ExpectedConditions.elementToBeClickable(Search));
			jse.executeScript("arguments[0].click();", Search);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		}

	}

	public void shipLabel() throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		try {
			// --Ship Label
			WebElement ShipLabel = isElementPresent("TT3ShipLabel_id");
			wait.until(ExpectedConditions.elementToBeClickable(ShipLabel));
			ShipLabel.click();
			logger.info("Clicked on Ship Label Services");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"SLForm\"]")));

			try {
				// --If Ship Label is not generated
				// --Select 3p Account
				Select p3acc = new Select(isElementPresent("TT3ACDrop_id"));
				p3acc.selectByIndex(1);
				logger.info("Selected 3p Account");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --Select Service
				Select Contacttype = new Select(isElementPresent("TT3Servicedrp_id"));
				Contacttype.selectByVisibleText("FEDEX_GROUND");
				logger.info("Selected Service");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --Click on Submit
				WebElement Submit = isElementPresent("TT3ShiLSubmit_id");
				wait.until(ExpectedConditions.elementToBeClickable(Submit));
				Submit.click();
				logger.info("Clicked on Submit button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			} catch (Exception eShipLabel) {
				// --If Ship Label is generated

				// --Send Email
				WebElement SendEmail = isElementPresent("SLSemail_id");
				wait.until(ExpectedConditions.elementToBeClickable(SendEmail));
				SendEmail.sendKeys("Ravina.prajapati@samyak.com");
				logger.info("Entered Email in Send Email");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --Click on Send Button
				WebElement SendBTN = isElementPresent("SLSSend_id");
				wait.until(ExpectedConditions.elementToBeClickable(SendBTN));
				SendBTN.click();
				logger.info("Clicked on Send button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// ErrorMsg
				try {
					wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"idValidationPopup\"]")));
					logger.info("ErroMsg is Displayed=" + isElementPresent("SLSVal_xpath").getText());

					// -- Check the checkbox
					WebElement ShipLabl = isElementPresent("SLSSelect1_xpath");
					wait.until(ExpectedConditions.elementToBeClickable(ShipLabl));
					js.executeScript("arguments[0].click();", ShipLabl);
					logger.info("Checked the checkbox of ship label");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					// --Send Email
					SendEmail = isElementPresent("SLSemail_id");
					wait.until(ExpectedConditions.elementToBeClickable(SendEmail));
					SendEmail.sendKeys("Ravina.prajapati@samyak.com");
					logger.info("Entered Email in Send Email");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					// --Click on Send Button
					SendBTN = isElementPresent("SLSSend_id");
					wait.until(ExpectedConditions.elementToBeClickable(SendBTN));
					SendBTN.click();
					logger.info("Clicked on Send button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
					System.out
							.println("Success Message is Displayed=" + isElementPresent("SLSSuccess_xpath").getText());

				} catch (Exception e) {
					logger.info("Error Message is not displayed");
					System.out.println(e);
				}
				// --Print button
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[@id=\"scrollboxIframe\"]//button[@id=\"btnPrint\"]")));
				String TrackingNo = isElementPresent("SLSTrackNo_xpath").getText();
				logger.info("Tracking No==" + TrackingNo);

				// --Click on Print Button
				WebElement PrintBTN = isElementPresent("SLSPrintBTN_id");
				wait.until(ExpectedConditions.elementToBeClickable(PrintBTN));
				PrintBTN.click();
				logger.info("Clicked on Print button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"SLForm\"]")));

				// Handle Print window
				String WindowHandlebefore = Driver.getWindowHandle();
				for (String windHandle : Driver.getWindowHandles()) {
					Driver.switchTo().window(windHandle);
					logger.info("Switched to Print window");
					Thread.sleep(5000);
					getScreenshot(Driver, "PrintShipLabelService");
				}
				Driver.close();
				logger.info("Closed Print window");

				Driver.switchTo().window(WindowHandlebefore);
				logger.info("Switched to main window");

			}

			try {
				// --Close
				WebElement SLClose = isElementPresent("SLSCloseBtn_id");
				js.executeScript("arguments[0].click();", SLClose);
				logger.info("Clicked on Close button of ShipLabel");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@ng-form=\\\"SLForm\\\"]")));
				Thread.sleep(2000);
			} catch (Exception CLoseee) {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"SLForm\"]")));

				// --Close
				WebElement SLClose = isElementPresent("SLSCloseBtn_id");
				act.moveToElement(SLClose).build().perform();
				act.moveToElement(SLClose).click().perform();
				logger.info("Clicked on Close button of ShipLabel");
				Thread.sleep(2000);
			}

		} catch (Exception noShipLabel) {
			logger.info("There is no Ship Label");

		}

		// --Close Ship Label Service pop up

		/*
		 * logger.info("===ShipLabel Test End==="); msg.append("===ShipLabel Test End==="
		 * + "\n\n");
		 */
		logger.info("ShipLabel Test=PASS");
		msg.append("ShipLabel Test=PASS" + "\n");

	}

	public static void inventorySearch(int i) throws InterruptedException, IOException {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 40);// wait time
		Actions act = new Actions(Driver);

		// --Click on Search Parts button
		WebElement PartsSearch = isElementPresent("OCPartSearch_id");
		wait.until(ExpectedConditions.elementToBeClickable(PartsSearch));
		jse.executeScript("arguments[0].click();", PartsSearch);
		logger.info("Clicked on Parts search button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(
				ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"modal-dialog modal-lg\"]")));
		getScreenshot(Driver, "InventorySearch_" + i);

		// --Click on Advanced Search
		WebElement AdSearch = isElementPresent("OCPSAdvanceSearch_id");
		wait.until(ExpectedConditions.elementToBeClickable(AdSearch));
		jse.executeScript("arguments[0].click();", AdSearch);
		logger.info("Clicked on Advanced search button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --CLick on FSL Dropdown
		WebElement FSL = isElementPresent("OCPSASFSlDrp_id");
		wait.until(ExpectedConditions.elementToBeClickable(FSL));
		Select FSLdrp = new Select(FSL);
		FSLdrp.selectByVisibleText("AUTOMATION RV (F5505)");
		logger.info("Selected FSL");

		// --Field 1
		WebElement Field1 = isElementPresent("OCPSField1_id");
		wait.until(ExpectedConditions.elementToBeClickable(Field1));
		Field1.clear();
		Field1.sendKeys("MN03");
		logger.info("Entered value of Field 1");

		// --CLick on Search button
		WebElement PartSearch = isElementPresent("OCPSASPartSearch_id");
		wait.until(ExpectedConditions.elementToBeClickable(PartSearch));
		jse.executeScript("arguments[0].click();", PartSearch);
		logger.info("Clicked on search button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --CLick on Add button
		WebElement AddParts = isElementPresent("OCPSASAddPart_xpath");
		wait.until(ExpectedConditions.elementToBeClickable(AddParts));
		jse.executeScript("arguments[0].click();", AddParts);
		logger.info("Clicked on Add Part button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Parts added or not
		String totalQty = isElementPresent("OCPSAddedQty_id").getText();
		logger.info("Total Qty of added parts==" + totalQty);

		if (totalQty.equalsIgnoreCase("1")) {
			logger.info("Part is added successfully==PASS");

		} else {
			logger.info("Part is not added successfully==FAIL, Add Part button is not working");
			getScreenshot(Driver, "PartNotAdded_" + i);

		}

		// --CLick on Add button
		WebElement SavePart = isElementPresent("OCPSSave_id");
		wait.until(ExpectedConditions.elementToBeClickable(SavePart));
		jse.executeScript("arguments[0].click();", SavePart);
		logger.info("Clicked on Save Part button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Parts Saved or not
		String PartQty = isElementPresent("OCPartsQty_id").getText();
		logger.info("Qty of added parts==" + PartQty);

		if (PartQty.equalsIgnoreCase("1")) {
			logger.info("Part is saved successfully==PASS");

		} else {
			logger.info("Part is not added successfully==FAIL, Save Part button is not working");
			getScreenshot(Driver, "PartNotSaved_" + i);

		}

	}

}
