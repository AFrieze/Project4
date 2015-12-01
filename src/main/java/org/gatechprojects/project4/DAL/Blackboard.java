package org.gatechprojects.project4.DAL;

import java.io.Closeable;

import javax.annotation.concurrent.ThreadSafe;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.google.common.base.Preconditions;

/**
 * The data, or blackboard, portion of the Blackboard architecure design. This
 * implementation utilizes a database behind the scenes in order to handle
 * persistence. Additionally, this blackboard represents a unit of work, and any
 * modifications to the blackboard must be made within a transaction by
 * utilizing the {@link #startTransaction()} and {@link #commitTransaction()}
 * methods.
 * 
 * @author ubuntu
 *
 */
@ThreadSafe
public class Blackboard implements Closeable

{
	private State state = State.LATENT;
	private Session session = null;
	private Transaction transaction = null;

	public synchronized void clearSession() {
		session.clear();
	}

	@Override
	public synchronized void close() {
		state = State.CLOSED;
		if (this.session != null) {
			this.session.close();
		}
	}

	/**
	 * Commits the transaction currently in progress.
	 */
	public void commitTransaction() {
		Preconditions.checkNotNull(transaction);
		Preconditions.checkArgument(transaction.getStatus() == TransactionStatus.ACTIVE);
		transaction.commit();
		transaction = null;
	}

	/**
	 * Returns an instance of the requested type from persistence which matched
	 * against the passed in id. If no match is found, null is returned.
	 * 
	 * @param classType
	 * @param id
	 * @return
	 */
	public <T> T getByID(Class<T> classType, int id) {
		return session.get(classType, id);
	}

	/**
	 * Returns a {@link CatalogBoard} which provides funcitonality specific to
	 * the program's catalog.
	 * 
	 * @return
	 */
	public CatalogBoard getCatalogBoard() {
		return new CatalogBoard(transaction, session);
	}

	/**
	 * Returns a {@link MembershipBoard} which provides functionality specific
	 * to the program's membership.
	 * 
	 * @return
	 */
	public MembershipBoard getMembershipBoard() {
		return new MembershipBoard(transaction, session);
	}

	/**
	 * Returns a {@link OptimizerBoard} which provides functionality specific to
	 * the optimizer.
	 * 
	 * @return
	 */
	public OptimizerBoard getOptimizerBoard() {
		return new OptimizerBoard(transaction, session);
	}

	/**
	 * 
	 * Returns a {@link MembershipBoard} which provides functionality specific
	 * to the program's student users.
	 * 
	 * @return
	 */
	public UserBoard getUserBoard() {
		return new UserBoard(transaction, session);
	}

	/**
	 * Loads this blackboard. Note that this method must be called before a
	 * transaction is started.
	 * 
	 */
	public synchronized void load() {
		Preconditions.checkArgument(state == State.LATENT);
		DatabaseConfiguration dbConfig = new DatabaseConfiguration();
		SessionFactory factory = dbConfig.buildConfiguration().buildSessionFactory();
		this.session = factory.openSession();
		state = State.INITIALIZED;
	}

	/**
	 * Cancels any pending changes and cancels the current transaction.
	 */
	public void rollbackTransaction() {
		Preconditions.checkNotNull(transaction);
		Preconditions.checkArgument(transaction.getStatus() == TransactionStatus.ACTIVE);
		transaction.rollback();
	}

	/**
	 * Begins a transaction. Generally uses in conjunction with
	 * {@link #commitTransaction()} or {@link #rollbackTransaction()}
	 */
	public void startTransaction() {
		Preconditions.checkArgument(state == State.INITIALIZED);
		Preconditions.checkArgument(transaction == null);
		transaction = session.beginTransaction();
	}

}
