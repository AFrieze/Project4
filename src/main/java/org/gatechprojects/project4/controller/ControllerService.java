package org.gatechprojects.project4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.gatechprojects.project4.DAL.Blackboard;
import org.gatechprojects.project4.optimizer.SemesterOptimizer;

public class ControllerService {
	private static int CONTROLLER_INTERVAL = 5000;
	private static TimeUnit CONTROLLER_INTERVAL_TIMEUNIT = TimeUnit.SECONDS;

	public static void main(String[] args) throws IOException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		ControllerService service = new ControllerService(Integer.parseInt(args[0]));
		service.start();
		Runnable serviceProcess = () -> {
			service.start();
		};
		executor.submit(serviceProcess);
		// exit on input to console
		System.out.println("Entry any key to shutdown the controller");
		System.in.read();
		try {
			executor.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.exit(-1);
		}
		System.exit(0);
	}

	private final int semesterId;

	private List<Participant> participants = new ArrayList<Participant>();

	// static final Logger LOG =
	// LogManager.getLogger(ControllerService.class.getName());
	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	private BlackboardState shadowState = null;

	private BlackboardState regularState = null;

	public ControllerService(int semesterId) {
		this.semesterId = semesterId;
	}

	public void checkAndAssignWork() {
		boolean shadowChanged = hasBlackBoardChanged(true);
		boolean regularChanged = hasBlackBoardChanged(false);
		if (shadowChanged || regularChanged) {
			for (Participant participant : participants) {
				if (shadowChanged) {
					participant.notifyBlackboardChanged(true);
				}
				if (regularChanged) {
					participant.notifyBlackboardChanged(false);
				}
			}
		}

	}

	private boolean hasBlackBoardChanged(boolean isShadow) {
		Blackboard blackboard = new Blackboard();
		BlackboardState tempState = BlackboardState.newInstance(blackboard, semesterId, isShadow);
		if (!regularState.equals(tempState)) {
			regularState = tempState;
			return true;
		}
		return false;
	}

	private void initializeBlackboardStates() {
		Blackboard blackboard = new Blackboard();
		regularState = BlackboardState.newInstance(blackboard, semesterId, false);
		shadowState = BlackboardState.newInstance(blackboard, semesterId, true);

	}

	private void registerParticipants() {
		participants.add(new SemesterOptimizer(semesterId));
	}

	public void start() {
		initializeBlackboardStates();
		registerParticipants();
		Runnable task = () -> checkAndAssignWork();
		executor.schedule(task, CONTROLLER_INTERVAL, CONTROLLER_INTERVAL_TIMEUNIT);
	}

}
