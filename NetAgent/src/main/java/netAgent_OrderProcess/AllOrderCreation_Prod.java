package netAgent_OrderProcess;

import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;


import connect_OrderProcessNonSPL.LOC;
import connect_OrderProcessNonSPL.P3P;
import connect_OrderProcessNonSPL.PA;
import connect_OrderProcessNonSPL.RTE;
import connect_OrderProcessNonSPL.SD;
import connect_OrderProcessSPL.CPU;
import connect_OrderProcessSPL.D3P;
import connect_OrderProcessSPL.H3P;
import connect_OrderProcessSPL.T3PLAST;
import netAgent_BasePackage.BaseInit;
import netAgent_BasePackage.SendEmail;

public class AllOrderCreation_Prod extends BaseInit {
	String File = null;

	@Test
	public void allOrderCreation() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;
		msg.append("\n\n" + "===== NA Order Processing Test : Start ====" + "\n");
		String Env = storage.getProperty("Env");

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

			/*
			 * try { RTE rteJob = new RTE(); rteJob.rtejobcreationprocess();
			 * 
			 * } catch (Exception RTE) { logger.info(RTE); getScreenshot(Driver,
			 * "RTEIssue"); String Error = RTE.getMessage(); setResultData("Result", 25, 4,
			 * "FAIL"); setResultData("Result", 25, 5, Error); }
			 */

			try {
				LOC LocJob = new LOC();
				LocJob.locLocal();

				setResultData("Result", 1, 4, "PASS");

			} catch (Exception LOC) {
				logger.info(LOC);
				getScreenshot(Driver, "LOCIssue");
				String Error = LOC.getMessage();
				setResultData("Result", 1, 4, "FAIL");
				setResultData("Result", 1, 5, Error);
			}

			try {
				SD SDJob = new SD();
				SDJob.sdSameDay();
				setResultData("Result", 2, 4, "PASS");

			} catch (Exception SD) {
				logger.info(SD);
				getScreenshot(Driver, "SDIssue");
				String Error = SD.getMessage();
				setResultData("Result", 2, 4, "FAIL");
				setResultData("Result", 2, 5, Error);

			}

			try {
				H3P H3PJob = new H3P();
				H3PJob.h3P();
				setResultData("Result", 10, 4, "PASS");

			} catch (Exception H3P) {
				logger.info(H3P);
				getScreenshot(Driver, "LOCIssue");
				String Error = H3P.getMessage();
				setResultData("Result", 10, 4, "FAIL");
				setResultData("Result", 10, 5, Error);

				// --Connect Login

			}

			try {
				T3PLAST T3PLASTJob = new T3PLAST();
				T3PLASTJob.t3PLAST();
				setResultData("Result", 12, 4, "PASS");

			} catch (Exception T3PLAST) {
				logger.info(T3PLAST);
				getScreenshot(Driver, "LOCIssue");
				String Error = T3PLAST.getMessage();
				setResultData("Result", 12, 4, "FAIL");
				setResultData("Result", 12, 5, Error);

			}

			try {
				CPU CPUJob = new CPU();
				CPUJob.FedExCpu();
				setResultData("Result", 13, 4, "PASS");

			} catch (Exception CPU) {
				logger.info(CPU);
				getScreenshot(Driver, "CPUIssue");
				String Error = CPU.getMessage();
				setResultData("Result", 13, 4, "FAIL");
				setResultData("Result", 13, 5, Error);

			}

			// --Connect LogOut

			// --Moved to tab
			tabs = new ArrayList<String>(Driver.getWindowHandles());
			Driver.switchTo().window(tabs.get(1));

			Driver.close();

			Driver.switchTo().window(tabs.get(0));
			// Switch back to original browser (first window)logger.info("Moved to main
			// window");
			Thread.sleep(2000);
		} catch (Exception ee) {
			logger.error(ee);
			logger.info("Line number is: " + ee.getStackTrace()[0].getLineNumber() + ee.getClass());
			getScreenshot(Driver, "OrderCreation&ProcessConnectIssue");
			System.out.println("Issue in Order Creation and processing from connect");
			logger.info("Issue in Order Creation and processing from connect");
			// --Connect LogOut
			// ConnectlogOut();

		}

		// --NetAgentLogin
		Login();

		msg.append("\n" + "===== Order Processing Test : End ====" + "\n");

	}

}
