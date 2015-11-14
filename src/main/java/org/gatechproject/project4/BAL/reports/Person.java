package org.gatechproject.project4.BAL.reports;

import org.gatechprojects.project4.SharedDataModules.User;

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

	public int getUserId() {
		return userId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setUserId(int id) {
		this.userId = id;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
