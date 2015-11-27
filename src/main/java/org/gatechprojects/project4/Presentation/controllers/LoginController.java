package org.gatechprojects.project4.Presentation.controllers;

import org.gatechprojects.project4.BAL.Membership;
import org.gatechprojects.project4.SharedDataModules.User;

import java.util.HashMap;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class LoginController {

	private static String SESSION_USER = "user";
	private static String PAGE_TITLE = "Login";
	private static String LAYOUT_TEMPLATE = "templates/layout.vtl";
	
	public ModelAndView getLoginPage(Request request) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		model.put("template", "templates/login.vtl");
		model.put("pageTitle", PAGE_TITLE);
		model.put("includeHeader", false);
		model.put("request", request);
		return new ModelAndView(model, LAYOUT_TEMPLATE);
	}

	public ModelAndView postLoginPage(Request request, Response response) {
		// authenticate user here
		// redirect to logged in page or send back to loginPage
		
		HashMap model = new HashMap();
		Membership membership = new Membership();
		
		
		// NOT SURE WHY BUT request.params("inputUsername") returns a empty string 
		if(request.queryParams("inputUsername") != "" && request.queryParams("inputPassword") != ""){
			
			if(membership.authenticate(request.queryParams("inputUsername"), request.queryParams("inputPassword"))){
				request.session().attribute(SESSION_USER, membership.getUser(request.queryParams("inputUsername"), request.queryParams("inputPassword")));
				if(((User) request.session().attribute(SESSION_USER)).isAdministrator()){
					response.redirect("/admin/home");
				} else {
					response.redirect("/student/home");
				}
			
			} else {
				// MAYBE ADD A MESSAGE;
			};
			
		} else {
			// MAYBE ADD A MESSAGE;
		}
		
		model.put("template", "templates/login.vtl");
		model.put("pageTitle", PAGE_TITLE);
		model.put("includeHeader", false);
		model.put("request", request);

		return new ModelAndView(model, LAYOUT_TEMPLATE);
	}

}