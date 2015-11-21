package org.gatechproject.project4.BAL.dto;

/**
 * This class will be used by the "Student Home Page" screen/view
 */

public class ViewStudent {

	private Student student = null;
	
	public ViewStudent() {
	}
	
	public ViewStudent(Student student) {
		this.student = student;
		// TODO - Luc - We are missing the "system" class that has the system status
	}

	public Student getStudent() {
		return student;
	}
	
	public String getSystemStatus() {
		return "Good";
	}

}
