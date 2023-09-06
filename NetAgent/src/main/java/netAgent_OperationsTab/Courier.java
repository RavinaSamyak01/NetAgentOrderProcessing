package netAgent_OperationsTab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class Courier extends BaseInit {
	@Test
	public void courier() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 30);

		logger.info("=======Courier Test Start=======");
		// msg.append("=======Courier Test Start=======" + "\n\n");

		try {
			// --Go to Courier screen
			wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Operations")));
			Driver.findElement(By.partialLinkText("Operations")).click();
			logger.info("Clicked on Operations");

			Driver.findElement(By.linkText("Courier")).click();
			logger.info("Clicked on Courier");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class='ajax-loadernew']")));
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("panel-body")));

			String Courier = Driver.findElement(By.id("txtCourierId")).getAttribute("value");
			System.out.println(Courier);
			logger.info("CourierID is==" + Courier);
			msg.append("CourierID is==" + Courier + "\n\n");
			setResultData("Result", 8, 3, Courier);

			if (!Courier.equals(Agent)) {
				System.out.println("Status : Agent Id is wrong.");
				logger.info("Status : Agent Id is wrong.==FAIL");
				msg.append("Status : Agent Id is wrong.==FAIL" + "\n\n");

			} else {
				System.out.println("Status : Agent Id is right.");
				logger.info("Status : Agent Id is right.==PASS");
				msg.append("Status : Agent Id is right.==PASS" + "\n\n");
			}

			// Check all fields
			// Courier
			// 1. Id

			WebElement AgentId = Driver.findElement(By.id("txtCourierId"));

			System.out.println(AgentId);
			if (AgentId.isDisplayed()) {
				logger.info("Status : Agent Id is displayed.");
				if (AgentId.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Agent Id is Enabled.==FAIL");
					msg.append("Status : Agent Id is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : Agent Id is Disabled.==PASS");

				}
			} else {
				logger.info("Status : Agent Id is not displayed.==FAIL");
				msg.append("Status : Agent Id is not displayed.==FAIL" + "/n");

			}

			// 2. Agent Code
			WebElement AgentCode = Driver.findElement(By.id("txtAgentcode"));

			if (AgentCode.isDisplayed()) {
				logger.info("Status : AgentCode is displayed.");
				if (AgentCode.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : AgentCode is Enabled.==FAIL");
					msg.append("Status : AgentCode is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : AgentCode is Disabled.==PASS");

				}
			} else {
				logger.info("Status : AgentCode is not displayed.==FAIL");
				msg.append("Status : AgentCode is not displayed.==FAIL" + "/n");

			}

			// 3. Vendor
			WebElement Vendor = Driver.findElement(By.id("txtVendorid"));

			if (Vendor.isDisplayed()) {
				logger.info("Status : Vendor is displayed.");
				if (Vendor.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Vendor is Enabled.==FAIL");
					msg.append("Status : Vendor is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : Vendor is Disabled.==PASS");

				}
			} else {
				logger.info("Status : Vendor is not displayed.==FAIL");
				msg.append("Status : Vendor is not displayed.==FAIL" + "/n");

			}

			// 4. Name
			WebElement Name = Driver.findElement(By.id("txtname"));

			if (Name.isDisplayed()) {
				logger.info("Status : Name is displayed.");
				if (Name.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Name is Enabled.==FAIL");
					msg.append("Status : Name is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : Name is Disabled.==PASS");

				}
			} else {
				logger.info("Status : Vendor is not displayed.==FAIL");
				msg.append("Status : Vendor is not displayed.==FAIL" + "/n");

			}

			// 5. Valid From
			WebElement ValidFrom = Driver.findElement(By.id("txtValidfrom"));

			if (ValidFrom.isDisplayed()) {
				logger.info("Status : ValidFrom is displayed.");
				if (Vendor.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : ValidFrom is Enabled.==FAIL");
					msg.append("Status : ValidFrom is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : ValidFrom is Disabled.==PASS");

				}
			} else {
				logger.info("Status : ValidFrom is not displayed.==FAIL");
				msg.append("Status : ValidFrom is not displayed.==FAIL" + "/n");

			}
			// 6. Valid To
			WebElement ValidTo = Driver.findElement(By.id("txtValidto"));

			if (ValidTo.isDisplayed()) {
				logger.info("Status : ValidTo is displayed.");
				if (ValidTo.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : ValidTo is Enabled.==FAIL");
					msg.append("Status : ValidTo is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : ValidTo is Disabled.==PASS");

				}
			} else {
				logger.info("Status : ValidTo is not displayed.==FAIL");
				msg.append("Status : ValidTo is not displayed.==FAIL" + "/n");

			}

			// 7. Country
			WebElement Country = Driver.findElement(By.id("txtCountry"));

			if (Country.isDisplayed()) {
				logger.info("Status : Country is displayed.");
				if (Country.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Country is Enabled.==FAIL");
					msg.append("Status : Country is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : Country is Disabled.==PASS");

				}
			} else {
				logger.info("Status : Country is not displayed.==FAIL");
				msg.append("Status : Country is not displayed.==FAIL" + "/n");

			}

			// 8. Zip
			WebElement Zip = Driver.findElement(By.id("txtZipCode"));
			if (Zip.isDisplayed()) {
				logger.info("Status : Zip is displayed.");
				if (Zip.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Zip is Enabled.==FAIL");
					msg.append("Status : Zip is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : Zip is Disabled.==PASS");

				}
			} else {
				logger.info("Status : Zip is not displayed.==FAIL");
				msg.append("Status : Zip is not displayed.==FAIL" + "/n");

			}
			// 9. City
			WebElement City = Driver.findElement(By.id("txtCity"));

			if (City.isDisplayed()) {
				logger.info("Status : City is displayed.");
				if (City.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : City is Enabled.==FAIL");
					msg.append("Status : City is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : City is Disabled.==PASS");

				}
			} else {
				logger.info("Status : City is not displayed.==FAIL");
				msg.append("Status : City is not displayed.==FAIL" + "/n");

			}

			// 10. State
			WebElement State = Driver.findElement(By.id("txtState"));

			if (State.isDisplayed()) {
				logger.info("Status : State is displayed.");
				if (State.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : State is Enabled.==FAIL");
					msg.append("Status : State is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : State is Disabled.==PASS");

				}
			} else {
				logger.info("Status : State is not displayed.==FAIL");
				msg.append("Status : State is not displayed.==FAIL" + "/n");

			}

			// 11. Address 1
			WebElement add1 = Driver.findElement(By.id("txtAddrLine1"));

			if (add1.isDisplayed()) {
				logger.info("Status : add1 is displayed.");
				if (add1.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : add1 is Enabled.==FAIL");
					msg.append("Status : add1 is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : add1 is Disabled.==PASS");

				}
			} else {
				logger.info("Status : add1 is not displayed.==FAIL");
				msg.append("Status : add1 is not displayed.==FAIL" + "/n");

			}
			// 12. Dept
			WebElement Dept = Driver.findElement(By.id("txtAddrLine2"));

			if (Dept.isDisplayed()) {
				logger.info("Status : Dept is displayed.");
				if (Dept.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Dept is Enabled.==FAIL");
					msg.append("Status : Dept is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : Dept is Disabled.==PASS");

				}
			} else {
				logger.info("Status : Dept is not displayed.==FAIL");
				msg.append("Status : Dept is not displayed.==FAIL" + "/n");

			}

			// 13. Phone
			WebElement Phone = Driver.findElement(By.id("txtPhoneNum"));

			if (Phone.isDisplayed()) {
				logger.info("Status : Phone is displayed.");
				if (Phone.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Phone is Enabled.==FAIL");
					msg.append("Status : Phone is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : Phone is Disabled.==PASS");

				}
			} else {
				logger.info("Status : Phone is not displayed.==FAIL");
				msg.append("Status : Phone is not displayed.==FAIL" + "/n");

			}

			WebElement PhoneExt = Driver.findElement(By.xpath(".//*[@name='txtExtensionNum']"));

			if (PhoneExt.isDisplayed()) {
				logger.info("Status : PhoneExt is displayed.");
				if (PhoneExt.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : PhoneExt is Enabled.==FAIL");
					msg.append("Status : PhoneExt is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : PhoneExt is Disabled.==PASS");

				}
			} else {
				logger.info("Status : PhoneExt is not displayed.==FAIL");
				msg.append("Status : PhoneExt is not displayed.==FAIL" + "/n");

			}

			// 14. Fax
			WebElement Fax = Driver.findElement(By.id("txtFaxum"));

			if (Fax.isDisplayed()) {
				logger.info("Status : Fax is displayed.");
				if (Fax.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Fax is Enabled.==FAIL");
					msg.append("Status : Fax is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : Fax is Disabled.==PASS");

				}
			} else {
				logger.info("Status : Fax is not displayed.==FAIL");
				msg.append("Status : Fax is not displayed.==FAIL" + "/n");

			}

			// 15. Email
			WebElement Email = Driver.findElement(By.id("txtEmailAddr"));

			if (Email.isDisplayed()) {
				logger.info("Status : Email is displayed.");
				if (Email.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Email is Enabled.==FAIL");
					msg.append("Status : Email is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : Email is Disabled.==PASS");

				}
			} else {
				logger.info("Status : Email is not displayed.==FAIL");
				msg.append("Status : Email is not displayed.==FAIL" + "/n");

			}

			// 16. AfterFname
			WebElement AfterFname = Driver.findElement(By.id("txtCourierAfterhrName"));

			if (AfterFname.isDisplayed()) {
				logger.info("Status : AfterFname is displayed.");
				if (AfterFname.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : AfterFname is Enabled.==FAIL");
					msg.append("Status : AfterFname is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : AfterFname is Disabled.==PASS");

				}
			} else {
				logger.info("Status : AfterFname is not displayed.==FAIL");
				msg.append("Status : AfterFname is not displayed.==FAIL" + "/n");

			}

			// 17. AfterLname
			WebElement AfterLname = Driver.findElement(By.id("txtCourierAfterhrLName"));

			if (AfterLname.isDisplayed()) {
				logger.info("Status : AfterLname is displayed.");
				if (AfterLname.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : AfterLname is Enabled.==FAIL");
					msg.append("Status : AfterLname is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : AfterLname is Disabled.==PASS");

				}
			} else {
				logger.info("Status : AfterLname is not displayed.==FAIL");
				msg.append("Status : AfterLname is not displayed.==FAIL" + "/n");

			}

			// 18. AfterPhone
			WebElement AfterPhone = Driver.findElement(By.id("txtCourierAfterhrPhone"));

			if (AfterPhone.isDisplayed()) {
				logger.info("Status : AfterPhone is displayed.");
				if (AfterPhone.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : AfterPhone is Enabled.==FAIL");
					msg.append("Status : AfterPhone is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : AfterPhone is Disabled.==PASS");

				}
			} else {
				logger.info("Status : AfterPhone is not displayed.==FAIL");
				msg.append("Status : AfterPhone is not displayed.==FAIL" + "/n");

			}

			WebElement AfterPhoneExt = Driver.findElement(By.id("txtCourierAfterhrExt"));

			if (AfterPhoneExt.isDisplayed()) {
				logger.info("Status : AfterPhoneExt is displayed.");
				if (AfterPhoneExt.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : AfterPhoneExt is Enabled.==FAIL");
					msg.append("Status : AfterPhoneExt is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : AfterPhoneExt is Disabled.==PASS");

				}
			} else {
				logger.info("Status : AfterPhoneExt is not displayed.==FAIL");
				msg.append("Status : AfterPhoneExt is not displayed.==FAIL" + "/n");

			}

			// 19. EmrgFname
			WebElement EmrgFname = Driver.findElement(By.id("txtEmrgFname"));

			if (EmrgFname.isDisplayed()) {
				logger.info("Status : EmrgFname is displayed.");
				if (EmrgFname.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : EmrgFname is Enabled.==FAIL");
					msg.append("Status : EmrgFname is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : EmrgFname is Disabled.==PASS");

				}
			} else {
				logger.info("Status : EmrgFname is not displayed.==FAIL");
				msg.append("Status : EmrgFname is not displayed.==FAIL" + "/n");

			}
			// 20. EmrgLname
			WebElement EmrgLname = Driver.findElement(By.id("txtEmrgLname"));

			if (EmrgLname.isDisplayed()) {
				logger.info("Status : EmrgLname is displayed.");
				if (EmrgLname.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : EmrgLname is Enabled.==FAIL");
					msg.append("Status : EmrgLname is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : EmrgLname is Disabled.==PASS");

				}
			} else {
				logger.info("Status : EmrgLname is not displayed.==FAIL");
				msg.append("Status : EmrgLname is not displayed.==FAIL" + "/n");

			}

			// 21. EmrgPhone
			WebElement EmrgPhone = Driver.findElement(By.xpath(".//*[@name='txtCOurierEmergPhone']"));
			if (EmrgPhone.isDisplayed()) {
				logger.info("Status : EmrgPhone is displayed.");
				if (EmrgPhone.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : EmrgPhone is Enabled.==FAIL");
					msg.append("Status : EmrgPhone is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : EmrgPhone is Disabled.==PASS");

				}
			} else {
				logger.info("Status : EmrgPhone is not displayed.==FAIL");
				msg.append("Status : EmrgPhone is not displayed.==FAIL" + "/n");

			}

			WebElement EmrgPhoneExt = Driver.findElement(By.xpath(".//*[@name='txtCourierEmergExt']"));

			if (EmrgPhoneExt.isDisplayed()) {
				logger.info("Status : EmrgPhoneExt is displayed.");
				if (EmrgPhoneExt.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : EmrgPhoneExt is Enabled.==FAIL");
					msg.append("Status : EmrgPhoneExt is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : EmrgPhoneExt is Disabled.==PASS");

				}
			} else {
				logger.info("Status : EmrgPhoneExt is not displayed.==FAIL");
				msg.append("Status : EmrgPhoneExt is not displayed.==FAIL" + "/n");

			}

			// 22. APAcct
			WebElement APAcct1 = Driver.findElement(By.xpath(".//*[@name='txtApAcctNo']"));

			if (APAcct1.isDisplayed()) {
				logger.info("Status : APAcct1 is displayed.");
				if (APAcct1.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : APAcct1 is Enabled.==FAIL");
					msg.append("Status : APAcct1 is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : APAcct1 is Disabled.==PASS");

				}
			} else {
				logger.info("Status : APAcct1 is not displayed.==FAIL");
				msg.append("Status : APAcct1 is not displayed.==FAIL" + "/n");

			}

			WebElement APAcct2 = Driver.findElement(By.xpath(".//*[@name='txtApDeptNo']"));

			if (APAcct2.isDisplayed()) {
				logger.info("Status : APAcct2 is displayed.");
				if (APAcct2.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : APAcct2 is Enabled.==FAIL");
					msg.append("Status : APAcct2 is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : APAcct2 is Disabled.==PASS");

				}
			} else {
				logger.info("Status : APAcct2 is not displayed.==FAIL");
				msg.append("Status : APAcct2 is not displayed.==FAIL" + "/n");

			}

			// 23. CashAcct
			WebElement CashAcct1 = Driver.findElement(By.xpath(".//*[@name='txtCashAcctNo']"));

			if (CashAcct1.isDisplayed()) {
				logger.info("Status : CashAcct1 is displayed.");
				if (CashAcct1.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : CashAcct1 is Enabled.==FAIL");
					msg.append("Status : CashAcct1 is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : CashAcct1 is Disabled.==PASS");

				}
			} else {
				logger.info("Status : CashAcct1 is not displayed.==FAIL");
				msg.append("Status : CashAcct1 is not displayed.==FAIL" + "/n");

			}
			WebElement CashAcct2 = Driver.findElement(By.xpath(".//*[@name='txtCashDeptNo']"));

			if (CashAcct2.isDisplayed()) {
				logger.info("Status : CashAcct2 is displayed.");
				if (CashAcct2.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : CashAcct2 is Enabled.==FAIL");
					msg.append("Status : CashAcct2 is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : CashAcct2 is Disabled.==PASS");

				}
			} else {
				logger.info("Status : CashAcct2 is not displayed.==FAIL");
				msg.append("Status : CashAcct2 is not displayed.==FAIL" + "/n");

			}

			// 24. ExpAcct
			WebElement ExpAcct1 = Driver.findElement(By.xpath(".//*[@name='txtExpAcctNo']"));

			if (ExpAcct1.isDisplayed()) {
				logger.info("Status : ExpAcct1 is displayed.");
				if (ExpAcct1.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : ExpAcct1 is Enabled.==FAIL");
					msg.append("Status : ExpAcct1 is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : ExpAcct1 is Disabled.==PASS");

				}
			} else {
				logger.info("Status : ExpAcct1 is not displayed.==FAIL");
				msg.append("Status : ExpAcct1 is not displayed.==FAIL" + "/n");

			}

			WebElement ExpAcct2 = Driver.findElement(By.xpath(".//*[@name='txtExpDeptNo']"));

			if (ExpAcct2.isDisplayed()) {
				logger.info("Status : ExpAcct2 is displayed.");
				if (ExpAcct2.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : ExpAcct2 is Enabled.==FAIL");
					msg.append("Status : ExpAcct2 is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : ExpAcct2 is Disabled.==PASS");

				}
			} else {
				logger.info("Status : ExpAcct2 is not displayed.==FAIL");
				msg.append("Status : ExpAcct2 is not displayed.==FAIL" + "/n");

			}

			// 25. Type
			WebElement Type = Driver.findElement(By.xpath(".//*[@name='txtCourierTypename']"));
			if (Type.isDisplayed()) {
				logger.info("Status : Type is displayed.");
				if (Type.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Type is Enabled.==FAIL");
					msg.append("Status : Type is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : Type is Disabled.==PASS");

				}
			} else {
				logger.info("Status : Type is not displayed.==FAIL");
				msg.append("Status : Type is not displayed.==FAIL" + "/n");

			}

			// 26. BillingPOC
			WebElement BillingPOC = Driver.findElement(By.id("txtBilligDoc"));

			if (BillingPOC.isDisplayed()) {
				logger.info("Status : BillingPOC is displayed.");
				if (BillingPOC.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : BillingPOC is Enabled.==FAIL");
					msg.append("Status : BillingPOC is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : BillingPOC is Disabled.==PASS");

				}
			} else {
				logger.info("Status : BillingPOC is not displayed.==FAIL");
				msg.append("Status : BillingPOC is not displayed.==FAIL" + "/n");

			}

			// 27. OperationsPOC
			WebElement OperationsPOC = Driver.findElement(By.id("txtOperationsPOC"));

			if (OperationsPOC.isDisplayed()) {
				logger.info("Status : OperationsPOC is displayed.");
				if (OperationsPOC.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : OperationsPOC is Enabled.==FAIL");
					msg.append("Status : OperationsPOC is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : OperationsPOC is Disabled.==PASS");

				}
			} else {
				logger.info("Status : OperationsPOC is not displayed.==FAIL");
				msg.append("Status : OperationsPOC is not displayed.==FAIL" + "/n");

			}

			// 28. ManagementPOC
			WebElement MangPOC = Driver.findElement(By.id("txtManagementPOC"));

			if (MangPOC.isDisplayed()) {
				logger.info("Status : MangPOC is displayed.");
				if (MangPOC.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : MangPOC is Enabled.==FAIL");
					msg.append("Status : MangPOC is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : MangPOC is Disabled.==PASS");

				}
			} else {
				logger.info("Status : MangPOC is not displayed.==FAIL");
				msg.append("Status : MangPOC is not displayed.==FAIL" + "/n");

			}

			// 29. Compliance POC
			WebElement CompPOC = Driver.findElement(By.id("txtCompliancePOC"));

			if (CompPOC.isDisplayed()) {
				logger.info("Status : CompPOC is displayed.");
				if (CompPOC.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : CompPOC is Enabled.==FAIL");
					msg.append("Status : CompPOC is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : CompPOC is Disabled.==PASS");

				}
			} else {
				logger.info("Status : CompPOC is not displayed.==FAIL");
				msg.append("Status : CompPOC is not displayed.==FAIL" + "/n");

			}

			// 30. Driver Required
			WebElement DrvReq = Driver.findElement(By.id("chkIsDrvRequired"));
			if (DrvReq.isDisplayed()) {
				logger.info("Status : DrvReq is displayed.");
				if (DrvReq.isEnabled()) {
					logger.info("Status : RecSerAgr is Enabled.==FAIL");
					msg.append("Status : RecSerAgr is Enabled.==FAIL" + "/n");

				} else {
					logger.info("Status : RecSerAgr is Disabled.==PASS");
				}
			} else {
				logger.info("Status : DrvReq is not displayed.==FAIL");
				msg.append("Status : DrvReq is not displayed.==FAIL" + "/n");

			}

			// Miscellaneous Information
			// 1. AlertType
			WebElement AlertType = Driver.findElement(By.id("txtDeptStop"));

			if (AlertType.isDisplayed()) {
				logger.info("Status : AlertType is displayed.");
				if (AlertType.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : AlertType is Enabled.==FAIL");

				} else {
					logger.info("Status : AlertType is Disabled.==PASS");

				}
			} else {
				logger.info("Status : AlertType is not displayed.==FAIL");
			}
			// 2. ReceivedOn1
			WebElement ReceivedOn1 = Driver.findElement(By.id("txtRcvdSrvAgrDttm"));

			if (ReceivedOn1.isDisplayed()) {
				logger.info("Status : ReceivedOn1 is displayed.");
				if (ReceivedOn1.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : ReceivedOn1 is Enabled.==FAIL");
				} else {
					logger.info("Status : ReceivedOn1 is Disabled.==PASS");

				}
			} else {
				logger.info("Status : ReceivedOn1 is not displayed.==FAIL");
			}

			// 3. ReceivedOn2
			WebElement ReceivedOn2 = Driver.findElement(By.id("txtRcvdW9Dttm"));

			if (ReceivedOn2.isDisplayed()) {
				logger.info("Status : ReceivedOn2 is displayed.");
				if (ReceivedOn2.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : ReceivedOn2 is Enabled.==FAIL");
				} else {
					logger.info("Status : ReceivedOn2 is Disabled.==PASS");

				}
			} else {
				logger.info("Status : ReceivedOn2 is not displayed.==FAIL");
			}
			// 4. GeneralLiability
			WebElement GenLiab = Driver.findElement(By.id("txtInsrGenExpDttm"));

			if (GenLiab.isDisplayed()) {
				logger.info("Status : GenLiab is displayed.");
				if (GenLiab.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : GenLiab is Enabled.==FAIL");
				} else {
					logger.info("Status : GenLiab is Disabled.==PASS");

				}
			} else {
				logger.info("Status : GenLiab is not displayed.==FAIL");
			}

			// 5. Auto
			WebElement Auto = Driver.findElement(By.id("txtInsrAutoExpDttm"));
			if (Auto.isDisplayed()) {
				logger.info("Status : Auto is displayed.");
				if (Auto.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Auto is Enabled.==FAIL");
				} else {
					logger.info("Status : Auto is Disabled.==PASS");

				}
			} else {
				logger.info("Status : Auto is not displayed.==FAIL");
			}

			// 6. WareHouse
			WebElement WareHouse = Driver.findElement(By.id("txtInsrWhsExpDttm"));

			if (WareHouse.isDisplayed()) {
				logger.info("Status : WareHouse is displayed.");
				if (WareHouse.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : WareHouse is Enabled.==FAIL");
				} else {
					logger.info("Status : WareHouse is Disabled.==PASS");

				}
			} else {
				logger.info("Status : WareHouse is not displayed.==FAIL");
			}

			// 7. Cargo
			WebElement Cargo = Driver.findElement(By.id("txtInsrCargExpDttm"));

			if (Cargo.isDisplayed()) {
				logger.info("Status : Cargo is displayed.");
				if (Cargo.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Cargo is Enabled.==FAIL");
				} else {
					logger.info("Status : Cargo is Disabled.==PASS");

				}
			} else {
				logger.info("Status : Cargo is not displayed.==FAIL");
			}

			// 8. Worker Compensation
			WebElement WorkerCo = Driver.findElement(By.id("txtInsrWkCompExpDttm"));

			if (WorkerCo.isDisplayed()) {
				logger.info("Status : WorkerCo is displayed.");
				if (WorkerCo.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : WorkerCo is Enabled.==FAIL");
				} else {
					logger.info("Status : WorkerCo is Disabled.==PASS");

				}
			} else {
				logger.info("Status : WorkerCo is not displayed.==FAIL");
			}

			// 9. ReceivedOn3
			WebElement ReceivedOn3 = Driver.findElement(By.id("txtRcvdTSARosterDttm"));

			if (ReceivedOn3.isDisplayed()) {
				logger.info("Status : ReceivedOn3 is displayed.");
				if (ReceivedOn3.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : ReceivedOn3 is Enabled.==FAIL");
				} else {
					logger.info("Status : ReceivedOn3 is Disabled.==PASS");

				}
			} else {
				logger.info("Status : ReceivedOn3 is not displayed.==FAIL");
			}
			// 10. ReceivedOn4
			WebElement ReceivedOn4 = Driver.findElement(By.id("txtRcvdAcknDttm"));
			if (ReceivedOn4.isDisplayed()) {
				logger.info("Status : ReceivedOn4 is displayed.");
				if (ReceivedOn4.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : ReceivedOn4 is Enabled.==FAIL");
				} else {
					logger.info("Status : ReceivedOn4 is Disabled.==PASS");

				}
			} else {
				logger.info("Status : ReceivedOn4 is not displayed.==FAIL");
			}

			// 11. After Hr Start
			WebElement AftHrStrt = Driver.findElement(By.id("txtAfHrStartTime"));

			if (AftHrStrt.isDisplayed()) {
				logger.info("Status : AftHrStrt is displayed.");
				if (AftHrStrt.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : AftHrStrt is Enabled.==FAIL");
				} else {
					logger.info("Status : AftHrStrt is Disabled.==PASS");

				}
			} else {
				logger.info("Status : AftHrStrt is not displayed.==FAIL");
			}
			// 12. After Hr End
			WebElement AftHrEnd = Driver.findElement(By.id("txtAfHrEndTime"));
			if (AftHrEnd.isDisplayed()) {
				logger.info("Status : AftHrEnd is displayed.");
				if (AftHrEnd.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : AftHrEnd is Enabled.==FAIL");
				} else {
					logger.info("Status : AftHrEnd is Disabled.==PASS");

				}
			} else {
				logger.info("Status : AftHrEnd is not displayed.==FAIL");
			}

			// 13. Received Service Agreement
			WebElement RecSerAgr = Driver.findElement(By.id("chkRcvdSrvAgr"));
			if (RecSerAgr.isDisplayed()) {
				logger.info("Status : RecSerAgr is displayed.");
				if (RecSerAgr.isEnabled()) {
					logger.info("Status : RecSerAgr is Enabled.==FAIL");
				} else {
					logger.info("Status : RecSerAgr is Disabled.==PASS");

				}
			} else {
				logger.info("Status : RecSerAgr is not displayed.==FAIL");
			}

			// 14. Received W9
			WebElement Rec9 = Driver.findElement(By.id("chkRcvdW9"));

			if (Rec9.isDisplayed()) {
				logger.info("Status : Rec9 is displayed.");
				if (Rec9.isEnabled()) {
					logger.info("Status : Rec9 is Enabled.==FAIL");
				} else {
					logger.info("Status : Rec9 is Disabled.==PASS");

				}
			} else {
				logger.info("Status : Rec9 is not displayed.==FAIL");
			}

			// 15. Received Proof of Insurance
			WebElement RecPrfIns = Driver.findElement(By.id("chkRcvdProfInsr"));

			if (RecPrfIns.isDisplayed()) {
				logger.info("Status : RecPrfIns is displayed.");
				if (RecPrfIns.isEnabled()) {
					logger.info("Status : RecPrfIns is Enabled.==FAIL");
				} else {
					logger.info("Status : RecPrfIns is Disabled.==PASS");

				}
			} else {
				logger.info("Status : RecPrfIns is not displayed.==FAIL");
			}

			// 16. Received TSA Roster
			WebElement RecTSARos = Driver.findElement(By.id("chkRcvdTSARoster"));
			if (RecTSARos.isDisplayed()) {
				logger.info("Status : RecTSARos is displayed.");
				if (RecTSARos.isEnabled()) {
					logger.info("Status : RecTSARos is Enabled.==FAIL");
				} else {
					logger.info("Status : RecTSARos is Disabled.==PASS");

				}
			} else {
				logger.info("Status : RecTSARos is not displayed.==FAIL");
			}
			// 17. Received TSA Ackn
			WebElement RecTSAAck = Driver.findElement(By.id("chkRcvdAckn"));
			if (RecTSAAck.isDisplayed()) {
				logger.info("Status : RecTSAAck is displayed.");
				if (RecTSAAck.isEnabled()) {
					logger.info("Status : RecTSAAck is Enabled.==FAIL");
				} else {
					logger.info("Status : RecTSAAck is Disabled.==PASS");

				}
			} else {
				logger.info("Status : RecTSAAck is not displayed.==FAIL");
			}

			// Other Information
			// 1. Note
			WebElement Note = Driver.findElement(By.id("txtOpsnote"));

			if (Note.isDisplayed()) {
				logger.info("Status : Note is displayed.");
				if (Note.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : Note is Enabled.==FAIL");
				} else {
					logger.info("Status : Note is Disabled.==PASS");

				}
			} else {
				logger.info("Status : Note is not displayed.==FAIL");
			}
			// 2. Company Type
			WebElement CompType = Driver.findElement(By.id("txtCompanyTypename"));

			if (CompType.isDisplayed()) {
				logger.info("Status : CompType is displayed.");
				if (CompType.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : CompType is Enabled.==FAIL");
				} else {
					logger.info("Status : CompType is Disabled.==PASS");

				}
			} else {
				logger.info("Status : CompType is not displayed.==FAIL");
			}

			// 3. Ship Lable Key
			WebElement ShipLableKey = Driver.findElement(By.id("txtShipLabelKey"));

			if (ShipLableKey.isDisplayed()) {
				logger.info("Status : ShipLableKey is displayed.");
				if (ShipLableKey.getAttribute("readonly").equalsIgnoreCase("false")) {
					logger.info("Status : ShipLableKey is Enabled.==FAIL");
				} else {
					logger.info("Status : ShipLableKey is Disabled.==PASS");

				}
			} else {
				logger.info("Status : ShipLableKey is not displayed.==FAIL");
			}
			// 4. FDX TSA
			WebElement FDXTSA = Driver.findElement(By.id("chkIsFdxTsa"));

			if (FDXTSA.isDisplayed()) {
				logger.info("Status : FDXTSA is displayed.");
				if (FDXTSA.isEnabled()) {
					logger.info("Status : FDXTSA is Enabled.==FAIL");
				} else {
					logger.info("Status : FDXTSA is Disabled.==PASS");

				}
			} else {
				logger.info("Status : FDXTSA is not displayed.==FAIL");
			}

			// 5. TSA Certified
			WebElement TSACert = Driver.findElement(By.id("chkIsTsaCert"));
			if (TSACert.isDisplayed()) {
				logger.info("Status : TSACert is displayed.");
				if (TSACert.isEnabled()) {
					logger.info("Status : TSACert is Enabled.==FAIL");
				} else {
					logger.info("Status : TSACert is Disabled.==PASS");

				}
			} else {
				logger.info("Status : TSACert is not displayed.==FAIL");
			}
			// 6. HAZ Certified
			WebElement HAZCert = Driver.findElement(By.id("chkIsHAZCertified"));

			if (HAZCert.isDisplayed()) {
				logger.info("Status : HAZCert is displayed.");
				if (HAZCert.isEnabled()) {
					logger.info("Status : HAZCert is Enabled.==FAIL");
				} else {
					logger.info("Status : HAZCert is Disabled.==PASS");

				}
			} else {
				logger.info("Status : HAZCert is not displayed.==FAIL");
			}
			// 7. CCFS Trans Certified
			WebElement CCFS = Driver.findElement(By.id("chkCCSFTransCertified"));

			if (CCFS.isDisplayed()) {
				logger.info("Status : CCFS is displayed.");
				if (CCFS.isEnabled()) {
					logger.info("Status : CCFS is Enabled.==FAIL");
				} else {
					logger.info("Status : CCFS is Disabled.==PASS");

				}
			} else {
				logger.info("Status : CCFS is not displayed.==FAIL");
			}

			// 8. Elevated Risk Certified
			WebElement Elev = Driver.findElement(By.id("chkElevateRiskTrained"));
			if (Elev.isDisplayed()) {
				logger.info("Status : Elev is displayed.");
				if (Elev.isEnabled()) {
					logger.info("Status : Elev is Enabled.==FAIL");
				} else {
					logger.info("Status : Elev is Disabled.==PASS");

				}
			} else {
				logger.info("Status : Elev is not displayed.==FAIL");
			}
			// 9. 3P Courier
			WebElement PC = Driver.findElement(By.id("chkIsCourier3p"));
			if (PC.isDisplayed()) {
				logger.info("Status : PC is displayed.");
				if (PC.isEnabled()) {
					logger.info("Status : PC is Enabled.==FAIL");
				} else {
					logger.info("Status : PC is Disabled.==PASS");

				}
			} else {
				logger.info("Status : PC is not displayed.==FAIL");
			}

			getScreenshot(Driver, "Courier");

			logger.info("Courier Test=PASS");
			msg.append("Courier Test=PASS" + "\n\n");
			setResultData("Result", 8, 5, "PASS");

		} catch (Exception CourierE) {
			logger.error(CourierE);
			getScreenshot(Driver, "Courier_error");
			logger.info("Courier Test=FAIL");
			msg.append("Courier Test=FAIL" + "\n\n");
			String Error = CourierE.getMessage();

			setResultData("Result", 8, 5, "FAIL");
			setResultData("Result", 8, 6, Error);

		}

		logger.info("=======Courier Test End=======");
		// msg.append("=======Courier Test End=======" + "\n\n");
		msg.append("=======Operations Tab Test End=======" + "\n\n");

		// --Refresh the App
		narefreshApp();

	}

}
