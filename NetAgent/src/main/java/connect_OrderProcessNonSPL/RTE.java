package connect_OrderProcessNonSPL;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.testng.annotations.Test;

import com.thoughtworks.qdox.parser.ParseException;

import connect_OCBaseMethods.OrderCreation;
import connect_OCBaseMethods.VerifyCustomerBill;
import netAgent_BasePackage.BaseInit;
import netAgent_OrderProcessing.RTE_OrderProcess;

public class RTE extends BaseInit {

	@Test
	public void rtejobcreationprocess()
			throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException {

		WebDriverWait wait = new WebDriverWait(Driver, 40);

		OrderCreation OC = new OrderCreation();

		// Get Tracking No 
		getRTETrackingNo();

		// -Search RTE job 
		searchRTEJob();

		// -Process From Connect 
		rteConnectProcess();

		// --Refresh App
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		OC.refreshApp();

		// --NetAgent Tab 
		OC.naTab();

		RTE_OrderProcess RTEO = new RTE_OrderProcess();
		RTEO.orderProcessRTEJOB();

		// --COnnect Tab
		OC.connectTab();

		// Process from connect
		rteVerifyConnect();


	}

	public void getRTETrackingNo() throws EncryptedDocumentException, InvalidFormatException, IOException {
		WebDriverWait wait = new WebDriverWait(Driver, 60);
		WebDriverWait wait2 = new WebDriverWait(Driver, 7);
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
				wait2.until(ExpectedConditions.visibilityOf(NoData));
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
					setResultData("Result", 23, 2, RWTrackingNo);
					setResultData("Result", 23, 4, "PASS");

				}
			}

		} catch (Exception esearchjob) {
			logger.error(esearchjob);
			getScreenshot(Driver, "getRTETrackingIDError");
			String Error = esearchjob.getMessage();
			setResultData("Result", 23, 4, "FAIL");
			setResultData("Result", 23, 5, Error);
		}
	}

	public void searchRTEJob()
			throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException {

		WebDriverWait wait = new WebDriverWait(Driver, 50);// wait time
		WebDriverWait wait2 = new WebDriverWait(Driver, 10);// wait time
		Actions act = new Actions(Driver);
		JavascriptExecutor js = (JavascriptExecutor) Driver;

		try {
			// --Go To Operations
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_operations")));
			WebElement Operations = isElementPresent("OperationsTab_id");
			act.moveToElement(Operations).build().perform();
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
				wait2.until(ExpectedConditions.visibilityOf(NoData));
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
					setResultData("Result", 24, 4, "PASS");

				}

			}
		} catch (Exception ewait) {
			getScreenshot(Driver, "SearchRTEError");
			String Error = ewait.getMessage();
			setResultData("Result", 24, 4, "FAIL");
			setResultData("Result", 24, 5, Error);

		}

	}

	public void rteConnectProcess() throws IOException {
		WebDriverWait wait = new WebDriverWait(Driver, 60);
		WebDriverWait wait2 = new WebDriverWait(Driver, 10);
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
		String jobStatus = isElementPresent("EOStageName_id").getText();
		logger.info("Job status is==" + jobStatus);
		msg.append("Job status is==" + jobStatus + "\n");

		logger.info("It is TC ACK stage");
		getScreenshot(Driver, "JobEditor_TCACK");

		// --Click on Acknowledge button
		wait.until(ExpectedConditions.elementToBeClickable(By.id("GreyTick")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		isElementPresent("TLAcknoldge_id").click();

		logger.info("Clicked on Acknowledge button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		WebElement PickUPSection = isElementPresent("TLAlertstages_id");
		wait.until(ExpectedConditions.visibilityOf(PickUPSection));
		getScreenshot(Driver, "JobEditor_RDYFORDSP");
		jobStatus = isElementPresent("EOStageName_id").getText();
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
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
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

	public void rteVerifyConnect() throws IOException {
		WebDriverWait wait = new WebDriverWait(Driver, 40);
		WebDriverWait wait2 = new WebDriverWait(Driver, 7);
		Actions act = new Actions(Driver);
		JavascriptExecutor js = (JavascriptExecutor) Driver;

		// search job
		try {
			searchRTEJob();
			logger.info("Data is exist with search parameter");
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
			getScreenshot(Driver, "JobEditor_Delivered");
			String jobStatus = isElementPresent("EOStageName_id").getText();
			logger.info("Job status is==" + jobStatus);
			msg.append("Job status is==" + jobStatus + "\n");

			if (jobStatus.contains("DELIVERED")) {
				logger.info("Job is Delivered successfully");

				// --Go to Edit Route
				try {
					WebElement EditOrTabSelect = isElementPresent("RoutestatTabSel_xpath");
					wait.until(ExpectedConditions.visibilityOf(EditOrTabSelect));
					logger.info("Edit Route tab is already selected");

				} catch (Exception eTabSelection) {
					// --Go to Job status tab is selected
					WebElement EditOrTabSelect = isElementPresent("EditOrderTabsel_xpath");
					wait.until(ExpectedConditions.visibilityOf(EditOrTabSelect));
					logger.info("Edit Route tab is not selected");

					// --Move to Edit Route
					WebElement JoStatusTab = isElementPresent("EditOrderTabsel_xpath");
					wait.until(ExpectedConditions.visibilityOf(JoStatusTab));
					wait.until(ExpectedConditions.elementToBeClickable(JoStatusTab));
					act.moveToElement(JoStatusTab).click().build().perform();
					logger.info("Clicked on Edit Route Tab");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				}

				// --End Route

				// --Click on End Route
				WebElement EndR = isElementPresent("TLEndRoute_id");
				wait.until(ExpectedConditions.visibilityOf(EndR));
				act.moveToElement(EndR).build().perform();
				act.moveToElement(EndR).click().perform();
				logger.info("Clicked on End Route");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				try {
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("errorid")));

					String Val = isElementPresent("Error_id").getText();
					logger.info("Validation is displayed==" + Val);

					if (Val.contains("Route End Date") && Val.contains("Route End Time")) {
						logger.info("Validation is displayed for Route End Date and Time==PASS");

						// --Enter Route End Date
						// --Get ZoneID
						String ZOneID = isElementPresent("TLERZone_xpath").getText();
						logger.info("ZoneID of is==" + ZOneID);

						// --Get the Date
						String REndDate = getDateAsTZone(ZOneID);

						// --Get the Time
						String REndTime = getTimeAsTZone(ZOneID);

						// --Route End Date
						WebElement ERDate = isElementPresent("TLERDate_id");
						ERDate.clear();
						ERDate.sendKeys(REndDate);
						ERDate.sendKeys(Keys.TAB);
						logger.info("Entered Actual Route End Date");

						// --Route End Time
						WebElement ERTime = isElementPresent("TLERTime_id");
						ERTime.clear();
						wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActualTime")));
						ERTime.sendKeys(REndTime);
						logger.info("Entered Actual Route End Time");

						// --Click on End Route
						EndR = isElementPresent("TLEndRoute_id");
						js.executeScript("arguments[0].scrollIntoView(true);", EndR);
						wait.until(ExpectedConditions.visibilityOf(EndR));
						act.moveToElement(EndR).build().perform();
						act.moveToElement(EndR).click().perform();
						logger.info("Clicked on End Route");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						try {
							wait2.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("errorid")));

							Val = isElementPresent("Error_id").getText();
							logger.info("Validation is displayed==" + Val);

							if (Val.contains(
									"Actual route end date should be same or greater then last stop's pod time."))
								;
							{
								logger.info(
										"Validation is displayed for route end date should be same or greater then last stop's pod time==PASS");

								routeEndDateValidation();
							}

						} catch (Exception EndTimeIssue) {
							logger.info(
									"Validation not displayed for route end date should be same or greater then last stop's pod time");

							WebElement EWSave = isElementPresent("TLQCExitWSave_id");
							wait.until(ExpectedConditions.visibilityOf(EWSave));
							act.moveToElement(EWSave).build().perform();
							act.moveToElement(EWSave).click().perform();
							logger.info("Clicked on Exit Without Save");
						}
					} else {
						logger.info("Validation is not displayed for Route End Date and Time==FAIL");

						WebElement EWSave = isElementPresent("TLQCExitWSave_id");
						wait.until(ExpectedConditions.visibilityOf(EWSave));
						act.moveToElement(EWSave).build().perform();
						act.moveToElement(EWSave).click().perform();
						logger.info("Clicked on Exit Without Save");
					}

				} catch (Exception EndRoute) {
					logger.info("Validation is not displayed for Route End Date and Time==FAIL");

				}
				// --Verify Customer Bill

				try {
					/*
					 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")
					 * )); String PickUpID = getData("SearchRTE", 1, 2);
					 * isElementPresent("TLBasicSearch_id").clear();
					 * isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
					 * logger.info("Entered PickUpID in basic search");
					 */

					// --Click on Search
					searchRTEJob();

					try {
						WebElement NoDataV = isElementPresent("NoData_className");
						wait.until(ExpectedConditions.visibilityOf(NoDataV));
						if (NoDataV.isDisplayed()) {
							logger.info("There is no Data with Search parameter");

						}

					} catch (Exception NoDatae) {
						logger.info("Data is exist with search parameter");
						wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
						getScreenshot(Driver, "JobEditor_Delivered");
						jobStatus = isElementPresent("EOStageName_id").getText();
						logger.info("Job status is==" + jobStatus);
						msg.append("Job status is==" + jobStatus + "\n");

						if (jobStatus.contains("VERIFY CUSTOMER BILL")) {
							logger.info("Job is moved to Verify Customer Bill stage");
							getScreenshot(Driver, "JobEditor_VerifyCustBill");

							// --Verify
							// --Zoom Out
							/*
							 * js.executeScript("document.body.style.zoom='80%';"); Thread.sleep(2000);
							 */
							// --Click on Verify button
							WebElement Verify = isElementPresent("TLVerify_id");
							wait.until(ExpectedConditions.visibilityOf(Verify));
							js.executeScript("arguments[0].click();", Verify);
							logger.info("Clicked on Verify button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							// --Verified
							// --Zoom IN
							/*
							 * js.executeScript("document.body.style.zoom='100%';"); Thread.sleep(2000);
							 */

							try {
								/*
								 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")
								 * )); PickUpID = getData("SearchRTE", 1, 2);
								 * isElementPresent("TLBasicSearch_id").clear();
								 * isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
								 * logger.info("Entered PickUpID in basic search");
								 */
								// --Click on Search
								searchRTEJob();

								try {
									WebElement NoDataV = isElementPresent("	");
									wait.until(ExpectedConditions.visibilityOf(NoDataV));
									if (NoDataV.isDisplayed()) {
										logger.info("There is no Data with Search parameter");

									}

								} catch (Exception NoDataee) {
									logger.info("Data is exist with search parameter");
									wait.until(ExpectedConditions
											.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
									getScreenshot(Driver, "JobEditor_Delivered");
									jobStatus = isElementPresent("EOStageName_id").getText();
									logger.info("Job status is==" + jobStatus);

									if (jobStatus.contains("VERIFIED")) {
										logger.info("Job is moved to VERIFIED stage");
										getScreenshot(Driver, "JobEditor_Verified");
										// PickUpID = getData("SearchRTE", 1, 2);
										msg.append("Job status is==." + jobStatus + "\n");
										msg.append("Job is Proceed successfully." + "\n");

									} else {
										logger.info("Job is not moved to VERIFIED stage");
										jobStatus = isElementPresent("EOStageName_id").getText();
										logger.info("Job status is==" + jobStatus);

										WebElement EWSave = isElementPresent("TLQCExitWSave_id");
										wait.until(ExpectedConditions.visibilityOf(EWSave));
										act.moveToElement(EWSave).build().perform();
										act.moveToElement(EWSave).click().perform();
										logger.info("Clicked on Exit Without Save");

									}

								}

								//

							} catch (Exception VerifyCBill) {
								logger.error(VerifyCBill);
								getScreenshot(Driver, "VerifyBillError");
								logger.info("job is not moved to VERIFIED stage");
								jobStatus = isElementPresent("EOStageName_id").getText();
								logger.info("Job status is==" + jobStatus);

								WebElement EWSave = isElementPresent("TLQCExitWSave_id");
								wait.until(ExpectedConditions.visibilityOf(EWSave));
								act.moveToElement(EWSave).build().perform();
								act.moveToElement(EWSave).click().perform();
								logger.info("Clicked on Exit Without Save");

							}

						} else {
							logger.info("Job is not moved to Verify Customer Bill stage");
							jobStatus = isElementPresent("EOStageName_id").getText();
							logger.info("Job status is==" + jobStatus);

							WebElement EWSave = isElementPresent("TLQCExitWSave_id");
							wait.until(ExpectedConditions.visibilityOf(EWSave));
							act.moveToElement(EWSave).build().perform();
							act.moveToElement(EWSave).click().perform();
							logger.info("Clicked on Exit Without Save");

						}

					}

					//

				} catch (Exception VerifyCBill) {
					logger.error(VerifyCBill);
					getScreenshot(Driver, "VerifyCBillError");
					logger.info("job is not moved to Verify Customer Bill stage");
					jobStatus = isElementPresent("EOStageName_id").getText();
					logger.info("Job status is==" + jobStatus);
				}

			} else {
				logger.info("Job is not Delivered successfully");
				jobStatus = isElementPresent("EOStageName_id").getText();
				logger.info("Job status is==" + jobStatus);

			}
		} catch (Exception e) {
			logger.info(e);
			getScreenshot(Driver, "RTEVerifyBillIssue");
			System.out.println("Issue in RTE verify Bill stage");
		}

	}

	public void routeEndDateValidation() throws InterruptedException, ParseException, java.text.ParseException {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		Actions act = new Actions(Driver);
		JavascriptExecutor js = (JavascriptExecutor) Driver;

		// --Move to shipment detail section
		WebElement ShipDetailsDiv = isElementPresent("TLShipMentDetails_xpath");
		js.executeScript("arguments[0].scrollIntoView(true);", ShipDetailsDiv);
		Thread.sleep(2000);

		// --Get the Total Shipment
		List<WebElement> SHipments = Driver.findElements(By.id("lblActDeliveryValue"));
		WebElement LastSHip = SHipments.get(SHipments.size() - 1);
		logger.info("LastShip==" + LastSHip);

		String LastDelDateTime = LastSHip.getText();
		logger.info("Last Del Date Time==" + LastDelDateTime);

		// 2022-08-18 05:58
		// --Get the DelTime only
		String LastDTime = LastDelDateTime;
		String[] lineSplits = LastDTime.split(" ");
		String LastDelTime = lineSplits[1].trim();
		logger.info("Last Del Time==" + LastDelTime);

		// --Add 1 minute in Last Del Time and set in End Time
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date d = df.parse(LastDelTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE, 1);
		String newTime = df.format(cal.getTime());
		System.out.println("New Time after add 1 minute is==" + newTime);

		// --Scroll Up
		WebElement RWDetails = isElementPresent("RWDetailSection_id");
		js.executeScript("arguments[0].scrollIntoView();", RWDetails);
		Thread.sleep(2000);

		// --Route End Time
		WebElement ERTime = isElementPresent("TLERTime_id");
		ERTime.clear();
		ERTime.sendKeys(newTime);
		ERTime.sendKeys(Keys.TAB);
		logger.info("Entered Actual Route End Time");

		// --Click on End Route
		WebElement EndR = isElementPresent("TLEndRoute_id");
		js.executeScript("arguments[0].scrollIntoView(true);", EndR);
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOf(EndR));
		act.moveToElement(EndR).build().perform();
		wait.until(ExpectedConditions.elementToBeClickable(EndR));
		act.moveToElement(EndR).click().perform();
		logger.info("Clicked on End Route");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
	}

}
