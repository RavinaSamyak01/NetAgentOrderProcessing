package connect_OCBaseMethods;

import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class cancel_job extends OrderCreation {

	@Test
	public void job_cancel(int i) throws Exception {

		// WebDriverWait wait = new WebDriverWait(Driver, Duration.ofSeconds(60));//
		// wait time
		WebDriverWait wait = new WebDriverWait(Driver, 60);// wait time
		Actions act = new Actions(Driver);
		String Env = storage.getProperty("Env");
		logger.info("Start process for job cancel");
		msg.append("Start process for job cancel" + "\n");

		JavascriptExecutor js = (JavascriptExecutor) Driver;
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		Thread.sleep(1500);

		// --Search the Job
		OrderCreation OC = new OrderCreation();
		OC.searchJob(i);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		Thread.sleep(1500);
		// --Go to Edit Job tab
		WebElement EditOrTab = isElementPresent("EOEditOrderTab_id");
		act.moveToElement(EditOrTab).build().perform();
		wait.until(ExpectedConditions.elementToBeClickable(EditOrTab));
		act.moveToElement(EditOrTab).click().perform();
		logger.info("Click on Edit Order Tab");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		Thread.sleep(1500);

		// -- select Cancel Job : Cancel/NC
		WebElement cancel_nc_drp_down = isElementPresent("job_type_cancel_id");
		act.moveToElement(cancel_nc_drp_down).build().perform();
		Thread.sleep(1500);
		Select cancel_nc = new Select(isElementPresent("job_type_cancel_id"));
		cancel_nc.selectByVisibleText("CANCEL/NC");
		logger.info("Select job type : CANCEL/NC");
		Thread.sleep(2000);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		getScreenshot(Driver, "Select_canceljob_" + i);
		// --Click on Save Changes
		isElementPresent("TLSaveChanges_id").click();
		logger.info("Clicked on Save Changes button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Click yes for cancel cnfm

		Thread.sleep(2000);
		WebElement job_cance_cnfm = isElementPresent("BTNOk_id");
		job_cance_cnfm.click();
		logger.info("Clicked on ok for cancel job confirmation");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// -- enter cancel note

		WebElement cancel_note = isElementPresent("cancel_note_xpath");
		cancel_note.sendKeys("cancel_note_xpath");
		logger.info("Entering Cancellation description");
		getScreenshot(Driver, "canceljob_description_" + i);

		// -- Click on ok to final cancel job

		Thread.sleep(1500);
		WebElement job_cancel_cnfm = isElementPresent("BTNOk_id");
		job_cancel_cnfm.click();
		logger.info("Click on Ok for Final Job cancel Confirmation");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		Thread.sleep(2000);
		getScreenshot(Driver, "job_cancel_" + i);

		// -- additinal popup for change ready time after cancellation process
		try {

			WebElement cnfm_popup_redy_time = isElementPresent("redy_time_change_popup_xpath");
			if (cnfm_popup_redy_time.isDisplayed()) {

				WebElement redy_time_change_yes = isElementPresent("TLDPUOK_id");

				js.executeScript("arguments[0].click();", redy_time_change_yes);
				logger.info("Click on No button for change ready time popup");

				// - -Click on SAve changes button

				WebElement save_changes = isElementPresent("TLSaveChanges_id");
				js.executeScript("arguments[0].scrollIntoView(true);", save_changes);
				js.executeScript("arguments[0].click();", save_changes);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				Thread.sleep(1500);
				logger.info("Click on Save Changes button");

			}

		}

		catch (Exception e) {
			// TODO: handle exception
			logger.info("no addidtional popup up is display");
		}

		// -- validate job is cancel or not by reropen and verify it's title
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		Thread.sleep(1500);
		if(!(i==23)) {
		OC.searchJob(i);
		}
		else {
			searchRTEJob();
		}
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		Thread.sleep(1500);

		String Job_status = isElementPresent("TLStageLable_id").getText();

		WebElement cancel_nc_nav = isElementPresent("job_type_cancel_id");
		act.moveToElement(cancel_nc_nav).build().perform();

		Thread.sleep(1500);
		getScreenshot(Driver, "canceljob_type_" + i);

		if (Env.equalsIgnoreCase("PROD")) {

			if (Job_status.equalsIgnoreCase("CANCEL")) {
				logger.info("job is cancelled");
				logger.info("job cancel process == PASS");
				setData("OrderCreation", i, 40, Job_status + " -PASS");
				setData("OrderCreation", i, 40, "PASS");
				getStageName();
				logger.info("Job Cancellation Process == pass");
				msg.append("Job Cancellation Process == PASS" + "\n");
			}

			// -- re-attempt to cancel job
			else {

				Select cancel_ncs = new Select(isElementPresent("job_type_cancel_id"));
				cancel_ncs.selectByVisibleText("CANCEL/NC");
				logger.info("Select job type : CANCEL/NC");
				Thread.sleep(1500);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				getScreenshot(Driver, "Select_canceljob_" + i);
				// --Click on Save Changes
				isElementPresent("TLSaveChanges_id").click();
				logger.info("Clicked on Save Changes button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --Click yes for cancel cnfm

				Thread.sleep(1500);
				WebElement job_cance_cnfm1 = isElementPresent("BTNOk_id");
				job_cance_cnfm1.click();
				logger.info("Clicked on ok for cancel job confirmation");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// -- enter cancel note

				WebElement cancel_note1 = isElementPresent("cancel_note_xpath");
				cancel_note1.sendKeys("cancel_note_xpath");
				logger.info("Entering Cancellation description");
				getScreenshot(Driver, "canceljob_description_" + i);

				// -- Click on ok to final cancel job

				Thread.sleep(1500);
				WebElement job_cancel_cnfm1 = isElementPresent("BTNOk_id");
				job_cancel_cnfm1.click();
				logger.info("Click on Ok for Final Job cancel Confirmation");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// -- additinal popup for change ready time after cancellation process
				try {

					WebElement cnfm_popup_redy_time = isElementPresent("redy_time_change_popup_xpath");
					if (cnfm_popup_redy_time.isDisplayed()) {

						WebElement redy_time_change_yes = isElementPresent("TLDPUOK_id");

						js.executeScript("arguments[0].click();", redy_time_change_yes);
						logger.info("Click on No button for change ready time popup");

						// - -Click on SAve changes button

						WebElement save_changes = isElementPresent("TLSaveChanges_id");
						js.executeScript("arguments[0].scrollIntoView(true);", save_changes);
						js.executeScript("arguments[0].click();", save_changes);
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						Thread.sleep(1500);
						logger.info("Click on Save Changes button");

					}

				}

				catch (Exception e) {
					// TODO: handle exception
					logger.info("no addidtional popup up is display");
				}

				open_pickup_frm_tasklog(i);
				// -- validate job is cancel or not by reropen and verify it's title

				String Job_statuses = getStageName();

				WebElement cancel_nc1 = isElementPresent("job_type_cancel_id");
				act.moveToElement(cancel_nc1).build().perform();

				Thread.sleep(1500);
				getScreenshot(Driver, "canceljob_type_" + i);

				if (Job_statuses.equalsIgnoreCase("CANCEL")) {
					logger.info("job is cancelled");
					setData("OrderCreation", i, 40, Job_status + " -PASS");
					setData("OrderCreation", i, 40, "PASS");
				}

				else {

					logger.info("job is not cancelled == Fail");
					setData("OrderCreation", i, 40, Job_status + " - FAIL");
					setData("OrderCreation", i, 40, "FAIL");
					msg.append("Job cancel == FAIL" + "\n");
				}

			}
		}

		else if (Env.equalsIgnoreCase("STG") || Env.equalsIgnoreCase("TEST")) {

			logger.info("Do not process for job cancelaltion");

		}

	}

	public void open_pickup_frm_tasklog(int i)
			throws InterruptedException, EncryptedDocumentException, InvalidFormatException, IOException {
		WebDriverWait wait = new WebDriverWait(Driver, 40);// wait time
		Actions act = new Actions(Driver);
		WebDriverWait wait2 = new WebDriverWait(Driver, 60);// wait time
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click

		try {
			// Go To Operations
			WebElement operations = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_operations")));
			act.moveToElement(operations).click().perform();
			wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// Go to TaskLog
			WebElement taskLog = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_TaskLog")));
			taskLog.click();
			wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// Enter pickUpID
			String pickupidSeg = getData("Test_Case", i, 6);
			logger.info("Pickup ID use for verification is : " + pickupidSeg);
			wait.until(ExpectedConditions.elementToBeClickable(By.id("txtContains")));
			WebElement tlSearch = isElementPresent("TLSearch_id");
			tlSearch.clear();
			tlSearch.sendKeys(pickupidSeg);
			logger.info("Pickup id entered in search box");

			// Click on search button for pickup search
			WebElement searchButton = isElementPresent("TLSearchButton_id");
			searchButton.click();
			wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			List<WebElement> totalResult = Driver.findElements(By.xpath("//span[@class='dx-checkbox-icon']"));
			int size = totalResult.size();

			if (size > 1) {
				WebElement pickupIdTask = Driver
						.findElement(By.xpath("//label[contains(text(),'" + pickupidSeg + "')]"));
				act.moveToElement(pickupIdTask).click().perform();
				logger.info("Appropriate Job selected from multiple jobs");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			} else {
				logger.info("Multiple jobs not visible for the entered pickup id in taskbar");
			}
		} catch (Exception e) {
			logger.info("Error in opening tasklog: " + e);
		}
	}

}