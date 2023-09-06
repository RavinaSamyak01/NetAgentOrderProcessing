package netAgent_Reports;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class InventoryOnHandReport extends BaseInit {
	@Test
	public void OnHandReport() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		Actions act = new Actions(Driver);

		logger.info("=======OnHand Report Test Start=======");
		//msg.append("=======OnHand Report Test Start=======" + "\n\n");

		try {
			try {
				WebElement Reports = Driver.findElement(By.id("idReports"));
				act.moveToElement(Reports).build().perform();
				logger.info("Clicked on Reports");
				Thread.sleep(2000);
				wait.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.xpath("//*[@aria-labelledby=\"idReports\"]")));
				WebElement Inventory = Driver.findElement(By.id("idReportInventory"));
				act.moveToElement(Inventory).build().perform();
				logger.info("Clicked on Inventory");
				wait.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.xpath("//*[@aria-labelledby=\"idReportInventory\"]")));
				Driver.findElement(By.id("idOn")).click();
				logger.info("Clicked on OnHand Report");
			} catch (Exception e) {
				Driver.findElement(By.id("idReports")).click();
				logger.info("Clicked on Reports");
				Thread.sleep(2000);
				wait.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.xpath("//*[@aria-labelledby=\"idReports\"]")));
				Driver.findElement(By.id("idReportInventory")).click();
				logger.info("Clicked on Inventory");
				wait.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.xpath("//*[@aria-labelledby=\"idReportInventory\"]")));
				Driver.findElement(By.id("idOn")).click();
				logger.info("Clicked on OnHand Report");
			}

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			JavascriptExecutor js = (JavascriptExecutor) Driver;
			js.executeScript("window.scrollBy(0,-250)");
			Thread.sleep(2000);

			// select FSL
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn_ddlfslclass=")));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("btn_ddlfslclass=")));
			WebElement FSL = Driver.findElement(By.id("btn_ddlfslclass="));
			act.moveToElement(FSL).click().perform();
			Thread.sleep(2000);
			wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//*[@id=\"ddlfsl\"]//input[@id=\"idcheckboxInput\"]")));
			Driver.findElement(By.xpath("//*[@id=\"ddlfsl\"]//input[@id=\"idcheckboxInput\"]")).click();
			Thread.sleep(2000);
			Driver.findElement(By.id("btn_ddlfslclass=")).click();
			logger.info("Selected FSL");

			// select client
			Driver.findElement(By.id("btn_ddlClientclass=")).click();
			Thread.sleep(2000);
			Driver.findElement(By.xpath("//*[@id=\"ddlClient\"]//input[@id=\"idcheckboxInput\"]")).click();
			Thread.sleep(2000);
			Driver.findElement(By.id("btn_ddlClientclass=")).click();
			logger.info("Selected Client");

			// part num
			Driver.findElement(By.id("txtField1")).clear();
			logger.info("Clear PartNum");
			Driver.findElement(By.id("txtField1")).sendKeys(Part2);
			logger.info("Enter PartNum");

			// view report
			Driver.findElement(By.id("btnView")).click();
			logger.info("Click on View button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			// --wait to get the notification message
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@id=\"idwait\"]")));
			String WaitMsg = Driver.findElement(By.xpath("//label[@id=\"idwait\"]")).getText();
			System.out.println("Wait Message is==" + WaitMsg);
			logger.info("Wait Message is==" + WaitMsg);

			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//label[@id=\"idwait\"]")));
			} catch (Exception loading) {
				WebDriverWait waitLoad = new WebDriverWait(Driver, 120);
				waitLoad.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//label[@id=\"idwait\"]")));

			}
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("reportid")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//iframe[@id=\"myIframe\"]")));

			boolean AvArRp = Driver.findElement(By.xpath("//iframe[@id=\"myIframe\"]")).isDisplayed();

			if (AvArRp == false) {
				throw new Error("Error: On Hand Report grid not display");
			} else {
				WebElement ReportFrame = Driver.findElement(By.id("myIframe"));
				Driver.switchTo().frame(ReportFrame);
				logger.info("Switched to Report Frame");

				// --Click on Export button
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rpt_ctl05_ctl04_ctl00_ButtonImg")));
				Driver.findElement(By.id("rpt_ctl05_ctl04_ctl00_ButtonImg")).click();
				logger.info("Clicked on Export img");
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("rpt_ctl05_ctl04_ctl00_Menu")));

				// --Same file exist, delete it
				isFileDownloaded("NetShipOnHand_NA");

				// --Download report in all available format
				List<WebElement> ReportOpt = Driver
						.findElements(By.xpath("//*[@id=\"rpt_ctl05_ctl04_ctl00_Menu\"]//a"));
				logger.info("Total No of Options are==" + ReportOpt.size());
				for (int RO = 0; RO < ReportOpt.size(); RO++) {
					try {
						if (RO > 0) {
							Driver.findElement(By.id("rpt_ctl05_ctl04_ctl00_ButtonImg")).click();
							logger.info("Clicked on Export img");
							wait.until(ExpectedConditions
									.visibilityOfAllElementsLocatedBy(By.id("rpt_ctl05_ctl04_ctl00_Menu")));

						}
						String OptionName = ReportOpt.get(RO).getText();
						logger.info("Option name is==" + OptionName);

						ReportOpt.get(RO).click();
						logger.info("Clicked on Option to download Report");
						// --Wait until file download
						waitUntilFileToDownload("NetShipOnHand_NA");
						Thread.sleep(2000);
					} catch (Exception ClickIntercepted) {
						Driver.findElement(By.id("rpt_ctl05_ctl04_ctl00_ButtonImg")).click();
						logger.info("Clicked on Export img");
						List<WebElement> ReportOpt1 = Driver
								.findElements(By.xpath("//*[@id=\"rpt_ctl05_ctl04_ctl00_Menu\"]//a"));
						logger.info("Total No of Options are==" + ReportOpt.size());
						for (int RO1 = RO; RO1 < ReportOpt1.size();) {
							String OptionName1 = ReportOpt1.get(RO1).getText();
							logger.info("Option name is==" + OptionName1);

							// --CLick on option
							ReportOpt1.get(RO1).click();
							logger.info("Clicked on Option to download Report");
							// --Wait until file download
							waitUntilFileToDownload("NetShipOnHand_NA");
							Thread.sleep(2000);
							break;
						}
					}
				}

			}
			Driver.switchTo().defaultContent();
			logger.info("Switched to Main screen");
			getScreenshot(Driver, "OnHandReportView");

			// Reset
			Driver.findElement(By.id("btnReset")).click();
			logger.info("Click on Reset button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			logger.info("OnHand Report Test=PASS");
			msg.append("OnHand Report Test=PASS" + "\n\n");
			setResultData("Result", 27, 5, "PASS");

		} catch (Exception OnHandReportE) {
			logger.error(OnHandReportE);
			getScreenshot(Driver, "OnHandReport_error");
			logger.info("OnHand Report Test=FAIL");
			msg.append("OnHand Report Test=FAIL" + "\n\n");
			String Error = OnHandReportE.getMessage();

			setResultData("Result", 27, 5, "FAIL");
			setResultData("Result", 27, 6, Error);

		}

		logger.info("=======OnHand Report Test End=======");
		//msg.append("=======OnHand Report Test End=======" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}

}
