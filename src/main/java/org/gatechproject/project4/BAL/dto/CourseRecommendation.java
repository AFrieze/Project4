package org.gatechproject.project4.BAL.dto;

import java.util.ArrayList;
import java.util.List;

import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.OutputOfferedCourse;
import org.gatechprojects.project4.SharedDataModules.OutputTACourseAssignment;

public class CourseRecommendation {
	private Course course;
	private int courseSize;
	private Professor assignedProfessor;
	private List<TeacherAssistant> assignedTAs = new ArrayList<>();

	public CourseRecommendation(OutputOfferedCourse offeredCourse) {
		this.course = offeredCourse.getCourse();
		this.courseSize = offeredCourse.getCourseSize();
		if (offeredCourse.getAssignedProfessor() != null) {
			this.assignedProfessor = new Professor(offeredCourse.getAssignedProfessor());
		}
		for (OutputTACourseAssignment ta : offeredCourse.getAssignedTAs()) {
			this.assignedTAs.add(new TeacherAssistant(ta.getUser()));
		}
	}

	public Professor getAssignedProfessor() {
		return assignedProfessor;
	}

	public List<TeacherAssistant> getAssignedTAs() {
		return assignedTAs;
	}

	public Course getCourse() {
		return course;
	}

	public int getCourseSize() {
		return courseSize;
	}

	public void setAssignedProfessor(Professor assignedProfessor) {
		this.assignedProfessor = assignedProfessor;
	}

	public void setAssignedTAs(List<TeacherAssistant> assignedTAs) {
		this.assignedTAs = assignedTAs;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setCourseSize(int courseSize) {
		this.courseSize = courseSize;
	}

}
