package org.gatechprojects.project4.Presentation.controllers.admin;

import java.util.ArrayList;
import java.util.Date;
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
import org.gatechprojects.project4.Presentation.controllers.Controller;
import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.User;

import spark.ModelAndView;
import spark.Request;

public class SemesterController extends Controller {

	private static String PAGE_TITLE = "Semester Configuration";
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

		getCommonDetails(model, request);
		
		return new ModelAndView(model, "templates/admin/SemesterConfiguration.vtl");
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
		getCommonDetails(model, request);
		
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
		List<ConfiguredCourse> courses = new ArrayList<ConfiguredCourse>();
		
		for (int i = 0; i < selectedCourses.length; i++) {
		    int courseId = Integer.parseInt(selectedCourses[i]);
		    ConfiguredCourse configuredCourse = new ConfiguredCourse(courseService.getCourse(courseId),true);
		    courses.add(configuredCourse);
		}
		
		semesterConfiguration.setOfferedCourses(courses);
	}


	private void getCommonDetails(HashMap<String, Object> model, Request request)
	{
		model.put("template", "templates/admin/SemesterConfiguration.vtl");
		model.put("pageTitle", PAGE_TITLE);
		model.put("includeHeader", true);
		model.put("user", ((User)request.session().attribute(SESSION_USER)));
		Date date = new Date();
		model.put("currentdate", DATE_FORMAT.format(date));
		model.put("dateFormat", DATE_FORMAT);
		model.put("request", request);
	}

	private void getAllMasters(HashMap<String, Object> model) {
		model.put("Semesters", semesterSetupService.getAvailableSemesters());
		model.put("SelectedSemester", "");
		model.put("TeacherAssistants", staffSetupService.getAvailableTeacherAssistants());
		model.put("Professors", staffSetupService.getAvailableProfessors());
		model.put("Courses", semesterSetupService.getAvailableCourses());
	}


	private void getSemesterConfiguration(HashMap<String, Object> model, String semesterId) {
		SemesterConfiguration semesterConfiguration = semesterSetupService.getSemesterConfiguration(Integer.parseInt(semesterId),true);
		
		List<TeacherAssistant> allTAs = staffSetupService.getAvailableTeacherAssistants();
		List<TeacherAssistant> assignedTAs = semesterConfiguration.getTeacherAssistants();
		for (TeacherAssistant ta : allTAs) {
		    if(assignedTAs.contains(ta))
		    {
		    	ta.setAssigned(true);
		    }
		}

		List<Professor> allProfessors = staffSetupService.getAvailableProfessors();
		List<Professor> assignedProfessors = semesterConfiguration.getProfessors();
		for (Professor prof : allProfessors) {
		    if(assignedProfessors.contains(prof))
		    {
		    	prof.setAssigned(true);
		    }
		}
		
		List<Course> allCourses = semesterSetupService.getAvailableCourses();
		List<ConfiguredCourse> assignedCourses = semesterConfiguration.getOfferedCourses();
		for (Course course : allCourses) {
		    if(!assignedCourses.contains(course))
		    {
		    	assignedCourses.add(new ConfiguredCourse(course,false));
		    }
		}
		model.put("TeacherAssistants", allTAs);
		model.put("Professors", allProfessors);
		model.put("Courses", assignedCourses);
		model.put("Semesters", semesterSetupService.getAvailableSemesters());
		
		model.put("SelectedSemester", semesterId);
	}

}
