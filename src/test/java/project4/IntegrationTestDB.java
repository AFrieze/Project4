package project4;

import org.gatechprojects.project4.DAL.DatabaseConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class IntegrationTestDB {

	private SessionFactory factory = null;
	private Session session;

	public void endTestingSession() {
		session.close();
	}

	public void startTestingSession() {
		if (factory == null) {
			DatabaseConfiguration dbConfig = new DatabaseConfiguration();
			Configuration configuration = dbConfig.buildConfiguration().configure();
			configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
			this.factory = configuration.buildSessionFactory();
		}
		this.session = factory.openSession();
	}

}
