package com.fidelity.advanced;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

// This class is a complete fake. It does nothing useful except demonstrate bean lifecycle
@Repository("dao")
public class DepartmentDao {
	private String status = "UNINITIALIZED";
	
	private String name;

	public DepartmentDao() {
		System.out.println("In constructor");
		changeStatus("CONSTRUCTED");
	}

	// Call this method after the bean is fully configured
	@PostConstruct
	public void init() {
		System.out.println("In init()");
		changeStatus("INITIALIZED");
	}
	
	// Call this method when we no longer need the bean
	@PreDestroy
	public void close() {
		System.out.println("In close()");
		changeStatus("CLOSED");
	}
	
	// Record internal status of bean fof illustration
	private void changeStatus(String newStatus) {
		System.out.println("Status change: " + status + " -> " + newStatus);
		status = newStatus;
	}

	// Getters and setters
	public String getName() {
		return name;
	}

	@Value("hello")
	public void setName(String name) {
		this.name = name;
		System.out.println("Set name: " + name);
		changeStatus("SET NAME");
	}
	

	public String getStatus() {
		return status;
	}
	
	// Simulated database query!
	public void queryAllDepartments() {
		System.out.println("Get all departments from database");
	}

}
