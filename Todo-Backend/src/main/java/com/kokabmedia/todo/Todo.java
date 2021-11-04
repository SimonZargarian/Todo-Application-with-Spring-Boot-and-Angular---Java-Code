package com.kokabmedia.todo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
 * This is a model class for the purpose of retrieving, creating, updating, deleting 
 * data with REST resources as well as with the database, mapping HTTP POST request 
 * body data to java objects with controller methods in the UserResource and the 
 * UserJPAResource class.  
 * 
 * The @Entity annotation from javax.persistence enables the JPA framework to manage 
 * the User class as a JPA entity. The Todo class is an entity and will be  mapped to a 
 * database table with the name Todo. 
 * 
 * The @Entity annotation will automatically with Hibernate, JPA and Spring auto 
 * configuration create a Todo table in the H2 in memory database.
 */
@Entity
public class Todo {
	
	/*
	 * The @Id annotation makes this field a primary key in the database table.
	 * 
	 * The @GeneratedValue annotation makes the Hibernate generate the primary 
	 * key value.
	 * 
	 * Primary key will uniquely identify each row in a database table.
	 */
	@Id
	@GeneratedValue
	private Long id;
	
	private String username;
	private String description;
	private Date targetDate;
	private boolean isDone;
	
	protected Todo() {
		
	}
	
	public Todo(long id, String username, String description, Date targetDate, boolean isDone) {
		super();
		this.id = id;
		this.username = username;
		this.description = description;
		this.targetDate = targetDate;
		this.isDone = isDone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	/*
	 * The goal of the equals() method is to verify that two objects are equal in the represented data. 
	 * The standard equals() method of the object class does not look at the memory location of the 
	 * objects it only verifies the amount of instances created, it does not look into the underlying 
	 * data of the created objects. The overwritten equals() method makes sure that two equal instances 
	 * result with the same hashCode. 
	 * 
	 * The hashCode() method returns a unique number for each instance, the unique number is the memory 
	 * location of the object converted into a integer, the hashCode method lets the system identify an 
	 * object in the heap memory of other objects.
	 * 
	 * The hashCode() method and the equals() method stop the system from inserting a object if two java 
	 * objects represent the same object with the same memory location, they stop duplicated objects from 
	 * being represented as two unique objects.   
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Todo other = (Todo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
}
