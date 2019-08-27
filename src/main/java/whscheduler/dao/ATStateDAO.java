package whscheduler.dao;

import org.hibernate.Session;

import whscheduler.model.ATStateModel;

public class ATStateDAO {

	public long getDurationByStID(long id,Session hbSession) {
		long duration = 0;
		ATStateModel atState = null;
		if(hbSession != null) {
			atState = (ATStateModel) new CommonDAO().getModelByPrimary(ATStateModel.class, id, hbSession);
			if(atState != null) {
				duration = atState.getDurationInMillis();
			}
		}
		return duration;
	}
}
