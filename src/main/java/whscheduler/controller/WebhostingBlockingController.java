package whscheduler.controller;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import whscheduler.dao.CommonDAO;
import whscheduler.dao.ATWebhostingDAO;
import whscheduler.model.ATRequestModel;
import whscheduler.model.ATWebhostingModel;
import whscheduler.service.WebhostingRenewService;
import whscheduler.util.ApplicationConstants;

public class WebhostingBlockingController {
	private static Logger logger = Logger.getLogger(WebhostingBlockingController.class.getName());
	
	public void webhostingToBlockStage() {
		logger.debug("Checking webhosting for blocking...");
		//ArrayList<ATWebhostingModel> list = new ATWebhostingDAO().getExpiredWebhostingForBlocking(ApplicationConstants.WEBHOSTING_BLOCKING_AFTER_HOW_MANY_DAYS);
		//if(list != null && list.size() > 0) {
			Session hbSession = null;
			CommonDAO comDAO = null;
			WebhostingRenewService whService = null;
			ATRequestModel atRequest = null;
			ATWebhostingModel hosting = null;
			try {
				comDAO = new CommonDAO();
				hbSession = CommonDAO.buildSessionFactory().openSession();
				ArrayList<ATWebhostingModel> list = new ATWebhostingDAO().getExpiredWebhostingForBlocking(ApplicationConstants.WEBHOSTING_BLOCKING_AFTER_HOW_MANY_DAYS);
				if(list != null && list.size() > 0) {
					for(ATWebhostingModel athosting : list) {
						hbSession.beginTransaction();
						try {
							
							hosting = (ATWebhostingModel)comDAO.getModelByPrimary(ATWebhostingModel.class,athosting.getID(), hbSession);
							logger.debug("WebhostingDTO: "+hosting);
							
							if(hosting != null && hosting.getCurrentStatus()!=ApplicationConstants.WEBHOSTING_BLOCKED_TYPEID) {
	                        	hosting.setLastModificationTime(System.currentTimeMillis());
	    						hosting.setLatestStatus(ApplicationConstants.WEBHOSTING_BLOCKED_TYPEID);
	    						hosting.setCurrentStatus(ApplicationConstants.WEBHOSTING_BLOCKED_TYPEID);
	    						hosting.setCpanelWrittingStatus(2);//suspend cpanel account
	    						
	    						long atRequestId = comDAO.getIdFromVbSequenceByPrimary("at_req");
	    						if(atRequestId > 0) {
	    							atRequest = whService.getAtRequestDataInstance(hosting, atRequestId, ApplicationConstants.WEBHOSTING_BLOCKED_TYPEID, 0, hbSession);
	    							atRequest.setArDescription("Webhosting blocked by System");
	    							comDAO.saveModel(atRequest, hbSession);
	    							hbSession.getTransaction().commit();
	    						}
							}else {
								if(hbSession.getTransaction() != null)
									hbSession.getTransaction().rollback();
							}
									
						}catch(Exception ex) {						
						     logger.fatal("Exception",ex);
							if(hbSession.getTransaction() != null)
								hbSession.getTransaction().rollback();
						}
					}
				}			
				
			}catch(Exception ex) {	
				logger.fatal("Exception",ex);
				if(hbSession.getTransaction() != null)
					hbSession.getTransaction().rollback();
			}finally {
				hbSession.close();
				logger.debug("----DB closed----");
			}
		//}else {
		//	logger.debug("No data found for webhosting for blocking...");
		//}
	}

}
