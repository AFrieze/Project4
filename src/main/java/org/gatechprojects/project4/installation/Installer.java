package org.gatechprojects.project4.installation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Installer {

	public static void main(String[] args) {
		Installer installer = new Installer();
		installer.install();
	}

	public void install() {
		initializeDatabaseSchema();
		seedData();

	}

	private void initializeDatabaseSchema() {
		Configuration configuration = new Configuration().configure();
		configuration.setProperty("hibernate.hbm2ddl.auto", "create");
		SessionFactory factory = configuration.buildSessionFactory();
		Session session = factory.openSession();
		session.close();
		factory.close();
	}

	private void seedData() {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		// Seed Users
		// Seed Courses
		session.close();
		factory.close();
	}

}
