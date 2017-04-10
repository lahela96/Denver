package com.colorado.denver.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "Solution")
public class Solution extends EducationEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8150720003975451024L;

	public static final String SOLUTION = "solution";
	public static final String CODE = "code";
	public static final String EXERCISE = "exercise";
	public static final String SUBMITTED = "submitted";
	public static final String ANSWER = "answer";
	public static final String CORRECT = "correct";
	public static final String HAS_BEEN_MODIFIED = "hasBeenModified";
	public static final String MESSAGE = "message";

	private String anwswer;
	private String message;
	private boolean submitted;
	private boolean correct;
	private String code;
	private boolean hasBeenModified;
	// reference to the exercise
	private Exercise exercise;

	public Solution() {

	}

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "exerciseID")
	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	@Column(name = Solution.CODE, columnDefinition = "TEXT")
	public String getCode() {
		return code;
	}

	public boolean isSubmitted() {
		return submitted;
	}

	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	@Override
	@Transient
	public String getPrefix() {
		return SOLUTION;
	}

	@Override
	@Transient
	public String setPrefix() {
		return getPrefix();
	}

	public boolean isHasBeenModified() {
		return hasBeenModified;
	}

	public void setHasBeenModified(boolean hasBeenModified) {
		this.hasBeenModified = hasBeenModified;
	}

	public String getAnwswer() {
		return anwswer;
	}

	public void setAnwswer(String anwswer) {
		this.anwswer = anwswer;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
