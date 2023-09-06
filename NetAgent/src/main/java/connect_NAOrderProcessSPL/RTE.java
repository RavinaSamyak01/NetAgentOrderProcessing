package connect_NAOrderProcessSPL;

import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import connect_OCBaseMethods.OrderCreation;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import netAgent_BasePackage.BaseInit;
import netAgent_OrderProcessing.RTE_OrderProcess;

public class RTE extends BaseInit {

	public void rteorderprocess()
			throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(Driver, 40);

		OrderCreation OC = new OrderCreation();

		getTrackNo();

		searchJob();
		
		

		// --Refresh App
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		OC.refreshApp();

		// --NetAgent Tab
		OC.naTab();

		RTE_OrderProcess RTEO = new RTE_OrderProcess();
		RTEO.orderProcessRTEJOB();

		// --COnnect Tab
		OC.connectTab();

	}

	public void searchJob()
			throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException {

		WebDriverWait wait = new WebDriverWait(Driver, 50);// wait time

		Actions act = new Actions(Driver);
		JavascriptExecutor js = (JavascriptExecutor) Driver;

		// --Go To Operations
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_operations")));
		WebElement Operations = isElementPresent("OperationsTab_id");
		act.moveToElement(Operations).click().perform();
		logger.info("Clicked on Operations");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"OpenCloseClass dropdown open\"]//ul")));

		// --Go to TaskLog
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_TaskLog")));
		isElementPresent("TaskLog_id").click();
		logger.info("Clicked on TaskLog");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		getScreenshot(Driver, "TaskLog");

		// --Zoom Out
		js.executeScript("document.body.style.zoom='90%';");
		Thread.sleep(2000);

		// --Go to Search All Job
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hlkOrderSearch")));
		WebElement SearchAllJob = isElementPresent("TLSearchAllJob_id");
		act.moveToElement(SearchAllJob).build().perform();
		js.executeScript("arguments[0].click();", SearchAllJob);
		logger.info("Clicked on SearchAllJobs");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("AdvancesSearch")));

		// --Zoom In
		js.executeScript("document.body.style.zoom='100%';");
		Thread.sleep(2000);

		// --Reset button
		isElementPresent("RLReset_id").click();
		logger.info("Clicked on Reset button");

		// --Set Origin FRom Date
		WebElement OFromDate = isElementPresent("TLOrderRFrom_id");
		OFromDate.clear();
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		logger.info(dateFormat.format(date));
		OFromDate.sendKeys(dateFormat.format(date));
		OFromDate.sendKeys(Keys.TAB);
		logger.info("Entered Origin From Date");

		// --Set Origin To Date
		WebElement OToDate = isElementPresent("TLOrderRTo_id");
		OToDate.clear();
		date = new Date();
		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		logger.info(dateFormat.format(date));
		OToDate.sendKeys(dateFormat.format(date));
		OToDate.sendKeys(Keys.TAB);
		logger.info("Entered Origin From Date");

		// --RouteTrackingNo
		wait.until(ExpectedConditions.elementToBeClickable(By.id("txtRouteTrackingNum")));
		String RouteTrackingNo = getData("RTE", 1, 1);
		logger.info("Route Tracking No==" + RouteTrackingNo);
		msg.append("\n");
		msg.append("Route Tracking No==" + RouteTrackingNo + "\n");
		isElementPresent("TLSARoutTrackNo_id").sendKeys(RouteTrackingNo);
		logger.info("Entered RouteTrackingID");

		// --Click on Search
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearch")));
		isElementPresent("RLSearch_id").click();
		logger.info("Click on Search button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		try {
			WebElement NoData = isElementPresent("NoData_className");
			wait.until(ExpectedConditions.visibilityOf(NoData));
			if (NoData.isDisplayed()) {
				logger.info("There is no Data with Search TrackingNO");

			}
		} catch (Exception NoData1) {

			logger.info("Data is exist with search TrackingNO");

			// --Stored all the records of the table
			List<WebElement> Records = Driver
					.findElements(By.xpath("//*[contains(@class,'dx-datagrid-table-content')]//tbody//tr"));
			int RecordNo = Records.size() - 1;
			logger.info("Total No of records are==" + RecordNo);

			for (int RTE = 0; RTE < RecordNo; RTE++) {
				String JobID = "lblJobIdValue_" + RTE;
				String PickUpID = "lblPickupIdValue_" + RTE;
				String BOLNO = "lblBOLNumValue_" + RTE;

				// --Moved to JobID
				WebElement BOLNODiv = Driver.findElement(By.id(BOLNO));
				act.moveToElement(BOLNODiv).build().perform();

				String JobIDValue = Driver.findElement(By.id(JobID)).getText();
				String PickUpIDValue = Driver.findElement(By.id(PickUpID)).getText();
				String BOLNoValue = Driver.findElement(By.id(BOLNO)).getText();

				msg.append("JobID is==" + JobIDValue + "\n");
				msg.append("PickUpID is==" + PickUpIDValue + "\n");
				msg.append("BOLNo is==" + BOLNoValue + "\n");

				int RT = RTE + 1;
				System.out.println("RT==" + RT);
				logger.info("JobID is==" + JobIDValue);
				setData("RTE", 1, 2, JobIDValue);

				logger.info("PickUpID is==" + PickUpIDValue);
				setData("RTE", 1, 3, PickUpIDValue);

				logger.info("BOLNo is==" + BOLNoValue);
				setData("RTE", 1, 4, BOLNoValue);

				// --Not working in jenkins because of window size
				js.executeScript("document.body.style.zoom='80%';");
				Thread.sleep(2000);

				// ---Select Record
				WebElement Job = Driver.findElement(By.id(JobID));
				act.moveToElement(Job).build().perform();
				js.executeScript("arguments[0].click();", Job);
				logger.info("Clicked on Record");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				js.executeScript("document.body.style.zoom='100%';");
				Thread.sleep(2000);

				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
				getScreenshot(Driver, "JobEditor_RWTrackingID");

				// --Exit Without Save
				isElementPresent("TLEXWSave_id").click();
				logger.info("Clicked on Exit without Save");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				try {
					// --Go to TaskManager tab
					wait.until(ExpectedConditions.elementToBeClickable(By.id("hlkTaskManager")));
					logger.info("Task Manager tab is not selected, select it");
					isElementPresent("TaskManager_id").click();
					logger.info("Click on Task Manager Tab");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				} catch (Exception e) {

					logger.info("Task Manager tab is selected");

				}

				PickUpID = getData("RTE", 1, 3);
				logger.info("PickUpID from excel is==" + PickUpID);

				System.out.println("PickedUp id==" + PickUpID);
				wait.until(ExpectedConditions.elementToBeClickable(By.id("txtContains")));
				isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
				logger.info("Entered PickUpID in basic search");

				// --Click on Search
				wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
				isElementPresent("TLGlobSearch_id").click();
				logger.info("Click on Search button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				try {

					try {
						logger.info("Data is exist with search PickUpID");
						wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
						getScreenshot(Driver, "JobEditor_PickUP");

						// --Get Route Tracking No
						String RWTrackNo = getData("RTE", 1, 1);
						logger.info("RW Tracking No===" + RWTrackNo);

						// --Exit Without Save
						isElementPresent("TLEXWSave_id").click();
						logger.info("Clicked on Exit without Save");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// --Go to Search All Job
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hlkOrderSearch")));
						// --Zoom Out
						js.executeScript("document.body.style.zoom='90%';");
						Thread.sleep(2000);

						// --Go to Search All Job
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hlkOrderSearch")));
						SearchAllJob = isElementPresent("TLSearchAllJob_id");
						act.moveToElement(SearchAllJob).build().perform();
						js.executeScript("arguments[0].click();", SearchAllJob);
						logger.info("Clicked on SearchAllJobs");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("AdvancesSearch")));

						// --Zoom In
						js.executeScript("document.body.style.zoom='100%';");
						Thread.sleep(2000);
					} catch (Exception Multiple) {
						logger.info("Data is exist with search PickUpID");

						wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("idOperationTasklogGrd")));
						List<WebElement> jobs = Driver.findElements(
								By.xpath("//td[contains(@aria-label,'Column Pickup #')]//label[@id=\"lblDateTime\"]"));
						int totaljobs = jobs.size();
						for (int job = 0; job < totaljobs; job++) {

							PickUpID = getData("RTE", 1, 3);
							logger.info("Entered PickupID is==" + PickUpID);

							String PickUPId = jobs.get(job).getText();
							logger.info("PickupID is==" + PickUPId);

							if (PickUPId.contains(PickUpID)) {
								logger.info("Searched job is exist");

								// --Click on the job
								jobs.get(job).click();
								logger.info("Clicked on searched Job");

								// --Job Status
								wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
								getScreenshot(Driver, "JobEditor_PickUP");

								// --Get Route Tracking No
								String RWTrackNo = isElementPresent("TLERWTrackNO_id").getText().trim();
								logger.info("RW Tracking No===" + RWTrackNo);
								// --set data in excel

								WebElement ShipCharges = isElementPresent("TLEShCharges_id");
								act.moveToElement(ShipCharges).build().perform();
								String Charges = ShipCharges.getText().trim();
								getScreenshot(Driver, "ChargesOnCreation");
								logger.info("Shipment Charges on Creation===" + Charges);
								// --set data in excel

								// --Exit Without Save
								isElementPresent("TLEXWSave_id").click();
								logger.info("Clicked on Exit without Save");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Go to Search All Job
								// --Zoom Out
								js.executeScript("document.body.style.zoom='90%';");
								Thread.sleep(2000);

								// --Go to Search All Job
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hlkOrderSearch")));
								SearchAllJob = isElementPresent("TLSearchAllJob_id");
								act.moveToElement(SearchAllJob).build().perform();
								js.executeScript("arguments[0].click();", SearchAllJob);
								logger.info("Clicked on SearchAllJobs");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
								wait.until(
										ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("AdvancesSearch")));

								// --Zoom In
								js.executeScript("document.body.style.zoom='100%';");
								Thread.sleep(2000);

								break;
							} else {
								logger.info("Searched job is not exist");

							}
						}

					}

				} catch (Exception NoDataEx) {
					logger.error(NoDataEx);
					getScreenshot(Driver, "NoDataEx_Error");
					try {
						WebElement NoData = isElementPresent("NoData_className");
						wait.until(ExpectedConditions.visibilityOf(NoData));
						if (NoData.isDisplayed()) {
							logger.info("There is no Data with Search PickUpID");

						}
					} catch (Exception e) {
						logger.error(e);
						WebElement NoData = isElementPresent("NODataTL_xpath");
						wait.until(ExpectedConditions.visibilityOf(NoData));
						if (NoData.isDisplayed()) {
							logger.info("There is no Data with Search PickUpID");

						}
					}
				}

			}

		}
	}

	public void getTrackNo() throws IOException, EncryptedDocumentException, InvalidFormatException {

		WebDriverWait wait = new WebDriverWait(Driver, 40);
		Actions act = new Actions(Driver);

		// --Search RTE Job in RouteList
		// --Go to RouteList
		// --Go to Tools tab
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_tools")));
		WebElement Tools = isElementPresent("Tools_id");
		act.moveToElement(Tools).click().perform();
		logger.info("Clicked on Tools");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"OpenCloseClass dropdown open\"]//ul")));

		// --Click on RouteList
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_RouteWorkList")));
		isElementPresent("RouteList_linkText").click();
		logger.info("Clicked on RouteList");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		getScreenshot(Driver, "RouteList");

		// --Enter RoutWorkID
		wait.until(ExpectedConditions.elementToBeClickable(By.id("txtRouteWorkId")));
		String RoutWID = getData("RTE", 1, 0);
		msg.append("RouteWorkID is==" + RoutWID + "\n");
		isElementPresent("RLRWIDInput_id").clear();
		isElementPresent("RLRWIDInput_id").sendKeys(RoutWID);
		logger.info("Entered RoutWorkID");

		// --Click on Search
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearch")));
		isElementPresent("RLSearch_id").click();
		logger.info("Click on Search button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		try {
			WebElement NoData = isElementPresent("NoData_className");
			wait.until(ExpectedConditions.visibilityOf(NoData));
			if (NoData.isDisplayed()) {
				logger.info("There is no Data with Search parameter");

			}
		} catch (Exception NoData1) {

			logger.info("Data is exist with search parameter");
			String RWTrackingNo = isElementPresent("RLRWTrackingNo_xpath").getText();

			if (RWTrackingNo.isEmpty()) {
				logger.info("RWTrackingNo is still not generated");

			} else {
				logger.info("RWTrackingNo is generated");
				RWTrackingNo = isElementPresent("RLRWTrackingNo_xpath").getText();
				logger.info("RWTrackingNo is ==" + RWTrackingNo);
				msg.append("RWTrackingNo is ==" + RWTrackingNo + "\n");

				setData("RTE", 1, 1, RWTrackingNo);
				logger.info("Inserted RWTrackingNo in Excel");

			}

		}

	}
}
