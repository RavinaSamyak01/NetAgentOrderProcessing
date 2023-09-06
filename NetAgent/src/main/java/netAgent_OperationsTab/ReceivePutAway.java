package netAgent_OperationsTab;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class ReceivePutAway extends BaseInit {

	@Test
	public void receivePutAway() throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(Driver, 40);
		// JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		logger.info("=======Receive To PutAway Test Start=======");
		// msg.append("=======Receive To PutAway Test Start=======" + "\n\n");

		try {
			// Go To TaskLog
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idOperations")));
			isElementPresent("Operations_id").click();
			logger.info("Clicked on Operations");

			isElementPresent("TaskLog_linkText").click();
			logger.info("Clicked on TaskLog");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));

			getScreenshot(Driver, "TaskLog_Operations");

			// --Combined Tab
			wait.until(ExpectedConditions.elementToBeClickable(By.id("combined")));
			isElementPresent("TLCombinedTab_id").click();
			logger.info("Click on Combined Tab");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			// --Go to Advance Tab
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			isElementPresent("TLAdSearchTab_id").click();
			logger.info("Click on Advance Search");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("AdvancesSearch")));

			// --Search by ASN Type--PutAway
			// --Deselect OrderType
			Select Ordertype = new Select(isElementPresent("TLADOrderType_id"));
			Ordertype.selectByVisibleText("(select)");
			logger.info("Deselected OrderType");
			System.out.println("Deselected OrderType");

			// ---Remove PickUp
			isElementPresent("TLPickUp_id").clear();
			logger.info("Cleared Pickup");
			System.out.println("Cleared Pickup");

			// --Remove Service
			isElementPresent("TLService_id").clear();
			System.out.println("Cleared Service");
			logger.info("Cleared Service");

			// --Enter Customer
			isElementPresent("TLCustomer_id").clear();
//					/isElementPresent("TLCustomer_id").sendKeys("950682");
			System.out.println("Enter Customer");
			logger.info("Clear Customer");

			//// --Remove PickUp
			isElementPresent("TLPickUp_id").clear();
			System.out.println("Cleared PickUp");
			logger.info("Cleared Pickup");

			/*
			 * // --ASNType isElementPresent("TLASNType_id").click();
			 * logger.info("Click on ASNType dropdown"); Thread.sleep(2000); // -Select All
			 * isElementPresent("TLASNReplenish_xpath").click();
			 * logger.info("Selected Replenish from ASNType dropdown"); Thread.sleep(2000);
			 * isElementPresent("TLASNType_id").click();
			 * System.out.println("Close the ASNType dropdown");
			 * logger.info("Close the ASNType dropdown");
			 */

			// --Click on Search
			isElementPresent("TLADSearchBTN_id").click();
			logger.info("Click on Search button of Advance Search");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			try {

				wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
				WebElement NoData = isElementPresent("NoData_className");

				if (NoData.isDisplayed()) {
					System.out.println("Data is not present related search parameter");
					logger.info("Data is not present related search parameter");
				}

			} catch (Exception Nodata) {
				logger.error(Nodata);

				try {
					System.out.println("Data is present related search parameter");
					logger.info("Data is present related search parameter");
					String Orderstage = isElementPresent("TLStage_xpath").getText();
					System.out.println("Current stage of the order is=" + Orderstage);
					logger.info("Current stage of the order is=" + Orderstage);
					getScreenshot(Driver, "ReplenishMent_PutAway");
				} catch (Exception multipledata) {
					logger.error(multipledata);
					System.out.println("Multiple Data is present related search parameter");
					logger.info("Multiple Data is present related search parameter");
					try {
						WebElement Repl = Driver
								.findElement(By.xpath("//td[contains(@aria-label,'RECEIPT - REPLENISHMENT')]/div"));
						act.moveToElement(Repl).build().perform();
						act.moveToElement(Repl).click().perform();
						logger.info("Click on the Replenish order");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						String Orderstage = isElementPresent("TLStage_xpath").getText();
						System.out.println("Current stage of the order is=" + Orderstage);
						logger.info("Current stage of the order is=" + Orderstage);
						getScreenshot(Driver, "ReplenishMent_PutAway");

						// --Click on Receive to PutAway
						isElementPresent("TLRecToPutAway_linkText").click();
						System.out.println("Clicked on RECEIVE TO PUTAWAY");
						logger.info("Clicked on RECEIVE TO PUTAWAY");
						// --Enter Accepted Quantity
						// --table
						WebElement parttable = isElementPresent("TLPartTable_xpath");
						List<WebElement> Partrow = parttable.findElements(By.tagName("tr"));
						System.out.println("total No of rows in part table are==" + Partrow.size());
						logger.info("total No of rows in part table are==" + Partrow.size());

						for (int part = 0; part < Partrow.size(); part++) {
							// --Find SerialNo column
							try {
								WebElement SerialNo = Partrow.get(part).findElement(By.id("txtSerialNo"));
								act.moveToElement(SerialNo).build().perform();
								logger.info("Moved to SerialNo");
								wait.until(ExpectedConditions.elementToBeClickable(SerialNo));
								SerialNo.clear();
								logger.info("Cleared SerialNo");
								SerialNo.sendKeys("SerialNo" + part);
								logger.info("Entered SerialNo" + part);
								System.out.println("Enetered serial Number in " + part + " part");
								logger.info("Enetered serial Number in " + part + " part");

								// --Enter Accepted Quantity
								WebElement AccQty = Partrow.get(part).findElement(By.id("txtReceivedQty"));
								act.moveToElement(AccQty).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(AccQty));
								AccQty.clear();
								logger.info("Cleared Accepted Qty");
								AccQty.sendKeys("1");
								logger.info("Entered Accepted Qty");
								AccQty.sendKeys(Keys.TAB);
								System.out.println("Enetered Accepted Quantity in " + part + " part");
								logger.info("Enetered Accepted Quantity in " + part + " part");
								// --Click on Save
								wait.until(ExpectedConditions.elementToBeClickable(By.id("idsaveicon")));
								WebElement Save = isElementPresent("TLPTSave_id");
								act.moveToElement(Save).click().perform();
								logger.info("Click on Save");
								System.out.println("Clicked on the Save");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							} catch (Exception staleelement) {
								logger.error(staleelement);

								try {
									WebElement parttable1 = isElementPresent("TLPartTable_xpath");
									List<WebElement> Partrow1 = parttable1.findElements(By.tagName("tr"));
									System.out.println("total No of rows in part table are==" + Partrow.size());
									logger.info("total No of rows in part table are==" + Partrow.size());
									for (int partR = part; partR < Partrow1.size();) {
										WebElement SerialNo = Partrow1.get(partR).findElement(By.id("txtSerialNo"));
										logger.info("Moved to SerialNo");
										wait.until(ExpectedConditions.elementToBeClickable(SerialNo));
										SerialNo.clear();
										logger.info("Cleared SerialNo");
										SerialNo.sendKeys("SerialNo" + part);
										logger.info("Entered SerialNo" + part);
										System.out.println("Enetered serial Number in " + part + " part");
										logger.info("Enetered serial Number in " + part + " part");

										// --Enter Accepted Quantity
										WebElement AccQty = Partrow1.get(partR).findElement(By.id("txtReceivedQty"));
										act.moveToElement(AccQty).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(AccQty));
										AccQty.clear();
										logger.info("Cleared Accepted Qty");
										AccQty.sendKeys("1");
										logger.info("Entered Accepted Qty");
										AccQty.sendKeys(Keys.TAB);
										System.out.println("Enetered Accepted Quantity in " + part + " part");
										logger.info("Enetered Accepted Quantity in " + part + " part");
										// --Click on Save
										wait.until(ExpectedConditions.elementToBeClickable(By.id("idsaveicon")));
										WebElement Save = isElementPresent("TLPTSave_id");
										act.moveToElement(Save).click().perform();
										logger.info("Click on Save");
										System.out.println("Clicked on the Save");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
										break;
									}
								} catch (Exception StaleElement) {
									logger.error(staleelement);
									WebElement parttable1 = isElementPresent("TLPartTable_xpath");
									List<WebElement> Partrow1 = parttable1.findElements(By.tagName("tr"));
									System.out.println("total No of rows in part table are==" + Partrow.size());
									logger.info("total No of rows in part table are==" + Partrow.size());
									for (int partRw = part; partRw < Partrow1.size();) {
										WebElement SerialNo = Partrow1.get(partRw).findElement(By.id("txtSerialNo"));
										logger.info("Moved to SerialNo");
										wait.until(ExpectedConditions.elementToBeClickable(SerialNo));
										SerialNo.clear();
										logger.info("Cleared SerialNo");
										SerialNo.sendKeys("Entered SerialNo" + part);
										logger.info("Entered SerialNo" + part);
										System.out.println("Enetered serial Number in " + part + " part");
										logger.info("Enetered serial Number in " + part + " part");

										// --Enter Accepted Quantity
										WebElement AccQty = Partrow1.get(partRw).findElement(By.id("txtReceivedQty"));
										act.moveToElement(AccQty).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(AccQty));
										AccQty.clear();
										logger.info("Cleared Accepted Qty");
										AccQty.sendKeys("1");
										logger.info("Entered Accepted Qty");
										AccQty.sendKeys(Keys.TAB);
										System.out.println("Enetered Accepted Quantity in " + part + " part");
										logger.info("Enetered Accepted Quantity in " + part + " part");
										// --Click on Save
										wait.until(ExpectedConditions.elementToBeClickable(By.id("idsaveicon")));
										WebElement Save = isElementPresent("TLPTSave_id");
										act.moveToElement(Save).click().perform();
										logger.info("Click on Save");
										System.out.println("Clicked on the Save");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
										break;
									}
								}
							}
						}

						// --Click on Update
						wait.until(ExpectedConditions.elementToBeClickable(By.id("idupdateicon")));
						WebElement update = isElementPresent("TLPTUpdate_id");
						act.moveToElement(update).click().perform();
						logger.info("Click on Update");
						System.out.println("Clicked on the update");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

						try {
							wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("errorid")));
							String ValMessage = isElementPresent("CCAImErr_id").getText();
							if (ValMessage.contains("Duplicate Serial")) {

								for (int part = 0; part < Partrow.size(); part++) {
									// --Find SerialNo column
									try {
										WebElement SerialNo = Partrow.get(part).findElement(By.id("txtSerialNo"));
										act.moveToElement(SerialNo).build().perform();
										logger.info("Moved to SerialNo");
										wait.until(ExpectedConditions.elementToBeClickable(SerialNo));
										SerialNo.clear();
										logger.info("Cleared SerialNo");
										SerialNo.sendKeys("SerialNo" + System.currentTimeMillis());
										logger.info("Entered SerialNo" + System.currentTimeMillis());
										System.out.println("Enetered serial Number in " + part + " part");
										logger.info("Enetered serial Number in " + part + " part");

										// --Click on Save
										wait.until(ExpectedConditions.elementToBeClickable(By.id("idsaveicon")));
										WebElement Save = isElementPresent("TLPTSave_id");
										act.moveToElement(Save).click().perform();
										logger.info("Click on Save");
										System.out.println("Clicked on the Save");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ajax-loadernew\"]")));
									} catch (Exception staleelement) {
										logger.error(staleelement);

										try {
											WebElement parttable1 = isElementPresent("TLPartTable_xpath");
											List<WebElement> Partrow1 = parttable1.findElements(By.tagName("tr"));
											System.out.println("total No of rows in part table are==" + Partrow.size());
											logger.info("total No of rows in part table are==" + Partrow.size());
											for (int partR = part; partR < Partrow1.size();) {
												WebElement SerialNo = Partrow1.get(partR)
														.findElement(By.id("txtSerialNo"));
												logger.info("Moved to SerialNo");
												wait.until(ExpectedConditions.elementToBeClickable(SerialNo));
												SerialNo.clear();
												logger.info("Cleared SerialNo");
												SerialNo.sendKeys("SerialNo" + System.currentTimeMillis());
												logger.info("Entered SerialNo" + System.currentTimeMillis());
												System.out.println("Enetered serial Number in " + part + " part");
												logger.info("Enetered serial Number in " + part + " part");

												// --Click on Save
												wait.until(
														ExpectedConditions.elementToBeClickable(By.id("idsaveicon")));
												WebElement Save = isElementPresent("TLPTSave_id");
												act.moveToElement(Save).click().perform();
												logger.info("Click on Save");
												System.out.println("Clicked on the Save");
												wait.until(ExpectedConditions.invisibilityOfElementLocated(
														By.xpath("//*[@class=\"ajax-loadernew\"]")));
												break;
											}
										} catch (Exception StaleElement) {
											logger.error(staleelement);
											WebElement parttable1 = isElementPresent("TLPartTable_xpath");
											List<WebElement> Partrow1 = parttable1.findElements(By.tagName("tr"));
											System.out.println("total No of rows in part table are==" + Partrow.size());
											logger.info("total No of rows in part table are==" + Partrow.size());
											for (int partRw = part; partRw < Partrow1.size();) {
												WebElement SerialNo = Partrow1.get(partRw)
														.findElement(By.id("txtSerialNo"));
												logger.info("Moved to SerialNo");
												wait.until(ExpectedConditions.elementToBeClickable(SerialNo));
												SerialNo.clear();
												logger.info("Cleared SerialNo");
												SerialNo.sendKeys("SerialNo" + System.currentTimeMillis());
												logger.info("Entered SerialNo" + System.currentTimeMillis());
												System.out.println("Enetered serial Number in " + part + " part");
												logger.info("Enetered serial Number in " + part + " part");

												// --Click on Save
												wait.until(
														ExpectedConditions.elementToBeClickable(By.id("idsaveicon")));
												WebElement Save = isElementPresent("TLPTSave_id");
												act.moveToElement(Save).click().perform();
												logger.info("Click on Save");
												System.out.println("Clicked on the Save");
												wait.until(ExpectedConditions.invisibilityOfElementLocated(
														By.xpath("//*[@class=\"ajax-loadernew\"]")));
												break;
											}
										}
									}
								}

								// --Click on Update
								wait.until(ExpectedConditions.elementToBeClickable(By.id("idupdateicon")));
								update = isElementPresent("TLPTUpdate_id");
								act.moveToElement(update).click().perform();
								logger.info("Click on Update");
								System.out.println("Clicked on the update");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							}

						} catch (Exception ValMsg) {
							logger.info("Validation for duplicate serial No is not displayed.");

						}
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successid")));
						String SuccMsg = isElementPresent("TLSuccess_id").getText();
						System.out.println("Success Message==" + SuccMsg);
						logger.info("Success Message==" + SuccMsg);

						// --Close button
						WebElement Close = isElementPresent("TLIconClose_id");
						act.moveToElement(Close).click().perform();
						System.out.println("Clicked on the Close");
						logger.info("Click on Close");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

					} catch (Exception RPLSH) {
						logger.info("There is no Replenish order");

					}

					logger.info("Receive To PutAway Test=PASS");
					msg.append("Receive To PutAway Test=PASS" + "\n\n");

				}
			}

		} catch (Exception ReceiveToPutAwayE) {
			logger.error(ReceiveToPutAwayE);
			getScreenshot(Driver, "ReceiveToPutAwayE_error");
			logger.info("Receive To PutAway Test=FAIL");
			msg.append("Receive To PutAway Test=FAIL" + "\n\n");

		}
		logger.info("=======Receive To PutAway Test End=======");
		// msg.append("=======Receive To PutAway Test End=======" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}
}
