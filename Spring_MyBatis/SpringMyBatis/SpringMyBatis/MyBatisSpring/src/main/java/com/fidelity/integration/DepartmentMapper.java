package com.fidelity.integration;

import com.fidelity.business.Department;
import com.fidelity.business.Employee;

import java.util.List;

public interface DepartmentMapper {

    public List<Department> getDepartments();

    public List<Employee> getEmployees(int deptno);

}