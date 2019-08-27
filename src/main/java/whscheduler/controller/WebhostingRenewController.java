package whscheduler.controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import whscheduler.service.PDFBillGenerateService;
import whscheduler.util.ApplicationConstants;
import whscheduler.model.ATClientModel;
import whscheduler.dao.ATBillDAO;
import whscheduler.dao.ATWebhostingDAO;
import whscheduler.dao.CommonDAO;
import whscheduler.model.ATBillModel;
import whscheduler.model.ATRequestModel;
import whscheduler.model.ATWebhostingModel;
import whscheduler.model.ATWebhostingRequestModel;
import whscheduler.model.WebHostingDiscountModel;
import whscheduler.model.WebHostingPackageInfoModel;
import whscheduler.service.ATBillService;
import whscheduler.service.NotificationService;
import whscheduler.service.WebhostingRenewService;

public class WebhostingRenewController {
	private static Logger logger = Logger.getLogger(WebhostingRenewController.class.getName());
	
	public void webhostingRenewProcessStart() {
		long atRequestId = 0,atWebhostingRequestId = 0,atBillId = 0;
		Session hbSession = null;
		CommonDAO comDAO = null;
		ATRequestModel atRequest = null;
		ATWebhostingModel processingHosting = null;
		ATWebhostingRequestModel atWebhostingRequest = null;
		WebhostingRenewService whService = null;
		ATBillModel atBillModel = null;
		ATBillModel bill = null;
		ATBillDAO billDao = null;
		ATClientModel clientModel = null;
		boolean renewProcessSuccess = false;
		NotificationService notify = null;
		ArrayList<WebHostingDiscountModel> discountList = null;
		WebHostingPackageInfoModel packageDTO =null;
		ATWebhostingDAO whDAO = null;
		whDAO = new ATWebhostingDAO();
		
		ArrayList<ATWebhostingModel>  hostingList = whDAO.getExpiredWebhostingForRenew(ApplicationConstants.RENEW_INVOCE_BEFORE_HOW_MANY_DAYS);
		logger.debug("Total Domain: "+hostingList.size());	
		
		if(hostingList != null && hostingList.size() > 0) {
			try {
				comDAO = new CommonDAO();
				billDao = new ATBillDAO();
				notify = new NotificationService();
				whService = new WebhostingRenewService();				
				hbSession = CommonDAO.buildSessionFactory().openSession();
				
				for(ATWebhostingModel hosting : hostingList) {
					logger.debug("Domain Name: "+hosting.getDomain());
					long time = System.currentTimeMillis();
					hbSession.beginTransaction();
					renewProcessSuccess = false;
					
					try {
						if(hosting != null) {
							packageDTO = whDAO.getPackageInfoDTO(" webPackID="+hosting.getPackageID(), hbSession);							
							double price = packageDTO.getPrice();
							int packageType = packageDTO.getPackageType();
							//packageType =1 ; renew for yearly package type client
							processingHosting = (ATWebhostingModel) comDAO.getModelByPrimary(ATWebhostingModel.class,hosting.getID(), hbSession);
							if(processingHosting !=null && packageType==1) {								
								bill = billDao.getSingleBillByCondition("blEntityID="+hosting.getID()+" and blEntityTypeID=201 and blRequestType in(0,2) AND blType IN(11) and blPaymentID=0 and blIsDeleted=0", hbSession);
								if(bill!=null) {
									if(hbSession.getTransaction() != null) {
										hbSession.getTransaction().rollback();
									}
									logger.debug("Skipping hosting for the Domain: "+hosting.getDomain());
									continue;
								}
								
								clientModel = (ATClientModel) comDAO.getModelByPrimary(ATClientModel.class, processingHosting.getClientID(), hbSession);
								hbSession.evict(clientModel);
								
								processingHosting.setLastModificationTime(System.currentTimeMillis());
								processingHosting.setLatestStatus(ApplicationConstants.WEBHOSTING_RENEW_TYPEID);
								
																
								int slot = 1;
								double totalCost = slot*price*1.0;
								//discount
								double discount = 0;
								discountList = new  ATWebhostingDAO().getWebHostingDiscountList();
								if(discountList!=null && discountList.size()>0) {
									for(WebHostingDiscountModel discountDTO : discountList) {
										discount+=new ATWebhostingDAO().getDiscount(discountDTO,slot,price,packageType);
									}
								}
								
								atRequestId = comDAO.getIdFromVbSequenceByPrimary("at_req");
								if(atRequestId > 0) {
									atRequest = whService.getAtRequestDataInstance(hosting, atRequestId, ApplicationConstants.WEBHOSTING_RENEW_TYPEID,30,hbSession);
									if(atRequest != null) {
										atRequest.setArDescription("Web hosting Renewal Invoice Generated by System");
										atRequest.setArRequestedToAccountID(hosting.getClientID());
										comDAO.saveModel(atRequest, hbSession);
										
										atWebhostingRequestId = comDAO.getIdFromVbSequenceByPrimary("at_webhosting_req");
										atWebhostingRequest = whService.getWebhostingRequestDataInstance(hosting, atWebhostingRequestId, atRequestId);
										if(atWebhostingRequest != null) {
											comDAO.saveModel(atWebhostingRequest, hbSession);
											atBillId = comDAO.getIdFromVbSequenceByPrimary("at_bill");
											if(atBillId > 0) {
												atBillModel = whService.getATBillDataInstance(hosting, atBillId, atRequestId,totalCost,discount);
												if(atBillModel != null) {
													atBillModel.setDescription(new ATBillService().getAtBillDetailsHTML(atBillModel, hosting.getDomain()));
													
													comDAO.saveModel(atBillModel, hbSession);
													hbSession.getTransaction().commit();
													renewProcessSuccess = true;
												}
											}
										}
									}
									
									
									
								}
							
							}
							if(renewProcessSuccess) {
								new PDFBillGenerateService().generatePDF(atBillModel, hosting, clientModel);
								if(ApplicationConstants.WEBHOSTING_RENEW_NOTIFICATION_CUSTOMER || ApplicationConstants.WEBHOSTING_RENEW_NOTIFICATION_ADMIN) {
									try {
										notify.notificationRequest(hosting, clientModel,atBillId);
									}catch(Exception ex) {
										logger.fatal("--Error in notification save--",ex);
										if(hbSession.getTransaction() != null) {
											hbSession.getTransaction().rollback();
										}
									}
								}
							}else {
								logger.fatal("--Skipped Current execution for untracked error--");
								if(hbSession.getTransaction() != null) {
									hbSession.getTransaction().rollback();
								}
							}
						}
						
					}
					catch(Exception ex) {
						logger.fatal("Web hosting Renew Process Start->Exception",ex);
						logger.fatal("--Exception found for a single  renew. clearing all cached queries--");
						if(hbSession.getTransaction() != null) {
							hbSession.getTransaction().rollback();
						}
					}
					
					logger.debug("Processing time for a hosting: "+(System.currentTimeMillis() - time));
				}
				
			}catch(Exception ex) {
				logger.fatal("Web hosting Renew Process Start->Exception",ex);
			}finally {
				if(hbSession != null) {
					hbSession.close();
				}
			}
			
		}
	}

}