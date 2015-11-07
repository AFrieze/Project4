package org.gatechprojects.project4.SharedDataModules;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	@Id
	private int id;
	private String firstName;
	private String lastName;
	private boolean isTA;
	private boolean isProfessor;
	private boolean isStudent;
	private Set<CourseTaken> coursesTaken = new HashSet<CourseTaken>();

	public Set<CourseTaken> getCoursesTaken() {
		return coursesTaken;
	}

	public String getFirstName() {
		return firstName;
	}

	public int getId() {
		return id;
	}

	public String getLastName() {
		return lastName;
	}

	public boolean isProfessor() {
		return isProfessor;
	}

	public boolean isStudent() {
		return isStudent;
	}

	public boolean isTA() {
		return isTA;
	}

	public void setCoursesTaken(Set<CourseTaken> coursesTaken) {
		this.coursesTaken = coursesTaken;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setProfessor(boolean isProfessor) {
		this.isProfessor = isProfessor;
	}

	public void setStudent(boolean isStudent) {
		this.isStudent = isStudent;
	}

	public void setTA(boolean isTA) {
		this.isTA = isTA;
	}

}
