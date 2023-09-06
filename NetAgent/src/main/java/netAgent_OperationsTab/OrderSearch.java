package netAgent_OperationsTab;

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

import com.google.common.base.Throwables;

import netAgent_BasePackage.BaseInit;

public class OrderSearch extends BaseInit {

	@Test
	public void orderSearch() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 40);
		JavascriptExecutor js = (JavascriptExecutor) Driver;
		Actions act = new Actions(Driver);
		logger.info("=======Order Search Test Start=======");
		// msg.append("=======Order Search Test Start=======" + "\n\n");

		try {
			// --GetData Method for getting Data from excel
			String PickUpID = getData("OrderSearch", 1, 0);
			String JobID = getData("OrderSearch", 1, 1);
			String SPLPickUpID = getData("OrderSearch", 1, 2);
			// String SPLJobID = formatter.formatCellValue(sh0.getRow(1).getCell(3));

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idOperations")));
				Driver.findElement(By.id("idOperations")).click();
				logger.info("Click on Operations");
			} catch (Exception e) {
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id=\"idOperations\"]")));
				WebElement OperationMenu = Driver.findElement(By.xpath("//a[@id=\"idOperations\"]"));
				act.moveToElement(OperationMenu).build().perform();
				js.executeScript("arguments[0].click();", OperationMenu);

			}

			wait.until(ExpectedConditions.elementToBeClickable(By.id("idOrder")));
			Driver.findElement(By.id("idOrder")).click();
			logger.info("Click on Order Search");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("ordersearch")));
			Thread.sleep(2000);
			wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearch")));
			Driver.findElement(By.id("btnSearch")).click();
			logger.info("Click on Search");

			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("idValidation")));
			String VMessages = Driver.findElement(By.id("idValidation")).getText();
			System.out.println("Validation message:-" + VMessages);
			logger.info("Validation Message is Displayed==" + VMessages);

			Driver.findElement(By.id("btnReset")).click();
			logger.info("Click on Reset");

			wait.until(ExpectedConditions.elementToBeClickable(By.id("txtPickup")));
			Driver.findElement(By.id("txtPickup")).clear();
			logger.info("Cleared Pickup");
			Driver.findElement(By.id("txtPickup")).sendKeys(PickUpID);
			logger.info("Entered Pickupid in Pickup");

			Driver.findElement(By.id("btnSearch")).click();
			logger.info("Click on Search");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			// --Searching SPL Order
			Driver.findElement(By.id("txtPickup")).clear();
			logger.info("Cleared PickUp");
			Driver.findElement(By.id("txtPickup")).sendKeys(SPLPickUpID);
			logger.info("Entered Pickupid of SPL Job");

			Driver.findElement(By.id("btnSearch")).click();
			logger.info("Click on Search");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			// --Handling new window- new window ID is not unique so we can not handle it

			try {
				// --Click on Print button
				Driver.findElement(By.linkText("Print")).click();
				logger.info("Click on Print");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				// --Transfer to new window
				String winHandleBefore = Driver.getWindowHandle();
				System.out.println(winHandleBefore);

				for (String winHandle : Driver.getWindowHandles()) {
					Driver.switchTo().window(winHandle);
					getScreenshot(Driver, "OrderSearch_PrintLabel");

					Thread.sleep(2000);
					logger.info("Moved to Print window");
					System.out.println(winHandle);
					

				}

				Ashotscreenshot("PrintLabelOrderSearch");

				Driver.close();
				logger.info("Close the Print window");

				// Switch back to original browser (first window)
				Driver.switchTo().window(winHandleBefore);
				Thread.sleep(2000);

				Driver.findElement(By.id("txtPickup")).clear();
				logger.info("Cleared PickUp");

				// --OrderRangeFrom
				Driver.findElement(By.id("orderRangeFrom")).sendKeys("01/01/2022");
				logger.info("Entered OrderRangeFrom");

				// --OrderRangeFrom
				WebElement RangeTo = Driver.findElement(By.id("orderRangeTo"));
				act.moveToElement(RangeTo).build().perform();
				RangeTo.clear();
				Date date = new Date();
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				logger.info(dateFormat.format(date));
				RangeTo.sendKeys(dateFormat.format(date));
				RangeTo.sendKeys(Keys.TAB);
				logger.info("Entered OrderRangeTo");

				Driver.findElement(By.id("btnSearch")).click();
				logger.info("Click on Search");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

				String pageCount = Driver.findElement(By.xpath("//*[@class=\"dx-info\"]")).getText();
				System.out.println("No of Record found=" + pageCount);
				logger.info("No of Record found==" + pageCount);

				// --Searching SPL Order
				Driver.findElement(By.id("txtPickup")).clear();
				logger.info("Cleared PickUp");
				Driver.findElement(By.id("txtPickup")).sendKeys(SPLPickUpID);
				logger.info("Entered Pickupid of SPL Job");

				Driver.findElement(By.id("btnSearch")).click();
				logger.info("Click on Search");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
				pageCount = Driver.findElement(By.xpath("//*[@class=\"dx-info\"]")).getText();
				System.out.println("No of Record found=" + pageCount);
				logger.info("No of Record found==" + pageCount);
				Thread.sleep(2000);

				if (pageCount.contains("Page 1 of 1 (0 items)")) {
					System.out.println(
							"Status : Order Search is not Working after Search with Date Range.(May be there is no Order.)");
					logger.info(
							"Status : Order Search is not Working after Search with Date Range.(May be there is no Order.)");

				} else {
					System.out.println("Status : Order Search is Working after Search with Date Range.");
					logger.info("Status : Order Search is Working after Search with Date Range.");

				}

				// --Upload
				TaskLog tlUplaod = new TaskLog();
				tlUplaod.tlUpload();

			} catch (Exception e) {
				logger.error(e);
				System.out.println("SPL order is not available");
				logger.info("SPL order is not available");
			}

			// --Reset button
			Driver.findElement(By.id("btnReset")).click();
			logger.info("Click on Reset button");

			// Search with job
			Driver.findElement(By.id("txtJob")).clear();
			logger.info("Cleared Job");
			Driver.findElement(By.id("txtJob")).sendKeys(JobID);
			logger.info("Entered JobID");

			Driver.findElement(By.id("btnSearch")).click();
			logger.info("Click on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			Driver.findElement(By.id("btnReset")).click();
			logger.info("Click on Reset button");

			// Search with date range
			// Enter from date
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date frmdt = new Date();
			System.out.println(frmdt);
			Date frmdt1 = addDays(frmdt, -20);
			System.out.println(frmdt1);
			String FromDate = dateFormat.format(frmdt1);
			System.out.println(FromDate);

			Driver.findElement(By.id("orderRangeFrom")).clear();
			logger.info("Clear OrderRangeFrom");
			Driver.findElement(By.id("orderRangeFrom")).sendKeys(FromDate);
			logger.info("Select date from OrderRangeFrom");

			// Enter to date
			Date todt = new Date();
			String ToDate = dateFormat.format(todt);

			Driver.findElement(By.id("orderRangeTo")).clear();
			logger.info("Cleared OrderRangeTo");

			Driver.findElement(By.id("orderRangeTo")).sendKeys(ToDate);
			logger.info("Select date from OrderRangeTo");

			Driver.findElement(By.id("btnSearch")).click();
			logger.info("Click on Search button");

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dx-info")));
			String pagecntOS = Driver.findElement(By.className("dx-info")).getText();
			System.out.println(pagecntOS);
			logger.info("Page Count==" + pagecntOS);

			if (pagecntOS.contains("Page 1 of 1 (0 items)")) {
				System.out.println(
						"Status : Order Search is not Working after Search with Date Range.(May be there is no Order.)");
			} else {
				System.out.println("Status : Order Search is Working after Search with Date Range.");
				// Click on column title for sorting
				// --storing all the columns of the table
				List<WebElement> Columns = Driver.findElements(By.xpath("//td[@role=\"columnheader\"]"));
				System.out.println("total No. of columns of the table is=" + Columns.size());
				logger.info("total No. of columns of the table is=" + Columns.size());

				// --Clicking on all the columns one by one for sorting
				for (int col = 0; col < Columns.size() - 4; col++) {
					WebElement Cols = Columns.get(col);
					String ColName = Columns.get(col).getAttribute("aria-label");
					System.out.println("column name is=" + ColName);
					logger.info("column name is=" + ColName);

					// --Check the sorting value before sorting applied
					String ColSortBefore = Columns.get(col).getAttribute("aria-sort");
					System.out.println("Sorting for " + ColName + " is==" + ColSortBefore);
					logger.info("Sorting for " + ColName + " is==" + ColSortBefore);

					if (ColName.equalsIgnoreCase("FROM Column")) {
						logger.info("Not applying sorting on 'From' column, It is not displayed in 1032x776 window");

					} else if (ColName.equalsIgnoreCase("TO Column")) {
						logger.info("Not applying sorting on 'TO' column, It is not displayed in 1032x776 window");

					} else {
						Cols = Columns.get(col);
						wait.until(ExpectedConditions.elementToBeClickable(Cols));
						Cols.click();
						System.out.println("Clicked on column for sorting");
						logger.info("Clicked on column for sorting");

						// --Check the sorting value after sorting applied
						String ColSortAsc = Columns.get(col).getAttribute("aria-sort");
						System.out.println("after Sorting value of sort for " + ColName + " is==" + ColSortAsc);
						logger.info("after Sorting value of sort for " + ColName + " is==" + ColSortAsc);

						// --Checking sorting is applied or not
						if (ColSortAsc.equals(ColSortBefore)) {
							System.out.println("Sorting is not applied");
							logger.info("Sorting is not applied");

						} else {
							System.out.println("Sorting is applied and sorting is applied on " + ColSortAsc + " Order");
							logger.info("Sorting is applied and sorting is applied on " + ColSortAsc + " Order");

						}

						// --Clicking on column
						Columns.get(col).click();
						System.out.println("Clicked on column for sorting");
						logger.info("Clicked on column for sorting");

						// --Check the sorting value after sorting applied
						String ColSortDesc = Columns.get(col).getAttribute("aria-sort");
						System.out.println("after Sorting value of sort for " + ColName + " is==" + ColSortDesc);
						logger.info("after Sorting value of sort for " + ColName + " is==" + ColSortDesc);

						// --Checking sorting is applied on desc order or not
						if (ColSortDesc.equals("descending")) {
							System.out.println("Sorting is applied and sorting is applied on " + ColSortAsc + " Order");
							logger.info("Sorting is applied and sorting is applied on " + ColSortAsc + " Order");

						} else {
							System.out.println("Sorting is not applied");
							logger.info("Sorting is not applied");

						}
					}

				}

			}

			// --Clicking on reset button
			Driver.findElement(By.id("btnReset")).click();
			logger.info("Click on Reset button");

			logger.info("Order Search Test=PASS");
			msg.append("Order Search Test=PASS" + "\n\n");
			setResultData("Result", 5, 5, "PASS");

		} catch (Exception OrderSearchE) {
			logger.error(OrderSearchE);
			getScreenshot(Driver, "OrderSearch_error");
			logger.info("Order Search Test=FAIL");
			msg.append("Order Search Test=FAIL" + "\n\n");
			// String Error = Throwables.getStackTraceAsString(OrderSearchE);
			// String err = OrderSearchE.getCause().toString();
			// System.out.println(err);
			String Error = OrderSearchE.getMessage();

			setResultData("Result", 5, 5, "FAIL");
			setResultData("Result", 5, 6, Error);

		}
		logger.info("=======Order Search Test End=======");
		// msg.append("=======Order Search Test End=======" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}
}
