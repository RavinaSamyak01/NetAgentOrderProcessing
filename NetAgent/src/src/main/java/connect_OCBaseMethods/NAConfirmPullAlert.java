package connect_OCBaseMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import netAgent_BasePackage.BaseInit;

public class NAConfirmPullAlert extends BaseInit {

	public void naConfirmPullAlertstage() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		Actions act = new Actions(Driver);
		JavascriptExecutor js = (JavascriptExecutor) Driver;

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ajax-loadernew\"]")));

		// --Get the ServiceID
		WebElement service = isElementPresent("NASPLService_xpath");
		wait.until(ExpectedConditions.visibilityOf(service));
		String svc = service.getAttribute("value");
		System.out.println(svc);
		logger.info("ServiceID=" + svc);
		msg.append("ServiceID=" + svc + "\n");

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'CONF PULL ALERT')]")));

			OrderCreation OC = new OrderCreation();
			OC.getNAStageName();

			try {
				WebElement Spoke = isElementPresent("NASPLSpoke_id");
				wait.until(ExpectedConditions.visibilityOf(Spoke));
				Spoke.clear();
				Spoke.sendKeys("RVOza");
				logger.info("Enter Spoke with");

			} catch (Exception e) {
				logger.info("Spoke with is not available");

			}

			// Click on Confirm Pull ALert
			WebElement ConfPullAlert = isElementPresent("NAConfPull_id");
			wait.until(ExpectedConditions.elementToBeClickable(ConfPullAlert));
			act.moveToElement(ConfPullAlert).build().perform();
			js.executeScript("arguments[0].click();", ConfPullAlert);
			logger.info("Click on Confirm Pull Alert  button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception e) {
			logger.error(e);
			getScreenshot(Driver, "ConfirmPullAlert" + svc);
			System.out.println("Confirm Pull ALert Not Exist in Flow!!");
			logger.info("Confirm Pull ALert Not Exist in Flow!!");
		}

	}
}
