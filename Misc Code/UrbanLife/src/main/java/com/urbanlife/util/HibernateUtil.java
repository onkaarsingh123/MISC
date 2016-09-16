package com.urbanlife.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	/*
	 * private static final SessionFactory sessionFactory =
	 * buildSessionFactory();
	 * 
	 * 
	 * private static SessionFactory buildSessionFactory() { try { // Create the
	 * SessionFactory from hibernate.cfg.xml
	 * 
	 * Configuration configuration = new Configuration().configure();
	 * StandardServiceRegistryBuilder builder = new
	 * StandardServiceRegistryBuilder().
	 * applySettings(configuration.getProperties()); SessionFactory factory =
	 * configuration.buildSessionFactory(builder.build()); return factory; //
	 * return new Configuration().configure().buildSessionFactory(); } catch
	 * (Throwable ex) { // Make sure you log the exception, as it might be
	 * swallowed System.err.println("Initial SessionFactory creation failed." +
	 * ex); throw new ExceptionInInitializerError(ex); } }
	 * 
	 * public static SessionFactory getSessionFactory() { return sessionFactory;
	 * }
	 * 
	 * public static void shutdown() { // Close caches and connection pools
	 * getSessionFactory().close(); }
	 * 
	 * }
	 */

	private static HibernateUtil instance = null;

	private static SessionFactory sessionFactory;
	private static StandardServiceRegistry serviceRegistry;

	private HibernateUtil() {

		Configuration configuration = new Configuration();
		configuration.configure();
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
				configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		// Configuration configuration = new Configuration().configure();
		// StandardServiceRegistryBuilder builder = new
		// StandardServiceRegistryBuilder().
		// applySettings(configuration.getProperties());
		// sessionFactory = configuration.buildSessionFactory(builder.build());
	}

	public static HibernateUtil getInstance() {
		if (instance == null) {
			instance = new HibernateUtil();
		}
		return instance;
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}

	public Session getSession() {
		Session currentSession = null;
		if (sessionFactory.getCurrentSession() != null) {
			currentSession = sessionFactory.getCurrentSession();
		} else {
			currentSession = sessionFactory.openSession();
		}
		return currentSession;
	}
}