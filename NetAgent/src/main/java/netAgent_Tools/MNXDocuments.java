package netAgent_Tools;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class MNXDocuments extends BaseInit {
	@Test
	public void MNXDoc() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);

		logger.info("=======MNX Documents Test Start=======");
		// msg.append("=======MNX Documents Test Start=======" + "\n\n");
		String mainWindowHandle = Driver.getWindowHandle();
		

		try {
			// Go to Tools - NGL Doc screen
			wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Tools")));
			Driver.findElement(By.partialLinkText("Tools")).click();
			logger.info("Click on Tools");

			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("MNX Documents")));
			Driver.findElement(By.linkText("MNX Documents")).click();
			logger.info("Click on MNX Documents");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

			getScreenshot(Driver, "MNXDocuments");

			JavascriptExecutor js = (JavascriptExecutor) Driver;
			js.executeScript("window.scrollBy(0,-250)");
			Thread.sleep(2000);

//				//Click on Automation  Doc
//			String winHandleBefore1 = Driver.getWindowHandle();

//				//CLick on doc link
			try {
//				wait.until(ExpectedConditions.visibilityOfElementLocated(
//						By.xpath("//*[@ng-click=\"NglDocData(doc)\"][contains(text(),'TOPIC1 DOC (09/05/2018)')]")));
//				wait.until(ExpectedConditions.elementToBeClickable(
//						By.xpath("//*[@ng-click=\"NglDocData(doc)\"][contains(text(),'TOPIC1 DOC (09/05/2018) ')]")));

				List<WebElement> Services = Driver.findElements(By.xpath("//*[@ng-click='NglDocData(doc)']"));
				int Size = Services.size();
				logger.info("Total no of Documents is/are : " + Size);

				for (int i = 1; i <= Size; i++) {
					String doc_locator_1 = "(//*[@ng-click='NglDocData(doc)'])[";

					String doc_locator_2 = i + "]";

					String final_doc_locator = doc_locator_1 + doc_locator_2;

					WebElement doc = Driver.findElement(By.xpath(final_doc_locator));
					doc.click();
					logger.info("Click on " + i + " document sequence");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
					Thread.sleep(2000);
					for (String windHandle : Driver.getWindowHandles()) {
						System.out.println("Parent Window Id is: " + mainWindowHandle);	
						 System.out.println("Child Window Id is: " + windHandle);
					    if (!windHandle.equals(mainWindowHandle)) { // Check if the handle is not the main window handle
					        Driver.switchTo().window(windHandle);
					        	        					       
					        logger.info("Switched to child window");
					      //  getScreenshot(Driver, "MNXDoc_ChildWindow");
					        Thread.sleep(2000);
					        Driver.close();
					        logger.info("Closed child window");
					    }
					}

					Driver.switchTo().window(mainWindowHandle); // Switch back to the main window
					logger.info("Switched to main window");
					Thread.sleep(2000);
					// getScreenshot(driver, "Print_shilabel_" + service);
					

				}

//				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
//
//				// Switch back to original browser (first window)
//				Driver.switchTo().window(winHandleBefore1);
//				logger.info("Switched to Main window");
//				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			} catch (Exception nodoc) {
				logger.error(nodoc);
				logger.info("There is no doc with 'Automation' text");

			}

			logger.info("MNX Documents Test=PASS");
			msg.append("MNX Documents Test=PASS" + "\n\n");
			setResultData("Result", 35, 5, "PASS");

		} catch (Exception MNXDocumentsE) {
			logger.error(MNXDocumentsE);
			getScreenshot(Driver, "MNXDocuments_error");
			logger.info("MNX Documents Test=FAIL");
			msg.append("MNX Documents Test=FAIL" + "\n\n");
			String Error = MNXDocumentsE.getMessage();

			setResultData("Result", 35, 5, "FAIL");
			setResultData("Result", 35, 6, Error);

		}
		logger.info("=======MNX Documents Test End=======");
		// msg.append("=======MNX Documents Test End=======" + "\n\n");
		msg.append("=======Tools Tab Test End=======" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}

}
