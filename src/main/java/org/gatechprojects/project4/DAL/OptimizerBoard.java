package org.gatechprojects.project4.DAL;

import java.util.Calendar;
import java.util.List;

import org.gatechprojects.project4.SharedDataModules.InputStudentCoursePreference;
import org.gatechprojects.project4.SharedDataModules.OptimizerCalculation;
import org.gatechprojects.project4.SharedDataModules.OutputUserCourseAssignment;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class OptimizerBoard extends Board {

	public OptimizerBoard(Transaction transaction, Session session) {
		super(transaction, session);
	}

	public Integer createOptimizerCalculation(OptimizerCalculation optimizerCalculation) {
		verifyTransaction();
		return (Integer) getSession().save(optimizerCalculation);

	}

	public int getCourseDemand(int courseId, int optimizerCalculationId) {
		Query query = getSession().createQuery(
				"select count(*) from InputStudentCoursePreference where course.id = :courseId and inputStudent.optimizerCalculation.id = :optimizerCalculationId");
		query.setInteger("optimizerCalculationId", optimizerCalculationId);
		query.setInteger("courseId", courseId);
		Integer count = (Integer) query.uniqueResult();
		return count;
	}

	public Integer getOptimizerCalculationId(Calendar calendar, boolean isShadow) {
		String hql = "from OptimizerCalculation as c where c.completionTime <= :completionTime and c.isShadow = :isShadow order by c.completionTime desc";
		List calculations = getSession().createQuery(hql).setParameter("completionTime", calendar)
				.setParameter("isShadow", isShadow).list();
		if (calculations.size() > 1) {
			OptimizerCalculation optCal = (OptimizerCalculation) calculations.get(0);
			return optCal.getId();
		}
		return -1;
	}

	public List<InputStudentCoursePreference> getStudentPreferencesForCalculation(int studentID,
			int optimizerCalculationID) {
		return getSession().createCriteria(InputStudentCoursePreference.class)
				.add(Restrictions.eq("inputStudent.user.id", studentID))
				.add(Restrictions.eq("inputStudent.optimizerCalculation.id", optimizerCalculationID)).list();
	}

	public List<OutputUserCourseAssignment> getUserCourseAssignments(int optimizerCalculationId, boolean isShadow) {
		return getSession().createCriteria(OutputUserCourseAssignment.class)
				.add(Restrictions.eq("optimizerCalculation.id", optimizerCalculationId))
				.add(Restrictions.eq("optimizerCalculation.isShadow", isShadow)).list();
	}
	// public InputStudent getStudentInput(int optimizerCalculationId, int
	// userId) {
	// return null;
	// }

}
