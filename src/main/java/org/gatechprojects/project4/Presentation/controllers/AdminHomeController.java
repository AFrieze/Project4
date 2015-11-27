package org.gatechprojects.project4.Presentation.controllers;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Request;

public class AdminHomeController {
	
	private static String SESSION_USER = "user";
	private static String PAGE_TITLE = "Administrator Home Page";
	private static String LAYOUT_TEMPLATE = "templates/layout.vtl";
	
	public ModelAndView getHomePage(Request request) {
		// TODO Auto-generated method stub
		HashMap model = new HashMap();
		model.put("template", "templates/admin/home.vtl");
		model.put("pageTitle", PAGE_TITLE);
		model.put("includeHeader", true);
		model.put("user", request.session().attribute(SESSION_USER));
		model.put("request", request);
		return new ModelAndView(model, LAYOUT_TEMPLATE);
	}

}
