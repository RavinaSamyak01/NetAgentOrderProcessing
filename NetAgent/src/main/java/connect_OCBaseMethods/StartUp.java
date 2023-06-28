package connect_OCBaseMethods;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class StartUp extends BaseInit {
	String File = null;

	@Test
	public void startup() throws IOException {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;

		try {

			// Open new tab
			jse = (JavascriptExecutor) Driver;
			jse.executeScript("window.open()");

			// --Moved to tab
			ArrayList<String> tabs = new ArrayList<String>(Driver.getWindowHandles());
			Driver.switchTo().window(tabs.get(1));

			// --Connect Login

			Connectlogin();

			// -- Activate Account

			ActivateAccount();

			File = null;
		} catch (Exception ee) {
			logger.error(ee);
			logger.info("Line number is: " + ee.getStackTrace()[0].getLineNumber() + ee.getClass());
			getScreenshot(Driver, "ConnectLoginissue");
			System.out.println("Login issue of connect");
			logger.info("Login issue of connect");
			// --Connect LogOut
			// ConnectlogOut();
			File = ".\\Report\\NA_Screenshot\\ConnectLoginissue.png, .\\Report\\ExtentReport\\ExtentReportResults.html,.\\Report\\log\\NetAgentLog.html";

		}
	}

	@Test
	public void endup() {
		ArrayList<String> tabs = new ArrayList<String>(Driver.getWindowHandles());

		// --Moved to tab
		tabs = new ArrayList<String>(Driver.getWindowHandles());
		Driver.switchTo().window(tabs.get(1));

		Driver.close();

		Driver.switchTo().window(tabs.get(0));
		// Switch back to original browser (first window)logger.info("Moved to main
		// window");

	}

}
