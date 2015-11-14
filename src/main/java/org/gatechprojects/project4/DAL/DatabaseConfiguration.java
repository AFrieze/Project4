package org.gatechprojects.project4.DAL;

import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.CourseTaken;
import org.gatechprojects.project4.SharedDataModules.ProfessorCompetence;
import org.gatechprojects.project4.SharedDataModules.Semester;
import org.gatechprojects.project4.SharedDataModules.StudentCoursePreference;
import org.gatechprojects.project4.SharedDataModules.StudentPreference;
import org.gatechprojects.project4.SharedDataModules.User;
import org.gatechprojects.project4.SharedDataModules.UserAvailability;
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
		configuration.addAnnotatedClass(Semester.class);
		configuration.addAnnotatedClass(CourseTaken.class);
		configuration.addAnnotatedClass(StudentPreference.class);
		configuration.addAnnotatedClass(StudentCoursePreference.class);
		configuration.addAnnotatedClass(ProfessorCompetence.class);
		configuration.addAnnotatedClass(UserAvailability.class);
		// configuration.addAnnotatedClass(CoursePrerequisite.class);
		// configuration.addAnnotatedClass(CourseSemester.class);
		// configuration.addAnnotatedClass(InputOfferedCourse.class);
		// configuration.addAnnotatedClass(InputCourseCompetence.class);
		// configuration.addAnnotatedClass(InputProfessor.class);
		// configuration.addAnnotatedClass(InputStudent.class);
		// configuration.addAnnotatedClass(InputStudentPreference.class);
		// configuration.addAnnotatedClass(InputTA.class);
		// configuration.addAnnotatedClass(InputTaCourseAssignment.class);
		// configuration.addAnnotatedClass(OptimizerCalculation.class);
		// configuration.addAnnotatedClass(OutputOfferedCourse.class);
		// configuration.addAnnotatedClass(OutputProfessorCourseAssignment.class);
		// configuration.addAnnotatedClass(OutputTACourseAssignment.class);
		// configuration.addAnnotatedClass(OutputUserCourseAssignment.class);
	}
}
