package whscheduler.util;

public class ApplicationConstants {

	public static boolean IS_DEVELOPMENT_ENVIORMENT = true;
	public static boolean WEBHOSTING_RENEW_NOTIFICATION_CUSTOMER = false;
	public static boolean WEBHOSTING_RENEW_NOTIFICATION_ADMIN = false;
	
	public static final int WEBHOSTING_RENEW_TYPEID = 20011;
	public static final int WEBHOSTING_BLOCKED_TYPEID = -22301;
	public static final int WEBHOSTING_RELEASE_TYPEID = -22201;
	public static final int WEBHOSTING_REQUEST_TYPE_RENEW = 2;
	public static final int WEBHOSTING_TYPEID = 201;
	public static final long ADMIN_ID_FROM_WHOME_RENEW_REQUEST_RISE = -53127;
	
	public static final float VAT_WEBHOSTING_PRICE = 0.15f;
	
	public static final String TLD_TYPE_BANGLA = ".বাংলা";
	public static final String TLD_TYPE_BD = ".bd";
	

	public static String BTCL_ADMIN_MAIL = "info@btcl.com.bd";
	public static String BTCL_INFO_MAIL = "info@btcl.com.bd";
	public static String BTCL_AUTHORITY_MAIL = "amanc.svr@btcl.com.bd";
	public static String BTCL_AUTHORITY_CC_MAIL = "amanc.svr@btcl.com.bd";
	public static String BTCL_AUTHORITY_BCC_MAIL = "monirul@revesoft.com";
	
	public static final String JASPER_REPORT_TEMPLATE = "jasper/webhosting-bill/domain-renew-invoice.jrxml";
	
	public static int RECORD_PROCESS_PER_REQUEST = 100;
	public static int INVOICE_MAIL_PROCESS_PER_REQUEST = 10;
	
	
	public static int RENEW_INVOCE_BEFORE_HOW_MANY_DAYS = 30;
	public static int WEBHOSTING_BLOCKING_AFTER_HOW_MANY_DAYS = 0;
	public static int WEBHOSTING_GRACE_PERIOD = 7;
	public static final String BOUNCE_MAIL_ADDRESS = "campaign@btcl.com.bd";
	
}
