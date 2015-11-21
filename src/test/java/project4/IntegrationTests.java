package project4;

import static org.junit.Assert.assertEquals;

import org.gatechproject.project4.BAL.reports.Professor;
import org.gatechproject.project4.BAL.reports.SemesterConfiguration;
import org.gatechproject.project4.BAL.reports.TeacherAssistant;
import org.gatechprojects.project4.BAL.SemesterSetupService;
import org.gatechprojects.project4.BAL.StaffService;
import org.gatechprojects.project4.BAL.UserService;
import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.CourseSemester;
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
	public void semesterConfigurationTest() {

		StaffService staffService = new StaffService();
		UserService userService = new UserService();
		SemesterSetupService semesterService = new SemesterSetupService();
		// seed some test data
		int userId = userService.addUser("Andrew", "Burke", true, false, false);
		int professorId = userService.addUser("Tim", "Block", false, false, true);
		int taId = userService.addUser("John", "Jackson", false, true, false);
		int semesterId = semesterService.addSemester("Spring", 2016);
		Semester semester = semesterService.getSemester(semesterId);
		int course1Id = semesterService.addCourse("cs 6300 - Software Process", 3);
		Course course = semesterService.getCourse(course1Id);
		staffService.addCourseCompetency(course1Id, professorId);

		// verify that the seeded data is now available
		assertEquals(1, semesterService.getAvailableCourses().size());
		assertEquals(1, semesterService.getAvailableProfessors().size());
		assertEquals(1, semesterService.getAvailableProfessors().get(0).getCourseCompetencies().size());
		assertEquals(1, semesterService.getAvailableTeacherAssistants().size());

		// verify that the current semesterConfiguration is empty
		SemesterConfiguration configuration = semesterService.getSemesterConfiguration(semesterId);
		assertEquals(0, configuration.getCourses().size());
		assertEquals(0, configuration.getProfessors().size());
		assertEquals(0, configuration.getTeacherAssistants().size());
		assertEquals(semesterId, configuration.getSemesterId());

		// configure the seeded data to apply to the semesterId
		User professorUser = userService.getUser(professorId);
		CourseSemester cs = new CourseSemester();
		cs.setSemester(semester);
		cs.setCourse(course);
		cs.setAssignedProfessor(professorUser);
		configuration.getCourses().add(cs);
		Professor professor = new Professor(professorUser);
		configuration.getProfessors().add(professor);
		configuration.getTeacherAssistants().add(new TeacherAssistant(userService.getUser(taId)));

		// apply the new semesterConfiguration
		semesterService.applySemesterConfiguration(configuration, false);

		// verify that the new configuration has taken effect
		configuration = semesterService.getSemesterConfiguration(semesterId);
		assertEquals(1, configuration.getCourses().size());
		assertEquals(1, configuration.getProfessors().size());
		assertEquals(1, semesterService.getAvailableProfessors().get(0).getCourseCompetencies().size());
		assertEquals(1, configuration.getTeacherAssistants().size());
		assertEquals(semesterId, configuration.getSemesterId());
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
		int userId = userService.addUser(firstName, lastName, isStudent, isTA, isProfessor);
		User user = userService.getUser(userId);
		assertEquals(firstName, user.getFirstName());
		assertEquals(lastName, user.getLastName());
		assertEquals(isStudent, user.isStudent());
		assertEquals(isTA, user.isTA());
		assertEquals(isProfessor, user.isProfessor());
		return userId;
	}

	private void testUserUpdate(UserService userService, int userId, String firstName, String correctedLastName,
			boolean isStudent, boolean isTA, boolean isProfessor) {

		userService.updateUser(userId, firstName, correctedLastName, isStudent, isTA, isProfessor);
		User user = userService.getUser(userId);
		assertEquals(firstName, user.getFirstName());
		assertEquals(correctedLastName, user.getLastName());
		assertEquals(isStudent, user.isStudent());
		assertEquals(isTA, user.isTA());
		assertEquals(isProfessor, user.isProfessor());
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
		testUserUpdate(userService, userId, firstName, correctedLastName, isStudent, isTA, isProfessor);

		String courseName = "CS 6100";
		int nbrCredits = 3;
		int courseId = testCourseCreation(catalogService, courseName, nbrCredits);

		String semesterName = "Spring";
		int semesterYear = 2016;
		int semesterId = testSemesterCreation(catalogService, semesterName, semesterYear);
	}

}
