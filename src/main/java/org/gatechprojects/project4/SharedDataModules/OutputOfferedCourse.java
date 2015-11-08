package org.gatechprojects.project4.SharedDataModules;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "output_offered_course")
public class OutputOfferedCourse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int courseId;
	private int optimizerCalculationId;

	public int getCourseId() {
		return courseId;
	}

	public int getId() {
		return id;
	}

	public int getOptimizerCalculationId() {
		return optimizerCalculationId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setOptimizerCalculationId(int optimizerCalculationId) {
		this.optimizerCalculationId = optimizerCalculationId;
	}
}
