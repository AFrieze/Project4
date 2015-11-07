package org.gatechprojects.project4.DAL;

import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.CoursePrerequisite;
import org.gatechprojects.project4.SharedDataModules.CourseSemester;
import org.gatechprojects.project4.SharedDataModules.CourseTaken;
import org.gatechprojects.project4.SharedDataModules.InputOfferedCourse;
import org.gatechprojects.project4.SharedDataModules.Semester;
import org.gatechprojects.project4.SharedDataModules.User;
import org.hibernate.cfg.Configuration;

public class DatabaseConfiguration {

	public Configuration buildConfiguration() {
		Configuration configuration = new Configuration().configure();
		configuration.addPackage("org.gatechprojects.project4.SharedDataModules");
		registerModels(configuration);
		return configuration;
	}

	private void registerModels(Configuration configuration) {
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Course.class);
		configuration.addAnnotatedClass(CoursePrerequisite.class);
		configuration.addAnnotatedClass(CourseSemester.class);
		configuration.addAnnotatedClass(CourseTaken.class);
		configuration.addAnnotatedClass(Semester.class);
		configuration.addAnnotatedClass(InputOfferedCourse.class);
	}
}
