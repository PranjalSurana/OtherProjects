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
	private int deptId;

	public Employee() {
	}

	// All Eclipse-generated from here
	public Employee(int id, String name, String job, int manager, LocalDate hireDate, double salary, double commission,
			int deptId) {
		this.id = id;
		this.name = name;
		this.job = job;
		this.manager = manager;
		this.hireDate = hireDate;
		this.salary = salary;
		this.commission = commission;
		this.deptId = deptId;
	}

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
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(commission);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + deptId;
		result = prime * result + ((hireDate == null) ? 0 : hireDate.hashCode());
		result = prime * result + id;
		result = prime * result + ((job == null) ? 0 : job.hashCode());
		result = prime * result + manager;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		temp = Double.doubleToLongBits(salary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Employee other = (Employee) obj;
		if (Double.doubleToLongBits(commission) != Double.doubleToLongBits(other.commission))
			return false;
		if (deptId != other.deptId)
			return false;
		if (hireDate == null) {
			if (other.hireDate != null)
				return false;
		} else if (!hireDate.equals(other.hireDate))
			return false;
		if (id != other.id)
			return false;
		if (job == null) {
			if (other.job != null)
				return false;
		} else if (!job.equals(other.job))
			return false;
		if (manager != other.manager)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(salary) != Double.doubleToLongBits(other.salary))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", job=" + job + ", manager=" + manager + ", hireDate="
				+ hireDate + ", salary=" + salary + ", commission=" + commission + ", deptId=" + deptId + "]";
	}	

}
