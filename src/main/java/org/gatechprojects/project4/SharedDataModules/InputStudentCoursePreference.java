package org.gatechprojects.project4.SharedDataModules;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "input_student_course_preference")
public class InputStudentCoursePreference {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int coursePriority;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	@ManyToOne
	@JoinColumn(name = "input_student_id")
	private InputStudent inputStudent;

	@ManyToOne
	@JoinColumn(name = "optimizer_calculation_id")
	private OptimizerCalculation optimizerCalculation;
	
	public Course getCourse() {
		return course;
	}

	public int getCoursePriority() {
		return coursePriority;
	}

	public int getId() {
		return id;
	}

	public InputStudent getInputStudent() {
		return inputStudent;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setCoursePriority(int coursePriority) {
		this.coursePriority = coursePriority;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setInputStudent(InputStudent inputStudent) {
		this.inputStudent = inputStudent;
	}

	public OptimizerCalculation getOptimizerCalculation() {
		return optimizerCalculation;
	}

	public void setOptimizerCalculation(OptimizerCalculation optimizerCalculation) {
		this.optimizerCalculation = optimizerCalculation;
	}

}
