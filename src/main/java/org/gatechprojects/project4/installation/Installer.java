package org.gatechprojects.project4.installation;

import org.gatechprojects.project4.DAL.Blackboard;
import org.gatechprojects.project4.DAL.DatabaseConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Installer {

	public static void main(String[] args) {
		Installer installer = new Installer();
		installer.install();
	}

	private void initializeDatabaseSchema() {
		DatabaseConfiguration dbConfig = new DatabaseConfiguration();
		Configuration configuration = dbConfig.buildConfiguration().configure();
		configuration.setProperty("hibernate.hbm2ddl.auto", "create");
		SessionFactory factory = configuration.buildSessionFactory();
		Session session = factory.openSession();
		session.close();
		factory.close();
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
