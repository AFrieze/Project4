package org.gatechprojects.project4.SharedDataModules;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "output_user_course_assignment")
public class OutputUserCourseAssignment {
	@Id
	private int id;
	private int userId;
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

	public int getUserId() {
		return userId;
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

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
