package netAgent_Admin;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class UserList extends BaseInit {
	@Test
	public void userlist() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 40);
		Actions act = new Actions(Driver);

		logger.info("=======User List Test Start=======");
		// msg.append("=======User List Test Start=======" + "\n\n");

		try {

			// -- Go to admin
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idAdmin")));
			Driver.findElement(By.id("idAdmin")).click();
			logger.info("Clicked on Admin");

			// --UserList
			Driver.findElement(By.linkText("User List")).click();
			logger.info("Clicked on User List");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("UserlistSearchGD")));

			getScreenshot(Driver, "UserList");

			JavascriptExecutor js = (JavascriptExecutor) Driver;
			js.executeScript("window.scrollBy(0,-250)");
			Thread.sleep(2000);

			// search with first name
			Driver.findElement(By.id("txtFirstName")).clear();
			logger.info("Cleared FirstName");

			String FirstName = getData("Sheet1", 2, 36);

			Driver.findElement(By.id("txtFirstName")).sendKeys(FirstName);
			logger.info("Enter FirstName");

			// --Click on Search
			wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearch")));
			WebElement Search = Driver.findElement(By.id("btnSearch"));
			act.moveToElement(Search).build().perform();
			js.executeScript("arguments[0].click();", Search);
			logger.info("Click on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			String FNact = Driver
					.findElement(
							By.xpath("//*[@class=\"dx-datagrid-content\"]//td[contains(@aria-label,'First Name')]"))
					.getText();
			System.out.println(FirstName);
			logger.info("Entered FirstName==" + FirstName);
			System.out.println(FNact);
			logger.info("Actual FirstName==" + FNact);

			if (FirstName.contentEquals(FNact)) {
				System.out.println("First Name Search Compare is PASS");
				logger.info("First Name Search Compare is PASS\"");
			} else {
				System.out.println("First Name Search Compare is FAIL");
				logger.info("First Name Search Compare is FAIL");
			}

			Driver.findElement(By.id("btnReset")).click();
			logger.info("Click on Reset button");
			String LastName = getData("Sheet1", 2, 37);

			// search with last name
			Driver.findElement(By.id("txtLastName")).clear();
			logger.info("Clear LastName");
			Driver.findElement(By.id("txtLastName")).sendKeys(LastName);
			logger.info("Enter Last Name");

			Thread.sleep(2000);
			wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearch")));
			Search = Driver.findElement(By.id("btnSearch"));
			act.moveToElement(Search).build().perform();
			js.executeScript("arguments[0].click();", Search);
			logger.info("Click on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			String LNact = Driver
					.findElement(By.xpath("//*[@class=\"dx-datagrid-content\"]//td[contains(@aria-label,'Last Name')]"))
					.getText();
			System.out.println(LastName);
			logger.info("Entered LastName==" + LastName);
			System.out.println(LNact);
			logger.info("Actual LastName==" + LNact);

			if (LastName.contentEquals(LNact)) {
				System.out.println("Last Name Search Compare is PASS");
				logger.info("Last Name Search Compare is PASS");
			} else {
				System.out.println("Last Name Search Compare is FAIL");
				logger.info("Last Name Search Compare is FAIL");
			}

			Driver.findElement(By.id("btnReset")).click();
			logger.info("Click on Reset button");

			Driver.findElement(By.id("txtFirstName")).sendKeys("Test1234");
			logger.info("Entered Invalid FirstName");

			wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearch")));
			Search = Driver.findElement(By.id("btnSearch"));
			act.moveToElement(Search).build().perform();
			js.executeScript("arguments[0].click();", Search);
			logger.info("Click on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			try {
				wait.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"dx-datagrid-nodata\"]")));
				String NoData = Driver.findElement(By.xpath("//*[@class=\"dx-datagrid-nodata\"]")).getText();
				System.out.println(NoData);
				logger.info("Records==" + NoData);

			} catch (Exception e) {

			}

			Driver.findElement(By.id("btnReset")).click();
			logger.info("Click on Reset button");

			Driver.findElement(By.id("txtLastName")).sendKeys("Test1234");
			logger.info("Entered Invalid LastName");

			wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearch")));
			Search = Driver.findElement(By.id("btnSearch"));
			act.moveToElement(Search).build().perform();
			js.executeScript("arguments[0].click();", Search);
			logger.info("Click on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			String NoData = Driver.findElement(By.xpath("//*[@class=\"dx-datagrid-nodata\"]")).getText();
			System.out.println(NoData);
			logger.info("Records==" + NoData);

			Driver.findElement(By.id("btnReset")).click();
			logger.info("Click on Reset button");

			Driver.findElement(By.id("txtLoginId")).sendKeys("Test1234");
			logger.info("Enter invalid LoginID");

			wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearch")));
			Search = Driver.findElement(By.id("btnSearch"));
			act.moveToElement(Search).build().perform();
			js.executeScript("arguments[0].click();", Search);
			logger.info("Click on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			Thread.sleep(5000);
			NoData = Driver.findElement(By.xpath("//*[@class=\"dx-datagrid-nodata\"]")).getText();
			System.out.println(NoData);
			logger.info("Records==" + NoData);

			Driver.findElement(By.id("btnReset")).click();
			logger.info("Click on Reset button");

			String LoginID = getData("Sheet1", 2, 38);

			// search with login id
			Driver.findElement(By.id("txtLoginId")).clear();
			logger.info("Clear LoginID");
			Driver.findElement(By.id("txtLoginId")).sendKeys(LoginID);
			logger.info("Enter LoginID");

			wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearch")));
			WebElement btnSearch = Driver.findElement(By.id("btnSearch"));
			act.moveToElement(btnSearch).build().perform();
			act.moveToElement(btnSearch).click().perform();
			logger.info("Click on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			Thread.sleep(5000);
			// String Loginidexp = Driver.findElement(By.id("txtLoginId")).getText();
			String Loginidact = Driver
					.findElement(By.xpath("//*[@class=\"dx-datagrid-content\"]//td[contains(@aria-label,'Login Id')]"))
					.getText();
			System.out.println(LoginID);
			logger.info("Entered LoginID==" + LoginID);
			System.out.println(Loginidact);
			logger.info("Actual LoginID==" + Loginidact);

			if (LoginID.contentEquals(Loginidact)) {
				System.out.println("Login ID Search Compare is PASS");
				logger.info("Login ID Search Compare is PASS");
			} else {
				System.out.println("Login ID Search Compare is FAIL");
				logger.info("Login ID Search Compare is FAIL0");
			}

			// Click on Edit
			Driver.findElement(By.id("imgUserListEdit_1")).click();
			logger.info("Click on Edit");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			// wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'well-sm')]")));

			// check all editable fields
			// 1. Login Type
			Boolean logintype = Driver.findElement(By.id("txtLoginType")).isEnabled();

			if (logintype == true) {
				throw new Error("Error: Login Type field is enable");
			}

			// 2. Reporting To
			Boolean RepoTo = Driver.findElement(By.id("txtReportingTo")).isEnabled();

			if (RepoTo == false) {
				throw new Error("Error: Reporting To field is disable");
			}

			// 3. Login ID
			Boolean loginid = Driver.findElement(By.id("txtLoginId")).isEnabled();

			if (loginid == true) {
				throw new Error("Error: Login ID field is enable");
			}

			// 4. Password
			Boolean pwd = Driver.findElement(By.id("txtPwd")).isEnabled();

			if (pwd == true) {
				throw new Error("Error: Password field is enable");
			}

			// 5. Confirm Password
			Boolean cnfpwd = Driver.findElement(By.id("txtConfPwd")).isEnabled();

			if (cnfpwd == true) {
				throw new Error("Error: Confirm Password field is enable");
			}

			// 6. First Name
			Boolean fname = Driver.findElement(By.id("txtFirstName")).isEnabled();

			if (fname == false) {
				throw new Error("Error: First Name field is disable");
			}

			// 7. Middle Name
			Boolean mname = Driver.findElement(By.id("txtMiddleName")).isEnabled();

			if (mname == false) {
				throw new Error("Error: Middle Name field is disable");
			}

			// 8. Last Name
			Boolean lname = Driver.findElement(By.id("txtLastName")).isEnabled();

			if (lname == false) {
				throw new Error("Error: Last Name field is disable");
			}

			// 9. Title
			Boolean title = Driver.findElement(By.id("txtTitle")).isEnabled();

			if (title == false) {
				throw new Error("Error: Title field is disable");
			}

			// 10. Portal Type
			Boolean porttype = Driver.findElement(By.id("txtPortalType")).isEnabled();

			if (porttype == true) {
				throw new Error("Error: Portal Type  field is enable");
			}

			// 11. Password Last Set
			Boolean pwdlastset = Driver.findElement(By.id("txtPwdLastSet")).isEnabled();

			if (pwdlastset == true) {
				throw new Error("Error: Password Last Set field is enable");
			}

			// 12. Valid From
			Boolean vfrom = Driver.findElement(By.id("txtvalidfrom")).isEnabled();

			if (vfrom == false) {
				throw new Error("Error: Valid From field is disable");
			}

			// 13. Valid To
			Boolean vto = Driver.findElement(By.id("txtValidto")).isEnabled();

			if (vto == false) {
				throw new Error("Error: Valid To field is disable");
			}

			// 14. Description
			Boolean desc = Driver.findElement(By.id("txtDescription")).isEnabled();

			if (desc == false) {
				throw new Error("Error: Description field is disable");
			}

			// User Contact grid
			// 1. Country
			Boolean asdasd = Driver.findElement(By.id("drpCountry")).isEnabled();

			if (asdasd == false) {
				throw new Error("Error: Country field is disable");
			}

			// 2. Zip/Postal Code
			Boolean Zip = Driver.findElement(By.id("txtZipCode")).isEnabled();

			if (Zip == false) {
				throw new Error("Error: Zip/Postal Code field is disable");
			}

			// 3. City
			Boolean City = Driver.findElement(By.id("txtCity")).isEnabled();

			if (City == false) {
				throw new Error("Error: City field is disable");
			}

			// 4. State
			Boolean State = Driver.findElement(By.id("txtState")).isEnabled();

			if (State == true) {
				throw new Error("Error: State field is enable");
			}

			// 5. Address Line 1
			Boolean add1 = Driver.findElement(By.id("txtAddr1")).isEnabled();

			if (add1 == false) {
				throw new Error("Error: Address Line 1 field is disable");
			}

			// 6. Dept/Suite
			Boolean Dept = Driver.findElement(By.id("txtDept")).isEnabled();

			if (Dept == false) {
				throw new Error("Error: Dept/Suite field is disable");
			}

			// 7. Main Phone
			Boolean mphone = Driver.findElement(By.id("txtMain")).isEnabled();

			if (mphone == false) {
				throw new Error("Error: Main Phone field is disable");
			}

			// 8. Main Phone ext
			Boolean mphoneext = Driver.findElement(By.id("txtExt")).isEnabled();

			if (mphoneext == false) {
				throw new Error("Error: Main Phone ext field is disable");
			}

			// 9. Fax
			Boolean Fax = Driver.findElement(By.id("txtFax")).isEnabled();

			if (Fax == false) {
				throw new Error("Error: Fax field is disable");
			}

			// 10. Email
			Boolean Email = Driver.findElement(By.id("txtEmail")).isEnabled();

			if (Email == false) {
				throw new Error("Error: Email field is disable");
			}

			// 11. Work Phone
			Boolean wphone = Driver.findElement(By.id("txtUserWorkphone")).isEnabled();

			if (wphone == false) {
				throw new Error("Error: Work Phone field is disable");
			}

			// 12. Work Phone Ext
			Boolean wphoneext = Driver.findElement(By.id("txtWorkphoneExt")).isEnabled();

			if (wphoneext == false) {
				throw new Error("Error: Work Phone Ext field is disable");
			}

			// 13. Call Phone
			Boolean cphone = Driver.findElement(By.id("txtCallphone")).isEnabled();

			if (cphone == false) {
				throw new Error("Error: Call Phone field is disable");
			}

			// 14. Home Phone
			Boolean hphone = Driver.findElement(By.id("txtHomephone")).isEnabled();

			if (hphone == false) {
				throw new Error("Error: Home Phone field is disable");
			}

			// 15. Web Address
			Boolean wadd = Driver.findElement(By.id("txtWebaddress")).isEnabled();

			if (wadd == false) {
				throw new Error("Error: Web Address field is disable");
			}

			// 16. Security Question
			Boolean sque = Driver.findElement(By.id("txtSecQue")).isEnabled();

			if (sque == false) {
				throw new Error("Error: Security Question field is disable");
			}

			// 17. Response
			// Driver.findElement(By.id("chkShowResponse")).click();
			// Thread.sleep(5000);

			// Boolean Response = Driver.findElement(By.id("txtSecAns")).isEnabled();

			// if(Response == false)
			// {
			// throw new Error("Error: Response field is disable");
			// }

			// Click on save
			// Driver.findElement(By.id("imgSaveUserMaster")).click();
			// Thread.sleep(5000);

			// add and delete user role
			js.executeScript("window.scrollTo(2, document.body.scrollHeight);");

			// select all
			Driver.findElement(By.id("chkRoleItemsSelectAll")).click();
			logger.info("All Role selected");

			// -Delete
			Driver.findElement(By.id("imgCancelIcon")).click();
			logger.info("Click on Delete button");

			Driver.switchTo().alert();
			Driver.switchTo().alert().accept();
			logger.info("Click on Ok button of Confirmation popup");

			Driver.findElement(By.id("imgSaveUserMaster")).click();
			logger.info("Click on Save User button");

			getScreenshot(Driver, "UserList_Validation");

			String Message1 = Driver.findElement(By.id("idValidation")).getText();

			if (Message1.equals("Please assign atleast one Role to User.")) {
				Message1 = "*****Validation message is matched*****";
				System.out.println(Message1);
				logger.info("Please assign atleast one Role to User.");
			}

			// --Assign Role
			Driver.findElement(By.id("imgPlusIcon")).click();
			// js.executeScript("window.scrollTo(2, document.body.scrollHeight);");
			logger.info("Click on Add Role button");

			// --Select Role
			WebElement Roledrp = Driver.findElement(By.xpath("//*[@id=\"drpRole\"]"));
			act.moveToElement(Roledrp).build().perform();
			js.executeScript("arguments[0].scrollIntoView();", Roledrp);
			Thread.sleep(2000);
			wait.until(ExpectedConditions.elementToBeClickable(Roledrp));
			Roledrp.click();
			logger.info("Click on Role dropdown");
			Thread.sleep(2000);
			Select Rolename = new Select(Driver.findElement(By.id("drpRole")));
			Rolename.selectByVisibleText("NETAGENT W/Inventory");
			logger.info("Select Role");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

			Driver.findElement(By.id("imgSaveUserMaster")).click();
			logger.info("Click on Save User button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("UserlistSearchGD")));

			// enter valid to for newly added
			/*
			 * DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy"); Date Todate = new
			 * Date(); String ValidToDate = dateFormat.format(Todate);
			 * 
			 * Driver.findElement(By.id("txtCalValidTo")).clear();
			 * Driver.findElement(By.id("txtCalValidTo")).sendKeys(ValidToDate);
			 * Thread.sleep(7000);
			 */

			// Select added record and click on delete
			/*
			 * Driver.findElement(By.xpath(
			 * "//*[@id=\"RoleDetailsTable\"]/tbody/tr[7]/td[1]/input")).click();
			 * Thread.sleep(7000);
			 * 
			 * Driver.findElement(By.id("imgCancelIcon")).click(); Thread.sleep(7000);
			 * 
			 * Driver.switchTo().alert(); Driver.switchTo().alert().accept();
			 * Thread.sleep(7000);
			 */

			logger.info("UserList Test=PASS");
			msg.append("UserList Test=PASS" + "\n\n");
			setResultData("Result", 32, 5, "PASS");

		} catch (Exception UserLIstE) {
			logger.error(UserLIstE);
			getScreenshot(Driver, "UserLIst_error");
			logger.info("UserList Test=FAIL");
			msg.append("UserList Test=FAIL" + "\n\n");
			String Error = UserLIstE.getMessage();

			setResultData("Result", 32, 5, "FAIL");
			setResultData("Result", 32, 6, Error);

		}

		logger.info("=======User List Test End=======");
		// msg.append("=======User List Test End=======" + "\n\n");
		msg.append("=======Admin Tab Test End=======" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}
}
