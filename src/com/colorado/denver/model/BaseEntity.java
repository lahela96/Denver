package com.colorado.denver.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

//There should be NO instance of this entity! Create children of this entity instead!
@MappedSuperclass
public abstract class BaseEntity<T> implements Serializable, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2741548100143780881L;
	public static final String ID = "id";
	public static final String OBJECT_CLASS = "objectClass";
	public static final String CREATION_DATE = "creationDate";
	public static final String CRUD = "crud";

	public transient Date creationDate;

	@Transient
	public int crud;// Both transient for Hibernate

	// Important for identifying the used object. This property is set during Hibernate create
	public String objectClass;
	private String id;

	public BaseEntity() {

	}

	@Id
	@GenericGenerator(name = "custom_id", strategy = "com.colorado.denver.services.persistence.HibernateIdGenerator")
	@GeneratedValue(generator = "custom_id")
	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObjectClass() {
		return objectClass;
	}

	public void setObjectClass(String objectClass) {
		this.objectClass = objectClass;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Transient
	public abstract String getPrefix();

	@Transient
	public abstract String setPrefix();

	@Transient
	public int getCrud() {
		return this.crud;
	}

	public void setCrud(int crud) {
		this.crud = crud;
	}
}
