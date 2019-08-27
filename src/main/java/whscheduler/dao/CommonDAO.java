package whscheduler.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import whscheduler.model.VbsequencerModel;
import whscheduler.util.ApplicationConstants;

public class CommonDAO {

	private static Logger logger = Logger.getLogger(CommonDAO.class.getName());
	
	private static SessionFactory sessionFactory;
	
	public CommonDAO() {
		if(sessionFactory == null) {
			buildSessionFactory();			
		}
	}
	
	public static SessionFactory buildSessionFactory() {
		try {
			Configuration config = new Configuration();
			File file = null;
			if(ApplicationConstants.IS_DEVELOPMENT_ENVIORMENT) {
				file = new File("resources/hibernate.local.cfg.xml");
			}else {
				file = new File("resources/hibernate.cfg.xml");
			}
			if(!file.isDirectory() && file.exists()) {
				config.configure(file);
			}
			logger.debug("----Hibernate Configured and Initiated SessionFactory----");
			//ServiceRegistry serviceRegi = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
			sessionFactory = config.buildSessionFactory();
		}catch(Exception e) {
			logger.fatal("buildSessionFactory->Exception",e);
		}
		
		return sessionFactory;
	}
	
	public List<?> getList(Class<?> entityClass, String condition,int skip,int limit) {
		List<?> list = null;
		String queryString = null;
		Session session = null;
		if(entityClass != null) {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();
				list = new ArrayList<>();
				queryString = "From "+entityClass.getName();
				if(condition != null && condition.length() > 0) {
					queryString += " WHERE "+condition;
				}
				logger.debug("Sql :"+queryString);
				Query query = session.createQuery(queryString);
				
				
				if(skip > 0) {
					query.setFirstResult(skip);
				}
				if(limit > 0) {
					query.setMaxResults(limit);
				}
				list = query.list();
				logger.debug("Result size: "+list.size());
			}catch(Exception sqException) {
				logger.fatal("buildSessionFactory->sqException",sqException);
				if(session.getTransaction() != null) {
					logger.debug("---Transaction is being rollback---");
					session.getTransaction().rollback();
				}
			}finally {
				if(session != null) {
					session.close();
				}
			}
		}
		return list;
	}
	
	public Object getModelByPrimary(Class<?> entity,long key,Session hbSession){
		Object model = null;
		if(entity != null && hbSession != null) {
			try {
				model = hbSession.get(entity.getName(), key);
			}catch(Exception ex) {
				logger.fatal("getModelByPrimary->Exception",ex);
			}
		}
		return model;
	}
	
	public void update(Class<?> entity,Session hbSession) {
		if(entity != null && hbSession != null) {
			try {
				hbSession.update(entity.getName());
			}catch(Exception ex) {
				logger.fatal("getModelByPrimary->Exception",ex);
			}
		}
	}
	
	public void saveModel(Object entity,Session hbSession) {
		if(entity != null && hbSession != null) {
			hbSession.save(entity);
		}
	}
	
	public long getIdFromVbSequenceByPrimary(String key) {
		long id = 0;
		Session session = null;
		if(key != null && key.length() > 0) {
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();
				VbsequencerModel vbsequence = session.get(VbsequencerModel.class, key);
				id = vbsequence.getNextId() + 1;
				vbsequence.setNextId(id + 1);
				vbsequence.setTableLastModificationTime(System.currentTimeMillis());
				session.getTransaction().commit();
			}catch(Exception ex) {
				if(session.getTransaction() != null) {
					logger.debug("---Failed to fetch/update vbsequence. Calling rollback---");
					session.getTransaction().rollback();
				}
				logger.fatal("getIdFromVbSequenceByTableName->SQLException",ex);
			}finally {
				if(session != null) {
					session.close();
				}
			}
		}
		return id;
	}
}
