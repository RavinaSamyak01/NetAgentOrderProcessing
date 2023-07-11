package netAgent_OrderProcessing;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

public class RTE_OrderProcess extends BaseInit {

	@Test
	public void orderProcessRTEJOB() throws EncryptedDocumentException, InvalidFormatException, IOException {

		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		try {

			// Go To TaskLog
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id=\"idOperations\"]")));
			WebElement OperationMenu = Driver.findElement(By.xpath("//a[@id=\"idOperations\"]"));
			act.moveToElement(OperationMenu).build().perform();
			js.executeScript("arguments[0].click();", OperationMenu);
			logger.info("Click on Operations");

			try {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("login")));
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name("loginForm")));
				Login();
				// Go To TaskLog
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id=\"idOperations\"]")));
				OperationMenu = Driver.findElement(By.xpath("//a[@id=\"idOperations\"]"));
				act.moveToElement(OperationMenu).build().perform();
				js.executeScript("arguments[0].click();", OperationMenu);
				logger.info("Click on Operations");

			} catch (Exception loginissue) {

			}
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id=\"idTask\"]")));
			WebElement TaskLogMenu = Driver.findElement(By.xpath("//a[@id=\"idTask\"]"));
			act.moveToElement(TaskLogMenu).build().perform();
			js.executeScript("arguments[0].click();", TaskLogMenu);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));
			logger.info("Click on Task Log");

			getScreenshot(Driver, "TaskLog_OperationsRTE");

			// String ServiceID = getData("OrderProcessing", 9, 0);
			// logger.info("ServiceID is==" + ServiceID);
			// msg.append("ServiceID==" + ServiceID + "\n");
			String TrackingNo = getData("RTE", 1, 1);
			logger.info("TrackingID is==" + TrackingNo);
			msg.append("TrackingID==" + TrackingNo + "\n");

			try {// --Click on RTE tab
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-model=\"RTE\"]")));
				WebElement RTETab = Driver.findElement(By.xpath("//*[@ng-model=\"RTE\"]"));
				act.moveToElement(RTETab).build().perform();
				js.executeScript("arguments[0].click();", RTETab);
				logger.info("Click on RTE Tab");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RTE")));
				getScreenshot(Driver, "RTETab");

				// --Search
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearchRTE")));
				Driver.findElement(By.id("txtBasicSearchRTE")).clear();
				Driver.findElement(By.id("txtBasicSearchRTE")).sendKeys(TrackingNo);
				WebElement RTESearch = Driver.findElement(By.id("btnRTESearch2"));
				act.moveToElement(RTESearch).build().perform();
				js.executeScript("arguments[0].click();", RTESearch);
				logger.info("Click on Search button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
				if (NoData.isDisplayed()) {
					logger.info("Record is not available with search parameters");
				} else {
					logger.info("Record is available with search parameters");

					// --click on record
					WebElement Record = Driver.findElement(By.xpath("//*[@id=\"idRTEList\"]//span/strong"));
					act.moveToElement(Record).build().perform();
					js.executeScript("arguments[0].click();", Record);
					logger.info("Clicked on the record");
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@ng-form=\"RouteForm\"]")));
					String Orderstage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
					logger.info("Current stage of the order is=" + Orderstage);
					msg.append("Current stage of the order is=" + Orderstage + "\n");

					getScreenshot(Driver, "RTE_" + Orderstage + TrackingNo);

					if (Orderstage.contains("Confirm Alert")) {
						// --Confirm Alert stage

						// --Click on Accept

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
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						// ---Pickup@Stop 1 of 2 stage
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearchRTE")));
						Driver.findElement(By.id("txtBasicSearchRTE")).clear();
						Driver.findElement(By.id("txtBasicSearchRTE")).sendKeys(TrackingNo);
						RTESearch = Driver.findElement(By.id("btnRTESearch2"));
						act.moveToElement(RTESearch).build().perform();
						js.executeScript("arguments[0].click();", RTESearch);
						logger.info("Click on Search button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
						if (NoData.isDisplayed()) {
							logger.info("Record is not available with search parameters");
						} else {
							logger.info("Record is available with search parameters");
							// --Again click on Record
							Record = Driver.findElement(By.xpath("//*[@id=\"idRTEList\"]//span/strong"));
							act.moveToElement(Record).build().perform();
							js.executeScript("arguments[0].click();", Record);
							logger.info("Clicked on the record");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							wait.until(ExpectedConditions
									.visibilityOfElementLocated(By.xpath("//*[@ng-form=\"RouteForm\"]")));
							Orderstage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
							logger.info("Current stage of the order is=" + Orderstage);
							msg.append("Current stage of the order is=" + Orderstage + "\n");
							getScreenshot(Driver, "RTE_" + Orderstage + TrackingNo);
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

								WebElement ActPUTime = Driver.findElement(By.id("txtActPuTime"));
								ActPUTime.clear();
								String tzone = isElementPresent("RTEPUTimeZone_xpath").getText();
								String rectime = getTimeAsTZone(tzone);
								ActPUTime.sendKeys(rectime);
								ActPUTime.sendKeys(Keys.TAB);

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
							Driver.findElement(By.id("txtBasicSearchRTE")).sendKeys(TrackingNo);
							RTESearch = Driver.findElement(By.id("btnRTESearch2"));
							act.moveToElement(RTESearch).build().perform();
							js.executeScript("arguments[0].click();", RTESearch);
							logger.info("Click on Search button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Record is not available with search parameters");
							} else {
								logger.info("Record is available with search parameters");
								// --Again click on Record
								Record = Driver.findElement(By.xpath("//*[@id=\"idRTEList\"]//span/strong"));
								act.moveToElement(Record).build().perform();
								js.executeScript("arguments[0].click();", Record);
								logger.info("Clicked on the record");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.xpath("//*[@ng-form=\"RouteForm\"]")));

								Orderstage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]"))
										.getText();
								logger.info("Current stage of the order is=" + Orderstage);
								msg.append("Current stage of the order is=" + Orderstage + "\n");
								getScreenshot(Driver, "RTE_" + Orderstage + TrackingNo);

								// --Click on save
								try {
									WebElement RTESAve = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(RTESAve).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(RTESAve));
									act.moveToElement(RTESAve).click().perform();
									logger.info("Clicked on Accept button");

								} catch (Exception Saveb) {
									WebElement RTESAve = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(RTESAve).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(RTESAve));
									js.executeScript("arguments[0].click();", RTESAve);
									logger.info("Clicked on Accept button");

								}
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
									String Errmsg = Driver.findElement(By.id("errorid")).getText();
									logger.info("validation message=" + Errmsg);
									// --Enter Actual PU
									String tzone = isElementPresent("RTEDelTimeZone_xpath").getText();
									String rectime = getTimeAsTZone(tzone);
									WebElement ActDelTime = Driver.findElement(By.id("txtActDlTime"));
									ActDelTime.clear();
									ActDelTime.sendKeys(rectime);
									ActDelTime.sendKeys(Keys.TAB);

									// --Click on save
									try {
										WebElement RTESAve = Driver.findElement(By.id("idiconsave"));
										act.moveToElement(RTESAve).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(RTESAve));
										act.moveToElement(RTESAve).click().perform();
										logger.info("Clicked on Accept button");

									} catch (Exception Saveb) {
										WebElement RTESAve = Driver.findElement(By.id("idiconsave"));
										act.moveToElement(RTESAve).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(RTESAve));
										js.executeScript("arguments[0].click();", RTESAve);
										logger.info("Clicked on Accept button");

									}
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									try {
										wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
										Errmsg = Driver.findElement(By.id("errorid")).getText();
										logger.info("validation message=" + Errmsg);

										// --Enter Signature
										WebElement Sign = Driver.findElement(By.id("txtsign"));
										act.moveToElement(Sign).build().perform();
										Sign.clear();
										Sign.sendKeys("RV");
										logger.info("Enter signature");

										// --Click on save
										try {
											WebElement RTESAve = Driver.findElement(By.id("idiconsave"));
											act.moveToElement(RTESAve).build().perform();
											wait.until(ExpectedConditions.elementToBeClickable(RTESAve));
											js.executeScript("arguments[0].click();", RTESAve);
											logger.info("Clicked on Accept button");

										} catch (Exception Saveb) {
											WebElement RTESAve = Driver.findElement(By.id("idiconsave"));
											act.moveToElement(RTESAve).build().perform();
											wait.until(ExpectedConditions.elementToBeClickable(RTESAve));
											act.moveToElement(RTESAve).click().perform();
											logger.info("Clicked on Accept button");

										}
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
										try {
											wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
											Errmsg = Driver.findElement(By.id("errorid")).getText();
											logger.info("validation message=" + Errmsg);

											if (Errmsg.contains(
													"Actual Delivery Datetime can not be less than or equal to Actual Pickup Datetime.")) {

												tzone = isElementPresent("RTEDelTimeZone_xpath").getText();
												rectime = getTimeAsTZone(tzone);
												ActDelTime = Driver.findElement(By.id("txtActDlTime"));
												ActDelTime.clear();
												ActDelTime.sendKeys(rectime);
												ActDelTime.sendKeys(Keys.TAB);
												logger.info("Entered Actual Deliver Time");

												// --Click on save
												try {
													WebElement RTESAve = Driver.findElement(By.id("idiconsave"));
													act.moveToElement(RTESAve).build().perform();
													wait.until(ExpectedConditions.elementToBeClickable(RTESAve));
													js.executeScript("arguments[0].click();", RTESAve);
													logger.info("Clicked on Accept button");

												} catch (Exception Saveb) {
													WebElement RTESAve = Driver.findElement(By.id("idiconsave"));
													act.moveToElement(RTESAve).build().perform();
													wait.until(ExpectedConditions.elementToBeClickable(RTESAve));
													act.moveToElement(RTESAve).click().perform();
													logger.info("Clicked on Accept button");

												}
												wait.until(ExpectedConditions.invisibilityOfElementLocated(
														By.xpath("//*[@class=\"ajax-loadernew\"]")));
											}
										} catch (Exception VMsg) {
											logger.info("validation message is not displayed for actual date time");

										}
									} catch (Exception e) {
										logger.info("validation message is not displayed");
									}
								} catch (Exception e) {
									logger.info("validation message is not displayed");
								}
								// --Search the job
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearchRTE")));
								Driver.findElement(By.id("txtBasicSearchRTE")).clear();
								Driver.findElement(By.id("txtBasicSearchRTE")).sendKeys(TrackingNo);
								RTESearch = Driver.findElement(By.id("btnRTESearch2"));
								act.moveToElement(RTESearch).build().perform();
								js.executeScript("arguments[0].click();", RTESearch);
								logger.info("Click on Search button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
									NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
									if (NoData.isDisplayed()) {
										logger.info("Job is not Delivered");
										msg.append("Job is not Delivered" + "\n");

									}
								} catch (Exception e) {
									// --Again click on Record
									Record = Driver.findElement(By.xpath("//*[@id=\"idRTEList\"]//span/strong"));
									act.moveToElement(Record).build().perform();
									js.executeScript("arguments[0].click();", Record);
									logger.info("Clicked on the record");

									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.xpath("//*[@ng-form=\"RouteForm\"]")));
									Orderstage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]"))
											.getText();
									logger.info("Current stage of the order is=" + Orderstage);
									msg.append("Current stage of the order is=" + Orderstage + "\n");

									if (Orderstage.equalsIgnoreCase("Delivered@Stop 2 of 2")) {
										logger.info("Job is delivered");
										// --Close button
										WebElement close = Driver.findElement(By.id("idiconclose"));
										act.moveToElement(close).build().perform();
										js.executeScript("arguments[0].click();", close);
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
						Orderstage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
						logger.info("Current stage of the order is=" + Orderstage);
						msg.append("Current stage of the order is=" + Orderstage + "\n");
						getScreenshot(Driver, "RTE_" + Orderstage + TrackingNo);
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
							ActPUTime.sendKeys(Keys.TAB);

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
						Driver.findElement(By.id("txtBasicSearchRTE")).sendKeys(TrackingNo);
						RTESearch = Driver.findElement(By.id("btnRTESearch2"));
						act.moveToElement(RTESearch).build().perform();
						js.executeScript("arguments[0].click();", RTESearch);
						logger.info("Click on Search button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
						if (NoData.isDisplayed()) {
							logger.info("Record is not available with search parameters");
						} else {
							logger.info("Record is available with search parameters");
							// --Again click on Record
							Record = Driver.findElement(By.xpath("//*[@id=\"idRTEList\"]//span/strong"));
							act.moveToElement(Record).build().perform();
							js.executeScript("arguments[0].click();", Record);
							logger.info("Clicked on the record");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							wait.until(ExpectedConditions
									.visibilityOfElementLocated(By.xpath("//*[@ng-form=\"RouteForm\"]")));

							Orderstage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
							logger.info("Current stage of the order is=" + Orderstage);
							msg.append("Current stage of the order is=" + Orderstage + "\n");
							getScreenshot(Driver, "RTE_" + Orderstage + TrackingNo);

							// --Click on save
							try {
								WebElement RTESAve = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(RTESAve).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(RTESAve));
								act.moveToElement(RTESAve).click().perform();
								logger.info("Clicked on Accept button");

							} catch (Exception Saveb) {
								WebElement RTESAve = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(RTESAve).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(RTESAve));
								js.executeScript("arguments[0].click();", RTESAve);
								logger.info("Clicked on Accept button");

							}
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
								ActDelTime.sendKeys(Keys.TAB);

								// --Click on save
								try {
									WebElement RTESAve = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(RTESAve).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(RTESAve));
									act.moveToElement(RTESAve).click().perform();
									logger.info("Clicked on Accept button");

								} catch (Exception Saveb) {
									WebElement RTESAve = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(RTESAve).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(RTESAve));
									js.executeScript("arguments[0].click();", RTESAve);
									logger.info("Clicked on Accept button");

								}
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								try {
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
									Errmsg = Driver.findElement(By.id("errorid")).getText();
									logger.info("validation message=" + Errmsg);

									// --Enter Signature
									WebElement Sign = Driver.findElement(By.id("txtsign"));
									act.moveToElement(Sign).build().perform();
									Sign.clear();
									Sign.sendKeys("RV");
									logger.info("Enter signature");

									// --Click on save
									try {
										WebElement RTESAve = Driver.findElement(By.id("idiconsave"));
										act.moveToElement(RTESAve).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(RTESAve));
										act.moveToElement(RTESAve).click().perform();
										logger.info("Clicked on Accept button");

									} catch (Exception Saveb) {
										WebElement RTESAve = Driver.findElement(By.id("idiconsave"));
										act.moveToElement(RTESAve).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(RTESAve));
										js.executeScript("arguments[0].click();", RTESAve);
										logger.info("Clicked on Accept button");

									}
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
							Driver.findElement(By.id("txtBasicSearchRTE")).sendKeys(TrackingNo);
							RTESearch = Driver.findElement(By.id("btnRTESearch2"));
							act.moveToElement(RTESearch).build().perform();
							js.executeScript("arguments[0].click();", RTESearch);
							logger.info("Click on Search button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
								NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
								if (NoData.isDisplayed()) {
									logger.info("Job is not Delivered");
									msg.append("Job is not Delivered" + "\n");

								}
							} catch (Exception e) {
								logger.error(e);
								getScreenshot(Driver, "e_RTE_Error");
								// --Again click on Record
								Record = Driver.findElement(By.xpath("//*[@id=\"idRTEList\"]//span/strong"));
								act.moveToElement(Record).build().perform();
								js.executeScript("arguments[0].click();", Record);
								logger.info("Clicked on the record");

								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.xpath("//*[@ng-form=\"RouteForm\"]")));
								Orderstage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]"))
										.getText();
								logger.info("Current stage of the order is=" + Orderstage);
								msg.append("Current stage of the order is=" + Orderstage + "\n");

								if (Orderstage.equalsIgnoreCase("Delivered@Stop 2 of 2")) {
									logger.info("Job is delivered");
									// --Close button
									WebElement close = Driver.findElement(By.id("idiconclose"));
									act.moveToElement(close).build().perform();
									js.executeScript("arguments[0].click();", close);
									logger.info("Clicked on Close button");
									wait.until(ExpectedConditions
											.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

								} else {
									logger.info("Job is not delivered");

								}

							}
						}
					} else if (Orderstage.contains("Deliver@Stop 2 of 2")) {
						Orderstage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
						logger.info("Current stage of the order is=" + Orderstage);
						msg.append("Current stage of the order is=" + Orderstage + "\n");
						getScreenshot(Driver, "RTE_" + Orderstage + TrackingNo);

						// --Click on save
						try {
							WebElement RTESAve = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(RTESAve).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(RTESAve));
							act.moveToElement(RTESAve).click().perform();
							logger.info("Clicked on Accept button");

						} catch (Exception Saveb) {
							WebElement RTESAve = Driver.findElement(By.id("idiconsave"));
							act.moveToElement(RTESAve).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(RTESAve));
							js.executeScript("arguments[0].click();", RTESAve);
							logger.info("Clicked on Accept button");

						}
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
							ActDelTime.sendKeys(Keys.TAB);

							// --Click on save
							try {
								WebElement RTESAve = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(RTESAve).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(RTESAve));
								act.moveToElement(RTESAve).click().perform();
								logger.info("Clicked on Accept button");

							} catch (Exception Saveb) {
								WebElement RTESAve = Driver.findElement(By.id("idiconsave"));
								act.moveToElement(RTESAve).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(RTESAve));
								js.executeScript("arguments[0].click();", RTESAve);
								logger.info("Clicked on Accept button");

							}
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
								Errmsg = Driver.findElement(By.id("errorid")).getText();
								logger.info("validation message=" + Errmsg);

								// --Enter Signature
								WebElement Sign = Driver.findElement(By.id("txtsign"));
								act.moveToElement(Sign).build().perform();
								Sign.clear();
								Sign.sendKeys("RV");
								logger.info("Enter signature");

								// --Click on save
								try {
									WebElement RTESAve = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(RTESAve).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(RTESAve));
									act.moveToElement(RTESAve).click().perform();
									logger.info("Clicked on Accept button");

								} catch (Exception Saveb) {
									WebElement RTESAve = Driver.findElement(By.id("idiconsave"));
									act.moveToElement(RTESAve).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(RTESAve));
									js.executeScript("arguments[0].click();", RTESAve);
									logger.info("Clicked on Accept button");

								}
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
						Driver.findElement(By.id("txtBasicSearchRTE")).sendKeys(TrackingNo);
						RTESearch = Driver.findElement(By.id("btnRTESearch2"));
						act.moveToElement(RTESearch).build().perform();
						js.executeScript("arguments[0].click();", RTESearch);
						logger.info("Click on Search button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						try {
							wait.until(
									ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
							NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
							if (NoData.isDisplayed()) {
								logger.info("Job is not Delivered");
								msg.append("Job is not Delivered" + "\n");

							}
						} catch (Exception e) {
							logger.error(e);
							getScreenshot(Driver, "e_RTE_Error");
							// --Again click on Record
							Record = Driver.findElement(By.xpath("//*[@id=\"idRTEList\"]//span/strong"));
							act.moveToElement(Record).build().perform();
							js.executeScript("arguments[0].click();", Record);
							logger.info("Clicked on the record");

							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							wait.until(ExpectedConditions
									.visibilityOfElementLocated(By.xpath("//*[@ng-form=\"RouteForm\"]")));
							Orderstage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
							logger.info("Current stage of the order is=" + Orderstage);
							msg.append("Current stage of the order is=" + Orderstage + "\n");

							if (Orderstage.equalsIgnoreCase("Delivered@Stop 2 of 2")) {
								logger.info("Job is delivered");
								// --Close button
								WebElement close = Driver.findElement(By.id("idiconclose"));
								act.moveToElement(close).build().perform();
								js.executeScript("arguments[0].click();", close);
								logger.info("Clicked on Close button");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

							} else {
								logger.info("Job is not delivered");

							}

						}
					} else if (Orderstage.contains("Delivered@Stop 2 of 2")) {
						logger.info("Job is delivered");
						// --Close button
						WebElement close = Driver.findElement(By.id("idiconclose"));
						act.moveToElement(close).build().perform();
						js.executeScript("arguments[0].click();", close);
						logger.info("Clicked on Close button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					} else {
						logger.info("Unknown stage found");
					}
				}
			} catch (Exception NoData1) {
				try {
					logger.error(NoData1);
					getScreenshot(Driver, "NoData1_RTE_Error");
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
					WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
					if (NoData.isDisplayed()) {
						logger.info("Job is not exist with the search parameters");
						msg.append("Job is not exist with the search parameters" + "\n\n");

					}
				} catch (Exception OnBoard) {
					logger.error(OnBoard);
					getScreenshot(Driver, "OnBoard_RTE_Error");
					String Orderstage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
					logger.info("Current stage of the order is=" + Orderstage);
					msg.append("Current stage of the order is=" + Orderstage + "\n");
					logger.info("Issue in Order stage==" + Orderstage);
					msg.append("Issue in Order stage==" + Orderstage + "\n\n");
					getScreenshot(Driver, "RTEStageIssue_" + Orderstage);

				}
			}

			/*
			 * logger.info("RTE Order Processing Test=PASS");
			 * msg.append("RTE Order Processing Test=PASS" + "\n\n");
			 */
		} catch (

		Exception RTEE) {
			logger.error(RTEE);
			getScreenshot(Driver, "RTE_error");
			/*
			 * logger.info("RTE Order Processing Test=FAIL");
			 * msg.append("RTE Order Processing Test=FAIL" + "\n\n");
			 */

		}

		// logger.info("=====RTE Order Processing Test End=====");
		// msg.append("=====RTE Order Processing Test End=====" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}
}
