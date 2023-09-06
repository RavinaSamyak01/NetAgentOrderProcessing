package netAgent_Inventory;

import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class PutAway extends BaseInit {

	@Test
	public void putAway() throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		// Actions act = new Actions(Driver);

		logger.info("=======PutAway Test Start=======");
		//msg.append("=======PutAway Test Start=======" + "\n\n");

		try {
			// --Inventory
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idInventory")));
			Driver.findElement(By.id("idInventory")).click();
			logger.info("Clicked on Inventory");
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@aria-labelledby=\"idInventory\"]")));

			// --PutAway
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idPutaway")));
			Driver.findElement(By.id("idPutaway")).click();
			logger.info("Clicked on PutAway");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			for (int i = 0; i < new Select(Driver.findElement(By.id("drpFSLList"))).getOptions().size(); i++) {
				Select value = new Select(Driver.findElement(By.id("drpFSLList")));
				value.selectByIndex(i);
				String OptionValue = value.getFirstSelectedOption().getText();
				logger.info("Clicked on option==" + OptionValue);
				// --Search
				Driver.findElement(By.id("idbtnsearch")).click();
				System.out.println("Clicked on Search button");
				logger.info("Clicked on Search button");
				Thread.sleep(5000);

				WebElement NoData = Driver.findElement(By.className("dx-datagrid-nodata"));
				if (NoData.isDisplayed()) {
					System.out.println("Data is not present related search parameter");
					logger.info("Data is not present related search parameter");

				} else {
					System.out.println("Data is present related search parameter");
					logger.info("Data is present related search parameter");

					// Bin
					Driver.findElement(By.id("txtBinBarcode")).clear();
					logger.info("Cleared Bin");
					Driver.findElement(By.id("txtBinBarcode")).sendKeys("DEFAULTBIN");
					logger.info("Enter value in BIN");
					Driver.findElement(By.id("txtBinBarcode")).sendKeys(Keys.TAB);
					logger.info("Entered Tab");

					// get the STock IDs
					List<WebElement> stockIDS = Driver
							.findElements(By.xpath("//*[contains(@aria-label,'Stock ID,')]/span"));
					System.out.println("Total records are==" + stockIDS.size());
					logger.info("Total records are==" + stockIDS.size());
					for (int sID = 0; sID < stockIDS.size();) {
						String stockID = stockIDS.get(sID).getText();
						System.out.println("Stock ID is==" + stockID + "\n");
						logger.info("Stock ID is==" + stockID + "\n");

						// --Enter in stockID
						Driver.findElement(By.id("txtPartBarcode")).sendKeys(stockID);
						System.out.println("Entered stock ID");
						logger.info("Entered stock ID");
						Driver.findElement(By.id("txtPartBarcode")).sendKeys(Keys.TAB);
						logger.info("Press Tab");
						Thread.sleep(2000);
						getScreenshot(Driver, "PartMoved_" + stockID);

						// --check Success Message
						try {
							wait.until(ExpectedConditions
									.visibilityOfElementLocated(By.xpath("//*[@ng-bind=\"SuccessMsg\"]")));
							String SuccMsg = Driver.findElement(By.xpath("//*[@ng-bind=\"SuccessMsg\"]")).getText();
							System.out.println("Success message==" + SuccMsg + "\n");
							logger.info("Success message==" + SuccMsg + "\\n");

							// --Check Green sign for selected part
							// *[contains(@class,'showGreenTick ')]
							stockID = stockIDS.get(sID).getText();
							String xpath = "//*[@id=\"gridProcessPutaway\"]//tbody/tr[" + sID + 1 + "]//i";
							WebElement GreenTick = Driver.findElement(By.xpath(xpath));
							if (GreenTick.isDisplayed()) {
								System.out.println("Part is moved to DEFAULTBIN");
								logger.info("Part is moved to DEFAULTBIN");
								break;

							}

						} catch (Exception wrong) {
							logger.error(wrong);
							List<WebElement> stockIDSNew = Driver
									.findElements(By.xpath("//*[contains(@aria-label,'Stock ID,')]/span"));
							System.out.println("Total records are==" + stockIDSNew.size());
							logger.info("Total records are==" + stockIDSNew.size());
							for (int sID1 = sID; sID1 < stockIDSNew.size();) {
								String xpath = "//*[@id=\"gridProcessPutaway\"]//tbody/tr[" + sID1 + 1 + "]//i";
								WebElement GreenTick = Driver.findElement(By.xpath(xpath));
								if (GreenTick.isDisplayed()) {
									System.out.println("Part is moved to DEFAULTBIN");
									logger.info("Part is moved to DEFAULTBIN");
									break;

								}
							}
						}
						break;

					}
					break;
				}
			}
			/*
			 * // --FSL Dropdown Select FSL = new
			 * Select(Driver.findElement(By.id("drpFSLList")));
			 * FSL.selectByVisibleText("RAVINA FSL (F5491)");
			 * System.out.println("Selected FSL"); logger.info("Selected FSL");
			 * Thread.sleep(3000);
			 */

			logger.info("PutAway Test=PASS");
			msg.append("PutAway Test=PASS" + "\n\n");
			setResultData("Result", 20, 5, "PASS");

		} catch (Exception PutAwayE) {
			logger.error(PutAwayE);
			getScreenshot(Driver, "PutAway_error");
			logger.info("PutAway Test=FAIL");
			msg.append("PutAway Test=FAIL" + "\n\n");
			String Error = PutAwayE.getMessage();

			setResultData("Result", 20, 5, "FAIL");
			setResultData("Result", 20, 6, Error);

		}

		logger.info("=======PutAway Test End=======");
		//msg.append("=======PutAway Test End=======" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}

}
