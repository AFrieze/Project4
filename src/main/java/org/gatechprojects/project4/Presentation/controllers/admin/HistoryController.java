package org.gatechprojects.project4.Presentation.controllers.admin;

import java.util.Date;
import java.util.HashMap;

import org.gatechproject.project4.BAL.dto.SemesterConfiguration;
import org.gatechprojects.project4.BAL.SemesterSetupService;
import org.gatechprojects.project4.BAL.StaffService;
import org.gatechprojects.project4.BAL.UserService;
import org.gatechprojects.project4.Presentation.controllers.Controller;
import org.gatechprojects.project4.SharedDataModules.User;

import spark.ModelAndView;
import spark.Request;

public class HistoryController extends Controller {


	private static String PAGE_TITLE = "History";
	private	SemesterSetupService semesterSetupService = new SemesterSetupService();

    private	StaffService staffSetupService = new StaffService();

    
	public ModelAndView getHistoryCourseDemandPage(Request request) {
			
		HashMap<String, Object> model = new HashMap<String, Object>();
		model.put("template", "templates/admin/HistoryCourseDemand.vtl");

		getCommonDetails(model, request);
		return new ModelAndView(model, "templates/admin/HistoryCourseDemand.vtl");
	}

	public ModelAndView postHistoryCourseDemandPage(Request request) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		model.put("template", "templates/admin/HistoryCourseDemand.vtl");

		getCommonDetails(model, request);
		return new ModelAndView(model, "templates/admin/HistoryCourseDemand.vtl");
	}


    
	public ModelAndView getHistoryStudentHistoryPage(Request request) {
			
		HashMap<String, Object> model = new HashMap<String, Object>();
		model.put("template", "templates/admin/HistoryStudentHistory.vtl");

		getCommonDetails(model, request);
		return new ModelAndView(model, "templates/admin/HistoryStudentHistory.vtl");
	}

	public ModelAndView postHistoryStudentHistoryPage(Request request) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		model.put("template", "templates/admin/HistoryStudentHistory.vtl");

		getCommonDetails(model, request);
		return new ModelAndView(model, "templates/admin/HistoryStudentHistory.vtl");
	}


	private void getCommonDetails(HashMap<String, Object> model, Request request)
	{
		model.put("pageTitle", PAGE_TITLE);
		model.put("includeHeader", true);
		model.put("user", ((User)request.session().attribute(SESSION_USER)));
		Date date = new Date();
		model.put("currentdate", DATE_FORMAT.format(date));
		model.put("dateFormat", DATE_FORMAT);
		model.put("request", request);
	}

}
