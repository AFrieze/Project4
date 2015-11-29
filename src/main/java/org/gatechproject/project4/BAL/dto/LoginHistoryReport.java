package org.gatechproject.project4.BAL.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

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
	
	public ArrayList<Date> getLoginHistory(){
		if(this.student == null) {
			return null;
		}
		//return this.student.getLoginDates()
		
		/* 
		 * Fake data for testing
		 * */
		
		ArrayList<Date> DateArray = new ArrayList<Date>();
		
		long lonin1 = Timestamp.valueOf("2015-11-28 00:00:00").getTime();
	    long login2 = Timestamp.valueOf("2015-11-28 00:58:00").getTime();
		
		
		DateArray.add(new Date());
		DateArray.add(new Date(login2));
		DateArray.add(new Date(lonin1));
		
		return DateArray;
		
		
	}
}
