package whscheduler.dao;

import java.util.ArrayList;

import org.hibernate.Session;

import whscheduler.model.ATRequestModel;

public class ATRequestDAO {

	@SuppressWarnings("unchecked")
	public ATRequestModel getSingleRequestByCondition(String condition, Session hbSession) {
		ATRequestModel atrequest = null;
		ArrayList<ATRequestModel> list = null;
		if(condition != null && condition.length() > 0 && hbSession != null) {
			list = (ArrayList<ATRequestModel>) new CommonDAO().getList(ATRequestModel.class, condition, 0, 1);
			if(list != null && list.size() == 1) {
				atrequest = list.get(0);
			}
		}
		return atrequest;
	}
}
