package org.gatechprojects.project4.DAL;

import java.util.List;

import org.gatechprojects.project4.SharedDataModules.MembershipUser;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class MembershipBoard extends Board {

	public MembershipBoard(Transaction transaction, Session session) {
		super(transaction, session);
	}

	public boolean authenticate(String userName, String password) {
		List users = getSession().createCriteria(MembershipUser.class).add(Restrictions.eq("userName", userName))
				.add(Restrictions.eq("password", password)).setFetchSize(1).list();
		return users.size() == 1;
	}

	public Integer registerMember(String userName, String password) {
		MembershipUser user = new MembershipUser();
		user.setUserName(userName);
		user.setPassword(password);
		return (Integer) getSession().save(user);
	}

}
