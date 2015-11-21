package org.gatechprojects.project4.BAL;

import org.gatechproject.project4.BAL.dto.Professor;
import org.gatechproject.project4.BAL.dto.TeacherAssistant;
import org.gatechprojects.project4.DAL.Blackboard;
import org.gatechprojects.project4.SharedDataModules.User;

public class StaffService {
	private Blackboard blackboard;

	/**
	 * Default public constructor. A Blackboard instance will be created using
	 * {@link Blackboard blackboards} default constructor.
	 */
	public StaffService() {
		blackboard = new Blackboard();
		blackboard.load();
	}

	/**
	 * Constructor which allows for injection of the blackboard.
	 * 
	 * @param blackboard
	 */
	public StaffService(Blackboard blackboard) {
		this.blackboard = blackboard;
	}

	/**
	 * 
	 * Indicates that an individual is able to teach the specified course. Note:
	 * It is expected that the passed in userId corresponds to a Professor. If
	 * the associated use is not a professor, and
	 * {@link IllegalArgumentException} will be thrown.
	 * 
	 * @param courseId
	 * @param userId
	 */
	public void addCourseCompetency(int courseId, int userId) {
		User user = blackboard.getUserBoard().getUser(userId);
		if (!user.isProfessor()) {
			throw new IllegalArgumentException("Passed in user is not a professor");
		}
		blackboard.startTransaction();
		blackboard.getUserBoard().addProfessorCompetency(courseId, userId);
		blackboard.commitTransaction();

	}

	public Professor addProfessor(Integer membershipId, String firstName, String lastName) {
		blackboard.startTransaction();
		int userId = blackboard.getUserBoard().addUser(membershipId, firstName, lastName, false, false, true, false);
		blackboard.commitTransaction();
		return new Professor(blackboard.getUserBoard().getUser(userId));
	}

	public TeacherAssistant addTeacherAssistant(Integer membershipId, String firstName, String lastName) {
		blackboard.startTransaction();
		int userId = blackboard.getUserBoard().addUser(membershipId, firstName, lastName, false, true, false, false);
		blackboard.commitTransaction();
		return new TeacherAssistant(blackboard.getUserBoard().getUser(userId));
	}
}
