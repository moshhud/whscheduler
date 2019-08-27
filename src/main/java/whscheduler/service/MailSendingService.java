package whscheduler.service;

import java.io.File;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import whscheduler.dao.CommonDAO;
import whscheduler.model.NotificationModel;
import whscheduler.util.ApplicationConstants;

public class MailSendingService implements Runnable{

	private static Logger logger = Logger.getLogger(MailSendingService.class.getName());
	private NotificationModel mailInfo;
	private Authenticator auth;
	Properties prop = null;
	public MailSendingService(NotificationModel mailInfo, Properties pros,Authenticator auth) {
		this.mailInfo = mailInfo;
		this.prop = pros;
		this.auth = auth;
	}
	@Override
	public void run() {
		File file = null;
		Session session = null;
		Message message = null;
		String fileName = null;
		try {
			if(this.mailInfo.getAttachment() != null && this.mailInfo.getAttachment().length() > 0) {
				file = new File(this.mailInfo.getAttachment());
				//System.out.println("File Name:"+this.mailInfo.getAttachment());
				if(file.exists() && !file.isDirectory()) {
					fileName = this.mailInfo.getAttachment().split("/")[1];
					if(this.auth == null || this.prop == null) {
						this.prop = new Properties();
						this.prop.setProperty("mail.smtp.submitter", new MailAuthenticationService().getPasswordAuthentication().getUserName());
						this.prop.setProperty("mail.smtp.auth", "true");
						this.prop.setProperty("mail.smtp.host", "mail.btcl.com.bd");
						this.prop.setProperty("mail.smtp.port", "25");
						this.prop.put("mail.smtp.starttls.enable", "true");     
						this.prop.setProperty("mail.transport.protocol", "smtp");
						this.prop.setProperty("mail.smtp.from",  ApplicationConstants.BOUNCE_MAIL_ADDRESS);
					}
					session = Session.getInstance(this.prop, this.auth);
					message = new MimeMessage(session);
					message.setFrom(new InternetAddress(this.mailInfo.getSentFrom()));
					
					
					if(ApplicationConstants.WEBHOSTING_RENEW_NOTIFICATION_CUSTOMER) {
						
						 StringTokenizer stk = new StringTokenizer(this.mailInfo.getSentTo(), ",;");
						    String[] to = new String[stk.countTokens()];
						    for (int i = 0; i < to.length; i++) {
						      to[i] = stk.nextToken().trim();
						    }
						    
						    InternetAddress[] address = new InternetAddress[to.length];
						      for (int i = 0; i < to.length; i++) {
						        address[i] = new InternetAddress(to[i]);
						      }
						      					    
						message.setRecipients(Message.RecipientType.TO, address);
					}else {
						message.setRecipient(Message.RecipientType.TO, new InternetAddress(ApplicationConstants.BTCL_ADMIN_MAIL));
					}
					//message.setRecipient(Message.RecipientType.TO, new InternetAddress("amanc.svr@btcl.com.bd"));
					//message.setRecipient(Message.RecipientType.TO, new InternetAddress("palash@revesoft.com"));
					if(ApplicationConstants.BTCL_AUTHORITY_CC_MAIL.length() > 0) {
						message.setRecipient(Message.RecipientType.CC, new InternetAddress(ApplicationConstants.BTCL_AUTHORITY_CC_MAIL));
					}
					if(ApplicationConstants.BTCL_AUTHORITY_BCC_MAIL.length() > 0) {
						message.setRecipient(Message.RecipientType.BCC, new InternetAddress(ApplicationConstants.BTCL_AUTHORITY_BCC_MAIL));
					}
					message.setSubject(this.mailInfo.getSentSubject());
					
					Multipart multipart = new MimeMultipart();
					
					MimeBodyPart messageBody = new MimeBodyPart();
					messageBody.setText(this.mailInfo.getSentBody());
					messageBody.addHeader("MAIL FROM", "BTCL");
					messageBody.setHeader("Content-Type", "text/html; charset=UTF-8");
					multipart.addBodyPart(messageBody);
					
					MimeBodyPart attachmentPart = new MimeBodyPart();
					
					attachmentPart.setFileName(fileName);
					try {
						attachmentPart.attachFile(file);
						multipart.addBodyPart(attachmentPart);
					}catch(Exception ex) {
						logger.fatal("Exception",ex);
					}
					
					message.setContent(multipart);
					
					Transport.send(message);
					logger.debug("Mail sent to :"+this.mailInfo.getSentTo());
					//update the record//
					org.hibernate.Session hbSession = CommonDAO.buildSessionFactory().openSession();
					try {
						hbSession.beginTransaction();
						NotificationModel model = (NotificationModel) new CommonDAO().getModelByPrimary(NotificationModel.class, this.mailInfo.getId(), hbSession);
						if(model != null) {
							model.setStatus("sent");
							hbSession.getTransaction().commit();
						}
					}catch(Exception ex) {
						if( hbSession.getTransaction() != null)
							hbSession.getTransaction().rollback();
						logger.fatal("Exception in mail send",ex);
					}finally {
						hbSession.close();
					}
				}else {
					logger.fatal("No attachment was File not found");
				}
				
			}
		}catch(Exception ex) {
			logger.fatal("Exception",ex);
		}finally {

		}
	}

}
