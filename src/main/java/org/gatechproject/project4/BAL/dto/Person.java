package org.gatechproject.project4.BAL.dto;

import org.gatechprojects.project4.SharedDataModules.User;

/**
 * 
 * Abstract class which provides the shared information that users in the system
 * share.
 * 
 * @author ubuntu
 *
 */
public abstract class Person {
	private String firstName;
	private String lastName;
	private int userId;

	public Person() {
	}

	public Person(User user) {
		this.userId = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getUserId() {
		return userId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setUserId(int id) {
		this.userId = id;
	}

}
