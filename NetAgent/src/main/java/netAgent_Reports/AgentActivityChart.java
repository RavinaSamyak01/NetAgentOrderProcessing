package netAgent_Reports;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class AgentActivityChart extends BaseInit {
	@Test
	public void AgentActivityChartReport() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		Actions act = new Actions(Driver);
		logger.info("=======Agent Activity Chart Report Test Start=======");
		// msg.append("=======Agent Activity Chart Report Test Start=======" + "\n\n");

		try {
			WebElement Reports = Driver.findElement(By.id("idReports"));
			act.moveToElement(Reports).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idReports")));
			Driver.findElement(By.id("idReports")).click();
			logger.info("Clicked on Reports");

			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Agent Activity Chart")));
			Driver.findElement(By.linkText("Agent Activity Chart")).click();
			logger.info("Clicked on Agent Activity Chart");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			JavascriptExecutor js = (JavascriptExecutor) Driver;
			js.executeScript("window.scrollBy(0,-250)");
			Thread.sleep(2000);

			// select all agent
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn_ddlCourierclass=")));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("btn_ddlCourierclass=")));
			WebElement Courier = Driver.findElement(By.id("btn_ddlCourierclass="));
			act.moveToElement(Courier).click().perform();
			Thread.sleep(2000);
			Driver.findElement(By.id("chkAllddlCourier")).click();
			logger.info("Select all");
			Driver.findElement(By.id("btn_ddlCourierclass=")).click();
			logger.info("Close Courier dropdown");

			// from date
			DateFormat dateFormatAgAcChrtRp = new SimpleDateFormat("MM/dd/yyyy");
			Date frmdtAgAcChrtRp = new Date();
			Date frmdt1AgAcChrtRp = addDays(frmdtAgAcChrtRp, -10);
			String FromDateAgAcChrtRp = dateFormatAgAcChrtRp.format(frmdt1AgAcChrtRp);

			Driver.findElement(By.id("txtValidFrom")).clear();
			logger.info("Cleared Valid From");
			Driver.findElement(By.id("txtValidFrom")).sendKeys(FromDateAgAcChrtRp);
			logger.info("Enter Valid From");
			WebElement AgAcChrtRpfdate = Driver.findElement(By.id("txtValidFrom"));
			AgAcChrtRpfdate.sendKeys(Keys.TAB);

			// to date
			Date todtAgAcChrtRp = new Date();
			String ToDateAgAcChrtRp = dateFormatAgAcChrtRp.format(todtAgAcChrtRp);

			Driver.findElement(By.id("txtValidTo")).clear();
			logger.info("Cleared Valid To");
			Driver.findElement(By.id("txtValidTo")).sendKeys(ToDateAgAcChrtRp);
			logger.info("Cleared Valid To");
			WebElement AgAcChrtRptdate = Driver.findElement(By.id("txtValidTo"));
			AgAcChrtRptdate.sendKeys(Keys.TAB);

			// click on view report

			Driver.findElement(By.id("btnView")).click();
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
			getScreenshot(Driver, "AgentActivityChartReport");

			boolean AvArRp = Driver.findElement(By.xpath("//iframe[@id=\"myIframe\"]")).isDisplayed();

			if (AvArRp == false) {
				throw new Error("Error: Agent Activity Chart Report grid not display");
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
				isFileDownloaded("AgentActivityChart");

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
						waitUntilFileToDownload("AgentActivityChart");
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
							waitUntilFileToDownload("AgentActivityChart");
							Thread.sleep(2000);
							break;
						}
					}
				}

			}
			Driver.switchTo().defaultContent();
			logger.info("Switched to Main screen");
			getScreenshot(Driver, "AgentActivityChartReportView");

			logger.info("Agent Activity Chart Report Test=PASS");
			msg.append("Agent Activity Chart Report Test=PASS" + "\n\n");
			setResultData("Result", 23, 5, "PASS");

		} catch (Exception AgentActivityChartRE) {
			logger.error(AgentActivityChartRE);
			getScreenshot(Driver, "AgentActivityChartR_error");
			logger.info("Agent Activity Chart Report Test=FAIL");
			msg.append("Agent Activity Chart Report Test=FAIL" + "\n\n");
			String Error = AgentActivityChartRE.getMessage();

			setResultData("Result", 23, 5, "FAIL");
			setResultData("Result", 23, 6, Error);

		}

		logger.info("=======Agent Activity Chart Report Test End=======");
		// msg.append("=======Agent Activity Chart Report Test End=======" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}
}
