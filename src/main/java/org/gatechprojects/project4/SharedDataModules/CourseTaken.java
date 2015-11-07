package org.gatechprojects.project4.SharedDataModules;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "course_taken")
public class CourseTaken {

	@Id
	private int id;
	private int courseID;
	private int userID;

	public int getCourseID() {
		return courseID;
	}

	public int getId() {
		return id;
	}

	public int getUserID() {
		return userID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

}
