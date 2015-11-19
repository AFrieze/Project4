package org.gatechproject.project4.BAL.reports;

import java.util.ArrayList;
import java.util.List;

import org.gatechprojects.project4.SharedDataModules.CourseSemester;

/*
 * This class will be used by the Admin screen to show TAs, profs and courses
 */

public class SemesterConfiguration {

	private int semesterId;
	private List<TeacherAssistant> teacherAssistants = new ArrayList<>();

	private List<Professor> professors = new ArrayList<>();

	private List<CourseSemester> courses = new ArrayList<>();

	public List<CourseSemester> getCourses() {
		return courses;
	}

	public List<Professor> getProfessors() {
		return professors;
	}

	public int getSemesterId() {
		return semesterId;
	}

	public List<TeacherAssistant> getTeacherAssistants() {
		return teacherAssistants;
	}

	public void setCourses(List<CourseSemester> courses) {
		this.courses = courses;
	}

	public void setProfessors(List<Professor> professors) {
		this.professors = professors;
	}

	public void setSemesterId(int semesterId) {
		this.semesterId = semesterId;
	}

	public void setTeacherAssistants(List<TeacherAssistant> teacherAssistants) {
		this.teacherAssistants = teacherAssistants;
	}
}
