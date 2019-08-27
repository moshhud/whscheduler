package whscheduler.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import whscheduler.util.ApplicationConstants;
import whscheduler.model.ATBillModel;
import whscheduler.model.ATClientContactDetailsModel;
import whscheduler.model.ATClientModel;
import whscheduler.model.ATWebhostingModel;

public class PDFBillGenerateService {
	private static Logger logger = Logger.getLogger(PDFBillGenerateService.class.getName());
	
	public void generatePDF(ATBillModel bill,ATWebhostingModel hosting,ATClientModel client) {
		double charge = 0;
		String invoiceFileName = null;
		if(bill != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(ApplicationConstants.JASPER_REPORT_TEMPLATE);
			try {
				HashMap<String, Object> billAttributes = new HashMap<>();
				billAttributes.put("logo_btcl", "resources/images/btcl_logo.jpg");
				billAttributes.put("logo_bd", "resources/images/logo_bd.png");
				billAttributes.put("top_bangla_heading", "resources/images/top_bangla_heading.png");
				
				List<ATClientContactDetailsModel> list = client.getClientDetails().getContactDetails();
				String billingName="",billingAddress="",billingEmail="",billingPhoneNo="";
				for(int i=0;i<list.size();i++) {
					if(list.get(i).getDetailsType()==1 && list.get(i).getEmail().length()>0) {
						billingName=list.get(i).getName();
						billingAddress=list.get(i).getAddress();
						billingEmail=list.get(i).getEmail();
						billingPhoneNo=list.get(i).getPhoneNumber();
						logger.debug("Email: "+list.get(i).getEmail());
						break;
					}
				}
				
				if(billingName!=null && billingName.equals(""))
					billAttributes.put("registrant name", "Not found");
				else
					billAttributes.put("registrant name", billingName);
				
				if(billingAddress!=null && billingAddress.equals(""))
					billAttributes.put("registrant address", "Not found");
				else
					billAttributes.put("registrant address", billingAddress);
				if(billingEmail!=null && billingEmail.equals(""))
					billAttributes.put("registrant email", "Not found");
				else
					billAttributes.put("registrant email", billingEmail);
				if(billingPhoneNo!=null && billingPhoneNo.equals(""))
					billAttributes.put("registrant contact", "Not found");
				else
					billAttributes.put("registrant contact", billingPhoneNo);
				
				
				billAttributes.put("username", client.getLoginName());
				try {
					String domainImgName = "invoices/domains/"+hosting.getID()+".png";
					convertTextToImage(": "+hosting.getDomain(), domainImgName,700, 45 );
					billAttributes.put("domainName", domainImgName);
				}catch(Exception ex) {
					logger.fatal("Exception",ex);
					billAttributes.put("domainName", hosting.getDomain());
				}
				
				billAttributes.put("applicationType", "Renewal");
				billAttributes.put("year", 1);
				billAttributes.put("servicePeriodHidden", false);
				billAttributes.put("serviceStart", dateFormat.format(hosting.getExpiryDate()));
				billAttributes.put("serviceEnd", dateFormat.format(bill.getActivationTimeTo()));
				
				billAttributes.put("packageNotFound", hosting.getPackageID() > 0 ? true : false);
				billAttributes.put("perYearCost", bill.getGrandTotal());
				
				charge = Math.ceil(bill.getTotalPayable()*.10);				
				if(bill.getGenerationTime()>(1549821600000L-1))
				     charge=0;
				billAttributes.put("teletalkCharge", charge);
				billAttributes.put("payable_using_teletalk", bill.getNetPayable() + charge);
				
				billAttributes.put("footer_text_2", null);
				billAttributes.put("footer_text_3", null);
				billAttributes.put("header_text_1","Web Hosting Renewal Invoice");
				
				billAttributes.put("generationTime", System.currentTimeMillis());
				billAttributes.put("lastPaymentDate", bill.getLastPaymentDate());
				
				billAttributes.put("totalPayableWithoutLateFee", bill.getNetPayable());
				billAttributes.put("withoutLateTime", hosting.getExpiryDate() - 24*60*60*1000);
				double totalAmountWithLateFeeAndVat = bill.getNetPayable()+(1000 + 1000 * .15);
				billAttributes.put("totalPayableWithLateFee", totalAmountWithLateFeeAndVat);
				
				billAttributes.put("latePaymentDetails","Note: "+bill.getNetPayable()+" (Payable Amount) + 1000 (late fee) + "+
						(1000*.15) + "(VAT for late fee)");
	            charge = Math.ceil((bill.getTotalPayable() + 1000) *.10);
	            charge = Math.ceil((bill.getTotalPayable() + 1000) *.10);
				// Teletalk service charge is 0 from 11 February, 2019
				if(bill.getGenerationTime()>(1549821600000L-1))
				charge=0;
				billAttributes.put("teletalkChargeOnLateFee", charge);
				billAttributes.put("payable_using_teletalk_withlate_fee", totalAmountWithLateFeeAndVat + charge);
	
				JasperDesign design = JRXmlLoader.load(in);
				JasperReport jasper = JasperCompileManager.compileReport(design);
				List<ATBillModel> bills = Arrays.asList(bill);
				JRBeanCollectionDataSource jrBeans = new JRBeanCollectionDataSource(bills,false);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, billAttributes,jrBeans);
				invoiceFileName = "invoices/webhosting_renew_invoice_"+bill.getID()+".pdf";
				JasperExportManager.exportReportToPdfFile(jasperPrint, invoiceFileName);
				
			}catch (JRException e) {
				logger.fatal("JRException",e);
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					logger.fatal("IOException",e);
				}
			}
		}
	}
	
	public static void convertTextToImage(String text, String location, int width, int height ) {
		 try {
			 if(new File(location).exists() == false) {
				BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		        Graphics2D g2d = img.createGraphics();
		        
		        //Font font = new Font( fontName, Font.BOLD, fontSize );
		        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("invoices/domains/Nikosh.ttf")).deriveFont(38f);
		        g2d.setFont(customFont);
		        FontMetrics fm = g2d.getFontMetrics();
		        g2d.dispose();

		        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		        g2d = img.createGraphics();
		        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		        g2d.setFont(customFont);
		        fm = g2d.getFontMetrics();
		        g2d.setColor(Color.BLACK);
		        g2d.drawString(text, 0, fm.getAscent());
		        g2d.dispose();
		        try {
		            ImageIO.write( img, "png", new File( location ) );
		        } catch (IOException ex) {
		        	logger.fatal("IOException",ex);
		        } 
			 }
		 }catch(Exception ex) {
			 logger.fatal("Exception",ex);
		 }
	 }

}
