package org.gatechprojects.project4.DAL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.MembershipUser;
import org.gatechprojects.project4.SharedDataModules.OutputUserCourseAssignment;
import org.gatechprojects.project4.SharedDataModules.ProfessorCompetence;
import org.gatechprojects.project4.SharedDataModules.Semester;
import org.gatechprojects.project4.SharedDataModules.StudentCoursePreference;
import org.gatechprojects.project4.SharedDataModules.StudentPreference;
import org.gatechprojects.project4.SharedDataModules.User;
import org.gatechprojects.project4.SharedDataModules.UserAvailability;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
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

	public int addUserAvailability(int userId, int semesterId, boolean isShadow) {
		UserAvailability ua = new UserAvailability();
		ua.setUser(getSession().byId(User.class).load(userId));
		ua.setSemester(getSession().byId(Semester.class).load(semesterId));
		ua.setShadow(isShadow);
		return (Integer) getSession().save(ua);
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
			boolean isProfessor, int semesterId, boolean isShadow) {
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
		hql = hql + ") and ua.isShadow = :isShadow";

		return getSession().createQuery(hql).setParameter("isShadow", isShadow).list();

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

	public StudentPreference getMostRecentStudentPreference(int semesterId) {
		List<StudentPreference> studentPreferences = getSession().createCriteria(StudentPreference.class)
				.add(Restrictions.eq("semester.id", semesterId)).addOrder(Order.desc("id")).setFirstResult(0)
				.setMaxResults(1).list();
		if (studentPreferences.size() > 0) {
			return studentPreferences.get(0);
		}
		return null;
	}

	public List<Course> getProfessorCompetencies(int userId) {
		Query query = getSession()
				.createQuery(
						"select c from course c join professor_competence pc on c.id = pc.course_id where pc.user_id=:userId")
				.setString("userId", Integer.toString(userId));
		List<Course> courses = query.list();
		return courses;
	}

	public List<OutputUserCourseAssignment> getStudentCourseAssignments(int studentID, int optimizerCalculationID) {
		return getSession().createCriteria(OutputUserCourseAssignment.class).add(Restrictions.eq("user.id", studentID))
				.add(Restrictions.eq("optimizerCalculation.id", optimizerCalculationID)).list();
	}

	public StudentPreference getStudentPreference(int userId, int semesterId) {
		return (StudentPreference) getSession().createCriteria(StudentPreference.class)
				.add(Restrictions.eq("user.id", userId)).add(Restrictions.eq("semester.id", semesterId)).uniqueResult();
	}

	public List<StudentPreference> getStudentPreferences(int semesterId) {
		return getSession().createCriteria(StudentPreference.class).add(Restrictions.eq("semester.id", semesterId))
				.list();
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

	/**
	 * 
	 * Method for login and session management
	 * 
	 * @param userName
	 * @param password
	 * @return User
	 */

	public User getUserByMembershipUsernameAndPassword(String userName, String password) {

		String hql = "from User as u where u.membership.userName = :userName and u.membership.password = :password";
		List users = getSession().createQuery(hql).setParameter("userName", userName).setParameter("password", password)
				.setFetchSize(1).list();
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

	public int updateUserPreferences(int userId, int semesterId, int desiredNumberCourses,
			Integer... desiredCourseIds) {
		verifyTransaction();

		// ugly child delete..couldn't figure out how to set cascade in the
		// database
		StudentPreference preference = (StudentPreference) getSession().createCriteria(StudentPreference.class)
				.setCacheable(false).add(Restrictions.eq("user.id", userId))
				.add(Restrictions.eq("semester.id", semesterId)).uniqueResult();
		if (preference != null) {
			Iterator<StudentCoursePreference> iterator = preference.getCoursePreferences().iterator();
			while (iterator.hasNext()) {
				StudentCoursePreference p = iterator.next();
				// String hql = "delete from StudentCoursePreference where id =
				// :id";
				getSession().delete(p);
				// getSession().createQuery(hql).setInteger("id",
				// p.getId()).executeUpdate();
				iterator.remove();
			}
		}
		getSession().flush();
		String hql = "delete from StudentPreference where semester.id = :semesterId and user.id = :userId";
		getSession().createQuery(hql).setInteger("semesterId", semesterId).setInteger("userId", userId).executeUpdate();

		// add new preferences
		Semester semester = getSession().get(Semester.class, semesterId);
		StudentPreference preferences = new StudentPreference();
		preferences.setUser(getUser(userId));
		preferences.setDesiredNumberCourses(desiredNumberCourses);
		preferences.setSemester(semester);
		List<StudentCoursePreference> coursePreferences = new ArrayList<StudentCoursePreference>();
		for (int desiredCourseId : desiredCourseIds) {
			Course course = getSession().get(Course.class, desiredCourseId);
			StudentCoursePreference p = new StudentCoursePreference();
			p.setCourse(course);
			p.setStudentPreference(preferences);
			coursePreferences.add(p);
		}
		preferences.setCoursePreferences(coursePreferences);
		Integer prefID = (Integer) getSession().save(preferences);
		for (StudentCoursePreference cp : coursePreferences) {
			getSession().save(cp);
		}
		return prefID;
	}

}
