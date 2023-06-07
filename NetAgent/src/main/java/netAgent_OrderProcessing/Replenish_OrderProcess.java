package netAgent_OrderProcessing;

import java.io.IOException;
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

import netAgent_BasePackage.BaseInit;

public class Replenish_OrderProcess extends BaseInit {

	@Test
	public void orderProcessReplenishJOB() throws EncryptedDocumentException, InvalidFormatException, IOException {

		WebDriverWait wait = new WebDriverWait(Driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);

		logger.info("=====Replenish Order Processing Test Start=====");
		// msg.append("=====Replenish Order Processing Test Start=====" + "\n\n");

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

			getScreenshot(Driver, "TaskLog_OperationsReplenish");

			String ServiceID = getData("OrderProcessing", 10, 0);
			logger.info("ServiceID is==" + ServiceID);
			msg.append("ServiceID==" + ServiceID + "\n");
			String PUID = getData("OrderProcessing", 10, 1);
			logger.info("PickUpID is==" + PUID);
			msg.append("PickUpID==" + PUID + "\n");

			try {
				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//*[@id=\"inventory\"][contains(@class,'active')]")));
				logger.info("Inventory tab is already selected");

			} catch (Exception Operation) {
				Driver.findElement(By.id("inventory")).click();
				logger.info("Click on Inventory tab");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtBasicSearch")));
				PUID = getData("OrderProcessing", 10, 1);
				Driver.findElement(By.id("txtBasicSearch")).clear();
				logger.info("Clear search input");
				Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
				logger.info("Enter pickupID in search input");
				WebElement InvSearch = Driver.findElement(By.id("btnSearch2"));
				wait.until(ExpectedConditions.elementToBeClickable(InvSearch));
				act.moveToElement(InvSearch).build().perform();
				js.executeScript("arguments[0].click();", InvSearch);
				logger.info("Click on Search button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			}

			try {
				// --click on record
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("idparttable")));
				logger.info("Record is available with search parameters");
				String Orderstage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
				logger.info("Current stage of the order is=" + Orderstage);
				msg.append("Current stage of the order is=" + Orderstage + "\n");

				getScreenshot(Driver, "Rplnsh_" + Orderstage + PUID);

				// --Click on Update
				WebElement Update = Driver.findElement(By.id("idupdateicon"));
				act.moveToElement(Update).build().perform();
				js.executeScript("arguments[0].click();", Update);
				logger.info("Clicked on the Update");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				try {
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("fslavailableloclist")));
					logger.info("Alert is displayed for create sub ASN");
					// --ALert
					// --Close the pop up
					WebElement AlCncl = Driver.findElement(By.id("btnCancel"));
					js.executeScript("arguments[0].click();", AlCncl);
					logger.info("Click on Close PopUp");
				} catch (Exception e) {
					logger.info("Alert is not displayed for create sub ASN");
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
					String ValMsg = Driver.findElement(By.id("errorid")).getText();
					logger.info("Validation Msg==" + ValMsg);
				}
				// --Click on Save
				WebElement Save = Driver.findElement(By.id("idsaveicon"));
				act.moveToElement(Save).build().perform();
				js.executeScript("arguments[0].click();", Save);
				logger.info("Clicked on the Save");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

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
						SerialNo.sendKeys("SerialNo" + System.currentTimeMillis());
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
						Save = Driver.findElement(By.id("idsaveicon"));
						act.moveToElement(Save).click().perform();
						logger.info("Clicked on the Save");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
					} catch (Exception staleelement) {
						try {
							WebElement parttable1 = Driver.findElement(By.xpath("//*[@id=\"parttable\"]/tbody"));
							List<WebElement> Partrow1 = parttable1.findElements(By.tagName("tr"));
							logger.info("total No of rows in part table are==" + Partrow.size());
							for (int partR = part; partR < Partrow1.size();) {
								WebElement SerialNo = Partrow1.get(partR).findElement(By.id("txtSerialNo"));
								act.moveToElement(SerialNo).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(SerialNo));
								SerialNo.clear();
								SerialNo.sendKeys("SerialNo" + System.currentTimeMillis());
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
								Save = Driver.findElement(By.id("idsaveicon"));
								act.moveToElement(Save).click().perform();
								logger.info("Clicked on the Save");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
								break;
							}
						} catch (Exception StaleElement) {
							WebElement parttable1 = Driver.findElement(By.xpath("//*[@id=\"parttable\"]/tbody"));
							List<WebElement> Partrow1 = parttable1.findElements(By.tagName("tr"));
							logger.info("total No of rows in part table are==" + Partrow.size());
							for (int partRw = part; partRw < Partrow1.size();) {
								WebElement SerialNo = Partrow1.get(partRw).findElement(By.id("txtSerialNo"));
								act.moveToElement(SerialNo).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(SerialNo));
								SerialNo.clear();
								SerialNo.sendKeys("SerialNo" + System.currentTimeMillis());
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
								Save = Driver.findElement(By.id("idsaveicon"));
								act.moveToElement(Save).click().perform();
								logger.info("Clicked on the Save");
								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
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
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

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
						getScreenshot(Driver, "Rplnsh_PrintLabel_" + PUID);

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
				PUID = getData("OrderProcessing", 10, 1);
				Driver.findElement(By.id("txtBasicSearch")).clear();
				logger.info("Clear search input");
				Driver.findElement(By.id("txtBasicSearch")).sendKeys(PUID);
				logger.info("Enter pickupID in search input");
				WebElement InvSearch = Driver.findElement(By.id("btnSearch2"));
				wait.until(ExpectedConditions.elementToBeClickable(InvSearch));
				act.moveToElement(InvSearch).build().perform();
				js.executeScript("arguments[0].click();", InvSearch);
				logger.info("Click on Search button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
					WebElement NoData1 = Driver.findElement(By.className("dx-datagrid-nodata"));
					if (NoData1.isDisplayed()) {
						logger.info("Order is Replenished");
						msg.append("Order is Replenished" + "\n");

					}
				} catch (Exception Data) {
					logger.info("Order is not Replenished");
					msg.append("Order is not Replenished" + "\n");

				}
			} catch (Exception NoData1) {
				logger.error(NoData1);
				getScreenshot(Driver, "NoData1_Replenish_Error");

				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-datagrid-nodata")));
					WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
					if (NoData.isDisplayed()) {
						logger.info("Job is not exist with the search parameters");
						msg.append("Job is not exist with the search parameters" + "\n\n");

					}
				} catch (Exception OnBoard) {
					logger.error(OnBoard);
					getScreenshot(Driver, "OnBoard_Replenish_Error");

					String Orderstage = Driver.findElement(By.xpath("//h3[contains(@class,'panel-title')]")).getText();
					logger.info("Current stage of the order is=" + Orderstage);
					msg.append("Current stage of the order is=" + Orderstage + "\n");
					logger.info("Issue in Order stage==" + Orderstage);
					msg.append("Issue in Order stage==" + Orderstage + "\n\n");
					getScreenshot(Driver, "Rplnsh_ReplenishStageIssue_" + Orderstage);

				}
			}
			logger.info("Replenish Order Processing Test=PASS");
			msg.append("Replenish Order Processing Test=PASS" + "\n\n");

		} catch (Exception ReplenishE) {
			logger.error(ReplenishE);
			getScreenshot(Driver, "Replenish_error");
			logger.info("Replenish Order Processing Test=FAIL");
			msg.append("Replenish Order Processing Test=FAIL" + "\n\n");

		}
		logger.info("=====Replenish Order Processing Test End=====");
		// msg.append("=====Replenish Order Processing Test End=====" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}
}
