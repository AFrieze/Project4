package org.gatechprojects.project4.DAL;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.MembershipUser;
import org.gatechprojects.project4.SharedDataModules.ProfessorCompetence;
import org.gatechprojects.project4.SharedDataModules.Semester;
import org.gatechprojects.project4.SharedDataModules.StudentCoursePreference;
import org.gatechprojects.project4.SharedDataModules.StudentPreference;
import org.gatechprojects.project4.SharedDataModules.User;
import org.gatechprojects.project4.SharedDataModules.UserAvailability;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

public class UserBoard extends Board {

	UserBoard(Transaction transaction, Session session) {
		super(transaction, session);
	}

	public void addProfessorCompetency(int courseId, int userId) {
		ProfessorCompetence pc = new ProfessorCompetence();
		Course course = getSession().get(Course.class, courseId);
		User user = getSession().get(User.class, userId);
		pc.setCourse(course);
		pc.setUser(user);
		getSession().save(pc);

	}

	public int addUser(Integer membershipId, String firstName, String lastName, boolean isStudent, boolean isTA,
			boolean isProfessor, boolean isAdministrator) {
		verifyTransaction();
		User user = new User();
		populateUser(membershipId, user, firstName, lastName, isStudent, isTA, isProfessor, isAdministrator);
		return (Integer) getSession().save(user);
	}

	private Criterion[] buildUserTypeCriterion(boolean isStudent, boolean isTA, boolean isProfessor) {
		List<SimpleExpression> restrictions = new ArrayList<SimpleExpression>();
		if (isStudent) {
			restrictions.add(Restrictions.eq("isStudent", isStudent));
		}
		if (isTA) {
			restrictions.add(Restrictions.eq("isTA", isTA));
		}
		if (isProfessor) {
			restrictions.add(Restrictions.eq("isProfessor", isProfessor));
		}
		return restrictions.toArray(new Criterion[0]);
	}

	public List<UserAvailability> getAvailableUsersBySemesterAndType(boolean isStudent, boolean isTA,
			boolean isProfessor, int semesterId) {
		Criterion[] userTypeCriterion = buildUserTypeCriterion(isStudent, isTA, isProfessor);
		if (userTypeCriterion.length == 0) {
			return new ArrayList<UserAvailability>();
		}

		List<String> restrictions = new ArrayList<String>();
		if (isStudent) {
			restrictions.add("ua.user.isStudent = true");
		} else if (isTA) {
			restrictions.add("ua.user.isTA = true");
		} else if (isProfessor) {
			restrictions.add("ua.user.isProfessor = true");
		}
		if (restrictions.size() == 0) {
			return new ArrayList<UserAvailability>();
		}

		String hql = "from UserAvailability as ua where (";
		for (int i = 0; i < restrictions.size(); i++) {
			if (i > 0) {
				hql = hql + " or";
			}
			hql = hql + " " + restrictions.get(i);
		}
		hql = hql + ")";

		return getSession().createQuery(hql).list();

		// return
		// getSession().createCriteria(User.class).add(Restrictions.eq("semester_id",
		// semesterId))
		// .add(Restrictions.or(userTypeCriterion)).list();
	}

	public List<User> getAvailableUsersByType(boolean isStudent, boolean isTA, boolean isProfessor) {
		Criterion[] userTypeCriterion = buildUserTypeCriterion(isStudent, isTA, isProfessor);
		if (userTypeCriterion.length == 0) {
			return new ArrayList<User>();
		}

		return getSession().createCriteria(User.class).add(Restrictions.or(userTypeCriterion)).list();
	}

	public User getUser(int userId) {
		return getSession().get(User.class, userId);
	}

	public User getUserByMembershipId(int membershipId) {

		String hql = "from User as u where u.membership.id = :membershipId";
		List users = getSession().createQuery(hql).setParameter("membershipId", membershipId).setFetchSize(1).list();
		if (users.size() == 1) {
			return (User) users.get(0);
		}
		return null;
	}

	private void populateUser(Integer membershipId, User user, String firstName, String lastName, boolean isStudent,
			boolean isTA, boolean isProfessor, boolean isAdministrator) {

		if (membershipId != null) {
			user.setMembership(getSession().get(MembershipUser.class, membershipId));
		}
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setStudent(isStudent);
		user.setTA(isTA);
		user.setProfessor(isProfessor);
		user.setAdministrator(isAdministrator);
	}

	public void updateUser(Integer membershipId, int userId, String firstName, String lastName, boolean isStudent,
			boolean isTA, boolean isProfessor, boolean isAdministrator) {
		verifyTransaction();
		User user = getSession().get(User.class, userId);
		populateUser(membershipId, user, firstName, lastName, isStudent, isTA, isProfessor, isAdministrator);
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
