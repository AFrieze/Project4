package org.gatechprojects.project4.installation;

import java.util.List;

import org.gatechproject.project4.BAL.dto.ConfiguredCourse;
import org.gatechproject.project4.BAL.dto.Professor;
import org.gatechproject.project4.BAL.dto.StudentSemesterPreferences;
import org.gatechprojects.project4.BAL.Membership;
import org.gatechprojects.project4.BAL.StaffService;
import org.gatechprojects.project4.BAL.UserService;
import org.gatechprojects.project4.DAL.Blackboard;
import org.gatechprojects.project4.DAL.CatalogBoard;
import org.gatechprojects.project4.DAL.DatabaseConfiguration;
import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.CourseSemester;
import org.gatechprojects.project4.SharedDataModules.CourseTaken;
import org.gatechprojects.project4.SharedDataModules.Semester;
import org.gatechprojects.project4.SharedDataModules.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Installer {

	private static int NBR_STUDENTS = 600;
	private static int NBR_PROFESSORS = 10;
	private static int NBR_TAS = 50;
	private static String ADMIN_USERNAME = "Administrator";
	private static String ADMIN_PASSWORD = "password1";
	// static final Logger LOG =
	// LogManager.getLogger(Installer.class.getName());

	public static void main(String[] args) {
		Installer installer = new Installer();
		installer.install();
	}

	private void initializeDatabaseSchema() {
		// LOG.info("Opening database purely to drop the schema");
		DatabaseConfiguration dbConfig = new DatabaseConfiguration();
		Configuration configuration = dbConfig.buildConfiguration().configure();
		configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		SessionFactory factory = configuration.buildSessionFactory();
		Session session = factory.openSession();
		session.close();
		factory.close();

		configuration = dbConfig.buildConfiguration().configure();
		configuration.setProperty("hibernate.hbm2ddl.auto", "update");
		factory = configuration.buildSessionFactory();
		session = factory.openSession();
		session.close();
		factory.close();

	}

	public void install() {
		initializeDatabaseSchema();
		seedData();

	}

	private void seedAdministrator() {

		Membership membership = new Membership();
		int membershipId = membership.registerMember(ADMIN_USERNAME, ADMIN_PASSWORD);

		Blackboard blackboard = new Blackboard();
		blackboard.load();
		blackboard.startTransaction();
		blackboard.getUserBoard().addUser(membershipId, "Admin", "Boss", false, false, false, true);
		blackboard.commitTransaction();
		blackboard.close();
	}

	private void seedCourses() {
		Blackboard blackboard = new Blackboard();
		blackboard.load();
		blackboard.startTransaction();
		CatalogBoard board = blackboard.getCatalogBoard();
		board.createCourse("*CS 6476 Computer Vision (Formerly CS 4495)", 3);
		board.createCourse("CS 6035 Introduction to Information Security", 3);
		board.createCourse("*CS 6210 Advanced Operating Systems", 3);
		board.createCourse("*CSE 6220 Intro to High-Performance Computing", 3);
		board.createCourse("*CS 6250 Computer Networks", 3);
		board.createCourse("*CS 6290 High Performance Computer Architecture", 3);
		board.createCourse("*CS 6300 Software Development Process", 3);
		board.createCourse("*CS 6310 Software Architecture and Design", 3);
		board.createCourse("CS 6440 Intro to Health Informatics", 3);
		board.createCourse("CS 6460 Educational Technology", 3);
		board.createCourse("CS 6475 Computational Photography", 3);
		board.createCourse("*CS 6505 Computability, Complexity and Algorithms", 3);
		board.createCourse("*CS 7637 Knowledge-Based Artificial Intelligence: Cognitive Systems", 3);
		board.createCourse("*CS 7641 Machine Learning", 3);
		board.createCourse("CS 7646 Machine Learning for Trading", 3);
		board.createCourse("CS 8803-001 Artificial Intelligence for Robotics", 3);
		board.createCourse("*CS 8803-002 Introduction to Operating Systems", 3);
		board.createCourse("CS 8803-003 Special Topics: Reinforcement Learning", 3);
		blackboard.commitTransaction();
		blackboard.close();
	}

	private void seedCourseSemesters() {

		Blackboard blackboard = new Blackboard();
		blackboard.load();
		List<Course> courses = blackboard.getCatalogBoard().getAvailableCourses();
		blackboard.startTransaction();
		Semester semester = blackboard.getByID(Semester.class, 1);
		for (Course course : courses) {
			CourseSemester courseSemester = new CourseSemester();
			courseSemester.setSemester(semester);
			courseSemester.setCourse(course);
			courseSemester.setMaxCourseSize(50);
			courseSemester.setShadow(false);
			blackboard.getCatalogBoard().addCourseSemester(courseSemester);
		}
		blackboard.commitTransaction();
		blackboard.close();
	}

	private void seedCoursesTaken() {
		Blackboard blackboard = new Blackboard();
		blackboard.load();
		blackboard.startTransaction();
		List<User> students = blackboard.getUserBoard().getAvailableUsersByType(true, false, false);
		List<Course> courses = blackboard.getCatalogBoard().getAvailableCourses();
		int coursesTaken = 0;
		int maxCoursesTaken = 5;
		for (int i = 0; i < students.size(); i++) {
			User user = students.get(i);
			for (int j = 0; j < coursesTaken; j++) {
				CourseTaken courseTaken = new CourseTaken();
				courseTaken.setUser(user);
				courseTaken.setCourse(courses.get(j));
				blackboard.getCatalogBoard().createCourseTaken(courseTaken);
			}
			coursesTaken = coursesTaken + 1 > maxCoursesTaken ? 0 : coursesTaken + 1;
		}
		blackboard.commitTransaction();
		blackboard.close();
	}

	private void seedData() {

		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.close();
		factory.close();
		seedProfessors();
		seedStudents();
		seedTeacherAssistants();
		seedSemester();
		seedCourses();
		seedCourseSemesters();
		seedCoursesTaken();
		seedStudentPreferences();
		seedProfessorCompetencies();
		seedAdministrator();
	}

	private void seedProfessorCompetencies() {
		Blackboard blackboard = new Blackboard();
		blackboard.load();
		StaffService staffService = new StaffService(blackboard);
		List<Professor> professors = staffService.getAvailableProfessors();
		List<Course> courses = blackboard.getCatalogBoard().getAvailableCourses();
		int nbrCompetencies = 3;
		int courseIndex = 0;
		for (Professor professor : professors) {
			for (int i = 0; i < nbrCompetencies; i++) {
				staffService.addCourseCompetency(courses.get(courseIndex).getId(), professor.getUserId());
				courseIndex = courseIndex + 1 >= courses.size() ? 0 : courseIndex + 1;
			}
		}
		blackboard.close();
	}

	private void seedProfessors() {
		StaffService staffService = new StaffService();
		for (int i = 0; i < NBR_PROFESSORS; i++) {
			String firstName = String.format("Mr %s", i);
			String lastName = String.format("Prof%s", i);
			staffService.addProfessor(null, firstName, lastName);
		}
	}

	private void seedSemester() {
		Blackboard blackboard = new Blackboard();
		blackboard.load();
		blackboard.startTransaction();
		blackboard.getCatalogBoard().createSemester("Spring", 2016);
		blackboard.commitTransaction();
		blackboard.close();
	}

	private void seedStudentPreferences() {
		Blackboard blackboard = new Blackboard();
		blackboard.load();
		Semester semester = blackboard.getCatalogBoard().getSemesters().get(0);
		UserService userService = new UserService(blackboard);
		List<User> students = blackboard.getUserBoard().getAvailableUsersByType(true, false, false);
		List<Course> courses = blackboard.getCatalogBoard().getAvailableCourses();
		int nbrCoursesDesired = 1;
		int maxNbrCoursesDesired = 4;
		for (User user : students) {
			StudentSemesterPreferences semesterPreferences = new StudentSemesterPreferences();
			semesterPreferences.setUserId(user.getId());
			semesterPreferences.setNbrCoursesDesired(nbrCoursesDesired);
			semesterPreferences.setSemesterId(semester.getId());
			for (int i = 0; i < nbrCoursesDesired; i++) {
				for (Course course : courses) {
					boolean isTaken = false;
					for (CourseTaken taken : user.getCoursesTaken()) {
						if (course.getId() == taken.getCourse().getId()) {
							isTaken = true;
							break;
						}
					}
					if (!isTaken) {
						ConfiguredCourse cc = new ConfiguredCourse(course);
						semesterPreferences.getPreferredCourses().add(cc);
						break;
					}
				}
			}
			userService.applyStudentPreferences(semesterPreferences);
			nbrCoursesDesired = nbrCoursesDesired + 1 > maxNbrCoursesDesired ? 1 : nbrCoursesDesired + 1;
		}
		blackboard.close();
	}

	private void seedStudents() {
		UserService studentService = new UserService();
		Membership membership = new Membership();
		for (int i = 0; i < NBR_STUDENTS; i++) {
			String firstName = String.format("Stud%s", i);
			String lastName = String.format("Studly%s", i);
			String userName = lastName;
			String password = lastName;
			int membershipId = membership.registerMember(userName, password);
			studentService.addStudent(membershipId, firstName, lastName);
		}
	}

	private void seedTeacherAssistants() {
		StaffService staffService = new StaffService();
		for (int i = 0; i < NBR_TAS; i++) {
			String firstName = String.format("Low %s", i);
			String lastName = String.format("ly%s", i);
			staffService.addTeacherAssistant(null, firstName, lastName);
		}
	}

}
