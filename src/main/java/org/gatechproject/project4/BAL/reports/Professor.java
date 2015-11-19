package org.gatechproject.project4.BAL.reports;

import java.util.ArrayList;
import java.util.List;

import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.ProfessorCompetence;
import org.gatechprojects.project4.SharedDataModules.User;

public class Professor extends Person {

	private List<Course> courseCompetencies = new ArrayList<Course>();

	public Professor() {
	}

	public Professor(User user) {
		super(user);
		for (ProfessorCompetence pc : user.getProfessorCompetencies()) {
			courseCompetencies.add(pc.getCourse());
		}
	}

	public List<Course> getCourseCompetencies() {
		return courseCompetencies;
	}

	public void setCourseCompetencies(List<Course> courseCompetencies) {
		this.courseCompetencies = courseCompetencies;
	}
}
