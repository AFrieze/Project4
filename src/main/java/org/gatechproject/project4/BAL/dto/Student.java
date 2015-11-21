package org.gatechproject.project4.BAL.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.User;

public class Student extends Person{

	private List<Date> historyLoginDates = new ArrayList<>();

	private List<StudentSolution> historySolutions = new ArrayList<>();
	private String solutionStatus = "";
	private Date solutionDate = new Date();

	private List<Course> completedCourses = new ArrayList<>();
	private List<Course> currentCourses = new ArrayList<>();
	
	private String studentStanding = "Good";	// Default

	// ===========================================================================
	
	public Student(User user) {
		super(user);
		
		// TODO - Luc - get student data from the database classes
	}
	
	public int getCompletedCoursesCount() {
		return getCompletedCourses().size() ;
	}
	
	public int getCurrentCoursesCount() {
		return getCurrentCourses().size();
	}

	public Date getLastLoginDate() {
		if (historyLoginDates.size() > 0)
			return historyLoginDates.get(0);
		else
			return null;
	}

	public List<Date> getLoginDates() {
		return historyLoginDates;
	}

	public Date getSolutionDate() {
		return solutionDate;
	}

	public String getSolutionStatus() {
		return solutionStatus;
	}

	public String getStudentStanding() {
		return studentStanding;
	}

	public void setSolutionDate(Date solutionDate) {
		this.solutionDate = solutionDate;
	}

	public void setSolutionStatus(String solutionStatus) {
		this.solutionStatus = solutionStatus;
	}

	public void setStudentStanding(String studentStanding) {
		this.studentStanding = studentStanding;
	}

	public List<Course> getCompletedCourses() {
		return completedCourses;
	}

	public void setCompletedCourses(List<Course> completedCourses) {
		this.completedCourses = completedCourses;
	}

	public List<Course> getCurrentCourses() {
		return currentCourses;
	}

	public void setCurrentCourses(List<Course> currentCourses) {
		this.currentCourses = currentCourses;
	}
}
