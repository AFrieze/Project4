package project4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.gatechproject.project4.BAL.dto.ConfiguredCourse;
import org.gatechproject.project4.BAL.dto.Professor;
import org.gatechproject.project4.BAL.dto.SemesterConfiguration;
import org.gatechproject.project4.BAL.dto.Student;
import org.gatechproject.project4.BAL.dto.StudentSemesterPreferences;
import org.gatechproject.project4.BAL.dto.TeacherAssistant;
import org.gatechprojects.project4.BAL.Membership;
import org.gatechprojects.project4.BAL.SemesterSetupService;
import org.gatechprojects.project4.BAL.StaffService;
import org.gatechprojects.project4.BAL.UserService;
import org.gatechprojects.project4.DAL.Blackboard;
import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.InputCourseCompetence;
import org.gatechprojects.project4.SharedDataModules.InputOfferedCourse;
import org.gatechprojects.project4.SharedDataModules.InputProfessor;
import org.gatechprojects.project4.SharedDataModules.InputStudent;
import org.gatechprojects.project4.SharedDataModules.InputStudentCoursePreference;
import org.gatechprojects.project4.SharedDataModules.InputTA;
import org.gatechprojects.project4.SharedDataModules.OptimizerCalculation;
import org.gatechprojects.project4.SharedDataModules.Semester;
import org.gatechprojects.project4.SharedDataModules.User;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IntegrationTests {
	// static final Logger LOG =
	// LogManager.getLogger(IntegrationTests.class.getName());
	private IntegrationTestDB testDB = new IntegrationTestDB();
	private Session dbSession;

	@Test
	public void logSomething() {
		// LOG.info("Testing log, This test should be removed");
	}

	@Test
	public void membershipTest() {
		String userName = "Bob.Test";
		String password = "somevalue";

		Membership membership = new Membership();
		int membershipId = membership.registerMember(userName, password);
		assertTrue(membership.authenticate(userName, password));
		assertFalse(membership.authenticate("randomuser", "badvalue"));

		String firstName = "Bob";
		UserService userService = new UserService();
		userService.addStudent(membershipId, firstName, "Test");

		Student student = userService.getStudentByMembershipId(membershipId);
		assertEquals(firstName, student.getFirstName());

	}

	@Test
	public void semesterConfigurationAndStudentPreferencesTest() {

		StaffService staffService = new StaffService();
		UserService userService = new UserService();
		SemesterSetupService semesterService = new SemesterSetupService();
		// seed some test data
		Student student = userService.addStudent(null, "Andrew", "Burke");
		Professor professor = staffService.addProfessor(null, "Tim", "Block");
		TeacherAssistant ta = staffService.addTeacherAssistant(null, "John", "Jackson");
		int semesterId = semesterService.addSemester("Spring", 2016);
		Semester semester = semesterService.getSemester(semesterId);
		int course1Id = semesterService.addCourse("cs 6300 - Software Process", 3);
		Course course = semesterService.getCourse(course1Id);
		staffService.addCourseCompetency(course1Id, professor.getUserId());

		// refresh the caches
		staffService = new StaffService();
		userService = new UserService();
		semesterService = new SemesterSetupService();

		// verify that the seeded data is now available
		assertEquals(1, semesterService.getAvailableCourses().size());
		assertEquals(1, staffService.getAvailableProfessors().size());
		assertEquals(1, staffService.getAvailableProfessors().get(0).getCourseCompetencies().size());
		assertEquals(1, staffService.getAvailableTeacherAssistants().size());

		// verify that the current semesterConfiguration is empty
		SemesterConfiguration configuration = semesterService.getSemesterConfiguration(semesterId, false);
		assertEquals(0, configuration.getOfferedCourses().size());
		assertEquals(0, configuration.getProfessors().size());
		assertEquals(0, configuration.getTeacherAssistants().size());
		assertEquals(semesterId, configuration.getSemesterId());

		// configure the seeded data to apply to the semesterId
		ConfiguredCourse configuredCourse = new ConfiguredCourse();
		configuredCourse.setCourseId(course1Id);
		configuredCourse.setAssignedProfessorId(professor.getUserId());
		configuration.getOfferedCourses().add(configuredCourse);
		configuration.getProfessors().add(professor);
		configuration.getTeacherAssistants().add(ta);
		// apply the new semesterConfiguration
		semesterService.applySemesterConfiguration(configuration, false);

		// verify that the new configuration has taken effect
		configuration = semesterService.getSemesterConfiguration(semesterId, false);
		assertEquals(1, configuration.getOfferedCourses().size());
		assertEquals(1, configuration.getProfessors().size());
		assertEquals(1, staffService.getAvailableProfessors().get(0).getCourseCompetencies().size());
		assertEquals(1, configuration.getTeacherAssistants().size());
		assertEquals(semesterId, configuration.getSemesterId());

		// verify current student preferences
		StudentSemesterPreferences preferences = userService.getStudentPreferences(student.getUserId(), semesterId);
		assertEquals(0, preferences.getPreferredCourses().size());
		assertEquals(student.getUserId(), preferences.getUserId());
		assertEquals(0, preferences.getPreferredCourses().size());
		assertEquals(semesterId, preferences.getSemesterId());

		// update preferences
		preferences.getPreferredCourses().add(configuration.getOfferedCourses().get(0));
		preferences.setNbrCoursesDesired(1);
		userService.applyStudentPreferences(preferences);

		// verify preferences updated
		preferences = userService.getStudentPreferences(student.getUserId(), semesterId);
		assertEquals(1, preferences.getPreferredCourses().size());
		assertEquals(1, preferences.getNbrCoursesDesired());

		// test creation of OptimizerCalculation
		Blackboard blackboard = new Blackboard();
		blackboard.load();
		OptimizerCalculation calculation = new OptimizerCalculation();
		InputStudent inputStudent = new InputStudent();
		inputStudent.setOptimizerCalculation(calculation);
		inputStudent.setCreditsTaken(9);
		inputStudent.setUser(blackboard.getByID(User.class, student.getUserId()));
		InputStudentCoursePreference coursePreference = new InputStudentCoursePreference();
		coursePreference.setCourse(course);
		coursePreference.setCoursePriority(2);
		coursePreference.setInputStudent(inputStudent);
		inputStudent.getCoursePreferences().add(coursePreference);
		calculation.getInputStudents().add(inputStudent);

		InputTA inputTa = new InputTA();
		inputTa.setUser(blackboard.getByID(User.class, ta.getUserId()));
		inputTa.setOptimizerCalculation(calculation);
		calculation.getInputTAs().add(inputTa);

		InputProfessor inputProfessor = new InputProfessor();
		inputProfessor.setOptimizerCalculation(calculation);
		inputProfessor.setUser(blackboard.getByID(User.class, professor.getUserId()));
		InputCourseCompetence inputCourseCompetence = new InputCourseCompetence();
		inputCourseCompetence.setCourse(course);

		inputCourseCompetence.setInputProfessor(inputProfessor);
		inputProfessor.getCourseCompetencies().add(inputCourseCompetence);
		calculation.getInputProfessors().add(inputProfessor);

		InputOfferedCourse offeredCourse = new InputOfferedCourse();
		offeredCourse.setAssignedProfessor(blackboard.getByID(User.class, professor.getUserId()));
		offeredCourse.setMaxCourseSize(150);
		offeredCourse.setOptimizerCalculation(calculation);
		calculation.getInputOfferedCourses().add(offeredCourse);

		blackboard.startTransaction();
		Integer calculationId = blackboard.getOptimizerBoard().createOptimizerCalculation(calculation);
		blackboard.commitTransaction();
		calculation = blackboard.getByID(OptimizerCalculation.class, calculationId);
		assertEquals(1, calculation.getInputStudents().size());
		assertEquals(1, calculation.getInputStudents().get(0).getCoursePreferences().size());
		assertEquals(9, calculation.getInputStudents().get(0).getCreditsTaken());
		assertEquals(1, calculation.getInputTAs().size());
		assertEquals(1, calculation.getInputProfessors().size());
		assertEquals(1, calculation.getInputOfferedCourses().size());
		assertEquals(150, calculation.getInputOfferedCourses().get(0).getMaxCourseSize());
		assertEquals(professor.getUserId(), calculation.getInputOfferedCourses().get(0).getAssignedProfessor().getId());

	}

	@Before
	public void setup() {
		testDB.startTestingSession();
	}

	@After
	public void teardown() {
		testDB.endTestingSession();
	}

	private int testCourseCreation(SemesterSetupService catalog, String name, int nbrCredits) {
		int courseId = catalog.addCourse(name, nbrCredits);
		Course course = catalog.getCourse(courseId);
		assertEquals(name, course.getName());
		assertEquals(nbrCredits, course.getCredits());
		return courseId;
	}

	private int testSemesterCreation(SemesterSetupService catalog, String name, int year) {
		int semesterId = catalog.addSemester(name, year);
		Semester semester = catalog.getSemester(semesterId);
		assertEquals(name, semester.getName());
		assertEquals(year, semester.getYear());
		return semesterId;
	}

	private int testUserCreate(UserService userService, String firstName, String lastName, boolean isStudent,
			boolean isTA, boolean isProfessor) {
		Student student = userService.addStudent(null, firstName, lastName);
		Student fetchedStudent = userService.getStudentById(student.getUserId());
		assertEquals(firstName, fetchedStudent.getFirstName());
		assertEquals(lastName, fetchedStudent.getLastName());
		return student.getUserId();
	}

	@Test
	public void userTests() {
		UserService userService = new UserService();
		SemesterSetupService catalogService = new SemesterSetupService();
		String firstName = "Andrew";
		String lastName = "Freeze";
		String correctedLastName = "Frieze";
		boolean isTA = false;
		boolean isStudent = true;
		boolean isProfessor = false;
		int userId = testUserCreate(userService, firstName, lastName, isStudent, isTA, isProfessor);

		String courseName = "CS 6100";
		int nbrCredits = 3;
		int courseId = testCourseCreation(catalogService, courseName, nbrCredits);

		String semesterName = "Spring";
		int semesterYear = 2016;
		int semesterId = testSemesterCreation(catalogService, semesterName, semesterYear);
	}

}
