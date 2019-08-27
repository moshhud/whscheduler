package whscheduler.controller;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import whscheduler.dao.NotificationDAO;
import whscheduler.model.NotificationModel;
import whscheduler.service.MailAuthenticationService;
import whscheduler.service.MailSendingService;
import whscheduler.util.ApplicationConstants;

public class MailSendingController {
	
	private static Logger logger = Logger.getLogger(MailSendingController.class.getName());
	
	private MailAuthenticationService auth = null;
	private Properties props = null;
	
		
	public void openSMTPConnection(){
		this.props = new Properties();
		this.auth = new MailAuthenticationService();
		this.props.setProperty("mail.smtp.submitter", auth.getPasswordAuthentication().getUserName());
		this.props.setProperty("mail.smtp.auth", "true");
		this.props.setProperty("mail.smtp.host", "mail.btcl.com.bd");
		this.props.setProperty("mail.smtp.port", "25");
		this.props.put("mail.smtp.starttls.enable", "true");   
		this.props.setProperty("mail.transport.protocol", "smtp");
		this.props.setProperty("mail.smtp.from",  ApplicationConstants.BOUNCE_MAIL_ADDRESS);
	} 

	public void sendPendingMails() {
		List<String> noEmails = null,hasEmails = null;
		int count = 0;
		String domainWithoutEmailsMailContent = "";
		try {
			ArrayList<NotificationModel> notificationList = new NotificationDAO().getPendingNotifications();
			if(notificationList != null && notificationList.size() > 0) {
				logger.debug("Invoice mail pending :"+notificationList.size());
				noEmails = new ArrayList<>();
				hasEmails = new ArrayList<>();
				openSMTPConnection();
				ExecutorService threadExecutors = Executors.newFixedThreadPool(10);
				for(NotificationModel mailModel : notificationList) {
					try {
						if(mailModel.getAttachment() != null && mailModel.getAttachment().length() > 0) {
							count ++;
							if(this.props == null || this.auth == null) {
								openSMTPConnection();
							}
							if(mailModel.getAditionalInfo() != null && mailModel.getAditionalInfo().length() > 0
									&& !mailModel.getAditionalInfo().equalsIgnoreCase("skip")) {
								if(mailModel.getSentTo().equalsIgnoreCase(ApplicationConstants.BTCL_ADMIN_MAIL)) {
									noEmails.add(mailModel.getAditionalInfo());
									mailModel.setSentTo(ApplicationConstants.BTCL_ADMIN_MAIL);
								}else {
									hasEmails.add(mailModel.getAditionalInfo());
								}
							}
							MailSendingService mail = new MailSendingService(mailModel,this.props,this.auth);
							try{
								threadExecutors.execute(mail);
								Thread.sleep(1500);
							}catch (Exception e) {
								logger.fatal("Exception",e);
								System.exit(0);
							}
						}
					}catch(Exception ex) {
						logger.fatal("Exception",ex);
					}
				}
				threadExecutors.shutdown();
				while(threadExecutors.isTerminated() == false){
					
				}
				logger.debug("Allocated memory for Mail Invoice: " + (Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory()));
				logger.debug("Total mail sent:"+count);
				try{
					logger.debug("Invoice Mail Completed");
					Thread.interrupted();
					if(noEmails.size() > 0 || hasEmails.size() > 0) {
						String fileText = "";
						if(noEmails.size() > 0) {
							fileText += "\r\nNo email id was found for bellow domains. \r\n";
							
							domainWithoutEmailsMailContent = "Dear BTCL Authority,<br/>Renew invoice generated from scheduler. Please check these "+
									"and take neccessary step. Renew invoice which dont have email ids those domain has been mailed to <b>"+ApplicationConstants.BTCL_ADMIN_MAIL+"</b>"+
									"<br/>A txt file also attached for download.<br/><br/>";
							domainWithoutEmailsMailContent +="<table width='600' cellpadding='1' cellspacing='1' style='margin-bottom:50px'><tr><td>Below domains have no email address</td></tr>";
							for(String dmn : noEmails) {
								domainWithoutEmailsMailContent +="<tr><td style='padding:2px;border:1px solid black'><b>"+dmn+"</td></tr>";
								fileText +=dmn+"\r\n";
							}
							domainWithoutEmailsMailContent +="</table>";
						}
						if(hasEmails.size() > 0) {
							fileText += "\r\nemail id was found for bellow domains. \r\n";
							domainWithoutEmailsMailContent +="<table width='600' cellpadding='1' cellspacing='1'><tr><td>Below domains have email address</td></tr>";
							for(String dmn : hasEmails) {
								domainWithoutEmailsMailContent +="<tr><td style='padding:2px;border:1px solid black'><b>"+dmn+"</td></tr>";
								fileText +=dmn+"\r\n";
							}
							domainWithoutEmailsMailContent +="</table>";
						}
						
						NotificationModel notify = new NotificationModel();
						try {
							String fileName = "domain_without_emails_"+System.currentTimeMillis()+".txt";
							FileWriter fw = new FileWriter("invoices/"+fileName);
							fw.write(fileText);
							fw.close();
							notify.setAttachment("invoices/"+fileName);
						}catch(Exception ex) {
							logger.fatal("Exception",ex);
						}
						notify.setAditionalInfo("skip");
						notify.setRequestGeneratedFrom("automation jar");
						notify.setSentFrom(ApplicationConstants.BTCL_INFO_MAIL);
						notify.setSentTo(ApplicationConstants.BTCL_AUTHORITY_MAIL);
						notify.setSentBody(domainWithoutEmailsMailContent);
						notify.setSentType("email");
						notify.setSentSubject("Renew Invoice generated from Scheduler: Domain List");
						notify.setStatus("pending");
						new NotificationDAO().insertNotification(notify);
					}
				}catch (Exception e) {
					logger.fatal("Exception",e);
				}finally {
					System.exit(0);
				}
			}else {
				System.out.println("No email found");
				System.exit(0);
			}
		}catch(Exception ex) {
			logger.fatal("Exception",ex);
			System.exit(0);
		}
	}
}
