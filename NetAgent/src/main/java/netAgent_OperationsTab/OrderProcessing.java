package netAgent_OperationsTab;

import java.awt.AWTException;
import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

import netAgent_OrderProcessing.RETURN_OrderProcess;
import netAgent_OrderProcessing.RTE_OrderProcess;
import netAgent_OrderProcessing.Replenish_OrderProcess;

public class OrderProcessing extends BaseInit {

	@Test
	public void orderProcessing()
			throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException, AWTException {

		logger.info("=======Order Processing Test Start=======" + "\n\n");
		msg.append("=======Order Processing Test Start=======" + "\n\n");

		// --RETURN
		RETURN_OrderProcess RTN = new RETURN_OrderProcess();
		RTN.orderProcessRETURNJOB();

		// --RTE
		RTE_OrderProcess RTE = new RTE_OrderProcess();
		RTE.orderProcessRTEJOB();

		// --Replenish
		Replenish_OrderProcess Rplsh = new Replenish_OrderProcess();
		Rplsh.orderProcessReplenishJOB();

		logger.info("=======Order Processing Test End=======" + "\n\n");
		msg.append("=======Order Processing Test End=======" + "\n\n");

	}

}
