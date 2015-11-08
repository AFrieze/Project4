package org.gatechprojects.project4.DAL;

import java.util.TreeSet;

import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.Semester;
import org.gatechprojects.project4.SharedDataModules.StudentCoursePreference;
import org.gatechprojects.project4.SharedDataModules.StudentPreference;
import org.gatechprojects.project4.SharedDataModules.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.google.common.base.Preconditions;

public class UserBoard {

	private Transaction transaction;
	private Session session;

	UserBoard(Transaction transaction, Session session) {
		this.transaction = transaction;
		this.session = session;
	}

	public int addUser(String firstName, String lastName, boolean isStudent, boolean isTA, boolean isProfessor) {
		verifyTransaction();
		User user = new User();
		populateUser(user, firstName, lastName, isStudent, isTA, isProfessor);
		return (Integer) session.save(user);
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
		User user = session.get(User.class, userId);
		populateUser(user, firstName, lastName, isStudent, isTA, isProfessor);
	}

	public void updateUserPreferences(int userId, int semesterId, int desiredNumberCourses, int... desiredCourseIds) {
		verifyTransaction();
		User user = session.get(User.class, userId);
		Semester semester = session.get(Semester.class, semesterId);
		StudentPreference preferences = null;
		preferences.setDesiredNumberCourses(desiredNumberCourses);
		// preferences.setSemester(semester);
		TreeSet<StudentCoursePreference> coursePreferences = new TreeSet<StudentCoursePreference>();
		for (int desiredCourseId : desiredCourseIds) {
			Course course = session.get(Course.class, desiredCourseId);
			StudentCoursePreference p = new StudentCoursePreference();
			// p.setCourse(course);
			coursePreferences.add(p);
		}
	}

	private void verifyTransaction() {
		Preconditions.checkState(transaction.getStatus() == TransactionStatus.ACTIVE);
	}
}
