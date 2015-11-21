package org.gatechprojects.project4.installation;

import org.gatechprojects.project4.DAL.Blackboard;
import org.gatechprojects.project4.DAL.DatabaseConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Installer {

	// static final Logger LOG =
	// LogManager.getLogger(Installer.class.getName());

	public static void main(String[] args) {
		Installer installer = new Installer();
		installer.install();
	}

	private void initializeDatabaseSchema() {
		// LOG.info("Opening database purely to drop the schema");
		DatabaseConfiguration dbConfig = new DatabaseConfiguration();
		Configuration configuration = dbConfig.buildConfiguration().configure();
		configuration.setProperty("hibernate.hbm2ddl.auto", "update");
		SessionFactory factory = configuration.buildSessionFactory();
		Session session = factory.openSession();
		session.close();
		factory.close();
		//
		// LOG.info("Opening database to create new schema");
		// configuration = dbConfig.buildConfiguration().configure();
		// configuration.setProperty("hibernate.hbm2ddl.auto", "create");
		// factory = configuration.buildSessionFactory();
		// session = factory.openSession();
		// session.close();
		// factory.close();

	}

	public void install() {
		initializeDatabaseSchema();
		seedData();

	}

	private void seedData() {
		Blackboard blackBoard = new Blackboard();

		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		// Seed Users
		// Seed Courses
		session.close();
		factory.close();
	}

}
