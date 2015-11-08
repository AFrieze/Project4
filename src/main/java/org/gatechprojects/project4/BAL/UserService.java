package org.gatechprojects.project4.BAL;

import org.gatechprojects.project4.DAL.Blackboard;
import org.gatechprojects.project4.SharedDataModules.User;

public class UserService {

	private Blackboard blackboard;

	/**
	 * Default public contructor. A Blackboard instance will be created using
	 * {@link Blackboard blackboards} default constructor.
	 */
	public UserService() {
		blackboard = new Blackboard();
		blackboard.load();
	}

	/**
	 * Constructor which allows for injection of the blackboard.
	 * 
	 * @param blackboard
	 */
	public UserService(Blackboard blackboard) {
		this.blackboard = blackboard;
	}

	public int addUser(String firstName, String lastName, boolean isStudent, boolean isTA, boolean isProfessor) {
		blackboard.startTransaction();
		int userId = blackboard.getUserBoard().addUser(firstName, lastName, isStudent, isTA, isProfessor);
		blackboard.commitTransaction();
		return userId;
	}

	public User getUser(int userId) {
		return blackboard.getByID(User.class, userId);
	}

	public void updateUser(int userId, String firstName, String lastName, boolean isStudent, boolean isTA,
			boolean isProfessor) {
		blackboard.startTransaction();
		blackboard.getUserBoard().updateUser(userId, firstName, lastName, isStudent, isTA, isProfessor);
		blackboard.commitTransaction();
	}

	public void updateUserPreferences(int userId, int semesterId, int desiredNumberCourses, int... desiredCourseIds) {
		blackboard.startTransaction();
		blackboard.getUserBoard().updateUserPreferences(userId, semesterId, desiredNumberCourses, desiredCourseIds);
		blackboard.commitTransaction();
	}

}
