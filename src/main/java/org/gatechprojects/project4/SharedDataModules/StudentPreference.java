package org.gatechprojects.project4.SharedDataModules;

import java.util.Set;

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

	@OneToMany(mappedBy = "studentPreference")
	private Set<StudentCoursePreference> coursePreferences;

	public Set<StudentCoursePreference> getCoursePreferences() {
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

	public void setCoursePreferences(Set<StudentCoursePreference> coursePreferences) {
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
