package com.kings.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.kings.model.User;

public class DataAccess {
	private static Session session;
	private static SessionFactory sessionFactory;
	private static Configuration config;
	
	@SuppressWarnings("deprecation")
	public static void configure() {
		if(config == null) {
			config = new Configuration().configure(); 
			addClassMappings();
		}
		
		if(sessionFactory == null) {
			sessionFactory = config.buildSessionFactory();
		}
		
		if(session == null) {
			session = sessionFactory.openSession();
		}
	}
	
	public Session getSession() {
		configure();
		return session;
	}
	
	public static void addClassMappings() {
		config.addClass(User.class);
	}
	
	public void beginTransaction() {
		getSession().beginTransaction();
	}
	
	public void commit() {
		getSession().getTransaction().commit();
	}
	
	public void rollback() {
		getSession().getTransaction().rollback();
	}
	
	public void save(Object o) {
		getSession().save(o);
	}
	
	public static void main(String[] args) {
		DataAccess access = new DataAccess();
		Session s = access.getSession();
		access.beginTransaction();
		s.save(new User("2"));
		access.commit();
	}
}
