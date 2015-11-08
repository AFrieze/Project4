package org.gatechprojects.project4.DAL;

import java.util.List;

import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.Semester;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CatalogBoard extends Board {

	public CatalogBoard(Transaction transaction, Session session) {
		super(transaction, session);
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

	public Course getCourse(int courseId) {
		return getSession().get(Course.class, courseId);
	}

	public Semester getSemester(int semesterId) {
		return getSession().get(Semester.class, semesterId);
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
