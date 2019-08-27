package whscheduler.dao;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import whscheduler.model.NotificationModel;
import whscheduler.util.ApplicationConstants;

public class NotificationDAO {

	private static Logger logger = Logger.getLogger(NotificationDAO.class.getName());
	
	public void insertNotification(NotificationModel notify) {
		if(notify != null) {
			Session hbSession = null;
			try {
				hbSession = CommonDAO.buildSessionFactory().openSession();
				hbSession.beginTransaction();
				new CommonDAO().saveModel(notify, hbSession);
				hbSession.getTransaction().commit();
			}catch(Exception ex) {
				logger.fatal("RuntimeException",ex);
				if(hbSession.getTransaction() != null)
					hbSession.getTransaction().rollback();
			}finally {
				hbSession.close();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<NotificationModel> getPendingNotifications(){
		ArrayList<NotificationModel> list = null;
		String condition = " sent_type='email' AND status='pending' AND request_generated_from='webhosting jar' ORDER BY id DESC ";
		list = (ArrayList<NotificationModel>) new CommonDAO().getList(NotificationModel.class, condition, 0, ApplicationConstants.INVOICE_MAIL_PROCESS_PER_REQUEST);
		return list;
	}
	
}
