package netAgent_OperationsTab;

import java.io.StringWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.google.common.base.Throwables;

import netAgent_BasePackage.BaseInit;

public class Replenish extends BaseInit {
	@Test
	public void replenish() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 40);
		Actions act = new Actions(Driver);
		logger.info("=======Replenish Test Start=======");
		// msg.append("=======Replenish Test Start=======" + "\n\n");

		try {
			// Go to replenish screen
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idOperations")));
			Driver.findElement(By.id("idOperations")).click();
			logger.info("Click on Operations");

			wait.until(ExpectedConditions.elementToBeClickable(By.id("idReplenish")));
			Driver.findElement(By.id("idReplenish")).click();
			logger.info("Click on Replenish");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			getScreenshot(Driver, "Replenish");

			// --Scenario=Select Account and Add part
			// Select Account number
			WebElement AccDrop = Driver.findElement(By.id("ddlClient"));
			wait.until(ExpectedConditions.visibilityOf(AccDrop));
			wait.until(ExpectedConditions.elementToBeClickable(AccDrop));
			Select AccNo = new Select(AccDrop);
			AccNo.selectByVisibleText("AUTOMATION INVENTORY PROFILE");
			logger.info("Selected Account Number");
			Thread.sleep(2000);

			// add part
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("lnkPartDtl")));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("lnkPartDtl")));
			Driver.findElement(By.id("lnkPartDtl")).click();
			logger.info("Click on Add Parts");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			try {
				// Search with part# and name

				Driver.findElement(By.id("txtF1lable")).clear();
				Driver.findElement(By.id("txtF1lable")).sendKeys(Part1);
				logger.info("Entered Sprint Part Number");

				Driver.findElement(By.id("txtPartName")).clear();
				Driver.findElement(By.id("txtPartName")).sendKeys(Part1Name);
				logger.info("Entered Part Name");

				Driver.findElement(By.id("btnSearch")).click();
				logger.info("Click on Search button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				// select part
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@title='Add']")));
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@title='Add']")));
				Driver.findElement(By.xpath(".//*[@title='Add']")).click();
				logger.info("Selected the part");
				Thread.sleep(2000);

				getScreenshot(Driver, "Replenish-Part Popup");

				// delete part
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@title='Delete']")));
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@title='Delete']")));
				Driver.findElement(By.xpath(".//*[@title='Delete']")).click();
				logger.info("Deleted the part");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				// select part
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@title='Add']")));
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@title='Add']")));
				Driver.findElement(By.xpath(".//*[@title='Add']")).click();
				logger.info("Selected the part");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				// save part
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@title='Save']")));
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@title='Save']")));
				Driver.findElement(By.xpath(".//*[@title='Save']")).click();
				logger.info("Save the part");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			} catch (Exception Part) {
				logger.error(Part);
				logger.info("Part is not available with search parameters");

			}
			// click on + icon for expand

			Driver.findElement(By.id("lnkexpandlnkexpand_0")).click();
			logger.info("Click on Expand button");

			// Click on add line

			Driver.findElement(By.xpath(".//*[@id='lnklinedtl_0']")).click();
			logger.info("Click on Add Line button");

			/*
			 * Driver.findElement(By.id("ReplanishQty_0")).clear();
			 * Driver.findElement(By.id("ReplanishQty_0")).sendKeys("2");
			 * logger.info("Entered Replenished Qty");
			 */

			// --SerialNo

			Driver.findElement(By.id("idchildsegmentserialno")).clear();
			Driver.findElement(By.id("idchildsegmentserialno")).sendKeys("Serial" + System.currentTimeMillis());
			logger.info("Entered SerialNo");

			// click on add location
			Driver.findElement(By.linkText("Add Location")).click();
			logger.info("Click on Add Location link");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idlocationname")));

			Driver.findElement(By.id("idsavelocationprocess")).click();
			logger.info("Click on Save Location");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@compile=\"ErrorMsg\"]//li")));

			String Text = Driver.findElement(By.id("idValidation")).getText();
			logger.info("Validation Message is displayed==" + Text);
			System.out.println(Text);

			// --Enter Location Name
			Driver.findElement(By.id("idlocationname")).sendKeys("AutoLocation");
			logger.info("Entered Location Name");
			Driver.findElement(By.id("idsavelocationprocess")).click();
			logger.info("Click on Save Location");

			// --Cancel Add Location
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idAddLocationProcess")));
			WebElement AddLocCancel = Driver.findElement(By.id("idAddLocationProcess"));
			wait.until(ExpectedConditions.elementToBeClickable(AddLocCancel));
			act.moveToElement(AddLocCancel).click().build().perform();
			logger.info("Click on Cancel Location");
			Thread.sleep(2000);

			// Click on expand and collapse
			Driver.findElement(By.xpath("//a[contains(.,'Collapse All')]")).click();
			logger.info("Click on Expand");
			Driver.findElement(By.xpath("//a[contains(.,'Expand All')]")).click();
			logger.info("Click on Collapse");

			// Process Save for replenish
			Driver.findElement(By.id("hlksaveReplenish")).click();
			logger.info("Click on Save for Replenish");

			// Success message
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("success")));
			SuccMsgReplnsh = Driver.findElement(By.id("success")).getText();
			logger.info("Success Message is displayed==" + SuccMsgReplnsh);
			System.out.println(SuccMsgReplnsh);

			String winHandleBefore = Driver.getWindowHandle();

			// Open Print label
			Driver.findElement(By.id("idlabelgenerate")).click();
			logger.info("Click on Label Generate");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			for (String winHandle : Driver.getWindowHandles()) {
				Driver.switchTo().window(winHandle);
				Thread.sleep(2000);
				logger.info("Moved to Label Generate window");
				getScreenshot(Driver, "Replenish-Print Label");
			}

			Driver.close();
			logger.info("Close Label Generate window");

			// Switch back to original browser (first window)
			Driver.switchTo().window(winHandleBefore);
			logger.info("Moved to main window");
			Thread.sleep(2000);

			Driver.findElement(By.id("txtOrderNo")).click();
			logger.info("Click on Order No");

			WOID = Driver.findElement(By.id("txtOrderNo")).getAttribute("value");
			System.out.println("WorkOrder # " + WOID);
			logger.info("WorkOrder # " + WOID);
			msg.append("********** Replenish Work Order **********" + "\n");
			msg.append("Work Order Id : " + WOID + "\n");
			setResultData("Result", 6, 2, WOID);

			Thread.sleep(2000);

			WOTP = Driver.findElement(By.id("txtOrderType")).getAttribute("value");
			System.out.println("WorkOrder Type : " + WOTP);
			logger.info("WorkOrder Type : " + WOTP);
			msg.append("Work Order Id : " + WOTP + "\n\n");
			setResultData("Result", 6, 3, WOTP);

			Thread.sleep(2000);

			Driver.findElement(By.id("hlkCreateReplenish")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=\"scroll_div\"]")));

			logger.info("Replenish Test=PASS");
			msg.append("Replenish Test=PASS" + "\n\n");
			setResultData("Result", 6, 5, "PASS");

		} catch (Exception ReplenishE) {
			logger.error(ReplenishE);
			StringWriter sw = new StringWriter();
			String sStackTrace = sw.toString();
			getScreenshot(Driver, "Replenish_error");
			logger.info("Replenish Test=FAIL");
			msg.append("Replenish Test=FAIL" + "\n\n");
			String Error = ReplenishE.getMessage();
			setResultData("Result", 6, 5, "FAIL");
			setResultData("Result", 6, 6, Error);

		}

		logger.info("=======Replenish Test End=======");
		// msg.append("=======Replenish Test End=======" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}

}
