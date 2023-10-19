package com.fidelity.integration;

import java.util.List;
import java.util.Map;

import com.fidelity.business.Department;

public interface DepartmentMapper {
	List<Department> getAllDepartments();
	List<Department> getAllDepartmentsAndEmployees();
	int insertDepartment(Department department);
	int updateDepartment(Department department);
	// Not asked for, but allows us to demonstrate the constraint violation
	int deleteDepartment(int deptId);

	void reassignEmployeesDeleteDepartment(Map<Object, Object> parameterMap);

}
