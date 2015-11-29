/**
 * 
 */
package org.gatechprojects.project4.Presentation.controllers.students;

import java.util.HashMap;

import spark.ModelAndView;
import spark.Request;

/**
 * @author Bryce
 *
 */
public class HomeController {

	private static String SESSION_USER = "user";
	private static String PAGE_TITLE = "Student Home Page";
	private static String LAYOUT_TEMPLATE = "templates/layout.vtl";
	
	public ModelAndView getHomePage(Request request) {
		// TODO Auto-generated method stub
		HashMap model = new HashMap();
		model.put("template", "templates/student/home.vtl");
		model.put("pageTitle", PAGE_TITLE);
		model.put("includeHeader", true);
		model.put("user", request.session().attribute(SESSION_USER));
		model.put("request", request);
		return new ModelAndView(model, LAYOUT_TEMPLATE);
	}

}
