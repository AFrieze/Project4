package org.gatechproject.project4.BAL.dto;

import java.util.ArrayList;
import java.util.List;

import org.gatechprojects.project4.SharedDataModules.StudentCoursePreference;
import org.gatechprojects.project4.SharedDataModules.StudentPreference;

public class StudentSemesterPreferences {

	private int userId;
	private List<ConfiguredCourse> preferredCourses = new ArrayList<ConfiguredCourse>();
	private int nbrCoursesDesired;
	private int semesterId;

	public StudentSemesterPreferences() {
	}

	public StudentSemesterPreferences(StudentPreference configuration, int semesterId) {
		this.userId = configuration.getUser().getId();
		this.semesterId = configuration.getSemester().getId();
		for (StudentCoursePreference cp : configuration.getCoursePreferences()) {
			preferredCourses.add(new ConfiguredCourse(cp.getCourse()));
		}
		this.nbrCoursesDesired = configuration.getDesiredNumberCourses();
	}

	public int getNbrCoursesDesired() {
		return nbrCoursesDesired;
	}

	public List<ConfiguredCourse> getPreferredCourses() {
		return preferredCourses;
	}

	public int getSemesterId() {
		return semesterId;
	}

	public int getUserId() {
		return userId;
	}

	public void setNbrCoursesDesired(int nbrCoursesDesired) {
		this.nbrCoursesDesired = nbrCoursesDesired;
	}

	public void setPreferredCourses(List<ConfiguredCourse> preferredCourses) {
		this.preferredCourses = preferredCourses;
	}

	public void setSemesterId(int semesterId) {
		this.semesterId = semesterId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
