package org.gatechprojects.project4.SharedDataModules;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "course_semester")
public class CourseSemester {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int semesterID;

	public int getId() {
		return id;
	}

	public int getSemesterID() {
		return semesterID;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSemesterID(int semesterID) {
		this.semesterID = semesterID;
	}

}
