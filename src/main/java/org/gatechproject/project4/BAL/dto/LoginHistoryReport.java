package org.gatechproject.project4.BAL.dto;

/**
 * This class will be used by the screen "Student login history"
 * 
 * @author Luc
 *
 */

public class LoginHistoryReport {

	private Student student = null;

	// ===========================================================================
	
	public LoginHistoryReport() {
	}
	
	public LoginHistoryReport(Student student) {
		this.student = student;
	}

	public Student getStudent() {
		return student;
	}
}
