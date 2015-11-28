package org.gatechprojects.project4.SharedDataModules;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "student_preference")
public class StudentPreference {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "semester_id")
	private Semester semester;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	private int desiredNumberCourses;

	@OneToMany(mappedBy = "studentPreference", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<StudentCoursePreference> coursePreferences;

	public List<StudentCoursePreference> getCoursePreferences() {
		return coursePreferences;
	}

	public int getDesiredNumberCourses() {
		return desiredNumberCourses;
	}

	public int getId() {
		return id;
	}

	public Semester getSemester() {
		return semester;
	}

	public User getUser() {
		return user;
	}

	public void setCoursePreferences(List<StudentCoursePreference> coursePreferences) {
		this.coursePreferences = coursePreferences;
	}

	public void setDesiredNumberCourses(int desiredNumberCourses) {
		this.desiredNumberCourses = desiredNumberCourses;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
