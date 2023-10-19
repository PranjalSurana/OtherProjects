package com.fidelity.business;

import java.time.LocalDate;

public class Employee {
	private int id;
	private String name;
	private String job;
	private int manager;
	private LocalDate hireDate;
	private double salary;
	private double commission;
//	private int deptId;

	private Department department;

	public Employee() {
	}

	// All Eclipse-generated from here
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public int getManager() {
		return manager;
	}
	public void setManager(int manager) {
		this.manager = manager;
	}
	public LocalDate getHireDate() {
		return hireDate;
	}
	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public double getCommission() {
		return commission;
	}
	public void setCommission(double commission) {
		this.commission = commission;
	}
//	public int getDeptId() {
//		return deptId;
//	}
//	public void setDeptId(int deptId) {
//		this.deptId = deptId;
//	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}



}