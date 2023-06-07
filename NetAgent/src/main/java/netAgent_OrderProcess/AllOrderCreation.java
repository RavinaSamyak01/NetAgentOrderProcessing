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

			} catch (Exception LOC) {
				logger.info(LOC);
				getScreenshot(Driver, "LOCIssue");

			}

			try {

				SD SDJob = new SD();
				SDJob.sdSameDay();

			} catch (Exception SD) {
				logger.info(SD);
				getScreenshot(Driver, "SDIssue");

				// --Connect Login Connectlogin();

			}

			try {
				PA PAJob = new PA();
				PAJob.paPriorityAir();

			} catch (Exception PA) {
				logger.info(PA);
				getScreenshot(Driver, "PAIssue");

			}

			try {
				P3P P3PJob = new P3P();
				P3PJob.p3pservice();

			} catch (Exception P3P) {
				logger.info(P3P);
				getScreenshot(Driver, "P3PIssue");

			}

			try {
				SDC SDCJob = new SDC();
				SDCJob.sdcSameDayCity();

			} catch (Exception SDC) {
				logger.info(SDC);
				getScreenshot(Driver, "SDCIssue");

			}

			try {
				DRV DRVJob = new DRV();
				DRVJob.drvDriver();

			} catch (Exception DRV) {
				logger.info(DRV);
				getScreenshot(Driver, "DRVIssue");

			}

			try {
				FRA FRAJob = new FRA();
				FRAJob.fraFreight();

			} catch (Exception FRA) {
				logger.info(FRA);
				getScreenshot(Driver, "FRAIssue");

				// --Connect Login Connectlogin();

			}

			try {
				FRG FRGJob = new FRG();
				FRGJob.frgFreight();

			} catch (Exception FRG) {
				logger.info(FRG);
				getScreenshot(Driver, "FRGIssue");

				// --Connect Login Connectlogin();

			}

			try {
				AIR AIRJob = new AIR();
				AIRJob.airService();

			} catch (Exception AIR) {
				logger.info(AIR);
				getScreenshot(Driver, "AIRIssue");

				// --Connect Login Connectlogin();

			}

			try {
				CPU CPUJob = new CPU();
				CPUJob.FedExCpu();

			} catch (Exception CPU) {
				logger.info(CPU);
				getScreenshot(Driver, "CPUIssue");

				// --Connect Login Connectlogin();

			}

			try {
				D3P D3PJob = new D3P();
				D3PJob.d3P();

			} catch (Exception D3P) {
				logger.info(D3P);
				getScreenshot(Driver, "D3PIssue");

				// --Connect Login Connectlogin();

			}

			try {
				H3P H3PJob = new H3P();
				H3PJob.h3P();

			} catch (Exception H3P) {
				logger.info(H3P);
				getScreenshot(Driver, "LOCIssue");

				// --Connect Login Connectlogin();

			}

			try {
				T3PLAST T3PLASTJob = new T3PLAST();
				T3PLASTJob.t3PLAST();

			} catch (Exception T3PLAST) {
				logger.info(T3PLAST);
				getScreenshot(Driver, "LOCIssue");

				// --Connect Login Connectlogin();

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

			File = null;

		} catch (Exception ee) {
			logger.error(ee);
			logger.info("Line number is: " + ee.getStackTrace()[0].getLineNumber() + ee.getClass());
			getScreenshot(Driver, "OrderCreation&ProcessConnectIssue");
			System.out.println("Issue in Order Creation and processing from connect");
			logger.info("Issue in Order Creation and processing from connect");
			// --Connect LogOut
			// ConnectlogOut();
			File = ".\\Report\\NA_Screenshot\\OrderCreation&ProcessConnectIssue.png, .\\Report\\ExtentReport\\ExtentReportResults.html,.\\Report\\log\\NetAgentLog.html";

		}

		// --NetAgentLogin
		Login();

		String Env = storage.getProperty("Env");
		String subject = "Selenium Automation Script: " + Env + " Order Processing from NetAgent";

		try {
//			/kunjan.modi@samyak.com, pgandhi@samyak.com,parth.doshi@samyak.com

			SendEmail.sendMail(EmailID, subject, msg.toString(), File);
			/*
			 * SendEmail.
			 * sendMail("ravina.prajapati@samyak.com, asharma@samyak.com, parth.doshi@samyak.com"
			 * , subject, msg.toString(), File);
			 */
			// SendEmail.sendMail("ravina.prajapati@samyak.com, asharma@samyak.com
			// ,parth.doshi@samyak.com", subject, msg.toString(), File);

		} catch (Exception ex) {
			logger.error(ex);
		}
	}

}
