package org.gatechproject.project4.BAL.dto;

import java.util.ArrayList;
import java.util.List;

import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.ProfessorCompetence;
import org.gatechprojects.project4.SharedDataModules.User;

public class Professor extends Person {

	private List<Course> courseCompetencies = new ArrayList<Course>();
	private boolean assigned = false;
	
	public Professor() {
	}

	public Professor(User user) {
		super(user);
		for (ProfessorCompetence pc : user.getProfessorCompetencies()) {
			courseCompetencies.add(pc.getCourse());
		}
	}

	/**
	 * Returns a list of the {@link Course courses} this professor is able to
	 * teach.
	 * 
	 * @return
	 */
	public List<Course> getCourseCompetencies() {
		return courseCompetencies;
	}

	public void setCourseCompetencies(List<Course> courseCompetencies) {
		this.courseCompetencies = courseCompetencies;
	}

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean isAssigned) {
		this.assigned = isAssigned;
	}
}
