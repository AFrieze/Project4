package org.gatechprojects.project4.Presentation.controllers;

import java.util.Date;
import java.util.HashMap;
import spark.ModelAndView;
import spark.Request;

public class AdminHomeController extends Controller {
	
	private static String SESSION_USER = "user";
	private static String PAGE_TITLE = "Administrator Home Page";
	private static String LAYOUT_TEMPLATE = "templates/layout.vtl";
	
	public ModelAndView getHomePage(Request request) {
		// TODO Auto-generated method stub
		HashMap model = new HashMap();
		model.put("template", "templates/admin/home.vtl");
		model.put("pageTitle", PAGE_TITLE);
		model.put("includeHeader", true);
		Date date = new Date();
		model.put("currentdate", DATE_FORMAT.format(date));
		model.put("user", request.session().attribute(SESSION_USER));
		model.put("request", request);
		return new ModelAndView(model, "templates/admin/home.vtl");
	}

}
