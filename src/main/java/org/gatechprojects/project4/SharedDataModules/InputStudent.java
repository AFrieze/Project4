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
@Table(name = "input_student")
public class InputStudent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int creditsTaken;

	private int nbrCoursesDesired;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "optimizer_calculation_id")
	private OptimizerCalculation optimizerCalculation;

	@OneToMany(mappedBy = "inputStudent")
	private List<InputStudentCoursePreference> coursePreferences = new ArrayList<InputStudentCoursePreference>();

	public List<InputStudentCoursePreference> getCoursePreferences() {
		return coursePreferences;
	}

	public int getCreditsTaken() {
		return creditsTaken;
	}

	public int getId() {
		return id;
	}

	public int getNbrCoursesDesired() {
		return nbrCoursesDesired;
	}

	public OptimizerCalculation getOptimizerCalculation() {
		return optimizerCalculation;
	}

	public User getUser() {
		return user;
	}

	public void setCoursePreferences(List<InputStudentCoursePreference> coursePreferences) {
		this.coursePreferences = coursePreferences;
	}

	public void setCreditsTaken(int creditsTaken) {
		this.creditsTaken = creditsTaken;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNbrCoursesDesired(int nbrCoursesDesired) {
		this.nbrCoursesDesired = nbrCoursesDesired;
	}

	public void setOptimizerCalculation(OptimizerCalculation optimizerCalculation) {
		this.optimizerCalculation = optimizerCalculation;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
