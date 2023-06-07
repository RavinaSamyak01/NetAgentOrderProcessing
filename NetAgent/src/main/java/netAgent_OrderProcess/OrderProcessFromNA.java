package netAgent_OrderProcess;

import netAgent_BasePackage.BaseInit;

import org.testng.annotations.Test;

import connect_OrderProcessNonSPL.*;
import connect_OrderProcessSPL.*;


public class OrderProcessFromNA extends BaseInit {

	@Test
	public void naOrderProcess() throws Exception {

		String result = null;
		try {
			// --Create orders from connect
			OrderCreationConnect();
			result = "PASS";
		} catch (Exception e) {
			logger.info(e);
			result = "FAIL";
			getScreenshot(Driver, "OrderCreationIssue");

		}

		if (result.equalsIgnoreCase("PASS")) {
			// --NetAgentLogin
			Login();

			// --LOC Process
			try {
				LOC locp = new LOC();
				locp.locProcessing();
			} catch (Exception e) {
				logger.info(e);
				getScreenshot(Driver, "LOCProcessIssue");
			}

			// --SD Process
			try {
				SD sdp = new SD();
				sdp.sdProcessing();
			} catch (Exception e) {
				logger.info(e);
				getScreenshot(Driver, "SDProcessIssue");
			}

			// --PA Process
			try {
				PA pap = new PA();
				pap.paProcessing();
			} catch (Exception e) {
				logger.info(e);
				getScreenshot(Driver, "PAProcessIssue");
			}

			// --P3P Process
			try {
				P3P p3pp = new P3P();
				p3pp.p3pProcessing();
			} catch (Exception e) {
				logger.info(e);
				getScreenshot(Driver, "P3PProcessIssue");
			}

			// --SDC Process
			try {
				SDC sdcp = new SDC();
				sdcp.sdcProcessing();
			} catch (Exception e) {
				logger.info(e);
				getScreenshot(Driver, "SDCProcessIssue");
			}

			// --AIR Process
			try {
				AIR airp = new AIR();
				airp.airProcessing();
			} catch (Exception e) {
				logger.info(e);
				getScreenshot(Driver, "AIRProcessIssue");
			}

			// --DRV Process
			try {
				DRV DRVp = new DRV();
				DRVp.drvProcessing();
			} catch (Exception e) {
				logger.info(e);
				getScreenshot(Driver, "DRVProcessIssue");
			}

			// --FRA Process
			try {
				FRA FRAp = new FRA();
				FRAp.fRAProcessing();
			} catch (Exception e) {
				logger.info(e);
				getScreenshot(Driver, "FRAProcessIssue");
			}

			// --FRG Process
			try {
				FRG FRGp = new FRG();
				FRGp.frgProcessing();
			} catch (Exception e) {
				logger.info(e);
				getScreenshot(Driver, "FRGProcessIssue");
			}

			// --3PLAST Process
			try {
				T3PLAST T3PLASTp = new T3PLAST();
				T3PLASTp.t3PlastProcessing();
			} catch (Exception e) {
				logger.info(e);
				getScreenshot(Driver, "FRGProcessIssue");
			}

			// --CPU Process
			try {
				CPU CPUp = new CPU();
				CPUp.cpuProcessing();
			} catch (Exception e) {
				logger.info(e);
				getScreenshot(Driver, "CPUProcessIssue");
			}

			// --H3P Process
			try {
				H3P H3Pp = new H3P();
				H3Pp.h3pProcessing();
			} catch (Exception e) {
				logger.info(e);
				getScreenshot(Driver, "FRGProcessIssue");
			}

			// --D3P Process
			try {
				D3P D3Pp = new D3P();
				D3Pp.d3pProcessing();
			} catch (Exception e) {
				logger.info(e);
				getScreenshot(Driver, "FRGProcessIssue");
			}

		} else {
			logger.info("can't run order process from NetAgent because order creation issue from connect");
			msg.append("can't run order process from NetAgent because order creation issue from connect" + "\n");
		}

	}

	public void OrderCreationConnect() throws Exception {

		logOut();

		// --Connect Login

		Connectlogin();

		// -- Activate Account
		ActivateAccount();

		try {
			LOC locp = new LOC();
			locp.locLocal();
		} catch (Exception e) {
			logger.info(e);
			getScreenshot(Driver, "LOCCreateIssue");
		}

		// --SD Create
		try {
			SD sdp = new SD();
			sdp.sdSameDay();
		} catch (Exception e) {
			logger.info(e);
			getScreenshot(Driver, "SDCreateIssue");
		}

		// --PA Create
		try {
			PA pap = new PA();
			pap.paPriorityAir();
		} catch (Exception e) {
			logger.info(e);
			getScreenshot(Driver, "PACreateIssue");
		}

		// --P3P Create
		try {
			P3P p3pp = new P3P();
			p3pp.p3pservice();
		} catch (Exception e) {
			logger.info(e);
			getScreenshot(Driver, "P3PCreateIssue");
		}

		// --SDC Create
		try {
			SDC sdcp = new SDC();
			sdcp.sdcSameDayCity();
		} catch (Exception e) {
			logger.info(e);
			getScreenshot(Driver, "SDCCreateIssue");
		}

		// --AIR Create
		try {
			AIR airp = new AIR();
			airp.airService();
		} catch (Exception e) {
			logger.info(e);
			getScreenshot(Driver, "AIRCreateIssue");
		}

		// --DRV Create
		try {
			DRV DRVp = new DRV();
			DRVp.drvDriver();
		} catch (Exception e) {
			logger.info(e);
			getScreenshot(Driver, "DRVCreateIssue");
		}

		// --FRA Create
		try {
			FRA FRAp = new FRA();
			FRAp.fraFreight();
		} catch (Exception e) {
			logger.info(e);
			getScreenshot(Driver, "FRACreateIssue");
		}

		// --FRG Create
		try {
			FRG FRGp = new FRG();
			FRGp.frgFreight();
		} catch (Exception e) {
			logger.info(e);
			getScreenshot(Driver, "FRGCreateIssue");
		}

		// --3PLAST Create
		try {
			T3PLAST T3PLASTp = new T3PLAST();
			T3PLASTp.t3PLAST();
		} catch (Exception e) {
			logger.info(e);
			getScreenshot(Driver, "FRGCreateIssue");
		}

		// --CPU Create
		try {
			CPU CPUp = new CPU();
			CPUp.FedExCpu();
		} catch (Exception e) {
			logger.info(e);
			getScreenshot(Driver, "CPUCreateIssue");
		}

		// --H3P Create
		try {
			H3P H3Pp = new H3P();
			H3Pp.h3P();
		} catch (Exception e) {
			logger.info(e);
			getScreenshot(Driver, "FRGCreateIssue");
		}

		// --D3P Create
		try {
			D3P D3Pp = new D3P();
			D3Pp.d3P();
		} catch (Exception e) {
			logger.info(e);
			getScreenshot(Driver, "FRGCreateIssue");
		}

		// --Connect LogOut
		ConnectlogOut();

	}

}
