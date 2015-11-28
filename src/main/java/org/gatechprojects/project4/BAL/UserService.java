package org.gatechprojects.project4.BAL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.gatechproject.project4.BAL.dto.ConfiguredCourse;
import org.gatechproject.project4.BAL.dto.Student;
import org.gatechproject.project4.BAL.dto.StudentCourseRecommendation;
import org.gatechproject.project4.BAL.dto.StudentSemesterPreferences;
import org.gatechprojects.project4.DAL.Blackboard;
import org.gatechprojects.project4.SharedDataModules.MembershipUser;
import org.gatechprojects.project4.SharedDataModules.StudentPreference;
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

	/**
	 * Applies the passed in {@link StudentSemesterPreferences}. Any existing
	 * preferences for the {@link StudentSemesterPreferences#getUserId() user's}
	 * {@link StudentSemesterPreferences#getSemesterId() semester} will be
	 * cleared and replaced by those provided.
	 * 
	 * @param studentPreferences
	 */
	public void applyStudentPreferences(StudentSemesterPreferences studentPreferences) {
		List<Integer> courseIds = new ArrayList<Integer>();
		for (ConfiguredCourse cc : studentPreferences.getPreferredCourses()) {
			courseIds.add(cc.getCourseId());
		}
		blackboard.startTransaction();
		blackboard.getUserBoard().updateUserPreferences(studentPreferences.getUserId(),
				studentPreferences.getSemesterId(), studentPreferences.getNbrCoursesDesired(),
				courseIds.toArray(new Integer[] {}));
		blackboard.commitTransaction();
	}

	/**
	 * Fetches a {@link Student} based on the provided userId. If no student is
	 * found, null is returned.
	 * 
	 * @param userId
	 * @return
	 */
	public Student getStudentById(int userId) {
		Student student = null;
		User user = blackboard.getUserBoard().getUser(userId);
		if (user != null && user.isStudent()) {
			student = new Student(user);
		}
		return student;
	}

	/**
	 * Fetches a {@link Student} based on their {@link MembershipUser#getId()
	 * membershipId}. This is generally used to link an authenticated membership
	 * user to their catalog account.
	 * 
	 * @param membershipId
	 * @return
	 */
	public Student getStudentByMembershipId(int membershipId) {
		Student student = null;
		User user = blackboard.getUserBoard().getUserByMembershipId(membershipId);
		if (user != null) {
			student = new Student(user);
		}
		return student;
	}

	/**
	 * Returns the most recent list of recommended
	 * {@link StudentCourseRecommendation courseSolutions} which were generated
	 * before the passed in time.
	 * 
	 * @param time
	 * @return
	 */
	private List<StudentCourseRecommendation> getStudentCourseRecommendations(Calendar time) {
		return null;
	}

	/**
	 * Returns a list of all the {@link StudentSemesterPreferences} for the
	 * specified semesterId.
	 * 
	 * @param semesterId
	 * @return
	 */
	public List<StudentSemesterPreferences> getStudentPreferences(int semesterId) {
		List<StudentPreference> preferences = blackboard.getUserBoard().getStudentPreferences(semesterId);
		List<StudentSemesterPreferences> semesterPreferences = new ArrayList<StudentSemesterPreferences>();
		for (StudentPreference sp : preferences) {
			semesterPreferences.add(new StudentSemesterPreferences(sp, semesterId));
		}
		return semesterPreferences;
	}

	/**
	 * Returns the {@link StudentSemesterPreferences} for the provided user and
	 * semester. If no preferences currently exist, a default preferences object
	 * will be provided populated with the usersID and semester.
	 * 
	 * @param userId
	 * @param semesterId
	 * @return
	 */
	public StudentSemesterPreferences getStudentPreferences(int userId, int semesterId) {
		StudentPreference studentPreference = blackboard.getUserBoard().getStudentPreference(userId, semesterId);
		StudentSemesterPreferences preferences = null;
		if (studentPreference != null) {
			preferences = new StudentSemesterPreferences(studentPreference, semesterId);
		} else {
			preferences = new StudentSemesterPreferences();
			preferences.setSemesterId(semesterId);
			preferences.setUserId(userId);
		}
		return preferences;
	}

}
