package org.gatechprojects.project4.controller;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.gatechprojects.project4.SharedDataModules.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ControllerService {

	public static void main(String[] args) throws IOException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		ControllerService service = new ControllerService();
		Runnable serviceProcess = () -> {
			service.start();
		};
		executor.submit(serviceProcess);
		// exit on input to console
		System.in.read();
		try {
			executor.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// LOG.error("executor inturrpted during shutdown, controller
			// exiting with error");
			System.exit(-1);
		}
		// LOG.info("Controller exiting normally");
		System.exit(0);
	}

	// static final Logger LOG =
	// LogManager.getLogger(ControllerService.class.getName());
	private ExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	public void start() {
		try {
			SessionFactory factory = new Configuration().configure().buildSessionFactory();
			User user = new User();
			Session session = factory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(user);
			tx.commit();
		} catch (Exception e) {
			// LOG.error("Error in controller", e);
		}
	}

}
