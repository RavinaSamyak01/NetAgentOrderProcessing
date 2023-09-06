package netAgent_LogOutDiv;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class ContactUS extends BaseInit {
	@Test
	public void ContactUs() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 40);
		logger.info("=======ContactUS Test Start=======");
		// msg.append("=======ContactUS Test Start=======" + "\n\n");
		Actions act = new Actions(Driver);
		String stg = "LogOutDiv_xpath";
		String prod = "LogOutDiv_prod_xpath";

		String Env = storage.getProperty("Env");
		String logout_div = null;
		if (Env.equalsIgnoreCase("STG")) {
			logout_div = stg;
		} else if (Env.equalsIgnoreCase("Test")) {
			logout_div = stg;
		} else if (Env.equalsIgnoreCase("PROD")) {
			logout_div = prod;
		}

		try {
			// Go to Welcome - Contact Us screen
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'userthumb')]")));
//			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'userthumb')]")));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			isElementPresent(logout_div).click();
			Thread.sleep(1500);
			isElementPresent("ContactUS_linkText").click();
			logger.info("Clicked on ContactUS");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			// -- Comapny name

			WebElement Company_name = isElementPresent("contact_company_id");
			wait.until(ExpectedConditions.visibilityOf(Company_name));
			wait.until(ExpectedConditions.elementToBeClickable(Company_name));
			Company_name.clear();
			Company_name.sendKeys("PACIFIC COAST LOGISTIX");

			// -- Name

			WebElement name = isElementPresent("cntct_name_id");
			name.clear();
			name.sendKeys("Parth");
			

			// --Email
			WebElement email = isElementPresent("cntct_email_id");
			email.clear();
			email.sendKeys("parth.shah@samyak.com");

			// Contact Us screen and Add comment and submit.
			System.out.println(Driver.getTitle());
			isElementPresent("Comments_name").clear();
			isElementPresent("Comments_name").sendKeys("Test Note");
			logger.info("Entered Comments");
			WebElement submit_button = isElementPresent("Submit_id");
			act.moveToElement(submit_button).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(submit_button));
			submit_button.click();
			logger.info("Clicked on Submit button");

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			try {
				// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[@class=\"ng-binding\"]")));
				WebElement SuccMsg = isElementPresent("SuccessMsg_xpath");
				wait.until(ExpectedConditions.visibilityOf(SuccMsg));
				if (SuccMsg.isDisplayed()) {
					System.out.println("Success Message is displayed==" + SuccMsg.getText() + "\n\n");
					logger.info("Success Message is displayed==" + SuccMsg.getText() + "\n\n");
					msg.append("Success Message is displayed==" + SuccMsg.getText() + "\n\n");
					logger.info("Success Message is displayed==PASS");
					logger.info("ContactUS Test=PASS");
					msg.append("ContactUS Test=PASS" + "\n");
					setResultData("Result", 3, 5, "PASS");

				}
			} catch (Exception SUccMsg) {
				logger.error(SUccMsg);
				System.out.println("Success Message is not displayed" + "\n\n");
				logger.info("Success Message is not displayed" + "\n\n");
				msg.append("Success Message is not displayed" + "\n\n");
				logger.info("Success Message is not displayed==FAIL");

				logger.info("ContactUS Test=FAIL");
				msg.append("ContactUS Test=FAIL" + "\n");
				String Errors = SUccMsg.getMessage();
				setResultData("Result", 3, 5, "FAIL");
				setResultData("Result", 3, 6, Errors);
			}

			// Take screen-shot.
			getScreenshot(Driver, "ContactUs");

		} catch (Exception ContactUSE) {
			logger.error(ContactUSE);
			getScreenshot(Driver, "ContactUS_error");
			logger.info("ContactUS Test=FAIL");
			msg.append("ContactUS Test=FAIL" + "\n");
			String Error = ContactUSE.getMessage();
			setResultData("Result", 3, 5, "FAIL");
			setResultData("Result", 3, 6, Error);

		}

		logger.info("=======ContactUS Test End=======" + "\n\n");
		// msg.append("=======ContactUS Test End=======" + "\n\n");
		msg.append("=======LogOut Div Test End=======" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}

}
