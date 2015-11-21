package org.gatechprojects.project4.SharedDataModules;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@OneToOne
	@JoinColumn(name = "membership_id")
	private MembershipUser membership;
	private String firstName;

	private String lastName;

	private boolean isTA;
	private boolean isProfessor;
	private boolean isStudent;
	@OneToMany(mappedBy = "user")
	private Set<CourseTaken> coursesTaken = new HashSet<CourseTaken>();
	@OneToMany(mappedBy = "user")
	private Set<ProfessorCompetence> professorCompetencies = new HashSet<ProfessorCompetence>();
	@OneToMany(mappedBy = "user")
	private Set<StudentPreference> preferences = new HashSet<StudentPreference>();
	@OneToMany(mappedBy = "user")
	private Set<UserAvailability> userAvailabilities = new HashSet<UserAvailability>();

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

	public MembershipUser getMembership() {
		return membership;
	}

	public Set<StudentPreference> getPreferences() {
		return preferences;
	}

	public Set<ProfessorCompetence> getProfessorCompetencies() {
		return professorCompetencies;
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

	public void setMembership(MembershipUser membership) {
		this.membership = membership;
	}

	public void setPreferences(Set<StudentPreference> preferences) {
		this.preferences = preferences;
	}

	public void setProfessor(boolean isProfessor) {
		this.isProfessor = isProfessor;
	}

	public void setProfessorCompetencies(Set<ProfessorCompetence> professorCompetencies) {
		this.professorCompetencies = professorCompetencies;
	}

	public void setStudent(boolean isStudent) {
		this.isStudent = isStudent;
	}

	public void setTA(boolean isTA) {
		this.isTA = isTA;
	}

}
