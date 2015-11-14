package org.gatechprojects.project4.DAL;

import java.util.List;

import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.CourseSemester;
import org.gatechprojects.project4.SharedDataModules.Semester;
import org.gatechprojects.project4.SharedDataModules.UserAvailability;
import org.hibernate.Session;
import org.hibernate.Transaction;
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

	public void clearCurrentSemesterConfigurations(int semesterId) {
		String hql = "delete from course_semester where semester_id = :semesterId";
		getSession().createQuery(hql).setInteger("semesterId", semesterId).executeUpdate();
		hql = "delete from user_availability where semester_id = :semesterId";
		getSession().createQuery(hql).setInteger("semesterId", semesterId).executeUpdate();
	}

	public int createCourse(String name, int nbrCredits) {
		Course course = new Course();
		course = populateCourse(course, name, nbrCredits);
		return (int) getSession().save(course);
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

	public Semester getSemester(int semesterId) {
		return getSession().get(Semester.class, semesterId);
	}

	public List<CourseSemester> getSemesterCourses(int semesterId) {
		return getSession().createCriteria(CourseSemester.class).add(Restrictions.eq("semester_id", semesterId)).list();
	}

	public List<Semester> getSemesters() {
		return getSession().createCriteria(Semester.class).list();
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
