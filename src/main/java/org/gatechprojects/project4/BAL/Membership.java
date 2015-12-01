package org.gatechprojects.project4.BAL;

import org.gatechprojects.project4.DAL.Blackboard;
import org.gatechprojects.project4.SharedDataModules.MembershipUser;
import org.gatechprojects.project4.SharedDataModules.User;

/**
 * Service used to create {@link MembershipUser membershipUsers} which are
 * capable of logging into the system.
 * 
 * @author afrieze
 *
 */
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

	/**
	 * 
	 * Returns true if the provided userName and password are correctly matched
	 * to a user in the system with login credentials
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean authenticate(String userName, String password) {
		return blackboard.getMembershipBoard().authenticate(userName, password);
	}

	/**
	 * Creates a new membershipUser in the system. Users created in this manner
	 * may login to the system.
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public int registerMember(String userName, String password) {
		blackboard.startTransaction();
		int membershipId = blackboard.getMembershipBoard().registerMember(userName, password);
		blackboard.commitTransaction();
		return membershipId;
	}

	/**
	 * 
	 * Returns the User object for a given login
	 * 
	 * @param userName
	 * @param password
	 * @return User
	 */
	public User getUser(String userName, String password) {
		return blackboard.getUserBoard().getUserByMembershipUsernameAndPassword(userName, password);
	}
}
