package com.mywebgalery.cms.model;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionManager {

	private static final Logger _log = LoggerFactory.getLogger(TransactionManager.class);
	
	private Set<Session> _sessions = new HashSet<Session>();
	
	public Session getSession(){
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		if(s == null || !s.isOpen()){
			s = HibernateUtil.getSessionFactory().openSession();
		}
		_sessions.add(s);
		return s;
	}

	public void commit(){
		for(Session s:_sessions){
			try {
				if(s.isOpen())
					s.getTransaction().commit();
			} catch (Exception e) {
				_log.warn(e.getMessage());
			}
		}
	}
	
	public void rollback(){
		for(Session s:_sessions){
			try {
				if(s.isOpen())
					s.getTransaction().rollback();
			} catch (Exception e) {
				_log.warn(e.getMessage());
			}
		}
	}
	
	public void close(){
		for(Session s:_sessions){
			try {
				if(s.isOpen())
					s.close();
				_sessions.remove(s);
			} catch (Exception e) {
				_log.warn(e.getMessage());
			}
		}		
	}
	
	public void clear(){
		close();
	}
}
