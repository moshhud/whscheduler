package whscheduler.appMain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import whscheduler.controller.MailSendingController;
import whscheduler.controller.WebhostingBlockingController;
import whscheduler.controller.WebhostingReleaseController;
import whscheduler.controller.WebhostingRenewController;
import whscheduler.util.ApplicationConstants;

/**
 * Hello world!
 *
 */
public class WHSchedulerMain 
{
	private static Logger logger = Logger.getLogger(WHSchedulerMain.class.getName());
	
    public static void main( String[] args )
    {
    	if(new WHSchedulerMain().readConfig()) {
    		logger.debug("configuration loaded.");
    		try {
    			
    			//renew hosting
    			new WebhostingRenewController().webhostingRenewProcessStart();
    			    			
    			//block hosting
    			new WebhostingBlockingController().webhostingToBlockStage();
    			
    			//release hosting
    			new WebhostingReleaseController().webhostingRelease();
    			
    			//void all invoices those  payment dates are exceeded for package upgrade.
    			new WebhostingReleaseController().webhostingVoidBill4Upgrade();
    			
    			//ongoing hosting release
    			new WebhostingReleaseController().ongoingWebhostingReject();
    			
    			//sending email
    			new MailSendingController().sendPendingMails();
    			
    			logger.debug("Process completed.");
    			System.exit(0);
    		}catch(Exception e) {
				e.printStackTrace();
				logger.fatal("Exception",e);
			}
    	}
        
    }
    
    
    private boolean readConfig() {
		boolean readSuccess = false;
		FileReader fileReader = null;
		File file = new File("resources/configuration.json");
		BufferedReader br = null;
		String txt = "",line = null;
		JSONObject json = null;
		try {
			if(file.exists() && file.isFile()) {
				fileReader = new FileReader(file);
				br = new BufferedReader(fileReader);
				while((line = br.readLine()) != null) {
					txt += line;
				}
				if(txt.length() > 0) {
					json = new JSONObject(txt);
					if(json.has("renewInvoiceWillGenerateBefore")) {
						ApplicationConstants.RENEW_INVOCE_BEFORE_HOW_MANY_DAYS = json.getInt("renewInvoiceWillGenerateBefore");
					}
					if(json.has("renewInvoiceMailToClient")) {
						ApplicationConstants.WEBHOSTING_RENEW_NOTIFICATION_CUSTOMER = json.getBoolean("renewInvoiceMailToClient");
					}
					if(json.has("expiredWebhostingBlockAfter")) {
						ApplicationConstants.WEBHOSTING_BLOCKING_AFTER_HOW_MANY_DAYS = json.getInt("expiredWebhostingBlockAfter");
					}
					
					if(json.has("processPerRequest")) {
						ApplicationConstants.RECORD_PROCESS_PER_REQUEST = json.getInt("processPerRequest");
					}
					if(json.has("invoiceMailprocessPerRequest")) {
						ApplicationConstants.INVOICE_MAIL_PROCESS_PER_REQUEST = json.getInt("invoiceMailprocessPerRequest");
					}
					if(json.has("mailBtclAuthority")) {
						ApplicationConstants.BTCL_AUTHORITY_MAIL = json.getString("mailBtclAuthority");
					}
					if(json.has("mailBtclAdmin")) {
						ApplicationConstants.BTCL_ADMIN_MAIL = json.getString("mailBtclAdmin");
					}
					if(json.has("mailBtclInfo")) {
						ApplicationConstants.BTCL_INFO_MAIL = json.getString("mailBtclInfo");
					}
					if(json.has("mailBtclAuthorityCC")) {
						ApplicationConstants.BTCL_AUTHORITY_CC_MAIL = json.getString("mailBtclAuthorityCC");
					}
					if(json.has("mailBtclAuthorityBcc")) {
						ApplicationConstants.BTCL_AUTHORITY_BCC_MAIL = json.getString("mailBtclAuthorityBcc");
					}
					readSuccess = true;
				}
			}
		}catch(FileNotFoundException ex) {
			logger.fatal("RuntimeException",ex);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(fileReader != null) {
					fileReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return readSuccess;
	}
}
