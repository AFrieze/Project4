package org.gatechprojects.project4.SharedDataModules;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "input_ta")
public class InputTA {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	private int optimizerCalculationId;
	private int userId;

	public int getId() {
		return Id;
	}

	public int getOptimizerCalculationId() {
		return optimizerCalculationId;
	}

	public int getUserId() {
		return userId;
	}

	public void setId(int id) {
		Id = id;
	}

	public void setOptimizerCalculationId(int optimizerCalculationId) {
		this.optimizerCalculationId = optimizerCalculationId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
