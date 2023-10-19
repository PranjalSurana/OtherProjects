package com.fidelity.advanced;

// This class is a complete fake. It does nothing useful except demonstrate bean lifecycle
public class DepartmentDao {
	private String status = "UNINITIALIZED";
	
	private String name;

	public DepartmentDao() {
		System.out.println("In constructor");
		changeStatus("CONSTRUCTED");
	}

	// Call this method after the bean is fully configured
	public void init() {
		System.out.println("In init()");
		changeStatus("INITIALIZED");
	}
	
	// Call this method when we no longer need the bean
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
