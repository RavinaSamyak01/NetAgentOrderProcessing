package netAgent_OrderProcess;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import connect_OrderProcessNonSPL.*;

import netAgent_BasePackage.BaseInit;

public class LOCProcess extends BaseInit {

	public void locProcess() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time

		//--get the PUID
		LOC locp=new LOC();
		locp.locProcessing();
		
				
	}
}
