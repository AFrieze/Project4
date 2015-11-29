package org.gatechprojects.project4.Presentation.controllers.admin;

import java.util.HashMap;

import org.gatechproject.project4.BAL.dto.SemesterConfiguration;
import org.gatechprojects.project4.BAL.SemesterSetupService;
import org.gatechprojects.project4.BAL.StaffService;
import org.gatechprojects.project4.BAL.UserService;

import spark.ModelAndView;
import spark.Request;

public class SemesterController {

    private	SemesterSetupService semesterSetupService = new SemesterSetupService();

    private	StaffService staffSetupService = new StaffService();

    
	public ModelAndView getSemesterConfigurationPage(Request request) {
			
		HashMap<String, Object> model = new HashMap<String, Object>();
		String semesterId = (String)request.queryParams("Semester");
		if (semesterId != null && !semesterId.isEmpty()) 
		{	
			getSemesterConfiguration(model, semesterId);
		}
		else
		{	
			model.put("Semesters", semesterSetupService.getAvailableSemesters());
			model.put("TeacherAssistants", staffSetupService.getAvailableTeacherAssistants());
			model.put("Professors", staffSetupService.getAvailableProfessors());
			model.put("Courses", semesterSetupService.getAvailableCourses());
			//model.put("Competencies", staffSetupService.getAvailableProfessorCompetencies(1));
		}
		model.put("template", "templates/admin/SemesterConfiguration.vtl");
		
		return new ModelAndView(model, "templates/admin/SemesterConfiguration.vtl");
	}

	public ModelAndView postSemesterConfigurationPage(Request request) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String semesterId = (String)request.queryMap().get("Semester").value();
		
		if (semesterId != null && !semesterId.isEmpty()) 
		{
			SemesterConfiguration semesterConfiguration = new SemesterConfiguration();
			semesterConfiguration.setSemesterId(Integer.parseInt(semesterId));
			
			semesterSetupService.applySemesterConfiguration(semesterConfiguration, true);
		}
		model.put("template", "templates/admin/SemesterConfiguration.vtl");
		
		return new ModelAndView(model, "templates/admin/SemesterConfiguration.vtl");
	}


	private void getSemesterConfiguration(HashMap<String, Object> model, String semesterId) {
		SemesterConfiguration semesterConfiguration = semesterSetupService.getSemesterConfiguration(Integer.parseInt(semesterId),true);
		model.put("TeacherAssistants", semesterConfiguration.getTeacherAssistants());
		model.put("Professors", semesterConfiguration.getProfessors());
		model.put("Courses", semesterConfiguration.getOfferedCourses());
		model.put("Semesters", semesterSetupService.getAvailableSemesters());
		//model.put("Competencies", staffSetupService.getAvailableProfessorCompetencies(1));
		model.put("SelectedSemester", semesterSetupService.getSemester(Integer.parseInt(semesterId)));
	}
	
}
