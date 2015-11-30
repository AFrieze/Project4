package org.gatechprojects.project4.optimizer;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.gatechproject.project4.BAL.dto.ConfiguredCourse;
import org.gatechproject.project4.BAL.dto.Professor;
import org.gatechproject.project4.BAL.dto.SemesterConfiguration;
import org.gatechproject.project4.BAL.dto.StudentSemesterPreferences;
import org.gatechproject.project4.BAL.dto.TeacherAssistant;
import org.gatechprojects.project4.BAL.SemesterSetupService;
import org.gatechprojects.project4.BAL.UserService;
import org.gatechprojects.project4.DAL.Blackboard;
import org.gatechprojects.project4.SharedDataModules.Course;
import org.gatechprojects.project4.SharedDataModules.CourseTaken;
import org.gatechprojects.project4.SharedDataModules.InputCourseCompetence;
import org.gatechprojects.project4.SharedDataModules.InputOfferedCourse;
import org.gatechprojects.project4.SharedDataModules.InputProfessor;
import org.gatechprojects.project4.SharedDataModules.InputStudent;
import org.gatechprojects.project4.SharedDataModules.InputStudentCoursePreference;
import org.gatechprojects.project4.SharedDataModules.InputTA;
import org.gatechprojects.project4.SharedDataModules.OptimizerCalculation;
import org.gatechprojects.project4.SharedDataModules.User;
import org.gatechprojects.project4.controller.Participant;

/**
 * 
 * The semester optimizer is a concrete implementation of the
 * {@link Participant} interface. As such, the semester optimizer expects to
 * have it's {@link #notifyBlackboardChanged(boolean)} method called whenever
 * data on the blackboard changes. The optimizer is responsible for creating a
 * {@link OptimizerCalculation} object and persisting it to the blackboard. This
 * implementation manages a queue of change notifications and executes
 * calculations in the order the are received, one at a time.
 * 
 * @author Andrew
 *
 */
public class OptimizerParticipant implements Participant {

	private ExecutorService optimizerQueue = Executors.newSingleThreadExecutor();
	private final int semesterId;

	public OptimizerParticipant(int semesterId) {
		this.semesterId = semesterId;
	}

	private OptimizerCalculation computeResults(OptimizerCalculation optimizerCalculation) {
		/*
		 * Mark - the optimizerCalculation should be populated with input data
		 * here.
		 * 
		 * This is where you would run your optimizer(could call out to another
		 * class).
		 * 
		 * I'm expecting the outputs of the passed in optimizerCalculation
		 * object to be populated by you. I may not have captured everything
		 * required for output
		 * 
		 */

		return optimizerCalculation;
	}

	private OptimizerCalculation createOptimizerInput(boolean isShadow) {
		OptimizerCalculation calculation = new OptimizerCalculation();
		calculation.setCreateTime(Calendar.getInstance());
		calculation.setShadow(isShadow);
		Blackboard blackboard = new Blackboard();
		blackboard.load();
		SemesterSetupService setupService = new SemesterSetupService(blackboard);
		SemesterConfiguration configuration = setupService.getSemesterConfiguration(semesterId, isShadow);
		populateInputOfferedCourses(calculation, configuration, blackboard);
		populateInputProfessors(calculation, configuration, blackboard);
		populateInputTAs(calculation, configuration, blackboard);
		populateInputStudents(calculation, blackboard);
		blackboard.close();
		return calculation;
	}

	@Override
	public void notifyBlackboardChanged(boolean isShadow) {
		OptimizerCalculation calculation = createOptimizerInput(isShadow);
		Runnable task = () -> runOptimizer(calculation);
		optimizerQueue.submit(task);
	}

	private OptimizerCalculation populateInputOfferedCourses(OptimizerCalculation calculation,
			SemesterConfiguration configuration, Blackboard blackboard) {
		for (ConfiguredCourse cc : configuration.getOfferedCourses()) {
			InputOfferedCourse inputCourse = new InputOfferedCourse();
			inputCourse.setOptimizerCalculation(calculation);
			inputCourse.setCourse(blackboard.getByID(Course.class, cc.getCourseId()));
			inputCourse.setMaxCourseSize(cc.getMaxCourseSize());
			if (cc.getAssignedProfessorId() != null) {
				inputCourse.setAssignedProfessor(blackboard.getByID(User.class, cc.getAssignedProfessorId()));
			}
			calculation.getInputOfferedCourses().add(inputCourse);
		}
		return calculation;
	}

	private OptimizerCalculation populateInputProfessors(OptimizerCalculation calculation,
			SemesterConfiguration configuration, Blackboard blackboard) {
		for (Professor professor : configuration.getProfessors()) {
			InputProfessor inputProfessor = new InputProfessor();
			inputProfessor.setOptimizerCalculation(calculation);
			inputProfessor.setUser(blackboard.getByID(User.class, professor.getUserId()));
			for (Course course : professor.getCourseCompetencies()) {
				InputCourseCompetence competence = new InputCourseCompetence();
				competence.setCourse(course);
				competence.setInputProfessor(inputProfessor);
				inputProfessor.getCourseCompetencies().add(competence);
			}
			calculation.getInputProfessors().add(inputProfessor);
		}
		return calculation;
	}

	private OptimizerCalculation populateInputStudents(OptimizerCalculation calculation, Blackboard blackboard) {
		UserService userService = new UserService(blackboard);
		List<StudentSemesterPreferences> preferences = userService.getStudentPreferences(semesterId);
		for (StudentSemesterPreferences pref : preferences) {
			InputStudent inputStudent = new InputStudent();
			inputStudent.setOptimizerCalculation(calculation);
			inputStudent.setUser(blackboard.getByID(User.class, pref.getUserId()));
			inputStudent.setNbrCoursesDesired(pref.getNbrCoursesDesired());
			// determine courses already taken
			User user = blackboard.getByID(User.class, pref.getUserId());
			int nbrCompletedCredits = 0;
			for (CourseTaken courseTaken : user.getCoursesTaken()) {
				nbrCompletedCredits += courseTaken.getCourse().getCredits();
			}
			inputStudent.setCreditsTaken(nbrCompletedCredits);
			int coursePriority = 1;
			for (ConfiguredCourse course : pref.getPreferredCourses()) {
				InputStudentCoursePreference inputCourse = new InputStudentCoursePreference();
				inputCourse.setCourse(blackboard.getByID(Course.class, course.getCourseId()));
				inputCourse.setCoursePriority(coursePriority);
				inputCourse.setInputStudent(inputStudent);
				coursePriority++;
				inputStudent.getCoursePreferences().add(inputCourse);
			}
		}

		return calculation;
	}

	private OptimizerCalculation populateInputTAs(OptimizerCalculation calculation, SemesterConfiguration configuration,
			Blackboard blackboard) {
		for (TeacherAssistant ta : configuration.getTeacherAssistants()) {
			InputTA inputTA = new InputTA();
			inputTA.setUser(blackboard.getByID(User.class, ta.getUserId()));
			inputTA.setOptimizerCalculation(calculation);
			calculation.getInputTAs().add(inputTA);
		}
		return calculation;
	}

	private void runOptimizer(OptimizerCalculation calculation) {
		try (Blackboard blackboard = new Blackboard()) {
			blackboard.load();
			computeResults(calculation);
			blackboard.getOptimizerBoard().createOptimizerCalculation(calculation);
		}
	}

}
