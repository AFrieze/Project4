package org.gatechprojects.project4.Presentation.controllers.students;

import java.util.HashMap;

import spark.ModelAndView;
import spark.Request;

public class LoginHistoryController {

	private static String SESSION_USER = "user";
	private static String PAGE_TITLE = "Login History";
	private static String LAYOUT_TEMPLATE = "templates/layout.vtl";
	
	public ModelAndView getHistory(Request request) {
		
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		model.put("template", "templates/student/loginhistory.vtl");
		model.put("pageTitle", PAGE_TITLE);
		model.put("includeHeader", true);
		model.put("user", request.session().attribute(SESSION_USER));
		model.put("request", request);
		return new ModelAndView(model, LAYOUT_TEMPLATE);
	}

}
