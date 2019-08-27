package whscheduler.service;

import java.util.List;

import org.apache.log4j.Logger;

import whscheduler.util.DateTimeHelper;
import whscheduler.dao.NotificationDAO;
import whscheduler.util.ApplicationConstants;
import whscheduler.model.ATClientContactDetailsModel;
import whscheduler.model.ATClientModel;
import whscheduler.model.ATWebhostingModel;
import whscheduler.model.NotificationModel;

public class NotificationService {
	
	Logger logger = Logger.getLogger(NotificationService.class.getName());
	
	public void notificationRequest(ATWebhostingModel hosting, ATClientModel client, long billId) {
		try {
			NotificationModel notify = new NotificationModel();
			notify.setSentType("email");
			notify.setSentFrom(ApplicationConstants.BTCL_INFO_MAIL);
			
			List<ATClientContactDetailsModel> list  = client.getClientDetails().getContactDetails();
			String phoneNo="",email="";
			boolean hasValidContact=false;
			
			for(int i=0;i<list.size();i++) {
				if((list.get(i).getDetailsType()==0 && list.get(i).getEmail().length()>0) || (list.get(i).getDetailsType()==1 && list.get(i).getEmail().length()>0)) {
					if(phoneNo.equals("")) {
						phoneNo=list.get(i).getPhoneNumber();
					}						
					email=list.get(i).getEmail();
					email=email.replace(" ", "");
					if(!email.contains("@")) {
							email="";
					}							
					logger.debug("Email: "+list.get(i).getEmail());
					if(!email.equals("")) {
						if(notify.getSentTo()!=null && notify.getSentTo().length()>0) {
						   if(!notify.getSentTo().equals(email))
							  notify.setSentTo(notify.getSentTo()+";"+email);
						}
						else
							notify.setSentTo(email);
						hasValidContact=true;
					}
				}
				
			}
			
			if(hasValidContact) {
				logger.debug("To Emails: "+notify.getSentTo());	
				notify.setSentSubject("WEB HOSTING WILL EXPIRE. RENEW TO AVOID SERVICE INTERRUPTION");
				notify.setSentBody("Dear webhosting Registrant/User/Client/Agent,<br/>The webhosting for the domain <span style='color:red'><b>"+hosting.getDomain()+"</b>"+
						" will expire at "+DateTimeHelper.getDateTimeFromLong(hosting.getExpiryDate())+"</span><br/>"+
								"You are requested to renew the hosting.<br/><br/> Invoice has been issued for renewal of the web hosting and carefully read the attached renewal invoice.<br/>"+
								"Please make the payment in time to get uninterrupted service.<br/><br/>Domain: "+hosting.getDomain()+"<br/>"+								
								"Invoice ID :<b>"+billId+"</b><br/>No. of requested year(s): 1(one)<br/>"+
								"Note: Do not reply to this email. It is an auto-generated email.<br/>" + 
								"For web hosting related query, support or complain, please email at domain@btcl.com.bd");				
				notify.setStatus("pending");
				notify.setAttachment("invoices/webhosting_renew_invoice_"+billId+".pdf");
				notify.setAditionalInfo(hosting.getDomain());
				notify.setRequestGeneratedFrom("webhosting jar");
				new NotificationDAO().insertNotification(notify);
				
			}
			
			notify = new NotificationModel();
			notify.setSentType("sms");
			notify.setSentFrom("");
			if(phoneNo!=null && phoneNo.length()>0) {
				notify.setSentTo(phoneNo);
				notify.setSentSubject("Webhosting Renew Invoice Auto Generated");
				notify.setSentBody("Dear sir, Renew Invoice Generated");
				notify.setAttachment("");
				notify.setStatus("pending");
				new NotificationDAO().insertNotification(notify);
			}
			
			
		}
		catch(Exception ex) {
			logger.fatal("Exception",ex);
		}
	}
	

}
