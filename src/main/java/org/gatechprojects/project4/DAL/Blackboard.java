package org.gatechprojects.project4.DAL;

import java.io.Closeable;

import javax.annotation.concurrent.ThreadSafe;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.google.common.base.Preconditions;

@ThreadSafe
public class Blackboard implements Closeable

{
	private State state = State.LATENT;
	private State transactionState = State.LATENT;
	private Session session = null;
	private Transaction transaction = null;

	@Override
	public synchronized void close() {
		state = State.CLOSED;
		if (this.session != null) {
			this.session.close();
		}
	}

	public void commitTransaction() {
		Preconditions.checkArgument(transactionState == State.INITIALIZED);
		transactionState = State.CLOSED;
		transaction.commit();

	}

	public synchronized void load() {
		Preconditions.checkArgument(state == State.LATENT);
		DatabaseConfiguration dbConfig = new DatabaseConfiguration();
		SessionFactory factory = dbConfig.buildConfiguration().buildSessionFactory();
		this.session = factory.openSession();
		state = State.INITIALIZED;
	}

	public void rollbackTransaction() {
		Preconditions.checkArgument(transactionState == State.INITIALIZED);
		transactionState = State.CLOSED;
		transaction.rollback();
	}

	public void saveChanges() {

	}

	public void startTransaction() {
		Preconditions.checkArgument(transactionState == State.LATENT);
		transactionState = State.INITIALIZED;
		transaction = session.beginTransaction();
	}

}
