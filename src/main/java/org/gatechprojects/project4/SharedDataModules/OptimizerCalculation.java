package org.gatechprojects.project4.SharedDataModules;

import java.util.ArrayList;
import java.util.Calendar;
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
@Table(name = "optimizer_calculation")
public class OptimizerCalculation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "semester_id")
	private Semester semester;
	private Calendar createTime;

	private boolean isShadow = false;
	private Calendar completionTime;

	@OneToMany(mappedBy = "optimizerCalculation")
	private List<InputStudent> inputStudents = new ArrayList<InputStudent>();

	@OneToMany(mappedBy = "optimizerCalculation")
	private List<InputTA> inputTAs = new ArrayList<InputTA>();

	@OneToMany(mappedBy = "optimizerCalculation")
	private List<InputProfessor> inputProfessors = new ArrayList<InputProfessor>();
	@OneToMany(mappedBy = "optimizerCalculation")
	private List<InputOfferedCourse> inputOfferedCourses = new ArrayList<InputOfferedCourse>();

	public Calendar getCompletionTime() {
		return completionTime;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public int getId() {
		return id;
	}

	public List<InputOfferedCourse> getInputOfferedCourses() {
		return inputOfferedCourses;
	}

	public List<InputProfessor> getInputProfessors() {
		return inputProfessors;
	}

	public List<InputStudent> getInputStudents() {
		return inputStudents;
	}

	public List<InputTA> getInputTAs() {
		return inputTAs;
	}

	public Semester getSemester() {
		return semester;
	}

	public boolean isShadow() {
		return isShadow;
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

	public void setInputOfferedCourses(List<InputOfferedCourse> inputOfferedCourses) {
		this.inputOfferedCourses = inputOfferedCourses;
	}

	public void setInputProfessors(List<InputProfessor> inputProfessors) {
		this.inputProfessors = inputProfessors;
	}

	public void setInputStudents(List<InputStudent> inputStudents) {
		this.inputStudents = inputStudents;
	}

	public void setInputTAs(List<InputTA> inputTAs) {
		this.inputTAs = inputTAs;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public void setShadow(boolean isShadow) {
		this.isShadow = isShadow;
	}

}
