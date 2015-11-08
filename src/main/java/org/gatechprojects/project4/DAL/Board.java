package org.gatechprojects.project4.DAL;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.google.common.base.Preconditions;

public abstract class Board {
	private Transaction transaction;
	private Session session;

	public Board(Transaction transaction, Session session) {
		this.transaction = transaction;
		this.session = session;
	}

	protected Session getSession() {
		return session;
	}

	protected void verifyTransaction() {
		Preconditions.checkState(transaction.getStatus() == TransactionStatus.ACTIVE);
	}
}
