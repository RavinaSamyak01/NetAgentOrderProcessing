package netAgent_Reports;

import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class Reports extends BaseInit {

	@Test
	public void testAllReports() throws Exception {

		AgentActivity AGACt = new AgentActivity();
		AGACt.AgentActivityReport();

		AgentActivityChart AAChart = new AgentActivityChart();
		AAChart.AgentActivityChartReport();

		AgentActivityDetail AADetail = new AgentActivityDetail();
		AADetail.agentActivityDetailReport();

		InventoryOnHandReport IOnHand = new InventoryOnHandReport();
		IOnHand.OnHandReport();

		InventoryPullReport InPull = new InventoryPullReport();
		InPull.PullReport();

		InventoryReceiptReport InRec = new InventoryReceiptReport();
		InRec.ReceiptReport();

		InventoryTransactionReport InTran = new InventoryTransactionReport();
		InTran.TransactionReport();

		QuarantineReport Quar = new QuarantineReport();
		Quar.quarantineReport();

		RTEDriverBilling RTEBill = new RTEDriverBilling();
		RTEBill.rteDriverBillingReport();
	}

}
