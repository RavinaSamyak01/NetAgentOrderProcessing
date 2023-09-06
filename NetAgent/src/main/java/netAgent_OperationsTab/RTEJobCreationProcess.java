package netAgent_OperationsTab;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import connect_OCBaseMethods.OrderCreation;
import netAgent_BasePackage.BaseInit;

public class RTEJobCreationProcess extends BaseInit {

	public void rtejobcreationprocess()
			throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException {
		msg.append("\n\n" + "==RTE Job Search & Process==" + "\n");

		// Get Tracking No
		getRTETrackingNo();

		// -Search RTE job
		searchRTEJob();

		// -Process From Connect
		rteConnectProcess();

		msg.append("\n\n" + "==RTE Job Search & Process==" + "\n");

	}

	public void getRTETrackingNo() throws EncryptedDocumentException, InvalidFormatException, IOException {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		Actions act = new Actions(Driver);
		// --Go to RouteList
		// --Go to Tools tab
		try {
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

			int TotalRow = getTotalRow("RTE");
			logger.info("Total Rows==" + TotalRow);

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
					setResultData("Result", 13, 3, RWTrackingNo);
					setResultData("Result", 13, 5, "PASS");

				}
			}

		} catch (Exception esearchjob) {
			logger.error(esearchjob);
			getScreenshot(Driver, "getRTETrackingIDError");
			String Error = esearchjob.getMessage();
			setResultData("Result", 13, 5, "FAIL");
			setResultData("Result", 13, 6, Error);

		}
	}

	public void searchRTEJob()
			throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException {

		WebDriverWait wait = new WebDriverWait(Driver, 50);// wait time

		Actions act = new Actions(Driver);
		JavascriptExecutor js = (JavascriptExecutor) Driver;

		try {
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
					setResultData("Result", 13, 5, "PASS");

				}

			}
		} catch (Exception ewait) {
			getScreenshot(Driver, "SearchRTEError");
			String Error = ewait.getMessage();
			setResultData("Result", 13, 5, "FAIL");
			setResultData("Result", 13, 6, Error);

		}

	}

	public void rteConnectProcess() throws IOException {
		WebDriverWait wait = new WebDriverWait(Driver, 40);
		Actions act = new Actions(Driver);
		JavascriptExecutor js = (JavascriptExecutor) Driver;

		try {
			WebElement JobStatusDis = isElementPresent("TLJobstatusDsb_xpath");
			if (JobStatusDis.isDisplayed()) {
				logger.info("Job status tab is Disabled");
			}
		} catch (Exception JobStatusDisabled) {
			logger.info("Job status tab is not Opened");
			// --Go to Job Status Tab
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idJobOverview")));
			isElementPresent("TLJobSTatus_id").click();
			logger.info("Clicked on Job Status Tab");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		}

		// --Job Status
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
		String jobStatus = isElementPresent("TLStageLable_id").getText();
		logger.info("Job status is==" + jobStatus);
		msg.append("Job status is==" + jobStatus + "\n");

		logger.info("It is TC ACK stage");
		getScreenshot(Driver, "JobEditor_TCACK");

		// --Click on Acknowledge button
		wait.until(ExpectedConditions.elementToBeClickable(By.id("GreyTick")));
		isElementPresent("TLAcknoldge_id").click();

		logger.info("Clicked on Acknowledge button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		WebElement PickUPSection = isElementPresent("TLAlertstages_id");
		wait.until(ExpectedConditions.visibilityOf(PickUPSection));
		getScreenshot(Driver, "JobEditor_RDYFORDSP");
		jobStatus = isElementPresent("TLStageLable_id").getText();
		logger.info("Job status is==" + jobStatus);
		msg.append("Job status is==" + jobStatus + "\n");

		// --Edit driver
		OrderCreation OC = new OrderCreation();
		OC.EditDriver();

		// --Click on SendPuAlert button
		wait.until(ExpectedConditions.elementToBeClickable(By.id("WhiteTickAlert")));
		isElementPresent("TLSendPUAl_id").click();
		logger.info("Clicked on Send PU Alert button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
			String ValMsg = isElementPresent("TLAlValidation_id").getText();
			logger.info("Validation is displayed==" + ValMsg);

			// --Enter SpokeWith
			wait.until(ExpectedConditions.elementToBeClickable(By.id("txtSpokeWith")));
			isElementPresent("TLSpokeWith_id").sendKeys("RV");
			logger.info("Entered Spoke With");

			// --Click on SendPuAlert button
			wait.until(ExpectedConditions.elementToBeClickable(By.id("WhiteTickAlert")));
			isElementPresent("TLSendPUAl_id").click();
			logger.info("Clicked on Send PU Alert button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception NoVal) {
			logger.info("Spoke With validation is not displayed");

		}

	}

}
