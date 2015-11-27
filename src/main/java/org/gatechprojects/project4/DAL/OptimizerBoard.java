package org.gatechprojects.project4.DAL;

import java.util.Calendar;
import java.util.List;

import org.gatechprojects.project4.SharedDataModules.OptimizerCalculation;
import org.gatechprojects.project4.SharedDataModules.OutputUserCourseAssignment;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class OptimizerBoard extends Board {

	public OptimizerBoard(Transaction transaction, Session session) {
		super(transaction, session);
	}

	public Integer createOptimizerCalculation(OptimizerCalculation optimizerCalculation) {
		verifyTransaction();
		return (Integer) getSession().save(optimizerCalculation);

	}

	// public InputStudent getStudentInput(int optimizerCalculationId, int
	// userId) {
	// return null;
	// }

	public Integer getOptimizerCalculationId(Calendar calendar) {
		return null;
	}

	public List<OutputUserCourseAssignment> getUserCourseAssignments(int optimizerCalculationId) {
		return null;
	}

}
