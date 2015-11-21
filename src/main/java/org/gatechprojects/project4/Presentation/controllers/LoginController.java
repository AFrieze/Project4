package org.gatechprojects.project4.Presentation.controllers;

import java.util.HashMap;

import spark.ModelAndView;
import spark.Request;

public class LoginController {

	public ModelAndView getLoginPage(Request request) {
		return new ModelAndView(new HashMap(), "templates/login.vtl");
	}

	public ModelAndView postLoginPage(Request request) {
		// authenticate user here
		// redirect to logged in page or send back to loginPage
		return new ModelAndView(new HashMap(), "templates/login.vtl");
	}

}
