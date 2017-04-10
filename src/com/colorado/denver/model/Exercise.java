package com.colorado.denver.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "Exercise")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Exercise extends EducationEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7599837012217952314L;
	public static final String EXERCISE = "exercise";
	public static final String CODE = "code";
	public static final String SOLUTION_CODE = "solution_code";
	public static final String ANSWER = "answer";
	public static final String HAS_BEEN_MODIFIED = "hasBeenModified";
	public static final String LANGUAGE = "language";

	private Set<Lecture> lectures;

	private Date deadline;
	private String anwswer;
	private boolean hasBeenModified;
	private String videoLink;
	// template code
	private String code;
	private String solution_code;
	private String inputType;
	private String outputType;
	private String input;
	private String language;

	public Exercise() {

	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "lectures_exercises", joinColumns = { @JoinColumn(name = "exercises_id") }, inverseJoinColumns = { @JoinColumn(name = "lectures_id") })
	// @Cascade({ CascadeType.ALL })
	public Set<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(Set<Lecture> lectures) {
		this.lectures = lectures;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getVideoLink() {
		return videoLink;
	}

	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}

	@Column(name = Exercise.CODE, columnDefinition = "TEXT")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = Exercise.SOLUTION_CODE, columnDefinition = "TEXT")
	public String getSolution_code() {
		return solution_code;
	}

	public void setSolution_code(String solution_code) {
		this.solution_code = solution_code;
	}

	@Override
	@Transient
	public String getPrefix() {
		return EXERCISE;
	}

	@Override
	@Transient
	public String setPrefix() {
		return getPrefix();
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getOutputType() {
		return outputType;
	}

	public void setOutputType(String outputType) {
		this.outputType = outputType;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getAnwswer() {
		return anwswer;
	}

	public void setAnwswer(String anwswer) {
		this.anwswer = anwswer;
	}

	public boolean isHasBeenModified() {
		return hasBeenModified;
	}

	public void setHasBeenModified(boolean hasBeenModified) {
		this.hasBeenModified = hasBeenModified;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
