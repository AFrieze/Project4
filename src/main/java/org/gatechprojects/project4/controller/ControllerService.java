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
	private static int CONTROLLER_INTERVAL = 5;
	private static TimeUnit CONTROLLER_INTERVAL_TIMEUNIT = TimeUnit.SECONDS;

	public static void main(String[] args) throws IOException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		ControllerService service = new ControllerService(Integer.parseInt(args[0]));
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

		Blackboard blackboard = new Blackboard();
		blackboard.load();
		boolean shadowChanged = hasBlackBoardChanged(true, blackboard);
		boolean regularChanged = hasBlackBoardChanged(false, blackboard);
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
		blackboard.close();

	}

	private boolean hasBlackBoardChanged(boolean isShadow, Blackboard blackboard) {
		// blackboard.clearSession();
		BlackboardState tempState = BlackboardState.newInstance(blackboard, semesterId, isShadow);
		if (isShadow && !shadowState.equals(tempState)) {
			shadowState = tempState;
			return true;
		} else if (!isShadow && !regularState.equals(tempState)) {
			regularState = tempState;
			return true;
		}
		return false;
	}

	private void initializeBlackboardStates(Blackboard blackboard) {
		regularState = BlackboardState.newInstance(blackboard, semesterId, false);
		shadowState = BlackboardState.newInstance(blackboard, semesterId, true);
	}

	private void registerParticipants() {
		participants.add(new SemesterOptimizer(semesterId));
	}

	public void start() {
		Blackboard blackboard = new Blackboard();
		blackboard.load();
		initializeBlackboardStates(blackboard);
		registerParticipants();
		Runnable task = () -> checkAndAssignWork();
		executor.scheduleAtFixedRate(task, 0, CONTROLLER_INTERVAL, CONTROLLER_INTERVAL_TIMEUNIT);
	}

}
