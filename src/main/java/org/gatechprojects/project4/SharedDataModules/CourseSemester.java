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

	public int getId() {
		return id;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

}
