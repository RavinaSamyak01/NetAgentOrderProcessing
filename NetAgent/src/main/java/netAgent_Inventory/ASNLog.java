package netAgent_Inventory;

import java.util.Calendar;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class ASNLog extends BaseInit {
	@Test
	public void aSNLog() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		Actions act = new Actions(Driver);

		logger.info("=======ASN Log Test Start=======");
		// msg.append("=======ASN Log Test Start=======" + "\n\n");
		try {
			// --Inventory
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idInventory")));
			Driver.findElement(By.id("idInventory")).click();
			logger.info("Clicked on Inventory");
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@aria-labelledby=\"idInventory\"]")));

			// --ASN
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idASN")));
			Driver.findElement(By.id("idASN")).click();
			logger.info("Clicked on ASN Log");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			getScreenshot(Driver, "ASNLog");
			/*
			 * // Search with Tracking#
			 * Driver.findElement(By.id("txtTracking")).sendKeys(formatter.formatCellValue(
			 * sh0.getRow(2).getCell(19))); Thread.sleep(2000);
			 * Driver.findElement(By.id("idbtnRunSearch")).click();
			 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
			 * "//*[@class=\"ajax-loadernew\"]")));
			 * 
			 * // -Created ASN Doesnt have tracking ID
			 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
			 * "//*[@class=\"ajax-loadernew\"]")));
			 * System.out.println("Title of the screen is" + Driver.getTitle()); String
			 * Trackexp = formatter.formatCellValue(sh0.getRow(2).getCell(19)); String
			 * Trackact = Driver.findElement(By.
			 * xpath("//td[@role=\"gridcell\" and contains(@aria-label,'Tracking')]"))
			 * .getText(); System.out.println(Trackexp); System.out.println(Trackact);
			 * 
			 * if (Trackexp.equals(Trackact)) {
			 * System.out.println("Tracking Number search result is PASS"); } else {
			 * System.out.println("Tracking Number search result is FAIL"); } // Back to
			 * screen Driver.findElement(By.id("hlkBackToScreen")).click();
			 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
			 * "//*[@class=\"ajax-loadernew\"]")));
			 * Driver.findElement(By.id("txtTracking")).clear();
			 */

			// --Get the data from Excel
			String ASN = getData("Sheet1", 2, 20);

			// Search with ASN# and go to ASN Details screen
			Driver.findElement(By.xpath("//input[@id='txtASN']")).sendKeys(ASN);
			logger.info("Enter value in ASN");
			Driver.findElement(By.id("idbtnRunSearch")).click();
			logger.info("Clicked on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			System.out.println("Title of the screen is" + Driver.getTitle());
			logger.info("Title of the screen is" + Driver.getTitle());

			String ASNact = Driver.findElement(By.id("txtasnno")).getAttribute("value");
			System.out.println(ASN);
			logger.info("Entered ASN==" + ASN);
			System.out.println(ASNact);
			logger.info("Searched ASN==" + ASNact);

			if (ASN.equals(ASNact)) {
				System.out.println("ASN Number search result is PASS");
				logger.info("ASN Number search result is PASS");
			} else {
				System.out.println("ASN Number search result is FAIL");
				logger.info("ASN Number search result is FAIL");
			}

			// Expand and Collapse in ASN details screen
			Driver.findElement(By.id("expandId")).click();
			logger.info("Expand ASN Detail");
			Thread.sleep(2000);
			logger.info("Expand ASN Details==PASS");

			getScreenshot(Driver, "ASN Log Details");

			Driver.findElement(By.id("collapseId")).click();
			logger.info("Collapse ASN Detail");
			Thread.sleep(2000);
			logger.info("Collapse ASN Details==PASS");

			// Go back to ASNLog screen
			Driver.findElement(By.id("hlkBackToScreen")).click();
			logger.info("Click on Back button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			Driver.findElement(By.id("txtASN")).clear();
			logger.info("Cleared ASN");
			Driver.findElement(By.id("idbtnRunSearch")).click();
			logger.info("Click on Search");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			try {
				// Go back to ASNLog screen
				Driver.findElement(By.id("hlkBackToScreen")).click();
				logger.info("Click on Back button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception ee) {

			}
			// Search with ASN Type
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("btn_cmbAsnTypeclass=")));
			Driver.findElement(By.id("btn_cmbAsnTypeclass=")).click();
			logger.info("Click on ASNType dropdown");
			Thread.sleep(2000);
			Driver.findElement(By.id("chkAllcmbAsnType")).click();
			logger.info("Select all");
			Driver.findElement(By.id("btn_cmbAsnTypeclass=")).click();
			logger.info("Close ASNType dropdown");
			Driver.findElement(By.id("idbtnRunSearch")).click();
			logger.info("Click on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			try {
				// Go back to ASNLog screen
				Driver.findElement(By.id("hlkBackToScreen")).click();
				logger.info("Click on Back button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception ee) {

			}
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("btn_cmbAsnTypeclass=")));
			Driver.findElement(By.id("btn_cmbAsnTypeclass=")).click();
			logger.info("Click on ASNType dropdown");
			Thread.sleep(2000);
			Driver.findElement(By.id("chkAllcmbAsnType")).click();
			Thread.sleep(5000);
			logger.info("Deselect all");
			Driver.findElement(By.id("btn_cmbAsnTypeclass=")).click();
			logger.info("Close ASNType dropdown");
			Driver.findElement(By.id("idbtnRunSearch")).click();
			logger.info("Click on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			try {
				// Go back to ASNLog screen
				Driver.findElement(By.id("hlkBackToScreen")).click();
				logger.info("Click on Back button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception ee) {

			}
			// Search with ASN Status
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("btn_cmbAsnStatusclass=")));
			Driver.findElement(By.id("btn_cmbAsnStatusclass=")).click();
			logger.info("Click on ASN Status dropdown");
			Thread.sleep(2000);
			Driver.findElement(By.id("chkAllcmbAsnStatus")).click();
			logger.info("Select all");
			Driver.findElement(By.id("btn_cmbAsnStatusclass=")).click();
			logger.info("Close ASN Status dropdown");
			Driver.findElement(By.id("idbtnRunSearch")).click();
			logger.info("Click on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			try {
				// Go back to ASNLog screen
				Driver.findElement(By.id("hlkBackToScreen")).click();
				logger.info("Click on Back button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception ee) {

			}
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("btn_cmbAsnStatusclass=")));
			Driver.findElement(By.id("btn_cmbAsnStatusclass=")).click();
			logger.info("Click on ASN Status dropdown");
			Thread.sleep(3000);
			Driver.findElement(By.id("chkAllcmbAsnStatus")).click();
			Thread.sleep(5000);
			logger.info("Deselect all");
			Driver.findElement(By.id("btn_cmbAsnStatusclass=")).click();
			logger.info("Close ASN Status dropdown");
			Driver.findElement(By.id("idbtnRunSearch")).click();
			logger.info("Click on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			try {
				// Go back to ASNLog screen
				Driver.findElement(By.id("hlkBackToScreen")).click();
				logger.info("Click on Back button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception ee) {

			}
			// Search with Carrier - Fedex
			/*
			 * wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(
			 * "btn_cmbAsnCarrierclass=")));
			 * Driver.findElement(By.id("btn_cmbAsnCarrierclass=")).click();
			 * Thread.sleep(2000); Driver.findElement(By.id("chkAllcmbAsnCarrier")).click();
			 * Driver.findElement(By.id("btn_cmbAsnCarrierclass=")).click();
			 * Driver.findElement(By.id("idbtnRunSearch")).click();
			 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
			 * "//*[@class=\"ajax-loadernew\"]")));
			 * 
			 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(
			 * "hlkBackToScreen"))); Driver.findElement(By.id("hlkBackToScreen")).click();
			 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
			 * "//*[@class=\"ajax-loadernew\"]")));
			 * 
			 * Driver.findElement(By.id("btn_cmbAsnCarrierclass=")).click();
			 * Thread.sleep(2000); Driver.findElement(By.id("chkAllcmbAsnCarrier")).click();
			 * Thread.sleep(2000); Driver.findElement(By.id("chkAllcmbAsnCarrier")).click();
			 * Driver.findElement(By.id("btn_cmbAsnCarrierclass=")).click();
			 * Driver.findElement(By.id("idbtnRunSearch")).click();
			 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
			 * "//*[@class=\"ajax-loadernew\"]")));
			 */

			// Search with Location
			Select Location = new Select(Driver.findElement(By.id("ddlFsl")));
			Location.selectByIndex(1);
			logger.info("Select Location");
			Thread.sleep(2000);
			Driver.findElement(By.id("idbtnRunSearch")).click();
			logger.info("Click on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			String Locationexp = getData("Sheet1", 2, 39);
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//td[@role=\"gridcell\" and contains(@aria-label,'Location')]")));
			String Locationact = Driver
					.findElement(By.xpath("//td[@role=\"gridcell\" and contains(@aria-label,'Location')]")).getText();
			System.out.println(Locationexp);
			logger.info("Entered Location==" + Locationexp);
			System.out.println(Locationact);
			logger.info("Searched Location==" + Locationact);

			if (Locationexp.equals(Locationact)) {
				System.out.println("Location search result is PASS");
				logger.info("Location search result is PASS");
			} else {
				System.out.println("Location search result is FAIL");
				logger.info("Location search result is FAIL");
			}

			Select Account = new Select(Driver.findElement(By.id("drpAccount")));
			Account.selectByIndex(1);
			logger.info("Select Account");
			Thread.sleep(2000);
			Driver.findElement(By.id("idbtnRunSearch")).click();
			logger.info("Click on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			Select Location1 = new Select(Driver.findElement(By.id("ddlFsl")));
			Location1.selectByIndex(0);
			logger.info("Deselect Location");
			Thread.sleep(2000);
			Driver.findElement(By.id("idbtnRunSearch")).click();
			logger.info("Click on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			// From date and To date selection
			Calendar cal = Calendar.getInstance();
			// cal.add(Calendar.DATE, -30);
			String ValiFrom = getDate(cal);
			System.out.println("Valid From Date :- " + ValiFrom);
			logger.info("Valid From Date :- " + ValiFrom);
			String ValiTo = getDate(cal);
			System.out.println("Valid To Date :- " + ValiTo);
			logger.info("Valid To Date :- " + ValiTo);

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtFromEstArrival")));
			Driver.findElement(By.id("txtFromEstArrival")).sendKeys(ValiFrom);
			logger.info("Enter From Est. Arrival");
			Driver.findElement(By.id("txtToEstArrival")).sendKeys(ValiTo);

			Driver.findElement(By.id("txtASN")).clear();
			logger.info("Clear ASN");
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idbtnRunSearch")));
			WebElement RSearch = Driver.findElement(By.id("idbtnRunSearch"));
			act.moveToElement(RSearch).build().perform();
			act.moveToElement(RSearch).click().perform();
			logger.info("Click on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			try {
				Driver.findElement(By.id("hlkBackToScreen")).click();
				logger.info("Click on Back button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception e) {
				System.out.println("There is no data with enetered date");
				logger.info("There is no data with enetered date");
			}
			Driver.findElement(By.id("txtFromEstArrival")).clear();
			logger.info("Cleared FromESTArrival");
			Driver.findElement(By.id("txtToEstArrival")).clear();
			logger.info("Cleared ToESTArrival");

			try {
				Driver.findElement(By.id("idbtnRunSearch")).click();
				logger.info("Click on Search button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception e) {
				WebElement Search = Driver.findElement(By.id("idbtnRunSearch"));
				act.moveToElement(Search).build().perform();
				act.moveToElement(Search).click().perform();

			}

			Driver.findElement(By.id("txtAsnFromDate")).sendKeys(ValiFrom);
			logger.info("Enter ASNFromDate");
			Driver.findElement(By.id("txtAsnToDate")).sendKeys(ValiTo);
			logger.info("Enter ASNToDate");
			try {
				Driver.findElement(By.id("idbtnRunSearch")).click();
				logger.info("Click on Search button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception e) {
				WebElement Search = Driver.findElement(By.id("idbtnRunSearch"));
				act.moveToElement(Search).build().perform();
				act.moveToElement(Search).click().perform();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			}
			try {
				Driver.findElement(By.id("hlkBackToScreen")).click();
				logger.info("Click on Back button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception e) {
				System.out.println("There is no data with enetered date");
				logger.info("There is no data with enetered date");
			}
			Driver.findElement(By.id("txtAsnFromDate")).clear();
			logger.info("Cleared ASNFromDate");
			Driver.findElement(By.id("txtAsnToDate")).clear();
			logger.info("Cleared ASNToDate");
			try {
				Driver.findElement(By.id("idbtnRunSearch")).click();
				logger.info("Click on Search button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception e) {
				WebElement Search = Driver.findElement(By.id("idbtnRunSearch"));
				act.moveToElement(Search).build().perform();
				act.moveToElement(Search).click().perform();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			}
			try {
				Driver.findElement(By.id("hlkBackToScreen")).click();
				logger.info("Click on Back button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception e) {
				System.out.println("There is no data with enetered date");
				logger.info("There is no data with enetered date");
			}
			getScreenshot(Driver, "ASNLog_1");

			Driver.findElement(By.id("txtTracking")).sendKeys("Test1234");
			logger.info("Enter Tracking No");
			try {
				Driver.findElement(By.id("idbtnRunSearch")).click();
				logger.info("Click on Search button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception e) {
				WebElement Search = Driver.findElement(By.id("idbtnRunSearch"));
				act.moveToElement(Search).build().perform();
				act.moveToElement(Search).click().perform();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			}
			String NoData = Driver.findElement(By.className("dx-datagrid-nodata")).getText();
			logger.info("Records==" + NoData);
			Driver.findElement(By.id("txtTracking")).clear();
			logger.info("Cleared Tracking");

			Driver.findElement(By.id("txtWorkOrder")).sendKeys("1234567890");
			logger.info("Enter WorkOrder");
			try {
				Driver.findElement(By.id("idbtnRunSearch")).click();
				logger.info("Click on Search button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception e) {
				WebElement Search = Driver.findElement(By.id("idbtnRunSearch"));
				act.moveToElement(Search).build().perform();
				act.moveToElement(Search).click().perform();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			}
			NoData = Driver.findElement(By.className("dx-datagrid-nodata")).getText();
			logger.info("Records==" + NoData);
			Driver.findElement(By.id("txtWorkOrder")).clear();
			logger.info("Clear WorkOrder");

			Driver.findElement(By.id("txtASN")).sendKeys("1234567890");
			logger.info("Enter ASN");
			try {
				Driver.findElement(By.id("idbtnRunSearch")).click();
				logger.info("Click on Search button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception e) {
				WebElement Search = Driver.findElement(By.id("idbtnRunSearch"));
				act.moveToElement(Search).build().perform();
				act.moveToElement(Search).click().perform();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			}
			NoData = Driver.findElement(By.className("dx-datagrid-nodata")).getText();
			logger.info("Records==" + NoData);
			Driver.findElement(By.id("txtASN")).clear();
			logger.info("Clear ASN");

			Driver.findElement(By.id("txtAsnRef")).sendKeys("Test1234");
			logger.info("Enter ASN ref");
			try {
				Driver.findElement(By.id("idbtnRunSearch")).click();
				logger.info("Click on Search button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception e) {
				WebElement Search = Driver.findElement(By.id("idbtnRunSearch"));
				act.moveToElement(Search).build().perform();
				act.moveToElement(Search).click().perform();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			}
			NoData = Driver.findElement(By.className("dx-datagrid-nodata")).getText();
			logger.info("Records==" + NoData);
			Driver.findElement(By.id("txtAsnRef")).clear();
			logger.info("Clear ASN Ref");
			Driver.findElement(By.id("txtFromEstArrival")).clear();
			logger.info("Cleared FromESTArrival");
			Driver.findElement(By.id("txtTracking")).click();
			Thread.sleep(1000);
			Driver.findElement(By.id("txtToEstArrival")).clear();
			Thread.sleep(1000);
			Driver.findElement(By.id("txtTracking")).click();
			Thread.sleep(1000);

			logger.info("Cleared ToESTArrival");

			try {
				Driver.findElement(By.id("idbtnRunSearch")).click();
				logger.info("Click on Search button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception e) {
				WebElement Search = Driver.findElement(By.id("idbtnRunSearch"));
				act.moveToElement(Search).build().perform();
				act.moveToElement(Search).click().perform();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			}

			try {
				WebElement NextPage = Driver.findElement(By.xpath("//*[@aria-label=\" Next page\"]"));
				wait.until(ExpectedConditions.visibilityOf(NextPage));
				logger.info("Testing Pagination");
				pagination();

			} catch (Exception e) {
				logger.info("There is no more than 1 page");

			}

			// Click on ASN No.
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hrfAct")));
			WebElement ASNO = Driver.findElement(By.id("hrfAct"));
			act.moveToElement(ASNO).build().perform();
			act.moveToElement(ASNO).click().build().perform();
			logger.info("Click on ASN No");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			Thread.sleep(5000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtasnno")));

			Boolean ASNNo = Driver.findElement(By.id("txtasnno")).getAttribute("readonly").equals("");
			if (ASNNo == true) {
				System.out.println("ASNNo. field is Editable");
				logger.info("ASNNo. field is Editable");
			} else {
				System.out.println("ASNNo. field is Non-editable");
				logger.info("ASNNo. field is Non-editable");
			}

			Boolean ASNType = Driver.findElement(By.id("txtASNType")).getAttribute("readonly").equals("");
			if (ASNType == true) {
				System.out.println("ASNType field is Editable");
				logger.info("ASNType field is Editable");
			} else {
				System.out.println("ASNType field is Non-editable");
				logger.info("ASNType field is Non-editable");
			}

			Boolean AccountNum = Driver.findElement(By.id("txtacc")).getAttribute("readonly").equals("");
			if (AccountNum == true) {
				System.out.println("Account Number field is Editable");
				logger.info("Account Number field is Editable");
			} else {
				System.out.println("Account Number field is Non-editable");
				logger.info("Account Number field is Non-editable");
			}

			Boolean Locfield = Driver.findElement(By.id("txtfsl")).getAttribute("readonly").equals("");
			if (Locfield == true) {
				System.out.println("Location field is Editable");
				logger.info("Location field is Editable");
			} else {
				System.out.println("Location field is Non-editable");
				logger.info("Location field is Non-editable");
			}

			Boolean ASNRef = Driver.findElement(By.id("txtasnref")).getAttribute("readonly").equals("");
			if (ASNRef == true) {
				System.out.println("ASN Reference field is Editable");
				logger.info("ASN Reference field is Editable");
			} else {
				System.out.println("ASN Reference field is Non-editable");
				logger.info("ASN Reference field is Non-editable");
			}

			Boolean CarrierName = Driver.findElement(By.id("txtcarriername")).getAttribute("readonly").equals("");
			if (CarrierName == true) {
				System.out.println("Carrier Name field is Editable");
				logger.info("Carrier Name field is Editable");
			} else {
				System.out.println("Carrier Name field is Non-editable");
				logger.info("Carrier Name field is Non-editable");
			}

			Boolean TrackingNum = Driver.findElement(By.id("txttrackingno")).getAttribute("readonly").equals("");
			if (TrackingNum == true) {
				System.out.println("Tracking Number field is Editable");
				logger.info("Tracking Number field is Editable");
			} else {
				System.out.println("Tracking Number field is Non-editable");
				logger.info("Tracking Number field is Non-editable");
			}

			Boolean RefURL = Driver.findElement(By.id("txtreferenceurl")).getAttribute("readonly").equals("");
			if (RefURL == true) {
				System.out.println("RefURL field is Editable");
				logger.info("RefURL field is Editable");
			} else {
				System.out.println("RefURL field is Non-editable");
				logger.info("RefURL field is Non-editable");
			}

			Boolean Note = Driver.findElement(By.id("txtnotes")).getAttribute("readonly").equals("");
			if (Note == true) {
				System.out.println("Note field is Editable");
				logger.info("Note field is Editable");
			} else {
				System.out.println("Note field is Non-editable");
				logger.info("Note field is Non-editable");
			}

			Driver.findElement(By.id("hlkBackToScreen")).click();
			logger.info("Click on Back button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			WebElement nextpage = Driver.findElement(By.xpath("//*[@aria-label=\" Next page\"]"));
			act.moveToElement(nextpage).click().perform();
			System.out.println("clicked on next page");
			logger.info("Click on next page");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			String P1 = Driver.findElement(By.xpath("//*[@class=\"dx-info\"]")).getText();
			System.out.println(P1);
			logger.info("No.Of Records==" + P1);

			WebElement prevpage = Driver.findElement(By.xpath("//*[@aria-label=\"Previous page\"]"));
			act.moveToElement(prevpage).click().perform();
			System.out.println("clicked on previous page");
			logger.info("Click on Previous Page");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			String P2 = Driver.findElement(By.xpath("//*[@class=\"dx-info\"]")).getText();
			System.out.println(P2);
			logger.info("No.Of Records==" + P2);

			// Export Action
			// Delete existing file
			isFileDownloaded("ASN-");

			Driver.findElement(By.id("idbtnexport")).click();
			logger.info("Click on Export button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			waitUntilFileToDownload("ASN-");
			getScreenshot(Driver, "ASNLog_2");

			logger.info("ASN Log Test=PASS");
			msg.append("ASN Log Test=PASS" + "\n\n");
			setResultData("Result", 21, 5, "PASS");
		} catch (Exception ASNLogE) {
			logger.error(ASNLogE);
			getScreenshot(Driver, "ASNLog_error");
			logger.info("ASN Log Test=FAIL");
			msg.append("ASN Log Test=FAIL" + "\n\n");
			String Error = ASNLogE.getMessage();

			setResultData("Result", 21, 5, "FAIL");
			setResultData("Result", 21, 6, Error);

		}

		logger.info("=======ASN Log Test End=======");
		// msg.append("=======ASN Log Test End=======" + "\n\n");
		msg.append("=======Inventory Tab Test End=======" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}

	// --Not exist in new NetAgent app
	/*
	 * @Test public void NetlinkInv() throws Exception { WebDriverWait wait = new
	 * WebDriverWait(Driver, 50);
	 * 
	 * Thread.sleep(10000);
	 * Driver.findElement(By.partialLinkText("Inventory")).click();
	 * 
	 * Driver.findElement(By.linkText("Netlink Inventory")).click();
	 * Thread.sleep(10000);
	 * 
	 * Driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/a/img")).click();
	 * Thread.sleep(10000); }
	 */
}
