package org.gatechprojects.project4.SharedDataModules;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "optimizer_calculation")
public class OptimizerCalculation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int semesterId;
	private Calendar createTime;
	private Calendar completionTime;

	public Calendar getCompletionTime() {
		return completionTime;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public int getId() {
		return id;
	}

	public int getSemesterId() {
		return semesterId;
	}

	public void setCompletionTime(Calendar completionTime) {
		this.completionTime = completionTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSemesterId(int semesterId) {
		this.semesterId = semesterId;
	}
}
