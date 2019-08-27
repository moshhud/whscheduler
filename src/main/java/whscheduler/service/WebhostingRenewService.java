package whscheduler.service;

import java.util.ArrayList;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import whscheduler.dao.CommonDAO;
import whscheduler.model.ATBillModel;
import whscheduler.service.ATBillService;
import whscheduler.dao.ATStateDAO;
import whscheduler.model.ATWebhostingModel;
import whscheduler.model.ATWebhostingRequestModel;
import whscheduler.model.ATRequestModel;
import whscheduler.util.ApplicationConstants;

public class WebhostingRenewService {
	private static Logger logger = Logger.getLogger(WebhostingRenewService.class.getName());
	
	public ATRequestModel getAtRequestDataInstance(ATWebhostingModel hosting,long atReId,int reqyestTypeId,int requestExpireDays, Session hbSession) {
		ATRequestModel atRequest = null;
		if(hosting != null) {
			Calendar cal = Calendar.getInstance();
			atRequest = new ATRequestModel();
			atRequest.setArID(atReId);
			atRequest.setArRequestTypeID(reqyestTypeId);
			atRequest.setArEntityTypeID(ApplicationConstants.WEBHOSTING_TYPEID);
			atRequest.setArEntityID(hosting.getID());
			atRequest.setArClientID(hosting.getClientID());
			atRequest.setArReqTime(System.currentTimeMillis());
			atRequest.setArRequestedByAccountID(ApplicationConstants.ADMIN_ID_FROM_WHOME_RENEW_REQUEST_RISE);
			atRequest.setArIP("1.2.3.4");
			atRequest.setArPriority(false);
			
			if(requestExpireDays > 0) {
				cal.add(Calendar.DATE, requestExpireDays);
				atRequest.setArExpireTime(cal.getTimeInMillis());
			}else {
				long duration = 0;
				String id = ""+reqyestTypeId;
				try {
					long l = Long.parseLong(id);
					duration = new ATStateDAO().getDurationByStID(l, hbSession);
				}catch(RuntimeException ex) {
					logger.fatal("RuntimeException",ex);
				}
				atRequest.setArExpireTime(cal.getTimeInMillis() + duration);
			}
			atRequest.setArLastModificationTime(System.currentTimeMillis());
			atRequest.setArIsDeleted(false);
			atRequest.setArCompletionStatus(false);
			atRequest.setArSourceRequestID("0");
			
		}
		return atRequest;
	}
	
	public ATWebhostingRequestModel getWebhostingRequestDataInstance(ATWebhostingModel hosting,long atHostingRequestId,long atRequestId) {
		ATWebhostingRequestModel atWHostingRequest = null;
		if(hosting != null) {
			atWHostingRequest = new ATWebhostingRequestModel();
			atWHostingRequest.setID(atHostingRequestId);
			atWHostingRequest.setWebHostingID(hosting.getID());
			atWHostingRequest.setLastModificationTime(System.currentTimeMillis());
			atWHostingRequest.setYear(1);
			atWHostingRequest.setPackageType(1);
			atWHostingRequest.setReqID(atRequestId);
			atWHostingRequest.setBuyType(ApplicationConstants.WEBHOSTING_REQUEST_TYPE_RENEW);
			if(hosting.getPackageID() > 0) {
				atWHostingRequest.setPackageID(hosting.getPackageID());
			}
		}
		return atWHostingRequest;
	}
	
	public ATBillModel getATBillDataInstance(ATWebhostingModel hosting,long blID, long reqID, double cost, double discount) {
		ATBillModel atBill = null;
		float lateFee = 0;		
		double vatOnExtraCharge=150;
		double extraChargeAfterDueDate=1000;
		if(hosting != null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, 1);
			atBill = new ATBillModel();
			atBill.setID(blID);
			atBill.setClientID(hosting.getClientID());
			atBill.setBlEntityID(hosting.getID());
			atBill.setBlEntityTypeID(ApplicationConstants.WEBHOSTING_TYPEID);
			atBill.setBlYear(cal.get(Calendar.YEAR));
			atBill.setBlMonth(cal.get(Calendar.MONTH));
			atBill.setType(11);
			atBill.setRequestType(ApplicationConstants.WEBHOSTING_REQUEST_TYPE_RENEW);
			atBill.setIsDeleted(0);
			atBill.setGenerationTime(System.currentTimeMillis());
			atBill.setLastModificationTime(System.currentTimeMillis());
			cal.setTimeInMillis(hosting.getExpiryDate());
			cal.add(Calendar.DATE, 90);
			atBill.setLastPaymentDate(cal.getTimeInMillis());
			atBill.setReqID(reqID);
			atBill.setActivationTimeFrom(hosting.getExpiryDate());
			Calendar cal2 = Calendar.getInstance();
			cal2.setTimeInMillis(hosting.getExpiryDate());
			cal2.add(Calendar.YEAR, 1);
			cal2.add(Calendar.DATE, -1);
			atBill.setActivationTimeTo(cal2.getTimeInMillis());
			double vat = Math.floor(cost * ApplicationConstants.VAT_WEBHOSTING_PRICE);
			atBill.setGrandTotal(cost);
			atBill.setTotalPayable(cost + lateFee);
			atBill.setDiscount(discount);
			atBill.setVAT(vat);
			atBill.setNetPayable(Math.ceil(cost + vat - discount));
			atBill.setDescription(new ATBillService().getAtBillDetailsHTML(atBill, hosting.getDomain()));
			atBill.setBlClassName("webAndEmailHosting.managehosting.WebHostingRenewDemandNote");
			
			atBill.setDuePaymentDate((hosting.getExpiryDate()-1));
			atBill.setExtraChargeAfterDueDate(extraChargeAfterDueDate);
			
			atBill.setExtraVATAfterDueDate(vatOnExtraCharge);
			atBill.setNetPayableAfterDueDate(Math.ceil(atBill.getTotalPayable() + atBill.getVAT()+extraChargeAfterDueDate+vatOnExtraCharge - discount));

		}
		return atBill;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ATWebhostingModel> getExpiredWebhostingBillList(ArrayList<ATWebhostingModel> list){
		ArrayList<ATWebhostingModel> billList = null;
		String condition = "",sql = null;
		if(list != null && list.size() > 0) {
			for(ATWebhostingModel hosting : list) {
				condition += hosting.getID()+",";
			}
			if(condition.endsWith(",")) {
				condition = condition.substring(0,condition.length() -1);
				sql = " blEntityID IN ("+condition+") AND blRequestType=2";
				billList = (ArrayList<ATWebhostingModel>) new CommonDAO().getList(ATWebhostingModel.class, sql, 0, 0);
			}
		}
		return billList;
	}

}
