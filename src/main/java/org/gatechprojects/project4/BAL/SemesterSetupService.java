package org.gatechprojects.project4.BAL;

import java.util.List;

import org.gatechprojects.project4.DAL.Blackboard;
import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.Semester;

public class SemesterSetupService {

	private Blackboard blackboard;

	/**
	 * Default public contructor. A Blackboard instance will be created using
	 * {@link Blackboard blackboards} default constructor.
	 */
	public SemesterSetupService() {
		blackboard = new Blackboard();
		blackboard.load();
	}

	/**
	 * Constructor which allows for injection of the blackboard.
	 * 
	 * @param blackboard
	 */
	public SemesterSetupService(Blackboard blackboard) {
		this.blackboard = blackboard;
	}

	public int addCourse(String name, int nbrCredits) {
		blackboard.startTransaction();
		int courseId = blackboard.getCatalogBoard().createCourse(name, nbrCredits);
		blackboard.commitTransaction();
		return courseId;
	}

	public int addSemester(String name, int year) {
		blackboard.startTransaction();
		int semesterId = blackboard.getCatalogBoard().createSemester(name, year);
		blackboard.commitTransaction();
		return semesterId;
	}

	public List<Semester> getAvailableSemesters() {
		return blackboard.getCatalogBoard().getSemesters();
	}

	public Course getCourse(int courseId) {
		return blackboard.getCatalogBoard().getCourse(courseId);
	}

	public List<Course> getCoursesForSemester(int semesterId) {
		return null;
	}

	public Semester getSemester(int semesterId) {
		return blackboard.getByID(Semester.class, semesterId);
	}
}
