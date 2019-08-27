package whscheduler.service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MailAuthenticationService extends Authenticator{

	private PasswordAuthentication authentication;
	
	public MailAuthenticationService() {
		String username = "campaign@btcl.com.bd";
	    String password = "1T78#2cn5Tt";
	    authentication = new PasswordAuthentication(username, password);
	}
	
	public PasswordAuthentication getPasswordAuthentication() {
		return authentication;
	}
}
