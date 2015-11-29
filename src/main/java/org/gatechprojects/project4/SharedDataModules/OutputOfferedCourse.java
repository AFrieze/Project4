package org.gatechprojects.project4.SharedDataModules;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "output_offered_course")
public class OutputOfferedCourse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int courseSize;
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	@OneToMany(mappedBy = "outputOfferedCourse", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<OutputTACourseAssignment> assignedTAs = new ArrayList<OutputTACourseAssignment>();
	@ManyToOne
	@JoinColumn(name = "assigned_professor_id")
	private User assignedProfessor;

	@ManyToOne
	@JoinColumn(name = "optimizer_calculation_id")
	private OptimizerCalculation optimizerCalculation;

	public User getAssignedProfessor() {
		return assignedProfessor;
	}

	public List<OutputTACourseAssignment> getAssignedTAs() {
		return assignedTAs;
	}

	public Course getCourse() {
		return course;
	}

	public int getCourseSize() {
		return courseSize;
	}

	public int getId() {
		return id;
	}

	public OptimizerCalculation getOptimizerCalculation() {
		return optimizerCalculation;
	}

	public void setAssignedProfessor(User assignedProfessor) {
		this.assignedProfessor = assignedProfessor;
	}

	public void setAssignedTAs(List<OutputTACourseAssignment> assignedTAs) {
		this.assignedTAs = assignedTAs;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setCourseSize(int courseSize) {
		this.courseSize = courseSize;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setOptimizerCalculation(OptimizerCalculation optimizerCalculation) {
		this.optimizerCalculation = optimizerCalculation;
	}

}
