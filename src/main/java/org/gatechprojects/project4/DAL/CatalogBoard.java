package org.gatechprojects.project4.DAL;

import java.util.ArrayList;
import java.util.List;

import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.CourseSemester;
import org.gatechprojects.project4.SharedDataModules.CourseTaken;
import org.gatechprojects.project4.SharedDataModules.Semester;
import org.gatechprojects.project4.SharedDataModules.UserAvailability;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class CatalogBoard extends Board {

	public CatalogBoard(Transaction transaction, Session session) {
		super(transaction, session);
	}

	public int addCourseSemester(CourseSemester courseSemester) {
		return (int) getSession().save(courseSemester);

	}

	public int addUserAvailability(UserAvailability userAvailability) {
		return (Integer) getSession().save(userAvailability);

	}

	/**
	 * Clears the {@link CourseSemester} and {@link UserAvailability} for the
	 * provided semesterId and shadow status.
	 * 
	 * @param semesterId
	 * @param isShadow
	 */
	public void clearCurrentSemesterConfigurations(int semesterId, boolean isShadow) {
		String hql = "delete from CourseSemester where semester.id = :semesterId and isShadow = :isShadow";
		getSession().createQuery(hql).setInteger("semesterId", semesterId).setBoolean("isShadow", isShadow)
				.executeUpdate();
		hql = "delete from UserAvailability where semester.id = :semesterId and isShadow = :isShadow";
		getSession().createQuery(hql).setInteger("semesterId", semesterId).setBoolean("isShadow", isShadow)
				.executeUpdate();
	}

	public int createCourse(String name, int nbrCredits) {
		Course course = new Course();
		course = populateCourse(course, name, nbrCredits);
		return (int) getSession().save(course);
	}

	public void createCourseTaken(CourseTaken courseTaken) {
		verifyTransaction();
		getSession().save(courseTaken);

	}

	public int createSemester(String name, int year) {
		Semester semester = new Semester();
		semester = populateSemester(semester, name, year);
		return (int) getSession().save(semester);
	}

	public List<Course> getAvailableCourses() {
		return getSession().createCriteria(Course.class).list();
	}

	public Course getCourse(int courseId) {
		return getSession().get(Course.class, courseId);
	}

	public CourseSemester getMostRecentCourseSemester(int semesterId, boolean isShadow) {
		List<CourseSemester> courseSemesters = getSession().createCriteria(CourseSemester.class)
				.add(Restrictions.eq("semester.id", semesterId)).add(Restrictions.eq("isShadow", isShadow))
				.addOrder(Order.desc("id")).setFirstResult(0).setMaxResults(1).list();
		if (courseSemesters.size() > 0) {
			return courseSemesters.get(0);
		}
		return null;
	}

	public UserAvailability getMostRecentUserAvailability(int semesterId, boolean isShadow) {
		List<UserAvailability> availabilities = getSession().createCriteria(UserAvailability.class)
				.add(Restrictions.eq("semester.id", semesterId)).add(Restrictions.eq("isShadow", isShadow))
				.addOrder(Order.desc("id")).setFirstResult(0).setMaxResults(1).list();
		if (availabilities.size() > 0) {
			return availabilities.get(0);
		}
		return null;
	}

	public Semester getSemester(int semesterId) {
		return getSession().get(Semester.class, semesterId);
	}

	public List<CourseSemester> getSemesterCourses(int semesterId, boolean isShadow) {
		return new ArrayList<CourseSemester>(
				getSession().createCriteria(CourseSemester.class).add(Restrictions.eq("semester.id", semesterId))
						.add(Restrictions.eqOrIsNull("isShadow", isShadow)).list());
	}

	public List<Semester> getSemesters() {
		return getSession().createCriteria(Semester.class).list();
	}
	
	public Semester getMostRecentSemester() {
		List<Semester> currentSemester = getSession().createCriteria(Semester.class).addOrder(Order.desc("id")).setFirstResult(0).setMaxResults(1).list();
		if (currentSemester.size() > 0) {
			return currentSemester.get(0);
		}
		return null;
	}
	

	private Course populateCourse(Course course, String name, int nbrCredits) {
		course.setName(name);
		course.setCredits(nbrCredits);
		return course;
	}

	private Semester populateSemester(Semester semester, String name, int year) {
		semester.setName(name);
		semester.setYear(year);
		return semester;
	}

}
