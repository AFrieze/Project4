package org.gatechprojects.project4.BAL;

import org.gatechproject.project4.BAL.dto.Student;
import org.gatechprojects.project4.DAL.Blackboard;
import org.gatechprojects.project4.SharedDataModules.User;

public class UserService {

	private Blackboard blackboard;

	/**
	 * Default public constructor. A Blackboard instance will be created using
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

	/**
	 * Adds a student to the program.
	 * 
	 * @param membershipId
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	public Student addStudent(Integer membershipId, String firstName, String lastName) {
		blackboard.startTransaction();
		int userId = blackboard.getUserBoard().addUser(membershipId, firstName, lastName, true, false, false, false);
		blackboard.commitTransaction();
		return new Student(blackboard.getUserBoard().getUser(userId));
	}

	public Student getStudentById(int userId) {
		Student student = null;
		User user = blackboard.getUserBoard().getUser(userId);
		if (user != null) {
			student = new Student(user);
		}
		return student;
	}

	public Student getStudentByMembershipId(int membershipId) {
		Student student = null;
		User user = blackboard.getUserBoard().getUserByMembershipId(membershipId);
		if (user != null) {
			student = new Student(user);
		}
		return student;
	}

	public void updateStudentPreferences(int userId, int semesterId, int desiredNumberCourses,
			int... desiredCourseIds) {
		blackboard.startTransaction();
		blackboard.getUserBoard().updateUserPreferences(userId, semesterId, desiredNumberCourses, desiredCourseIds);
		blackboard.commitTransaction();
	}

}
