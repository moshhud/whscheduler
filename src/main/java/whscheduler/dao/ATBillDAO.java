package whscheduler.dao;

import java.util.ArrayList;

import javax.persistence.Query;

import org.hibernate.Session;

import whscheduler.model.ATBillModel;
import whscheduler.util.ApplicationConstants;

public class ATBillDAO {

	public long getCountBill(String condition,Session hbSession) {
		long count = 0;
		String sql = null;
		Query query = null;
		if(hbSession != null) {
			sql = "SELECT COUNT(*) FROM ATBillModel";
			if(condition != null && condition.length() > 0) {
				sql += condition;
			}
			query = (Query) hbSession.createQuery(sql);
			count = (long)query.getSingleResult(); 
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public ATBillModel getSingleBillByCondition(String condition,Session hbSession) {
		ATBillModel bill = null;
		ArrayList<ATBillModel> list = null;
		if(condition != null && condition.length() > 0 && hbSession != null) {
			list =  (ArrayList<ATBillModel>) new CommonDAO().getList(ATBillModel.class, condition, 0, 1);
			if(list != null && list.size() == 1) {
				bill = list.get(0);
			}
		}
		return bill;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ATBillModel> getOverdueBills(int gracPerriod){
		long gacePeriod = gracPerriod * 24 * 60 * 60 * 1000L;
		String condition = " blRequestType IN (0,1,2) AND blType IN(10,11) AND blIsDeleted=0 AND blPaymentID=0 AND blEntityTypeID=201 "+
							" AND (blLastPaymentDate + "+gacePeriod+") <"+System.currentTimeMillis();
		return (ArrayList<ATBillModel>) new CommonDAO().getList(ATBillModel.class, condition, 0, ApplicationConstants.RECORD_PROCESS_PER_REQUEST);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ATBillModel> getOverdueBills4Upgrade(int gracPerriod){
		long gacePeriod = gracPerriod * 24 * 60 * 60 * 1000L;
		String condition = " blRequestType IN (0,1,2) AND blType IN(12) AND blIsDeleted=0 AND blPaymentID=0 AND blEntityTypeID=201 "+
							" AND (blLastPaymentDate + "+gacePeriod+") <"+System.currentTimeMillis();
		return (ArrayList<ATBillModel>) new CommonDAO().getList(ATBillModel.class, condition, 0, ApplicationConstants.RECORD_PROCESS_PER_REQUEST);
	}
	
}
