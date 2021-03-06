package org.gatechprojects.project4.SharedDataModules;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "course_taken")
public class CourseTaken {

	@Id
	@GeneratedValue
	// @Column(name = "course_id", columnDefinition = "INT NOT NULL
	// AUTO_INCREMENT")
	private int id;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Course getCourse() {
		return course;
	}

	public int getId() {
		return id;
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

	public void setUser(User user) {
		this.user = user;
	}

}
