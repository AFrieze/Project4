package org.gatechproject.project4.BAL.dto;

import java.util.ArrayList;
import java.util.List;

import org.gatechprojects.project4.SharedDataModules.Course;

/**
 * This class will be used by the "Next Semester Courses" screen/View
 */

public class ViewStudentSemesterCourses {

	private Student student = null;
	private List<Course> availableCourses = new ArrayList<>();
	private List<Course> semesterCoursePlan = new ArrayList<>();
	
	public ViewStudentSemesterCourses() {
	}

	public ViewStudentSemesterCourses(Student student) {
		this.student = student;
		// TODO - Luc - need constructor to take data from database
		// TODO - Luc - need the object that will get us solution status and the solution
	}

	public Student getStudent() {
		return student;
	}

	public List<Course> getAvailableCourses() {
		return availableCourses;
	}

	public void setAvailableCourses(List<Course> availableCourses) {
		this.availableCourses = availableCourses;
	}

	public List<Course> getSemesterCoursePlan() {
		return semesterCoursePlan;
	}

	public void setSemesterCoursePlan(List<Course> semesterCoursePlan) {
		this.semesterCoursePlan = semesterCoursePlan;
	}

	
	
}
