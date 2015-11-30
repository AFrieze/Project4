package org.gatechprojects.project4.Presentation.controllers.students;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gatechproject.project4.BAL.dto.StudentCourseRecommendation;
import org.gatechprojects.project4.BAL.SemesterSetupService;
import org.gatechprojects.project4.BAL.UserService;
import org.gatechprojects.project4.Presentation.controllers.Controller;
import org.gatechprojects.project4.SharedDataModules.InputStudentCoursePreference;
import org.gatechprojects.project4.SharedDataModules.OptimizerCalculation;
import org.gatechprojects.project4.SharedDataModules.User;

import spark.ModelAndView;
import spark.Request;

public class SolutionHistoryController extends Controller {
	
	private static String PAGE_TITLE = "Solution History";
	private static String TEMPLATE_PATH = "templates/student/solutionhistory.vtl";
	
	
	public ModelAndView getHistory(Request request) {
		
		HashMap<String, Object> model = new HashMap<String, Object>();
		long filterTime;
		Calendar filterCal = Calendar.getInstance();
		
		UserService userService = new UserService();
		SemesterSetupService courseService = new SemesterSetupService();
		User currentUser = request.session().attribute(SESSION_USER);
		
		
		if(request.queryParams("selectedDate") != null && request.queryParams("selectedDate") != ""){
			filterTime = Long.parseLong(request.queryParams("selectedDate"));
		} else {
			filterTime = new Date().getTime();
		}
		
		filterCal.setTimeInMillis(filterTime);
		
		int currentOptimizerId = courseService.getOptimizerCalculationId(false, filterCal );
		
		if(currentOptimizerId > -1){
			OptimizerCalculation optimizerCalculation = courseService.getOptimizerCalculation(currentOptimizerId);
			List<InputStudentCoursePreference> currentStudentPreferences = userService.getStudentOptimizerPreferences(currentUser.getId() , currentOptimizerId);
			List<StudentCourseRecommendation> currentStudentRecommendation = userService.getStudentCourseRecommendations(currentUser.getId() , currentOptimizerId);
			model.put("preferences", currentStudentPreferences);
			model.put("optimizerCalculation", optimizerCalculation);
			model.put("recommendations", currentStudentRecommendation);
			
		} else {
			model.put("notSolution", true);
		}
		
		
		model.put("template", TEMPLATE_PATH);
		model.put("pageTitle", PAGE_TITLE);
		model.put("includeHeader", true);
		model.put("user", currentUser);
		model.put("request", request);
		return new ModelAndView(model, LAYOUT_TEMPLATE);
		
	}
	
}
