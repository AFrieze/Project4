package org.gatechprojects.project4.SharedDataModules;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "output_ta_course_assignment")
public class OutputTACourseAssignment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	@ManyToOne
	@JoinColumn(name = "output_offered_course_id")
	private OutputOfferedCourse outputOfferedCourse;

	public Course getCourse() {
		return course;
	}

	public int getId() {
		return id;
	}

	public OutputOfferedCourse getOutputOfferedCourse() {
		return outputOfferedCourse;
	}

	public User getUser() {
		return user;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setOutputOfferedCourse(OutputOfferedCourse outputOfferedCourse) {
		this.outputOfferedCourse = outputOfferedCourse;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
