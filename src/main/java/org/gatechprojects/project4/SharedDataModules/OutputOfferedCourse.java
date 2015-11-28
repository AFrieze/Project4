package org.gatechprojects.project4.SharedDataModules;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "output_offered_course")
public class OutputOfferedCourse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	@ManyToOne
	@JoinColumn(name = "optimizer_calculation_id")
	private OptimizerCalculation optimizerCalculation;

	public Course getCourse() {
		return course;
	}

	public int getId() {
		return id;
	}

	public OptimizerCalculation getOptimizerCalculation() {
		return optimizerCalculation;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setOptimizerCalculation(OptimizerCalculation optimizerCalculation) {
		this.optimizerCalculation = optimizerCalculation;
	}

}
