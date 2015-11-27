package org.gatechprojects.project4.SharedDataModules;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "input_professor")
public class InputProfessor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "optimizer_calculation_id")
	private OptimizerCalculation optimizerCalculation;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "inputProfessor")
	private List<InputCourseCompetence> courseCompetencies = new ArrayList<InputCourseCompetence>();

	public List<InputCourseCompetence> getCourseCompetencies() {
		return courseCompetencies;
	}

	public int getId() {
		return id;
	}

	public OptimizerCalculation getOptimizerCalculation() {
		return optimizerCalculation;
	}

	public User getUser() {
		return user;
	}

	public void setCourseCompetencies(List<InputCourseCompetence> courseCompetencies) {
		this.courseCompetencies = courseCompetencies;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setOptimizerCalculation(OptimizerCalculation optimizerCalculation) {
		this.optimizerCalculation = optimizerCalculation;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
