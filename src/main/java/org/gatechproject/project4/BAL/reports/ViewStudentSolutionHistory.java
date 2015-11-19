package org.gatechproject.project4.BAL.reports;

import java.util.Date;

/**
 * This class will provide all of the data to the Student Solution History
 * screen
 * 
 * @author Luc
 *
 */

public class ViewStudentSolutionHistory {

	private Student student = null;
	private Date currentSolutionDate = null;

	// ===========================================================================
	
	public ViewStudentSolutionHistory() {

	}

	public ViewStudentSolutionHistory(Student student) {
		this.student = student;
	}
	
	public StudentSolution getSolution(Date date) {
		// TODO - Luc - select the closest solution to the given date
		// Does the solution come from the Student or the system?
		return null;
	}

	public Date getCurrentSolutionDate() {
		return currentSolutionDate;
	}

	public Student getStudent() {
		return student;
	}

}
