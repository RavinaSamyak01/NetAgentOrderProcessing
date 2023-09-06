package netAgent_Admin;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class UserProfile extends BaseInit {
	@Test
	public void userprofile() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);

		logger.info("=======User Profile Test Start=======");
		msg.append("=======Admin Tab Test Start=======" + "\n\n");

		try {
			// -- Go to admin
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idAdmin")));
			// WebElement Admin=Driver.findElement(By.id("idAdmin"));
			// act.moveToElement(Admin).build().perform();
			Driver.findElement(By.partialLinkText("Admin")).click();
			logger.info("Clicked on Admin");

			// --Click on UserProfile
			Driver.findElement(By.linkText("User Profile")).click();
			logger.info("Clicked on User Profile");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));

			getScreenshot(Driver, "UserProfile");

			JavascriptExecutor js = (JavascriptExecutor) Driver;
			js.executeScript("window.scrollBy(0,-250)");
			Thread.sleep(2000);

			// Check all fields

			// --UserDetails
			// --1.Login ID
			wait.until(ExpectedConditions.elementToBeClickable(By.id("txtLoginId")));
			WebElement LognID = Driver.findElement(By.id("txtLoginId"));

			if (LognID.isDisplayed()) {
				System.out.println("Status: Login ID field is display");
				logger.info("Status: Login ID field is display");
				if (LognID.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : LognID is Enabled.==FAIL");
				} else {
					logger.info("Status : ALognID is Disabled.==PASS");
				}
			}

			// --2.Email
			WebElement Email = Driver.findElement(By.id("txtEmail"));

			if (Email.isDisplayed()) {
				System.out.println("Status: Email field is display");
				logger.info("Status: Email field is display");
				if (Email.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status :Email is Enabled.==FAIL");
				} else {
					logger.info("Status : Email is Disabled.==PASS");
				}
			}
			// --3. Password
			WebElement paswd = Driver.findElement(By.id("txtPwd"));

			if (paswd.isDisplayed()) {
				System.out.println("Status: Password field is display");
				logger.info("Status: Password field is display");
				if (paswd.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Password is Enabled.==FAIL");
				} else {
					logger.info("Status : Password is Disabled.==PASS");
				}
			}

			// --4. Confirm Password
			WebElement Confpaswd = Driver.findElement(By.id("txtConfpassword"));

			if (Confpaswd.isDisplayed()) {
				System.out.println("Status: Confirm Password field is display");
				logger.info("Status: Confirm Password field is display");
				if (Confpaswd.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Confirm Password is Enabled.==FAIL");
				} else {
					logger.info("Status : Confirm Password is Disabled.==PASS");
				}
			}

			// --UserContact

			// 5. Title
			WebElement Title = Driver.findElement(By.id("txtTitle"));

			if (Title.isDisplayed()) {
				System.out.println("Status: Title  field is display");
				logger.info("Status: Title field is display");
				if (Title.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Title is Enabled.==FAIL");
				} else {
					logger.info("Status : Title is Disabled.==PASS");
				}
			}

			// 6. First Name
			WebElement fname = Driver.findElement(By.id("txtFirstname"));

			if (fname.isDisplayed()) {
				System.out.println("Status: First Name field is display");
				logger.info("Status: First NameD field is display");
				if (fname.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : First Name is Enabled.==FAIL");
				} else {
					logger.info("Status : First Name is Disabled.==PASS");
				}
			}

			// 7. Middle Name
			WebElement mname = Driver.findElement(By.id("txtMiddleName"));

			if (mname.isDisplayed()) {
				System.out.println("Status: Middle Name field is display");
				logger.info("Status: Middle Name ID field is display");
				if (mname.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Middle Name is Enabled.==FAIL");
				} else {
					logger.info("Status : Middle Name is Disabled.==PASS");

				}
			}

			// 8. Last Name
			WebElement lname = Driver.findElement(By.id("txtLastName"));

			if (lname.isDisplayed()) {
				System.out.println("Status: Last Name field is display");
				logger.info("Status: Last Name field is display");
				if (lname.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Last Name is Enabled.==FAIL");
				} else {
					logger.info("Status : Last Name is Disabled.==PASS");

				}
			}

			// 9. Address Line 1
			WebElement Add1UP = Driver.findElement(By.id("txtAddrline1"));

			if (Add1UP.isDisplayed()) {
				System.out.println("Status: Address Line 1 field is display");
				logger.info("Status: Address Line 1 field is display");
				if (Add1UP.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Address Line 1 is Enabled.==FAIL");
				} else {
					logger.info("Status : Address Line 1 is Disabled.==PASS");

				}
			}

			// 10. Dept/Suite
			WebElement deptUP = Driver.findElement(By.id("txtAddrline2"));

			if (deptUP.isDisplayed()) {
				System.out.println("Status: Dept/Suite  field is display");
				logger.info("Status: Dept/Suite field is display");
				if (deptUP.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Dept/Suite is Enabled.==FAIL");
				} else {
					logger.info("Status : Dept/Suite is Disabled.==PASS");

				}
			}

			// 11. CityUP
			WebElement CityUP = Driver.findElement(By.id("txtCity"));

			if (CityUP.isDisplayed()) {
				System.out.println("Status: City field is display");
				logger.info("Status: City field is display");
				if (CityUP.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : City is Enabled.==FAIL");
				} else {
					logger.info("Status : City is Disabled.==PASS");

				}
			}

			// 12. StateUP
			WebElement StateUP = Driver.findElement(By.id("txtState"));

			if (StateUP.isDisplayed()) {
				System.out.println("Status: State field is display");
				logger.info("Status: State field is display");
				if (StateUP.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : State is Enabled.==FAIL");
				} else {
					logger.info("Status : State is Disabled.==PASS");

				}
			}

			// 13. Zip/Postal Code
			WebElement zippost = Driver.findElement(By.id("txtZipcode"));

			if (zippost.isDisplayed()) {
				System.out.println("Status: Zip/Postal Code field is display");
				logger.info("Status: Zip/Postal field is display");
				if (zippost.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Zip/Postal Code is Enabled.==FAIL");
				} else {
					logger.info("Status : Zip/Postal Code is Disabled.==PASS");

				}
			}

			// 14. Country
			WebElement Country = Driver.findElement(By.id("txtCountryId"));

			if (Country.isDisplayed()) {
				System.out.println("Status: Country field is display");
				logger.info("Status: Country field is display");
				if (Country.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Country is Enabled.==FAIL");
				} else {
					logger.info("Status : Country is Disabled.==PASS");

				}
			}

			// 15. Main Phone
			WebElement MainPhone = Driver.findElement(By.id("txtUserMainphone"));

			if (MainPhone.isDisplayed()) {
				System.out.println("Status: Main Phone field is display");
				logger.info("Status: Main Phone field is display");
				if (MainPhone.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Main Phone is Enabled.==FAIL");
				} else {
					logger.info("Status : Main Phone is Disabled.==PASS");

				}
			}

			// 16. Work Phone
			WebElement WorkPhone = Driver.findElement(By.id("txtUserworkphone"));

			if (WorkPhone.isDisplayed()) {
				System.out.println("Status: Work Phone field is display");
				logger.info("Status: Work Phone field is display");
				if (WorkPhone.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Work Phone is Enabled.==FAIL");
				} else {
					logger.info("Status : Work Phone is Disabled.==PASS");

				}
			}

			// 17. Cell Phone
			WebElement CellPhone = Driver.findElement(By.id("txtCallphone"));

			if (CellPhone.isDisplayed()) {
				System.out.println("Status: Cell Phone field is display");
				logger.info("Status: Cell Phone field is display");
				if (CellPhone.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Cell Phone is Enabled.==FAIL");
				} else {
					logger.info("Status : Cell Phone is Disabled.==PASS");

				}
			}

			// 18. Home Phone
			WebElement HomePhone = Driver.findElement(By.id("txtHomephone"));

			if (HomePhone.isDisplayed()) {
				System.out.println("Status: Home Phone field is display");
				logger.info("Status: Home Phone field is display");
				if (HomePhone.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Home Phone is Enabled.==FAIL");
				} else {
					logger.info("Status : Home Phone is Disabled.==PASS");

				}
			}
			logger.info("User Profile Test=PASS");
			msg.append("User Profile Test=PASS" + "\n\n");
			setResultData("Result", 31, 5, "PASS");

		} catch (Exception UserProfileE) {
			logger.error(UserProfileE);
			getScreenshot(Driver, "UserProfile_error");
			logger.info("User Profile Test=FAIL");
			msg.append("User Profile Test=FAIL" + "\n\n");
			String Error = UserProfileE.getMessage();

			setResultData("Result", 31, 5, "FAIL");
			setResultData("Result", 31, 6, Error);

		}
		logger.info("=======User Profile Test End=======");
		// msg.append("=======User Profile Test End=======" + "\n\n");
		// --Refresh the App
		narefreshApp();

	}
}
