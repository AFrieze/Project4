package org.gatechproject.project4.BAL.dto;

import java.util.ArrayList;
import java.util.List;

/*
 * This class will be used by the Admin screen to show TAs, profs and courses
 */

public class SemesterConfiguration {

	private int semesterId;
	private List<TeacherAssistant> teacherAssistants = new ArrayList<>();

	private List<Professor> professors = new ArrayList<>();

	private List<ConfiguredCourse> offeredCourses = new ArrayList<>();

	public List<ConfiguredCourse> getOfferedCourses() {
		return offeredCourses;
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

	public void setOfferedCourses(List<ConfiguredCourse> courses) {
		this.offeredCourses = courses;
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
