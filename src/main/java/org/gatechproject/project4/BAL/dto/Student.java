package org.gatechproject.project4.BAL.dto;

import org.gatechprojects.project4.SharedDataModules.User;

public class Student extends Person {
	public Student(User user) {
		super(user);
	}
	public String getStudentStanding(){
		return "Good";
	}
}
