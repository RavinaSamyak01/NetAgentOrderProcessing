package netAgent_LogOutDiv;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class AgentTSATraining extends BaseInit {
	@Test
	public void AgentTSA() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 40);
		// Go to Welcome - AgentTSA Training screen
		logger.info("=======Agent TSA Training Test Start=======");
		msg.append("=======LogOut Div Test Start=======" + "\n\n");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
		Thread.sleep(3000);
		try {
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'userthumb')]")));
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'userthumb')]")));
				isElementPresent("LogOutDiv_xpath").click();
				logger.info("Clicked on LogOutDiv");
				isElementPresent("AgentTSA_linkText").click();
				logger.info("Clicked on Agent TSA Training");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

			} catch (Exception e) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'userthumb')]")));
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'userthumb')]")));
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
				isElementPresent("LogOutDiv_xpath").click();
				logger.info("Clicked on LogOutDiv");
				isElementPresent("AgentTSA_linkText").click();
				logger.info("Clicked on Agent TSA Training");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'userthumb')]")));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			// AgentTSA screen.
			System.out.println(Driver.getTitle());
			logger.info("Title of the screen is==" + Driver.getTitle());

			// Take screen-shot.
			getScreenshot(Driver, "AgentTSA");

			String strParentWindowHandle = Driver.getWindowHandle();

			// CLick on link
			isElementPresent("AnnualTSA_linkText").click();
			logger.info("Clicked on Annual TSA Training Presentation(Authorized Representative Version) Link");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			Thread.sleep(5000);

			for (String winHandle : Driver.getWindowHandles()) {
				Driver.switchTo().window(winHandle);
				/*
				 * try { WebElement PwdDialogue = isElementPresent("PwdDialogue_id");
				 * wait.until(ExpectedConditions.visibilityOfAllElements(PwdDialogue));
				 * getScreenshot(Driver, "AnnualTSATraPresentation");
				 * logger.info("Dialogue is exist for the Password");
				 * msg.append("Dialogue is exist for the Password" + "\n\n"); String title =
				 * Driver.findElement(By.xpath("slot=\"title\"")).getText();
				 * logger.info("Title of the Dialogue==" + title);
				 * msg.append("Title of the Dialogue==" + title + "\n\n"); String Message =
				 * Driver.findElement(By.id("message")).getText();
				 * logger.info("Message of the Dialogue==" + Message);
				 * msg.append("Message of the Dialogue==" + Message + "\n\n");
				 * 
				 * // --Enter Incorrect password
				 * isElementPresent("PwdInput_id").sendKeys("dfg");
				 * logger.info("Entered password"); // --Click Submit
				 * isElementPresent("DialogueSubmit_id").click();
				 * logger.info("Clicked on Submit button"); try { WebElement Error =
				 * isElementPresent("DialError_id"); if (Error.isDisplayed()) {
				 * logger.info("Error message is displayed==" + Error.getText());
				 * msg.append("Password validation for Agent TSA Document=PASS" + "\n\n"); } }
				 * catch (Exception Error) {
				 * logger.info("Error message is not displayed even password is incorrect");
				 * msg.append("Password validation for Agent TSA Document=FAIL" + "\n\n");
				 * 
				 * }
				 * 
				 * } catch (Exception Dialogue) { logger.error(Dialogue); getScreenshot(Driver,
				 * "AnnualTSATraPresentation");
				 * System.out.println("There is no Dialogue for Password");
				 * logger.info("There is No Dialogue for Password");
				 * msg.append("There is No Dialogue for Password" + "\n\n"); }
				 */
			}
			// Close new window
			Driver.close();

			Thread.sleep(2000);

			// Switch back to original browser (first window)
			Driver.switchTo().window(strParentWindowHandle);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

			String strParentWindowHandle1 = Driver.getWindowHandle();

			// CLick on link
			isElementPresent("AnnualTSATest_linkText").click();
			logger.info("Annual TSA Test(Authorized Representative Version) Link");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			Thread.sleep(5000);

			// String strParentWindowHandle1 = Driver.getWindowHandle();

			for (String winHandle : Driver.getWindowHandles()) {
				Driver.switchTo().window(winHandle);

				try {
					WebElement siteCantBeReached = isElementPresent("SiteCantBeReach_xpath");
					getScreenshot(Driver, "AnnualTSATest");
					logger.info("Unable to Open Doc, Reason== " + siteCantBeReached.getText() + " Result=FAIL");
					msg.append("Unable to Open Doc, Reason== " + siteCantBeReached.getText() + " Result=FAIL" + "\n\n");

				} catch (Exception SiteCantBeReached) {
					logger.error(SiteCantBeReached);
					getScreenshot(Driver, "AnnualTSATest");
					logger.info("Document is Opened, Result=PASS");
					msg.append("Document is Opened, Result=PASS" + "\n\n");
				}
			}
			// Close new window
			Driver.close();

			Thread.sleep(2000);

			// Switch back to original browser (first window)
			Driver.switchTo().window(strParentWindowHandle1);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			logger.info("Agent TSA Training Test=PASS");
			msg.append("Agent TSA Training Test=PASS" + "\n");
			setResultData("Result", 1, 5, "PASS");

		} catch (Exception ATSATrainingE) {
			logger.error(ATSATrainingE);
			getScreenshot(Driver, "ATSATraining_error");
			logger.info("Agent TSA Training Test=FAIL");
			msg.append("Agent TSA Training Test=FAIL" + "\n");
			String Error = ATSATrainingE.getMessage();
			setResultData("Result", 1, 5, "FAIL");
			setResultData("Result", 1, 6, Error);
		
		}

		logger.info("=======Agent TSA Training Test End=======");
		// msg.append("=======Agent TSA Training Test End=======" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}
}
