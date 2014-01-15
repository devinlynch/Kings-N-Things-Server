package com.kings.test.prototype.JSON;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ClassB implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	
	@JsonIgnore
	private ClassA owner;
	
	public ClassB(){
		
	}
	
	public static ClassB testCreate(ClassA owner) {
		ClassB b = new ClassB();
		b.setName("ClassB");
		b.setOwner(owner);
		return b;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ClassA getOwner() {
		return owner;
	}
	public void setOwner(ClassA owner) {
		this.owner = owner;
	}
	
	
	
}
