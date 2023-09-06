package netAgent_LogOutDiv;

import java.awt.Desktop.Action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class AgentElevatesRiskTraining extends BaseInit {
	@Test
	public void AgentRisk() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 50);
		Actions act = new Actions(Driver);
		// Go to Welcome - Agent Risk screen
		logger.info("=======Agent Elevated Risk Training Test Start=======");
		// msg.append("=======Agent Elevated Risk Training Test Start=======" + "\n\n");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
		try {
		WebElement logout_module=	isElementPresent("LogOutDiv_xpath");
		act.moveToElement(logout_module).build().perform();
		Thread.sleep(500);
		act.click(logout_module).build().perform();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			logger.info("Click on LogOutDiv");
			isElementPresent("AgentElevated_linkText").click();
			logger.info("Click on Agent Elevated Risk Training");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
Thread.sleep(1500);
			System.out.println(Driver.getTitle());

			// Take screen-shot.
			getScreenshot(Driver, "AgentRisk");

			String strParentWindowHandle = Driver.getWindowHandle();

			// CLick on link
			isElementPresent("ElevatedRisk_linkText").click();
			logger.info("Click on Elevated Risk Physical Search 2.0A Training Presentation link");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			Thread.sleep(5000);

			for (String winHandle : Driver.getWindowHandles()) {
				Driver.switchTo().window(winHandle);
				/*
				 * try { WebElement PwdDialogue = isElementPresent("PwdDialogue_id");
				 * wait.until(ExpectedConditions.visibilityOfAllElements(PwdDialogue));
				 * getScreenshot(Driver, "ElevatedtRiskTrain");
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
				 * "ElevatedtRiskTrain");
				 * System.out.println("There is no Dialogue for Password");
				 * logger.info("There is No Dialogue for Password");
				 * msg.append("There is No Dialogue for Password" + "\n\n"); }
				 */
			}
			// Close newwindow
			Driver.close();
			Thread.sleep(2000);

			// Switch back to original browser window
			Driver.switchTo().window(strParentWindowHandle);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

			String strParentWindowHandle1 = Driver.getWindowHandle();
			Thread.sleep(2000);
			// CLick on link
			isElementPresent("ElevatedRiskTest_linkText").click();
			logger.info("Elevated Risk Physical Search 2.0A Test");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			Thread.sleep(5000);

			for (String winHandle : Driver.getWindowHandles()) {
				Driver.switchTo().window(winHandle);

				try {
					WebElement siteCantBeReached = isElementPresent("SiteCantBeReach_xpath");
					getScreenshot(Driver, "ElevatedRiskTest");
					logger.info("Unable to Open Doc, Reason== " + siteCantBeReached.getText() + " Result=FAIL");
					msg.append("Unable to Open Doc, Reason== " + siteCantBeReached.getText() + " Result=FAIL" + "\n\n");

				} catch (Exception SiteCantBeReached) {
					logger.error(SiteCantBeReached);
					getScreenshot(Driver, "ElevatedRiskTest");
					logger.info("Document is Opened, Result=PASS");
					msg.append("Document is Opened, Result=PASS" + "\n\n");
				}
			}
			// Close new window
			Driver.close();
			Thread.sleep(2000);

			// Switch back to original browser window
			Driver.switchTo().window(strParentWindowHandle1);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));

			logger.info("Agent Elevated Risk Training Test=PASS");
			msg.append("Agent Elevated Risk Training Test=PASS" + "\n");
			setResultData("Result", 2, 5, "PASS");

		} catch (Exception UERTariningE) {
			logger.error(UERTariningE);
			getScreenshot(Driver, "UERTariningE_error");
			logger.info("Agent Elevated Risk Training Test=FAIL");
			msg.append("Agent Elevated Risk Training Test=FAIL" + "\n");
			setResultData("Result", 2, 5, "FAIL");
			String Error = UERTariningE.getMessage();
			setResultData("Result", 2, 6, Error);

			

		}

		logger.info("=======Agent Elevated Risk Training Test End=======");
		// msg.append("=======Agent Elevated Risk Training Test End=======" + "\n\n");
		// --Refresh the App
		narefreshApp();

	}
}
