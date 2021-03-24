package com.home.timemanagment;

import java.util.UUID;

public class Customer {
	
	private String id;
    private String name;
    
    @SuppressWarnings("unused")
	private Customer(String name) {
		
	}
    
    @SuppressWarnings("unused")
	private Customer() {
		
	}
    
	public Customer(String id, String name) {
		
		this.id = id;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	
	public static Customer createNew(String name) {
		
		String id = UUID.randomUUID().toString();
		return new Customer(id, name);
		
	}
	
	

}
