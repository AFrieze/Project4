/**
 * 
 */
package org.gatechprojects.project4.Presentation.controllers.students;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.gatechproject.project4.BAL.dto.ConfiguredCourse;
import org.gatechproject.project4.BAL.dto.StudentCourseRecommendation;
import org.gatechproject.project4.BAL.dto.StudentSemesterPreferences;
import org.gatechprojects.project4.BAL.SemesterSetupService;
import org.gatechprojects.project4.BAL.UserService;
import org.gatechprojects.project4.Presentation.controllers.Controller;
import org.gatechprojects.project4.SharedDataModules.OptimizerCalculation;
import org.gatechprojects.project4.SharedDataModules.Semester;
import org.gatechprojects.project4.SharedDataModules.User;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 * @author Bryce
 *
 */
public class NextSemesterCoursesController extends Controller {

	private static String PAGE_TITLE = "Next Semester Courses";
	private static int MAX_COURSES = 2;

	public ModelAndView getNextSemesterCoursesPage(Request request) {

		HashMap<String, Object> model = createModel(request);

		return new ModelAndView(model, LAYOUT_TEMPLATE);
	}

	public ModelAndView postNextSemesterCoursesPage(Request request, Response response) {
		// Setup to process request
		HashMap<String, Object> model = createModel(request);
		LinkedHashMap<String, String> message = new LinkedHashMap<String, String>();
		List<ConfiguredCourse> preferredCourses = new ArrayList<ConfiguredCourse>();

		// Get Services and objects
		UserService userService = new UserService();
		SemesterSetupService courseService = new SemesterSetupService();
		User currentUser = request.session().attribute(SESSION_USER);

		// Create configuration
		StudentSemesterPreferences studentPreferences = new StudentSemesterPreferences();

		// Setup configuration
		Semester currentSemester = courseService.getCurrentSemester();
		studentPreferences.setUserId(currentUser.getId());
		studentPreferences.setSemesterId(currentSemester.getId());

		// Process request
		if (request.queryParams("selectedCourses") != null && !request.queryParams("selectedCourses").isEmpty()) {

			String selectCoursesListStr = request.queryParams("selectedCourses");
			String[] selectCoursesArrayStr = selectCoursesListStr.split(",");

			for (int i = 0; i < selectCoursesArrayStr.length; i++) {
				int courseId = Integer.parseInt(selectCoursesArrayStr[i]);
				ConfiguredCourse configuredCourse = new ConfiguredCourse(courseService.getCourse(courseId));
				preferredCourses.add(configuredCourse);
			}
			int desiredNumberCourses = selectCoursesArrayStr.length;
			if (desiredNumberCourses > MAX_COURSES) {
				desiredNumberCourses = MAX_COURSES;
			}

			studentPreferences.setNbrCoursesDesired(desiredNumberCourses);

			studentPreferences.setPreferredCourses(preferredCourses);
			try {
				userService.applyStudentPreferences(studentPreferences);
				message.put("type", "success");
				message.put("summary", "Success! ");
				message.put("message", "Preferences updated.");
			} catch (Exception e) {
				System.out.println(e);
				message.put("type", "danger");
				message.put("summary", "Error! ");
				message.put("message", e.toString());
			}
		} else {
			// DO WE WANT TO NO ALLOW ZERO COURSE AS A PREFERENCE?
			message.put("type", "warning");
			message.put("summary", "Warning! ");
			message.put("message", " No course were selected.");
		}

		model.put("includeMessage", true);
		model.put("message", message);
		return new ModelAndView(model, LAYOUT_TEMPLATE);
	}

	private HashMap<String, Object> createModel(Request request) {
		// Setup Object
		HashMap<String, Object> model = new HashMap<String, Object>();
		// Get Services
		SemesterSetupService courseService = new SemesterSetupService();
		UserService userService = new UserService();
		// Get Return Object
		User currentUser = request.session().attribute(SESSION_USER);
		int currentOptimizerId = courseService.getOptimizerCalculationId(false, Calendar.getInstance());
		List<StudentCourseRecommendation> currentStudentSolutionRecommendations = userService
				.getStudentCourseRecommendations(currentUser.getId(), currentOptimizerId);
		OptimizerCalculation optimizerCalculation = courseService.getOptimizerCalculation(currentOptimizerId);
		// Normal Configuration
		model.put("template", "templates/student/nextsemestercourses.vtl");
		model.put("pageTitle", PAGE_TITLE);
		model.put("includeHeader", true);
		model.put("user", currentUser);
		model.put("request", request);
		// Extra Objects
		model.put("courses", courseService.getCurrentSemesterCourses());
		model.put("optimizerCalculation", optimizerCalculation);
		model.put("recommndations", currentStudentSolutionRecommendations);
		// Return model
		return model;
	}

}
