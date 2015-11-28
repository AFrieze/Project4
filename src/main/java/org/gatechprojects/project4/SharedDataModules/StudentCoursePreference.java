package org.gatechprojects.project4.SharedDataModules;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "student_course_preference")
public class StudentCoursePreference {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne()
	@JoinColumn(name = "student_preference_id")
	private StudentPreference studentPreference;
	@ManyToOne()
	@JoinColumn(name = "course_id")
	private Course course;

	public Course getCourse() {
		return course;
	}

	public int getId() {
		return id;
	}

	public StudentPreference getStudentPreference() {
		return studentPreference;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setStudentPreference(StudentPreference studentPreference) {
		this.studentPreference = studentPreference;
	}

}
