package org.gatechproject.project4.BAL.dto;

import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.CourseSemester;

public class ConfiguredCourse {
	private int courseId;
	private int maxCourseSize;
	private Integer assignedProfessorId;
	private String courseName;

	public ConfiguredCourse() {

	}

	public ConfiguredCourse(Course course) {
		this.courseId = course.getId();
		this.courseName = course.getName();
	}

	public ConfiguredCourse(CourseSemester cs) {
		if(cs.getAssignedProfessor() != null)
			this.assignedProfessorId = cs.getAssignedProfessor().getId();
		this.courseId = cs.getCourse().getId();
		this.maxCourseSize = cs.getMaxCourseSize();
		this.courseName = cs.getCourse().getName();
	}

	public Integer getAssignedProfessorId() {
		return assignedProfessorId;
	}

	public int getCourseId() {
		return courseId;
	}

	public String getName() {
		return courseName;
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

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public void setMaxCourseSize(int maxCourseSize) {
		this.maxCourseSize = maxCourseSize;
	}
}
