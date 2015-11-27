package org.gatechprojects.project4.SharedDataModules;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "input_ta")
public class InputTA {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;

	@ManyToOne
	@JoinColumn(name = "optimizer_calculation_id")
	private OptimizerCalculation optimizerCalculation;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public int getId() {
		return Id;
	}

	public OptimizerCalculation getOptimizerCalculation() {
		return optimizerCalculation;
	}

	public User getUser() {
		return user;
	}

	public void setId(int id) {
		Id = id;
	}

	public void setOptimizerCalculation(OptimizerCalculation optimizerCalculation) {
		this.optimizerCalculation = optimizerCalculation;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
