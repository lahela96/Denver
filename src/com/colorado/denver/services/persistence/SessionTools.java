package com.colorado.denver.services.persistence;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.LoggerFactory;

import com.colorado.denver.model.Course;
import com.colorado.denver.model.Exercise;
import com.colorado.denver.model.Lecture;
import com.colorado.denver.model.Role;
import com.colorado.denver.model.Solution;
import com.colorado.denver.model.User;
import com.colorado.denver.tools.HibernateInterceptor;

public class SessionTools {

	public static SessionFactory sessionFactory;// SINGLETON!
	private static ServiceRegistry serviceRegistry;
	public static Session session;

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SessionTools.class);

	public static void createSessionFactory(boolean useUpdateRoutine) {
		try {
			// Create configuration instance
			Configuration configuration = new Configuration();

			// Pass hibernate configuration file
			configuration.configure("hibernate.cfg.xml");

			Properties prop = new Properties();
			if (useUpdateRoutine) {
				prop.setProperty("hibernate.hbm2ddl.auto", "update");
			} else {
				prop.setProperty("hibernate.hbm2ddl.auto", "create");
			}
			configuration.addProperties(prop);
			LOGGER.info("Try using UpdateRoutine: " + useUpdateRoutine);
			LOGGER.info("Hibernate mode: " + configuration.getProperty("hibernate.hbm2ddl.auto"));
			// Since version 4.x, service registry is being used
			serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			LOGGER.info("Creating session factory instance!");

			// Create session factory instance

			sessionFactory = configuration.addPackage("com.colorado").addProperties(prop).addAnnotatedClass(Role.class)
					.addAnnotatedClass(User.class).addAnnotatedClass(Exercise.class)
					.addAnnotatedClass(Lecture.class).addAnnotatedClass(Course.class).addAnnotatedClass(Solution.class)
					.setInterceptor(new HibernateInterceptor())
					.buildSessionFactory(serviceRegistry);

			// TODO: Drop sequences on create!

			LOGGER.info("Created session factory instance!");
			// Get current session
			session = sessionFactory.getCurrentSession();
			// Begin transaction
			session.getTransaction().begin();

			// Print out all required information
			System.out.println("Session Is Opened : " + session.isOpen());
			System.out.println("Session Is Connected : " + session.isConnected());

			// Commit transaction
			session.getTransaction().commit();
			LOGGER.info("Session opened and committed!!");
		} catch (Throwable ex) {
			LOGGER.error("Failed to create sessionFactory" + ex);
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}

	}

}
