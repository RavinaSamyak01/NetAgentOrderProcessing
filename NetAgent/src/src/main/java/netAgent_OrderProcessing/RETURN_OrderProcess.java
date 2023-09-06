package netAgent_OrderProcessing;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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

import netAgent_BasePackage.BaseInit;

public class RETURN_OrderProcess extends BaseInit {

	@Test
	public void orderProcessRETURNJOB() throws EncryptedDocumentException, InvalidFormatException, IOException {

		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		logger.info("=====RETURN Order Processing Test Start=====");
		//msg.append("=====RETURN Order Processing Test Start=====" + "\n\n");

		try {
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

			getScreenshot(Driver, "TaskLog_OperationsRETURN");

			String ServiceID = getData("OrderProcessing", 8, 0);
			logger.info("ServiceID is==" + ServiceID);
			msg.append("ServiceID==" + ServiceID + "\n");
			String PUID = getData("OrderProcessing", 8, 1);
			logger.info("PickUpID is==" + PUID);
			msg.append("PickUpID==" + PUID + "\n");

			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//*[@id=\"operation\"][contains(@class,'active')]")));
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

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
				Driver.findElement(By.id("txtBasicSearch2")).clear();
				logger.info("Clear search input");
				Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
				logger.info("Enter PickUpID in Search input");
				WebElement OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
				act.moveToElement(OPSearch).build().perform();
				js.executeScript("arguments[0].click();", OPSearch);
				logger.info("Click on Search button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				// --Checking editor
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//*[@id=\"hlkMemo\"][contains(text(),'Memo')]")));

				// Ship Label
				OrderProcess OP = new OrderProcess();

				OP.shipLabel(PUID);

				// --Memo
				OP.memo(PUID);

				// -Notification
				OP.notification(PUID);

				// Upload
				OP.upload(PUID);

				// --Print Pull
				OP.printPull(PUID);

				String Orderstage = null;
				try {
					String Orderstage1 = Driver.findElement(By.xpath("//div/h3[contains(@class,\"ng-binding\")]"))
							.getText();
					Orderstage = Orderstage1;
					logger.info("Current stage of the order is=" + Orderstage);
					msg.append("Current stage of the order is=" + Orderstage + "\n");

				} catch (Exception stage) {
					try {
						String Orderstage2 = Driver.findElement(By.xpath("//Strong/span[@class=\"ng-binding\"]"))
								.getText();
						Orderstage = Orderstage2;
						logger.info("Current stage of the order is=" + Orderstage);
						msg.append("Current stage of the order is=" + Orderstage + "\n");
					} catch (Exception SName) {
						String StageName = Driver.findElement(By.xpath("//*[@ng-if=\"ConfirmPickupPage\"]")).getText();
						Orderstage = StageName;
						logger.info("Stage is==" + StageName);
						msg.append("Current stage of the order is=" + Orderstage + "\n");

					}
				}
				if (Orderstage.equalsIgnoreCase("Confirm Pu Alert")) {

					// --scroll to confirm btn
					WebElement DelAddress = Driver.findElement(By.id("lblDeliverAddress"));
					js.executeScript("arguments[0].scrollIntoView();", DelAddress);
					Thread.sleep(2000);

					// --Click on CONFIRM button //
					WebElement Confirm = Driver.findElement(By.id("lnkConfPick"));
					act.moveToElement(Confirm).build().perform();
					js.executeScript("arguments[0].click();", Confirm);
					logger.info("Click on CONFIRM button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					// --Search again from Operation Tab
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					PUID = getData("OrderProcessing", 8, 1);
					Driver.findElement(By.id("txtBasicSearch2")).clear();
					logger.info("Clear search input");
					Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
					logger.info("Enter PickUpID in Search input");
					OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
					act.moveToElement(OPSearch).build().perform();
					js.executeScript("arguments[0].click();", OPSearch);
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
							PUID = getData("OrderProcessing", 8, 1);
							Driver.findElement(By.id("txtBasicSearch")).clear();
							logger.info("Clear search input");
							Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
							logger.info("Enter pickupID in search input");
							WebElement InvSearch = Driver.findElement(By.id("btnSearch2"));
							wait.until(ExpectedConditions.elementToBeClickable(InvSearch));
							act.moveToElement(InvSearch).build().perform();
							js.executeScript("arguments[0].click();", InvSearch);
							logger.info("Click on Search button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						}
					} catch (Exception Tab) {

					}

					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
						WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
						if (NoData.isDisplayed()) {
							logger.info("Job is not moved to Confirm Pull Alert stage successfully");
						}
					} catch (Exception e1) {
						logger.info("Job is moved to Confirm Pull Alert stage successfully");

						// --Confirm Pull Alert
						String stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
						logger.info("stage=" + stage);
						logger.info("If order stage is Confirm Pull Alert.....");
						getScreenshot(Driver, "RETURN_ConfirmPullAlert_" + PUID);

						try {
							try {
								WebElement Accept = Driver.findElement(By.id("idiconaccept"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								act.moveToElement(Accept).click().perform();
								logger.info("Clicked on Accept button");

							} catch (Exception saveee) {
								WebElement Accept = Driver.findElement(By.id("idiconaccept"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								js.executeScript("arguments[0].click();", Accept);
								logger.info("Clicked on Accept button");

							}
						} catch (Exception Saveb) {
							try {
								WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
								act.moveToElement(Accept).click().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								act.moveToElement(Accept).click().perform();
								logger.info("Clicked on Accept button");

							} catch (Exception saveee) {
								WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								js.executeScript("arguments[0].click();", Accept);
								logger.info("Clicked on Accept button");
							}

						}
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						try {
							wait.until(ExpectedConditions
									.visibilityOfElementLocated(By.xpath("//*[@ng-message=\"required\"]")));
							logger.info("Validation Message is=="
									+ Driver.findElement(By.xpath("//*[@ng-message=\"required\"]")).getText());
							// --Spoke with
							Driver.findElement(By.id("idConfPullAlertForm")).sendKeys("Ravina Oza");
							logger.info("Entered spoke with");

							// --Click on Accept
							try {
								try {
									WebElement Accept = Driver.findElement(By.id("idiconaccept"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									act.moveToElement(Accept).click().perform();
									logger.info("Clicked on Accept button");

								} catch (Exception saveee) {
									WebElement Accept = Driver.findElement(By.id("idiconaccept"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									js.executeScript("arguments[0].click();", Accept);
									logger.info("Clicked on Accept button");

								}
							} catch (Exception Saveb) {
								try {
									WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
									act.moveToElement(Accept).click().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									act.moveToElement(Accept).click().perform();
									logger.info("Clicked on Accept button");

								} catch (Exception saveee) {
									WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									js.executeScript("arguments[0].click();", Accept);
									logger.info("Clicked on Accept button");
								}

							}
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception e) {
							logger.info("Spoke with validation is not displayed");

						}
						// --Search again from Operation Tab
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
						PUID = getData("OrderProcessing", 8, 1);
						Driver.findElement(By.id("txtBasicSearch")).clear();
						logger.info("Clear search input");
						Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
						logger.info("Enter pickupID in search input");
						WebElement InvSearch = Driver.findElement(By.id("btnSearch2"));
						wait.until(ExpectedConditions.elementToBeClickable(InvSearch));
						act.moveToElement(InvSearch).build().perform();
						js.executeScript("arguments[0].click();", InvSearch);
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
								PUID = getData("OrderProcessing", 8, 1);
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								logger.info("Clear search input");
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								logger.info("Enter PickUpID in Search input");
								OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
								act.moveToElement(OPSearch).build().perform();
								js.executeScript("arguments[0].click();", OPSearch);
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
							getScreenshot(Driver, "RETURN_ConfirmPull_" + PUID);

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
								getScreenshot(Driver, "RETURN_Labelgeneration" + PUID);
							}
							Driver.close();
							logger.info("Closed Label generation window");

							Driver.switchTo().window(WindowHandlebefore);
							logger.info("Switched to main window");

							// --Save button
							try {
								try {
									WebElement Accept = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									act.moveToElement(Accept).click().perform();
									logger.info("Clicked on Accept button");

								} catch (Exception Saveb) {
									WebElement Accept = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									js.executeScript("arguments[0].click();", Accept);
									logger.info("Clicked on Accept button");

								}
							} catch (Exception Saveee) {
								try {
									WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									act.moveToElement(Accept).click().perform();
									logger.info("Clicked on Accept button");

								} catch (Exception Saveb) {
									WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									js.executeScript("arguments[0].click();", Accept);
									logger.info("Clicked on Accept button");
								}
							}
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
								PartPullTime.sendKeys(Keys.TAB);
								// --Save button
								try {
									try {
										WebElement Accept = Driver.findElement(By.id("idiconsave"));
										act.moveToElement(Accept).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(Accept));
										act.moveToElement(Accept).click().perform();
										logger.info("Clicked on Accept button");

									} catch (Exception Saveb) {
										WebElement Accept = Driver.findElement(By.id("idiconsave"));
										act.moveToElement(Accept).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(Accept));
										js.executeScript("arguments[0].click();", Accept);
										logger.info("Clicked on Accept button");

									}
								} catch (Exception Saveee) {
									try {
										WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
										act.moveToElement(Accept).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(Accept));
										act.moveToElement(Accept).click().perform();
										logger.info("Clicked on Accept button");

									} catch (Exception Saveb) {
										WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
										act.moveToElement(Accept).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(Accept));
										js.executeScript("arguments[0].click();", Accept);
										logger.info("Clicked on Accept button");
									}
								}
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							} catch (Exception ev) {
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
								WebElement SerialNoBar = Driver.findElement(By.id("txtBarcode"));
								act.moveToElement(SerialNoBar).build().perform();
								SerialNoBar.clear();
								Driver.findElement(By.id("txtBarcode")).sendKeys(SerialNo);
								Driver.findElement(By.id("txtBarcode")).sendKeys(Keys.TAB);
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								logger.info("Entered serial No in scan barcode");

								/*
								 * // --Zoom out js.executeScript("document.body.style.zoom='80%';");
								 * Thread.sleep(2000);
								 */

								// --Save button
								try {
									try {
										WebElement Accept = Driver.findElement(By.id("idiconsave"));
										act.moveToElement(Accept).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(Accept));
										act.moveToElement(Accept).click().perform();
										logger.info("Clicked on Accept button");

									} catch (Exception Saveb) {
										WebElement Accept = Driver.findElement(By.id("idiconsave"));
										act.moveToElement(Accept).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(Accept));
										js.executeScript("arguments[0].click();", Accept);
										logger.info("Clicked on Accept button");

									}
								} catch (Exception Saveee) {
									try {
										WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
										act.moveToElement(Accept).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(Accept));
										act.moveToElement(Accept).click().perform();
										logger.info("Clicked on Accept button");

									} catch (Exception Saveb) {
										WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
										act.moveToElement(Accept).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(Accept));
										js.executeScript("arguments[0].click();", Accept);
										logger.info("Clicked on Accept button");
									}
								}
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

							} catch (Exception errmsg) {
								logger.info("Validation message is not displayed");

							}
							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(
										By.xpath("//label[contains(@class,'error-messages')]")));
								logger.info("ErroMsg is Displayed=" + Driver
										.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());

								/*
								 * // --Zoom In js.executeScript("document.body.style.zoom='100%';");
								 * Thread.sleep(2000);
								 */

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
								PartPullTime.sendKeys(Keys.TAB);

								/*
								 * // --Zoom out js.executeScript("document.body.style.zoom='80%';");
								 * Thread.sleep(2000);
								 */

								try {
									try {
										WebElement Accept = Driver.findElement(By.id("idiconsave"));
										act.moveToElement(Accept).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(Accept));
										act.moveToElement(Accept).click().perform();
										logger.info("Clicked on Accept button");

									} catch (Exception Saveb) {
										WebElement Accept = Driver.findElement(By.id("idiconsave"));
										act.moveToElement(Accept).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(Accept));
										js.executeScript("arguments[0].click();", Accept);
										logger.info("Clicked on Accept button");

									}
								} catch (Exception Saveee) {
									try {
										WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
										act.moveToElement(Accept).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(Accept));
										act.moveToElement(Accept).click().perform();
										logger.info("Clicked on Accept button");

									} catch (Exception Saveb) {
										WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
										act.moveToElement(Accept).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(Accept));
										js.executeScript("arguments[0].click();", Accept);
										logger.info("Clicked on Accept button");
									}
								}
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							} catch (Exception Time) {
								logger.info("Time validation is not displayed-Time is as per timeZone");
							}

							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
							PUID = getData("OrderProcessing", 8, 1);
							Driver.findElement(By.id("txtBasicSearch")).clear();
							logger.info("Clear search input");
							Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
							logger.info("Enter pickupID in search input");
							InvSearch = Driver.findElement(By.id("btnSearch2"));
							wait.until(ExpectedConditions.elementToBeClickable(InvSearch));
							act.moveToElement(InvSearch).build().perform();
							js.executeScript("arguments[0].click();", InvSearch);
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
									PUID = getData("OrderProcessing", 8, 1);
									Driver.findElement(By.id("txtBasicSearch2")).clear();
									Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
									OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
									act.moveToElement(OPSearch).build().perform();
									js.executeScript("arguments[0].click();", OPSearch);
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
								msg.append("Current stage of the order is=" + Orderstage + "\n");
								getScreenshot(Driver, "RETURN_Pickup_" + PUID);

								DelAddress = Driver.findElement(By.id("lblAddress"));
								js.executeScript("arguments[0].scrollIntoView();", DelAddress);
								Thread.sleep(2000);

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
								PUPTime.sendKeys(Keys.TAB);

								try {
									WebElement Save = Driver.findElement(By.id("lnksave"));
									act.moveToElement(Save).build().perform();
									js.executeScript("arguments[0].click();", Save);
									logger.info("Clicked on PICKUP button");
								} catch (Exception saveeee) {
									WebElement Save = Driver.findElement(By.id("lnksave"));
									act.moveToElement(Save).build().perform();
									act.moveToElement(Save).click().perform();
									logger.info("Clicked on PICKUP button");
								}
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions
											.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
									WebElement Dyes = Driver.findElement(By.id("iddataok"));
									js.executeScript("arguments[0].click();", Dyes);
									logger.info("Clicked on Yes button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								} catch (Exception Dialogue) {
									logger.info("Dialogue is not exist");
								}
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
								act.moveToElement(OPSearch).build().perform();
								js.executeScript("arguments[0].click();", OPSearch);
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
									msg.append("Current stage of the order is=" + Orderstage + "\n");
									getScreenshot(Driver, "RETURN_Deliver_" + PUID);

									WebElement Deliver = Driver.findElement(By.id("lblAddress"));
									js.executeScript("arguments[0].scrollIntoView();", Deliver);
									Thread.sleep(2000);

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
									DropTime.sendKeys(Keys.TAB);

									// --Click on Deliver
									WebElement Del = Driver.findElement(By.id("btnsavedelivery"));
									act.moveToElement(Del).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Del));
									act.moveToElement(Del).build().perform();
									js.executeScript("arguments[0].click();", Del);
									logger.info("Clicked on Deliver button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									try {

										WebElement ErrorMSG = Driver.findElement(By.id("errorid"));
										js.executeScript("arguments[0].scrollIntoView(true);", ErrorMSG);
										Thread.sleep(2000);
										wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
										WebElement Error = Driver.findElement(By.id("errorid"));
										if (Error.isDisplayed()) {
											logger.info("Validation displayed==" + Error.getText());

											// --Signed For By
											Deliver = Driver.findElement(By.id("lblAddress"));
											js.executeScript("arguments[0].scrollIntoView();", Deliver);
											Thread.sleep(2000);
											WebElement Sign = Driver.findElement(By.id("txtSignature"));
											act.moveToElement(Sign).build().perform();
											wait.until(ExpectedConditions.elementToBeClickable(Sign));
											Sign.clear();
											Sign.sendKeys("Ravina Prajapati");
											logger.info("Enter signature");

											// --Click on DELIVER
											Del = Driver.findElement(By.id("btnsavedelivery"));
											act.moveToElement(Del).build().perform();
											wait.until(ExpectedConditions.elementToBeClickable(Del));
											act.moveToElement(Del).build().perform();
											js.executeScript("arguments[0].click();", Del);
											logger.info("Clicked on Deliver button");
											wait.until(ExpectedConditions.invisibilityOfElementLocated(
													By.xpath("//*[@class=\"ajax-loadernew\"]")));
										}
									} catch (Exception signedby) {
										logger.info("Validation for SignedBy is not displayed");

									}

									try {
										wait.until(ExpectedConditions
												.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
										WebElement Dyes = Driver.findElement(By.id("iddataok"));
										js.executeScript("arguments[0].click();", Dyes);
										logger.info("Clicked on Yes button");

										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));

									} catch (Exception Delll) {
										logger.info("Dialogue is not present");
									}

									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
									PUID = getData("OrderProcessing", 8, 1);
									Driver.findElement(By.id("txtBasicSearch2")).clear();
									logger.info("Cleared search input");
									Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
									logger.info("Enter pickupID in search input");
									OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
									act.moveToElement(OPSearch).build().perform();
									js.executeScript("arguments[0].click();", OPSearch);
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
											msg.append(
													"Job is moved to VERIFY CUSTOMER BILL stage successfully" + "\n");
										}
									} catch (Exception Deliverrrr) {
										System.out
												.println("Job is not moved to VERIFY CUSTOMER BILL stage successfully");
										logger.info("Job is not moved to VERIFY CUSTOMER BILL stage successfully");
										msg.append(
												"Job is not moved to VERIFY CUSTOMER BILL stage successfully" + "\n");

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
					getScreenshot(Driver, "RETURN_ConfirmPullAlert_" + PUID);

					try {
						try {
							WebElement Accept = Driver.findElement(By.id("idiconaccept"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							act.moveToElement(Accept).click().perform();
							logger.info("Clicked on Accept button");

						} catch (Exception saveee) {
							WebElement Accept = Driver.findElement(By.id("idiconaccept"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							js.executeScript("arguments[0].click();", Accept);
							logger.info("Clicked on Accept button");

						}
					} catch (Exception Saveb) {
						try {
							WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
							act.moveToElement(Accept).click().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							act.moveToElement(Accept).click().perform();
							logger.info("Clicked on Accept button");

						} catch (Exception saveee) {
							WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							js.executeScript("arguments[0].click();", Accept);
							logger.info("Clicked on Accept button");
						}

					}
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					try {
						wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//*[@ng-message=\"required\"]")));
						logger.info("Validation Message is=="
								+ Driver.findElement(By.xpath("//*[@ng-message=\"required\"]")).getText());
						// --Spoke with
						Driver.findElement(By.id("idConfPullAlertForm")).sendKeys("Ravina Oza");
						logger.info("Entered spoke with");

						// --Click on Accept
						try {
							try {
								WebElement Accept = Driver.findElement(By.id("idiconaccept"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								act.moveToElement(Accept).click().perform();
								logger.info("Clicked on Accept button");

							} catch (Exception saveee) {
								WebElement Accept = Driver.findElement(By.id("idiconaccept"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								js.executeScript("arguments[0].click();", Accept);
								logger.info("Clicked on Accept button");

							}
						} catch (Exception Saveb) {
							try {
								WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
								act.moveToElement(Accept).click().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								act.moveToElement(Accept).click().perform();
								logger.info("Clicked on Accept button");

							} catch (Exception saveee) {
								WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								js.executeScript("arguments[0].click();", Accept);
								logger.info("Clicked on Accept button");
							}

						}
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					} catch (Exception e) {
						logger.info("Spoke with validation is not displayed");

					}
					// --Search again from Operation Tab
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
					PUID = getData("OrderProcessing", 8, 1);
					Driver.findElement(By.id("txtBasicSearch")).clear();
					logger.info("Clear search input");
					Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
					logger.info("Enter pickupID in search input");
					WebElement InvSearch = Driver.findElement(By.id("btnSearch2"));
					wait.until(ExpectedConditions.elementToBeClickable(InvSearch));
					act.moveToElement(InvSearch).build().perform();
					js.executeScript("arguments[0].click();", InvSearch);
					logger.info("Click on Search button");

					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
						WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
						if (NoData.isDisplayed()) {
							logger.info("Job is not found in Inventory Tab");
							Driver.findElement(By.xpath("//*[@id=\"operation\" and  text()='Operations']")).click();
							logger.info("Click on Operation tab");
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							PUID = getData("OrderProcessing", 8, 1);
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							logger.info("Clear search input");
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							logger.info("Enter PickUpID in Search input");
							OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
							act.moveToElement(OPSearch).build().perform();
							js.executeScript("arguments[0].click();", OPSearch);
							logger.info("Click on Search button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						}
					} catch (Exception Tab) {
						logger.info("Job is exist");

					}
					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
						WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
						if (NoData.isDisplayed()) {
							logger.info("Job is not moved to Confirm Pull stage successfully");
						}
					} catch (Exception e) {
						logger.info("Job is moved to Confirm Pull stage successfully");

						// --Confirm Pull stage
						stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
						logger.info("stage=" + stage);
						getScreenshot(Driver, "RETURN_ConfirmPull_" + PUID);

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
							getScreenshot(Driver, "RETURN_Labelgeneration" + PUID);
						}
						Driver.close();
						logger.info("Closed Label generation window");

						Driver.switchTo().window(WindowHandlebefore);
						logger.info("Switched to main window");

						// --Save button
						try {
							try {
								WebElement Accept = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								act.moveToElement(Accept).click().perform();
								logger.info("Clicked on Accept button");

							} catch (Exception Saveb) {
								WebElement Accept = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								js.executeScript("arguments[0].click();", Accept);
								logger.info("Clicked on Accept button");

							}
						} catch (Exception Saveee) {
							try {
								WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								act.moveToElement(Accept).click().perform();
								logger.info("Clicked on Accept button");

							} catch (Exception Saveb) {
								WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								js.executeScript("arguments[0].click();", Accept);
								logger.info("Clicked on Accept button");
							}
						}
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
							PartPullTime.sendKeys(Keys.TAB);
							// --Save button
							try {
								try {
									WebElement Accept = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									act.moveToElement(Accept).click().perform();
									logger.info("Clicked on Accept button");

								} catch (Exception Saveb) {
									WebElement Accept = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									js.executeScript("arguments[0].click();", Accept);
									logger.info("Clicked on Accept button");

								}
							} catch (Exception Saveee) {
								try {
									WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									act.moveToElement(Accept).click().perform();
									logger.info("Clicked on Accept button");

								} catch (Exception Saveb) {
									WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									js.executeScript("arguments[0].click();", Accept);
									logger.info("Clicked on Accept button");
								}
							}
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						} catch (Exception ev) {
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
							WebElement SerialNoBar = Driver.findElement(By.id("txtBarcode"));
							act.moveToElement(SerialNoBar).build().perform();
							SerialNoBar.clear();
							Driver.findElement(By.id("txtBarcode")).sendKeys(SerialNo);
							Driver.findElement(By.id("txtBarcode")).sendKeys(Keys.TAB);
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							logger.info("Entered serial No in scan barcode");

							/*
							 * // --Zoom out js.executeScript("document.body.style.zoom='80%';");
							 * Thread.sleep(2000);
							 */

							// --Save button
							try {
								try {
									WebElement Accept = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									act.moveToElement(Accept).click().perform();
									logger.info("Clicked on Accept button");

								} catch (Exception Saveb) {
									WebElement Accept = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									js.executeScript("arguments[0].click();", Accept);
									logger.info("Clicked on Accept button");

								}
							} catch (Exception Saveee) {
								try {
									WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									act.moveToElement(Accept).click().perform();
									logger.info("Clicked on Accept button");

								} catch (Exception Saveb) {
									WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									js.executeScript("arguments[0].click();", Accept);
									logger.info("Clicked on Accept button");
								}
							}
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception errmsg) {
							logger.info("Validation message is not displayed");

						}
						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//label[contains(@class,'error-messages')]")));
							logger.info("ErroMsg is Displayed=" + Driver
									.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());

							/*
							 * // --Zoom In js.executeScript("document.body.style.zoom='100%';");
							 * Thread.sleep(2000);
							 */

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
							PartPullTime.sendKeys(Keys.TAB);

							/*
							 * // --Zoom out js.executeScript("document.body.style.zoom='80%';");
							 * Thread.sleep(2000);
							 */

							try {
								try {
									WebElement Accept = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									act.moveToElement(Accept).click().perform();
									logger.info("Clicked on Accept button");

								} catch (Exception Saveb) {
									WebElement Accept = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									js.executeScript("arguments[0].click();", Accept);
									logger.info("Clicked on Accept button");

								}
							} catch (Exception Saveee) {
								try {
									WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									act.moveToElement(Accept).click().perform();
									logger.info("Clicked on Accept button");

								} catch (Exception Saveb) {
									WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
									act.moveToElement(Accept).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Accept));
									js.executeScript("arguments[0].click();", Accept);
									logger.info("Clicked on Accept button");
								}
							}
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						} catch (Exception Time) {
							logger.info("Time validation is not displayed-Time is as per timeZone");
						}

						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
						PUID = getData("OrderProcessing", 8, 1);
						Driver.findElement(By.id("txtBasicSearch")).clear();
						logger.info("Clear search input");
						Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
						logger.info("Enter pickupID in search input");
						InvSearch = Driver.findElement(By.id("btnSearch2"));
						wait.until(ExpectedConditions.elementToBeClickable(InvSearch));
						act.moveToElement(InvSearch).build().perform();
						js.executeScript("arguments[0].click();", InvSearch);
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
								PUID = getData("OrderProcessing", 8, 1);
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
								act.moveToElement(OPSearch).build().perform();
								js.executeScript("arguments[0].click();", OPSearch);
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
							msg.append("Current stage of the order is=" + Orderstage + "\n");
							getScreenshot(Driver, "RETURN_Pickup_" + PUID);

							WebElement DelAddress = Driver.findElement(By.id("lblAddress"));
							js.executeScript("arguments[0].scrollIntoView();", DelAddress);
							Thread.sleep(2000);

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
							PUPTime.sendKeys(Keys.TAB);

							try {
								WebElement Save = Driver.findElement(By.id("lnksave"));
								act.moveToElement(Save).build().perform();
								js.executeScript("arguments[0].click();", Save);
								logger.info("Clicked on PICKUP button");
							} catch (Exception saveeee) {
								WebElement Save = Driver.findElement(By.id("lnksave"));
								act.moveToElement(Save).build().perform();
								act.moveToElement(Save).click().perform();
								logger.info("Clicked on PICKUP button");
							}
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
								WebElement Dyes = Driver.findElement(By.id("iddataok"));
								js.executeScript("arguments[0].click();", Dyes);
								logger.info("Clicked on Yes button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							} catch (Exception Dialogue) {
								logger.info("Dialogue is not exist");
							}
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
							act.moveToElement(OPSearch).build().perform();
							js.executeScript("arguments[0].click();", OPSearch);
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
								getScreenshot(Driver, "RETURN_Deliver_" + PUID);

								WebElement Deliver = Driver.findElement(By.id("lblAddress"));
								js.executeScript("arguments[0].scrollIntoView();", Deliver);
								Thread.sleep(2000);

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
								DropTime.sendKeys(Keys.TAB);

								// --Click on Deliver
								WebElement Del = Driver.findElement(By.id("btnsavedelivery"));
								act.moveToElement(Del).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Del));
								act.moveToElement(Del).build().perform();
								js.executeScript("arguments[0].click();", Del);
								logger.info("Clicked on Deliver button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
									WebElement Error = Driver.findElement(By.id("errorid"));
									if (Error.isDisplayed()) {
										logger.info("Validation displayed==" + Error.getText());
										// --Signed For By
										WebElement Sign = Driver.findElement(By.id("txtSignature"));
										act.moveToElement(Sign).build().perform();
										Sign.clear();
										Sign.sendKeys("Ravina Prajapati");
										logger.info("Enter signature");

										// --Click on DELIVER
										Del = Driver.findElement(By.id("btnsavedelivery"));
										act.moveToElement(Del).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(Del));
										act.moveToElement(Del).build().perform();
										js.executeScript("arguments[0].click();", Del);
										logger.info("Clicked on Deliver button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
									}
								} catch (Exception signedby) {
									logger.info("Validation for SignedBy is not displayed");

								}

								try {
									wait.until(ExpectedConditions
											.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
									WebElement Dyes = Driver.findElement(By.id("iddataok"));
									js.executeScript("arguments[0].click();", Dyes);
									logger.info("Clicked on Yes button");

									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								} catch (Exception Delll) {
									logger.info("Dialogue is not present");
								}

								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
								PUID = getData("OrderProcessing", 8, 1);
								Driver.findElement(By.id("txtBasicSearch2")).clear();
								logger.info("Cleared search input");
								Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
								logger.info("Enter pickupID in search input");
								OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
								act.moveToElement(OPSearch).build().perform();
								js.executeScript("arguments[0].click();", OPSearch);
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
										msg.append("Job is moved to VERIFY CUSTOMER BILL stage successfully" + "\n");
									}
								} catch (Exception Deliverrrr) {
									System.out.println("Job is not moved to VERIFY CUSTOMER BILL stage successfully");
									logger.info("Job is not moved to VERIFY CUSTOMER BILL stage successfully");
									msg.append("Job is not moved to VERIFY CUSTOMER BILL stage successfully" + "\n");

								}
							}

						}

					}
				} else if (Orderstage.equalsIgnoreCase("Confirm Pull")) {
					logger.info("Job is moved to Confirm Pull stage successfully");

					// --Confirm Pull stage
					String stage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
					logger.info("stage=" + stage);
					getScreenshot(Driver, "RETURN_ConfirmPull_" + PUID);

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
						getScreenshot(Driver, "RETURN_Labelgeneration" + PUID);
					}
					Driver.close();
					logger.info("Closed Label generation window");

					Driver.switchTo().window(WindowHandlebefore);
					logger.info("Switched to main window");

					// --Save button
					try {
						try {
							WebElement Accept = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							act.moveToElement(Accept).click().perform();
							logger.info("Clicked on Accept button");

						} catch (Exception Saveb) {
							WebElement Accept = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							js.executeScript("arguments[0].click();", Accept);
							logger.info("Clicked on Accept button");

						}
					} catch (Exception Saveee) {
						try {
							WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							act.moveToElement(Accept).click().perform();
							logger.info("Clicked on Accept button");

						} catch (Exception Saveb) {
							WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
							act.moveToElement(Accept).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Accept));
							js.executeScript("arguments[0].click();", Accept);
							logger.info("Clicked on Accept button");
						}
					}
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idPartPullDttmValidation")));
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
						PartPullTime.sendKeys(Keys.TAB);
						// --Save button
						try {
							try {
								WebElement Accept = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								act.moveToElement(Accept).click().perform();
								logger.info("Clicked on Accept button");

							} catch (Exception Saveb) {
								WebElement Accept = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								js.executeScript("arguments[0].click();", Accept);
								logger.info("Clicked on Accept button");

							}
						} catch (Exception Saveee) {
							try {
								WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								act.moveToElement(Accept).click().perform();
								logger.info("Clicked on Accept button");

							} catch (Exception Saveb) {
								WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								js.executeScript("arguments[0].click();", Accept);
								logger.info("Clicked on Accept button");
							}
						}
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					} catch (Exception ev) {
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
						WebElement SerialNoBar = Driver.findElement(By.id("txtBarcode"));
						act.moveToElement(SerialNoBar).build().perform();
						SerialNoBar.clear();
						Driver.findElement(By.id("txtBarcode")).sendKeys(SerialNo);
						Driver.findElement(By.id("txtBarcode")).sendKeys(Keys.TAB);
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						logger.info("Entered serial No in scan barcode");

						/*
						 * // --Zoom out js.executeScript("document.body.style.zoom='80%';");
						 * Thread.sleep(2000);
						 */

						// --Save button
						try {
							try {
								WebElement Accept = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								act.moveToElement(Accept).click().perform();
								logger.info("Clicked on Accept button");

							} catch (Exception Saveb) {
								WebElement Accept = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								js.executeScript("arguments[0].click();", Accept);
								logger.info("Clicked on Accept button");

							}
						} catch (Exception Saveee) {
							try {
								WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								act.moveToElement(Accept).click().perform();
								logger.info("Clicked on Accept button");

							} catch (Exception Saveb) {
								WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								js.executeScript("arguments[0].click();", Accept);
								logger.info("Clicked on Accept button");
							}
						}
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					} catch (Exception errmsg) {
						logger.info("Validation message is not displayed");

					}
					try {
						wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//label[contains(@class,'error-messages')]")));
						logger.info("ErroMsg is Displayed="
								+ Driver.findElement(By.xpath("//label[contains(@class,'error-messages')]")).getText());

						/*
						 * // --Zoom In js.executeScript("document.body.style.zoom='100%';");
						 * Thread.sleep(2000);
						 */

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
						PartPullTime.sendKeys(Keys.TAB);

						/*
						 * // --Zoom out js.executeScript("document.body.style.zoom='80%';");
						 * Thread.sleep(2000);
						 */

						try {
							try {
								WebElement Accept = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								act.moveToElement(Accept).click().perform();
								logger.info("Clicked on Accept button");

							} catch (Exception Saveb) {
								WebElement Accept = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								js.executeScript("arguments[0].click();", Accept);
								logger.info("Clicked on Accept button");

							}
						} catch (Exception Saveee) {
							try {
								WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								act.moveToElement(Accept).click().perform();
								logger.info("Clicked on Accept button");

							} catch (Exception Saveb) {
								WebElement Accept = Driver.findElement(By.id("lnkcnfpull"));
								act.moveToElement(Accept).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Accept));
								js.executeScript("arguments[0].click();", Accept);
								logger.info("Clicked on Accept button");
							}
						}
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					} catch (Exception Time) {
						logger.info("Time validation is not displayed-Time is as per timeZone");
					}

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
					PUID = getData("OrderProcessing", 8, 1);
					Driver.findElement(By.id("txtBasicSearch")).clear();
					logger.info("Clear search input");
					Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
					logger.info("Enter pickupID in search input");
					WebElement InvSearch = Driver.findElement(By.id("btnSearch2"));
					wait.until(ExpectedConditions.elementToBeClickable(InvSearch));
					act.moveToElement(InvSearch).build().perform();
					js.executeScript("arguments[0].click();", InvSearch);
					logger.info("Click on Search button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
						WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
						if (NoData.isDisplayed()) {
							logger.info("Job is not found in Inventory Tab");
							// --Go to Operation tab
							Driver.findElement(By.xpath("//*[@id=\"operation\" and  text()='Operations']")).click();
							logger.info("Click on Operation Tab");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							PUID = getData("OrderProcessing", 8, 1);
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
							act.moveToElement(OPSearch).build().perform();
							js.executeScript("arguments[0].click();", OPSearch);
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						}
					} catch (Exception Tab) {
						logger.info("Job is exist");

					}

					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
						WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
						if (NoData.isDisplayed()) {
							logger.info("Job is not moved to PICKUP stage successfully");
						}
					} catch (Exception eDataA) {
						logger.info("Job is moved to PICKUP stage successfully");

						// --Pickup
						Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
						logger.info("Current stage of the order is=" + Orderstage);
						msg.append("Current stage of the order is=" + Orderstage + "\n");
						getScreenshot(Driver, "RETURN_Pickup_" + PUID);

						WebElement DelAddress = Driver.findElement(By.id("lblAddress"));
						js.executeScript("arguments[0].scrollIntoView();", DelAddress);
						Thread.sleep(2000);

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
						PUPTime.sendKeys(Keys.TAB);

						try {
							WebElement Save = Driver.findElement(By.id("lnksave"));
							act.moveToElement(Save).build().perform();
							js.executeScript("arguments[0].click();", Save);
							logger.info("Clicked on PICKUP button");
						} catch (Exception saveeee) {
							WebElement Save = Driver.findElement(By.id("lnksave"));
							act.moveToElement(Save).build().perform();
							act.moveToElement(Save).click().perform();
							logger.info("Clicked on PICKUP button");
						}
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						try {
							wait.until(
									ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
							WebElement Dyes = Driver.findElement(By.id("iddataok"));
							js.executeScript("arguments[0].click();", Dyes);
							logger.info("Clicked on Yes button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						} catch (Exception Dialogue) {
							logger.info("Dialogue is not exist");
						}
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
						act.moveToElement(OPSearch).build().perform();
						js.executeScript("arguments[0].click();", OPSearch);
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
							getScreenshot(Driver, "RETURN_Deliver_" + PUID);

							WebElement Deliver = Driver.findElement(By.id("lblAddress"));
							js.executeScript("arguments[0].scrollIntoView();", Deliver);
							Thread.sleep(2000);

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
							DropTime.sendKeys(Keys.TAB);

							// --Click on Deliver
							WebElement Del = Driver.findElement(By.id("btnsavedelivery"));
							act.moveToElement(Del).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Del));
							act.moveToElement(Del).build().perform();
							js.executeScript("arguments[0].click();", Del);
							logger.info("Clicked on Deliver button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
								WebElement Error = Driver.findElement(By.id("errorid"));
								if (Error.isDisplayed()) {
									logger.info("Validation displayed==" + Error.getText());
									// --Signed For By
									WebElement Sign = Driver.findElement(By.id("txtSignature"));
									act.moveToElement(Sign).build().perform();
									Sign.clear();
									Sign.sendKeys("Ravina Prajapati");
									logger.info("Enter signature");

									// --Click on DELIVER
									Del = Driver.findElement(By.id("btnsavedelivery"));
									act.moveToElement(Del).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(Del));
									act.moveToElement(Del).build().perform();
									js.executeScript("arguments[0].click();", Del);
									logger.info("Clicked on Deliver button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								}
							} catch (Exception signedby) {
								logger.info("Validation for SignedBy is not displayed");

							}

							try {
								wait.until(ExpectedConditions
										.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
								WebElement Dyes = Driver.findElement(By.id("iddataok"));
								js.executeScript("arguments[0].click();", Dyes);
								logger.info("Clicked on Yes button");

								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

							} catch (Exception Delll) {
								logger.info("Dialogue is not present");
							}

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
							PUID = getData("OrderProcessing", 8, 1);
							Driver.findElement(By.id("txtBasicSearch2")).clear();
							logger.info("Cleared search input");
							Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
							logger.info("Enter pickupID in search input");
							OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
							act.moveToElement(OPSearch).build().perform();
							js.executeScript("arguments[0].click();", OPSearch);
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
									msg.append("Job is moved to VERIFY CUSTOMER BILL stage successfully" + "\n");
								}
							} catch (Exception Deliverrrr) {
								System.out.println("Job is not moved to VERIFY CUSTOMER BILL stage successfully");
								logger.info("Job is not moved to VERIFY CUSTOMER BILL stage successfully");
								msg.append("Job is not moved to VERIFY CUSTOMER BILL stage successfully" + "\n");

							}
						}

					}

				} else if (Orderstage.equalsIgnoreCase("PICKUP")) {
					logger.info("Job is moved to PICKUP stage successfully");

					// --Pickup
					Orderstage = Driver.findElement(By.xpath("//strong/span[@class=\"ng-binding\"]")).getText();
					logger.info("Current stage of the order is=" + Orderstage);
					msg.append("Current stage of the order is=" + Orderstage + "\n");
					getScreenshot(Driver, "RETURN_Pickup_" + PUID);

					WebElement DelAddress = Driver.findElement(By.id("lblAddress"));
					js.executeScript("arguments[0].scrollIntoView();", DelAddress);
					Thread.sleep(2000);

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
					PUPTime.sendKeys(Keys.TAB);

					try {
						WebElement Save = Driver.findElement(By.id("lnksave"));
						act.moveToElement(Save).build().perform();
						js.executeScript("arguments[0].click();", Save);
						logger.info("Clicked on PICKUP button");
					} catch (Exception saveeee) {
						WebElement Save = Driver.findElement(By.id("lnksave"));
						act.moveToElement(Save).build().perform();
						act.moveToElement(Save).click().perform();
						logger.info("Clicked on PICKUP button");
					}
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					try {
						wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
						WebElement Dyes = Driver.findElement(By.id("iddataok"));
						js.executeScript("arguments[0].click();", Dyes);
						logger.info("Clicked on Yes button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					} catch (Exception Dialogue) {
						logger.info("Dialogue is not exist");
					}
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					Driver.findElement(By.id("txtBasicSearch2")).clear();
					Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
					OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
					act.moveToElement(OPSearch).build().perform();
					js.executeScript("arguments[0].click();", OPSearch);
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
						WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
						if (NoData.isDisplayed()) {
							logger.info("Job is not moved to Deliver stage successfully");
						}
					} catch (Exception eNoData) {
						logger.info("Job is moved to Deliver stage successfully");
						getScreenshot(Driver, "RETURN_Deliver_" + PUID);

						WebElement Deliver = Driver.findElement(By.id("lblAddress"));
						js.executeScript("arguments[0].scrollIntoView();", Deliver);
						Thread.sleep(2000);

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
						DropTime.sendKeys(Keys.TAB);

						// --Click on Deliver
						WebElement Del = Driver.findElement(By.id("btnsavedelivery"));
						act.moveToElement(Del).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(Del));
						act.moveToElement(Del).build().perform();
						js.executeScript("arguments[0].click();", Del);
						logger.info("Clicked on Deliver button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
							WebElement Error = Driver.findElement(By.id("errorid"));
							if (Error.isDisplayed()) {
								logger.info("Validation displayed==" + Error.getText());
								// --Signed For By
								WebElement Sign = Driver.findElement(By.id("txtSignature"));
								act.moveToElement(Sign).build().perform();
								Sign.clear();
								Sign.sendKeys("Ravina Prajapati");
								logger.info("Enter signature");

								// --Click on DELIVER
								Del = Driver.findElement(By.id("btnsavedelivery"));
								act.moveToElement(Del).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Del));
								act.moveToElement(Del).build().perform();
								js.executeScript("arguments[0].click();", Del);
								logger.info("Clicked on Deliver button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							}
						} catch (Exception signedby) {
							logger.info("Validation for SignedBy is not displayed");

						}

						try {
							wait.until(
									ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
							WebElement Dyes = Driver.findElement(By.id("iddataok"));
							js.executeScript("arguments[0].click();", Dyes);
							logger.info("Clicked on Yes button");

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						} catch (Exception Delll) {
							logger.info("Dialogue is not present");
						}

						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
						PUID = getData("OrderProcessing", 8, 1);
						Driver.findElement(By.id("txtBasicSearch2")).clear();
						logger.info("Cleared search input");
						Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
						logger.info("Enter pickupID in search input");
						OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
						act.moveToElement(OPSearch).build().perform();
						js.executeScript("arguments[0].click();", OPSearch);
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
								msg.append("Job is moved to VERIFY CUSTOMER BILL stage successfully" + "\n");
							}
						} catch (Exception Deliverrrr) {
							System.out.println("Job is not moved to VERIFY CUSTOMER BILL stage successfully");
							logger.info("Job is not moved to VERIFY CUSTOMER BILL stage successfully");
							msg.append("Job is not moved to VERIFY CUSTOMER BILL stage successfully" + "\n");

						}
					}

				} else if (Orderstage.equalsIgnoreCase("Deliver")) {
					logger.info("Job is moved to Deliver stage successfully");
					getScreenshot(Driver, "RETURN_Deliver_" + PUID);

					WebElement Deliver = Driver.findElement(By.id("lblAddress"));
					js.executeScript("arguments[0].scrollIntoView();", Deliver);
					Thread.sleep(2000);

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
					DropTime.sendKeys(Keys.TAB);

					// --Click on Deliver
					WebElement Del = Driver.findElement(By.id("btnsavedelivery"));
					act.moveToElement(Del).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(Del));
					act.moveToElement(Del).build().perform();
					js.executeScript("arguments[0].click();", Del);
					logger.info("Clicked on Deliver button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					try {

						WebElement ErrorMSG = Driver.findElement(By.id("errorid"));
						js.executeScript("arguments[0].scrollIntoView(true);", ErrorMSG);
						Thread.sleep(2000);
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
						WebElement Error = Driver.findElement(By.id("errorid"));
						if (Error.isDisplayed()) {
							logger.info("Validation displayed==" + Error.getText());

							// --Signed For By
							WebElement Sign = Driver.findElement(By.id("txtSignature"));
							js.executeScript("arguments[0].scrollIntoView(true);", Sign);
							Thread.sleep(2000);
							act.moveToElement(Sign).build().perform();
							Sign.clear();
							Sign.sendKeys("Ravina Prajapati");
							logger.info("Enter signature");

							// --Click on DELIVER
							Del = Driver.findElement(By.id("btnsavedelivery"));
							act.moveToElement(Del).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(Del));
							act.moveToElement(Del).build().perform();
							js.executeScript("arguments[0].click();", Del);
							logger.info("Clicked on Deliver button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						}
					} catch (Exception signedby) {
						logger.info("Validation for SignedBy is not displayed");

					}

					try {
						wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-content")));
						WebElement Dyes = Driver.findElement(By.id("iddataok"));
						js.executeScript("arguments[0].click();", Dyes);
						logger.info("Clicked on Yes button");

						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					} catch (Exception Delll) {
						logger.info("Dialogue is not present");
					}

					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch2")));
					PUID = getData("OrderProcessing", 8, 1);
					Driver.findElement(By.id("txtBasicSearch2")).clear();
					logger.info("Cleared search input");
					Driver.findElement(By.id("txtBasicSearch2")).sendKeys(PUID);
					logger.info("Enter pickupID in search input");
					OPSearch = Driver.findElement(By.id("btnGXNLSearch2"));
					act.moveToElement(OPSearch).build().perform();
					js.executeScript("arguments[0].click();", OPSearch);
					logger.info("Click on Search button");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
						WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
						if (NoData.isDisplayed()) {
							System.out.println("Job is moved to VERIFY CUSTOMER BILL stage successfully");
							logger.info("Job is moved to VERIFY CUSTOMER BILL stage successfully");
							msg.append("Job is moved to VERIFY CUSTOMER BILL stage successfully" + "\n");
						}
					} catch (Exception Deliverrrr) {
						System.out.println("Job is not moved to VERIFY CUSTOMER BILL stage successfully");
						logger.info("Job is not moved to VERIFY CUSTOMER BILL stage successfully");
						msg.append("Job is not moved to VERIFY CUSTOMER BILL stage successfully" + "\n");

					}
				}
			} catch (Exception NoData1) {
				logger.error(NoData1);
				getScreenshot(Driver, "NoData1_Return_Error");

				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
					WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
					if (NoData.isDisplayed()) {
						logger.info("Job is not exist with the search parameters");
						msg.append("Job is not exist with the search parameters" + "\n\n");

					}
				} catch (Exception OnBoard) {
					logger.error(OnBoard);
					getScreenshot(Driver, "OnBoard_Return_Error");

					try {
						String Orderstage1 = Driver.findElement(By.xpath("//div/h3[contains(@class,\"ng-binding\")]"))
								.getText();
						String Orderstage = Orderstage1;
						logger.info("Current stage of the order is=" + Orderstage);
						msg.append("Current stage of the order is=" + Orderstage + "\n");
						logger.info("Issue in Order stage==" + Orderstage);
						msg.append("Issue in Order stage==" + Orderstage + "\n\n");
						getScreenshot(Driver, "RETURNStageIssue_" + Orderstage);
					} catch (Exception stage) {
						logger.error(stage);
						getScreenshot(Driver, "stage_Return_Error");

						try {
							String Orderstage2 = Driver.findElement(By.xpath("//Strong/span[@class=\"ng-binding\"]"))
									.getText();
							String Orderstage = Orderstage2;
							logger.info("Current stage of the order is=" + Orderstage);
							msg.append("Current stage of the order is=" + Orderstage + "\n");
							logger.info("Issue in Order stage==" + Orderstage);
							msg.append("Issue in Order stage==" + Orderstage + "\n\n");
							getScreenshot(Driver, "RETURNStageIssue_" + Orderstage);
						} catch (Exception SName) {
							logger.error(SName);
							getScreenshot(Driver, "SName_Return_Error");

							String StageName = Driver.findElement(By.xpath("//*[@ng-if=\"ConfirmPickupPage\"]"))
									.getText();
							String Orderstage = StageName;
							logger.info("Current stage of the order is=" + Orderstage);
							msg.append("Current stage of the order is=" + Orderstage + "\n");
							logger.info("Issue in Order stage==" + Orderstage);
							msg.append("Issue in Order stage==" + Orderstage + "\n\n");
							getScreenshot(Driver, "RETURNStageIssue_" + Orderstage);

						}
					}

				}
			}
			logger.info("RETURN Order Processing Test=PASS");
			msg.append("RETURN Order Processing Test=PASS" + "\n\n");
		} catch (Exception RETURNE) {
			logger.error(RETURNE);
			getScreenshot(Driver, "RETURN_error");
			logger.info("RETURN Order Processing Test=FAIL");
			msg.append("RETURN Order Processing Test=FAIL" + "\n\n");

		}

		logger.info("=====RETURN Order Processing Test End=====");
		//msg.append("=====RETURN Order Processing Test End=====" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}

}
