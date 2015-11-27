package org.gatechprojects.project4.optimizer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.gatechprojects.project4.DAL.Blackboard;
import org.gatechprojects.project4.SharedDataModules.OptimizerCalculation;
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
public class SemesterOptimizer implements Participant {

	private ExecutorService optimizerQueue = Executors.newSingleThreadExecutor();

	private OptimizerCalculation computeResults(OptimizerCalculation optimizerCalculation) {
		/*
		 * Mark - the optimizerCalculation should be populated with input data
		 * here This is where you would run your optimizer(could be in another
		 * class). I'm expecting the outputs of the passed in
		 * optimizerCalculation object to be populated. I know that we still
		 * need to better define the outputs.
		 */

		return optimizerCalculation;
	}

	private OptimizerCalculation createOptimizerInput(boolean isShadow) {
		Blackboard blackboard = new Blackboard();
		OptimizerCalculation calculation = new OptimizerCalculation();
		calculation.setShadow(isShadow);
		return calculation;
	}

	@Override
	public void notifyBlackboardChanged(boolean isShadow) {
		OptimizerCalculation calculation = createOptimizerInput(isShadow);

		Runnable task = () -> runOptimizer(calculation);
		optimizerQueue.submit(task);
	}

	private void runOptimizer(OptimizerCalculation calculation) {
		Blackboard blackboard = new Blackboard();
		computeResults(calculation);
		blackboard.getOptimizerBoard().createOptimizerCalculation(calculation);
	}

}
