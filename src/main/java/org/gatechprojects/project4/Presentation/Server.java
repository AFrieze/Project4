package org.gatechprojects.project4.Presentation;

import static spark.Spark.*;

import org.gatechprojects.project4.Presentation.controllers.*;
import org.gatechprojects.project4.Presentation.controllers.students.HomeController;
import org.gatechprojects.project4.Presentation.controllers.students.LoginHistoryController;
import org.gatechprojects.project4.Presentation.controllers.students.NextSemesterCoursesController;
import org.gatechprojects.project4.Presentation.controllers.students.SolutionHistoryController;

import spark.template.velocity.VelocityTemplateEngine;

public class Server {

	private static String SESSION_USER = "user";

	public static void main(String[] args) {

		/*
		 * Any requests from a non-logged in user should be redirected to the
		 * login page
		 */
		staticFileLocation("/public");
		
		before((request, response) -> {
			
			// Only check authentication on non-static files
			if( !request.pathInfo().startsWith("/js")
				&& !request.pathInfo().startsWith("/styles")
				&& !request.pathInfo().startsWith("/images")
				&& !request.pathInfo().startsWith("/favicon.ico")
				)
			{
				boolean isLoggedIn = request.session().attribute(SESSION_USER) != null;
				// ... check if authenticated
				if (!isLoggedIn && !request.pathInfo().equals("/login")){
					response.redirect("/login");
				}
			}
		});

		get("/login", (req, res) -> {
			LoginController controller = new LoginController();
			return controller.getLoginPage(req);
		} , new VelocityTemplateEngine());

		post("/login", (req, res) -> {
			LoginController controller = new LoginController();
			return controller.postLoginPage(req, res);
		} , new VelocityTemplateEngine());
		
		get("/logout", (req, res) -> {
			req.session().attribute(SESSION_USER, null);
			LoginController controller = new LoginController();
			return controller.getLoginPage(req);
		} , new VelocityTemplateEngine());

		post("/logout", (req, res) -> {
			req.session().attribute(SESSION_USER, null);
			LoginController controller = new LoginController();
			return controller.postLoginPage(req, res);
		} , new VelocityTemplateEngine());
		
		
		
		get("/student/home", (req, res) -> {
			HomeController controller = new HomeController();
			return controller.getHomePage(req);
		} , new VelocityTemplateEngine());

		post("/student/home", (req, res) -> {
			HomeController controller = new HomeController();
			return controller.getHomePage(req);
		} , new VelocityTemplateEngine());
		
		get("/student/nextsemestercourses", (req, res) -> {
			NextSemesterCoursesController controller = new NextSemesterCoursesController();
			return controller.getNextSemesterCoursesPage(req);
		} , new VelocityTemplateEngine());

		post("/student/nextsemestercourses", (req, res) -> {
			NextSemesterCoursesController controller = new NextSemesterCoursesController();
			return controller.postNextSemesterCoursesPage(req, res);
		} , new VelocityTemplateEngine());
		
		
		get("/student/solutionhistory", (req, res) -> {
			SolutionHistoryController controller = new SolutionHistoryController();
			return controller.getHistory(req);
		} , new VelocityTemplateEngine());

		post("/student/solutionhistory", (req, res) -> {
			SolutionHistoryController controller = new SolutionHistoryController();
			return controller.getHistory(req);
		} , new VelocityTemplateEngine());
		
		
		get("/student/loginhistory", (req, res) -> {
			LoginHistoryController controller = new LoginHistoryController();
			return controller.getHistory(req);
		} , new VelocityTemplateEngine());

		post("/student/loginhistory", (req, res) -> {
			LoginHistoryController controller = new LoginHistoryController();
			return controller.getHistory(req);
		} , new VelocityTemplateEngine());
		
		get("/admin/home", (req, res) -> {
			AdminHomeController controller = new AdminHomeController();
			return controller.getHomePage(req);
		} , new VelocityTemplateEngine());

		post("/admin/home", (req, res) -> {
			AdminHomeController controller = new AdminHomeController();
			return controller.getHomePage(req);
		} , new VelocityTemplateEngine());
		
		
		
	}
}
