package org.gatechprojects.project4.controller;

import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.gatechprojects.project4.DAL.Blackboard;
import org.gatechprojects.project4.SharedDataModules.CourseSemester;
import org.gatechprojects.project4.SharedDataModules.StudentPreference;
import org.gatechprojects.project4.SharedDataModules.UserAvailability;

/**
 * @author Andrew
 *
 */
@Immutable
public class BlackboardState {
	public static BlackboardState newInstance(Blackboard blackboard, int semesterId, boolean isShadow) {

		StudentPreference latestPreference = blackboard.getUserBoard().getMostRecentStudentPreference(semesterId);
		CourseSemester latestCourseConfiguration = blackboard.getCatalogBoard().getMostRecentCourseSemester(semesterId,
				isShadow);
		UserAvailability latestUserAvailability = blackboard.getCatalogBoard().getMostRecentUserAvailability(semesterId,
				isShadow);
		return new BlackboardState(semesterId, latestUserAvailability == null ? -1 : latestUserAvailability.getId(),
				latestPreference == null ? -1 : latestPreference.getId(),
				latestCourseConfiguration == null ? -1 : latestCourseConfiguration.getId());
	}

	private final int userAvailability;
	private final int studentPreferenceState;
	private final int courseSemesterState;
	private final int semesterId;

	private BlackboardState(int semesterId, int userAvailability, int studentPreferenceState, int courseSemesterState) {
		this.semesterId = semesterId;
		this.userAvailability = userAvailability;
		this.studentPreferenceState = studentPreferenceState;
		this.courseSemesterState = courseSemesterState;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof BlackboardState)) {
			return false;
		}
		BlackboardState otherState = (BlackboardState) other;
		boolean isEquals = otherState.courseSemesterState == courseSemesterState;
		isEquals = isEquals ? otherState.userAvailability == userAvailability : isEquals;
		isEquals = isEquals ? otherState.studentPreferenceState == studentPreferenceState : isEquals;
		isEquals = isEquals ? otherState.semesterId == semesterId : isEquals;
		return isEquals;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 31).append(userAvailability).append(studentPreferenceState)
				.append(courseSemesterState).append(semesterId).toHashCode();
	}
}
