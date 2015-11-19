package org.gatechproject.project4.BAL.reports;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gatechprojects.project4.SharedDataModules.Course;

/**
 * This class will contain all of the data for a solution for a student
 */

public class StudentSolution {

	private Date solutionDate = new Date();
	private List<Course> semesterPlan = new ArrayList<>();
	private List<Course> courseSolution = new ArrayList<>();

	// ===========================================================================

	public StudentSolution() {
	}

	// TODO - Luc - add constructor that will take in data from the database

	public Date getSolutionDate() {
		return solutionDate;
	}

	public void setSolutionDate(Date solutionDate) {
		this.solutionDate = solutionDate;
	}

	public List<Course> getSemesterPlan() {
		return semesterPlan;
	}

	public void setSemesterPlan(List<Course> semesterPlan) {
		this.semesterPlan = semesterPlan;
	}

	public List<Course> getCourseSolution() {
		return courseSolution;
	}

	public void setCourseSolution(List<Course> courseSolution) {
		this.courseSolution = courseSolution;
	}

}
