package project4;

import static org.junit.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.gatechprojects.project4.BAL.SemesterSetupService;
import org.gatechprojects.project4.BAL.UserService;
import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.Semester;
import org.gatechprojects.project4.SharedDataModules.User;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserIntegrationTest {
	static final Logger LOG = LogManager.getLogger(UserIntegrationTest.class.getName());
	private IntegrationTestDB testDB = new IntegrationTestDB();
	private Session dbSession;

	@Test
	public void logSomething() {
		LOG.info("Testing log, This test should be removed");
	}

	@Test
	public void semesterConfigurationTest() {

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
