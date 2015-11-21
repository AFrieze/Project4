package org.gatechproject.project4.BAL.dto;

import org.gatechprojects.project4.SharedDataModules.CourseSemester;

public class ConfiguredCourse {
	private int courseId;
	private int maxCourseSize;
	private Integer assignedProfessorId;

	public ConfiguredCourse() {

	}

	public ConfiguredCourse(CourseSemester cs) {
		this.assignedProfessorId = cs.getAssignedProfessor().getId();
		this.courseId = cs.getCourse().getId();
		this.maxCourseSize = cs.getMaxCourseSize();
	}

	public Integer getAssignedProfessorId() {
		return assignedProfessorId;
	}

	public int getCourseId() {
		return courseId;
	}

	public int getMaxCourseSize() {
		return maxCourseSize;
	}

	public void setAssignedProfessorId(Integer assignedProfessorId) {
		this.assignedProfessorId = assignedProfessorId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public void setMaxCourseSize(int maxCourseSize) {
		this.maxCourseSize = maxCourseSize;
	}
}
