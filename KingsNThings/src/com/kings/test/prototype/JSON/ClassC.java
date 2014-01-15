package com.kings.test.prototype.JSON;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ClassC implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String name;
	public Set<Integer> values;
	
	public ClassC(){
		
	}
	
	public static ClassC testCreate(int num) {
		ClassC c = new ClassC();
		c.setName("ClassC_"+num);
		
		Set<Integer> set = new HashSet<Integer>();
		set.add(num*(num*5));
		set.add(num*(num*5));
		set.add(num*(num*5));
		set.add(num*(num*5));
		
		c.setValues(set);
		return c;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Integer> getValues() {
		return values;
	}

	public void setValues(Set<Integer> values) {
		this.values = values;
	}
}
