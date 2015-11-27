package org.gatechprojects.project4.SharedDataModules;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "input_course_competence")
public class InputCourseCompetence {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	@ManyToOne
	@JoinColumn(name = "input_professor_id")
	private InputProfessor inputProfessor;

	public Course getCourse() {
		return course;
	}

	public int getId() {
		return id;
	}

	public InputProfessor getInputProfessor() {
		return inputProfessor;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setInputProfessor(InputProfessor inputProfessor) {
		this.inputProfessor = inputProfessor;
	}
}
