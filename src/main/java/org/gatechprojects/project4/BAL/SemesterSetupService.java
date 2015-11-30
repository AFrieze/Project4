package org.gatechprojects.project4.BAL;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.gatechproject.project4.BAL.dto.ConfiguredCourse;
import org.gatechproject.project4.BAL.dto.CourseRecommendation;
import org.gatechproject.project4.BAL.dto.Professor;
import org.gatechproject.project4.BAL.dto.SemesterConfiguration;
import org.gatechproject.project4.BAL.dto.TeacherAssistant;
import org.gatechprojects.project4.DAL.Blackboard;
import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.CourseSemester;
import org.gatechprojects.project4.SharedDataModules.OptimizerCalculation;
import org.gatechprojects.project4.SharedDataModules.OutputOfferedCourse;
import org.gatechprojects.project4.SharedDataModules.Semester;
import org.gatechprojects.project4.SharedDataModules.User;
import org.gatechprojects.project4.SharedDataModules.UserAvailability;

/**
 * 
 * Provides access to data needed to understand and configure a sememster.
 * 
 * @author Andrew
 *
 */
public class SemesterSetupService {

	private Blackboard blackboard;

	/**
	 * Default public constructor. A Blackboard instance will be created using
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
	 * <p>
	 * Generally used in conjunction with {@link #getSemesterConfiguration(int)}
	 * 
	 * @param configuration
	 */
	public void applySemesterConfiguration(SemesterConfiguration configuration, boolean isShadow) {
		Semester semester = blackboard.getCatalogBoard().getSemester(configuration.getSemesterId());
		blackboard.startTransaction();
		blackboard.getCatalogBoard().clearCurrentSemesterConfigurations(configuration.getSemesterId(), false);
		for (TeacherAssistant ta : configuration.getTeacherAssistants()) {
			User user = blackboard.getUserBoard().getUser(ta.getUserId());
			UserAvailability ua = new UserAvailability();
			ua.setUser(user);
			ua.setSemester(semester);
			ua.setShadow(isShadow);
			blackboard.getCatalogBoard().addUserAvailability(ua);
		}

		for (Professor professor : configuration.getProfessors()) {
			User user = blackboard.getUserBoard().getUser(professor.getUserId());
			UserAvailability ua = new UserAvailability();
			ua.setUser(user);
			ua.setSemester(semester);
			ua.setShadow(isShadow);
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
			cs.setShadow(isShadow);
			blackboard.getCatalogBoard().addCourseSemester(cs);
		}
		blackboard.commitTransaction();
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
	
	public List<Course> getCurrentSemesterCourses() {
		//List<CourseSemester> currentSemesterCourses = blackboard.getCatalogBoard().getSemesterCourses(blackboard.getCatalogBoard().getMostRecentSemester().getId(), false);
		List<CourseSemester> currentSemesterCourses = new ArrayList<CourseSemester>(); 
		List<Course> avaliableCourses = new ArrayList<Course>();
		for (CourseSemester courseSemesterObj : currentSemesterCourses) {
			avaliableCourses.add(courseSemesterObj.getCourse());
		}
		return avaliableCourses;
	}

	/**
	 * @return the {@link Semester semesters} available in the system
	 */
	public List<Semester> getAvailableSemesters() {
		return blackboard.getCatalogBoard().getSemesters();
	}
	
	public Semester getCurrentSemester(){
		//return blackboard.getCatalogBoard().getMostRecentSemester();
		return null;
	}
	

	public Course getCourse(int courseId) {
		return blackboard.getCatalogBoard().getCourse(courseId);
	}

	/**
	 * Returns the {@link OptimizerCalculation#getId() id} of the optimization
	 * calculation which occurred closest to and before the passed in time. If
	 * no calculation is found, -1 is returned.
	 * 
	 * @param isShadow
	 * @param time
	 * @return
	 */
	public int getOptimizerCalculationId(boolean isShadow, Calendar time) {
		return blackboard.getOptimizerBoard().getOptimizerCalculationId(time, isShadow);
	}
	
	public OptimizerCalculation getOptimizerCalculation(int optimizerCalculationId) {
		return blackboard.getOptimizerBoard().getOptimizerCalculation(optimizerCalculationId);
	}
	
	public List<OptimizerCalculation> getLastOptimizerCalculations(int limit){
		return blackboard.getOptimizerBoard().getLastOptimizerCalculations(limit);
	}

	/**
	 * Returns a list of {@link CourseRecommendation courseRecommendations} made
	 * by the optimizer for the provided optimizerCalculationId.
	 * 
	 * @param optimizerCalculationId
	 * @return
	 */
	public List<CourseRecommendation> getOptimizerCourseRecommendations(int optimizerCalculationId) {

		List<OutputOfferedCourse> offeredCourses = blackboard.getOptimizerBoard()
				.getOfferedCourses(optimizerCalculationId);
		List<CourseRecommendation> recommendations = new ArrayList<>();
		for (OutputOfferedCourse offeredCourse : offeredCourses) {
			recommendations.add(new CourseRecommendation(offeredCourse));
		}
		return recommendations;
	}

	public Semester getSemester(int semesterId) {
		return blackboard.getByID(Semester.class, semesterId);
	}

	/**
	 * 
	 * Builds and returns as {@link SemesterConfiguration} containing
	 * information regarding the currently configured courses, tas, and
	 * professors for a semester. If the passed in isShadow parameter is true,
	 * the most recent shadow for the semester is loaded.
	 * <p>
	 * 
	 * 
	 * 
	 * <p>
	 * One recommended usage of this method to request the
	 * SemesterConfiguration, modify it, then apply your modifications through a
	 * call to
	 * {@link #applySemesterConfiguration(SemesterConfiguration, boolean)
	 * applySemesterConfiguration}.
	 * 
	 * @param semesterId
	 * @return
	 */
	public SemesterConfiguration getSemesterConfiguration(int semesterId, boolean isShadow) {
		SemesterConfiguration semesterConfiguration = new SemesterConfiguration();
		semesterConfiguration.setSemesterId(semesterId);
		semesterConfiguration = populateConfigurationCourses(semesterConfiguration, semesterId, isShadow);
		semesterConfiguration = populateConfigurationProfessors(semesterConfiguration, semesterId, isShadow);
		semesterConfiguration = populateConfigurationTAs(semesterConfiguration, semesterId, isShadow);
		return semesterConfiguration;
	}

	private SemesterConfiguration populateConfigurationCourses(SemesterConfiguration semesterConfiguration,
			int semesterId, boolean isShadow) {
		List<CourseSemester> semesterCourses = blackboard.getCatalogBoard().getSemesterCourses(semesterId, isShadow);
		List<ConfiguredCourse> configuredCourses = new ArrayList<>();
		for (CourseSemester cs : semesterCourses) {
			configuredCourses.add(new ConfiguredCourse(cs,true));
		}
		semesterConfiguration.setOfferedCourses(configuredCourses);
		return semesterConfiguration;
	}

	private SemesterConfiguration populateConfigurationProfessors(SemesterConfiguration semesterConfiguration,
			int semesterId, boolean isShadow) {
		List<UserAvailability> professorConfigurations = blackboard.getUserBoard()
				.getAvailableUsersBySemesterAndType(false, false, true, semesterId, isShadow);
		for (UserAvailability user : professorConfigurations) {
			semesterConfiguration.getProfessors().add(new Professor(user.getUser()));
		}
		return semesterConfiguration;
	}

	private SemesterConfiguration populateConfigurationTAs(SemesterConfiguration semesterConfiguration, int semesterId,
			boolean isShadow) {
		List<UserAvailability> taConfigurations = blackboard.getUserBoard().getAvailableUsersBySemesterAndType(false,
				true, false, semesterId, isShadow);
		for (UserAvailability user : taConfigurations) {
			semesterConfiguration.getTeacherAssistants().add(new TeacherAssistant(user.getUser()));
		}
		return semesterConfiguration;
	}
}
