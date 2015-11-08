package org.gatechprojects.project4.DAL;

import java.util.TreeSet;

import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.Semester;
import org.gatechprojects.project4.SharedDataModules.StudentCoursePreference;
import org.gatechprojects.project4.SharedDataModules.StudentPreference;
import org.gatechprojects.project4.SharedDataModules.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserBoard extends Board {

	UserBoard(Transaction transaction, Session session) {
		super(transaction, session);
	}

	public int addUser(String firstName, String lastName, boolean isStudent, boolean isTA, boolean isProfessor) {
		verifyTransaction();
		User user = new User();
		populateUser(user, firstName, lastName, isStudent, isTA, isProfessor);
		return (Integer) getSession().save(user);
	}

	public User getUser(int userId) {
		return getSession().get(User.class, userId);
	}

	private void populateUser(User user, String firstName, String lastName, boolean isStudent, boolean isTA,
			boolean isProfessor) {
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setStudent(isStudent);
		user.setTA(isTA);
		user.setProfessor(isProfessor);
	}

	public void updateUser(int userId, String firstName, String lastName, boolean isStudent, boolean isTA,
			boolean isProfessor) {
		verifyTransaction();
		User user = getSession().get(User.class, userId);
		populateUser(user, firstName, lastName, isStudent, isTA, isProfessor);
	}

	public void updateUserPreferences(int userId, int semesterId, int desiredNumberCourses, int... desiredCourseIds) {
		verifyTransaction();
		User user = getSession().get(User.class, userId);
		Semester semester = getSession().get(Semester.class, semesterId);
		StudentPreference preferences = null;
		preferences.setDesiredNumberCourses(desiredNumberCourses);
		preferences.setSemester(semester);
		TreeSet<StudentCoursePreference> coursePreferences = new TreeSet<StudentCoursePreference>();
		for (int desiredCourseId : desiredCourseIds) {
			Course course = getSession().get(Course.class, desiredCourseId);
			StudentCoursePreference p = new StudentCoursePreference();
			p.setCourse(course);
			coursePreferences.add(p);
		}
	}

}
