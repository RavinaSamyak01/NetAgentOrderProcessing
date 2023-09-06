package Auto_verify_30018;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.mongodb.diagnostics.logging.Logger;

import connect_OCBaseMethods.NAConfirmPUAlert;
import connect_OCBaseMethods.NADeliver;
import connect_OCBaseMethods.NAPickup;
import connect_OCBaseMethods.OrderCreation;
import connect_OCBaseMethods.ReadyForDispatch;
import connect_OCBaseMethods.TCAcknowledge;
import netAgent_BasePackage.BaseInit;

public class ASN_verify_NetAgent extends BaseInit {
	method_30018 mth = new method_30018();

	@Test
	public void create_loc_verify_status() throws Exception {

		msg.append("===== NetAgent ASN Auto Verify Testcase  : Start =====" + "\n");
		logger.info("===== NetAgent ASN Auto Verify Testcase  : Start =====");
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 60);// wait time
		WebDriverWait wait2 = new WebDriverWait(Driver, 90);
		OrderCreation OC = new OrderCreation();
		Actions act = new Actions(Driver);
		String connect_STG_link = storage.getProperty("connect_STGURL");

		String new_tab_link = "window.open('" + connect_STG_link + "','_blank');";

		((JavascriptExecutor) Driver).executeScript(new_tab_link);

		// Get handles of the windows , move focus on connect portal

		String mainWindowHandle = Driver.getWindowHandle();
		Set<String> allWindowHandles = Driver.getWindowHandles();
		Iterator<String> iterator = allWindowHandles.iterator();
		{

			// Here we will check if child window has other child windows and will fetch the
			// heading of the child window
			try {
				while (iterator.hasNext()) {

					String ChildWindow = iterator.next();
					if (!mainWindowHandle.equalsIgnoreCase(ChildWindow)) {

						// -- focucs is now on connect portal
						Driver.switchTo().window(ChildWindow);

						logger.info("Focus is on Connect portal");

						Connectlogin();
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						// -- Checking for Outbound order ============
						logger.info("Order creation in Connect : Start");
						try {

							// --Order Creation

							mth.ASN_create(12);

							Driver.switchTo().window(mainWindowHandle);
							Thread.sleep(2000);
							logger.info("On Net Agenty portal");

							// --Refresh App
							Driver.navigate().refresh();
							wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							// -- REDIRECT TO TASK LOG

							WebElement tasklog = isElementPresent("taslog_id");
							tasklog.click();
							wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							logger.info("Click on Tasklog link");

							// -- inventoory from tasklog
							WebElement inventory = isElementPresent("inventory_xpath");
							inventory.click();
							wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							logger.info("Click on Inventory button from tasklog link");

							// -- get asn no and enter

							String asn_no = getData("Auto_verify_30018", 12, 5);
							logger.info("fetched ASN is : " + asn_no);
							wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							WebElement enter_asn_search = isElementPresent("TLBasicSearch_id");
							enter_asn_search.clear();
							enter_asn_search.sendKeys(asn_no);
							logger.info("ASN entered in search field");
							getScreenshot(Driver, "Entered ASN in Search");

							WebElement search_asn_na = isElementPresent("TLInvSearchBTN_id");
							search_asn_na.click();
							logger.info("Click on search button");
							wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							WebElement expected_qty = isElementPresent("expected_qty_id");
							expected_qty.clear();
							expected_qty.sendKeys("1");
							logger.info("Expected Qty is entered");

							// -- save asn data

							WebElement save = isElementPresent("TLPTSave_id");
							save.click();
							wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							logger.info("Clicked on Save button");

							// - -update qty

							WebElement update = isElementPresent("TLPTUpdate_id");
							update.click();
							wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							logger.info("Clicked on Update button");

							String expected_suc_msg = "Data Saved Successfully. You may print label from Print Label icon or click on close to return to Task log.";

							String actual_msg = isElementPresent("TLSuccess_id").getText();
							if (actual_msg.equalsIgnoreCase(expected_suc_msg)) {
								logger.info("ASN QTy update in from Netagent");
								getScreenshot(Driver, "ASN update suc msg");
							} else {
								logger.info("ASN QTy not update in from Netagent");
								getScreenshot(Driver, "ASN suc msg fail");
							}

						} catch (Exception e) {
							// TODO: handle exception
							logger.info("Issue in ASN Creation processing");
						}
						
						Driver.switchTo().window(ChildWindow);

						logger.info("Focus is on Connect portal");
						
						// --Click on Operation menu and select SPL
						WebElement operation_menu = isElementPresent("ConnectOperations_id");
						act.moveToElement(operation_menu).build().perform();
						Thread.sleep(500);
						act.click(operation_menu).build().perform();
						Thread.sleep(1500);
						 //-- opt SPL from OPeration menu
						WebElement spl = isElementPresent("spl_xpath");
						getScreenshot(Driver, "PL_menu");
						spl.click();
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						logger.info("OPen SPL");
						
						// --  seatch all Workorder
						
						WebElement all_work_order = isElementPresent("search_wo_xpath");
						all_work_order.click();
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						logger.info("Click on All Work order search");
						
						String asn_no = getData("Auto_verify_30018", 12, 5);
						
						WebElement asn = isElementPresent("wo_asn_id");
						asn.clear();
						asn.sendKeys(asn_no);
						logger.info("ASN Entered in AO search");
						
						// -- submit asn search
						getScreenshot(Driver, "Entered ASN Details");
						WebElement asn_earch = isElementPresent("CustSearchBTN_id");
						asn_earch.click();
						logger.info("ASN Searched");
						wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						try {

							String wo_id = getData("Auto_verify_30018", 12, 6);

							WebElement audit = isElementPresent("audit_id");
							audit.click();
							wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							logger.info("CLick on Audit Link");

							String ad_audit = Driver.findElement(By.xpath(
									"//td[@role='gridcell']//span[@class='ng-scope' and contains(text(),'WorkOrder')]"))
									.getText();
							logger.info("ASN Auto dispatch Audit text : " + ad_audit);
							getScreenshot(Driver, "audit popup");

							String expected_audit_text = "WorkOrder # " + wo_id
									+ " is Auto Verify as per Auto Verify flag setup";
							logger.info("Expected Audiot text : " + expected_audit_text);

							if (ad_audit.equalsIgnoreCase(expected_audit_text)) {

								logger.info("ASN Auto dispatch for Netagent is  passed");
								msg.append("Netagent ASN Auto dispatch == PASS" + "\n");
								setData("Auto_verify_30018", 12, 3, "PASS");
							} else {
								logger.info("ASN Auto dispatch for Netagent is  FAIL");
								msg.append("Netagent ASN Auto dispatch == FAIL" + "\n");
								setData("Auto_verify_30018", 12, 3, "FAIL");
							}

						}

						catch (Exception e) {
							// TODO: handle exception
							logger.info("ASN Auto dispatch for Connect is  FAIL");
							msg.append("Connect ASN Auto dispatch == FAIL" + "\n");
							setData("Auto_verify_30018", 12, 3, "FAIL");
						}

					}

				}

				Driver.close();
				Driver.switchTo().window(mainWindowHandle);
				Thread.sleep(2000);

			} catch (Exception e) {
// TODO: handle exception
				Driver.switchTo().window(mainWindowHandle);
				logger.info("Error in Order creation from connect : " + e);
				getScreenshot(Driver, "Order_issue_AV");
							}
		}
		msg.append("===== NetAgent ASN Auto Verify Testcase  : End =====" + "\n");
		logger.info("===== NetAgent ASN Auto Verify Testcase  : End =====");
	}

	


}
