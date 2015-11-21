package org.gatechprojects.project4.BAL;

import java.util.ArrayList;
import java.util.List;

import org.gatechproject.project4.BAL.dto.ConfiguredCourse;
import org.gatechproject.project4.BAL.dto.Professor;
import org.gatechproject.project4.BAL.dto.SemesterConfiguration;
import org.gatechproject.project4.BAL.dto.Student;
import org.gatechproject.project4.BAL.dto.TeacherAssistant;
import org.gatechprojects.project4.DAL.Blackboard;
import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.CourseSemester;
import org.gatechprojects.project4.SharedDataModules.Semester;
import org.gatechprojects.project4.SharedDataModules.User;
import org.gatechprojects.project4.SharedDataModules.UserAvailability;

public class SemesterSetupService {

	private Blackboard blackboard;

	/**
	 * Default public contructor. A Blackboard instance will be created using
	 * {@link Blackboard blackboards} default constructor.
	 */
	public SemesterSetupService() {
		blackboard = new Blackboard();
		blackboard.load();
	}

	/**
	 * Constructor which allows for injection of the blackboard.
	 * 
	 * @param blackboard
	 */
	public SemesterSetupService(Blackboard blackboard) {
		this.blackboard = blackboard;
	}

	/**
	 * Creates a new course in the program.
	 * 
	 * @param name
	 * @param nbrCredits
	 * @return
	 */
	public int addCourse(String name, int nbrCredits) {
		blackboard.startTransaction();
		int courseId = blackboard.getCatalogBoard().createCourse(name, nbrCredits);
		blackboard.commitTransaction();
		return courseId;
	}

	/**
	 * Adds a new semester to the program
	 * 
	 * @param name
	 * @param year
	 * @return
	 */
	public int addSemester(String name, int year) {
		blackboard.startTransaction();
		int semesterId = blackboard.getCatalogBoard().createSemester(name, year);
		blackboard.commitTransaction();
		return semesterId;
	}

	/**
	 * Clears the current configuration for the
	 * {@link SemesterConfiguration#getSemesterId() semester} and applies the
	 * passed in configuration.
	 * 
	 * @param configuration
	 */
	public void applySemesterConfiguration(SemesterConfiguration configuration, boolean isShadow) {
		Semester semester = blackboard.getCatalogBoard().getSemester(configuration.getSemesterId());
		blackboard.startTransaction();
		blackboard.getCatalogBoard().clearCurrentSemesterConfigurations(configuration.getSemesterId());
		for (TeacherAssistant ta : configuration.getTeacherAssistants()) {
			User user = blackboard.getUserBoard().getUser(ta.getUserId());
			UserAvailability ua = new UserAvailability();
			ua.setUser(user);
			ua.setSemester(semester);
			blackboard.getCatalogBoard().addUserAvailability(ua);
		}

		for (Professor professor : configuration.getProfessors()) {
			User user = blackboard.getUserBoard().getUser(professor.getUserId());
			UserAvailability ua = new UserAvailability();
			ua.setUser(user);
			ua.setSemester(semester);
			blackboard.getCatalogBoard().addUserAvailability(ua);
		}

		for (ConfiguredCourse courseConfig : configuration.getOfferedCourses()) {
			CourseSemester cs = new CourseSemester();
			if (courseConfig.getAssignedProfessorId() != null) {
				cs.setAssignedProfessor(blackboard.getUserBoard().getUser(courseConfig.getAssignedProfessorId()));
			}
			cs.setCourse(blackboard.getCatalogBoard().getCourse(courseConfig.getCourseId()));
			cs.setMaxCourseSize(courseConfig.getMaxCourseSize());
			cs.setSemester(blackboard.getCatalogBoard().getSemester(configuration.getSemesterId()));
			blackboard.getCatalogBoard().addCourseSemester(cs);
		}
		blackboard.commitTransaction();
	}

	/**
	 * Applys the student's semester plan of courses. The priorty of the courses
	 * are based on the order in the List.
	 */
	public void applyStudentSemesterPlan(Student student, List<Course> coursePlan) {
		// TODO - Luc - Apply the student's semester plan to the database
	}

	/**
	 * Returns a list of all the {@link Course courses} available in the
	 * programs.
	 * 
	 * @return
	 */
	public List<Course> getAvailableCourses() {
		return blackboard.getCatalogBoard().getAvailableCourses();
	}

	/**
	 * Returns a list of {@link Professor professors} which are available to the
	 * program.
	 * 
	 * @return
	 */
	public List<Professor> getAvailableProfessors() {
		List<User> users = blackboard.getUserBoard().getAvailableUsersByType(false, false, true);
		List<Professor> professors = new ArrayList<Professor>();
		for (User user : users) {
			professors.add(new Professor(user));
		}
		return professors;
	}

	public List<Semester> getAvailableSemesters() {
		return blackboard.getCatalogBoard().getSemesters();
	}

	/**
	 * Returns a List of {@link TeacherAssistant tas} which are available for
	 * the program.
	 * 
	 * @return
	 */
	public List<TeacherAssistant> getAvailableTeacherAssistants() {
		List<User> users = blackboard.getUserBoard().getAvailableUsersByType(false, true, false);
		List<TeacherAssistant> tas = new ArrayList<TeacherAssistant>();
		for (User user : users) {
			tas.add(new TeacherAssistant(user));
		}
		return tas;
	}

	public Course getCourse(int courseId) {
		return blackboard.getCatalogBoard().getCourse(courseId);
	}

	public Semester getSemester(int semesterId) {
		return blackboard.getByID(Semester.class, semesterId);
	}

	/**
	 * 
	 * Builds and returns as {@link SemesterConfiguration} containing
	 * information regarding the currently configured courses, tas, and
	 * professors for a semester.
	 * 
	 * @param semesterId
	 * @return
	 */
	public SemesterConfiguration getSemesterConfiguration(int semesterId) {
		SemesterConfiguration semesterConfiguration = new SemesterConfiguration();
		semesterConfiguration.setSemesterId(semesterId);
		semesterConfiguration = populateConfigurationCourses(semesterConfiguration, semesterId);
		semesterConfiguration = populateConfigurationProfessors(semesterConfiguration, semesterId);
		semesterConfiguration = populateConfigurationTAs(semesterConfiguration, semesterId);
		return semesterConfiguration;
	}

	private SemesterConfiguration populateConfigurationCourses(SemesterConfiguration semesterConfiguration,
			int semesterId) {
		List<CourseSemester> semesterCourses = blackboard.getCatalogBoard().getSemesterCourses(semesterId);
		List<ConfiguredCourse> configuredCourses = new ArrayList<>();
		for (CourseSemester cs : semesterCourses) {
			configuredCourses.add(new ConfiguredCourse(cs));
		}
		semesterConfiguration.setOfferedCourses(configuredCourses);
		return semesterConfiguration;
	}

	private SemesterConfiguration populateConfigurationProfessors(SemesterConfiguration semesterConfiguration,
			int semesterId) {
		List<UserAvailability> professorConfigurations = blackboard.getUserBoard()
				.getAvailableUsersBySemesterAndType(false, false, true, semesterId);
		for (UserAvailability user : professorConfigurations) {
			semesterConfiguration.getProfessors().add(new Professor(user.getUser()));
		}
		return semesterConfiguration;
	}

	private SemesterConfiguration populateConfigurationTAs(SemesterConfiguration semesterConfiguration,
			int semesterId) {
		List<UserAvailability> taConfigurations = blackboard.getUserBoard().getAvailableUsersBySemesterAndType(false,
				true, false, semesterId);
		for (UserAvailability user : taConfigurations) {
			semesterConfiguration.getTeacherAssistants().add(new TeacherAssistant(user.getUser()));
		}
		return semesterConfiguration;
	}

}
