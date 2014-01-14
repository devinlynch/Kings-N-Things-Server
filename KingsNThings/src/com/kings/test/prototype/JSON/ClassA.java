package com.kings.test.prototype.JSON;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ClassA implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private ClassB classB;
	private Set<ClassC> classCs;
	
	public ClassA() {
		
	}
	
	public static ClassA testCreate(){
		ClassA a = new ClassA();
		a.setName("ClassA");
		a.setClassB(ClassB.testCreate(a));
		
		ClassC c1= ClassC.testCreate(1);
		ClassC c2= ClassC.testCreate(2);
		ClassC c3= ClassC.testCreate(3);
		ClassC[] arr = {c1,c2,c3}; 
		
		a.setClassCs(new HashSet<ClassC>(Arrays.asList(arr)));
		return a;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ClassB getClassB() {
		return classB;
	}
	public void setClassB(ClassB classB) {
		this.classB = classB;
	}
	public Set<ClassC> getClassCs() {
		return classCs;
	}
	public void setClassCs(Set<ClassC> classCs) {
		this.classCs = classCs;
	}
	
	
}
