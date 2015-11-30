package org.gatechprojects.project4.Presentation.controllers.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.gatechproject.project4.BAL.dto.ConfiguredCourse;
import org.gatechproject.project4.BAL.dto.Professor;
import org.gatechproject.project4.BAL.dto.SemesterConfiguration;
import org.gatechproject.project4.BAL.dto.TeacherAssistant;
import org.gatechprojects.project4.BAL.SemesterSetupService;
import org.gatechprojects.project4.BAL.StaffService;
import org.gatechprojects.project4.BAL.UserService;

import spark.ModelAndView;
import spark.Request;

public class SemesterController {

    private	SemesterSetupService semesterSetupService = new SemesterSetupService();

    private	StaffService staffSetupService = new StaffService();
    private UserService userService = new UserService();

    
	public ModelAndView getSemesterConfigurationPage(Request request) {
			
		HashMap<String, Object> model = new HashMap<String, Object>();
		String semesterId = (String)request.queryParams("Semester");
		if (semesterId != null && !semesterId.isEmpty()) 
		{	
			getSemesterConfiguration(model, semesterId);
		}
		else
		{	
			getAllMasters(model);
		}
		model.put("template", "templates/admin/SemesterConfiguration.vtl");
		
		return new ModelAndView(model, "templates/admin/SemesterConfiguration.vtl");
	}


	private void getAllMasters(HashMap<String, Object> model) {
		model.put("Semesters", semesterSetupService.getAvailableSemesters());
		model.put("SelectedSemester", "");
		model.put("TeacherAssistants", staffSetupService.getAvailableTeacherAssistants());
		model.put("Professors", staffSetupService.getAvailableProfessors());
		model.put("Courses", semesterSetupService.getAvailableCourses());
		//model.put("Competencies", staffSetupService.getAvailableProfessorCompetencies(1));
	}


	private void getSemesterConfiguration(HashMap<String, Object> model, String semesterId) {
		SemesterConfiguration semesterConfiguration = semesterSetupService.getSemesterConfiguration(Integer.parseInt(semesterId),true);
		model.put("TeacherAssistants", semesterConfiguration.getTeacherAssistants());
		model.put("Professors", semesterConfiguration.getProfessors());
		model.put("Courses", semesterConfiguration.getOfferedCourses());
		model.put("Semesters", semesterSetupService.getAvailableSemesters());
		//model.put("Competencies", staffSetupService.getAvailableProfessorCompetencies(1));
		//model.put("SelectedSemester", semesterSetupService.getSemester(Integer.parseInt(semesterId)));
		model.put("SelectedSemester", semesterId);
	}

	public ModelAndView postSemesterConfigurationPage(Request request) {
		HashMap<String, Object> model = new HashMap<String, Object>();
		String semesterId = (String)request.queryMap().get("Semester").value();
		String button = (String)request.queryMap().get("button").value();

		if (semesterId != null && !semesterId.isEmpty() && button == null) 
		{
			getSemesterConfiguration(model, semesterId);
		}
		else if (button.equals("Apply"))
		{
			createSemsterConfiguration(model, request, semesterId);
			getAllMasters(model);
		}
		model.put("template", "templates/admin/SemesterConfiguration.vtl");
		
		return new ModelAndView(model, "templates/admin/SemesterConfiguration.vtl");
	}
	
	private void createSemsterConfiguration(HashMap<String, Object> model, Request request, String semesterId)
	{

		LinkedHashMap<String, String> message = new LinkedHashMap<String, String>();
		SemesterConfiguration semesterConfiguration = new SemesterConfiguration();
		semesterConfiguration.setSemesterId(Integer.parseInt(semesterId));
	
		String[] selectedTAs = (String[])request.queryMap().get("tas").values();
		String[] selectedProfessors = (String[])request.queryMap().get("professors").values();
		String[] selectedCourses = (String[])request.queryMap().get("courses").values();
		
		if(selectedTAs != null && selectedProfessors != null && selectedTAs != null)
		{
			addTAs(semesterConfiguration, selectedTAs);
			addProfessors(semesterConfiguration, selectedProfessors);
			addCourses(semesterConfiguration, selectedCourses);
			
			semesterSetupService.applySemesterConfiguration(semesterConfiguration, true);
		}
		else
		{
			// DO WE WANT TO NO ALLOW ZERO COURSE AS A PREFERENCE?
			message.put("type", "warning");
			message.put("summary", "Warning! ");
			message.put("message", " No course were selected.");	
		}

		model.put("includeMessage", true);
		model.put("message", message);
		
	}

	private void addTAs(SemesterConfiguration semesterConfiguration, String[] selectedTAs) {

		//String[] selectCoursesArrayStr = selectCourses.split(",");
		List<TeacherAssistant> tAs = new ArrayList<TeacherAssistant>();
		
		for (int i = 0; i < selectedTAs.length; i++) {
		    int taId = Integer.parseInt(selectedTAs[i]);
		    TeacherAssistant teacherAssistant = userService.getTAById(taId);
		    tAs.add(teacherAssistant);
		}
		
		semesterConfiguration.setTeacherAssistants(tAs);
	}

	private void addProfessors(SemesterConfiguration semesterConfiguration, String[] selectedProfessors) {

		//String[] selectCoursesArrayStr = selectCourses.split(",");
		List<Professor> professors = new ArrayList<Professor>();
		
		for (int i = 0; i < selectedProfessors.length; i++) {
		    int professorId = Integer.parseInt(selectedProfessors[i]);
		    Professor professor = userService.getProfessorById(professorId);
		    professors.add(professor);
		}
		
		semesterConfiguration.setProfessors(professors);
	}
	

	private void addCourses(SemesterConfiguration semesterConfiguration, String[] selectedCourses) {

		SemesterSetupService courseService = new SemesterSetupService();
		//String[] selectCoursesArrayStr = selectCourses.split(",");
		List<ConfiguredCourse> courses = new ArrayList<ConfiguredCourse>();
		
		for (int i = 0; i < selectedCourses.length; i++) {
		    int courseId = Integer.parseInt(selectedCourses[i]);
		    ConfiguredCourse configuredCourse = new ConfiguredCourse(courseService.getCourse(courseId));
		    courses.add(configuredCourse);
		}
		
		semesterConfiguration.setOfferedCourses(courses);
	}
}
