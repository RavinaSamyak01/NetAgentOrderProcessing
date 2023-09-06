package netAgent_OperationsTab;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class CycleCount extends BaseInit {
	@Test
	public void cycleCount() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		logger.info("=======CycleCount Test Start=======");
		// msg.append("=======CycleCount Test Start=======" + "\n\n");

		try {
			// Go to CycleCount screen
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idOperations")));
			Driver.findElement(By.id("idOperations")).click();
			logger.info("Click on Operations");

			wait.until(ExpectedConditions.elementToBeClickable(By.id("idCycle")));
			Driver.findElement(By.id("idCycle")).click();
			logger.info("Click on CycleCount");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			getScreenshot(Driver, "CycleCount");

			// Start Cycle

			// --if Action column is not empty for 1st row
			if (!Driver.findElements(By.xpath("//a[@class=\"dx-link\"]")).isEmpty()) {
				logger.info("If action column is not empty");
				getScreenshot(Driver, "CycleCountStart");

				// --Click on start button of first row
				Driver.findElement(By.xpath("//a[@class=\"dx-link\"]")).click();
				logger.info("Click on Start button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				try {
					// --Wait for workOrder ID
					WebElement WorkOID = Driver.findElement(By.id("workid"));
					wait.until(ExpectedConditions.visibilityOf(WorkOID));
					logger.info("WorkOrderID after start==" + WorkOID.getText());
					logger.info("Able to start cycle===PASS");
					msg.append("Able to start cycle===PASS" + "\n");

				} catch (Exception Part) {
					logger.error(Part);
					getScreenshot(Driver, "CycleCountWoIDnotVisible");
					try {
						WebElement PartError = Driver.findElement(By.id("CycleNoGrid"));
						if (PartError.isDisplayed()) {
							logger.info("Part validation is displayed==" + PartError.getText());

						} else {
							logger.info("Unable to start cycle");
							getScreenshot(Driver, "CycleCountStartError");

						}
						logger.info("Unable to start cycle===FAIL");
						msg.append("Unable to start cycle===FAIL" + "\n");

					} catch (Exception ee) {
						logger.error(ee);
						getScreenshot(Driver, "CycleCountStartIssue");
						logger.info("Unable to start cycle===FAIL");

					}

				}

			}

			// --if Reset button is exist
			if (!Driver.findElements(By.xpath("//a[@class='dx-link' and text()='Reset']")).isEmpty()) {
				logger.info("If Reset button is exist");
				getScreenshot(Driver, "CycleCountReset");
				// --Click on Reset button
				Driver.findElement(By.xpath("//a[@class='dx-link' and text()='Reset']")).click();
				logger.info("Click on Reset button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				// --Wait for workOrder ID
				WebElement WorkOID = Driver.findElement(By.id("woid"));
				wait.until(ExpectedConditions.visibilityOf(WorkOID));
				logger.info("WorkOrderID after Reset==" + WorkOID.getText());
			}

			// --Start both the cycle count
			if (!Driver.findElements(By.xpath("//a[@class=\"dx-link\"]")).isEmpty()) {
				logger.info("If action column is not empty");
				getScreenshot(Driver, "CycleCountStart");

				// --Click on start button of first row

				List<WebElement> multistart = Driver.findElements(By.xpath("//a[@class='dx-link' and text()='Start']"));
				logger.info("Total number of cycles==" + multistart.size());

				for (int cycle = 0; cycle < multistart.size(); cycle++) {
					try {
						multistart.get(cycle).click();
						logger.info("Click on Start button");
						wait.until(ExpectedConditions
								.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
						try {
							// --Wait for workOrder ID
							WebElement WorkOID = Driver.findElement(By.id("workid"));
							wait.until(ExpectedConditions.visibilityOf(WorkOID));
							String[] WOID = WorkOID.getText().split(":");
							String wOID = WOID[1].trim();
							logger.info("WorkOrderID after start==" + wOID);
							setData("OrderSearch", 1, 13, wOID);
							logger.info("Inserted workorderID in excel");
							logger.info("Able to start cycle===PASS");
							msg.append("Able to start cycle===PASS" + "\n");

						} catch (Exception Part) {
							logger.error(Part);
							WebElement PartError = Driver.findElement(By.id("CycleNoGrid"));
							if (PartError.isDisplayed()) {
								logger.info("Part validation is displayed==" + PartError.getText());

							} else {
								logger.info("Unable to start cycle");
								getScreenshot(Driver, "CycleCountStartError");

							}
							logger.info("Unable to start cycle===FAIL");
							msg.append("Unable to start cycle===FAIL" + "\n");

						}
					} catch (Exception staleelement) {
						logger.error(staleelement);
						List<WebElement> multistart1 = Driver
								.findElements(By.xpath("//a[@class='dx-link' and text()='Start']"));
						logger.info("Total number of cycles==" + multistart1.size());

						for (int cycle1 = cycle; cycle1 < multistart1.size(); cycle1++) {
							multistart1.get(cycle1).click();
							logger.info("Click on Start button");
							wait.until(ExpectedConditions
									.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
							try {
								// --Wait for workOrder ID
								WebElement WorkOID = Driver.findElement(By.id("workid"));
								wait.until(ExpectedConditions.visibilityOf(WorkOID));
								String[] WOID = WorkOID.getText().split(":");
								String wOID = WOID[1].trim();
								logger.info("WorkOrderID after start==" + wOID);
								setData("OrderSearch", 1, 13, wOID);
								logger.info("Inserted workorderID in excel");
								logger.info("Able to start cycle===PASS");
								msg.append("Able to start cycle===PASS" + "\n");

								break;

							} catch (Exception Part) {
								logger.error(Part);
								WebElement PartError = Driver.findElement(By.id("CycleNoGrid"));
								if (PartError.isDisplayed()) {
									logger.info("Part validation is displayed==" + PartError.getText());

								} else {
									logger.info("Unable to start cycle");
									getScreenshot(Driver, "CycleCountStartError");

								}
								logger.info("Unable to start cycle===FAIL");
								msg.append("Unable to start cycle===FAIL" + "\n");

							}

						}
					}
				}

			}

			logger.info("CycleCount Test=PASS");
			msg.append("CycleCount Test=PASS" + "\n\n");
			setResultData("Result", 7, 5, "PASS");

		} catch (Exception CycleCountE) {
			logger.error(CycleCountE);
			getScreenshot(Driver, "CycleCount_error");
			logger.info("CycleCount Test=FAIL");
			msg.append("CycleCount Test=FAIL" + "\n\n");
			String Error = CycleCountE.getMessage();

			setResultData("Result", 7, 5, "FAIL");
			setResultData("Result", 7, 6, Error);

			logger.info("=======CycleCount Test End=======");
			// msg.append("=======CycleCount Test End=======" + "\n\n");

		}
		// --Refresh the App
		narefreshApp();

	}
}
