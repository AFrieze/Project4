package project4;

import org.gatechprojects.project4.DAL.DatabaseConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class IntegrationTestDB {

	private Session session;

	public void endTestingSession() {
		session.close();
	}

	public void startTestingSession() {
		DatabaseConfiguration dbConfig = new DatabaseConfiguration();
		Configuration configuration = dbConfig.buildConfiguration().configure();
		configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		SessionFactory factory = configuration.buildSessionFactory();
		this.session = factory.openSession();
	}

}
