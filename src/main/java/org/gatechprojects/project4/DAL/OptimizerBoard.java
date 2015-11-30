package org.gatechprojects.project4.DAL;

import java.util.Calendar;
import java.util.List;

import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.InputStudentCoursePreference;
import org.gatechprojects.project4.SharedDataModules.OptimizerCalculation;
import org.gatechprojects.project4.SharedDataModules.OutputOfferedCourse;
import org.gatechprojects.project4.SharedDataModules.OutputUserCourseAssignment;
import org.gatechprojects.project4.SharedDataModules.StudentPreference;
import org.gatechprojects.project4.SharedDataModules.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
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

	public List<OutputOfferedCourse> getOfferedCourses(int optimizerCalculationId) {
		return getSession().createCriteria(OutputOfferedCourse.class)
				.add(Restrictions.eq("optimizerCalculation.id", optimizerCalculationId)).list();
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
	
	public OptimizerCalculation getOptimizerCalculation(int optimizerCalculationId) {
		return getSession().get(OptimizerCalculation.class, optimizerCalculationId);
	}
	
	public List<OptimizerCalculation> getLastOptimizerCalculations(int limit) {
		List<OptimizerCalculation> optimizerSet =  getSession().createCriteria(OptimizerCalculation.class)
				.addOrder(Order.desc("id")).setFirstResult(0)
				.setMaxResults(limit).list();

			return optimizerSet;

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
	
	/**
	 * Used for testing the method getOptimizerCourseRecommendations() 
	 */
	public void createOutputOfferedCourse(Course course, OptimizerCalculation optimizerCalculation) {
		OutputOfferedCourse offeredCourse = new OutputOfferedCourse();

		offeredCourse.setCourse(course);
		offeredCourse.setCourseSize(50);
		offeredCourse.setOptimizerCalculation(optimizerCalculation);

		getSession().save(offeredCourse);
	}

}
