package org.gatechproject.project4.BAL.reports;

/**
 * This class will be used by the screen "Student login history"
 * 
 * @author Luc
 *
 */

public class ViewLoginHistory {

	private Student student = null;

	// ===========================================================================
	
	public ViewLoginHistory() {
	}
	
	public ViewLoginHistory(Student student) {
		this.student = student;
	}

	public Student getStudent() {
		return student;
	}
}
