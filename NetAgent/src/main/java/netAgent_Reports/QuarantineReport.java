package netAgent_Reports;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class QuarantineReport extends BaseInit {
	@Test
	public void quarantineReport() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		Actions act = new Actions(Driver);
		logger.info("=======Quarantine Report Test Start=======");
		//msg.append("=======Quarantine Report Test Start=======" + "\n\n");
		String Env = storage.getProperty("Env");
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idReports")));
			Driver.findElement(By.id("idReports")).click();
			logger.info("Clicked on Reports");

			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Quarantine Report")));
			Driver.findElement(By.linkText("Quarantine Report")).click();
			logger.info("Clicked on Quarantine Report");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			getScreenshot(Driver, "QuarantineReportScreen");

			JavascriptExecutor js = (JavascriptExecutor) Driver;
			js.executeScript("window.scrollBy(0,-250)");
			Thread.sleep(2000);

			
			if (Env.equalsIgnoreCase("STG")) {
			
			
			// --client select
			Select client = new Select(Driver.findElement(By.id("ddlClient")));
			client.selectByVisibleText("AUTOMATION INVENTORY PROFILE");
			System.out.println("Client selected");
			logger.info("Selected Client");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			}
			else if (Env.equalsIgnoreCase("TEST")) {
				Select client = new Select(Driver.findElement(By.id("ddlClient")));
				client.selectByVisibleText("AUTOMATION INVENTORY PROFILE");
				System.out.println("Client selected");
				logger.info("Selected Client");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				
			}else if (Env.equalsIgnoreCase("PROD")) {
				Select client = new Select(Driver.findElement(By.id("ddlClient")));
				client.selectByValue("103272");
				System.out.println("Client selected");
				logger.info("Selected Client");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				
			}

			// --FSL Name select
			Select fsl = new Select(Driver.findElement(By.id("ddlfsl")));
			fsl.selectByIndex(1);
			System.out.println("FSL selected");
			logger.info("Selected FSL");

			// click on view report
			WebElement View = Driver.findElement(By.id("btnView"));
			act.moveToElement(View).click().perform();
			logger.info("Click on View button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			// --wait to get the notification message
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[@id=\"idwait\"]")));
			String WaitMsg = Driver.findElement(By.xpath("//label[@id=\"idwait\"]")).getText();
			System.out.println("Wait Message is==" + WaitMsg);
			logger.info("Wait Message is==" + WaitMsg);

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//label[@id=\"idwait\"]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("reportid")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//iframe[@id=\"myIframe\"]")));

			boolean AvArRp = Driver.findElement(By.xpath("//iframe[@id=\"myIframe\"]")).isDisplayed();

			if (AvArRp == false) {
				throw new Error("Error: Quarantine Report grid not display");
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
				isFileDownloaded("NAQuarantine");

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
						waitUntilFileToDownload("NAQuarantine");
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
							waitUntilFileToDownload("NAQuarantine");
							Thread.sleep(2000);
							break;
						}
					}
				}

			}
			Driver.switchTo().defaultContent();
			logger.info("Switched to Main screen");
			getScreenshot(Driver, "QuarantineReportView");

			// --click on Reset button
			Driver.findElement(By.id("btnReset")).click();
			System.out.println("Clicked on Reset button");
			logger.info("Click on Reset button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			logger.info("Quarantine Report Test=PASS");
			msg.append("Quarantine Report Test=PASS" + "\n\n");
			setResultData("Result", 25, 5, "PASS");

		} catch (Exception QuarantineReportE) {
			logger.error(QuarantineReportE);
			getScreenshot(Driver, "QuarantineReportE_error");
			logger.info("Quarantine Report Test=FAIL");
			msg.append("Quarantine Report Test=FAIL" + "\n\n");
			String Error = QuarantineReportE.getMessage();

			setResultData("Result", 25, 5, "FAIL");
			setResultData("Result", 25, 6, Error);

		}

		logger.info("=======Quarantine Report Test End=======");
		//msg.append("=======Quarantine Report Test End=======" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}
}
