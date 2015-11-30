package org.gatechproject.project4.BAL.dto;

import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.CourseSemester;

public class ConfiguredCourse {
	private int courseId;
	private int maxCourseSize;
	private Integer assignedProfessorId;
	private String courseName;
	private boolean assigned = false;
	
	public ConfiguredCourse() {

	}

	public ConfiguredCourse(Course course) {
		this.courseId = course.getCourseId();
		this.courseName = course.getName();
	}

	public ConfiguredCourse(Course course, boolean isAssigned) {
		this.courseId = course.getCourseId();
		this.courseName = course.getName();
		this.assigned = isAssigned;
	}
	
	public ConfiguredCourse(CourseSemester cs, boolean isAssigned) {
		if(cs.getAssignedProfessor() != null)
			this.assignedProfessorId = cs.getAssignedProfessor().getId();
		this.courseId = cs.getCourse().getCourseId();
		this.maxCourseSize = cs.getMaxCourseSize();
		this.courseName = cs.getCourse().getName();
		this.assigned = isAssigned;
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

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean isAssigned) {
		this.assigned = isAssigned;
	}
}
