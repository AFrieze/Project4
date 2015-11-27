package org.gatechprojects.project4.SharedDataModules;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "input_offered_course")
public class InputOfferedCourse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	private int maxCourseSize;
	@ManyToOne
	@JoinColumn(name = "assigned_user_id")
	private User assignedProfessor;

	@ManyToOne
	@JoinColumn(name = "optimizer_calculation_id")
	private OptimizerCalculation optimizerCalculation;

	public User getAssignedProfessor() {
		return assignedProfessor;
	}

	public Course getCourse() {
		return course;
	}

	public int getId() {
		return id;
	}

	public int getMaxCourseSize() {
		return maxCourseSize;
	}

	public OptimizerCalculation getOptimizerCalculation() {
		return optimizerCalculation;
	}

	public void setAssignedProfessor(User assignedProfessor) {
		this.assignedProfessor = assignedProfessor;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMaxCourseSize(int maxCourseSize) {
		this.maxCourseSize = maxCourseSize;
	}

	public void setOptimizerCalculation(OptimizerCalculation optimizerCalculation) {
		this.optimizerCalculation = optimizerCalculation;
	}
}
