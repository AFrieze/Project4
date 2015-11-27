package org.gatechproject.project4.BAL.dto;

import java.util.Calendar;

public class StudentCourseRecommendation {

	private Calendar solutionDt;
	private int userId;
	private ConfiguredCourse course;
	private Professor professor;

	public ConfiguredCourse getCourse() {
		return course;
	}

	public Professor getProfessor() {
		return professor;
	}

	public Calendar getSolutionDt() {
		return solutionDt;
	}

	public int getUserId() {
		return userId;
	}

	public void setCourse(ConfiguredCourse course) {
		this.course = course;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public void setSolutionDt(Calendar solutionDt) {
		this.solutionDt = solutionDt;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
