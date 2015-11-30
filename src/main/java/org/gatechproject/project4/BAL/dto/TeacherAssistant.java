package org.gatechproject.project4.BAL.dto;

import org.gatechprojects.project4.SharedDataModules.User;

public class TeacherAssistant extends Person {


	private boolean assigned = false;
	
	public TeacherAssistant() {
	}

	public TeacherAssistant(User user) {
		super(user);
	}

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean isAssigned) {
		this.assigned = isAssigned;
	}

	
}
