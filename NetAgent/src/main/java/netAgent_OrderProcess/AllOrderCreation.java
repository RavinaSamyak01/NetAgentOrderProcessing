
package netAgent_OrderProcess;

import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Test;

import connect_OrderProcessNonSPL.AIR;
import connect_OrderProcessNonSPL.DRV;
import connect_OrderProcessNonSPL.FRA;
import connect_OrderProcessNonSPL.FRG;
import connect_OrderProcessNonSPL.LOC;
import connect_OrderProcessNonSPL.P3P;
import connect_OrderProcessNonSPL.PA;
import connect_OrderProcessNonSPL.RTE;
import connect_OrderProcessNonSPL.SD;
import connect_OrderProcessNonSPL.SDC;
import connect_OrderProcessSPL.CPU;
import connect_OrderProcessSPL.D3P;
import connect_OrderProcessSPL.H3P;
import connect_OrderProcessSPL.T3PLAST;
import netAgent_BasePackage.BaseInit;
import netAgent_BasePackage.SendEmail;

public class AllOrderCreation extends BaseInit {
	String File = null;

	@Test
	public void allOrderCreation() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) Driver;
		msg.append("\n\n" + "===== NA Order Processing Test : Start ====" + "\n");
		String Env = storage.getProperty("Env");

		if (Env.equalsIgnoreCase("Test") || Env.equalsIgnoreCase("STG")) {

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
					SD sdJob = new SD();
					sdJob.sdSameDay();

					setResultData("Result", 2, 4, "PASS");

				} catch (Exception LOC) {
					logger.info(LOC);
					getScreenshot(Driver, "LOCIssue");
					String Error = LOC.getMessage();
					setResultData("Result", 2, 4, "FAIL");
					setResultData("Result", 2, 5, Error);
				}

				try {
					P3P P3PJob = new P3P();
					P3PJob.p3pservice();
					setResultData("Result", 3, 4, "PASS");

				} catch (Exception P3P) {
					logger.info(P3P);
					getScreenshot(Driver, "P3PIssue");
					String Error = P3P.getMessage();
					setResultData("Result", 3, 4, "FAIL");
					setResultData("Result", 3, 5, Error);

				}

				try {
					PA PAJob = new PA();
					PAJob.paPriorityAir();
					setResultData("Result", 4, 4, "PASS");

				} catch (Exception PA) {
					logger.info(PA);
					getScreenshot(Driver, "PAIssue");
					String Error = PA.getMessage();
					setResultData("Result", 4, 4, "FAIL");
					setResultData("Result", 4, 5, Error);

				}

				try {
					DRV DRVJob = new DRV();
					DRVJob.drvDriver();
					setResultData("Result", 5, 4, "PASS");

				} catch (Exception DRV) {
					logger.info(DRV);
					getScreenshot(Driver, "DRVIssue");
					String Error = DRV.getMessage();
					setResultData("Result", 5, 4, "FAIL");
					setResultData("Result", 5, 5, Error);

				}

				try {
					AIR AIRJob = new AIR();
					AIRJob.airService();
					setResultData("Result", 6, 4, "PASS");

				} catch (Exception AIR) {
					logger.info(AIR);
					getScreenshot(Driver, "AIRIssue");
					String Error = AIR.getMessage();
					setResultData("Result", 6, 4, "FAIL");
					setResultData("Result", 6, 5, Error);

				}

				try {
					SDC SDCJob = new SDC();
					SDCJob.sdcSameDayCity();
					setResultData("Result", 7, 4, "PASS");

				} catch (Exception SDC) {
					logger.info(SDC);
					String Error = SDC.getMessage();
					setResultData("Result", 7, 4, "FAIL");
					setResultData("Result", 7, 5, Error);
					getScreenshot(Driver, "SDCIssue");

				}

				try {
					FRA FRAJob = new FRA();
					FRAJob.fraFreight();
					setResultData("Result", 8, 4, "PASS");

				} catch (Exception FRA) {
					logger.info(FRA);
					getScreenshot(Driver, "FRAIssue");
					String Error = FRA.getMessage();
					setResultData("Result", 8, 4, "FAIL");
					setResultData("Result", 8, 5, Error);

				}

				try {
					FRG FRGJob = new FRG();
					FRGJob.frgFreight();
					setResultData("Result", 9, 4, "PASS");

				} catch (Exception FRG) {
					logger.info(FRG);
					getScreenshot(Driver, "FRGIssue");
					String Error = FRG.getMessage();
					setResultData("Result", 9, 4, "FAIL");
					setResultData("Result", 9, 5, Error);

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
					D3P D3PJob = new D3P();
					D3PJob.d3P();
					setResultData("Result", 11, 4, "PASS");

				} catch (Exception D3P) {
					logger.info(D3P);
					getScreenshot(Driver, "D3PIssue");
					String Error = D3P.getMessage();
					setResultData("Result", 11, 4, "FAIL");
					setResultData("Result", 11, 5, Error);

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

				try {
					RTE rteJob = new RTE();
					rteJob.rtejobcreationprocess();

				} catch (Exception RTE) {
					logger.info(RTE);
					getScreenshot(Driver, "RTEIssue");
					String Error = RTE.getMessage();
					setResultData("Result", 25, 4, "FAIL");
					setResultData("Result", 25, 5, Error);
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

		}

		else if (Env.equalsIgnoreCase("PROD")) {

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

				// --SD JOB

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

				// -- H3P job

				try {
					H3P H3PJob = new H3P();
					H3PJob.h3P();
					setResultData("Result", 10, 4, "PASS");

				} catch (Exception H3P) {
					logger.info(H3P);
					getScreenshot(Driver, "H3PIssue");
					String Error = H3P.getMessage();
					setResultData("Result", 10, 4, "FAIL");
					setResultData("Result", 10, 5, Error);
				}

				// -- CPU

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

				// -- T3PLAST
				try {
					T3PLAST T3PLASTJob = new T3PLAST();
					T3PLASTJob.t3PLAST();
					setResultData("Result", 12, 4, "PASS");

				} catch (Exception T3PLAST) {
					logger.info(T3PLAST);
					getScreenshot(Driver, "T3PLAST");
					String Error = T3PLAST.getMessage();
					setResultData("Result", 12, 4, "FAIL");
					setResultData("Result", 12, 5, Error);

				}

				// -- RTE create by loC merge and then process

				try {
					RTE rteJob = new RTE();
					rteJob.rtejobcreationprocess();

				} catch (Exception RTE) {
					logger.info(RTE);
					getScreenshot(Driver, "RTEIssue");
					String Error = RTE.getMessage();
					setResultData("Result", 25, 4, "FAIL");
					setResultData("Result", 25, 5, Error);
				}

			} catch (Exception ee) {
				logger.error(ee);
				logger.info("Line number is: " + ee.getStackTrace()[0].getLineNumber() + ee.getClass());
				getScreenshot(Driver, "OrderCreation&ProcessConnectIssue");
				System.out.println("Issue in Order Creation and processing from connect");
				logger.info("Issue in Order Creation and processing from connect");

			}

		}

//		// --NetAgentLogin
//		Login();

		msg.append("\n" + "===== Order Processing Test : End ====" + "\n");

	}

}
