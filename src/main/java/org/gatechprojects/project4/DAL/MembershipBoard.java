package org.gatechprojects.project4.DAL;

import java.util.List;

import org.gatechprojects.project4.SharedDataModules.MembershipUser;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * 
 * The membership board is responsible for user authentication and registration.
 * 
 * @author Andrew
 *
 */
public class MembershipBoard extends Board {

	public MembershipBoard(Transaction transaction, Session session) {
		super(transaction, session);
	}

	/**
	 * 
	 * Returns true if the provided userName and password are correctly matched
	 * to a {@link MembershipUser}.
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean authenticate(String userName, String password) {
		List users = getSession().createCriteria(MembershipUser.class).add(Restrictions.eq("userName", userName))
				.add(Restrictions.eq("password", password)).setFetchSize(1).list();
		return users.size() == 1;
	}

	/**
	 * Creates a new {@link MembershipUser} in the system. The
	 * {@link MembershipUser#getId() membershipId} is returned.
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public Integer registerMember(String userName, String password) {
		MembershipUser user = new MembershipUser();
		user.setUserName(userName);
		user.setPassword(password);
		return (Integer) getSession().save(user);
	}

}
