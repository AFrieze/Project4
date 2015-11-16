package org.gatechprojects.project4.BAL;

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

}
