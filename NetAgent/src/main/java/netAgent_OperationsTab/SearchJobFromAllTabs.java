package netAgent_OperationsTab;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.eclipse.sisu.reflect.Logs;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import connect_OCBaseMethods.OrderCreation;
import connect_OCBaseMethods.ReadyForDispatch;
import connect_OCBaseMethods.SendPull;
import connect_OCBaseMethods.TCAcknowledge;
import netAgent_BasePackage.BaseInit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SearchJobFromAllTabs extends BaseInit {

	@Test
	public void searchLocFromAllTabs() throws Exception {

		msg.append("\n\n" + "=======Search Job from All the Tabs Test start=======" + "\n");

		try {
			// --Create LOC job from connect and process to confirm stage
			createLOCJob();

			OrderCreation OC = new OrderCreation();

			String PUID = getData("OrderCreation", 11, 32);
			logger.info("PickUpID is==" + PUID);
			msg.append("PickUpID==" + PUID + "\n");

			// -Open NetAGent and move to tasklog
			// opentasklog();

			// --Search the Job from Operation Tab
			searchOperationTab();

			// --Search the Job from Inventory Tab
			searchInventoryTab();

			// --Search the Job from Combined Tab
			searchCombinedTab();

			// --Search the Job from RTE Tab
			searchRTETab();
		} catch (Exception e) {
			logger.info(e);
			getScreenshot(Driver, "SearchAlljobError");
		}

		msg.append("\n" + "=======Search Job from All the Tabs Test End=======" + "\n");

	}

	public void searchRTETab()
			throws IOException, EncryptedDocumentException, InvalidFormatException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		String TrackingID = getData("RTE", 1, 1);
		logger.info("TrackingID is==" + TrackingID);
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

		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@ng-model=\"RTE\"]")));
		isElementPresent("TLRTETab_xpath").click();
		logger.info("Click on RTE Tab");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RTE")));
		getScreenshot(Driver, "RTETab");

		String TotalJob = isElementPresent("TLTotalJob_xpath").getText();
		System.out.println("Total No of job in RTE Tab is/are==" + TotalJob);
		logger.info("Total No of job in RTE Tab is/are==" + TotalJob);

		// --Search
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearchRTE")));
		isElementPresent("TLRTESearch_id").clear();
		logger.info("Cleared Search box");
		isElementPresent("TLRTESearch_id").sendKeys(TrackingID);
		logger.info("Entered value in Search box");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnRTESearch2")));
		isElementPresent("TLRTESearchBTN_id").click();
		logger.info("Click on Search button of RTE tab");

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		TotalJob = isElementPresent("TLTotalJob_xpath").getText();
		System.out.println("Total No of job in RTE Tab is/are==" + TotalJob);
		logger.info("Total No of job in RTE Tab is/are==" + TotalJob);

		if (TotalJob.contains("1")) {
			logger.info("Job is found in RTE Tab");
			msg.append("Job is found in RTE Tab==PASS" + "\n");
			setResultData("Result", 14, 5, "PASS");

		} else {
			logger.info("Job is not found in RTE Tab");
			msg.append("Job is not found in RTE Tab==FAIL" + "\n");
			setResultData("Result", 14, 5, "FAIL");
		}

	}

	public void searchOperationTab() throws EncryptedDocumentException, InvalidFormatException, IOException {
		WebDriverWait wait = new WebDriverWait(Driver, 60);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		try {
			String PUID = getData("OrderCreation", 11, 32);
			logger.info("PickUpID is==" + PUID);
			setResultData("Result", 9, 3, PUID);

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

			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//*[@id=\"operation\"][contains(@class,'active')]")));
				logger.info("Operation tab is already selected");

			} catch (Exception Operation) {
				logger.info("Operation tab is not selected");
				WebElement OpTab = isElementPresent("NTOpeTab_xpath");
				wait.until(ExpectedConditions.visibilityOf(OpTab));
				wait.until(ExpectedConditions.elementToBeClickable(OpTab));
				act.moveToElement(OpTab).build().perform();
				js.executeScript("arguments[0].click();", OpTab);
				logger.info("Click on Operation Tab");
				// wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			}
			try {
				WebElement OpTab = isElementPresent("NTOpeTab_xpath");
				wait.until(ExpectedConditions.visibilityOf(OpTab));
				wait.until(ExpectedConditions.elementToBeClickable(OpTab));
				act.moveToElement(OpTab).build().perform();
				js.executeScript("arguments[0].click();", OpTab);
				logger.info("Click on Operation Tab");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception Operation) {
				logger.info("Operation tab is selected");

			}
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

			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception e) {
				WebDriverWait wait1 = new WebDriverWait(Driver, 180);
				wait1.until(
						ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			}

			getScreenshot(Driver, "SearchJobOperationTab");

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
				WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
				if (NoData.isDisplayed()) {
					logger.info("Job is not found in Operation Tab");
					msg.append("Job is not found in Operation Tab==FAIL" + "\n");
					setResultData("Result", 10, 5, "FAIL");

				}
			} catch (Exception e) {
				logger.info("Job is found in Operation Tab");
				msg.append("Job is found in Operation Tab==PASS" + "\n");
				setResultData("Result", 10, 5, "PASS");

				try {
					// --Click on Close button
					isElementPresent("TLCloseTab_id").click();
					logger.info("Clicked on close button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				} catch (Exception close) {
					isElementPresent("TLIconClose_id").click();
					logger.info("Clicked on close button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				}
			}
		} catch (Exception esearchjob) {
			logger.error(esearchjob);
			getScreenshot(Driver, "SearchFrmOperationError");
			logger.info("Search From Operation Tab Test=FAIL");
			msg.append("Search From Operation Tab Test=FAIL" + "\n\n");
			String Error = esearchjob.getMessage();
			setResultData("Result", 10, 5, "FAIL");
			setResultData("Result", 10, 6, Error);

		}

	}

	public void searchCombinedTab() throws EncryptedDocumentException, InvalidFormatException, IOException {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		try {
			String PUID = getData("OrderCreation", 11, 32);
			logger.info("PickUpID is==" + PUID);
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

			// --Go to Combined tab
			WebElement CombTab = isElementPresent("TLCombinedTab_id");
			wait.until(ExpectedConditions.visibilityOf(CombTab));
			wait.until(ExpectedConditions.elementToBeClickable(CombTab));
			CombTab.click();
			logger.info("Click on Combined tab");
			WebElement SearchIn = isElementPresent("TLBasicSearch_id");
			wait.until(ExpectedConditions.visibilityOf(SearchIn));
			wait.until(ExpectedConditions.elementToBeClickable(SearchIn));
			SearchIn.clear();
			logger.info("Clear search input");
			SearchIn.sendKeys(PUID);
			logger.info("Enter pickupID in search input");

			WebElement InvSearch = isElementPresent("TLComSearch_id");
			act.moveToElement(InvSearch).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(InvSearch));
			js.executeScript("arguments[0].click();", InvSearch);
			logger.info("Click on Search button");
			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception e) {
				WebDriverWait wait1 = new WebDriverWait(Driver, 180);
				wait1.until(
						ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			}

			getScreenshot(Driver, "SearchJobCombinedTab");
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
				WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
				if (NoData.isDisplayed()) {
					logger.info("Job is not available in Combined Tab");
					msg.append("Job is not found in Combined Tab==FAIL" + "\n");
					setResultData("Result", 12, 5, "FAIL");

				}
			} catch (Exception e1) {
				logger.info("Job is available in Combined Tab");
				msg.append("Job is found in Combined Tab==PASS" + "\n");
				setResultData("Result", 12, 5, "PASS");

				try {
					try {
						// --Click on Close button
						isElementPresent("TLCloseTab_id").click();
						logger.info("Clicked on close button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					} catch (Exception close) {
						isElementPresent("TLIconClose_id").click();
						logger.info("Clicked on close button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					}
				} catch (Exception ee) {
					logger.info("Job is not opened in Edit mode");
				}

			}
		} catch (Exception esearchjob) {
			logger.error(esearchjob);
			getScreenshot(Driver, "SearchFrmCombinedError");
			logger.info("Search From Combined Tab Test=FAIL");
			msg.append("Search From Combined Tab Test=FAIL" + "\n\n");
			String Error = esearchjob.getMessage();
			setResultData("Result", 12, 5, "FAIL");
			setResultData("Result", 12, 6, Error);

		}

	}

	public void searchInventoryTab() throws EncryptedDocumentException, InvalidFormatException, IOException {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		try {
			String PUID = getData("OrderCreation", 11, 32);
			logger.info("PickUpID is==" + PUID);
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

			// --Go to inventory tab
			WebElement InvTab = isElementPresent("NAInvTab_id");
			wait.until(ExpectedConditions.visibilityOf(InvTab));
			wait.until(ExpectedConditions.elementToBeClickable(InvTab));
			InvTab.click();
			logger.info("Click on Inventory tab");
			WebElement SearchIn = isElementPresent("NAInvSearch_id");
			wait.until(ExpectedConditions.visibilityOf(SearchIn));
			wait.until(ExpectedConditions.elementToBeClickable(SearchIn));
			SearchIn.clear();
			logger.info("Clear search input");
			SearchIn.sendKeys(PUID);
			logger.info("Enter pickupID in search input");

			WebElement InvSearch = isElementPresent("NAInvSearchBtn_id");
			act.moveToElement(InvSearch).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(InvSearch));
			js.executeScript("arguments[0].click();", InvSearch);
			logger.info("Click on Search button");
			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception e) {
				WebDriverWait wait1 = new WebDriverWait(Driver, 180);
				wait1.until(
						ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			}

			getScreenshot(Driver, "SearchJobInventoryTab");

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
				WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
				if (NoData.isDisplayed()) {
					logger.info("Job is not available in Inventory Tab");
					msg.append("Job is not found in Inventory Tab==FAIL" + "\n");
					setResultData("Result", 11, 5, "FAIL");

				}
			} catch (Exception e1) {
				logger.info("Job is available in Inventory Tab");
				msg.append("Job is found in Inventory Tab==PASS" + "\n");
				setResultData("Result", 11, 5, "PASS");

				try {
					// --Click on Close button
					isElementPresent("TLCloseTab_id").click();
					logger.info("Clicked on close button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				} catch (Exception close) {
					isElementPresent("TLIconClose_id").click();
					logger.info("Clicked on close button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				}

			}
		} catch (Exception esearchjob) {
			logger.error(esearchjob);
			getScreenshot(Driver, "SearchFrmInventoryError");
			logger.info("Search From Inventory Tab Test=FAIL");
			msg.append("Search From Inventory Tab Test=FAIL" + "\n\n");
			String Error = esearchjob.getMessage();
			setResultData("Result", 11, 5, "FAIL");
			setResultData("Result", 11, 6, Error);

		}

	}

	public void opentasklog() throws Exception {

		WebDriverWait wait = new WebDriverWait(Driver, 40);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		// --NetAgent Login
		// Login();

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

		String PUID = getData("OrderCreation", 11, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"operation\"][contains(@class,'active')]")));
			logger.info("Operation tab is already selected");

		} catch (Exception Operation) {
			logger.info("Operation tab is not selected");
			WebElement OpTab = isElementPresent("NTOpeTab_xpath");
			wait.until(ExpectedConditions.visibilityOf(OpTab));
			wait.until(ExpectedConditions.elementToBeClickable(OpTab));
			act.moveToElement(OpTab).build().perform();
			js.executeScript("arguments[0].click();", OpTab);
			logger.info("Click on Operation Tab");
			// wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		}

	}

	public void createLOCJob() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		try {
			// Open new tab
			jse = (JavascriptExecutor) Driver;
			jse.executeScript("window.open()");

			// --Moved to tab
			ArrayList<String> tabs = new ArrayList<String>(Driver.getWindowHandles());
			Driver.switchTo().window(tabs.get(1));

			// --Connect Login

			Connectlogin();

			// -- Activate Account

			ActivateAccount();
			OrderCreation OC = new OrderCreation();

			try {
				RTEJobCreationProcess RTEJCP = new RTEJobCreationProcess();
				RTEJCP.rtejobcreationprocess();
			} catch (Exception e) {
				logger.info("RTE send Pu Alert failed");
			}

			OC.refreshApp();

			// --Order Creation
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

			Driver.close();

			// --NetAgent Tab
			OC.naTab();
			setResultData("Result", 9, 5, "PASS");

		} catch (Exception e) {
			logger.info(e);
			getScreenshot(Driver, "SAllJobD3pIssue");
			String Error = e.getMessage();
			setResultData("Result", 9, 5, "FAIL");
			setResultData("Result", 9, 6, Error);

		}

	}

}
