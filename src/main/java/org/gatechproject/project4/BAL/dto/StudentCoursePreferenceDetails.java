package org.gatechproject.project4.BAL.dto;

import org.gatechprojects.project4.SharedDataModules.Course;

public class StudentCoursePreferenceDetails {
	private Course course;
	private int demand;
	private int priority;

	public StudentCoursePreferenceDetails(Course course, int demand, int priority) {
		this.course = course;
		this.demand = demand;
		this.priority = priority;
	}

	public Course getCourse() {
		return course;
	}

	public int getDemand() {
		return demand;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setDemand(int demand) {
		this.demand = demand;
	}
}
