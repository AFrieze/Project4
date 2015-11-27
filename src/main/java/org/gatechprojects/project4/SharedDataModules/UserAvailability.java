package org.gatechprojects.project4.SharedDataModules;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_availability")
public class UserAvailability {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "semester_id")
	private Semester semester;

	private boolean isShadow = false;

	public int getId() {
		return id;
	}

	public Semester getSemester() {
		return semester;
	}

	public User getUser() {
		return user;
	}

	public boolean isShadow() {
		return isShadow;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public void setShadow(boolean isShadow) {
		this.isShadow = isShadow;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
