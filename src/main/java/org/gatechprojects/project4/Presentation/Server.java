package org.gatechprojects.project4.Presentation;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.post;

import org.gatechprojects.project4.Presentation.controllers.LoginController;

import spark.template.velocity.VelocityTemplateEngine;

public class Server {

	private static String SESSION_USER = "user";

	public static void main(String[] args) {

		/*
		 * Any requests from a non-logged in user should be redirected to the
		 * login page
		 */
		before((request, response) -> {
			boolean isLoggedIn = request.session().attribute(SESSION_USER) != null;
			// ... check if authenticated
			if (!isLoggedIn && !request.pathInfo().equals("/login")) {
				response.redirect("/login");
			}
		});

		get("/login", (req, res) -> {
			LoginController controller = new LoginController();
			return controller.getLoginPage(req);
		} , new VelocityTemplateEngine());

		post("/login", (req, res) -> {
			LoginController controller = new LoginController();
			return controller.postLoginPage(req);
		} , new VelocityTemplateEngine());
	}
}
