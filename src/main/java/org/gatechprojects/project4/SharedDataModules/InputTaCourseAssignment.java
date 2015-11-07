package org.gatechprojects.project4.SharedDataModules;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "input_ta_course_assignment")
public class InputTaCourseAssignment {
	@Id
	private int id;
	private int courseId;
	private int inputTAId;

	public int getCourseId() {
		return courseId;
	}

	public int getId() {
		return id;
	}

	public int getInputTAId() {
		return inputTAId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setInputTAId(int inputTAId) {
		this.inputTAId = inputTAId;
	}

}
