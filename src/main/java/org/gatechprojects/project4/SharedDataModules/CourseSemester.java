package org.gatechprojects.project4.SharedDataModules;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "course_semester")
public class CourseSemester {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "semester_id")
	private Semester semester;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User assignedProfessor;

	private int maxCourseSize;

	public User getAssignedProfessor() {
		return assignedProfessor;
	}

	public Course getCourse() {
		return course;
	}

	public int getId() {
		return id;
	}

	public int getMaxCourseSize() {
		return maxCourseSize;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setAssignedProfessor(User assignedProfessor) {
		this.assignedProfessor = assignedProfessor;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMaxCourseSize(int maxCourseSize) {
		this.maxCourseSize = maxCourseSize;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

}
