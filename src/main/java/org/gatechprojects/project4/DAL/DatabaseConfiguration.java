package org.gatechprojects.project4.DAL;

import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.CourseSemester;
import org.gatechprojects.project4.SharedDataModules.CourseTaken;
import org.gatechprojects.project4.SharedDataModules.InputCourseCompetence;
import org.gatechprojects.project4.SharedDataModules.InputOfferedCourse;
import org.gatechprojects.project4.SharedDataModules.InputProfessor;
import org.gatechprojects.project4.SharedDataModules.InputStudent;
import org.gatechprojects.project4.SharedDataModules.InputStudentCoursePreference;
import org.gatechprojects.project4.SharedDataModules.InputTA;
import org.gatechprojects.project4.SharedDataModules.MembershipUser;
import org.gatechprojects.project4.SharedDataModules.OptimizerCalculation;
import org.gatechprojects.project4.SharedDataModules.OutputOfferedCourse;
import org.gatechprojects.project4.SharedDataModules.OutputTACourseAssignment;
import org.gatechprojects.project4.SharedDataModules.OutputUserCourseAssignment;
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
		configuration.addAnnotatedClass(CourseSemester.class);
		configuration.addAnnotatedClass(MembershipUser.class);
		configuration.addAnnotatedClass(OptimizerCalculation.class);
		configuration.addAnnotatedClass(InputStudent.class);
		configuration.addAnnotatedClass(InputStudentCoursePreference.class);
		configuration.addAnnotatedClass(InputOfferedCourse.class);
		configuration.addAnnotatedClass(InputProfessor.class);
		configuration.addAnnotatedClass(InputTA.class);
		configuration.addAnnotatedClass(InputCourseCompetence.class);
		configuration.addAnnotatedClass(OutputOfferedCourse.class);
		configuration.addAnnotatedClass(OutputTACourseAssignment.class);
		configuration.addAnnotatedClass(OutputUserCourseAssignment.class);
	}
}
