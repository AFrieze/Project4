package org.gatechprojects.project4.SharedDataModules;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "course_prerequisite")
public class CoursePrerequisite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int courseID;
	private int prereqCourseID;

	public int getCourseID() {
		return courseID;
	}

	public int getId() {
		return id;
	}

	public int getPrereqCourseID() {
		return prereqCourseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPrereqCourseID(int preReqCourseID) {
		this.prereqCourseID = preReqCourseID;
	}
}
