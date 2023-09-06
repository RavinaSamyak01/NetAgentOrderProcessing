package NetAgent_Master_screen_Production;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
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

public class SD_Order extends OrderCreation {
	@Test
	public void sdSameDay() throws Exception {
		/*
		 * JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		 * WebDriverWait wait = new WebDriverWait(Driver, 50);// wait time Actions act =
		 * new Actions(Driver);
		 */

		// --Order Creation
		OrderCreation_prod OC = new OrderCreation_prod();

		OC.orderCreation_production(2);

		// --Unknown Shipper
		OC.unknowShipper(2);

		// --Select Flight
		OC.selectFlight(2);

		// --Add AirBill
		// OC.addAirBill(2);

		addAirBills(2);

		// -- Fetch Airport , Flight and location

		fetch_flight_details_connect(2);

		// TC Acknowledge
		TCAcknowledge TCAck = new TCAcknowledge();
		TCAck.tcAcknowledge();

		// Pickup Alert
		ReadyForDispatch RFD = new ReadyForDispatch();
		RFD.pickupAlert();

		// --Refresh App
		refreshApp();

		ConnectlogOut_prod();

		sdProcessing();

		msg.append("**Verify from connect**" + "\n");

		Connectlogin();

		OC.searchJob(2);

		SendDelAlert SDA = new SendDelAlert();
		SDA.delAlert();

		ConnectlogOut_prod();

		sdConfDelstages();

		msg.append("**Verify from connect**" + "\n");

		Connectlogin();

		OC.searchJob(2);

		// Wait for Departure
		WaitForDeptarture_prod WFD = new WaitForDeptarture_prod();
		WFD.waitForDept();

		// OnBorad
		Board_prod Brd = new Board_prod();
		Brd.onBoard();

		// XER wait for Arrival
		XerWaitForArrival_prod XWFA = new XerWaitForArrival_prod();
		XWFA.xerWaitForArr();

		// XER Wait for Departure
		XerWaitForDeparture_prod XWFD = new XerWaitForDeparture_prod();
		XWFD.xerWaitForDept();

		// board2
		Board1_prod Brd1 = new Board1_prod();
		Brd1.onBoard1();

		// Wait for Arrival
		WaitForArrival_prod WFA = new WaitForArrival_prod();
		WFA.waitForArr();

		// Recover
		NARecover_prod RCV = new NARecover_prod();
		RCV.recoverAtDestination();

		// DELIVERED
		Deliver_prod Del = new Deliver_prod();
		Del.confirmDelivery();

		// Verify Customer Bill
		VerifyCustomerBill_prod VCB = new VerifyCustomerBill_prod();
		VCB.verifyCustomerBill(2);

		// --Refresh App
		refreshApp();

		// -- Cancel Prod Job

		cancel_prod_job(2);

	}

//----------------------------------------------------------------------------------------
	public void sdProcessing() throws Exception {

		// --Order Creation
		OrderCreation OC = new OrderCreation();

		String PUID = getData("OrderCreation", 2, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		// OC.opFromNetAgent(2);
		opFromNetAgent(2);

		NAConfirmPUAlert_prod CPU = new NAConfirmPUAlert_prod();
		CPU.naconfirmPUAlert();

		// OC.NATaskSearch(PUID);
		NATaskSearchs(PUID);

		NAPickup_prod Pup = new NAPickup_prod();
		Pup.naconfirmPickup();

		// OC.NATaskSearch(PUID);
		NATaskSearchs(PUID);

		NADrop_prod drp = new NADrop_prod();
		drp.nadropAtOrigin();

		// OC.NATaskSearch(PUID);
		NATaskSearchs(PUID);

		logOut();

	}

	public void sdConfDelstages() throws Exception {
		// --Order Creation
		OrderCreation OC = new OrderCreation();

		String PUID = getData("OrderCreation", 2, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		// OC.opFromNetAgent(2);
		opFromNetAgent(2);

		ConfirmDelAlert_prod CDA = new ConfirmDelAlert_prod();
		CDA.confDelAlert();

		logOut();
	}

	public void opFromNetAgent(int i) throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		WebDriverWait wait2 = new WebDriverWait(Driver, 7);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		// --NetAgent Login
		Login();

		msg.append("\n" + "**OrderProcessing from NetAgent**" + "\n");

		// Go To TaskLog
		WebElement OperationMenu = isElementPresent("NTLOperations_xpath");
		wait.until(ExpectedConditions.elementToBeClickable(OperationMenu));
		act.moveToElement(OperationMenu).build().perform();
		js.executeScript("arguments[0].click();", OperationMenu);
		logger.info("Click on Operations");

		WebElement TaskLogMenu = isElementPresent("NTTasklog_xpath");
		act.moveToElement(TaskLogMenu).build().perform();
		wait.until(ExpectedConditions.elementToBeClickable(TaskLogMenu));
		js.executeScript("arguments[0].click();", TaskLogMenu);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));
		logger.info("Click on Task Log");

		String PUID = getData("OrderCreation", i, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		NATaskSearchs(PUID);

	}

	public void NATaskSearchs(String PUID) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		WebDriverWait wait2 = new WebDriverWait(Driver, 10);
		WebDriverWait wait3 = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		// Go To TaskLog
		WebElement OperationMenu = isElementPresent("NTLOperations_xpath");
		wait2.until(ExpectedConditions.elementToBeClickable(OperationMenu));
		act.moveToElement(OperationMenu).build().perform();
		js.executeScript("arguments[0].click();", OperationMenu);
		logger.info("Click on Operations");

		WebElement TaskLogMenu = isElementPresent("NTTasklog_xpath");
		act.moveToElement(TaskLogMenu).build().perform();
		wait2.until(ExpectedConditions.elementToBeClickable(TaskLogMenu));
		js.executeScript("arguments[0].click();", TaskLogMenu);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait2.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));
		logger.info("Click on Task Log");

		WebElement OpTab = isElementPresent("NTOpeTab_xpath");
		wait2.until(ExpectedConditions.visibilityOf(OpTab));
		wait2.until(ExpectedConditions.elementToBeClickable(OpTab));
		act.moveToElement(OpTab).build().perform();
		js.executeScript("arguments[0].click();", OpTab);
		logger.info("Click on Operation Tab");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		WebElement SearchBox = isElementPresent("NTaskSearchbox_id");
		wait.until(ExpectedConditions.visibilityOf(SearchBox));
		wait.until(ExpectedConditions.elementToBeClickable(SearchBox));
		SearchBox.clear();
		logger.info("Clear search input");
		SearchBox.sendKeys(PUID);
		logger.info("Enter PickUpID in Search input");
		WebElement OPSearch = isElementPresent("NTSearchBtn_id");
		act.moveToElement(OPSearch).build().perform();
		js.executeScript("arguments[0].click();", OPSearch);
		logger.info("Click on Search button");
		wait3.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

		try {

			WebElement noData = isElementPresent("//span[@class='dx-datagrid-nodata']");

			if (!noData.isDisplayed()) {
				logger.info("Job is available in NetAgent");

			} else {
				logger.info("Job is not available in NetAgent");
			}

		} catch (Exception e) {
			logger.info("Job is available in Operation Tab");
		}

	}

	public void addAirBills(int i)
			throws IOException, InterruptedException, EncryptedDocumentException, InvalidFormatException {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		Actions act = new Actions(Driver);

		try {
			WebElement AddAirBillBtn = isElementPresent("TLDAOAirBill_id");
			act.moveToElement(AddAirBillBtn).build().perform();
			wait.until(ExpectedConditions.visibilityOf(AddAirBillBtn));
			act.moveToElement(AddAirBillBtn).click().build().perform();
			logger.info("Click on Add AirBill");

			WebElement AirBillEdi = isElementPresent("TLAirEditor_xpath");
			act.moveToElement(AirBillEdi).build().perform();
			wait.until(ExpectedConditions.visibilityOf(AirBillEdi));

			// --AirBill
			isElementPresent("TLDAOAirBillNo_id").clear();
			isElementPresent("TLDAOAirBillNo_id").sendKeys("2121-2170");
			logger.info("Click on Add AirBill");

			// --Qty
			isElementPresent("TLDAOAirQty_id").clear();
			isElementPresent("TLDAOAirQty_id").sendKeys("1");
			logger.info("Enter Quantity");

			// --Weight
			isElementPresent("TLDAOAirWeight_id").clear();
			isElementPresent("TLDAOAirWeight_id").sendKeys("10");
			logger.info("Enter Weight");

			// --Enter Description
			isElementPresent("EOAirBDesc_id").clear();
			isElementPresent("EOAirBDesc_id").sendKeys("Test Description");
			logger.info("Enter Description");

			try {
				logger.info("Account and Service Lavel dropdowns are available");
				// Account
				Select Account = new Select(isElementPresent("EOAccountdrp_xpath"));
				Account.selectByIndex(1);
				logger.info("Selected Account");

				// Service Level
				Select SerLevel = new Select(isElementPresent("EOServcLvlSelection_xpath"));
				SerLevel.selectByIndex(1);
				logger.info("Selected Service Level");
			} catch (Exception ee) {
				logger.info("Account and Service Lavel dropdowns are not available");

			}

			// --Save Airbill
			WebElement saveairbill = isElementPresent("TLDAOSaveBill_id");
			act.moveToElement(saveairbill).build().perform();
			jse.executeScript("arguments[0].click();", saveairbill);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			logger.info("Click on Save Bill button");

			jse.executeScript("window.scrollBy(0,-250)");
			// --Set result in test scenarios
			if (i == 2) {
				setData("TC_OrderProcess", 15, 5, "PASS");
			}
		} catch (Exception e) {
			getScreenshot(Driver, "AirBillIssue");
			// --Set result in test scenarios
			if (i == 2) {
				setData("TC_OrderProcess", 15, 5, "FAIL");
			}
		}
	}

	public void fetch_flight_details_connect(int i)
			throws IOException, InterruptedException, EncryptedDocumentException, InvalidFormatException {

		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		Actions act = new Actions(Driver);

		// -- navigate Edit job

		WebElement edit_job_tab = isElementPresent("EOEditOrderTab_id");
		edit_job_tab.click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		Thread.sleep(2000);

		WebElement flight_plan = isElementPresent("fligt_plan_id");

		jse.executeScript("arguments[0].scrollIntoView(true);", flight_plan);
		Thread.sleep(1500);

		WebElement flight_no = Driver
				.findElement(By.xpath("//span[@ng-bind='obj.Seg_CarrierCode + obj.Seg_FlightNum']"));
		String flight_no_get = flight_no.getText();
		logger.info("Flight no is : " + flight_no_get);

		String input = flight_no_get;

		// Define regular expressions for alphabets and numerics
		String alphabetRegex = "[A-Za-z]+";
		String numericRegex = "[0-9]+";

		// Create pattern objects for the regular expressions
		Pattern alphabetPattern = Pattern.compile(alphabetRegex);
		Pattern numericPattern = Pattern.compile(numericRegex);

		// Create matcher objects for the input string
		Matcher alphabetMatcher = alphabetPattern.matcher(input);
		Matcher numericMatcher = numericPattern.matcher(input);

		// Find and print the alphabet part
		if (alphabetMatcher.find()) {
			String Airline_ID = alphabetMatcher.group();
			System.out.println("Airline ID: " + Airline_ID);
			logger.info("Airline ID: " + Airline_ID);
			// -- set data in sheet
			setData("OrderCreation", 2, 39, Airline_ID);
			setData("AgentConsole", 1, 0, Airline_ID);

		}

		// Find and print the numeric part
		if (numericMatcher.find()) {
			String Flight_No = numericMatcher.group();
			System.out.println("Flight No : " + Flight_No);
			logger.info("Flight No : " + Flight_No);
			// -- set data in sheet
			setData("OrderCreation", 2, 40, Flight_No);
			setData("AgentConsole", 1, 1, Flight_No);
		}

		// -- find airport location

		WebElement airport_id = isElementPresent("airport_location_xpath");
		String a_id = airport_id.getText();
		logger.info("Airport ID is : " + a_id);
		// -- set data in sheet
		setData("OrderCreation", 2, 41, a_id);
		setData("AgentConsole", 1, 2, a_id);

	}

	public void cancel_prod_job(int i) throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		Actions act = new Actions(Driver);
		logger.info("Start process for job cancel");
		msg.append("Start process for job cancel");
		try {
			OrderCreation_prod OC = new OrderCreation_prod();
			OC.searchJob(2);

			// --Go to Edit Job tab
			WebElement EditOrTab = isElementPresent("EOEditOrderTab_id");
			act.moveToElement(EditOrTab).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(EditOrTab));
			act.moveToElement(EditOrTab).click().perform();
			logger.info("Click on Edit Order Tab");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			Thread.sleep(1500);

			// -- select Cancel Job : Cancel/NC
			WebElement cancel_nc_drp_down = isElementPresent("job_type_cancel_id");
			act.moveToElement(cancel_nc_drp_down).build().perform();
			Thread.sleep(1500);
			Select cancel_nc = new Select(isElementPresent("job_type_cancel_id"));
			cancel_nc.selectByVisibleText("CANCEL/NC");
			logger.info("Select job type : CANCEL/NC");
			Thread.sleep(1500);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			getScreenshot(Driver, "Select_canceljob");
			// --Click on Save Changes
			isElementPresent("TLSaveChanges_id").click();
			logger.info("Clicked on Save Changes button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Click yes for cancel cnfm

			Thread.sleep(1500);
			WebElement job_cance_cnfm = isElementPresent("BTNOk_id");
			job_cance_cnfm.click();
			logger.info("Clicked on ok for cancel job confirmation");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// -- enter cancel note

			WebElement cancel_note = isElementPresent("cancel_note_xpath");
			cancel_note.sendKeys("cancel_note_xpath");
			logger.info("Entering Cancellation description");
			getScreenshot(Driver, "canceljob_description");

			// -- Click on ok to final cancel job

			Thread.sleep(1500);
			WebElement job_cancel_cnfm = isElementPresent("BTNOk_id");
			job_cancel_cnfm.click();
			logger.info("Click on Ok for Final Job cancel Confirmation");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// -- validate job is cancel or not by reropen and verify it's title
			OC.searchJob(2);

			String Job_status = getStageName();

			WebElement cancel_nc_nav = isElementPresent("job_type_cancel_id");
			act.moveToElement(cancel_nc_nav).build().perform();

			Thread.sleep(1500);
			getScreenshot(Driver, "canceljob_type");

			if (Job_status.equalsIgnoreCase("CANCEL")) {
				logger.info("job is cancelled");

				setData("TC_OrderProcess", 39, 5, "PASS");
			}

			else {

				Select cancel_ncs = new Select(isElementPresent("job_type_cancel_id"));
				cancel_ncs.selectByVisibleText("CANCEL/NC");
				logger.info("Select job type : CANCEL/NC");
				Thread.sleep(1500);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				getScreenshot(Driver, "Select_canceljob");
				// --Click on Save Changes
				isElementPresent("TLSaveChanges_id").click();
				logger.info("Clicked on Save Changes button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --Click yes for cancel cnfm

				Thread.sleep(1500);
				WebElement job_cance_cnfm1 = isElementPresent("BTNOk_id");
				job_cance_cnfm1.click();
				logger.info("Clicked on ok for cancel job confirmation");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// -- enter cancel note

				WebElement cancel_note1 = isElementPresent("cancel_note_xpath");
				cancel_note1.sendKeys("cancel_note_xpath");
				logger.info("Entering Cancellation description");
				getScreenshot(Driver, "canceljob_description");

				// -- Click on ok to final cancel job

				Thread.sleep(1500);
				WebElement job_cancel_cnfm1 = isElementPresent("BTNOk_id");
				job_cancel_cnfm1.click();
				logger.info("Click on Ok for Final Job cancel Confirmation");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// -- validate job is cancel or not by reropen and verify it's title
				OC.searchJob(2);

				String Jobstatus = getStageName();

				WebElement cancel_nc1 = isElementPresent("job_type_cancel_id");
				act.moveToElement(cancel_nc1).build().perform();

				Thread.sleep(1500);
				getScreenshot(Driver, "canceljob_type");

				if (Jobstatus.equalsIgnoreCase("CANCEL")) {
					logger.info("job is cancelled");
					setData("TC_OrderProcess", 39, 5, "PASS");
				}

				else {

					logger.info("job is not cancelled");
					setData("TC_OrderProcess", 39, 5, "FAIL");
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			Driver.navigate().refresh();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			OrderCreation_prod OC = new OrderCreation_prod();
			OC.searchJob(2);

			// -- select Cancel Job : Cancel/NC

			Select cancel_nc = new Select(isElementPresent("job_type_cancel_id"));
			cancel_nc.selectByVisibleText("CANCEL/NC");
			logger.info("Select job type : CANCEL/NC");
			Thread.sleep(1500);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			getScreenshot(Driver, "Select_canceljob");
			// --Click on Save Changes
			isElementPresent("TLSaveChanges_id").click();
			logger.info("Clicked on Save Changes button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Click yes for cancel cnfm

			Thread.sleep(1500);
			WebElement job_cance_cnfm = isElementPresent("BTNOk_id");
			job_cance_cnfm.click();
			logger.info("Clicked on ok for cancel job confirmation");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// -- enter cancel note

			WebElement cancel_note = isElementPresent("cancel_note_xpath");
			cancel_note.sendKeys("cancel_note_xpath");
			logger.info("Entering Cancellation description");
			getScreenshot(Driver, "canceljob_description");

			// -- Click on ok to final cancel job

			Thread.sleep(1500);
			WebElement job_cancel_cnfm = isElementPresent("BTNOk_id");
			job_cancel_cnfm.click();
			logger.info("Click on Ok for Final Job cancel Confirmation");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// -- validate job is cancel or not by reropen and verify it's title
			OC.searchJob(2);

			String Job_status = getStageName();

			WebElement cancel_nc_nav = isElementPresent("job_type_cancel_id");
			act.moveToElement(cancel_nc_nav).build().perform();

			Thread.sleep(1500);
			getScreenshot(Driver, "canceljob_type");

			if (Job_status.equalsIgnoreCase("CANCEL")) {
				logger.info("job is cancelled");
				setData("TC_OrderProcess", 39, 5, "PASS");
			}

			else {

				logger.info("job is not cancelled");
				setData("TC_OrderProcess", 39, 5, "FAIL");
			}

		}
	}

}
