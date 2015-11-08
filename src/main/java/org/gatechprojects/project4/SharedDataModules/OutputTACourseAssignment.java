package org.gatechprojects.project4.SharedDataModules;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "output_ta_course_assignment")
public class OutputTACourseAssignment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int userId;
	private int courseId;

	public int getCourseId() {
		return courseId;
	}

	public int getId() {
		return id;
	}

	public int getUserId() {
		return userId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
