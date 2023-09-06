package netAgent_Inventory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class Parts extends BaseInit {
	@Test
	public void parts() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);

		logger.info("=======Parts Test Start=======");
		msg.append("=======Inventory Tab Test Start=======" + "\n\n");

		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Inventory")));
			Driver.findElement(By.partialLinkText("Inventory")).click();
			logger.info("Clicked on Inventory");

			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Parts")));
			Driver.findElement(By.linkText("Parts")).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			logger.info("Parts Screen: Parts screen opened.");
			System.out.println(Driver.getTitle());

			String Env = storage.getProperty("Env");
			System.out.println("Env " + Env);
			logger.info("Env for execute script is : " + Env);
			
			String stg_profile = "AUTOMATION INVENTORY PROFILE";
			String prod_profile = "TEST QA ACCOUNT #103272";
			
			String profile = null;
			if (Env.equalsIgnoreCase("STG")) {
				profile = stg_profile;
			} else if (Env.equalsIgnoreCase("PROD")) {
				profile = prod_profile;
			}else if (Env.equalsIgnoreCase("TEST")) {
				profile = stg_profile;
			}

			// parts
						WebElement clientprt = Driver.findElement(By.id("ddlClient"));
						Select optprt = new Select(clientprt);
						optprt.selectByVisibleText(profile);
						logger.info("Parts Screen: Client selected");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));


			// -Search button
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idsearchbutton")));
			Driver.findElement(By.id("idsearchbutton")).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			logger.info("Parts Screen: Click on search button.");

			// Select Include Zero Qty
			Driver.findElement(By.id("IncludeZeroQty")).click();
			logger.info("Parts Screen: Tick on Include zero qty checkbox.");
			// --Search
			Driver.findElement(By.id("idsearchbutton")).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			logger.info("Parts Screen: Click on search button.");

			// DeSelect Include Zero Qty
			WebElement element1 = Driver.findElement(By.id("IncludeZeroQty"));
			Actions actions1 = new Actions(Driver);
			actions1.moveToElement(element1).click().build().perform();
			logger.info("Parts Screen: UnTick on Include zero qty checkbox.");
			// -search
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idsearchbutton")));
			Driver.findElement(By.id("idsearchbutton")).click();
			logger.info("Parts Screen: Click on search button.");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

			getScreenshot(Driver, "Part");

			// partDetail

			// --Get data from excel
			String ModelNumber = getData("Sheet1", 2, 22);
			String EXPIRATIONDATE = getData("Sheet1", 2, 23);
			String LotBatchNo = getData("Sheet1", 2, 24);
			String MFGSerialNo = getData("Sheet1", 2, 26);

			// Click on Part# and Return

			Driver.findElement(By.id("txtField1")).sendKeys(ModelNumber);
			logger.info("Entered value in MODEL NUMBER");
			Driver.findElement(By.id("idsearchbutton")).click();
			logger.info("Clicked on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			Thread.sleep(2000);

			try {

				Driver.findElement(By.xpath("//*[@id='PartMasterGD']//tbody//a")).click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
				Thread.sleep(2000);

				logger.info("Parts Screen: Click on part and go to part details screen.");
				System.out.println(Driver.getTitle());

				getScreenshot(Driver, "Part_PartEditor");

				Driver.findElement(By.id("idreturntoitem")).click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
				logger.info("Parts Screen: Return from part details screen.");
				Thread.sleep(2000);

				System.out.println("Expected MODEL NUMBER is=" + ModelNumber);
				logger.info("Expected MODEL NUMBER is=" + ModelNumber);
				String act1 = Driver.findElement(By.xpath("//*[@id='PartMasterGD']//tbody//a")).getText();
				System.out.println("Actual MODEL NUMBER is=" + act1);
				logger.info("Actual MODEL NUMBER is=" + act1);

				if (act1.contains(ModelNumber)) {
					System.out.println("Field1 Search Compare is - PASS");
					logger.info("Field1 Search Compare is - PASS");
				}

				else {
					System.out.println("Field1 Search Compare is - FAIL");
					logger.info("Field1 Search Compare is - FAIL");
				}
			} catch (Exception Nodata) {
				logger.error(Nodata);
				System.out.println("There is no parts with search parameters");

			}

			// --Search with second field
			Driver.findElement(By.id("txtField1")).clear();
			logger.info("Cleared MODEL NUMBER");
			Driver.findElement(By.id("txtField2")).sendKeys(EXPIRATIONDATE);
			logger.info("Entered value in EXPIRATION DATE");
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idsearchbutton")));
			Driver.findElement(By.id("idsearchbutton")).click();
			logger.info("Clicked on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			Driver.findElement(By.id("txtField2")).clear();
			logger.info("Cleared EXPIRATION DATE");

			// --Search with third field
			Driver.findElement(By.id("txtField3")).sendKeys(LotBatchNo);
			logger.info("Entered LOT/BATCH NUMBER");
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idsearchbutton")));
			Driver.findElement(By.id("idsearchbutton")).click();
			logger.info("Clicked on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

			// -Search with Fourth field
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtField3")));
			Driver.findElement(By.id("txtField3")).clear();
			logger.info("LOT/BATCH NUMBER");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtSrAliasName")));
			Driver.findElement(By.id("txtSrAliasName")).sendKeys(MFGSerialNo);
			logger.info("Entered MFG Serial No");
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idsearchbutton")));
			Driver.findElement(By.id("idsearchbutton")).click();
			logger.info("Clicked on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

			// --Invalid text
			Driver.findElement(By.id("txtSrAliasName")).clear();
			logger.info("Cleared MFG Serial No");

			// Search with invalid text
			Driver.findElement(By.id("txtField1")).sendKeys("Test123");
			logger.info("Entered invalid value in MODEL NUMBER");
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idsearchbutton")));
			Driver.findElement(By.id("idsearchbutton")).click();
			logger.info("Clicked on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			Driver.findElement(By.id("PartNoGrid")).getText();
			logger.info("Records==" + Driver.findElement(By.id("PartNoGrid")).getText());
			Driver.findElement(By.id("txtField1")).clear();
			logger.info("Cleared MODEL NUMBER");

			Driver.findElement(By.id("txtField2")).sendKeys("Test123");
			logger.info("Entered invalid value in EXPIRATION DATE");
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idsearchbutton")));
			Driver.findElement(By.id("idsearchbutton")).click();
			logger.info("Clicked on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			Driver.findElement(By.id("PartNoGrid")).getText();
			logger.info("Records==" + Driver.findElement(By.id("PartNoGrid")).getText());
			Driver.findElement(By.id("txtField2")).clear();
			logger.info("Cleared EXPIRATION DATE");

			Driver.findElement(By.id("txtField3")).sendKeys("Test123");
			logger.info("Entered invalid value in LOT/BATCH NUMBER");
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idsearchbutton")));
			Driver.findElement(By.id("idsearchbutton")).click();
			logger.info("Clicked on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			Driver.findElement(By.id("PartNoGrid")).getText();
			logger.info("Records==" + Driver.findElement(By.id("PartNoGrid")).getText());
			Driver.findElement(By.id("txtField3")).clear();
			logger.info("Cleared LOT/BATCH NUMBER");

			Driver.findElement(By.id("txtSrAliasName")).sendKeys("Test123");
			logger.info("Entered invalid value in MFG Serial No");
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idsearchbutton")));
			Driver.findElement(By.id("idsearchbutton")).click();
			logger.info("Clicked on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			Driver.findElement(By.id("PartNoGrid")).getText();
			logger.info("Records==" + Driver.findElement(By.id("PartNoGrid")).getText());
			Driver.findElement(By.id("txtSrAliasName")).clear();
			logger.info("Cleared MFG Serial No");

			// Select Bin and Search
			Driver.findElement(By.id("btn_cmbLocationBinclass=")).click();
			Thread.sleep(2000);
			logger.info("Parts Screen: User has clicked on Bin dropdown box.");

			Driver.findElement(By.xpath("//label[contains(.,'All')]")).click();
			Thread.sleep(2000);
			logger.info("Parts Screen: User has tick All checkbox.");

			Driver.findElement(By.id("btn_cmbLocationBinclass=")).click();
			logger.info("Close bin dropdown");
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idsearchbutton")));
			Driver.findElement(By.id("idsearchbutton")).click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			logger.info("Parts Screen: User has click on search button.");

			Driver.findElement(By.id("idResetbutton")).click();
			logger.info("Parts Screen: User has click Reset button.");

			WebElement clientstck = Driver.findElement(By.id("ddlClient"));
			Select optstck = new Select(clientstck);
			optstck.selectByIndex(1);
			logger.info("Selected Client");
			Thread.sleep(2000);

			// WebElement fslstck = Driver.findElement(By.id("ddlfsl"));
			// Select opt1stck = new Select(fslstck);
			// opt1stck.selectByVisibleText(FSLName);
			// Thread.sleep(10000);

			// Stock Details

			// Click on Stock
			Driver.findElement(By.id("txtField1")).clear();
			logger.info("Cleared Sprint Part No");
			Driver.findElement(By.id("txtField1")).sendKeys(ModelNumber);
			logger.info("Entered Sprint Part No");
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idsearchbutton")));
			Driver.findElement(By.id("idsearchbutton")).click();
			logger.info("Clicked on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

			try {
				Thread.sleep(2000);
				String stock_partDetails = Driver.findElement(By.xpath(".//*[@id=\"PartMasterGD\"]//tr[1]/td[4]/a"))
						.getText();
				String[] list = stock_partDetails.split(" ");
				String a = list[1].replaceAll("[^0-9]", "");
				String stock_partDetails1 = a;
				System.out.println("First : " + stock_partDetails1);
				logger.info("First : " + stock_partDetails1);

				// System.out.println("First :: "+stock_partDetails);

				Driver.findElement(By.xpath(".//*[@id=\"PartMasterGD\"]//tr[1]/td[4]/a")).click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

				String stock_Details = Driver.findElement(By.xpath("//div[@style=\"text-align:right\"]/strong"))
						.getText();
				System.out.println("Second : " + stock_Details);
				logger.info("Second : " + stock_Details);

				if (stock_partDetails1.equals(stock_Details)) {
					System.out.println("Total Records Matched");
					logger.info("Total Records Matched");
				} else {
					System.out.println("Total Records Not Matched");
					logger.info("Total Records Not Matched");
				}

				logger.info("Parts Screen: Go to stock details screen.");
				System.out.println(Driver.getTitle());

				getScreenshot(Driver, "Part_StockDetails");

				// --Click on PrintLabel button
				Driver.findElement(By.linkText("Print Label")).click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
				logger.info("Parts Screen: User has performed print label.");

				String winHandleBefore = Driver.getWindowHandle();
				for (String winHandle : Driver.getWindowHandles()) {
					Driver.switchTo().window(winHandle);
					logger.info("Switched to Print Label window");
				}
				Thread.sleep(2000);

				Driver.close();
				logger.info("Closed Print Label window");
				Driver.switchTo().window(winHandleBefore);
				logger.info("Switched to main window");
				Thread.sleep(2000);

				// --Return to Item
				Driver.findElement(By.id("idreturntoitem")).click();
				logger.info("Clicked on Return To Item");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

				Driver.findElement(By.id("btnPrint")).click();
				logger.info("Clicked Print button");
				Thread.sleep(10000);
				boolean plabel = Driver.findElement(By.id("PartNoGrid")).getText()
						.contains("Please select atleast one record.");

				if (plabel == true) {
					Driver.findElement(By.xpath("//td[@role=\"gridcell\"]//span[@class=\"dx-checkbox-icon\"]")).click();

					Driver.findElement(By.id("btnPrint")).click();
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

					String winHandleBefore1 = Driver.getWindowHandle();
					for (String winHandle1 : Driver.getWindowHandles()) {
						Driver.switchTo().window(winHandle1);
						logger.info("Switched to Print Label window");
					}

					getScreenshot(Driver, "PartsPrintLabel");
					Driver.close();
					logger.info("Closed Print Label window");
					Driver.switchTo().window(winHandleBefore1);
					logger.info("Switched to main window");
					Thread.sleep(2000);

				}
				getScreenshot(Driver, "PartswithSelection");
			} catch (Exception NoData) {
				logger.error(NoData);
				logger.info("There is no part with search parameter");

			}
			Driver.findElement(By.id("idResetbutton")).click();
			logger.info("Parts Screen: User has click reset button.");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

			logger.info("Parts Test=PASS");
			msg.append("Parts Test=PASS" + "\n\n");
			setResultData("Result", 16, 5, "PASS");

		} catch (Exception PartsE) {
			logger.error(PartsE);
			getScreenshot(Driver, "Parts_error");
			logger.info("Parts Test=FAIL");
			msg.append("Parts Test=FAIL" + "\n\n");
			String Error = PartsE.getMessage();

			setResultData("Result", 16, 5, "FAIL");
			setResultData("Result", 16, 6, Error);

		}

		logger.info("=======Parts Test End=======");
		//msg.append("=======Parts Test End=======" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}
}
