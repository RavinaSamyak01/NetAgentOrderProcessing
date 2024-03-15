package connect_OCBaseMethods;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.seleniumhq.jetty9.util.log.Log;

import netAgent_BasePackage.BaseInit;

public class OrderCreation extends BaseInit {

	static String rdytime, rectime, arrtime, tndrtime, ServID, dmi, ServiceID;

	public String orderCreation(int i) throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		Actions act = new Actions(Driver);
		String Env = storage.getProperty("Env");

		WebDriverWait wait = new WebDriverWait(Driver, 60);// wait time

		WebDriverWait wait2 = new WebDriverWait(Driver, 5);// wait time
		Robot r = new Robot();
		String ServiceID = null;
		String pck = null;
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

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
				Thread.sleep(2500);
			} catch (Exception Doc) {
				logger.info("Dialogue is not displayed for Customer");

			}

			if (i < 10) {
				// --wait until pU section is enabled
//				try {
//					
//					WebDriverWait wait3 = new WebDriverWait(Driver, 5);// wait time
//					wait3.until(ExpectedConditions.invisibilityOfElementLocated(
//							By.xpath("//*[@id='PickupSection'][@disabled='disabled']")));
//				} catch (Exception PUDIsable) {
//					logger.error(PUDIsable);
//					logger.info("Line number is: " + PUDIsable.getStackTrace()[0].getLineNumber());
//					getScreenshot(Driver, "PUSDisabled");
//					//WebDriverWait waitPUD = new WebDriverWait(Driver, 120);// wait time
//					wait2.until(ExpectedConditions.invisibilityOfElementLocated(
//							By.xpath("//*[@id='PickupSection'][@disabled='disabled']")));
//					logger.info("PU Section is Enabled");
//
//				}
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
					wait2.until(ExpectedConditions.invisibilityOfElementLocated(
							By.xpath("//input[contains(@class,'ng-invalid ng-invalid-required')]")));
					logger.info("PU Ready time is blank");
					msg.append("\n" + "PU Ready time is blank" + "\n");

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
				isElementPresent("OCDLPhone_id").sendKeys(DLPhone, Keys.TAB);
				logger.info("Entered DL Phone");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(2000);
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
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
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
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(1000);

// -- COMMODITY_DROPDOWN

				Select comodity_drpdown1 = new Select(isElementPresent("comodity_drpdown_xpath"));
				comodity_drpdown1.selectByIndex(1);
				logger.info("comodity dropdown is selected");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(2000);

				// --Commodity
				String Commodity = getData("OrderCreation", i, 23);
				isElementPresent("OCDesc_id").clear();
				isElementPresent("OCDesc_id").sendKeys(Commodity);
				isElementPresent("OCDesc_id").sendKeys(Keys.TAB);
				logger.info("Entered Commodity");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(1000);
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

				if (i == 3) {
					// p3P
					jse.executeScript("window.scrollBy(0,-350)", "");
					Thread.sleep(2000);

					// --Enter P3P service
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					Thread.sleep(2000);
					WebElement ServiceInpt = isElementPresent("EOServiceInp_id");
					wait.until(ExpectedConditions.elementToBeClickable(ServiceInpt));
					ServiceInpt.sendKeys(Keys.CONTROL, "a");
					ServiceInpt.sendKeys(Keys.BACK_SPACE);
					ServiceInpt.clear();
					ServiceInpt.clear();
					ServiceInpt.sendKeys("P3P");
					Thread.sleep(500);
					ServiceInpt.sendKeys(Keys.TAB);
					Thread.sleep(2000);
					logger.info("Enter P3P service in Service input");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					Thread.sleep(2000);
					// --Select 3P Account
					WebElement p3acc = isElementPresent("EO3PAcDrop_id");
					wait.until(ExpectedConditions.elementToBeClickable(p3acc));
					Select T3pAc = new Select(p3acc);
					T3pAc.selectByIndex(1);
					logger.info("Selected 3P account");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					// --Select 3P Service
					WebElement t3pService = isElementPresent("EO3PServiceS_id");
					act.moveToElement(t3pService).build().perform();
					jse.executeScript("arguments[0].scrollIntoView();", t3pService);
					Thread.sleep(2000);
					wait.until(ExpectedConditions.visibilityOf(t3pService));
					wait.until(ExpectedConditions.elementToBeClickable(t3pService));
					Select T3pService = new Select(t3pService);
					T3pService.selectByVisibleText("FEDEX_GROUND");
					logger.info("Selected 3P Service");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					// --Scroll down
					r.keyPress(KeyEvent.VK_TAB);
					jse.executeScript("window.scrollBy(0,250)", "");
					Thread.sleep(2000);

					selectDropOffLoc();

				} else if (i == 4) {
					// PA
					jse.executeScript("window.scrollBy(0,-350)", "");
					Thread.sleep(2000);

					// --Enter PA service
					WebElement ServiceInpt = isElementPresent("EOServiceInp_id");
					wait.until(ExpectedConditions.elementToBeClickable(ServiceInpt));
					ServiceInpt.clear();
					Thread.sleep(2000);
					ServiceInpt.clear();
					ServiceInpt.sendKeys("PA");
					Thread.sleep(2000);
					ServiceInpt.sendKeys(Keys.TAB);
					Thread.sleep(2000);
					logger.info("Enter PA service in Service input");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				} else if ((i == 5) || (i == 6) || (i == 7) || (i == 8) || (i == 9)) {
					logger.info("No need to run FedExSameDay services");

				}

				// --Click on Create Order button
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				WebElement order = isElementPresent("OCOProcess_id");
				jse.executeScript("arguments[0].scrollIntoView();", order);
				Thread.sleep(5000);
				act.moveToElement(order).build().perform();
				order = isElementPresent("OCOProcess_id");
				wait.until(ExpectedConditions.elementToBeClickable(order));
				jse.executeScript("arguments[0].click();", order);
				logger.info("Click on Create Order button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				try {

					WebDriverWait wait5 = new WebDriverWait(Driver, 5);

					// Use WebDriverWait to wait for the text to be present on the page
					Boolean sameairport = wait5
							.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"),
									"Pickup and Delivery airport are different. Do you want to make it same?"));

					if (sameairport == true) {
						logger.info("PopUp message is displayed for Same Airport");
						WebElement Yes = isElementPresent("OCSameApPupYes_xpath");
						wait5.until(ExpectedConditions.elementToBeClickable(Yes));
						jse.executeScript("arguments[0].click();", Yes);
						logger.info("Clicked on YES button of popup");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					}
				} catch (Exception eee) {

				}

				try {
					wait2.until(ExpectedConditions
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

						// -- COMMODITY_DROPDOWN

						Select comodity_drpdown = new Select(isElementPresent("comodity_drpdown_xpath"));
						comodity_drpdown.selectByIndex(1);
						logger.info("comodity dropdown is selected");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						Thread.sleep(2000);

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
							WebDriverWait wait5 = new WebDriverWait(Driver, 5);

							// Use WebDriverWait to wait for the text to be present on the page
							Boolean sameairport = wait5
									.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"),
											"Pickup and Delivery airport are different. Do you want to make it same?"));

							if (sameairport == true) {
								logger.info("PopUp message is displayed for Same Airport");
								WebElement Yes = isElementPresent("OCSameApPupYes_xpath");
								wait5.until(ExpectedConditions.elementToBeClickable(Yes));
								jse.executeScript("arguments[0].click();", Yes);
								logger.info("Clicked on YES button of popup");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

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
					wait2.until(ExpectedConditions
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
						/*
						 * order = isElementPresent("OCOProcess_id");
						 * act.moveToElement(order).build().perform();
						 * wait.until(ExpectedConditions.elementToBeClickable(order));
						 * jse.executeScript("arguments[0].click();", order);
						 * logger.info("Click on Create Order button");
						 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")
						 * )); Thread.sleep(2000);
						 */

						try {
							WebDriverWait wait5 = new WebDriverWait(Driver, 5);

							// Use WebDriverWait to wait for the text to be present on the page
							Boolean sameairport = wait5
									.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"),
											"Pickup and Delivery airport are different. Do you want to make it same?"));

							if (sameairport == true) {
								logger.info("PopUp message is displayed for Same Airport");
								WebElement Yes = isElementPresent("OCSameApPupYes_xpath");
								wait5.until(ExpectedConditions.elementToBeClickable(Yes));
								jse.executeScript("arguments[0].click();", Yes);
								logger.info("Clicked on YES button of popup");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							}
						} catch (Exception eee) {

						}

						try {
							wait.until(ExpectedConditions
									.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"modal-dialog\"]")));
							String DialogueContent = isElementPresent("AirSTationH_xpath").getText();
							logger.info("Content of the Dialogue is==" + DialogueContent);

							if (DialogueContent
									.contains("Quoted Pickup time cannot be greater than Schedule Drop time.")) {
								msg.append("Wrong QPT QDT set==FAIL" + "\n");
								try {
									// --CLick on Yes button
									WebElement YesProceed = isElementPresent("CPUDYesPrc_xpath");
									wait2.until(ExpectedConditions.elementToBeClickable(YesProceed));
									jse.executeScript("arguments[0].click();", YesProceed);
									logger.info("Click on Yes Proceed button");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"modal-dialog\"]")));

								} catch (Exception YesBTN) {
									// --CLick on Yes button
									WebElement YesProceed = isElementPresent("EOCont_xpath");
									wait2.until(ExpectedConditions.elementToBeClickable(YesProceed));
									act.moveToElement(YesProceed).click().build().perform();
									logger.info("Click on Yes Proceed button");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								}

							}

						} catch (Exception e) {
							logger.info("QPT QDT validation not displayed");
						}
						WebDriverWait wait1 = new WebDriverWait(Driver, 50);// wait time
						wait1.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//*[@class=\"modal-dialog modal-sm\"]")));

					} catch (Exception e) {

					}
				}

			}
			if (i >= 10 && i < 20) {
				// --Select Part
				try {
					inventorySearch(i);
				}

				catch (Exception e) {

					logger.info("Part selection option is not available");
					getScreenshot(Driver, "inventory_select_option_UI");
				}
				// --Del Zip
				String DLZip = getData("OrderCreation", i, 11);
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
				String DelCompany = getData("OrderCreation", i, 12);
				isElementPresent("OCDLComp_id").clear();
				isElementPresent("OCDLComp_id").sendKeys(DelCompany);
				logger.info("Entered DL Company");

				// --Del Address
				WebElement DL = isElementPresent("OCDLAdd_id");
				jse.executeScript("arguments[0].click();", DL);
				logger.info("Entered DL Address");

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
				String ServID = getData("OrderCreation", i, 18);

				// --Enter the ServiceID
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idNewOrderServiceId")));
				WebElement Service = isElementPresent("OCServiceID_id");
				wait.until(ExpectedConditions.elementToBeClickable(Service));
				Service.clear();
				Service.sendKeys(ServID);
				Service.sendKeys(Keys.TAB);
				if (ServID.equalsIgnoreCase("CPU")) {

				} else if (ServID.equalsIgnoreCase("D3P") || ServID.equalsIgnoreCase("3PLAST")) {
					// --Select 3P Account
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cmb3PAccount")));
					WebElement p3acc = isElementPresent("EO3PAcDrop_id");
					wait.until(ExpectedConditions.elementToBeClickable(p3acc));
					Select T3pAc = new Select(p3acc);
					T3pAc.selectByIndex(1);
					logger.info("Selected 3P account");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					try {
						// --select FedEx service
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("scrollCarrierService")));
						WebElement ServiceSelect = isElementPresent("OCFDXServiceSelect_id");
						act.moveToElement(ServiceSelect).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(ServiceSelect));
						jse.executeScript("arguments[0].click();", ServiceSelect);
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						logger.info("Selected FedEx service");
					}

					catch (Exception e) {

						logger.info("Fed is not select as 3P Account");

						WebElement ServiceSelect = isElementPresent("UPS_3pservice_id");
						act.moveToElement(ServiceSelect).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(ServiceSelect));
						jse.executeScript("arguments[0].click();", ServiceSelect);
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						logger.info("Selected UPS ground service");
					}

					try {
						// --Select 3P Service
						// --Select 3P Service
						WebElement t3pService = isElementPresent("EO3PServiceS_id");
						act.moveToElement(t3pService).build().perform();
						jse.executeScript("arguments[0].scrollIntoView();", t3pService);
						Thread.sleep(2000);
						wait.until(ExpectedConditions.visibilityOf(t3pService));
						wait.until(ExpectedConditions.elementToBeClickable(t3pService));
						Select T3pService = new Select(t3pService);
						T3pService.selectByVisibleText("FEDEX_GROUND");
						logger.info("Selected 3P Service");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					} catch (Exception e) {

					}
					// --Select Drop Off Location
					selectDropOffLoc();

				} else if (ServID.equalsIgnoreCase("H3P")) {
					// --Select 3P Account
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cmb3PAccount")));
					WebElement p3acc = isElementPresent("EO3PAcDrop_id");
					wait.until(ExpectedConditions.elementToBeClickable(p3acc));
					Select T3pAc = new Select(p3acc);

					if (Env.equalsIgnoreCase("Test") || Env.equalsIgnoreCase("STG")) {
						T3pAc.selectByIndex(1);
						logger.info("Selected 3P account");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					}

					else if (Env.equalsIgnoreCase("PROD")) {

						T3pAc.selectByValue("string:Cust FedEx");
						logger.info("Selected 3P account");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					}

					// --select FedEx service
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("scrollCarrierService")));
					WebElement ServiceSelect = isElementPresent("OCFDXServiceSelect_xpath");
					act.moveToElement(ServiceSelect).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(ServiceSelect));
					jse.executeScript("arguments[0].click();", ServiceSelect);
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					logger.info("Selected FedEx service");
				}

				try {
					// --Select 3P Service
					// --Select 3P Service
					WebElement t3pService = new WebDriverWait(Driver, 7)
							.until(ExpectedConditions.visibilityOfElementLocated(By.id("EO3PServiceS_id")));

					// WebElement t3pService = isElementPresent("EO3PServiceS_id");
					act.moveToElement(t3pService).build().perform();
					jse.executeScript("arguments[0].scrollIntoView();", t3pService);
					Thread.sleep(2000);
					wait.until(ExpectedConditions.visibilityOf(t3pService));
					wait.until(ExpectedConditions.elementToBeClickable(t3pService));
					Select T3pService = new Select(t3pService);
					T3pService.selectByVisibleText("FEDEX_GROUND");
					logger.info("Selected 3P Service");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				} catch (Exception e) {
					System.out.println("Element with ID 	did not become visible within the specified timeout.");
				}

				// --Scroll Up
				r.keyPress(KeyEvent.VK_TAB);
				jse.executeScript("window.scrollBy(0,250)", "");
				Thread.sleep(2000);

				// --Total Mileage
				String tmile = isElementPresent("OCTotalMil_id").getAttribute("value");
				logger.info("Total Mileage==" + tmile);

				setData("OrderCreation", i, 27, dmi);
				setData("OrderCreation", i, 29, tmile);

				// --Scroll down
				/*
				 * r.keyPress(KeyEvent.VK_TAB); jse.executeScript("window.scrollBy(0,250)", "");
				 * Thread.sleep(2000);
				 */

				// --GetScreenShot
				getScreenshot(Driver, "CreateOrder_" + ServID);

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

				try {
					wait2.until(ExpectedConditions
							.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"modal-dialog\"]")));
					String DialogueContent = isElementPresent("CPUDContent_xpath").getText();
					logger.info("Content of the Dialogue is==" + DialogueContent);

					try {
						// --CLick on Yes button
						WebElement YesProceed = isElementPresent("CPUDYesPrc_xpath");
						wait.until(ExpectedConditions.elementToBeClickable(YesProceed));
						jse.executeScript("arguments[0].click();", YesProceed);
						logger.info("Click on Yes Proceed button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"modal-dialog\"]")));

					} catch (Exception YesBTN) {
						// --CLick on Yes button
						WebElement YesProceed = isElementPresent("CPUDYesPrc_xpath");
						wait.until(ExpectedConditions.elementToBeClickable(YesProceed));
						act.moveToElement(YesProceed).click().build().perform();
						logger.info("Click on Yes Proceed button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					}

				} catch (Exception e) {
					logger.info("Confirmation message for warehouse is not displayed");
				}

			}

			if (i > 20) {

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

				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
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
				isElementPresent("OCDLPhone_id").sendKeys(DLPhone, Keys.TAB);
				logger.info("Entered DL Phone");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(2000);
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

				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(2000);

				// --Total Qty
				isElementPresent("OCTotalQty_id").clear();
				// isElementPresent("OCTotalQty_id").sendKeys(Keys.BACK_SPACE);
				isElementPresent("OCTotalQty_id").sendKeys(Keys.CONTROL, "a");
				isElementPresent("OCTotalQty_id").sendKeys(Keys.BACK_SPACE);
				isElementPresent("OCTotalQty_id").clear();
				isElementPresent("OCTotalQty_id").clear();
				isElementPresent("OCTotalQty_id").sendKeys("01");
				isElementPresent("OCTotalQty_id").sendKeys(Keys.TAB);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
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
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(1000);

// -- COMMODITY_DROPDOWN

				Select comodity_drpdown1 = new Select(isElementPresent("comodity_drpdown_xpath"));
				comodity_drpdown1.selectByIndex(1);
				logger.info("comodity dropdown is selected");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(2000);

				// --Commodity
				String Commodity = getData("OrderCreation", i, 23);
				isElementPresent("OCDesc_id").clear();
				isElementPresent("OCDesc_id").sendKeys(Commodity);
				isElementPresent("OCDesc_id").sendKeys(Keys.TAB);
				logger.info("Entered Commodity");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(1000);
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

				// LOC for RTE creation
				jse.executeScript("window.scrollBy(0,-350)", "");
				Thread.sleep(2000);

				// --Enter LOC service
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(2000);
				WebElement ServiceInpt = isElementPresent("EOServiceInp_id");
				wait.until(ExpectedConditions.elementToBeClickable(ServiceInpt));
				ServiceInpt.sendKeys(Keys.CONTROL, "a");
				ServiceInpt.sendKeys(Keys.BACK_SPACE);
				ServiceInpt.clear();
				ServiceInpt.clear();
				ServiceInpt.sendKeys("LOC");
				Thread.sleep(500);
				ServiceInpt.sendKeys(Keys.TAB);
				Thread.sleep(2000);
				logger.info("Enter LOC service in Service input");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(2000);

				// --Click on Create Order button
				WebElement order = isElementPresent("OCSPLCreateOrder_xpath");
				jse.executeScript("arguments[0].scrollIntoView();", order);
				Thread.sleep(2000);
				act.moveToElement(order).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(order));
				jse.executeScript("arguments[0].click();", order);
				logger.info("Click on Create Order button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(2000);

				try {
					wait2.until(ExpectedConditions
							.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"modal-dialog\"]")));
					String DialogueContent = isElementPresent("CPUDContent_xpath").getText();
					logger.info("Content of the Dialogue is==" + DialogueContent);

					try {
						// --CLick on Yes button
						WebElement YesProceed = isElementPresent("CPUDYesPrc_xpath");
						wait.until(ExpectedConditions.elementToBeClickable(YesProceed));
						jse.executeScript("arguments[0].click();", YesProceed);
						logger.info("Click on Yes Proceed button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"modal-dialog\"]")));

					} catch (Exception YesBTN) {
						// --CLick on Yes button
						WebElement YesProceed = isElementPresent("CPUDYesPrc_xpath");
						wait.until(ExpectedConditions.elementToBeClickable(YesProceed));
						act.moveToElement(YesProceed).click().build().perform();
						logger.info("Click on Yes Proceed button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					}

				} catch (Exception e) {
					logger.info("Confirmation message for warehouse is not displayed");
				}

			}

			WebElement PickUPID = isElementPresent("OCPickuPID_xpath");
			wait.until(ExpectedConditions.visibilityOf(PickUPID));
			wait.until(ExpectedConditions.elementToBeClickable(PickUPID));
			pck = PickUPID.getText();
			System.out.println("Pickup = " + pck);
			logger.info("=====Pickup =" + pck + "=====" + "\n");
			msg.append("=====Pickup =" + pck + "=====" + "\n");

			// --Set PickUPID
			setData("OrderCreation", i, 32, pck);
			setResultData("Result", i, 2, pck);

			// --Set Pass in TestScenarios
			if (i == 1) {
				setData("TC_OrderProcess", 1, 3, pck);
				setData("TC_OrderProcess", 1, 5, "PASS");

			} else if (i == 2) {
				setData("TC_OrderProcess", 12, 3, pck);
				setData("TC_OrderProcess", 12, 5, "PASS");

			}

			// --Click on Edit Order
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
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

			if (i == 1) {
				setData("TC_OrderProcess", 1, 5, "FAIL");

			} else if (i == 2) {
				setData("TC_OrderProcess", 12, 5, "FAIL");

			}
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
		return pck;

	}

	public void movjobstatus() {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait;
		wait = new WebDriverWait(Driver, 40);// wait time
		Actions act = new Actions(Driver);

		try {
			// --Move to Job Status Tab
			WebElement JoStatusTab = isElementPresent("TLJobStatusTab_id");
			wait.until(ExpectedConditions.visibilityOf(JoStatusTab));
			wait.until(ExpectedConditions.elementToBeClickable(JoStatusTab));
			act.moveToElement(JoStatusTab).click().build().perform();
			logger.info("Clicked on Job Status Tab");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			Thread.sleep(5000);

		} catch (Exception e) {

		}

	}

	public void compareCourier(int i, String SVC)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait;
		try {
			wait = new WebDriverWait(Driver, 40);// wait time

		} catch (Exception ewait) {
			wait = new WebDriverWait(Driver, 120);// wait time

		}
		Actions act = new Actions(Driver);

		String ExpCourierID = getData("Sheet1", i, 38);
		logger.info("Expected CourierID==" + ExpCourierID);
		msg.append("Expected CourierID==" + ExpCourierID + "\n");
		try {
			// --Get CourierID'
			// --Send Pull Alert
			WebElement Courier = isElementPresent("SPCourierID_xpath");
			act.moveToElement(Courier).build().perform();
			wait.until(ExpectedConditions.visibilityOf(Courier));
			String ActCourierID = Courier.getText();
			logger.info("Actual CourierID==" + ActCourierID);
			msg.append("Actual CourierID==" + ActCourierID + "\n");

			if (ExpCourierID.equalsIgnoreCase(ActCourierID) && SVC.equalsIgnoreCase("H3P")
					|| SVC.equalsIgnoreCase("D3P") || SVC.equalsIgnoreCase("3PLAST") || SVC.equalsIgnoreCase("CPU")) {
				logger.info("Default selected CourierID is matched with Customer's CourierId==PASS");
				msg.append("Default selected CourierID is matched with Customer's CourierId==PASS" + "\n");
				getScreenshot(Driver, "DefaultCourier");
				setResultData("Result", 20, 4, "PASS");

			} else if (ExpCourierID != (ActCourierID) && SVC.equalsIgnoreCase("H3P") || SVC.equalsIgnoreCase("D3P")
					|| SVC.equalsIgnoreCase("3PLAST") || SVC.equalsIgnoreCase("CPU")) {
				logger.info("Default selected CourierID is not matched with Customer's CourierId==FAIL");
				msg.append("Default selected CourierID is not matched with Customer's CourierId==FAIL" + "\n");
				setResultData("Result", 20, 4, "FAIL");
				selectCourier(SVC);

			} else if (ExpCourierID.equalsIgnoreCase(ActCourierID) && SVC != ("H3P") || SVC != ("D3P")
					|| SVC != ("3PLAST") || SVC != ("CPU")) {
				logger.info("Default selected CourierID is as per expected");
				msg.append("Default selected CourierID is as per expected" + "\n");

			} else if (ExpCourierID != (ActCourierID) && SVC != ("H3P") || SVC != ("D3P") || SVC != ("3PLAST")
					|| SVC != ("CPU")) {
				logger.info("Default selected CourierID is not as per expected");
				msg.append("Default selected CourierID is not as per expected" + "\n");
				selectCourier(SVC);
			}
		} catch (Exception ee) {
			logger.error(ee);
			getScreenshot(Driver, "CompareCourier" + SVC);
			String Error = ee.getMessage();
			setResultData("Result", 20, 4, "FAIL");
			setResultData("Result", 20, 5, Error);
			System.out.println("Courier is not there");
			logger.info("Courier is not there");
		}

	}

	public void selectCourier(String SVC) {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait;
		try {
			wait = new WebDriverWait(Driver, 40);// wait time

		} catch (Exception ewait) {
			wait = new WebDriverWait(Driver, 120);// wait time

		}
		Actions act = new Actions(Driver);

		// --Select Courier
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hlkedit")));
			isElementPresent("CourierEdit_id").click();
			logger.info("Clicked on Edit button of Driver");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"modal-dialog modal-md\"]")));
			getScreenshot(Driver, "CourierSearch" + SVC);

			// --Search Driver by AgentID
			wait.until(ExpectedConditions.elementToBeClickable(By.id("txtCourierId")));
			isElementPresent("CourierID_id").clear();
			isElementPresent("CourierID_id").sendKeys("34769");
			logger.info("Enter CourierID");

			// --Click on Search
			WebElement SearchBTN = isElementPresent("PDSearch_id");
			jse.executeScript("arguments[0].click();", SearchBTN);
			logger.info("Clicked on search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Get the List of Agent
			List<WebElement> Agents = Driver
					.findElements(By.xpath("//*[@id=\"scrollboxEditAgent\"]//a[contains(@id,'AgentId')]"));

			int TotalAgent = Agents.size();
			logger.info("Total Agent==" + TotalAgent);
			String AgentValue = null;
			for (int agent = 0; agent < TotalAgent; agent++) {
				WebElement AgentID = Agents.get(agent);
				String AgentIDV = Agents.get(agent).getText();
				act.moveToElement(AgentID).build().perform();
				AgentValue = AgentIDV;
				if (AgentIDV.equalsIgnoreCase("34769")) {
					logger.info("Searched Agent is exist");

					// --Select the Agent
					act.moveToElement(AgentID).build().perform();
					act.moveToElement(AgentID).click().perform();
					logger.info("Select the AgentID");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hlkedit")));
					AgentValue = AgentIDV;

				} else {
					logger.info("There is no Agent with search parameter");

				}

			}

			// --Compare the Searched Agent and selected Agent
			String AgeValue = isElementPresent("PDAgentValue_id").getText();
			logger.info("Selected agent is==" + AgeValue);

			if (AgeValue.equalsIgnoreCase(AgentValue)) {
				logger.info("Selected Agent is displayed in Driver section");

			} else {
				logger.info("Selected Agent is not displayed in Driver section");

			}

			// msg.append("==RTE Edit Driver Test End==" + "\n");
			msg.append("Edit Driver Test=PASS" + "\n");

		} catch (Exception ex) {
			logger.error(ex);
			logger.info("Line number is: " + ex.getStackTrace()[0].getLineNumber());
			msg.append("Edit Courier Test=FAIL" + "\n");

		}
	}

	public void searchJob(int i) throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 60);// wait time;
		Actions act = new Actions(Driver);
		WebDriverWait wait2 = new WebDriverWait(Driver, 10);// wait time;
		try {

			try {
				// Enter JobID*
				wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
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

				wait2.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
						By.xpath("//*[@class=\"OpenCloseClass dropdown open\"]//ul")));

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
			setResultData("Result", 22, 4, "PASS");

		} catch (Exception ewait) {
			getScreenshot(Driver, "SearchJobError" + i);
			String Error = ewait.getMessage();
			setResultData("Result", 22, 4, "FAIL");
			setResultData("Result", 22, 5, Error);

		}

	}

	public String getServiceID() {
		// --Get ServiceID

		String svc = Driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);
		logger.info("Service is==" + svc);

		return svc;
	}

	public String getStageName() {
		WebDriverWait wait;
		try {
			wait = new WebDriverWait(Driver, 40);// wait time

		} catch (Exception ewait) {
			wait = new WebDriverWait(Driver, 120);// wait time

		}
		// --Get the Stage Name
		WebElement Stage = isElementPresent("EOStageName_id");
		wait.until(ExpectedConditions.visibilityOf(Stage));
		String StageName = Stage.getText();
		System.out.println(StageName);
		logger.info("Stage=" + StageName);
		msg.append("Stage=" + StageName + "\n");
		return StageName;

	}

	public void refreshApp() {
		WebDriverWait wait;
		try {
			wait = new WebDriverWait(Driver, 40);// wait time

		} catch (Exception ewait) {
			wait = new WebDriverWait(Driver, 120);// wait time

		}
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		try {
			WebElement NGLLOgo = isElementPresent("RefreshLogo_xpath");
			wait.until(ExpectedConditions.elementToBeClickable(NGLLOgo));
			act.moveToElement(NGLLOgo).build().perform();
			js.executeScript("arguments[0].click();", NGLLOgo);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
		} catch (Exception refresh) {
			WebElement NGLLOgo = isElementPresent("RefreshLogo_xpath");
			wait.until(ExpectedConditions.elementToBeClickable(NGLLOgo));
			act.moveToElement(NGLLOgo).build().perform();
			act.moveToElement(NGLLOgo).click().perform();
			js.executeScript("arguments[0].click();", NGLLOgo);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
		}

	}

	public void unknowShipper(int i) throws EncryptedDocumentException, InvalidFormatException, IOException {
		WebDriverWait wait = new WebDriverWait(Driver, 60);// wait time
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);
		WebDriverWait wait2 = new WebDriverWait(Driver, 7);// wait time

		try {

			try {
				// --Unknown Shipper click
				WebElement UnShipper = isElementPresent("TLUnShipp_id");
				wait.until(ExpectedConditions.visibilityOf(UnShipper));
				wait.until(ExpectedConditions.elementToBeClickable(UnShipper));
				act.moveToElement(UnShipper).build().perform();
				js.executeScript("arguments[0].click();", UnShipper);
				logger.info("Clicked on Unknown Shipper");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --Wait for pop up of Unknown Shipper
				wait2.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"modal-dialog modal-sm\"]")));

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
				setData("OrderCreation", i, 31, cost);
				logger.info("Scroll down to Get the Rate");

				// --Click on Save Changes
				isElementPresent("TLSaveChanges_id").click();
				logger.info("Clicked on Save Changes button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --Set result in test scenarios
				if (i == 2) {
					setData("TC_OrderProcess", 13, 5, "PASS");
				}
			} catch (Exception e) {
				logger.info(e);
				getScreenshot(Driver, "UnknownShippereRROR");
				// --Set result in test scenarios
				if (i == 2) {
					setData("TC_OrderProcess", 13, 5, "FAIL");
				}
			}
			setResultData("Result", 14, 4, "PASS");

		} catch (Exception ewait) {
			logger.info(ewait);
			getScreenshot(Driver, "eUnknownShipperError");
			String Error = ewait.getMessage();
			setResultData("Result", 14, 4, "FAIL");
			setResultData("Result", 14, 5, Error);
		}

	}

	public void selectFlight(int i)
			throws InterruptedException, EncryptedDocumentException, InvalidFormatException, IOException {
		WebDriverWait wait = new WebDriverWait(Driver, 60);// wait time
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);
		WebDriverWait wait1 = new WebDriverWait(Driver, 30);// wait time

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		try {
			// --Move to Job Status Tab
			WebElement JoStatusTab = isElementPresent("TLJobStatusTab_id");
			wait.until(ExpectedConditions.visibilityOf(JoStatusTab));
			wait1.until(ExpectedConditions.elementToBeClickable(JoStatusTab));
			act.moveToElement(JoStatusTab).click().build().perform();
			logger.info("Clicked on Job Status Tab");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Click on Select Flight
			WebElement SelectFlight = isElementPresent("TLSelFlight_id");
			wait1.until(ExpectedConditions.elementToBeClickable(SelectFlight));
			SelectFlight.click();
			logger.info("Clicked on Select Flight button");

			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			} catch (Exception ExtraWait) {
				WebDriverWait wait2 = new WebDriverWait(Driver, 50);// wait time
				wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			}

			wait.until(
					ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"ItineraryForm\"]")));
			Thread.sleep(2000);

			// --CLick on Select Flight
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hlkSel_0")));
			WebElement Select1stFlight = isElementPresent("TLSelect1stFlgt_id");
			wait1.until(ExpectedConditions.elementToBeClickable(Select1stFlight));
			act.moveToElement(Select1stFlight).build().perform();
			js.executeScript("arguments[0].click();", Select1stFlight);
			logger.info("Selected 1st flight");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Click on Assign button
			WebElement AssignFlight = isElementPresent("TLAssignFlght_xpath");
			wait1.until(ExpectedConditions.elementToBeClickable(AssignFlight));
			act.moveToElement(AssignFlight).build().perform();
			js.executeScript("arguments[0].click();", AssignFlight);
			logger.info("Clicked on Assign button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --station hr validation
			try {
				// --Click on Yes button
				WebElement STationHR = isElementPresent("AIrstHrPup_xpath");
				wait.until(ExpectedConditions.visibilityOf(STationHR));
				String StHMsg = STationHR.getText();
				logger.info("Message==" + StHMsg);
				WebElement BtnYes = isElementPresent("CPUDYesPrc_xpath");
				wait1.until(ExpectedConditions.elementToBeClickable(BtnYes));
				act.moveToElement(BtnYes).build().perform();
				js.executeScript("arguments[0].click();", BtnYes);
				logger.info("Clicked on OK button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			} catch (Exception ee) {

			}
			try {

				WebElement Validation = isElementPresent("Error_id");
				wait.until(ExpectedConditions.visibilityOf(Validation));
				String ValMsg = Validation.getText();
				logger.info("Validation==" + ValMsg);

				if (ValMsg
						.equalsIgnoreCase("Please select the appropriate Product Code before assigning the flight.")) {

					// --Select Product Code
					WebElement ProductCode = isElementPresent("TLSFProdCode_id");
					wait1.until(ExpectedConditions.elementToBeClickable(ProductCode));
					Select FSLdrp = new Select(ProductCode);
					FSLdrp.selectByIndex(1);
					logger.info("Selected Product Code");
					Thread.sleep(2000);

					// --Click on Assign button
					AssignFlight = isElementPresent("TLAssignFlght_xpath");
					act.moveToElement(AssignFlight).build().perform();
					wait1.until(ExpectedConditions.elementToBeClickable(AssignFlight));
					js.executeScript("arguments[0].click();", AssignFlight);
					logger.info("Clicked on Assign button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					if (i == 2) {
						setData("TC_OrderProcess", 14, 5, "PASS");
					}
					getScreenshot(Driver, "SelectFlight");

					try {
						try {
							// --Click on Yes button
							wait1.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnYes")));
							WebElement BtnYes = isElementPresent("EOAIRUnSHYes_id");
							wait1.until(ExpectedConditions.elementToBeClickable(BtnYes));
							act.moveToElement(BtnYes).build().perform();
							js.executeScript("arguments[0].click();", BtnYes);
							logger.info("Clicked on Yes button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						} catch (Exception ee) {
							// --Click on Yes button
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@ng-click=\"ok()\"]")));
							String StHMsg = isElementPresent("AirSTationH_xpath").getText();
							logger.info("Message==" + StHMsg);
							WebElement BtnYes = isElementPresent("CPUDYesPrc_xpath");
							wait1.until(ExpectedConditions.elementToBeClickable(BtnYes));
							act.moveToElement(BtnYes).build().perform();
							js.executeScript("arguments[0].click();", BtnYes);
							logger.info("Clicked on Yes button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						}

					} catch (Exception e) {

					}

				}

			} catch (Exception ee) {
				logger.info("Issue in Flight selection");
				if (i == 2) {
					setData("TC_OrderProcess", 14, 5, "FAIL");
				}
			}

		} catch (Exception e) {
			getScreenshot(Driver, "FlightSelectIssue_" + i);
			// --Set result in test scenarios
			if (i == 2) {
				setData("TC_OrderProcess", 14, 5, "FAIL");
			}
		}

	}

	public void reCalc(String svc) {
		WebDriverWait wait;
		try {
			wait = new WebDriverWait(Driver, 60);// wait time

		} catch (Exception ewait) {
			wait = new WebDriverWait(Driver, 120);// wait time

		}
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);
		WebDriverWait wait2 = new WebDriverWait(Driver, 10);// wait time

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
					wait2.until(ExpectedConditions.visibilityOf(Validation));
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

				/*
				 * // --Enter SIgnature
				 * wait.until(ExpectedConditions.elementToBeClickable(By.id(
				 * "txtDeliverySignature"))); isElementPresent("TLDSignature_id").clear();
				 * isElementPresent("TLDSignature_id").sendKeys("RVOza");
				 * isElementPresent("TLDSignature_id").sendKeys(Keys.TAB);
				 * logger.info("Enter Signature");
				 */
				if (svc.equals("LOC") || svc.equals("3PLAST")) {
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
				} else if (svc.equals("SD") || svc.equals("PA")) {

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

				}

			}

		} catch (Exception PModify) {
			logger.info("Validation message is not displayed for Recalculate the charges");

		}
	}

	public static void inventorySearch(int i)
			throws InterruptedException, IOException, EncryptedDocumentException, InvalidFormatException {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 50);// wait time;
		WebDriverWait wait2 = new WebDriverWait(Driver, 10);// wait time;
		Actions act = new Actions(Driver);
		String Env = storage.getProperty("Env");
		try {

			String PartName = getData("OrderCreation", i, 37);
			// --Click on Search Parts button
			WebElement PartsSearch = isElementPresent("OCPartSearch_id");
			wait.until(ExpectedConditions.elementToBeClickable(PartsSearch));
			jse.executeScript("arguments[0].click();", PartsSearch);
			logger.info("Clicked on Parts search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"modal-dialog modal-lg\"]")));
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

			if (Env.equalsIgnoreCase("PROD")) {
				FSLdrp.selectByValue("string:5013");
				logger.info("Selected FSL");

			}

			else if (Env.equalsIgnoreCase("Test") || Env.equalsIgnoreCase("STG")) {
				FSLdrp.selectByIndex(1);
				logger.info("Selected FSL");
			}

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

			try {
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
					setResultData("Result", 16, 4, "PASS");

				} else {
					logger.info("Part is not added successfully==FAIL, Save Part button is not working");
					getScreenshot(Driver, "PartNotSaved_" + i);
					setResultData("Result", 16, 4, "FAIL");

				}
			} catch (Exception e) {
				logger.info("Parts is not available");
				getScreenshot(Driver, "SearchPartNotAvailable");
				msg.append("Parts not available" + "\n");
				setResultData("Result", 16, 4, "PASS");
				setResultData("Result", 16, 5, "Parts not available"); // wait time

				// --Close the parts table
				WebElement PartsClose = isElementPresent("TLQClose_id");
				wait.until(ExpectedConditions.visibilityOf(PartsClose));
				act.moveToElement(PartsClose).build().perform();
				act.moveToElement(PartsClose).click().build().perform();
				logger.info("Closed parts pop up");

			}
		} catch (Exception ewait) {
			logger.info(ewait);
			getScreenshot(Driver, "InventorySearchError");
			String Error = ewait.getMessage();
			setResultData("Result", 16, 4, "FAIL");
			setResultData("Result", 16, 5, Error); // wait time

		} // Actions act = new Actions(Driver);

	}

	public void selectDropOffLoc()
			throws InterruptedException, IOException, EncryptedDocumentException, InvalidFormatException {
		WebDriverWait wait = new WebDriverWait(Driver, 60);// wait time
		WebDriverWait wait2 = new WebDriverWait(Driver, 10);// wait time
		JavascriptExecutor jse = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		try {
			// --Select Drop Off Location
			try {
				WebElement dropOfLoc = isElementPresent("EOSelDropOfLoc_id");
				act.moveToElement(dropOfLoc).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(dropOfLoc));
				jse.executeScript("arguments[0].click();", dropOfLoc);

			} catch (Exception e) {
				WebElement dropOfLoc = isElementPresent("EODropOfLoc_id");
				act.moveToElement(dropOfLoc).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(dropOfLoc));
				jse.executeScript("arguments[0].click();", dropOfLoc);

			}
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

			getScreenshot(Driver, "DropOffLocationPopUp");
			logger.info("Drop Off Location Test= PASS");
			msg.append("Drop Off Location Test= PASS" + "\n");
			setResultData("Result", 17, 4, "PASS");

		} catch (Exception ee) {
			logger.info("issue in drop off location");
			logger.info(ee);
			getScreenshot(Driver, "DropofflocationnoLocation");
			String Error = ee.getMessage();
			setResultData("Result", 17, 4, "FAIL");
			setResultData("Result", 17, 5, Error); // wait time
			logger.info("Drop Off Location Test= FAIL");
			msg.append("Drop Off Location Test= FAIL" + "\n");
			try {
				// -close drop off pop up
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("IconClose")));
				WebElement CloseDropOf = isElementPresent("OCSelDropOfClose_id");
				wait.until(ExpectedConditions.elementToBeClickable(CloseDropOf));
				jse.executeScript("arguments[0].click();", CloseDropOf);
				logger.info("Click on Close button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// -Select FCA
				WebElement FCA = isElementPresent("OCFCA_xpath");
				wait.until(ExpectedConditions.visibilityOf(FCA));
				wait.until(ExpectedConditions.elementToBeClickable(FCA));
				jse.executeScript("arguments[0].click();", FCA);
				logger.info("Click on FCA button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --Enter Company
				WebElement FCAComp = isElementPresent("OCFCACompany_id");
				wait.until(ExpectedConditions.visibilityOf(FCAComp));
				wait.until(ExpectedConditions.elementToBeClickable(FCAComp));
				FCAComp.clear();
				FCAComp.sendKeys("Nothing Bundt Cakes");
				logger.info("Enter FCA Company");

				// --Enter Zipcode
				WebElement FCAZip = isElementPresent("OCFCAZip_id");
				wait.until(ExpectedConditions.visibilityOf(FCAZip));
				wait.until(ExpectedConditions.elementToBeClickable(FCAZip));
				FCAZip.clear();
				FCAZip.sendKeys("77598");
				logger.info("Enter FCA ZipCode");
				FCAZip.sendKeys(Keys.TAB);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --Enter Address
				WebElement FCAADD = isElementPresent("OCFCAAddress_id");
				wait.until(ExpectedConditions.visibilityOf(FCAADD));
				wait.until(ExpectedConditions.elementToBeClickable(FCAADD));
				FCAADD.clear();
				FCAADD.sendKeys("154 W Bay Area Blvd.");
				logger.info("Enter FCA Address");

				// --CLick on Save button
				WebElement FCASave = isElementPresent("OCFCASave_xpath");
				wait.until(ExpectedConditions.visibilityOf(FCASave));
				wait.until(ExpectedConditions.elementToBeClickable(FCASave));
				jse.executeScript("arguments[0].click();", FCASave);
				logger.info("Click on FCA Save button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				try {
					WebElement SuccMsg = isElementPresent("OCFCASuccess_id");
					wait.until(ExpectedConditions.visibilityOf(FCASave));
					String SUccMsg = SuccMsg.getText();
					logger.info(SUccMsg);
					logger.info("Consignee address saved successfully=PASS");
					setResultData("Result", 18, 4, "PASS");

				} catch (Exception e) {
					logger.info("Unable to add consignee Address=FAIL");
					getScreenshot(Driver, "DropofflocationFAIL");
					logger.info("Drop Off Location Test= FAIL");
					msg.append("Drop Off Location Test= FAIL" + "\n");
					String Error1 = e.getMessage();
					setResultData("Result", 18, 4, "FAIL");
					setResultData("Result", 18, 5, Error1); // wait time
				}

				// --CLick on Close button
				WebElement FCAClose = isElementPresent("OCFCAClose_id");
				wait.until(ExpectedConditions.visibilityOf(FCAClose));
				wait.until(ExpectedConditions.elementToBeClickable(FCAClose));
				jse.executeScript("arguments[0].click();", FCAClose);
				logger.info("Click on FCA Close button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			} catch (Exception e) {
				logger.info("Unable to close drop off location");
				logger.info(e);
				getScreenshot(Driver, "DropofflocationnoLocation");

				logger.info("Drop Off Location Test= FAIL");
				msg.append("Drop Off Location Test= FAIL" + "\n");

			}

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
		cracct.selectByVisibleText("Quantum DHL UK (ca_7787eb057e2c4e408589cd5e5dee7ba7)");
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

	public String shipLabel()
			throws IOException, InterruptedException, EncryptedDocumentException, InvalidFormatException {
		WebDriverWait wait = new WebDriverWait(Driver, 60);// wait time
		WebDriverWait wait2 = new WebDriverWait(Driver, 10);// wait time
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);
		String Env = storage.getProperty("Env");
		String TrackNo = null;
		try {
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

					if (Env.equalsIgnoreCase("PROD")) {

						p3acc.selectByValue("string:Cust FedEx");
						logger.info("Selected 3p Account");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					}

					else {
						p3acc.selectByIndex(1);
						logger.info("Selected 3p Account");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					}
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

					// --Print button
					wait.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//*[@id=\"scrollboxIframe\"]//button[@id=\"btnPrint\"]")));
					String TrackingNo = isElementPresent("SLSTrackNo_xpath").getText();
					logger.info("Tracking No==" + TrackingNo);
					setResultData("Result", 19, 2, TrackNo); // wait time // wait time

					// --Get the TrackingNo
					String inLine = TrackingNo;
					String[] lineSplits = inLine.split(":");
					TrackNo = lineSplits[1];
					logger.info("Tracking No==" + TrackNo);

				} catch (Exception eShipLabel) {
					// --If Ship Label is generated

					// --Send Email
					WebElement SendEmail = isElementPresent("SLSemail_id");
					wait.until(ExpectedConditions.elementToBeClickable(SendEmail));
					SendEmail.clear();
					SendEmail.sendKeys("ravina.prajapati.samyak@gmail.com");
					logger.info("Entered Email in Send Email");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					// --Click on Send Button
					WebElement SendBTN = isElementPresent("SLSSend_id");
					wait.until(ExpectedConditions.elementToBeClickable(SendBTN));
					js.executeScript("arguments[0].click();", SendBTN);
					logger.info("Clicked on Send button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					// ErrorMsg
					try {
						wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//*[@id=\"idValidationPopup\"]")));
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
						SendEmail.clear();
						SendEmail.sendKeys("ravina.prajapati.samyak@gmail.com");
						logger.info("Entered Email in Send Email");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// --Click on Send Button
						SendBTN = isElementPresent("SLSSend_id");
						wait.until(ExpectedConditions.elementToBeClickable(SendBTN));
						SendBTN.click();
						logger.info("Clicked on Send button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
						System.out.println(
								"Success Message is Displayed=" + isElementPresent("SLSSuccess_xpath").getText());

					} catch (Exception e) {
						logger.info("Error Message is not displayed");
						System.out.println(e);
					}

					// --Print button
					wait.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//*[@id=\"scrollboxIframe\"]//button[@id=\"btnPrint\"]")));
					String TrackingNo = isElementPresent("SLSTrackNo_xpath").getText();
					logger.info("Tracking No==" + TrackingNo);

					// --Get the TrackingNo
					String inLine = TrackingNo;
					String[] lineSplits = inLine.split(":");
					TrackNo = lineSplits[1];
					logger.info("Tracking No==" + TrackNo);
					setResultData("Result", 19, 2, TrackNo); // wait time // wait time

					/*
					 * // --Click on Print Button WebElement PrintBTN =
					 * isElementPresent("SLSPrintBTN_id");
					 * wait.until(ExpectedConditions.elementToBeClickable(PrintBTN));
					 * PrintBTN.click(); logger.info("Clicked on Print button");
					 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")
					 * )); wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(
					 * "//*[@ng-form=\"SLForm\"]")));
					 * 
					 * // Handle Print window String WindowHandlebefore = Driver.getWindowHandle();
					 * for (String windHandle : Driver.getWindowHandles()) {
					 * Driver.switchTo().window(windHandle);
					 * logger.info("Switched to Print window"); Thread.sleep(5000);
					 * getScreenshot(Driver, "PrintShipLabelService"); } Driver.close();
					 * logger.info("Closed Print window");
					 * 
					 * Driver.switchTo().window(WindowHandlebefore);
					 * logger.info("Switched to main window");
					 */

				}

				try {
					// --Close
					WebElement SLClose = isElementPresent("SLSCloseBtn_id");
					js.executeScript("arguments[0].click();", SLClose);
					logger.info("Clicked on Close button of ShipLabel");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					Thread.sleep(2000);
				} catch (Exception CLoseee) {
					wait.until(
							ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"SLForm\"]")));

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
			setResultData("Result", 19, 4, "PASS");

		} catch (Exception ewait) {
			logger.info(ewait);
			getScreenshot(Driver, "ShipLabelError");
			String Error1 = ewait.getMessage();
			setResultData("Result", 19, 4, "FAIL");
			setResultData("Result", 19, 5, Error1); // wait time // wait time
			// --Close Ship Label Service pop up

			/*
			 * logger.info("===ShipLabel Test End===");
			 * msg.append("===ShipLabel Test End===" + "\n\n");
			 */

		}
		logger.info("ShipLabel Test=PASS");
		msg.append("ShipLabel Test=PASS" + "\n");
		return TrackNo;
	}

	public void stage3Pdeliver(int i) throws Exception {
		// JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait;
		try {
			wait = new WebDriverWait(Driver, 40);// wait time

		} catch (Exception ewait) {
			wait = new WebDriverWait(Driver, 120);// wait time

		}
		Actions act = new Actions(Driver);

		// Search the Job
		OrderCreation OC = new OrderCreation();
		OC.searchJob(i);

		String STGName = OC.getStageName();
		String ServiceName = OC.getServiceID();

		if (STGName.equalsIgnoreCase("3P Deliver")
				|| STGName.equalsIgnoreCase("Deliver") && ServiceName.equalsIgnoreCase("H3P")) {
			// --Get the timeZone
			WebElement TimeZone = isElementPresent("EOCSTTime_id");
			act.moveToElement(TimeZone).build().perform();
			wait.until(ExpectedConditions.visibilityOf(TimeZone));
			String tzone = TimeZone.getText();
			String rectime = getTimeAsTZone(tzone);

			// --Move to DeliveryDate and Time
			WebElement DelTime = isElementPresent("EOActDelTime_id");
			act.moveToElement(DelTime).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(DelTime));
			DelTime.sendKeys(rectime);
			logger.info("Entered Delivery Time");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			String DELDateValue = getDateAsTZone(tzone);

			// --Move to DeliveryDate and Time
			WebElement DelDate = isElementPresent("EOActDelDate_id");
			act.moveToElement(DelDate).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(DelDate));
			DelDate.sendKeys(DELDateValue);
			logger.info("Entered Delivery Date");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Move to DeliveryDate and Time
			WebElement Signature = isElementPresent("EOSignature_id");
			act.moveToElement(Signature).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(Signature));
			Signature.sendKeys("Ravina");
			logger.info("Entered Signature");
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
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//*[@id=\"idValidationforMain\"]//ul[@id=\"errorid\"]")));
				String Validmsg = isElementPresent("OCValOnePack_xpath").getText();
				logger.info("Validation message is displayed=" + Validmsg);
				if (Validmsg.contains("Delivery time cannot be less than Drop Time.")) {
					// --Get the timeZone
					TimeZone = isElementPresent("EOH3pTimeZone_id");
					act.moveToElement(TimeZone).build().perform();
					wait.until(ExpectedConditions.visibilityOf(TimeZone));
					tzone = TimeZone.getText();
					logger.info("TimeZone=" + tzone);
					rectime = getTimeAsTZone(tzone);
					logger.info("Time=" + rectime);

					// --Move to DeliveryDate and Time
					DelTime = isElementPresent("EOActDelTime_id");
					act.moveToElement(DelTime).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(DelTime));
					DelTime.clear();
					DelTime.sendKeys(rectime);
					logger.info("Entered Delivery Time");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					DELDateValue = getDateAsTZone(tzone);
					logger.info("Date=" + DELDateValue);

					// --Move to DeliveryDate and Time
					DelDate = isElementPresent("EOActDelDate_id");
					act.moveToElement(DelDate).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(DelDate));
					DelDate.clear();
					DelDate.sendKeys(DELDateValue);
					logger.info("Entered Delivery Date");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					// --Move to DeliveryDate and Time
					Signature = isElementPresent("EOSignature_id");
					act.moveToElement(Signature).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(Signature));
					Signature.clear();
					Signature.sendKeys("Ravina");
					logger.info("Entered Signature");
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

							// --Click on Save&Exit button
							WebElement SaveExit = isElementPresent("EOSaveExit_id");
							act.moveToElement(SaveExit).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(SaveExit));
							act.moveToElement(SaveExit).click().perform();
							logger.info("Click on Save&Exit button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							Thread.sleep(2000);

						} else if (Validmsg.contains("Delivery time cannot be less than Drop Time.")) {
							// --Get the timeZone
							TimeZone = isElementPresent("EOH3pTimeZone_id");
							act.moveToElement(TimeZone).build().perform();
							wait.until(ExpectedConditions.visibilityOf(TimeZone));
							tzone = TimeZone.getText();
							rectime = getTimeAsTZone(tzone);

							// --Move to DeliveryDate and Time
							DelTime = isElementPresent("EOActDelTime_id");
							act.moveToElement(DelTime).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(DelTime));
							DelTime.clear();
							DelTime.sendKeys(rectime);
							logger.info("Entered Delivery Time");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							DELDateValue = getDateAsTZone(tzone);

							// --Move to DeliveryDate and Time
							DelDate = isElementPresent("EOActDelDate_id");
							act.moveToElement(DelDate).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(DelDate));
							DelDate.sendKeys(DELDateValue);
							logger.info("Entered Delivery Date");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							// --Move to DeliveryDate and Time
							Signature = isElementPresent("EOSignature_id");
							act.moveToElement(Signature).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Signature));
							Signature.clear();
							Signature.sendKeys("Ravina");
							logger.info("Entered Signature");
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

				}

			} catch (

			Exception ee) {
				logger.info("Validation message is not displayed for Drop Time");

			}
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
					try {

						wait.until(ExpectedConditions.visibilityOfElementLocated(
								By.xpath("//*[@id=\"idValidationforMain\"]//ul[@id=\"errorid\"]")));
						String Validmsg = isElementPresent("OCValOnePack_xpath").getText();
						logger.info("Validation message is displayed=" + Validmsg);
						if (Validmsg.contains("Delivery time cannot be less than Drop Time.")) {
							// --Get the timeZone
							TimeZone = isElementPresent("EOH3pTimeZone_id");
							act.moveToElement(TimeZone).build().perform();
							wait.until(ExpectedConditions.visibilityOf(TimeZone));
							tzone = TimeZone.getText();
							logger.info("TimeZone=" + tzone);
							rectime = getTimeAsTZone(tzone);
							logger.info("Time=" + rectime);

							// --Move to DeliveryDate and Time
							DelTime = isElementPresent("EOActDelTime_id");
							act.moveToElement(DelTime).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(DelTime));
							DelTime.clear();
							DelTime.sendKeys(rectime);
							logger.info("Entered Delivery Time");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							DELDateValue = getDateAsTZone(tzone);
							logger.info("Date=" + DELDateValue);

							// --Move to DeliveryDate and Time
							DelDate = isElementPresent("EOActDelDate_id");
							act.moveToElement(DelDate).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(DelDate));
							DelDate.clear();
							DelDate.sendKeys(DELDateValue);
							logger.info("Entered Delivery Date");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							// --Move to DeliveryDate and Time
							Signature = isElementPresent("EOSignature_id");
							act.moveToElement(Signature).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Signature));
							Signature.clear();
							Signature.sendKeys("Ravina");
							logger.info("Entered Signature");
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

					} catch (Exception e) {
						// --Click on Save&Exit button
						WebElement SaveExit = isElementPresent("EOSaveExit_id");
						act.moveToElement(SaveExit).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(SaveExit));
						act.moveToElement(SaveExit).click().perform();
						logger.info("Click on Save&Exit button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						Thread.sleep(2000);
					}

				}

			} catch (Exception eRequiredMsg) {

			}

		} else if (STGName.equalsIgnoreCase("Deliver")
				&& (ServiceName.equalsIgnoreCase("D3P") || ServiceName.equalsIgnoreCase("P3P"))) {

			// --Get the timeZone
			WebElement TimeZone = isElementPresent("D3PDelTimeZone_id");
			act.moveToElement(TimeZone).build().perform();
			wait.until(ExpectedConditions.visibilityOf(TimeZone));
			String tzone = TimeZone.getText();
			String rectime = getTimeAsTZone(tzone);

			// --Move to DeliveryDate and Time
			WebElement DelTime = isElementPresent("D3PDelTime_id");
			act.moveToElement(DelTime).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(DelTime));
			DelTime.sendKeys(rectime);
			logger.info("Entered Delivery Time");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			String DELDateValue = getDateAsTZone(tzone);

			// --Move to DeliveryDate and Time
			WebElement DelDate = isElementPresent("D3PDelDate_id");
			act.moveToElement(DelDate).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(DelDate));
			DelDate.sendKeys(DELDateValue);
			logger.info("Entered Delivery Date");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Move to DeliveryDate and Time
			WebElement Signature = isElementPresent("D3PDelSign_id");
			act.moveToElement(Signature).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(Signature));
			Signature.sendKeys("Ravina");
			logger.info("Entered Signature");
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
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//*[@id=\"idValidationforMain\"]//ul[@id=\"errorid\"]")));
				String Validmsg = isElementPresent("OCValOnePack_xpath").getText();
				logger.info("Validation message is displayed=" + Validmsg);
				if (Validmsg.contains("Actual Datetime cannot be greater than Current Datetime.")) {
					// --Get the timeZone
					TimeZone = isElementPresent("D3pDelivTimeZone_xpath");
					act.moveToElement(TimeZone).build().perform();
					wait.until(ExpectedConditions.visibilityOf(TimeZone));
					tzone = TimeZone.getText();
					rectime = getTimeAsTZone(tzone);

					// --Move to DeliveryDate and Time
					DelTime = isElementPresent("D3PDelTime_id");
					act.moveToElement(DelTime).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(DelTime));
					DelTime.clear();
					DelTime.sendKeys(rectime);
					logger.info("Entered Delivery Time");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					DELDateValue = getDateAsTZone(tzone);

					// --Move to DeliveryDate and Time
					DelDate = isElementPresent("D3PDelDate_id");
					act.moveToElement(DelDate).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(DelDate));
					DelDate.clear();
					DelDate.sendKeys(DELDateValue);
					logger.info("Entered Delivery Date");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					// --Move to DeliveryDate and Time
					Signature = isElementPresent("D3PDelSign_id");
					act.moveToElement(Signature).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(Signature));
					Signature.clear();
					Signature.sendKeys("Ravina");
					logger.info("Entered Signature");
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

							// --Click on Save&Exit button
							WebElement SaveExit = isElementPresent("EOSaveExit_id");
							act.moveToElement(SaveExit).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(SaveExit));
							act.moveToElement(SaveExit).click().perform();
							logger.info("Click on Save&Exit button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							Thread.sleep(2000);

						} else if (Validmsg.contains("Delivery time cannot be less than Drop Time.")) {
							// --Move to DeliveryDate and Time
							DelTime = isElementPresent("EOActDelTime_id");
							act.moveToElement(DelTime).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(DelTime));
							DelTime.sendKeys(rectime);
							logger.info("Entered Delivery Time");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							DELDateValue = getDateAsTZone(tzone);

							// --Move to DeliveryDate and Time
							DelDate = isElementPresent("D3PDelDate_id");
							act.moveToElement(DelDate).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(DelDate));
							DelDate.sendKeys(DELDateValue);
							logger.info("Entered Delivery Date");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							// --Move to DeliveryDate and Time
							Signature = isElementPresent("D3PDelSign_id");
							act.moveToElement(Signature).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Signature));
							Signature.sendKeys("Ravina");
							logger.info("Entered Signature");
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

				}
			} catch (

			Exception ee) {
				logger.info("Validation message is not displayed for Drop Time");

			}
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

					// -- COMMODITY_DROPDOWN
					WebElement CommDrop = isElementPresent("EOCommDrop_id");
					act.moveToElement(CommDrop).build().perform();
					Thread.sleep(2000);
					Select comodity_drpdown = new Select(CommDrop);
					comodity_drpdown.selectByIndex(1);
					logger.info("comodity dropdown is selected");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					Thread.sleep(2000);

					WebElement Commo = isElementPresent("EOCommodity_id");
					act.moveToElement(Commo).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(Commo));
					Commo.sendKeys("Packet");
					logger.info("Entered Commodity");
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
						wait.until(ExpectedConditions.visibilityOfElementLocated(
								By.xpath("//*[@id=\"idValidationforMain\"]//ul[@id=\"errorid\"]")));
						String Validmsg = isElementPresent("OCValOnePack_xpath").getText();
						logger.info("Validation message is displayed=" + Validmsg);
						if (Validmsg.contains("Actual Datetime cannot be greater than Current Datetime.")) {
							// --Get the timeZone
							TimeZone = isElementPresent("D3pDelivTimeZone_xpath");
							act.moveToElement(TimeZone).build().perform();
							wait.until(ExpectedConditions.visibilityOf(TimeZone));
							tzone = TimeZone.getText();
							rectime = getTimeAsTZone(tzone);

							// --Move to DeliveryDate and Time
							DelTime = isElementPresent("D3PDelTime_id");
							act.moveToElement(DelTime).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(DelTime));
							DelTime.clear();
							DelTime.sendKeys(rectime);
							logger.info("Entered Delivery Time");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							DELDateValue = getDateAsTZone(tzone);

							// --Move to DeliveryDate and Time
							DelDate = isElementPresent("D3PDelDate_id");
							act.moveToElement(DelDate).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(DelDate));
							DelDate.clear();
							DelDate.sendKeys(DELDateValue);
							logger.info("Entered Delivery Date");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							// --Move to DeliveryDate and Time
							Signature = isElementPresent("D3PDelSign_id");
							act.moveToElement(Signature).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Signature));
							Signature.clear();
							Signature.sendKeys("Ravina");
							logger.info("Entered Signature");
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

								if (ValMsg.equalsIgnoreCase("Pickup Phone# is Required.")) {
									// --Enter Pickup Phone No
									PUPhoneNo = isElementPresent("EOPickupPhone_id");
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

									// --Click on Save&Exit button
									WebElement SaveExit = isElementPresent("EOSaveExit_id");
									act.moveToElement(SaveExit).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(SaveExit));
									act.moveToElement(SaveExit).click().perform();
									logger.info("Click on Save&Exit button");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
									Thread.sleep(2000);

								} else if (Validmsg.contains("Delivery time cannot be less than Drop Time.")) {
									// --Move to DeliveryDate and Time
									DelTime = isElementPresent("EOActDelTime_id");
									act.moveToElement(DelTime).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(DelTime));
									DelTime.sendKeys(rectime);
									logger.info("Entered Delivery Time");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									DELDateValue = getDateAsTZone(tzone);

									// --Move to DeliveryDate and Time
									DelDate = isElementPresent("D3PDelDate_id");
									act.moveToElement(DelDate).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(DelDate));
									DelDate.sendKeys(DELDateValue);
									logger.info("Entered Delivery Date");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									// --Move to DeliveryDate and Time
									Signature = isElementPresent("D3PDelSign_id");
									act.moveToElement(Signature).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Signature));
									Signature.sendKeys("Ravina");
									logger.info("Entered Signature");
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

						}
					} catch (

					Exception ee) {
						logger.info("Validation message is not displayed for Drop Time");

					}

				}

			} catch (Exception eRequiredMsg) {

			}

		}

		// --Click on Save&Exit button
		WebElement SaveExit = isElementPresent("EOSaveExit_xpath");
		act.moveToElement(SaveExit).build().perform();
		wait.until(ExpectedConditions.elementToBeClickable(SaveExit));
		act.moveToElement(SaveExit).click().perform();
		logger.info("Click on Save&Exit button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		Thread.sleep(2000);

	}

	public void EditDriver() throws IOException {

		WebDriverWait wait;
		try {
			wait = new WebDriverWait(Driver, 60);// wait time

		} catch (Exception ewait) {
			wait = new WebDriverWait(Driver, 120);// wait time

		}
		WebDriverWait wait2 = new WebDriverWait(Driver, 10);// wait time;
		Actions act = new Actions(Driver);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		String Env = storage.getProperty("Env");
		try {
			logger.info("==Edit Driver Test Start==");
			// msg.append("==RTE Edit Driver Test Start==" + "\n");

			Thread.sleep(2000);

			WebElement DriverE = isElementPresent("PDEdit_id");
			wait.until(ExpectedConditions.visibilityOf(DriverE));
			wait.until(ExpectedConditions.elementToBeClickable(DriverE));
			js.executeScript("arguments[0].click();", DriverE);
			logger.info("Clicked on Edit button of Driver");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
//			wait2.until(ExpectedConditions
//					.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"modal-dialog modal-md\"]")));
			getScreenshot(Driver, "DriverSearch");
			Thread.sleep(1500);

			if (Env.equalsIgnoreCase("Test") || Env.equalsIgnoreCase("STG")) {

				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				// --Search Driver by AgentID
				wait.until(ExpectedConditions.elementToBeClickable(By.id("txtCourierId")));
				isElementPresent("PDCourierID_id").clear();
				isElementPresent("PDCourierID_id").sendKeys("34769");
				logger.info("Enter CourierID");

				// --Search Driver by AgentKey
				wait.until(ExpectedConditions.elementToBeClickable(By.id("txtAgentKey")));
				isElementPresent("PDAgentkey_id").clear();
				isElementPresent("PDAgentkey_id").sendKeys("AUTOMATION");
				logger.info("Enter Agent Key");

				// --Click on Search
				WebElement SearchBTN = isElementPresent("PDSearch_id");
				js.executeScript("arguments[0].click();", SearchBTN);
				logger.info("Clicked on search button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --Get the List of Agent
				List<WebElement> Agents = Driver
						.findElements(By.xpath("//*[@id=\"scrollboxEditAgent\"]//a[contains(@id,'AgentId')]"));

				int TotalAgent = Agents.size();
				logger.info("Total Agent==" + TotalAgent);
				String AgentValue = null;
				for (int agent = 0; agent < TotalAgent; agent++) {
					WebElement AgentID = Agents.get(agent);
					act.moveToElement(AgentID).build().perform();
					String AgentIDV = Agents.get(agent).getText();
					act.moveToElement(AgentID).build().perform();
					AgentValue = AgentIDV;
					System.out.println("AgentID==" + AgentIDV);

					/*
					 * if(agent>7) { int lastRecord=Agents.size()-1; WebElement LastAgent=
					 * Agents.get(lastRecord);
					 * 
					 * }
					 */

					if (AgentIDV.equalsIgnoreCase("34769")) {
						logger.info("Searched Agent is exist");

						// --Select the Agent
						act.moveToElement(AgentID).build().perform();
						act.moveToElement(AgentID).click().perform();
						logger.info("Select the AgentID");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hlkedit")));
						AgentValue = AgentIDV;
						break;

					} else {
						logger.info("There is no Agent with search parameter");

					}

				}

				// --Compare the Searched Agent and selected Agent
				String AgeValue = isElementPresent("PDAgentValue_xpath").getText();
				logger.info("Selected agent is==" + AgeValue);

				if (AgeValue.equalsIgnoreCase(AgentValue)) {
					logger.info("Selected Agent is displayed in Driver section");

				} else {
					logger.info("Selected Agent is not displayed in Driver section");

				}

			} else if (Env.equalsIgnoreCase("PROD")) {

				WebElement CourierTextBox = isElementPresent("CourierIDTextBox_xpath");

				CourierTextBox.sendKeys("30738");
				logger.info("Entered 34769 Courier Number");

				// Search Button
				WebElement Search = isElementPresent("SearchCourier_xpath");
				wait.until(ExpectedConditions.elementToBeClickable(Search));
				js.executeScript("arguments[0].click();", Search);
				logger.info("Clicked on Search Button");
				Thread.sleep(1500);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(1500);
				// Select Courier id
				WebElement agent_id = Driver
						.findElement(By.xpath("//span[@ng-bind='Agent.AgentId' and contains(text(),'30738')]"));
				js.executeScript("arguments[0].scrollIntoView(true);", agent_id);
				Thread.sleep(1500);
				wait.until(ExpectedConditions.elementToBeClickable(agent_id));
				String AgeValue = agent_id.getText();
				logger.info("Selected agent is==" + AgeValue);
				js.executeScript("arguments[0].scrollIntoView(true);", agent_id);
				Thread.sleep(1000);

				String AgentIDV = agent_id.getText();

				if (AgentIDV.equalsIgnoreCase("30738")) {
					logger.info("Searched Agent is exist");

				} else {
					logger.info("There is no Agent with search parameter");

				}
				js.executeScript("arguments[0].click();", agent_id);
				logger.info("clicked on agent ID for agent selection");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(1500);
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
		WebDriverWait wait;
		try {
			wait = new WebDriverWait(Driver, 60);// wait time

		} catch (Exception ewait) {
			wait = new WebDriverWait(Driver, 60);// wait time

		}
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);
		WebDriverWait wait2 = new WebDriverWait(Driver, 7);// wait time;
		WebDriverWait wait3 = new WebDriverWait(Driver, 60);// wait time

		// --NetAgent Login

		try {
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("welcomecontent")));
			logger.info("ALready loggedin");
		} catch (Exception e) {
			Login();

		}

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
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
		wait3.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));
		logger.info("Click on Task Log");

		String PUID = getData("OrderCreation", i, 32);
		logger.info("PickUpID is==" + PUID);
		msg.append("PickUpID==" + PUID + "\n");

		try {
			wait2.until(ExpectedConditions
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
			// wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

		}

		NATaskSearch(PUID);

	}

	public void NATaskSearch(String PUID) throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(Driver, 60);// wait time
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);
		WebDriverWait wait2 = new WebDriverWait(Driver, 7);// wait time;

		try {
			WebElement SearchBox = isElementPresent("NTaskSearchbox_id");
			wait.until(ExpectedConditions.visibilityOf(SearchBox));
			wait2.until(ExpectedConditions.elementToBeClickable(SearchBox));
			SearchBox.clear();
			logger.info("Clear search input");
			SearchBox.sendKeys(PUID);
			logger.info("Enter PickUpID in Search input");
			WebElement OPSearch = isElementPresent("NTSearchBtn_id");
			act.moveToElement(OPSearch).build().perform();
			js.executeScript("arguments[0].click();", OPSearch);
			logger.info("Click on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

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
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));
			logger.info("Click on Task Log");

			try {
				wait2.until(ExpectedConditions
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
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

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
			wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

		} catch (Exception e) {
			WebDriverWait wait1 = new WebDriverWait(Driver, 180);
			wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

		}

		try {
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
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
				// InvSearch.click();
				js.executeScript("arguments[0].click();", InvSearch);
				logger.info("Click on Search button");
				try {
					wait.until(
							ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

				} catch (Exception e) {
					// WebDriverWait wait1 = new WebDriverWait(Driver, 180);
					wait2.until(
							ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

				}

			}
		} catch (Exception Tab) {
			try {
				wait2.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
				WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
				if (NoData.isDisplayed()) {
					logger.info("Job is not available in NetAgent");

				}
			} catch (Exception e1) {
				logger.info("Job is available in NetAgent");
				Thread.sleep(7000);
			}
		}

		getScreenshot(Driver, "NAJobAvailable_" + PUID);
//		try {
//			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
//			WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
//			if (NoData.isDisplayed()) {
//				logger.info("Job is not available in NetAgent");
//
//			}
//		} catch (Exception e1) {
//			logger.info("Job is available in NetAgent");
//		}
	}

	public String getNAStageName() {
		WebDriverWait wait;
		try {
			wait = new WebDriverWait(Driver, 40);// wait time

		} catch (Exception ewait) {
			wait = new WebDriverWait(Driver, 60);// wait time

		}
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

	public void addAirBill(int i)
			throws IOException, InterruptedException, EncryptedDocumentException, InvalidFormatException {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait;
		try {
			wait = new WebDriverWait(Driver, 40);// wait time

		} catch (Exception ewait) {
			wait = new WebDriverWait(Driver, 60);// wait time

		}
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
			wait.until(ExpectedConditions.elementToBeClickable(saveairbill));
			jse.executeScript("arguments[0].click();", saveairbill);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			logger.info("Click on Save Bill button");

			try {
				String DescVal = isElementPresent("Error_id").getText();
				System.out.println(DescVal);
				logger.info(DescVal);
				if (DescVal.contains("Description Required.")) {

					// --Enter Description
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtEditDescription_0")));
					isElementPresent("EOAirBDesc_id").clear();
					isElementPresent("EOAirBDesc_id").sendKeys("Test Description");
					logger.info("Enter Description");

					saveairbill = isElementPresent("TLDAOSaveBill_id");
					wait.until(ExpectedConditions.elementToBeClickable(saveairbill));
					jse.executeScript("arguments[0].click();", saveairbill);
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					logger.info("Click on Save Bill button");

					try {
						DescVal = isElementPresent("Error_id").getText();
						System.out.println(DescVal);
						logger.info(DescVal);
						if (DescVal.contains("Account Required.")) {

							// --Enter Description
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cmbCarrier")));
							Select Account = new Select(isElementPresent("EOAccount_id"));
							Account.selectByIndex(1);
							Thread.sleep(2000);
							logger.info("Select Account");

							saveairbill = isElementPresent("TLDAOSaveBill_id");
							wait.until(ExpectedConditions.elementToBeClickable(saveairbill));
							jse.executeScript("arguments[0].click();", saveairbill);
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							logger.info("Click on Save Bill button");

							try {
								DescVal = isElementPresent("Error_id").getText();
								System.out.println(DescVal);
								logger.info(DescVal);
								if (DescVal.contains("Service Level Required.")) {

									// --Enter Description
									wait.until(ExpectedConditions.visibilityOfElementLocated(
											By.xpath("//*[@id=\"cmbCarrier\"][@ng-model=\"AWB.CodeValue\"]")));
									Select ServLvlSelection = new Select(isElementPresent("EOServcLvlSelection_xpath"));
									ServLvlSelection.selectByIndex(1);
									Thread.sleep(2000);
									logger.info("Select Service Level");

									saveairbill = isElementPresent("TLDAOSaveBill_id");
									wait.until(ExpectedConditions.elementToBeClickable(saveairbill));
									jse.executeScript("arguments[0].click();", saveairbill);
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
									logger.info("Click on Save Bill button");

								}
							} catch (Exception eDesc) {
								logger.info("Validation for Service Level is not displayed");
							}

						}
					} catch (Exception eDesc) {
						logger.info("Validation for Account is not displayed");
					}
				}

			} catch (Exception eDesc) {
				logger.info("Validation for Air Bill Description is not displayed");
			}
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

	public void naTab() throws InterruptedException {
		ArrayList<String> tabs = new ArrayList<String>(Driver.getWindowHandles());
		Driver.switchTo().window(tabs.get(0));
		logger.info("==Moved to NetAgent==");
		msg.append("=Moved to NetAgent=" + "\n");
		Thread.sleep(1000);
	}

	public void connectTab() {
		ArrayList<String> tabs = new ArrayList<String>(Driver.getWindowHandles());
		Driver.switchTo().window(tabs.get(1));
		logger.info("==Moved to Connect==");
		msg.append("==Moved to Connect==" + "\n");

	}

	// create 4 Loc and merge it to craete new RTE
	public void create_Loc_merge_loc_create_rte()
			throws InterruptedException, EncryptedDocumentException, InvalidFormatException, IOException, Exception {
		msg.append("==== LOC creation for RTE Creation : Start ==== " + "\n");
		int i;
		String pickup_id;
		try {

			logger.info("==== LOC creation for RTE Creation : Start ==== " + "\n");

			WebDriverWait wait = new WebDriverWait(Driver, 60);
			Actions act = new Actions(Driver);
			JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click

			BaseInit OC = new BaseInit();

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Order Creation // ArrayList<?> results = new ArrayList<Object>();
			List<String> list_pickup = new ArrayList<String>();

			for (i = 23; i < 24; i++) {
				// for (i = 23; i <= 24; i++) {
				Driver.navigate().refresh();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(1500);

				pickup_id = orderCreation(i);
				// OC.orderCreation(i);

				list_pickup.add(pickup_id);

				logger.info("Pickupid " + i + " is :" + pickup_id);

			}
			// -- set i to default 23 pointer
			i = 23;
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			int TotalPickup = list_pickup.size();
			logger.info("Total Pickup points is/are==" + TotalPickup);

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			Thread.sleep(1500);

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
			// wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
			logger.info("Click on Tasklog");
			Thread.sleep(1500);

			for (int pu_id = 0; pu_id < TotalPickup; pu_id++) {

				String pickup = list_pickup.get(pu_id);
				Thread.sleep(1500);
				WebElement loc_pickup_checkbox = Driver
						.findElement(By.xpath("//label[contains(text(),'" + pickup + "')]//preceding::span[1]"));
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				act.moveToElement(loc_pickup_checkbox).build().perform();
				jse.executeScript("arguments[0].scrollIntoView();", loc_pickup_checkbox);
				Thread.sleep(1500);
				act.click(loc_pickup_checkbox).build().perform();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(3000);
				logger.info("Checkbox of appropriate pickup id of LOC is selected");

			}
			try {
				RTE_merge_prod(i);
			}

			catch (Exception e) {
				// TODO: handle exception
				logger.info("Issue in RTE creation using loc mege : " + e);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("Issue in RTE creation using loc mege : " + e);
		}

	}

	public void RTE_merge_prod(int i) throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 60);
		WebDriverWait wait1 = new WebDriverWait(Driver, 10);
		Actions act = new Actions(Driver);

		// -- Click on Create RTE on tasklog

		WebElement create_rte = isElementPresent("TLCreateRTE_id");
		create_rte.click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		Thread.sleep(1500);
		logger.info("CLicked on Create RTE  buttoon from tasklog");

		// --Create RTE form
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"CreateRTEForm\"]")));
		getScreenshot(Driver, "TLCreateRTEForm");

		// --RW Name
		isElementPresent("TLRWName_id").clear();
		isElementPresent("TLRWName_id").sendKeys("Test sel RTE with LOC merge");
		logger.info("Entered Route Work Name");

		// --Declared Value
		isElementPresent("TLRWDecValue_id").clear();
		isElementPresent("TLRWDecValue_id").sendKeys("10");
		logger.info("Entered Declare Value");

		// --Reference 2
		isElementPresent("TLRWRef2_id").clear();
		isElementPresent("TLRWRef2_id").sendKeys("AutoRef2");
		logger.info("Entered Reference 2");

		// --Reference 4
		isElementPresent("TLRWRef4_id").clear();
		isElementPresent("TLRWRef4_id").sendKeys("AutoRef4");
		logger.info("Entered Reference 4");

		// --RW Manifest
		isElementPresent("TLRWManifest_id").clear();
		isElementPresent("TLRWManifest_id").sendKeys("Ravina.prajapati@samyak.com");
		logger.info("Entered RW Manifest");

		// --Enter Ready Date
		WebElement ReadyDate = isElementPresent("TLRWReadyDate_id");
		ReadyDate.clear();
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		logger.info(dateFormat.format(date));
		ReadyDate.sendKeys(dateFormat.format(date));
		ReadyDate.sendKeys(Keys.TAB);
		logger.info("Entered Ready Date");

		// --Enter Ready Time
		WebElement ReadyTime = isElementPresent("TLRWRTime_id");
		ReadyTime.clear();
		date = new Date();
		dateFormat = new SimpleDateFormat("HH:mm");
		logger.info(dateFormat.format(date));
		ReadyTime.sendKeys(dateFormat.format(date));
		logger.info("Entered Ready Time");

		// --Enter End Date
		WebElement EndDate = isElementPresent("TLRWSEndDate_id");
		EndDate.clear();
		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 2);
		String newDate = dateFormat.format(cal.getTime());
		logger.info(newDate);
		EndDate.sendKeys(newDate);
		EndDate.sendKeys(Keys.TAB);
		logger.info("Entered Start Date");

		// --Enter End Time
		WebElement EndTime = isElementPresent("TLRWSEndTime_id");
		EndTime.clear();
		date = new Date();
		dateFormat = new SimpleDateFormat("HH:mm");
		logger.info(dateFormat.format(date));
		EndTime.sendKeys(dateFormat.format(date));
		logger.info("Entered End Time");

		// Contact Name
		isElementPresent("TLRWUCName_id").clear();
		isElementPresent("TLRWUCName_id").sendKeys("Ravina Prajapati");
		logger.info("Entered Contact Name");

		// Company Name
		isElementPresent("TLRWUComName_id").clear();
		isElementPresent("TLRWUComName_id").sendKeys("Automation return Pvt. Ltd.");
		logger.info("Entered Company Name");

		// AddressLine 1
		isElementPresent("TLRWUnAdd_id").clear();
		isElementPresent("TLRWUnAdd_id").sendKeys("1 Cooper Plaza");
		logger.info("Entered AddressLine 1");

		// ZipCode
		isElementPresent("TLRWUnZpCde_id").clear();
		isElementPresent("TLRWUnZpCde_id").sendKeys("77054");
		isElementPresent("TLRWUnZpCde_id").sendKeys(Keys.TAB);
		logger.info("Entered ZipCode");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// Phone Number
		isElementPresent("TLRWUnPhone_id").clear();
		isElementPresent("TLRWUnPhone_id").sendKeys("9856320147");
		logger.info("Entered Phone Number");

		// --clicked on Create Route button
		WebElement CreateRoute = isElementPresent("TLRWCreateRoute_id");
		wait1.until(ExpectedConditions.elementToBeClickable(CreateRoute));
		act.moveToElement(CreateRoute).build().perform();
		jse.executeScript("arguments[0].click();", CreateRoute);

		logger.info("Clicked on Create Route button of RTE form");

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		try {
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("success")));

			String SUccMsg = isElementPresent("TLRWSucc_id").getText();
			logger.info("Message is displayed==" + SUccMsg);
			logger.info("RTE Job is created successfully.");
			msg.append("RTE Job is created successfully." + "/n");
			setResultData("Result", 26, 4, "PASS");

			// --Get the RouteWorkID
			String inLine = SUccMsg;
			String[] lineSplits = inLine.split(" ");
			for (String Detail : lineSplits) {
				if (Detail.contains("RT")) {
					final String RWID = Detail.split("\\.")[0];
					RWID.trim();
					System.out.println("RoutWorkID is==" + RWID);
					logger.info("RoutWorkID is==" + RWID);
					msg.append("RoutWorkID is==" + RWID + "/n");
					setData("RTE", 2, 0, RWID);

					logger.info("Stored RoutWorkID in excel");
					setResultData("Result", 26, 2, RWID);

				}

			}

		} catch (Exception e) {
			logger.info(e);
			logger.info("RTE Job is not created successfully,Something went wrong==FAIL");
			getScreenshot(Driver, "RTEjobNotCreated");
			String Error = e.getMessage();

			setResultData("Result", 23, 4, "FAIL");

		}

		// --clicked on cancel button
		WebElement CancelRTE = isElementPresent("TLRWCancelRW_id");
		wait1.until(ExpectedConditions.elementToBeClickable(CancelRTE));
		act.moveToElement(CancelRTE).click().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		Thread.sleep(1500);
		logger.info("Clicked on Cancel button of RTE form");

		// -- get tracking no
		get_disabled_RTETrackingNo();

		// -- Search job /Pickup/BU id from All order screen
		searchRTEJob();

		// -- open job
		i = 23;
		openJob_tasklog_connect(i);

	}

	public void get_disabled_RTETrackingNo() throws EncryptedDocumentException, InvalidFormatException, IOException {
		WebDriverWait wait = new WebDriverWait(Driver, 60);
		WebDriverWait wait2 = new WebDriverWait(Driver, 7);
		Actions act = new Actions(Driver);
		String Env = storage.getProperty("Env");
		// --Go to RouteList
		// --Go to Tools tab
		try {

			Driver.navigate().refresh();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			Thread.sleep(1000);
			logger.info("page is refreshed");

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

//					int TotalRow = getTotalRow("RTE");
//					logger.info("Total Rows==" + TotalRow);

			// --Enter RoutWorkID
			wait.until(ExpectedConditions.elementToBeClickable(By.id("txtRouteWorkId")));
			String RoutWID = getData("RTE", 2, 0);
			msg.append("RouteWorkID is==" + RoutWID + "\n");
			isElementPresent("RLRWIDInput_id").clear();
			isElementPresent("RLRWIDInput_id").sendKeys(RoutWID);
			logger.info("Entered RoutWorkID");

			// -- Status filter -- disable

			Select status_filter = new Select(isElementPresent("connect_route_list_status_id"));
			status_filter.selectByValue("string:Disabled");
			logger.info("Rooute List status select as Disabled");

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

					if (Env.equalsIgnoreCase("Test") || Env.equalsIgnoreCase("STG")) {

						setData("RTE", 1, 1, RWTrackingNo);

						setResultData("Result", 23, 2, RWTrackingNo);
						setResultData("Result", 23, 4, "PASS");

					} else if (Env.equalsIgnoreCase("PROD")) {

						setData("RTE", 2, 1, RWTrackingNo);

						setResultData("Result", 27, 2, RWTrackingNo);
						setResultData("Result", 27, 4, "PASS");

					}

				}
			}

		} catch (Exception esearchjob) {
			logger.error(esearchjob);
			getScreenshot(Driver, "getRTETrackingIDError");
			String Error = esearchjob.getMessage();

			if (Env.equalsIgnoreCase("Test") || Env.equalsIgnoreCase("STG")) {

				setResultData("Result", 23, 4, "FAIL");
				setResultData("Result", 23, 5, Error);

			} else if (Env.equalsIgnoreCase("PROD")) {

				setResultData("Result", 27, 4, "FAIL");
				setResultData("Result", 27, 5, Error);

			}
		}
	}

	public String openJob_tasklog_connect(int i) throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 60);// wait time
		WebDriverWait wait2 = new WebDriverWait(Driver, 10);// wait time
		Actions act = new Actions(Driver);
		String PUID = null;

		try {

			Driver.navigate().refresh();

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			Thread.sleep(2000);
			logger.info("Page is refreshed");

			// --Go To Operations
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
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

			// Enter JobID#
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// Enter JobID#
			wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			PUID = getData("OrderCreation", i, 32);
			Thread.sleep(1000);
			// setData("Test_Case", 2, 4, PUID);

			isElementPresent("TLSearch_id").clear();
			isElementPresent("TLSearch_id").sendKeys(PUID);
			isElementPresent("TLSearch_id").sendKeys(Keys.TAB);
			logger.info("Enter Pickup ID : " + PUID + " in Tasklog serach box");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			WebElement Search = isElementPresent("TLSearchButton_id");
			wait.until(ExpectedConditions.elementToBeClickable(Search));
			jse.executeScript("arguments[0].click();", Search);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			getScreenshot(Driver, "H3PJob_After_TenderTo3P");

			List<WebElement> Jobs = Driver
					.findElements(By.xpath("//*[contains(@aria-label,'Pickup #,')]//label[@id=\"lblDateTime\"]"));
			int total_job_find = Jobs.size();
			logger.info("Total job visible on TAsk log for entered pickup id is : " + total_job_find);
			for (int job = 0; job < Jobs.size(); job++) {
				String PickupID = Jobs.get(job).getText();
				logger.info("Fetched job id is : " + PickupID);
				if (PickupID.startsWith("N") || PickupID.startsWith("R")) {
					String[] PickValue = PickupID.split("N");
					String PickID = PickValue[1];
					logger.info("Searched PickUpID==" + PickID);
					if (PickID.equalsIgnoreCase(PUID)) {
						Jobs.get(job).click();
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						break;
					}

				} else {
					String[] PickValue = PickupID.split("R");
					String PickID = PickValue[1];
					logger.info("Searched PickUpID==" + PickID);
					if (PickID.equalsIgnoreCase(PUID)) {
						Jobs.get(job).click();
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						break;
					}

				}

				logger.info("Same job is displayed with 2 status==PASS");
				// msg.append("Same job is displayed with 2 status==PASS" + "\n");

				// --Get StageName
			}

		} catch (Exception eTasklog) {
			// --Go To Operations
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_operations")));
			WebElement Operations = isElementPresent("Operations_id");
			act.moveToElement(Operations).click().perform();
			logger.info("Clicked on Operations");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Go to TaskLog
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_TaskLog")));
			isElementPresent("OpTaskLog_id").click();
			logger.info("Clicked on TaskLog");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// Enter JobID#
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			PUID = getData("OrderCreation", i, 32);
			// setData("Test_Case", 2, 4, PUID);
			logger.info("PickUpID fetch from sheet is =" + PUID + "\n");
			isElementPresent("TLSearch_id").clear();
			isElementPresent("TLSearch_id").sendKeys(PUID);
			logger.info("Pickup Enter in tasklog search is : " + PUID);
			getScreenshot(Driver, "TaskLog_pickup_entered");
			isElementPresent("TLSearch_id").sendKeys(Keys.TAB);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			WebElement Search = isElementPresent("TLSearchButton_id");
			wait.until(ExpectedConditions.elementToBeClickable(Search));
			jse.executeScript("arguments[0].click();", Search);
			logger.info("Click on Search button of tasklog");

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			try {
				getScreenshot(Driver, "tasklog_screen");

				List<WebElement> Jobs = Driver
						.findElements(By.xpath("//*[contains(@aria-label,'Pickup #,')]//label[@id='lblDateTime']"));

				int total_job_find = Jobs.size();
				logger.info("Total job visible on TAsk log for entered pickup id is : " + total_job_find);

				for (int job = total_job_find - 1; job >= 0; job--) {

					String PickupID = Jobs.get(job).getText();
					logger.info("Fetched job id is : " + PickupID);
					if (PickupID.startsWith("N") || PickupID.startsWith("R")) {
						String[] PickValue = PickupID.split("R");
						String PickID = PickValue[1];
						logger.info("Searched PickUpID==" + PickID);
						if (PickID.equalsIgnoreCase(PUID)) {
							Jobs.get(job).click();
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							break;
						}

					} else {
						String[] PickValue = PickupID.split("N");
						String PickID = PickValue[1];
						logger.info("Searched PickUpID==" + PickID);
						if (PickID.equalsIgnoreCase(PUID)) {
							Jobs.get(job).click();
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							break;
						}

					}

					logger.info("Same job is displayed with 2 status==PASS");
					// msg.append("Same job is displayed with 2 status==PASS" + "\n");

					// --Get StageName
				}
				// --Get StageName
			} catch (Exception e) {
				logger.info("Same job is not displayed with 2 status " + e);
				// msg.append("Same job is not displayed with 2 status" + "\n");

				// --Get StageName

			}

		}
		return PUID;

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

			String Env = storage.getProperty("Env");

			if (Env.equalsIgnoreCase("Test") || Env.equalsIgnoreCase("STG")) {

				String RouteTrackingNo = getData("RTE", 1, 1);

				logger.info("Route Tracking No==" + RouteTrackingNo);
				msg.append("\n");
				msg.append("Route Tracking No==" + RouteTrackingNo + "\n");
				isElementPresent("TLSARoutTrackNo_id").sendKeys(RouteTrackingNo);
				logger.info("Entered RouteTrackingID");
			}

			else if (Env.equalsIgnoreCase("PROD")) {

				String RouteTrackingNo = getData("RTE", 2, 1);

				logger.info("Route Tracking No==" + RouteTrackingNo);
				msg.append("\n");
				msg.append("Route Tracking No==" + RouteTrackingNo + "\n");
				isElementPresent("TLSARoutTrackNo_id").sendKeys(RouteTrackingNo);
				logger.info("Entered RouteTrackingID");
			}
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

					if (Env.equalsIgnoreCase("Test") || Env.equalsIgnoreCase("STG")) {

						int RT = RTE + 1;
						System.out.println("RT==" + RT);
						logger.info("JobID is==" + JobIDValue);
						setData("RTE", 1, 2, JobIDValue);

						logger.info("PickUpID is==" + PickUpIDValue);
						setData("RTE", 1, 3, PickUpIDValue);

						logger.info("BOLNo is==" + BOLNoValue);
						setData("RTE", 1, 4, BOLNoValue);
					}

					else if (Env.equalsIgnoreCase("PROD")) {

						int RT = RTE + 1;
						System.out.println("RT==" + RT);
						logger.info("JobID is==" + JobIDValue);
						setData("RTE", 2, 2, JobIDValue);

						logger.info("PickUpID is==" + PickUpIDValue);
						setData("RTE", 2, 3, PickUpIDValue);

						logger.info("BOLNo is==" + BOLNoValue);
						setData("RTE", 2, 4, BOLNoValue);
					}

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
					if (Env.equalsIgnoreCase("Test") || Env.equalsIgnoreCase("STG")) {
						setResultData("Result", 24, 4, "PASS");
					}

					else if (Env.equalsIgnoreCase("PROD")) {
						setResultData("Result", 25, 4, "PASS");
					}
				}
			}
		} catch (Exception ewait) {
			getScreenshot(Driver, "SearchRTEError");
			String Error = ewait.getMessage();
			setResultData("Result", 24, 4, "FAIL");
			setResultData("Result", 24, 5, Error);

		}

	}

	public void memoAuditHistory(String Service) throws IOException, InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		WebDriverWait wait1 = new WebDriverWait(Driver, 10);
		Actions act = new Actions(Driver);

		// --Go to Edit job
		try {
			// --Go to Edit Job tab
			WebElement EditOrTab = isElementPresent("EOEditOrderTab_id");
			act.moveToElement(EditOrTab).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(EditOrTab));
			act.moveToElement(EditOrTab).click().perform();
			logger.info("Click on Edit Order Tab");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception ee) {
			logger.info("Already Edit Job");

		}

		// --Click on Memo
		WebElement EMemo = isElementPresent("edit_tab_memo_id");
		wait1.until(ExpectedConditions.visibilityOf(EMemo));
		wait1.until(ExpectedConditions.elementToBeClickable(EMemo));
		EMemo.click();
		logger.info("Click on Memo");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("MemoPopup")));

		// --Click on AuditHistory
		WebElement AHistory = isElementPresent("EOAuditH_id");
		wait1.until(ExpectedConditions.visibilityOf(AHistory));
		wait1.until(ExpectedConditions.elementToBeClickable(AHistory));
		AHistory.click();
		logger.info("Click on Audit History");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("idAuditReportList")));

		// --Screenshot
		getScreenshot(Driver, "AUditHistory_0_" + Service);

		// --Scroll down
		// --Get total rows

		List<WebElement> AHRows = Driver.findElements(By.xpath("//*[@id=\"idAuditReportList\"]//tbody//tr"));
		int HistoryRows = AHRows.size();
		logger.info("Total rows==" + HistoryRows);

		int TotalRows = HistoryRows - 1;
		if (TotalRows > 10) {

			for (int Hrow = 11; Hrow < TotalRows; Hrow++) {
				WebElement currentRow = AHRows.get(Hrow);
				highLight(currentRow, Driver);
				logger.info("Row==" + Hrow);
				int NewHRow = Hrow;
				try {
					if (Hrow == 11 || Hrow == 21 || Hrow == 31 || Hrow == 41 || Hrow == 51 || Hrow == 61 || Hrow == 71
							|| Hrow == 81 || Hrow == 91 || Hrow == 101) {

						currentRow = AHRows.get(Hrow);
						act.moveToElement(currentRow).build().perform();
						Thread.sleep(2000);

						// --scroll to row no.11
						AHRows.get(Hrow);
						jse.executeScript("arguments[0].scrollIntoView(true);", currentRow);
						Thread.sleep(2000);
						logger.info("Scroll Next" + Hrow);

						int Sno = 0;
						if (Hrow == 11) {
							Sno = 1;
							NewHRow = 21;
						} else if (Hrow == 21) {
							Sno = 2;
							NewHRow = 31;

						} else if (Hrow == 31) {
							Sno = 3;
							NewHRow = 41;

						} else if (Hrow == 41) {
							Sno = 4;
							NewHRow = 51;

						} else if (Hrow == 51) {
							Sno = 5;
							NewHRow = 61;

						} else if (Hrow == 61) {
							Sno = 6;
							NewHRow = 71;

						} else if (Hrow == 71) {
							Sno = 7;
							NewHRow = 81;

						} else if (Hrow == 81) {
							Sno = 8;
							NewHRow = 91;

						} else if (Hrow == 91) {
							Sno = 9;
							NewHRow = 101;

						} else if (Hrow == 101) {
							Sno = 10;

						}

						getScreenshot(Driver, "AUditHi1tory_" + Sno + "_" + Service);

					}

				} catch (Exception stalelement) {
					List<WebElement> AHRows1 = Driver
							.findElements(By.xpath("//*[@id=\"idAuditReportList\"]//tbody//tr"));
					int HistoryRows1 = AHRows1.size();
					logger.info("Total rows==" + HistoryRows1);

					int TotalRows1 = HistoryRows1 - 1;

					for (int Hrow1 = Hrow; Hrow1 <= TotalRows1; Hrow1++) {
						WebElement currentRow1 = AHRows1.get(Hrow1);
						highLight(currentRow, Driver);

						if (Hrow1 == 11 || Hrow1 == 21 || Hrow1 == 31 || Hrow1 == 41 || Hrow1 == 51 || Hrow1 == 61
								|| Hrow1 == 71 || Hrow1 == 81 || Hrow1 == 91 || Hrow1 == 101) {

							currentRow1 = AHRows1.get(Hrow1);
							act.moveToElement(currentRow1).build().perform();
							Thread.sleep(2000);

							int Sno = 0;
							if (Hrow1 == 11) {
								Sno = 1;
							} else if (Hrow1 == 21) {
								Sno = 2;
							} else if (Hrow1 == 31) {
								Sno = 3;
							} else if (Hrow1 == 41) {
								Sno = 4;
							} else if (Hrow1 == 51) {
								Sno = 5;
							} else if (Hrow1 == 61) {
								Sno = 6;
							} else if (Hrow1 == 71) {
								Sno = 7;
							} else if (Hrow1 == 81) {
								Sno = 8;
							} else if (Hrow1 == 91) {
								Sno = 9;
							} else if (Hrow1 == 101) {
								Sno = 10;
							}

							// --scroll to row no.11
							jse.executeScript("arguments[0].scrollIntoView(true);", currentRow);
							Thread.sleep(2000);
							logger.info("Scroll to page");
							getScreenshot(Driver, "AUditHi1tory_" + Sno + "_" + Service);
							break;
						}
					}
				}
				Hrow = NewHRow;
				logger.info("Next Row==" + NewHRow);

			}
		}

		// --Close memo pop up
		WebElement AHClose = isElementPresent("EOAuditClose_xpath");
		wait1.until(ExpectedConditions.visibilityOf(AHClose));
		wait1.until(ExpectedConditions.elementToBeClickable(AHClose));
		AHClose.click();
		logger.info("Click on Close button of Audit History");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		refreshApp();
	}
}
