package netAgent_OrderProcessing;

import java.awt.AWTException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class OrderProcess extends BaseInit {

	@Test
	public void orderProcess()
			throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException, AWTException {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		logger.info("===Order Processing Test Start===");
		msg.append("===Order Processing Test Start===" + "\n\n");

		int rowNum = getTotalRow("OrderProcessing");
		logger.info("total No of Rows=" + rowNum);

		int colNum = getTotalCol("OrderProcessing");
		logger.info("total No of Columns=" + colNum);

		// Go To TaskLog
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id=\"idOperations\"]")));
		WebElement OperationMenu = Driver.findElement(By.xpath("//a[@id=\"idOperations\"]"));
		act.moveToElement(OperationMenu).build().perform();
		js.executeScript("arguments[0].click();", OperationMenu);

		logger.info("Click on Operations");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id=\"idTask\"]")));
		WebElement TaskLogMenu = Driver.findElement(By.xpath("//a[@id=\"idTask\"]"));
		act.moveToElement(TaskLogMenu).build().perform();
		js.executeScript("arguments[0].click();", TaskLogMenu);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));
		logger.info("Click on Task Log");

		getScreenshot(Driver, "TaskLog_Operations");

		// --Basic Search

		for (int row1 = 1; row1 < rowNum; row1++) {
			// --Search with PickUP ID
			String ServiceID = getData("OrderProcessing", row1, 0);
			logger.info("ServiceID is==" + ServiceID);
			String PUID = getData("OrderProcessing", row1, 1);
			logger.info("PickUpID is==" + PUID);

			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//*[@id=\"operation\"][contains(@class,'active ')]")));
				logger.info("Operation tab is already selected");

			} catch (Exception Operation) {
				logger.info("Operation tab is not selected");
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//*[@id=\"operation\" and  text()='Operations']")));
				wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//*[@id=\"operation\" and  text()='Operations']")));
				Driver.findElement(By.xpath("//*[@id=\"operation\" and  text()='Operations']")).click();
				logger.info("Click on Operation Tab");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			}
			getScreenshot(Driver, "TaskLog_Operations");

			try {

				if (ServiceID.contains("LOC") || ServiceID.contains("SD")) {
					logger.info("If ServiceID contain LOC or SD....");
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					Driver.findElement(By.id("txtBasicSearch2")).clear();
					logger.info("Clear search input");
					Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
					logger.info("Enter PickUpID in Search input");
					Driver.findElement(By.id("btnGXNLSearch2")).click();
					logger.info("Click on Search button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					WebElement PickuPBox = Driver.findElement(By.xpath("//*[contains(@class,'pickupbx')]"));
					if (PickuPBox.isDisplayed()) {
						logger.info("Searched Job is displayed in edit mode");
						getScreenshot(Driver, "OrderEditor_" + PUID);
					}
					// --Memo
					// memo(PUID);

					// -Notification
					// notification(PUID);

					// Upload
					// upload(PUID);

					// Map
					// map(PUID);

					// --Get current stage of the order
					String Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
					logger.info("Current stage of the order is=" + Orderstage);

					if (Orderstage.equalsIgnoreCase("Confirm Pu Alert")) {

						/*
						 * WebElement ConfPUSection = Driver.findElement( By.xpath(
						 * "//*[@class=\"Pickupdata\"]/div[contains(@ng-if,\"ConfirmPickUpResponse\")]")
						 * ); js.executeScript("arguments[0].scrollIntoView();", ConfPUSection);
						 */
						// --Confirm button
						WebElement ConfPU = Driver.findElement(By.id("lnkConfPick"));
						js.executeScript("arguments[0].scrollIntoView();", ConfPU);
						act.moveToElement(ConfPU).build().perform();
						js.executeScript("arguments[0].click();", ConfPU);
						logger.info("Clicked on CONFIRM button");

						try {
							// --Click on Close button //
							WebElement Closebtn = Driver.findElement(By.id("idclosetab"));
							act.moveToElement(Closebtn).build().perform();
							act.moveToElement(Closebtn).click().perform();
							logger.info("Clicked on Close button");
						} catch (Exception close) {
							logger.info("Editor is already closed");

						}

						// --Search again
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						WebElement Search = Driver.findElement(By.id("txtBasicSearch2"));
						Search.clear();
						logger.info("Clear search input");
						Search.sendKeys(PUID);
						logger.info("Enter PickUpID in Search input");
						act.moveToElement(Search).click().perform();
						logger.info("Click on Search button");

						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is not moved to PICKUP stage successfully");

							}
						} catch (Exception e1) {
							logger.info("Job is moved to PICKUP stage successfully");

							// --Pickup
							Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
							logger.info("Current stage of the order is=" + Orderstage);

							// js.executeScript("document.body.style.zoom = '80%';");

							WebElement Save = Driver.findElement(By.id("lnksave"));
							js.executeScript("arguments[0].scrollIntoView();", Save);
							act.moveToElement(Save).build().perform();
							act.moveToElement(Save).build().perform();
							act.moveToElement(Save).build().perform();

							// --Enter PickUp Time

							String ZOneID = Driver.findElement(By.id("spanTimezoneId")).getText();
							logger.info("ZoneID of is==" + ZOneID);

							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";

							}

							WebElement PUPTime = Driver.findElement(By.id("txtActualPickUpTime"));
							act.moveToElement(PUPTime).build().perform();
							PUPTime.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PUPTime.sendKeys(dateFormat.format(date));

							// Scroll up
							// js.executeScript("window.scrollBy(0,-250)");

							// PickUp
							Save = Driver.findElement(By.id("lnksave"));
							act.moveToElement(Save).build().perform();
							js.executeScript("arguments[0].click();", Save);
							logger.info("Clicked on PICKUP button");

							try {
								wait.until(ExpectedConditions
										.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
								Driver.findElement(By.id("iddataok")).click();
								logger.info("Clicked on Yes button");

							} catch (Exception e) {
								logger.info("Dialogue is not exist");

							}
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							logger.info("Clear search input");
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							logger.info("Enter PickUpID in Search input");
							Driver.findElement(By.id("btnGXNLSearch2")).click();
							logger.info("Click on Search button");

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not moved to DELIVER stage successfully");

								}
							} catch (Exception DNoData) {
								logger.info("Job is moved to DELIVER stage successfully");
								ServiceID = getData("OrderProcessing", row1, 0);
								logger.info("Service ID is==" + ServiceID);
								if (ServiceID.equalsIgnoreCase("LOC")) {

									// --Deliver Stage
									Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]"))
											.getText();
									logger.info("Current stage of the order is=" + Orderstage);

									WebElement DelZone = Driver.findElement(By.id("lblactdltz"));
									act.moveToElement(DelZone).build().perform();

									// --Deliver Time

									ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
									logger.info("ZoneID of is==" + ZOneID);
									if (ZOneID.equalsIgnoreCase("EDT")) {
										ZOneID = "America/New_York";
									} else if (ZOneID.equalsIgnoreCase("CDT")) {
										ZOneID = "CST";

									}

									WebElement DelTime = Driver.findElement(By.id("txtActualDeliveryTme"));
									act.moveToElement(DelTime).build().perform();
									DelTime.clear();
									date = new Date();
									dateFormat = new SimpleDateFormat("HH:mm");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									DelTime.sendKeys(dateFormat.format(date));

									// --Signature
									WebElement Sign = Driver.findElement(By.id("txtSignature"));
									act.moveToElement(Sign).build().perform();
									Sign.sendKeys("Ravina Prajapati");

									logger.info("Entered signature");

									// --Click on Deliver
									WebElement Del = Driver.findElement(By.id("btnsavedelivery"));
									act.moveToElement(Del).build().perform();
									js.executeScript("arguments[0].click();", Del);
									logger.info("Clicked on Deliver button");

									try {
										wait.until(ExpectedConditions
												.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
										Driver.findElement(By.id("iddataok")).click();
										logger.info("Clicked on Yes button");

									} catch (Exception e) {
										logger.info("Dialogue is not exist");

									}
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
									Driver.findElement(By.id("txtBasicSearch2")).clear();
									logger.info("Clear search input");
									Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
									logger.info("Enter PickUpID in Search input");
									Driver.findElement(By.id("btnGXNLSearch2")).click();
									logger.info("Click on Search button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									try {
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
										WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
										if (NoData.isDisplayed()) {
											logger.info("Job is Delivered successfully");

										}
									} catch (Exception NoData) {
										logger.info("Job is not delivered yet");

									}

								} else if (ServiceID.equalsIgnoreCase("SD")) {

									Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]"))
											.getText();
									logger.info("Current stage of the order is=" + Orderstage);

									WebElement DelZone = Driver.findElement(By.id("lblactdltz"));
									act.moveToElement(DelZone).build().perform();
									act.moveToElement(DelZone).build().perform();
									act.moveToElement(DelZone).build().perform();

									// --Drop Time

									ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
									logger.info("ZoneID of is==" + ZOneID);
									if (ZOneID.equalsIgnoreCase("EDT")) {
										ZOneID = "America/New_York";
									} else if (ZOneID.equalsIgnoreCase("CDT")) {
										ZOneID = "CST";
									}

									WebElement DelTime = Driver.findElement(By.id("txtActualDeliveryTme"));
									act.moveToElement(DelTime).build().perform();
									DelTime.clear();
									date = new Date();
									dateFormat = new SimpleDateFormat("HH:mm");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									DelTime.sendKeys(dateFormat.format(date));

									// --Click on Drop
									WebElement Drop = Driver.findElement(By.id("btnsavedelivery"));
									act.moveToElement(Drop).build().perform();
									act.moveToElement(Drop).build().perform();
									js.executeScript("arguments[0].click();", Drop);
									logger.info("Clicked on Deliver button");
									try {
										WebElement ErrorID = Driver.findElement(By.id("errorid"));
										if (ErrorID.getText().contains("The Air Bill is required")) {
											logger.info("Message:-" + ErrorID.getText());

											// --Add Airbill
											WebElement AirBill = Driver.findElement(By.id("lnkAddAWB"));
											js.executeScript("arguments[0].scrollIntoView();", AirBill);

											WebElement AddAirBill = Driver.findElement(By.id("btnAddAWB"));
											js.executeScript("arguments[0].click();", AddAirBill);
											wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
													By.xpath("//*[@id=\"tableairbill\"]/tbody/tr")));
											logger.info("AirBill editor is opened");

											/// --Enter AirBill

											WebElement Airbill = Driver.findElement(By.id("txtAWBNum_0"));
											act.moveToElement(Airbill).build().perform();
											Airbill.sendKeys("11111111");
											logger.info("Entered AirBill");

											/// --Enter Description
											WebElement Desc = Driver.findElement(By.id("txtAWBDec_0"));
											act.moveToElement(Desc).build().perform();
											Desc.sendKeys("SD Service Automation");
											logger.info("Entered Description");

											/// --Enter NoOFPieces
											WebElement NoOFP = Driver.findElement(By.id("txtNoOfPieces_0"));
											act.moveToElement(NoOFP).build().perform();
											NoOFP.sendKeys("2");
											logger.info("Entered NoOFPieces");

											/// --Enter Total Weight
											WebElement TWght = Driver.findElement(By.id("txtTotalweight_0"));
											act.moveToElement(TWght).build().perform();
											TWght.sendKeys("10");
											logger.info("Entered Total Weight");

											// --Track
											wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Track")));
											WebElement Track = Driver.findElement(By.linkText("Track"));
											js.executeScript("arguments[0].click();", Track);
											logger.info("Clicked on Track button");

											// --AIrbill new window
											String WindowHandlebefore = Driver.getWindowHandle();
											for (String windHandle : Driver.getWindowHandles()) {
												Driver.switchTo().window(windHandle);
												logger.info("Switched to Track window");

												Thread.sleep(5000);
												getScreenshot(Driver, "Track" + PUID);

											}
											Driver.close();
											logger.info("Closed Track window");

											Driver.switchTo().window(WindowHandlebefore);
											logger.info("Switched to main window");

											// Scroll up
											js.executeScript("window.scrollBy(0,-250)");

											// --Click on Drop
											Drop = Driver.findElement(By.id("btnsavedelivery"));
											js.executeScript("arguments[0].scrollIntoView();", Drop);
											act.moveToElement(Drop).build().perform();
											act.moveToElement(Drop).build().perform();
											js.executeScript("arguments[0].click();", Drop);
											logger.info("Clicked on Drop button");
											wait.until(ExpectedConditions.invisibilityOfElementLocated(
													By.xpath("//*[@class=\"ajax-loadernew\"]")));

											wait.until(ExpectedConditions
													.visibilityOfElementLocated(By.id("txtBasicSearch2")));
											Driver.findElement(By.id("txtBasicSearch2")).clear();
											logger.info("Clear search input");
											Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
											logger.info("Enter PickUpID in Search input");
											Driver.findElement(By.id("btnGXNLSearch2")).click();
											logger.info("Click on Search button");
											wait.until(ExpectedConditions.invisibilityOfElementLocated(
													By.xpath("//*[@class=\"ajax-loadernew\"]")));
											try {
												wait.until(ExpectedConditions.visibilityOfElementLocated(
														By.className("dx-datagrid-nodata")));
												WebElement NoData = Driver
														.findElement(By.className("dx-datagrid-nodata"));
												if (NoData.isDisplayed()) {
													logger.info("Job is Delivered successfully");

												}
											} catch (Exception dNoData) {
												logger.info("Job is not Delivered yet");

											}

										}
									} catch (Exception AB) {
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.id("txtBasicSearch2")));
										Driver.findElement(By.id("txtBasicSearch2")).clear();
										logger.info("Clear search input");
										Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
										logger.info("Enter PickUpID in Search input");
										Driver.findElement(By.id("btnGXNLSearch2")).click();
										logger.info("Click on Search button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
										try {
											wait.until(ExpectedConditions
													.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
											WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
											if (NoData.isDisplayed()) {
												logger.info("Job is Delivered successfully");

											}
										} catch (Exception NoDataD) {
											logger.info("Job is not Delivered yet");

										}
									}

								} else {
									logger.info("Service is not SD or LOC");

								}

							}

						}

					} else if (Orderstage.equalsIgnoreCase("PICKUP")) {
						logger.info("Job is moved to PICKUP stage successfully");

						// --Pickup
						Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
						logger.info("Current stage of the order is=" + Orderstage);

						// js.executeScript("document.body.style.zoom = '80%';");

						WebElement Save = Driver.findElement(By.id("lnksave"));
						js.executeScript("arguments[0].scrollIntoView();", Save);
						act.moveToElement(Save).build().perform();
						act.moveToElement(Save).build().perform();
						act.moveToElement(Save).build().perform();

						// --Enter PickUp Time

						String ZOneID = Driver.findElement(By.id("spanTimezoneId")).getText();
						logger.info("ZoneID of is==" + ZOneID);

						if (ZOneID.equalsIgnoreCase("EDT")) {
							ZOneID = "America/New_York";
						} else if (ZOneID.equalsIgnoreCase("CDT")) {
							ZOneID = "CST";

						}

						WebElement PUPTime = Driver.findElement(By.id("txtActualPickUpTime"));
						act.moveToElement(PUPTime).build().perform();
						PUPTime.clear();
						Date date = new Date();
						DateFormat dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logger.info(dateFormat.format(date));
						PUPTime.sendKeys(dateFormat.format(date));

						// Scroll up
						// js.executeScript("window.scrollBy(0,-250)");

						// PickUp
						Save = Driver.findElement(By.id("lnksave"));
						act.moveToElement(Save).build().perform();
						js.executeScript("arguments[0].click();", Save);
						logger.info("Clicked on PICKUP button");

						try {
							wait.until(
									ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
							Driver.findElement(By.id("iddataok")).click();
							logger.info("Clicked on Yes button");

						} catch (Exception e) {
							logger.info("Dialogue is not exist");

						}
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						logger.info("Clear search input");
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						logger.info("Enter PickUpID in Search input");
						Driver.findElement(By.id("btnGXNLSearch2")).click();
						logger.info("Click on Search button");

						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is not moved to DELIVER stage successfully");

							}
						} catch (Exception DNoData) {
							logger.info("Job is moved to DELIVER stage successfully");
							ServiceID = getData("OrderProcessing", row1, 0);
							logger.info("Service ID is==" + ServiceID);
							if (ServiceID.equalsIgnoreCase("LOC")) {

								// --Deliver Stage
								Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]"))
										.getText();
								logger.info("Current stage of the order is=" + Orderstage);

								WebElement DelZone = Driver.findElement(By.id("lblactdltz"));
								act.moveToElement(DelZone).build().perform();

								// --Deliver Time

								ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
								logger.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";

								}

								WebElement DelTime = Driver.findElement(By.id("txtActualDeliveryTme"));
								act.moveToElement(DelTime).build().perform();
								DelTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								DelTime.sendKeys(dateFormat.format(date));

								// --Signature
								WebElement Sign = Driver.findElement(By.id("txtSignature"));
								act.moveToElement(Sign).build().perform();
								Sign.sendKeys("Ravina Prajapati");

								logger.info("Entered signature");

								// --Click on Deliver
								WebElement Del = Driver.findElement(By.id("btnsavedelivery"));
								act.moveToElement(Del).build().perform();
								js.executeScript("arguments[0].click();", Del);
								logger.info("Clicked on Deliver button");

								try {
									wait.until(ExpectedConditions
											.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
									Driver.findElement(By.id("iddataok")).click();
									logger.info("Clicked on Yes button");

								} catch (Exception e) {
									logger.info("Dialogue is not exist");

								}
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								logger.info("Clear search input");
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								logger.info("Enter PickUpID in Search input");
								Driver.findElement(By.id("btnGXNLSearch2")).click();
								logger.info("Click on Search button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										logger.info("Job is Delivered successfully");

									}
								} catch (Exception NoData) {
									logger.info("Job is not delivered yet");

								}

							} else if (ServiceID.equalsIgnoreCase("SD")) {

								Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]"))
										.getText();
								logger.info("Current stage of the order is=" + Orderstage);

								WebElement DelZone = Driver.findElement(By.id("lblactdltz"));
								act.moveToElement(DelZone).build().perform();
								act.moveToElement(DelZone).build().perform();
								act.moveToElement(DelZone).build().perform();

								// --Drop Time

								ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
								logger.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}

								WebElement DelTime = Driver.findElement(By.id("txtActualDeliveryTme"));
								act.moveToElement(DelTime).build().perform();
								DelTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								DelTime.sendKeys(dateFormat.format(date));

								// --Click on Drop
								WebElement Drop = Driver.findElement(By.id("btnsavedelivery"));
								act.moveToElement(Drop).build().perform();
								act.moveToElement(Drop).build().perform();
								js.executeScript("arguments[0].click();", Drop);
								logger.info("Clicked on Deliver button");
								try {
									WebElement ErrorID = Driver.findElement(By.id("errorid"));
									if (ErrorID.getText().contains("The Air Bill is required")) {
										logger.info("Message:-" + ErrorID.getText());

										// --Add Airbill
										WebElement AirBill = Driver.findElement(By.id("lnkAddAWB"));
										js.executeScript("arguments[0].scrollIntoView();", AirBill);

										WebElement AddAirBill = Driver.findElement(By.id("btnAddAWB"));
										js.executeScript("arguments[0].click();", AddAirBill);
										wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
												By.xpath("//*[@id=\"tableairbill\"]/tbody/tr")));
										logger.info("AirBill editor is opened");

										/// --Enter AirBill

										WebElement Airbill = Driver.findElement(By.id("txtAWBNum_0"));
										act.moveToElement(Airbill).build().perform();
										Airbill.sendKeys("11111111");
										logger.info("Entered AirBill");

										/// --Enter Description
										WebElement Desc = Driver.findElement(By.id("txtAWBDec_0"));
										act.moveToElement(Desc).build().perform();
										Desc.sendKeys("SD Service Automation");
										logger.info("Entered Description");

										/// --Enter NoOFPieces
										WebElement NoOFP = Driver.findElement(By.id("txtNoOfPieces_0"));
										act.moveToElement(NoOFP).build().perform();
										NoOFP.sendKeys("2");
										logger.info("Entered NoOFPieces");

										/// --Enter Total Weight
										WebElement TWght = Driver.findElement(By.id("txtTotalweight_0"));
										act.moveToElement(TWght).build().perform();
										TWght.sendKeys("10");
										logger.info("Entered Total Weight");

										// --Track
										wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Track")));
										WebElement Track = Driver.findElement(By.linkText("Track"));
										js.executeScript("arguments[0].click();", Track);
										logger.info("Clicked on Track button");

										// --AIrbill new window
										String WindowHandlebefore = Driver.getWindowHandle();
										for (String windHandle : Driver.getWindowHandles()) {
											Driver.switchTo().window(windHandle);
											logger.info("Switched to Track window");

											Thread.sleep(5000);
											getScreenshot(Driver, "Track" + PUID);

										}
										Driver.close();
										logger.info("Closed Track window");

										Driver.switchTo().window(WindowHandlebefore);
										logger.info("Switched to main window");

										// Scroll up
										js.executeScript("window.scrollBy(0,-250)");

										// --Click on Drop
										Drop = Driver.findElement(By.id("btnsavedelivery"));
										js.executeScript("arguments[0].scrollIntoView();", Drop);
										act.moveToElement(Drop).build().perform();
										act.moveToElement(Drop).build().perform();
										js.executeScript("arguments[0].click();", Drop);
										logger.info("Clicked on Drop button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));

										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.id("txtBasicSearch2")));
										Driver.findElement(By.id("txtBasicSearch2")).clear();
										logger.info("Clear search input");
										Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
										logger.info("Enter PickUpID in Search input");
										Driver.findElement(By.id("btnGXNLSearch2")).click();
										logger.info("Click on Search button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
										try {
											wait.until(ExpectedConditions
													.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
											WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
											if (NoData.isDisplayed()) {
												logger.info("Job is Delivered successfully");

											}
										} catch (Exception dNoData) {
											logger.info("Job is not Delivered yet");

										}

									}
								} catch (Exception AB) {
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
									Driver.findElement(By.id("txtBasicSearch2")).clear();
									logger.info("Clear search input");
									Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
									logger.info("Enter PickUpID in Search input");
									Driver.findElement(By.id("btnGXNLSearch2")).click();
									logger.info("Click on Search button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									try {
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
										WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
										if (NoData.isDisplayed()) {
											logger.info("Job is Delivered successfully");

										}
									} catch (Exception NoDataD) {
										logger.info("Job is not Delivered yet");

									}
								}

							} else {
								logger.info("Service is not SD or LOC");

							}

						}

					} else if (Orderstage.equalsIgnoreCase("Deliver")) {
						logger.info("Job is moved to DELIVER stage successfully");
						ServiceID = getData("OrderProcessing", row1, 0);
						logger.info("Service ID is==" + ServiceID);
						if (ServiceID.equalsIgnoreCase("LOC")) {

							// --Deliver Stage
							Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
							logger.info("Current stage of the order is=" + Orderstage);

							WebElement DelZone = Driver.findElement(By.id("lblactdltz"));
							act.moveToElement(DelZone).build().perform();

							// --Deliver Time

							String ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
							logger.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";

							}

							WebElement DelTime = Driver.findElement(By.id("txtActualDeliveryTme"));
							act.moveToElement(DelTime).build().perform();
							DelTime.clear();
							Date date = new Date();
							SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							DelTime.sendKeys(dateFormat.format(date));

							// --Signature
							WebElement Sign = Driver.findElement(By.id("txtSignature"));
							act.moveToElement(Sign).build().perform();
							Sign.sendKeys("Ravina Prajapati");

							logger.info("Entered signature");

							// --Click on Deliver
							WebElement Del = Driver.findElement(By.id("btnsavedelivery"));
							act.moveToElement(Del).build().perform();
							js.executeScript("arguments[0].click();", Del);
							logger.info("Clicked on Deliver button");

							try {
								wait.until(ExpectedConditions
										.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
								Driver.findElement(By.id("iddataok")).click();
								logger.info("Clicked on Yes button");

							} catch (Exception e) {
								logger.info("Dialogue is not exist");

							}
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							logger.info("Clear search input");
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							logger.info("Enter PickUpID in Search input");
							Driver.findElement(By.id("btnGXNLSearch2")).click();
							logger.info("Click on Search button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is Delivered successfully");

								}
							} catch (Exception NoData) {
								logger.info("Job is not delivered yet");

							}

						} else if (ServiceID.equalsIgnoreCase("SD")) {

							Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
							logger.info("Current stage of the order is=" + Orderstage);

							WebElement DelZone = Driver.findElement(By.id("lblactdltz"));
							act.moveToElement(DelZone).build().perform();
							act.moveToElement(DelZone).build().perform();
							act.moveToElement(DelZone).build().perform();

							// --Drop Time

							String ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
							logger.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							}

							WebElement DelTime = Driver.findElement(By.id("txtActualDeliveryTme"));
							act.moveToElement(DelTime).build().perform();
							DelTime.clear();
							Date date = new Date();
							SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							DelTime.sendKeys(dateFormat.format(date));

							// --Click on Drop
							WebElement Drop = Driver.findElement(By.id("btnsavedelivery"));
							act.moveToElement(Drop).build().perform();
							act.moveToElement(Drop).build().perform();
							js.executeScript("arguments[0].click();", Drop);
							logger.info("Clicked on Deliver button");
							try {
								WebElement ErrorID = Driver.findElement(By.id("errorid"));
								if (ErrorID.getText().contains("The Air Bill is required")) {
									logger.info("Message:-" + ErrorID.getText());

									// --Add Airbill
									WebElement AirBill = Driver.findElement(By.id("lnkAddAWB"));
									js.executeScript("arguments[0].scrollIntoView();", AirBill);

									WebElement AddAirBill = Driver.findElement(By.id("btnAddAWB"));
									js.executeScript("arguments[0].click();", AddAirBill);
									wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
											By.xpath("//*[@id=\"tableairbill\"]/tbody/tr")));
									logger.info("AirBill editor is opened");

									/// --Enter AirBill

									WebElement Airbill = Driver.findElement(By.id("txtAWBNum_0"));
									act.moveToElement(Airbill).build().perform();
									Airbill.sendKeys("11111111");
									logger.info("Entered AirBill");

									/// --Enter Description
									WebElement Desc = Driver.findElement(By.id("txtAWBDec_0"));
									act.moveToElement(Desc).build().perform();
									Desc.sendKeys("SD Service Automation");
									logger.info("Entered Description");

									/// --Enter NoOFPieces
									WebElement NoOFP = Driver.findElement(By.id("txtNoOfPieces_0"));
									act.moveToElement(NoOFP).build().perform();
									NoOFP.sendKeys("2");
									logger.info("Entered NoOFPieces");

									/// --Enter Total Weight
									WebElement TWght = Driver.findElement(By.id("txtTotalweight_0"));
									act.moveToElement(TWght).build().perform();
									TWght.sendKeys("10");
									logger.info("Entered Total Weight");

									// --Track
									wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Track")));
									WebElement Track = Driver.findElement(By.linkText("Track"));
									js.executeScript("arguments[0].click();", Track);
									logger.info("Clicked on Track button");

									// --AIrbill new window
									String WindowHandlebefore = Driver.getWindowHandle();
									for (String windHandle : Driver.getWindowHandles()) {
										Driver.switchTo().window(windHandle);
										logger.info("Switched to Track window");

										Thread.sleep(5000);
										getScreenshot(Driver, "Track" + PUID);

									}
									Driver.close();
									logger.info("Closed Track window");

									Driver.switchTo().window(WindowHandlebefore);
									logger.info("Switched to main window");

									// Scroll up
									js.executeScript("window.scrollBy(0,-250)");

									// --Click on Drop
									Drop = Driver.findElement(By.id("btnsavedelivery"));
									js.executeScript("arguments[0].scrollIntoView();", Drop);
									act.moveToElement(Drop).build().perform();
									act.moveToElement(Drop).build().perform();
									js.executeScript("arguments[0].click();", Drop);
									logger.info("Clicked on Drop button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
									Driver.findElement(By.id("txtBasicSearch2")).clear();
									logger.info("Clear search input");
									Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
									logger.info("Enter PickUpID in Search input");
									Driver.findElement(By.id("btnGXNLSearch2")).click();
									logger.info("Click on Search button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									try {
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
										WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
										if (NoData.isDisplayed()) {
											logger.info("Job is Delivered successfully");

										}
									} catch (Exception dNoData) {
										logger.info("Job is not Delivered yet");

									}

								}
							} catch (Exception AB) {
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								logger.info("Clear search input");
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								logger.info("Enter PickUpID in Search input");
								Driver.findElement(By.id("btnGXNLSearch2")).click();
								logger.info("Click on Search button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										logger.info("Job is Delivered successfully");

									}
								} catch (Exception NoDataD) {
									logger.info("Job is not Delivered yet");

								}
							}

						} else {
							logger.info("Service is not SD or LOC");

						}

					} else if (Orderstage.equalsIgnoreCase("Drop @ Origin")) {

						Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
						logger.info("Current stage of the order is=" + Orderstage);

						WebElement DelZone = Driver.findElement(By.id("lblactdltz"));
						act.moveToElement(DelZone).build().perform();
						act.moveToElement(DelZone).build().perform();
						act.moveToElement(DelZone).build().perform();

						// --Drop Time

						String ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
						logger.info("ZoneID of is==" + ZOneID);
						if (ZOneID.equalsIgnoreCase("EDT")) {
							ZOneID = "America/New_York";
						} else if (ZOneID.equalsIgnoreCase("CDT")) {
							ZOneID = "CST";
						}

						WebElement DelTime = Driver.findElement(By.id("txtActualDeliveryTme"));
						act.moveToElement(DelTime).build().perform();
						DelTime.clear();
						Date date = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logger.info(dateFormat.format(date));
						DelTime.sendKeys(dateFormat.format(date));

						// --Click on Drop
						WebElement Drop = Driver.findElement(By.id("btnsavedelivery"));
						act.moveToElement(Drop).build().perform();
						act.moveToElement(Drop).build().perform();
						js.executeScript("arguments[0].click();", Drop);
						logger.info("Clicked on Deliver button");
						try {
							WebElement ErrorID = Driver.findElement(By.id("errorid"));
							if (ErrorID.getText().contains("The Air Bill is required")) {
								logger.info("Message:-" + ErrorID.getText());

								// --Add Airbill
								WebElement AirBill = Driver.findElement(By.id("lnkAddAWB"));
								js.executeScript("arguments[0].scrollIntoView();", AirBill);

								WebElement AddAirBill = Driver.findElement(By.id("btnAddAWB"));
								js.executeScript("arguments[0].click();", AddAirBill);
								wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
										By.xpath("//*[@id=\"tableairbill\"]/tbody/tr")));
								logger.info("AirBill editor is opened");

								/// --Enter AirBill

								WebElement Airbill = Driver.findElement(By.id("txtAWBNum_0"));
								act.moveToElement(Airbill).build().perform();
								Airbill.sendKeys("11111111");
								logger.info("Entered AirBill");

								/// --Enter Description
								WebElement Desc = Driver.findElement(By.id("txtAWBDec_0"));
								act.moveToElement(Desc).build().perform();
								Desc.sendKeys("SD Service Automation");
								logger.info("Entered Description");

								/// --Enter NoOFPieces
								WebElement NoOFP = Driver.findElement(By.id("txtNoOfPieces_0"));
								act.moveToElement(NoOFP).build().perform();
								NoOFP.sendKeys("2");
								logger.info("Entered NoOFPieces");

								/// --Enter Total Weight
								WebElement TWght = Driver.findElement(By.id("txtTotalweight_0"));
								act.moveToElement(TWght).build().perform();
								TWght.sendKeys("10");
								logger.info("Entered Total Weight");

								// --Track
								wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Track")));
								WebElement Track = Driver.findElement(By.linkText("Track"));
								js.executeScript("arguments[0].click();", Track);
								logger.info("Clicked on Track button");

								// --AIrbill new window
								String WindowHandlebefore = Driver.getWindowHandle();
								for (String windHandle : Driver.getWindowHandles()) {
									Driver.switchTo().window(windHandle);
									logger.info("Switched to Track window");

									Thread.sleep(5000);
									getScreenshot(Driver, "Track" + PUID);

								}
								Driver.close();
								logger.info("Closed Track window");

								Driver.switchTo().window(WindowHandlebefore);
								logger.info("Switched to main window");

								// Scroll up
								js.executeScript("window.scrollBy(0,-250)");

								// --Click on Drop
								Drop = Driver.findElement(By.id("btnsavedelivery"));
								js.executeScript("arguments[0].scrollIntoView();", Drop);
								act.moveToElement(Drop).build().perform();
								act.moveToElement(Drop).build().perform();
								js.executeScript("arguments[0].click();", Drop);
								logger.info("Clicked on Drop button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								logger.info("Clear search input");
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								logger.info("Enter PickUpID in Search input");
								Driver.findElement(By.id("btnGXNLSearch2")).click();
								logger.info("Click on Search button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										logger.info("Job is Delivered successfully");

									}
								} catch (Exception dNoData) {
									logger.info("Job is not Delivered yet");

								}

							}
						} catch (Exception AB) {
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							logger.info("Clear search input");
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							logger.info("Enter PickUpID in Search input");
							Driver.findElement(By.id("btnGXNLSearch2")).click();
							logger.info("Click on Search button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is Delivered successfully");

								}
							} catch (Exception NoDataD) {
								logger.info("Job is not Delivered yet");

							}
						}

					} else if (Orderstage.equalsIgnoreCase("Confirm Del Alert")) {

						// --Zoom out
						js.executeScript("document.body.style.zoom='80%';");
						Thread.sleep(2000);

						// --Confirm button
						WebElement CondirmP = Driver.findElement(By.id("lnkConfPick"));
						act.moveToElement(CondirmP).build().perform();
						CondirmP.click();
						logger.info("Clicked on CONFIRM button");

						try {
							// --Click on Close button //
							Driver.findElement(By.id("idclosetab")).click();
							logger.info("Clicked on Close button");
						} catch (Exception close) {
							logger.info("Editor is already closed");

						}
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						// --Zoom In
						js.executeScript("document.body.style.zoom='100%';");
						Thread.sleep(2000);

						// --Search again
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						logger.info("Clear search input");
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						logger.info("Enter PickUpID in Search input");
						Driver.findElement(By.id("btnGXNLSearch2")).click();
						logger.info("Click on Search button");

						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is not moved to ON BOARD stage successfully");

							}
						} catch (Exception e1) {
							logger.info("Job is moved to ON BOARD stage successfully");

						}

					}
				} else if (ServiceID.contains("H3P")) {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					Driver.findElement(By.id("txtBasicSearch2")).clear();
					Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
					Driver.findElement(By.id("btnGXNLSearch2")).click();
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					// --Memo
					memo(PUID);

					// -Notification
					notification(PUID);

					// Upload
					upload(PUID);

					// Map
					map(PUID);
					String Orderstage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
					logger.info("Current stage of the order is=" + Orderstage);

					if (Orderstage.contains("Confirm Pull and Apply Return Pack(s)")
							|| Orderstage.contains("CONF PULL ALERT")) {
						WebElement Form = Driver.findElement(By.name("ConfPullAlertForm"));
						if (Form.isDisplayed()) {
							logger.info("Searched Job is displayed in edit mode");
							getScreenshot(Driver, "OrderEditor_" + PUID);
						}
						// --Confirm Pull and Apply Return Pack(s) stage
						String stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
						logger.info("stage=" + stage);
						getScreenshot(Driver, "ConfirmPullandApplyReturnPack(s)_" + PUID);

						// --Zoom out
						js.executeScript("document.body.style.zoom='80%';");
						Thread.sleep(2000);

						// --Click on Accept
						WebElement Accept = Driver.findElement(By.id("idiconaccept"));
						act.moveToElement(Accept).build().perform();
						Accept.click();
						logger.info("Clicked on Accept button");

						// --Zoom out
						js.executeScript("document.body.style.zoom='100%';");
						Thread.sleep(2000);

						try {
							wait.until(ExpectedConditions
									.visibilityOfElementLocated(By.xpath("//*[@ng-message=\"required\"]")));
							logger.info("Validation Message is=="
									+ Driver.findElement(By.xpath("//*[@ng-message=\"required\"]")).getText());
							// --Spoke with
							WebElement SpokeWith = Driver.findElement(By.id("idConfPullAlertForm"));
							act.moveToElement(SpokeWith).build().perform();
							SpokeWith.sendKeys("Ravina Oza");
							logger.info("Entered spoke with");
							// --Click on Accept
							Accept = Driver.findElement(By.id("idiconaccept"));
							act.moveToElement(Accept).build().perform();
							Accept.click();
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							Driver.findElement(By.id("btnGXNLSearch2")).click();
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not moved to Confirm Pull stage successfully");
								}
							} catch (Exception e1) {
								logger.info("Job is moved to Confirm Pull stage successfully");

								// --Confirm Pull stage
								stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
								logger.info("stage=" + stage);
								getScreenshot(Driver, "ConfirmPull_" + PUID);
								// --Label generation
								WebElement LabelG = Driver.findElement(By.id("idiconprint"));
								act.moveToElement(LabelG).build().perform();
								LabelG.click();
								logger.info("Clicked on Label Generation");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								// Handle Label Generation window
								String WindowHandlebefore = Driver.getWindowHandle();
								for (String windHandle : Driver.getWindowHandles()) {
									Driver.switchTo().window(windHandle);
									logger.info("Switched to Label generation window");
									Thread.sleep(5000);
									getScreenshot(Driver, "Labelgeneration" + PUID);
								}
								Driver.close();
								logger.info("Closed Label generation window");

								Driver.switchTo().window(WindowHandlebefore);
								logger.info("Switched to main window");

								// --Save button
								WebElement Save = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Save).build().perform();
								Save.click();
								logger.info("Clicked on Save button");
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.id("idPartPullDttmValidation")));
									String ValMsg = Driver.findElement(By.id("idPartPullDttmValidation")).getText();
									logger.info("Validation Message=" + ValMsg);

									// --ZoneID
									String ZOneID = Driver
											.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]")).getText();
									logger.info("ZoneID of is==" + ZOneID);
									if (ZOneID.equalsIgnoreCase("EDT")) {
										ZOneID = "America/New_York";
									} else if (ZOneID.equalsIgnoreCase("CDT")) {
										ZOneID = "CST";
									}

									// --Part Pull Date
									WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
									act.moveToElement(PartPullDate).build().perform();
									PartPullDate.clear();
									Date date = new Date();
									DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									PartPullDate.sendKeys(dateFormat.format(date));
									PartPullDate.sendKeys(Keys.TAB);

									// --Part Pull Time
									WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
									act.moveToElement(PartPullTime).build().perform();
									PartPullTime.clear();
									date = new Date();
									dateFormat = new SimpleDateFormat("HH:mm");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									PartPullTime.sendKeys(dateFormat.format(date));

									// --Zoom out
									js.executeScript("document.body.style.zoom='80%';");
									Thread.sleep(2000);

									// --Save button
									Save = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Save).build().perform();
									Save.click();
									logger.info("Clicked on Save button");
								} catch (Exception e) {
									logger.info("Validation Message is not displayed");
								}

								try {
									wait.until(ExpectedConditions.visibilityOfElementLocated(
											By.xpath("//label[contains(@class,'error-messages')]")));
									logger.info("ErroMsg is Displayed="
											+ Driver.findElement(By.xpath("//label[contains(@class,'error-messages')]"))
													.getText());
									Thread.sleep(2000);

									// --Zoom In
									js.executeScript("document.body.style.zoom='100%';");
									Thread.sleep(2000);

									// --Get the serial NO
									String SerialNo = Driver.findElement(By.xpath("//*[@ng-bind=\"segment.SerialNo\"]"))
											.getText();
									logger.info("Serial No of Part is==" + SerialNo + "\n");
									// enter serial number in scan
									WebElement SerialNoBar = Driver.findElement(By.id("txtBarcode"));
									act.moveToElement(SerialNoBar).build().perform();
									SerialNoBar.clear();
									Driver.findElement(By.id("txtBarcode")).sendKeys(SerialNo);
									Driver.findElement(By.id("txtBarcode")).sendKeys(Keys.TAB);
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									logger.info("Entered serial No in scan barcode");

									// --Zoom out
									js.executeScript("document.body.style.zoom='80%';");
									Thread.sleep(2000);

									// --Save button
									Save = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Save).build().perform();
									Save.click();
									logger.info("Clicked on Save button");

									try {
										wait.until(ExpectedConditions.visibilityOfElementLocated(
												By.xpath("//label[contains(@class,'error-messages')]")));
										logger.info("ErroMsg is Displayed=" + Driver
												.findElement(By.xpath("//label[contains(@class,'error-messages')]"))
												.getText());

										// --Zoom In
										js.executeScript("document.body.style.zoom='100%';");
										Thread.sleep(2000);

										// --ZoneID
										String ZOneID = Driver
												.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]"))
												.getText();
										logger.info("ZoneID of is==" + ZOneID);
										if (ZOneID.equalsIgnoreCase("EDT")) {
											ZOneID = "America/New_York";
										} else if (ZOneID.equalsIgnoreCase("CDT")) {
											ZOneID = "CST";
										}

										// --Part Pull Date
										WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
										act.moveToElement(PartPullDate).build().perform();
										PartPullDate.clear();
										Date date = new Date();
										DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										logger.info(dateFormat.format(date));
										PartPullDate.sendKeys(dateFormat.format(date));
										PartPullDate.sendKeys(Keys.TAB);

										// --Part Pull Time
										WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
										act.moveToElement(PartPullTime).build().perform();
										PartPullTime.clear();
										date = new Date();
										dateFormat = new SimpleDateFormat("HH:mm");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										logger.info(dateFormat.format(date));
										PartPullTime.sendKeys(dateFormat.format(date));

										// --Zoom out
										js.executeScript("document.body.style.zoom='80%';");
										Thread.sleep(2000);

										// --Save button
										Save = Driver.findElement(By.id("idiconsave"));
										act.moveToElement(Save).build().perform();
										Save.click();
										logger.info("Clicked on Save button");

									} catch (Exception Time) {
										logger.info("Time validation is not displayed-Time is as per timeZone");
									}

									// --Zoom In
									js.executeScript("document.body.style.zoom='100%';");
									Thread.sleep(2000);

									// --Checking Error issue
									dbNullError();

									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
									PUID = getData("OrderProcessing", row1, 1);
									Driver.findElement(By.id("txtBasicSearch2")).clear();
									Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
									Driver.findElement(By.id("btnGXNLSearch2")).click();
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									try {
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
										WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
										if (NoData.isDisplayed()) {
											logger.info("Job is not moved to TENDER TO 3P stage successfully");
										}
									} catch (Exception e) {
										logger.info("Job is moved to TENDER TO 3P stage successfully");

										// --Enter Drop Time
										String ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
										logger.info("ZoneID of is==" + ZOneID);
										if (ZOneID.equalsIgnoreCase("EDT")) {
											ZOneID = "America/New_York";
										} else if (ZOneID.equalsIgnoreCase("CDT")) {
											ZOneID = "CST";
										}
										WebElement DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
										act.moveToElement(DropTime).build().perform();
										DropTime.clear();
										Date date = new Date();
										SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										logger.info(dateFormat.format(date));
										DropTime.sendKeys(dateFormat.format(date));

										// --Zoom out
										js.executeScript("document.body.style.zoom='80%';");
										Thread.sleep(2000);

										// --Click on Tender To 3P
										WebElement Delivery = Driver.findElement(By.id("btnsavedelivery"));
										act.moveToElement(Delivery).build().perform();
										Delivery.click();
										logger.info("Clicked on Tender to 3P");

										// --Zoom In
										js.executeScript("document.body.style.zoom='100%';");
										Thread.sleep(2000);
										try {
											wait.until(ExpectedConditions
													.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
											Driver.findElement(By.id("iddataok")).click();
											logger.info("Click on OK of Dialogue box");

											wait.until(ExpectedConditions.invisibilityOfElementLocated(
													By.xpath("//*[@class=\"ajax-loadernew\"]")));

										} catch (Exception SPickup) {
											logger.info("Dialogue is not present");
										}
										try {
											wait.until(ExpectedConditions
													.visibilityOfAllElementsLocatedBy(By.id("errorid")));
											String Validation = Driver.findElement(By.id("errorid")).getText();
											logger.info("Validation is displayed==" + Validation);

											Driver.findElement(By.id("btnclsdelivery")).click();
											logger.info("Click on Close button");

											wait.until(ExpectedConditions.invisibilityOfElementLocated(
													By.xpath("//*[@class=\"ajax-loadernew\"]")));

										} catch (Exception POD) {
											logger.info("Validation is not displayed for Package Tracking No");

										}
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.id("txtBasicSearch2")));
										PUID = getData("OrderProcessing", row1, 1);
										Driver.findElement(By.id("txtBasicSearch2")).clear();
										Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
										Driver.findElement(By.id("btnGXNLSearch2")).click();
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
										try {
											wait.until(ExpectedConditions
													.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
											WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
											if (NoData.isDisplayed()) {
												logger.info("Job is Delivered successfully");
											}
										} catch (Exception Deliver) {
											logger.info("Job is not Delivered successfully");
											Driver.findElement(By.id("btnclsdelivery")).click();
											logger.info("Click on Close button");

											wait.until(ExpectedConditions.invisibilityOfElementLocated(
													By.xpath("//*[@class=\"ajax-loadernew\"]")));

										}

									}
								} catch (Exception errmsg) {
									logger.info("Validation message is not displayed");
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
									PUID = getData("OrderProcessing", row1, 1);
									Driver.findElement(By.id("txtBasicSearch2")).clear();
									Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
									Driver.findElement(By.id("btnGXNLSearch2")).click();
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									try {
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
										WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
										if (NoData.isDisplayed()) {
											logger.info("Job is not moved to TENDER TO 3P stage successfully");
										}
									} catch (Exception e) {
										logger.info("Job is moved to TENDER TO 3P stage successfully");
									}
								}
							}

						} catch (Exception e) {
							logger.info("Spoke with validation is not displayed");
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							PUID = getData("OrderProcessing", row1, 1);
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							Driver.findElement(By.id("btnGXNLSearch2")).click();
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not moved to Confirm Pull stage successfully");
								}
							} catch (Exception Data) {
								logger.info("Job is moved to Confirm Pull successfully");
							}
						}

					} else if (Orderstage.contains("Confirm Pull")) {
						logger.info("Job is moved to Confirm Pull stage successfully");

						// --Confirm Pull stage
						String stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
						logger.info("stage=" + stage);
						getScreenshot(Driver, "ConfirmPull_" + PUID);
						// --Label generation
						WebElement LabelG = Driver.findElement(By.id("idiconprint"));
						act.moveToElement(LabelG).build().perform();
						LabelG.click();
						logger.info("Clicked on Label Generation");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						// Handle Label Generation window
						String WindowHandlebefore = Driver.getWindowHandle();
						for (String windHandle : Driver.getWindowHandles()) {
							Driver.switchTo().window(windHandle);
							logger.info("Switched to Label generation window");
							Thread.sleep(5000);
							getScreenshot(Driver, "Labelgeneration" + PUID);
						}
						Driver.close();
						logger.info("Closed Label generation window");

						Driver.switchTo().window(WindowHandlebefore);
						logger.info("Switched to main window");

						// --Save button
						WebElement Save = Driver.findElement(By.id("idiconsave"));
						act.moveToElement(Save).build().perform();
						Save.click();
						logger.info("Clicked on Save button");
						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.id("idPartPullDttmValidation")));
							String ValMsg = Driver.findElement(By.id("idPartPullDttmValidation")).getText();
							logger.info("Validation Message=" + ValMsg);

							// --ZoneID
							String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]"))
									.getText();
							logger.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							}

							// --Part Pull Date
							WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
							act.moveToElement(PartPullDate).build().perform();
							PartPullDate.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullDate.sendKeys(dateFormat.format(date));
							PartPullDate.sendKeys(Keys.TAB);

							// --Part Pull Time
							WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
							act.moveToElement(PartPullTime).build().perform();
							PartPullTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullTime.sendKeys(dateFormat.format(date));

							// --Zoom out
							js.executeScript("document.body.style.zoom='80%';");
							Thread.sleep(2000);

							// --Save button
							Save = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Save).build().perform();
							Save.click();
							logger.info("Clicked on Save button");
						} catch (Exception e) {
							logger.info("Validation Message is not displayed");
						}

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//label[contains(@class,'error-messages')]")));
							logger.info("ErroMsg is Displayed=" + Driver
									.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());
							Thread.sleep(2000);

							// --Zoom In
							js.executeScript("document.body.style.zoom='100%';");
							Thread.sleep(2000);

							// --Get the serial NO
							String SerialNo = Driver.findElement(By.xpath("//*[@ng-bind=\"segment.SerialNo\"]"))
									.getText();
							logger.info("Serial No of Part is==" + SerialNo + "\n");
							// enter serial number in scan
							WebElement SerialNoBar = Driver.findElement(By.id("txtBarcode"));
							act.moveToElement(SerialNoBar).build().perform();
							SerialNoBar.clear();
							Driver.findElement(By.id("txtBarcode")).sendKeys(SerialNo);
							Driver.findElement(By.id("txtBarcode")).sendKeys(Keys.TAB);
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							logger.info("Entered serial No in scan barcode");

							// --Zoom out
							js.executeScript("document.body.style.zoom='80%';");
							Thread.sleep(2000);

							// --Save button
							Save = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Save).build().perform();
							Save.click();
							logger.info("Clicked on Save button");

							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(
										By.xpath("//label[contains(@class,'error-messages')]")));
								logger.info("ErroMsg is Displayed=" + Driver
										.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());

								// --Zoom In
								js.executeScript("document.body.style.zoom='100%';");
								Thread.sleep(2000);

								// --ZoneID
								String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]"))
										.getText();
								logger.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}

								// --Part Pull Date
								WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
								act.moveToElement(PartPullDate).build().perform();
								PartPullDate.clear();
								Date date = new Date();
								DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullDate.sendKeys(dateFormat.format(date));
								PartPullDate.sendKeys(Keys.TAB);

								// --Part Pull Time
								WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
								act.moveToElement(PartPullTime).build().perform();
								PartPullTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullTime.sendKeys(dateFormat.format(date));

								// --Zoom out
								js.executeScript("document.body.style.zoom='80%';");
								Thread.sleep(2000);

								// --Save button
								Save = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Save).build().perform();
								Save.click();
								logger.info("Clicked on Save button");

							} catch (Exception Time) {
								logger.info("Time validation is not displayed-Time is as per timeZone");
							}

							// --Zoom In
							js.executeScript("document.body.style.zoom='100%';");
							Thread.sleep(2000);

							// --Checking Error issue
							dbNullError();

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							PUID = getData("OrderProcessing", row1, 1);
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							Driver.findElement(By.id("btnGXNLSearch2")).click();
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not moved to TENDER TO 3P stage successfully");
								}
							} catch (Exception e) {
								logger.info("Job is moved to TENDER TO 3P stage successfully");

								// --Enter Drop Time
								String ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
								logger.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}
								WebElement DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
								act.moveToElement(DropTime).build().perform();
								DropTime.clear();
								Date date = new Date();
								SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								DropTime.sendKeys(dateFormat.format(date));

								// --Zoom out
								js.executeScript("document.body.style.zoom='80%';");
								Thread.sleep(2000);

								// --Click on Tender To 3P
								WebElement Delivery = Driver.findElement(By.id("btnsavedelivery"));
								act.moveToElement(Delivery).build().perform();
								Delivery.click();
								logger.info("Clicked on Tender to 3P");

								// --Zoom In
								js.executeScript("document.body.style.zoom='100%';");
								Thread.sleep(2000);
								try {
									wait.until(ExpectedConditions
											.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
									Driver.findElement(By.id("iddataok")).click();
									logger.info("Click on OK of Dialogue box");

									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								} catch (Exception SPickup) {
									logger.info("Dialogue is not present");
								}
								try {
									wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("errorid")));
									String Validation = Driver.findElement(By.id("errorid")).getText();
									logger.info("Validation is displayed==" + Validation);

									Driver.findElement(By.id("btnclsdelivery")).click();
									logger.info("Click on Close button");

									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								} catch (Exception POD) {
									logger.info("Validation is not displayed for Package Tracking No");

								}
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
								PUID = getData("OrderProcessing", row1, 1);
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								Driver.findElement(By.id("btnGXNLSearch2")).click();
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										logger.info("Job is Delivered successfully");
									}
								} catch (Exception Deliver) {
									logger.info("Job is not Delivered successfully");
									Driver.findElement(By.id("btnclsdelivery")).click();
									logger.info("Click on Close button");

									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								}

							}
						} catch (Exception errmsg) {
							logger.info("Validation message is not displayed");
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							PUID = getData("OrderProcessing", row1, 1);
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							Driver.findElement(By.id("btnGXNLSearch2")).click();
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not moved to TENDER TO 3P stage successfully");
								}
							} catch (Exception e) {
								logger.info("Job is moved to TENDER TO 3P stage successfully");
							}
						}
					} else if (Orderstage.contains("Pull Inventory and Apply Return Pack(s)")) {
						// --Pull Inventory and Apply Return Pack(s) stage
						String stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
						logger.info("stage=" + stage);
						getScreenshot(Driver, "PullInventoryandApplyReturnPack(s)_" + PUID);
						// --Label generation
						// --Label generation
						WebElement LabelG = Driver.findElement(By.id("idiconprint"));
						act.moveToElement(LabelG).build().perform();
						LabelG.click();
						logger.info("Clicked on Label Generation");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						// Handle Label Generation window
						String WindowHandlebefore = Driver.getWindowHandle();
						for (String windHandle : Driver.getWindowHandles()) {
							Driver.switchTo().window(windHandle);
							logger.info("Switched to Label generation window");
							Thread.sleep(5000);
							getScreenshot(Driver, "Labelgeneration" + PUID);
						}
						Driver.close();
						logger.info("Closed Label generation window");

						Driver.switchTo().window(WindowHandlebefore);
						logger.info("Switched to main window");

						// --Zoom out
						js.executeScript("document.body.style.zoom='80%';");
						Thread.sleep(2000);

						// --Save button
						WebElement Save = Driver.findElement(By.id("idiconsave"));
						act.moveToElement(Save).build().perform();
						Save.click();
						logger.info("Clicked on Save button");
						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.id("idPartPullDttmValidation")));
							String ValMsg = Driver.findElement(By.id("idPartPullDttmValidation")).getText();
							logger.info("Validation Message=" + ValMsg);

							// --Zoom out
							js.executeScript("document.body.style.zoom='100%';");
							Thread.sleep(2000);

							// --ZoneID
							String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]"))
									.getText();
							logger.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							}

							// --Part Pull Date
							WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
							act.moveToElement(PartPullDate).build().perform();
							PartPullDate.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullDate.sendKeys(dateFormat.format(date));
							PartPullDate.sendKeys(Keys.TAB);

							// --Part Pull Time
							WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
							act.moveToElement(PartPullTime).build().perform();
							PartPullTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullTime.sendKeys(dateFormat.format(date));

							// --Zoom out
							js.executeScript("document.body.style.zoom='80%';");
							Thread.sleep(2000);

							// --Save button
							Save = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Save).build().perform();
							Save.click();
							logger.info("Clicked on Save button");

							// --Zoom out
							js.executeScript("document.body.style.zoom='100%';");
							Thread.sleep(2000);

						} catch (Exception e) {
							logger.info("Validation Message is not displayed");
						}
						try {
							// --Zoom out
							js.executeScript("document.body.style.zoom='80%';");
							Thread.sleep(2000);

							wait.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//label[contains(@class,'error-messages')]")));
							logger.info("ErroMsg is Displayed=" + Driver
									.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());
							Thread.sleep(2000);

							// --Zoom out
							js.executeScript("document.body.style.zoom='100%';");
							Thread.sleep(2000);

							// --Get the serial NO
							String SerialNo = Driver.findElement(By.xpath("//*[@ng-bind=\"segment.SerialNo\"]"))
									.getText();
							logger.info("Serial No of Part is==" + SerialNo + "\n");
							// enter serial number in scan
							WebElement SerialNoBar = Driver.findElement(By.id("txtBarcode"));
							act.moveToElement(SerialNoBar).build().perform();
							SerialNoBar.clear();
							Driver.findElement(By.id("txtBarcode")).sendKeys(SerialNo);
							Driver.findElement(By.id("txtBarcode")).sendKeys(Keys.TAB);
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							logger.info("Entered serial No in scan barcode");

							// --Zoom out
							js.executeScript("document.body.style.zoom='80%';");
							Thread.sleep(2000);

							// --Save button
							Save = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Save).build().perform();
							Save.click();
							logger.info("Clicked on Save button");

							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(
										By.xpath("//label[contains(@class,'error-messages')]")));
								logger.info("ErroMsg is Displayed=" + Driver
										.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());
								// --Zoom out
								js.executeScript("document.body.style.zoom='100%';");
								Thread.sleep(2000);

								// --ZoneID
								String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]"))
										.getText();
								logger.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}

								// --Part Pull Date
								WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
								act.moveToElement(PartPullDate).build().perform();
								PartPullDate.clear();
								Date date = new Date();
								DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullDate.sendKeys(dateFormat.format(date));
								PartPullDate.sendKeys(Keys.TAB);

								// --Part Pull Time
								WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
								act.moveToElement(PartPullTime).build().perform();
								PartPullTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullTime.sendKeys(dateFormat.format(date));

								// --Zoom out
								js.executeScript("document.body.style.zoom='80%';");
								Thread.sleep(2000);

								// --Save button
								Save = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Save).build().perform();
								Save.click();
								logger.info("Clicked on Save button");

								// --Zoom out
								js.executeScript("document.body.style.zoom='100%';");
								Thread.sleep(2000);

							} catch (Exception Time) {
								logger.info("Time validation is not displayed-Time is as per timeZone");
							}
							// --Zoom out
							js.executeScript("document.body.style.zoom='100%';");
							Thread.sleep(2000);

							// --Checking Error issue
							dbNullError();

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							PUID = getData("OrderProcessing", row1, 1);
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							Driver.findElement(By.id("btnGXNLSearch2")).click();
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not moved to TENDER TO 3P stage successfully");
								}
							} catch (Exception e) {
								logger.info("Job is moved to TENDER TO 3P stage successfully");

								// --Enter Drop Time
								String ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
								logger.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}
								WebElement DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
								act.moveToElement(DropTime).build().perform();
								DropTime.clear();
								Date date = new Date();
								SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								DropTime.sendKeys(dateFormat.format(date));

								// --Zoom out
								js.executeScript("document.body.style.zoom='80%';");
								Thread.sleep(2000);

								// --Click on Tender To 3P
								WebElement Delivery = Driver.findElement(By.id("btnsavedelivery"));
								act.moveToElement(Delivery).build().perform();
								Delivery.click();
								logger.info("Clicked on Tender to 3P");

								// --Zoom In
								js.executeScript("document.body.style.zoom='100%';");
								Thread.sleep(2000);
								try {
									wait.until(ExpectedConditions
											.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
									Driver.findElement(By.id("iddataok")).click();
									logger.info("Click on OK of Dialogue box");

									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								} catch (Exception SPickup) {
									logger.info("Dialogue is not present");
								}
								try {
									wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("errorid")));
									String Validation = Driver.findElement(By.id("errorid")).getText();
									logger.info("Validation is displayed==" + Validation);

									Driver.findElement(By.id("btnclsdelivery")).click();
									logger.info("Click on Close button");

									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								} catch (Exception POD) {
									logger.info("Validation is not displayed for Package Tracking No");

								}
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
								PUID = getData("OrderProcessing", row1, 1);
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								Driver.findElement(By.id("btnGXNLSearch2")).click();
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										logger.info("Job is Delivered successfully");
									}
								} catch (Exception Deliver) {
									logger.info("Job is not Delivered successfully");
									Driver.findElement(By.id("btnclsdelivery")).click();
									logger.info("Click on Close button");

									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								}

							}
						} catch (Exception errmsg) {
							logger.info("Validation message is not displayed");
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							PUID = getData("OrderProcessing", row1, 1);
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							Driver.findElement(By.id("btnGXNLSearch2")).click();
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not moved to TENDER TO 3P stage successfully");
								}
							} catch (Exception e) {
								logger.info("Job is moved to TENDER TO 3P stage successfully");
							}
						}
					} else if (Orderstage.contains("Tender to 3P")) {
						logger.info("Job is moved to TENDER TO 3P stage successfully");

						// --Enter Drop Time
						String ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
						logger.info("ZoneID of is==" + ZOneID);
						if (ZOneID.equalsIgnoreCase("EDT")) {
							ZOneID = "America/New_York";
						} else if (ZOneID.equalsIgnoreCase("CDT")) {
							ZOneID = "CST";
						}
						WebElement DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
						act.moveToElement(DropTime).build().perform();
						DropTime.clear();
						Date date = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logger.info(dateFormat.format(date));
						DropTime.sendKeys(dateFormat.format(date));

						// --Zoom out
						js.executeScript("document.body.style.zoom='80%';");
						Thread.sleep(2000);

						// --Click on Tender To 3P
						WebElement Delivery = Driver.findElement(By.id("btnsavedelivery"));
						act.moveToElement(Delivery).build().perform();
						Delivery.click();
						logger.info("Clicked on Tender to 3P");

						// --Zoom In
						js.executeScript("document.body.style.zoom='100%';");
						Thread.sleep(2000);
						try {
							wait.until(
									ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
							Driver.findElement(By.id("iddataok")).click();
							logger.info("Click on OK of Dialogue box");

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception SPickup) {
							logger.info("Dialogue is not present");
						}
						try {
							wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("errorid")));
							String Validation = Driver.findElement(By.id("errorid")).getText();
							logger.info("Validation is displayed==" + Validation);

							Driver.findElement(By.id("btnclsdelivery")).click();
							logger.info("Click on Close button");

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception POD) {
							logger.info("Validation is not displayed for Package Tracking No");

						}
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						PUID = getData("OrderProcessing", row1, 1);
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						Driver.findElement(By.id("btnGXNLSearch2")).click();
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is Delivered successfully");
							}
						} catch (Exception Deliver) {
							logger.info("Job is not Delivered successfully");
							Driver.findElement(By.id("btnclsdelivery")).click();
							logger.info("Click on Close button");

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						}

					} else {
						logger.info("unknown stage found for H3P service");
					}
				} else if (ServiceID.contains("RTE")) {
					// --Click on RTE tab
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-model=\"RTE\"]")));
					Driver.findElement(By.xpath("//*[@ng-model=\"RTE\"]")).click();
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RTE")));
					getScreenshot(Driver, "RTETab");
					// --Search
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearchRTE")));
					Driver.findElement(By.id("txtBasicSearchRTE")).clear();
					Driver.findElement(By.id("txtBasicSearchRTE")).sendKeys(PUID);
					Driver.findElement(By.id("btnRTESearch2")).click();
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
					if (NoData.isDisplayed()) {
						logger.info("Record is not available with search parameters");
					} else {
						logger.info("Record is available with search parameters");
						// --click on record
						Driver.findElement(By.xpath("//*[@id=\"idRTEList\"]//span/strong")).click();
						logger.info("Clicked on the record");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblRWID")));
						String Orderstage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]"))
								.getText();
						logger.info("Current stage of the order is=" + Orderstage);

						getScreenshot(Driver, Orderstage + PUID);

						if (Orderstage.contains("Confirm Alert")) {
							// --Confirm Alert stage
							// --Click on Accept
							WebElement Accept = Driver.findElement(By.id("idiconaccept"));
							act.moveToElement(Accept).build().perform();
							Accept.click();
							logger.info("Clicked on Accept");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

							// ---Pickup@Stop 1 of 2 stage
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearchRTE")));
							Driver.findElement(By.id("txtBasicSearchRTE")).clear();
							Driver.findElement(By.id("txtBasicSearchRTE")).sendKeys(PUID);
							Driver.findElement(By.id("btnRTESearch2")).click();
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Record is not available with search parameters");
							} else {
								logger.info("Record is available with search parameters");
								// --Again click on Record
								Driver.findElement(By.xpath("//*[@id=\"idRTEList\"]//span/strong")).click();
								logger.info("Clicked on the record");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblRWID")));
								Orderstage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]"))
										.getText();
								logger.info("Current stage of the order is=" + Orderstage);

								getScreenshot(Driver, Orderstage + PUID);
								// --Click on save
								WebElement Save = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Save).build().perform();
								Save.click();
								logger.info("Clicked on the Save");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
									String Errmsg = Driver.findElement(By.id("errorid")).getText();
									logger.info("validation message=" + Errmsg);
									// --Enter Actual PickupTime
									String ZOneID = Driver
											.findElement(By.xpath("//span[contains(@ng-bind,'PUTimeZone')]")).getText();
									logger.info("ZoneID of is==" + ZOneID);
									if (ZOneID.equalsIgnoreCase("EDT")) {
										ZOneID = "America/New_York";
									} else if (ZOneID.equalsIgnoreCase("CDT")) {
										ZOneID = "CST";
									}
									WebElement ActPUTime = Driver.findElement(By.id("txtActPuTime"));
									ActPUTime.clear();
									Date date = new Date();
									SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									ActPUTime.sendKeys(dateFormat.format(date));
									// --Click on save
									Save = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Save).build().perform();
									Save.click();
									logger.info("Clicked on the Save");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								} catch (Exception e) {
									logger.info("validation message is not displayed");
								}

								// ---DEL@Stop 2 of 2 stage
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearchRTE")));
								Driver.findElement(By.id("txtBasicSearchRTE")).clear();
								Driver.findElement(By.id("txtBasicSearchRTE")).sendKeys(PUID);
								Driver.findElement(By.id("btnRTESearch2")).click();
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Record is not available with search parameters");
								} else {
									logger.info("Record is available with search parameters");
									// --Again click on Record
									Driver.findElement(By.xpath("//*[@id=\"idRTEList\"]//span/strong")).click();
									logger.info("Clicked on the record");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblRWID")));
									Orderstage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]"))
											.getText();
									logger.info("Current stage of the order is=" + Orderstage);

									getScreenshot(Driver, Orderstage + PUID);
									// --Click on save
									Save = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Save).build().perform();
									Save.click();
									logger.info("Clicked on the Save");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									try {
										wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
										String Errmsg = Driver.findElement(By.id("errorid")).getText();
										logger.info("validation message=" + Errmsg);
										// --Enter Actual DeliverTime
										String ZOneID = Driver
												.findElement(By.xpath("//span[contains(@ng-bind,'PUTimeZone')]"))
												.getText();
										logger.info("ZoneID of is==" + ZOneID);
										if (ZOneID.equalsIgnoreCase("EDT")) {
											ZOneID = "America/New_York";
										} else if (ZOneID.equalsIgnoreCase("CDT")) {
											ZOneID = "CST";
										}
										WebElement ActDelTime = Driver.findElement(By.id("txtActDlTime"));
										ActDelTime.clear();
										Date date = new Date();
										SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										logger.info(dateFormat.format(date));
										ActDelTime.sendKeys(dateFormat.format(date));
										// --Click on save
										Save = Driver.findElement(By.id("idiconsave"));
										act.moveToElement(Save).build().perform();
										Save.click();
										logger.info("Clicked on the Save");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
										try {
											wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
											Errmsg = Driver.findElement(By.id("errorid")).getText();
											logger.info("validation message=" + Errmsg);
											// --Enter Signature
											Driver.findElement(By.id("txtsign")).sendKeys("RV");
											logger.info("Entered Signature");
											// --Click on save
											Save = Driver.findElement(By.id("idiconsave"));
											act.moveToElement(Save).build().perform();
											Save.click();
											logger.info("Clicked on the Save");
											wait.until(ExpectedConditions.invisibilityOfElementLocated(
													By.xpath("//*[@class=\"ajax-loadernew\"]")));
										} catch (Exception e) {
											logger.info("validation message is not displayed");
										}
									} catch (Exception e) {
										logger.info("validation message is not displayed");
									}
									// --Search the job
									wait.until(
											ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearchRTE")));
									Driver.findElement(By.id("txtBasicSearchRTE")).clear();
									Driver.findElement(By.id("txtBasicSearchRTE")).sendKeys(PUID);
									Driver.findElement(By.id("btnRTESearch2")).click();
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									try {
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
										NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
										if (NoData.isDisplayed()) {
											logger.info("Job is not Delivered");
										}
									} catch (Exception e) {
										// --Again click on Record
										Driver.findElement(By.xpath("//*[@id=\"idRTEList\"]//span/strong")).click();
										logger.info("Clicked on the record");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
										wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblRWID")));
										Orderstage = Driver
												.findElement(By.xpath("//h3[contains(@class,'panel-title')]"))
												.getText();
										logger.info("Current stage of the order is=" + Orderstage);

										if (Orderstage.equalsIgnoreCase("Delivered@Stop 2 of 2")) {
											logger.info("Job is delivered");
											// --Close button
											Driver.findElement(By.id("idiconclose")).click();
											logger.info("Clicked on Close button");
											wait.until(ExpectedConditions.invisibilityOfElementLocated(
													By.xpath("//*[@class=\"ajax-loadernew\"]")));

										} else {
											logger.info("Job is not delivered");

										}

									}
								}
							}
						} else if (Orderstage.contains("Pickup@Stop 1 of 2")) {
							// ---Pickup@Stop 1 of 2 stage
							// --Click on save
							WebElement Save = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Save).build().perform();
							Save.click();
							logger.info("Clicked on the Save");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
								String Errmsg = Driver.findElement(By.id("errorid")).getText();
								logger.info("validation message=" + Errmsg);
								// --Enter Actual PickupTime
								String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'PUTimeZone')]"))
										.getText();
								logger.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}
								WebElement ActPUTime = Driver.findElement(By.id("txtActPuTime"));
								ActPUTime.clear();
								Date date = new Date();
								SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								ActPUTime.sendKeys(dateFormat.format(date));
								// --Click on save
								Save = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Save).build().perform();
								Save.click();
								logger.info("Clicked on the Save");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							} catch (Exception e) {
								logger.info("validation message is not displayed");
							}

							// ---DEL@Stop 2 of 2 stage
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearchRTE")));
							Driver.findElement(By.id("txtBasicSearchRTE")).clear();
							Driver.findElement(By.id("txtBasicSearchRTE")).sendKeys(PUID);
							Driver.findElement(By.id("btnRTESearch2")).click();
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Record is not available with search parameters");
							} else {
								logger.info("Record is available with search parameters");
								// --Again click on Record
								Driver.findElement(By.xpath("//*[@id=\"idRTEList\"]//span/strong")).click();
								logger.info("Clicked on the record");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblRWID")));
								Orderstage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]"))
										.getText();
								logger.info("Current stage of the order is=" + Orderstage);

								getScreenshot(Driver, Orderstage + PUID);
								// --Click on save
								Save = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Save).build().perform();
								Save.click();
								logger.info("Clicked on the Save");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
									String Errmsg = Driver.findElement(By.id("errorid")).getText();
									logger.info("validation message=" + Errmsg);
									// --Enter Actual DeliverTime
									String ZOneID = Driver
											.findElement(By.xpath("//span[contains(@ng-bind,'PUTimeZone')]")).getText();
									logger.info("ZoneID of is==" + ZOneID);
									if (ZOneID.equalsIgnoreCase("EDT")) {
										ZOneID = "America/New_York";
									} else if (ZOneID.equalsIgnoreCase("CDT")) {
										ZOneID = "CST";
									}
									WebElement ActDelTime = Driver.findElement(By.id("txtActDlTime"));
									ActDelTime.clear();
									Date date = new Date();
									SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									ActDelTime.sendKeys(dateFormat.format(date));
									// --Click on save
									Save = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Save).build().perform();
									Save.click();
									logger.info("Clicked on the Save");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									try {
										wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
										Errmsg = Driver.findElement(By.id("errorid")).getText();
										logger.info("validation message=" + Errmsg);
										// --Enter Signature
										Driver.findElement(By.id("txtsign")).sendKeys("RV");
										logger.info("Entered Signature");
										// --Click on save
										Save = Driver.findElement(By.id("idiconsave"));
										act.moveToElement(Save).build().perform();
										Save.click();
										logger.info("Clicked on the Save");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
									} catch (Exception e) {
										logger.info("validation message is not displayed");
									}
								} catch (Exception e) {
									logger.info("validation message is not displayed");
								}
								// --Search the job
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearchRTE")));
								Driver.findElement(By.id("txtBasicSearchRTE")).clear();
								Driver.findElement(By.id("txtBasicSearchRTE")).sendKeys(PUID);
								Driver.findElement(By.id("btnRTESearch2")).click();
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										logger.info("Job is not Delivered");
									}
								} catch (Exception e) {
									// --Again click on Record
									Driver.findElement(By.xpath("//*[@id=\"idRTEList\"]//span/strong")).click();
									logger.info("Clicked on the record");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblRWID")));
									Orderstage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]"))
											.getText();
									logger.info("Current stage of the order is=" + Orderstage);

									if (Orderstage.equalsIgnoreCase("Delivered@Stop 2 of 2")) {
										logger.info("Job is delivered");

										// --Close button
										Driver.findElement(By.id("idiconclose")).click();
										logger.info("Clicked on Close button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));

									} else {
										logger.info("Job is not delivered");

									}

								}
							}

						} else if (Orderstage.contains("DEL@Stop 2 of 2")) {
							// ---DEL@Stop 2 of 2 stage

							// --Click on save
							WebElement Save = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Save).build().perform();
							Save.click();
							logger.info("Clicked on the Save");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
								String Errmsg = Driver.findElement(By.id("errorid")).getText();
								logger.info("validation message=" + Errmsg);
								// --Enter Actual DeliverTime
								String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'PUTimeZone')]"))
										.getText();
								logger.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}
								WebElement ActDelTime = Driver.findElement(By.id("txtActDlTime"));
								ActDelTime.clear();
								Date date = new Date();
								SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								ActDelTime.sendKeys(dateFormat.format(date));
								// --Click on save
								Save = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Save).build().perform();
								Save.click();
								logger.info("Clicked on the Save");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
									Errmsg = Driver.findElement(By.id("errorid")).getText();
									logger.info("validation message=" + Errmsg);
									// --Enter Signature
									Driver.findElement(By.id("txtsign")).sendKeys("RV");
									logger.info("Entered Signature");
									// --Click on save
									Save = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Save).build().perform();
									Save.click();
									logger.info("Clicked on the Save");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								} catch (Exception e) {
									logger.info("validation message is not displayed");
								}
							} catch (Exception e) {
								logger.info("validation message is not displayed");
							}
							// --Search the job
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearchRTE")));
							Driver.findElement(By.id("txtBasicSearchRTE")).clear();
							Driver.findElement(By.id("txtBasicSearchRTE")).sendKeys(PUID);
							Driver.findElement(By.id("btnRTESearch2")).click();
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not Delivered");
								}
							} catch (Exception e) {
								// --Again click on Record
								Driver.findElement(By.xpath("//*[@id=\"idRTEList\"]//span/strong")).click();
								logger.info("Clicked on the record");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblRWID")));
								Orderstage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]"))
										.getText();
								logger.info("Current stage of the order is=" + Orderstage);

								if (Orderstage.equalsIgnoreCase("Delivered@Stop 2 of 2")) {
									logger.info("Job is delivered");
									// --Close button
									Driver.findElement(By.id("idiconclose")).click();
									logger.info("Clicked on Close button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								} else {
									logger.info("Job is not delivered");

								}

							}

						} else if (Orderstage.contains("Delivered@Stop 2 of 2")) {
							logger.info("Job is already delivered");
							// --Close button
							Driver.findElement(By.id("idiconclose")).click();
							logger.info("Clicked on Close button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} else {
							logger.info("Unknown stage found");
						}
					}
				} else if (ServiceID.contains("Replenish")) {
					// --Click on Inventory tab
					Driver.findElement(By.id("inventory")).click();
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("WO")));
					getScreenshot(Driver, "InventoryTab");
					String TotalJob = Driver.findElement(By.xpath("//*[@ng-bind=\"TotalJob\"]")).getText();
					logger.info("Total No of job in Inventory Tab is/are==" + TotalJob);
					// --Search
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
					Driver.findElement(By.id("txtBasicSearch")).clear();
					Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
					Driver.findElement(By.id("btnSearch2")).click();
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
						WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
						if (NoData.isDisplayed()) {
							logger.info("Record is not available with search parameters");
						}
					} catch (Exception NoData) {
						logger.info("Record is available with search parameters");
						// --click on record
						wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("idparttable")));
						String Orderstage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]"))
								.getText();
						logger.info("Current stage of the order is=" + Orderstage);

						getScreenshot(Driver, Orderstage + PUID);
						// --Click on Update
						Driver.findElement(By.id("idupdateicon")).click();
						logger.info("Clicked on the Update");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						try {
							wait.until(
									ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("fslavailableloclist")));
							logger.info("Alert is displayed for create sub ASN");
							// --ALert
							// --Close the pop up
							Driver.findElement(By.id("btnCancel")).click();
							logger.info("Click on Close PopUp");
						} catch (Exception e) {
							logger.info("Alert is not displayed for create sub ASN");
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
							String ValMsg = Driver.findElement(By.id("errorid")).getText();
							logger.info("Validation Msg==" + ValMsg);
						}
						// --Click on Save
						Driver.findElement(By.id("idsaveicon")).click();
						logger.info("Clicked on the Save");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						// --table
						WebElement parttable = Driver.findElement(By.xpath("//*[@id=\"parttable\"]/tbody"));
						List<WebElement> Partrow = parttable.findElements(By.tagName("tr"));
						logger.info("total No of rows in part table are==" + Partrow.size());

						for (int part = 0; part < Partrow.size(); part++) {
							// --Find SerialNo column
							try {
								WebElement SerialNo = Partrow.get(part).findElement(By.id("txtSerialNo"));
								act.moveToElement(SerialNo).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(SerialNo));
								SerialNo.clear();
								SerialNo.sendKeys("SerialNo" + part);
								logger.info("Entered serial Number in " + part + " part");

								// --Enter Accepted Quantity
								WebElement AccQty = Partrow.get(part).findElement(By.id("txtReceivedQty"));
								act.moveToElement(AccQty).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(AccQty));
								AccQty.clear();
								AccQty.sendKeys("1");
								AccQty.sendKeys(Keys.TAB);
								logger.info("Entered Accepted Quantity in " + part + " part");
								// --Click on Save
								wait.until(ExpectedConditions.elementToBeClickable(By.id("idsaveicon")));
								WebElement Save = Driver.findElement(By.id("idsaveicon"));
								act.moveToElement(Save).click().perform();
								logger.info("Clicked on the Save");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							} catch (Exception staleelement) {
								try {
									WebElement parttable1 = Driver
											.findElement(By.xpath("//*[@id=\"parttable\"]/tbody"));
									List<WebElement> Partrow1 = parttable1.findElements(By.tagName("tr"));
									logger.info("total No of rows in part table are==" + Partrow.size());
									for (int partR = part; partR < Partrow1.size();) {
										WebElement SerialNo = Partrow1.get(partR).findElement(By.id("txtSerialNo"));
										act.moveToElement(SerialNo).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(SerialNo));
										SerialNo.clear();
										SerialNo.sendKeys("SerialNo" + part);
										logger.info("Entered serial Number in " + part + " part");

										// --Enter Accepted Quantity
										WebElement AccQty = Partrow1.get(partR).findElement(By.id("txtReceivedQty"));
										act.moveToElement(AccQty).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(AccQty));
										AccQty.clear();
										AccQty.sendKeys("1");
										AccQty.sendKeys(Keys.TAB);
										logger.info("Entered Accepted Quantity in " + part + " part");
										// --Click on Save
										wait.until(ExpectedConditions.elementToBeClickable(By.id("idsaveicon")));
										WebElement Save = Driver.findElement(By.id("idsaveicon"));
										act.moveToElement(Save).click().perform();
										logger.info("Clicked on the Save");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
										break;
									}
								} catch (Exception StaleElement) {
									WebElement parttable1 = Driver
											.findElement(By.xpath("//*[@id=\"parttable\"]/tbody"));
									List<WebElement> Partrow1 = parttable1.findElements(By.tagName("tr"));
									logger.info("total No of rows in part table are==" + Partrow.size());
									for (int partRw = part; partRw < Partrow1.size();) {
										WebElement SerialNo = Partrow1.get(partRw).findElement(By.id("txtSerialNo"));
										act.moveToElement(SerialNo).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(SerialNo));
										SerialNo.clear();
										SerialNo.sendKeys("SerialNo" + part);
										logger.info("Entered serial Number in " + part + " part");

										// --Enter Accepted Quantity
										WebElement AccQty = Partrow1.get(partRw).findElement(By.id("txtReceivedQty"));
										act.moveToElement(AccQty).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(AccQty));
										AccQty.clear();
										AccQty.sendKeys("1");
										AccQty.sendKeys(Keys.TAB);
										logger.info("Entered Accepted Quantity in " + part + " part");
										// --Click on Save
										wait.until(ExpectedConditions.elementToBeClickable(By.id("idsaveicon")));
										WebElement Save = Driver.findElement(By.id("idsaveicon"));
										act.moveToElement(Save).click().perform();
										logger.info("Clicked on the Save");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
										break;
									}
								}
							}
						}
						// --Click on Update

						wait.until(ExpectedConditions.elementToBeClickable(By.id("idupdateicon")));
						WebElement update = Driver.findElement(By.id("idupdateicon"));
						act.moveToElement(update).click().perform();
						logger.info("Clicked on the update");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successid")));
							String SuccMsg = Driver.findElement(By.id("successid")).getText();
							logger.info("Success Message==" + SuccMsg);

							// --Binless label
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idBinlessLabelGenerate")));
							Driver.findElement(By.id("idBinlessLabelGenerate")).click();
							logger.info("Clicked on BinLess Label");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
							String ErrMsg = Driver.findElement(By.id("errorid")).getText();
							logger.info("Error Message==" + ErrMsg);

							// --Print Label
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idLabelGenerate")));
							Driver.findElement(By.id("idLabelGenerate")).click();
							logger.info("Clicked on Print Label");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							// --Handle Print label window
							String WindowHandlebefore = Driver.getWindowHandle();
							for (String windHandle : Driver.getWindowHandles()) {
								Driver.switchTo().window(windHandle);
								logger.info("Switched to Print Label window");
								Thread.sleep(5000);
								getScreenshot(Driver, "PrintLabel_" + PUID);

							}
							Driver.close();
							logger.info("Closed Print Label window");

							Driver.switchTo().window(WindowHandlebefore);
							logger.info("Switched to main window");

							// --Close button
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idiconclose")));
							Driver.findElement(By.id("idiconclose")).click();
							logger.info("Clicked on Close button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception sMsg) {
							logger.info(" Data is not Saved Successfully");
							// --Close button
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idiconclose")));
							Driver.findElement(By.id("idiconclose")).click();
							logger.info("Clicked on Close button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						}
						// --Search
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
						Driver.findElement(By.id("txtBasicSearch")).clear();
						Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
						Driver.findElement(By.id("btnSearch2")).click();
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData1 = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData1.isDisplayed()) {
								logger.info("Order is Replenished");
							}
						} catch (Exception Data) {
							logger.info("Order is not Replenished");
						}
					}

				} else if (ServiceID.contains("3PLAST")) {
					logger.info("If ServiceID is 3PLAST....");
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					Driver.findElement(By.id("txtBasicSearch2")).clear();
					logger.info("Clear search input");
					Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
					logger.info("Enter PickUpID in Search input");
					Driver.findElement(By.id("btnGXNLSearch2")).click();
					logger.info("Click on Search button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					// --Memo
					// memo(PUID);

					// -Notification
					// notification(PUID);

					// Upload
					// upload(PUID);

					// Ship Label Services

					// --Print pull Ticket

					// --Get current stage of the order
					String Orderstage = null;
					try {
						String Orderstage1 = Driver.findElement(By.xpath("//div/h3[contains(@class,\"ng-binding\")]"))
								.getText();
						Orderstage = Orderstage1;
						logger.info("Current stage of the order is=" + Orderstage);
					} catch (Exception stage) {
						try {
							String Orderstage2 = Driver.findElement(By.xpath("//Strong/span[@class=\"ng-binding\"]"))
									.getText();
							Orderstage = Orderstage2;
							logger.info("Current stage of the order is=" + Orderstage);
						} catch (Exception SName) {
							String StageName = Driver.findElement(By.xpath("//*[@ng-if=\"ConfirmPickupPage\"]"))
									.getText();
							Orderstage = StageName;
							logger.info("Stage is==" + StageName);
						}
					}
					if (Orderstage.equalsIgnoreCase("CONF PULL ALERT")) {
						logger.info("Current stage of the order is=" + Orderstage);

						// --Click on Accept button //
						Driver.findElement(By.id("lnkcnfpull")).click();
						logger.info("Click on Accept button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						WebElement SpokeWithVal = Driver.findElement(By.id("idValidation"));
						if (SpokeWithVal.isDisplayed()) {
							logger.info("Spoke with validation is displayed==" + SpokeWithVal.getText());

						} else {
							logger.info("Spoke with validation is not displayed");

						}
						// --Spoke with
						Driver.findElement(By.id("idConfPullAlertForm")).sendKeys("RV");
						logger.info("Enter Spoke with");

						// --Click on Accept button //
						Driver.findElement(By.id("lnkcnfpull")).click();
						logger.info("Click on Accept button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						// --Search again
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						PUID = getData("OrderProcessing", row1, 1);
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						Driver.findElement(By.id("btnGXNLSearch2")).click();
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is not moved to Confirm Pull stage successfully");
							}
						} catch (Exception e1) {
							logger.info("Job is moved to Confirm Pull stage successfully");

							// --Confirm Pull
							String stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]"))
									.getText();
							logger.info("stage=" + stage);
							logger.info("If order stage is Confirm Pull.....");
							getScreenshot(Driver, "ConfirmPull_" + PUID);
							// --Label generation
							Driver.findElement(By.id("idiconprint")).click();
							logger.info("Clicked on Label Generation");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							// Handle Label Generation window
							String WindowHandlebefore = Driver.getWindowHandle();
							for (String windHandle : Driver.getWindowHandles()) {
								Driver.switchTo().window(windHandle);
								logger.info("Switched to Label generation window");
								Thread.sleep(5000);
								getScreenshot(Driver, "Labelgeneration" + PUID);
							}
							Driver.close();
							logger.info("Closed Label generation window");

							Driver.switchTo().window(WindowHandlebefore);
							logger.info("Switched to main window");

							// --Click on Accept
							Driver.findElement(By.id("lnkcnfpull")).click();
							logger.info("Clicked on Accept button");

							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.id("idPartPullDttmValidation")));
								String ValMsg = Driver.findElement(By.id("idPartPullDttmValidation")).getText();
								logger.info("Validation Message=" + ValMsg);

								// --ZoneID
								String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]"))
										.getText();
								logger.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}

								// --Part Pull Date
								WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
								PartPullDate.clear();
								Date date = new Date();
								DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullDate.sendKeys(dateFormat.format(date));
								PartPullDate.sendKeys(Keys.TAB);

								// --Part Pull Time
								WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
								PartPullTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullTime.sendKeys(dateFormat.format(date));

								// --Save button
								WebElement Save = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Save).build().perform();
								Save.click();
								logger.info("Clicked on Save button");

							} catch (Exception e) {
								logger.info("Validation Message is not displayed");

							}

							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(
										By.xpath("//label[contains(@class,'error-messages')]")));
								logger.info("ErroMsg is Displayed=" + Driver
										.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());

								Thread.sleep(2000);

								// --Get the serial NO
								String SerialNo = Driver.findElement(By.xpath("//*[@ng-bind=\"segment.SerialNo\"]"))
										.getText();
								logger.info("Serial No of Part is==" + SerialNo + "\n");

								// enter serial number in scan
								Driver.findElement(By.id("txtBarcode")).clear();
								logger.info("Cleared scan input");
								Driver.findElement(By.id("txtBarcode")).sendKeys(SerialNo);
								logger.info("Entered serialNo in Scan input");
								Driver.findElement(By.id("txtBarcode")).sendKeys(Keys.TAB);
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								logger.info("Entered serial No in scan barcode");
								// --Save button
								WebElement Save = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Save).build().perform();
								Save.click();

								logger.info("Clicked on Save button");

								try {
									wait.until(ExpectedConditions.visibilityOfElementLocated(
											By.xpath("//label[contains(@class,'error-messages')]")));
									logger.info("ErroMsg is Displayed="
											+ Driver.findElement(By.xpath("//label[contains(@class,'error-messages')]"))
													.getText());
									// --ZoneID
									String ZOneID = Driver
											.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]")).getText();
									logger.info("ZoneID of is==" + ZOneID);
									if (ZOneID.equalsIgnoreCase("EDT")) {
										ZOneID = "America/New_York";
									} else if (ZOneID.equalsIgnoreCase("CDT")) {
										ZOneID = "CST";
									}

									// --Part Pull Date
									WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
									PartPullDate.clear();
									Date date = new Date();
									DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									PartPullDate.sendKeys(dateFormat.format(date));
									PartPullDate.sendKeys(Keys.TAB);

									// --Part Pull Time
									WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
									PartPullTime.clear();
									date = new Date();
									dateFormat = new SimpleDateFormat("HH:mm");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									PartPullTime.sendKeys(dateFormat.format(date));
									// --Save button
									Save = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Save).build().perform();
									Save.click();
									logger.info("Clicked on Save button");

								} catch (Exception Time) {
									logger.info("Time validation is not displayed-Time is as per timeZone");
								}

								// --Checking Error issue
								dbNullError();

								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
								PUID = getData("OrderProcessing", row1, 1);
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								logger.info("Cleared search input");
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								logger.info("Enter pickupID in search input");
								Driver.findElement(By.id("btnGXNLSearch2")).click();
								logger.info("Click on Search button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										logger.info("Job is not moved to TENDER TO 3P stage successfully");
									}
								} catch (Exception e) {
									logger.info("Job is moved to TENDER TO 3P stage successfully");
									getScreenshot(Driver, "TenderTo3P_" + PUID);
									// --Enter Drop Time
									String ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
									logger.info("ZoneID of is==" + ZOneID);

									if (ZOneID.equalsIgnoreCase("EDT")) {
										ZOneID = "America/New_York";
									} else if (ZOneID.equalsIgnoreCase("CDT")) {
										ZOneID = "CST";
									}
									WebElement DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
									DropTime.clear();
									Date date = new Date();
									SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									DropTime.sendKeys(dateFormat.format(date));

									// --Click on Tender To 3P
									Driver.findElement(By.id("btnsavedelivery")).click();
									logger.info("Clicked on Tender to 3P");

									try {
										wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
										WebElement TrackNoReq = Driver.findElement(By.id("errorid"));
										logger.info("Tracking Number validation is displayed==" + TrackNoReq.getText());
										// --Enter Tracking Number
										Driver.findElement(By.xpath("//input[contains(@id,'txtPackageTrackNum')]"))
												.sendKeys("Track001");
										logger.info("Entered Tracking Number");
										// --Click on Tender To 3P
										Driver.findElement(By.id("btnsavedelivery")).click();
										logger.info("Clicked on Tender to 3P");

									} catch (Exception Error) {
										logger.info("Tracking Number validation is not displayed");
									}

									try {
										wait.until(ExpectedConditions
												.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
										Driver.findElement(By.id("iddataok")).click();
										logger.info("Click on OK of Dialogue box");

										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));

									} catch (Exception SPickup) {
										logger.info("Dialogue is not present");
									}
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
									PUID = getData("OrderProcessing", row1, 1);
									Driver.findElement(By.id("txtBasicSearch2")).clear();
									logger.info("Cleared search input");
									Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
									logger.info("Enter pickupID in search input");
									Driver.findElement(By.id("btnGXNLSearch2")).click();
									logger.info("Click on Search button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									try {
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
										WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
										if (NoData.isDisplayed()) {
											logger.info("Job is moved to send to Deliver stage successfully");

										}
									} catch (Exception Deliver) {
										logger.info("Job is not moved to send to Deliver stage successfully");

									}

								}
							} catch (Exception errmsg) {
								logger.info("Validation message is not displayed");
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
								PUID = getData("OrderProcessing", row1, 1);
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								logger.info("Cleared search input");
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								logger.info("Enter pickupID in search input");
								Driver.findElement(By.id("btnGXNLSearch2")).click();
								logger.info("Click on Search button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										logger.info("Job is not moved to TENDER TO 3P stage successfully");
									}
								} catch (Exception e) {
									logger.info("Job is moved to TENDER TO 3P stage successfully");
								}
							}
						}

					} else if (Orderstage.equalsIgnoreCase("Confirm Pull")) {
						String stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
						logger.info("stage=" + stage);

						logger.info("stage=" + stage);
						logger.info("If order stage is Confirm Pull.....");
						getScreenshot(Driver, "ConfirmPull_" + PUID);
						// --Label generation
						Driver.findElement(By.id("idiconprint")).click();
						logger.info("Clicked on Label Generation");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						// Handle Label Generation window
						String WindowHandlebefore = Driver.getWindowHandle();
						for (String windHandle : Driver.getWindowHandles()) {
							Driver.switchTo().window(windHandle);
							logger.info("Switched to Label generation window");
							Thread.sleep(5000);
							getScreenshot(Driver, "Labelgeneration" + PUID);
						}
						Driver.close();
						logger.info("Closed Label generation window");

						Driver.switchTo().window(WindowHandlebefore);
						logger.info("Switched to main window");

						// --Click on Accept
						Driver.findElement(By.id("lnkcnfpull")).click();
						logger.info("Clicked on Accept button");

						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.id("idPartPullDttmValidation")));
							String ValMsg = Driver.findElement(By.id("idPartPullDttmValidation")).getText();
							logger.info("Validation Message=" + ValMsg);

							// --ZoneID
							String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]"))
									.getText();
							logger.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							}

							// --Part Pull Date
							WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
							PartPullDate.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullDate.sendKeys(dateFormat.format(date));
							PartPullDate.sendKeys(Keys.TAB);

							// --Part Pull Time
							WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
							PartPullTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullTime.sendKeys(dateFormat.format(date));

							// --Save button
							WebElement Save = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Save).build().perform();
							Save.click();
							logger.info("Clicked on Save button");

						} catch (Exception e) {
							logger.info("Validation Message is not displayed");

						}

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//label[contains(@class,'error-messages')]")));

							logger.info("ErroMsg is Displayed=" + Driver
									.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());

							Thread.sleep(2000);

							// --Get the serial NO
							String SerialNo = Driver.findElement(By.xpath("//*[@ng-bind=\"segment.SerialNo\"]"))
									.getText();
							logger.info("Serial No of Part is==" + SerialNo + "\n");

							// enter serial number in scan
							Driver.findElement(By.id("txtBarcode")).clear();
							logger.info("Cleared scan input");
							Driver.findElement(By.id("txtBarcode")).sendKeys(SerialNo);
							logger.info("Entered serialNo in Scan input");
							Driver.findElement(By.id("txtBarcode")).sendKeys(Keys.TAB);
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							logger.info("Entered serial No in scan barcode");

							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(
										By.xpath("//label[contains(@class,'error-messages')]")));
								logger.info("ErroMsg is Displayed=" + Driver
										.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());
								// --ZoneID
								String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]"))
										.getText();
								logger.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}

								// --Part Pull Date
								WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
								PartPullDate.clear();
								Date date = new Date();
								DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullDate.sendKeys(dateFormat.format(date));
								PartPullDate.sendKeys(Keys.TAB);

								// --Part Pull Time
								WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
								PartPullTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullTime.sendKeys(dateFormat.format(date));

							} catch (Exception Time) {
								logger.info("Time validation is not displayed-Time is as per timeZone");
							}

							// --Save button
							WebElement Save = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Save).build().perform();
							Save.click();
							logger.info("Clicked on Save button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							PUID = getData("OrderProcessing", row1, 1);
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							logger.info("Cleared search input");
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							logger.info("Enter pickupID in search input");
							Driver.findElement(By.id("btnGXNLSearch2")).click();
							logger.info("Click on Search button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not moved to TENDER TO 3P stage successfully");
								}
							} catch (Exception e) {
								logger.info("Job is moved to TENDER TO 3P stage successfully");
								getScreenshot(Driver, "TenderTo3P_" + PUID);
								// --Enter Drop Time
								String ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
								logger.info("ZoneID of is==" + ZOneID);

								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}
								WebElement DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
								DropTime.clear();
								Date date = new Date();
								SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								DropTime.sendKeys(dateFormat.format(date));

								// --Click on Tender To 3P
								Driver.findElement(By.id("btnsavedelivery")).click();
								logger.info("Clicked on Tender to 3P");

								try {
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
									WebElement TrackNoReq = Driver.findElement(By.id("errorid"));
									logger.info("Tracking Number validation is displayed==" + TrackNoReq.getText());
									// --Enter Tracking Number
									Driver.findElement(By.xpath("//input[contains(@id,'txtPackageTrackNum')]"))
											.sendKeys("Track001");
									logger.info("Entered Tracking Number");

									// --Click on Tender To 3P
									Driver.findElement(By.id("btnsavedelivery")).click();
									logger.info("Clicked on Tender to 3P");

								} catch (Exception Error) {
									logger.info("Tracking Number validation is not displayed");
								}

								try {
									wait.until(ExpectedConditions
											.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
									Driver.findElement(By.id("iddataok")).click();
									logger.info("Click on OK of Dialogue box");

									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								} catch (Exception SPickup) {
									logger.info("Dialogue is not present");
								}
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
								PUID = getData("OrderProcessing", row1, 1);
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								logger.info("Cleared search input");
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								logger.info("Enter pickupID in search input");
								Driver.findElement(By.id("btnGXNLSearch2")).click();
								logger.info("Click on Search button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										logger.info("Job is moved to send to Deliver stage successfully");

									}
								} catch (Exception Deliver) {
									logger.info("Job is not moved to send to Deliver stage successfully");

								}

							}
						} catch (Exception errmsg) {
							logger.info("Validation message is not displayed");
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							PUID = getData("OrderProcessing", row1, 1);
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							logger.info("Cleared search input");
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							logger.info("Enter pickupID in search input");
							Driver.findElement(By.id("btnGXNLSearch2")).click();
							logger.info("Click on Search button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not moved to TENDER TO 3P stage successfully");
								}
							} catch (Exception e) {
								logger.info("Job is moved to TENDER TO 3P stage successfully");
							}
						}
					} else if (Orderstage.equalsIgnoreCase("TENDER TO 3P")) {
						logger.info("Job is moved to TENDER TO 3P stage successfully");
						getScreenshot(Driver, "TenderTo3P_" + PUID);
						// --Enter Drop Time
						String ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
						logger.info("ZoneID of is==" + ZOneID);

						if (ZOneID.equalsIgnoreCase("EDT")) {
							ZOneID = "America/New_York";
						} else if (ZOneID.equalsIgnoreCase("CDT")) {
							ZOneID = "CST";
						}
						WebElement DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
						DropTime.clear();
						Date date = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logger.info(dateFormat.format(date));
						DropTime.sendKeys(dateFormat.format(date));

						// --Click on Tender To 3P
						Driver.findElement(By.id("btnsavedelivery")).click();
						logger.info("Clicked on Tender to 3P");

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
							WebElement TrackNoReq = Driver.findElement(By.id("errorid"));
							logger.info("Tracking Number validation is displayed==" + TrackNoReq.getText());
							// --Enter Tracking Number
							Driver.findElement(By.xpath("//input[contains(@id,'txtPackageTrackNum')]"))
									.sendKeys("Track001");
							logger.info("Entered Tracking Number");
							// --Click on Tender To 3P
							Driver.findElement(By.id("btnsavedelivery")).click();
							logger.info("Clicked on Tender to 3P");

						} catch (Exception Error) {
							logger.info("Tracking Number validation is not displayed");
						}

						try {
							wait.until(
									ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
							Driver.findElement(By.id("iddataok")).click();
							logger.info("Click on OK button of Dialogue box");

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception SPickup) {
							logger.info("Dialogue is not present");
						}
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						PUID = getData("OrderProcessing", row1, 1);
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						logger.info("Cleared search input");
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						logger.info("Enter pickupID in search input");
						Driver.findElement(By.id("btnGXNLSearch2")).click();
						logger.info("Click on Search button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is moved to send to Deliver stage successfully");

							}
						} catch (Exception Deliver) {
							logger.info("Job is not moved to send to Deliver stage successfully");

						}

					} else if (Orderstage.equalsIgnoreCase("Confirm Del Alert")) {
						// --Confirm Del Alert
						logger.info("Job is moved to Confirm Del Alert stage successfully");
						getScreenshot(Driver, "ConfDelAlert_" + PUID);

						// --Click on Confirm
						Driver.findElement(By.id("lnkConfPick")).click();
						logger.info("Clicked on Confirm");

						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						PUID = getData("OrderProcessing", row1, 1);
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						logger.info("Cleared search input");
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						logger.info("Enter pickupID in search input");
						Driver.findElement(By.id("btnGXNLSearch2")).click();
						logger.info("Click on Search button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is not moved to RECOVER stage successfully");

							}
						} catch (Exception Recover) {
							logger.info("Job is moved to RECOVER stage successfully");

							// ----Recover @ Destination stage
							String StageName = Driver.findElement(By.xpath("//*[@ng-if=\"ConfirmPickupPage\"]"))
									.getText();
							logger.info("Stage is==" + StageName);
							getScreenshot(Driver, "RecoverAtDestination_" + PUID);
							// --Enter Recover Time
							String ZOneID = Driver.findElement(By.id("spanTimezoneId")).getText();
							logger.info("ZoneID of is==" + ZOneID);

							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							}
							WebElement RecoverTime = Driver.findElement(By.id("txtActualPickUpTime"));
							RecoverTime.clear();
							Date date = new Date();
							SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							RecoverTime.sendKeys(dateFormat.format(date));

							// --Click on RECOVER
							Driver.findElement(By.id("lnksave")).click();
							logger.info("Clicked on RECOVER");

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							PUID = getData("OrderProcessing", row1, 1);
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							logger.info("Cleared search input");
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							logger.info("Enter pickupID in search input");
							Driver.findElement(By.id("btnGXNLSearch2")).click();
							logger.info("Click on Search button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not moved to Deliver stage successfully");

								}
							} catch (Exception Deliver) {
								logger.info("Job is moved to Deliver stage successfully");

								// ----DELIVER stage
								StageName = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]"))
										.getText();
								logger.info("Stage is==" + StageName);
								getScreenshot(Driver, "DELIVER_" + PUID);
								// --Enter Recover Time
								ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
								logger.info("ZoneID of is==" + ZOneID);
								logger.info("ZoneID of is==" + ZOneID);

								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}
								WebElement DelTime = Driver.findElement(By.id("txtActualDeliveryTme"));
								DelTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								DelTime.sendKeys(dateFormat.format(date));

								// --Signed For By
								Driver.findElement(By.id("txtSignature")).clear();
								Driver.findElement(By.id("txtSignature")).sendKeys("RV");
								logger.info("Enter signature");

								// --Click on DELIVER
								Driver.findElement(By.id("btnsavedelivery")).click();
								logger.info("Clicked on DELIVER");
								try {
									wait.until(ExpectedConditions
											.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
									Driver.findElement(By.id("iddataok")).click();
									logger.info("Click on OK of Dialogue box");

									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								} catch (Exception Del) {
									logger.info("Dialogue is not present");
								}

								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
								PUID = getData("OrderProcessing", row1, 1);
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								logger.info("Cleared search input");
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								logger.info("Enter pickupID in search input");
								Driver.findElement(By.id("btnGXNLSearch2")).click();
								logger.info("Click on Search button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										logger.info("Job is moved to VERIFY CUSTOMER BILL stage successfully");

									}
								} catch (Exception VERIFYCUSTOMERBILL) {
									logger.info("Job is not moved to VERIFY CUSTOMER BILL stage successfully");

								}

							}
						}

					} else if (Orderstage.equalsIgnoreCase("Recover @ Destination")) {
						logger.info("Job is moved to RECOVER stage successfully");

						// ----Recover @ Destination stage
						String StageName = Driver.findElement(By.xpath("//*[@ng-if=\"ConfirmPickupPage\"]")).getText();
						logger.info("Stage is==" + StageName);
						getScreenshot(Driver, "RecoverAtDestination_" + PUID);
						// --Enter Recover Time
						String ZOneID = Driver.findElement(By.id("spanTimezoneId")).getText();
						logger.info("ZoneID of is==" + ZOneID);

						if (ZOneID.equalsIgnoreCase("EDT")) {
							ZOneID = "America/New_York";
						} else if (ZOneID.equalsIgnoreCase("CDT")) {
							ZOneID = "CST";
						}
						WebElement RecoverTime = Driver.findElement(By.id("txtActualPickUpTime"));
						RecoverTime.clear();
						Date date = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logger.info(dateFormat.format(date));
						RecoverTime.sendKeys(dateFormat.format(date));

						// --Click on RECOVER
						Driver.findElement(By.id("lnksave")).click();
						logger.info("Clicked on RECOVER");

						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						PUID = getData("OrderProcessing", row1, 1);
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						logger.info("Cleared search input");
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						logger.info("Enter pickupID in search input");
						Driver.findElement(By.id("btnGXNLSearch2")).click();
						logger.info("Click on Search button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is not moved to Deliver stage successfully");

							}
						} catch (Exception Deliver) {
							logger.info("Job is moved to Deliver stage successfully");

							// ----DELIVER stage
							StageName = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
							logger.info("Stage is==" + StageName);
							getScreenshot(Driver, "DELIVER_" + PUID);
							// --Enter Recover Time
							ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
							logger.info("ZoneID of is==" + ZOneID);

							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							}
							WebElement DelTime = Driver.findElement(By.id("txtActualDeliveryTme"));
							DelTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							DelTime.sendKeys(dateFormat.format(date));

							// --Signed For By
							Driver.findElement(By.id("txtSignature")).clear();
							Driver.findElement(By.id("txtSignature")).sendKeys("RV");
							logger.info("Enter signature");

							// --Click on DELIVER
							Driver.findElement(By.id("btnsavedelivery")).click();
							logger.info("Clicked on DELIVER");
							try {
								wait.until(ExpectedConditions
										.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
								Driver.findElement(By.id("iddataok")).click();
								logger.info("Click on OK of Dialogue box");

								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

							} catch (Exception Del) {
								logger.info("Dialogue is not present");
							}

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							PUID = getData("OrderProcessing", row1, 1);
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							logger.info("Cleared search input");
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							logger.info("Enter pickupID in search input");
							Driver.findElement(By.id("btnGXNLSearch2")).click();
							logger.info("Click on Search button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is moved to VERIFY CUSTOMER BILL stage successfully");

								}
							} catch (Exception VERIFYCUSTOMERBILL) {
								logger.info("Job is not moved to VERIFY CUSTOMER BILL stage successfully");

							}

						}
					} else if (Orderstage.equalsIgnoreCase("DELIVER")) {
						// ----DELIVER stage
						String StageName = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]"))
								.getText();
						logger.info("Stage is==" + StageName);
						getScreenshot(Driver, "DELIVER_" + PUID);
						// --Enter Recover Time
						String ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
						logger.info("ZoneID of is==" + ZOneID);

						if (ZOneID.equalsIgnoreCase("EDT")) {
							ZOneID = "America/New_York";
						} else if (ZOneID.equalsIgnoreCase("CDT")) {
							ZOneID = "CST";
						}
						WebElement DelTime = Driver.findElement(By.id("txtActualDeliveryTme"));
						DelTime.clear();
						Date date = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logger.info(dateFormat.format(date));
						DelTime.sendKeys(dateFormat.format(date));

						// --Signed For By
						Driver.findElement(By.id("txtSignature")).clear();
						Driver.findElement(By.id("txtSignature")).sendKeys("RV");
						logger.info("Enter signature");

						// --Click on DELIVER
						Driver.findElement(By.id("btnsavedelivery")).click();
						logger.info("Clicked on DELIVER");
						try {
							wait.until(
									ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
							Driver.findElement(By.id("iddataok")).click();
							logger.info("Click on OK of Dialogue box");

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception Del) {
							logger.info("Dialogue is not present");
						}

						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						PUID = getData("OrderProcessing", row1, 1);
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						logger.info("Cleared search input");
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						logger.info("Enter pickupID in search input");
						Driver.findElement(By.id("btnGXNLSearch2")).click();
						logger.info("Click on Search button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is moved to VERIFY CUSTOMER BILL stage successfully");

							}
						} catch (Exception VERIFYCUSTOMERBILL) {
							logger.info("Job is not moved to VERIFY CUSTOMER BILL stage successfully");

						}

					} else if (Orderstage.equalsIgnoreCase("3rd Party Delivery")) {

						// ----3rd Party Delivery stage
						String StageName = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]"))
								.getText();
						logger.info("Stage is==" + StageName);
						getScreenshot(Driver, "3rdPartyDelivery_" + PUID);

						// --Click on Close
						Driver.findElement(By.id("btnclsdelivery")).click();
						logger.info("Clicked on Close");

					}

				} else if (ServiceID.contains("D3P")) {
					logger.info("If ServiceID is D3P....");
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					Driver.findElement(By.id("txtBasicSearch2")).clear();
					logger.info("Clear search input");
					Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
					logger.info("Enter PickUpID in Search input");
					Driver.findElement(By.id("btnGXNLSearch2")).click();
					logger.info("Click on Search button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
						WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
						if (NoData.isDisplayed()) {
							logger.info("Job is not found in Operation Tab");
							Driver.findElement(By.id("inventory")).click();
							logger.info("Click on Inventory tab");
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
							PUID = getData("OrderProcessing", row1, 1);
							Driver.findElement(By.id("txtBasicSearch")).clear();
							logger.info("Clear search input");
							Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
							logger.info("Enter pickupID in search input");
							Driver.findElement(By.id("btnSearch2")).click();
							logger.info("Click on Search button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						}
					} catch (Exception Tab) {
						logger.info("Job is exist");

					}
					// --Memo
					// memo(PUID);

					// -Notification
					// notification(PUID);

					// Upload
					// upload(PUID);

					// Ship Label Services
					// shipLabel(PUID);

					// --Print pull
					// printPull(PUID);

					// --Get current stage of the order
					String Orderstage = null;
					try {
						String Orderstage1 = Driver.findElement(By.xpath("//div/h3[contains(@class,\"ng-binding\")]"))
								.getText();
						Orderstage = Orderstage1;
						logger.info("Current stage of the order is=" + Orderstage);
					} catch (Exception stage) {
						try {
							String Orderstage2 = Driver.findElement(By.xpath("//Strong/span[@class=\"ng-binding\"]"))
									.getText();
							Orderstage = Orderstage2;
							logger.info("Current stage of the order is=" + Orderstage);
						} catch (Exception SName) {
							String StageName = Driver.findElement(By.xpath("//*[@ng-if=\"ConfirmPickupPage\"]"))
									.getText();
							Orderstage = StageName;
							logger.info("Stage is==" + StageName);
						}
					}
					if (Orderstage.equalsIgnoreCase("Confirm Pu Alert")) {

						// --Click on CONFIRM button //
						Driver.findElement(By.id("lnkConfPick")).click();
						logger.info("Click on CONFIRM button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						// --Search again from Operation Tab
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						logger.info("Clear search input");
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						logger.info("Enter PickUpID in Search input");
						Driver.findElement(By.id("btnGXNLSearch2")).click();
						logger.info("Click on Search button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is not moved to Confirm Pull Alert stage successfully");
							}
						} catch (Exception e1) {
							logger.info("Job is moved to Confirm Pull Alert stage successfully");

							// --Confirm Pull Alert
							String stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]"))
									.getText();
							logger.info("stage=" + stage);
							logger.info("If order stage is Confirm Pull Alert.....");
							getScreenshot(Driver, "ConfirmPullAlert_" + PUID);

							// --Click on Accept
							Driver.findElement(By.id("idiconaccept")).click();
							logger.info("Clicked on Accept button");

							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.xpath("//*[@ng-message=\"required\"]")));
								logger.info("Validation Message is=="
										+ Driver.findElement(By.xpath("//*[@ng-message=\"required\"]")).getText());
								// --Spoke with
								Driver.findElement(By.id("idConfPullAlertForm")).sendKeys("Ravina Oza");
								logger.info("Entered spoke with");
								// --Click on Accept
								Driver.findElement(By.id("idiconaccept")).click();
								logger.info("Clicked on Accept button");

							} catch (Exception e) {
								logger.info("Validation Message is not displayed");

							}

							// --Search again from Operation Tab
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							logger.info("Clear search input");
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							logger.info("Enter PickUpID in Search input");
							Driver.findElement(By.id("btnGXNLSearch2")).click();
							logger.info("Click on Search button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not found in Operation Tab");
									Driver.findElement(By.id("inventory")).click();
									logger.info("Click on Inventory tab");
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
									PUID = getData("OrderProcessing", row1, 1);
									Driver.findElement(By.id("txtBasicSearch")).clear();
									logger.info("Clear search input");
									Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
									logger.info("Enter pickupID in search input");
									Driver.findElement(By.id("btnSearch2")).click();
									logger.info("Click on Search button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								}
							} catch (Exception Tab) {
								logger.info("Job is exist");

							}
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not moved to Confirm Pull stage successfully");
								}
							} catch (Exception e) {
								logger.info("Job is moved to Confirm Pull stage successfully");

								// --Confirm Pull stage
								stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
								logger.info("stage=" + stage);
								getScreenshot(Driver, "ConfirmPull_" + PUID);
								// --Label generation
								Driver.findElement(By.id("idiconprint")).click();
								logger.info("Clicked on Label Generation");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								// Handle Label Generation window
								String WindowHandlebefore = Driver.getWindowHandle();
								for (String windHandle : Driver.getWindowHandles()) {
									Driver.switchTo().window(windHandle);
									logger.info("Switched to Label generation window");
									Thread.sleep(5000);
									getScreenshot(Driver, "Labelgeneration" + PUID);
								}
								Driver.close();
								logger.info("Closed Label generation window");

								Driver.switchTo().window(WindowHandlebefore);
								logger.info("Switched to main window");

								// --Save button
								WebElement Save = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Save).build().perform();
								Save.click();
								logger.info("Clicked on Save button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.id("idPartPullDttmValidation")));
									String ValMsg = Driver.findElement(By.id("idPartPullDttmValidation")).getText();
									logger.info("Validation Message=" + ValMsg);
									// --ZoneID
									String ZOneID = Driver
											.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]")).getText();
									logger.info("ZoneID of is==" + ZOneID);
									if (ZOneID.equalsIgnoreCase("EDT")) {
										ZOneID = "America/New_York";
									} else if (ZOneID.equalsIgnoreCase("CDT")) {
										ZOneID = "CST";
									}

									// --Part Pull Date
									WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
									PartPullDate.clear();
									Date date = new Date();
									DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									PartPullDate.sendKeys(dateFormat.format(date));
									PartPullDate.sendKeys(Keys.TAB);

									// --Part Pull Time
									WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
									PartPullTime.clear();
									date = new Date();
									dateFormat = new SimpleDateFormat("HH:mm");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									PartPullTime.sendKeys(dateFormat.format(date));
									// --Save button
									Save = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Save).build().perform();
									Save.click();
									logger.info("Clicked on Save button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								} catch (Exception eDataB) {
									logger.info("Validation Message is not displayed");
								}

								try {
									wait.until(ExpectedConditions.visibilityOfElementLocated(
											By.xpath("//label[contains(@class,'error-messages')]")));
									logger.info("ErroMsg is Displayed="
											+ Driver.findElement(By.xpath("//label[contains(@class,'error-messages')]"))
													.getText());
									Thread.sleep(2000);

									// --Get the serial NO
									String SerialNo = Driver.findElement(By.xpath("//*[@ng-bind=\"segment.SerialNo\"]"))
											.getText();
									logger.info("Serial No of Part is==" + SerialNo + "\n");
									// enter serial number in scan
									Driver.findElement(By.id("txtBarcode")).clear();
									Driver.findElement(By.id("txtBarcode")).sendKeys(SerialNo);
									Driver.findElement(By.id("txtBarcode")).sendKeys(Keys.TAB);
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									logger.info("Entered serial No in scan barcode");
									// --Save button
									Save = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Save).build().perform();
									Save.click();
									logger.info("Clicked on Save button");
								} catch (Exception SerialNo) {
									logger.info("Validation for Serial No is not displayed");

								}

								try {
									wait.until(ExpectedConditions.visibilityOfElementLocated(
											By.xpath("//label[contains(@class,'error-messages')]")));
									logger.info("ErroMsg is Displayed="
											+ Driver.findElement(By.xpath("//label[contains(@class,'error-messages')]"))
													.getText());
									// --ZoneID
									String ZOneID = Driver
											.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]")).getText();
									logger.info("ZoneID of is==" + ZOneID);
									if (ZOneID.equalsIgnoreCase("EDT")) {
										ZOneID = "America/New_York";
									} else if (ZOneID.equalsIgnoreCase("CDT")) {
										ZOneID = "CST";
									}

									// --Part Pull Date
									WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
									PartPullDate.clear();
									Date date = new Date();
									DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									PartPullDate.sendKeys(dateFormat.format(date));
									PartPullDate.sendKeys(Keys.TAB);

									// --Part Pull Time
									WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
									PartPullTime.clear();
									date = new Date();
									dateFormat = new SimpleDateFormat("HH:mm");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									PartPullTime.sendKeys(dateFormat.format(date));

									// --Save button
									Save = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Save).build().perform();
									Save.click();
									logger.info("Clicked on Save button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								} catch (Exception Time) {
									logger.info("Time validation is not displayed-Time is as per timeZone");
								}

								// --Checking Error issue
								dbNullError();

								// --Search job from Inventory tab
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
								PUID = getData("OrderProcessing", row1, 1);
								Driver.findElement(By.id("txtBasicSearch")).clear();
								logger.info("Clear search input");
								Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
								logger.info("Enter pickupID in search input");
								Driver.findElement(By.id("btnSearch2")).click();
								logger.info("Click on Search button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										logger.info("Job is not found in Inventory Tab");
										Driver.findElement(By.xpath("//*[@id=\"operation\" and  text()='Operations']"))
												.click();
										logger.info("Click on operation tab");
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.id("txtBasicSearch2")));
										PUID = getData("OrderProcessing", row1, 1);
										Driver.findElement(By.id("txtBasicSearch2")).clear();
										logger.info("Clear search input");
										Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
										logger.info("Enter PickUpID in Search input");
										Driver.findElement(By.id("btnGXNLSearch2")).click();
										logger.info("Click on Search button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
									}
								} catch (Exception Tab) {
									logger.info("Job is exist");

								}
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										logger.info("Job is not moved to PICKUP stage successfully");
									}
								} catch (Exception eDataA) {
									logger.info("Job is moved to PICKUP stage successfully");

									// --Pickup
									Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]"))
											.getText();
									logger.info("Current stage of the order is=" + Orderstage);

									// --Enter PickUp Time

									String ZOneID = Driver.findElement(By.id("spanTimezoneId")).getText();
									logger.info("ZoneID of is==" + ZOneID);
									if (ZOneID.equalsIgnoreCase("EDT")) {
										ZOneID = "America/New_York";
									} else if (ZOneID.equalsIgnoreCase("CDT")) {
										ZOneID = "CST";

									}

									WebElement PUPTime = Driver.findElement(By.id("txtActualPickUpTime"));
									PUPTime.clear();
									Date date = new Date();
									DateFormat dateFormat = new SimpleDateFormat("HH:mm");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									PUPTime.sendKeys(dateFormat.format(date));
									Driver.findElement(By.id("lnksave")).click();
									logger.info("Clicked on PICKUP button");
									Thread.sleep(2000);
									try {
										wait.until(ExpectedConditions
												.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
										Driver.findElement(By.id("iddataok")).click();
										logger.info("Clicked on Yes button");

									} catch (Exception Dialogue) {
										logger.info("Dialogue is not exist");
									}
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
									PUID = getData("OrderProcessing", row1, 1);
									Driver.findElement(By.id("txtBasicSearch2")).clear();
									logger.info("Clear search input");
									Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
									logger.info("Enter PickUpID in Search input");
									Driver.findElement(By.id("btnGXNLSearch2")).click();
									logger.info("Click on Search button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

									try {
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
										WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
										if (NoData.isDisplayed()) {
											logger.info("Job is not moved to TENDER TO 3P stage successfully");
										}
									} catch (Exception eNoData) {

										logger.info("Job is moved to TENDER TO 3P stage successfully");

										// --Enter Drop Time
										ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
										logger.info("ZoneID of is==" + ZOneID);
										if (ZOneID.equalsIgnoreCase("EDT")) {
											ZOneID = "America/New_York";
										} else if (ZOneID.equalsIgnoreCase("CDT")) {
											ZOneID = "CST";
										}
										WebElement DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
										DropTime.clear();
										date = new Date();
										dateFormat = new SimpleDateFormat("HH:mm");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										logger.info(dateFormat.format(date));
										DropTime.sendKeys(dateFormat.format(date));

										// --Click on Tender To 3P
										Driver.findElement(By.id("btnsavedelivery")).click();
										logger.info("Clicked on Tender to 3P");

										try {
											wait.until(ExpectedConditions
													.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
											Driver.findElement(By.id("iddataok")).click();
											logger.info("Click on OK of Dialogue box");

											wait.until(ExpectedConditions.invisibilityOfElementLocated(
													By.xpath("//*[@class=\"ajax-loadernew\"]")));

										} catch (Exception SPickup) {
											logger.info("Dialogue is not present");
										}
										try {
											wait.until(ExpectedConditions
													.visibilityOfAllElementsLocatedBy(By.id("errorid")));
											String Validation = Driver.findElement(By.id("errorid")).getText();
											logger.info("Validation is displayed==" + Validation);

											Driver.findElement(By.id("btnclsdelivery")).click();
											logger.info("Click on Close button");

											wait.until(ExpectedConditions.invisibilityOfElementLocated(
													By.xpath("//*[@class=\"ajax-loadernew\"]")));

										} catch (Exception POD) {
											logger.info("Validation is not displayed for Package Tracking No");

										}
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.id("txtBasicSearch2")));
										PUID = getData("OrderProcessing", row1, 1);
										Driver.findElement(By.id("txtBasicSearch2")).clear();
										Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
										Driver.findElement(By.id("btnGXNLSearch2")).click();
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
										try {
											wait.until(ExpectedConditions
													.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
											WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
											if (NoData.isDisplayed()) {
												logger.info("Job is Delivered successfully");
											}
										} catch (Exception Deliver) {
											logger.info("Job is not Delivered successfully");
											Driver.findElement(By.id("btnclsdelivery")).click();
											logger.info("Click on Close button");

											wait.until(ExpectedConditions.invisibilityOfElementLocated(
													By.xpath("//*[@class=\"ajax-loadernew\"]")));

										}

									}

								}

							}
						}

					} else if (Orderstage.equalsIgnoreCase("CONF PULL ALERT")) {
						logger.info("Job is moved to Confirm Pull Alert stage successfully");

						// --Confirm Pull Alert
						String stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
						logger.info("stage=" + stage);
						logger.info("If order stage is Confirm Pull Alert.....");
						getScreenshot(Driver, "ConfirmPullAlert_" + PUID);

						// --Click on Accept
						Driver.findElement(By.id("idiconaccept")).click();
						logger.info("Clicked on Accept button");

						try {
							wait.until(ExpectedConditions
									.visibilityOfElementLocated(By.xpath("//*[@ng-message=\"required\"]")));
							logger.info("Validation Message is=="
									+ Driver.findElement(By.xpath("//*[@ng-message=\"required\"]")).getText());
							// --Spoke with
							Driver.findElement(By.id("idConfPullAlertForm")).sendKeys("Ravina Oza");
							logger.info("Entered spoke with");
							// --Click on Accept
							Driver.findElement(By.id("idiconaccept")).click();
							logger.info("Clicked on Accept button");

						} catch (Exception e) {
							logger.info("Validation Message is not displayed");

						}

						// --Search again from Operation Tab
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						logger.info("Clear search input");
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						logger.info("Enter PickUpID in Search input");
						Driver.findElement(By.id("btnGXNLSearch2")).click();
						logger.info("Click on Search button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is not found in Operation Tab");
								Driver.findElement(By.id("inventory")).click();
								logger.info("Click on Inventory tab");
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
								PUID = getData("OrderProcessing", row1, 1);
								Driver.findElement(By.id("txtBasicSearch")).clear();
								logger.info("Clear search input");
								Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
								logger.info("Enter pickupID in search input");
								Driver.findElement(By.id("btnSearch2")).click();
								logger.info("Click on Search button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							}
						} catch (Exception Tab) {
							logger.info("Job is exist");

						}
						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is not moved to Confirm Pull stage successfully");
							}
						} catch (Exception e) {
							logger.info("Job is moved to Confirm Pull stage successfully");

							// --Confirm Pull stage
							stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
							logger.info("stage=" + stage);
							getScreenshot(Driver, "ConfirmPull_" + PUID);
							// --Label generation
							Driver.findElement(By.id("idiconprint")).click();
							logger.info("Clicked on Label Generation");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							// Handle Label Generation window
							String WindowHandlebefore = Driver.getWindowHandle();
							for (String windHandle : Driver.getWindowHandles()) {
								Driver.switchTo().window(windHandle);
								logger.info("Switched to Label generation window");
								Thread.sleep(5000);
								getScreenshot(Driver, "Labelgeneration" + PUID);
							}
							Driver.close();
							logger.info("Closed Label generation window");

							Driver.switchTo().window(WindowHandlebefore);
							logger.info("Switched to main window");

							// --Save button
							WebElement Save = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Save).build().perform();
							Save.click();
							logger.info("Clicked on Save button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.id("idPartPullDttmValidation")));
								String ValMsg = Driver.findElement(By.id("idPartPullDttmValidation")).getText();
								logger.info("Validation Message=" + ValMsg);
								// --ZoneID
								String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]"))
										.getText();
								logger.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}

								// --Part Pull Date
								WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
								PartPullDate.clear();
								Date date = new Date();
								DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullDate.sendKeys(dateFormat.format(date));
								PartPullDate.sendKeys(Keys.TAB);

								// --Part Pull Time
								WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
								PartPullTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullTime.sendKeys(dateFormat.format(date));
								// --Save button
								Save = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Save).build().perform();
								Save.click();
								logger.info("Clicked on Save button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							} catch (Exception eDataB) {
								logger.info("Validation Message is not displayed");
							}

							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(
										By.xpath("//label[contains(@class,'error-messages')]")));
								logger.info("ErroMsg is Displayed=" + Driver
										.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());
								Thread.sleep(2000);

								// --Get the serial NO
								String SerialNo = Driver.findElement(By.xpath("//*[@ng-bind=\"segment.SerialNo\"]"))
										.getText();
								logger.info("Serial No of Part is==" + SerialNo + "\n");
								// enter serial number in scan
								Driver.findElement(By.id("txtBarcode")).clear();
								Driver.findElement(By.id("txtBarcode")).sendKeys(SerialNo);
								Driver.findElement(By.id("txtBarcode")).sendKeys(Keys.TAB);
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								logger.info("Entered serial No in scan barcode");
								// --Save button
								Save = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Save).build().perform();
								Save.click();
								logger.info("Clicked on Save button");
							} catch (Exception SerialNo) {
								logger.info("Validation for Serial No is not displayed");

							}

							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(
										By.xpath("//label[contains(@class,'error-messages')]")));
								logger.info("ErroMsg is Displayed=" + Driver
										.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());
								// --ZoneID
								String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]"))
										.getText();
								logger.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}

								// --Part Pull Date
								WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
								PartPullDate.clear();
								Date date = new Date();
								DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullDate.sendKeys(dateFormat.format(date));
								PartPullDate.sendKeys(Keys.TAB);

								// --Part Pull Time
								WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
								PartPullTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullTime.sendKeys(dateFormat.format(date));

								// --Save button
								Save = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Save).build().perform();
								Save.click();
								logger.info("Clicked on Save button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

							} catch (Exception Time) {
								logger.info("Time validation is not displayed-Time is as per timeZone");
							}

							// --Checking Error issue
							dbNullError();

							// --Search job from Inventory tab
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
							PUID = getData("OrderProcessing", row1, 1);
							Driver.findElement(By.id("txtBasicSearch")).clear();
							logger.info("Clear search input");
							Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
							logger.info("Enter pickupID in search input");
							Driver.findElement(By.id("btnSearch2")).click();
							logger.info("Click on Search button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not found in Inventory Tab");
									Driver.findElement(By.xpath("//*[@id=\"operation\" and  text()='Operations']"))
											.click();
									logger.info("Click on operation tab");
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
									PUID = getData("OrderProcessing", row1, 1);
									Driver.findElement(By.id("txtBasicSearch2")).clear();
									logger.info("Clear search input");
									Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
									logger.info("Enter PickUpID in Search input");
									Driver.findElement(By.id("btnGXNLSearch2")).click();
									logger.info("Click on Search button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								}
							} catch (Exception Tab) {
								logger.info("Job is exist");

							}
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not moved to PICKUP stage successfully");
								}
							} catch (Exception eDataA) {
								logger.info("Job is moved to PICKUP stage successfully");

								// --Pickup
								Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]"))
										.getText();
								logger.info("Current stage of the order is=" + Orderstage);

								// --Enter PickUp Time

								String ZOneID = Driver.findElement(By.id("spanTimezoneId")).getText();
								logger.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";

								}

								WebElement PUPTime = Driver.findElement(By.id("txtActualPickUpTime"));
								PUPTime.clear();
								Date date = new Date();
								DateFormat dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PUPTime.sendKeys(dateFormat.format(date));
								Driver.findElement(By.id("lnksave")).click();
								logger.info("Clicked on PICKUP button");
								Thread.sleep(2000);
								try {
									wait.until(ExpectedConditions
											.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
									Driver.findElement(By.id("iddataok")).click();
									logger.info("Clicked on Yes button");

								} catch (Exception Dialogue) {
									logger.info("Dialogue is not exist");
								}
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
								PUID = getData("OrderProcessing", row1, 1);
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								logger.info("Clear search input");
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								logger.info("Enter PickUpID in Search input");
								Driver.findElement(By.id("btnGXNLSearch2")).click();
								logger.info("Click on Search button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										logger.info("Job is not moved to TENDER TO 3P stage successfully");
									}
								} catch (Exception eNoData) {

									logger.info("Job is moved to TENDER TO 3P stage successfully");

									// --Enter Drop Time
									ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
									logger.info("ZoneID of is==" + ZOneID);
									if (ZOneID.equalsIgnoreCase("EDT")) {
										ZOneID = "America/New_York";
									} else if (ZOneID.equalsIgnoreCase("CDT")) {
										ZOneID = "CST";
									}
									WebElement DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
									DropTime.clear();
									date = new Date();
									dateFormat = new SimpleDateFormat("HH:mm");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									DropTime.sendKeys(dateFormat.format(date));

									// --Click on Tender To 3P
									Driver.findElement(By.id("btnsavedelivery")).click();
									logger.info("Clicked on Tender to 3P");

									try {
										wait.until(ExpectedConditions
												.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
										Driver.findElement(By.id("iddataok")).click();
										logger.info("Click on OK of Dialogue box");

										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));

									} catch (Exception SPickup) {
										logger.info("Dialogue is not present");
									}
									try {
										wait.until(
												ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("errorid")));
										String Validation = Driver.findElement(By.id("errorid")).getText();
										logger.info("Validation is displayed==" + Validation);

										Driver.findElement(By.id("btnclsdelivery")).click();
										logger.info("Click on Close button");

										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));

									} catch (Exception POD) {
										logger.info("Validation is not displayed for Package Tracking No");

									}
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
									PUID = getData("OrderProcessing", row1, 1);
									Driver.findElement(By.id("txtBasicSearch2")).clear();
									Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
									Driver.findElement(By.id("btnGXNLSearch2")).click();
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									try {
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
										WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
										if (NoData.isDisplayed()) {
											logger.info("Job is Delivered successfully");
										}
									} catch (Exception Deliver) {
										logger.info("Job is not Delivered successfully");
										Driver.findElement(By.id("btnclsdelivery")).click();
										logger.info("Click on Close button");

										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));

									}

								}

							}

						}
					} else if (Orderstage.equalsIgnoreCase("Confirm Pull")) {
						logger.info("Job is moved to Confirm Pull stage successfully");

						// --Confirm Pull stage
						String stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
						logger.info("stage=" + stage);
						getScreenshot(Driver, "ConfirmPull_" + PUID);
						// --Label generation
						Driver.findElement(By.id("idiconprint")).click();
						logger.info("Clicked on Label Generation");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						// Handle Label Generation window
						String WindowHandlebefore = Driver.getWindowHandle();
						for (String windHandle : Driver.getWindowHandles()) {
							Driver.switchTo().window(windHandle);
							logger.info("Switched to Label generation window");
							Thread.sleep(5000);
							getScreenshot(Driver, "Labelgeneration" + PUID);
						}
						Driver.close();
						logger.info("Closed Label generation window");

						Driver.switchTo().window(WindowHandlebefore);
						logger.info("Switched to main window");

						// --Save button
						WebElement Save = Driver.findElement(By.id("idiconsave"));
						act.moveToElement(Save).build().perform();
						Save.click();
						logger.info("Clicked on Save button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.id("idPartPullDttmValidation")));
							String ValMsg = Driver.findElement(By.id("idPartPullDttmValidation")).getText();
							logger.info("Validation Message=" + ValMsg);
							// --ZoneID
							String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]"))
									.getText();
							logger.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							}

							// --Part Pull Date
							WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
							PartPullDate.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullDate.sendKeys(dateFormat.format(date));
							PartPullDate.sendKeys(Keys.TAB);

							// --Part Pull Time
							WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
							PartPullTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullTime.sendKeys(dateFormat.format(date));
							// --Save button
							Save = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Save).build().perform();
							Save.click();
							logger.info("Clicked on Save button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						} catch (Exception eDataB) {
							logger.info("Validation Message is not displayed");
						}

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//label[contains(@class,'error-messages')]")));
							logger.info("ErroMsg is Displayed=" + Driver
									.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());
							Thread.sleep(2000);

							// --Get the serial NO
							String SerialNo = Driver.findElement(By.xpath("//*[@ng-bind=\"segment.SerialNo\"]"))
									.getText();
							logger.info("Serial No of Part is==" + SerialNo + "\n");
							// enter serial number in scan
							Driver.findElement(By.id("txtBarcode")).clear();
							Driver.findElement(By.id("txtBarcode")).sendKeys(SerialNo);
							Driver.findElement(By.id("txtBarcode")).sendKeys(Keys.TAB);
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							logger.info("Entered serial No in scan barcode");
							// --Save button
							Save = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Save).build().perform();
							Save.click();
							logger.info("Clicked on Save button");
						} catch (Exception SerialNo) {
							logger.info("Validation for Serial No is not displayed");

						}

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//label[contains(@class,'error-messages')]")));
							logger.info("ErroMsg is Displayed=" + Driver
									.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());
							// --ZoneID
							String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]"))
									.getText();
							logger.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							}

							// --Part Pull Date
							WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
							PartPullDate.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullDate.sendKeys(dateFormat.format(date));
							PartPullDate.sendKeys(Keys.TAB);

							// --Part Pull Time
							WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
							PartPullTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullTime.sendKeys(dateFormat.format(date));
							// --Save button
							Save = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Save).build().perform();
							Save.click();
							logger.info("Clicked on Save button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception Time) {
							logger.info("Time validation is not displayed-Time is as per timeZone");
						}

						// --Checking Error issue
						dbNullError();

						// --Search job from Inventory tab
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
						PUID = getData("OrderProcessing", row1, 1);
						Driver.findElement(By.id("txtBasicSearch")).clear();
						logger.info("Clear search input");
						Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
						logger.info("Enter pickupID in search input");
						Driver.findElement(By.id("btnSearch2")).click();
						logger.info("Click on Search button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is not found in Inventory Tab");
								Driver.findElement(By.xpath("//*[@id=\"operation\" and  text()='Operations']")).click();
								logger.info("Click on operation tab");
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
								PUID = getData("OrderProcessing", row1, 1);
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								logger.info("Clear search input");
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								logger.info("Enter PickUpID in Search input");
								Driver.findElement(By.id("btnGXNLSearch2")).click();
								logger.info("Click on Search button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							}
						} catch (Exception Tab) {
							logger.info("Job is exist");

						}
						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is not moved to PICKUP stage successfully");
							}
						} catch (Exception eDataA) {
							logger.info("Job is moved to PICKUP stage successfully");

							// --Pickup
							Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
							logger.info("Current stage of the order is=" + Orderstage);

							// --Enter PickUp Time

							String ZOneID = Driver.findElement(By.id("spanTimezoneId")).getText();
							logger.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";

							}

							WebElement PUPTime = Driver.findElement(By.id("txtActualPickUpTime"));
							PUPTime.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PUPTime.sendKeys(dateFormat.format(date));
							Driver.findElement(By.id("lnksave")).click();
							logger.info("Clicked on PICKUP button");
							Thread.sleep(2000);
							try {
								wait.until(ExpectedConditions
										.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
								Driver.findElement(By.id("iddataok")).click();
								logger.info("Clicked on Yes button");

							} catch (Exception Dialogue) {
								logger.info("Dialogue is not exist");
							}
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							PUID = getData("OrderProcessing", row1, 1);
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							logger.info("Clear search input");
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							logger.info("Enter PickUpID in Search input");
							Driver.findElement(By.id("btnGXNLSearch2")).click();
							logger.info("Click on Search button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not moved to TENDER TO 3P stage successfully");
								}
							} catch (Exception eNoData) {

								logger.info("Job is moved to TENDER TO 3P stage successfully");

								// --Enter Drop Time
								ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
								logger.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}
								WebElement DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
								DropTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								DropTime.sendKeys(dateFormat.format(date));

								// --Click on Tender To 3P
								Driver.findElement(By.id("btnsavedelivery")).click();
								logger.info("Clicked on Tender to 3P");

								try {
									wait.until(ExpectedConditions
											.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
									Driver.findElement(By.id("iddataok")).click();
									logger.info("Click on OK of Dialogue box");

									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								} catch (Exception SPickup) {
									logger.info("Dialogue is not present");
								}
								try {
									wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("errorid")));
									String Validation = Driver.findElement(By.id("errorid")).getText();
									logger.info("Validation is displayed==" + Validation);

									Driver.findElement(By.id("btnclsdelivery")).click();
									logger.info("Click on Close button");

									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								} catch (Exception POD) {
									logger.info("Validation is not displayed for Package Tracking No");

								}
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
								PUID = getData("OrderProcessing", row1, 1);
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								Driver.findElement(By.id("btnGXNLSearch2")).click();
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										logger.info("Job is Delivered successfully");
									}
								} catch (Exception Deliver) {
									logger.info("Job is not Delivered successfully");
									Driver.findElement(By.id("btnclsdelivery")).click();
									logger.info("Click on Close button");

									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								}

							}

						}

					} else if (Orderstage.equalsIgnoreCase("PICKUP")) {
						logger.info("Job is moved to PICKUP stage successfully");

						// --Pickup
						Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
						logger.info("Current stage of the order is=" + Orderstage);

						// --Enter PickUp Time

						String ZOneID = Driver.findElement(By.id("spanTimezoneId")).getText();
						logger.info("ZoneID of is==" + ZOneID);
						if (ZOneID.equalsIgnoreCase("EDT")) {
							ZOneID = "America/New_York";
						} else if (ZOneID.equalsIgnoreCase("CDT")) {
							ZOneID = "CST";

						}

						WebElement PUPTime = Driver.findElement(By.id("txtActualPickUpTime"));
						PUPTime.clear();
						Date date = new Date();
						DateFormat dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logger.info(dateFormat.format(date));
						PUPTime.sendKeys(dateFormat.format(date));
						Driver.findElement(By.id("lnksave")).click();
						logger.info("Clicked on PICKUP button");
						Thread.sleep(2000);
						try {
							wait.until(
									ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
							Driver.findElement(By.id("iddataok")).click();
							logger.info("Clicked on Yes button");

						} catch (Exception Dialogue) {
							logger.info("Dialogue is not exist");
						}
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						PUID = getData("OrderProcessing", row1, 1);
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						logger.info("Clear search input");
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						logger.info("Enter PickUpID in Search input");
						Driver.findElement(By.id("btnGXNLSearch2")).click();
						logger.info("Click on Search button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is not moved to TENDER TO 3P stage successfully");
							}
						} catch (Exception eNoData) {

							logger.info("Job is moved to TENDER TO 3P stage successfully");

							// --Enter Drop Time
							ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
							logger.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							}
							WebElement DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
							DropTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							DropTime.sendKeys(dateFormat.format(date));

							// --Click on Tender To 3P
							Driver.findElement(By.id("btnsavedelivery")).click();
							logger.info("Clicked on Tender to 3P");

							try {
								wait.until(ExpectedConditions
										.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
								Driver.findElement(By.id("iddataok")).click();
								logger.info("Click on OK of Dialogue box");

								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

							} catch (Exception SPickup) {
								logger.info("Dialogue is not present");
							}
							try {
								wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("errorid")));
								String Validation = Driver.findElement(By.id("errorid")).getText();
								logger.info("Validation is displayed==" + Validation);

								Driver.findElement(By.id("btnclsdelivery")).click();
								logger.info("Click on Close button");

								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

							} catch (Exception POD) {
								logger.info("Validation is not displayed for Package Tracking No");

							}
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							PUID = getData("OrderProcessing", row1, 1);
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							Driver.findElement(By.id("btnGXNLSearch2")).click();
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is Delivered successfully");
								}
							} catch (Exception Deliver) {
								logger.info("Job is not Delivered successfully");
								Driver.findElement(By.id("btnclsdelivery")).click();
								logger.info("Click on Close button");

								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

							}

						}

					} else if (Orderstage.equalsIgnoreCase("Tender to 3P")) {

						logger.info("Job is moved to TENDER TO 3P stage successfully");

						// --Enter Drop Time
						String ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
						logger.info("ZoneID of is==" + ZOneID);
						if (ZOneID.equalsIgnoreCase("EDT")) {
							ZOneID = "America/New_York";
						} else if (ZOneID.equalsIgnoreCase("CDT")) {
							ZOneID = "CST";
						}
						WebElement DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
						DropTime.clear();
						Date date = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logger.info(dateFormat.format(date));
						DropTime.sendKeys(dateFormat.format(date));

						// --Click on Tender To 3P
						Driver.findElement(By.id("btnsavedelivery")).click();
						logger.info("Clicked on Tender to 3P");

						try {
							wait.until(
									ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
							Driver.findElement(By.id("iddataok")).click();
							logger.info("Click on OK of Dialogue box");

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception SPickup) {
							logger.info("Dialogue is not present");
						}
						try {
							wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("errorid")));
							String Validation = Driver.findElement(By.id("errorid")).getText();
							logger.info("Validation is displayed==" + Validation);

							Driver.findElement(By.id("btnclsdelivery")).click();
							logger.info("Click on Close button");

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception POD) {
							logger.info("Validation is not displayed for Package Tracking No");

						}
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						PUID = getData("OrderProcessing", row1, 1);
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						Driver.findElement(By.id("btnGXNLSearch2")).click();
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is Delivered successfully");
							}
						} catch (Exception Deliver) {
							logger.info("Job is not Delivered successfully");
							Driver.findElement(By.id("btnclsdelivery")).click();
							logger.info("Click on Close button");

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						}

					}

				} else if (ServiceID.contains("RETURN")) {

					logger.info("If ServiceID is RETURN....");
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					Driver.findElement(By.id("txtBasicSearch2")).clear();
					logger.info("Clear search input");
					Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
					logger.info("Enter PickUpID in Search input");
					Driver.findElement(By.id("btnGXNLSearch2")).click();
					logger.info("Click on Search button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
						WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
						if (NoData.isDisplayed()) {
							logger.info("Job is not found in Operation Tab");
							Driver.findElement(By.id("inventory")).click();
							logger.info("Click on Inventory tab");
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
							PUID = getData("OrderProcessing", row1, 1);
							Driver.findElement(By.id("txtBasicSearch")).clear();
							logger.info("Clear search input");
							Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
							logger.info("Enter pickupID in search input");
							Driver.findElement(By.id("btnSearch2")).click();
							logger.info("Click on Search button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						}
					} catch (Exception Tab) {
						logger.info("Job is exist");

					}
					// --Memo
					memo(PUID);

					// -Notification
					notification(PUID);

					// Upload
					upload(PUID);

					// Ship Label Services
					shipLabel(PUID);

					// --Print pull
					printPull(PUID);

					// --Get current stage of the order
					String Orderstage = null;
					try {
						String Orderstage1 = Driver.findElement(By.xpath("//div/h3[contains(@class,\"ng-binding\")]"))
								.getText();
						Orderstage = Orderstage1;
						logger.info("Current stage of the order is=" + Orderstage);
					} catch (Exception stage) {
						try {
							String Orderstage2 = Driver.findElement(By.xpath("//Strong/span[@class=\"ng-binding\"]"))
									.getText();
							Orderstage = Orderstage2;
							logger.info("Current stage of the order is=" + Orderstage);
						} catch (Exception SName) {
							String StageName = Driver.findElement(By.xpath("//*[@ng-if=\"ConfirmPickupPage\"]"))
									.getText();
							Orderstage = StageName;
							logger.info("Stage is==" + StageName);
						}
					}
					if (Orderstage.equalsIgnoreCase("Confirm Pu Alert")) {

						// --Click on CONFIRM button //
						Driver.findElement(By.id("lnkConfPick")).click();
						logger.info("Click on CONFIRM button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						// --Search again from Operation Tab
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						PUID = getData("OrderProcessing", row1, 1);
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						logger.info("Clear search input");
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						logger.info("Enter PickUpID in Search input");
						Driver.findElement(By.id("btnGXNLSearch2")).click();
						logger.info("Click on Search button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is not found in Operation Tab");
								Driver.findElement(By.id("inventory")).click();
								logger.info("Click on Inventory tab");
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
								PUID = getData("OrderProcessing", row1, 1);
								Driver.findElement(By.id("txtBasicSearch")).clear();
								logger.info("Clear search input");
								Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
								logger.info("Enter pickupID in search input");
								Driver.findElement(By.id("btnSearch2")).click();
								logger.info("Click on Search button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							}
						} catch (Exception Tab) {
							logger.info("Job is exist");

						}

						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is not moved to Confirm Pull Alert stage successfully");
							}
						} catch (Exception e1) {
							logger.info("Job is moved to Confirm Pull Alert stage successfully");

							// --Confirm Pull Alert
							String stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]"))
									.getText();
							logger.info("stage=" + stage);
							logger.info("If order stage is Confirm Pull Alert.....");
							getScreenshot(Driver, "ConfirmPullAlert_" + PUID);

							// --Click on Accept
							Driver.findElement(By.id("idiconaccept")).click();
							logger.info("Clicked on Accept button");

							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.xpath("//*[@ng-message=\"required\"]")));
								logger.info("Validation Message is=="
										+ Driver.findElement(By.xpath("//*[@ng-message=\"required\"]")).getText());
								// --Spoke with
								Driver.findElement(By.id("idConfPullAlertForm")).sendKeys("Ravina Oza");
								logger.info("Entered spoke with");
								// --Click on Accept
								Driver.findElement(By.id("idiconaccept")).click();
								logger.info("Clicked on Accept button");

							} catch (Exception e) {
								logger.info("Validation Message is not displayed");

							}
							// --Search again from Operation Tab
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
							PUID = getData("OrderProcessing", row1, 1);
							Driver.findElement(By.id("txtBasicSearch")).clear();
							logger.info("Clear search input");
							Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
							logger.info("Enter pickupID in search input");
							Driver.findElement(By.id("btnSearch2")).click();
							logger.info("Click on Search button");

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not found in Inventory Tab");
									Driver.findElement(By.xpath("//*[@id=\"operation\" and  text()='Operations']"))
											.click();
									logger.info("Click on Operation tab");
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
									PUID = getData("OrderProcessing", row1, 1);
									Driver.findElement(By.id("txtBasicSearch2")).clear();
									logger.info("Clear search input");
									Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
									logger.info("Enter PickUpID in Search input");
									Driver.findElement(By.id("btnGXNLSearch2")).click();
									logger.info("Click on Search button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								}
							} catch (Exception Tab) {
								logger.info("Job is exist");

							}
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not moved to Confirm Pull stage successfully");
								}
							} catch (Exception e) {
								logger.info("Job is moved to Confirm Pull stage successfully");

								// --Confirm Pull stage
								stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
								logger.info("stage=" + stage);
								getScreenshot(Driver, "ConfirmPull_" + PUID);
								// --Label generation
								Driver.findElement(By.id("idiconprint")).click();
								logger.info("Clicked on Label Generation");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								// Handle Label Generation window
								String WindowHandlebefore = Driver.getWindowHandle();
								for (String windHandle : Driver.getWindowHandles()) {
									Driver.switchTo().window(windHandle);
									logger.info("Switched to Label generation window");
									Thread.sleep(5000);
									getScreenshot(Driver, "Labelgeneration" + PUID);
								}
								Driver.close();
								logger.info("Closed Label generation window");

								Driver.switchTo().window(WindowHandlebefore);
								logger.info("Switched to main window");

								// --Save button
								WebElement Save = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Save).build().perform();
								Save.click();
								logger.info("Clicked on Save button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.id("idPartPullDttmValidation")));
									String ValMsg = Driver.findElement(By.id("idPartPullDttmValidation")).getText();
									logger.info("Validation Message=" + ValMsg);
									// --ZoneID
									String ZOneID = Driver
											.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]")).getText();
									logger.info("ZoneID of is==" + ZOneID);
									if (ZOneID.equalsIgnoreCase("EDT")) {
										ZOneID = "America/New_York";
									} else if (ZOneID.equalsIgnoreCase("CDT")) {
										ZOneID = "CST";
									}

									// --Part Pull Date
									WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
									PartPullDate.clear();
									Date date = new Date();
									DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									PartPullDate.sendKeys(dateFormat.format(date));
									PartPullDate.sendKeys(Keys.TAB);

									// --Part Pull Time
									WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
									PartPullTime.clear();
									date = new Date();
									dateFormat = new SimpleDateFormat("HH:mm");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									PartPullTime.sendKeys(dateFormat.format(date));

									// --Save button
									Save = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Save).build().perform();
									Save.click();
									logger.info("Clicked on Save button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								} catch (Exception eDataB) {
									logger.info("Validation Message is not displayed");
								}

								try {
									wait.until(ExpectedConditions.visibilityOfElementLocated(
											By.xpath("//label[contains(@class,'error-messages')]")));
									logger.info("ErroMsg is Displayed="
											+ Driver.findElement(By.xpath("//label[contains(@class,'error-messages')]"))
													.getText());
									Thread.sleep(2000);

									// --Get the serial NO
									String SerialNo = Driver.findElement(By.xpath("//*[@ng-bind=\"segment.SerialNo\"]"))
											.getText();
									logger.info("Serial No of Part is==" + SerialNo + "\n");
									// enter serial number in scan
									Driver.findElement(By.id("txtBarcode")).clear();
									Driver.findElement(By.id("txtBarcode")).sendKeys(SerialNo);
									Driver.findElement(By.id("txtBarcode")).sendKeys(Keys.TAB);
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									logger.info("Entered serial No in scan barcode");
									// --Save button
									Save = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Save).build().perform();
									Save.click();
									logger.info("Clicked on Save button");
								} catch (Exception SerialNo) {
									logger.info("Validation for Serial No is not displayed");

								}

								try {
									wait.until(ExpectedConditions.visibilityOfElementLocated(
											By.xpath("//label[contains(@class,'error-messages')]")));
									logger.info("ErroMsg is Displayed="
											+ Driver.findElement(By.xpath("//label[contains(@class,'error-messages')]"))
													.getText());
									// --ZoneID
									String ZOneID = Driver
											.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]")).getText();
									logger.info("ZoneID of is==" + ZOneID);
									if (ZOneID.equalsIgnoreCase("EDT")) {
										ZOneID = "America/New_York";
									} else if (ZOneID.equalsIgnoreCase("CDT")) {
										ZOneID = "CST";
									}

									// --Part Pull Date
									WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
									PartPullDate.clear();
									Date date = new Date();
									DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									PartPullDate.sendKeys(dateFormat.format(date));
									PartPullDate.sendKeys(Keys.TAB);

									// --Part Pull Time
									WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
									PartPullTime.clear();
									date = new Date();
									dateFormat = new SimpleDateFormat("HH:mm");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									PartPullTime.sendKeys(dateFormat.format(date));

									// --Save button
									Save = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Save).build().perform();
									Save.click();
									logger.info("Clicked on Save button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								} catch (Exception Time) {
									logger.info("Time validation is not displayed-Time is as per timeZone");
								}

								// --Checking Error issue
								dbNullError();

								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
								PUID = getData("OrderProcessing", row1, 1);
								Driver.findElement(By.id("txtBasicSearch")).clear();
								logger.info("Clear search input");
								Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
								logger.info("Enter pickupID in search input");
								Driver.findElement(By.id("btnSearch2")).click();
								logger.info("Click on Search button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										logger.info("Job is not found in Inventory Tab");
										// --Go to Operation tab
										Driver.findElement(By.xpath("//*[@id=\"operation\" and  text()='Operations']"))
												.click();
										logger.info("Click on Operation Tab");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.id("txtBasicSearch2")));
										PUID = getData("OrderProcessing", row1, 1);
										Driver.findElement(By.id("txtBasicSearch2")).clear();
										Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
										Driver.findElement(By.id("btnGXNLSearch2")).click();
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
									}
								} catch (Exception Tab) {
									logger.info("Job is exist");

								}

								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										logger.info("Job is not moved to PICKUP stage successfully");
									}
								} catch (Exception eDataA) {
									logger.info("Job is moved to PICKUP stage successfully");

									// --Pickup
									Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]"))
											.getText();
									logger.info("Current stage of the order is=" + Orderstage);

									// --Enter PickUp Time

									String ZOneID = Driver.findElement(By.id("spanTimezoneId")).getText();
									logger.info("ZoneID of is==" + ZOneID);
									if (ZOneID.equalsIgnoreCase("EDT")) {
										ZOneID = "America/New_York";
									} else if (ZOneID.equalsIgnoreCase("CDT")) {
										ZOneID = "CST";

									}

									WebElement PUPTime = Driver.findElement(By.id("txtActualPickUpTime"));
									PUPTime.clear();
									Date date = new Date();
									DateFormat dateFormat = new SimpleDateFormat("HH:mm");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									PUPTime.sendKeys(dateFormat.format(date));
									Driver.findElement(By.id("lnksave")).click();
									logger.info("Clicked on PICKUP button");
									Thread.sleep(2000);
									try {
										wait.until(ExpectedConditions
												.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
										Driver.findElement(By.id("iddataok")).click();
										logger.info("Clicked on Yes button");

									} catch (Exception Dialogue) {
										logger.info("Dialogue is not exist");
									}
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
									Driver.findElement(By.id("txtBasicSearch2")).clear();
									Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
									Driver.findElement(By.id("btnGXNLSearch2")).click();
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

									try {
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
										WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
										if (NoData.isDisplayed()) {
											logger.info("Job is not moved to Deliver stage successfully");
										}
									} catch (Exception eNoData) {
										logger.info("Job is moved to Deliver stage successfully");
										getScreenshot(Driver, "Deliver_" + PUID);

										// --Enter Drop Time
										ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
										logger.info("ZoneID of is==" + ZOneID);
										logger.info("ZoneID of is==" + ZOneID);

										if (ZOneID.equalsIgnoreCase("EDT")) {
											ZOneID = "America/New_York";
										} else if (ZOneID.equalsIgnoreCase("CDT")) {
											ZOneID = "CST";
										}
										WebElement DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
										DropTime.clear();
										date = new Date();
										dateFormat = new SimpleDateFormat("HH:mm");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										logger.info(dateFormat.format(date));
										DropTime.sendKeys(dateFormat.format(date));

										// --Click on Deliver
										Driver.findElement(By.id("btnsavedelivery")).click();
										logger.info("Clicked on Deliver");

										try {
											wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
											WebElement Error = Driver.findElement(By.id("errorid"));
											if (Error.isDisplayed()) {
												logger.info("Validation displayed==" + Error.getText());
												// --Signed For By
												Driver.findElement(By.id("txtSignature")).clear();
												Driver.findElement(By.id("txtSignature")).sendKeys("RV");
												logger.info("Enter signature");

												// --Click on DELIVER
												Driver.findElement(By.id("btnsavedelivery")).click();
												logger.info("Clicked on DELIVER");
											}
										} catch (Exception signedby) {
											logger.info("Validation for SignedBy is not displayed");

										}

										try {
											wait.until(ExpectedConditions
													.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
											Driver.findElement(By.id("iddataok")).click();
											logger.info("Click on OK button of Dialogue box");

											wait.until(ExpectedConditions.invisibilityOfElementLocated(
													By.xpath("//*[@class=\"ajax-loadernew\"]")));

										} catch (Exception Del) {
											logger.info("Dialogue is not present");
										}

										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.id("txtBasicSearch2")));
										PUID = getData("OrderProcessing", row1, 1);
										Driver.findElement(By.id("txtBasicSearch2")).clear();
										logger.info("Cleared search input");
										Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
										logger.info("Enter pickupID in search input");
										Driver.findElement(By.id("btnGXNLSearch2")).click();
										logger.info("Click on Search button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
										try {
											wait.until(ExpectedConditions
													.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
											WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
											if (NoData.isDisplayed()) {
												System.out.println(
														"Job is moved to VERIFY CUSTOMER BILL stage successfully");
												logger.info("Job is moved to VERIFY CUSTOMER BILL stage successfully");

											}
										} catch (Exception Deliver) {
											System.out.println(
													"Job is not moved to VERIFY CUSTOMER BILL stage successfully");
											logger.info("Job is not moved to VERIFY CUSTOMER BILL stage successfully");

										}
									}

								}

							}
						}

					} else if (Orderstage.equalsIgnoreCase("Confirm Pull Alert")
							|| Orderstage.equalsIgnoreCase("CONF PULL ALERT")) {
						logger.info("Job is moved to Confirm Pull Alert stage successfully");

						// --Confirm Pull Alert
						String stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
						logger.info("stage=" + stage);
						logger.info("If order stage is Confirm Pull Alert.....");
						getScreenshot(Driver, "ConfirmPullAlert_" + PUID);

						// --Click on Accept
						Driver.findElement(By.id("idiconaccept")).click();
						logger.info("Clicked on Accept button");

						try {
							wait.until(ExpectedConditions
									.visibilityOfElementLocated(By.xpath("//*[@ng-message=\"required\"]")));
							logger.info("Validation Message is=="
									+ Driver.findElement(By.xpath("//*[@ng-message=\"required\"]")).getText());
							// --Spoke with
							Driver.findElement(By.id("idConfPullAlertForm")).sendKeys("Ravina Oza");
							logger.info("Entered spoke with");
							// --Click on Accept
							Driver.findElement(By.id("idiconaccept")).click();
							logger.info("Clicked on Accept button");

						} catch (Exception e) {
							logger.info("Validation Message is not displayed");

						}
						// --Search again from Inventory Tab
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
						PUID = getData("OrderProcessing", row1, 1);
						Driver.findElement(By.id("txtBasicSearch")).clear();
						logger.info("Clear search input");
						Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
						logger.info("Enter pickupID in search input");
						Driver.findElement(By.id("btnSearch2")).click();
						logger.info("Click on Search button");

						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is not found in Inventory Tab");
								Driver.findElement(By.xpath("//*[@id=\"operation\" and  text()='Operations']")).click();
								logger.info("Click on Operation tab");
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
								PUID = getData("OrderProcessing", row1, 1);
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								logger.info("Clear search input");
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								logger.info("Enter PickUpID in Search input");
								Driver.findElement(By.id("btnGXNLSearch2")).click();
								logger.info("Click on Search button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							}
						} catch (Exception Tab) {
							logger.info("Job is exist");

						}
						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is not moved to Confirm Pull stage successfully");
							}
						} catch (Exception e) {
							logger.info("Job is moved to Confirm Pull stage successfully");

							// --Confirm Pull stage
							stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
							logger.info("stage=" + stage);
							getScreenshot(Driver, "ConfirmPull_" + PUID);
							// --Label generation
							Driver.findElement(By.id("idiconprint")).click();
							logger.info("Clicked on Label Generation");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							// Handle Label Generation window
							String WindowHandlebefore = Driver.getWindowHandle();
							for (String windHandle : Driver.getWindowHandles()) {
								Driver.switchTo().window(windHandle);
								logger.info("Switched to Label generation window");
								Thread.sleep(5000);
								getScreenshot(Driver, "Labelgeneration" + PUID);
							}
							Driver.close();
							logger.info("Closed Label generation window");

							Driver.switchTo().window(WindowHandlebefore);
							logger.info("Switched to main window");

							// --Save button
							WebElement Save = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Save).build().perform();
							Save.click();
							logger.info("Clicked on Save button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.id("idPartPullDttmValidation")));
								String ValMsg = Driver.findElement(By.id("idPartPullDttmValidation")).getText();
								logger.info("Validation Message=" + ValMsg);
								// --ZoneID
								String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]"))
										.getText();
								logger.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}

								// --Part Pull Date
								WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
								PartPullDate.clear();
								Date date = new Date();
								DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullDate.sendKeys(dateFormat.format(date));
								PartPullDate.sendKeys(Keys.TAB);

								// --Part Pull Time
								WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
								PartPullTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullTime.sendKeys(dateFormat.format(date));

								// --Save button
								Save = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Save).build().perform();
								Save.click();
								logger.info("Clicked on Save button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							} catch (Exception eDataB) {
								logger.info("Validation Message is not displayed");
							}

							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(
										By.xpath("//label[contains(@class,'error-messages')]")));
								logger.info("ErroMsg is Displayed=" + Driver
										.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());
								Thread.sleep(2000);

								// --Get the serial NO
								String SerialNo = Driver.findElement(By.xpath("//*[@ng-bind=\"segment.SerialNo\"]"))
										.getText();
								logger.info("Serial No of Part is==" + SerialNo + "\n");
								// enter serial number in scan
								Driver.findElement(By.id("txtBarcode")).clear();
								Driver.findElement(By.id("txtBarcode")).sendKeys(SerialNo);
								Driver.findElement(By.id("txtBarcode")).sendKeys(Keys.TAB);
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								logger.info("Entered serial No in scan barcode");
								// --Save button
								Save = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Save).build().perform();
								Save.click();
								logger.info("Clicked on Save button");
							} catch (Exception SerialNo) {
								logger.info("Validation for Serial No is not displayed");

							}

							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(
										By.xpath("//label[contains(@class,'error-messages')]")));
								logger.info("ErroMsg is Displayed=" + Driver
										.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());
								// --ZoneID
								String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]"))
										.getText();
								logger.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}

								// --Part Pull Date
								WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
								PartPullDate.clear();
								Date date = new Date();
								DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullDate.sendKeys(dateFormat.format(date));
								PartPullDate.sendKeys(Keys.TAB);

								// --Part Pull Time
								WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
								PartPullTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PartPullTime.sendKeys(dateFormat.format(date));

							} catch (Exception Time) {
								logger.info("Time validation is not displayed-Time is as per timeZone");
							}

							// --Checking Error issue
							dbNullError();

							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
							PUID = getData("OrderProcessing", row1, 1);
							Driver.findElement(By.id("txtBasicSearch")).clear();
							logger.info("Clear search input");
							Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
							logger.info("Enter pickupID in search input");
							Driver.findElement(By.id("btnSearch2")).click();
							logger.info("Click on Search button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not found in Inventory Tab");
									// --Go to Operation tab
									Driver.findElement(By.xpath("//*[@id=\"operation\" and  text()='Operations']"))
											.click();
									logger.info("Click on Operation Tab");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
									PUID = getData("OrderProcessing", row1, 1);
									Driver.findElement(By.id("txtBasicSearch2")).clear();
									Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
									Driver.findElement(By.id("btnGXNLSearch2")).click();
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								}
							} catch (Exception Tab) {
								logger.info("Job is exist");

							}

							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not moved to PICKUP stage successfully");
								}
							} catch (Exception eDataA) {
								logger.info("Job is moved to PICKUP stage successfully");

								// --Pickup
								Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]"))
										.getText();
								logger.info("Current stage of the order is=" + Orderstage);

								// --Enter PickUp Time

								String ZOneID = Driver.findElement(By.id("spanTimezoneId")).getText();
								logger.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";

								}

								WebElement PUPTime = Driver.findElement(By.id("txtActualPickUpTime"));
								PUPTime.clear();
								Date date = new Date();
								DateFormat dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								PUPTime.sendKeys(dateFormat.format(date));
								Driver.findElement(By.id("lnksave")).click();
								logger.info("Clicked on PICKUP button");
								Thread.sleep(2000);
								try {
									wait.until(ExpectedConditions
											.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
									Driver.findElement(By.id("iddataok")).click();
									logger.info("Clicked on Yes button");

								} catch (Exception Dialogue) {
									logger.info("Dialogue is not exist");
								}
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								Driver.findElement(By.id("btnGXNLSearch2")).click();
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										logger.info("Job is not moved to Deliver stage successfully");
									}
								} catch (Exception eNoData) {
									logger.info("Job is moved to Deliver stage successfully");
									getScreenshot(Driver, "Deliver_" + PUID);

									// --Enter Drop Time
									ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
									logger.info("ZoneID of is==" + ZOneID);
									logger.info("ZoneID of is==" + ZOneID);

									if (ZOneID.equalsIgnoreCase("EDT")) {
										ZOneID = "America/New_York";
									} else if (ZOneID.equalsIgnoreCase("CDT")) {
										ZOneID = "CST";
									}
									WebElement DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
									DropTime.clear();
									date = new Date();
									dateFormat = new SimpleDateFormat("HH:mm");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logger.info(dateFormat.format(date));
									DropTime.sendKeys(dateFormat.format(date));

									// --Click on Deliver
									Driver.findElement(By.id("btnsavedelivery")).click();
									logger.info("Clicked on Deliver");

									try {
										wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
										WebElement Error = Driver.findElement(By.id("errorid"));
										if (Error.isDisplayed()) {
											logger.info("Validation displayed==" + Error.getText());
											// --Signed For By
											Driver.findElement(By.id("txtSignature")).clear();
											Driver.findElement(By.id("txtSignature")).sendKeys("RV");
											logger.info("Enter signature");

											// --Click on DELIVER
											Driver.findElement(By.id("btnsavedelivery")).click();
											logger.info("Clicked on DELIVER");
										}
									} catch (Exception signedby) {
										logger.info("Validation for SignedBy is not displayed");

									}

									try {
										wait.until(ExpectedConditions
												.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
										Driver.findElement(By.id("iddataok")).click();
										logger.info("Click on OK button of Dialogue box");

										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));

									} catch (Exception Del) {
										logger.info("Dialogue is not present");
									}

									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
									PUID = getData("OrderProcessing", row1, 1);
									Driver.findElement(By.id("txtBasicSearch2")).clear();
									logger.info("Cleared search input");
									Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
									logger.info("Enter pickupID in search input");
									Driver.findElement(By.id("btnGXNLSearch2")).click();
									logger.info("Click on Search button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									try {
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
										WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
										if (NoData.isDisplayed()) {
											System.out
													.println("Job is moved to VERIFY CUSTOMER BILL stage successfully");
											logger.info("Job is moved to VERIFY CUSTOMER BILL stage successfully");

										}
									} catch (Exception Deliver) {
										System.out
												.println("Job is not moved to VERIFY CUSTOMER BILL stage successfully");
										logger.info("Job is not moved to VERIFY CUSTOMER BILL stage successfully");

									}
								}

							}

						}
					} else if (Orderstage.equalsIgnoreCase("Confirm Pull")) {
						logger.info("Job is moved to Confirm Pull stage successfully");

						// --Confirm Pull stage
						String stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
						logger.info("stage=" + stage);
						getScreenshot(Driver, "ConfirmPull_" + PUID);
						// --Label generation
						Driver.findElement(By.id("idiconprint")).click();
						logger.info("Clicked on Label Generation");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						// Handle Label Generation window
						String WindowHandlebefore = Driver.getWindowHandle();
						for (String windHandle : Driver.getWindowHandles()) {
							Driver.switchTo().window(windHandle);
							logger.info("Switched to Label generation window");
							Thread.sleep(5000);
							getScreenshot(Driver, "Labelgeneration" + PUID);
						}
						Driver.close();
						logger.info("Closed Label generation window");

						Driver.switchTo().window(WindowHandlebefore);
						logger.info("Switched to main window");

						// --Save button
						WebElement Save = Driver.findElement(By.id("idiconsave"));
						act.moveToElement(Save).build().perform();
						Save.click();
						logger.info("Clicked on Save button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.id("idPartPullDttmValidation")));
							String ValMsg = Driver.findElement(By.id("idPartPullDttmValidation")).getText();
							logger.info("Validation Message=" + ValMsg);
							// --ZoneID
							String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]"))
									.getText();
							logger.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							}

							// --Part Pull Date
							WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
							PartPullDate.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullDate.sendKeys(dateFormat.format(date));
							PartPullDate.sendKeys(Keys.TAB);

							// --Part Pull Time
							WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
							PartPullTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullTime.sendKeys(dateFormat.format(date));
							// --Save button
							Save = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Save).build().perform();
							Save.click();
							logger.info("Clicked on Save button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						} catch (Exception eDataB) {
							logger.info("Validation Message is not displayed");
						}

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//label[contains(@class,'error-messages')]")));
							logger.info("ErroMsg is Displayed=" + Driver
									.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());
							Thread.sleep(2000);

							// --Get the serial NO
							String SerialNo = Driver.findElement(By.xpath("//*[@ng-bind=\"segment.SerialNo\"]"))
									.getText();
							logger.info("Serial No of Part is==" + SerialNo + "\n");
							// enter serial number in scan
							Driver.findElement(By.id("txtBarcode")).clear();
							Driver.findElement(By.id("txtBarcode")).sendKeys(SerialNo);
							Driver.findElement(By.id("txtBarcode")).sendKeys(Keys.TAB);
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							logger.info("Entered serial No in scan barcode");
							// --Save button
							Save = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Save).build().perform();
							Save.click();
							logger.info("Clicked on Save button");
						} catch (Exception SerialNo) {
							logger.info("Validation for Serial No is not displayed");

						}

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//label[contains(@class,'error-messages')]")));
							logger.info("ErroMsg is Displayed=" + Driver
									.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());
							// --ZoneID
							String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]"))
									.getText();
							logger.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							}

							// --Part Pull Date
							WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
							PartPullDate.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullDate.sendKeys(dateFormat.format(date));
							PartPullDate.sendKeys(Keys.TAB);

							// --Part Pull Time
							WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
							PartPullTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PartPullTime.sendKeys(dateFormat.format(date));
							// --Save button
							Save = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Save).build().perform();
							Save.click();
							logger.info("Clicked on Save button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception Time) {
							logger.info("Time validation is not displayed-Time is as per timeZone");
						}
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
						PUID = getData("OrderProcessing", row1, 1);
						Driver.findElement(By.id("txtBasicSearch")).clear();
						logger.info("Clear search input");
						Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
						logger.info("Enter pickupID in search input");
						Driver.findElement(By.id("btnSearch2")).click();
						logger.info("Click on Search button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is not found in Inventory Tab");
								// --Go to Operation tab
								Driver.findElement(By.xpath("//*[@id=\"operation\" and  text()='Operations']")).click();
								logger.info("Click on Operation Tab");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
								PUID = getData("OrderProcessing", row1, 1);
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								Driver.findElement(By.id("btnGXNLSearch2")).click();
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							}
						} catch (Exception Tab) {
							logger.info("Job is exist");

						}

						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is not moved to PICKUP stage successfully");
							}
						} catch (Exception eDataA) {
							logger.info("Job is moved to PICKUP stage successfully");

							// --Pickup
							Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
							logger.info("Current stage of the order is=" + Orderstage);

							// --Enter PickUp Time

							String ZOneID = Driver.findElement(By.id("spanTimezoneId")).getText();
							logger.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";

							}

							WebElement PUPTime = Driver.findElement(By.id("txtActualPickUpTime"));
							PUPTime.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							PUPTime.sendKeys(dateFormat.format(date));
							Driver.findElement(By.id("lnksave")).click();
							logger.info("Clicked on PICKUP button");
							Thread.sleep(2000);
							try {
								wait.until(ExpectedConditions
										.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
								Driver.findElement(By.id("iddataok")).click();
								logger.info("Clicked on Yes button");

							} catch (Exception Dialogue) {
								logger.info("Dialogue is not exist");
							}
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							Driver.findElement(By.id("btnGXNLSearch2")).click();
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not moved to Deliver stage successfully");
								}
							} catch (Exception eNoData) {
								logger.info("Job is moved to Deliver stage successfully");
								getScreenshot(Driver, "Deliver_" + PUID);

								// --Enter Drop Time
								ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
								logger.info("ZoneID of is==" + ZOneID);
								logger.info("ZoneID of is==" + ZOneID);

								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}
								WebElement DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
								DropTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logger.info(dateFormat.format(date));
								DropTime.sendKeys(dateFormat.format(date));

								// --Click on Deliver
								Driver.findElement(By.id("btnsavedelivery")).click();
								logger.info("Clicked on Deliver");

								try {
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
									WebElement Error = Driver.findElement(By.id("errorid"));
									if (Error.isDisplayed()) {
										logger.info("Validation displayed==" + Error.getText());
										// --Signed For By
										Driver.findElement(By.id("txtSignature")).clear();
										Driver.findElement(By.id("txtSignature")).sendKeys("RV");
										logger.info("Enter signature");

										// --Click on DELIVER
										Driver.findElement(By.id("btnsavedelivery")).click();
										logger.info("Clicked on DELIVER");
									}
								} catch (Exception signedby) {
									logger.info("Validation for SignedBy is not displayed");

								}

								try {
									wait.until(ExpectedConditions
											.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
									Driver.findElement(By.id("iddataok")).click();
									logger.info("Click on OK button of Dialogue box");

									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								} catch (Exception Del) {
									logger.info("Dialogue is not present");
								}

								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
								PUID = getData("OrderProcessing", row1, 1);
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								logger.info("Cleared search input");
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								logger.info("Enter pickupID in search input");
								Driver.findElement(By.id("btnGXNLSearch2")).click();
								logger.info("Click on Search button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										System.out.println("Job is moved to VERIFY CUSTOMER BILL stage successfully");
										logger.info("Job is moved to VERIFY CUSTOMER BILL stage successfully");

									}
								} catch (Exception Deliver) {
									System.out.println("Job is not moved to VERIFY CUSTOMER BILL stage successfully");
									logger.info("Job is not moved to VERIFY CUSTOMER BILL stage successfully");

								}
							}

						}

					} else if (Orderstage.equalsIgnoreCase("PICKUP")) {
						logger.info("Job is moved to PICKUP stage successfully");

						// --Pickup
						Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
						logger.info("Current stage of the order is=" + Orderstage);

						// --Enter PickUp Time

						String ZOneID = Driver.findElement(By.id("spanTimezoneId")).getText();
						logger.info("ZoneID of is==" + ZOneID);
						if (ZOneID.equalsIgnoreCase("EDT")) {
							ZOneID = "America/New_York";
						} else if (ZOneID.equalsIgnoreCase("CDT")) {
							ZOneID = "CST";

						}

						WebElement PUPTime = Driver.findElement(By.id("txtActualPickUpTime"));
						PUPTime.clear();
						Date date = new Date();
						DateFormat dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logger.info(dateFormat.format(date));
						PUPTime.sendKeys(dateFormat.format(date));
						Driver.findElement(By.id("lnksave")).click();
						logger.info("Clicked on PICKUP button");
						Thread.sleep(2000);
						try {
							wait.until(
									ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
							Driver.findElement(By.id("iddataok")).click();
							logger.info("Clicked on Yes button");

						} catch (Exception Dialogue) {
							logger.info("Dialogue is not exist");
						}
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						Driver.findElement(By.id("btnGXNLSearch2")).click();
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is not moved to Deliver stage successfully");
							}
						} catch (Exception eNoData) {
							logger.info("Job is moved to Deliver stage successfully");
							getScreenshot(Driver, "Deliver_" + PUID);

							// --Enter Drop Time
							ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
							logger.info("ZoneID of is==" + ZOneID);
							logger.info("ZoneID of is==" + ZOneID);

							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							}
							WebElement DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
							DropTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logger.info(dateFormat.format(date));
							DropTime.sendKeys(dateFormat.format(date));

							// --Click on Deliver
							Driver.findElement(By.id("btnsavedelivery")).click();
							logger.info("Clicked on Deliver");

							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
								WebElement Error = Driver.findElement(By.id("errorid"));
								if (Error.isDisplayed()) {
									logger.info("Validation displayed==" + Error.getText());
									// --Signed For By
									Driver.findElement(By.id("txtSignature")).clear();
									Driver.findElement(By.id("txtSignature")).sendKeys("RV");
									logger.info("Enter signature");

									// --Click on DELIVER
									Driver.findElement(By.id("btnsavedelivery")).click();
									logger.info("Clicked on DELIVER");
								}
							} catch (Exception signedby) {
								logger.info("Validation for SignedBy is not displayed");

							}

							try {
								wait.until(ExpectedConditions
										.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
								Driver.findElement(By.id("iddataok")).click();
								logger.info("Click on OK button of Dialogue box");

								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

							} catch (Exception Del) {
								logger.info("Dialogue is not present");
							}

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							PUID = getData("OrderProcessing", row1, 1);
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							logger.info("Cleared search input");
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							logger.info("Enter pickupID in search input");
							Driver.findElement(By.id("btnGXNLSearch2")).click();
							logger.info("Click on Search button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									System.out.println("Job is moved to VERIFY CUSTOMER BILL stage successfully");
									logger.info("Job is moved to VERIFY CUSTOMER BILL stage successfully");

								}
							} catch (Exception Deliver) {
								System.out.println("Job is not moved to VERIFY CUSTOMER BILL stage successfully");
								logger.info("Job is not moved to VERIFY CUSTOMER BILL stage successfully");

							}
						}

					} else if (Orderstage.equalsIgnoreCase("Deliver")) {
						logger.info("Job is moved to Deliver stage successfully");
						getScreenshot(Driver, "Deliver_" + PUID);

						// --Enter Drop Time
						String ZOneID = Driver.findElement(By.id("lblactdltz")).getText();
						logger.info("ZoneID of is==" + ZOneID);
						logger.info("ZoneID of is==" + ZOneID);

						if (ZOneID.equalsIgnoreCase("EDT")) {
							ZOneID = "America/New_York";
						} else if (ZOneID.equalsIgnoreCase("CDT")) {
							ZOneID = "CST";
						}
						WebElement DropTime = Driver.findElement(By.id("txtActualDeliveryTme"));
						DropTime.clear();
						Date date = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logger.info(dateFormat.format(date));
						DropTime.sendKeys(dateFormat.format(date));

						// --Click on Deliver
						Driver.findElement(By.id("btnsavedelivery")).click();
						logger.info("Clicked on Deliver");

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
							WebElement Error = Driver.findElement(By.id("errorid"));
							if (Error.isDisplayed()) {
								logger.info("Validation displayed==" + Error.getText());
								// --Signed For By
								Driver.findElement(By.id("txtSignature")).clear();
								Driver.findElement(By.id("txtSignature")).sendKeys("RV");
								logger.info("Enter signature");

								// --Click on DELIVER
								Driver.findElement(By.id("btnsavedelivery")).click();
								logger.info("Clicked on DELIVER");
							}
						} catch (Exception signedby) {
							logger.info("Validation for SignedBy is not displayed");

						}

						try {
							wait.until(
									ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
							Driver.findElement(By.id("iddataok")).click();
							logger.info("Click on OK button of Dialogue box");

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception Del) {
							logger.info("Dialogue is not present");
						}

						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						PUID = getData("OrderProcessing", row1, 1);
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						logger.info("Cleared search input");
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						logger.info("Enter pickupID in search input");
						Driver.findElement(By.id("btnGXNLSearch2")).click();
						logger.info("Click on Search button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								System.out.println("Job is moved to VERIFY CUSTOMER BILL stage successfully");
								logger.info("Job is moved to VERIFY CUSTOMER BILL stage successfully");

							}
						} catch (Exception Deliver) {
							System.out.println("Job is not moved to VERIFY CUSTOMER BILL stage successfully");
							logger.info("Job is not moved to VERIFY CUSTOMER BILL stage successfully");

						}
					}

				} else {
					logger.info("Unknown Service found");

				}

			} catch (Exception NoData1) {
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
					WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
					if (NoData.isDisplayed()) {
						logger.info("Job is not exist with the search parameters");

					}
				} catch (Exception OnBoard) {
					try {
						wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//*[@class=\"pull-left\"]/strong")));
						WebElement ONBOARD = Driver.findElement(By.xpath("//*[@class=\"pull-left\"]/strong"));
						if (ONBOARD.getText().equalsIgnoreCase("ON BOARD")) {
							logger.info("SD Job is on OnBoard stage");

						}
					} catch (Exception stage) {
						String Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]"))
								.getText();
						logger.info("Current stage of the order is=" + Orderstage);
						logger.info("Issue in Order stage==" + Orderstage);
						getScreenshot(Driver, "StageIssue_" + Orderstage);

					}
				}
			}

		}

		logger.info("===Order Processing Test End===");
		msg.append("===Order Processing Test End===" + "\n\n");

	}

	public static boolean retryingFindClick(WebElement webElement) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 2) {
			try {
				webElement.click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
		return result;
	}

	public void memo(String PID) throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		try {
			/*
			 * logger.info("===Memo Test Start==="); msg.append("===Memo Test Start===" +
			 * "\n\n");
			 */

			Driver.findElement(By.xpath("//*[@id=\"hlkMemo\"][contains(text(),'Memo')]")).click();
			logger.info("Clicked on Memo");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"MemoForm\"]")));
			getScreenshot(Driver, "Memo_" + PID);

			// --Total no of existing memo
			String Memoheader = Driver.findElement(By.xpath("//*[contains(@class,'popupheadeing')]/strong")).getText();
			String NoOfMemo = Memoheader.split(" ")[1];

			logger.info("Total no of memo is/are=" + NoOfMemo);

			// --Enter value in memo
			Driver.findElement(By.id("txtMemoNA")).sendKeys("Confirm Pu Alert stage from NetAgent");
			logger.info("Entered value in memo");
			// --Save
			Driver.findElement(By.id("btnAgentMemoNA")).click();
			logger.info("Clicked on Save button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			try {
				// --Close
				WebElement memoClose = Driver.findElement(By.id("idanchorclose"));

				js.executeScript("arguments[0].click();", memoClose);
				logger.info("Clicked on Close button of Memo");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@ng-form=\"MemoForm\"]")));
				Thread.sleep(2000);
			} catch (Exception CLoseee) {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"MemoForm\"]")));
				// --Close
				WebElement memoClose = Driver.findElement(By.id("idanchorclose"));

				act.moveToElement(memoClose).build().perform();
				act.moveToElement(memoClose).click().perform();
				logger.info("Clicked on Close button of Memo");
				Thread.sleep(2000);
			}
			/*
			 * logger.info("===Memo Test End==="); msg.append("===Memo Test End===" +
			 * "\n\n");
			 */

			logger.info("Memo Test=PASS");
			msg.append("Memo Test=PASS" + "\n");
		} catch (Exception memonotexist) {
			logger.error(memonotexist);
			getScreenshot(Driver, "MemoNtExist_error");
			logger.info("Memo Test=FAIL");
			msg.append("Memo Test=FAIL" + "\n");
			/*
			 * logger.info("Memo is not exist"); msg.append("Memo is not exist" + "\n\n");
			 */
		}

	}

	public void notification(String PID) throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		try {
			/*
			 * logger.info("===Notification Test Start===");
			 * msg.append("===Notification Test Start===" + "\n\n");
			 */

			WebElement Notification = Driver.findElement(By.id("hlkNotification"));
			act.moveToElement(Notification).build().perform();
			js.executeScript("arguments[0].click();", Notification);
			logger.info("Clicked on Notification");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"NotificationDetailform\"]")));

			getScreenshot(Driver, "Notification_" + PID);

			try {
				// --Close
				WebElement memoClose = Driver.findElement(By.id("idanchorclose"));

				js.executeScript("arguments[0].click();", memoClose);
				logger.info("Clicked on Close button of Notification");
				wait.until(ExpectedConditions
						.invisibilityOfElementLocated(By.xpath("//*[@ng-form=\"NotificationDetailform\"]")));
				Thread.sleep(2000);
			} catch (Exception CLoseee) {
				wait.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"NotificationDetailform\"]")));
				// --Close
				WebElement memoClose = Driver.findElement(By.id("idanchorclose"));

				act.moveToElement(memoClose).build().perform();
				act.moveToElement(memoClose).click().perform();
				logger.info("Clicked on Close button of Notification");
				Thread.sleep(2000);
			}

			/*
			 * logger.info("===Notification Test End===");
			 * msg.append("===Notification Test End===" + "\n\n");
			 */

			logger.info("Notification Test=PASS");
			msg.append("Notification Test=PASS" + "\n");
		} catch (Exception NotificationE) {
			logger.error(NotificationE);
			getScreenshot(Driver, "Notification_error");
			logger.info("Notification Test=FAIL");
			msg.append("Notification Test=FAIL" + "\n");
			/*
			 * logger.info("Notification is not exist");
			 * msg.append("Notification is not exist" + "\n\n");
			 */
		}

	}

	public void upload(String PID) throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);
		try {
			/*
			 * logger.info("===Upload Test Start==="); msg.append("===Upload Test Start==="
			 * + "\n\n");
			 */
			WebElement Upload = Driver.findElement(By.id("hlkUploadDocument"));
			act.moveToElement(Upload).build().perform();
			js.executeScript("arguments[0].click();", Upload);
			logger.info("Clicked on Upload");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(
					ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"DocDetailsForm\"]")));
			getScreenshot(Driver, "Upload_" + PID);

			// --Click on Plus sign
			wait.until(ExpectedConditions.elementToBeClickable(By.id("hlkaddUpload")));
			Driver.findElement(By.id("hlkaddUpload")).click();
			logger.info("Click on plus sign");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtDocName")));
			// --Enter Doc name
			Driver.findElement(By.id("txtDocName")).sendKeys("AutoDocument");
			logger.info("Enter doc name");

			WebElement SelectFile = Driver.findElement(By.id("btnSelectFile"));
			act.moveToElement(SelectFile).build().perform();
			js.executeScript("arguments[0].click();", SelectFile);
			logger.info("Click on select file");

			Thread.sleep(2000);

			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"userForm\"]")));

			// --Select File
			Thread.sleep(2000);
			String Fpath = System.getProperty("user.dir") + "\\src\\main\\resources\\Job Upload Doc STG.xls";
			WebElement InFile = Driver.findElement(By.id("inputfile"));
			InFile.sendKeys(Fpath);
			logger.info("Select the file");
			Thread.sleep(2000);

			// --Click on Upload btn
			WebElement BTNUpload = Driver.findElement(By.id("btnUpload"));
			act.moveToElement(BTNUpload).build().perform();
			js.executeScript("arguments[0].click();", BTNUpload);
			logger.info("File is uploaded successfully");

			Thread.sleep(2000);
			try {
				String ErrorMsg = Driver.findElement(By.xpath("ng-bind=\"RenameFileErrorMsg\"")).getText();
				if (ErrorMsg.contains("already exists.Your file was saved as")) {
					logger.info("File already exist in the system");
				}
			} catch (Exception e) {
				logger.info("File is uploaded successfully");
			}
			WebElement UpLoadOK = Driver.findElement(By.id("btnOk"));
			js.executeScript("arguments[0].click();", UpLoadOK);

			Thread.sleep(2000);
			try {
				// --Close
				wait.until(
						ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@ng-form=\"DocDetailsForm\"]")));
			} catch (Exception CLoseee) {
				try {
					// --Close
					WebElement memoClose = Driver.findElement(By.id("idanchorclose"));

					js.executeScript("arguments[0].click();", memoClose);
					logger.info("Clicked on Close button of Upload");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@ng-form=\"DocDetailsForm\"]")));
					Thread.sleep(2000);
				} catch (Exception CLosee) {
					wait.until(ExpectedConditions
							.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"DocDetailsForm\"]")));
					// --Close
					WebElement memoClose = Driver.findElement(By.id("idanchorclose"));

					act.moveToElement(memoClose).build().perform();
					act.moveToElement(memoClose).click().perform();
					logger.info("Clicked on Close button of Upload");
					Thread.sleep(2000);
				}
			}

			/*
			 * logger.info("===Upload Test End==="); msg.append("===Upload Test End===" +
			 * "\n\n");
			 */
			logger.info("Upload Test=PASS");
			msg.append("Upload Test=PASS" + "\n");
		} catch (Exception Upload) {

			logger.error(Upload);
			getScreenshot(Driver, "Upload_error");
			logger.info("Upload Test=FAIL");
			msg.append("Upload Test=FAIL" + "\n");

			/*
			 * logger.info("Upload is not exist"); msg.append("Upload is not exist" +
			 * "\n\n");
			 */
		}

	}

	public void map(String PID) throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		try {
			/*
			 * logger.info("===Map Test Start==="); msg.append("===Map Test Start===" +
			 * "\n\n");
			 */

			Driver.findElement(By.id("hlkMap")).click();
			logger.info("Clicked on Map");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			Thread.sleep(5000);
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
					By.xpath("//*[@class=\"panel-title\"]//strong[contains(text(),'Map')]")));
			getScreenshot(Driver, "Map_" + PID);

			try {
				// --Close
				WebElement memoClose = Driver.findElement(By.id("idMapClose"));

				js.executeScript("arguments[0].click();", memoClose);
				logger.info("Clicked on Close button of Map");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(
						By.xpath("//*[@class=\"panel-title\"]//strong[contains(text(),'Map')]")));
				Thread.sleep(2000);
			} catch (Exception CLoseee) {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
						By.xpath("//*[@class=\"panel-title\"]//strong[contains(text(),'Map')]")));
				// --Close
				WebElement memoClose = Driver.findElement(By.id("idMapClose"));

				act.moveToElement(memoClose).build().perform();
				act.moveToElement(memoClose).click().perform();
				logger.info("Clicked on Close button of Map");
				Thread.sleep(2000);
			}

			/*
			 * logger.info("===Map Test End==="); msg.append("===Map Test End===" + "\n\n");
			 */

			logger.info("Map Test=PASS");
			msg.append("Map Test=PASS" + "\n");

		} catch (Exception Map) {
			logger.error(Map);
			getScreenshot(Driver, "Map_error");
			logger.info("Map Test=FAIL");
			msg.append("Map Test=FAIL" + "\n");

			/*
			 * logger.info("Map is not exist"); msg.append("Map is not exist" + "\n\n");
			 */
		}

	}

	public void addPackage(String PID) throws IOException {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		logger.info("===Add Package Test Start===");
		msg.append("===Add Package Test Start===" + "\n\n");

		// Get the total package exist in current Job
		List<WebElement> TotalPackage = Driver.findElements(
				By.xpath("//*[@id=\"PackageInformationTable\"]/tbody/tr[contains(@ng-repeat,'PickupItemsDetail')]"));
		int BTotalPack = TotalPackage.size();
		logger.info("No of Packages before Add Package==" + BTotalPack);

		WebElement AddPackage = Driver.findElement(By.id("idaddimg"));
		act.moveToElement(AddPackage).click().perform();
		logger.info("Clicked on Add Package");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
		getScreenshot(Driver, "AddPackage" + PID);

		TotalPackage = Driver.findElements(
				By.xpath("//*[@id=\"PackageInformationTable\"]/tbody/tr[contains(@ng-repeat,'PickupItemsDetail')]"));
		int ATotalPack = TotalPackage.size();
		logger.info("No of Packages before Add Package==" + ATotalPack);

		if (BTotalPack == ATotalPack) {
			logger.info("New row for add package is displayed");

		} else {
			logger.info("New row for add package is not displayed");

		}

		// --Close
		WebElement memoClose = Driver.findElement(By.id("idMapClose"));
		js.executeScript("arguments[0].click();", memoClose);
		logger.info("Clicked on Close button of Map");

		logger.info("===Add Package Test End===");
		msg.append("===Add Package Test End===" + "\n\n");

	}

	public void shipLabel(String PickUpID) throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		try {
			/*
			 * logger.info("===ShipLabel Test Start===");
			 * msg.append("===ShipLabel Test Start===" + "\n\n");
			 */
			// --Ship Label Services
			Driver.findElement(By.linkText("Ship Label Services")).click();
			logger.info("Clicked on Ship Label Services");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"SLForm\"]")));
			getScreenshot(Driver, "ShipLabelServices_" + PickUpID);

			try {
				// --Send Email
				Driver.findElement(By.id("txtEmailLabelto")).sendKeys("Ravina.prajapati@samyak.com");
				logger.info("Entered EmailID");
				Driver.findElement(By.id("btnSend")).click();
				logger.info("Clicked on Send button");
				Thread.sleep(2000);
				// ErrorMsg
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@ng-bind=\"ErrorMsg\"]")));
					logger.info("ErroMsg is Displayed="
							+ Driver.findElement(By.xpath("//*[@ng-bind=\"ErrorMsg\"]")).getText());

					// -- check the checkbox
					Driver.findElement(By.id("chkbShip_0")).click();
					logger.info("Checked the shiplabel");
					Thread.sleep(2000);
					// --Send Email
					Driver.findElement(By.id("txtEmailLabelto")).clear();
					Driver.findElement(By.id("txtEmailLabelto")).sendKeys("Ravina.prajapati@samyak.com");
					logger.info("Entered EmailID");
					Driver.findElement(By.id("btnSend")).click();
					logger.info("Clicked on Send button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
					System.out
							.println("Success Message is Displayed=" + Driver.findElement(By.id("success")).getText());

				} catch (Exception e) {
					logger.info("Error Message is not displayed");
				}
				// --Print button
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[@id=\"scrollboxIframe\"]//button[@id=\"btnPrint\"]")));
				String TrackingNo = Driver.findElement(By.xpath("//*[contains(@ng-bind,'Your tracking number is')]"))
						.getText();
				logger.info("Tracking No==" + TrackingNo);
				Driver.findElement(By.xpath("//*[@id=\"scrollboxIframe\"]//button[@id=\"btnPrint\"]")).click();
				logger.info("Clicked on Print button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
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

			} catch (Exception noShipLabel) {
				logger.info("There is no Ship Label generated");
				WebElement Label = Driver.findElement(By.xpath("//*[@ng-if=\"ShowTransSection\"]"));
				logger.info("Label is==" + Label);

			}

			// --Close Ship Label Service pop up

			try {
				// --Close
				WebElement memoClose = Driver.findElement(By.id("idanchorclose"));
				js.executeScript("arguments[0].click();", memoClose);
				logger.info("Clicked on Close button of ShipLabel");
				Thread.sleep(2000);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@ng-form=\\\"SLForm\\\"]")));
				Thread.sleep(2000);
			} catch (Exception CLoseee) {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"SLForm\"]")));

				// --Close
				WebElement memoClose = Driver.findElement(By.id("idanchorclose"));

				act.moveToElement(memoClose).build().perform();
				act.moveToElement(memoClose).click().perform();
				logger.info("Clicked on Close button of ShipLabel");
				Thread.sleep(2000);
			}

			/*
			 * logger.info("===ShipLabel Test End===");
			 * msg.append("===ShipLabel Test End===" + "\n\n");
			 */
			logger.info("ShipLabel Test=PASS");
			msg.append("ShipLabel Test=PASS" + "\n");

		} catch (Exception ShipLabel) {
			logger.error(ShipLabel);
			getScreenshot(Driver, "ShipLabel_error");
			logger.info("ShipLabel Test=FAIL");
			msg.append("ShipLabel Test=FAIL" + "\n");

			/*
			 * logger.info("ShipLabel is not exist"); msg.append("ShipLabel is not exist" +
			 * "\n\n");
			 */
		}

	}

	public void printPull(String PickUpID) throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(Driver, 30);

		try {
			/*
			 * logger.info("===Print Pull Test Start===");
			 * msg.append("===Print Pull Test Start===" + "\n\n");
			 */

			// --Print pull Ticket
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idprintpullticket")));
				Driver.findElement(By.id("idprintpullticket")).click();
				logger.info("Clicked on Print Pull Ticket");
			} catch (Exception PrintTicc) {
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idprintpull")));
					Driver.findElement(By.id("idprintpull")).click();
					logger.info("Clicked on Print Pull Ticket");
				} catch (Exception PrintPullT3P) {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idprintticket")));
					Driver.findElement(By.id("idprintticket")).click();
					logger.info("Clicked on Print Pull Ticket");
				}

			}

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			// Handle pull Print window

			String WindowHandlebefore = Driver.getWindowHandle();
			for (String windHandle : Driver.getWindowHandles()) {
				Driver.switchTo().window(windHandle);
				logger.info("Switched to Print Pull Ticket window");
				Thread.sleep(5000);
				getScreenshot(Driver, "PrintPullTicket_" + PickUpID);
			}
			Driver.close();
			logger.info("Closed Print Pull Ticket window");

			Driver.switchTo().window(WindowHandlebefore);
			logger.info("Switched to main window");
			Thread.sleep(2000);

			/*
			 * logger.info("===Print Pull Test End===");
			 * msg.append("===Print Pull Test End===" + "\n\n");
			 */
			logger.info("Print Pull Test=PASS");
			msg.append("Print Pull Test=PASS" + "\n");

		} catch (Exception PrintPull) {

			logger.error(PrintPull);
			getScreenshot(Driver, "PrintPull_error");
			logger.info("Print Pull Test=FAIL");
			msg.append("Print Pull Test=FAIL" + "\n");

			/*
			 * logger.info("Print Pull is not exist"); msg.append("Print Pull is not exist"
			 * + "\n\n");
			 */
		}

	}

	public void dbNullError() {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		Actions act = new Actions(Driver);

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
			logger.info("Error is Displayed=" + Driver.findElement(By.id("errorid")).getText());
			logger.info("Unable to process, try again");
			// --Click on Close button
			Driver.findElement(By.id("lnkclose")).click();
			logger.info("Clicked on close button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//td[contains(@aria-label,'Value CONF PULL')]/div")));
				WebElement ConfirmPull = Driver
						.findElement(By.xpath("//td[contains(@aria-label,'Value CONF PULL')]/div"));
				ConfirmPull.click();
				logger.info("Clicked on Job with status 'Conf Pull'");

				// --Save button
				WebElement Save = Driver.findElement(By.id("idiconsave"));
				act.moveToElement(Save).build().perform();
				Save.click();
				logger.info("Clicked on Save button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idPartPullDttmValidation")));
					String ValMsg = Driver.findElement(By.id("idPartPullDttmValidation")).getText();
					logger.info("Validation Message=" + ValMsg);
					// --ZoneID
					String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]")).getText();
					logger.info("ZoneID of is==" + ZOneID);
					if (ZOneID.equalsIgnoreCase("EDT")) {
						ZOneID = "America/New_York";
					} else if (ZOneID.equalsIgnoreCase("CDT")) {
						ZOneID = "CST";
					}

					// --Part Pull Date
					WebElement PartPullDate = Driver.findElement(By.id("idtxtPartPullDate"));
					PartPullDate.clear();
					Date date = new Date();
					DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
					logger.info(dateFormat.format(date));
					PartPullDate.sendKeys(dateFormat.format(date));
					PartPullDate.sendKeys(Keys.TAB);

					// --Part Pull Time
					WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
					PartPullTime.clear();
					date = new Date();
					dateFormat = new SimpleDateFormat("HH:mm");
					dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
					logger.info(dateFormat.format(date));
					PartPullTime.sendKeys(dateFormat.format(date));
					// --Save button
					Save = Driver.findElement(By.id("idiconsave"));
					act.moveToElement(Save).build().perform();
					Save.click();
					logger.info("Clicked on Save button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				} catch (Exception eDataB) {
					logger.info("Validation Message is not displayed");
				}

				try {
					wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.xpath("//label[contains(@class,'error-messages')]")));
					logger.info("ErroMsg is Displayed="
							+ Driver.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());
					Thread.sleep(2000);

					// --Get the serial NO
					String SerialNo = Driver.findElement(By.xpath("//*[@ng-bind=\"segment.SerialNo\"]")).getText();
					logger.info("Serial No of Part is==" + SerialNo + "\n");
					// enter serial number in scan
					Driver.findElement(By.id("txtBarcode")).clear();
					Driver.findElement(By.id("txtBarcode")).sendKeys(SerialNo);
					Driver.findElement(By.id("txtBarcode")).sendKeys(Keys.TAB);
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					logger.info("Entered serial No in scan barcode");
					// --Save button
					Save = Driver.findElement(By.id("idiconsave"));
					act.moveToElement(Save).build().perform();
					Save.click();
					logger.info("Clicked on Save button");
				} catch (Exception SerialNo1) {
					logger.info("Validation for Serial No is not displayed");

				}

				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
					logger.info("Error is Displayed=" + Driver.findElement(By.id("errorid")).getText());
					String ZOneID = Driver.findElement(By.xpath("//span[contains(@ng-bind,'TimezoneId')]")).getText();
					logger.info("ZoneID of is==" + ZOneID);
					if (ZOneID.equalsIgnoreCase("EDT")) {
						ZOneID = "America/New_York";
					} else if (ZOneID.equalsIgnoreCase("CDT")) {
						ZOneID = "CST";
					}
					WebElement PartPullTime = Driver.findElement(By.id("txtPartPullTime"));
					PartPullTime.clear();
					Date date = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
					dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
					logger.info(dateFormat.format(date));
					PartPullTime.sendKeys(dateFormat.format(date));

					// --Save button
					Save = Driver.findElement(By.id("idiconsave"));
					act.moveToElement(Save).build().perform();
					Save.click();
					logger.info("Clicked on Save button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				} catch (Exception DateTime) {
					logger.info("Validation for DateTime greater than is not display, DateTime is as per expected");

				}

			} catch (Exception noCOnfPull) {
				logger.info("There is no job with search PickUpID");

			}

		} catch (Exception PullTicket) {
			logger.info("Error is not Displayed");

		}
	}

}
