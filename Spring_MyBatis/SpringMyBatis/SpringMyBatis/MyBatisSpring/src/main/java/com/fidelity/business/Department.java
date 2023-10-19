package com.fidelity.business;

import java.util.Objects;
import java.util.Set;

public class Department {
	private int id;
	private String name;
	private String location;
	private Set<Employee> employees;

	Department() {}

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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Department that)) return false;
		return getId() == that.getId() && Objects.equals(getName(), that.getName()) && Objects.equals(getLocation(), that.getLocation()) && Objects.equals(getEmployees(), that.getEmployees());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName(), getLocation(), getEmployees());
	}

	@Override
	public String toString() {
		return "Department{" +
				"id=" + id +
				", name='" + name + '\'' +
				", location='" + location + '\'' +
				", employees=" + employees +
				'}';
	}

}