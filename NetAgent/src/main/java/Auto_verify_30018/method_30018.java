package Auto_verify_30018;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import connect_OCBaseMethods.OrderCreation;
import netAgent_BasePackage.BaseInit;

public class method_30018 extends BaseInit {
	
	public static String getData(String sheetName, int row, int col)
			throws EncryptedDocumentException, InvalidFormatException, IOException {

		String Env = storage.getProperty("Env");
		String FilePath = null;
		if (Env.equalsIgnoreCase("Pre-Prod")) {
			FilePath = storage.getProperty("PrePRODFile");
		} else if (Env.equalsIgnoreCase("STG")) {
			FilePath = storage.getProperty("STGFile");
		} else if (Env.equalsIgnoreCase("DEV")) {
			FilePath = storage.getProperty("DEVFile");
		}

		File src = new File(FilePath);

		FileInputStream FIS = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(FIS);
		Sheet sh1 = workbook.getSheet(sheetName);

		DataFormatter formatter = new DataFormatter();
		String Cell = formatter.formatCellValue(sh1.getRow(row).getCell(col));
		FIS.close();
		return Cell;
	}

	public static void setData(String sheetName, int row, int col, String value)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		String Env = storage.getProperty("Env");
		String FilePath = null;
		if (Env.equalsIgnoreCase("Pre-Prod")) {
			FilePath = storage.getProperty("PrePRODFile");
		} else if (Env.equalsIgnoreCase("STG")) {
			FilePath = storage.getProperty("STGFile");
		} else if (Env.equalsIgnoreCase("DEV")) {
			FilePath = storage.getProperty("DEVFile");
		}

		File src = new File(FilePath);
		FileInputStream fis = new FileInputStream(src);
		Workbook workbook = WorkbookFactory.create(fis);
		FileOutputStream fos1 = new FileOutputStream(src);
		Sheet sh = workbook.getSheet(sheetName);

		sh.getRow(row).createCell(col).setCellValue(value);
		workbook.write(fos1);
		fos1.close();
		fis.close();
	}
	static String pck, rdytime, rectime, arrtime, tndrtime;
	public void orderCreation(int i) throws Exception {
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
			getData("OrderCreation", i, 0);

			// --Get Service
			ServiceID = getData("OrderCreation", i, 18);
			System.out.println("Service== " + ServiceID);
			logger.info("=====Service:- " + ServiceID + "=====");
			msg.append("\n" + "=====Service:- " + ServiceID + "=====" + "\n");

			// Enter Caller
			String Caller = getData("OrderCreation", i, 0);
			isElementPresent("OCCallerName_id").clear();
			isElementPresent("OCCallerName_id").sendKeys(Caller);
			logger.info("Entered CallerName");

			// Enter Phone
			String Phone = getData("OrderCreation", i, 1);
			isElementPresent("OCContactPh_id").clear();
			isElementPresent("OCContactPh_id").sendKeys(Phone);
			logger.info("Entered Contact/Phone");

			// Enter Account*
			String Account = getData("OrderCreation", i, 2);
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

			
				// --wait until pU section is enabled
				try {
					wait.until(ExpectedConditions.invisibilityOfElementLocated(
							By.xpath("//*[@id=\"PickupSection\"][@disabled=\"disabled\"]")));
				} catch (Exception PUDIsable) {
					logger.error(PUDIsable);
					logger.info("Line number is: " + PUDIsable.getStackTrace()[0].getLineNumber());
					getScreenshot(Driver, "PUSDisabled");
					WebDriverWait waitPUD = new WebDriverWait(Driver, 120);// wait time
					waitPUD.until(ExpectedConditions.invisibilityOfElementLocated(
							By.xpath("//*[@id=\"PickupSection\"][@disabled=\"disabled\"]")));
					logger.info("PU Section is Enabled");

				}
				// Enter Pickup Zip code
				String PUZip = getData("OrderCreation", i, 3);
				isElementPresent("OCPUZp_id").clear();
				isElementPresent("OCPUZp_id").sendKeys(PUZip);
				isElementPresent("OCPUZp_id").sendKeys(Keys.TAB);
				Thread.sleep(2000);
				logger.info("Entered PU Zip");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --PU Address
				WebElement Puaddr = isElementPresent("OCPUAdd_id");
				wait.until(ExpectedConditions.elementToBeClickable(Puaddr));
				jse.executeScript("arguments[0].click();", Puaddr);
				logger.info("Click on PU Address");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --PU Company
				String PickupCom = getData("OrderCreation", i, 4);
				isElementPresent("OCPUComp_id").clear();
				isElementPresent("OCPUComp_id").sendKeys(PickupCom);
				logger.info("Entered PU Company");

				// --PU AddressLine1
				String PUAddress1 = getData("OrderCreation", i, 5);
				isElementPresent("OCPUAddL1_id").clear();
				isElementPresent("OCPUAddL1_id").sendKeys(PUAddress1);
				logger.info("Entered PU AddressLine1");

				// String Add2 = getData("OrderCreation", i, 6);
				// Driver.findElement(By.id("txtPUAddrLine2")).sendKeys(Add2);

				// --PU Attention
				String Attn = getData("OrderCreation", i, 7);
				isElementPresent("OCPUAtt_id").clear();
				isElementPresent("OCPUAtt_id").sendKeys(Attn);
				logger.info("Entered PU Attention");

				// --PU Phone
				String PuPhone = getData("OrderCreation", i, 8);
				isElementPresent("OCPUPhone_id").clear();
				isElementPresent("OCPUPhone_id").sendKeys(PuPhone);
				logger.info("Entered PU Phone");

				// String PUInst = getData("OrderCreation", i, 9);
				// Driver.findElement(By.id(" ")).sendKeys(PUInst);

				// --Wait to get PU Ready time
				try {
					wait.until(ExpectedConditions.invisibilityOfElementLocated(
							By.xpath("//input[contains(@class,'ng-invalid ng-invalid-required')]")));
					logger.info("PU Ready time is blank");
				} catch (Exception PUTimeNotExist) {
					logger.error(PUTimeNotExist);
					logger.info("Line number is: " + PUTimeNotExist.getStackTrace()[0].getLineNumber());
					logger.info("PU Ready time is exist");

				}
				// --Getting ready PickupTime
				rdytime = isElementPresent("OCPURTime_id").getAttribute("value");
				logger.info("PU Ready Time==" + rdytime);
				setData("OrderCreation", i, 34, rdytime);

				rectime = isElementPresent("OCPURTime_id").getAttribute("value");
				logger.info("PU Receive Time==" + rectime);
				setData("OrderCreation", i, 35, rectime);

				arrtime = isElementPresent("OCPURTime_id").getAttribute("value");
				logger.info("PU Arrival Time==" + arrtime);
				setData("OrderCreation", i, 36, arrtime);

				// set time in excel

				// tndrtime = Driver.findElement(By.id("txtReadyforPickupTime")).getText();

				// --PU Miles
				String pmi = isElementPresent("OCPUMiles_id").getAttribute("value");
				logger.info("PU Mileage==" + pmi);

				// --Del Zip
				String DLZip = getData("OrderCreation", i, 11);
				isElementPresent("OCDLZip_id").clear();
				isElementPresent("OCDLZip_id").sendKeys(DLZip);
				isElementPresent("OCDLZip_id").sendKeys(Keys.TAB);
				Thread.sleep(2000);
				logger.info("Entered DL Zip");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --Del Address
				WebElement DL = isElementPresent("OCDLAdd_id");
				wait.until(ExpectedConditions.elementToBeClickable(DL));
				jse.executeScript("arguments[0].click();", DL);
				logger.info("Entered DL Address");

				// --DEL Company
				String DelCompany = getData("OrderCreation", i, 12);
				isElementPresent("OCDLComp_id").clear();
				isElementPresent("OCDLComp_id").sendKeys(DelCompany);
				logger.info("Entered DL Company");

				// --DEL Address1
				String DLAddress1 = getData("OrderCreation", i, 13);
				isElementPresent("OCDLAddL1_id").clear();
				isElementPresent("OCDLAddL1_id").sendKeys(DLAddress1);
				logger.info("Entered DL Address Line1");

				// String DLAddr2 = getData("OrderCreation", i, 14);
				// Driver.findElement(By.id("txtDelAddrLine2")).sendKeys(DLAddr2);

				// --DL Attention
				String DLAttn = getData("OrderCreation", i, 15);
				isElementPresent("OCDLAtt_id").clear();
				isElementPresent("OCDLAtt_id").sendKeys(DLAttn);
				logger.info("Entered DL Attention");

				// --DL Phone
				String DLPhone = getData("OrderCreation", i, 16);
				isElementPresent("OCDLPhone_id").clear();
				isElementPresent("OCDLPhone_id").sendKeys(DLPhone);
				logger.info("Entered DL Phone");

				// --DL Miles
				String dmi = isElementPresent("OCDLMiles_id").getAttribute("value");
				logger.info("DL Miles==" + dmi);

				// String DLInst = getData("OrderCreation", i, 17);
				// Driver.findElement(By.id("txtDLPhone")).sendKeys(DLInst);
				// String srv =
				// Driver.findElement(By.id("idNewOrderServiceId")).getAttribute("value");

				// --Get the ServiceID
				String GeneratedServiceID = isElementPresent("OCServiceID_id").getAttribute("value");
				System.out.println("ServiceID==" + GeneratedServiceID);

				// --Total Qty
				isElementPresent("OCTotalQty_id").clear();
				// isElementPresent("OCTotalQty_id").sendKeys(Keys.BACK_SPACE);
				isElementPresent("OCTotalQty_id").sendKeys(Keys.CONTROL, "a");
				isElementPresent("OCTotalQty_id").sendKeys(Keys.BACK_SPACE);
				isElementPresent("OCTotalQty_id").clear();
				isElementPresent("OCTotalQty_id").clear();
				isElementPresent("OCTotalQty_id").sendKeys("01");
				isElementPresent("OCTotalQty_id").sendKeys(Keys.TAB);
				Thread.sleep(2000);
				logger.info("Entered Total Qty");

				// --Weight
				String Weight = getData("OrderCreation", i, 19);
				isElementPresent("OCWeight_id").clear();
				isElementPresent("OCWeight_id").sendKeys(Weight);
				isElementPresent("OCWeight_id").sendKeys(Keys.TAB);
				logger.info("Entered Weight");

				// --Length
				String Len = getData("OrderCreation", i, 20);
				isElementPresent("OCLength_id").clear();
				isElementPresent("OCLength_id").sendKeys(Len);
				isElementPresent("OCLength_id").sendKeys(Keys.TAB);
				logger.info("Entered Length");

				// --Width
				String Width = getData("OrderCreation", i, 21);
				isElementPresent("OCWidth_id").clear();
				isElementPresent("OCWidth_id").sendKeys(Width);
				isElementPresent("OCWidth_id").sendKeys(Keys.TAB);
				logger.info("Entered Width");

				// --Height
				String Height = getData("OrderCreation", i, 22);
				isElementPresent("OCHeight_id").clear();
				isElementPresent("OCHeight_id").sendKeys(Height);
				isElementPresent("OCHeight_id").sendKeys(Keys.TAB);
				logger.info("Entered Height");

				// --Commodity
				String Commodity = getData("OrderCreation", i, 23);
				isElementPresent("OCDesc_id").clear();
				isElementPresent("OCDesc_id").sendKeys(Commodity);
				isElementPresent("OCDesc_id").sendKeys(Keys.TAB);
				logger.info("Entered Commodity");

				// --Scroll Up
				r.keyPress(KeyEvent.VK_TAB);
				jse.executeScript("window.scrollBy(0,250)", "");
				Thread.sleep(2000);

				// --Total Mileage
				String tmile = isElementPresent("OCTotalMil_id").getAttribute("value");
				logger.info("Total Mileage==" + tmile);

				setData("OrderCreation", i, 25, pmi);
				setData("OrderCreation", i, 27, dmi);
				setData("OrderCreation", i, 29, tmile);

				// --Click on Create Order button
				WebElement order = isElementPresent("OCOProcess_id");
				jse.executeScript("arguments[0].scrollIntoView();", order);
				Thread.sleep(2000);
				act.moveToElement(order).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(order));
				jse.executeScript("arguments[0].click();", order);
				logger.info("Click on Create Order button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(2000);

			
				try {
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//*[@name=\"NewOrderForm\"]//ul[@id=\"errorid\"]")));
					String ValMsg = isElementPresent("OCValMsg_xpath").getText();
					logger.info("Validation message=" + ValMsg);
					if (ValMsg.contains("Please add atleast one Package.")) {

						// --Total Qty
						isElementPresent("OCTotalQty_id").clear();
						// isElementPresent("OCTotalQty_id").sendKeys(Keys.BACK_SPACE);
						isElementPresent("OCTotalQty_id").sendKeys(Keys.CONTROL, "a");
						isElementPresent("OCTotalQty_id").sendKeys(Keys.BACK_SPACE);
						isElementPresent("OCTotalQty_id").clear();
						isElementPresent("OCTotalQty_id").clear();
						isElementPresent("OCTotalQty_id").sendKeys("01");
						isElementPresent("OCTotalQty_id").sendKeys(Keys.TAB);
						Thread.sleep(2000);
						logger.info("Entered Total Qty");

						// --Weight
						Weight = getData("OrderCreation", i, 19);
						isElementPresent("OCWeight_id").clear();
						isElementPresent("OCWeight_id").sendKeys(Weight);
						isElementPresent("OCWeight_id").sendKeys(Keys.TAB);
						logger.info("Entered Weight");

						// --Length
						Len = getData("OrderCreation", i, 20);
						isElementPresent("OCLength_id").clear();
						isElementPresent("OCLength_id").sendKeys(Len);
						isElementPresent("OCLength_id").sendKeys(Keys.TAB);
						logger.info("Entered Length");

						// --Width
						Width = getData("OrderCreation", i, 21);
						isElementPresent("OCWidth_id").clear();
						isElementPresent("OCWidth_id").sendKeys(Width);
						isElementPresent("OCWidth_id").sendKeys(Keys.TAB);
						logger.info("Entered Width");

						// --Height
						Height = getData("OrderCreation", i, 22);
						isElementPresent("OCHeight_id").clear();
						isElementPresent("OCHeight_id").sendKeys(Height);
						isElementPresent("OCHeight_id").sendKeys(Keys.TAB);
						logger.info("Entered Height");

						// --Commodity
						Commodity = getData("OrderCreation", i, 23);
						isElementPresent("OCDesc_id").clear();
						isElementPresent("OCDesc_id").sendKeys(Commodity);
						isElementPresent("OCDesc_id").sendKeys(Keys.TAB);
						logger.info("Entered Commodity");

						// --Click on Create Order button
						order = isElementPresent("OCOProcess_id");
						jse.executeScript("arguments[0].scrollIntoView();", order);
						Thread.sleep(2000);
						act.moveToElement(order).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(order));
						jse.executeScript("arguments[0].click();", order);
						logger.info("Click on Create Order button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						Thread.sleep(2000);

						try {
							boolean sameairport = Driver.getPageSource().contains(
									"Pickup and Delivery airport are different. Do you want to make it same?");

							if (sameairport == true) {
								logger.info("PopUp message is displayed for Same Airport");
								WebElement Yes = isElementPresent("OCSameApPupYes_xpath");
								wait.until(ExpectedConditions.elementToBeClickable(Yes));
								jse.executeScript("arguments[0].click();", Yes);
								logger.info("Clicked on YES button of popup");

							}
						} catch (Exception eee) {

						}

					}

				} catch (Exception packagee) {
					logger.info("Validation for Package is not displayed");

				}
				// --Scroll down
				/*
				 * r.keyPress(KeyEvent.VK_TAB); jse.executeScript("window.scrollBy(0,250)", "");
				 * Thread.sleep(2000);
				 */

				// --Get the PickUPID
				try {
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//*[@class=\"modal-dialog modal-sm\"]")));

				} catch (Exception WaitOp) {
					try {
						/*
						 * order = isElementPresent("OCOProcess_id");
						 * jse.executeScript("arguments[0].scrollIntoView();", order);
						 * Thread.sleep(2000);
						 */
						/*
						 * order = isElementPresent("OCOProcess_id");
						 * wait.until(ExpectedConditions.elementToBeClickable(order));
						 * act.moveToElement(order).click().build().perform();
						 * logger.info("Click on Create Order button");
						 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")
						 * ));
						 */
						/*
						 * order = isElementPresent("OCOProcess_id");
						 * jse.executeScript("arguments[0].scrollIntoView();", order);
						 * Thread.sleep(2000);
						 */
						order = isElementPresent("OCOProcess_id");
						act.moveToElement(order).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(order));
						jse.executeScript("arguments[0].click();", order);
						logger.info("Click on Create Order button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						Thread.sleep(2000);

						try {
							boolean sameairport = Driver.getPageSource().contains(
									"Pickup and Delivery airport are different. Do you want to make it same?");

							if (sameairport == true) {
								logger.info("PopUp message is displayed for Same Airport");
								WebElement Yes = isElementPresent("OCSameApPupYes_xpath");
								wait.until(ExpectedConditions.elementToBeClickable(Yes));
								jse.executeScript("arguments[0].click();", Yes);
								logger.info("Clicked on YES button of popup");

							}
						} catch (Exception eee) {

						}
						WebDriverWait wait1 = new WebDriverWait(Driver, 40);// wait time
						wait1.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//*[@class=\"modal-dialog modal-sm\"]")));

					} catch (Exception e) {

					}
				}

			


			WebElement PickUPID = isElementPresent("OCPickuPID_xpath");
			wait.until(ExpectedConditions.visibilityOf(PickUPID));
			wait.until(ExpectedConditions.elementToBeClickable(PickUPID));
			String pck = PickUPID.getText();
			System.out.println("Pickup = " + pck);
			logger.info("=====Pickup =" + pck + "=====" + "\n");
			msg.append("=====Pickup =" + pck + "=====" + "\n");

			// --Set PickUPID
			setData("OrderCreation", i, 32, pck);

			// --Set Pass in TestScenarios
		
				setData("Auto_verify_30018", 1, 3, pck);
				setData("Auto_verify_30018", 1, 5, "PASS");

			// --Click on Edit Order
			WebElement EditOrder = isElementPresent("OCEditOrder_id");
			wait.until(ExpectedConditions.elementToBeClickable(EditOrder));
			EditOrder.click();
			logger.info("Clicked on Edit Order");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		} catch (Exception e) {
			logger.error(e);
			logger.info("Line number is: " + e.getStackTrace()[0].getLineNumber());
			getScreenshot(Driver, "CreateOrder_" + ServiceID);

			// --Set FAIL in TestScenarios

			
				setData("Auto_verify_30018", 1, 3, "FAIL");

			
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

	
	public void tcAcknowledge() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		Actions act = new Actions(Driver);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		// --Get the ServiceID
		String svc = isElementPresent("TLServID_id").getText();
		System.out.println(svc);
		logger.info("ServiceID=" + svc);
		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"lblStages\"][contains(text(),'Acknowledge')]")));

			// --Get StageName
			OrderCreation OC = new OrderCreation();
			OC.getStageName();

			// --Click on TC Ack button
			if (svc.equals("LOC") || svc.equals("P3P") || svc.equals("DRV") || svc.equals("SDC") || svc.equals("FRG")
					|| svc.equals("H3P") || svc.equals("CPU") || svc.equals("D3P") || svc.equals("3PLAST")) {
				WebElement TCAckBtn = isElementPresent("TLAcknBTN_id");
				act.moveToElement(TCAckBtn).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(TCAckBtn));
				jse.executeScript("arguments[0].click();", TCAckBtn);
				logger.info("Clicked on TC Acknowledge button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				try {

					wait.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//*[@id=\"idValidationforMain\"]//ul[@id=\"errorid\"]")));
					String Validmsg = isElementPresent("OCValOnePack_xpath").getText();
					logger.info("Validation message is displayed=" + Validmsg);
					if (Validmsg.contains("Please enter Last Quoted Delivery Time through Edit Order.")) {
						// Recalculate the charges
						// --Go to Edit Job tab
						WebElement EditOrTab = isElementPresent("EOEditOrderTab_id");
						act.moveToElement(EditOrTab).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(EditOrTab));
						act.moveToElement(EditOrTab).click().perform();
						logger.info("Click on Edit Order Tab");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						Thread.sleep(5000);

						// --Get the timeZone
						String tzone = isElementPresent("TLLastPuTimeZone_xpath").getText();
						String rectime = getTimeAsTZone(tzone) + System.currentTimeMillis();

						// --Move to DeliveryDate and Time
						WebElement DelTime = isElementPresent("TLLastQDelTime_id");
						act.moveToElement(DelTime).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(DelTime));
						DelTime.clear();
						DelTime.sendKeys(rectime);
						logger.info("EnteredLast  Quoted Delivery Time");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						String DELDateValue = CuDate();

						// --Move to DeliveryDate and Time
						WebElement DelDate = isElementPresent("TLLastQDelDate_id");
						act.moveToElement(DelDate).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(DelDate));
						DelDate.clear();
						DelDate.sendKeys(DELDateValue);
						DelDate.sendKeys(Keys.TAB);
						logger.info("Entered Last quoted Delivery Date");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// --Click on Save Changes button
						WebElement SaveChanges = isElementPresent("TLSaveChanges_id");
						act.moveToElement(SaveChanges).build().perform();
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
						wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
						jse.executeScript("arguments[0].click();", SaveChanges);
						logger.info("Click on Save Changes button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						Thread.sleep(5000);

						try {
							WebElement Validation = isElementPresent("EOValidation_id");
							wait.until(ExpectedConditions.visibilityOf(Validation));
							String ValMsg = Validation.getText();
							logger.info("Validation==" + ValMsg);

							if (ValMsg.equalsIgnoreCase("Pickup Phone# is Required.")) {
								// --Enter Pickup Phone No
								WebElement PUPhoneNo = isElementPresent("EOPickupPhone_id");
								act.moveToElement(PUPhoneNo).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(PUPhoneNo));
								PUPhoneNo.clear();
								PUPhoneNo.sendKeys("1112221112");
								logger.info("Entered PU Phone");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Click on Save Changes button
								SaveChanges = isElementPresent("TLSaveChanges_id");
								act.moveToElement(SaveChanges).build().perform();
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
								wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
								act.moveToElement(SaveChanges).click().perform();
								logger.info("Click on Save Changes button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
								Thread.sleep(2000);
								try {
									Validation = isElementPresent("EOValidation_id");
									wait.until(ExpectedConditions.visibilityOf(Validation));
									ValMsg = Validation.getText();
									logger.info("Validation==" + ValMsg);

									if (ValMsg.contains(
											"Estimated Delivery time cannot be less than Quoted for Pickup time.")) {
										// --Get the timeZone
										tzone = isElementPresent("TLLastPuTimeZone_xpath").getText();
										rectime = getTimeAsTZone(tzone) + System.currentTimeMillis();

										// --Move to DeliveryDate and Time
										DelTime = isElementPresent("TLLastQDelTime_id");
										act.moveToElement(DelTime).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(DelTime));
										DelTime.clear();
										DelTime.sendKeys(rectime);
										logger.info("EnteredLast  Quoted Delivery Time");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

										DELDateValue = CuDate();

										// --Move to DeliveryDate and Time
										DelDate = isElementPresent("TLLastQDelDate_id");
										act.moveToElement(DelDate).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(DelDate));
										DelDate.clear();
										DelDate.sendKeys(DELDateValue);
										DelDate.sendKeys(Keys.TAB);
										logger.info("Entered Last quoted Delivery Date");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

										// --Click on Save Changes button
										SaveChanges = isElementPresent("TLSaveChanges_id");
										act.moveToElement(SaveChanges).build().perform();
										wait.until(
												ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
										wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
										jse.executeScript("arguments[0].click();", SaveChanges);
										logger.info("Click on Save Changes button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
										Thread.sleep(5000);

									}
								} catch (Exception ee) {

								}

							} else if (Validmsg
									.contains("Estimated Delivery time cannot be less than Quoted for Pickup time.")) {
								// --Get the timeZone
								tzone = isElementPresent("TLLastPuTimeZone_xpath").getText();
								rectime = getTimeAsTZone(tzone) + System.currentTimeMillis();

								// --Move to DeliveryDate and Time
								DelTime = isElementPresent("TLLastQDelTime_id");
								act.moveToElement(DelTime).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(DelTime));
								DelTime.clear();
								DelTime.sendKeys(rectime);
								logger.info("EnteredLast  Quoted Delivery Time");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								DELDateValue = CuDate();

								// --Move to DeliveryDate and Time
								DelDate = isElementPresent("TLLastQDelDate_id");
								act.moveToElement(DelDate).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(DelDate));
								DelDate.clear();
								DelDate.sendKeys(DELDateValue);
								logger.info("Entered Last quoted Delivery Date");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Click on Save Changes button
								SaveChanges = isElementPresent("TLSaveChanges_id");
								act.moveToElement(SaveChanges).build().perform();
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
								wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
								jse.executeScript("arguments[0].click();", SaveChanges);
								logger.info("Click on Save Changes button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
								Thread.sleep(5000);

							}

						} catch (Exception ee) {
							// --Click on Save Changes button
							SaveChanges = isElementPresent("TLSaveChanges_id");
							act.moveToElement(SaveChanges).build().perform();
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
							wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
							act.moveToElement(SaveChanges).click().perform();
							logger.info("Click on Save Changes button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							Thread.sleep(2000);

							logger.info("Validation message is not displayed for Recalculate");

						}
					} else if (Validmsg.contains("Pickup is in after hours Please enter commodity")) {
						// --Go to Edit Job tab
						WebElement EditOrTab = isElementPresent("EOEditOrderTab_id");
						act.moveToElement(EditOrTab).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(EditOrTab));
						act.moveToElement(EditOrTab).click().perform();
						logger.info("Click on Edit Order Tab");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						Thread.sleep(5000);

						// --Enter Commodity
						WebElement Commodity = isElementPresent("EOCommodity_id");
						act.moveToElement(Commodity).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(Commodity));
						Commodity.sendKeys("Boxes");
						logger.info("Entered Commodity");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// --Click on Save Changes button
						WebElement SaveChanges = isElementPresent("TLSaveChanges_id");
						act.moveToElement(SaveChanges).build().perform();
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
						wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
						jse.executeScript("arguments[0].click();", SaveChanges);
						logger.info("Click on Save Changes button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						Thread.sleep(5000);

						try {
							WebElement Validation = isElementPresent("EOValidation_id");
							wait.until(ExpectedConditions.visibilityOf(Validation));
							String ValMsg = Validation.getText();
							logger.info("Validation==" + ValMsg);

							if (ValMsg.equalsIgnoreCase("Pickup Phone# is Required.")) {
								// --Enter Pickup Phone No
								WebElement PUPhoneNo = isElementPresent("EOPickupPhone_id");
								act.moveToElement(PUPhoneNo).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(PUPhoneNo));
								PUPhoneNo.clear();
								PUPhoneNo.sendKeys("1112221112");
								logger.info("Entered PU Phone");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Click on Save Changes button
								SaveChanges = isElementPresent("TLSaveChanges_id");
								act.moveToElement(SaveChanges).build().perform();
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
								wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
								act.moveToElement(SaveChanges).click().perform();
								logger.info("Click on Save Changes button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
								Thread.sleep(2000);

							} else if (Validmsg
									.contains("Estimated Delivery time cannot be less than Quoted for Pickup time.")) {
								// --Get the timeZone
								String tzone = isElementPresent("TLLastPuTimeZone_xpath").getText();
								String rectime = getTimeAsTZone(tzone) + System.currentTimeMillis();

								// --Move to DeliveryDate and Time
								WebElement DelTime = isElementPresent("TLLastQDelTime_id");
								act.moveToElement(DelTime).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(DelTime));
								DelTime.clear();
								DelTime.sendKeys(rectime);
								logger.info("EnteredLast  Quoted Delivery Time");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								String DELDateValue = CuDate();

								// --Move to DeliveryDate and Time
								WebElement DelDate = isElementPresent("TLLastQDelDate_id");
								act.moveToElement(DelDate).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(DelDate));
								DelDate.clear();
								DelDate.sendKeys(DELDateValue);
								logger.info("Entered Last quoted Delivery Date");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Click on Save Changes button
								SaveChanges = isElementPresent("TLSaveChanges_id");
								act.moveToElement(SaveChanges).build().perform();
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
								wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
								jse.executeScript("arguments[0].click();", SaveChanges);
								logger.info("Click on Save Changes button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
								Thread.sleep(5000);

							}

						} catch (Exception ee) {
							logger.info("Validation message is not displayed for Phone");

						}
					}
					// --Go to job Status Tab
					WebElement JobOverTab = isElementPresent("TLJobStatusTab_id");
					act.moveToElement(JobOverTab).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(JobOverTab));
					act.moveToElement(JobOverTab).click().perform();
					logger.info("Click on Job Overview Tab");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					TCAckBtn = isElementPresent("TLAcknBTN_id");
					wait.until(ExpectedConditions.elementToBeClickable(TCAckBtn));
					jse.executeScript("arguments[0].click();", TCAckBtn);
					logger.info("Clicked on TC Acknowledge button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				} catch (Exception eLastQDElTime) {

				}

			}

			if (svc.equals("SD") || svc.equals("PA") || svc.equals("AIR") || svc.equals("FRA")) {
				WebElement TCAckBtn = isElementPresent("TLAckBTn2_id");
				act.moveToElement(TCAckBtn).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(TCAckBtn));
				jse.executeScript("arguments[0].click();", TCAckBtn);
				logger.info("Clicked on TC Acknowledge button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			}
			
			// --Set Pass in TestScenarios

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 2, 5, "PASS");

			}
		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "TCACKNOWLEDGE" + svc);
			System.out.println("TC ACKNOWLEDGE Not Exist in Flow!!");
			logger.info("TC ACKNOWLEDGE Not Exist in Flow!!");
			
			// --Set FAIL in TestScenarios

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 2, 5, "FAIL");

			}
		}

	}
	
	public void pickupAlert() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		Actions act = new Actions(Driver);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		OrderCreation OC = new OrderCreation();
		String svc = OC.getServiceID();
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"lblStages\"][contains(text(),'Ready For Dispatch')]")));

			// --Get StageName
			OC.getStageName();

			EditDriver();

			// --Check Contacted
			if (isElementPresent("TLRDContacted_id").isDisplayed()) {
				WebElement email = isElementPresent("TLRDContacted_id");
				wait.until(ExpectedConditions.elementToBeClickable(email));
				jse.executeScript("arguments[0].click();", email);
				Select CBy = new Select(email);
				CBy.selectByValue("number:377");
				System.out.println("email selected");
				logger.info("Email is selected as a Contact By");
			} else {
				Select Contacttype = new Select(isElementPresent("TLRDContacted_id"));
				Contacttype.selectByVisibleText("Email");
				logger.info("Email is selected as a Contact By");

			}

			// --Enter ContactBy Value
			WebElement emailValue = isElementPresent("TLRDContValue_id");
			wait.until(ExpectedConditions.elementToBeClickable(emailValue));
			emailValue.clear();
			emailValue.sendKeys("parth.shah@samyak.com");
			logger.info("Entered EmailID");

			// --Spoke With
			WebElement spoke = isElementPresent("TLRDSpokeW_id");
			wait.until(ExpectedConditions.elementToBeClickable(spoke));
			spoke.clear();
			spoke.sendKeys("Parth");
			logger.info("Entered Spoke With");

			// --Click on Alert and Confirm
			try {
				WebElement Sendpualert = isElementPresent("TLRDSPUALert_id");
				act.moveToElement(Sendpualert).build().perform();
				jse.executeScript("arguments[0].click();", Sendpualert);
				logger.info("Clicked on Alert&Confirm button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			} catch (Exception e) {
				WebElement Sendpualert = isElementPresent("TLRDAlConfrmBtn_id");
				act.moveToElement(Sendpualert).build().perform();
				jse.executeScript("arguments[0].click();", Sendpualert);
				logger.info("Clicked on Alert&Confirm button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			}

			// --Set Pass in TestScenarios

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 3, 5, "PASS");

			}

		} catch (Exception e) {
			logger.error(e);

			getScreenshot(Driver, "READYFORDISPATCH" + svc);
			System.out.println("READY FOR DISPATCH Not Exist in Flow!!");
			logger.info("READY FOR DISPATCH Not Exist in Flow!!");
			
			
			// --Set FAIL in TestScenarios

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 3, 5, "FAIL");

			}
		}

	}
	
	public void EditDriver() throws IOException {

		WebDriverWait wait = new WebDriverWait(Driver, 50);
		Actions act = new Actions(Driver);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		try {
			logger.info("==Edit Driver Test Start==");
			// msg.append("==RTE Edit Driver Test Start==" + "\n");

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hlkedit")));
			isElementPresent("PDEdit_id").click();
			logger.info("Clicked on Edit button of Driver");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"modal-dialog modal-md\"]")));
			getScreenshot(Driver, "DriverSearch");

			// --Search Driver by AgentID
			wait.until(ExpectedConditions.elementToBeClickable(By.id("txtCourierId")));
			isElementPresent("PDCourierID_id").clear();
			isElementPresent("PDCourierID_id").sendKeys("34769");
			logger.info("Enter CourierID");

			// --Search Driver by AgentKey
			wait.until(ExpectedConditions.elementToBeClickable(By.id("txtAgentKey")));
			isElementPresent("PDAgentkey_id").clear();
			isElementPresent("PDAgentkey_id").sendKeys("AUTOMATION");
			logger.info("Enter CourierID");

			// --Click on Search
			WebElement SearchBTN = isElementPresent("PDSearch_id");
			js.executeScript("arguments[0].click();", SearchBTN);
			logger.info("Clicked on search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Get the List of Agent
			
		WebElement agent_id= Driver.findElement(By.xpath("//span[@ng-bind='Agent.AgentId' and contains(text(),'34769')]"));
		js.executeScript("arguments[0].scrollIntoView(true);", agent_id);
		Thread.sleep(1000);
		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		

			// --Compare the Searched Agent and selected Agent
			String AgeValue = agent_id.getText();
			logger.info("Selected agent is==" + AgeValue);

			if (AgeValue.equalsIgnoreCase("34769")) {
				logger.info("Selected Agent is displayed in Driver section");
				js.executeScript("arguments[0].click();", agent_id); 

			} else {
				logger.info("Selected Agent is not displayed in Driver section");

			}

			logger.info("== Edit Driver Test End==");
			// msg.append("== Edit Driver Test End==" + "\n");
			msg.append("Edit Driver Test=PASS" + "\n");

		} catch (Exception ex) {
			logger.error(ex);
			logger.info("Line number is: " + ex.getStackTrace()[0].getLineNumber());
			msg.append("Edit Driver Test=FAIL" + "\n");

		}

	}
	
	public void opFromNetAgent(int i) throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

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

		NATaskSearch(PUID);

	}

	public void NATaskSearch_after_delivery(String PUID)
			throws InterruptedException, EncryptedDocumentException, InvalidFormatException, IOException {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		try {
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
		} catch (Exception ee) {
			// Go To TaskLog
			WebElement OperationMenu = isElementPresent("NTLOperations_xpath");
			wait.until(ExpectedConditions.elementToBeClickable(OperationMenu));
			act.moveToElement(OperationMenu).build().perform();
			// js.executeScript("arguments[0].click();", OperationMenu);
			logger.info("Click on Operations");
			Thread.sleep(2000);
			WebElement TaskLogMenu = isElementPresent("NTTasklog_xpath");
			act.moveToElement(TaskLogMenu).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(TaskLogMenu));
			Thread.sleep(1000);
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

				WebElement TaskLogMenus = isElementPresent("NTTasklog_xpath");
				act.moveToElement(TaskLogMenu).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(TaskLogMenus));
				Thread.sleep(1000);
				js.executeScript("arguments[0].click();", TaskLogMenus);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));
				logger.info("Click on Task Log");
				// wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

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

		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
			WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
			if (NoData.isDisplayed()) {
				logger.info("Job is not found in Operation Tab");

				logger.info("Job is not available in NetAgent");
				setData("Auto_verify_30018", 8, 3, "PASS");

			}
		} catch (Exception e1) {
			logger.info("Job is available in NetAgent");
			setData("Auto_verify_30018", 8, 3, "FAIL");
		}
	}

	public void searchJob(int i) throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait;
		try {
			wait = new WebDriverWait(Driver, 40);// wait time

		} catch (Exception ewait) {
			wait = new WebDriverWait(Driver, 120);// wait time

		}
		Actions act = new Actions(Driver);

		try {
			// Enter JobID*
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("txtContains")));

			String PUID = getData("OrderCreation", i, 32);
			isElementPresent("TLSearch_id").clear();
			isElementPresent("TLSearch_id").sendKeys(PUID);
			isElementPresent("TLSearch_id").sendKeys(Keys.TAB);

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			WebElement Search = isElementPresent("TLSearchButton_id");
			wait.until(ExpectedConditions.elementToBeClickable(Search));
			jse.executeScript("arguments[0].click();", Search);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			try {
				getScreenshot(Driver, "H3PJob_After_TenderTo3P");

				List<WebElement> Jobs = Driver
						.findElements(By.xpath("//*[contains(@aria-label,'Pickup')]//label[@id=\"lblDateTime\"]"));
				int totaljobs = Jobs.size();
				logger.info("Total Jobs==" + totaljobs);
				for (int job = 0; job < totaljobs; job++) {
					String PickupID = Jobs.get(job).getText();
					String PickID = null;

					if (PickupID.startsWith("N")) {
						String[] PickValue = PickupID.split("N");
						PickID = PickValue[1];
					} else if (PickupID.startsWith("F")) {
						String[] PickValue = PickupID.split("F");
						PickID = PickValue[1];
					} else if (PickupID.startsWith("R")) {
						String[] PickValue = PickupID.split("R");
						PickID = PickValue[1];
					}
					PUID = getData("OrderCreation", i, 32);
					logger.info("Searched PickUpID==" + PickID);
					if (PickID.equalsIgnoreCase(PUID)) {
						Jobs.get(job).click();
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						break;
					}

				}

				/*
				 * // --Click on Job Name WebElement JobName =
				 * isElementPresent("TLH3PJobName_id");
				 * wait.until(ExpectedConditions.elementToBeClickable(JobName));
				 * JobName.click(); logger.info("Clicked on Job Name");
				 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")
				 * ));
				 */
				logger.info("Same job is displayed with 2 status==PASS");
				// msg.append("Same job is displayed with 2 status==PASS" + "\n");

				// --Get StageName
			} catch (Exception eTenderTo3P) {
				logger.info("Same job is not displayed with 2 status");
				// msg.append("Same job is not displayed with 2 status" + "\n");

				// --Get StageName

			}

		} catch (Exception eTasklog) {
			// --Go To Operations
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_operations")));
			WebElement Operations = isElementPresent("OperMenu_id");
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

			// Enter JobID*
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			String PUID = getData("OrderCreation", i, 32);
			logger.info("PickUpID=" + PUID + "\n");
			isElementPresent("TLSearch_id").clear();
			isElementPresent("TLSearch_id").sendKeys(PUID);
			isElementPresent("TLSearch_id").sendKeys(Keys.TAB);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			WebElement Search = isElementPresent("TLSearchButton_id");
			wait.until(ExpectedConditions.elementToBeClickable(Search));
			jse.executeScript("arguments[0].click();", Search);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			try {
				getScreenshot(Driver, "H3PJob_After_TenderTo3P");

				List<WebElement> Jobs = Driver
						.findElements(By.xpath("//*[contains(@aria-label,'Pickup')]//label[@id=\"lblDateTime\"]"));
				int totaljobs = Jobs.size();
				logger.info("Total Jobs==" + totaljobs);
				for (int job = 0; job < Jobs.size(); job++) {
					String PickupID = Jobs.get(job).getText();
					String PickID = null;

					if (PickupID.startsWith("N")) {
						String[] PickValue = PickupID.split("N");
						PickID = PickValue[1];
					} else if (PickupID.startsWith("F")) {
						String[] PickValue = PickupID.split("F");
						PickID = PickValue[1];
					} else if (PickupID.startsWith("R")) {
						String[] PickValue = PickupID.split("R");
						PickID = PickValue[1];
					}
					PUID = getData("OrderCreation", i, 32);
					logger.info("Searched PickUpID==" + PickID);
					if (PickID.equalsIgnoreCase(PUID)) {
						Jobs.get(job).click();
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						break;
					}

				}
				/*
				 * // --Click on Job Name WebElement JobName =
				 * isElementPresent("TLH3PJobName_id");
				 * wait.until(ExpectedConditions.elementToBeClickable(JobName));
				 * JobName.click(); logger.info("Clicked on Job Name");
				 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")
				 * ));
				 */
				logger.info("Same job is displayed with 2 status==PASS");
				// msg.append("Same job is displayed with 2 status==PASS" + "\n");

				// --Get StageName
			} catch (Exception eTenderTo3P) {
				logger.info("Same job is not displayed with 2 status");
				// msg.append("Same job is not displayed with 2 status" + "\n");

				// --Get StageName

			}

		}

	}
	
	public void NATaskSearch(String PUID) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		try {
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
		} catch (Exception ee) {
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

		}
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		} catch (Exception e) {
			WebDriverWait wait1 = new WebDriverWait(Driver, 180);
			wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		}

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
			WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
			if (NoData.isDisplayed()) {
				logger.info("Job is not found in Operation Tab");

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
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				} catch (Exception e) {
					WebDriverWait wait1 = new WebDriverWait(Driver, 180);
					wait1.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				}

				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
					NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
					if (NoData.isDisplayed()) {
						logger.info("Job is not available in NetAgent");

					}
				} catch (Exception e1) {
					logger.info("Job is available in NetAgent");
				}
			}
		} catch (Exception Tab) {
			logger.info("Job is available in Operation Tab");

		}

	}
	
	
	
	
	public void naconfirmPUAlert() throws Exception {

		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 50);// wait time
		Actions act = new Actions(Driver);

		/*
		 * try { wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
		 * "//*[@class=\"ajax-loadernew\"]")));
		 * 
		 * } catch (Exception ee) {
		 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
		 * "//*[@class=\"ajax-loadernew\"]")));
		 * 
		 * } Thread.sleep(5000);
		 */

		// --Get the ServiceID
		WebElement service = isElementPresent("NOEServiceID_xpath");
		wait.until(ExpectedConditions.visibilityOf(service));
		String svc = service.getText();
		System.out.println(svc);
		logger.info("ServiceID=" + svc);
		msg.append("ServiceID=" + svc + "\n");

		try {

			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Confirm Pu Alert')]")));
				// --Set Pass in TestScenarios
				if (svc.equals("LOC")) {
					setData("TC_OrderProcess", 4, 5, "PASS");

				}
			} catch (Exception eStageName) {

				if (svc.equals("LOC")) {
					setData("TC_OrderProcess", 4, 5, "FAIL");

				}
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[@id=\"lblStages\"][contains(text(),'CONFIRM PULL ALERT')]")));

			}

			OrderCreation OC = new OrderCreation();
			OC.getNAStageName();

			// --Click on Confirm PU Alert
			try {
				WebElement ConfPUAlert = isElementPresent("NPUCOnfBtn_id");
				act.moveToElement(ConfPUAlert).build().perform();
				jse.executeScript("arguments[0].click();", ConfPUAlert);
				logger.info("Clicked on Confirm PU Alert button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			} catch (Exception e) {
				try {
					WebElement ConfPUAlert = isElementPresent("TLRDSPUALert_id");
					act.moveToElement(ConfPUAlert).build().perform();
					jse.executeScript("arguments[0].click();", ConfPUAlert);
					logger.info("Clicked on Confirm PU Alert button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				} catch (Exception ee) {
					WebElement ConfPUAlert = isElementPresent("D3PConfull_id");
					act.moveToElement(ConfPUAlert).build().perform();
					jse.executeScript("arguments[0].click();", ConfPUAlert);
					logger.info("Clicked on Confirm PU Alert button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				}

			}

			// --Set Pass in TestScenarios

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 5, 5, "PASS");

			}

		} catch (Exception e) {
			logger.error(e);
			logger.info("Line number is: " + e.getStackTrace()[0].getLineNumber());
			getScreenshot(Driver, "NAConfirmPUAlert" + svc);
			System.out.println("Confirm PU Alert Not Exist in Flow!!");
			logger.info("Confirm PU Alert Not Exist in Flow!!");

			// --Set Pass in TestScenarios

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 5, 5, "FAIL");

			}

		}

	}
	public void naconfirmPickup() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		Actions act = new Actions(Driver);
		JavascriptExecutor js = (JavascriptExecutor) Driver;

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Get the ServiceID
		String svc = isElementPresent("NOEServiceID_xpath").getText();
		System.out.println(svc);
		logger.info("ServiceID=" + svc);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Pickup')]")));

			OrderCreation OC = new OrderCreation();
			OC.getNAStageName();
			

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 6, 5, "PASS");

			}

			if (svc.equals("LOC") || svc.equals("P3P") || svc.equals("DRV") || svc.equals("SDC")
					|| svc.equals("FRG") | svc.equals("D3P") || svc.equals("SD")) {

				// --Get the timeZone
				String tzone = isElementPresent("NPUTimeZone_id").getText();
				String rectime = getTimeAsTZone(tzone);

				// --Enter Actual Pickup Time
				isElementPresent("NTLPuTime_id").clear();
				isElementPresent("NTLPuTime_id").sendKeys(rectime);
				isElementPresent("NTLPuTime_id").sendKeys(Keys.TAB);
				isElementPresent("NTLPuTime_id").sendKeys(Keys.TAB);
				logger.info("Enter Actual pickup time");

				// --Click on Confirm PU button
				WebElement ConfPU = isElementPresent("NTPickupBtn_id");
				wait.until(ExpectedConditions.elementToBeClickable(ConfPU));
				Thread.sleep(2000);
				js.executeScript("arguments[0].click();", ConfPU);
				logger.info("Click on Confirm PU button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				try {
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
					WebElement Dyes = isElementPresent("NTPuPopYes_id");
					js.executeScript("arguments[0].click();", Dyes);
					logger.info("Clicked on Yes button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				} catch (Exception e) {
					logger.info("Dialogue is not exist");

				}

			}

			if (svc.equals("PA") || svc.equals("AIR") || svc.equals("FRA")) {

				// --Get the timeZone
				String tzone = isElementPresent("NPUTimeZone_id").getText();
				String rectime = getTimeAsTZone(tzone);

				// --Enter Actual Pickup Time
				isElementPresent("NTLPuTime_id").clear();
				isElementPresent("NTLPuTime_id").sendKeys(rectime);
				isElementPresent("NTLPuTime_id").sendKeys(Keys.TAB);
				isElementPresent("NTLPuTime_id").sendKeys(Keys.TAB);

				logger.info("Enter Actual pickup time");

				// --Click on Confirm PU button
				WebElement ConfPU = isElementPresent("NTPickupBtn_id");
				wait.until(ExpectedConditions.elementToBeClickable(ConfPU));
				Thread.sleep(2000);
				ConfPU.click();
				logger.info("Click on Confirm PU button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				try {

					wait.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//*[@id=\"idValidationforMain\"]//ul[@id=\"errorid\"]")));
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

						// --Go to job Status Tab
						WebElement JobOverTab = isElementPresent("TLJobStatusTab_id");
						act.moveToElement(JobOverTab).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(JobOverTab));
						act.moveToElement(JobOverTab).click().perform();
						logger.info("Click on Job Overview Tab");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						tzone = isElementPresent("TLPUTimeZone_id").getText();
						rectime = getTimeAsTZone(tzone);

						// --Enter Actual Pickup Time
						isElementPresent("TLPActPUpTime_id").clear();
						isElementPresent("TLPActPUpTime_id").sendKeys(rectime);
						isElementPresent("TLPActPUpTime_id").sendKeys(Keys.TAB);
						logger.info("Enter Actual pickup time");

						// --Click on Confirm PU button
						ConfPU = isElementPresent("TLPUConfPU2_id");
						wait.until(ExpectedConditions.elementToBeClickable(ConfPU));
						Thread.sleep(2000);
						ConfPU.click();
						logger.info("Click on Confirm PU button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					}

				} catch (Exception PModify) {
					logger.info("Validation message is not displayed for Recalculate the charges");

				}

			}
			
			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 7, 5, "PASS");

			}

		} catch (Exception e) {
			logger.error(e);
			logger.info("Line number is: " + e.getStackTrace()[0].getLineNumber());
			getScreenshot(Driver, "NAPICKUP" + svc);
			System.out.println("PICKUP Not Exist in Flow!!");
			logger.info("PICKUP Not Exist in Flow!!");
			
			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 6, 5, "FAIL");

			}
			
			
			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 7, 5, "FAIL");

			}

		}

	}

	public String getNAStageName() {
		WebDriverWait wait = new WebDriverWait(Driver, 50);// wait time

		// --Get the Stage Name
		WebElement Stage = null;
		try {
			Stage = isElementPresent("NStageName_xpath");
			wait.until(ExpectedConditions.visibilityOf(Stage));

		} catch (Exception ee) {
			Stage = isElementPresent("NSPLstagename_xpath");
			wait.until(ExpectedConditions.visibilityOf(Stage));

		}

		String StageName = Stage.getText();
		System.out.println(StageName);
		logger.info("Stage=" + StageName);
		msg.append("Stage=" + StageName + "\n");
		return StageName;

	}
	
	public void naconfirmDelivery() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		// Actions act = new Actions(Driver);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		String svc = null;
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Deliver')]")));

			// --Get the ServiceID

			svc = isElementPresent("NOEServiceID_xpath").getText();
			System.out.println(svc);
			logger.info("ServiceID=" + svc);

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 8, 5, "PASS");

			}

			OrderCreation OC = new OrderCreation();
			OC.getNAStageName();

			if (svc.equals("LOC") || svc.equals("DRV") || svc.equals("SDC") || svc.equals("FRG")
					|| svc.equals("3PLAST")) {

				// --Get the timeZone
				String tzone = isElementPresent("NDelTimeZone_id").getText();
				String rectime = getTimeAsTZone(tzone);

				// --Enter Actual DL time
				isElementPresent("NDelTime_id").clear();
				isElementPresent("NDelTime_id").sendKeys(rectime);
				logger.info("Enter Actual DL Time");

				// --Enter SIgnature
				isElementPresent("NDelsign_id").clear();
				isElementPresent("NDelsign_id").sendKeys("RVOza");
				logger.info("Enter Signature");

				// --Click on Confirm DL
				WebElement ConfirmDel = isElementPresent("NDelbutton_id");
				wait.until(ExpectedConditions.elementToBeClickable(ConfirmDel));
				jse.executeScript("arguments[0].click();", ConfirmDel);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				logger.info("Clicked on Confirm DEL button");

				try {
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
					WebElement DOK = Driver.findElement(By.id("iddataok"));
					jse.executeScript("arguments[0].click();", DOK);
					logger.info("Click on OK of Dialogue box");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				} catch (Exception e) {
					logger.info("Dialogue is not exist");

				}
			}

			if (svc.equals("SD") || svc.equals("PA") || svc.equals("FRA")) {
				// --Get the timeZone
				String tzone = isElementPresent("TLSDDActTimZone_id").getText();
				String rectime = getTimeAsTZone(tzone);

				// --Enter Actual Del Time
				isElementPresent("TLDActDLTime_id").clear();
				isElementPresent("TLDActDLTime_id").sendKeys(rectime);
				isElementPresent("TLDActDLTime_id").sendKeys(Keys.TAB);

				// --Enter Signature
				isElementPresent("TLDSignature_id").clear();
				isElementPresent("TLDSignature_id").sendKeys("RVOza");
				logger.info("Enter Signature");

				// --Click on Confirm DL
				WebElement ConDL = isElementPresent("TLDConfDL2_id");
				wait.until(ExpectedConditions.elementToBeClickable(ConDL));
				jse.executeScript("arguments[0].click();", ConDL);
				logger.info("Clicked on Confirm DEL button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --CHeck parameter modify validation
				reCalc(svc);

				// --Pop Up
				boolean dlpop = Driver.getPageSource().contains("NetLink Global Logistics");

				if (dlpop == true) {
					isElementPresent("TLDPUOK_id").click();
					logger.info("Clicked on OK button of pop up");

				}

				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			}

			if (svc.equals("AIR")) {
				// --Get the timeZone
				String tzone = isElementPresent("TLDAIRTZone_id").getText();
				String rectime = getTimeAsTZone(tzone);

				// --Enter Actual Del Time
				isElementPresent("TLDAIRActualDTime_id").clear();
				isElementPresent("TLDAIRActualDTime_id").sendKeys(rectime);
				isElementPresent("TLDAIRActualDTime_id").sendKeys(Keys.TAB);

				// --Enter Signature
				isElementPresent("TLDAIRSign_id").clear();
				isElementPresent("TLDAIRSign_id").sendKeys("RVOza");
				logger.info("Enter Signature");

				// --Click on Confirm DL
				WebElement ConDL = isElementPresent("TLDConfDL2_id");
				wait.until(ExpectedConditions.elementToBeClickable(ConDL));
				jse.executeScript("arguments[0].click();", ConDL);
				logger.info("Clicked on Confirm DEL button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --CHeck parameter modify validation
				reCalc(svc);

				// --Pop Up
				boolean dlpop = Driver.getPageSource().contains("NetLink Global Logistics");

				if (dlpop == true) {
					isElementPresent("TLDPUOK_id").click();
					logger.info("Clicked on OK button of pop up");

				}

				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			}

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 9, 5, "PASS");

			}
		} catch (Exception e) {
			logger.error(e);
			logger.info("Line number is: " + e.getStackTrace()[0].getLineNumber());
			getScreenshot(Driver, "DELIVER" + svc);
			System.out.println("DELIVER Not Exist in Flow!!");
			logger.info("DELIVER Not Exist in Flow!!");
			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 8, 5, "FAIL");

			}
			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 9, 5, "FAIL");

			}
		}

	}
	public void reCalc(String svc) {
		WebDriverWait wait = new WebDriverWait(Driver, 120);
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

				try {
					WebElement Validation = isElementPresent("EOValidation_id");
					wait.until(ExpectedConditions.visibilityOf(Validation));
					String ValMsg = Validation.getText();
					logger.info("Validation==" + ValMsg);

					if (ValMsg.equalsIgnoreCase("Pickup Phone# is Required.")) {
						// --Enter Pickup Phone No
						WebElement PUPhoneNo = isElementPresent("EOPickupPhone_id");
						act.moveToElement(PUPhoneNo).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(PUPhoneNo));
						PUPhoneNo.sendKeys("1112221112");
						logger.info("Entered PU Phone");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// --Click on Save Changes button
						SaveChanges = isElementPresent("TLSaveChanges_id");
						act.moveToElement(SaveChanges).build().perform();
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
						wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
						act.moveToElement(SaveChanges).click().perform();
						logger.info("Click on Save Changes button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						Thread.sleep(2000);
					}
				} catch (Exception eRequiredMsg) {
				}

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

				if (svc.equals("LOC") || svc.equals("DRV") || svc.equals("SDC") || svc.equals("FRG")
						|| svc.equals("3PLAST")) {
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

					// --Click on Confirm DL button
					WebElement ConfDL = isElementPresent("TLDConfDL2_id");
					wait.until(ExpectedConditions.elementToBeClickable(ConfDL));
					Thread.sleep(2000);
					act.moveToElement(ConfDL).click().build().perform();
					logger.info("Click on Confirm DEL button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				}

			}

		} catch (Exception PModify) {
			logger.info("Validation message is not displayed for Recalculate the charges");

		}
	}
	
	
	public static void inventorySearch(int i)
			throws InterruptedException, IOException, EncryptedDocumentException, InvalidFormatException {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 40);// wait time
		// Actions act = new Actions(driver);

		String PartName = getData("Sheet1", i, 37);
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
		/*
		 * // --CLick on FSL Dropdown String Env = storage.getProperty("Env");
		 * System.out.println("Env " + Env); String FSLValue = null; if
		 * (Env.equalsIgnoreCase("STG")) { FSLValue = "AUTOMATION RV (F5505)"; } else if
		 * (Env.equalsIgnoreCase("Pre-Prod")) { FSLValue =
		 * "ORD - MNX DC: CHICAGO IL (F5099)"; }
		 */

		WebElement FSL = isElementPresent("OCPSASFSlDrp_id");
		wait.until(ExpectedConditions.elementToBeClickable(FSL));
		Select FSLdrp = new Select(FSL);
		FSLdrp.selectByIndex(1);
		logger.info("Selected FSL");

		// --Field 1
		WebElement Field1 = isElementPresent("OCPSField1_id");
		wait.until(ExpectedConditions.elementToBeClickable(Field1));
		Field1.clear();
		Field1.sendKeys(PartName);
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

		// --CLick on save button
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
	
	// -- Create ASN
	
	public void ASN_create(int i) throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		WebDriverWait wait2 = new WebDriverWait(Driver, 60);// wait time
		Actions act = new Actions(Driver);

		Driver.navigate().refresh();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		logger.info("Page is refreshed");
		Thread.sleep(500);
		
		try {
			
			// --Click on Operation menu and select SPL
			WebElement operation_menu = isElementPresent("ConnectOperations_id");
			act.moveToElement(operation_menu).build().perform();
			Thread.sleep(500);
			act.click(operation_menu).build().perform();
			Thread.sleep(1500);
			 //-- opt SPL from OPeration menu
			WebElement spl = isElementPresent("spl_xpath");
			highLighted_screenshot(spl, Driver, "SPL_menu");
			spl.click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			
			//-- ASN Creation 
			Asn();
			
			// --Save and store ASN WO ID and Validate memo
			
			
			

		} catch (Exception e) {
			logger.error(e);
			
		}

	}

	public void Asn() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		WebDriverWait wait2 = new WebDriverWait(Driver, 60);// wait time
		Actions act = new Actions(Driver);

		
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		
		Thread.sleep(500);
		
		try {
			
			//-- Select FSL Group
			
			WebElement fsl_grp = isElementPresent("fsl_grp_id");
			fsl_grp.clear();
			fsl_grp.sendKeys("AUTOMATION RV");
			Thread.sleep(500);
			logger.info("Enter FSL Group name");
			//-- opt FSL grp from dropdown
			
			WebElement fsl_option = isElementPresent("opt_fsl_grp_xpath");
			act.moveToElement(fsl_option).click(fsl_option).build().perform();
			
			// -- Click on Change default for select FSL group
			
			WebElement changeDefault = isElementPresent("fsl_change_xpath");
			changeDefault.click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			logger.info("Change default FSL for ASN creation");
			
			// --Click on create ASN
			WebElement ASN_create = isElementPresent("create_asn_xpath");
			act.moveToElement(ASN_create).build().perform();
			Thread.sleep(500);
			act.click(ASN_create).build().perform();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			getScreenshot(Driver, "ASN_creation_page");
			
			// -- Select Client Name
			
			Select Clent_name = new Select(isElementPresent("asn_clent_name_id"));
			Clent_name.selectByVisibleText("AUTOMATION INVENTORY PROFILE");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			logger.info("Clent_name : AUTOMATION INVENTORY PROFILE");
			
			// -- OPt inventory
			
			inventorySearch(15);

			// -- Click on Save button to create ASN
			WebElement save_asn = isElementPresent("asn_save_id");
			save_asn.click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			logger.info("Click on Save button for save ASN");
			
			WebElement asn_save_error = isElementPresent("asn_save_error_xpath");
			
			if(asn_save_error.isDisplayed()) {
				String today_date = current_date();
				// --Enter Current date 
				WebElement arrival_date = isElementPresent("asn_arrival_date_xpath");
				arrival_date.sendKeys(today_date, Keys.TAB);
				
				// --Enter Current Time
				
				
			}
			
		} catch (Exception e) {
			logger.error(e);
			
		}

	}
	
	public static void highLighted_screenshot(WebElement element, WebDriver driver, String screenshotName)
			throws IOException {
		// for (int i = 0; i < 2; i++) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
					"color: black; border: 4px solid red;");
			Thread.sleep(500);
			getScreenshot(driver, screenshotName);

			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// }

	}
	
}
