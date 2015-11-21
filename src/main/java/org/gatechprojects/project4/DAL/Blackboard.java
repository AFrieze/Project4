package org.gatechprojects.project4.DAL;

import java.io.Closeable;

import javax.annotation.concurrent.ThreadSafe;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.google.common.base.Preconditions;

@ThreadSafe
public class Blackboard implements Closeable

{
	private State state = State.LATENT;
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
		Preconditions.checkNotNull(transaction);
		Preconditions.checkArgument(transaction.getStatus() == TransactionStatus.ACTIVE);
		transaction.commit();
		transaction = null;
	}

	public <T> T getByID(Class<T> classType, int id) {
		return session.get(classType, id);
	}

	public CatalogBoard getCatalogBoard() {
		return new CatalogBoard(transaction, session);
	}

	public MembershipBoard getMembershipBoard() {
		return new MembershipBoard(transaction, session);
	}

	public UserBoard getUserBoard() {
		return new UserBoard(transaction, session);
	}

	public synchronized void load() {
		Preconditions.checkArgument(state == State.LATENT);
		DatabaseConfiguration dbConfig = new DatabaseConfiguration();
		SessionFactory factory = dbConfig.buildConfiguration().buildSessionFactory();
		this.session = factory.openSession();
		state = State.INITIALIZED;
	}

	public void rollbackTransaction() {
		Preconditions.checkNotNull(transaction);
		Preconditions.checkArgument(transaction.getStatus() == TransactionStatus.ACTIVE);
		transaction.rollback();
	}

	public void startTransaction() {
		Preconditions.checkArgument(state == State.INITIALIZED);
		Preconditions.checkArgument(transaction == null);
		transaction = session.beginTransaction();
	}

}
