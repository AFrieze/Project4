package org.gatechprojects.project4.SharedDataModules;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "input_offered_course")
public class InputOfferedCourse {
	@Id
	private int id;
	private int courseId;
	private boolean mustBeTaught = false;

	public int getCourseId() {
		return courseId;
	}

	public int getId() {
		return id;
	}

	public boolean isMustBeTaught() {
		return mustBeTaught;
	}

	public void setCourseID(int courseId) {
		this.courseId = courseId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMustBeTaught(boolean mustBeTaught) {
		this.mustBeTaught = mustBeTaught;
	}

}
