package whscheduler.controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import whscheduler.dao.ATRequestDAO;
import whscheduler.dao.ATWebhostingDAO;
import whscheduler.dao.ATBillDAO;
import whscheduler.util.ApplicationConstants;
import whscheduler.model.ATBillModel;
import whscheduler.model.ATRequestModel;
import whscheduler.dao.CommonDAO;
import whscheduler.model.ATWebhostingModel;
import whscheduler.service.WebhostingRenewService;

public class WebhostingReleaseController {
	private static Logger logger = Logger.getLogger(WebhostingReleaseController.class.getName());
	
	public void webhostingRelease() {
		Session hbSession = null;
		CommonDAO comDAO = null;
		ATWebhostingModel hosting = null;
		ATRequestModel atRequest = null;
		WebhostingRenewService whService = null;		
		ATBillModel atDueBill = null;
		ArrayList<ATBillModel> dueBills = null;
		try {
			whService = new WebhostingRenewService();
			comDAO = new CommonDAO();
			hbSession = CommonDAO.buildSessionFactory().openSession();
			dueBills = new ATBillDAO().getOverdueBills(ApplicationConstants.WEBHOSTING_GRACE_PERIOD);
			if(dueBills != null && dueBills.size() > 0) {
				for(ATBillModel bill : dueBills) {
					hbSession.beginTransaction();
					try {
						
						hosting = (ATWebhostingModel)comDAO.getModelByPrimary(ATWebhostingModel.class,bill.getBlEntityID(), hbSession);
						if(hosting!=null && hosting.getIsPrivileged()==0 && hosting.getExpiryDate() < System.currentTimeMillis()
								&&((hosting.getLatestStatus() == ApplicationConstants.WEBHOSTING_BLOCKED_TYPEID)
								    || (hosting.getActivationDate()==0 && hosting.getCurrentStatus()!=ApplicationConstants.WEBHOSTING_RELEASE_TYPEID && hosting.getLatestStatus()!=ApplicationConstants.WEBHOSTING_RELEASE_TYPEID))) {
							long atRequestId = comDAO.getIdFromVbSequenceByPrimary("at_req");
							if(atRequestId > 0) {
								atRequest = whService.getAtRequestDataInstance(hosting, atRequestId, ApplicationConstants.WEBHOSTING_RELEASE_TYPEID, 0, hbSession);
								atRequest.setArSourceRequestID(""+bill.getReqID());
								atRequest.setArRootRequestID(bill.getReqID());
								atRequest.setArCompletionStatus(true);
								atRequest.setArDescription("Webhosting Released by System");
								comDAO.saveModel(atRequest, hbSession);
								
								hosting.setLastModificationTime(System.currentTimeMillis());
								if(hosting.getActivationDate()>0) {
									hosting.setCpanelWrittingStatus(7);	//DELETE cpanel account				
								}
								
								hosting.setDeleted(true);	
								hosting.setLatestStatus(ApplicationConstants.WEBHOSTING_RELEASE_TYPEID);
								hosting.setCurrentStatus(ApplicationConstants.WEBHOSTING_RELEASE_TYPEID);
								
								atDueBill = (ATBillModel) comDAO.getModelByPrimary(ATBillModel.class, bill.getID(), hbSession);
								atDueBill.setIsDeleted(1);
								hbSession.getTransaction().commit(); 
								
							}
						}else {
							atDueBill = (ATBillModel) comDAO.getModelByPrimary(ATBillModel.class, bill.getID(), hbSession);
							atDueBill.setIsDeleted(1);
							hbSession.getTransaction().commit();
						}					
				    }catch(Exception ex) {
						logger.fatal("Exception",ex);
						if(hbSession.getTransaction() != null)
							hbSession.beginTransaction().rollback();
				    }
				}
			}
			else {
				logger.debug("No pending bill found for releasing webhosting.");
			}
			
		}
		catch(Exception ex) {
			logger.fatal("Exception",ex);
			if(hbSession.getTransaction() != null)
				hbSession.beginTransaction().rollback();
		}finally {
			hbSession.close();
		}
	}
	
	public void webhostingVoidBill4Upgrade() {
		Session hbSession = null;
		CommonDAO comDAO = null;
		ATWebhostingModel hosting = null;
		ATRequestModel atRequest = null;
		WebhostingRenewService whService = null;		
		ATBillModel atDueBill = null;
		ArrayList<ATBillModel> dueBills = null;
		try {
			whService = new WebhostingRenewService();
			comDAO = new CommonDAO();
			hbSession = CommonDAO.buildSessionFactory().openSession();
			dueBills = new ATBillDAO().getOverdueBills4Upgrade(ApplicationConstants.WEBHOSTING_GRACE_PERIOD);
			if(dueBills != null && dueBills.size() > 0) {
				for(ATBillModel bill : dueBills) {
					hbSession.beginTransaction();
					try {
						
						hosting = (ATWebhostingModel)comDAO.getModelByPrimary(ATWebhostingModel.class,bill.getBlEntityID(), hbSession);
						if(hosting!=null && hosting.getIsPrivileged()==0 && hosting.getExpiryDate() < System.currentTimeMillis()
								&&((hosting.getLatestStatus() == ApplicationConstants.WEBHOSTING_BLOCKED_TYPEID)
								    || (hosting.getActivationDate()==0 && hosting.getCurrentStatus()!=ApplicationConstants.WEBHOSTING_RELEASE_TYPEID && hosting.getLatestStatus()!=ApplicationConstants.WEBHOSTING_RELEASE_TYPEID))) {
							long atRequestId = comDAO.getIdFromVbSequenceByPrimary("at_req");
							if(atRequestId > 0) {
								atRequest = whService.getAtRequestDataInstance(hosting, atRequestId, ApplicationConstants.WEBHOSTING_RELEASE_TYPEID, 0, hbSession);
								atRequest.setArSourceRequestID(""+bill.getReqID());
								atRequest.setArRootRequestID(bill.getReqID());
								atRequest.setArCompletionStatus(true);
								atRequest.setArDescription("Webhosting Released by System");
								comDAO.saveModel(atRequest, hbSession);
								
								
								if(hosting.getActivationDate()>0) {
									hosting.setCpanelWrittingStatus(7);					
								}
								
								hosting.setDeleted(true);	
								hosting.setLastModificationTime(System.currentTimeMillis());
								hosting.setLatestStatus(ApplicationConstants.WEBHOSTING_RELEASE_TYPEID);
								hosting.setCurrentStatus(ApplicationConstants.WEBHOSTING_RELEASE_TYPEID);
								
								atDueBill = (ATBillModel) comDAO.getModelByPrimary(ATBillModel.class, bill.getID(), hbSession);
								atDueBill.setIsDeleted(1);
								hbSession.getTransaction().commit(); 
								
							}
						}else if(hosting!=null && hosting.getIsPrivileged()==0 && hosting.getExpiryDate() > System.currentTimeMillis()) {
							long atRequestId = comDAO.getIdFromVbSequenceByPrimary("at_req");
							if(atRequestId > 0) {
								atRequest = whService.getAtRequestDataInstance(hosting, atRequestId, ApplicationConstants.WEBHOSTING_RELEASE_TYPEID, 0, hbSession);
								atRequest.setArSourceRequestID(""+bill.getReqID());
								atRequest.setArRootRequestID(bill.getReqID());
								atRequest.setArCompletionStatus(true);
								atRequest.setArDescription("Package upgrade canceled by System");
								comDAO.saveModel(atRequest, hbSession);
								
								hosting.setLastModificationTime(System.currentTimeMillis());
								hosting.setLatestStatus(hosting.getCurrentStatus());								
								
								atDueBill = (ATBillModel) comDAO.getModelByPrimary(ATBillModel.class, bill.getID(), hbSession);
								atDueBill.setIsDeleted(1);
								hbSession.getTransaction().commit(); 
						     }
						}
						else {
							atDueBill = (ATBillModel) comDAO.getModelByPrimary(ATBillModel.class, bill.getID(), hbSession);
							atDueBill.setIsDeleted(1);
							hbSession.getTransaction().commit();
						}					
				    }catch(Exception ex) {
						logger.fatal("Exception",ex);
						if(hbSession.getTransaction() != null)
							hbSession.beginTransaction().rollback();
				    }
				}
			}
			else {
				logger.debug("No pending bill found for releasing webhosting.");
			}
			
		}
		catch(Exception ex) {
			logger.fatal("Exception",ex);
			if(hbSession.getTransaction() != null)
				hbSession.beginTransaction().rollback();
		}finally {
			hbSession.close();
		}
	}
	
	public void ongoingWebhostingReject() {
		ATRequestModel atRequest = null,requestForInsert = null;
		CommonDAO comDAO = null;
		ATBillDAO billDao = null;
		Session hbSession = null;
		ATBillModel bill = null;
		ATRequestDAO requestDao = null;
		ATWebhostingModel processingHosting = null;
		WebhostingRenewService whService = null;
		
		ArrayList<ATWebhostingModel> list = new ATWebhostingDAO().getOngoingExpiredWebhosting(ApplicationConstants.WEBHOSTING_GRACE_PERIOD);
		if(list != null && list.size() > 0) {
			try {
				comDAO = new CommonDAO();
				billDao = new ATBillDAO();
				requestDao = new ATRequestDAO();
				whService = new WebhostingRenewService();
				hbSession = CommonDAO.buildSessionFactory().openSession();
				for(ATWebhostingModel hosting : list) {
					hbSession.beginTransaction();
					try {
						bill = billDao.getSingleBillByCondition("blEntityID="+hosting.getID(), hbSession);
						if(bill == null) {
							atRequest = requestDao.getSingleRequestByCondition(" arEntityID="+hosting.getID(), hbSession);
							if(atRequest != null) {
								long atRequestId = comDAO.getIdFromVbSequenceByPrimary("at_req");
								if(atRequestId > 0) {
									requestForInsert = whService.getAtRequestDataInstance(hosting, atRequestId, ApplicationConstants.WEBHOSTING_RELEASE_TYPEID,0,hbSession);
									requestForInsert.setArSourceRequestID(""+atRequest.getArID());
									requestForInsert.setArRootRequestID(atRequest.getArID());
									requestForInsert.setArDescription("Webhosting Rejected by System");
									comDAO.saveModel(requestForInsert, hbSession);
									
									processingHosting = (ATWebhostingModel) comDAO.getModelByPrimary(ATWebhostingModel.class,hosting.getID(), hbSession);
									processingHosting.setLastModificationTime(System.currentTimeMillis());
									processingHosting.setLatestStatus(ApplicationConstants.WEBHOSTING_RELEASE_TYPEID);
									processingHosting.setCurrentStatus(ApplicationConstants.WEBHOSTING_RELEASE_TYPEID);		
									hbSession.getTransaction().commit(); 
									
								}
							}
						}
					}
					catch(Exception ex) {
						logger.fatal("Exception",ex);
						if(hbSession.getTransaction() != null)
							hbSession.beginTransaction().rollback();
					}
				}
			}
			catch(Exception ex) {
				logger.fatal("Exception",ex);
			}finally {
				hbSession.close();
				logger.debug("----DB closed----");
			}
			
		}
	}

}
