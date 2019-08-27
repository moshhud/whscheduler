package whscheduler.dao;

import java.util.ArrayList;
import java.util.Calendar;

import org.hibernate.Session;

import whscheduler.model.ATWebhostingModel;
import whscheduler.model.WebHostingDiscountModel;
import whscheduler.model.WebHostingPackageInfoModel;
import whscheduler.util.ApplicationConstants;

public class ATWebhostingDAO {
	
	@SuppressWarnings("unchecked")
	public ArrayList<ATWebhostingModel> getExpiredWebhostingForRenew(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);		
		String condition = " whExpiryDate <= "+cal.getTimeInMillis()+" AND whLatestStatus NOT IN ("+ApplicationConstants.WEBHOSTING_RENEW_TYPEID+
				","+ApplicationConstants.WEBHOSTING_BLOCKED_TYPEID+","+ApplicationConstants.WEBHOSTING_RELEASE_TYPEID+") AND whIsPrivileged=0 "+
				" AND whActivationDate > 0 ORDER BY whID";
		return (ArrayList<ATWebhostingModel>) new CommonDAO().getList(ATWebhostingModel.class, condition,0,ApplicationConstants.RECORD_PROCESS_PER_REQUEST);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ATWebhostingModel> getExpiredWebhostingForBlocking(int days){
		ArrayList<ATWebhostingModel> list = null;
		if(days > 0) {
			long time = days * 24 * 60 * 60 * 1000L;
			String condition = " ("+System.currentTimeMillis()+" - whExpiryDate) >= "+time+" AND whLatestStatus ="+ApplicationConstants.WEBHOSTING_RENEW_TYPEID+
					" AND whIsPrivileged=0 ORDER BY whID";
			list = (ArrayList<ATWebhostingModel>) new CommonDAO().getList(ATWebhostingModel.class, condition,0,ApplicationConstants.RECORD_PROCESS_PER_REQUEST);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ATWebhostingModel> getOngoingExpiredWebhosting(int days){
		ArrayList<ATWebhostingModel> list = null;
		if(days > 0) {
			long expireDate= days * 24 * 60 * 60* 1000L;
			String condition = "  ("+System.currentTimeMillis()+" - whExpiryDate) >= "+expireDate+" AND whLatestStatus IN (20101,20111)"+
			"AND whActivationDate=0 AND whIsDeleted=0 ORDER BY whID";
			list = (ArrayList<ATWebhostingModel>) new CommonDAO().getList(ATWebhostingModel.class, condition,0,ApplicationConstants.RECORD_PROCESS_PER_REQUEST);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<WebHostingDiscountModel> getWebHostingDiscountList(){
		ArrayList<WebHostingDiscountModel> list = null;	
		Long currentTime = System.currentTimeMillis();
		String condition = " wdIsActive=1 and ("+currentTime+" between wdEffectiveFrom and wdEffectiveTo) ";
		list = (ArrayList<WebHostingDiscountModel>) new CommonDAO().getList(WebHostingDiscountModel.class, condition,0,0);
		
		return list;
	}
	
	public double getDiscount(WebHostingDiscountModel discountDTO,int slot,double price,int packageType){
    	double val = 0;
    	double totalPrice = 0;
    	double discountAmount = 0;
    	int s = slot;
    	int pType = packageType;
    	double p = price;
    	System.out.println("DiscountType: "+discountDTO.getDiscountType());
    	switch(discountDTO.getDiscountType()){
    	  case 1:
    		  totalPrice = s*p;
    		  discountAmount = totalPrice*(discountDTO.getDiscountPrcnt()/100);		  
    		  if(discountAmount>=discountDTO.getMinDiscount()&&discountAmount<=discountDTO.getMaxDiscount()){
    			  val = discountAmount;
    		  }else if(discountAmount>discountDTO.getMaxDiscount()){
    			  val = discountDTO.getMaxDiscount();
    		  }else if(discountAmount<discountDTO.getMinDiscount()){
    			  if(discountDTO.getRestrictMin()==0) {
    				  val = discountDTO.getMinDiscount();
    			  }else {
    				  val = 0;
    			  }
    			  
    		  }
    		  break;
    	  case 2:
    		  if(pType==1){
    			  if(discountDTO.getYear()==1){
    				  if(s>=discountDTO.getAmount()){
    					  totalPrice = s*p;
    					  discountAmount = totalPrice*(discountDTO.getDiscountPrcnt()/100);		  
    					  if(discountAmount>=discountDTO.getMinDiscount()&&discountAmount<=discountDTO.getMaxDiscount()){
    						  val = discountAmount;
    					  }else if(discountAmount>discountDTO.getMaxDiscount()){
    						  val = discountDTO.getMaxDiscount();
    					  }else if(discountAmount<discountDTO.getMinDiscount()){    						  
    						  if(discountDTO.getRestrictMin()==0) {
    		    				  val = discountDTO.getMinDiscount();
    		    			  }else {
    		    				  val = 0;
    		    			  }
    					  }
    				  }
    			  }else{
    				  totalPrice = s*p;
    				  if(totalPrice>=discountDTO.getAmount()){
    					  discountAmount = totalPrice*(discountDTO.getDiscountPrcnt()/100);		  
    					  if(discountAmount>=discountDTO.getMinDiscount()&&discountAmount<=discountDTO.getMaxDiscount()){
    						  val = discountAmount;
    					  }else if(discountAmount>discountDTO.getMaxDiscount()){
    						  val = discountDTO.getMaxDiscount();
    					  }else if(discountAmount<discountDTO.getMinDiscount()){
    						  if(discountDTO.getRestrictMin()==0) {
    		    				  val = discountDTO.getMinDiscount();
    		    			  }else {
    		    				  val = 0;
    		    			  }
    					  }
    				  }
    			  }
    			  
    		  }
    		  
    		  
    		  break;
    	  case 3:
    		  discountAmount = discountDTO.getAmount();
    		  val = discountAmount;
    		  break;
    	  case 4:
    		  if(discountDTO.getIsPercent()==1){
    			  totalPrice = s*p;
    			  discountAmount = totalPrice*(discountDTO.getAmount()/100);
    		  }else{
    			  discountAmount = discountDTO.getAmount();
    		  }		  
    		  
    		  val = discountAmount;
    		  break;
    		  
    	}
    	System.out.println("discountAmount: "+val);
    	return val;
    }
	
	@SuppressWarnings("unchecked")
	public WebHostingPackageInfoModel getPackageInfoDTO(String condition,Session hbSession) {
		WebHostingPackageInfoModel packageDTO = null;
		ArrayList<WebHostingPackageInfoModel> list = null;
		if(condition != null && condition.length() > 0 && hbSession != null) {
			list =  (ArrayList<WebHostingPackageInfoModel>) new CommonDAO().getList(WebHostingPackageInfoModel.class, condition, 0, 1);
			if(list != null && list.size() == 1) {
				packageDTO = list.get(0);
			}
		}
		return packageDTO;
	}
}
