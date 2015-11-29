package org.gatechprojects.project4.Presentation.controllers.students;

import java.util.HashMap;

import org.gatechproject.project4.BAL.dto.LoginHistoryReport;
import org.gatechproject.project4.BAL.dto.Student;
import org.gatechprojects.project4.SharedDataModules.User;
import org.gatechprojects.project4.Presentation.controllers.Controller;

import spark.ModelAndView;
import spark.Request;

public class LoginHistoryController extends Controller {

	private static String PAGE_TITLE = "Login History";
	
	public ModelAndView getHistory(Request request) {
		
		HashMap<String, Object> model = new HashMap<String, Object>();
		Student currentStudent = new Student((User) request.session().attribute(SESSION_USER));
		LoginHistoryReport loginHistory = new LoginHistoryReport(currentStudent);
		
		model.put("template", "templates/student/loginhistory.vtl");
		model.put("pageTitle", PAGE_TITLE);
		model.put("includeHeader", true);
		model.put("user", request.session().attribute(SESSION_USER));
		model.put("loginHistory", loginHistory);
		model.put("dateFormat", DATE_FORMAT);
		model.put("currentStudent", currentStudent);
		model.put("request", request);
		return new ModelAndView(model, LAYOUT_TEMPLATE);
	}

}
