package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class VerifyCustomerBill extends BaseInit {

	@Test
	public void verifyCustomerBill(int i) throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);//
		WebDriverWait wait2 = new WebDriverWait(Driver, 20);// wait time
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Search the Job
		OrderCreation OC = new OrderCreation();
		OC.searchJob(i);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblServiceID")));
		String svc = Driver.findElement(By.id("lblServiceID")).getText();
		System.out.println(svc);

		try {
			try {		
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[@id=\"lblStages\"][contains(text(),'Verify Customer Bill')]")));

			} catch (Exception eStage) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//*[@id=\"lblStages\"][contains(text(),'VFY CUST BILL')]")));

			}

			// --Set FAIL in TestScenarios

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 11, 5, "PASS");

			}

			// --Get StageName
			OC.getStageName();

			OC.memoAuditHistory(svc);

		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "VerifyCustBill" + svc);
			System.out.println("Verify Customer Bill Not Exist in Flow!!");
			logger.info("Verify Customer Bill Not Exist in Flow!!");
			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 11, 5, "FAIL");

			}
		}

	}
}
