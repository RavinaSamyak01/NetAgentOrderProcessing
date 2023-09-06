package netAgent_Preferences;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class UserPreferences extends BaseInit {
	@Test
	public void UserPreference() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);
		Actions act = new Actions(Driver);

		logger.info("=======User Preferences Test Start=======");
		msg.append("=======Preferences Tab Test Start=======" + "\n\n");

		try {
			// --Clicked on Preferences
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idPreferences")));
			WebElement Preference = Driver.findElement(By.id("idPreferences"));
			act.moveToElement(Preference).click().perform();
			logger.info("Clicked on Preferences");
			Thread.sleep(2000);

			// --UserPreference
			wait.until(ExpectedConditions.elementToBeClickable(By.linkText("User Preferences")));
			Driver.findElement(By.linkText("User Preferences")).click();
			logger.info("Clicked on User Preferences");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));

			JavascriptExecutor js = (JavascriptExecutor) Driver;
			js.executeScript("window.scrollBy(0,-250)");
			Thread.sleep(2000);
			getScreenshot(Driver, "UserPreference_Screen");


			// Check all fields
			// User Pref
			// 1. User Name
			Boolean uname = Driver.findElement(By.id("txtUsername")).isEnabled();

			if (uname == true) {
				throw new Error("Error: User Name field is enable");
			}

			// Currency Pref
			// 1. Currency
			Boolean Currency = Driver.findElement(By.id("txtCurrency")).isEnabled();

			if (Currency == true) {
				throw new Error("Error: Currency field is enable");
			}

			// 2. Currency Symbol
			Boolean currsym = Driver.findElement(By.id("txtCurrencySymbol")).isEnabled();

			if (currsym == true) {
				throw new Error("Error: Currency Symbol field is enable");
			}

			// 3. Currency Separator
			Boolean currsep = Driver.findElement(By.id("ddlCurrencySeparator")).isEnabled();

			if (currsep == true) {
				throw new Error("Error: Currency Separator field is enable");
			}

			// Regional Preferences
			// 1. Country
			Boolean Country = Driver.findElement(By.id("ddlCountry")).isEnabled();

			if (Country == false) {
				throw new Error("Error: Country field is disable");
			}

			// 2. Culture
			Boolean Culture = Driver.findElement(By.id("ddlCULTURE")).isEnabled();

			if (Culture == false) {
				throw new Error("Error: Culture field is disable");
			}

			// 4. Time Zone
			Boolean TZ = Driver.findElement(By.id("ddlTimeZone")).isEnabled();

			if (TZ == false) {
				throw new Error("Error: Time Zone field is disable");
			}

			// 3. Date/Time Format
			Boolean dttmfor = Driver.findElement(By.id("ddlDateTimeFormat")).isEnabled();

			if (dttmfor == false) {
				throw new Error("Error: Date/Time Format field is disable");
			}

			// 2. FSL
			Boolean FSL = Driver.findElement(By.id("ddlCourierFSL")).isEnabled();

			if (FSL == false) {
				throw new Error("Error: FSL field is disable");
			}

			// --Click on save
			Driver.findElement(By.id("btnSave")).click();
			logger.info("Clicked on Save button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			getScreenshot(Driver, "UserPreference");

			logger.info("User Preferences Test=PASS");
			msg.append("User Preferences Test=PASS" + "\n\n");
			setResultData("Result", 36, 5, "PASS");

		} catch (Exception UserPreferencesE) {
			logger.error(UserPreferencesE);
			getScreenshot(Driver, "UserPreferences_error");
			logger.info("User Preferences Test=FAIL");
			msg.append("User Preferences Test=FAIL" + "\n\n");
			String Error = UserPreferencesE.getMessage();

			setResultData("Result", 36, 5, "FAIL");
			setResultData("Result", 36, 6, Error);

		}

		logger.info("=======User Preferences Test End=======");
		msg.append("=======Preferences Tab Test End=======" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}
}
