package org.gatechprojects.project4.BAL;

import org.gatechprojects.project4.DAL.Blackboard;

public class Membership {

	private Blackboard blackboard;

	/**
	 * Default public contructor. A Blackboard instance will be created using
	 * {@link Blackboard blackboards} default constructor.
	 */
	public Membership() {
		blackboard = new Blackboard();
		blackboard.load();
	}

	/**
	 * Constructor which allows for injection of the blackboard.
	 * 
	 * @param blackboard
	 */
	public Membership(Blackboard blackboard) {
		this.blackboard = blackboard;
	}

	public boolean authenticate(String userName, String password) {
		return true;
	}

	public boolean hasRole(MembershipRole role) {
		return true;
	}

	public int registerMember(String userName, String password) {
		blackboard.startTransaction();
		int membershipId = blackboard.getMembershipBoard().registerMember(userName, password);
		blackboard.commitTransaction();
		return membershipId;
	}
}
