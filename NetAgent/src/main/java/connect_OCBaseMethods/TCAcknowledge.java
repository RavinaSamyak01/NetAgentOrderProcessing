package connect_OCBaseMethods;

import java.time.LocalTime;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import netAgent_BasePackage.BaseInit;

public class TCAcknowledge extends BaseInit {

	@Test
	public void tcAcknowledge() throws Exception {
		WebDriverWait wait = new WebDriverWait(Driver, 60);// wait time
		WebDriverWait wait2 = new WebDriverWait(Driver, 10);// wait time
		JavascriptExecutor jse = (JavascriptExecutor) Driver;// scroll,click
		Actions act = new Actions(Driver);

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		// --Get the ServiceID
		String svc = isElementPresent("TLServID_id").getText();
		System.out.println(svc);
		logger.info("ServiceID=" + svc);
		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"lblStages\"][contains(text(),'Acknowledge')]")));

			// --Get StageName
			OrderCreation OC = new OrderCreation();
			OC.getStageName();

			OC.movjobstatus();

			// --Click on TC Ack button
			if (svc.equals("LOC") || svc.equals("P3P") || svc.equals("DRV") || svc.equals("SDC") || svc.equals("FRG")
					|| svc.equals("H3P") || svc.equals("CPU") || svc.equals("D3P") || svc.equals("3PLAST")) {
				try {
					WebElement TCAckBtn = isElementPresent("TLAckBTn2_id");
					act.moveToElement(TCAckBtn).build().perform();
					Thread.sleep(2000);
					wait.until(ExpectedConditions.elementToBeClickable(TCAckBtn));
					jse.executeScript("arguments[0].click();", TCAckBtn);
					logger.info("Clicked on TC Acknowledge button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				} catch (Exception e) {
					WebElement TCAckBtn = isElementPresent("TLAcknBTN_id");
					wait.until(ExpectedConditions.elementToBeClickable(TCAckBtn));
					jse.executeScript("arguments[0].click();", TCAckBtn);
					logger.info("Clicked on TC Acknowledge button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				}
				try {

					wait2.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//*[@id=\"idValidationforMain\"]//ul[@id=\"errorid\"]")));
					String Validmsg = isElementPresent("OCValOnePack_xpath").getText();
					logger.info("Validation message is displayed=" + Validmsg);
					if (Validmsg.contains("Please enter Last Quoted Delivery Time through Edit Order.")) {
						// Recalculate the charges
						// --Go to Edit Job tab
						WebElement EditOrTab = isElementPresent("EOEditOrderTab_id");
						act.moveToElement(EditOrTab).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(EditOrTab));
						act.moveToElement(EditOrTab).click().perform();
						logger.info("Click on Edit Order Tab");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						Thread.sleep(5000);

						// --Get the timeZone
						String tzone = isElementPresent("TLLastPuTimeZone_xpath").getText();
						String rectime = getTimeAsTZone(tzone) + System.currentTimeMillis();

						// --Move to DeliveryDate and Time
						WebElement DelTime = isElementPresent("TLLastQDelTime_id");
						act.moveToElement(DelTime).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(DelTime));
						DelTime.clear();
						DelTime.sendKeys(rectime);
						logger.info("EnteredLast  Quoted Delivery Time");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						String DELDateValue = CuDate();

						// --Move to DeliveryDate and Time
						WebElement DelDate = isElementPresent("TLLastQDelDate_id");
						act.moveToElement(DelDate).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(DelDate));
						DelDate.clear();
						DelDate.sendKeys(DELDateValue);
						DelDate.sendKeys(Keys.TAB);
						logger.info("Entered Last quoted Delivery Date");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// --Click on Save Changes button
						WebElement SaveChanges = isElementPresent("TLSaveChanges_id");
						act.moveToElement(SaveChanges).build().perform();
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
						wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
						jse.executeScript("arguments[0].click();", SaveChanges);
						logger.info("Click on Save Changes button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						Thread.sleep(5000);

						try {
							WebElement Validation = isElementPresent("EOValidation_id");
							wait.until(ExpectedConditions.visibilityOf(Validation));
							String ValMsg = Validation.getText();
							logger.info("Validation==" + ValMsg);

							if (ValMsg.equalsIgnoreCase("Pickup Phone# is Required.")) {
								// --Enter Pickup Phone No
								WebElement PUPhoneNo = isElementPresent("EOPickupPhone_id");
								act.moveToElement(PUPhoneNo).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(PUPhoneNo));
								PUPhoneNo.clear();
								PUPhoneNo.sendKeys("1112221112");
								logger.info("Entered PU Phone");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Click on Save Changes button
								SaveChanges = isElementPresent("TLSaveChanges_id");
								act.moveToElement(SaveChanges).build().perform();
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
								wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
								act.moveToElement(SaveChanges).click().perform();
								logger.info("Click on Save Changes button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
								Thread.sleep(2000);
								try {
									Validation = isElementPresent("EOValidation_id");
									wait.until(ExpectedConditions.visibilityOf(Validation));
									ValMsg = Validation.getText();
									logger.info("Validation==" + ValMsg);

									if (ValMsg.contains(
											"Estimated Delivery time cannot be less than Quoted for Pickup time.")) {
										// --Get the timeZone
										tzone = isElementPresent("TLLastPuTimeZone_xpath").getText();
										rectime = getTimeAsTZone(tzone) + System.currentTimeMillis();

										// --Move to DeliveryDate and Time
										DelTime = isElementPresent("TLLastQDelTime_id");
										act.moveToElement(DelTime).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(DelTime));
										DelTime.clear();
										DelTime.sendKeys(rectime);
										logger.info("EnteredLast  Quoted Delivery Time");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

										DELDateValue = CuDate();

										// --Move to DeliveryDate and Time
										DelDate = isElementPresent("TLLastQDelDate_id");
										act.moveToElement(DelDate).build().perform();
										wait.until(ExpectedConditions.elementToBeClickable(DelDate));
										DelDate.clear();
										DelDate.sendKeys(DELDateValue);
										DelDate.sendKeys(Keys.TAB);
										logger.info("Entered Last quoted Delivery Date");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

										// --Click on Save Changes button
										SaveChanges = isElementPresent("TLSaveChanges_id");
										act.moveToElement(SaveChanges).build().perform();
										wait.until(
												ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
										wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
										jse.executeScript("arguments[0].click();", SaveChanges);
										logger.info("Click on Save Changes button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
										Thread.sleep(5000);

									}
								} catch (Exception ee) {

								}

							} else if (Validmsg
									.contains("Estimated Delivery time cannot be less than Quoted for Pickup time.")) {
								// --Get the timeZone
								tzone = isElementPresent("TLLastPuTimeZone_xpath").getText();
								rectime = getTimeAsTZone(tzone) + System.currentTimeMillis();

								// --Move to DeliveryDate and Time
								DelTime = isElementPresent("TLLastQDelTime_id");
								act.moveToElement(DelTime).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(DelTime));
								DelTime.clear();
								DelTime.sendKeys(rectime);
								logger.info("EnteredLast  Quoted Delivery Time");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								DELDateValue = CuDate();

								// --Move to DeliveryDate and Time
								DelDate = isElementPresent("TLLastQDelDate_id");
								act.moveToElement(DelDate).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(DelDate));
								DelDate.clear();
								DelDate.sendKeys(DELDateValue);
								logger.info("Entered Last quoted Delivery Date");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Click on Save Changes button
								SaveChanges = isElementPresent("TLSaveChanges_id");
								act.moveToElement(SaveChanges).build().perform();
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
								wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
								jse.executeScript("arguments[0].click();", SaveChanges);
								logger.info("Click on Save Changes button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
								Thread.sleep(5000);

							}

						} catch (Exception ee) {
							// --Click on Save Changes button
							SaveChanges = isElementPresent("TLSaveChanges_id");
							act.moveToElement(SaveChanges).build().perform();
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
							wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
							act.moveToElement(SaveChanges).click().perform();
							logger.info("Click on Save Changes button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							Thread.sleep(2000);

							logger.info("Validation message is not displayed for Recalculate");

						}
					} else if (Validmsg.contains("Pickup is in after hours Please enter commodity")
							|| Validmsg.contains("The Commodity field is required.")) {
						// --Go to Edit Job tab
						WebElement EditOrTab = isElementPresent("EOEditOrderTab_id");
						act.moveToElement(EditOrTab).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(EditOrTab));
						act.moveToElement(EditOrTab).click().perform();
						logger.info("Click on Edit Order Tab");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						Thread.sleep(5000);

						// -- COMMODITY_DROPDOWN
						WebElement CommDrop = isElementPresent("EOCommDrop_id");
						act.moveToElement(CommDrop).build().perform();
						Thread.sleep(2000);
						Select comodity_drpdown = new Select(CommDrop);
						comodity_drpdown.selectByIndex(1);
						logger.info("comodity dropdown is selected");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						Thread.sleep(2000);

						// --Enter Commodity
						WebElement Commodity = isElementPresent("EOCommodity_id");
						act.moveToElement(Commodity).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(Commodity));
						Commodity.clear();
						Commodity.sendKeys("Boxes");
						logger.info("Entered Commodity");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// --Click on Save Changes button
						WebElement SaveChanges = isElementPresent("TLSaveChanges_id");
						act.moveToElement(SaveChanges).build().perform();
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
						wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
						jse.executeScript("arguments[0].click();", SaveChanges);
						logger.info("Click on Save Changes button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						Thread.sleep(5000);

						try {
							WebElement Validation = isElementPresent("EOValidation_id");
							wait.until(ExpectedConditions.visibilityOf(Validation));
							String ValMsg = Validation.getText();
							logger.info("Validation==" + ValMsg);

							if (ValMsg.equalsIgnoreCase("Pickup Phone# is Required.")) {
								// --Enter Pickup Phone No
								WebElement PUPhoneNo = isElementPresent("EOPickupPhone_id");
								act.moveToElement(PUPhoneNo).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(PUPhoneNo));
								PUPhoneNo.clear();
								PUPhoneNo.sendKeys("1112221112");
								logger.info("Entered PU Phone");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Click on Save Changes button
								SaveChanges = isElementPresent("TLSaveChanges_id");
								act.moveToElement(SaveChanges).build().perform();
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
								wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
								act.moveToElement(SaveChanges).click().perform();
								logger.info("Click on Save Changes button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
								Thread.sleep(2000);

							} else if (Validmsg
									.contains("Estimated Delivery time cannot be less than Quoted for Pickup time.")) {
								// --Get the timeZone
								String tzone = isElementPresent("TLLastPuTimeZone_xpath").getText();
								String rectime = getTimeAsTZone(tzone) + System.currentTimeMillis();

								// --Move to DeliveryDate and Time
								WebElement DelTime = isElementPresent("TLLastQDelTime_id");
								act.moveToElement(DelTime).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(DelTime));
								DelTime.clear();
								DelTime.sendKeys(rectime);
								logger.info("EnteredLast  Quoted Delivery Time");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								String DELDateValue = CuDate();

								// --Move to DeliveryDate and Time
								WebElement DelDate = isElementPresent("TLLastQDelDate_id");
								act.moveToElement(DelDate).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(DelDate));
								DelDate.clear();
								DelDate.sendKeys(DELDateValue);
								logger.info("Entered Last quoted Delivery Date");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Click on Save Changes button
								SaveChanges = isElementPresent("TLSaveChanges_id");
								act.moveToElement(SaveChanges).build().perform();
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
								wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
								jse.executeScript("arguments[0].click();", SaveChanges);
								logger.info("Click on Save Changes button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
								Thread.sleep(5000);

							}

						} catch (Exception ee) {
							logger.info("Validation message is not displayed for Phone");

						}
					}
					// --Go to job Status Tab
					WebElement JobOverTab = isElementPresent("TLJobStatusTab_id");
					act.moveToElement(JobOverTab).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(JobOverTab));
					// act.moveToElement(JobOverTab).click().perform();
					jse.executeScript("arguments[0].click();", JobOverTab);
					logger.info("Click on Job Overview Tab");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					try {
						WebElement TCAckBtn = isElementPresent("TLAckBTn2_id");
						act.moveToElement(TCAckBtn).build().perform();
						Thread.sleep(2000);
						wait.until(ExpectedConditions.elementToBeClickable(TCAckBtn));
						jse.executeScript("arguments[0].click();", TCAckBtn);
						logger.info("Clicked on TC Acknowledge button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					} catch (Exception e) {
						WebElement TCAckBtn = isElementPresent("TLAcknBTN_id");
						act.moveToElement(TCAckBtn).build().perform();
						Thread.sleep(2000);
						wait.until(ExpectedConditions.elementToBeClickable(TCAckBtn));
						jse.executeScript("arguments[0].click();", TCAckBtn);
						logger.info("Clicked on TC Acknowledge button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					}
				} catch (Exception eLastQDElTime) {

				}

			}

			if (svc.equals("SD") || svc.equals("PA") || svc.equals("AIR") || svc.equals("FRA"))

			{
				WebElement TCAckBtn = isElementPresent("TLAckBTn2_id");
				act.moveToElement(TCAckBtn).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(TCAckBtn));
				jse.executeScript("arguments[0].click();", TCAckBtn);
				logger.info("Clicked on TC Acknowledge button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			}

			try {

				wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath("//*[@id=\"idValidationforMain\"]//ul[@id=\"errorid\"]")));
				String Validmsg = isElementPresent("OCValOnePack_xpath").getText();
				logger.info("Validation message is displayed=" + Validmsg);
				if (Validmsg.contains("Please enter Last Quoted Delivery Time through Edit Order.")) {
					// Recalculate the charges
					// --Go to Edit Job tab
					WebElement EditOrTab = isElementPresent("EOEditOrderTab_id");
					act.moveToElement(EditOrTab).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(EditOrTab));
					act.moveToElement(EditOrTab).click().perform();
					logger.info("Click on Edit Order Tab");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					Thread.sleep(5000);

					// --Get the timeZone
					String tzone = isElementPresent("TLLastPuTimeZone_xpath").getText();
					String rectime = getTimeAsTZone(tzone) + System.currentTimeMillis();

					// --Move to DeliveryDate and Time
					WebElement DelTime = isElementPresent("TLLastQDelTime_id");
					act.moveToElement(DelTime).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(DelTime));
					DelTime.clear();
					DelTime.sendKeys(rectime);
					logger.info("EnteredLast  Quoted Delivery Time");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					String DELDateValue = CuDate();

					// --Move to DeliveryDate and Time
					WebElement DelDate = isElementPresent("TLLastQDelDate_id");
					act.moveToElement(DelDate).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(DelDate));
					DelDate.clear();
					DelDate.sendKeys(DELDateValue);
					DelDate.sendKeys(Keys.TAB);
					logger.info("Entered Last quoted Delivery Date");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					// --Click on Save Changes button
					WebElement SaveChanges = isElementPresent("TLSaveChanges_id");
					act.moveToElement(SaveChanges).build().perform();
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
					wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
					jse.executeScript("arguments[0].click();", SaveChanges);
					logger.info("Click on Save Changes button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					Thread.sleep(5000);

					try {
						WebElement Validation = isElementPresent("EOValidation_id");
						wait.until(ExpectedConditions.visibilityOf(Validation));
						String ValMsg = Validation.getText();
						logger.info("Validation==" + ValMsg);

						if (ValMsg.equalsIgnoreCase("Pickup Phone# is Required.")) {
							// --Enter Pickup Phone No
							WebElement PUPhoneNo = isElementPresent("EOPickupPhone_id");
							act.moveToElement(PUPhoneNo).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(PUPhoneNo));
							PUPhoneNo.clear();
							PUPhoneNo.sendKeys("1112221112");
							logger.info("Entered PU Phone");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							// --Click on Save Changes button
							SaveChanges = isElementPresent("TLSaveChanges_id");
							act.moveToElement(SaveChanges).build().perform();
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
							wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
							act.moveToElement(SaveChanges).click().perform();
							logger.info("Click on Save Changes button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							Thread.sleep(2000);
							try {
								Validation = isElementPresent("EOValidation_id");
								wait.until(ExpectedConditions.visibilityOf(Validation));
								ValMsg = Validation.getText();
								logger.info("Validation==" + ValMsg);

								if (ValMsg.contains(
										"Estimated Delivery time cannot be less than Quoted for Pickup time.")) {
									// --Get the timeZone
									tzone = isElementPresent("TLLastPuTimeZone_xpath").getText();
									rectime = getTimeAsTZone(tzone) + System.currentTimeMillis();

									// --get QPT
									String QPT = isElementPresent("TCAQPT_id").getAttribute("value");
									logger.info("QPT===" + QPT);

									LocalTime t = LocalTime.parse(QPT);
									LocalTime tn = t.plusMinutes(1);
									String Time = tn.toString();
									logger.info("new time==" + Time);

									// --Move to DeliveryDate and Time
									DelTime = isElementPresent("TLLastQDelTime_id");
									act.moveToElement(DelTime).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(DelTime));
									DelTime.clear();
									DelTime.sendKeys(Time);
									logger.info("EnteredLast  Quoted Delivery Time");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									DELDateValue = CuDate();

									// --Move to DeliveryDate and Time
									DelDate = isElementPresent("TLLastQDelDate_id");
									act.moveToElement(DelDate).build().perform();
									wait.until(ExpectedConditions.elementToBeClickable(DelDate));
									DelDate.clear();
									DelDate.sendKeys(DELDateValue);
									DelDate.sendKeys(Keys.TAB);
									logger.info("Entered Last quoted Delivery Date");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									// --Click on Save Changes button
									SaveChanges = isElementPresent("TLSaveChanges_id");
									act.moveToElement(SaveChanges).build().perform();
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
									wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
									jse.executeScript("arguments[0].click();", SaveChanges);
									logger.info("Click on Save Changes button");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
									Thread.sleep(5000);

								}
							} catch (Exception ee) {

							}

						} else if (Validmsg
								.contains("Estimated Delivery time cannot be less than Quoted for Pickup time.")) {
							// --Get the timeZone
							tzone = isElementPresent("TLLastPuTimeZone_xpath").getText();
							rectime = getTimeAsTZone(tzone) + System.currentTimeMillis();

							// --get QPT
							String QPT = isElementPresent("TCAQPT_id").getAttribute("value");
							logger.info("QPT===" + QPT);

							LocalTime t = LocalTime.parse(QPT);
							LocalTime tn = t.plusMinutes(1);
							String Time = tn.toString();
							logger.info("new time==" + Time);

							// --Move to DeliveryDate and Time
							DelTime = isElementPresent("TLLastQDelTime_id");
							act.moveToElement(DelTime).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(DelTime));
							DelTime.clear();
							DelTime.sendKeys(Time);
							logger.info("EnteredLast  Quoted Delivery Time");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							DELDateValue = CuDate();

							// --Move to DeliveryDate and Time
							DelDate = isElementPresent("TLLastQDelDate_id");
							act.moveToElement(DelDate).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(DelDate));
							DelDate.clear();
							DelDate.sendKeys(DELDateValue);
							logger.info("Entered Last quoted Delivery Date");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							// --Click on Save Changes button
							SaveChanges = isElementPresent("TLSaveChanges_id");
							act.moveToElement(SaveChanges).build().perform();
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
							wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
							jse.executeScript("arguments[0].click();", SaveChanges);
							logger.info("Click on Save Changes button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							Thread.sleep(5000);

						}

					} catch (Exception ee) {
						// --Click on Save Changes button
						SaveChanges = isElementPresent("TLSaveChanges_id");
						act.moveToElement(SaveChanges).build().perform();
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
						wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
						act.moveToElement(SaveChanges).click().perform();
						logger.info("Click on Save Changes button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						Thread.sleep(2000);

						logger.info("Validation message is not displayed for Recalculate");

					}
				} else if (Validmsg.contains("Pickup is in after hours Please enter commodity")) {
					// --Go to Edit Job tab
					WebElement EditOrTab = isElementPresent("EOEditOrderTab_id");
					act.moveToElement(EditOrTab).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(EditOrTab));
					act.moveToElement(EditOrTab).click().perform();
					logger.info("Click on Edit Order Tab");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					Thread.sleep(5000);

					// -- COMMODITY_DROPDOWN
					WebElement CommDrop = isElementPresent("EOCommDrop_id");
					act.moveToElement(CommDrop).build().perform();
					Thread.sleep(2000);
					Select comodity_drpdown = new Select(CommDrop);
					comodity_drpdown.selectByIndex(1);
					logger.info("comodity dropdown is selected");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					Thread.sleep(2000);

					// --Enter Commodity
					WebElement Commo = isElementPresent("TLEJCommodity_id");
					act.moveToElement(Commo).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(Commo));
					Commo.clear();
					Commo.sendKeys("BOX");
					logger.info("Enter Commodity");
					// --Click on Save Changes button
					WebElement SaveChanges = isElementPresent("TLSaveChanges_id");
					act.moveToElement(SaveChanges).build().perform();
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
					wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
					jse.executeScript("arguments[0].click();", SaveChanges);
					logger.info("Click on Save Changes button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					Thread.sleep(5000);

					try {
						WebElement Validation = isElementPresent("EOValidation_id");
						wait.until(ExpectedConditions.visibilityOf(Validation));
						String ValMsg = Validation.getText();
						logger.info("Validation==" + ValMsg);

						if (ValMsg.equalsIgnoreCase("Pickup Phone# is Required.")) {
							// --Enter Pickup Phone No
							WebElement PUPhoneNo = isElementPresent("EOPickupPhone_id");
							act.moveToElement(PUPhoneNo).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(PUPhoneNo));
							PUPhoneNo.clear();
							PUPhoneNo.sendKeys("1112221112");
							logger.info("Entered PU Phone");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							// --Click on Save Changes button
							SaveChanges = isElementPresent("TLSaveChanges_id");
							act.moveToElement(SaveChanges).build().perform();
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
							wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
							act.moveToElement(SaveChanges).click().perform();
							logger.info("Click on Save Changes button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							Thread.sleep(2000);

						} else if (Validmsg
								.contains("Estimated Delivery time cannot be less than Quoted for Pickup time.")) {
							// --Get the timeZone
							String tzone = isElementPresent("TLLastPuTimeZone_xpath").getText();
							String rectime = getTimeAsTZone(tzone) + System.currentTimeMillis();

							// --Move to DeliveryDate and Time
							WebElement DelTime = isElementPresent("TLLastQDelTime_id");
							act.moveToElement(DelTime).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(DelTime));
							DelTime.clear();
							DelTime.sendKeys(rectime);
							logger.info("EnteredLast  Quoted Delivery Time");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							String DELDateValue = CuDate();

							// --Move to DeliveryDate and Time
							WebElement DelDate = isElementPresent("TLLastQDelDate_id");
							act.moveToElement(DelDate).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(DelDate));
							DelDate.clear();
							DelDate.sendKeys(DELDateValue);
							logger.info("Entered Last quoted Delivery Date");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							// --Click on Save Changes button
							SaveChanges = isElementPresent("TLSaveChanges_id");
							act.moveToElement(SaveChanges).build().perform();
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));
							wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
							jse.executeScript("arguments[0].click();", SaveChanges);
							logger.info("Click on Save Changes button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							Thread.sleep(5000);

						}

					} catch (Exception ee) {
						logger.info("Validation message is not displayed for Phone");

					}
				}
				// --Go to job Status Tab
				WebElement JobOverTab = isElementPresent("TLJobStatusTab_id");
				act.moveToElement(JobOverTab).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(JobOverTab));
				act.moveToElement(JobOverTab).click().perform();
				logger.info("Click on Job Overview Tab");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				WebElement TCAckBtn = isElementPresent("TLAckBTn2_id");
				act.moveToElement(TCAckBtn).build().perform();
				Thread.sleep(2000);
				wait.until(ExpectedConditions.elementToBeClickable(TCAckBtn));
				jse.executeScript("arguments[0].click();", TCAckBtn);
				logger.info("Clicked on TC Acknowledge button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			} catch (Exception eLastQDElTime) {

			}

			// --Set Pass in TestScenarios

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 2, 5, "PASS");

			} else if (svc.equals("SD")) {
				setData("TC_OrderProcess", 16, 5, "PASS");
			}
		} catch (

		Exception e) {
			logger.error(e);
			getScreenshot(Driver, "TCACKNOWLEDGE" + svc);
			System.out.println("TC ACKNOWLEDGE Not Exist in Flow!!");
			logger.info("TC ACKNOWLEDGE Not Exist in Flow!!");

			// --Set FAIL in TestScenarios

			if (svc.equals("LOC")) {
				setData("TC_OrderProcess", 2, 5, "FAIL");

			} else if (svc.equals("SD")) {
				setData("TC_OrderProcess", 16, 5, "FAIL");
			}
		}

	}
}
