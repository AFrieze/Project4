package org.gatechprojects.project4.Presentation.controllers.admin;

import java.util.HashMap;

import org.gatechproject.project4.BAL.dto.SemesterConfiguration;
import org.gatechprojects.project4.BAL.SemesterSetupService;
import org.gatechprojects.project4.BAL.StaffService;
import org.gatechprojects.project4.BAL.UserService;

import spark.ModelAndView;
import spark.Request;

public class HistoryController {

    private	SemesterSetupService semesterSetupService = new SemesterSetupService();

    private	StaffService staffSetupService = new StaffService();

    
	public ModelAndView getHistoryCourseDemandPage(Request request) {
			
		HashMap<String, Object> model = new HashMap<String, Object>();
		model.put("template", "templates/admin/HistoryCourseDemand.vtl");
		
		return new ModelAndView(model, "templates/admin/HistoryCourseDemand.vtl");
	}

	public ModelAndView postHistoryCourseDemandPage(Request request) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		model.put("template", "templates/admin/HistoryCourseDemand.vtl");
		
		return new ModelAndView(model, "templates/admin/HistoryCourseDemand.vtl");
	}


    
	public ModelAndView getHistoryStudentHistoryPage(Request request) {
			
		HashMap<String, Object> model = new HashMap<String, Object>();
		model.put("template", "templates/admin/HistoryStudentHistory.vtl");
		
		return new ModelAndView(model, "templates/admin/HistoryStudentHistory.vtl");
	}

	public ModelAndView postHistoryStudentHistoryPage(Request request) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		model.put("template", "templates/admin/HistoryStudentHistory.vtl");
		
		return new ModelAndView(model, "templates/admin/HistoryStudentHistory.vtl");
	}

	
}
