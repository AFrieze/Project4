package org.gatechprojects.project4.SharedDataModules;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "input_student")
public class InputStudent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int userId;
	private int studentPriority;
	private int optimizerCalculationId;

	public int getId() {
		return id;
	}

	public int getOptimizerCalculationId() {
		return optimizerCalculationId;
	}

	public int getStudentPriority() {
		return studentPriority;
	}

	public int getUserId() {
		return userId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setOptimizerCalculationId(int optimizerCalculationId) {
		this.optimizerCalculationId = optimizerCalculationId;
	}

	public void setStudentPriority(int studentPriority) {
		this.studentPriority = studentPriority;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
