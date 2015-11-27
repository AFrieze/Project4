package org.gatechprojects.project4.controller;

/**
 * Interface defining the method(s) required for to register for changes to the
 * blackboard.
 * 
 * @author Andrew
 *
 */
public interface Participant {

	/**
	 * Method is called whenever a potentially relevant change to the blackboard
	 * is detected.
	 * 
	 * @param isShadow
	 *            - whether or not shadow data changed.
	 */
	void notifyBlackboardChanged(boolean isShadow);
}
