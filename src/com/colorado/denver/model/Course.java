package com.colorado.denver.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "Course")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Course extends EducationEntity {
	/**
	 * 
	 */
	public static final String COURSE = "course";
	public static final String USERS = "users";
	public static final String LECTURES = "lectures";

	private static final long serialVersionUID = 917641590246636493L;

	// The students in the course
	private Set<User> users;
	private Set<Lecture> lectures;

	public Course() {

	}

	@OneToMany(fetch = FetchType.EAGER)
	@Cascade({ CascadeType.ALL })
	public Set<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(Set<Lecture> lectures) {
		this.lectures = lectures;
	}

	@OneToMany(fetch = FetchType.EAGER)
	@Cascade({ CascadeType.ALL })
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	@Transient
	public String getPrefix() {
		return COURSE;
	}

	@Override
	public String setPrefix() {
		return getPrefix();
	}

}
