package netAgent_Inventory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class FSLStorage extends BaseInit {
	@Test
	public void fSLStorage() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);

		logger.info("=======FSL Storage Test Start=======");
		// msg.append("=======FSL Storage Test Start=======" + "\n\n");

		try {
			// --Click on Inventory
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idInventory")));
			Driver.findElement(By.id("idInventory")).click();
			logger.info("Clicked on Inventory");
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@aria-labelledby=\"idInventory\"]")));

			// --Click on FSL Storage
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idFSLStorage")));
			Driver.findElement(By.id("idFSLStorage")).click();
			logger.info("Clicked on FSL Storage");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			System.out.println(Driver.getTitle());
			logger.info("Title of the Screen is==" + Driver.getTitle());

			WebElement Nodata = Driver.findElement(By.className("dx-datagrid-nodata"));
			if (Nodata.isDisplayed()) {
				logger.info("There is no data with selected Month and year");

			} else {
				logger.info("Data is/are exist with selected Month and year");

			}

			getScreenshot(Driver, "FSLStorage");

			// --Month dropdown
			Select monthname = new Select(Driver.findElement(By.id("ddlmonth")));
			String selectedComboValue = monthname.getFirstSelectedOption().getText();
			System.out.println("Default Current Month Displayed in Combo : " + selectedComboValue);
			logger.info("Default Current Month Displayed in Combo : " + selectedComboValue);

			/*
			 * // Get current month Date date = new Date(); SimpleDateFormat formatter = new
			 * SimpleDateFormat("MMMMMMMMM"); String CurrentMonth = formatter.format(date);
			 */

			WebElement month = Driver.findElement(By.id("ddlmonth"));
			Select opt = new Select(month);
			opt.selectByVisibleText("February");
			logger.info("Selected Month");
			Driver.findElement(By.id("idSearchFsl")).click();
			logger.info("Clicked on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			Nodata = Driver.findElement(By.className("dx-datagrid-nodata"));
			if (Nodata.isDisplayed()) {
				logger.info("There is no data with selected Month and year");

			} else {
				logger.info("Data is/are exist with selected Month and year");

			}
			// Year dropdown
			Select yrname = new Select(Driver.findElement(By.id("ddlyear")));
			String selectedComboValue1 = yrname.getFirstSelectedOption().getText();
			System.out.println("Default Current Year Displayed in Combo : " + selectedComboValue1);
			logger.info("Default Current Year Displayed in Combo : " + selectedComboValue1);

			getScreenshot(Driver, "FSLStorage");

			// --Current Year
			/* String thisYear = new SimpleDateFormat("yyyy").format(new Date()); */

			WebElement year = Driver.findElement(By.id("ddlyear"));
			Select opt1 = new Select(year);
			opt1.selectByVisibleText("2022");
			logger.info("Selected Year");
			Driver.findElement(By.id("idSearchFsl")).click();
			logger.info("Clicked on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			Nodata = Driver.findElement(By.className("dx-datagrid-nodata"));
			if (Nodata.isDisplayed()) {
				logger.info("There is no data with selected Month and year");

			} else {
				logger.info("Data is/are exist with selected Month and year");

			}
			// --nothing to edit and save in the screen
			/*
			 * Driver.findElement(By.xpath(
			 * "//*[@id=\"gridFSLStorage\"]/div/div[6]/div/div[1]/div/table/tbody/tr[1]/td[3]"
			 * )) .click(); Driver.findElement( By.xpath(
			 * "//*[@id=\"gridFSLStorage\"]/div/div[6]/div/div[1]/div/table/tbody/tr[1]/td[3]/div/div/input"
			 * )) .clear(); Thread.sleep(5000); Driver.findElement(By.xpath(
			 * "//*[@id=\"gridFSLStorage\"]/div/div[6]/div/div[1]/div/table/tbody/tr[1]/td[3]"
			 * )) .click(); Driver.findElement( By.xpath(
			 * "//*[@id=\"gridFSLStorage\"]/div/div[6]/div/div[1]/div/table/tbody/tr[1]/td[3]/div/div/input"
			 * )) .sendKeys("21"); Thread.sleep(5000);
			 * Driver.findElement(By.id("idSaveFSlStorage")).click(); Thread.sleep(5000);
			 * 
			 * Driver.findElement(By.id("success")).getText(); Thread.sleep(5000);
			 */

			logger.info("FSL Storage Test=PASS");
			msg.append("FSL Storage Test=PASS" + "\n\n");
			setResultData("Result", 17, 5, "PASS");

		} catch (Exception FSLStorageE) {
			logger.error(FSLStorageE);
			getScreenshot(Driver, "FSLStorageE_error");
			logger.info("FSL Storage Test=FAIL");
			msg.append("FSL Storage Test=FAIL" + "\n\n");
			String Error = FSLStorageE.getMessage();

			setResultData("Result", 17, 5, "FAIL");
			setResultData("Result", 17, 6, Error);

		}
		logger.info("=======FSL Storage Test End=======");
		//msg.append("=======FSL Storage Test End=======" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}
}
