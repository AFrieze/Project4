/**
 * 
 */
package org.gatechprojects.project4.Presentation.controllers.students;


//USE FOR FULL LIST UNTIL FILTER LIST IS AVALIABLE
import org.gatechprojects.project4.BAL.SemesterSetupService;

import org.gatechprojects.project4.BAL.UserService;
import org.gatechprojects.project4.SharedDataModules.User;

import java.util.HashMap;
import java.util.LinkedHashMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 * @author bryce
 *
 */
public class NextSemesterCoursesController {
	
	private static String SESSION_USER = "user";
	private static String PAGE_TITLE = "Next Semester Courses";
	private static String LAYOUT_TEMPLATE = "templates/layout.vtl";
	private static int MAX_COURSES = 2;
	
	public ModelAndView getNextSemesterCoursesPage(Request request) {
		
		HashMap model = new HashMap();
		model.put("template", "templates/student/nextsemestercourses.vtl");
		model.put("pageTitle", PAGE_TITLE);
		model.put("includeHeader", true);
		model.put("user", request.session().attribute(SESSION_USER));
		
		//USE SETUP SERVICE FOR NOW
		SemesterSetupService courseService = new SemesterSetupService();
		model.put("courses", courseService.getAvailableCourses());
		model.put("request", request);
		
		return new ModelAndView(model, LAYOUT_TEMPLATE);
	}

	public ModelAndView postNextSemesterCoursesPage(Request request, Response response) {
		HashMap model = new HashMap();
		LinkedHashMap<String, String> message = new LinkedHashMap<String, String>();
		UserService userService = new UserService();
		
		if(request.queryParams("selectedCourses") != null && !request.queryParams("selectedCourses").isEmpty()) {
			
			String selectCoursesListStr = request.queryParams("selectedCourses");
			String[] selectCoursesArrayStr = selectCoursesListStr.split(",");
			int[] courseIdsArray = new int[selectCoursesArrayStr.length];
			for (int i = 0; i < selectCoursesArrayStr.length; i++) {
			    int courseId = Integer.parseInt(selectCoursesArrayStr[i]);
			    courseIdsArray[i] = courseId;
			}
			int desiredNumberCourses = selectCoursesArrayStr.length;
			if(desiredNumberCourses > MAX_COURSES){
				desiredNumberCourses = MAX_COURSES;
			}
			User currentUser = request.session().attribute(SESSION_USER);
			// NEED BAL METHOD FOR semesterId
			int semesterId = 1;
			// THERE APPEARS TO BE A BUG IN UPDATING USING THE FOLLOWING LINE 
			
			//Hibernate: select user0_.id as id1_8_0_, user0_.firstName as firstNam2_8_0_, user0_.isAdministrator as isAdmini3_8_0_, user0_.isProfessor as isProfes4_8_0_, user0_.isStudent as isStuden5_8_0_, user0_.isTA as isTA6_8_0_, user0_.lastName as lastName7_8_0_, user0_.membership_id as membersh8_8_0_, membership1_.id as id1_3_1_, membership1_.password as password2_3_1_, membership1_.userName as userName3_3_1_ from user user0_ left outer join membership_user membership1_ on user0_.membership_id=membership1_.id where user0_.id=?
			//Hibernate: select semester0_.id as id1_5_0_, semester0_.name as name2_5_0_, semester0_.year as year3_5_0_ from semester semester0_ where semester0_.id=?
			
			//userService.updateStudentPreferences(currentUser.getId(), semesterId, desiredNumberCourses, courseIdsArray);
			
			message.put("type", "success");
			message.put("summary", "Success! ");
			message.put("message", "Preferences updated.");
		} else {
			// DO WE WANT TO NO ALLOW ZERO COURSE AS A PREFERENCE?
			message.put("type", "warning");
			message.put("summary", "Warning! ");
			message.put("message", " No course were selected.");			
		}
		
		
		
		
		model.put("template", "templates/student/nextsemestercourses.vtl");
		model.put("pageTitle", PAGE_TITLE);
		model.put("includeHeader", true);
		model.put("user", request.session().attribute(SESSION_USER));
		
		model.put("includeMessage", true);
		model.put("message", message);
		
		//USE SETUP SERVICE FOR NOW
		SemesterSetupService courseService = new SemesterSetupService();
		model.put("courses", courseService.getAvailableCourses());
		
		model.put("request", request);
		return new ModelAndView(model, LAYOUT_TEMPLATE);
	}

}
