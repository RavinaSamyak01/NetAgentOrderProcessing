package NetAgent_Master_screen_Production;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import connect_OCBaseMethods.OrderCreation;
import netAgent_Admin.UserList;
import netAgent_Admin.UserProfile;
import netAgent_BasePackage.BaseInit;
import netAgent_Inventory.ASNLog;
import netAgent_Inventory.FSLSetUp;
import netAgent_Inventory.FSLStorage;
import netAgent_Inventory.Parts;
import netAgent_LogOutDiv.AgentElevatesRiskTraining;
import netAgent_LogOutDiv.AgentTSATraining;
import netAgent_LogOutDiv.ContactUS;
import netAgent_OperationsTab.Courier;
import netAgent_OperationsTab.OrderSearch;
import netAgent_Preferences.UserPreferences;
import netAgent_Reports.AgentActivity;
import netAgent_Reports.AgentActivityChart;
import netAgent_Reports.AgentActivityDetail;
import netAgent_Reports.InventoryOnHandReport;
import netAgent_Reports.InventoryPullReport;
import netAgent_Reports.InventoryReceiptReport;
import netAgent_Reports.InventoryTransactionReport;
import netAgent_Reports.QuarantineReport;
import netAgent_Reports.RTEDriverBilling;
import netAgent_Tools.AgentConsole;
import netAgent_Tools.MNXDocuments;
import netAgent_Tools.MileageCalc;

public class NetAgent_Production extends BaseInit {

	@Test
	public void screen_validate() throws Exception {

		msg.append("===== Net Agent Master Screen Verification  : Start =====" + "\n");
		logger.info("===== Net Agent Master Screen Verification  : Start =====");
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		WebDriverWait wait = new WebDriverWait(Driver, 30);// wait time
		WebDriverWait wait2 = new WebDriverWait(Driver, 90);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
		// -- logout Division

		msg.append("Log out division verification : Start " + "\n");
		logger.info("Log out division verification : Start " + "\n");

		AgentElevatesRiskTraining ld = new AgentElevatesRiskTraining();
	//	ld.AgentRisk();

		AgentTSATraining ld1 = new AgentTSATraining();
	//	ld1.AgentTSA();

		ContactUS ld2 = new ContactUS();
	//	ld2.ContactUs();

		msg.append("Log out division verification : End " + "\n");
		logger.info("Log out division verification : End " + "\n");

		// -- Operations Division

		msg.append("Operations division verification : Start " + "\n");
		logger.info("Operations division verification : Start " + "\n");

		Courier ops = new Courier();
	//	ops.courier();

		OrderSearch ops1 = new OrderSearch();
	//	ops1.orderSearch();

		msg.append("Operations division verification : End " + "\n");
		logger.info("Operations division verification : End " + "\n");

		// -- Admin Division

		msg.append("Admin division verification : Start " + "\n");
		logger.info("Admin division verification : Start " + "\n");

		UserList ad = new UserList();
	//	ad.userlist();

		UserProfile ad1 = new UserProfile();
//		ad1.userprofile();

		msg.append("Admin division verification : End " + "\n");
		logger.info("Admin division verification : End " + "\n");

		// -- Inventory Division

		msg.append("Inventory division verification : Start " + "\n");
		logger.info("Inventory division verification : Start " + "\n");

		Parts in1 = new Parts();
	//	in1.parts();

		ASNLog in = new ASNLog();
	//	in.aSNLog();

		FSLStorage in2 = new FSLStorage();
	//	in2.fSLStorage();

		FSLSetUp in3 = new FSLSetUp();
	//	in3.fSLSetup();

		msg.append("Inventory division verification : End " + "\n");
		logger.info("Inventory division verification : End " + "\n");

		// -- Tools Division

		msg.append("Tools division verification : Start " + "\n");
		logger.info("Tools division verification : Start " + "\n");

		AgentConsole tools = new AgentConsole();
		// tools.agentConsole();
		
// WIP (putting on hold due to fetch and select appropriate data from dropdown dynamic as per airline data)

		MileageCalc tools1 = new MileageCalc();
	//	tools1.mileageCalc();

		MNXDocuments tools2 = new MNXDocuments();
	//	tools2.MNXDoc();

		msg.append("Tools division verification : End " + "\n");
		logger.info("Tools division verification : End " + "\n");

		// -- Reports Division

		msg.append("Reports division verification : Start " + "\n");
		logger.info("Reports division verification : Start " + "\n");
		AgentActivity AGACt = new AgentActivity();
	//	AGACt.AgentActivityReport();

		AgentActivityChart AAChart = new AgentActivityChart();
	//	AAChart.AgentActivityChartReport();

		AgentActivityDetail AADetail = new AgentActivityDetail();
	//	AADetail.agentActivityDetailReport();

		InventoryOnHandReport IOnHand = new InventoryOnHandReport();
	//	IOnHand.OnHandReport();

		InventoryPullReport InPull = new InventoryPullReport();
	//	InPull.PullReport();

		InventoryReceiptReport InRec = new InventoryReceiptReport();
	//	InRec.ReceiptReport();

		InventoryTransactionReport InTran = new InventoryTransactionReport();
	//	InTran.TransactionReport();

		QuarantineReport Quar = new QuarantineReport();
		Quar.quarantineReport();

		RTEDriverBilling RTEBill = new RTEDriverBilling();
	//	RTEBill.rteDriverBillingReport();
		
		//--- RTE DRiver Billing not visible on Prod server 

		msg.append("Reports division verification : End " + "\n");
		logger.info("Reports division verification : End " + "\n");

		// -- UserPreference Division

		msg.append("UserPreference division verification : Start " + "\n");
		logger.info("UserPreference division verification : Start " + "\n");

		UserPreferences pr = new UserPreferences();
		pr.UserPreference();

		msg.append("UserPreference division verification : End " + "\n");
		logger.info("UserPreference division verification : End " + "\n");

	}
}
