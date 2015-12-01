package org.gatechprojects.project4.BAL;

import java.util.ArrayList;
import java.util.List;

import org.gatechproject.project4.BAL.dto.Professor;
import org.gatechproject.project4.BAL.dto.TeacherAssistant;
import org.gatechprojects.project4.DAL.Blackboard;
import org.gatechprojects.project4.SharedDataModules.Course;
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

	/**
	 * Adds a {@link Professor} to the system. Passing in membershipId is
	 * optional, and only expected if the user is expected to be able to login
	 * to the system.
	 * 
	 * @param membershipId
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	public Professor addProfessor(Integer membershipId, String firstName, String lastName) {
		blackboard.startTransaction();
		int userId = blackboard.getUserBoard().addUser(membershipId, firstName, lastName, false, false, true, false);
		blackboard.commitTransaction();
		return new Professor(blackboard.getUserBoard().getUser(userId));
	}

	/**
	 * Method used to indicate that a user, generally a professor or ta, is
	 * available for the specified semester.
	 * 
	 * @param userId
	 * @param semesterId
	 * @return
	 */
	public int addStaffAvailability(int userId, int semesterId, boolean isShadow) {
		blackboard.startTransaction();
		int id = blackboard.getUserBoard().addUserAvailability(userId, semesterId, isShadow);
		blackboard.commitTransaction();
		return id;

	}

	/**
	 * Adds a {@link TeacherAssistant} to the system. Passing in membershipId is
	 * optional, and only expected if the user is expected to be able to login
	 * to the system.
	 * 
	 * @param membershipId
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	public TeacherAssistant addTeacherAssistant(Integer membershipId, String firstName, String lastName) {
		blackboard.startTransaction();
		int userId = blackboard.getUserBoard().addUser(membershipId, firstName, lastName, false, true, false, false);
		blackboard.commitTransaction();
		return new TeacherAssistant(blackboard.getUserBoard().getUser(userId));
	}

	/**
	 * Returns a list of {@link Course courses} which a professor is capable of
	 * teaching.
	 * 
	 * @param userId
	 * @return
	 */
	public List<Course> getAvailableProfessorCompetencies(int userId) {

		User user = blackboard.getUserBoard().getUser(userId);
		if (!user.isProfessor()) {
			throw new IllegalArgumentException("Passed in user is not a professor");
		}
		blackboard.startTransaction();
		List<Course> courses = blackboard.getUserBoard().getProfessorCompetencies(userId);
		blackboard.commitTransaction();

		return courses;
	}

	/**
	 * Returns a list of {@link Professor professors} which are available to the
	 * program.
	 * 
	 * @return
	 */
	public List<Professor> getAvailableProfessors() {
		List<User> users = blackboard.getUserBoard().getAvailableUsersByType(false, false, true);
		List<Professor> professors = new ArrayList<Professor>();
		for (User user : users) {
			professors.add(new Professor(user));
		}
		return professors;
	}

	/**
	 * Returns a List of {@link TeacherAssistant tas} which are available for
	 * the program.
	 * 
	 * @return
	 */
	public List<TeacherAssistant> getAvailableTeacherAssistants() {
		List<User> users = blackboard.getUserBoard().getAvailableUsersByType(false, true, false);
		List<TeacherAssistant> tas = new ArrayList<TeacherAssistant>();
		for (User user : users) {
			tas.add(new TeacherAssistant(user));
		}
		return tas;
	}
}
