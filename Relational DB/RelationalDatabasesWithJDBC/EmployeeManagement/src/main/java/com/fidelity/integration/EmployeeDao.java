package com.fidelity.integration;

import java.util.List;

import javax.sql.DataSource;

import com.fidelity.model.Employee;

public abstract class EmployeeDao {
	private DataSource dataSource;

	public EmployeeDao(DataSource datasource) {
		this.dataSource = datasource;
	}

	protected DataSource getDataSource() {
		return dataSource;
	}

	public abstract List<Employee> queryAllEmployees();

	public abstract Employee queryEmployeeById(int empNo);

	public abstract Employee queryEmployeeByIdWithPerformance(int empNo);

	public abstract List<Employee> queryEmployeeByName(String name);

	public abstract List<Employee> queryEmployeeByDepartment(int departmentId);

	public abstract Employee insertEmployee(Employee employee);

	public abstract Employee updateEmployee(Employee upd7369);

	public abstract boolean deleteEmployee(int id);

}